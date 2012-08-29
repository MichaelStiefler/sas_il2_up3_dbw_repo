// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNetExt.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgNetExtListener, MessageCache, NetSocket, 
//            NetAddress

public class MsgNetExt extends com.maddox.rts.Message
{

    public MsgNetExt()
    {
    }

    public static void postReal(long l, java.lang.Object obj, byte abyte0[], com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        com.maddox.rts.MsgNetExt msgnetext = (com.maddox.rts.MsgNetExt)cache.get();
        msgnetext.buf = abyte0;
        msgnetext.socket = netsocket;
        msgnetext.address = netaddress;
        msgnetext.port = i;
        msgnetext.post(64, obj, l);
    }

    public void clean()
    {
        super.clean();
        buf = null;
        socket = null;
        address = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgNetExtListener)
        {
            ((com.maddox.rts.MsgNetExtListener)obj).msgNetExt(buf, socket, address, port);
            return true;
        } else
        {
            return false;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected byte buf[];
    protected com.maddox.rts.NetSocket socket;
    protected com.maddox.rts.NetAddress address;
    protected int port;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgNetExt.class);
    }
}
