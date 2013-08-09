<?php

$routeMatcher = new Vertx\Http\RouteMatcher();

$routeMatcher->get('/details/:user/:id', function($request) {
  $request->response->end('User: '. $request->params['user'] .' ID: '. $request->params['id']);
});

$routeMatcher->getWithRegEx('.*', function($request) {
  $request->response->sendFile('route_match/index.html');
});

Vertx::createHttpServer()->routeHandler($routeMatcher)->listen(8080);
