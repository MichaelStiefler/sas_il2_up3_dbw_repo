// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgTimeOut.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgTimeOutListener, MessageCache

public class MsgTimeOut extends com.maddox.rts.Message
{

    public MsgTimeOut()
    {
        _param = null;
    }

    public MsgTimeOut(java.lang.Object obj)
    {
        _param = obj;
    }

    public static void post(int i, long l, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.MsgTimeOut msgtimeout = (com.maddox.rts.MsgTimeOut)cache.get();
        msgtimeout._param = obj1;
        msgtimeout.post(i, obj, l);
    }

    public static void post(int i, double d, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.MsgTimeOut msgtimeout = (com.maddox.rts.MsgTimeOut)cache.get();
        msgtimeout._param = obj1;
        msgtimeout.post(i, obj, d);
    }

    public static void post(long l, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.MsgTimeOut msgtimeout = (com.maddox.rts.MsgTimeOut)cache.get();
        msgtimeout._param = obj1;
        msgtimeout.post(0, obj, l);
    }

    public static void post(long l, int i, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.MsgTimeOut msgtimeout = (com.maddox.rts.MsgTimeOut)cache.get();
        msgtimeout._param = obj1;
        msgtimeout.post(0, obj, l, i);
    }

    public static void post(double d, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.rts.MsgTimeOut msgtimeout = (com.maddox.rts.MsgTimeOut)cache.get();
        msgtimeout._param = obj1;
        msgtimeout.post(0, obj, d);
    }

    public void clean()
    {
        super.clean();
        _param = null;
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgTimeOutListener)
        {
            ((com.maddox.rts.MsgTimeOutListener)obj).msgTimeOut(_param);
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

    protected java.lang.Object _param;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgTimeOut.class);
    }
}
