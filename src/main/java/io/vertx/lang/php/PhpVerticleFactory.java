/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertx.lang.php;


import io.vertx.lang.php.streams.impl.InstantWriteStream;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.vertx.java.core.VertxException;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import org.vertx.java.platform.VerticleFactory;

import com.caucho.quercus.Location;
import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.QuercusException;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NullValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.function.AbstractFunction;
import com.caucho.quercus.page.InterpretedPage;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.quercus.parser.QuercusParser;
import com.caucho.quercus.program.QuercusProgram;
import com.caucho.vfs.ReadStream;
import com.caucho.vfs.StdoutStream;
import com.caucho.vfs.StringPath;
import com.caucho.vfs.WriteStream;

/**
 * A PHP verticle factory.
 * 
 * The PHP implementation is run on the Java-based Quercus PHP engine.
 * 
 * @author Jordan Halterman
 */
public class PhpVerticleFactory implements VerticleFactory {

  private ClassLoader cl;

  public static org.vertx.java.core.Vertx vertx;

  public static org.vertx.java.platform.Container container;

  public QuercusContext context = null;

  /**
   * Initializes the factory.
   */
  @Override
  public void init(org.vertx.java.core.Vertx vertx, org.vertx.java.platform.Container container, ClassLoader cl) {
    this.cl = cl;
    PhpVerticleFactory.vertx = vertx;
    PhpVerticleFactory.container = container;
  }

  protected void initQuercusContext() {
      if (context != null) {
        return;
      }
      
      ClassLoader old = Thread.currentThread().getContextClassLoader();
      try {
	      Thread.currentThread().setContextClassLoader(cl);
	    
	      context = new QuercusContext();
	      // Setting PHP's error_reporting to 0 makes Quercus give us more
	      // interesting exception messages and thus better error reporting.
	      context.setIni("error_reporting", "0");
	
	      // Make vertx-php classes available in the PHP code context.
	      // Note that for now we only make available classes which should
	      // be instantiated outside the context of the internal Vert.x
	      // library. However, once default constructors have been supplied
	      // for the various wrapper classes, we should expose as many classes
	      // as possible for extensibility's sake.
	      context.addJavaClass("Vertx", io.vertx.lang.php.Vertx.class);
        context.addJavaClass("Vertx\\EventBus\\ReplyException", io.vertx.lang.php.eventbus.ReplyException.class);
        context.addJavaClass("Vertx\\EventBus\\ReplyFailure", io.vertx.lang.php.eventbus.ReplyFailure.class);
	      context.addJavaClass("Vertx\\Http\\HttpServer", io.vertx.lang.php.http.HttpServer.class);
	      context.addJavaClass("Vertx\\Http\\HttpClient", io.vertx.lang.php.http.HttpClient.class);
	      context.addJavaClass("Vertx\\Http\\RouteMatcher", io.vertx.lang.php.http.RouteMatcher.class);
	      context.addJavaClass("Vertx\\Net\\NetServer", io.vertx.lang.php.net.NetServer.class);
	      context.addJavaClass("Vertx\\Net\\NetClient", io.vertx.lang.php.net.NetClient.class);
	      context.addJavaClass("Vertx\\Net\\NetSocket", io.vertx.lang.php.net.NetSocket.class);
	      context.addJavaClass("Vertx\\Buffer", io.vertx.lang.php.buffer.Buffer.class);
	      context.addJavaClass("Vertx\\Logger", org.vertx.java.core.logging.Logger.class);
	      context.addJavaClass("Vertx\\Pump", io.vertx.lang.php.streams.Pump.class);
	      context.addJavaClass("Vertx\\ParseTools\\RecordParser", io.vertx.lang.php.parsetools.RecordParser.class);
        context.addJavaClass("Vertx\\ReadStream", io.vertx.lang.php.streams.ReadStream.class);
        context.addJavaClass("Vertx\\WriteStream", io.vertx.lang.php.streams.WriteStream.class);
        context.addJavaClass("Vertx\\VertxException", org.vertx.java.core.VertxException.class);
	
	      // Add PHP test helpers.
	      context.addJavaClass("Vertx\\Test\\TestRunner", io.vertx.lang.php.testtools.PhpTestRunner.class);
	      context.addJavaClass("Vertx\\Test\\PhpTestCase", io.vertx.lang.php.testtools.PhpTestCase.class);
	      
	      context.init();
	      context.start();
	      
	      addRequireVertxToContext();

	      context.start();
	
	      AbstractFunction func = context.findFunction(context.createString("phpinfo"));
	
	      if (func == null) {
	        context = null;
	        throw new VertxException("PHP Environment didn't load properly");
	      }
      } catch (Exception e) {
    	  e.printStackTrace();
    	  throw e;
      } finally {
    	  Thread.currentThread().setContextClassLoader(old);
      }
  }

