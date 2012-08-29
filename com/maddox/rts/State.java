// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   State.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            MessageListener, Message

public class State
    implements com.maddox.rts.MessageListener
{

    public java.lang.Object superObj()
    {
        return parentListener;
    }

    public java.lang.Object getParentListener(com.maddox.rts.Message message)
    {
        return parentListener;
    }

    public void begin(int i)
    {
    }

    public void end(int i)
    {
    }

    protected void setParentListener(java.lang.Object obj)
    {
        parentListener = obj;
    }

    public State()
    {
        parentListener = null;
    }

    public State(java.lang.Object obj)
    {
        parentListener = obj;
    }

    public static final int UNKNOWN = -1;
    public static final int DESTROYED = -2;
    private java.lang.Object parentListener;
}
