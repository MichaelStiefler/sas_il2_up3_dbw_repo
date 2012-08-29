// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   War.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.NearestTargets;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.List;

// Referenced classes of package com.maddox.il2.ai:
//            Chief, World, RangeRandom

public class War
{

    public static com.maddox.il2.ai.War cur()
    {
        return com.maddox.il2.ai.World.cur().war;
    }

    public boolean isActive()
    {
        if(!com.maddox.il2.game.Mission.isPlaying())
            return false;
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            return false;
        if(com.maddox.il2.game.Mission.isSingle())
            return true;
        return com.maddox.il2.game.Mission.isServer() && com.maddox.il2.game.Mission.isCoop();
    }

    public void onActorDied(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(!isActive())
            return;
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && (((com.maddox.il2.objects.air.Aircraft)actor).FM instanceof com.maddox.il2.ai.air.Maneuver))
        {
            com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM;
            if(maneuver.Group != null)
            {
                maneuver.Group.delAircraft((com.maddox.il2.objects.air.Aircraft)actor);
                maneuver.Group = null;
            }
        }
    }

    public void missionLoaded()
    {
    }

    public void resetGameCreate()
    {
        curArmy = 0;
        curGroup = 0;
    }

    public void resetGameClear()
    {
        for(int i = 0; i < 2; i++)
            while(Groups[i] != null) 
            {
                Groups[i].G.release();
                com.maddox.il2.ai.air.AirGroupList.delAirGroup(Groups, i, Groups[i].G);
            }

    }

    public War()
    {
    }

    public void interpolateTick()
    {
        if(!isActive())
            return;
        try
        {
            if(com.maddox.rts.Time.tickCounter() % 4 == 0)
            {
                checkCollisionForAircraft();
                if(com.maddox.rts.Time.tickCounter() % 32 == 0)
                {
                    checkGroupsContact();
                    if(com.maddox.rts.Time.tickCounter() % 64 == 0)
                        delEmptyGroups();
                }
                upgradeGroups();
            }
            formationUpdate();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void upgradeGroups()
    {
        int i = com.maddox.il2.ai.air.AirGroupList.length(Groups[curArmy]);
        if(i > curGroup)
        {
            com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[curArmy], curGroup).update();
        } else
        {
            curArmy++;
            if(curArmy > 1)
                curArmy = 0;
            curGroup = 0;
            return;
        }
        curGroup++;
    }

    private void formationUpdate()
    {
        for(int i = 0; i < 2; i++)
            if(Groups[i] != null)
            {
                int j = com.maddox.il2.ai.air.AirGroupList.length(Groups[i]);
                for(int k = 0; k < j; k++)
                    com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[i], k).formationUpdate();

            }

    }

    private void checkGroupsContact()
    {
        int i = com.maddox.il2.ai.air.AirGroupList.length(Groups[0]);
        int j = com.maddox.il2.ai.air.AirGroupList.length(Groups[1]);
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[0], k);
            for(int l = 0; l < j; l++)
            {
                com.maddox.il2.ai.air.AirGroup airgroup1 = com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[1], l);
                tmpV.sub(airgroup.Pos, airgroup1.Pos);
                if(tmpV.lengthSquared() < 400000000D && airgroup.groupsInContact(airgroup1))
                {
                    if(!com.maddox.il2.ai.air.AirGroupList.groupInList(airgroup.enemies[0], airgroup1))
                    {
                        com.maddox.il2.ai.air.AirGroupList.addAirGroup(airgroup.enemies, 0, airgroup1);
                        if(airgroup.airc[0] != null && airgroup1.airc[0] != null)
                            com.maddox.il2.objects.sounds.Voice.speakEnemyDetected(airgroup.airc[0], airgroup1.airc[0]);
                        airgroup.setEnemyFighters();
                    }
                    if(!com.maddox.il2.ai.air.AirGroupList.groupInList(airgroup1.enemies[0], airgroup))
                    {
                        com.maddox.il2.ai.air.AirGroupList.addAirGroup(airgroup1.enemies, 0, airgroup);
                        if(airgroup.airc[0] != null && airgroup1.airc[0] != null)
                            com.maddox.il2.objects.sounds.Voice.speakEnemyDetected(airgroup1.airc[0], airgroup.airc[0]);
                        airgroup1.setEnemyFighters();
                    }
                } else
                {
                    if(com.maddox.il2.ai.air.AirGroupList.groupInList(airgroup.enemies[0], airgroup1))
                    {
                        com.maddox.il2.ai.air.AirGroupList.delAirGroup(airgroup.enemies, 0, airgroup1);
                        airgroup.setEnemyFighters();
                    }
                    if(com.maddox.il2.ai.air.AirGroupList.groupInList(airgroup1.enemies[0], airgroup))
                    {
                        com.maddox.il2.ai.air.AirGroupList.delAirGroup(airgroup1.enemies, 0, airgroup);
                        airgroup1.setEnemyFighters();
                    }
                }
            }

        }

    }

    private void delEmptyGroups()
    {
        int i = com.maddox.il2.ai.air.AirGroupList.length(Groups[0]);
        int j = com.maddox.il2.ai.air.AirGroupList.length(Groups[1]);
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup = com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[0], k);
            if(airgroup != null && airgroup.nOfAirc == 0)
            {
                airgroup.release();
                com.maddox.il2.ai.air.AirGroupList.delAirGroup(Groups, 0, airgroup);
            }
        }

        for(int l = 0; l < j; l++)
        {
            com.maddox.il2.ai.air.AirGroup airgroup1 = com.maddox.il2.ai.air.AirGroupList.getGroup(Groups[1], l);
            if(airgroup1 != null && airgroup1.nOfAirc == 0)
            {
                airgroup1.release();
                com.maddox.il2.ai.air.AirGroupList.delAirGroup(Groups, 1, airgroup1);
            }
        }

    }

    private void checkCollisionForAircraft()
    {
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int k = list.size();
        for(int i = 0; i < k; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(i);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)actor).FM;
                for(int j = i + 1; j < k; j++)
                {
                    com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)list.get(j);
                    if(i != j && (actor1 instanceof com.maddox.il2.objects.air.Aircraft))
                    {
                        com.maddox.il2.fm.FlightModel flightmodel1 = ((com.maddox.il2.objects.air.Aircraft)actor1).FM;
                        if((flightmodel instanceof com.maddox.il2.ai.air.Pilot) && (flightmodel1 instanceof com.maddox.il2.ai.air.Pilot))
                        {
                            float f = (float)flightmodel.Loc.distanceSquared(flightmodel1.Loc);
                            if(f <= 1E+007F)
                            {
                                if(flightmodel.actor.getArmy() != flightmodel1.actor.getArmy())
                                {
                                    if(flightmodel instanceof com.maddox.il2.fm.RealFlightModel)
                                        com.maddox.il2.ai.War.testAsDanger(flightmodel, flightmodel1);
                                    if(flightmodel1 instanceof com.maddox.il2.fm.RealFlightModel)
                                        com.maddox.il2.ai.War.testAsDanger(flightmodel1, flightmodel);
                                }
                                Ve.sub(flightmodel.Loc, flightmodel1.Loc);
                                float f1 = (float)Ve.length();
                                Ve.normalize();
                                if(flightmodel.actor.getArmy() == flightmodel1.actor.getArmy())
                                {
                                    tmpV.set(Ve);
                                    flightmodel1.Or.transformInv(tmpV);
                                    if(tmpV.x > 0.0D && tmpV.y > -0.10000000000000001D && tmpV.y < 0.10000000000000001D && tmpV.z > -0.10000000000000001D && tmpV.z < 0.10000000000000001D)
                                        ((com.maddox.il2.ai.air.Maneuver)flightmodel1).setShotAtFriend(f1);
                                    tmpV.set(Ve);
                                    tmpV.scale(-1D);
                                    flightmodel.Or.transformInv(tmpV);
                                    if(tmpV.x > 0.0D && tmpV.y > -0.10000000000000001D && tmpV.y < 0.10000000000000001D && tmpV.z > -0.10000000000000001D && tmpV.z < 0.10000000000000001D)
                                        ((com.maddox.il2.ai.air.Maneuver)flightmodel).setShotAtFriend(f1);
                                }
                                if(f <= 20000F)
                                {
                                    float f2 = (flightmodel.actor.collisionR() + flightmodel1.actor.collisionR()) * 1.5F;
                                    f1 -= f2;
                                    Vtarg.sub(flightmodel1.Vwld, flightmodel.Vwld);
                                    Vtarg.scale(1.5D);
                                    float f3 = (float)Vtarg.length();
                                    if(f3 >= f1)
                                    {
                                        Vtarg.normalize();
                                        Vtarg.scale(f1);
                                        Ve.scale(Vtarg.dot(Ve));
                                        Vtarg.sub(Ve);
                                        if(Vtarg.length() < (double)f2 || f1 < 0.0F)
                                        {
                                            if(((com.maddox.il2.objects.air.Aircraft)actor).FM instanceof com.maddox.il2.ai.air.Pilot)
                                                ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor).FM).setStrikeEmer(flightmodel1);
                                            if(((com.maddox.il2.objects.air.Aircraft)actor1).FM instanceof com.maddox.il2.ai.air.Pilot)
                                                ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.objects.air.Aircraft)actor1).FM).setStrikeEmer(flightmodel);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

    }

    public static void testAsDanger(com.maddox.il2.fm.FlightModel flightmodel, com.maddox.il2.fm.FlightModel flightmodel1)
    {
        if(flightmodel.actor instanceof com.maddox.il2.objects.air.TypeTransport)
            return;
        Ve.sub(flightmodel1.Loc, flightmodel.Loc);
        flightmodel.Or.transformInv(Ve);
        if(Ve.x > 0.0D)
        {
            float f = (float)Ve.length();
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)flightmodel1).incDangerAggressiveness(4, (float)Ve.x, f, flightmodel);
        }
    }

    public static com.maddox.il2.objects.air.Aircraft getNearestFriend(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        return com.maddox.il2.ai.War.getNearestFriend(aircraft, 10000F);
    }

    public static com.maddox.il2.objects.air.Aircraft getNearestFriend(com.maddox.il2.objects.air.Aircraft aircraft, float f)
    {
        com.maddox.JGP.Point3d point3d = aircraft.pos.getAbsPoint();
        double d = f * f;
        int i = aircraft.getArmy();
        com.maddox.il2.objects.air.Aircraft aircraft1 = null;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor != aircraft && actor.getArmy() == i)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                if(d1 < d)
                {
                    aircraft1 = (com.maddox.il2.objects.air.Aircraft)actor;
                    d = d1;
                }
            }
        }

        return aircraft1;
    }

    public static com.maddox.il2.objects.air.Aircraft getNearestFriendAtPoint(com.maddox.JGP.Point3d point3d, com.maddox.il2.objects.air.Aircraft aircraft, float f)
    {
        double d = f * f;
        int i = aircraft.getArmy();
        com.maddox.il2.objects.air.Aircraft aircraft1 = null;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.getArmy() == i)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                if(d1 < d)
                {
                    aircraft1 = (com.maddox.il2.objects.air.Aircraft)actor;
                    d = d1;
                }
            }
        }

        return aircraft1;
    }

    public static com.maddox.il2.objects.air.Aircraft getNearestFriendlyFighter(com.maddox.il2.objects.air.Aircraft aircraft, float f)
    {
        double d = f * f;
        com.maddox.JGP.Point3d point3d = aircraft.pos.getAbsPoint();
        int i = aircraft.getArmy();
        com.maddox.il2.objects.air.Aircraft aircraft1 = null;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.objects.air.Aircraft aircraft2 = (com.maddox.il2.objects.air.Aircraft)actor;
                if(aircraft2 != aircraft && aircraft2.getArmy() == i && aircraft2.getWing() != aircraft.getWing() && (aircraft2 instanceof com.maddox.il2.objects.air.TypeFighter))
                {
                    com.maddox.JGP.Point3d point3d1 = aircraft2.pos.getAbsPoint();
                    double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                    if(d1 < d)
                    {
                        aircraft1 = aircraft2;
                        d = d1;
                    }
                }
            }
        }

        return aircraft1;
    }

    public static com.maddox.il2.objects.air.Aircraft getNearestEnemy(com.maddox.il2.objects.air.Aircraft aircraft, float f)
    {
        double d = f * f;
        com.maddox.JGP.Point3d point3d = aircraft.pos.getAbsPoint();
        int i = aircraft.getArmy();
        com.maddox.il2.objects.air.Aircraft aircraft1 = null;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int j = list.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(k);
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.getArmy() != i)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                double d1 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                if(d1 < d)
                {
                    aircraft1 = (com.maddox.il2.objects.air.Aircraft)actor;
                    d = d1;
                }
            }
        }

        return aircraft1;
    }

    public static com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.il2.engine.Actor actor, int i, float f)
    {
        return com.maddox.il2.ai.air.NearestTargets.getEnemy(0, i, actor.pos.getAbsPoint(), f, actor.getArmy());
    }

    public static com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.il2.engine.Actor actor, int i, float f, int j)
    {
        return com.maddox.il2.ai.air.NearestTargets.getEnemy(j, i, actor.pos.getAbsPoint(), f, actor.getArmy());
    }

    public static com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.il2.engine.Actor actor, int i, float f, com.maddox.JGP.Point3d point3d)
    {
        return com.maddox.il2.ai.air.NearestTargets.getEnemy(0, i, point3d, f, actor.getArmy());
    }

    public static com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.il2.engine.Actor actor, int i, com.maddox.JGP.Point3d point3d, float f)
    {
        return com.maddox.il2.ai.air.NearestTargets.getEnemy(i, 16, point3d, f, actor.getArmy());
    }

    public static com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, float f)
    {
        return com.maddox.il2.ai.air.NearestTargets.getEnemy(0, 16, point3d, f, actor.getArmy());
    }

    public static com.maddox.il2.engine.Actor GetNearestFromChief(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(actor1))
            return null;
        com.maddox.il2.engine.Actor actor2 = null;
        if((actor1 instanceof com.maddox.il2.ai.Chief) || (actor1 instanceof com.maddox.il2.objects.bridges.Bridge))
        {
            int i = actor1.getOwnerAttachedCount();
            if(i < 1)
                return null;
            actor2 = (com.maddox.il2.engine.Actor)actor1.getOwnerAttached(0);
            double d = actor.pos.getAbsPoint().distance(actor2.pos.getAbsPoint());
            for(int j = 1; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)actor1.getOwnerAttached(j);
                double d1 = actor.pos.getAbsPoint().distance(actor3.pos.getAbsPoint());
                if(d1 < d)
                {
                    double d2 = d;
                    actor2 = actor3;
                }
            }

        }
        return actor2;
    }

    public static com.maddox.il2.engine.Actor GetRandomFromChief(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(!com.maddox.il2.engine.Actor.isAlive(actor1))
            return null;
        if((actor1 instanceof com.maddox.il2.ai.Chief) || (actor1 instanceof com.maddox.il2.objects.bridges.Bridge))
        {
            int i = actor1.getOwnerAttachedCount();
            if(i < 1)
                return null;
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)actor1.getOwnerAttached(com.maddox.il2.ai.World.Rnd().nextInt(0, i - 1));
                if(com.maddox.il2.engine.Actor.isValid(actor2) && actor2.isAlive())
                    return actor2;
            }

            for(int k = 0; k < i; k++)
            {
                com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)actor1.getOwnerAttached(k);
                if(com.maddox.il2.engine.Actor.isValid(actor3) && actor3.isAlive())
                    return actor3;
            }

        }
        return actor1;
    }

    public static com.maddox.il2.objects.air.Aircraft GetNearestEnemyAircraft(com.maddox.il2.engine.Actor actor, float f, int i)
    {
        com.maddox.il2.engine.Actor actor1 = com.maddox.il2.ai.War.GetNearestEnemy(actor, -1, f, i);
        if(actor1 != null)
        {
            return (com.maddox.il2.objects.air.Aircraft)actor1;
        } else
        {
            com.maddox.il2.engine.Actor actor2 = com.maddox.il2.ai.War.GetNearestEnemy(actor, -1, f, 9);
            return (com.maddox.il2.objects.air.Aircraft)actor2;
        }
    }

    public static final int TICK_DIV4 = 4;
    public static final int TICK_DIV8 = 8;
    public static final int TICK_DIV16 = 16;
    public static final int TICK_DIV32 = 32;
    public static final int ARMY_NUM = 2;
    public static com.maddox.il2.ai.air.AirGroupList Groups[] = new com.maddox.il2.ai.air.AirGroupList[2];
    private static int curArmy = 0;
    private static int curGroup = 0;
    private static com.maddox.JGP.Vector3d tmpV = new Vector3d();
    private static com.maddox.JGP.Vector3d Ve = new Vector3d();
    private static com.maddox.JGP.Vector3d Vtarg = new Vector3d();

}
