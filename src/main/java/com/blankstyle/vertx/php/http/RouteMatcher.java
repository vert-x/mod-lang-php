package com.blankstyle.vertx.php.http;

import com.blankstyle.vertx.php.Handler;
import com.blankstyle.vertx.php.ArgumentModifier;
import com.caucho.quercus.env.Callback;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringValue;

/**
 * An HTTP route matcher implementation.
 */
public class RouteMatcher {

  private org.vertx.java.core.http.RouteMatcher matcher;

  public RouteMatcher(Env env) {
    this.matcher = new org.vertx.java.core.http.RouteMatcher();
  }

  /**
   * Specify a handler that will be called for all HTTP methods.
   */
  public RouteMatcher all(final Env env, final StringValue pattern, final Callback callback) {
    matcher.all(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher allWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.allWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher connect(final Env env, final StringValue pattern, final Callback callback) {
    matcher.connect(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher connectWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.connectWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher get(final Env env, final StringValue pattern, final Callback callback) {
    matcher.get(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher getWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.getWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher post(final Env env, final StringValue pattern, final Callback callback) {
    matcher.post(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher postWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.postWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher put(final Env env, final StringValue pattern, final Callback callback) {
    matcher.put(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher putWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.putWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher delete(final Env env, final StringValue pattern, final Callback callback) {
    matcher.delete(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher deleteWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.deleteWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher head(final Env env, final StringValue pattern, final Callback callback) {
    matcher.head(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher headWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.headWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher trace(final Env env, final StringValue pattern, final Callback callback) {
    matcher.trace(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher traceWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.traceWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher options(final Env env, final StringValue pattern, final Callback callback) {
    matcher.options(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher optionsWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.optionsWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher patch(final Env env, final StringValue pattern, final Callback callback) {
    matcher.patch(pattern.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher patchWithRegex(final Env env, final StringValue regex, final Callback callback) {
    matcher.patchWithRegEx(regex.toString(), new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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
  public RouteMatcher noMatch(final Env env, final Callback callback) {
    matcher.noMatch(new Handler<org.vertx.java.core.http.HttpServerRequest>(env, callback, new ArgumentModifier<org.vertx.java.core.http.HttpServerRequest, HttpServerRequest>() {
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

}
