// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiTargetsSupportMethods.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            Target, TDestroy, TDestroyGround, TDestroyBridge, 
//            TInspect, TEscort, TDefenceGround, TDefenceBridge, 
//            Wing, World, TargetsGuard

public class ZutiTargetsSupportMethods
{

    public ZutiTargetsSupportMethods()
    {
    }

    public static boolean checkIfActorPartOfTDestroyGround(com.maddox.il2.engine.Actor actor, com.maddox.il2.ai.TDestroyGround tdestroyground)
    {
        if(actor.pos == null)
            return false;
        actor.pos.getAbs(com.maddox.il2.ai.TDestroyGround.p);
        com.maddox.il2.ai.TDestroyGround.p.z = tdestroyground.pos.getAbsPoint().z;
        if(tdestroyground.pos.getAbsPoint().distance(com.maddox.il2.ai.TDestroyGround.p) <= tdestroyground.r)
        {
            if(!com.maddox.il2.ai.Target.isStaticActor(actor))
                return false;
            tdestroyground.countActors--;
            if(tdestroyground.countActors <= 0)
                return true;
        }
        return false;
    }

    public static boolean checkIfActorPartOfTDefenceGround(com.maddox.il2.engine.Actor actor, com.maddox.il2.ai.TDefenceGround tdefenceground)
    {
        if(actor.pos == null)
            return false;
        actor.pos.getAbs(com.maddox.il2.ai.TDefenceGround.p);
        com.maddox.il2.ai.TDefenceGround.p.z = tdefenceground.pos.getAbsPoint().z;
        if(tdefenceground.pos.getAbsPoint().distance(com.maddox.il2.ai.TDefenceGround.p) <= tdefenceground.r)
        {
            if(!com.maddox.il2.ai.Target.isStaticActor(actor))
                return false;
            tdefenceground.countActors--;
            if(tdefenceground.countActors <= 0)
                return true;
        }
        return false;
    }

