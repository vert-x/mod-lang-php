<?php

Vertx::createHttpServer()->requestHandler(function($request) {
  $request->response->putHeader('Content-Type', 'text/plain');
  $request->response->end('Hello world');
})->listen(8080);
