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
package io.vertx.lang.php.file;

import io.vertx.lang.php.buffer.Buffer;
import io.vertx.lang.php.streams.ReadStream;
import io.vertx.lang.php.streams.WriteStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.annotation.Optional;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x AsyncFile.
 * 
 * @author Jordan Halterman
 */
public final class AsyncFile implements ReadStream<AsyncFile>, WriteStream<AsyncFile> {

  private org.vertx.java.core.file.AsyncFile file;

  public AsyncFile(org.vertx.java.core.file.AsyncFile file) {
    this.file = file;
  }

  /**
   * Reads from the file.
   */
  public AsyncFile read(Env env, Buffer buffer, NumberValue offset, NumberValue position, NumberValue length,
      Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::read() must be callable.");
    file.read(buffer.__toVertxBuffer(), offset.toInt(), position.toInt(), length.toInt(),
        HandlerFactory.createAsyncBufferHandler(env, handler));
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(Env env, Buffer buffer, NumberValue position, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::write() must be callable.");
    file.write(buffer.__toVertxBuffer(), position.toInt(), HandlerFactory.createAsyncVoidHandler(env, handler));
    return this;
  }

  /**
   * Writes a value to the socket.
   */
  public AsyncFile write(Env env, Value data, StringValue enc) {
    if (data.isString()) {
      file.write(new org.vertx.java.core.buffer.Buffer(data.toString()));
    }
    else if (data.isObject()) {
      file.write(((Buffer) data.toJavaObject(env, Buffer.class)).__toVertxBuffer());
    }
    return this;
  }

  /**
   * Sets the file data handler.
   */
  public AsyncFile dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::dataHandler() must be callable.");
    file.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  /**
   * Pauses producing on the file.
   */
  public AsyncFile pause(Env env) {
    file.pause();
    return this;
  }

  /**
   * Resumes producing on the file.
   */
  public AsyncFile resume(Env env) {
    file.resume();
    return this;
  }

  /**
   * Sets the file end handler.
   */
  public AsyncFile endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::endHandler() must be callable.");
    file.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  /**
   * Sets the file drain handler.
   */
  public AsyncFile drainHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\File\\AsyncFile::drainHandler() must be callable.");
    file.drainHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  /**
   * Sets the max write queue size.
   */
  public AsyncFile writeQueueMaxSize(Env env, NumberValue value) {
    file.setWriteQueueMaxSize(value.toJavaInteger());
    return this;
  }

  /**
   * Indicates whether the write queue is full.
   */
  public BooleanValue writeQueueFull(Env env) {
    return BooleanValue.create(file.writeQueueFull());
  }

  /**
   * Flushes the file.
   */
  public AsyncFile flush(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::flush() must be callable.");
      file.flush(HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      file.flush();
    }
    return this;
  }

  /**
   * Closes the file.
   */
  public void close(Env env, @Optional Value handler) {
    if (PhpTypes.notNull(handler)) {
      PhpTypes.assertCallable(env, handler, "Handler argument to Vertx\\File\\AsyncFile::close() must be callable.");
      file.flush(HandlerFactory.createAsyncVoidHandler(env, handler));
    }
    else {
      file.flush();
    }
  }

  /**
   * Sets the socket exception handler callback.
   */
  public AsyncFile exceptionHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\File\\AsyncFile::exceptionHandler() must be callable.");
    file.exceptionHandler(HandlerFactory.createExceptionHandler(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\File\\AsyncFile";
  }

}
