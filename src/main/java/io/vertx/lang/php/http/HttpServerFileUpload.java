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
package io.vertx.lang.php.http;

import io.vertx.lang.php.streams.ReadStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.env.LongValue;
import com.caucho.quercus.env.StringValue;

/**
 * A PHP compatible implementation of the Vert.x HttpServerFileUpload.
 * 
 * @author Jordan Halterman
 */
public class HttpServerFileUpload implements ReadStream<HttpServerFileUpload> {

  private org.vertx.java.core.http.HttpServerFileUpload upload;

  public HttpServerFileUpload(org.vertx.java.core.http.HttpServerFileUpload upload) {
    this.upload = upload;
  }

  public HttpServerFileUpload(Env env, org.vertx.java.core.http.HttpServerFileUpload upload) {
    this.upload = upload;
  }

  public StringValue filename(Env env) {
    return env.createString(upload.filename());
  }

  public StringValue contentType(Env env) {
    return env.createString(upload.contentType());
  }

  public StringValue name(Env env) {
    return env.createString(upload.name());
  }

  public LongValue size(Env env) {
    return env.wrapJava(upload.size()).toLongValue();
  }

  public StringValue contentTransferEncoding(Env env) {
    return env.createString(upload.contentTransferEncoding());
  }

  public HttpServerFileUpload streamToFileSystem(Env env, StringValue filename) {
    upload.streamToFileSystem(filename.toString());
    return this;
  }

  @Override
  public HttpServerFileUpload pause(Env env) {
    upload.pause();
    return this;
  }

  @Override
  public HttpServerFileUpload resume(Env env) {
    upload.resume();
    return this;
  }

  @Override
  public HttpServerFileUpload dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerFileUpload::dataHandler() must be callable.");
    upload.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  @Override
  public HttpServerFileUpload endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerFileUpload::endHandler() must be callable.");
    upload.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpServerFileUpload";
  }

}
