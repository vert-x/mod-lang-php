Vert.x PHP
==========

### For API documentation and tutorials see the [PHP User Manual](https://github.com/jordanhalterman/vertx-php/blob/master/docs/core_manual_php.md)

This project provides PHP language integration for [Vert.x](http://vertx.io/).
Since Vert.x runs on the JVM, this module is built on the Java-based
[Quercus](http://quercus.caucho.com/) PHP implementation, one which is
kept up to date and supports many useful new PHP features. The Vert.x PHP
API closely resembles that of the JavaScript module, using shorter method
names and closures for event handlers. The API is implemented entirely on
top of the Quercus Java API so as to allow more control over interaction
with the core Vert.x API.

## Installation
Please note that Vert.x PHP is still young and has not yet had an official
release. Large strides have been made towards stablizing the API, but a
few issues remain with various portions of the module. *This project is not
recommended for production until an official release has been made some
time in the comming weeks.*

1. Clone the repository with `git clone --branch master git@github.com:jordanhalterman/vertx-php.git`
1. CD into the Vert.x PHP directory with `cd vertx-php`
1. Build the module with `mvn install`
1. Update `langs.properties` in your `VERTX_HOME/conf` directory with:

```
php=io.vertx~lang-php~0.1.0-SNAPSHOT:io.vertx.lang.php.PhpVerticleFactory
.php=php
```

### Maven/Gradle project installation
Because both, Maven and Gradle are not using your global `langs.porperties` but the default one from the vert.x jar,
you have to add a lang.properties file to the projects class path, with the specific php settings.
If you've done step 4, you can do following:
```
cp VERTX_HOME/conf/langs.properties [YOUR PROJECT ROOT]/src/main/resources
```

## Documentation
See the [PHP User Manual](https://github.com/vert-x/mod-lang-php/blob/master/docs/core_manual_php.md)

## Examples
See [examples](https://github.com/vert-x/mod-lang-php/tree/master/examples)
