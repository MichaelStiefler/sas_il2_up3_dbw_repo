// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorViewPoint.java

package com.maddox.il2.objects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;

public class ActorViewPoint extends com.maddox.il2.engine.Actor
{
    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    static class HookUpdate extends com.maddox.il2.engine.Hook
    {

        public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
        {
        }

        HookUpdate()
        {
        }
    }


    public void setViewActor(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
        {
            return;
        } else
        {
            pos.setBase(actor, hook, true);
            return;
        }
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorViewPoint()
    {
        pos = new ActorPosMove(this, new Loc());
        hook = new HookUpdate();
        acoustics = com.maddox.il2.engine.Engine.worldAcoustics();
    }

    private com.maddox.il2.objects.HookUpdate hook;
}
