// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DreamEnv.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Actor

public abstract class DreamEnv
{

    public boolean isSleep(double d, double d1)
    {
        return true;
    }

    public boolean isSleep(com.maddox.JGP.Point3d point3d)
    {
        return true;
    }

    public boolean isSleep(int i, int j)
    {
        return true;
    }

    protected void doChanges()
    {
    }

    protected void changedListenerPos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
    }

    protected void addListener(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void removeListener(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void addGlobalListener(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void removeGlobalListener(com.maddox.il2.engine.Actor actor)
    {
    }

    public void resetGlobalListener(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void changedFirePos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
    }

    protected void addFire(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void removeFire(com.maddox.il2.engine.Actor actor)
    {
    }

    public void resetGameClear()
    {
    }

    public void resetGameCreate()
    {
    }

    protected void clearFire()
    {
    }

    protected DreamEnv()
    {
    }

    public static final double SQUARE_SIZE = 200D;
    public static final double FIRE_SIZE = 7600D;
    public static final double UPDATE_TIME = 1D;
}
