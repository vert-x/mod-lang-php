package com.blankstyle.vertx.php;

import org.vertx.java.core.VertxException;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import org.vertx.java.platform.VerticleFactory;

import com.caucho.quercus.Location;
import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.QuercusDieException;
import com.caucho.quercus.QuercusEngine;
import com.caucho.quercus.QuercusException;
import com.caucho.quercus.QuercusExitException;
import com.caucho.quercus.env.Env;

/**
 * A PHP verticle factory.
 */
public class PHPVerticleFactory implements VerticleFactory {

  @SuppressWarnings("unused")
  private ClassLoader cl;

  private static org.vertx.java.core.Vertx vertx;

  private static org.vertx.java.platform.Container container;

  /**
   * Initializes the factory.
   */
  @Override
  public void init(org.vertx.java.core.Vertx vertx, org.vertx.java.platform.Container container, ClassLoader cl) {
    this.cl = cl;
    PHPVerticleFactory.vertx = vertx;
    PHPVerticleFactory.container = container;
  }

  /**
   * Creates a verticle instance.
   */
  @Override
  public Verticle createVerticle(String main) throws Exception {
    return new PHPVerticle(main);
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
      if (funcName != "NULL" && !funcName.startsWith("__quercus_")) {
        if (className != "NULL" && funcName != "NULL" && !funcName.startsWith("__quercus_")) {
          logger.error(String.format("%s in %s on line %d in %s::%s()", t.getMessage(), location.getFileName(), location.getLineNumber(), className, funcName));
        }
        else {
          logger.error(String.format("%s in %s on line %d in %s()", t.getMessage(), location.getFileName(), location.getLineNumber(), funcName));
        }
      }
      else {
        logger.error(String.format("%s in %s on line %d", t.getMessage(), location.getFileName(), location.getLineNumber()));
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
    
  }

  /**
   * A PHP Verticle that runs PHP scripts via Quercus.
   */
  private class PHPVerticle extends Verticle {

    /**
     * The path to the verticle PHP script.
     */
    private final String script;

    /**
     * A Quercus script engine instance.
     */
    QuercusEngine engine;

    PHPVerticle(String script) {
      this.script = script;
    }

    /**
     * Starts the verticle.
     */
    @Override
    public void start() {
      engine = new QuercusEngine();
      QuercusContext context = engine.getQuercus();

      // Setting PHP's error_reporting to 0 makes Quercus give us more
      // interesting exception messages and thus better error reporting.
      context.setIni("error_reporting", "0");

      // Make vertx-php classes available in the PHP code context.
      context.addJavaClass("Vertx", com.blankstyle.vertx.php.Vertx.class);
      context.addJavaClass("Container", com.blankstyle.vertx.php.Container.class);
      context.addJavaClass("Vertx\\Http\\HttpServer", com.blankstyle.vertx.php.http.HttpServer.class);
      context.addJavaClass("Vertx\\Http\\HttpClient", com.blankstyle.vertx.php.http.HttpClient.class);
      context.addJavaClass("Vertx\\Http\\RouteMatcher", com.blankstyle.vertx.php.http.RouteMatcher.class);
      context.addJavaClass("Vertx\\Net\\NetServer", com.blankstyle.vertx.php.net.NetServer.class);
      context.addJavaClass("Vertx\\Net\\NetClient", com.blankstyle.vertx.php.net.NetClient.class);
      context.addJavaClass("Vertx\\Net\\NetSocket", com.blankstyle.vertx.php.net.NetSocket.class);
      context.addJavaClass("Vertx\\Buffer\\Buffer", org.vertx.java.core.buffer.Buffer.class);
      context.addJavaClass("Vertx\\Logging\\Logger", org.vertx.java.core.logging.Logger.class);
      context.addJavaClass("Vertx\\Streams\\Pump", com.blankstyle.vertx.php.streams.Pump.class);
      context.addJavaClass("Vertx\\ParseTools\\RecordParser", org.vertx.java.core.parsetools.RecordParser.class);
      context.addJavaClass("Vertx\\SharedData\\SharedData", org.vertx.java.core.shareddata.SharedData.class);
      Vertx.init(PHPVerticleFactory.vertx);
      Container.init(PHPVerticleFactory.container);

      // Evaluate a single line script which includes the verticle
      // script. This ensures that exceptions can be accurately logged
      // because Quercus will record actual file names rather than a
      // generic "eval" name.
      try {
        engine.execute(String.format("<?php require '%s'; ?>", script));
      }
      catch (QuercusDieException e) {
        // The interpreter died, do nothing.
      }
      catch (QuercusExitException e) {
        // The interpreter exiting cleanly, do nothing.
      }
      catch (Exception e) {
        throw new VertxException(e);
      }
    }

  }

}
