// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorLand.java

package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;

public class ActorLand extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.MsgExplosionListener
{

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        if(com.maddox.il2.engine.Engine.land().isWater(shot.p.x, shot.p.y))
            shot.bodyMaterial = 1;
        else
            shot.bodyMaterial = 0;
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorLand()
    {
        pos = new ActorPosStatic(this, new Loc());
        flags = 16;
        setName("landscape");
        net = new com.maddox.il2.engine.ActorNet(this, 250) {

            public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
            {
            }

        }
;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }
}
