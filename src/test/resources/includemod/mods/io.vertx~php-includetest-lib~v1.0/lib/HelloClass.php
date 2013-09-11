<?php

namespace lib;

class HelloClass {
    private $name = "Anonymous";

    public function __construct($name) {
        $this->name = $name;
    }

    public function sayHello() {
        return "Hello $this->name";
    }
}
