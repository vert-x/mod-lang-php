<?php

Vertx::createHttpServer()->requestHandler(function($request) {
  $request->response()->end('<html><body><h1>Hello from vert.x!</h1></body></html>');
})->listen(8080);
