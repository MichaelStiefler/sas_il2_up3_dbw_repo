// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Aiming.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

public class Aiming
{

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public boolean isInDelayMode()
    {
        return timeTot < 0L;
    }

    public boolean isInAimingMode()
    {
        return timeTot >= 0L;
    }

    public boolean tick()
    {
        return --timeCur <= 0L || timeTot >= 0L;
    }

    public float t()
    {
        return 1.0F - (float)timeCur / (float)timeTot;
    }

    public boolean aimedNow()
    {
        return timeCur <= 0L;
    }

    public Aiming()
    {
        anglesYaw = new AnglesFork();
        anglesPitch = new AnglesFork();
        clear();
    }

    private void clear()
    {
        setDelayMode(10F);
    }

    public void setDelayMode(float f)
    {
        timeTot = -1L;
        timeCur = com.maddox.il2.ai.ground.Aiming.SecsToTicks(f);
    }

    public void setDelayMode(double d)
    {
        timeTot = -1L;
        timeCur = com.maddox.il2.ai.ground.Aiming.SecsToTicks((float)d);
    }

    public int set(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, float f, float f1, com.maddox.JGP.Vector3d vector3d, float f2, float f3, 
            float f4, float f5, float f6, float f7)
    {
        clear();
        if(f3 <= 0.0F)
            f3 = 0.0F;
        if(f2 > 0.0F)
        {
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            vector3d1.x = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            vector3d1.y = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            vector3d1.z = com.maddox.il2.ai.World.Rnd().nextDouble(-1D, 1.0D);
            if(vector3d1.length() > 0.0001D)
            {
                float f9 = com.maddox.JGP.Geom.tanDeg(f2);
                vector3d1.scale((double)f9 / vector3d1.length());
                com.maddox.JGP.Vector3d vector3d2 = new Vector3d();
                vector3d2.set(vector3d);
                vector3d2.add(vector3d1);
                if(vector3d2.length() > 0.01D)
                {
                    vector3d2.normalize();
                    vector3d.set(vector3d2);
                }
            }
        }
        java.lang.Object obj = new Vector3d();
        loc.transformInv(vector3d, ((com.maddox.JGP.Vector3d) (obj)));
        com.maddox.il2.engine.Orient orient = new Orient();
        orient.setAT0(((com.maddox.JGP.Vector3d) (obj)));
        float f8 = orient.getYaw();
        com.maddox.il2.ai.AnglesFork anglesfork = new AnglesFork(orient.getPitch());
        float f10 = anglesfork.getSrcDeg();
        if(f10 < f4 || f10 > f5)
            return 0;
        anglesYaw.setDeg(f, f8);
        anglesPitch.setDeg(f1, f10);
        float f11 = anglesYaw.getAbsDiffDeg();
        float f12 = anglesPitch.getAbsDiffDeg();
        if(f11 > 3F && (f3 < 0.0001F || f11 / f3 > f6))
        {
            clear();
            return -1;
        }
        if(f12 > 3F && (f3 < 0.0001F || f12 / f3 > f7))
        {
            clear();
            return -1;
        }
        loc.get(checkBegPoint);
        checkEndPoint.set(vector3d);
        checkEndPoint.scale(100D);
        checkEndPoint.add(checkBegPoint);
        f11 = com.maddox.il2.engine.Engine.collideEnv().getLine(checkBegPoint, checkEndPoint, false, actor, null);
        if(f11 != null && (f11.getArmy() == 0 || f11.getArmy() == actor.getArmy()))
        {
            clear();
            return 0;
        } else
        {
            timeTot = timeCur = com.maddox.il2.ai.ground.Aiming.SecsToTicks(f3);
            return 1;
        }
    }

    public boolean setAutoTime(float f, float f1, float f2, float f3, float f4, float f5)
    {
        clear();
        anglesYaw.setDeg(f, f2);
        anglesPitch.setDeg(f1, f3);
        float f6 = anglesYaw.getAbsDiffDeg() / f4;
        float f7 = anglesPitch.getAbsDiffDeg() / f5;
        timeTot = timeCur = com.maddox.il2.ai.ground.Aiming.SecsToTicks(java.lang.Math.max(f6, f7));
        return timeTot > 1L;
    }

    public static final int RES_OK = 1;
    public static final int RES_UNREACHABLE_ANGLE = 0;
    public static final int RES_NOT_ENOUGH_TIME = -1;
    public com.maddox.il2.ai.AnglesFork anglesYaw;
    public com.maddox.il2.ai.AnglesFork anglesPitch;
    public long timeTot;
    public long timeCur;
    private static com.maddox.JGP.Point3d checkBegPoint = new Point3d();
    private static com.maddox.JGP.Point3d checkEndPoint = new Point3d();

}
