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

import io.vertx.lang.php.util.PhpTypes;

import org.vertx.java.core.ClientSSLSupport;
import org.vertx.java.core.TCPSupport;

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
public abstract class TCPClient<T extends TCPSupport<T> & ClientSSLSupport<T>> implements Gettable, Settable {

  protected T client;

  public TCPClient(T client) {
    this.client = client;
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
   * Indicates whether a certificate should trust all connections.
   */
  public BooleanValue trustAll(Env env) {
    return BooleanValue.create(client.isTrustAll());
  }

  /**
   * Sets the certificate trust all setting.
   */
  public TCPClient<T> trustAll(Env env, BooleanValue trustAll) {
    PhpTypes.assertNotNull(env, trustAll, "Value to %s::trustAll() must be a boolean.", this.toString());
    client.setTrustAll(trustAll.toBoolean());
    return this;
  }

  /**
   * Indicates whether this is an SSL connection.
   */
  public BooleanValue ssl(Env env) {
    return BooleanValue.create(client.isSSL());
  }

  /**
   * Sets the SSL status of the connection.
   */
  public TCPClient<T> ssl(Env env, BooleanValue ssl) {
    PhpTypes.assertNotNull(env, ssl, "Value to %s::ssl() must be a boolean.", this.toString());
    client.setSSL(ssl.toBoolean());
    return this;
  }

  /**
   * Returns the key store password.
   */
  public StringValue keyStorePassword(Env env) {
    return env.createString(client.getKeyStorePassword());
  }

  /**
   * Sets the key store password.
   */
  public TCPClient<T> keyStorePassword(Env env, StringValue pwd) {
    PhpTypes.assertNotNull(env, pwd, "Password to %s::keyStorePassword() must be a string.", this.toString());
    client.setKeyStorePassword(pwd.toString());
    return this;
  }

  /**
   * Returns the key store path.
   */
  public StringValue keyStorePath(Env env) {
    return env.createString(client.getKeyStorePath());
  }

  /**
   * Sets the key store path.
   */
  public TCPClient<T> keyStorePath(Env env, StringValue path) {
    PhpTypes.assertNotNull(env, path, "Path to %s::keyStorePath() must be a string.", this.toString());
    client.setKeyStorePath(path.toString());
    return this;
  }

  /**
   * Gets the trust store path option.
   */
  public StringValue trustStorePath(Env env) {
    return env.createString(client.getTrustStorePath());
  }

  /**
   * Sets the trust store path option.
   */
  public TCPClient<T> trustStorePath(Env env, StringValue pwd) {
    client.setTrustStorePath(pwd.toString());
    return this;
  }

  /**
   * Returns the trust store password.
   */
  public StringValue trustStorePassword(Env env) {
    return env.createString(client.getTrustStorePassword());
  }

  /**
   * Sets the trust store password.
   */
  public TCPClient<T> trustStorePassword(Env env, StringValue pwd) {
    PhpTypes.assertNotNull(env, pwd, "Password to %s::trustStorePassword() must be a string.", this.toString());
    client.setTrustStorePassword(pwd.toString());
    return this;
  }

  /**
   * Returns the send buffer size.
   */
  public Value sendBufferSize(Env env) {
    return env.wrapJava(client.getSendBufferSize());
  }

  /**
   * Set the TCP send buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> sendBufferSize(Env env, NumberValue size) {
    PhpTypes.assertNotNull(env, size, "Size to %s::sendBufferSize() must be a number.", this.toString());
    client.setSendBufferSize(size.toInt());
    return this;
  }

  /**
   * Returns the receive buffer size.
   */
  public Value receiveBufferSize(Env env) {
    return env.wrapJava(client.getReceiveBufferSize());
  }

  /**
   * Set the TCP receive buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> receiveBufferSize(Env env, NumberValue size) {
    PhpTypes.assertNotNull(env, size, "Size to %s::receiveBufferSize() must be a number.", this.toString());
    client.setReceiveBufferSize(size.toInt());
    return this;
  }

  /**
   * The value of TCP reuse address.
   */
  public BooleanValue reuseAddress(Env env) {
    return BooleanValue.create(client.isReuseAddress());
  }

  /**
   * Set the TCP reuseAddress setting for connections created by this instance to reuse.
   */
  public TCPClient<T> reuseAddress(Env env, BooleanValue reuse) {
    PhpTypes.assertNotNull(env, reuse, "Value to %s::reuseAddress() must be a boolean.", this.toString());
    client.setReuseAddress(reuse.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP so linger.
   */
  public Value soLinger(Env env) {
    return env.wrapJava(client.getSoLinger());
  }

  /**
   * Set the TCP soLinger setting for connections created by this instance to linger.
   */
  public TCPClient<T> soLinger(Env env, NumberValue linger) {
    PhpTypes.assertNotNull(env, linger, "Value to %s::soLinger() must be a number.", this.toString());
    client.setReceiveBufferSize(linger.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether TCP keep alive is enabled.
   */
  public BooleanValue keepAlive(Env env) {
    return BooleanValue.create(client.isTCPKeepAlive());
  }

  /**
   * Set the TCP keepAlive setting for connections created by this instance to keepAlive.
   */
  public TCPClient<T> keepAlive(Env env, BooleanValue keepAlive) {
    PhpTypes.assertNotNull(env, keepAlive, "Value to %s::keepAlive() must be a boolean.", this.toString());
    client.setTCPKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Returns a boolean indicating whether Nagle's algorithm is enabled.
   */
  public BooleanValue noDelay(Env env) {
    return BooleanValue.create(client.isTCPNoDelay());
  }

  /**
   * If tcpNoDelay is set to true then Nagle's algorithm will turned off for the TCP connections created by this instance.
   */
  public TCPClient<T> noDelay(Env env, BooleanValue tcpNoDelay) {
    PhpTypes.assertNotNull(env, tcpNoDelay, "Value to %s::noDelay() must be a boolean.", this.toString());
    client.setTCPNoDelay(tcpNoDelay.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP traffic class.
   */
  public Value trafficClass(Env env) {
    return env.wrapJava(client.getTrafficClass());
  }

  /**
   * Set the TCP trafficClass setting for connections created by this instance to trafficClass.
   */
  public TCPClient<T> trafficClass(Env env, NumberValue trafficClass) {
    PhpTypes.assertNotNull(env, trafficClass, "Value to %s::trafficClass() must be a number.", this.toString());
    client.setTrafficClass(trafficClass.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether pooled buffers are used.
   */
  public BooleanValue usePooledBuffers(Env env) {
    return BooleanValue.create(client.isUsePooledBuffers());
  }

  /**
   * Set if vertx should use pooled buffers for performance reasons.
   */
  public TCPClient<T> usePooledBuffers(Env env, BooleanValue pooledBuffers) {
    PhpTypes.assertNotNull(env, pooledBuffers, "Value to %s::usePooledBuffers() must be a boolean.", this.toString());
    client.setUsePooledBuffers(pooledBuffers.toBoolean());
    return this;
  }

  public String toString() {
    return "php:Vertx\\TCPClient";
  }

}

