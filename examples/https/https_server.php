<?php

$server = Vertx::createHttpServer()->ssl(TRUE)
  ->keyStorePath('server-keystore.jks')
  ->keyStorePassword('foo');

$server->requestHandler(function($request) {
  $request->response()->end('<html><body><h1>Hello from vert.x!</h1></body></html>');
})->listen(4443, 'localhost');
