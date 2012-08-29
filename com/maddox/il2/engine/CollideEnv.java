// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CollideEnv.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.AbstractCollection;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, ActorFilter, Accumulator

public abstract class CollideEnv
{

    public boolean isDoCollision()
    {
        return false;
    }

    protected void doCollision(java.util.List list)
    {
    }

    protected void doBulletMoveAndCollision()
    {
    }

    public void getSphere(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d)
    {
    }

    public com.maddox.il2.engine.Actor getLine(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, boolean flag, com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d2)
    {
        return null;
    }

    public com.maddox.il2.engine.Actor getLine(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, boolean flag, com.maddox.il2.engine.ActorFilter actorfilter, com.maddox.JGP.Point3d point3d2)
    {
        return null;
    }

    public void getFiltered(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d, com.maddox.il2.engine.ActorFilter actorfilter)
    {
    }

    public void getNearestEnemies(com.maddox.JGP.Point3d point3d, double d, int i, com.maddox.il2.engine.Accumulator accumulator)
    {
    }

    public void getNearestEnemiesCyl(com.maddox.JGP.Point3d point3d, double d, double d1, double d2, 
            int i, com.maddox.il2.engine.Accumulator accumulator)
    {
    }

    protected void changedPos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
    }

    protected void add(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void remove(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void changedPosStatic(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
    }

    protected void addStatic(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void removeStatic(com.maddox.il2.engine.Actor actor)
    {
    }

    protected void clear()
    {
    }

    protected void resetGameClear()
    {
    }

    protected void resetGameCreate()
    {
    }

    protected CollideEnv()
    {
    }
}
