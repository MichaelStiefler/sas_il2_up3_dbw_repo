// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgAction.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Message

public class MsgAction extends com.maddox.rts.Message
{

    public void doAction()
    {
    }

    public void doAction(java.lang.Object obj)
    {
        doAction();
    }

    public MsgAction(int i, long l, java.lang.Object obj)
    {
        post(i, this, l, obj);
    }

    public MsgAction(int i, double d, java.lang.Object obj)
    {
        post(i, this, d, obj);
    }

    public MsgAction(int i, long l)
    {
        post(i, this, l);
    }

    public MsgAction(int i, double d)
    {
        post(i, this, d);
    }

    public MsgAction(long l, java.lang.Object obj)
    {
        post(0, this, l, obj);
    }

    public MsgAction(double d, java.lang.Object obj)
    {
        post(0, this, d, obj);
    }

    public MsgAction(boolean flag, java.lang.Object obj)
    {
        post(flag ? 64 : 0, this, 0.0D, obj);
    }

    public MsgAction(boolean flag)
    {
        post(flag ? 64 : 0, this, 0.0D);
    }

    public MsgAction(long l)
    {
        post(0, this, l);
    }

    public MsgAction(double d)
    {
        post(0, this, d);
    }

    public MsgAction()
    {
    }

    public boolean invokeListener(java.lang.Object obj)
    {
        doAction(_sender);
        return true;
    }
}
