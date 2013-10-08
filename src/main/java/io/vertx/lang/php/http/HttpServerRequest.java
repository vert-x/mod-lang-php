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

import io.vertx.lang.php.Gettable;
import io.vertx.lang.php.Handler;
import io.vertx.lang.php.MultiMapArray;
import io.vertx.lang.php.ResultModifier;
import io.vertx.lang.php.net.NetSocket;
import io.vertx.lang.php.streams.ReadStream;
import io.vertx.lang.php.util.HandlerFactory;
import io.vertx.lang.php.util.PhpTypes;

import javax.net.ssl.SSLPeerUnverifiedException;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible implementation of the Vert.x HttpServerRequest.
 * 
 * @author Jordan Halterman
 */
public class HttpServerRequest implements ReadStream<HttpServerRequest>, Gettable {

  private org.vertx.java.core.http.HttpServerRequest request;

  public HttpServerRequest(org.vertx.java.core.http.HttpServerRequest request) {
    this.request = request;
  }

  public HttpServerRequest(Env env, org.vertx.java.core.http.HttpServerRequest request) {
    this.request = request;
  }

  @Override
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  public Value headers(Env env) {
    return env.wrapJava(new MultiMapArray(request.headers()));
  }

  public Value method(Env env) {
    return env.wrapJava(request.method());
  }

  public Value params(Env env) {
    return env.wrapJava(new MultiMapArray(request.params()));
  }

  public Value path(Env env) {
    return env.wrapJava(request.path());
  }

  public Value query(Env env) {
    return env.wrapJava(request.query());
  }

  public Value uri(Env env) {
    return env.wrapJava(request.uri());
  }

  public Value absoluteURI(Env env) {
    return env.wrapJava(request.absoluteURI());
  }

  public Value response(Env env) {
    return env.wrapJava(new HttpServerResponse(request.response()));
  }

  public Value version(Env env) {
    return env.wrapJava(request.version());
  }

  public Value remoteAddress(Env env) {
    return env.wrapJava(request.remoteAddress());
  }

  public Value netSocket(Env env) {
    return env.wrapJava(new NetSocket(request.netSocket()));
  }

  public Value formAttributes(Env env) {
    return env.wrapJava(new MultiMapArray(request.formAttributes()));
  }

  public Value peerCertificateChain(Env env) {
    try {
      return env.wrapJava(request.peerCertificateChain());
    }
    catch (SSLPeerUnverifiedException e) {
      return null;
    }
  }

  public Value expectMultiPart(Env env, BooleanValue expect) {
    request.expectMultiPart(expect.toBoolean());
    return env.wrapJava(this);
  }

  @Override
  public HttpServerRequest pause(Env env) {
    request.pause();
    return this;
  }

  @Override
  public HttpServerRequest resume(Env env) {
    request.resume();
    return this;
  }

  public HttpServerRequest bodyHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerRequest::bodyHandler() must be callable.");
    request.bodyHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  public HttpServerRequest uploadHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerRequest::uploadHandler() must be callable.");
    request.uploadHandler(new Handler<org.vertx.java.core.http.HttpServerFileUpload>(env, PhpTypes.toCallable(handler),
        new ResultModifier<org.vertx.java.core.http.HttpServerFileUpload, HttpServerFileUpload>() {
          @Override
          public HttpServerFileUpload modify(org.vertx.java.core.http.HttpServerFileUpload upload) {
            return new HttpServerFileUpload(upload);
          }
        }));
    return this;
  }

  @Override
  public HttpServerRequest dataHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerRequest::dataHandler() must be callable.");
    request.dataHandler(HandlerFactory.createBufferHandler(env, handler));
    return this;
  }

  @Override
  public HttpServerRequest endHandler(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler,
        "Handler argument to Vertx\\Http\\HttpServerRequest::endHandler() must be callable.");
    request.endHandler(HandlerFactory.createVoidHandler(env, handler));
    return this;
  }

  public String toString() {
    return "php:Vertx\\Http\\HttpServerRequest";
  }

}
