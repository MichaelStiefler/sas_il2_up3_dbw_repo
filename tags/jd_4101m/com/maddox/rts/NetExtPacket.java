package com.maddox.rts;

class NetExtPacket extends NetPacket
{
  byte[] buf;
  NetSocket socket;

  public NetExtPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt3)
  {
    this.buf = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, this.buf, 0, paramInt2);
    this.socket = paramNetSocket;
    setAddress(paramNetAddress);
    setPort(paramInt3);
  }
  public byte[] getBuf() {
    return this.buf; } 
  public NetSocket getSocket() { return this.socket; } 
  public void clear() {
    this.buf = null;
    this.socket = null;
    setAddress(null);
  }
}