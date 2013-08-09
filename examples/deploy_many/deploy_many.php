<?php

function do_deploy($count = 0) {
  Vertx::deployVerticle('child.php', function($deployId) {
    Vertx::logger()->info('Deployed '. $count);
    do_undeploy($deployId, $count);
  });
}

function do_undeploy($deployId, $count) {
  Vertx::undeployVerticle($deployId, function() {
    $count++;
    if ($count < 10) {
      do_deploy($count);
    }
    else {
      Vertx::logger()->info('Done!');
    }
  });
}

do_deploy();
