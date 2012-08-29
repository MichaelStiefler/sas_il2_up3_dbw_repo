// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNet.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgNetListener, NetChannel, NetMsgInput, 
//            MessageCache, Time

public class MsgNet extends com.maddox.rts.Message
{

    public MsgNet()
    {
        bDestroy = false;
    }

    public static void postGame(long l, java.lang.Object obj, com.maddox.rts.NetMsgInput netmsginput)
    {
        com.maddox.rts.MsgNet msgnet = (com.maddox.rts.MsgNet)cache.get();
        msgnet._sender = netmsginput;
        msgnet.post(0, obj, l);
    }

    public static void postReal(long l, java.lang.Object obj, com.maddox.rts.NetMsgInput netmsginput)
    {
        com.maddox.rts.MsgNet msgnet = (com.maddox.rts.MsgNet)cache.get();
        msgnet._sender = netmsginput;
        msgnet.post(64, obj, l);
    }

    public static void postRealNewChannel(java.lang.Object obj, com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.MsgNet msgnet = (com.maddox.rts.MsgNet)cache.get();
        msgnet._sender = netchannel;
        msgnet.bDestroy = false;
        msgnet.post(64, obj, com.maddox.rts.Time.currentReal());
    }

    public static void postRealDelChannel(java.lang.Object obj, com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.MsgNet msgnet = (com.maddox.rts.MsgNet)cache.get();
        msgnet._sender = netchannel;
        msgnet.bDestroy = true;
        msgnet.post(64, obj, com.maddox.rts.Time.currentReal());
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgNetListener)
        {
            if(_sender == null || (_sender instanceof com.maddox.rts.NetChannel))
            {
                if(bDestroy)
                    ((com.maddox.rts.MsgNetListener)obj).msgNetDelChannel((com.maddox.rts.NetChannel)_sender);
                else
                    ((com.maddox.rts.MsgNetListener)obj).msgNetNewChannel((com.maddox.rts.NetChannel)_sender);
            } else
            {
                ((com.maddox.rts.MsgNetListener)obj).msgNet((com.maddox.rts.NetMsgInput)_sender);
            }
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

    public boolean bDestroy;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgNet.class);
    }
}
