package com.blankstyle.vertx.php.streams;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;

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
  private ReadStream<?> readStream;

  /**
   * A write stream.
   */
  private WriteStream<?> writeStream;

  private int pumped;

  private final Handler<Void> drainHandler = new Handler<Void>() {
    public void handle(Void v) {
      readStream.resume(env);
    }
  };

  private final Handler<Buffer> dataHandler = new Handler<Buffer>() {
    public void handle(Buffer buffer) {
      writeStream.write(env, env.wrapJava(buffer), null);
      pumped += buffer.length();
      if (writeStream.writeQueueFull(env).toBoolean()) {
        readStream.pause(env);
        writeStream.drainHandler(drainHandler);
      }
    }
  };

  public Pump(Env env, ReadStream<?> readStream, WriteStream<?> writeStream, @Optional NumberValue writeQueueMaxSize) {
    this.env = env;
    this.readStream = readStream;
    this.writeStream = writeStream;
    if (writeQueueMaxSize != null) {
      this.writeStream.setWriteQueueMaxSize(env, writeQueueMaxSize);
    }
  }

  /**
   * Creates a new pump.
   */
  public static Pump createPump(Env env, ReadStream<?> readStream, WriteStream<?> writeStream, @Optional NumberValue writeQueueMaxSize) {
    return new Pump(env, readStream, writeStream, writeQueueMaxSize);
  }

  /**
   * Sets the write queue max size.
   */
  public Pump setWriteQueueMaxSize(Env env, NumberValue maxSize) {
    writeStream.setWriteQueueMaxSize(env, maxSize);
    return this;
  }

  /**
   * Start the Pump. The Pump can be started and stopped multiple times.
   */
  public Pump start() {
    readStream.dataHandler(dataHandler);
    return this;
  }

  /**
   * Stop the Pump. The Pump can be started and stopped multiple times.
   */
  public Pump stop() {
    writeStream.drainHandler(env, null);
    readStream.dataHandler(env, null);
    return this;
  }

  /**
   * Return the total number of bytes pumped by this pump.
   */
  public int bytesPumped() {
    return pumped;
  }

}
