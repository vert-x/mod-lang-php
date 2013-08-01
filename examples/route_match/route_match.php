<?php

$routeMatcher = new Vertx\Http\RouteMatcher();

$routeMatcher->get('/details/:user/:id', function($request) {
  $request->response()->end('User: '. $request->params()->get('user') .' ID: '. $request->params()->get('id'));
});

$routeMatcher->getWithRegEx('.*', function($request) {
  $request->response()->sendFile('route_match/index.html');
});

Vertx::createHttpServer()->requestHandler($routeMatcher)->listen(8080);
