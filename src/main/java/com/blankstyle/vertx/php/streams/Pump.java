package com.blankstyle.vertx.php.streams;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.ObjectValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x Pump.
 */
public class Pump {

  /**
   * The current PHP environment.
   */
  private Env env;

  /**
   * A read stream.
   */
  private ObjectValue readStream;

  /**
   * A write stream.
   */
  private ObjectValue writeStream;

  private int pumped;

  private final Handler<Void> drainHandler = new Handler<Void>() {
    public void handle(Void v) {
      readStream.callMethod(env, env.createString("resume"));
    }
  };

  private final Handler<Buffer> dataHandler = new Handler<Buffer>() {
    public void handle(Buffer buffer) {
      writeStream.callMethod(env, env.createString("write"), env.wrapJava(buffer));
      pumped += buffer.length();
      if (writeStream.callMethod(env, env.createString("writeQueueFull")).toBoolean()) {
        readStream.callMethod(env, env.createString("pause"));
        writeStream.callMethod(env, env.createString("drainHandler"), env.wrapJava(drainHandler));
      }
    }
  };

  public Pump(Env env, ObjectValue readStream, ObjectValue writeStream, @Optional NumberValue writeQueueMaxSize) {
    this.env = env;
    this.readStream = readStream;
    this.writeStream = writeStream;
    if (writeQueueMaxSize != null) {
      Value[] args = {writeQueueMaxSize};
      this.writeStream.callMethod(env, env.createString("setWriteQueueMaxSize"), args);
    }
  }

  /**
   * Creates a new pump.
   */
  public static Pump createPump(Env env, ObjectValue readStream, ObjectValue writeStream, @Optional NumberValue writeQueueMaxSize) {
    return new Pump(env, readStream, writeStream, writeQueueMaxSize);
  }

  /**
   * Sets the write queue max size.
   */
  public Pump setWriteQueueMaxSize(Env env, NumberValue maxSize) {
    Value[] args = {maxSize};
    writeStream.callMethod(env, env.createString("setWriteQueueMaxSize"), args);
    return this;
  }

  /**
   * Start the Pump. The Pump can be started and stopped multiple times.
   */
  public Pump start() {
    Value[] args = {env.wrapJava(dataHandler)};
    readStream.callMethod(env, env.createString("dataHandler"), args);
    return this;
  }

  /**
   * Stop the Pump. The Pump can be started and stopped multiple times.
   */
  public Pump stop() {
    Value[] args = {env.wrapJava(null)};
    writeStream.callMethod(env, env.createString("drainHandler"), args);
    readStream.callMethod(env, env.createString("dataHandler"), args);
    return this;
  }

  /**
   * Return the total number of bytes pumped by this pump.
   */
  public int bytesPumped() {
    return pumped;
  }

}
