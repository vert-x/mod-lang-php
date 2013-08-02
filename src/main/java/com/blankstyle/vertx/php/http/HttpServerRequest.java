package com.blankstyle.vertx.php.http;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.ArgumentModifier;
import com.blankstyle.vertx.php.Gettable;
import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callback;
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
    return env.wrapJava(request.headers());
  }

  public Value method(Env env) {
    return env.wrapJava(request.method());
  }

  public Value params(Env env) {
    return env.wrapJava(request.params());
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
    return env.wrapJava(request.netSocket());
  }

  public Value formAttributes(Env env) {
    return env.wrapJava(request.formAttributes());
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

  public HttpServerRequest uploadHandler(Env env, Callback handler) {
    request.uploadHandler(new Handler<org.vertx.java.core.http.HttpServerFileUpload>(env, handler, new ArgumentModifier<org.vertx.java.core.http.HttpServerFileUpload, HttpServerFileUpload>() {
      @Override
      public HttpServerFileUpload modify(org.vertx.java.core.http.HttpServerFileUpload upload) {
        return new HttpServerFileUpload(upload);
      }
    }));
    return this;
  }

  @Override
  public HttpServerRequest dataHandler(Env env, Callback handler) {
    request.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  @Override
  public HttpServerRequest endHandler(Env env, Callback handler) {
    request.endHandler(new Handler<Void>(env, handler));
    return this;
  }

  @Override
  public HttpServerRequest dataHandler(org.vertx.java.core.Handler<Buffer> handler) {
    request.dataHandler(handler);
    return this;
  }

}
