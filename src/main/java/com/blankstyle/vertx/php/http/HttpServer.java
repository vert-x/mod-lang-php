package com.blankstyle.vertx.php.http;

public class HttpServer {

  private org.vertx.java.core.http.HttpServer server;

  public HttpServer(org.vertx.java.core.http.HttpServer server) {
    this.server = server;
  }

}
