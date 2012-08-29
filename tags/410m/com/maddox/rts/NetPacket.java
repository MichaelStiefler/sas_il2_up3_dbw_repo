// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetPacket.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetAddress

public class NetPacket
{

    protected NetPacket()
    {
    }

    public NetPacket(byte abyte0[], int i, int j)
    {
        setData(abyte0, i, j);
        address = null;
        port = -1;
    }

    public NetPacket(byte abyte0[], int i)
    {
        this(abyte0, 0, i);
    }

    public NetPacket(byte abyte0[], int i, int j, com.maddox.rts.NetAddress netaddress, int k)
    {
        setData(abyte0, i, j);
        setAddress(netaddress);
        setPort(k);
    }

    public NetPacket(byte abyte0[], int i, com.maddox.rts.NetAddress netaddress, int j)
    {
        this(abyte0, 0, i, netaddress, j);
    }

    public com.maddox.rts.NetAddress getAddress()
    {
        return address;
    }

    public int getPort()
    {
        return port;
    }

    public byte[] getData()
    {
        return buf;
    }

    public int getOffset()
    {
        return offset;
    }

    public int getLength()
    {
        return length;
    }

    public void setData(byte abyte0[], int i, int j)
    {
        if(j < 0 || i < 0 || j + i > abyte0.length)
        {
            throw new IllegalArgumentException("illegal length or offset");
        } else
        {
            buf = abyte0;
            length = j;
            offset = i;
            return;
        }
    }

    public void setAddress(com.maddox.rts.NetAddress netaddress)
    {
        address = netaddress;
    }

    public void setPort(int i)
    {
        if(i < 0 || i > 65535)
        {
            throw new IllegalArgumentException("Port out of range:" + i);
        } else
        {
            port = i;
            return;
        }
    }

    public void setData(byte abyte0[])
    {
        if(abyte0 == null)
            throw new NullPointerException("null packet buffer");
        buf = abyte0;
        if(offset + length > abyte0.length)
            setLength(abyte0.length - offset);
    }

    public void setLength(int i)
    {
        if(offset + i > buf.length || i < 0)
        {
            throw new IllegalArgumentException("illegal length");
        } else
        {
            length = i;
            return;
        }
    }

    private byte buf[];
    private int offset;
    private int length;
    private com.maddox.rts.NetAddress address;
    private int port;
    public long time;
}
