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
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.Value;

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
    @Override
    public void handle(Void v) {
      readStream.resume(env);
    }
  };

  private final Value drainHandlerValue = new Callback() {
    private static final long serialVersionUID = 1L;

    @Override
    public Value call(Env arg0, Value arg1) {
      drainHandler.handle(null);
      return env.wrapJava(this);
    }

    @Override
    public Value call(Env env, Value[] args) {
      drainHandler.handle(null);
      return env.wrapJava(this);
    }

    @Override
    public String getCallbackName() {
      return drainHandler.toString();
    }

    @Override
    public boolean isInternal(Env arg0) {
      return false;
    }

    @Override
    public boolean isValid(Env arg0) {
      return true;
    }

    @Override
    public boolean isCallable(Env env, boolean a, Value b) {
      return true;
    }
  };

  private final Handler<Buffer> dataHandler = new Handler<Buffer>() {
    @Override
    public void handle(Buffer buffer) {
      writeStream.write(env, env.wrapJava(buffer.toString()), null);
      pumped += buffer.length();
      if (writeStream.writeQueueFull(env).toBoolean()) {
        readStream.pause(env);
        writeStream.drainHandler(env, drainHandlerValue);
      }
    }
  };

  private final Value dataHandlerValue = new Callback() {
    private static final long serialVersionUID = 1L;

    @Override
    public Value call(Env arg0, Value arg1) {
      dataHandler.handle(new Buffer(arg1.toString()));
      return env.wrapJava(this);
    }

    @Override
    public Value call(Env env, Value[] args) {
      dataHandler.handle(new Buffer(args[0].toString()));
      return env.wrapJava(this);
    }

    @Override
    public String getCallbackName() {
      return dataHandler.toString();
    }

    @Override
    public boolean isInternal(Env arg0) {
      return false;
    }

    @Override
    public boolean isValid(Env arg0) {
      return true;
    }

    @Override
    public boolean isCallable(Env env, boolean a, Value b) {
      return true;
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
  public Pump start(Env env) {
    readStream.dataHandler(env, dataHandlerValue);
    return this;
  }

  /**
   * Stop the Pump. The Pump can be started and stopped multiple times.
   */
  public Pump stop(Env env) {
    writeStream.drainHandler(env, null);
    readStream.dataHandler(env, null);
    return this;
  }

  /**
   * Return the total number of bytes pumped by this pump.
   */
  public int bytesPumped(Env env) {
    return pumped;
  }

}
