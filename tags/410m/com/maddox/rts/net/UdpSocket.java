// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UdpSocket.java

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

// Referenced classes of package com.maddox.rts.net:
//            IPAddress, SocksUdpSocket

public class UdpSocket extends com.maddox.rts.NetSocket
{

    public UdpSocket()
    {
        lastSendTime = 0L;
        ioIn = new DatagramPacket(new byte[1], 0);
        ioOut = new DatagramPacket(new byte[1], 0);
    }

    public int getHeaderSize()
    {
        return 28;
    }

    public com.maddox.rts.NetAddress getLocalAddress()
    {
        return com.maddox.rts.net.IPAddress.fromIP(udp.getLocalAddress());
        java.lang.Exception exception;
        exception;
        return null;
    }

    public int getLocalPort()
    {
        return udp.getLocalPort();
    }

    public void setSoTimeout(int i)
    {
        try
        {
            udp.setSoTimeout(i);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    public int getSoTimeout()
    {
        int i = 1;
        try
        {
            i = udp.getSoTimeout();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return i;
    }

    public boolean receive(com.maddox.rts.NetPacket netpacket)
    {
        ioIn.setData(netpacket.getData(), 0, netpacket.getData().length);
        udp.receive(ioIn);
        netpacket.setData(ioIn.getData(), ioIn.getOffset(), ioIn.getLength());
        netpacket.setAddress(com.maddox.rts.net.IPAddress.fromIP(ioIn.getAddress()));
        netpacket.setPort(ioIn.getPort());
        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public void send(com.maddox.rts.NetPacket netpacket)
    {
        if(com.maddox.rts.NetEnv.bTestTransfer)
        {
            if((float)java.lang.Math.random() < com.maddox.rts.NetEnv.testDown)
                return;
            int i = 0;
            if(com.maddox.rts.NetEnv.testSpeed > 0.0F)
                i = (int)((float)netpacket.getLength() / (com.maddox.rts.NetEnv.testSpeed / 1000F));
            if(com.maddox.rts.NetEnv.testMaxLag > 0)
                i += com.maddox.rts.NetEnv.testMinLag + (int)(java.lang.Math.random() * (double)(com.maddox.rts.NetEnv.testMaxLag - com.maddox.rts.NetEnv.testMinLag));
            if(i > 0)
            {
                long l = com.maddox.rts.Time.currentReal() + (long)i;
                if(l < lastSendTime)
                    l = lastSendTime;
                lastSendTime = l;
                byte abyte0[] = new byte[netpacket.getLength()];
                java.lang.System.arraycopy(netpacket.getData(), netpacket.getOffset(), abyte0, 0, netpacket.getLength());
                com.maddox.rts.NetPacket netpacket1 = new NetPacket(abyte0, 0, abyte0.length, netpacket.getAddress(), netpacket.getPort());
                new com.maddox.rts.MsgAction(64, l, netpacket1) {

                    public void doAction(java.lang.Object obj)
                    {
                        _send((com.maddox.rts.NetPacket)obj);
                    }

                }
;
                return;
            }
        }
        _send(netpacket);
    }

    private void _send(com.maddox.rts.NetPacket netpacket)
    {
        try
        {
            ioOut.setData(netpacket.getData(), netpacket.getOffset(), netpacket.getLength());
            ioOut.setAddress(((com.maddox.rts.net.IPAddress)netpacket.getAddress()).ip());
            ioOut.setPort(netpacket.getPort());
            udp.send(ioOut);
        }
        catch(java.lang.Exception exception) { }
    }

    public void open()
        throws java.net.SocketException
    {
        close();
        if(com.maddox.rts.net.SocksUdpSocket.isProxyEnable())
            udp = new SocksUdpSocket();
        else
            udp = new DatagramSocket();
        setParams();
        bOpen = true;
    }

    public void open(int i)
        throws java.net.SocketException
    {
        close();
        if(com.maddox.rts.net.SocksUdpSocket.isProxyEnable())
            udp = new SocksUdpSocket(i);
        else
            udp = new DatagramSocket(i);
        setParams();
        bOpen = true;
    }

    public void open(int i, com.maddox.rts.NetAddress netaddress)
        throws java.net.SocketException
    {
        close();
        if(com.maddox.rts.net.SocksUdpSocket.isProxyEnable())
            udp = new SocksUdpSocket(i, ((com.maddox.rts.net.IPAddress)netaddress).ip());
        else
            udp = new DatagramSocket(i, ((com.maddox.rts.net.IPAddress)netaddress).ip());
        setParams();
        bOpen = true;
    }

    private void setParams()
        throws java.net.SocketException
    {
        udp.setSoTimeout(1);
        udp.setSendBufferSize(getMaxDataSize() * 2);
        udp.setReceiveBufferSize(getMaxDataSize() * 2);
    }

    public void close()
        throws java.net.SocketException
    {
        if(isOpen())
        {
            udp.close();
            bOpen = false;
        }
    }

    public java.lang.Class addressClass()
    {
        return com.maddox.rts.net.IPAddress.class;
    }

    private java.net.DatagramSocket udp;
    private long lastSendTime;
    private java.net.DatagramPacket ioIn;
    private java.net.DatagramPacket ioOut;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.net.UdpSocket.class, "protocolName", "udp");
        com.maddox.rts.Property.set(com.maddox.rts.net.UdpSocket.class, "maxChannels", 128);
        com.maddox.rts.Property.set(com.maddox.rts.net.UdpSocket.class, "maxSpeed", 10D);
    }

}
