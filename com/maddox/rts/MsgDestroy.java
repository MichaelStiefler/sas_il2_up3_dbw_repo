// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgDestroy.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, Destroy, MessageCache, Time

public class MsgDestroy extends com.maddox.rts.Message
{

    public MsgDestroy()
    {
    }

    public static void Post(int i, long l, java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(i, obj, l);
    }

    public static void Post(int i, double d, java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(i, obj, d);
    }

    public static void Post(long l, java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(0, obj, l);
    }

    public static void Post(double d, java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(0, obj, d);
    }

    public static void Post(java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(0, obj, com.maddox.rts.Time.current());
    }

    public static void PostReal(long l, java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(64, obj, l);
    }

    public static void PostReal(java.lang.Object obj)
    {
        com.maddox.rts.MsgDestroy msgdestroy = (com.maddox.rts.MsgDestroy)cache.get();
        msgdestroy.post(64, obj, com.maddox.rts.Time.currentReal());
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.Destroy)
        {
            ((com.maddox.rts.Destroy)obj).destroy();
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

    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgDestroy.class);
    }
}
