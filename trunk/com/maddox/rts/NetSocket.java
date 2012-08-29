package com.maddox.rts;

import java.net.SocketException;
import java.util.List;

public abstract class NetSocket
{
  public int countChannels;
  public int maxChannels;
  protected double maxSpeed = 10.0D;

  protected boolean bOpen = false;

  public int getHeaderSize()
  {
    return 0;
  }
  public int getMaxDataSize() {
    return 512;
  }

  public double getMaxSpeed()
  {
    return this.maxSpeed;
  }

  public void setMaxSpeed(double paramDouble) {
    this.maxSpeed = paramDouble;
    NetEnv.cur(); List localList = NetEnv.channels();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      NetChannel localNetChannel = (NetChannel)localList.get(j);
      if ((!localNetChannel.isDestroying()) && (paramDouble < localNetChannel.getMaxSpeed()))
        localNetChannel.setMaxSpeed(paramDouble);
    }
  }

  public NetAddress getLocalAddress() {
    return null;
  }
  public int getLocalPort() {
    return -1;
  }
  public boolean isExclusive() { return false; } 
  public boolean isSupportedBlockOperation() {
    return true;
  }
  public void setSoTimeout(int paramInt) {  }

  public int getSoTimeout() { return 1;
  }

  public boolean receive(NetPacket paramNetPacket)
  {
    return false;
  }

  public void send(NetPacket paramNetPacket)
  {
  }

  public boolean isOpen()
  {
    return this.bOpen;
  }

  public void open()
    throws SocketException
  {
  }

  public void open(int paramInt)
    throws SocketException
  {
  }

  public void open(int paramInt, NetAddress paramNetAddress)
    throws SocketException
  {
  }

  public void close() throws SocketException
  {
  }

  public Class addressClass()
  {
    return null;
  }
}