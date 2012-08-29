package com.maddox.rts;

public class NetPacket
{
  private byte[] buf;
  private int offset;
  private int length;
  private NetAddress address;
  private int port;
  public long time;

  protected NetPacket()
  {
  }

  public NetPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    setData(paramArrayOfByte, paramInt1, paramInt2);
    this.address = null;
    this.port = -1;
  }

  public NetPacket(byte[] paramArrayOfByte, int paramInt)
  {
    this(paramArrayOfByte, 0, paramInt);
  }

  public NetPacket(byte[] paramArrayOfByte, int paramInt1, int paramInt2, NetAddress paramNetAddress, int paramInt3)
  {
    setData(paramArrayOfByte, paramInt1, paramInt2);
    setAddress(paramNetAddress);
    setPort(paramInt3);
  }

  public NetPacket(byte[] paramArrayOfByte, int paramInt1, NetAddress paramNetAddress, int paramInt2)
  {
    this(paramArrayOfByte, 0, paramInt1, paramNetAddress, paramInt2);
  }

  public NetAddress getAddress()
  {
    return this.address;
  }

  public int getPort()
  {
    return this.port;
  }

  public byte[] getData()
  {
    return this.buf;
  }

  public int getOffset()
  {
    return this.offset;
  }

  public int getLength()
  {
    return this.length;
  }

  public void setData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt2 + paramInt1 > paramArrayOfByte.length)) {
      throw new IllegalArgumentException("illegal length or offset");
    }
    this.buf = paramArrayOfByte;
    this.length = paramInt2;
    this.offset = paramInt1;
  }

  public void setAddress(NetAddress paramNetAddress) {
    this.address = paramNetAddress;
  }

  public void setPort(int paramInt) {
    if ((paramInt < 0) || (paramInt > 65535)) {
      throw new IllegalArgumentException("Port out of range:" + paramInt);
    }
    this.port = paramInt;
  }

  public void setData(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException("null packet buffer");
    }
    this.buf = paramArrayOfByte;

    if (this.offset + this.length > paramArrayOfByte.length)
      setLength(paramArrayOfByte.length - this.offset);
  }

  public void setLength(int paramInt)
  {
    if ((this.offset + paramInt > this.buf.length) || (paramInt < 0)) {
      throw new IllegalArgumentException("illegal length");
    }
    this.length = paramInt;
  }
}