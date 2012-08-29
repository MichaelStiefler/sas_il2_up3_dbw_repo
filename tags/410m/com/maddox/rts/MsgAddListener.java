// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgAddListener.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgAddListenerListener, MessageCache, Time

public class MsgAddListener extends com.maddox.rts.Message
{

    public MsgAddListener()
    {
        _listener = null;
        _param = null;
    }

    public MsgAddListener(java.lang.Object obj, java.lang.Object obj1)
    {
        _listener = obj;
        _param = obj1;
    }

    public MsgAddListener(java.lang.Object obj)
    {
        _listener = obj;
        _param = null;
    }

    public static void post(int i, java.lang.Object obj, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.MsgAddListener msgaddlistener = (com.maddox.rts.MsgAddListener)cache.get();
        msgaddlistener._listener = obj1;
        msgaddlistener._param = obj2;
        if((i & 0x40) != 0)
            msgaddlistener._time = com.maddox.rts.Time.currentReal();
        else
            msgaddlistener._time = com.maddox.rts.Time.current();
        msgaddlistener.post(i, obj);
    }

    public static void post(int i, java.lang.Object obj, long l, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.MsgAddListener msgaddlistener = (com.maddox.rts.MsgAddListener)cache.get();
        msgaddlistener._listener = obj1;
        msgaddlistener._param = obj2;
        msgaddlistener._time = l;
        msgaddlistener.post(i, obj);
    }

    public static void post(int i, java.lang.Object obj, long l, int j, java.lang.Object obj1, java.lang.Object obj2)
    {
        com.maddox.rts.MsgAddListener msgaddlistener = (com.maddox.rts.MsgAddListener)cache.get();
        msgaddlistener._listener = obj1;
        msgaddlistener._param = obj2;
        msgaddlistener._time = l;
        msgaddlistener._tickPos = j;
        msgaddlistener.post(i, obj);
    }

    public void clean()
    {
        super.clean();
        _listener = null;
        _param = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgAddListenerListener)
        {
            ((com.maddox.rts.MsgAddListenerListener)obj).msgAddListener(_listener, _param);
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
        cache = new MessageCache(com.maddox.rts.MsgAddListener.class);
    }
}
