package com.blankstyle.vertx.php.http;

import org.vertx.java.core.buffer.Buffer;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.streams.ReadStream;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
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
  public HttpServerFileUpload dataHandler(Env env, Callback handler) {
    upload.dataHandler(new Handler<Buffer>(env, handler));
    return this;
  }

  @Override
  public HttpServerFileUpload endHandler(Env env, Callback handler) {
    upload.endHandler(new Handler<Void>(env, handler));
    return this;
  }

}
