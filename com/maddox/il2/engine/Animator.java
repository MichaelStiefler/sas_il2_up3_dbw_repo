// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Animator.java

package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.engine:
//            Interpolate, AnimatedActor, Loc, Actor, 
//            Animate, Animates, ActorPos, Hook

public class Animator extends com.maddox.il2.engine.Interpolate
{

    public boolean isRun()
    {
        return bExecuted;
    }

    public java.lang.String getCur()
    {
        return bExecuted ? move.name : null;
    }

    public com.maddox.il2.engine.AnimatedActor animatedActor()
    {
        return (com.maddox.il2.engine.AnimatedActor)actor;
    }

    public com.maddox.il2.engine.Hook getLandHook()
    {
        if(landHook == null)
            landHook = actor.findHook("ground_level");
        return landHook;
    }

    public void start(java.lang.String s, double d, double d1)
    {
        start(s, d, d1, (com.maddox.rts.Message)null, 1.0D);
    }

    public void start(java.lang.String s, double d, double d1, java.lang.String s1)
    {
        start(s, d, d1, s1, 1.0D);
    }

    public void start(java.lang.String s, double d, double d1, com.maddox.rts.Message message)
    {
        start(s, d, d1, message, 1.0D);
    }

    public void start(java.lang.String s, double d, double d1, java.lang.String s1, double d2)
    {
        com.maddox.rts.MsgInvokeMethod msginvokemethod = null;
        if(s1 != null)
            msginvokemethod = new MsgInvokeMethod(s1);
        start(s, d, d1, ((com.maddox.rts.Message) (msginvokemethod)), d2);
    }

    public void start(java.lang.String s, double d, double d1, com.maddox.rts.Message message, double d2)
    {
        if(isRun())
            stop(false);
        msgEnd = message;
        speed = d2;
        move = moves.get(s);
        bExecuted = true;
        countMoves = 0.0D;
        time0 = timeCurrent();
        actor.pos.getAbs(loc0);
        lenStep = move.fullStepLen(this, d2);
        lenToEnd = move.setup(this, loc0, d, d1, d2);
        if(lenToEnd > 0.001D && lenStep > 0.001D)
        {
            tick();
        } else
        {
            countMoves = 1.0D;
            tick();
            stop(true);
        }
    }

    public void start(java.lang.String s, double d)
    {
        start(s, d, (com.maddox.rts.Message)null, 1.0D);
    }

    public void start(java.lang.String s, double d, java.lang.String s1)
    {
        start(s, d, s1, 1.0D);
    }

    public void start(java.lang.String s, double d, com.maddox.rts.Message message)
    {
        start(s, d, message, 1.0D);
    }

    public void start(java.lang.String s, double d, java.lang.String s1, double d1)
    {
        com.maddox.rts.MsgInvokeMethod msginvokemethod = null;
        if(s1 != null)
            msginvokemethod = new MsgInvokeMethod(s1);
        start(s, d, ((com.maddox.rts.Message) (msginvokemethod)), d1);
    }

    public void start(java.lang.String s, double d, com.maddox.rts.Message message, double d1)
    {
        if(isRun())
            stop(false);
        msgEnd = message;
        speed = d1;
        move = moves.get(s);
        bExecuted = true;
        countMoves = d;
        time0 = timeCurrent();
        actor.pos.getAbs(loc0);
        if(d != 0.0D)
        {
            tick();
        } else
        {
            countMoves = 1.0D;
            tick();
            stop(true);
        }
    }

    public void stop()
    {
        stop(false);
    }

    public void stop(boolean flag)
    {
        if(!bExecuted)
            return;
        bExecuted = false;
        if(flag && msgEnd != null)
        {
            if(!msgEnd.busy() && com.maddox.il2.engine.Actor.isValid(actor))
            {
                msgEnd.setListener(actor);
                msgEnd.setTime(timeCurrent());
                msgEnd.setSender(this);
                msgEnd.post();
            }
            msgEnd = null;
        }
    }

    protected long timeCurrent()
    {
        return actor.isRealTime() ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current();
    }

    public boolean tick()
    {
        long l = timeCurrent();
        if(l < time0)
            l = time0;
        double d = (double)(l - time0) / (double)move.time;
        if(countMoves != 0.0D)
        {
            if(countMoves > 0.0D && d >= countMoves)
            {
                d = countMoves;
                stop(true);
            }
            while(d > 1.0D) 
            {
                d--;
                time0 += move.time;
                if(countMoves > 0.0D)
                    countMoves--;
                move.fullStep(this, loc0, speed);
            }
        } else
        {
            if(d * lenStep >= lenToEnd)
            {
                d = lenToEnd / lenStep;
                stop(true);
            }
            while(d > 1.0D) 
            {
                d--;
                time0 += move.time;
                lenToEnd -= lenStep;
                move.fullStep(this, loc0, speed);
            }
        }
        move.step(this, loc0, getLandHook(), speed, d);
        return true;
    }

    public Animator(com.maddox.il2.engine.AnimatedActor animatedactor, com.maddox.il2.engine.Animates animates)
    {
        this(animatedactor, animates, "AnimatorMoves");
    }

    public Animator(com.maddox.il2.engine.AnimatedActor animatedactor, com.maddox.il2.engine.Animates animates, java.lang.String s)
    {
        loc0 = new Loc();
        if(animatedactor.getAnimator() != null)
        {
            return;
        } else
        {
            moves = animates;
            ((com.maddox.il2.engine.Actor)animatedactor).interpPut(this, s, -1L, null);
            return;
        }
    }

    protected com.maddox.il2.engine.Animates moves;
    protected com.maddox.il2.engine.Animate move;
    protected com.maddox.il2.engine.Hook landHook;
    protected double lenToEnd;
    protected double lenStep;
    protected double countMoves;
    protected double speed;
    protected long time0;
    protected com.maddox.il2.engine.Loc loc0;
}
