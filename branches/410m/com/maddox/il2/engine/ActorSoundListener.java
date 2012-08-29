// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorSoundListener.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Message;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, SoundListenerDraw, ActorPosMove

public class ActorSoundListener extends com.maddox.il2.engine.Actor
{

    public void setUseBaseSpeed(boolean flag)
    {
        bUseBaseSpeed = flag;
    }

    public boolean isUseBaseSpeed()
    {
        return bUseBaseSpeed;
    }

    public boolean isDrawing()
    {
        return true;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorSoundListener()
    {
        bUseBaseSpeed = true;
        absPos = new Point3d();
        draw = new SoundListenerDraw();
        pos = new ActorPosMove(this);
        drawing(true);
        setName("ActorSoundListener");
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public double getRelRhoSqr(com.maddox.JGP.Point3d point3d)
    {
        double d = point3d.x - absPos.x;
        double d1 = point3d.y - absPos.y;
        double d2 = point3d.z - absPos.z;
        return d * d + d1 * d1 + d2 * d2;
    }

    public void initDraw()
    {
        ((com.maddox.il2.engine.SoundListenerDraw)draw).init();
    }

    private boolean bUseBaseSpeed;
    public com.maddox.JGP.Point3d absPos;
}
