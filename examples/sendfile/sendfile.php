<?php

Vertx::createHttpServer()->requestHandler(function($request) {
  $request->response()->sendFile('sendfile/index.html');
})->listen(8080);
