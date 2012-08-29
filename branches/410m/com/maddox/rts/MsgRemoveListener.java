// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgRemoveListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgRemoveListenerListener, MessageCache, Time

public class MsgRemoveListener extends com.maddox.rts.Message
{

    public MsgRemoveListener()
    {
        _listener = null;
        _param = null;
    }

    public MsgRemoveListener(java.lang.Object obj, java.lang.Object obj1)
    {
        _listener = obj;
        _param = obj1;
    }

    public static void post(int i, java.lang.Object obj, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.MsgRemoveListener msgremovelistener = (com.maddox.rts.MsgRemoveListener)cache.get();
        msgremovelistener._listener = obj1;
        msgremovelistener._param = obj2;
        if((i & 0x40) != 0)
            msgremovelistener._time = com.maddox.rts.Time.currentReal();
        else
            msgremovelistener._time = com.maddox.rts.Time.current();
        msgremovelistener.post(i, obj);
    }

    public static void post(int i, java.lang.Object obj, long l, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.MsgRemoveListener msgremovelistener = (com.maddox.rts.MsgRemoveListener)cache.get();
        msgremovelistener._listener = obj1;
        msgremovelistener._param = obj2;
        msgremovelistener._time = l;
        msgremovelistener.post(i, obj);
    }

    public void clean()
    {
        super.clean();
        _listener = null;
        _param = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgRemoveListenerListener)
        {
            ((com.maddox.rts.MsgRemoveListenerListener)obj).msgRemoveListener(_listener, _param);
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

    protected java.lang.Object _listener;
    protected java.lang.Object _param;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgRemoveListener.class);
    }
}
