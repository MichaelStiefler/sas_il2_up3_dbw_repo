// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DrawEnv.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Engine, EngineProfile, ActorPos, 
//            ActorDraw, ActorFilter

public abstract class DrawEnv
{

    public void preRender(double d, double d1, double d2, float f, 
            int i, java.util.List list, java.util.List list1, java.util.List list2, boolean flag)
    {
    }

    public void getFiltered(java.util.AbstractCollection abstractcollection, double d, double d1, double d2, 
            double d3, int i, com.maddox.il2.engine.ActorFilter actorfilter)
    {
    }

    public void getFiltered(java.util.AbstractMap abstractmap, double d, double d1, double d2, 
            double d3, int i, com.maddox.il2.engine.ActorFilter actorfilter)
    {
    }

    public void staticTrimToSize()
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

    public void setUniformMaxDist(int i, int j, float f)
    {
    }

    protected void clear()
    {
    }

    public void resetGameClear()
    {
    }

    public void resetGameCreate()
    {
    }

    protected void preRender(com.maddox.il2.engine.Actor actor, int i, java.util.List list, java.util.List list1, java.util.List list2)
    {
        if(list1 != null && (i & 2) != 0)
            list1.add(actor);
        else
        if(list != null && (i & 1) != 0)
            list.add(actor);
        if(list2 != null && (i & 4) != 0)
            list2.add(actor);
        com.maddox.il2.engine.Engine.cur.profile.draw++;
        java.util.List list3 = actor.pos.getListBaseAttached();
        if(list3 == null)
            return;
        int j = list3.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)list3.get(k);
            if(!com.maddox.il2.engine.Actor.isValid(actor1) || (actor1.flags & 3) != 3 || actor1.draw == null)
                continue;
            i = actor1.draw.preRender(actor1);
            if(i > 0)
                preRender(actor1, i, list, list1, list2);
        }

    }

    protected DrawEnv()
    {
    }

    public static final int LANDPLATE = 1;
    public static final int STATIC = 2;
    public static final int MOVED = 12;
    public static final int MOVED_SHORT = 4;
    public static final int MOVED_LONG = 8;
    public static final int ALL = 15;
}
