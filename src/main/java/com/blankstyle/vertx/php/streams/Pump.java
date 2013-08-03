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
package com.blankstyle.vertx.php.streams;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;

/**
 * A PHP compatible implementation of the Vert.x Pump.
 *
 * @author Jordan Halterman
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
      this.writeStream.writeQueueMaxSize(env, writeQueueMaxSize);
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
    writeStream.writeQueueMaxSize(env, maxSize);
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
