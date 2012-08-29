// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ObjState.java

package com.maddox.rts;

import java.io.PrintStream;

// Referenced classes of package com.maddox.rts:
//            MessageComponent, Destroy, States, Message

public abstract class ObjState
    implements com.maddox.rts.MessageComponent, com.maddox.rts.Destroy
{

    public java.lang.Object THIS()
    {
        return this;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return states;
    }

    public void destroy()
    {
        if(states != null)
        {
            states.setState(-2);
            states.destroy();
            states = null;
        }
    }

    public boolean isDestroyed()
    {
        return states == null || states.getState() == -2;
    }

    public static void destroy(com.maddox.rts.Destroy destroy1)
    {
        if(destroy1 != null && !destroy1.isDestroyed())
            destroy1.destroy();
    }

    public com.maddox.rts.States states()
    {
        return states;
    }

    public int state()
    {
        return states.getState();
    }

    protected void finalize()
    {
        if(!isDestroyed())
            java.lang.System.out.println("ObjState.finalize: Object of " + getClass().getName() + " NOT destroyed");
    }

    protected ObjState()
    {
    }

    public com.maddox.rts.States states;
}
