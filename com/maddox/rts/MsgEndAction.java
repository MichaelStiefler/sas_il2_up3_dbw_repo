// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgEndAction.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgEndActionListener, MessageCache, Time

public class MsgEndAction extends com.maddox.rts.Message
{

    public MsgEndAction()
    {
    }

    public static void post(int i, long l, java.lang.Object obj, java.lang.Object obj1, int j)
    {
        com.maddox.rts.MsgEndAction msgendaction = (com.maddox.rts.MsgEndAction)cache.get();
        msgendaction._action = j;
        msgendaction.post(i, obj, l, obj1);
    }

    public static void post(int i, double d, java.lang.Object obj, java.lang.Object obj1, int j)
    {
        com.maddox.rts.MsgEndAction msgendaction = (com.maddox.rts.MsgEndAction)cache.get();
        msgendaction._action = j;
        msgendaction.post(i, obj, d, obj1);
    }

    public static void post(int i, java.lang.Object obj, java.lang.Object obj1, int j)
    {
        com.maddox.rts.MsgEndAction msgendaction = (com.maddox.rts.MsgEndAction)cache.get();
        msgendaction._action = j;
        if((i & 0x40) != 0)
            msgendaction.post(i, obj, com.maddox.rts.Time.currentReal(), obj1);
        else
            msgendaction.post(i, obj, com.maddox.rts.Time.current(), obj1);
    }

    public static void postReal(java.lang.Object obj, java.lang.Object obj1, int i)
    {
        com.maddox.rts.MsgEndAction msgendaction = (com.maddox.rts.MsgEndAction)cache.get();
        msgendaction._action = i;
        msgendaction.post(64, obj, com.maddox.rts.Time.currentReal(), obj1);
    }

    public static void post(java.lang.Object obj, java.lang.Object obj1, int i)
    {
        com.maddox.rts.MsgEndAction msgendaction = (com.maddox.rts.MsgEndAction)cache.get();
        msgendaction._action = i;
        msgendaction.post(0, obj, com.maddox.rts.Time.current(), obj1);
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgEndActionListener)
        {
            ((com.maddox.rts.MsgEndActionListener)obj).msgEndAction(_sender, _action);
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

    protected int _action;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgEndAction.class);
    }
}
