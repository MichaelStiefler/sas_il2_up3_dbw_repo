package com.maddox.rts.net;

import com.maddox.rts.MsgAction;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetPacket;
import com.maddox.rts.NetSocket;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpSocket extends NetSocket
{
  private DatagramSocket udp;
  private long lastSendTime = 0L;

  private DatagramPacket ioIn = new DatagramPacket(new byte[1], 0);
  private DatagramPacket ioOut = new DatagramPacket(new byte[1], 0);

  public int getHeaderSize()
  {
    return 28;
  }

  public NetAddress getLocalAddress()
  {
    try {
      return IPAddress.fromIP(this.udp.getLocalAddress()); } catch (Exception localException) {
    }
    return null;
  }

  public int getLocalPort()
  {
    return this.udp.getLocalPort();
  }

  public void setSoTimeout(int paramInt) {
    try {
      this.udp.setSoTimeout(paramInt);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public int getSoTimeout() {
    int i = 1;
    try {
      i = this.udp.getSoTimeout();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    return i;
  }

  public boolean receive(NetPacket paramNetPacket)
  {
    try
    {
      this.ioIn.setData(paramNetPacket.getData(), 0, paramNetPacket.getData().length);
      this.udp.receive(this.ioIn);
      paramNetPacket.setData(this.ioIn.getData(), this.ioIn.getOffset(), this.ioIn.getLength());
      paramNetPacket.setAddress(IPAddress.fromIP(this.ioIn.getAddress()));
      paramNetPacket.setPort(this.ioIn.getPort());
      return true;
    }
    catch (Exception localException) {
    }
    return false;
  }

  public void send(NetPacket paramNetPacket)
  {
    if (NetEnv.bTestTransfer) {
      if ((float)Math.random() < NetEnv.testDown)
        return;
      int i = 0;
      if (NetEnv.testSpeed > 0.0F)
        i = (int)(paramNetPacket.getLength() / (NetEnv.testSpeed / 1000.0F));
      if (NetEnv.testMaxLag > 0)
        i += NetEnv.testMinLag + (int)(Math.random() * (NetEnv.testMaxLag - NetEnv.testMinLag));
      if (i > 0) {
        long l = Time.currentReal() + i;
        if (l < this.lastSendTime) l = this.lastSendTime;
        this.lastSendTime = l;
        byte[] arrayOfByte = new byte[paramNetPacket.getLength()];
        System.arraycopy(paramNetPacket.getData(), paramNetPacket.getOffset(), arrayOfByte, 0, paramNetPacket.getLength());
        NetPacket localNetPacket = new NetPacket(arrayOfByte, 0, arrayOfByte.length, paramNetPacket.getAddress(), paramNetPacket.getPort());
        new MsgAction(64, l, localNetPacket) {
          public void doAction(Object paramObject) {
            UdpSocket.this._send((NetPacket)paramObject);
          }
        };
        return;
      }

    }

    _send(paramNetPacket);
  }

  private void _send(NetPacket paramNetPacket)
  {
    try {
      this.ioOut.setData(paramNetPacket.getData(), paramNetPacket.getOffset(), paramNetPacket.getLength());
      this.ioOut.setAddress(((IPAddress)paramNetPacket.getAddress()).ip());
      this.ioOut.setPort(paramNetPacket.getPort());
      this.udp.send(this.ioOut);
    }
    catch (Exception localException)
    {
    }
  }

  public void open()
    throws SocketException
  {
    close();
    if (SocksUdpSocket.isProxyEnable())
      this.udp = new SocksUdpSocket();
    else
      this.udp = new DatagramSocket();
    setParams();
    this.jdField_bOpen_of_type_Boolean = true;
  }

  public void open(int paramInt)
    throws SocketException
  {
    close();
    if (SocksUdpSocket.isProxyEnable())
      this.udp = new SocksUdpSocket(paramInt);
    else
      this.udp = new DatagramSocket(paramInt);
    setParams();
    this.jdField_bOpen_of_type_Boolean = true;
  }

  public void open(int paramInt, NetAddress paramNetAddress)
    throws SocketException
  {
    close();
    if (SocksUdpSocket.isProxyEnable())
      this.udp = new SocksUdpSocket(paramInt, ((IPAddress)paramNetAddress).ip());
    else
      this.udp = new DatagramSocket(paramInt, ((IPAddress)paramNetAddress).ip());
    setParams();
    this.jdField_bOpen_of_type_Boolean = true;
  }

  private void setParams() throws SocketException {
    this.udp.setSoTimeout(1);
    this.udp.setSendBufferSize(getMaxDataSize() * 2);
    this.udp.setReceiveBufferSize(getMaxDataSize() * 2);
  }

  public void close() throws SocketException
  {
    if (isOpen()) {
      this.udp.close();
      this.jdField_bOpen_of_type_Boolean = false;
    }
  }

  public Class addressClass() {
    return IPAddress.class;
  }
  static {
    Property.set(UdpSocket.class, "protocolName", "udp");
    Property.set(UdpSocket.class, "maxChannels", 128);
    Property.set(UdpSocket.class, "maxSpeed", 10.0D);
  }
}