  private void addRequireVertxToContext() {
    context.setFunction(context.createString("require_vertx"), new AbstractFunction() {

		private static final long serialVersionUID = 5350698219672910902L;

		@Override
		public Value call(Env env, Value[] args) {
			if (args.length != 1) {
				throw new IllegalArgumentException("require_vertx: missing Argument path");
			}

			String resourceName = args[0].toString();
			URL resourcePath = cl.getResource(args[0].toString());
			try {
				String script = String.format("require('%s');", resourcePath.toString());
				QuercusProgram program = context.parseCode(context.createString(script));
				program.execute(env);
			} catch (NullPointerException np) {
				if (Vertx.logger() == null) {
					System.out.println(String.format("Could not find Vertx resource '%s''", resourceName));
				} else {
					Vertx.logger().error(String.format("Could not find Vertx resource '%s''", resourceName));
				}
				np.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return NullValue.create();
		}
	});
  }

  /**
   * @return the Quercus context for all Verticle created by this factory
   */
  public QuercusContext getQuercusContext() {
    return context;
  }

  /**
   * Creates a verticle instance.
   */
  @Override
  public Verticle createVerticle(String main) throws Exception {
    if (context == null) {
     this.initQuercusContext();
    }
    String scriptPath = findScript(main);
    if (scriptPath == null) {
      throw new VertxException(String.format("%s is not a valid PHP verticle.", main));
    }
    return new PhpVerticle(context, scriptPath);
  }

  /**
   * Finds the full path to a PHP script.
   */
  private String findScript(String script) {
    URL filename = cl.getResource(script);
    if (filename != null) {
      File scriptFile = new File(filename.getPath());
	    if (scriptFile.exists()) {
	      return scriptFile.toPath().toString();
	    }
    }
    return null;
  }

  /**
   * Reports an exception in the verticle.
   */
  @Override
  public void reportException(Logger logger, Throwable t) {
    // A Quercus language exception.
    if (t instanceof QuercusException) {
      Env env = Env.getCurrent();
      Location location = env.getLocation();

      logger.error("\nAn exception occured in a PHP verticle.");

      // TODO: This stack trace should show only PHP related called, not
      // Java calls. Currently it only shows the trace of Java code execution.
      // logger.error(env.getStackTraceAsString(t, env.getLocation()) + "\n");
      String className = location.getClassName();
      String funcName = location.getFunctionName();
      if (funcName != null && funcName != "NULL" && !funcName.startsWith("__quercus_")) {
        if (className != "NULL" && funcName != "NULL" && !funcName.startsWith("__quercus_")) {
          logger.error(String.format("%s in %s on line %d in %s::%s()", t.getMessage(), location.getFileName(),
              location.getLineNumber(), className, funcName));
        }
        else {
          logger.error(String.format("%s in %s on line %d in %s()", t.getMessage(), location.getFileName(),
              location.getLineNumber(), funcName));
        }
      }
      else {
        logger.error(String.format("%s in %s on line %d", t.getMessage(), location.getFileName(),
            location.getLineNumber()));
      }
    }
    else {
      t.printStackTrace();
    }
  }

  /**
   * Closes the verticle.
   */
  @Override
  public void close() {
    AbstractFunction function = context.findFunction(StringValue.create("vertx_stop").toStringValue());
    if (function != null) {
      function.call(Env.getCurrent());
    }
    context.close();
    context = null;
  }

  @Override
  public void finalize() {
    close();
  }

  /**
   * A PHP Verticle that runs PHP scripts via Quercus.
   */
  private class PhpVerticle extends Verticle {

    /**
     * The path to the verticle PHP script.
     */
    private final String scriptName;

    private final QuercusContext context;

    private WriteStream out;

    private Env globalEnv;

    PhpVerticle(QuercusContext context, String script) {
      this.scriptName = script;
      this.context = context;
    }

    /**
     * Starts the verticle.
     */
    @Override
    public void start() {
      String classLoaderScript = "spl_autoload_register(function($class) {" +
  		    "require_vertx(str_replace('\\\\', '/', $class) . '.php');" +
          "});";
      
      // Evaluate a single line script which includes the verticle
      // script. This ensures that exceptions can be accurately logged
      // because Quercus will record actual file names rather than a
      // generic "eval" name.
      String script = String.format("<?php " + classLoaderScript + "require '%s'; ?>", this.scriptName);
      
      try (ReadStream reader = (new StringPath(script)).openRead()) {
        QuercusProgram program = QuercusParser.parse(context, null, reader);
        QuercusPage page = new InterpretedPage(program);

        out = new InstantWriteStream(StdoutStream.create());
        globalEnv = new Env(context, page, out, null, null);
        globalEnv.start();

        program.execute(globalEnv);
        out.flush();
      } catch (IOException e) {
        throw new VertxException("Cannot parse PHP verticle: " + this.scriptName);
      } catch (Exception e) {
        throw new VertxException(e);
      }

    }

    @Override
    public void stop() {
      globalEnv = null;

      if (out != null) {
	      try {
	        out.close();
	      } catch (IOException e) {
	        throw new VertxException(e);
	      }
	      out = null;
      }
    }

    @Override
    public void finalize() {
      stop();
    }

  }

}
