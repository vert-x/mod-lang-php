<?php

namespace lib;

class ExternalHelloClass {
    private $name = "Anonymous";

    public function __construct($name) {
        $this->name = $name;
    }

    public function sayHello() {
        return "Hello $this->name from an external Class";
    }
}
