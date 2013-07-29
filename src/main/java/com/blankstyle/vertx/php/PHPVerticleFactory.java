package com.blankstyle.vertx.php;

import org.vertx.java.core.VertxException;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;
import org.vertx.java.platform.VerticleFactory;

import com.blankstyle.vertx.php.http.HttpModule;
import com.blankstyle.vertx.php.net.NetModule;
import com.caucho.quercus.QuercusContext;
import com.caucho.quercus.QuercusDieException;
import com.caucho.quercus.QuercusEngine;
import com.caucho.quercus.QuercusExitException;
import com.caucho.quercus.module.ModuleContext;

/**
 * A PHP verticle.
 */
public class PHPVerticleFactory implements VerticleFactory {

  private ClassLoader cl;

  public static org.vertx.java.core.Vertx vertx;

  public static org.vertx.java.platform.Container container;

  @Override
  public void init(org.vertx.java.core.Vertx vertx, org.vertx.java.platform.Container container, ClassLoader cl) {
    this.cl = cl;
    PHPVerticleFactory.vertx = vertx;
    PHPVerticleFactory.container = container;
  }

  @Override
  public Verticle createVerticle(String main) throws Exception {
    return new PHPVerticle(main);
  }

  @Override
  public void reportException(Logger logger, Throwable t) {
    t.printStackTrace();
  }

  @Override
  public void close() {
    
  }

  /**
   * A PHP Verticle that runs PHP scripts via Quercus.
   */
  private class PHPVerticle extends Verticle {

    private final String script;

    QuercusEngine engine;

    PHPVerticle(String script) {
      this.script = script;
    }

    @Override
    public void start() {
      engine = new QuercusEngine();
      QuercusContext context = engine.getQuercus();
      ModuleContext modules = context.getModuleContext();
      modules.addModule("vertx", new VertxModule());
      modules.addModule("vertx_http", new HttpModule());
      modules.addModule("vertx_net", new NetModule());
      context.addJavaClass("Vertx", com.blankstyle.vertx.php.Vertx.class);
      context.addJavaClass("Container", com.blankstyle.vertx.php.Container.class);
      context.addJavaClass("HttpServer", com.blankstyle.vertx.php.http.HttpServer.class);
      context.addJavaClass("HttpClient", com.blankstyle.vertx.php.http.HttpClient.class);
      context.addJavaClass("NetServer", com.blankstyle.vertx.php.net.NetServer.class);
      context.addJavaClass("NetClient", com.blankstyle.vertx.php.net.NetClient.class);
      context.init();
      Vertx.init(PHPVerticleFactory.vertx);
      Container.init(PHPVerticleFactory.container);
      try {
        try {
          engine.executeFile(script);
        }
        catch (QuercusDieException e) {
          // Do nothing.
        }
        catch (QuercusExitException e) {
          // Do nothing.
        }
      }
      catch (Exception e) {
        throw new VertxException(e);
      }
    }

  }

}
