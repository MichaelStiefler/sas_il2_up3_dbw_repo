package com.maddox.rts.net;

import com.maddox.rts.NetAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class IPAddress extends NetAddress
{
  private InetAddress ip;
  private static HashMap ipHash = new HashMap();

  public InetAddress ip()
  {
    return this.ip;
  }

  public static IPAddress fromIP(InetAddress paramInetAddress) throws UnknownHostException {
    IPAddress localIPAddress = (IPAddress)ipHash.get(paramInetAddress);
    if (localIPAddress == null) {
      localIPAddress = new IPAddress();
      localIPAddress.create(paramInetAddress.getHostAddress());
    }
    return localIPAddress;
  }

  public boolean equals(Object paramObject) {
    if ((paramObject instanceof IPAddress)) return this.ip.equals(((IPAddress)paramObject).ip);
    return false;
  }

  public String getHostName() {
    return this.ip.getHostName();
  }
  public byte[] getAddress() {
    return this.ip.getAddress();
  }
  public String getHostAddress() {
    return this.ip.getHostAddress();
  }

  public String toString() {
    return getHostAddress();
  }

  public void create(String paramString)
    throws UnknownHostException
  {
    this.ip = InetAddress.getByName(paramString);
    ipHash.put(this.ip, this);
  }

  public NetAddress getLocalHost()
    throws UnknownHostException
  {
    InetAddress localInetAddress = InetAddress.getLocalHost();
    return fromIP(localInetAddress);
  }

  public NetAddress getByName(String paramString)
    throws UnknownHostException
  {
    InetAddress localInetAddress = InetAddress.getByName(paramString);
    return fromIP(localInetAddress);
  }

  public NetAddress[] getAllByName(String paramString)
    throws UnknownHostException
  {
    InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(paramString);
    int i = arrayOfInetAddress.length;
    if (i == 0) return null;
    NetAddress[] arrayOfNetAddress = new NetAddress[i];
    for (int j = 0; j < i; j++) {
      arrayOfNetAddress[j] = fromIP(arrayOfInetAddress[j]);
    }
    return arrayOfNetAddress;
  }
}