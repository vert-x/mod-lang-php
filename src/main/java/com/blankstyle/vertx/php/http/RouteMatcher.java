/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the MIT License (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blankstyle.vertx.php.http;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.ResultModifier;
import com.blankstyle.vertx.php.util.PhpTypes;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * An HTTP route matcher implementation.
 * 
 * @author Jordan Halterman
 */
public class RouteMatcher {

  private org.vertx.java.core.http.RouteMatcher matcher;

  public RouteMatcher(Env env) {
    this.matcher = new org.vertx.java.core.http.RouteMatcher();
  }

  /**
   * Specify a handler that will be called for all HTTP methods.
   */
  public RouteMatcher all(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::all() must be callable.");
    matcher.all(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for all HTTP methods.
   */
  public RouteMatcher allWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::allWithRegex() must be callable.");
    matcher.allWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP CONNECT.
   */
  public RouteMatcher connect(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::connect() must be callable.");
    matcher.connect(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP CONNECT.
   */
  public RouteMatcher connectWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes
        .assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::connectWithRegex() must be callable.");
    matcher.connectWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP GET.
   */
  public RouteMatcher get(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::get() must be callable.");
    matcher.get(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP GET.
   */
  public RouteMatcher getWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::getWithRegex() must be callable.");
    matcher.getWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP POST.
   */
  public RouteMatcher post(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::post() must be callable.");
    matcher.post(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP POST.
   */
  public RouteMatcher postWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::postWithRegex() must be callable.");
    matcher.postWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP PUT.
   */
  public RouteMatcher put(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::put() must be callable.");
    matcher.put(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP PUT.
   */
  public RouteMatcher putWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::putWithRegex() must be callable.");
    matcher.putWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP DELETE.
   */
  public RouteMatcher delete(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::delete() must be callable.");
    matcher.delete(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP DELETE.
   */
  public RouteMatcher deleteWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::deleteWithRegex() must be callable.");
    matcher.deleteWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP HEAD.
   */
  public RouteMatcher head(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::head() must be callable.");
    matcher.head(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP HEAD.
   */
  public RouteMatcher headWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::headWithRegex() must be callable.");
    matcher.headWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP TRACE.
   */
  public RouteMatcher trace(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::trace() must be callable.");
    matcher.trace(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP TRACE.
   */
  public RouteMatcher traceWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::traceWithRegex() must be callable.");
    matcher.traceWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP OPTIONS.
   */
  public RouteMatcher options(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::options() must be callable.");
    matcher.options(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP OPTIONS.
   */
  public RouteMatcher optionsWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes
        .assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::optionsWithRegex() must be callable.");
    matcher.optionsWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP PATCH.
   */
  public RouteMatcher patch(Env env, StringValue pattern, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::patch() must be callable.");
    matcher.patch(pattern.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called for a matching HTTP PATCH.
   */
  public RouteMatcher patchWithRegex(Env env, StringValue regex, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::patchWithRegex() must be callable.");
    matcher.patchWithRegEx(regex.toString(),
        new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
            new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
              @Override
              public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
                return new HttpServerRequest(request);
              }
            }));
    return this;
  }

  /**
   * Specify a handler that will be called when no other handlers match.
   */
  public RouteMatcher noMatch(Env env, Value handler) {
    PhpTypes.assertCallable(env, handler, "Argument to Vertx\\Http\\RouteMatcher::noMatch() must be callable.");
    matcher.noMatch(new Handler<org.vertx.java.core.http.HttpServerRequest>(env, PhpTypes.toCallable(handler),
        new ResultModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
          @Override
          public HttpServerRequest modify(org.vertx.java.core.http.HttpServerRequest request) {
            return new HttpServerRequest(request);
          }
        }));
    return this;
  }

  /**
   * Something has happened, so handle it.
   */
  public void handle(Env env, org.vertx.java.core.http.HttpServerRequest request) {
    matcher.handle(request);
  }

  public String toString() {
    return "php:Vertx\\Http\\RouteMatcher";
  }

}
