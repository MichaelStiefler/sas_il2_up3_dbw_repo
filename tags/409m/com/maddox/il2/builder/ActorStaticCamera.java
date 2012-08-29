// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorStaticCamera.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;

public class ActorStaticCamera extends com.maddox.il2.engine.Actor
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

    public ActorStaticCamera(com.maddox.JGP.Point3d point3d)
    {
        h = 100;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        pos.setAbs(point3d);
        align();
        drawing(true);
        icon = com.maddox.il2.engine.IconDraw.get("icons/camera.mat");
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public int h;
}
