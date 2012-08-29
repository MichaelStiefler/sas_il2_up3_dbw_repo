// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Eff3DActor.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.engine:
//            ActorPosStatic, Engine, DrawEnv, CollideEnv, 
//            Actor, DreamEnv, Loc

class ActorPosStaticEff3D extends com.maddox.il2.engine.ActorPosStatic
{

    protected void drawingChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.drawEnv.add(actor());
        else
            com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor());
    }

    protected void collideChange(boolean flag)
    {
        if(flag)
            com.maddox.il2.engine.Engine.cur.collideEnv.add(actor());
        else
            com.maddox.il2.engine.Engine.cur.collideEnv.remove(actor());
    }

    protected void changePosInEnvs(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        inValidate(true);
        int i = actor().flags;
        if((i & 1) == 1)
            com.maddox.il2.engine.Engine.cur.drawEnv.changedPos(actor(), point3d, point3d1);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.changedPosStatic(actor(), point3d, point3d1);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.changedListenerPos(actor(), point3d, point3d1);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.cur.dreamEnv.changedFirePos(actor(), point3d, point3d1);
    }

    protected void clearEnvs(com.maddox.il2.engine.Actor actor)
    {
        int i = actor.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base() == null))
            com.maddox.il2.engine.Engine.cur.drawEnv.remove(actor);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.removeStatic(actor);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.removeListener(actor);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.dreamEnv().removeFire(actor);
        int j = com.maddox.il2.engine.Engine.cur.posChanged.indexOf(actor);
        if(j >= 0)
            com.maddox.il2.engine.Engine.cur.posChanged.remove(j);
    }

    protected void initEnvs(com.maddox.il2.engine.Actor actor)
    {
        actor.pos = this;
        int i = actor.flags;
        if((i & 1) != 0 && ((i & 2) == 0 || base() == null))
            com.maddox.il2.engine.Engine.cur.drawEnv.add(actor);
        if((i & 0x30) == 16)
            com.maddox.il2.engine.Engine.cur.collideEnv.addStatic(actor);
        if((i & 0x200) == 512)
            com.maddox.il2.engine.Engine.cur.dreamEnv.addListener(actor);
        if((i & 0x100) == 256)
            com.maddox.il2.engine.Engine.dreamEnv().addFire(actor);
    }

    public ActorPosStaticEff3D(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc)
    {
        super(actor, loc);
    }
}
