package com.maddox.rts;

import java.net.UnknownHostException;

public abstract class NetAddress
{
  public String getHostName()
  {
    return null;
  }
  public byte[] getAddress() {
    return null;
  }
  public String getHostAddress() {
    return null;
  }

  public String toString() {
    return getHostAddress();
  }

  public void create(String paramString)
    throws UnknownHostException
  {
  }

  public NetAddress getLocalHost()
    throws UnknownHostException
  {
    return null;
  }

  public NetAddress getByName(String paramString)
    throws UnknownHostException
  {
    return null;
  }

  public NetAddress[] getAllByName(String paramString)
    throws UnknownHostException
  {
    return null;
  }
}