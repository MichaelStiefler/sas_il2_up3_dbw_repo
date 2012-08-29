// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgNetAskNak.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgNetAskNakListener, NetChannel, MessageCache, 
//            NetMsgGuaranted

public class MsgNetAskNak extends com.maddox.rts.Message
{

    public MsgNetAskNak()
    {
    }

    public static void postReal(long l, java.lang.Object obj, boolean flag, com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.MsgNetAskNak msgnetasknak = (com.maddox.rts.MsgNetAskNak)cache.get();
        msgnetasknak.bAsk = flag;
        msgnetasknak.guaranted = netmsgguaranted;
        msgnetasknak._sender = netchannel;
        msgnetasknak.post(64, obj, l);
    }

    public static void postGame(long l, java.lang.Object obj, boolean flag, com.maddox.rts.NetMsgGuaranted netmsgguaranted, com.maddox.rts.NetChannel netchannel)
    {
        com.maddox.rts.MsgNetAskNak msgnetasknak = (com.maddox.rts.MsgNetAskNak)cache.get();
        msgnetasknak.bAsk = flag;
        msgnetasknak.guaranted = netmsgguaranted;
        msgnetasknak._sender = netchannel;
        msgnetasknak.post(0, obj, l);
    }

    public void clean()
    {
        super.clean();
        guaranted = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgNetAskNakListener)
        {
            if(bAsk)
                ((com.maddox.rts.MsgNetAskNakListener)obj).msgNetAsk(guaranted, (com.maddox.rts.NetChannel)_sender);
            else
                ((com.maddox.rts.MsgNetAskNakListener)obj).msgNetNak(guaranted, (com.maddox.rts.NetChannel)_sender);
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

    private boolean bAsk;
    private com.maddox.rts.NetMsgGuaranted guaranted;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgNetAskNak.class);
    }
}
