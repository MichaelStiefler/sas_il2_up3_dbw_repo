// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorBorn.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import java.util.ArrayList;

public class ActorBorn extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{

    public void align()
    {
        alignPosToLand(0.0D, true);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorBorn(com.maddox.JGP.Point3d point3d)
    {
        r = 3000;
        airNames = new ArrayList();
        bParachute = true;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        pos.setAbs(point3d);
        align();
        drawing(true);
        icon = com.maddox.il2.engine.IconDraw.get("icons/born.mat");
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public int r;
    public java.util.ArrayList airNames;
    public boolean bParachute;
}
