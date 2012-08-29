// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ChiefGround.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.MsgDreamListener;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Referenced classes of package com.maddox.il2.ai.ground:
//            RoadPath, UnitInPackedForm, UnitInterface, Predator, 
//            Prey, UnitSpawn, RoadPart, UnitMove, 
//            RoadSegment, UnitData, NearestEnemies, StaticObstacle

public class ChiefGround extends com.maddox.il2.ai.Chief
    implements com.maddox.il2.engine.MsgDreamListener
{
    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(waitTime > 0L && com.maddox.rts.Time.tick() >= waitTime)
                waitTime = 0L;
            if(unitsPacked.size() > 0)
            {
                moveChiefPacked(com.maddox.rts.Time.tickLenFs());
                return true;
            }
            if(--stateCountdown <= 0)
            {
                int i = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(300F, 500F));
                switch(curState)
                {
                default:
                    break;

                case 0: // '\0'
                    stateCountdown = i;
                    break;

                case 1: // '\001'
                    curState = 0;
                    stateCountdown = i;
                    reformIfNeed(false);
                    break;

                case 2: // '\002'
                    if(shift_SwitchToBrakeWhenDone)
                    {
                        curState = 3;
                        stateCountdown = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(38F, 65F));
                    } else
                    {
                        curState = 0;
                        stateCountdown = i;
                    }
                    reformIfNeed(false);
                    break;

                case 3: // '\003'
                    curState = 0;
                    stateCountdown = i;
                    reformIfNeed(true);
                    break;
                }
            }
            if(--posCountdown <= 0)
                recomputeAveragePosition();
            return true;
        }

        Move()
        {
        }
    }


    private static final void ERR_NO_UNITS(java.lang.String s)
    {
        java.lang.String s1 = "INTERNAL ERROR IN ChiefGround." + s + "(): No units";
        java.lang.System.out.println(s1);
        throw new ActorException(s1);
    }

    private static final void ERR(java.lang.String s)
    {
        java.lang.String s1 = "INTERNAL ERROR IN ChiefGround: " + s;
        java.lang.System.out.println(s1);
        throw new ActorException(s1);
    }

    private static final void ConstructorFailure()
    {
        throw new ActorException();
    }

    public boolean isPacked()
    {
        return unitsPacked == null || unitsPacked.size() > 0;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private void SetPosition(com.maddox.JGP.Point3d point3d, float f)
    {
        pos.getAbs(tmpp);
        pos.setAbs(point3d);
        estim_speed.sub(point3d, tmpp);
        if(f <= 0.0001F)
            estim_speed.set(0.0D, 0.0D, 0.0D);
        else
            estim_speed.scale(1.0D / (double)f);
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        double d = estim_speed.length();
        if(vector3d == null)
            return d;
        vector3d.set(estim_speed);
        if(d <= 0.0001D)
            vector3d.set(0.0D, 0.0D, 0.0001D);
        return d;
    }

    public ChiefGround(java.lang.String s, int i, com.maddox.rts.SectFile sectfile, java.lang.String s1, com.maddox.rts.SectFile sectfile1, java.lang.String s2)
    {
        tmpp = new Point3d();
        try
        {
            road = new RoadPath(sectfile1, s2);
            road.RegisterTravellerToBridges(this);
            setName(s);
            setArmy(i);
            chiefSeg = 0;
            chiefAlong = 0.0D;
            minSeg = -1;
            maxSeg = -1;
            waitTime = 0L;
            curForm = null;
            minGrabSeg = maxGrabSeg = -1;
            pos = new ActorPosMove(this);
            pos.setAbs(road.get(0).start);
            pos.reset();
            posCountdown = 0;
            estim_speed = new Vector3d(0.0D, 0.0D, 0.0D);
            int j = sectfile.sectionIndex(s1);
            int k = sectfile.vars(j);
            if(k <= 0)
                throw new ActorException("ChiefGround: Missing units");
            unitsPacked = new ArrayList();
            for(int l = 0; l < k; l++)
            {
                java.lang.String s3 = sectfile.var(j, l);
                java.lang.Object obj = com.maddox.rts.Spawn.get(s3);
                if(obj == null)
                    throw new ActorException("ChiefGround: Unknown type of object (" + s3 + ")");
                int i1 = com.maddox.rts.Finger.Int(s3);
                int j1 = 0;
                unitsPacked.add(new UnitInPackedForm(l, i1, j1));
            }

            withPreys = false;
            unpackUnits();
            recomputeAveragePosition();
            if(!interpEnd("move"))
                interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("ChiefGround creation failure:");
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
            com.maddox.il2.ai.ground.ChiefGround.ConstructorFailure();
        }
    }

    public int getCodeOfBridgeSegment(com.maddox.il2.ai.ground.UnitInterface unitinterface)
    {
        int i = unitinterface.GetUnitData().segmentIdx;
        return road.getCodeOfBridgeSegment(i);
    }

    public void BridgeSegmentDestroyed(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        boolean flag = road.MarkDestroyedSegments(i, j);
        if(!flag)
            return;
        if(unitsPacked.size() > 0)
            return;
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("BridgeSegmentDestroyed");
        for(int k = 0; k < aobj.length; k++)
        {
            int l = ((com.maddox.il2.ai.ground.UnitInterface)aobj[k]).GetUnitData().segmentIdx;
            if(road.segIsWrongOrDamaged(l))
                ((com.maddox.il2.ai.ground.UnitInterface)aobj[k]).absoluteDeath(actor);
        }

    }

    private void recomputeAveragePosition()
    {
        if(unitsPacked.size() > 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("average position when PACKED");
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("recomputeAveragePosition");
        int i = aobj.length;
        int j = 10000;
        int k = -10000;
        for(int l = 0; l < i; l++)
        {
            int i1 = ((com.maddox.il2.ai.ground.UnitInterface)aobj[l]).GetUnitData().segmentIdx;
            if(i1 < j)
                j = i1;
            if(i1 > k)
                k = i1;
        }

        com.maddox.JGP.Point3d point3d = new Point3d(((com.maddox.il2.engine.Actor)aobj[0]).pos.getAbsPoint());
        double d = com.maddox.il2.ai.World.land().HQ(point3d.x, point3d.y);
        if(point3d.z < d)
            point3d.z = d;
        SetPosition(point3d, 1.05F);
        posCountdown = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.2F));
        road.unlockBridges(this, minGrabSeg, maxGrabSeg);
        minGrabSeg = j;
        maxGrabSeg = k;
        road.lockBridges(this, minGrabSeg, maxGrabSeg);
    }

    private void computePositionForPacked()
    {
        if(unitsPacked.size() > 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("advanced position when PACKED");
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        double d = 999999.90000000002D;
        chiefSeg = minSeg;
        for(int i = minSeg; i <= maxSeg; i++)
        {
            double d1 = road.get(i).computePosAlong(point3d);
            double d2 = road.get(i).computePosSide(point3d);
            double d3 = d1 * d1 + d2 * d2;
            if(d >= d3)
            {
                d = d3;
                chiefSeg = i;
            }
        }

        chiefAlong = road.get(chiefSeg).computePosAlong_Fit(point3d);
        SetPosition(road.get(chiefSeg).computePos_Fit(chiefAlong, 0.0D, 0.0F), 0.0F);
        posCountdown = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.2F));
        road.unlockBridges(this, minGrabSeg, maxGrabSeg);
        minGrabSeg = maxGrabSeg = chiefSeg;
        road.lockBridges(this, minGrabSeg, maxGrabSeg);
    }

    private void recomputeChiefWaitTime(int i)
    {
        long l = road.getMaxWaitTime(i, i);
        long l1 = com.maddox.rts.Time.tick();
        if(l > l1 && l > waitTime)
            waitTime = l;
    }

    private void recomputeMinMaxSegments()
    {
        if(unitsPacked.size() > 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("min/max seg when PACKED");
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("recomputeMinMaxSegments");
        int i = aobj.length;
        int j = 10000;
        int k = -10000;
        for(int l = 0; l < i; l++)
        {
            int i1 = ((com.maddox.il2.ai.ground.UnitInterface)aobj[l]).GetUnitData().segmentIdx;
            if(i1 < j)
                j = i1;
            if(i1 > k)
                k = i1;
        }

        road.unlockBridges(this, minGrabSeg, maxGrabSeg);
        minGrabSeg = j;
        maxGrabSeg = k;
        road.lockBridges(this, minGrabSeg, maxGrabSeg);
        if(j == minSeg && k == maxSeg)
            return;
        long l1 = road.getMaxWaitTime(java.lang.Math.min(j, minSeg), k);
        long l2 = com.maddox.rts.Time.tick();
        if(l1 > l2 && l1 > waitTime)
            waitTime = l1;
        minSeg = j;
        maxSeg = k;
    }

    private void recomputeUnitsProperties_Packed()
    {
        int i = unitsPacked.size();
        if(i <= 0)
        {
            recomputeUnitsProperties();
            return;
        }
        groupSpeed = 100000F;
        maxSpace = -1F;
        weaponsMask = 0;
        hitbyMask = 0;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.ground.UnitInPackedForm unitinpackedform = (com.maddox.il2.ai.ground.UnitInPackedForm)unitsPacked.get(j);
            float f = unitinpackedform.SPEED_AVERAGE;
            if(f < groupSpeed)
                groupSpeed = f;
            f = unitinpackedform.BEST_SPACE;
            if(f > maxSpace)
                maxSpace = f;
            weaponsMask |= unitinpackedform.WEAPONS_MASK;
            hitbyMask |= unitinpackedform.HITBY_MASK;
        }

        if(groupSpeed < 0.001F || groupSpeed > 10000F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("group speed is too small");
        if(maxSpace <= 0.01F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("maxSpace is too small");
    }

    public void recomputeUnitsProperties()
    {
        if(unitsPacked.size() > 0)
        {
            recomputeUnitsProperties_Packed();
            return;
        }
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("recomputeUnitsProperties");
        int i = aobj.length;
        groupSpeed = 10000F;
        maxSpace = -1F;
        weaponsMask = 0;
        hitbyMask = 0;
        withPreys = false;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.ground.UnitInterface unitinterface = (com.maddox.il2.ai.ground.UnitInterface)aobj[j];
            float f = unitinterface.SpeedAverage();
            if(f < groupSpeed)
                groupSpeed = f;
            f = unitinterface.BestSpace();
            if(f > maxSpace)
                maxSpace = f;
            if(unitinterface instanceof com.maddox.il2.ai.ground.Predator)
                weaponsMask |= ((com.maddox.il2.ai.ground.Predator)unitinterface).WeaponsMask();
            if(!(unitinterface instanceof com.maddox.il2.ai.ground.Prey))
                continue;
            hitbyMask |= ((com.maddox.il2.ai.ground.Prey)unitinterface).HitbyMask();
            if(!(unitinterface instanceof com.maddox.il2.ai.ground.Predator))
                withPreys = true;
        }

        if(groupSpeed <= 0.001F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("group speed is too small");
        if(maxSpace <= 0.01F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("maxSpace is too small");
    }

    private float computeMaxSpace(java.util.ArrayList arraylist, int i, int j)
    {
        float f = -1F;
        do
        {
            if(j-- <= 0)
                break;
            float f1 = ((com.maddox.il2.ai.ground.UnitInterface)arraylist.get(i++)).BestSpace();
            if(f1 > f)
                f = f1;
        } while(true);
        if(f <= 0.01F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("maxSpace is too small");
        return f;
    }

    private float computeMaxSpace(java.lang.Object aobj[], int i, int j)
    {
        float f = -1F;
        do
        {
            if(j-- <= 0)
                break;
            float f1 = ((com.maddox.il2.ai.ground.UnitInterface)aobj[i++]).BestSpace();
            if(f1 > f)
                f = f1;
        } while(true);
        if(f <= 0.01F)
            com.maddox.il2.ai.ground.ChiefGround.ERR("maxSpace is too small");
        return f;
    }

    private static final int SecsToTicks(float f)
    {
        int i = (int)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return i > 0 ? i : 0;
    }

    public com.maddox.il2.engine.Actor GetNearestEnemy(com.maddox.JGP.Point3d point3d, double d, int i, float f)
    {
        if(unitsPacked.size() > 0)
            return null;
        if(f < 0.0F)
            com.maddox.il2.ai.ground.NearestEnemies.set(i);
        else
            com.maddox.il2.ai.ground.NearestEnemies.set(i, -9999.9F, f);
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.ground.NearestEnemies.getAFoundEnemy(point3d, d, getArmy());
        if(actor == null)
            return null;
        if(!(actor instanceof com.maddox.il2.ai.ground.Prey))
        {
            java.lang.System.out.println("chiefg: nearest enemies: non-Prey");
            return null;
        }
        switch(curState)
        {
        case 2: // '\002'
        case 3: // '\003'
        default:
            break;

        case 0: // '\0'
            if(!withPreys)
            {
                curState = 1;
                stateCountdown = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(50F, 90F));
                reformIfNeed(false);
            }
            break;

        case 1: // '\001'
            stateCountdown = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(50F, 90F));
            break;
        }
        return actor;
    }

    public void Detach(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        if(unitsPacked.size() > 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("Detaching when PACKED");
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("Detach");
        int i = aobj.length;
        int j;
        for(j = 0; j < i && actor != (com.maddox.il2.engine.Actor)aobj[j]; j++);
        if(j >= i)
            com.maddox.il2.ai.ground.ChiefGround.ERR("Detaching unknown unit");
        com.maddox.il2.ai.ground.UnitInterface unitinterface = (com.maddox.il2.ai.ground.UnitInterface)actor;
        com.maddox.il2.ai.ground.UnitData unitdata = unitinterface.GetUnitData();
        if(j < i - 1)
        {
            com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)aobj[j + 1];
            com.maddox.il2.ai.ground.UnitInterface unitinterface1 = (com.maddox.il2.ai.ground.UnitInterface)actor2;
            com.maddox.il2.ai.ground.UnitData unitdata1 = unitinterface1.GetUnitData();
            unitdata1.leader = unitdata.leader;
        }
        unitdata.leader = null;
        actor.setOwner(null);
        if(i > 1)
        {
            recomputeUnitsProperties();
            recomputeMinMaxSegments();
            reformIfNeed(true);
            recomputeAveragePosition();
        }
        if(i <= 1)
        {
            road.UnregisterTravellerFromBridges(this);
            road.unlockBridges(this, minGrabSeg, maxGrabSeg);
            minGrabSeg = maxGrabSeg = -1;
            com.maddox.il2.ai.World.onActorDied(this, actor1);
            destroy();
        }
    }

    public void msgDream(boolean flag)
    {
        boolean flag1 = unitsPacked.size() > 0;
        if(flag)
        {
            if(!flag1)
                com.maddox.il2.ai.ground.ChiefGround.ERR("Wakeup out of place");
            unpackUnits();
        } else
        {
            if(flag1)
                com.maddox.il2.ai.ground.ChiefGround.ERR("Sleeping out of place");
            packUnits();
        }
    }

    public void packUnits()
    {
        if(unitsPacked.size() > 0)
            return;
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("packUnits");
        computePositionForPacked();
        int i = aobj.length;
        for(int j = 0; j < i; j++)
        {
            unitsPacked.add(((com.maddox.il2.ai.ground.UnitInterface)aobj[j]).Pack());
            ((com.maddox.il2.engine.Actor)aobj[j]).destroy();
        }

        recomputeUnitsProperties();
    }

    public void unpackUnits()
    {
        int i = unitsPacked.size();
        if(i <= 0)
            return;
        if(getOwnerAttached().length > 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("unpack units");
        for(int j = 0; j < i; j++)
        {
            int k = ((com.maddox.il2.ai.ground.UnitInPackedForm)unitsPacked.get(j)).CodeName();
            int l = ((com.maddox.il2.ai.ground.UnitInPackedForm)unitsPacked.get(j)).CodeType();
            java.lang.Object obj = com.maddox.rts.Spawn.get(l);
            int i1 = ((com.maddox.il2.ai.ground.UnitInPackedForm)unitsPacked.get(j)).State();
            ((com.maddox.il2.ai.ground.UnitSpawn)obj).unitSpawn(k, i1, this);
        }

        unitsPacked.clear();
        curState = 0;
        stateCountdown = 0;
        road.unlockBridges(this, minGrabSeg, maxGrabSeg);
        minGrabSeg = maxGrabSeg = -1;
        recomputeUnitsProperties();
        formUnitsAfterUnpacking();
    }

    private static void AutoChooseFormation(int i, boolean flag, int j, float f, double d, int ai[])
    {
        if(j <= 0)
            return;
        if(i == 2 || i == 3)
        {
            ai[0] = flag ? 2 : 3;
            ai[1] = 1;
            ai[2] = j;
            return;
        }
        ai[0] = 0;
        int k = (int)(d / (double)f);
        if(k <= 0)
            k = 1;
        if(k > j)
            k = j;
        if(k <= 1)
        {
            ai[1] = 1;
            ai[2] = j;
            return;
        }
        if(i == 0)
        {
            ai[1] = 1;
            ai[2] = j;
            return;
        }
        ai[0] = 1;
        if(k >= 3 && j < ((k - 1) + k) - 1)
        {
            k /= 2;
            if(k < 2)
                k = 2;
        }
        ai[1] = k;
        int l = 2 * k - 1;
        int i1 = ((j + l) - 1) / l;
        ai[2] = i1 * 2;
        if(((j + l) - 1) % l < k - 1)
            ai[2]--;
    }

    private void formUnitsAfterUnpacking()
    {
        if(unitsPacked.size() > 0)
            return;
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("formUnitsAfterUnpacking");
        float f = (float)(aobj.length - 1) * maxSpace;
        double d = road.get(chiefSeg).computePosAlong_Fit(pos.getAbsPoint());
        com.maddox.il2.ai.ground.RoadPart roadpart = new RoadPart();
        road.FindFreeSpace(f, chiefSeg, d, roadpart);
        int ai[] = new int[3];
        com.maddox.il2.ai.ground.ChiefGround.AutoChooseFormation(curState, shift_ToRightSide, aobj.length, maxSpace, road.ComputeMinRoadWidth(roadpart.begseg, roadpart.endseg), ai);
        curForm = ai;
        f = (float)(ai[2] - 1) * maxSpace;
        road.FindFreeSpace(f, chiefSeg, d, roadpart);
        double d1 = 1.0D;
        int i = roadpart.begseg;
        double d2 = roadpart.begt;
        int j = 0;
        float f1 = 0.0F;
        int k = 0;
label0:
        do
        {
            int i1;
            float f2;
            float f3;
label1:
            {
label2:
                {
                    if(k >= ai[2])
                        break label0;
                    int l = ai[1];
                    if((k & 1) == 0 && ai[0] == 1)
                        l--;
                    i1 = l;
                    if(j + i1 > aobj.length)
                        i1 = aobj.length - j;
                    f2 = computeMaxSpace(aobj, j, i1);
                    f3 = 0.0F;
                    if(k <= 0)
                        break label1;
                    f3 = java.lang.Math.max(f1, f2);
                    double d3 = f3;
                    d2 -= d3;
                    do
                    {
                        if(d2 >= 0.0D)
                            break label2;
                        double d4 = -d2;
                        d2 = 0.0D;
                        if(road.segIsWrongOrDamaged(i - 1))
                            break;
                        i--;
                        d2 = road.get(i).length2D - d4;
                    } while(true);
                    d2 = -1D;
                }
                if(d2 < 0.0D)
                    break label0;
            }
            for(int j1 = 0; j1 < i1; j1++)
            {
                com.maddox.il2.ai.ground.UnitData unitdata = ((com.maddox.il2.ai.ground.UnitInterface)aobj[j]).GetUnitData();
                unitdata.segmentIdx = i;
                unitdata.leaderDist = f3;
                if(j == 0)
                    unitdata.leader = null;
                else
                if(k == 0)
                {
                    unitdata.leader = (com.maddox.il2.engine.Actor)aobj[0];
                    unitdata.leaderDist = 0.0F;
                } else
                if(ai[0] == 0 || (k & 1) == 0 || j1 > 0)
                    unitdata.leader = (com.maddox.il2.engine.Actor)aobj[j - ai[1]];
                else
                    unitdata.leader = (com.maddox.il2.engine.Actor)aobj[(j - ai[1]) + 1];
                float f4 = ai[0] != 0 ? maxSpace : f2;
                float f5 = (float)(ai[1] - 1) * f4;
                if(ai[0] == 1 && (k & 1) == 0)
                    f5 -= f4;
                unitdata.sideOffset = -f5 / 2.0F + (float)j1 * f4;
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[j];
                com.maddox.JGP.Point3d point3d = road.get(i).computePos_Fit(d2, unitdata.sideOffset, actor.collisionR());
                point3d.z += ((com.maddox.il2.ai.ground.UnitInterface)actor).HeightAboveLandSurface();
                actor.pos.setAbs(point3d);
                com.maddox.JGP.Vector3f vector3f = new Vector3f();
                if(road.get(i).IsLandAligned())
                    com.maddox.il2.engine.Engine.land().N(point3d.x, point3d.y, vector3f);
                else
                    vector3f.set(road.get(i).normal);
                com.maddox.il2.engine.Orient orient = new Orient();
                orient.setYPR(road.get(i).yaw, 0.0F, 0.0F);
                orient.orient(vector3f);
                actor.pos.setAbs(orient);
                actor.pos.reset();
                ((com.maddox.il2.ai.ground.UnitInterface)aobj[j]).startMove();
                j++;
            }

            if(j >= aobj.length)
                break;
            f1 = f2;
            k++;
        } while(true);
        if(j <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR("No room to place units");
        for(; j < aobj.length; j++)
            ((com.maddox.il2.engine.Actor)aobj[j]).destroy();

        recomputeMinMaxSegments();
    }

    private void reformForSHIFT(java.lang.Object aobj[], float f, boolean flag)
    {
        if(aobj == null)
            return;
        java.util.ArrayList arraylist = new ArrayList(aobj.length);
        for(int i = 0; i < aobj.length; i++)
            arraylist.add(aobj[i]);

        java.util.Collections.sort(arraylist, new java.util.Comparator() {

            public int compare(java.lang.Object obj, java.lang.Object obj1)
            {
                com.maddox.il2.ai.ground.UnitData unitdata1 = ((com.maddox.il2.ai.ground.UnitInterface)obj).GetUnitData();
                com.maddox.il2.ai.ground.UnitData unitdata2 = ((com.maddox.il2.ai.ground.UnitInterface)obj1).GetUnitData();
                com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(unitdata1.segmentIdx);
                com.maddox.il2.ai.ground.RoadSegment roadsegment1 = road.get(unitdata2.segmentIdx);
                double d = (roadsegment1.length2Dallprev + roadsegment1.computePosAlong(((com.maddox.il2.engine.Actor)obj1).pos.getAbsPoint())) - roadsegment.length2Dallprev - roadsegment.computePosAlong(((com.maddox.il2.engine.Actor)obj).pos.getAbsPoint());
                return d != 0.0D ? d <= 0.0D ? -1 : 1 : 0;
            }

        }
);
        float f1 = flag ? 5000F : -5000F;
        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.il2.ai.ground.UnitData unitdata = ((com.maddox.il2.ai.ground.UnitInterface)arraylist.get(j)).GetUnitData();
            if(j == 0)
            {
                unitdata.leaderDist = 0.0F;
                unitdata.leader = null;
            } else
            {
                unitdata.leaderDist = f;
                unitdata.leader = (com.maddox.il2.engine.Actor)arraylist.get(j - 1);
            }
            unitdata.sideOffset = f1;
        }

        for(int k = 0; k < aobj.length; k++)
            ((com.maddox.il2.ai.ground.UnitInterface)aobj[k]).forceReaskMove();

    }

    private void reform(java.lang.Object aobj[], int ai[])
    {
        if(aobj == null)
            return;
        if(ai[0] == 2 || ai[0] == 3)
        {
            reformForSHIFT(aobj, maxSpace, ai[0] == 2);
            return;
        }
        java.util.ArrayList arraylist = new ArrayList(aobj.length);
        for(int i = 0; i < aobj.length; i++)
            arraylist.add(aobj[i]);

        java.util.Collections.sort(arraylist, new java.util.Comparator() {

            public int compare(java.lang.Object obj, java.lang.Object obj1)
            {
                com.maddox.il2.ai.ground.UnitData unitdata2 = ((com.maddox.il2.ai.ground.UnitInterface)obj).GetUnitData();
                com.maddox.il2.ai.ground.UnitData unitdata3 = ((com.maddox.il2.ai.ground.UnitInterface)obj1).GetUnitData();
                com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(unitdata2.segmentIdx);
                com.maddox.il2.ai.ground.RoadSegment roadsegment1 = road.get(unitdata3.segmentIdx);
                double d = (roadsegment1.length2Dallprev + roadsegment1.computePosAlong(((com.maddox.il2.engine.Actor)obj1).pos.getAbsPoint())) - roadsegment.length2Dallprev - roadsegment.computePosAlong(((com.maddox.il2.engine.Actor)obj).pos.getAbsPoint());
                return d != 0.0D ? d <= 0.0D ? -1 : 1 : 0;
            }

        }
);
        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.il2.ai.ground.UnitData unitdata = ((com.maddox.il2.ai.ground.UnitInterface)arraylist.get(j)).GetUnitData();
            if(ai[0] == 0)
            {
                unitdata.leaderDist = j / ai[1];
            } else
            {
                int i1 = j / (ai[1] * 2 - 1);
                i1 = j % (ai[1] * 2 - 1) >= ai[1] - 1 ? i1 * 2 + 1 : i1 * 2;
                unitdata.leaderDist = i1;
            }
        }

        java.util.Collections.sort(arraylist, new java.util.Comparator() {

            public int compare(java.lang.Object obj, java.lang.Object obj1)
            {
                com.maddox.il2.ai.ground.UnitData unitdata2 = ((com.maddox.il2.ai.ground.UnitInterface)obj).GetUnitData();
                com.maddox.il2.ai.ground.UnitData unitdata3 = ((com.maddox.il2.ai.ground.UnitInterface)obj1).GetUnitData();
                if(unitdata2.leaderDist != unitdata3.leaderDist)
                {
                    double d = unitdata2.leaderDist - unitdata3.leaderDist;
                    return d != 0.0D ? d <= 0.0D ? -1 : 1 : 0;
                } else
                {
                    com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(unitdata2.segmentIdx);
                    com.maddox.il2.ai.ground.RoadSegment roadsegment1 = road.get(unitdata3.segmentIdx);
                    double d1 = roadsegment.computePosSide(((com.maddox.il2.engine.Actor)obj).pos.getAbsPoint()) - roadsegment1.computePosSide(((com.maddox.il2.engine.Actor)obj1).pos.getAbsPoint());
                    return d1 != 0.0D ? d1 <= 0.0D ? -1 : 1 : 0;
                }
            }

        }
);
        int k = 0;
        float f = 0.0F;
        int j1 = 0;
        do
        {
            if(j1 >= ai[2])
                break;
            int k1 = ai[1];
            if((j1 & 1) == 0 && ai[0] == 1)
                k1--;
            int l1 = k1;
            if(k + l1 > arraylist.size())
                l1 = arraylist.size() - k;
            float f1 = computeMaxSpace(arraylist, k, l1);
            float f2 = 0.0F;
            if(j1 > 0)
                f2 = java.lang.Math.max(f, f1);
            for(int i2 = 0; i2 < l1; i2++)
            {
                com.maddox.il2.ai.ground.UnitData unitdata1 = ((com.maddox.il2.ai.ground.UnitInterface)arraylist.get(k)).GetUnitData();
                unitdata1.leaderDist = f2;
                int j2;
                if(k == 0)
                    j2 = -1;
                else
                if(j1 == 0)
                {
                    j2 = 0;
                    unitdata1.leaderDist = 0.0F;
                } else
                if(ai[0] == 0 || (j1 & 1) == 0 || i2 > 0)
                    j2 = k - ai[1];
                else
                    j2 = (k - ai[1]) + 1;
                unitdata1.leader = j2 >= 0 ? (com.maddox.il2.engine.Actor)arraylist.get(j2) : null;
                float f3 = ai[0] != 0 ? maxSpace : f1;
                float f4 = (float)(ai[1] - 1) * f3;
                if(ai[0] == 1 && (j1 & 1) == 0)
                    f4 -= f3;
                unitdata1.sideOffset = -f4 / 2.0F + (float)i2 * f3;
                k++;
            }

            if(k >= arraylist.size())
                break;
            f = f1;
            j1++;
        } while(true);
        for(int l = 0; l < aobj.length; l++)
            ((com.maddox.il2.ai.ground.UnitInterface)aobj[l]).forceReaskMove();

    }

    private int[] FindBestFormation(int i)
    {
        float f = (float)(i - 1) * maxSpace;
        int ai[] = new int[3];
        com.maddox.il2.ai.ground.ChiefGround.AutoChooseFormation(curState, shift_ToRightSide, i, maxSpace, road.ComputeMinRoadWidth(maxSeg, minSeg), ai);
        return ai;
    }

    private void reformIfNeed(boolean flag)
    {
        java.lang.Object aobj[] = getOwnerAttached();
        if(aobj.length <= 0)
            com.maddox.il2.ai.ground.ChiefGround.ERR_NO_UNITS("reformIfNeed");
        if(flag)
        {
            curForm = FindBestFormation(aobj.length);
            reform(aobj, curForm);
        } else
        {
            int ai[] = FindBestFormation(aobj.length);
            if(ai[0] != curForm[0] || ai[1] != curForm[1])
            {
                curForm = ai;
                reform(aobj, curForm);
            }
        }
    }

    public void CollisionOccured(com.maddox.il2.ai.ground.UnitInterface unitinterface, com.maddox.il2.engine.Actor actor)
    {
        if(!(actor instanceof com.maddox.il2.ai.ground.UnitInterface))
            return;
        if(actor.getArmy() != getArmy() && actor.isAlive() && actor.getArmy() != 0 && isAlive() && getArmy() != 0)
            return;
        com.maddox.il2.engine.Actor actor1 = actor.getOwner();
        if(actor1 == null)
            return;
        if(actor1 == this)
            return;
        if(!(actor1 instanceof com.maddox.il2.ai.ground.ChiefGround))
            throw new ActorException("ChiefGround: ground unit with wrong owner");
        com.maddox.il2.ai.ground.ChiefGround chiefground = (com.maddox.il2.ai.ground.ChiefGround)actor1;
        com.maddox.il2.ai.ground.UnitInterface unitinterface1 = (com.maddox.il2.ai.ground.UnitInterface)actor;
        com.maddox.JGP.Vector2d vector2d = road.get(unitinterface.GetUnitData().segmentIdx).dir2D;
        com.maddox.JGP.Vector2d vector2d1 = chiefground.road.get(unitinterface1.GetUnitData().segmentIdx).dir2D;
        boolean flag = vector2d.x * vector2d1.x + vector2d.y * vector2d1.y < 0.0D;
        boolean flag1 = false;
        if(chiefground.waitTime < waitTime)
            flag1 = true;
        else
        if(chiefground.waitTime == waitTime)
            if(chiefground.groupSpeed > groupSpeed)
                flag1 = true;
            else
            if(chiefground.groupSpeed == groupSpeed && chiefground.name().compareTo(name()) < 0)
                flag1 = true;
        curState = chiefground.curState = 2;
        int i = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(47F, 72F));
        int j = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(47F, 72F));
        if(flag)
        {
            shift_ToRightSide = true;
            shift_SwitchToBrakeWhenDone = false;
            chiefground.shift_ToRightSide = true;
            chiefground.shift_SwitchToBrakeWhenDone = false;
            stateCountdown = i;
            chiefground.stateCountdown = j;
        } else
        {
            shift_ToRightSide = flag1;
            shift_SwitchToBrakeWhenDone = flag1;
            chiefground.shift_ToRightSide = !flag1;
            chiefground.shift_SwitchToBrakeWhenDone = !flag1;
            int k = com.maddox.il2.ai.ground.ChiefGround.SecsToTicks(com.maddox.il2.ai.World.Rnd().nextFloat(18F, 25F));
            if(flag1)
            {
                chiefground.stateCountdown = i;
                stateCountdown = chiefground.stateCountdown - k;
            } else
            {
                stateCountdown = i;
                chiefground.stateCountdown = stateCountdown - k;
            }
        }
        reformIfNeed(false);
        chiefground.reformIfNeed(false);
    }

    public double computePosAlong_Fit(int i, com.maddox.JGP.Point3d point3d)
    {
        return road.get(i).computePosAlong_Fit(point3d);
    }

    public double computePosAlong(int i, com.maddox.JGP.Point3d point3d)
    {
        return road.get(i).computePosAlong(point3d);
    }

    public double computePosSide(int i, com.maddox.JGP.Point3d point3d)
    {
        return road.get(i).computePosSide(point3d);
    }

    private static final float distance2D(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        return (float)java.lang.Math.sqrt((point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y));
    }

    private static boolean intersectLineCircle(float f, float f1, float f2, float f3, float f4, float f5, float f6)
    {
        float f7 = f6 * f6;
        float f8 = f2 - f;
        float f9 = f3 - f1;
        float f10 = f8 * f8 + f9 * f9;
        float f11 = ((f4 - f) * f8 + (f5 - f1) * f9) / f10;
        if(f11 >= 0.0F && f11 <= 1.0F)
        {
            float f12 = f + f11 * f8;
            float f14 = f1 + f11 * f9;
            float f16 = (f12 - f4) * (f12 - f4) + (f14 - f5) * (f14 - f5);
            return f7 - f16 >= 0.0F;
        } else
        {
            float f13 = (f2 - f4) * (f2 - f4) + (f3 - f5) * (f3 - f5);
            float f15 = (f - f4) * (f - f4) + (f1 - f5) * (f1 - f5);
            return f13 <= f7 || f15 <= f7;
        }
    }

    private static boolean intersectCircle(com.maddox.JGP.Point3d point3d, double d, com.maddox.JGP.Point3d point3d1, double d1, float f)
    {
        float f1 = (float)(point3d1.x + d1 * (double)com.maddox.JGP.Geom.cosDeg(f));
        float f2 = (float)(point3d1.y + d1 * (double)com.maddox.JGP.Geom.sinDeg(f));
        return com.maddox.il2.ai.ground.ChiefGround.intersectLineCircle((float)point3d1.x, (float)point3d1.y, f1, f2, (float)point3d.x, (float)point3d.y, (float)d);
    }

    private com.maddox.il2.ai.ground.UnitMove createStay_UnitMove(float f, int i)
    {
        com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(i);
        if(roadsegment.IsLandAligned())
            return new UnitMove(f, new Vector3f(0.0F, 0.0F, -1F));
        else
            return new UnitMove(f, roadsegment.normal);
    }

    private boolean cantEnterIntoSegment_checkComplete(int i)
    {
        if(i >= road.nsegments() - 1)
        {
            boolean flag = waitTime > 0L && i > maxSeg;
            if(!flag)
                com.maddox.il2.ai.World.onTaskComplete(this);
            return true;
        } else
        {
            return !road.segIsPassableBy(i, this) || waitTime > 0L && i > maxSeg;
        }
    }

    private boolean cantEnterIntoSegment(int i)
    {
        return i >= road.nsegments() - 1 || !road.segIsPassableBy(i, this) || waitTime > 0L && i > maxSeg;
    }

    private boolean cantEnterIntoSegmentPacked_checkComplete(int i)
    {
        if(i >= road.nsegments() - 1)
        {
            boolean flag = waitTime > 0L;
            if(!flag)
                com.maddox.il2.ai.World.onTaskComplete(this);
            return true;
        } else
        {
            return !road.segIsPassableBy(i, this) || waitTime > 0L;
        }
    }

    private void moveChiefPacked(float f)
    {
        com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(chiefSeg);
        double d = groupSpeed * f;
        do
        {
            if(chiefAlong + d < roadsegment.length2D)
                break;
            if(cantEnterIntoSegmentPacked_checkComplete(chiefSeg + 1))
            {
                chiefAlong = roadsegment.length2D;
                d = 0.0D;
                break;
            }
            chiefAlong = (chiefAlong + d) - roadsegment.length2D;
            chiefSeg++;
            recomputeChiefWaitTime(chiefSeg);
            roadsegment = road.get(chiefSeg);
            road.unlockBridges(this, minGrabSeg, maxGrabSeg);
            minGrabSeg = maxGrabSeg = chiefSeg;
            road.lockBridges(this, minGrabSeg, maxGrabSeg);
        } while(true);
        chiefAlong += d;
        SetPosition(roadsegment.computePos_Fit(chiefAlong, 0.0D, 0.0F), f);
    }

    public com.maddox.il2.ai.ground.UnitMove AskMoveCommand(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.il2.ai.ground.StaticObstacle staticobstacle)
    {
        com.maddox.il2.ai.ground.UnitInterface unitinterface = (com.maddox.il2.ai.ground.UnitInterface)actor;
        com.maddox.il2.ai.ground.UnitData unitdata = unitinterface.GetUnitData();
        boolean flag = point3d != null && point3d.z < 0.0D;
        boolean flag1 = point3d != null && point3d.z > 0.0D;
        if((curState == 2 || curState == 3) && flag1)
            flag1 = false;
        com.maddox.il2.ai.ground.RoadSegment roadsegment = road.get(unitdata.segmentIdx);
        com.maddox.JGP.Point3d point3d1 = new Point3d(actor.pos.getAbsPoint());
        if(flag)
        {
            int i = 0;
            double d = roadsegment.computePosAlong(point3d1);
            if(d >= 0.0D)
                d = d <= roadsegment.length2D ? 0.0D : d - roadsegment.length2D;
            do
            {
                if(d > 0.0D)
                    cantEnterIntoSegment_checkComplete(unitdata.segmentIdx + 1);
                if(i >= 0)
                    if(cantEnterIntoSegment(unitdata.segmentIdx + 1))
                    {
                        if(i != 0)
                            break;
                    } else
                    {
                        com.maddox.il2.ai.ground.RoadSegment roadsegment1 = road.get(unitdata.segmentIdx + 1);
                        double d5 = roadsegment1.computePosAlong(point3d1);
                        if(d5 >= 0.0D)
                            d5 = d5 <= roadsegment1.length2D ? 0.0D : d5 - roadsegment1.length2D;
                        if(java.lang.Math.abs(d5) < java.lang.Math.abs(d))
                        {
                            i = 1;
                            d = d5;
                            unitdata.segmentIdx++;
                        } else
                        if(i != 0)
                            break;
                    }
                if(i > 0)
                    continue;
                if(cantEnterIntoSegment(unitdata.segmentIdx - 1))
                    break;
                com.maddox.il2.ai.ground.RoadSegment roadsegment2 = road.get(unitdata.segmentIdx - 1);
                double d6 = roadsegment2.computePosAlong(point3d1);
                if(d6 >= 0.0D)
                    d6 = d6 <= roadsegment2.length2D ? 0.0D : d6 - roadsegment2.length2D;
                if(java.lang.Math.abs(d6) >= java.lang.Math.abs(d))
                    break;
                i = -1;
                d = d6;
                unitdata.segmentIdx--;
            } while(true);
            if(i != 0)
            {
                roadsegment = road.get(unitdata.segmentIdx);
                recomputeMinMaxSegments();
                reformIfNeed(false);
            }
        } else
        {
            boolean flag2 = false;
            com.maddox.il2.ai.ground.UnitMove unitmove = null;
            for(; roadsegment.computePosAlong(actor.pos.getAbsPoint()) >= roadsegment.length2D - 1.0D; roadsegment = road.get(unitdata.segmentIdx))
            {
                if(cantEnterIntoSegment_checkComplete(unitdata.segmentIdx + 1))
                {
                    unitmove = createStay_UnitMove(5F, unitdata.segmentIdx);
                    break;
                }
                flag2 = true;
                unitdata.segmentIdx++;
                if(unitdata.segmentIdx > maxSeg || unitdata.segmentIdx - 1 <= minSeg)
                    recomputeMinMaxSegments();
            }

            if(flag2)
                reformIfNeed(false);
            if(unitmove != null)
                return unitmove;
        }
        com.maddox.JGP.Vector3d vector3d = new Vector3d(roadsegment.dir2D.x, roadsegment.dir2D.y, 0.0D);
        if(!flag && staticobstacle.updateState())
        {
            double d1 = roadsegment.computePosAlong(point3d1);
            double d4 = road.ComputeSignedDistAlong(unitdata.segmentIdx, d1, staticobstacle.segIdx, staticobstacle.along);
            double d9 = actor.collisionR();
            if(d9 <= 0.0D)
                d9 = 0.0D;
            d9 += staticobstacle.R;
            if(d9 <= 0.0D)
                d9 = 2D;
            double d12 = d4;
            if(d4 >= 0.0D)
            {
                d12 -= d9;
                if(d12 <= 0.0D)
                    d12 = 0.0D;
            } else
            {
                d12 += d9;
                if(d12 >= 0.0D)
                    d12 = 0.0D;
            }
            if(java.lang.Math.abs(d4) <= 130D);
            if(d12 < -java.lang.Math.max(7D * java.lang.Math.abs(d9), 120D))
                staticobstacle.clear();
            else
            if(d4 <= 0.0D)
            {
                if(d12 >= -1.5D)
                    vector3d.z = 2.5D;
            } else
            if(d12 > java.lang.Math.max(3D * d9, 20D))
            {
                vector3d.z = java.lang.Math.max(d9, 3D);
            } else
            {
                double d14 = 0.5D * d9;
                if(d14 < 0.01D)
                {
                    vector3d.z = 1.0D;
                } else
                {
                    com.maddox.JGP.Vector2f vector2f = new Vector2f((float)(staticobstacle.pos.x - point3d1.x), (float)(staticobstacle.pos.y - point3d1.y));
                    if(vector2f.length() < 0.1F)
                    {
                        vector3d.z = 4D;
                    } else
                    {
                        vector2f.normalize();
                        com.maddox.il2.ai.AnglesFork anglesfork = new AnglesFork();
                        anglesfork.setSrcRad((float)java.lang.Math.atan2(vector2f.y, vector2f.x));
                        anglesfork.setDstDeg(anglesfork.getSrcDeg() + (staticobstacle.side <= 0.0D ? -90F : 90F));
                        double d17 = d9 + 0.5D;
                        if(!com.maddox.il2.ai.ground.ChiefGround.intersectCircle(staticobstacle.pos, d17, point3d1, d14, anglesfork.getSrcDeg()))
                        {
                            float f6 = anglesfork.getSrcDeg();
                            vector3d.set(com.maddox.JGP.Geom.cosDeg(f6), com.maddox.JGP.Geom.sinDeg(f6), d14);
                        } else
                        if(com.maddox.il2.ai.ground.ChiefGround.intersectCircle(staticobstacle.pos, d17, point3d1, d14, anglesfork.getDstDeg()))
                        {
                            vector3d.z = 2D;
                        } else
                        {
                            for(int j = 0; j < 6; j++)
                            {
                                float f8 = anglesfork.getDeg(0.5F);
                                if(com.maddox.il2.ai.ground.ChiefGround.intersectCircle(staticobstacle.pos, d17, point3d1, d14, f8))
                                    anglesfork.setSrcDeg(f8);
                                else
                                    anglesfork.setDstDeg(f8);
                            }

                            float f7 = anglesfork.getDstDeg();
                            vector3d.set(com.maddox.JGP.Geom.cosDeg(f7), com.maddox.JGP.Geom.sinDeg(f7), d14);
                        }
                    }
                }
            }
        }
        if(vector3d.z > 0.0D)
        {
            flag1 = false;
            flag = true;
            vector3d.x *= vector3d.z;
            vector3d.y *= vector3d.z;
            point3d = new Point3d(vector3d);
            point3d.z = -1D;
        }
        if(flag)
        {
            com.maddox.JGP.Point3d point3d2 = new Point3d(point3d1);
            point3d2.x += point3d.x;
            point3d2.y += point3d.y;
            double d2 = roadsegment.computePosAlong(point3d2);
            double d7 = roadsegment.computePosSide(point3d2);
            if(d2 >= roadsegment.length2D - 0.20000000000000001D)
            {
                point3d2 = roadsegment.computePos_Fit(roadsegment.length2D, d7, actor.collisionR());
                point3d2.x += roadsegment.dir2D.x * 0.20000000000000001D;
                point3d2.y += roadsegment.dir2D.y * 0.20000000000000001D;
            } else
            {
                point3d2 = roadsegment.computePos_Fit(d2, d7, actor.collisionR());
            }
            double d10 = com.maddox.il2.ai.ground.ChiefGround.distance2D(point3d2, point3d1);
            float f4 = (float)d10 / groupSpeed;
            com.maddox.JGP.Vector3f vector3f = road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1F) : road.get(unitdata.segmentIdx).normal;
            return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d2, f4, vector3f, groupSpeed);
        }
        if(curState == 3)
            return createStay_UnitMove(5F, unitdata.segmentIdx);
        if(unitdata.leader == null)
        {
            float f = unitinterface.CommandInterval() * com.maddox.il2.ai.World.Rnd().nextFloat(0.8F, 1.0F);
            double d3 = groupSpeed * f;
            double d8 = d3 + roadsegment.computePosAlong(point3d1);
            double d11 = unitdata.sideOffset;
            if(flag1)
            {
                d8 += point3d.x;
                d11 += point3d.y;
            }
            com.maddox.JGP.Point3d point3d4;
            if(d8 >= roadsegment.length2D - 0.20000000000000001D)
            {
                point3d4 = roadsegment.computePos_Fit(roadsegment.length2D, d11, actor.collisionR());
                point3d4.x += roadsegment.dir2D.x * 0.20000000000000001D;
                point3d4.y += roadsegment.dir2D.y * 0.20000000000000001D;
            } else
            {
                point3d4 = roadsegment.computePos_Fit(d8, d11, actor.collisionR());
            }
            d3 = com.maddox.il2.ai.ground.ChiefGround.distance2D(point3d4, point3d1);
            float f5 = (float)d3 / groupSpeed;
            com.maddox.JGP.Vector3f vector3f1 = road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1F) : road.get(unitdata.segmentIdx).normal;
            return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d4, f5, vector3f1, groupSpeed);
        }
        com.maddox.il2.engine.Actor actor1 = unitdata.leader;
        com.maddox.il2.ai.ground.UnitData unitdata1 = ((com.maddox.il2.ai.ground.UnitInterface)actor1).GetUnitData();
        com.maddox.il2.ai.ground.RoadSegment roadsegment3 = road.get(unitdata1.segmentIdx);
        com.maddox.JGP.Point3d point3d3 = new Point3d();
        float f1 = unitinterface.CommandInterval();
        f1 *= com.maddox.il2.ai.World.Rnd().nextFloat(0.8F, 1.0F);
        float f3 = actor1.futurePosition(f1, point3d3);
        f3 += 0.4F;
        double d13 = roadsegment3.computePosAlong(point3d3);
        com.maddox.JGP.Point3d point3d5 = new Point3d();
        actor.pos.getAbs(point3d5);
        double d15 = roadsegment.computePosAlong(point3d5);
        double d16 = road.ComputeSignedDistAlong(unitdata.segmentIdx, d15, unitdata1.segmentIdx, d13);
        double d18 = unitdata.leaderDist;
        if(flag1)
            d18 += point3d.x;
        double d19 = d16 - d18;
        if(d19 < 0.0D)
        {
            float f2 = unitinterface.StayInterval();
            if(roadsegment.IsLandAligned())
                return new UnitMove(f2, new Vector3f(0.0F, 0.0F, -1F));
            else
                return new UnitMove(f2, road.get(unitdata.segmentIdx).normal);
        }
        double d20 = roadsegment.length2D - d15;
        if(d19 <= d20)
        {
            d15 += d19;
        } else
        {
            d15 = roadsegment.length2D;
            f3 = (float)((double)f3 * (d20 / d19));
        }
        f3 *= 1.05F;
        double d21 = unitdata.sideOffset;
        if(flag1)
            d21 += point3d.y;
        point3d5 = roadsegment.computePos_FitBegCirc(d15, d21, actor.collisionR());
        if(d15 >= roadsegment.length2D - 0.10000000000000001D)
        {
            point3d5.x += roadsegment.dir2D.x * 0.20000000000000001D;
            point3d5.y += roadsegment.dir2D.y * 0.20000000000000001D;
        }
        d18 = com.maddox.il2.ai.ground.ChiefGround.distance2D(point3d5, actor.pos.getAbsPoint());
        com.maddox.JGP.Vector3f vector3f2 = road.get(unitdata.segmentIdx).IsLandAligned() ? new Vector3f(0.0F, 0.0F, -1F) : road.get(unitdata.segmentIdx).normal;
        return new UnitMove(unitinterface.HeightAboveLandSurface(), point3d5, f3 >= 0.3F ? f3 : 0.3F, vector3f2, -1F);
    }

    private static final int PEACE = 0;
    private static final int FIGHT = 1;
    private static final int SHIFT = 2;
    private static final int BRAKE = 3;
    private int curState;
    private int stateCountdown;
    private boolean shift_ToRightSide;
    private boolean shift_SwitchToBrakeWhenDone;
    private java.util.ArrayList unitsPacked;
    private com.maddox.il2.ai.ground.RoadPath road;
    private int minGrabSeg;
    private int maxGrabSeg;
    private int chiefSeg;
    private double chiefAlong;
    private int minSeg;
    private int maxSeg;
    private float groupSpeed;
    private float maxSpace;
    private boolean withPreys;
    private long waitTime;
    private int posCountdown;
    private com.maddox.JGP.Vector3d estim_speed;
    private com.maddox.JGP.Point3d tmpp;
    private int curForm[];














}
