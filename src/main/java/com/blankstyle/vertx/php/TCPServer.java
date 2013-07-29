package com.blankstyle.vertx.php;

import org.vertx.java.core.ServerSSLSupport;
import org.vertx.java.core.ServerTCPSupport;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;

/**
 * An abstract TCP server.
 */
public abstract class TCPServer<T extends ServerTCPSupport<T> & ServerSSLSupport<T>> {

  protected T server;

  public TCPServer(T server) {
    this.server = server;
  }

  /**
   * Returns the server accept backlog.
   */
  public int getAcceptBacklog(Env env) {
    return server.getAcceptBacklog();
  }

  /**
   * Sets the server accept backlog.
   */
  public TCPServer<T> setAcceptBacklog(Env env, int backlog) {
    server.setAcceptBacklog(backlog);
    return this;
  }

  /**
   * Indicates whether client authentication is required.
   */
  public boolean isClientAuthRequired(Env env) {
    return server.isClientAuthRequired();
  }

  /**
   * Sets client authentication requirements.
   */
  public TCPServer<T> setClientAuthRequired(Env env, BooleanValue required) {
    server.setClientAuthRequired(required.toBoolean());
    return this;
  }

  /**
   * Returns the send buffer size.
   */
  public int getSendBufferSize(Env env) {
    return server.getSendBufferSize();
  }

  /**
   * Set the TCP send buffer size for connections created by this instance to size in bytes.
   */
  public TCPServer<T> setSendBufferSize(Env env, int size) {
    server.setSendBufferSize(size);
    return this;
  }

  /**
   * Returns the receive buffer size.
   */
  public int getReceiveBufferSize(Env env) {
    return server.getReceiveBufferSize();
  }

  /**
   * Set the TCP receive buffer size for connections created by this instance to size in bytes.
   */
  public TCPServer<T> setReceiveBufferSize(Env env, int size) {
    server.setReceiveBufferSize(size);
    return this;
  }

  /**
   * The value of TCP reuse address.
   */
  public boolean isReuseAddress(Env env) {
    return server.isReuseAddress();
  }

  /**
   * Set the TCP reuseAddress setting for connections created by this instance to reuse.
   */
  public TCPServer<T> setReuseAddress(Env env, boolean reuse) {
    server.setReuseAddress(reuse);
    return this;
  }

  /**
   * Returns the value of TCP so linger.
   */
  public int getSoLinger(Env env) {
    return server.getSoLinger();
  }

  /**
   * Set the TCP soLinger setting for connections created by this instance to linger.
   */
  public TCPServer<T> setSoLinger(Env env, int linger) {
    server.setReceiveBufferSize(linger);
    return this;
  }

  /**
   * Returns a boolean indicating whether TCP keep alive is enabled.
   */
  public boolean isTCPKeepAlive(Env env) {
    return server.isTCPKeepAlive();
  }

  /**
   * Set the TCP keepAlive setting for connections created by this instance to keepAlive.
   */
  public TCPServer<T> setTCPKeepAlive(Env env, BooleanValue keepAlive) {
    server.setTCPKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Returns a boolean indicating whether Nagle's algorithm is enabled.
   */
  public boolean isTCPNoDelay(Env env) {
    return server.isTCPNoDelay();
  }

  /**
   * If tcpNoDelay is set to true then Nagle's algorithm will turned off for the TCP connections created by this instance.
   */
  public TCPServer<T> setTCPNoDelay(Env env, BooleanValue tcpNoDelay) {
    server.setTCPNoDelay(tcpNoDelay.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP traffic class.
   */
  public int getTrafficClass(Env env) {
    return server.getTrafficClass();
  }

  /**
   * Set the TCP trafficClass setting for connections created by this instance to trafficClass.
   */
  public TCPServer<T> setTrafficClass(Env env, NumberValue trafficClass) {
    server.setReceiveBufferSize(trafficClass.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether pooled buffers are used.
   */
  public boolean isUsePooledBuffers(Env env) {
    return server.isUsePooledBuffers();
  }

  /**
   * Set if vertx should use pooled buffers for performance reasons.
   */
  public TCPServer<T> setUsePooledBuffers(Env env, BooleanValue pooledBuffers) {
    server.setUsePooledBuffers(pooledBuffers.toBoolean());
    return this;
  }

}
