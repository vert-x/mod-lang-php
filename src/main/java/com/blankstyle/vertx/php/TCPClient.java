package com.blankstyle.vertx.php;

import org.vertx.java.core.SSLSupport;
import org.vertx.java.core.TCPSupport;

import com.caucho.quercus.env.BooleanValue;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.NumberValue;

/**
 * An abstract TCP server.
 */
public abstract class TCPClient<T extends TCPSupport<T> & SSLSupport<T>> {

  protected T client;

  public TCPClient(T client) {
    this.client = client;
  }

  /**
   * Returns the send buffer size.
   */
  public int getSendBufferSize(Env env) {
    return client.getSendBufferSize();
  }

  /**
   * Set the TCP send buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> setSendBufferSize(Env env, int size) {
    client.setSendBufferSize(size);
    return this;
  }

  /**
   * Returns the receive buffer size.
   */
  public int getReceiveBufferSize(Env env) {
    return client.getReceiveBufferSize();
  }

  /**
   * Set the TCP receive buffer size for connections created by this instance to size in bytes.
   */
  public TCPClient<T> setReceiveBufferSize(Env env, int size) {
    client.setReceiveBufferSize(size);
    return this;
  }

  /**
   * The value of TCP reuse address.
   */
  public boolean isReuseAddress(Env env) {
    return client.isReuseAddress();
  }

  /**
   * Set the TCP reuseAddress setting for connections created by this instance to reuse.
   */
  public TCPClient<T> setReuseAddress(Env env, boolean reuse) {
    client.setReuseAddress(reuse);
    return this;
  }

  /**
   * Returns the value of TCP so linger.
   */
  public int getSoLinger(Env env) {
    return client.getSoLinger();
  }

  /**
   * Set the TCP soLinger setting for connections created by this instance to linger.
   */
  public TCPClient<T> setSoLinger(Env env, int linger) {
    client.setReceiveBufferSize(linger);
    return this;
  }

  /**
   * Returns a boolean indicating whether TCP keep alive is enabled.
   */
  public boolean isTCPKeepAlive(Env env) {
    return client.isTCPKeepAlive();
  }

  /**
   * Set the TCP keepAlive setting for connections created by this instance to keepAlive.
   */
  public TCPClient<T> setTCPKeepAlive(Env env, BooleanValue keepAlive) {
    client.setTCPKeepAlive(keepAlive.toBoolean());
    return this;
  }

  /**
   * Returns a boolean indicating whether Nagle's algorithm is enabled.
   */
  public boolean isTCPNoDelay(Env env) {
    return client.isTCPNoDelay();
  }

  /**
   * If tcpNoDelay is set to true then Nagle's algorithm will turned off for the TCP connections created by this instance.
   */
  public TCPClient<T> setTCPNoDelay(Env env, BooleanValue tcpNoDelay) {
    client.setTCPNoDelay(tcpNoDelay.toBoolean());
    return this;
  }

  /**
   * Returns the value of TCP traffic class.
   */
  public int getTrafficClass(Env env) {
    return client.getTrafficClass();
  }

  /**
   * Set the TCP trafficClass setting for connections created by this instance to trafficClass.
   */
  public TCPClient<T> setTrafficClass(Env env, NumberValue trafficClass) {
    client.setReceiveBufferSize(trafficClass.toInt());
    return this;
  }

  /**
   * Returns a boolean indicating whether pooled buffers are used.
   */
  public boolean isUsePooledBuffers(Env env) {
    return client.isUsePooledBuffers();
  }

  /**
   * Set if vertx should use pooled buffers for performance reasons.
   */
  public TCPClient<T> setUsePooledBuffers(Env env, BooleanValue pooledBuffers) {
    client.setUsePooledBuffers(pooledBuffers.toBoolean());
    return this;
  }

}

