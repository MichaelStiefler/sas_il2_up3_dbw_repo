// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Target.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.util.NumberTokenizer;

// Referenced classes of package com.maddox.il2.ai:
//            TDestroy, TDestroyGround, TDestroyBridge, TInspect, 
//            TEscort, TDefenceGround, TDefenceBridge, Chief, 
//            Wing, World, TargetsGuard

public class Target extends com.maddox.il2.engine.Actor
{
    static class StaticActorsFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(!actor.isAlive())
                return true;
            if(!com.maddox.il2.ai.Target.isStaticActor(actor))
            {
                return true;
            } else
            {
                com.maddox.il2.ai.Target._counterStaticActors++;
                return true;
            }
        }

        StaticActorsFilter()
        {
        }
    }


    public int importance()
    {
        return importance;
    }

    protected boolean checkActorDied(com.maddox.il2.engine.Actor actor)
    {
        return false;
    }

    protected boolean checkTaskComplete(com.maddox.il2.engine.Actor actor)
    {
        return false;
    }

    protected boolean checkPeriodic()
    {
        return false;
    }

    protected boolean checkTimeoutOff()
    {
        return false;
    }

    protected Target(int i, int j)
    {
        importance = i;
        if(j >= 0)
            timeout = (long)j * 60L * 1000L;
        else
            timeout = -1L;
        com.maddox.il2.ai.World.cur().targetsGuard.addTarget(this);
    }

    public static final void create(java.lang.String s)
    {
        if(s == null || s.length() == 0)
            return;
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s);
        int i = numbertokenizer.next(0, 0, 7);
        int j = numbertokenizer.next(0, 0, 2);
        boolean flag = numbertokenizer.next(0) == 1;
        int k = numbertokenizer.next(0, 0, 720);
        if(!flag)
            k = -1;
        int l = numbertokenizer.next(0);
        boolean flag1 = (l & 1) == 1;
        l /= 10;
        if(l < 0)
            l = 0;
        if(l > 100)
            l = 100;
        int i1 = numbertokenizer.next(0);
        int j1 = numbertokenizer.next(0);
        int k1 = numbertokenizer.next(1000, 2, 3000);
        int l1 = numbertokenizer.next(0);
        java.lang.String s1 = numbertokenizer.next(null);
        if(s1 != null && s1.startsWith("Bridge"))
            s1 = " " + s1;
        switch(i)
        {
        case 0: // '\0'
            new TDestroy(j, k, s1, l);
            break;

        case 1: // '\001'
            new TDestroyGround(j, k, i1, j1, k1, l);
            break;

        case 2: // '\002'
            new TDestroyBridge(j, k, s1);
            break;

        case 3: // '\003'
            new TInspect(j, k, i1, j1, k1, flag1, s1);
            break;

        case 4: // '\004'
        case 5: // '\005'
            new TEscort(j, k, s1, l);
            break;

        case 6: // '\006'
            new TDefenceGround(j, k, i1, j1, k1, l);
            break;

        case 7: // '\007'
            new TDefenceBridge(j, k, s1);
            break;
        }
    }

    protected static int countStaticActors(com.maddox.JGP.Point3d point3d, double d)
    {
        _counterStaticActors = 0;
        com.maddox.il2.engine.Engine.cur.collideEnv.getFiltered(null, point3d, d, staticActorsFilter);
        return _counterStaticActors;
    }

    protected static boolean isStaticActor(com.maddox.il2.engine.Actor actor)
    {
        if(actor.getArmy() == 0)
            return false;
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            return false;
        if(actor instanceof com.maddox.il2.ai.Chief)
            return false;
        if(actor instanceof com.maddox.il2.ai.Wing)
            return false;
        return !com.maddox.il2.engine.Actor.isValid(actor.getOwner()) || !(actor.getOwner() instanceof com.maddox.il2.ai.Chief);
    }

    public static final int PRIMARY = 0;
    public static final int SECONDARY = 1;
    public static final int SECRET = 2;
    public static final int DESTROY = 0;
    public static final int DESTROY_GROUND = 1;
    public static final int DESTROY_BRIDGE = 2;
    public static final int INSPECT = 3;
    public static final int ESCORT = 4;
    public static final int DEFENCE = 5;
    public static final int DEFENCE_GROUND = 6;
    public static final int DEFENCE_BRIDGE = 7;
    protected int importance;
    protected long timeout;
    private static int _counterStaticActors;
    private static com.maddox.il2.ai.StaticActorsFilter staticActorsFilter = new StaticActorsFilter();


}
