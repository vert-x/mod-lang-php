/*
 * Copyright 2014 the original author or authors.
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
package io.vertx.lang.php.sockjs;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Callable;
import com.caucho.quercus.env.NullValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * A PHP compatible event bus bridge hook.
 *
 * @author Jordan Halterman
 */
public interface EventBusBridgeHook {

  /**
   * The socket has been created.
   */
  BooleanValue handleSocketCreated(SockJSSocket socket);

  /**
   * The socket has been closed.
   */
  NullValue handleSocketClosed(SockJSSocket socket);

  /**
   * Client is sending or publishing on the socket.
   */
  BooleanValue handleSendOrPub(SockJSSocket socket, BooleanValue send, Value msg, StringValue address);

  /**
   * Called before a client registers a handler.
   */
  BooleanValue handlePreRegister(SockJSSocket socket, StringValue address);

  /**
   * Called after a client registers a handler.
   */
  NullValue handlePostRegister(SockJSSocket socket, StringValue address);

  /**
   * Called when a client unregisters a handler.
   */
  BooleanValue handleUnregister(SockJSSocket socket, StringValue address);

  /**
   * Called before authorisation.
   */
  BooleanValue handleAuthorise(Value msg, StringValue sessionID, Callable handler);

}