    public static void checkForDeactivatedTargets()
    {
        com.maddox.il2.ai.World world = com.maddox.il2.ai.World.cur();
        if(world == null || world.targetsGuard == null)
            return;
        java.util.List list = world.targetsGuard.zutiGetTargets();
        if(list == null || list.size() == 0)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        Object obj4 = null;
        Object obj5 = null;
        Object obj6 = null;
        Object obj7 = null;
        Object obj8 = null;
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)list.get(j);
            if(target instanceof com.maddox.il2.ai.TDestroy)
            {
                com.maddox.il2.ai.TDestroy tdestroy = (com.maddox.il2.ai.TDestroy)target;
                boolean flag = tdestroy.checkActor();
                if(tdestroy.actor != null && (tdestroy.actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    if(((com.maddox.il2.objects.ships.BigshipGeneric)tdestroy.actor).zutiGetDying() > 0)
                    {
                        com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdestroy.nameTarget);
                        arraylist.add(target);
                    }
                    continue;
                }
                if(tdestroy.actor != null && (tdestroy.actor instanceof com.maddox.il2.objects.ships.ShipGeneric))
                {
                    if(((com.maddox.il2.objects.ships.ShipGeneric)tdestroy.actor).zutiGetDying() > 0)
                    {
                        com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdestroy.nameTarget);
                        arraylist.add(target);
                    }
                    continue;
                }
                if(tdestroy.alive == -1)
                {
                    world.targetsGuard.zutiTargetNamesToRemove.add(tdestroy.nameTarget);
                    arraylist.add(target);
                    continue;
                }
                if(tdestroy.actor != null && tdestroy.actor.getDiedFlag())
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdestroy.nameTarget);
                    arraylist.add(target);
                    continue;
                }
                if(!flag)
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdestroy.nameTarget);
                    arraylist.add(target);
                }
                continue;
            }
            if(target instanceof com.maddox.il2.ai.TDestroyGround)
            {
                com.maddox.il2.ai.TDestroyGround tdestroyground = (com.maddox.il2.ai.TDestroyGround)target;
                if(tdestroyground.getDiedFlag())
                {
                    com.maddox.JGP.Point3d point3d = tdestroyground.pos.getAbsPoint();
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(point3d.x, point3d.y);
                    arraylist.add(target);
                }
                continue;
            }
            if(target instanceof com.maddox.il2.ai.TDestroyBridge)
            {
                com.maddox.il2.ai.TDestroyBridge tdestroybridge = (com.maddox.il2.ai.TDestroyBridge)target;
                if(tdestroybridge.actor.getDiedFlag())
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdestroybridge.actor.name());
                    arraylist.add(target);
                }
                continue;
            }
            if(target instanceof com.maddox.il2.ai.TInspect)
            {
                com.maddox.il2.ai.TInspect tinspect = (com.maddox.il2.ai.TInspect)target;
                if(!tinspect.getDiedFlag())
                    continue;
                if(tinspect.nameTarget == null || tinspect.nameTarget.trim().length() < 1)
                {
                    com.maddox.JGP.Point3d point3d1 = tinspect.pos.getAbsPoint();
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(point3d1.x, point3d1.y);
                    arraylist.add(target);
                } else
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tinspect.nameTarget);
                    arraylist.add(target);
                }
                continue;
            }
            if(target instanceof com.maddox.il2.ai.TEscort)
            {
                com.maddox.il2.ai.TEscort tescort = (com.maddox.il2.ai.TEscort)target;
                boolean flag1 = tescort.checkActor();
                if(tescort.actor != null && (tescort.actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    if(((com.maddox.il2.objects.ships.BigshipGeneric)tescort.actor).zutiGetDying() > 0)
                    {
                        com.maddox.il2.game.ZutiSupportMethods.removeTarget(tescort.nameTarget);
                        arraylist.add(target);
                    }
                    continue;
                }
                if(tescort.actor != null && (tescort.actor instanceof com.maddox.il2.objects.ships.ShipGeneric))
                {
                    if(((com.maddox.il2.objects.ships.ShipGeneric)tescort.actor).zutiGetDying() > 0)
                    {
                        com.maddox.il2.game.ZutiSupportMethods.removeTarget(tescort.nameTarget);
                        arraylist.add(target);
                    }
                    continue;
                }
                if(tescort.alive == -1)
                {
                    world.targetsGuard.zutiTargetNamesToRemove.add(tescort.nameTarget);
                    arraylist.add(target);
                    continue;
                }
                if(tescort.actor != null && tescort.actor.getDiedFlag())
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tescort.nameTarget);
                    arraylist.add(target);
                    continue;
                }
                if(!flag1)
                {
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(tescort.nameTarget);
                    arraylist.add(target);
                }
                continue;
            }
            if(target instanceof com.maddox.il2.ai.TDefenceGround)
            {
                com.maddox.il2.ai.TDefenceGround tdefenceground = (com.maddox.il2.ai.TDefenceGround)target;
                if(tdefenceground.getDiedFlag())
                {
                    com.maddox.JGP.Point3d point3d2 = tdefenceground.pos.getAbsPoint();
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(point3d2.x, point3d2.y);
                    arraylist.add(target);
                }
                continue;
            }
            if(!(target instanceof com.maddox.il2.ai.TDefenceBridge))
                continue;
            com.maddox.il2.ai.TDefenceBridge tdefencebridge = (com.maddox.il2.ai.TDefenceBridge)target;
            if(tdefencebridge.actor.getDiedFlag())
            {
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(tdefencebridge.actor.name());
                arraylist.add(target);
            }
        }

        i = arraylist.size();
        for(int k = 0; k < i; k++)
            list.remove((com.maddox.il2.ai.Target)arraylist.get(k));

    }

    public static void staticActorDied(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.ai.World world = com.maddox.il2.ai.World.cur();
        if(world == null || world.targetsGuard == null)
            return;
        java.util.List list = world.targetsGuard.zutiGetTargets();
        int i = list.size();
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)list.get(j);
            if(target instanceof com.maddox.il2.ai.TDestroyGround)
            {
                com.maddox.il2.ai.TDestroyGround tdestroyground = (com.maddox.il2.ai.TDestroyGround)target;
                if(com.maddox.il2.ai.ZutiTargetsSupportMethods.checkIfActorPartOfTDestroyGround(actor, tdestroyground))
                {
                    com.maddox.JGP.Point3d point3d = tdestroyground.pos.getAbsPoint();
                    com.maddox.il2.game.ZutiSupportMethods.removeTarget(point3d.x, point3d.y);
                    world.targetsGuard.zutiTargetPosToRemove.add(target);
                }
                continue;
            }
            if(!(target instanceof com.maddox.il2.ai.TDefenceGround))
                continue;
            com.maddox.il2.ai.TDefenceGround tdefenceground = (com.maddox.il2.ai.TDefenceGround)target;
            if(com.maddox.il2.ai.ZutiTargetsSupportMethods.checkIfActorPartOfTDefenceGround(actor, tdefenceground))
            {
                com.maddox.JGP.Point3d point3d1 = tdefenceground.pos.getAbsPoint();
                com.maddox.il2.game.ZutiSupportMethods.removeTarget(point3d1.x, point3d1.y);
                world.targetsGuard.zutiTargetPosToRemove.add(target);
            }
        }

    }

    public static com.maddox.JGP.Point3d getNearestTarget(com.maddox.JGP.Point3d point3d, boolean flag)
    {
        com.maddox.il2.ai.World world = com.maddox.il2.ai.World.cur();
        if(world == null || world.targetsGuard == null)
            return null;
        java.util.List list = world.targetsGuard.zutiGetTargets();
        com.maddox.JGP.Point3d point3d1 = null;
        double d = -1D;
        int i = list.size();
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        Object obj3 = null;
        com.maddox.il2.ai.TDestroy tdestroy = null;
        Object obj4 = null;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)list.get(j);
            if(!target.isAlive())
                continue;
            if(flag && (target instanceof com.maddox.il2.ai.TDestroyGround))
            {
                com.maddox.JGP.Point3d point3d2 = target.pos.getAbsPoint();
                double d1 = java.lang.Math.pow(point3d.x - point3d2.x, 2D) + java.lang.Math.pow(point3d.y - point3d2.y, 2D);
                if(d1 < d || d < 0.0D)
                {
                    d = d1;
                    point3d1 = point3d2;
                }
            }
            if(flag && (target instanceof com.maddox.il2.ai.TDestroyBridge))
            {
                com.maddox.il2.ai.TDestroyBridge tdestroybridge = (com.maddox.il2.ai.TDestroyBridge)target;
                if(tdestroybridge.actor == null)
                    continue;
                com.maddox.JGP.Point3d point3d3 = tdestroybridge.actor.pos.getAbsPoint();
                double d2 = java.lang.Math.pow(point3d.x - point3d3.x, 2D) + java.lang.Math.pow(point3d.y - point3d3.y, 2D);
                if(d2 < d || d < 0.0D)
                {
                    d = d2;
                    point3d1 = point3d3;
                }
            }
            if(flag && (target instanceof com.maddox.il2.ai.TDestroy))
            {
                tdestroy = (com.maddox.il2.ai.TDestroy)target;
                if(tdestroy.actor == null)
                    continue;
                if(tdestroy.actor instanceof com.maddox.il2.ai.Wing)
                {
                    com.maddox.JGP.Point3d point3d4 = com.maddox.il2.ai.ZutiTargetsSupportMethods.getPointFromWing((com.maddox.il2.ai.Wing)tdestroy.actor);
                    if(point3d4 == null)
                        continue;
                    double d3 = java.lang.Math.pow(point3d.x - point3d4.x, 2D) + java.lang.Math.pow(point3d.y - point3d4.y, 2D);
                    if(d3 < d || d < 0.0D)
                    {
                        d = d3;
                        point3d1 = point3d4;
                    }
                } else
                {
                    com.maddox.JGP.Point3d point3d5 = tdestroy.actor.pos.getAbsPoint();
                    double d4 = java.lang.Math.pow(point3d.x - point3d5.x, 2D) + java.lang.Math.pow(point3d.y - point3d5.y, 2D);
                    if(d4 < d || d < 0.0D)
                    {
                        d = d4;
                        point3d1 = point3d5;
                    }
                }
            }
            if(!flag && (target instanceof com.maddox.il2.ai.TDefenceGround))
            {
                com.maddox.JGP.Point3d point3d6 = target.pos.getAbsPoint();
                double d5 = java.lang.Math.pow(point3d.x - point3d6.x, 2D) + java.lang.Math.pow(point3d.y - point3d6.y, 2D);
                if(d5 < d || d < 0.0D)
                {
                    d = d5;
                    point3d1 = point3d6;
                }
            }
            if(!flag && (target instanceof com.maddox.il2.ai.TDefenceBridge))
            {
                com.maddox.il2.ai.TDefenceBridge tdefencebridge = (com.maddox.il2.ai.TDefenceBridge)target;
                if(tdefencebridge.actor == null)
                    continue;
                com.maddox.JGP.Point3d point3d7 = tdestroy.actor.pos.getAbsPoint();
                double d6 = java.lang.Math.pow(point3d.x - point3d7.x, 2D) + java.lang.Math.pow(point3d.y - point3d7.y, 2D);
                if(d6 < d || d < 0.0D)
                {
                    d = d6;
                    point3d1 = point3d7;
                }
            }
            if(flag || !(target instanceof com.maddox.il2.ai.TEscort))
                continue;
            com.maddox.il2.ai.TEscort tescort = (com.maddox.il2.ai.TEscort)target;
            tescort.checkActor();
            if(tescort.actor == null)
                continue;
            if(tescort.actor instanceof com.maddox.il2.ai.Wing)
            {
                com.maddox.JGP.Point3d point3d8 = com.maddox.il2.ai.ZutiTargetsSupportMethods.getPointFromWing((com.maddox.il2.ai.Wing)tdestroy.actor);
                if(point3d8 == null)
                    continue;
                double d7 = java.lang.Math.pow(point3d.x - point3d8.x, 2D) + java.lang.Math.pow(point3d.y - point3d8.y, 2D);
                if(d7 < d || d < 0.0D)
                {
                    d = d7;
                    point3d1 = point3d8;
                }
                continue;
            }
            com.maddox.JGP.Point3d point3d9 = tdestroy.actor.pos.getAbsPoint();
            double d8 = java.lang.Math.pow(point3d.x - point3d9.x, 2D) + java.lang.Math.pow(point3d.y - point3d9.y, 2D);
            if(d8 < d || d < 0.0D)
            {
                d = d8;
                point3d1 = point3d9;
            }
        }

        return point3d1;
    }

    public static com.maddox.JGP.Point3d getPointFromWing(com.maddox.il2.ai.Wing wing)
    {
        com.maddox.JGP.Point3d point3d = null;
        int i = wing.airc.length;
        int j = 0;
        do
        {
            if(j >= i)
                break;
            if(wing.airc[j] != null && !wing.airc[j].getDiedFlag())
            {
                point3d = wing.airc[j].pos.getAbsPoint();
                break;
            }
            j++;
        } while(true);
        return point3d;
    }

    public static boolean isOverTarget(double d, double d1)
    {
        com.maddox.il2.ai.World world = com.maddox.il2.ai.World.cur();
        if(world == null || world.targetsGuard == null)
            return false;
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        java.util.List list = world.targetsGuard.zutiGetTargets();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Target target = (com.maddox.il2.ai.Target)list.get(j);
            if(target instanceof com.maddox.il2.ai.TDestroyGround)
            {
                com.maddox.il2.ai.TDestroyGround tdestroyground = (com.maddox.il2.ai.TDestroyGround)target;
                if(!tdestroyground.getDiedFlag() && tdestroyground.zutiIsOverTarged(d, d1))
                    return true;
                continue;
            }
            if(!(target instanceof com.maddox.il2.ai.TDefenceGround))
                continue;
            com.maddox.il2.ai.TDefenceGround tdefenceground = (com.maddox.il2.ai.TDefenceGround)target;
            if(!tdefenceground.getDiedFlag() && tdefenceground.zutiIsOverTarged(d, d1))
                return true;
        }

        return false;
    }
}
