// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgProperty.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message, MsgPropertyListener, Property, MessageCache, 
//            Time

public class MsgProperty extends com.maddox.rts.Message
{

    public MsgProperty()
    {
    }

    public static void post(boolean flag, boolean flag1, java.lang.Object obj, com.maddox.rts.Property property, int i)
    {
        com.maddox.rts.MsgProperty msgproperty = (com.maddox.rts.MsgProperty)cache.get();
        msgproperty._action = i;
        if(flag)
        {
            msgproperty.setTime(com.maddox.rts.Time.currentReal());
            msgproperty.setFlags(64);
            if(flag1)
                msgproperty.send(obj, property);
            else
                msgproperty.post(obj, property);
        } else
        {
            msgproperty.setTime(com.maddox.rts.Time.current());
            msgproperty.setFlags(0);
            if(flag1)
                msgproperty.send(obj, property);
            else
                msgproperty.post(obj, property);
        }
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        if(obj instanceof com.maddox.rts.MsgPropertyListener)
        {
            switch(_action)
            {
            case 1: // '\001'
                ((com.maddox.rts.MsgPropertyListener)obj).msgPropertyAdded((com.maddox.rts.Property)_sender);
                break;

            case 2: // '\002'
                ((com.maddox.rts.MsgPropertyListener)obj).msgPropertyRemoved((com.maddox.rts.Property)_sender);
                break;

            case 3: // '\003'
                ((com.maddox.rts.MsgPropertyListener)obj).msgPropertyChanged((com.maddox.rts.Property)_sender);
                break;
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

    protected int _action;
    private static com.maddox.rts.MessageCache cache;

    static 
    {
        cache = new MessageCache(com.maddox.rts.MsgProperty.class);
    }
}
