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
package io.vertx.lang.php;

import org.vertx.java.core.ServerSSLSupport;
import org.vertx.java.core.ServerTCPSupport;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;
import com.caucho.quercus.env.StringValue;
import com.caucho.quercus.env.Value;

/**
 * An abstract TCP server.
 *
 * @author Jordan Halterman
 */
public abstract class TCPServer<T extends ServerTCPSupport<T> & ServerSSLSupport<T>> implements Gettable, Settable {

  protected T server;

  public TCPServer(T server) {
    this.server = server;
  }

  /**
   * Gets a field value.
   */
  public Value __getField(Env env, StringValue name) {
    return env.wrapJava(this).callMethod(env, name);
  }

  /**
   * Sets a field value.
   */
  public void __setField(Env env, StringValue name, Value value) {
    env.wrapJava(this).callMethod(env, name, value);
  }

  /**
   * Gets the accept backlog option.
   */
  public Value acceptBacklog(Env env) {
    return env.wrapJava(server.getAcceptBacklog());
  }

  /**
   * Sets the accept backlog option.
   */
  public TCPServer<T> acceptBacklog(Env env, NumberValue backlog) {
    server.setAcceptBacklog(backlog.toInt());
    return this;
  }

  /**
   * Gets the client authorization required option.
   */
  public BooleanValue clientAuthRequired(Env env) {
    return BooleanValue.create(server.isClientAuthRequired());
  }

  /**
   * Sets the client authorization required option.
   */
  public TCPServer<?> clientAuthRequired(Env env, BooleanValue required) {
    server.setClientAuthRequired(required.toBoolean());
    return this;
  }

  /**
   * Gets the SSL option.
   */
  public BooleanValue ssl(Env env) {
    return BooleanValue.create(server.isSSL());
  }

  /**
   * Sets the SSL option.
   */
  public TCPServer<?> ssl(Env env, BooleanValue ssl) {
    server.setSSL(ssl.toBoolean());
    return this;
  }

  /**
   * Gets the key store password option.
   */
  public StringValue keyStorePassword(Env env) {
    return env.createString(server.getKeyStorePassword());
  }

  /**
   * Sets the key store password option.
   */
  public TCPServer<T> keyStorePassword(Env env, StringValue pwd) {
    server.setKeyStorePassword(pwd.toString());
    return this;
  }

  /**
   * Gets the key store path option.
   */
  public StringValue keyStorePath(Env env) {
    return env.createString(server.getKeyStorePath());
  }

  /**
   * Sets the key store path option.
   */
  public TCPServer<T> keyStorePath(Env env, StringValue pwd) {
    server.setKeyStorePath(pwd.toString());
    return this;
  }

  /**
   * Gets the trust store password option.
   */
  public StringValue trustStorePassword(Env env) {
    return env.createString(server.getTrustStorePassword());
  }

  /**
   * Sets the trust store password option.
   */
  public TCPServer<T> trustStorePassword(Env env, StringValue pwd) {
    server.setTrustStorePassword(pwd.toString());
    return this;
  }

  /**
   * Gets the trust store path option.
   */
  public StringValue trustStorePath(Env env) {
    return env.createString(server.getTrustStorePath());
  }

  /**
   * Sets the trust store path option.
   */
  public TCPServer<T> trustStorePath(Env env, StringValue pwd) {
    server.setTrustStorePath(pwd.toString());
    return this;
  }

  /**
   * Gets the send buffer size option.
   */
  public Value sendBufferSize(Env env) {
    return env.wrapJava(server.getSendBufferSize());
  }

  /**
   * Sets the send buffer size option.
   */
  public TCPServer<T> sendBufferSize(Env env, NumberValue size) {
    server.setSendBufferSize(size.toInt());
    return this;
  }

  /**
   * Gets the receive buffer size option.
   */
  public Value receiveBufferSize(Env env) {
    return env.wrapJava(server.getReceiveBufferSize());
  }

  /**
   * Sets the receive buffer size option.
   */
  public TCPServer<T> receiveBufferSize(Env env, NumberValue size) {
    server.setReceiveBufferSize(size.toInt());
    return this;
  }

  /**
   * Gets the reuse address option.
   */
  public BooleanValue reuseAddress(Env env) {
    return BooleanValue.create(server.isReuseAddress());
  }

  /**
   * Sets the reuse address option.
   */
  public TCPServer<?> reuseAddress(Env env, BooleanValue reuseAddress) {
    server.setReuseAddress(reuseAddress.toBoolean());
    return this;
  }

  /**
   * Gets the so linger option.
   */
  public Value soLinger(Env env) {
    return env.wrapJava(server.getSoLinger());
  }

  /**
   * Sets the so linger option.
   */
  public TCPServer<T> soLinger(Env env, NumberValue soLinger) {
    server.setSoLinger(soLinger.toInt());
    return this;
  }

  /**
   * Gets the keep alive option.
   */
  public BooleanValue keepAlive(Env env) {
    return BooleanValue.create(server.isTCPKeepAlive());
  }

  /**
   * Sets the keep alive option.
   */
  public TCPServer<?> keepAlive(Env env, BooleanValue keepAlive) {
    server.setTCPKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Gets the no delay option.
   */
  public BooleanValue noDelay(Env env) {
    return BooleanValue.create(server.isTCPNoDelay());
  }

  /**
   * Sets the no delay option.
   */
  public TCPServer<?> noDelay(Env env, BooleanValue noDelay) {
    server.setTCPNoDelay(noDelay.toBoolean());
    return this;
  }

  /**
   * Gets the traffic class option.
   */
  public Value trafficClass(Env env) {
    return env.wrapJava(server.getTrafficClass());
  }

  /**
   * Sets the traffic class option.
   */
  public TCPServer<T> trafficClass(Env env, NumberValue trafficClass) {
    server.setTrafficClass(trafficClass.toInt());
    return this;
  }

  /**
   * Gets the use pooled buffers option.
   */
  public BooleanValue usePooledBuffers(Env env) {
    return BooleanValue.create(server.isUsePooledBuffers());
  }

  /**
   * Sets the use pooled buffers option.
   */
  public TCPServer<?> usePooledBuffers(Env env, BooleanValue usePooledBuffers) {
    server.setUsePooledBuffers(usePooledBuffers.toBoolean());
    return this;
  }

  public String toString() {
    return "php:Vertx\\TCPServer";
  }

}
