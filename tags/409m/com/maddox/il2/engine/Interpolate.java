// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Interpolate.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Interpolators

public abstract class Interpolate
{

    public void begin()
    {
    }

    public boolean tick()
    {
        return false;
    }

    public void sleep()
    {
    }

    public void wakeup(long l)
    {
    }

    public void cancel()
    {
    }

    public void end()
    {
        if(msgEnd != null && com.maddox.il2.engine.Actor.isValid(actor))
            if(actor.isRealTime())
                msgEnd.post(64, actor, com.maddox.rts.Time.currentReal());
            else
                msgEnd.post(0, actor, com.maddox.rts.Time.current());
    }

    public void doDestroy()
    {
    }

    public final void canceling()
    {
        if(actor != null && actor.interp != null)
            actor.interp.cancel(this);
    }

    public final void ending()
    {
        if(actor != null && actor.interp != null)
            actor.interp.end(this);
    }

    public Interpolate()
    {
        actor = null;
        id = null;
        msgEnd = null;
        bExecuted = false;
    }

    public com.maddox.il2.engine.Actor actor;
    public java.lang.Object id;
    public com.maddox.rts.Message msgEnd;
    public long timeBegin;
    public boolean bExecuted;
}
