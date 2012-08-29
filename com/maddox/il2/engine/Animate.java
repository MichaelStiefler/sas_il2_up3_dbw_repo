// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Animate.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Loc, HookOnLandNormal, Mesh, Actor, 
//            Hook, Engine, Landscape, Orient, 
//            ActorPos, Animator

public abstract class Animate
{

    public abstract double setup(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, double d, double d1, double d2);

    public abstract double fullStepLen(com.maddox.il2.engine.Animator animator, double d);

    public abstract void fullStep(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, double d);

    public abstract void step(com.maddox.il2.engine.Animator animator, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Hook hook, double d, double d1);

    protected void step(com.maddox.il2.engine.Mesh mesh, double d)
    {
        if(b2Frames)
        {
            mesh.setFrame(frame0, frame1, (float)d);
        } else
        {
            int i = frame0;
            int j = frame1;
            if(i == j)
                mesh.setFrame(i);
            else
            if(i > j)
            {
                int k = i;
                i = j;
                j = k;
            }
            int l = (j - i) + 1;
            double d1 = 1.0D / (double)l;
            if(d > 1.0D - d1)
                mesh.setFrame(j, i, (float)((d - (1.0D - d1)) / d1));
            else
                mesh.setFrameFromRange(i, j, (float)(d / (1.0D - d1)));
        }
    }

    protected void setPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook)
    {
        double d = 0.0D;
        if(hook == null)
        {
            d = actor.collisionR();
        } else
        {
            locHook.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            hook.computePos(actor, locNull, locHook);
            d = -locHook.getZ();
        }
        if(bLandNormal)
        {
            hookOnLandNormal.dz = d;
            hookOnLandNormal.computePos(actor, locNull, locRes);
        } else
        {
            double d1 = com.maddox.il2.engine.Engine.land().HQ(locRes.getX(), locRes.getY());
            locRes.getPoint().z = d1 + d;
            locRes.getOrient().set(locRes.getOrient().getAzimut(), 0.0F, 0.0F);
        }
        actor.pos.setRel(locRes);
    }

    public void set(java.lang.String s, int i, int j, float f, boolean flag, boolean flag1)
    {
        name = s;
        frame0 = i;
        frame1 = j;
        time = java.lang.Math.round(f * 1000F);
        bLandNormal = flag;
        b2Frames = flag1;
    }

    public Animate(java.lang.String s, int i, int j, float f)
    {
        bLandNormal = false;
        b2Frames = false;
        set(s, i, j, f, false, false);
    }

    public Animate(java.lang.String s, int i, int j, float f, boolean flag, boolean flag1)
    {
        bLandNormal = false;
        b2Frames = false;
        set(s, i, j, f, flag, flag1);
    }

    public java.lang.String name;
    public int frame0;
    public int frame1;
    public long time;
    public boolean bLandNormal;
    public boolean b2Frames;
    protected static com.maddox.il2.engine.Loc locRes = new Loc();
    private static com.maddox.il2.engine.Loc locNull = new Loc();
    private static com.maddox.il2.engine.Loc locHook = new Loc();
    private static com.maddox.il2.engine.HookOnLandNormal hookOnLandNormal = new HookOnLandNormal(0.0D);

}
