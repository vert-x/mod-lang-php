<?php

$server = Vertx::createHttpServer();
$server->ssl = TRUE;
$server->keyStorePath = 'server-keystore.jks';
$server->keyStorePassword = 'foo';

$server->requestHandler(function($request) {
  $request->response->end('<html><body><h1>Hello from vert.x!</h1></body></html>');
})->listen(4443, 'localhost');
