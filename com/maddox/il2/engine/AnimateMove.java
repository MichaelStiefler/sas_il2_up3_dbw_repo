// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AnimateMove.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Animate, AnimatedActor, Loc, Orient, 
//            Animator, Hook

public class AnimateMove extends com.maddox.il2.engine.Animate
{

    public double setup(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, double d, double d1, double d2)
    {
        com.maddox.JGP.Point3d point3d = loc.getPoint();
        float f = (float)((-java.lang.Math.atan2(d1 - point3d.y, d - point3d.x) * 180D) / 3.1415926535897931D);
        loc.getOrient().set(f, loc.getTangage(), loc.getKren());
        return java.lang.Math.sqrt((d - point3d.x) * (d - point3d.x) + (d1 - point3d.y) * (d1 - point3d.y)) * d2;
    }

    public double fullStepLen(com.maddox.il2.engine.Animator animator, double d)
    {
        return (double)len * d;
    }

    public void fullStep(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, double d)
    {
        loc.getPoint().z = 0.0D;
        locRel.getPoint().x = fullStepLen(animator, d);
        locRes.add(locRel, loc);
        loc.set(locRes);
    }

    public void step(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Hook hook, double d, double d1)
    {
        loc.getPoint().z = 0.0D;
        locRel.getPoint().x = fullStepLen(animator, d) * d1;
        locRes.add(locRel, loc);
        com.maddox.il2.engine.Actor actor = animator.actor;
        step(((com.maddox.il2.engine.AnimatedActor)actor).getAnimatedMesh(), d1);
        setPos(actor, hook);
    }

    public AnimateMove(java.lang.String s, int i, int j, float f, float f1)
    {
        super(s, i, j, f, false, false);
        len = f1;
    }

    public AnimateMove(java.lang.String s, int i, int j, float f, float f1, boolean flag, boolean flag1)
    {
        super(s, i, j, f, flag, flag1);
        len = f1;
    }

    public float len;
    private static com.maddox.il2.engine.Loc locRel = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);

}
