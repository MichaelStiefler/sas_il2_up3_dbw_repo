// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetEnv.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            NetPacket, NetSocket, NetAddress

class NetExtPacket extends com.maddox.rts.NetPacket
{

    public NetExtPacket(byte abyte0[], int i, int j, com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int k)
    {
        buf = new byte[j];
        java.lang.System.arraycopy(abyte0, i, buf, 0, j);
        socket = netsocket;
        setAddress(netaddress);
        setPort(k);
    }

    public byte[] getBuf()
    {
        return buf;
    }

    public com.maddox.rts.NetSocket getSocket()
    {
        return socket;
    }

    public void clear()
    {
        buf = null;
        socket = null;
        setAddress(null);
    }

    byte buf[];
    com.maddox.rts.NetSocket socket;
}
