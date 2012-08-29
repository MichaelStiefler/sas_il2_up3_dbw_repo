// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetSocket.java

package com.maddox.rts;

import java.net.SocketException;
import java.util.List;

// Referenced classes of package com.maddox.rts:
//            NetChannel, NetEnv, NetAddress, NetPacket

public abstract class NetSocket
{

    public NetSocket()
    {
        maxSpeed = 10D;
        bOpen = false;
    }

    public int getHeaderSize()
    {
        return 0;
    }

    public int getMaxDataSize()
    {
        return 512;
    }

    public double getMaxSpeed()
    {
        return maxSpeed;
    }

    public void setMaxSpeed(double d)
    {
        maxSpeed = d;
        com.maddox.rts.NetEnv.cur();
        java.util.List list = com.maddox.rts.NetEnv.channels();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(j);
            if(!netchannel.isDestroying() && d < netchannel.getMaxSpeed())
                netchannel.setMaxSpeed(d);
        }

    }

    public com.maddox.rts.NetAddress getLocalAddress()
    {
        return null;
    }

    public int getLocalPort()
    {
        return -1;
    }

    public boolean isExclusive()
    {
        return false;
    }

    public boolean isSupportedBlockOperation()
    {
        return true;
    }

    public void setSoTimeout(int i)
    {
    }

    public int getSoTimeout()
    {
        return 1;
    }

    public boolean receive(com.maddox.rts.NetPacket netpacket)
    {
        return false;
    }

    public void send(com.maddox.rts.NetPacket netpacket)
    {
    }

    public boolean isOpen()
    {
        return bOpen;
    }

    public void open()
        throws java.net.SocketException
    {
    }

    public void open(int i)
        throws java.net.SocketException
    {
    }

    public void open(int i, com.maddox.rts.NetAddress netaddress)
        throws java.net.SocketException
    {
    }

    public void close()
        throws java.net.SocketException
    {
    }

    public java.lang.Class addressClass()
    {
        return null;
    }

    public int countChannels;
    public int maxChannels;
    protected double maxSpeed;
    protected boolean bOpen;
}
