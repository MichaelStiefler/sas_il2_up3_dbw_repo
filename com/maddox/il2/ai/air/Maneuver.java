// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Maneuver.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.fm.AIFlightModel;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Polares;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.AR_234;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BI_1;
import com.maddox.il2.objects.air.BI_6;
import com.maddox.il2.objects.air.G4M2E;
import com.maddox.il2.objects.air.HE_LERCHE3;
import com.maddox.il2.objects.air.KI_46_OTSUHEI;
import com.maddox.il2.objects.air.ME_163B1A;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.TA_152C;
import com.maddox.il2.objects.air.TA_183;
import com.maddox.il2.objects.air.TypeAcePlane;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.ai.air:
//            AutopilotAI, Pilot, Point_Null, AirGroup, 
//            Airdrome, ManString, AirGroupList, Point_Any

public class Maneuver extends com.maddox.il2.fm.AIFlightModel
{

    public void set_task(int i)
    {
        task = i;
    }

    public int get_task()
    {
        return task;
    }

    public int get_maneuver()
    {
        return maneuver;
    }

    public void set_maneuver(int i)
    {
        if(maneuver == 44)
            return;
        if(i != 44 && (maneuver == 26 || maneuver == 69 || maneuver == 66 || maneuver == 46))
            return;
        int j = maneuver;
        maneuver = i;
        if(j != maneuver)
            set_flags();
    }

    public void pop()
    {
        if(maneuver == 44)
            return;
        if(program[0] != 44 && (maneuver == 26 || maneuver == 69 || maneuver == 66 || maneuver == 46))
            return;
        int i = maneuver;
        maneuver = program[0];
        for(int j = 0; j < program.length - 1; j++)
            program[j] = program[j + 1];

        program[program.length - 1] = 0;
        if(i != maneuver)
            set_flags();
    }

    public void unblock()
    {
        maneuver = 0;
    }

    public void safe_set_maneuver(int i)
    {
        dont_change_subm = true;
        set_maneuver(i);
        dont_change_subm = true;
    }

    public void safe_pop()
    {
        dont_change_subm = true;
        pop();
        dont_change_subm = true;
    }

    public void clear_stack()
    {
        for(int i = 0; i < program.length; i++)
            program[i] = 0;

    }

    public void push(int i)
    {
        for(int j = program.length - 2; j >= 0; j--)
            program[j + 1] = program[j];

        program[0] = i;
    }

    public void push()
    {
        push(maneuver);
    }

    public void accurate_set_task_maneuver(int i, int j)
    {
        if(maneuver == 44 || maneuver == 26 || maneuver == 69 || maneuver == 64)
            return;
        set_task(i);
        if(maneuver != j)
        {
            clear_stack();
            set_maneuver(j);
        }
    }

    public void accurate_set_FOLLOW()
    {
        if(maneuver == 44 || maneuver == 26 || maneuver == 69 || maneuver == 64)
            return;
        set_task(2);
        if(maneuver != 24 && maneuver != 53)
        {
            clear_stack();
            set_maneuver(24);
        }
    }

    public void setBusy(boolean flag)
    {
        bBusy = flag;
    }

    public boolean isBusy()
    {
        return bBusy;
    }

    public void setSpeedMode(int i)
    {
        speedMode = i;
    }

    private boolean isStallable()
    {
        if(actor instanceof com.maddox.il2.objects.air.TypeStormovik)
            return false;
        return !(actor instanceof com.maddox.il2.objects.air.TypeTransport);
    }

    private void resetControls()
    {
        CT.AileronControl = CT.BrakeControl = CT.ElevatorControl = CT.FlapsControl = CT.RudderControl = 0.0F;
        CT.AirBrakeControl = 0.0F;
    }

    private void set_flags()
    {
        if(com.maddox.il2.ai.World.cur().isDebugFM())
            printDebugFM();
        AP.setStabAll(false);
        mn_time = 0.0F;
        minElevCoeff = 4F;
        if(!dont_change_subm)
        {
            setSpeedMode(3);
            first = true;
            submaneuver = 0;
            sub_Man_Count = 0;
        }
        dont_change_subm = false;
        if(maneuver != 48 && maneuver != 0 && maneuver != 26 && maneuver != 64 && maneuver != 44)
            resetControls();
        if(maneuver == 20 || maneuver == 25 || maneuver == 66 || maneuver == 1 || maneuver == 26 || maneuver == 69 || maneuver == 44 || maneuver == 49 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 46 || maneuver == 2 || maneuver == 10 || maneuver == 57 || maneuver == 64)
            setCheckGround(false);
        else
            setCheckGround(true);
        if(maneuver == 24 || maneuver == 53 || maneuver == 68 || maneuver == 59 || maneuver == 8 || maneuver == 55 || maneuver == 27 || maneuver == 62 || maneuver == 63 || maneuver == 25 || maneuver == 66 || maneuver == 43 || maneuver == 50 || maneuver == 65 || maneuver == 44 || maneuver == 21 || maneuver == 64 || maneuver == 69)
            frequentControl = true;
        else
            frequentControl = false;
        if(maneuver == 25 || maneuver == 26 || maneuver == 69)
            turnOnChristmasTree(true);
        else
            turnOnChristmasTree(false);
        if(maneuver == 25)
            turnOnCloudShine(true);
        else
            turnOnCloudShine(false);
        if(maneuver == 60 || maneuver == 61 || maneuver == 66 || maneuver == 1 || maneuver == 24 || maneuver == 26 || maneuver == 69 || maneuver == 64 || maneuver == 44)
            checkStrike = false;
        else
            checkStrike = true;
        if(maneuver == 44 || maneuver == 1 || maneuver == 48 || maneuver == 0 || maneuver == 26 || maneuver == 69 || maneuver == 64 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 52 || maneuver == 47)
            stallable = false;
        else
            stallable = true;
        if(maneuver == 44 || maneuver == 1 || maneuver == 26 || maneuver == 69 || maneuver == 64 || maneuver == 2 || maneuver == 57 || maneuver == 60 || maneuver == 61 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 52 || maneuver == 47 || maneuver == 29)
            setBusy(true);
    }

    public void setCheckStrike(boolean flag)
    {
        checkStrike = flag;
    }

    private void setCheckGround(boolean flag)
    {
        checkGround = flag;
    }

    public Maneuver(java.lang.String s)
    {
        super(s);
        maneuver = 26;
        program = new int[8];
        bBusy = true;
        wasBusy = true;
        dontSwitch = false;
        aggressiveWingman = false;
        kamikaze = false;
        silence = true;
        bombsOutCounter = 0;
        first = true;
        rocketsDelay = 0;
        bulletDelay = 0;
        submanDelay = 0;
        Alt = 0.0F;
        corrCoeff = 0.0F;
        corrElev = 0.0F;
        corrAile = 0.0F;
        checkGround = false;
        checkStrike = false;
        frequentControl = false;
        stallable = false;
        airClient = null;
        target = null;
        danger = null;
        dangerAggressiveness = 0.0F;
        oldDanCoeff = 0.0F;
        shotAtFriend = 0;
        distToFriend = 0.0F;
        target_ground = null;
        TaxiMode = false;
        finished = false;
        canTakeoff = false;
        curAirdromePoi = 0;
        actionTimerStart = 0L;
        actionTimerStop = 0L;
        gattackCounter = 0;
        beNearOffsPhase = 0;
        submaneuver = 0;
        dont_change_subm = false;
        tmpi = 0;
        sub_Man_Count = 0;
        dist = 0.0F;
        man1Dist = 50F;
        bullTime = 0.0015F;
        speedMode = 3;
        smConstSpeed = 90F;
        smConstPower = 0.7F;
        tailForStaying = null;
        tailOffset = new Vector3d();
        WeWereInGAttack = false;
        WeWereInAttack = false;
        SpeakMissionAccomplished = false;
        takeIntoAccount = new float[8];
        AccountCoeff = new float[8];
        ApproachV = new Vector3d();
        TargV = new Vector3d();
        TargDevV = new Vector3d(0.0D, 0.0D, 0.0D);
        TargDevVnew = new Vector3d(0.0D, 0.0D, 0.0D);
        scaledApproachV = new Vector3d();
        followOffset = new Vector3d();
        followTargShift = new Vector3d(0.0D, 0.0D, 0.0D);
        followCurShift = new Vector3d(0.0D, 0.0D, 0.0D);
        raAilShift = 0.0F;
        raElevShift = 0.0F;
        raRudShift = 0.0F;
        sinKren = 0.0F;
        strikeEmer = false;
        strikeTarg = null;
        strikeV = new Vector3d();
        direc = false;
        Kmax = 10F;
        phase = 0.0D;
        radius = 50D;
        pointQuality = -1;
        curPointQuality = 50;
        saveOr = new Orient();
        oldVe = new Vector3d();
        Vtarg = new Vector3d();
        constVtarg = new Vector3d();
        constVtarg1 = new Vector3d();
        Vxy = new Vector3d();
        AFo = new AnglesFork();
        headPos = new float[3];
        headOr = new float[3];
        pilotHeadT = 0.0F;
        pilotHeadY = 0.0F;
        AP = new AutopilotAI(this);
    }

    public void decDangerAggressiveness()
    {
        dangerAggressiveness -= 0.01F;
        if(dangerAggressiveness < 0.0F)
            dangerAggressiveness = 0.0F;
        oldDanCoeff -= 0.005F;
        if(oldDanCoeff < 0.0F)
            oldDanCoeff = 0.0F;
    }

    public void incDangerAggressiveness(int i, float f, float f1, com.maddox.il2.fm.FlightModel flightmodel)
    {
        f -= 0.7F;
        if(f < 0.0F)
            f = 0.0F;
        f1 = 1000F - f1;
        if(f1 < 0.0F)
            f1 = 0.0F;
        double d = (double)(flightmodel.Energy - Energy) * 0.1019D;
        double d1 = 1.0D + d * 0.00125D;
        if(d1 > 1.2D)
            d1 = 1.2D;
        if(d1 < 0.80000000000000004D)
            d1 = 0.80000000000000004D;
        float f2 = (float)d1 * (7E-005F * f * f1);
        if(danger == null || f2 > oldDanCoeff)
            danger = flightmodel;
        oldDanCoeff = f2;
        dangerAggressiveness += f2 * (float)i;
        if(dangerAggressiveness > 1.0F)
            dangerAggressiveness = 1.0F;
    }

    public float getDangerAggressiveness()
    {
        return dangerAggressiveness;
    }

    public float getMaxHeightSpeed(float f)
    {
        if(f < HofVmax)
            return Vmax + (VmaxH - Vmax) * (f / HofVmax);
        float f1 = (f - HofVmax) / HofVmax;
        f1 = 1.0F - 1.5F * f1;
        if(f1 < 0.0F)
            f1 = 0.0F;
        return VmaxH * f1;
    }

    public float getMinHeightTurn(float f)
    {
        return Wing.T_turn;
    }

    public void setShotAtFriend(float f)
    {
        distToFriend = f;
        shotAtFriend = 30;
    }

    public boolean hasCourseWeaponBullets()
    {
        if(actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI)
            return CT.Weapons[1] != null && CT.Weapons[1][0] != null && CT.Weapons[1][0].countBullets() != 0;
        if(actor instanceof com.maddox.il2.objects.air.AR_234)
            return false;
        else
            return CT.Weapons[0] != null && CT.Weapons[0][0] != null && CT.Weapons[0][0].countBullets() != 0 || CT.Weapons[1] != null && CT.Weapons[1][0] != null && CT.Weapons[1][0].countBullets() != 0;
    }

    public boolean hasBombs()
    {
        if(CT.Weapons[3] != null)
        {
            for(int i = 0; i < CT.Weapons[3].length; i++)
                if(CT.Weapons[3][i] != null && CT.Weapons[3][i].countBullets() != 0)
                    return true;

        }
        return false;
    }

    public boolean hasRockets()
    {
        if(CT.Weapons[2] != null)
        {
            for(int i = 0; i < CT.Weapons[2].length; i++)
                if(CT.Weapons[2][i] != null && CT.Weapons[2][i].countBullets() != 0)
                    return true;

        }
        return false;
    }

    public boolean canAttack()
    {
        return (!Group.isWingman(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor)) || aggressiveWingman) && isOk() && hasCourseWeaponBullets();
    }

    public void update(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            headTurn(f);
        if(showFM)
            OutCT(20);
        super.update(f);
        callSuperUpdate = true;
        decDangerAggressiveness();
        if(Loc.z < -20D)
            ((com.maddox.il2.objects.air.Aircraft)actor).postEndAction(0.0D, actor, 4, null);
        LandHQ = (float)com.maddox.il2.engine.Engine.land().HQ_Air(Loc.x, Loc.y);
        Po.set(Vwld);
        Po.scale(3D);
        Po.add(Loc);
        LandHQ = (float)java.lang.Math.max(LandHQ, com.maddox.il2.engine.Engine.land().HQ_Air(Po.x, Po.y));
        Alt = (float)Loc.z - LandHQ;
        indSpeed = getSpeed() * (float)java.lang.Math.sqrt(Density / 1.225F);
        if(!Gears.onGround() && isOk() && Alt > 8F)
        {
            if(AOA > AOA_Crit - 2.0F)
                Or.increment(0.0F, 0.05F * (AOA_Crit - 2.0F - AOA), 0.0F);
            if(AOA < -5F)
                Or.increment(0.0F, 0.05F * (-5F - AOA), 0.0F);
        }
        if(!frequentControl && (com.maddox.rts.Time.tickCounter() + actor.hashCode()) % 4 != 0)
            return;
        turnOffTheWeapon();
        maxG = 3.5F + (float)Skill * 0.5F;
        Or.wrap();
        if(mn_time > 10F && AOA > AOA_Crit + 5F && isStallable() && stallable)
            safe_set_maneuver(20);
        switch(maneuver)
        {
        case 58: // ':'
        default:
            break;

        case 0: // '\0'
            target_ground = null;
            break;

        case 1: // '\001'
            dryFriction = 8F;
            CT.FlapsControl = 0.0F;
            CT.BrakeControl = 1.0F;
            break;

        case 48: // '0'
            if(mn_time >= 1.0F)
                pop();
            break;

        case 44: // ','
            if(Gears.onGround() && Vwld.length() < 0.30000001192092896D && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 4)
            {
                if(Group != null)
                    Group.delAircraft((com.maddox.il2.objects.air.Aircraft)actor);
                if((actor instanceof com.maddox.il2.objects.air.TypeGlider) || (actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                    break;
                com.maddox.il2.ai.World.cur();
                if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)actor).postEndAction(60D, actor, 3, null);
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, actor);
            }
            if(first)
            {
                AP.setStabAll(false);
                CT.ElevatorControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.07F, 0.4F);
                CT.AileronControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.15F, 0.15F);
            }
            break;

        case 7: // '\007'
            wingman(false);
            setSpeedMode(9);
            if(getW().x <= 0.0D)
            {
                CT.AileronControl = -1F;
                CT.RudderControl = 1.0F;
            } else
            {
                CT.AileronControl = 1.0F;
                CT.RudderControl = -1F;
            }
            float f1 = Or.getKren();
            if(f1 > -90F && f1 < 90F)
            {
                float f4 = 0.01111F * (90F - java.lang.Math.abs(f1));
                CT.ElevatorControl = -0.08F * f4 * (Or.getTangage() - 3F);
            } else
            {
                float f5 = 0.01111F * (java.lang.Math.abs(f1) - 90F);
                CT.ElevatorControl = 0.08F * f5 * (Or.getTangage() - 3F);
            }
            if(getSpeed() < 1.7F * Vmin)
                pop();
            if(mn_time > 2.2F)
                pop();
            if(danger != null && (danger instanceof com.maddox.il2.ai.air.Pilot) && ((com.maddox.il2.ai.air.Maneuver)danger).target == this && Loc.distance(danger.Loc) > 400D)
                pop();
            break;

        case 8: // '\b'
            if(first && !isCapableOfACM())
            {
                if(Skill > 0)
                    pop();
                if(Skill > 1)
                    setReadyToReturn(true);
            }
            setSpeedMode(6);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            if(Or.getKren() > 0.0F)
                Ve.set(100D, -6D, 10D);
            else
                Ve.set(100D, 6D, 10D);
            tmpOr.transform(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            farTurnToDirection();
            if(Alt > 250F && mn_time > 8F || mn_time > 120F)
                pop();
            break;

        case 55: // '7'
            if(first && !isCapableOfACM())
            {
                if(Skill > 0)
                    pop();
                if(Skill > 1)
                    setReadyToReturn(true);
            }
            setSpeedMode(6);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            if(Leader != null && com.maddox.il2.engine.Actor.isAlive(Leader.actor) && mn_time < 2.5F)
            {
                if(Leader.Or.getKren() > 0.0F)
                    Ve.set(100D, -6D, 10D);
                else
                    Ve.set(100D, 6D, 10D);
            } else
            if(Or.getKren() > 0.0F)
                Ve.set(100D, -6D, 10D);
            else
                Ve.set(100D, 6D, 10D);
            tmpOr.transform(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            farTurnToDirection();
            if(Alt > 250F && mn_time > 8F || mn_time > 120F)
                pop();
            break;

        case 45: // '-'
            setSpeedMode(7);
            smConstPower = 0.8F;
            dA = Or.getKren();
            if(dA > 0.0F)
                dA -= 25F;
            else
                dA -= 335F;
            if(dA < -180F)
                dA += 360F;
            dA = -0.01F * dA;
            CT.AileronControl = dA;
            CT.ElevatorControl = -0.04F * (Or.getTangage() - 1.0F) + 0.002F * ((AP.way.curr().z() - (float)Loc.z) + 250F);
            if(mn_time > 60F)
            {
                mn_time = 0.0F;
                pop();
            }
            break;

        case 54: // '6'
            if(Vwld.length() > (double)(VminFLAPS * 2.0F))
                Vwld.scale(0.99500000476837158D);
            // fall through

        case 9: // '\t'
            if(first && !isCapableOfACM())
            {
                if(Skill > 0)
                    pop();
                if(Skill > 1)
                    setReadyToReturn(true);
            }
            setSpeedMode(4);
            smConstSpeed = 100F;
            dA = Or.getKren();
            if(dA > 0.0F)
                dA -= 50F;
            else
                dA -= 310F;
            if(dA < -180F)
                dA += 360F;
            dA = -0.01F * dA;
            CT.AileronControl = dA;
            dA = (-10F - Or.getTangage()) * 0.05F;
            CT.ElevatorControl = dA;
            if((double)getOverload() < 1.0D / java.lang.Math.abs(java.lang.Math.cos(dA)))
                CT.ElevatorControl += 1.0F * f;
            else
                CT.ElevatorControl -= 1.0F * f;
            if(Alt < 100F)
            {
                push(3);
                pop();
            }
            if(mn_time > 5F)
                pop();
            break;

        case 14: // '\016'
            setSpeedMode(6);
            if(first)
                AP.setStabAltitude(true);
            dA = Or.getKren();
            if((double)getOverload() < 1.0D / java.lang.Math.abs(java.lang.Math.cos(dA)))
                CT.ElevatorControl += 1.0F * f;
            else
                CT.ElevatorControl -= 1.0F * f;
            if(dA > 0.0F)
                dA -= 50F;
            else
                dA -= 310F;
            if(dA < -180F)
                dA += 360F;
            dA = -0.01F * dA;
            CT.AileronControl = dA;
            if(mn_time > 5F)
                pop();
            break;

        case 4: // '\004'
            CT.AileronControl = getW().x > 0.0D ? 1.0F : -1F;
            CT.ElevatorControl = 0.1F * (float)java.lang.Math.cos(com.maddox.il2.fm.FMMath.DEG2RAD(Or.getKren()));
            CT.RudderControl = 0.0F;
            if(getSpeedKMH() < 220F)
            {
                push(3);
                pop();
            }
            if(mn_time > 7F)
                pop();
            break;

        case 2: // '\002'
            minElevCoeff = 20F;
            if(first)
            {
                wingman(false);
                AP.setStabAll(false);
                CT.RudderControl = 0.0F;
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 10 && Alt < 80F)
                    com.maddox.il2.objects.sounds.Voice.speakPullUp((com.maddox.il2.objects.air.Aircraft)actor);
            }
            setSpeedMode(9);
            CT.BayDoorControl = 0.0F;
            CT.AirBrakeControl = 0.0F;
            CT.AileronControl = -0.04F * (dA = Or.getKren());
            CT.ElevatorControl = 1.0F + 0.3F * (float)getW().y;
            if(CT.ElevatorControl < 0.0F)
                CT.ElevatorControl = 0.0F;
            if(AOA > 15F)
                Or.increment(0.0F, (15F - AOA) * 0.5F * f, 0.0F);
            if(Alt < 10F && Vwld.z < 0.0D)
                Vwld.z *= 0.89999997615814209D;
            if(Vwld.z > 1.0D)
            {
                if(actor instanceof com.maddox.il2.objects.air.TypeGlider)
                    push(49);
                else
                    push(57);
                pop();
            }
            if(mn_time > 25F)
            {
                push(49);
                pop();
            }
            break;

        case 60: // '<'
            setSpeedMode(9);
            CT.RudderControl = 0.0F;
            CT.ElevatorControl = 1.0F;
            if(mn_time > 0.15F)
                pop();
            break;

        case 61: // '='
            CT.RudderControl = 0.0F;
            CT.ElevatorControl = -0.4F;
            if(mn_time > 0.2F)
                pop();
            break;

        case 3: // '\003'
            if(first && program[0] == 49)
                pop();
            setSpeedMode(6);
            CT.AileronControl = -0.04F * Or.getKren();
            dA = (getSpeedKMH() - 180F - Or.getTangage() * 10F - getVertSpeed() * 5F) * 0.004F;
            CT.ElevatorControl = dA;
            if(getSpeed() > Vmin * 1.2F && getVertSpeed() > 0.0F)
            {
                setSpeedMode(7);
                smConstPower = 0.7F;
                pop();
            }
            break;

        case 10: // '\n'
            AP.setStabAll(false);
            setSpeedMode(6);
            CT.AileronControl = -0.04F * Or.getKren();
            dA = CT.ElevatorControl;
            if(Or.getTangage() > 15F)
                dA -= (Or.getTangage() - 15F) * 0.1F * f;
            else
                dA = (((float)Vwld.length() / VminFLAPS) * 140F - 50F - Or.getTangage() * 20F) * 0.004F;
            dA += 0.5D * getW().y;
            CT.ElevatorControl = dA;
            if(Alt > 250F && mn_time > 6F || mn_time > 20F)
                pop();
            break;

        case 57: // '9'
            AP.setStabAll(false);
            setSpeedMode(9);
            CT.AileronControl = -0.04F * Or.getKren();
            dA = CT.ElevatorControl;
            if(Or.getTangage() > 25F)
                dA -= (Or.getTangage() - 25F) * 0.1F * f;
            else
                dA = (((float)Vwld.length() / VminFLAPS) * 140F - 50F - Or.getTangage() * 20F) * 0.004F;
            dA += 0.5D * getW().y;
            CT.ElevatorControl = dA;
            if(Alt > 150F || Alt > 100F && mn_time > 2.0F || mn_time > 3F)
                pop();
            break;

        case 11: // '\013'
            setSpeedMode(8);
            if(java.lang.Math.abs(Or.getKren()) < 90F)
            {
                CT.AileronControl = -0.04F * Or.getKren();
                if(Vwld.z > 0.0D || getSpeedKMH() < 270F)
                    dA = -0.04F;
                else
                    dA = 0.04F;
                CT.ElevatorControl = CT.ElevatorControl * 0.9F + dA * 0.1F;
            } else
            {
                CT.AileronControl = 0.04F * (180F - java.lang.Math.abs(Or.getKren()));
                if(Or.getTangage() > -25F)
                    dA = 0.33F;
                else
                if(Vwld.z > 0.0D || getSpeedKMH() < 270F)
                    dA = 0.04F;
                else
                    dA = -0.04F;
                CT.ElevatorControl = CT.ElevatorControl * 0.9F + dA * 0.1F;
            }
            if(Alt < 120F || mn_time > 4F)
                pop();
            break;

        case 12: // '\f'
            setSpeedMode(4);
            smConstSpeed = 80F;
            CT.AileronControl = -0.04F * Or.getKren();
            if(Vwld.length() > (double)(VminFLAPS * 2.0F))
                Vwld.scale(0.99500000476837158D);
            dA = -((float)Vwld.z / (java.lang.Math.abs(getSpeed()) + 1.0F) + 0.5F);
            if(dA < -0.1F)
                dA = -0.1F;
            CT.ElevatorControl = CT.ElevatorControl * 0.9F + dA * 0.1F + 0.3F * (float)getW().y;
            if(mn_time > 5F || Alt < 200F)
                pop();
            break;

        case 13: // '\r'
            if(first)
            {
                AP.setStabAll(false);
                submaneuver = (actor instanceof com.maddox.il2.objects.air.TypeFighter) ? 0 : 2;
            }
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = Or.getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA = -0.04F * dA;
                CT.AileronControl = dA;
                if(mn_time > 3F || java.lang.Math.abs(Or.getKren()) > 175F - 5F * (float)Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                dA = Or.getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA = -0.04F * dA;
                CT.AileronControl = dA;
                CT.RudderControl = -0.1F * getAOS();
                setSpeedMode(8);
                if(Or.getTangage() > -45F && getOverload() < maxG)
                    CT.ElevatorControl += 1.5F * f;
                else
                    CT.ElevatorControl -= 0.5F * f;
                if(Or.getTangage() < -44F)
                    submaneuver++;
                if((double)Alt < -5D * Vwld.z || mn_time > 5F)
                    pop();
                break;

            case 2: // '\002'
                setSpeedMode(8);
                CT.AileronControl = -0.04F * Or.getKren();
                dA = -((float)Vwld.z / (java.lang.Math.abs(getSpeed()) + 1.0F) + 0.707F);
                if(dA < -0.75F)
                    dA = -0.75F;
                CT.ElevatorControl = CT.ElevatorControl * 0.9F + dA * 0.1F + 0.5F * (float)getW().y;
                if((double)Alt < -5D * Vwld.z || mn_time > 5F)
                    pop();
                break;
            }
            if(Alt < 200F)
                pop();
            break;

        case 5: // '\005'
            dA = Or.getKren();
            if(dA < 0.0F)
                dA -= 270F;
            else
                dA -= 90F;
            if(dA < -180F)
                dA += 360F;
            dA = -0.02F * dA;
            CT.AileronControl = dA;
            if(mn_time > 5F || java.lang.Math.abs(java.lang.Math.abs(Or.getKren()) - 90F) < 1.0F)
                pop();
            break;

        case 6: // '\006'
            dA = Or.getKren() - 180F;
            if(dA < -180F)
                dA += 360F;
            CT.AileronControl = (float)((double)(-0.04F * dA) - 0.5D * getW().x);
            if(mn_time > 4F || java.lang.Math.abs(Or.getKren()) > 178F)
            {
                W.x = 0.0D;
                pop();
            }
            break;

        case 35: // '#'
            if(first)
            {
                AP.setStabAll(false);
                direction = Or.getKren();
                submaneuver = Or.getKren() <= 0.0F ? -1 : 1;
                tmpi = 0;
                setSpeedMode(9);
            }
            CT.AileronControl = 1.0F * (float)submaneuver;
            CT.RudderControl = 0.0F * (float)submaneuver;
            float f2 = Or.getKren();
            if(f2 > -90F && f2 < 90F)
            {
                float f6 = 0.01111F * (90F - java.lang.Math.abs(f2));
                CT.ElevatorControl = -0.08F * f6 * (Or.getTangage() - 3F);
            } else
            {
                float f7 = 0.01111F * (90F - java.lang.Math.abs(f2));
                CT.ElevatorControl = 0.08F * f7 * (Or.getTangage() - 3F);
            }
            if(Or.getKren() * direction < 0.0F)
                tmpi = 1;
            if(tmpi == 1 && (submaneuver <= 0 ? Or.getKren() < direction : Or.getKren() > direction) || mn_time > 17.5F)
                pop();
            break;

        case 22: // '\026'
            setSpeedMode(9);
            CT.AileronControl = -0.04F * Or.getKren();
            CT.ElevatorControl = -0.04F * (Or.getTangage() + 5F);
            CT.RudderControl = 0.0F;
            if(getSpeed() > Vmax || mn_time > 30F)
                pop();
            break;

        case 67: // 'C'
            minElevCoeff = 18F;
            if(first)
            {
                sub_Man_Count = 0;
                setSpeedMode(9);
                CT.PowerControl = 1.1F;
            }
            if(danger != null)
            {
                float f8 = 700F - (float)danger.Loc.distance(Loc);
                if(f8 < 0.0F)
                    f8 = 0.0F;
                f8 *= 0.00143F;
                float f3 = Or.getKren();
                if(sub_Man_Count == 0 || first)
                {
                    if(raAilShift < 0.0F)
                        raAilShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(0.6F, 1.0F);
                    else
                        raAilShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-1F, -0.6F);
                    raRudShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F);
                    raElevShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-0.8F, 0.8F);
                }
                CT.AileronControl = 0.9F * CT.AileronControl + 0.1F * raAilShift;
                CT.RudderControl = 0.95F * CT.RudderControl + 0.05F * raRudShift;
                if(f3 > -90F && f3 < 90F)
                    CT.ElevatorControl = -0.04F * (Or.getTangage() + 5F);
                else
                    CT.ElevatorControl = 0.05F * (Or.getTangage() + 5F);
                CT.ElevatorControl += 0.1F * raElevShift;
                sub_Man_Count++;
                if((float)sub_Man_Count >= 80F * (1.5F - f8) && f3 > -70F && f3 < 70F)
                    sub_Man_Count = 0;
                if(mn_time > 30F)
                    pop();
            } else
            {
                pop();
            }
            break;

        case 16: // '\020'
            if(first)
            {
                if(!isCapableOfACM())
                    pop();
                AP.setStabAll(false);
                setSpeedMode(6);
                if(getSpeed() < 0.75F * Vmax)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
                submaneuver = 0;
            }
            maxAOA = Vwld.z <= 0.0D ? 12F : 7F;
            switch(submaneuver)
            {
            case 0: // '\0'
                CT.ElevatorControl = 0.05F;
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(Or.getKren()) < 2.0F)
                    submaneuver++;
                break;

            case 1: // '\001'
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                dA = 0.5F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.4F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() > 80F)
                    submaneuver++;
                if(getSpeed() < Vmin * 1.5F)
                    pop();
                break;

            case 2: // '\002'
                CT.RudderControl = -0.1F * getAOS() * (getSpeed() <= 300F ? 0.0F : 1.0F);
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.4F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() < 0.0F)
                    submaneuver++;
                break;

            case 3: // '\003'
                CT.RudderControl = -0.1F * getAOS() * (getSpeed() <= 300F ? 0.0F : 1.0F);
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 0.2F * f;
                if(Or.getTangage() < -60F)
                    submaneuver++;
                break;

            case 4: // '\004'
                if(Or.getTangage() > -45F)
                {
                    CT.AileronControl = -0.04F * Or.getKren();
                    maxAOA = 3.5F;
                }
                CT.RudderControl = -0.1F * getAOS();
                dA = 0.5F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 1.0F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() > 3F || Vwld.z > 0.0D)
                    pop();
                break;
            }
            break;

        case 17: // '\021'
            if(first)
            {
                if(Alt < 1000F)
                    pop();
                else
                if(getSpeed() < Vmin * 1.2F)
                {
                    push();
                    push(22);
                    pop();
                } else
                {
                    push(18);
                    push(19);
                    pop();
                }
            } else
            {
                pop();
            }
            break;

        case 19: // '\023'
            if(first)
            {
                if(Alt < 1000F)
                {
                    pop();
                    break;
                }
                AP.setStabAll(false);
                submaneuver = 0;
            }
            maxAOA = Vwld.z <= 0.0D ? 12F : 7F;
            switch(submaneuver)
            {
            case 0: // '\0'
                CT.ElevatorControl = 0.05F;
                CT.AileronControl = 0.04F * (Or.getKren() <= 0.0F ? -180F + Or.getKren() : 180F - Or.getKren());
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(Or.getKren()) > 178F)
                    submaneuver++;
                break;

            case 1: // '\001'
                setSpeedMode(7);
                smConstPower = 0.5F;
                CT.AileronControl = 0.0F;
                CT.RudderControl = -0.1F * getAOS();
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 1.2F * f;
                if(Or.getTangage() < -60F)
                    submaneuver++;
                break;

            case 2: // '\002'
                if(Or.getTangage() > -45F)
                {
                    CT.AileronControl = -0.04F * Or.getKren();
                    setSpeedMode(9);
                    maxAOA = 7F;
                }
                CT.RudderControl = -0.1F * getAOS();
                dA = 0.5F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.8F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() > AOA - 1.0F || Vwld.z > 0.0D)
                    pop();
                break;
            }
            break;

        case 18: // '\022'
            if(first)
            {
                if(!isCapableOfACM())
                    pop();
                if(getSpeed() < Vmax * 0.75F)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
                AP.setStabAll(false);
                submaneuver = 0;
                setSpeedMode(6);
            }
            maxAOA = Vwld.z <= 0.0D ? 12F : 7F;
            switch(submaneuver)
            {
            case 0: // '\0'
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(Or.getKren()) < 2.0F)
                    submaneuver++;
                break;

            case 1: // '\001'
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.4F * f;
                else
                    CT.ElevatorControl += 0.8F * f;
                if(Or.getTangage() > 80F)
                    submaneuver++;
                if(getSpeed() < Vmin * 1.5F)
                    pop();
                break;

            case 2: // '\002'
                CT.RudderControl = -0.1F * getAOS() * (getSpeed() <= 300F ? 0.0F : 1.0F);
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.4F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() < 12F)
                    submaneuver++;
                break;

            case 3: // '\003'
                if(java.lang.Math.abs(Or.getKren()) < 60F)
                    CT.ElevatorControl = 0.05F;
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(Or.getKren()) < 30F)
                    submaneuver++;
                break;

            case 4: // '\004'
                pop();
                break;
            }
            break;

        case 15: // '\017'
            if(first && getSpeed() < 0.35F * (Vmin + Vmax))
            {
                pop();
            } else
            {
                push(8);
                pop();
                CT.RudderControl = -0.1F * getAOS();
                if(Or.getKren() < 0.0F)
                    CT.AileronControl = -0.04F * (Or.getKren() + 30F);
                else
                    CT.AileronControl = -0.04F * (Or.getKren() - 30F);
                if(mn_time > 7.5F)
                    pop();
            }
            break;

        case 20: // '\024'
            if(first)
            {
                wingman(false);
                setSpeedMode(6);
            }
            if(!isCapableOfBMP())
            {
                setReadyToDie(true);
                pop();
            }
            if(getW().z > 0.0D)
                CT.RudderControl = 1.0F;
            else
                CT.RudderControl = -1F;
            if(AOA > AOA_Crit - 4F)
                Or.increment(0.0F, 0.01F * (AOA_Crit - 4F - AOA), 0.0F);
            if(AOA < -5F)
                Or.increment(0.0F, 0.01F * (-5F - AOA), 0.0F);
            if(AOA < AOA_Crit - 1.0F)
                pop();
            if(mn_time > 14F - (float)Skill * 4F || Alt < 50F)
                pop();
            break;

        case 21: // '\025'
            AP.setWayPoint(true);
            if(mn_time > 300F)
                pop();
            if(isTick(256, 0) && !actor.isTaskComplete() && (AP.way.isLast() && AP.getWayPointDistance() < 1500F || AP.way.isLanding()))
                com.maddox.il2.ai.World.onTaskComplete(actor);
            if(((com.maddox.il2.objects.air.Aircraft)actor).aircIndex() == 0 && !isReadyToReturn())
            {
                com.maddox.il2.ai.World.cur();
                if(com.maddox.il2.ai.World.getPlayerAircraft() != null)
                {
                    com.maddox.il2.ai.World.cur();
                    if(((com.maddox.il2.objects.air.Aircraft)actor).getRegiment() == com.maddox.il2.ai.World.getPlayerAircraft().getRegiment())
                    {
                        float f9 = 1E+012F;
                        if(AP.way.curr().Action == 3)
                        {
                            f9 = AP.getWayPointDistance();
                        } else
                        {
                            int i = AP.way.Cur();
                            AP.way.next();
                            if(AP.way.curr().Action == 3)
                                f9 = AP.getWayPointDistance();
                            AP.way.setCur(i);
                        }
                        if(Speak5minutes == 0 && 22000F < f9 && f9 < 30000F)
                        {
                            com.maddox.il2.objects.sounds.Voice.speak5minutes((com.maddox.il2.objects.air.Aircraft)actor);
                            Speak5minutes = 1;
                        }
                        if(Speak1minute == 0 && f9 < 10000F)
                        {
                            com.maddox.il2.objects.sounds.Voice.speak1minute((com.maddox.il2.objects.air.Aircraft)actor);
                            Speak1minute = 1;
                            Speak5minutes = 1;
                        }
                        if((WeWereInGAttack || WeWereInAttack) && mn_time > 5F)
                        {
                            if(!SpeakMissionAccomplished)
                            {
                                boolean flag = false;
                                int j = AP.way.Cur();
                                if(AP.way.curr().Action == 3 || AP.way.curr().getTarget() != null)
                                    break;
                                while(AP.way.Cur() < AP.way.size() - 1) 
                                {
                                    AP.way.next();
                                    if(AP.way.curr().Action == 3 || AP.way.curr().getTarget() != null)
                                        flag = true;
                                }
                                AP.way.setCur(j);
                                if(!flag)
                                {
                                    com.maddox.il2.objects.sounds.Voice.speakMissionAccomplished((com.maddox.il2.objects.air.Aircraft)actor);
                                    SpeakMissionAccomplished = true;
                                }
                            }
                            if(!SpeakMissionAccomplished)
                            {
                                Speak5minutes = 0;
                                Speak1minute = 0;
                                SpeakBeginGattack = 0;
                            }
                            WeWereInGAttack = false;
                            WeWereInAttack = false;
                        }
                    }
                }
            }
            if(((actor instanceof com.maddox.il2.objects.air.TypeBomber) || (actor instanceof com.maddox.il2.objects.air.TypeTransport)) && AP.way.curr() != null && AP.way.curr().Action == 3 && (AP.way.curr().getTarget() == null || (actor instanceof com.maddox.il2.objects.air.Scheme4)))
            {
                double d = Loc.z - com.maddox.il2.ai.World.land().HQ(Loc.x, Loc.y);
                if(d < 0.0D)
                    d = 0.0D;
                if((double)AP.getWayPointDistance() < (double)getSpeed() * java.lang.Math.sqrt(d * 0.20386999845504761D) && !bombsOut)
                {
                    if(CT.Weapons[3] != null && CT.Weapons[3][0] != null && CT.Weapons[3][0].countBullets() != 0 && !(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara))
                        com.maddox.il2.objects.sounds.Voice.airSpeaks((com.maddox.il2.objects.air.Aircraft)actor, 85, 1);
                    bombsOut = true;
                    AP.way.curr().Action = 0;
                    if(Group != null)
                        Group.dropBombs();
                }
            }
            setSpeedMode(3);
            if(AP.way.isLandingOnShip() && AP.way.isLanding())
            {
                AP.way.landingAirport.rebuildLandWay(this);
                if(CT.bHasCockpitDoorControl)
                    AS.setCockpitDoor(actor, 1);
            }
            break;

        case 23: // '\027'
            if(first)
            {
                wingman(false);
                if(getSpeedKMH() < Vmin * 1.25F)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
            }
            setSpeedMode(6);
            CT.AileronControl = -0.04F * Or.getKren();
            CT.RudderControl = -0.1F * getAOS();
            if(Or.getTangage() < 70F && getOverload() < maxG && AOA < 14F)
                CT.ElevatorControl += 0.5F * f;
            else
                CT.ElevatorControl -= 0.5F * f;
            if(Vwld.z < 1.0D)
                pop();
            break;

        case 24: // '\030'
            if(Leader == null || !com.maddox.il2.engine.Actor.isAlive(Leader.actor) || !((com.maddox.il2.ai.air.Maneuver)Leader).isOk() || ((com.maddox.il2.ai.air.Maneuver)Leader).isBusy() && (!(Leader instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)Leader).isRealMode()))
            {
                set_maneuver(0);
                break;
            }
            if(actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                if(Leader.AP.way.curr().Action != 0 && Leader.AP.way.curr().Action != 1)
                    set_maneuver(49);
            } else
            if(Leader.getSpeed() < 30F || Leader.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(Leader.Loc.x, Leader.Loc.y) < 50D)
            {
                airClient = Leader;
                push();
                push(59);
                pop();
                break;
            }
            if(!Leader.AP.way.isLanding())
            {
                AP.way.setCur(Leader.AP.way.Cur());
                if(Leader.Wingman != this)
                {
                    if(!bombsOut && ((com.maddox.il2.ai.air.Maneuver)Leader).bombsOut)
                    {
                        bombsOut = true;
                        for(com.maddox.il2.ai.air.Maneuver maneuver1 = this; maneuver1.Wingman != null;)
                        {
                            maneuver1 = (com.maddox.il2.ai.air.Maneuver)maneuver1.Wingman;
                            maneuver1.bombsOut = true;
                        }

                    }
                    if(CT.BayDoorControl != Leader.CT.BayDoorControl)
                    {
                        CT.BayDoorControl = Leader.CT.BayDoorControl;
                        for(com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)this; pilot.Wingman != null;)
                        {
                            pilot = (com.maddox.il2.ai.air.Pilot)pilot.Wingman;
                            pilot.CT.BayDoorControl = CT.BayDoorControl;
                        }

                    }
                }
            } else
            {
                if(Leader.Wingman != this)
                {
                    push(8);
                    push(8);
                    push(com.maddox.il2.ai.World.Rnd().nextBoolean() ? 8 : 48);
                    push(com.maddox.il2.ai.World.Rnd().nextBoolean() ? 8 : 48);
                    pop();
                }
                Leader = null;
                pop();
                break;
            }
            airClient = Leader;
            tmpOr.setAT0(airClient.Vwld);
            tmpOr.increment(0.0F, airClient.getAOA(), 0.0F);
            Ve.set(followOffset);
            Ve.x -= 300D;
            tmpV3f.sub(followTargShift, followCurShift);
            if(tmpV3f.lengthSquared() < 0.5D)
                followTargShift.set(com.maddox.il2.ai.World.cur().rnd.nextFloat(-0F, 10F), com.maddox.il2.ai.World.cur().rnd.nextFloat(-5F, 5F), com.maddox.il2.ai.World.cur().rnd.nextFloat(-3.5F, 3.5F));
            tmpV3f.normalize();
            tmpV3f.scale(2.0F * f);
            followCurShift.add(tmpV3f);
            Ve.add(followCurShift);
            tmpOr.transform(Ve, Po);
            Po.scale(-1D);
            Po.add(airClient.Loc);
            Ve.sub(Po, Loc);
            Or.transformInv(Ve);
            dist = (float)Ve.length();
            if(followOffset.x > 600D)
            {
                Ve.set(followOffset);
                Ve.x -= 0.5D * followOffset.x;
                tmpOr.transform(Ve, Po);
                Po.scale(-1D);
                Po.add(airClient.Loc);
                Ve.sub(Po, Loc);
                Or.transformInv(Ve);
            }
            Ve.normalize();
            if((double)dist > 600D + Ve.x * 400D)
            {
                push();
                push(53);
                pop();
            } else
            {
                if((actor instanceof com.maddox.il2.objects.air.TypeTransport) && (double)Vmax < 70D)
                    farTurnToDirection(6.2F);
                else
                    attackTurnToDirection(dist, f, 10F);
                setSpeedMode(10);
                tailForStaying = Leader;
                tailOffset.set(followOffset);
                tailOffset.scale(-1D);
            }
            break;

        case 65: // 'A'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                CT.dropFuelTanks();
            }
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(airClient.actor) || !isOk())
            {
                set_maneuver(0);
            } else
            {
                com.maddox.il2.ai.air.Maneuver maneuver2 = (com.maddox.il2.ai.air.Maneuver)airClient;
                com.maddox.il2.ai.air.Maneuver maneuver4 = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.ai.air.Maneuver)airClient).danger;
                if(maneuver2.getDangerAggressiveness() >= 1.0F - (float)Skill * 0.2F && maneuver4 != null && hasCourseWeaponBullets())
                {
                    com.maddox.il2.objects.sounds.Voice.speakCheckYour6((com.maddox.il2.objects.air.Aircraft)maneuver2.actor, (com.maddox.il2.objects.air.Aircraft)maneuver4.actor);
                    com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)actor, 1);
                    set_task(6);
                    target = maneuver4;
                    set_maneuver(27);
                }
                Ve.sub(airClient.Loc, Loc);
                Or.transformInv(Ve);
                dist = (float)Ve.length();
                Ve.normalize();
                attackTurnToDirection(dist, f, 10F + (float)Skill * 8F);
                if(Alt > 50F)
                    setSpeedMode(1);
                else
                    setSpeedMode(6);
                tailForStaying = airClient;
                tailOffset.set(followOffset);
                tailOffset.scale(-1D);
                if((double)dist > 600D + Ve.x * 400D && get_maneuver() != 27)
                {
                    push();
                    push(53);
                    pop();
                }
            }
            break;

        case 53: // '5'
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(airClient.actor) || !isOk())
            {
                airClient = null;
                set_maneuver(0);
            } else
            {
                maxAOA = 5F;
                Ve.set(followOffset);
                Ve.x -= 300D;
                tmpOr.setAT0(airClient.Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(Ve, Po);
                Po.scale(-1D);
                Po.add(airClient.Loc);
                Ve.sub(Po, Loc);
                Or.transformInv(Ve);
                dist = (float)Ve.length();
                Ve.normalize();
                if(Vmax < 83F)
                    farTurnToDirection(8.5F);
                else
                    farTurnToDirection(7F);
                float f10 = (Energy - airClient.Energy) * 0.1019F;
                if((double)f10 < -50D + followOffset.z)
                    setSpeedMode(9);
                else
                    setSpeedMode(10);
                tailForStaying = airClient;
                tailOffset.set(followOffset);
                tailOffset.scale(-1D);
                if((double)dist < 500D + Ve.x * 200D)
                {
                    pop();
                } else
                {
                    if(AOA > 12F && Alt > 15F)
                        Or.increment(0.0F, 12F - AOA, 0.0F);
                    if(mn_time > 30F && (Ve.x < 0.0D || dist > 10000F))
                        pop();
                }
            }
            break;

        case 68: // 'D'
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(airClient.actor) || !isOk())
            {
                set_maneuver(0);
            } else
            {
                com.maddox.il2.ai.air.Maneuver maneuver3 = (com.maddox.il2.ai.air.Maneuver)airClient;
                com.maddox.il2.ai.air.Maneuver maneuver5 = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.ai.air.Maneuver)airClient).danger;
                if(maneuver3.getDangerAggressiveness() >= 1.0F - (float)Skill * 0.3F && maneuver5 != null && hasCourseWeaponBullets())
                {
                    tmpV3d.sub(maneuver3.Vwld, maneuver5.Vwld);
                    if(tmpV3d.length() < 170D)
                    {
                        set_task(6);
                        target = maneuver5;
                        set_maneuver(27);
                    }
                }
                maxAOA = 5F;
                Ve.set(followOffset);
                Ve.x -= 300D;
                tmpOr.setAT0(airClient.Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(Ve, Po);
                Po.scale(-1D);
                Po.add(airClient.Loc);
                Ve.sub(Po, Loc);
                Or.transformInv(Ve);
                dist = (float)Ve.length();
                Ve.normalize();
                if(Vmax < 83F)
                    farTurnToDirection(8.5F);
                else
                    farTurnToDirection(7F);
                setSpeedMode(10);
                tailForStaying = airClient;
                tailOffset.set(followOffset);
                tailOffset.scale(-1D);
                if((double)dist < 500D + Ve.x * 200D)
                {
                    pop();
                } else
                {
                    if(AOA > 12F && Alt > 15F)
                        Or.increment(0.0F, 12F - AOA, 0.0F);
                    if(mn_time > 30F && (Ve.x < 0.0D || dist > 10000F))
                        pop();
                }
            }
            break;

        case 59: // ';'
            if(airClient == null || !com.maddox.il2.engine.Actor.isValid(airClient.actor) || !isOk())
            {
                airClient = null;
                set_maneuver(0);
            } else
            {
                maxAOA = 5F;
                if(first)
                    followOffset.set(1000F * (float)java.lang.Math.sin((float)beNearOffsPhase * 0.7854F), 1000F * (float)java.lang.Math.cos((float)beNearOffsPhase * 0.7854F), -400D);
                Ve.set(followOffset);
                Ve.x -= 300D;
                tmpOr.setAT0(airClient.Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(Ve, Po);
                Po.scale(-1D);
                Po.add(airClient.Loc);
                Ve.sub(Po, Loc);
                Or.transformInv(Ve);
                dist = (float)Ve.length();
                Ve.normalize();
                farTurnToDirection();
                setSpeedMode(2);
                tailForStaying = airClient;
                tailOffset.set(followOffset);
                tailOffset.scale(-1D);
                if(dist < 300F)
                {
                    beNearOffsPhase++;
                    if(beNearOffsPhase > 3)
                        beNearOffsPhase = 0;
                    float f12 = (float)java.lang.Math.sqrt(followOffset.x * followOffset.x + followOffset.y * followOffset.y);
                    followOffset.set(f12 * (float)java.lang.Math.sin((float)beNearOffsPhase * 1.5708F), f12 * (float)java.lang.Math.cos((float)beNearOffsPhase * 1.5708F), followOffset.z);
                }
                if(AOA > 12F && Alt > 15F)
                    Or.increment(0.0F, 12F - AOA, 0.0F);
                if(mn_time > 15F && (Ve.x < 0.0D || dist > 3000F))
                    pop();
                if(mn_time > 30F)
                    pop();
            }
            break;

        case 63: // '?'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                CT.dropFuelTanks();
            }
            if(target == null || !com.maddox.il2.engine.Actor.isValid(target.actor) || target.isTakenMortalDamage() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(3);
            } else
            if(target.getSpeedKMH() < 45F && target.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(target.Loc.x, target.Loc.y) < 50D && target.actor.getArmy() != actor.getArmy())
            {
                target_ground = target.actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if((actor instanceof com.maddox.il2.objects.air.HE_LERCHE3) && ((com.maddox.il2.objects.air.HE_LERCHE3)actor).bToFire)
                {
                    CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.HE_LERCHE3)actor).bToFire = false;
                }
                if((actor instanceof com.maddox.il2.objects.air.TA_183) && ((com.maddox.il2.objects.air.TA_183)actor).bToFire)
                {
                    CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.TA_183)actor).bToFire = false;
                }
                if((actor instanceof com.maddox.il2.objects.air.TA_152C) && ((com.maddox.il2.objects.air.TA_152C)actor).bToFire)
                {
                    CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.TA_152C)actor).bToFire = false;
                }
                if(TargV.z == -400D)
                    fighterUnderBomber(f);
                else
                    fighterVsBomber(f);
                if(AOA > AOA_Crit - 2.0F && Alt > 15F)
                    Or.increment(0.0F, 0.01F * (AOA_Crit - 2.0F - AOA), 0.0F);
            }
            break;

        case 27: // '\033'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                CT.dropFuelTanks();
            }
            if(target == null || !com.maddox.il2.engine.Actor.isValid(target.actor) || target.isTakenMortalDamage() || target.actor.getArmy() == actor.getArmy() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(0);
            } else
            if(target.getSpeedKMH() < 45F && target.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(target.Loc.x, target.Loc.y) < 50D && target.actor.getArmy() != actor.getArmy())
            {
                target_ground = target.actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if(first && (actor instanceof com.maddox.il2.objects.air.TypeAcePlane))
                {
                    if(target != null && target.actor != null && target.actor.getArmy() != actor.getArmy())
                        target.Skill = 0;
                    if(danger != null && danger.actor != null && danger.actor.getArmy() != actor.getArmy())
                        danger.Skill = 0;
                }
                if(target.actor.getArmy() != actor.getArmy())
                    ((com.maddox.il2.ai.air.Maneuver)target).danger = this;
                if(isTick(64, 0))
                {
                    float f11 = (target.Energy - Energy) * 0.1019F;
                    Ve.sub(target.Loc, Loc);
                    Or.transformInv(Ve);
                    Ve.normalize();
                    float f13 = (470F + (float)Ve.x * 120F) - f11;
                    if(f13 < 0.0F)
                    {
                        clear_stack();
                        set_maneuver(62);
                    }
                }
                fighterVsFighter(f);
                setSpeedMode(9);
                if(AOA > AOA_Crit - 1.0F && Alt > 15F)
                    Or.increment(0.0F, 0.01F * (AOA_Crit - 1.0F - AOA), 0.0F);
                if(mn_time > 100F)
                {
                    target = null;
                    pop();
                }
            }
            break;

        case 62: // '>'
            if(target == null || !com.maddox.il2.engine.Actor.isValid(target.actor) || target.isTakenMortalDamage() || target.actor.getArmy() == actor.getArmy() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(0);
            } else
            if(target.getSpeedKMH() < 45F && target.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(target.Loc.x, target.Loc.y) < 50D && target.actor.getArmy() != actor.getArmy())
            {
                target_ground = target.actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if(first && (actor instanceof com.maddox.il2.objects.air.TypeAcePlane))
                {
                    if(target != null && target.actor != null && target.actor.getArmy() != actor.getArmy())
                        target.Skill = 0;
                    if(danger != null && danger.actor != null && danger.actor.getArmy() != actor.getArmy())
                        danger.Skill = 0;
                }
                if(target.actor.getArmy() != actor.getArmy())
                    ((com.maddox.il2.ai.air.Maneuver)target).danger = this;
                goodFighterVsFighter(f);
            }
            break;

        case 70: // 'F'
            checkGround = false;
            checkStrike = false;
            frequentControl = true;
            stallable = false;
            if(actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
                switch(submaneuver)
                {
                case 0: // '\0'
                    AP.setStabAll(false);
                    submaneuver++;
                    sub_Man_Count = 0;
                    // fall through

                case 1: // '\001'
                    if(sub_Man_Count == 0)
                        CT.AileronControl = com.maddox.il2.ai.World.cur().rnd.nextFloat(-0.15F, 0.15F);
                    CT.AirBrakeControl = 1.0F;
                    CT.PowerControl = 1.0F;
                    CT.ElevatorControl = com.maddox.il2.objects.air.Aircraft.cvt(Or.getTangage(), 0.0F, 60F, 1.0F, 0.0F);
                    if(Or.getTangage() > 30F)
                    {
                        submaneuver++;
                        sub_Man_Count = 0;
                    }
                    sub_Man_Count++;
                    break;

                case 2: // '\002'
                    CT.AileronControl = 0.0F;
                    CT.ElevatorControl = 0.0F;
                    CT.PowerControl = 0.1F;
                    Or.increment(0.0F, (float)((double)f * 0.10000000000000001D * (double)sub_Man_Count * (90D - (double)Or.getTangage())), 0.0F);
                    if(Or.getTangage() > 89F)
                    {
                        saveOr.set(Or);
                        submaneuver++;
                    }
                    sub_Man_Count++;
                    break;

                case 3: // '\003'
                    CT.ElevatorControl = 0.0F;
                    if(Alt > 10F)
                        CT.PowerControl = 0.33F;
                    else
                        CT.PowerControl = 0.0F;
                    if(Alt < 20F)
                    {
                        if(Vwld.z < -4D)
                            Vwld.z *= 0.94999999999999996D;
                        if(Vwld.lengthSquared() < 1.0D)
                            EI.setEngineStops();
                    }
                    Or.set(saveOr);
                    if(mn_time > 100F)
                    {
                        Vwld.set(0.0D, 0.0D, 0.0D);
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 12000L, actor);
                    }
                    break;
                }
            break;

        case 25: // '\031'
            wingman(false);
            if(AP.way.isLanding())
            {
                if(AP.way.isLandingOnShip())
                {
                    AP.way.landingAirport.rebuildLandWay(this);
                    if(CT.GearControl == 1.0F && CT.arrestorControl < 1.0F && !Gears.onGround())
                        AS.setArrestor(actor, 1);
                }
                if(first)
                {
                    AP.setWayPoint(true);
                    doDumpBombsPassively();
                    submaneuver = 0;
                }
                if((actor instanceof com.maddox.il2.objects.air.HE_LERCHE3) && Alt < 50F)
                    maneuver = 70;
                AP.way.curr().getP(Po);
                int k = AP.way.Cur();
                float f17 = (float)Loc.z - AP.way.last().z();
                AP.way.setCur(k);
                Alt = java.lang.Math.min(Alt, f17);
                Po.add(0.0D, 0.0D, -3D);
                Ve.sub(Po, Loc);
                float f20 = (float)Ve.length();
                boolean flag3 = Alt > 4.5F + Gears.H && AP.way.Cur() < 8;
                if(AP.way.isLandingOnShip())
                    flag3 = Alt > 4.5F + Gears.H && AP.way.Cur() < 8;
                if(flag3)
                {
                    AP.way.prev();
                    AP.way.curr().getP(Pc);
                    AP.way.next();
                    tmpV3f.sub(Po, Pc);
                    tmpV3f.normalize();
                    if(tmpV3f.dot(Ve) < 0.0D && f20 > 1000F && !TaxiMode)
                    {
                        AP.way.first();
                        push(10);
                        pop();
                        CT.GearControl = 0.0F;
                    }
                    float f25 = (float)tmpV3f.dot(Ve);
                    tmpV3f.scale(-f25);
                    tmpV3f.add(Po, tmpV3f);
                    tmpV3f.sub(Loc);
                    float f29 = 0.0005F * (3000F - f20);
                    if(f29 > 1.0F)
                        f29 = 1.0F;
                    if(f29 < 0.1F)
                        f29 = 0.1F;
                    float f31 = (float)tmpV3f.length();
                    if(f31 > 40F * f29)
                    {
                        f31 = 40F * f29;
                        tmpV3f.normalize();
                        tmpV3f.scale(f31);
                    }
                    float f33 = VminFLAPS;
                    if(AP.way.Cur() >= 6)
                    {
                        if(AP.way.isLandingOnShip())
                        {
                            if(com.maddox.il2.engine.Actor.isAlive(AP.way.landingAirport) && (AP.way.landingAirport instanceof com.maddox.il2.ai.AirportCarrier))
                            {
                                float f34 = (float)((com.maddox.il2.ai.AirportCarrier)AP.way.landingAirport).speedLen();
                                if(VminFLAPS < f34 + 10F)
                                    f33 = f34 + 10F;
                            }
                        } else
                        {
                            f33 = VminFLAPS * 1.2F;
                        }
                        if(f33 < 14F)
                            f33 = 14F;
                    } else
                    {
                        f33 = VminFLAPS * 2.0F;
                    }
                    double d4 = Vwld.length();
                    double d6 = (double)f33 - d4;
                    float f35 = 2.0F * f;
                    if(d6 > (double)f35)
                        d6 = f35;
                    if(d6 < (double)(-f35))
                        d6 = -f35;
                    Ve.normalize();
                    Ve.scale(d4);
                    Ve.add(tmpV3f);
                    Ve.sub(Vwld);
                    float f36 = (50F * f29 - f31) * f;
                    if(Ve.length() > (double)f36)
                    {
                        Ve.normalize();
                        Ve.scale(f36);
                    }
                    Vwld.add(Ve);
                    Vwld.normalize();
                    Vwld.scale(d4 + d6);
                    Loc.x += Vwld.x * (double)f;
                    Loc.y += Vwld.y * (double)f;
                    Loc.z += Vwld.z * (double)f;
                    Or.transformInv(tmpV3f);
                    tmpOr.setAT0(Vwld);
                    float f37 = 0.0F;
                    if(AP.way.isLandingOnShip())
                        f37 = 0.9F * (45F - Alt);
                    else
                        f37 = 0.7F * (20F - Alt);
                    if(f37 < 0.0F)
                        f37 = 0.0F;
                    tmpOr.increment(0.0F, 4F + f37, (float)(tmpV3f.y * 0.5D));
                    Or.interpolate(tmpOr, 0.5F * f);
                    callSuperUpdate = false;
                    W.set(0.0D, 0.0D, 0.0D);
                    CT.ElevatorControl = 0.05F + 0.3F * f37;
                    CT.RudderControl = (float)(-tmpV3f.y * 0.02D);
                    direction = Or.getAzimut();
                } else
                {
                    AP.setStabDirection(true);
                }
            } else
            {
                AP.setStabDirection(true);
            }
            dA = CT.ElevatorControl;
            AP.update(f);
            setSpeedControl(f);
            CT.ElevatorControl = dA;
            if(maneuver != 25)
                return;
            if(Alt > 60F)
            {
                if(Alt < 160F)
                    CT.FlapsControl = 0.32F;
                else
                    CT.FlapsControl = 0.0F;
                setSpeedMode(7);
                smConstPower = 0.2F;
                dA = java.lang.Math.min(130F + Alt, 270F);
                if(Vwld.z > 0.0D || getSpeedKMH() < dA)
                    dA = -1.2F * f;
                else
                    dA = 1.2F * f;
                CT.ElevatorControl = CT.ElevatorControl * 0.9F + dA * 0.1F;
            } else
            {
                minElevCoeff = 15F;
                if(AP.way.Cur() >= 6 || AP.way.Cur() == 0)
                {
                    if(Or.getTangage() < -5F)
                        Or.increment(0.0F, -Or.getTangage() - 5F, 0.0F);
                    if(Or.getTangage() > Gears.Pitch + 10F)
                        Or.increment(0.0F, -(Or.getTangage() - (Gears.Pitch + 10F)), 0.0F);
                }
                CT.FlapsControl = 1.0F;
                if(Vrel.length() < 1.0D)
                {
                    CT.FlapsControl = CT.BrakeControl = 0.0F;
                    if(!TaxiMode)
                    {
                        setSpeedMode(8);
                        if(AP.way.isLandingOnShip())
                        {
                            if(CT.getFlap() < 0.001F)
                                AS.setWingFold(actor, 1);
                            CT.BrakeControl = 1.0F;
                            if(CT.arrestorControl == 1.0F && Gears.onGround())
                                AS.setArrestor(actor, 0);
                            com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 25000L, actor);
                        } else
                        {
                            EI.setEngineStops();
                            if(EI.engines[0].getPropw() < 1.0F)
                            {
                                com.maddox.il2.ai.World.cur();
                                if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                                    com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 12000L, actor);
                            }
                        }
                    }
                }
                if(getSpeed() < VmaxFLAPS * 0.21F)
                    CT.FlapsControl = 0.0F;
                if(Vrel.length() < (double)(VmaxFLAPS * 0.25F) && wayCurPos == null && !AP.way.isLandingOnShip())
                {
                    TaxiMode = true;
                    AP.way.setCur(0);
                    return;
                }
                if(getSpeed() > VminFLAPS * 0.6F && Alt < 10F)
                {
                    setSpeedMode(8);
                    if(AP.way.isLandingOnShip() && CT.bHasArrestorControl)
                    {
                        if(Vwld.z < -5.5D)
                            Vwld.z *= 0.94999998807907104D;
                        if(Vwld.z > 0.0D)
                            Vwld.z *= 0.9100000262260437D;
                    } else
                    {
                        if(Vwld.z < -0.60000002384185791D)
                            Vwld.z *= 0.93999999761581421D;
                        if(Vwld.z < -2.5D)
                            Vwld.z = -2.5D;
                        if(Vwld.z > 0.0D)
                            Vwld.z *= 0.9100000262260437D;
                    }
                }
                float f14 = Gears.Pitch - 2.0F;
                if(f14 < 5F)
                    f14 = 5F;
                if(Alt < 7F && Or.getTangage() < f14 || AP.way.isLandingOnShip())
                    CT.ElevatorControl += 1.5F * f;
                CT.ElevatorControl += 0.05000000074505806D * getW().y;
                if(Gears.onGround())
                {
                    if(Gears.Pitch > 5F)
                    {
                        if(Or.getTangage() < Gears.Pitch)
                            CT.ElevatorControl += 1.5F * f;
                        if(!AP.way.isLandingOnShip())
                            CT.ElevatorControl += 0.30000001192092896D * getW().y;
                    } else
                    {
                        CT.ElevatorControl = 0.0F;
                    }
                    if(!TaxiMode)
                    {
                        AFo.setDeg(Or.getAzimut(), direction);
                        CT.RudderControl = 8F * AFo.getDiffRad() + 0.5F * (float)getW().z;
                    } else
                    {
                        CT.RudderControl = 0.0F;
                    }
                }
            }
            AP.way.curr().getP(Po);
            return;

        case 66: // 'B'
            if(!isCapableOfTaxiing() || EI.getThrustOutput() < 0.01F)
            {
                set_task(3);
                maneuver = 0;
                set_maneuver(49);
                AP.setStabAll(false);
            } else
            {
                if(AS.isPilotDead(0))
                {
                    set_task(3);
                    maneuver = 0;
                    set_maneuver(44);
                    setSpeedMode(8);
                    smConstPower = 0.0F;
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)actor).postEndAction(6000D, actor, 3, null);
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 0x493e0L, actor);
                    return;
                }
                P.x = Loc.x;
                P.y = Loc.y;
                P.z = Loc.z;
                Vcur.set(Vwld);
                if(wayCurPos == null)
                {
                    com.maddox.il2.ai.World.cur().airdrome.findTheWay((com.maddox.il2.ai.air.Pilot)this);
                    wayPrevPos = wayCurPos = getNextAirdromeWayPoint();
                }
                if(wayCurPos != null)
                {
                    com.maddox.il2.ai.air.Point_Any point_any = wayCurPos;
                    com.maddox.il2.ai.air.Point_Any point_any1 = wayPrevPos;
                    Pcur.set((float)P.x, (float)P.y);
                    float f21 = Pcur.distance(point_any);
                    float f23 = Pcur.distance(point_any1);
                    V_to.sub(point_any, Pcur);
                    V_to.normalize();
                    float f26 = 5F + 0.1F * f21;
                    if(f26 > 12F)
                        f26 = 12F;
                    if(f26 > 0.9F * VminFLAPS)
                        f26 = 0.9F * VminFLAPS;
                    if(curAirdromePoi < airdromeWay.length && f21 < 15F || f21 < 4F)
                    {
                        f26 = 0.0F;
                        com.maddox.il2.ai.air.Point_Any point_any2 = getNextAirdromeWayPoint();
                        if(point_any2 == null)
                        {
                            CT.setPowerControl(0.0F);
                            Loc.set(P);
                            Loc.set(Loc);
                            if(finished)
                                return;
                            finished = true;
                            int l = 1000;
                            if(wayCurPos != null)
                                l = 0x249f00;
                            actor.collide(true);
                            Vwld.set(0.0D, 0.0D, 0.0D);
                            CT.setPowerControl(0.0F);
                            EI.setCurControlAll(true);
                            EI.setEngineStops();
                            com.maddox.il2.ai.World.cur();
                            if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                            {
                                com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + (long)l, actor);
                                setStationedOnGround(true);
                                maneuver = 0;
                                set_maneuver(44);
                            }
                            return;
                        }
                        wayPrevPos = wayCurPos;
                        wayCurPos = point_any2;
                    }
                    V_to.scale(f26);
                    float f30 = 2.0F * f;
                    Vdiff.set(V_to);
                    Vdiff.sub(Vcur);
                    float f32 = (float)Vdiff.length();
                    if(f32 > f30)
                    {
                        Vdiff.normalize();
                        Vdiff.scale(f30);
                    }
                    Vcur.add(Vdiff);
                    tmpOr.setYPR(com.maddox.il2.fm.FMMath.RAD2DEG((float)Vcur.direction()), Or.getPitch(), 0.0F);
                    Or.interpolate(tmpOr, 0.2F);
                    Vwld.x = Vcur.x;
                    Vwld.y = Vcur.y;
                    P.x += Vcur.x * (double)f;
                    P.y += Vcur.y * (double)f;
                } else
                {
                    wayPrevPos = wayCurPos = new Point_Null((float)Loc.x, (float)Loc.y);
                    com.maddox.il2.ai.World.cur();
                    if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    {
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, actor);
                        setStationedOnGround(true);
                    }
                }
                Loc.set(P);
                Loc.set(Loc);
                return;
            }
            break;

        case 49: // '1'
            emergencyLanding(f);
            break;

        case 64: // '@'
            if(actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                pop();
                break;
            }
            if(actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
            {
                boolean flag1 = com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && (AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier);
                if(!flag1)
                    callSuperUpdate = false;
            }
            CT.BrakeControl = 1.0F;
            CT.ElevatorControl = 0.5F;
            CT.setPowerControl(0.0F);
            EI.setCurControlAll(false);
            setSpeedMode(0);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                EI.setCurControl(submaneuver, true);
                if(EI.engines[submaneuver].getStage() == 0)
                {
                    setSpeedMode(0);
                    CT.setPowerControl(0.05F);
                    EI.engines[submaneuver].setControlThrottle(0.2F);
                    EI.toggle();
                    if((actor instanceof com.maddox.il2.objects.air.BI_1) || (actor instanceof com.maddox.il2.objects.air.BI_6))
                    {
                        pop();
                        break;
                    }
                }
            }
            if(EI.engines[submaneuver].getStage() == 6)
            {
                setSpeedMode(0);
                EI.engines[submaneuver].setControlThrottle(0.0F);
                submaneuver++;
                CT.setPowerControl(0.0F);
                if(submaneuver > EI.getNum() - 1)
                {
                    EI.setCurControlAll(true);
                    pop();
                }
            }
            break;

        case 26: // '\032'
            float f15 = Alt;
            float f18 = 0.4F;
            if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && (AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier))
            {
                f15 -= ((com.maddox.il2.ai.AirportCarrier)AP.way.takeoffAirport).height();
                f18 = 0.95F;
                if(Alt < 9F && Vwld.z < 0.0D)
                    Vwld.z *= 0.84999999999999998D;
                if(CT.bHasCockpitDoorControl)
                    AS.setCockpitDoor(actor, 1);
            }
            if(first)
            {
                setCheckGround(false);
                CT.BrakeControl = 1.0F;
                CT.GearControl = 1.0F;
                CT.PowerControl = 0.0F;
                if(CT.bHasArrestorControl)
                    AS.setArrestor(actor, 0);
                setSpeedMode(8);
                if(f15 > 7.21F && AP.way.Cur() == 0)
                {
                    EI.setEngineRunning();
                    com.maddox.il2.objects.air.Aircraft.debugprintln(actor, "in the air - engines running!.");
                }
                if(!com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport))
                    direction = actor.pos.getAbsOrient().getAzimut();
                if(actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
                {
                    maneuver = 69;
                    break;
                }
            }
            if(Gears.onGround())
            {
                if(EI.engines[0].getStage() == 0)
                {
                    CT.PowerControl = 0.0F;
                    setSpeedMode(8);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F && mn_time > 1.0F || mn_time > 8F)
                    {
                        push();
                        push(64);
                        submaneuver = 0;
                        maneuver = 0;
                        safe_pop();
                        break;
                    }
                } else
                {
                    Po.set(Loc);
                    Vpl.set(60D, 0.0D, 0.0D);
                    Or.transform(Vpl);
                    Po.add(Vpl);
                    Pd.set(Po);
                    if(canTakeoff)
                    {
                        CT.PowerControl = 1.1F;
                        setSpeedMode(9);
                    } else
                    {
                        setSpeedMode(8);
                        CT.BrakeControl = 1.0F;
                        boolean flag2 = true;
                        if(mn_time < 8F)
                            flag2 = false;
                        if(actor != com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)actor, 70F))
                            if(actor instanceof com.maddox.il2.objects.air.G4M2E)
                            {
                                if(com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)actor, 70F) != ((com.maddox.il2.objects.air.G4M2E)actor).typeDockableGetDrone())
                                    flag2 = false;
                            } else
                            {
                                flag2 = false;
                            }
                        if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && AP.way.takeoffAirport.takeoffRequest > 0)
                            flag2 = false;
                        if(flag2)
                        {
                            canTakeoff = true;
                            if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport))
                                AP.way.takeoffAirport.takeoffRequest = 300;
                        }
                    }
                }
                if(EI.engines[0].getStage() == 6 && CT.bHasWingControl && CT.getWing() > 0.001F)
                    AS.setWingFold(actor, 0);
            } else
            if(canTakeoff)
            {
                CT.PowerControl = 1.1F;
                setSpeedMode(9);
            }
            if(CT.FlapsControl == 0.0F && CT.getWing() < 0.001F)
                CT.FlapsControl = 0.4F;
            if(EI.engines[0].getStage() == 6 && CT.getPower() > f18)
            {
                CT.BrakeControl = 0.0F;
                brakeShoe = false;
                float f22 = (Vmin * M.getFullMass()) / M.massEmpty;
                float f24 = 12F * (getSpeed() / f22 - 0.2F);
                if(Gears.bIsSail)
                    f24 *= 2.0F;
                if(Gears.bFrontWheel)
                    f24 = Gears.Pitch + 4F;
                if(f24 < 1.0F)
                    f24 = 1.0F;
                if(f24 > 10F)
                    f24 = 10F;
                float f27 = 1.5F;
                if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && (AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier) && !Gears.isUnderDeck())
                {
                    CT.GearControl = 0.0F;
                    if(f15 < 0.0F)
                    {
                        f24 = 18F;
                        f27 = 0.05F;
                    } else
                    {
                        f24 = 14F;
                        f27 = 0.3F;
                    }
                }
                if(Or.getTangage() < f24)
                    dA = -0.7F * (Or.getTangage() - f24) + f27 * (float)getW().y + 0.5F * (float)getAW().y;
                else
                    dA = -0.1F * (Or.getTangage() - f24) + f27 * (float)getW().y + 0.5F * (float)getAW().y;
                CT.ElevatorControl += (dA - CT.ElevatorControl) * 3F * f;
            } else
            {
                CT.ElevatorControl = 1.0F;
            }
            AFo.setDeg(Or.getAzimut(), direction);
            double d1 = AFo.getDiffRad();
            if(EI.engines[0].getStage() == 6)
            {
                CT.RudderControl = 8F * (float)d1;
                if(d1 > -1D && d1 < 1.0D)
                {
                    if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && CT.getPower() > 0.3F)
                    {
                        double d2 = AP.way.takeoffAirport.ShiftFromLine(this);
                        double d3 = 3D - 3D * java.lang.Math.abs(d1);
                        if(d3 > 1.0D)
                            d3 = 1.0D;
                        double d5 = 0.25D * d2 * d3;
                        if(d5 > 1.5D)
                            d5 = 1.5D;
                        if(d5 < -1.5D)
                            d5 = -1.5D;
                        CT.RudderControl += (float)d5;
                    }
                } else
                {
                    CT.BrakeControl = 1.0F;
                }
            }
            CT.AileronControl = -0.05F * Or.getKren() + 0.3F * (float)getW().y;
            if(f15 > 5F && !Gears.isUnderDeck())
                CT.GearControl = 0.0F;
            float f28 = 1.0F;
            if(hasBombs())
                f28 *= 1.7F;
            if(f15 > 120F * f28 || getSpeed() > Vmin * 1.8F * f28 || f15 > 80F * f28 && getSpeed() > Vmin * 1.6F * f28 || f15 > 40F * f28 && getSpeed() > Vmin * 1.3F * f28 && mn_time > 60F + (float)((com.maddox.il2.objects.air.Aircraft)actor).aircIndex() * 3F)
            {
                CT.FlapsControl = 0.0F;
                CT.GearControl = 0.0F;
                rwLoc = null;
                if(actor instanceof com.maddox.il2.objects.air.TypeGlider)
                    push(24);
                maneuver = 0;
                brakeShoe = false;
                turnOffCollisions = false;
                if(CT.bHasCockpitDoorControl)
                    AS.setCockpitDoor(actor, 0);
                pop();
            }
            if(actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                CT.BrakeControl = 0.0F;
                CT.ElevatorControl = 0.05F;
                canTakeoff = true;
            }
            break;

        case 69: // 'E'
            float f16 = Alt;
            float f19 = 0.4F;
            CT.BrakeControl = 1.0F;
            W.scale(0.0D);
            boolean flag4 = com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport) && (AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier);
            if(flag4)
            {
                f16 -= ((com.maddox.il2.ai.AirportCarrier)AP.way.takeoffAirport).height();
                f19 = 0.8F;
                if(Alt < 9F && Vwld.z < 0.0D)
                    Vwld.z *= 0.84999999999999998D;
                if(CT.bHasCockpitDoorControl)
                    AS.setCockpitDoor(actor, 1);
            }
            if(Loc.z != 0.0D && ((com.maddox.il2.objects.air.HE_LERCHE3)actor).suka.getPoint().z == 0.0D)
                ((com.maddox.il2.objects.air.HE_LERCHE3)actor).suka.set(Loc, Or);
            if(Loc.z != 0.0D)
                if(EI.getPowerOutput() < f19 && !flag4)
                {
                    callSuperUpdate = false;
                    Loc.set(((com.maddox.il2.objects.air.HE_LERCHE3)actor).suka.getPoint());
                    Or.set(((com.maddox.il2.objects.air.HE_LERCHE3)actor).suka.getOrient());
                } else
                if(f16 < 100F)
                    Or.set(((com.maddox.il2.objects.air.HE_LERCHE3)actor).suka.getOrient());
            if(Gears.onGround() && EI.engines[0].getStage() == 0)
            {
                CT.PowerControl = 0.0F;
                setSpeedMode(8);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F && mn_time > 1.0F || mn_time > 8F)
                {
                    push();
                    push(64);
                    submaneuver = 0;
                    maneuver = 0;
                    safe_pop();
                    break;
                }
            }
            if(EI.engines[0].getStage() == 6)
            {
                CT.BrakeControl = 0.0F;
                CT.AirBrakeControl = 1.0F;
                brakeShoe = false;
                CT.PowerControl = 1.1F;
                setSpeedMode(9);
            }
            if(f16 > 200F)
            {
                CT.GearControl = 0.0F;
                rwLoc = null;
                CT.ElevatorControl = -1F;
                CT.AirBrakeControl = 0.0F;
                if(Or.getTangage() < 25F)
                    maneuver = 0;
                brakeShoe = false;
                turnOffCollisions = false;
                if(CT.bHasCockpitDoorControl)
                    AS.setCockpitDoor(actor, 0);
                pop();
            }
            break;

        case 28: // '\034'
            if(first)
            {
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-25F, 25F);
                AP.setStabAll(false);
                setSpeedMode(6);
                CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.22F, 0.22F);
                submaneuver = 0;
                if(getSpeed() < Vmin * 1.5F)
                    pop();
            }
            CT.AileronControl = -0.04F * (Or.getKren() - direction);
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = 1.0F;
                maxAOA = 12F + 1.0F * (float)Skill;
                if(AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.6F * f;
                else
                    CT.ElevatorControl += 3.3F * f;
                if(Or.getTangage() > 40F + 5.1F * (float)Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-25F, 25F);
                CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.75F, 0.75F);
                submaneuver++;
                // fall through

            case 2: // '\002'
                dA = -1F;
                maxAOA = 12F + 5F * (float)Skill;
                if(AOA < -maxAOA || CT.ElevatorControl < dA)
                    CT.ElevatorControl += 0.6F * f;
                else
                    CT.ElevatorControl -= 3.3F * f;
                if(Or.getTangage() < -45F)
                    pop();
                break;
            }
            if(mn_time > 3F)
                pop();
            break;

        case 29: // '\035'
            minElevCoeff = 17F;
            if(first)
            {
                AP.setStabAll(false);
                setSpeedMode(9);
                sub_Man_Count = 0;
            }
            if(danger != null)
            {
                if(sub_Man_Count == 0)
                {
                    tmpV3d.set(danger.getW());
                    danger.Or.transform(tmpV3d);
                    if(tmpV3d.z > 0.0D)
                        sinKren = -com.maddox.il2.ai.World.Rnd().nextFloat(60F, 90F);
                    else
                        sinKren = com.maddox.il2.ai.World.Rnd().nextFloat(60F, 90F);
                }
                if(Loc.distanceSquared(danger.Loc) < 5000D)
                {
                    setSpeedMode(8);
                    CT.PowerControl = 0.0F;
                } else
                {
                    setSpeedMode(9);
                }
            } else
            {
                pop();
            }
            CT.FlapsControl = 0.2F;
            if((double)getSpeed() < 120D)
                CT.FlapsControl = 0.33F;
            if((double)getSpeed() < 80D)
                CT.FlapsControl = 1.0F;
            CT.AileronControl = -0.08F * (Or.getKren() + sinKren);
            CT.ElevatorControl = 0.9F;
            CT.RudderControl = 0.0F;
            sub_Man_Count++;
            if(sub_Man_Count > 15)
                sub_Man_Count = 0;
            if(mn_time > 10F)
                pop();
            break;

        case 56: // '8'
            if(first)
            {
                submaneuver = com.maddox.il2.ai.World.Rnd().nextInt(0, 1);
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-20F, -10F);
            }
            CT.AileronControl = -0.08F * (Or.getKren() - direction);
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)Skill;
                if((double)getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(Or.getKren()))) || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                CT.RudderControl = -1F * (float)java.lang.Math.sin(java.lang.Math.toRadians(Or.getKren() + 55F));
                if(mn_time > 1.5F)
                    submaneuver++;
                break;

            case 1: // '\001'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(10F, 20F);
                submaneuver++;
                // fall through

            case 2: // '\002'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)Skill;
                if((double)getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(Or.getKren()))) || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                CT.RudderControl = 1.0F * (float)java.lang.Math.sin(java.lang.Math.toRadians(Or.getKren() - 55F));
                if(mn_time > 4.5F)
                    submaneuver++;
                break;

            case 3: // '\003'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-20F, -10F);
                submaneuver++;
                // fall through

            case 4: // '\004'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)Skill;
                if((double)getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(Or.getKren()))) || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                CT.RudderControl = -1F * (float)java.lang.Math.sin(java.lang.Math.toRadians(Or.getKren() + 55F));
                if(mn_time > 6F)
                    pop();
                break;
            }
            break;

        case 30: // '\036'
            push(14);
            push(18);
            pop();
            break;

        case 31: // '\037'
            push(14);
            push(19);
            pop();
            break;

        case 32: // ' '
            if(!isCapableOfACM() && com.maddox.il2.ai.World.Rnd().nextInt(-2, 9) < Skill)
                ((com.maddox.il2.objects.air.Aircraft)actor).hitDaSilk();
            if(first)
            {
                AP.setStabAll(false);
                setSpeedMode(6);
                submaneuver = 0;
                direction = 178F - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 8F) * (float)Skill;
            }
            maxAOA = Vwld.z <= 0.0D ? 24F : 14F;
            if(danger != null)
            {
                Ve.sub(danger.Loc, Loc);
                dist = (float)Ve.length();
                Or.transformInv(Ve);
                Vpl.set(danger.Vwld);
                Or.transformInv(Vpl);
                Vpl.normalize();
            }
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = Or.getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA = -0.08F * dA;
                CT.AileronControl = dA;
                CT.RudderControl = dA <= 0.0F ? -1F : 1.0F;
                CT.ElevatorControl = 0.01111111F * java.lang.Math.abs(Or.getKren());
                if(mn_time > 2.0F || java.lang.Math.abs(Or.getKren()) > direction)
                {
                    submaneuver++;
                    CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F);
                    direction = com.maddox.il2.ai.World.Rnd().nextFloat(-60F, -30F);
                    mn_time = 0.5F;
                }
                break;

            case 1: // '\001'
                dA = Or.getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA = -0.04F * dA;
                CT.AileronControl = dA;
                if(Or.getTangage() > direction + 5F && getOverload() < maxG && AOA < maxAOA)
                {
                    if(CT.ElevatorControl < 0.0F)
                        CT.ElevatorControl = 0.0F;
                    CT.ElevatorControl += 1.0F * f;
                } else
                {
                    if(CT.ElevatorControl > 0.0F)
                        CT.ElevatorControl = 0.0F;
                    CT.ElevatorControl -= 1.0F * f;
                }
                if(mn_time > 2.0F && Or.getTangage() < direction + 20F)
                    submaneuver++;
                if(danger != null)
                {
                    if(Skill >= 2 && Or.getTangage() < -30F && Vpl.x > 0.99862951040267944D)
                        submaneuver++;
                    if(Skill >= 3 && java.lang.Math.abs(Or.getTangage()) > 145F && java.lang.Math.abs(danger.Or.getTangage()) > 135F && dist < 400F)
                        submaneuver++;
                }
                break;

            case 2: // '\002'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-60F, 60F);
                setSpeedMode(6);
                submaneuver++;
                // fall through

            case 3: // '\003'
                dA = Or.getKren() - direction;
                CT.AileronControl = -0.04F * dA;
                CT.RudderControl = dA <= 0.0F ? -1F : 1.0F;
                CT.ElevatorControl = 0.5F;
                if(java.lang.Math.abs(dA) < 4F + 3F * (float)Skill)
                    submaneuver++;
                break;

            case 4: // '\004'
                direction *= com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.0F);
                setSpeedMode(6);
                submaneuver++;
                // fall through

            case 5: // '\005'
                dA = Or.getKren() - direction;
                CT.AileronControl = -0.04F * dA;
                CT.RudderControl = -0.1F * getAOS();
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA || Or.getTangage() > 40F)
                    CT.ElevatorControl -= 0.8F * f;
                else
                    CT.ElevatorControl += 1.6F * f;
                if(Or.getTangage() > 80F || mn_time > 4F || getSpeed() < Vmin * 1.5F)
                    pop();
                if(danger != null && (Ve.z < -1D || dist > 600F || Vpl.x < 0.78801000118255615D))
                    pop();
                break;
            }
            if((double)Alt < -7D * Vwld.z)
                pop();
            break;

        case 33: // '!'
            if(first)
            {
                if(Alt < 1000F)
                {
                    pop();
                    break;
                }
                AP.setStabAll(false);
                submaneuver = 0;
                direction = (com.maddox.il2.ai.World.Rnd().nextBoolean() ? 1.0F : -1F) * com.maddox.il2.ai.World.Rnd().nextFloat(107F, 160F);
            }
            maxAOA = Vwld.z <= 0.0D ? 24F : 14F;
            switch(submaneuver)
            {
            case 0: // '\0'
                if(java.lang.Math.abs(Or.getKren()) < 45F)
                    CT.ElevatorControl = 0.005555556F * java.lang.Math.abs(Or.getKren());
                else
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > 1.0F)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 1.2F * f;
                dA = Or.getKren() - direction;
                CT.AileronControl = -0.04F * dA;
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(dA) < 4F + 1.0F * (float)Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                setSpeedMode(7);
                smConstPower = 0.5F;
                CT.AileronControl = 0.0F;
                CT.RudderControl = -0.1F * getAOS();
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.2F * f;
                else
                    CT.ElevatorControl += 1.2F * f;
                if(Or.getTangage() < -60F)
                    submaneuver++;
                break;

            case 2: // '\002'
                if(Or.getTangage() > -45F)
                {
                    CT.AileronControl = -0.04F * Or.getKren();
                    setSpeedMode(9);
                    maxAOA = 7F;
                }
                CT.RudderControl = -0.1F * getAOS();
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.8F * f;
                else
                    CT.ElevatorControl += 0.4F * f;
                if(Or.getTangage() > AOA - 10F || Vwld.z > -1D)
                    pop();
                break;
            }
            if((double)Alt < -7D * Vwld.z)
                pop();
            break;

        case 34: // '"'
            if(first)
            {
                if(Alt < 500F)
                {
                    pop();
                    break;
                }
                direction = Or.getTangage();
                setSpeedMode(9);
            }
            dA = Or.getKren() - (Or.getKren() <= 0.0F ? -35F : 35F);
            CT.AileronControl = -0.04F * dA;
            CT.RudderControl = Or.getKren() <= 0.0F ? -1F : 1.0F;
            CT.ElevatorControl = -1F;
            if(direction > Or.getTangage() + 45F || Or.getTangage() < -60F || mn_time > 4F)
                pop();
            break;

        case 36: // '$'
        case 37: // '%'
            if(first)
            {
                if(!isCapableOfACM())
                    pop();
                if(getSpeed() < Vmax * 0.5F)
                {
                    pop();
                    break;
                }
                AP.setStabAll(false);
                submaneuver = 0;
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 80F);
                setSpeedMode(9);
            }
            maxAOA = Vwld.z <= 0.0D ? 24F : 14F;
            switch(submaneuver)
            {
            case 0: // '\0'
                CT.AileronControl = -0.04F * Or.getKren();
                CT.RudderControl = -0.1F * getAOS();
                if(java.lang.Math.abs(Or.getKren()) < 45F)
                    submaneuver++;
                break;

            case 1: // '\001'
                CT.AileronControl = 0.0F;
                dA = 1.0F;
                if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > dA)
                    CT.ElevatorControl -= 0.4F * f;
                else
                    CT.ElevatorControl += 0.8F * f;
                if(Or.getTangage() > direction)
                    submaneuver++;
                if(getSpeed() < Vmin * 1.25F)
                    pop();
                break;

            case 2: // '\002'
                push(maneuver != 36 ? 35 : 7);
                pop();
                break;
            }
            break;

        case 38: // '&'
            if(first)
                CT.RudderControl = Or.getKren() <= 0.0F ? -1F : 1.0F;
            CT.AileronControl += -0.02F * Or.getKren();
            if(CT.AileronControl > 0.1F)
                CT.AileronControl = 0.1F;
            if(CT.AileronControl < -0.1F)
                CT.AileronControl = -0.1F;
            dA = (getSpeedKMH() - 180F - Or.getTangage() * 10F - getVertSpeed() * 5F) * 0.004F;
            CT.ElevatorControl = dA;
            if(mn_time > 3.5F)
                pop();
            break;

        case 39: // '\''
            setSpeedMode(6);
            CT.AileronControl = -0.04F * Or.getKren();
            CT.ElevatorControl = -0.04F * (Or.getTangage() + 10F);
            if(CT.RudderControl > 0.1F)
                CT.RudderControl = 0.8F;
            else
            if(CT.RudderControl < -0.1F)
                CT.RudderControl = -0.8F;
            else
                CT.RudderControl = Or.getKren() <= 0.0F ? -1F : 1.0F;
            if(getSpeed() > Vmax || mn_time > 7F)
                pop();
            break;

        case 40: // '('
            push(39);
            push(57);
            pop();
            break;

        case 41: // ')'
            push(13);
            push(18);
            pop();
            break;

        case 42: // '*'
            push(19);
            push(57);
            pop();
            break;

        case 46: // '.'
            if(target_ground == null || target_ground.pos == null)
            {
                if(Group != null)
                {
                    dont_change_subm = true;
                    boolean flag5 = isBusy();
                    int j1 = Group.grTask;
                    com.maddox.il2.ai.air.AirGroup _tmp = Group;
                    Group.grTask = 4;
                    setBusy(false);
                    Group.setTaskAndManeuver(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor));
                    setBusy(flag5);
                    Group.grTask = j1;
                }
                if(target_ground == null || target_ground.pos == null)
                {
                    AP.way.first();
                    com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(AP.way.curr().getP(), actor.getArmy(), 7);
                    com.maddox.il2.ai.WayPoint waypoint;
                    if(airport != null)
                        waypoint = new WayPoint(airport.pos.getAbsPoint());
                    else
                        waypoint = new WayPoint(AP.way.first().getP());
                    waypoint.set(0.6F * Vmax);
                    waypoint.Action = 2;
                    AP.way.add(waypoint);
                    AP.way.last();
                    set_task(3);
                    clear_stack();
                    maneuver = 21;
                    set_maneuver(21);
                    break;
                }
            }
            groundAttackKamikaze(target_ground, f);
            break;

        case 43: // '+'
        case 47: // '/'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
            if(first && !isCapableOfACM())
            {
                bombsOut = true;
                setReadyToReturn(true);
                if(Group != null)
                {
                    Group.waitGroup(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor));
                } else
                {
                    AP.way.next();
                    set_task(3);
                    clear_stack();
                    set_maneuver(21);
                }
                break;
            }
            if(target_ground == null || target_ground.pos == null || !com.maddox.il2.engine.Actor.isAlive(target_ground))
            {
                int i1 = maneuver;
                if(Group != null)
                {
                    com.maddox.il2.ai.air.AirGroup _tmp1 = Group;
                    if(Group.grTask == 4)
                    {
                        dont_change_subm = true;
                        boolean flag6 = isBusy();
                        setBusy(false);
                        Group.setTaskAndManeuver(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor));
                        setBusy(flag6);
                    }
                }
                if(target_ground == null || target_ground.pos == null || !com.maddox.il2.engine.Actor.isAlive(target_ground))
                {
                    if(i1 == 50)
                        bombsOut = true;
                    if(Group != null)
                    {
                        Group.waitGroup(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)actor));
                    } else
                    {
                        AP.way.next();
                        set_task(3);
                        clear_stack();
                        set_maneuver(21);
                    }
                    push(2);
                    pop();
                    break;
                }
            }
            switch(maneuver)
            {
            case 44: // ','
            case 45: // '-'
            case 48: // '0'
            case 49: // '1'
            default:
                break;

            case 43: // '+'
                groundAttack(target_ground, f);
                if(mn_time > 120F)
                    set_maneuver(0);
                break;

            case 50: // '2'
                groundAttackDiveBomber(target_ground, f);
                if(mn_time > 120F)
                {
                    setSpeedMode(6);
                    CT.BayDoorControl = 0.0F;
                    CT.AirBrakeControl = 0.0F;
                    CT.FlapsControl = 0.0F;
                    pop();
                    sub_Man_Count = 0;
                }
                break;

            case 51: // '3'
                groundAttackTorpedo(target_ground, f);
                break;

            case 52: // '4'
                groundAttackCassette(target_ground, f);
                break;

            case 46: // '.'
                groundAttackKamikaze(target_ground, f);
                break;

            case 47: // '/'
                groundAttackTinyTim(target_ground, f);
                break;
            }
            break;
        }
        if(checkGround)
            doCheckGround(f);
        if(checkStrike && strikeEmer)
            doCheckStrike();
        strikeEmer = false;
        setSpeedControl(f);
        first = false;
        mn_time += f;
        if(frequentControl)
            AP.update(f);
        else
            AP.update(f * 4F);
        if(bBusy)
            wasBusy = true;
        else
            wasBusy = false;
        if(shotAtFriend > 0)
            shotAtFriend--;
    }

    void OutCT(int i)
    {
        if(actor != com.maddox.il2.game.Main3D.cur3D().viewActor())
            return;
        com.maddox.il2.engine.TextScr.output(i + 5, 45, "Alt(MSL)  " + (int)Loc.z + "    " + (CT.BrakeControl <= 0.0F ? "" : "BRAKE"));
        com.maddox.il2.engine.TextScr.output(i + 5, 65, "Alt(AGL)  " + (int)(Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(Loc.x, Loc.y)));
        int j = 0;
        com.maddox.il2.engine.TextScr.output(i + 225, 140, "---ENGINES (" + EI.getNum() + ")---" + EI.engines[j].getStage());
        com.maddox.il2.engine.TextScr.output(i + 245, 120, "THTL " + (int)(100F * EI.engines[j].getControlThrottle()) + "%" + (EI.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 100, "PROP " + (int)(100F * EI.engines[j].getControlProp()) + "%" + (CT.getStepControlAuto() ? " (AUTO)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 80, "MIX " + (int)(100F * EI.engines[j].getControlMix()) + "%");
        com.maddox.il2.engine.TextScr.output(i + 245, 60, "RAD " + (int)(100F * EI.engines[j].getControlRadiator()) + "%" + (CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 40, "SUPC " + EI.engines[j].getControlCompressor() + "x");
        com.maddox.il2.engine.TextScr.output(245, 20, "PropAoA :" + (int)java.lang.Math.toDegrees(EI.engines[j].getPropAoA()));
        com.maddox.il2.engine.TextScr.output(i + 455, 120, "Cyls/Cams " + EI.engines[j].getCylindersOperable() + "/" + EI.engines[0].getCylinders());
        com.maddox.il2.engine.TextScr.output(i + 455, 100, "Readyness " + (int)(100F * EI.engines[j].getReadyness()) + "%");
        com.maddox.il2.engine.TextScr.output(i + 455, 80, "PRM " + (int)((float)(int)(EI.engines[j].getRPM() * 0.02F) * 50F) + " rpm");
        com.maddox.il2.engine.TextScr.output(i + 455, 60, "Thrust " + (int)EI.engines[j].getEngineForce().x + " N");
        com.maddox.il2.engine.TextScr.output(i + 455, 40, "Fuel " + (int)((100F * M.fuel) / M.maxFuel) + "% Nitro " + (int)((100F * M.nitro) / M.maxNitro) + "%");
        com.maddox.il2.engine.TextScr.output(i + 455, 20, "MPrs " + (int)(1000F * EI.engines[j].getManifoldPressure()) + " mBar");
        com.maddox.il2.engine.TextScr.output(i + 640, 140, "---Controls---");
        com.maddox.il2.engine.TextScr.output(i + 640, 120, "A/C: " + (CT.bHasAileronControl ? "" : "AIL ") + (CT.bHasElevatorControl ? "" : "ELEV ") + (CT.bHasRudderControl ? "" : "RUD ") + (Gears.bIsHydroOperable ? "" : "GEAR "));
        com.maddox.il2.engine.TextScr.output(i + 640, 100, "ENG: " + (EI.engines[j].isHasControlThrottle() ? "" : "THTL ") + (EI.engines[j].isHasControlProp() ? "" : "PROP ") + (EI.engines[j].isHasControlMix() ? "" : "MIX ") + (EI.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (EI.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
        com.maddox.il2.engine.TextScr.output(i + 640, 80, "PIL: (" + (int)(AS.getPilotHealth(0) * 100F) + "%)");
        com.maddox.il2.engine.TextScr.output(i + 5, 105, "V   " + (int)getSpeedKMH());
        com.maddox.il2.engine.TextScr.output(i + 5, 125, "AOA " + (float)(int)(getAOA() * 1000F) / 1000F);
        com.maddox.il2.engine.TextScr.output(i + 5, 145, "AOS " + (float)(int)(getAOS() * 1000F) / 1000F);
        com.maddox.il2.engine.TextScr.output(i + 5, 165, "Kr  " + (int)Or.getKren());
        com.maddox.il2.engine.TextScr.output(i + 5, 185, "Ta  " + (int)Or.getTangage());
        com.maddox.il2.engine.TextScr.output(i + 250, 185, "way.speed  " + AP.way.curr().getV() * 3.6F + "way.z " + AP.way.curr().z() + "  mn_time = " + mn_time);
        com.maddox.il2.engine.TextScr.output(i + 5, 205, "<" + actor.name() + ">: " + com.maddox.il2.ai.air.ManString.tname(task) + ":" + com.maddox.il2.ai.air.ManString.name(maneuver) + " , WP=" + AP.way.Cur() + "(" + (AP.way.size() - 1) + ")-" + com.maddox.il2.ai.air.ManString.wpname(AP.way.curr().Action));
        com.maddox.il2.engine.TextScr.output(i + 7, 225, "======= " + com.maddox.il2.ai.air.ManString.name(program[0]) + "  Sub = " + submaneuver + " follOffs.x = " + followOffset.x + "  " + (((com.maddox.il2.ai.air.AutopilotAI)AP).bWayPoint ? "Stab WPoint " : "") + (((com.maddox.il2.ai.air.AutopilotAI)AP).bStabAltitude ? "Stab ALT " : "") + (((com.maddox.il2.ai.air.AutopilotAI)AP).bStabDirection ? "Stab DIR " : "") + (((com.maddox.il2.ai.air.AutopilotAI)AP).bStabSpeed ? "Stab SPD " : "   ") + (((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).isDumb() ? "DUMB " : " ") + (((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).Gears.lgear ? "L " : " ") + (((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).Gears.rgear ? "R " : " ") + (((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.air.Aircraft)actor).FM).TaxiMode ? "TaxiMode" : ""));
        com.maddox.il2.engine.TextScr.output(i + 7, 245, " ====== " + com.maddox.il2.ai.air.ManString.name(program[1]));
        com.maddox.il2.engine.TextScr.output(i + 7, 265, "  ===== " + com.maddox.il2.ai.air.ManString.name(program[2]));
        com.maddox.il2.engine.TextScr.output(i + 7, 285, "   ==== " + com.maddox.il2.ai.air.ManString.name(program[3]));
        com.maddox.il2.engine.TextScr.output(i + 7, 305, "    === " + com.maddox.il2.ai.air.ManString.name(program[4]));
        com.maddox.il2.engine.TextScr.output(i + 7, 325, "     == " + com.maddox.il2.ai.air.ManString.name(program[5]));
        com.maddox.il2.engine.TextScr.output(i + 7, 345, "      = " + com.maddox.il2.ai.air.ManString.name(program[6]) + "  " + (target != null ? "TARGET  " : "") + (target_ground != null ? "GROUND  " : "") + (danger != null ? "DANGER  " : ""));
        if(target != null && com.maddox.il2.engine.Actor.isValid(target.actor))
            com.maddox.il2.engine.TextScr.output(i + 1, 365, " AT: (" + target.actor.name() + ") " + ((target.actor instanceof com.maddox.il2.objects.air.Aircraft) ? "" : target.actor.getClass().getName()));
        if(target_ground != null && com.maddox.il2.engine.Actor.isValid(target_ground))
            com.maddox.il2.engine.TextScr.output(i + 1, 385, " GT: (" + target_ground.name() + ") ..." + target_ground.getClass().getName());
        com.maddox.il2.engine.TextScr.output(400, 500, "+");
        com.maddox.il2.engine.TextScr.output(400, 400, "|");
        com.maddox.il2.engine.TextScr.output((int)(400F + 200F * CT.AileronControl), (int)(500F - 200F * CT.ElevatorControl), "+");
        com.maddox.il2.engine.TextScr.output((int)(400F + 200F * CT.RudderControl), 400, "|");
        com.maddox.il2.engine.TextScr.output(250, 780, "followOffset  " + followOffset.x + "  " + followOffset.y + "  " + followOffset.z + "  ");
        if(Group != null && Group.enemies != null)
            com.maddox.il2.engine.TextScr.output(250, 760, "Enemies   " + com.maddox.il2.ai.air.AirGroupList.length(Group.enemies[0]));
        com.maddox.il2.engine.TextScr.output(700, 780, "speedMode   " + speedMode);
        if(Group != null)
            com.maddox.il2.engine.TextScr.output(700, 760, "Group task  " + Group.grTaskName());
        if(AP.way.isLandingOnShip())
            com.maddox.il2.engine.TextScr.output(5, 460, "Landing On Carrier");
        com.maddox.il2.engine.TextScr.output(700, 740, "gattackCounter  " + gattackCounter);
        com.maddox.il2.engine.TextScr.output(5, 360, "silence = " + silence);
    }

    private void groundAttackGuns(com.maddox.il2.engine.Actor actor, float f)
    {
        if(submaneuver == 0 && sub_Man_Count == 0 && CT.Weapons[1] != null)
        {
            float f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / f1;
        }
        maxAOA = 15F;
        minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            setCheckGround(true);
            rocketsDelay = 0;
            if(sub_Man_Count == 0)
            {
                Vtarg.set(actor.pos.getAbsPoint());
                actor.getSpeed(tmpV3d);
                tmpV3f.x = (float)tmpV3d.x;
                tmpV3f.y = (float)tmpV3d.y;
                tmpV3f.z = (float)tmpV3d.z;
                tmpV3f.z = 0.0D;
                if(tmpV3f.length() < 9.9999997473787516E-005D)
                {
                    tmpV3f.sub(Vtarg, Loc);
                    tmpV3f.z = 0.0D;
                }
                tmpV3f.normalize();
                Vtarg.x -= tmpV3f.x * 1500D;
                Vtarg.y -= tmpV3f.y * 1500D;
                Vtarg.z += 400D;
                constVtarg.set(Vtarg);
                Ve.sub(constVtarg, Loc);
                Ve.normalize();
                Vxy.cross(Ve, tmpV3f);
                Ve.sub(tmpV3f);
                Vtarg.z += 100D;
                if(Vxy.z > 0.0D)
                {
                    Vtarg.x += Ve.y * 1000D;
                    Vtarg.y -= Ve.x * 1000D;
                } else
                {
                    Vtarg.x -= Ve.y * 1000D;
                    Vtarg.y += Ve.x * 1000D;
                }
                constVtarg1.set(Vtarg);
            }
            Ve.set(constVtarg1);
            Ve.sub(Loc);
            float f2 = (float)Ve.length();
            Or.transformInv(Ve);
            if(Ve.x < 0.0D)
            {
                push(0);
                push(8);
                pop();
                dontSwitch = true;
            } else
            {
                Ve.normalize();
                setSpeedMode(4);
                smConstSpeed = 100F;
                farTurnToDirection();
                sub_Man_Count++;
                if(f2 < 300F)
                {
                    submaneuver++;
                    gattackCounter++;
                    sub_Man_Count = 0;
                }
            }
            break;

        case 1: // '\001'
            Ve.set(constVtarg);
            Ve.sub(Loc);
            float f3 = (float)Ve.length();
            Or.transformInv(Ve);
            Ve.normalize();
            setSpeedMode(4);
            smConstSpeed = 100F;
            farTurnToDirection();
            sub_Man_Count++;
            if(f3 < 300F)
            {
                submaneuver++;
                gattackCounter++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            if(rocketsDelay > 0)
                rocketsDelay--;
            if(sub_Man_Count > 100)
                setCheckGround(false);
            P.set(actor.pos.getAbsPoint());
            P.z += 4D;
            com.maddox.il2.engine.Engine.land();
            if(com.maddox.il2.engine.Landscape.rayHitHQ(Loc, P, P))
            {
                push(0);
                push(38);
                pop();
                gattackCounter--;
                if(gattackCounter < 0)
                    gattackCounter = 0;
            }
            Ve.sub(actor.pos.getAbsPoint(), Loc);
            Vtarg.set(Ve);
            float f4 = (float)Ve.length();
            actor.getSpeed(tmpV3d);
            tmpV3f.x = (float)tmpV3d.x;
            tmpV3f.y = (float)tmpV3d.y;
            tmpV3f.z = (float)tmpV3d.z;
            tmpV3f.sub(Vwld);
            tmpV3f.scale(f4 * bullTime * 0.3333F * (float)Skill);
            Ve.add(tmpV3f);
            float f5 = 0.3F * (f4 - 1000F);
            if(f5 < 0.0F)
                f5 = 0.0F;
            if(f5 > 300F)
                f5 = 300F;
            Ve.z += f5 + com.maddox.il2.ai.World.cur().rnd.nextFloat(-3F, 3F) * (float)(3 - Skill);
            Or.transformInv(Ve);
            if(f4 < 800F && (shotAtFriend <= 0 || distToFriend > f4) && java.lang.Math.abs(Ve.y) < 15D && java.lang.Math.abs(Ve.z) < 10D)
            {
                if(f4 < 550F)
                    CT.WeaponControl[0] = true;
                if(f4 < 450F)
                    CT.WeaponControl[1] = true;
                if(CT.Weapons[2] != null && CT.Weapons[2][0] != null && CT.Weapons[2][CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1 && f4 < 500F)
                {
                    CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)this.actor);
                    rocketsDelay += 30;
                }
            }
            if(sub_Man_Count > 200 && Ve.x < 200D || Alt < 40F)
            {
                if(Leader == null || !com.maddox.il2.engine.Actor.isAlive(Leader.actor))
                    com.maddox.il2.objects.sounds.Voice.speakEndGattack((com.maddox.il2.objects.air.Aircraft)this.actor);
                rocketsDelay = 0;
                push(0);
                push(55);
                push(10);
                pop();
                dontSwitch = true;
                return;
            }
            Ve.normalize();
            attackTurnToDirection(f4, f, 4F + (float)Skill * 2.0F);
            setSpeedMode(4);
            smConstSpeed = 100F;
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    private void groundAttack(com.maddox.il2.engine.Actor actor, float f)
    {
        setSpeedMode(4);
        smConstSpeed = 120F;
        float f3 = Vwld.z <= 0.0D ? 4F : 3F;
        boolean flag = false;
        if(CT.Weapons[3] != null && CT.Weapons[3][0] != null && CT.Weapons[3][0].countBullets() != 0)
            flag = true;
        else
        if(!(this.actor instanceof com.maddox.il2.objects.air.TypeStormovik) && !(this.actor instanceof com.maddox.il2.objects.air.TypeFighter) && !(this.actor instanceof com.maddox.il2.objects.air.TypeDiveBomber))
        {
            set_maneuver(0);
            return;
        }
        Ve.set(actor.pos.getAbsPoint());
        float f4 = (float)Loc.z - (float)Ve.z;
        if(f4 < 0.0F)
            f4 = 0.0F;
        float f5 = (float)java.lang.Math.sqrt(2.0F * f4 * 0.1019F) + 0.0017F * f4;
        actor.getSpeed(tmpV3d);
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && tmpV3d.length() > 20D)
        {
            target = ((com.maddox.il2.objects.air.Aircraft)actor).FM;
            set_task(6);
            clear_stack();
            set_maneuver(27);
            dontSwitch = true;
            return;
        }
        float f6 = 0.5F;
        if(flag)
            f6 = (f5 + 5F) * 0.33333F;
        Vtarg.x = (float)tmpV3d.x * f6 * (float)Skill;
        Vtarg.y = (float)tmpV3d.y * f6 * (float)Skill;
        Vtarg.z = (float)tmpV3d.z * f6 * (float)Skill;
        Ve.add(Vtarg);
        Ve.sub(Loc);
        Ve.add(0.0D, 0.0D, -0.5F + com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 0.8F));
        Vf.set(Ve);
        float f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        if(flag)
        {
            float f7 = getSpeed() * f5 + 500F;
            if(f1 > f7)
                Ve.z += 200D;
            else
                Ve.z = 0.0D;
        }
        Vtarg.set(Ve);
        Vtarg.normalize();
        Or.transformInv(Ve);
        if(!flag)
        {
            groundAttackGuns(actor, f);
            return;
        }
        if((this.actor instanceof com.maddox.il2.objects.air.TypeFighter) || (this.actor instanceof com.maddox.il2.objects.air.TypeStormovik))
        {
            groundAttackShallowDive(actor, f);
            return;
        }
        Ve.normalize();
        Vpl.set(Vwld);
        Vpl.normalize();
        CT.BayDoorControl = 1.0F;
        if(f1 + 4F * f5 < getSpeed() * f5 && Ve.x > 0.0D && Vpl.dot(Vtarg) > 0.98000001907348633D)
        {
            if(!bombsOut)
            {
                bombsOut = true;
                if(CT.Weapons[3] != null && CT.Weapons[3][0] != null && CT.Weapons[3][0].countBullets() != 0 && !(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara))
                    com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)this.actor);
            }
            push(0);
            push(55);
            push(48);
            pop();
            com.maddox.il2.objects.sounds.Voice.speakEndGattack((com.maddox.il2.objects.air.Aircraft)this.actor);
            CT.BayDoorControl = 0.0F;
        }
        if(Ve.x < 0.0D)
        {
            push(0);
            push(55);
            push(10);
            pop();
        }
        if(java.lang.Math.abs(Ve.y) > 0.10000000149011612D)
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.z) - 0.016F * Or.getKren();
        else
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.016F * Or.getKren();
        if(java.lang.Math.abs(Ve.y) > 0.0010000000474974513D)
            CT.RudderControl = -98F * (float)java.lang.Math.atan2(Ve.y, Ve.x);
        else
            CT.RudderControl = 0.0F;
        if((double)CT.RudderControl * W.z > 0.0D)
            W.z = 0.0D;
        else
            W.z *= 1.0399999618530273D;
        float f2 = (float)java.lang.Math.atan2(Ve.z, Ve.x);
        if(java.lang.Math.abs(Ve.y) < 0.0020000000949949026D && java.lang.Math.abs(Ve.z) < 0.0020000000949949026D)
            f2 = 0.0F;
        if(Ve.x < 0.0D)
        {
            f2 = 1.0F;
        } else
        {
            if((double)f2 * W.y > 0.0D)
                W.y = 0.0D;
            if(f2 < 0.0F)
            {
                if(getOverload() < 0.1F)
                    f2 = 0.0F;
                if(CT.ElevatorControl > 0.0F)
                    CT.ElevatorControl = 0.0F;
            } else
            if(CT.ElevatorControl < 0.0F)
                CT.ElevatorControl = 0.0F;
        }
        if(getOverload() > maxG || AOA > f3 || CT.ElevatorControl > f2)
            CT.ElevatorControl -= 0.2F * f;
        else
            CT.ElevatorControl += 0.2F * f;
    }

    private void groundAttackKamikaze(com.maddox.il2.engine.Actor actor, float f)
    {
        if(submaneuver == 0 && sub_Man_Count == 0 && CT.Weapons[1] != null)
        {
            float f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / f1;
        }
        maxAOA = 15F;
        minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            setCheckGround(true);
            rocketsDelay = 0;
            if(sub_Man_Count == 0)
            {
                Vtarg.set(actor.pos.getAbsPoint());
                tmpV3f.set(Vwld);
                tmpV3f.x += com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F);
                tmpV3f.y += com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F);
                tmpV3f.z = 0.0D;
                if(tmpV3f.length() < 9.9999997473787516E-005D)
                {
                    tmpV3f.sub(Vtarg, Loc);
                    tmpV3f.z = 0.0D;
                }
                tmpV3f.normalize();
                Vtarg.x -= tmpV3f.x * 1500D;
                Vtarg.y -= tmpV3f.y * 1500D;
                Vtarg.z += 400D;
                constVtarg.set(Vtarg);
                Ve.sub(constVtarg, Loc);
                Ve.normalize();
                Vxy.cross(Ve, tmpV3f);
                Ve.sub(tmpV3f);
                Vtarg.z += 100D;
                if(Vxy.z > 0.0D)
                {
                    Vtarg.x += Ve.y * 1000D;
                    Vtarg.y -= Ve.x * 1000D;
                } else
                {
                    Vtarg.x -= Ve.y * 1000D;
                    Vtarg.y += Ve.x * 1000D;
                }
                constVtarg1.set(Vtarg);
            }
            Ve.set(constVtarg1);
            Ve.sub(Loc);
            float f2 = (float)Ve.length();
            Or.transformInv(Ve);
            if(Ve.x < 0.0D)
            {
                push(0);
                push(8);
                pop();
                dontSwitch = true;
            } else
            {
                Ve.normalize();
                setSpeedMode(6);
                farTurnToDirection();
                sub_Man_Count++;
                if(f2 < 300F)
                {
                    submaneuver++;
                    gattackCounter++;
                    sub_Man_Count = 0;
                }
                if(sub_Man_Count > 1000)
                    sub_Man_Count = 0;
            }
            break;

        case 1: // '\001'
            setCheckGround(true);
            Ve.set(constVtarg);
            Ve.sub(Loc);
            float f3 = (float)Ve.length();
            Or.transformInv(Ve);
            Ve.normalize();
            setSpeedMode(6);
            farTurnToDirection();
            sub_Man_Count++;
            if(f3 < 300F)
            {
                submaneuver++;
                gattackCounter++;
                sub_Man_Count = 0;
            }
            if(sub_Man_Count > 700)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            setCheckGround(false);
            if(rocketsDelay > 0)
                rocketsDelay--;
            if(sub_Man_Count > 100)
                setCheckGround(false);
            Ve.set(actor.pos.getAbsPoint());
            Ve.sub(Loc);
            Vtarg.set(Ve);
            float f4 = (float)Ve.length();
            if(this.actor instanceof com.maddox.il2.objects.air.MXY_7)
            {
                Ve.z += 0.01D * (double)f4;
                Vtarg.z += 0.01D * (double)f4;
            }
            actor.getSpeed(tmpV3d);
            tmpV3f.x = (float)tmpV3d.x;
            tmpV3f.y = (float)tmpV3d.y;
            tmpV3f.z = (float)tmpV3d.z;
            tmpV3f.sub(Vwld);
            tmpV3f.scale(f4 * bullTime * 0.3333F * (float)Skill);
            Ve.add(tmpV3f);
            float f5 = 0.3F * (f4 - 1000F);
            if(f5 < 0.0F)
                f5 = 0.0F;
            if(f5 > 300F)
                f5 = 300F;
            Ve.z += f5 + com.maddox.il2.ai.World.cur().rnd.nextFloat(-3F, 3F) * (float)(3 - Skill);
            Or.transformInv(Ve);
            if(f4 < 50F && java.lang.Math.abs(Ve.y) < 40D && java.lang.Math.abs(Ve.z) < 30D)
            {
                CT.WeaponControl[0] = true;
                CT.WeaponControl[1] = true;
                CT.WeaponControl[2] = true;
                CT.WeaponControl[3] = true;
            }
            if(Ve.x < -50D)
            {
                rocketsDelay = 0;
                push(0);
                push(55);
                push(10);
                pop();
                dontSwitch = true;
                return;
            }
            Ve.normalize();
            attackTurnToDirection(f4, f, 4F + (float)Skill * 2.0F);
            setSpeedMode(4);
            smConstSpeed = 130F;
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    private void groundAttackTinyTim(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(4);
        smConstSpeed = 120F;
        minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            if(sub_Man_Count == 0)
            {
                Vtarg.set(actor.pos.getAbsPoint());
                actor.getSpeed(tmpV3d);
                if(tmpV3d.length() < 0.5D)
                    tmpV3d.sub(Vtarg, Loc);
                tmpV3d.normalize();
                Vtarg.x -= tmpV3d.x * 3000D;
                Vtarg.y -= tmpV3d.y * 3000D;
                Vtarg.z += 500D;
            }
            Ve.sub(Vtarg, Loc);
            double d = Ve.length();
            Or.transformInv(Ve);
            sub_Man_Count++;
            if(d < 1000D)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            Ve.normalize();
            farTurnToDirection();
            break;

        case 1: // '\001'
            Vtarg.set(actor.pos.getAbsPoint());
            Vtarg.z += 80D;
            Ve.sub(Vtarg, Loc);
            double d1 = Ve.length();
            Or.transformInv(Ve);
            sub_Man_Count++;
            if(d1 < 1500D)
            {
                if(java.lang.Math.abs(Ve.y) < 40D && java.lang.Math.abs(Ve.z) < 30D)
                    CT.WeaponControl[2] = true;
                push(0);
                push(10);
                push(48);
                pop();
                dontSwitch = true;
            }
            if(d1 < 500D && Ve.x < 0.0D)
            {
                push(0);
                push(10);
                pop();
            }
            Ve.normalize();
            if(Ve.x < 0.80000001192092896D)
                turnToDirection(f);
            else
                attackTurnToDirection((float)d1, f, 2.0F + (float)Skill * 1.5F);
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    private void groundAttackShallowDive(com.maddox.il2.engine.Actor actor, float f)
    {
        maxAOA = 10F;
        if(!hasBombs())
        {
            set_maneuver(0);
            wingman(true);
            return;
        }
        if(first)
            RandomVal = 50F - com.maddox.il2.ai.World.cur().rnd.nextFloat(0.0F, 100F);
        setSpeedMode(4);
        smConstSpeed = 120F;
        Ve.set(actor.pos.getAbsPoint());
        Ve.sub(Loc);
        float f1 = (float)(-Ve.z);
        if(f1 < 0.0F)
            f1 = 0.0F;
        Ve.z += 250D;
        float f2 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y) + RandomVal * (float)(3 - Skill);
        if(Ve.z < (double)(-0.1F * f2))
            Ve.z = -0.1F * f2;
        if((double)Alt + Ve.z < 250D)
            Ve.z = 250F - Alt;
        if(Alt < 50F)
        {
            push(10);
            pop();
        }
        Vf.set(Ve);
        CT.BayDoorControl = 1.0F;
        float f3 = (float)Vwld.z * 0.1019F;
        f3 += (float)java.lang.Math.sqrt(f3 * f3 + 2.0F * f1 * 0.1019F);
        float f4 = (float)java.lang.Math.sqrt(Vwld.x * Vwld.x + Vwld.y * Vwld.y);
        float f5 = f4 * f3 + 10F;
        actor.getSpeed(tmpV3d);
        tmpV3d.scale((double)f3 * 0.34999999999999998D * (double)Skill);
        Ve.x += (float)tmpV3d.x;
        Ve.y += (float)tmpV3d.y;
        Ve.z += (float)tmpV3d.z;
        if(f5 >= f2)
        {
            bombsOut = true;
            com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)this.actor);
            setSpeedMode(6);
            CT.BayDoorControl = 0.0F;
            push(0);
            push(55);
            push(48);
            pop();
            sub_Man_Count = 0;
        }
        Or.transformInv(Ve);
        Ve.normalize();
        turnToDirection(f);
    }

    private void groundAttackDiveBomber(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 10F;
        setSpeedMode(6);
        maxAOA = 4F;
        minElevCoeff = 20F;
        if(CT.Weapons[3] == null || CT.getWeaponCount(3) == 0)
        {
            if(AP.way.curr().Action == 3)
                AP.way.next();
            set_maneuver(0);
            wingman(true);
            return;
        }
        if(Alt < 350F)
        {
            bombsOut = true;
            com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)this.actor);
            setSpeedMode(6);
            CT.BayDoorControl = 0.0F;
            CT.AirBrakeControl = 0.0F;
            AP.way.next();
            push(0);
            push(8);
            push(2);
            pop();
            sub_Man_Count = 0;
        }
        Ve.set(actor.pos.getAbsPoint());
        Ve.sub(Loc);
        float f5 = (float)(-Ve.z);
        float f2 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
        float f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        if(f1 > 1000F || submaneuver == 3 && sub_Man_Count > 100)
        {
            Vtarg.set(actor.pos.getAbsPoint());
            actor.getSpeed(tmpV3d);
            float f6 = 0.0F;
            switch(submaneuver)
            {
            case 0: // '\0'
                f6 = f1 / 40F + 4F + Alt / 48F;
                // fall through

            case 1: // '\001'
                f6 = (f1 - man1Dist) / (float)Vwld.length() + 4F + Alt / 48F;
                // fall through

            case 2: // '\002'
                f6 = Alt / 60F;
                // fall through

            case 3: // '\003'
                f6 = Alt / 120F;
                // fall through

            default:
                f6 *= 0.33333F;
                break;
            }
            Vtarg.x += (float)tmpV3d.x * f6 * (float)Skill;
            Vtarg.y += (float)tmpV3d.y * f6 * (float)Skill;
            Vtarg.z += (float)tmpV3d.z * f6 * (float)Skill;
        }
        Ve.set(Vtarg);
        Ve.sub(Loc);
        float f4 = (float)(-Ve.z);
        if(f4 < 0.0F)
            f4 = 0.0F;
        Ve.add(Vxy);
        f5 = (float)(-Ve.z);
        f2 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
        f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        if(submaneuver < 2)
            Ve.z = 0.0D;
        Vf.set(Ve);
        Ve.normalize();
        Vpl.set(Vwld);
        Vpl.normalize();
        switch(submaneuver)
        {
        default:
            break;

        case 0: // '\0'
            push();
            pop();
            if(f5 < 1200F)
                man1Dist = 400F;
            else
            if(f5 > 4500F)
                man1Dist = 50F;
            else
                man1Dist = 50F + (350F * (4500F - f5)) / 3300F;
            float f7 = 0.01F * f5;
            if(f7 < 10F)
                f7 = 10F;
            Vxy.set(com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F), com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F), com.maddox.il2.ai.World.Rnd().nextFloat(5F, f7));
            Vxy.scale(4F - (float)Skill);
            float f8 = 2E-005F * f5 * f5;
            if(f8 < 60F)
                f8 = 60F;
            if(f8 > 350F)
                f8 = 350F;
            Vxy.z += f8;
            submaneuver++;
            break;

        case 1: // '\001'
            setSpeedMode(4);
            smConstSpeed = 110F;
            if(f1 >= man1Dist)
                break;
            CT.AirBrakeControl = 1.0F;
            if(this.actor instanceof com.maddox.il2.objects.air.TypeFighter)
                CT.FlapsControl = 1.0F;
            push();
            push(6);
            safe_pop();
            submaneuver++;
            break;

        case 2: // '\002'
            setSpeedMode(4);
            smConstSpeed = 110F;
            sub_Man_Count++;
            CT.AirBrakeControl = 1.0F;
            if(this.actor instanceof com.maddox.il2.objects.air.TypeFighter)
                CT.FlapsControl = 1.0F;
            float f3 = Or.getKren();
            if(Or.getTangage() > -90F)
            {
                f3 -= 180F;
                if(f3 < -180F)
                    f3 += 360F;
            }
            CT.AileronControl = (float)((double)(-0.04F * f3) - 0.5D * getW().x);
            if(getOverload() < 4F)
                CT.ElevatorControl += 0.3F * f;
            else
                CT.ElevatorControl -= 0.3F * f;
            if(sub_Man_Count > 30 && Or.getTangage() < -90F || sub_Man_Count > 150)
            {
                sub_Man_Count = 0;
                submaneuver++;
            }
            break;

        case 3: // '\003'
            CT.AirBrakeControl = 1.0F;
            if(this.actor instanceof com.maddox.il2.objects.air.TypeFighter)
                CT.FlapsControl = 1.0F;
            CT.BayDoorControl = 1.0F;
            setSpeedMode(4);
            smConstSpeed = 110F;
            sub_Man_Count++;
            if(sub_Man_Count > 80)
            {
                float f9 = (float)Vwld.z * 0.1019F;
                f9 = f9 + (float)java.lang.Math.sqrt(f9 * f9 + 2.0F * f4 * 0.1019F) + 0.00035F * f4;
                float f10 = (float)java.lang.Math.sqrt(Vwld.x * Vwld.x + Vwld.y * Vwld.y);
                float f11 = f10 * f9;
                float f12 = 0.2F * (f1 - f11);
                Vxy.z += f12;
                if(Vxy.z > (double)(0.7F * f4))
                    Vxy.z = 0.7F * f4;
            }
            if(sub_Man_Count > 100 && Alt < 1000F && Vpl.dot(Ve) > 0.99000000953674316D || Alt < 600F)
            {
                bombsOut = true;
                com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)this.actor);
                CT.BayDoorControl = 0.0F;
                CT.AirBrakeControl = 0.0F;
                AP.way.next();
                push(0);
                push(8);
                push(2);
                pop();
            }
            break;
        }
        Or.transformInv(Ve);
        Ve.normalize();
        if(submaneuver == 3)
            attackTurnToDirection(1000F, f, 30F);
        else
        if(submaneuver != 2)
            turnToDirection(f);
    }

    private void groundAttackTorpedo(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(6);
        minElevCoeff = 20F;
        Ve.set(actor.pos.getAbsPoint());
        Ve.sub(Loc);
        double d = Ve.x * Ve.x + Ve.y * Ve.y;
        float f1 = (float)java.lang.Math.sqrt(d);
        if(first)
        {
            Vtarg.set(actor.pos.getAbsPoint());
            submaneuver = 0;
        }
        if(submaneuver == 1 && sub_Man_Count == 0)
        {
            tmpV3f.set(actor.pos.getAbsPoint());
            tmpV3f.sub(Vtarg);
            actor.getSpeed(tmpV3d);
            if(tmpV3f.length() > 9.9999997473787516E-005D)
                tmpV3f.normalize();
            Vxy.set(tmpV3f.y * 3000D, -tmpV3f.x * 3000D, 0.0D);
            direc = Vxy.dot(Ve) > 0.0D;
            if(direc)
                Vxy.scale(-1D);
            Vtarg.add(Vxy);
            Vtarg.x += tmpV3d.x * 80D;
            Vtarg.y += tmpV3d.y * 80D;
            Vtarg.z = 80D;
            TargDevV.set((double)com.maddox.il2.ai.World.cur().rnd.nextFloat(-10F, 10F) * (3.7999999999999998D - (double)Skill), (double)com.maddox.il2.ai.World.cur().rnd.nextFloat(-10F, 10F) * (3.7999999999999998D - (double)Skill), 0.0D);
        }
        if(submaneuver == 2)
        {
            Vtarg.set(actor.pos.getAbsPoint());
            actor.getSpeed(tmpV3d);
            double d1 = tmpV3d.lengthSquared();
            float f4 = 20F;
            if(CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.TorpedoGun)
            {
                com.maddox.il2.objects.weapons.TorpedoGun torpedogun = (com.maddox.il2.objects.weapons.TorpedoGun)CT.Weapons[3][0];
                java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(torpedogun.getClass(), "bulletClass", null);
                if(class1 != null)
                    f4 = com.maddox.rts.Property.floatValue(class1, "velocity", 1.0F);
            }
            double d2 = java.lang.Math.sqrt(0.20399999999999999D * Loc.z);
            double d3 = 1.0D * d2 * (double)getSpeed();
            double d4 = (780D - d3) / (double)f4;
            Vtarg.x += (float)(tmpV3d.x * d4);
            Vtarg.y += (float)(tmpV3d.y * d4);
            Vtarg.z = 20D;
            if(Loc.z < 30D)
                Vtarg.z += 3D * (30D - Loc.z);
            Vtarg.add(TargDevV);
        }
        Ve.set(Vtarg);
        Ve.sub(Loc);
        float f2 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
        Vf.set(Ve);
        float f3 = (float)(-Ve.z);
        Vpl.set(Vwld);
        Vpl.normalize();
        if(Alt < 40F)
        {
            if(Vwld.z < 0.0D)
                Vwld.z *= -0.20000000000000001D + 0.025000000000000001D * (double)Alt;
            if(Alt < 8F)
                set_maneuver(2);
        }
        switch(submaneuver)
        {
        default:
            break;

        case 0: // '\0'
            sub_Man_Count++;
            if(sub_Man_Count > 60)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 1: // '\001'
            setSpeedMode(6);
            CT.PowerControl = 1.0F;
            sub_Man_Count++;
            if(f2 < 1000F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            setSpeedMode(6);
            if(f2 < 800F)
            {
                CT.WeaponControl[3] = true;
                AP.way.next();
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 3: // '\003'
            setSpeedMode(9);
            sub_Man_Count++;
            if(sub_Man_Count > 90)
            {
                task = 3;
                push(0);
                push(8);
                pop();
                submaneuver = 0;
                sub_Man_Count = 0;
            }
            break;
        }
        Or.transformInv(Ve);
        if(submaneuver == 3)
        {
            if(sub_Man_Count < 20)
                Ve.set(1.0D, 0.090000003576278687D, 0.029999999329447746D);
            else
                Ve.set(1.0D, 0.090000003576278687D, 0.0099999997764825821D);
            if(!direc)
                Ve.y *= -1D;
        }
        Ve.normalize();
        turnToDirection(f);
    }

    private void groundAttackCassette(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(4);
        smConstSpeed = 120F;
        minElevCoeff = 20F;
        Ve.set(actor.pos.getAbsPoint());
        Ve.sub(Loc);
        float f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        if(submaneuver == 3 && sub_Man_Count > 0 && sub_Man_Count < 45 && f1 > 200F)
        {
            tmpV3f.set(Vxy);
            float f4 = (float)tmpV3f.dot(Ve);
            tmpV3f.scale(-f4);
            tmpV3f.add(Ve);
            float f7 = (float)tmpV3f.length();
            float f6;
            if(f7 > 150F)
                f6 = 7.5F / f7;
            else
                f6 = 0.05F;
            tmpV3f.scale(f6);
            tmpV3f.z = 0.0D;
            Vwld.add(tmpV3f);
        }
        if(f1 <= 200F)
            sub_Man_Count = 50;
        if(first)
        {
            Vtarg.set(actor.pos.getAbsPoint());
            submaneuver = 0;
        }
        if(submaneuver == 1 && sub_Man_Count == 0)
        {
            tmpV3f.set(actor.pos.getAbsPoint());
            actor.getSpeed(tmpV3d);
            if(tmpV3d.length() < 0.5D)
                tmpV3d.set(Ve);
            tmpV3d.normalize();
            Vxy.set((float)tmpV3d.x, (float)tmpV3d.y, (float)tmpV3d.z);
            Vtarg.x -= tmpV3d.x * 3000D;
            Vtarg.y -= tmpV3d.y * 3000D;
            Vtarg.z += 250D;
        }
        if(submaneuver == 2 && sub_Man_Count == 0)
        {
            Vtarg.set(actor.pos.getAbsPoint());
            Vtarg.x -= Vxy.x * 1000D;
            Vtarg.y -= Vxy.y * 1000D;
            Vtarg.z += 50D;
        }
        if(submaneuver == 3 && sub_Man_Count == 0)
        {
            checkGround = false;
            Vtarg.set(actor.pos.getAbsPoint());
            Vtarg.x += Vxy.x * 1000D;
            Vtarg.y += Vxy.y * 1000D;
            Vtarg.z += 50D;
        }
        Ve.set(Vtarg);
        Ve.sub(Loc);
        float f2 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
        Vf.set(Ve);
        float f3 = (float)(-Ve.z);
        Or.transformInv(Ve);
        Ve.normalize();
        Vpl.set(Vwld);
        Vpl.normalize();
        if(Alt < 10F)
        {
            push(0);
            push(2);
            pop();
        }
        switch(submaneuver)
        {
        case 0: // '\0'
            setSpeedMode(9);
            sub_Man_Count++;
            if(sub_Man_Count > 60)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 1: // '\001'
            setSpeedMode(9);
            sub_Man_Count++;
            if(f2 < 1000F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            setSpeedMode(6);
            sub_Man_Count++;
            if(f2 < 155F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            if(sub_Man_Count > 320)
            {
                push(0);
                push(10);
                pop();
            }
            break;

        case 3: // '\003'
            setSpeedMode(6);
            sub_Man_Count++;
            Vwld.z *= 0.80000001192092896D;
            Or.transformInv(Vwld);
            Vwld.y *= 0.89999997615814209D;
            Or.transform(Vwld);
            float f5 = sub_Man_Count;
            if(f5 < 100F)
                f5 = 100F;
            if(Alt > 45F)
                Vwld.z -= 0.002F * (Alt - 45F) * f5;
            else
                Vwld.z -= 0.005F * (Alt - 45F) * f5;
            if(Alt < 0.0F)
                Alt = 0.0F;
            if(f2 < 1080F + getSpeed() * (float)java.lang.Math.sqrt((2.0F * Alt) / 9.81F))
                bombsOut = true;
            if(Ve.x < 0.0D || f2 < 350F || sub_Man_Count > 160)
            {
                push(0);
                push(10);
                push(10);
                pop();
            }
            break;
        }
        if(submaneuver == 0)
            Ve.set(1.0D, 0.0D, 0.0D);
        turnToDirection(f);
    }

    public void goodFighterVsFighter(float f)
    {
        Ve.sub(target.Loc, Loc);
        float f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        Or.transformInv(Ve);
        float f2 = (float)Ve.length();
        float f4 = 1.0F / f2;
        Vtarg.set(Ve);
        Vtarg.scale(f4);
        float f5 = (Energy - target.Energy) * 0.1019F;
        tmpV3f.sub(target.Vwld, Vwld);
        if(sub_Man_Count == 0)
        {
            float f6 = 0.0F;
            if(CT.Weapons[1] != null && CT.Weapons[1][0].countBullets() > 0)
                f6 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            else
            if(CT.Weapons[0] != null)
                f6 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).bulletSpeed();
            if(f6 > 0.01F)
                bullTime = 1.0F / f6;
            submanDelay = 0;
        }
        if(f1 < 1500F)
        {
            float f7 = 0.0F;
            float f9 = 0.0F;
            if(Vtarg.x > -0.20000000298023224D)
            {
                f7 = 3F * ((float)Vtarg.x + 0.2F);
                Vxy.set(tmpV3f);
                Vxy.scale(1.0D);
                Or.transformInv(Vxy);
                Vxy.add(Ve);
                Vxy.normalize();
                f9 = 10F * (float)(Vxy.x - Vtarg.x);
                if(f9 < -1F)
                    f9 = -1F;
                if(f9 > 1.0F)
                    f9 = 1.0F;
            } else
            {
                f7 = 3F * ((float)Vtarg.x + 0.2F);
            }
            if(submaneuver == 4 && Vtarg.x < 0.60000002384185791D && (double)f2 < 300D)
            {
                submaneuver = 1;
                submanDelay = 30;
            }
            if(submaneuver != 4 && (double)f5 > 300D && Vtarg.x > 0.75D)
            {
                submaneuver = 4;
                submanDelay = 240;
            }
            float f11 = 0.0015F * f5 + 0.0006F * f1 + f7 + 0.5F * f9;
            if(f11 > 0.9F && submanDelay == 0)
            {
                if(Vtarg.x > 0.5D || (double)f1 * 2D < (double)f2)
                {
                    submaneuver = 4;
                    submanDelay = 240;
                } else
                {
                    submaneuver = 3;
                    submanDelay = 120;
                }
            } else
            if(f5 > 800F && submaneuver == 0 || f5 > 1000F)
            {
                Ve.set(0.0D, 0.0D, 800D);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 30;
                }
            } else
            if(f2 > 450F && submaneuver == 2 || f2 > 600F)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 2;
                    submanDelay = 60;
                }
            } else
            if(submanDelay == 0)
            {
                submaneuver = 1;
                submanDelay = 30;
            }
        } else
        if(f1 < 3000F)
        {
            if(Vtarg.x < 0.5D)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 3;
                    submanDelay = 120;
                }
            } else
            if(f5 > 600F && submaneuver == 0 || f5 > 800F)
            {
                Ve.set(0.0D, 0.0D, 800D);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            } else
            if(f5 > -200F && submaneuver >= 4 || f5 > -100F)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 4;
                    submanDelay = 120;
                }
            } else
            {
                Ve.set(0.0D, 0.0D, Loc.z);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            }
        } else
        {
            Ve.set(0.0D, 0.0D, 1000D);
            if(submanDelay == 0)
            {
                submaneuver = 0;
                submanDelay = 180;
            }
        }
        switch(submaneuver)
        {
        case 0: // '\0'
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            tmpV3f.set(Ve);
            tmpV3f.z = 0.0D;
            float f3 = (float)tmpV3f.length();
            tmpV3f.normalize();
            Vtarg.set(target.Vwld);
            Vtarg.z = 0.0D;
            float f8 = (float)tmpV3f.dot(Vtarg);
            float f10 = getSpeed() - f8;
            if(f10 < 10F)
                f10 = 10F;
            float f12 = f3 / f10;
            if(f12 < 0.0F)
                f12 = 0.0F;
            tmpV3f.scale(Vtarg, f12);
            Ve.add(tmpV3f);
            Or.transformInv(Ve);
            Ve.normalize();
            setSpeedMode(9);
            farTurnToDirection();
            sub_Man_Count++;
            break;

        case 1: // '\001'
            setSpeedMode(9);
            CT.AileronControl = -0.04F * Or.getKren();
            CT.ElevatorControl = -0.04F * (Or.getTangage() + 5F);
            CT.RudderControl = 0.0F;
            break;

        case 2: // '\002'
            setSpeedMode(9);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            float f13 = 6F;
            if(Or.getKren() > 0.0F)
                Ve.set(100D, -f13, 10D);
            else
                Ve.set(100D, f13, 10D);
            tmpOr.transform(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            farTurnToDirection();
            break;

        case 3: // '\003'
            minElevCoeff = 20F;
            setSpeedMode(9);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            Ve.sub(target.Loc, Loc);
            Ve.z = 0.0D;
            Ve.normalize();
            Ve.z = 0.40000000000000002D;
            Or.transformInv(Ve);
            Ve.normalize();
            attackTurnToDirection(1000F, f, 15F);
            break;

        case 4: // '\004'
            minElevCoeff = 20F;
            boomAttack(f);
            setSpeedMode(9);
            break;

        default:
            minElevCoeff = 20F;
            fighterVsFighter(f);
            break;
        }
        if(submanDelay > 0)
            submanDelay--;
    }

    public void bNZFighterVsFighter(float f)
    {
        Ve.sub(target.Loc, Loc);
        Or.transformInv(Ve);
        dist = (float)Ve.length();
        float f1 = (float)java.lang.Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
        float f2 = 1.0F / dist;
        Vtarg.set(Ve);
        Vtarg.scale(f2);
        float f3 = (Energy - target.Energy) * 0.1019F;
        tmpV3f.sub(target.Vwld, Vwld);
        if(sub_Man_Count == 0)
        {
            float f4 = 0.0F;
            if(CT.Weapons[1] != null && CT.Weapons[1][0].countBullets() > 0)
                f4 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            else
            if(CT.Weapons[0] != null)
                f4 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).bulletSpeed();
            if(f4 > 0.01F)
                bullTime = 1.0F / f4;
            submanDelay = 0;
        }
        if(f1 < 1500F)
        {
            float f5 = 0.0F;
            float f7 = 0.0F;
            if(Vtarg.x > -0.20000000298023224D)
            {
                f5 = 3F * ((float)Vtarg.x + 0.2F);
                Vxy.set(tmpV3f);
                Vxy.scale(1.0D);
                Or.transformInv(Vxy);
                Vxy.add(Ve);
                Vxy.normalize();
                f7 = 20F * (float)(Vxy.x - Vtarg.x);
                if(f7 < -1F)
                    f7 = -1F;
                if(f7 > 1.0F)
                    f7 = 1.0F;
            }
            float f9 = f3 * 0.0015F + f1 * 0.0006F + f5 + f7;
            if(f9 > 0.8F && submaneuver >= 3 || f9 > 1.2F)
            {
                if(tmpV3f.length() < 100D)
                {
                    if(submanDelay == 0)
                    {
                        submaneuver = 4;
                        submanDelay = 120;
                    }
                } else
                if(submanDelay == 0)
                {
                    submaneuver = 3;
                    submanDelay = 120;
                }
            } else
            if(f3 > 800F && submaneuver == 0 || f3 > 1000F)
            {
                Ve.set(0.0D, 0.0D, 800D);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 30;
                }
            } else
            if(dist > 450F && submaneuver == 2 || dist > 600F)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 2;
                    submanDelay = 60;
                }
            } else
            if(submanDelay == 0)
            {
                submaneuver = 1;
                submanDelay = 30;
            }
        } else
        if(f1 < 3000F)
        {
            if(f3 > 600F && submaneuver == 0 || f3 > 800F)
            {
                Ve.set(0.0D, 0.0D, 800D);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            } else
            if(f3 > -200F && submaneuver >= 3 || f3 > -100F)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 3;
                    submanDelay = 120;
                }
            } else
            {
                Ve.set(0.0D, 0.0D, Loc.z);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            }
        } else
        {
            Ve.set(0.0D, 0.0D, 1000D);
            if(submanDelay == 0)
            {
                submaneuver = 0;
                submanDelay = 180;
            }
        }
        switch(submaneuver)
        {
        case 0: // '\0'
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            tmpV3f.set(Ve);
            tmpV3f.z = 0.0D;
            dist = (float)tmpV3f.length();
            tmpV3f.normalize();
            Vtarg.set(target.Vwld);
            Vtarg.z = 0.0D;
            float f6 = (float)tmpV3f.dot(Vtarg);
            float f8 = getSpeed() - f6;
            if(f8 < 10F)
                f8 = 10F;
            float f10 = dist / f8;
            if(f10 < 0.0F)
                f10 = 0.0F;
            tmpV3f.scale(Vtarg, f10);
            Ve.add(tmpV3f);
            Or.transformInv(Ve);
            Ve.normalize();
            setSpeedMode(9);
            farTurnToDirection();
            sub_Man_Count++;
            break;

        case 1: // '\001'
            setSpeedMode(9);
            CT.AileronControl = -0.04F * Or.getKren();
            CT.ElevatorControl = -0.04F * (Or.getTangage() + 5F);
            CT.RudderControl = 0.0F;
            break;

        case 2: // '\002'
            setSpeedMode(9);
            tmpOr.setYPR(Or.getYaw(), 0.0F, 0.0F);
            if(Or.getKren() > 0.0F)
                Ve.set(100D, -6D, 10D);
            else
                Ve.set(100D, 6D, 10D);
            tmpOr.transform(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            farTurnToDirection();
            break;

        case 3: // '\003'
            minElevCoeff = 20F;
            fighterVsFighter(f);
            setSpeedMode(6);
            break;

        default:
            minElevCoeff = 20F;
            fighterVsFighter(f);
            break;
        }
        if(submanDelay > 0)
            submanDelay--;
    }

    public void setBomberAttackType(int i)
    {
        float f;
        if(com.maddox.il2.engine.Actor.isValid(target.actor))
            f = target.actor.collisionR();
        else
            f = 15F;
        setRandomTargDeviation(0.8F);
        switch(i)
        {
        case 0: // '\0'
            ApproachV.set(-300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 150F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 1: // '\001'
            ApproachV.set(-300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(target.EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, target.EI.getNum() - 1)].getEnginePos());
            TargV.x--;
            ApproachR = 150F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 2: // '\002'
            ApproachV.set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(target.EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, target.EI.getNum() - 1)].getEnginePos());
            TargV.x--;
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 3: // '\003'
            ApproachV.set(2600D, 0.0D, 300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 800F;
            TargY = 25F;
            TargZ = 15F;
            TargYS = 3F;
            TargZS = 3F;
            break;

        case 4: // '\004'
            ApproachV.set(-400F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, -200F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 1.3F * f;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 5: // '\005'
            ApproachV.set(0.0D, 0.0D, 0.0D);
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 600F;
            TargY = 25F;
            TargZ = 12F;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 6: // '\006'
            ApproachV.set(600D, (float)(600 - com.maddox.il2.ai.World.Rnd().nextInt(0, 1) * 1200) + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 300D);
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 1.2F * f;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 7: // '\007'
            ApproachV.set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            TargV.set(target.EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, target.EI.getNum() - 1)].getEnginePos());
            ApproachR = 200F;
            TargY = 10F;
            TargZ = 10F;
            TargYS = 2.0F;
            TargZS = 2.0F;
            break;

        case 8: // '\b'
            ApproachV.set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 200F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.2F * f;
            TargZS = 0.2F * f;
            break;

        case 9: // '\t'
            ApproachV.set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.2F * f;
            TargZS = 0.2F * f;
            break;

        case 10: // '\n'
            ApproachV.set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, -300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            TargV.set(100D, 0.0D, -400D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        default:
            ApproachV.set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            TargV.set(target.EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, target.EI.getNum() - 1)].getEnginePos());
            ApproachR = 200F;
            TargY = 10F;
            TargZ = 10F;
            TargYS = 2.0F;
            TargZS = 2.0F;
            break;
        }
        float f1 = 0.0F;
        if(CT.Weapons[1] != null && CT.Weapons[1][0].countBullets() > 0)
            f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
        else
        if(CT.Weapons[0] != null)
            f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).bulletSpeed();
        if(f1 > 0.01F)
            bullTime = 1.0F / f1;
    }

    public void fighterVsBomber(float f)
    {
        maxAOA = 15F;
        tmpOr.setAT0(target.Vwld);
        switch(submaneuver)
        {
        case 0: // '\0'
            minElevCoeff = 4F;
            rocketsDelay = 0;
            bulletDelay = 0;
            double d = 0.0001D * target.Loc.z;
            Ve.sub(target.Loc, Loc);
            tmpOr.transformInv(Ve);
            scaledApproachV.set(ApproachV);
            scaledApproachV.x -= 700D * d;
            scaledApproachV.z += 500D * d;
            Ve.add(scaledApproachV);
            Ve.normalize();
            tmpV3f.scale(scaledApproachV, -1D);
            tmpV3f.normalize();
            double d1 = Ve.dot(tmpV3f);
            Ve.set(Vwld);
            Ve.normalize();
            tmpV3f.set(target.Vwld);
            tmpV3f.normalize();
            double d2 = Ve.dot(tmpV3f);
            Ve.set(scaledApproachV);
            Ve.x += 60D * (2D * (1.0D - d1) + 4D * (1.0D - d2));
            tmpOr.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            double d3 = Ve.z;
            tmpV3f.set(Ve);
            tmpV3f.z = 0.0D;
            float f1 = (float)tmpV3f.length();
            float f4 = 0.55F + 0.15F * (float)Skill;
            tmpV3f.normalize();
            Vtarg.set(target.Vwld);
            Vtarg.z = 0.0D;
            float f7 = (float)tmpV3f.dot(Vtarg);
            float f8 = getSpeed() - f7;
            if(f8 < 10F)
                f8 = 10F;
            float f9 = f1 / f8;
            if(f9 < 0.0F)
                f9 = 0.0F;
            tmpV3f.scale(Vtarg, f9 * f4);
            Ve.add(tmpV3f);
            Or.transformInv(Ve);
            Ve.normalize();
            if(d3 > -200D)
            {
                setSpeedMode(9);
            } else
            {
                setSpeedMode(4);
                smConstSpeed = 180F;
            }
            attackTurnToDirection(f1, f, 10F * (float)(1.0D + d));
            sub_Man_Count++;
            if((double)f1 < (double)ApproachR * (1.0D + d) && d3 < 200D)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 1: // '\001'
            minElevCoeff = 20F;
            Ve.set(TargV);
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            float f2 = (float)Ve.length();
            float f5 = 0.55F + 0.15F * (float)Skill;
            tmpV3f.sub(target.Vwld, Vwld);
            float f10 = f2 * bullTime * 0.0025F;
            if(f10 > 0.05F)
                f10 = 0.05F;
            tmpV3f.scale(f2 * f10 * f5);
            Ve.add(tmpV3f);
            Vtarg.set(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)Ve.x, f2, this);
            if(f2 > 3200F || Vtarg.z > 1500D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                set_maneuver(0);
                break;
            }
            if(Ve.x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.012F * (float)Skill * (800F + f2)) * (0.30000001192092896D - Ve.x);
                Ve.set(Vtarg);
                Or.transformInv(Ve);
                Ve.normalize();
            }
            attackTurnToDirection(f2, f, 10F + (float)Skill * 8F);
            setSpeedMode(4);
            smConstSpeed = 180F;
            if(f2 < 400F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            minElevCoeff = 20F;
            if(rocketsDelay > 0)
                rocketsDelay--;
            if(bulletDelay > 0)
                bulletDelay--;
            if(bulletDelay == 120)
                bulletDelay = 0;
            setRandomTargDeviation(0.8F);
            Ve.set(TargV);
            Ve.add(TargDevV);
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            Vtarg.set(Ve);
            float f3 = (float)Ve.length();
            float f6 = 0.55F + 0.15F * (float)Skill;
            float f11 = 0.0002F * (float)Skill;
            tmpV3f.sub(target.Vwld, Vwld);
            Vpl.set(tmpV3f);
            tmpV3f.scale(f3 * (bullTime + f11) * f6);
            Ve.add(tmpV3f);
            tmpV3f.set(Vpl);
            tmpV3f.scale(f3 * bullTime * f6);
            Vtarg.add(tmpV3f);
            if(f3 > 4000F || Vtarg.z > 2000D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                set_maneuver(0);
                break;
            }
            Or.transformInv(Vtarg);
            if(Vtarg.x > 0.0D && f3 < 500F && (shotAtFriend <= 0 || distToFriend > f3) && java.lang.Math.abs(Vtarg.y) < (double)(TargY - TargYS * (float)Skill) && java.lang.Math.abs(Vtarg.z) < (double)(TargZ - TargZS * (float)Skill) && bulletDelay < 120)
            {
                if(!(actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                    CT.WeaponControl[0] = true;
                CT.WeaponControl[1] = true;
                bulletDelay += 2;
                if(bulletDelay >= 118)
                {
                    int i = (int)(target.getW().length() * 150D);
                    if(i > 60)
                        i = 60;
                    bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * Skill, 2 * i * Skill);
                }
                if(CT.Weapons[2] != null && CT.Weapons[2][0] != null && CT.Weapons[2][CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                {
                    tmpV3f.sub(target.Vwld, Vwld);
                    Or.transformInv(tmpV3f);
                    if(java.lang.Math.abs(tmpV3f.y) < (double)(TargY - TargYS * (float)Skill) && java.lang.Math.abs(tmpV3f.z) < (double)(TargZ - TargZS * (float)Skill))
                        CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)actor);
                    rocketsDelay += 120;
                }
                ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.8F, f3, this);
                ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(actor);
            }
            Vtarg.set(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)Ve.x, f3, this);
            if(Ve.x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)Skill * (800F + f3)) * (0.30000001192092896D - Ve.x);
                Ve.set(Vtarg);
                Or.transformInv(Ve);
                Ve.normalize();
            }
            attackTurnToDirection(f3, f, 10F + (float)Skill * 8F);
            if(target.getSpeed() > 70F)
            {
                setSpeedMode(2);
                tailForStaying = target;
                tailOffset.set(-20D, 0.0D, 0.0D);
            } else
            {
                setSpeedMode(4);
                smConstSpeed = 70F;
            }
            if(strikeEmer)
            {
                Vpl.sub(strikeTarg.Loc, Loc);
                tmpV3f.set(Vpl);
                Or.transformInv(tmpV3f);
                if(tmpV3f.x < 0.0D)
                    return;
                tmpV3f.sub(strikeTarg.Vwld, Vwld);
                tmpV3f.scale(0.69999998807907104D);
                Vpl.add(tmpV3f);
                Or.transformInv(Vpl);
                push();
                if(Vpl.z < 0.0D)
                {
                    push(0);
                    push(8);
                    push(60);
                } else
                {
                    push(0);
                    push(8);
                    push(61);
                }
                pop();
                strikeEmer = false;
                submaneuver = 0;
                sub_Man_Count = 0;
            }
            if(sub_Man_Count > 600)
            {
                push(8);
                pop();
            }
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            set_maneuver(0);
            break;
        }
    }

    public void fighterUnderBomber(float f)
    {
        maxAOA = 15F;
        switch(submaneuver)
        {
        case 0: // '\0'
            rocketsDelay = 0;
            bulletDelay = 0;
            Ve.set(ApproachV);
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            tmpV3f.set(Ve);
            tmpV3f.z = 0.0D;
            float f1 = (float)tmpV3f.length();
            float f4 = 0.55F + 0.15F * (float)Skill;
            tmpV3f.normalize();
            Vtarg.set(target.Vwld);
            Vtarg.z = 0.0D;
            float f7 = (float)tmpV3f.dot(Vtarg);
            float f8 = getSpeed() - f7;
            if(f8 < 10F)
                f8 = 10F;
            float f9 = f1 / f8;
            if(f9 < 0.0F)
                f9 = 0.0F;
            tmpV3f.scale(Vtarg, f9 * f4);
            Ve.add(tmpV3f);
            Or.transformInv(Ve);
            Ve.normalize();
            setSpeedMode(4);
            smConstSpeed = 140F;
            farTurnToDirection();
            sub_Man_Count++;
            if(f1 < ApproachR)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 1: // '\001'
            Ve.set(TargV);
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            float f2 = (float)Ve.length();
            float f5 = 0.55F + 0.15F * (float)Skill;
            tmpV3f.sub(target.Vwld, Vwld);
            float f10 = f2 * bullTime * 0.0025F;
            if(f10 > 0.02F)
                f10 = 0.02F;
            tmpV3f.scale(f2 * f10 * f5);
            Ve.add(tmpV3f);
            Vtarg.set(Ve);
            Or.transformInv(Ve);
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)Ve.x, f2, this);
            if(f2 > 3200F || Vtarg.z > 1500D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                break;
            }
            if(Ve.x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)Skill * (800F + f2)) * (0.30000001192092896D - Ve.x);
                Ve.set(Vtarg);
                Or.transformInv(Ve);
                Ve.normalize();
            }
            attackTurnToDirection(f2, f, 10F);
            if(target.getSpeed() > 120F)
            {
                setSpeedMode(2);
                tailForStaying = target;
            } else
            {
                setSpeedMode(4);
                smConstSpeed = 120F;
            }
            if(f2 < 400F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            setCheckStrike(false);
            if(rocketsDelay > 0)
                rocketsDelay--;
            if(bulletDelay > 0)
                bulletDelay--;
            if(bulletDelay == 120)
                bulletDelay = 0;
            setRandomTargDeviation(0.8F);
            Ve.set(TargV);
            Ve.add(TargDevV);
            target.Or.transform(Ve);
            Ve.add(target.Loc);
            Ve.sub(Loc);
            float f3 = (float)Ve.length();
            float f6 = 0.55F + 0.15F * (float)Skill;
            tmpV3f.sub(target.Vwld, Vwld);
            tmpV3f.scale(f3 * bullTime * f6);
            Ve.add(tmpV3f);
            Vtarg.set(Ve);
            Or.transformInv(Ve);
            if(f3 > 4000F || Vtarg.z > 2000D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                break;
            }
            if(AS.astatePilotStates[1] < 100 && f3 < 330F && java.lang.Math.abs(Or.getKren()) < 3F)
                CT.WeaponControl[0] = true;
            Ve.normalize();
            if(Ve.x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)Skill * (800F + f3)) * (0.30000001192092896D - Ve.x);
                Ve.set(Vtarg);
                Or.transformInv(Ve);
                Ve.normalize();
            }
            attackTurnToDirection(f3, f, 6F + (float)Skill * 3F);
            setSpeedMode(1);
            tailForStaying = target;
            tailOffset.set(-190D, 0.0D, 0.0D);
            if(sub_Man_Count > 10000 || f3 < 240F)
            {
                push(9);
                pop();
            }
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    public void fighterVsFighter(float f)
    {
        if(sub_Man_Count == 0)
        {
            float f1 = 0.0F;
            if(CT.Weapons[1] != null && CT.Weapons[1][0].countBullets() > 0)
                f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            else
            if(CT.Weapons[0] != null)
                f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / f1;
        }
        maxAOA = 15F;
        if(rocketsDelay > 0)
            rocketsDelay--;
        if(bulletDelay > 0)
            bulletDelay--;
        if(bulletDelay == 122)
            bulletDelay = 0;
        Ve.sub(target.Loc, Loc);
        setRandomTargDeviation(0.3F);
        Ve.add(TargDevV);
        Vtarg.set(Ve);
        float f2 = (float)Ve.length();
        float f3 = 0.55F + 0.15F * (float)Skill;
        float f4 = 0.0002F * (float)Skill;
        tmpV3f.sub(target.Vwld, Vwld);
        Vpl.set(tmpV3f);
        tmpV3f.scale(f2 * (bullTime + f4) * f3);
        Ve.add(tmpV3f);
        tmpV3f.set(Vpl);
        tmpV3f.scale(f2 * bullTime * f3);
        Vtarg.add(tmpV3f);
        Or.transformInv(Vpl);
        float f5 = (float)(-Vpl.x);
        if(f5 < 0.001F)
            f5 = 0.001F;
        Vpl.normalize();
        if(Vpl.x < -0.93999999761581421D && f2 / f5 < 1.5F * (float)(3 - Skill))
        {
            push();
            set_maneuver(14);
            return;
        }
        Or.transformInv(Vtarg);
        if(Vtarg.x > 0.0D && f2 < 500F && (shotAtFriend <= 0 || distToFriend > f2))
        {
            if(java.lang.Math.abs(Vtarg.y) < 13D && java.lang.Math.abs(Vtarg.z) < 13D)
                ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.95F, f2, this);
            if(java.lang.Math.abs(Vtarg.y) < (double)(12.5F - 3.5F * (float)Skill) && java.lang.Math.abs(Vtarg.z) < 12.5D - 3.5D * (double)Skill && bulletDelay < 120)
            {
                if(!(actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                    CT.WeaponControl[0] = true;
                bulletDelay += 2;
                if(bulletDelay >= 118)
                {
                    int i = (int)(target.getW().length() * 150D);
                    if(i > 60)
                        i = 60;
                    bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * Skill, 2 * i * Skill);
                }
                if(f2 < 400F)
                {
                    CT.WeaponControl[1] = true;
                    if(f2 < 100F && CT.Weapons[2] != null && CT.Weapons[2][0] != null && CT.Weapons[2][CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                    {
                        tmpV3f.sub(target.Vwld, Vwld);
                        Or.transformInv(tmpV3f);
                        if(java.lang.Math.abs(tmpV3f.y) < 4D && java.lang.Math.abs(tmpV3f.z) < 4D)
                            CT.WeaponControl[2] = true;
                        com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)actor);
                        rocketsDelay += 120;
                    }
                }
                ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(actor);
            }
        }
        Vtarg.set(Ve);
        Or.transformInv(Ve);
        Ve.normalize();
        ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)Ve.x, f2, this);
        if(Ve.x < 0.30000001192092896D)
        {
            Vtarg.z += (double)(0.01F * (float)Skill * (800F + f2)) * (0.30000001192092896D - Ve.x);
            Ve.set(Vtarg);
            Or.transformInv(Ve);
            Ve.normalize();
        }
        attackTurnToDirection(f2, f, 10F + (float)Skill * 8F);
        if(Alt > 150F && Ve.x > 0.59999999999999998D && (double)f2 < 70D)
        {
            setSpeedMode(1);
            tailForStaying = target;
            tailOffset.set(-20D, 0.0D, 0.0D);
        } else
        {
            setSpeedMode(9);
        }
    }

    public void boomAttack(float f)
    {
        if(sub_Man_Count == 0)
        {
            float f1 = 0.0F;
            if(CT.Weapons[1] != null && CT.Weapons[1][0].countBullets() > 0)
                f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[1][0]).bulletSpeed();
            else
            if(CT.Weapons[0] != null)
                f1 = ((com.maddox.il2.engine.GunGeneric)CT.Weapons[0][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / f1;
        }
        maxAOA = 15F;
        if(rocketsDelay > 0)
            rocketsDelay--;
        if(bulletDelay > 0)
            bulletDelay--;
        if(bulletDelay == 122)
            bulletDelay = 0;
        Ve.sub(target.Loc, Loc);
        setRandomTargDeviation(0.3F);
        Ve.add(TargDevV);
        Vtarg.set(Ve);
        float f2 = (float)Ve.length();
        float f3 = 0.55F + 0.15F * (float)Skill;
        float f4 = 0.000333F * (float)Skill;
        tmpV3f.sub(target.Vwld, Vwld);
        Vpl.set(tmpV3f);
        tmpV3f.scale(f2 * (bullTime + f4) * f3);
        Ve.add(tmpV3f);
        tmpV3f.set(Vpl);
        tmpV3f.scale(f2 * bullTime * f3);
        Vtarg.add(tmpV3f);
        Or.transformInv(Vpl);
        float f5 = (float)(-Vpl.x);
        if(f5 < 0.001F)
            f5 = 0.001F;
        Vpl.normalize();
        if(Vpl.x < -0.93999999761581421D && f2 / f5 < 1.5F * (float)(3 - Skill))
        {
            push();
            set_maneuver(14);
            return;
        }
        Or.transformInv(Vtarg);
        if(Vtarg.x > 0.0D && f2 < 700F && (shotAtFriend <= 0 || distToFriend > f2) && java.lang.Math.abs(Vtarg.y) < (double)(15.5F - 3.5F * (float)Skill) && java.lang.Math.abs(Vtarg.z) < 15.5D - 3.5D * (double)Skill && bulletDelay < 120)
        {
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.8F, f2, this);
            if(!(actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                CT.WeaponControl[0] = true;
            bulletDelay += 2;
            if(bulletDelay >= 118)
            {
                int i = (int)(target.getW().length() * 150D);
                if(i > 60)
                    i = 60;
                bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * Skill, 2 * i * Skill);
            }
            if(f2 < 500F)
            {
                CT.WeaponControl[1] = true;
                if(f2 < 100F && CT.Weapons[2] != null && CT.Weapons[2][0] != null && CT.Weapons[2][CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                {
                    tmpV3f.sub(target.Vwld, Vwld);
                    Or.transformInv(tmpV3f);
                    if(java.lang.Math.abs(tmpV3f.y) < 4D && java.lang.Math.abs(tmpV3f.z) < 4D)
                        CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)actor);
                    rocketsDelay += 120;
                }
            }
            ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(actor);
        }
        Vtarg.set(Ve);
        Or.transformInv(Ve);
        Ve.normalize();
        ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)Ve.x, f2, this);
        if(Ve.x < 0.89999997615814209D)
        {
            Vtarg.z += (double)(0.03F * (float)Skill * (800F + f2)) * (0.89999997615814209D - Ve.x);
            Ve.set(Vtarg);
            Or.transformInv(Ve);
            Ve.normalize();
        }
        attackTurnToDirection(f2, f, 10F + (float)Skill * 8F);
    }

    private void turnToDirection(float f)
    {
        if(java.lang.Math.abs(Ve.y) > 0.10000000149011612D)
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.z) - 0.016F * Or.getKren();
        else
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.016F * Or.getKren();
        CT.RudderControl = -10F * (float)java.lang.Math.atan2(Ve.y, Ve.x);
        if((double)CT.RudderControl * W.z > 0.0D)
            W.z = 0.0D;
        else
            W.z *= 1.0399999618530273D;
        float f1 = (float)java.lang.Math.atan2(Ve.z, Ve.x);
        if(java.lang.Math.abs(Ve.y) < 0.0020000000949949026D && java.lang.Math.abs(Ve.z) < 0.0020000000949949026D)
            f1 = 0.0F;
        if(Ve.x < 0.0D)
        {
            f1 = 1.0F;
        } else
        {
            if((double)f1 * W.y > 0.0D)
                W.y = 0.0D;
            if(f1 < 0.0F)
            {
                if(getOverload() < 0.1F)
                    f1 = 0.0F;
                if(CT.ElevatorControl > 0.0F)
                    CT.ElevatorControl = 0.0F;
            } else
            if(CT.ElevatorControl < 0.0F)
                CT.ElevatorControl = 0.0F;
        }
        if(getOverload() > maxG || AOA > maxAOA || CT.ElevatorControl > f1)
            CT.ElevatorControl -= 0.3F * f;
        else
            CT.ElevatorControl += 0.3F * f;
    }

    private void farTurnToDirection()
    {
        farTurnToDirection(4F);
    }

    private void farTurnToDirection(float f)
    {
        Vpl.set(1.0D, 0.0D, 0.0D);
        tmpV3f.cross(Vpl, Ve);
        Or.transform(tmpV3f);
        CT.RudderControl = -10F * (float)java.lang.Math.atan2(Ve.y, Ve.x) + 1.0F * (float)W.y;
        float f7 = (getSpeed() / Vmax) * 45F;
        if(f7 > 85F)
            f7 = 85F;
        float f8 = (float)Ve.x;
        if(f8 < 0.0F)
            f8 = 0.0F;
        float f1;
        if(tmpV3f.z >= 0.0D)
            f1 = (-0.02F * (f7 + Or.getKren()) * (1.0F - f8) - 0.05F * Or.getTangage()) + 1.0F * (float)W.x;
        else
            f1 = -0.02F * (-f7 + Or.getKren()) * (1.0F - f8) + 0.05F * Or.getTangage() + 1.0F * (float)W.x;
        float f2 = (-(float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.016F * Or.getKren()) + 1.0F * (float)W.x;
        float f4 = -0.1F * (getAOA() - 10F) + 0.5F * (float)getW().y;
        float f5;
        if(Ve.z > 0.0D)
            f5 = -0.1F * (getAOA() - f) + 0.5F * (float)getW().y;
        else
            f5 = 1.0F * (float)Ve.z + 0.5F * (float)getW().y;
        if(java.lang.Math.abs(Ve.y) < 0.0020000000949949026D)
        {
            f2 = 0.0F;
            CT.RudderControl = 0.0F;
        }
        float f9 = 1.0F - java.lang.Math.abs((float)Ve.y) * 2.0F;
        if(f9 < 0.0F || Ve.x < 0.0D)
            f9 = 0.0F;
        float f3 = f9 * f2 + (1.0F - f9) * f1;
        float f6 = f9 * f5 + (1.0F - f9) * f4;
        CT.AileronControl = f3;
        CT.ElevatorControl = f6;
    }

    private void attackTurnToDirection(float f, float f1, float f2)
    {
        if(Ve.x < 0.0099999997764825821D)
            Ve.x = 0.0099999997764825821D;
        if(sub_Man_Count == 0)
            oldVe.set(Ve);
        if(Ve.x > 0.94999998807907104D)
        {
            CT.RudderControl = (float)(-30D * java.lang.Math.atan2(Ve.y, Ve.x) + 1.5D * (Ve.y - oldVe.y));
            float f3;
            if(Ve.z > 0.0D || CT.RudderControl > 0.9F)
            {
                f3 = (float)(10D * java.lang.Math.atan2(Ve.z, Ve.x) + 6D * (Ve.z - oldVe.z));
                CT.AileronControl = (-30F * (float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.02F * Or.getKren()) + 5F * (float)W.x;
            } else
            {
                f3 = (float)(5D * java.lang.Math.atan2(Ve.z, Ve.x) + 6D * (Ve.z - oldVe.z));
                CT.AileronControl = (-5F * (float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.02F * Or.getKren()) + 5F * (float)W.x;
            }
            if(Ve.x > (double)(1.0F - 0.005F * (float)Skill))
            {
                tmpOr.set(Or);
                tmpOr.increment((float)java.lang.Math.toDegrees(java.lang.Math.atan2(Ve.y, Ve.x)), (float)java.lang.Math.toDegrees(java.lang.Math.atan2(Ve.z, Ve.x)), 0.0F);
                Or.interpolate(tmpOr, 0.1F);
            }
            if(java.lang.Math.abs(CT.ElevatorControl - f3) < f2 * f1)
                CT.ElevatorControl = f3;
            else
            if(CT.ElevatorControl < f3)
                CT.ElevatorControl += f2 * f1;
            else
                CT.ElevatorControl -= f2 * f1;
        } else
        {
            if(Skill >= 2 && Ve.z > 0.5D && f < 600F)
                CT.FlapsControl = 0.1F;
            else
                CT.FlapsControl = 0.0F;
            float f5 = 0.6F - (float)Ve.z;
            if(f5 < 0.0F)
                f5 = 0.0F;
            CT.RudderControl = (float)(-30D * java.lang.Math.atan2(Ve.y, Ve.x) * (double)f5 + 1.0D * (Ve.y - oldVe.y) * Ve.x + 0.5D * W.z);
            float f4;
            if(Ve.z > 0.0D)
            {
                f4 = (float)(10D * java.lang.Math.atan2(Ve.z, Ve.x) + 6D * (Ve.z - oldVe.z) + 0.5D * (double)(float)W.y);
                if(f4 < 0.0F)
                    f4 = 0.0F;
                CT.AileronControl = (float)((-20D * java.lang.Math.atan2(Ve.y, Ve.z) - 0.050000000000000003D * (double)Or.getKren()) + 5D * W.x);
            } else
            {
                f4 = (float)(-5D * java.lang.Math.atan2(Ve.z, Ve.x) + 6D * (Ve.z - oldVe.z) + 0.5D * (double)(float)W.y);
                CT.AileronControl = (float)((-20D * java.lang.Math.atan2(Ve.y, Ve.z) - 0.050000000000000003D * (double)Or.getKren()) + 5D * W.x);
            }
            if(f4 < 0.0F)
                f4 = 0.0F;
            if(java.lang.Math.abs(CT.ElevatorControl - f4) < f2 * f1)
                CT.ElevatorControl = f4;
            else
            if(CT.ElevatorControl < f4)
                CT.ElevatorControl += 0.3F * f1;
            else
                CT.ElevatorControl -= 0.3F * f1;
        }
        float f6 = 0.054F * (600F - f);
        if(f6 < 4F)
            f6 = 4F;
        if(f6 > AOA_Crit)
            f6 = AOA_Crit;
        if(AOA > f6 - 1.5F)
            Or.increment(0.0F, 0.16F * (f6 - 1.5F - AOA), 0.0F);
        if(AOA < -5F)
            Or.increment(0.0F, 0.12F * (-5F - AOA), 0.0F);
        oldVe.set(Ve);
        oldAOA = AOA;
        sub_Man_Count++;
        W.x *= 0.94999999999999996D;
    }

    private void doCheckStrike()
    {
        if(M.massEmpty > 5000F && !AP.way.isLanding())
            return;
        if(maneuver == 24 && strikeTarg == Leader)
            return;
        if((actor instanceof com.maddox.il2.objects.air.TypeDockable) && ((com.maddox.il2.objects.air.TypeDockable)actor).typeDockableIsDocked())
            return;
        Vpl.sub(strikeTarg.Loc, Loc);
        tmpV3f.set(Vpl);
        Or.transformInv(tmpV3f);
        if(tmpV3f.x < 0.0D)
            return;
        tmpV3f.sub(strikeTarg.Vwld, Vwld);
        tmpV3f.scale(0.69999998807907104D);
        Vpl.add(tmpV3f);
        Or.transformInv(Vpl);
        push();
        if(Vpl.z > 0.0D)
            push(61);
        else
            push(60);
        safe_pop();
    }

    public void setStrikeEmer(com.maddox.il2.fm.FlightModel flightmodel)
    {
        strikeEmer = true;
        strikeTarg = flightmodel;
    }

    private float bombDist(float f)
    {
        float f1 = com.maddox.il2.fm.FMMath.positiveSquareEquation(-0.5F * com.maddox.il2.ai.World.g(), (float)Vwld.z, f);
        if(f1 < 0.0F)
            return -1000000F;
        else
            return f1 * (float)java.lang.Math.sqrt(Vwld.x * Vwld.x + Vwld.y * Vwld.y);
    }

    protected void wingman(boolean flag)
    {
        if(Wingman == null)
        {
            return;
        } else
        {
            Wingman.Leader = flag ? ((com.maddox.il2.fm.FlightModel) (this)) : null;
            return;
        }
    }

    public float getMnTime()
    {
        return mn_time;
    }

    public void doDumpBombsPassively()
    {
        boolean flag = false;
        for(int i = 0; i < CT.Weapons.length; i++)
            if(CT.Weapons[i] != null && CT.Weapons[i].length > 0)
            {
                for(int j = 0; j < CT.Weapons[i].length; j++)
                    if(CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.BombGun)
                        if(CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.BombGunPara)
                        {
                            flag = true;
                        } else
                        {
                            ((com.maddox.il2.objects.weapons.BombGun)CT.Weapons[i][j]).setBombDelay(3.3E+007F);
                            if(actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
                                CT.Weapons[i][j].loadBullets(0);
                        }

            }

        if(!flag)
            bombsOut = true;
    }

    protected void printDebugFM()
    {
        java.lang.System.out.print("<" + actor.name() + "> " + com.maddox.il2.ai.air.ManString.tname(task) + ":" + com.maddox.il2.ai.air.ManString.name(maneuver) + (target == null ? "t" : "T") + (danger == null ? "d" : "D") + (target_ground == null ? "g " : "G "));
        switch(maneuver)
        {
        case 21: // '\025'
            java.lang.System.out.println(": WP=" + AP.way.Cur() + "(" + (AP.way.size() - 1) + ")-" + com.maddox.il2.ai.air.ManString.wpname(AP.way.curr().Action));
            if(target_ground != null)
                java.lang.System.out.println(" GT=" + target_ground.getClass().getName() + "(" + target_ground.name() + ")");
            break;

        case 27: // '\033'
            if(target == null || !com.maddox.il2.engine.Actor.isValid(target.actor))
                java.lang.System.out.println(" T=null");
            else
                java.lang.System.out.println(" T=" + target.actor.getClass().getName() + "(" + target.actor.name() + ")");
            break;

        case 43: // '+'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
            if(target_ground == null || !com.maddox.il2.engine.Actor.isValid(target_ground))
                java.lang.System.out.println(" GT=null");
            else
                java.lang.System.out.println(" GT=" + target_ground.getClass().getName() + "(" + target_ground.name() + ")");
            break;

        default:
            java.lang.System.out.println("");
            if(target == null || !com.maddox.il2.engine.Actor.isValid(target.actor))
            {
                java.lang.System.out.println(" T=null");
                break;
            }
            java.lang.System.out.println(" T=" + target.actor.getClass().getName() + "(" + target.actor.name() + ")");
            if(target_ground == null || !com.maddox.il2.engine.Actor.isValid(target_ground))
                java.lang.System.out.println(" GT=null");
            else
                java.lang.System.out.println(" GT=" + target_ground.getClass().getName() + "(" + target_ground.name() + ")");
            break;
        }
    }

    protected void headTurn(float f)
    {
        if(actor == com.maddox.il2.game.Main3D.cur3D().viewActor() && AS.astatePilotStates[0] < 90)
        {
            boolean flag = false;
            switch(get_task())
            {
            case 2: // '\002'
                if(Leader != null)
                {
                    Ve.set(Leader.Loc);
                    flag = true;
                }
                break;

            case 6: // '\006'
                if(target != null)
                {
                    Ve.set(target.Loc);
                    flag = true;
                }
                break;

            case 5: // '\005'
                if(airClient != null)
                {
                    Ve.set(airClient.Loc);
                    flag = true;
                }
                break;

            case 4: // '\004'
                if(danger != null)
                {
                    Ve.set(danger.Loc);
                    flag = true;
                }
                break;

            case 7: // '\007'
                if(target_ground != null)
                {
                    Ve.set(target_ground.pos.getAbsPoint());
                    flag = true;
                }
                break;
            }
            float f1;
            float f2;
            if(flag)
            {
                Ve.sub(Loc);
                Or.transformInv(Ve);
                tmpOr.setAT0(Ve);
                f1 = tmpOr.getTangage();
                f2 = tmpOr.getYaw();
                if(f2 > 75F)
                    f2 = 75F;
                if(f2 < -75F)
                    f2 = -75F;
                if(f1 < -15F)
                    f1 = -15F;
                if(f1 > 40F)
                    f1 = 40F;
            } else
            if(get_maneuver() != 44)
            {
                f1 = 0.0F;
                f2 = 0.0F;
            } else
            {
                f2 = -15F;
                f1 = -15F;
            }
            if(java.lang.Math.abs(pilotHeadT - f1) > 3F)
                pilotHeadT += 90F * (pilotHeadT <= f1 ? 1.0F : -1F) * f;
            else
                pilotHeadT = f1;
            if(java.lang.Math.abs(pilotHeadY - f2) > 2.0F)
                pilotHeadY += 60F * (pilotHeadY <= f2 ? 1.0F : -1F) * f;
            else
                pilotHeadY = f2;
            tmpOr.setYPR(0.0F, 0.0F, 0.0F);
            tmpOr.increment(0.0F, pilotHeadY, 0.0F);
            tmpOr.increment(pilotHeadT, 0.0F, 0.0F);
            tmpOr.increment(0.0F, 0.0F, -0.2F * pilotHeadT + 0.05F * pilotHeadY);
            headOr[0] = tmpOr.getYaw();
            headOr[1] = tmpOr.getPitch();
            headOr[2] = tmpOr.getRoll();
            headPos[0] = 0.0005F * java.lang.Math.abs(pilotHeadY);
            headPos[1] = -0.0001F * java.lang.Math.abs(pilotHeadY);
            headPos[2] = 0.0F;
            ((com.maddox.il2.engine.ActorHMesh)actor).hierMesh().chunkSetLocate("Head1_D0", headPos, headOr);
        }
    }

    protected void turnOffTheWeapon()
    {
        CT.WeaponControl[0] = false;
        CT.WeaponControl[1] = false;
        CT.WeaponControl[2] = false;
        CT.WeaponControl[3] = false;
        if(bombsOut)
        {
            bombsOutCounter++;
            if(bombsOutCounter > 128)
            {
                bombsOutCounter = 0;
                bombsOut = false;
            }
            if(CT.Weapons[3] != null)
                CT.WeaponControl[3] = true;
            else
                bombsOut = false;
            if(CT.Weapons[3] != null && CT.Weapons[3][0] != null)
            {
                int i = 0;
                for(int j = 0; j < CT.Weapons[3].length; j++)
                    i += CT.Weapons[3][j].countBullets();

                if(i == 0)
                {
                    bombsOut = false;
                    for(int k = 0; k < CT.Weapons[3].length; k++)
                        CT.Weapons[3][k].loadBullets(0);

                }
            }
        }
    }

    protected void turnOnChristmasTree(boolean flag)
    {
        if(flag)
        {
            if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.22F)
                AS.setNavLightsState(true);
        } else
        {
            AS.setNavLightsState(false);
        }
    }

    protected void turnOnCloudShine(boolean flag)
    {
        if(flag)
        {
            if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.22F)
                AS.setLandingLightState(true);
        } else
        {
            AS.setLandingLightState(false);
        }
    }

    protected void doCheckGround(float f)
    {
        if(CT.AileronControl > 1.0F)
            CT.AileronControl = 1.0F;
        if(CT.AileronControl < -1F)
            CT.AileronControl = -1F;
        if(CT.ElevatorControl > 1.0F)
            CT.ElevatorControl = 1.0F;
        if(CT.ElevatorControl < -1F)
            CT.ElevatorControl = -1F;
        float f4 = 0.0003F * M.massEmpty;
        float f5 = 10F;
        float f6 = 80F;
        if(maneuver == 24)
        {
            f5 = 15F;
            f6 = 120F;
        }
        float f7 = (float)(-Vwld.z) * f5 * f4;
        float f8 = 1.0F + 7F * ((indSpeed - VmaxAllowed) / VmaxAllowed);
        if(f8 > 1.0F)
            f8 = 1.0F;
        if(f8 < 0.0F)
            f8 = 0.0F;
        float f1;
        float f2;
        float f3;
        if(f7 > Alt || Alt < f6 || f8 > 0.0F)
        {
            if(Alt < 0.001F)
                Alt = 0.001F;
            f1 = (f7 - Alt) / Alt;
            java.lang.Math.max(0.016670000000000001D * (double)(f6 - Alt), f1);
            if(f1 > 1.0F)
                f1 = 1.0F;
            if(f1 < 0.0F)
                f1 = 0.0F;
            if(f1 < f8)
                f1 = f8;
            f2 = -0.12F * (Or.getTangage() - 5F) + 3F * (float)W.y;
            f3 = -0.07F * Or.getKren() + 3F * (float)W.x;
            if(f3 > 2.0F)
                f3 = 2.0F;
            if(f3 < -2F)
                f3 = -2F;
            if(f2 > 2.0F)
                f2 = 2.0F;
            if(f2 < -2F)
                f2 = -2F;
        } else
        {
            f1 = 0.0F;
            f2 = 0.0F;
            f3 = 0.0F;
        }
        float f9 = 0.2F;
        if(corrCoeff < f1)
            corrCoeff = f1;
        if(corrCoeff > f1)
            corrCoeff *= 1.0D - (double)(f9 * f);
        if(f2 < 0.0F)
        {
            if(corrElev > f2)
                corrElev = f2;
            if(corrElev < f2)
                corrElev *= 1.0D - (double)(f9 * f);
        } else
        {
            if(corrElev < f2)
                corrElev = f2;
            if(corrElev > f2)
                corrElev *= 1.0D - (double)(f9 * f);
        }
        if(f3 < 0.0F)
        {
            if(corrAile > f3)
                corrAile = f3;
            if(corrAile < f3)
                corrAile *= 1.0D - (double)(f9 * f);
        } else
        {
            if(corrAile < f3)
                corrAile = f3;
            if(corrAile > f3)
                corrAile *= 1.0D - (double)(f9 * f);
        }
        CT.AileronControl = corrCoeff * corrAile + (1.0F - corrCoeff) * CT.AileronControl;
        CT.ElevatorControl = corrCoeff * corrElev + (1.0F - corrCoeff) * CT.ElevatorControl;
        if(Alt < 15F && Vwld.z < 0.0D)
            Vwld.z *= 0.85000002384185791D;
        if(-Vwld.z * 1.5D > (double)Alt || Alt < 10F)
        {
            if(maneuver == 27 || maneuver == 43 || maneuver == 21 || maneuver == 24 || maneuver == 68 || maneuver == 53)
                push();
            push(2);
            pop();
            submaneuver = 0;
            sub_Man_Count = 0;
        }
        W.x *= 0.94999999999999996D;
    }

    protected void setSpeedControl(float f)
    {
        float f1 = 0.8F;
        float f4 = 0.02F;
        float f5 = 1.5F;
        CT.setAfterburnerControl(false);
        switch(speedMode)
        {
        case 1: // '\001'
            if(tailForStaying == null)
            {
                f1 = 1.0F;
            } else
            {
                Ve.sub(tailForStaying.Loc, Loc);
                tailForStaying.Or.transform(tailOffset, tmpV3f);
                Ve.add(tmpV3f);
                float f14 = (float)Ve.z;
                float f6 = 0.005F * (200F - (float)Ve.length());
                Or.transformInv(Ve);
                float f10 = (float)Ve.x;
                Ve.normalize();
                f6 = java.lang.Math.max(f6, (float)Ve.x);
                if(f6 < 0.0F)
                    f6 = 0.0F;
                Ve.set(Vwld);
                Ve.normalize();
                tmpV3f.set(tailForStaying.Vwld);
                tmpV3f.normalize();
                float f8 = (float)tmpV3f.dot(Ve);
                if(f8 < 0.0F)
                    f8 = 0.0F;
                f6 *= f8;
                if(f6 > 0.0F && f10 < 1000F)
                {
                    if(f10 > 300F)
                        f10 = 300F;
                    float f16 = 0.6F;
                    if(tailForStaying.VmaxH == VmaxH)
                        f16 = tailForStaying.CT.getPower();
                    if(Vmax < 83F)
                    {
                        float f18 = f14;
                        if(f18 > 0.0F)
                        {
                            if(f18 > 20F)
                                f18 = 20F;
                            Vwld.z += 0.01F * f18;
                        }
                    }
                    float f12;
                    if(f10 > 0.0F)
                        f12 = 0.007F * f10 + 0.005F * f14;
                    else
                        f12 = 0.03F * f10 + 0.001F * f14;
                    float f19 = getSpeed() - tailForStaying.getSpeed();
                    float f22 = -0.3F * f19;
                    float f25 = -3F * (getForwAccel() - tailForStaying.getForwAccel());
                    if(f19 > 27F)
                    {
                        CT.FlapsControl = 1.0F;
                        f1 = 0.0F;
                    } else
                    {
                        CT.FlapsControl = 0.02F * f19 + 0.02F * -f10;
                        f1 = f16 + f12 + f22 + f25;
                    }
                } else
                {
                    f1 = 1.1F;
                }
            }
            break;

        case 2: // '\002'
            f1 = (float)(1.0D - 8.0000000000000007E-005D * (0.5D * Vwld.lengthSquared() - 9.8000000000000007D * Ve.z - 0.5D * tailForStaying.Vwld.lengthSquared()));
            break;

        case 3: // '\003'
            f1 = CT.PowerControl;
            if(AP.way.curr().Speed < 10F)
                AP.way.curr().set(1.7F * Vmin);
            float f17 = AP.way.curr().Speed / VmaxH;
            f1 = 0.2F + 0.8F * (float)java.lang.Math.pow(f17, 1.5D);
            f1 += 0.1F * (AP.way.curr().Speed - com.maddox.il2.fm.Pitot.Indicator((float)Loc.z, getSpeed())) - 3F * getForwAccel();
            if(getAltitude() < AP.way.curr().z() - 70F)
                f1 += AP.way.curr().z() - 70F - getAltitude();
            if(f1 > 0.93F)
                f1 = 0.93F;
            if(f1 < 0.35F && !AP.way.isLanding())
                f1 = 0.35F;
            break;

        case 4: // '\004'
            f1 = CT.PowerControl;
            f1 += (f4 * (smConstSpeed - com.maddox.il2.fm.Pitot.Indicator((float)Loc.z, getSpeed())) - f5 * getForwAccel()) * f;
            if(f1 > 1.0F)
                f1 = 1.0F;
            break;

        case 5: // '\005'
            f1 = CT.PowerControl;
            CT.FlapsControl = 1.0F;
            f1 += (f4 * (1.3F * VminFLAPS - com.maddox.il2.fm.Pitot.Indicator((float)Loc.z, getSpeed())) - f5 * getForwAccel()) * f;
            break;

        case 8: // '\b'
            f1 = 0.0F;
            break;

        case 6: // '\006'
            f1 = 1.0F;
            break;

        case 9: // '\t'
            f1 = 1.1F;
            CT.setAfterburnerControl(true);
            break;

        case 7: // '\007'
            f1 = smConstPower;
            break;

        case 10: // '\n'
            if(tailForStaying == null)
            {
                f1 = 1.0F;
            } else
            {
                Ve.sub(tailForStaying.Loc, Loc);
                tailForStaying.Or.transform(tailOffset, tmpV3f);
                Ve.add(tmpV3f);
                float f15 = (float)Ve.z;
                float f7 = 0.005F * (200F - (float)Ve.length());
                Or.transformInv(Ve);
                float f11 = (float)Ve.x;
                Ve.normalize();
                f7 = java.lang.Math.max(f7, (float)Ve.x);
                if(f7 < 0.0F)
                    f7 = 0.0F;
                Ve.set(Vwld);
                Ve.normalize();
                tmpV3f.set(tailForStaying.Vwld);
                tmpV3f.normalize();
                float f9 = (float)tmpV3f.dot(Ve);
                if(f9 < 0.0F)
                    f9 = 0.0F;
                f7 *= f9;
                if(f7 > 0.0F && f11 < 1000F)
                {
                    if(f11 > 300F)
                        f11 = 300F;
                    float f20 = 0.6F;
                    if(tailForStaying.VmaxH == VmaxH)
                        f20 = tailForStaying.CT.getPower();
                    if(Vmax < 83F)
                    {
                        float f23 = f15;
                        if(f23 > 0.0F)
                        {
                            if(f23 > 20F)
                                f23 = 20F;
                            Vwld.z += 0.01F * f23;
                        }
                    }
                    float f13;
                    if(f11 > 0.0F)
                        f13 = 0.007F * f11 + 0.005F * f15;
                    else
                        f13 = 0.03F * f11 + 0.001F * f15;
                    float f24 = getSpeed() - tailForStaying.getSpeed();
                    float f26 = -0.3F * f24;
                    float f27 = -3F * (getForwAccel() - tailForStaying.getForwAccel());
                    if(f24 > 27F)
                    {
                        Vwld.scale(0.98000001907348633D);
                        f1 = 0.0F;
                    } else
                    {
                        float f28 = 1.0F - 0.02F * (0.02F * f24 + 0.02F * -f11);
                        if(f28 < 0.98F)
                            f28 = 0.98F;
                        if(f28 > 1.0F)
                            f28 = 1.0F;
                        Vwld.scale(f28);
                        f1 = f20 + f13 + f26 + f27;
                    }
                } else
                {
                    f1 = 1.1F;
                }
            }
            break;

        default:
            return;
        }
        if(f1 > 1.1F)
            f1 = 1.1F;
        else
        if(f1 < 0.0F)
            f1 = 0.0F;
        if((double)java.lang.Math.abs(CT.PowerControl - f1) < 0.5D * (double)f)
            CT.PowerControl = f1;
        else
        if(CT.PowerControl < f1)
            CT.PowerControl += 0.5F * f;
        else
            CT.PowerControl -= 0.5F * f;
        float f21 = EI.engines[0].getCriticalW();
        if(EI.engines[0].getw() > 0.9F * f21)
        {
            float f2 = (10F * (f21 - EI.engines[0].getw())) / f21;
            if(f2 < CT.PowerControl)
                CT.PowerControl = f2;
        }
        if(indSpeed > 0.8F * VmaxAllowed)
        {
            float f3 = (1.0F * (VmaxAllowed - indSpeed)) / VmaxAllowed;
            if(f3 < CT.PowerControl)
                CT.PowerControl = f3;
        }
    }

    private void setRandomTargDeviation(float f)
    {
        if(isTick(16, 0))
        {
            float f1 = f * (8F - 1.5F * (float)Skill);
            TargDevVnew.set(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
        }
        TargDevV.interpolate(TargDevVnew, 0.01F);
    }

    private com.maddox.il2.ai.air.Point_Any getNextAirdromeWayPoint()
    {
        if(airdromeWay == null)
            return null;
        if(airdromeWay.length == 0)
            return null;
        if(curAirdromePoi >= airdromeWay.length)
            return null;
        else
            return airdromeWay[curAirdromePoi++];
    }

    private void findPointForEmLanding(float f)
    {
        com.maddox.JGP.Point3d point3d = elLoc.getPoint();
        if(radius > 2D * (double)rmax)
            if(submaneuver != 69)
                initTargPoint(f);
            else
                return;
        for(int i = 0; i < 32; i++)
        {
            Po.set(Vtarg.x + radius * java.lang.Math.sin(phase), Vtarg.y + radius * java.lang.Math.cos(phase), Loc.z);
            radius += 0.01D * (double)rmax;
            phase += 0.29999999999999999D;
            Ve.sub(Po, Loc);
            double d = Ve.length();
            Or.transformInv(Ve);
            Ve.normalize();
            float f1 = 0.9F - 0.005F * Alt;
            if(f1 < -1F)
                f1 = -1F;
            if(f1 > 0.8F)
                f1 = 0.8F;
            float f2 = 1.5F - 0.0005F * Alt;
            if(f2 < 1.0F)
                f2 = 1.0F;
            if(f2 > 1.5F)
                f2 = 1.5F;
            if((double)rmax > d && d > (double)(rmin * f2) && Ve.x > (double)f1 && isConvenientPoint())
            {
                submaneuver = 69;
                point3d.set(Po);
                pointQuality = curPointQuality;
                return;
            }
        }

    }

    private boolean isConvenientPoint()
    {
        Po.z = com.maddox.il2.engine.Engine.cur.land.HQ_Air(Po.x, Po.y);
        curPointQuality = 50;
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                com.maddox.il2.engine.Engine _tmp = com.maddox.il2.engine.Engine.cur;
                if(com.maddox.il2.engine.Engine.land().isWater(Po.x + 500D * (double)i, Po.y + 500D * (double)j))
                {
                    if(!(actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                        curPointQuality--;
                } else
                if(actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                    curPointQuality--;
                if(com.maddox.il2.engine.Engine.cur.land.HQ_ForestHeightHere(Po.x + 500D * (double)i, Po.y + 500D * (double)j) > 1.0D)
                    curPointQuality -= 2;
                if(com.maddox.il2.engine.Engine.cur.land.EQN(Po.x + 500D * (double)i, Po.y + 500D * (double)j, Ve) > 1110.949951171875D)
                    curPointQuality -= 2;
            }

        }

        if(curPointQuality < 0)
            curPointQuality = 0;
        return curPointQuality > pointQuality;
    }

    private void emergencyTurnToDirection(float f)
    {
        if(java.lang.Math.abs(Ve.y) > 0.10000000149011612D)
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.z) - 0.016F * Or.getKren();
        else
            CT.AileronControl = -(float)java.lang.Math.atan2(Ve.y, Ve.x) - 0.016F * Or.getKren();
        CT.RudderControl = -10F * (float)java.lang.Math.atan2(Ve.y, Ve.x);
        if((double)CT.RudderControl * W.z > 0.0D)
            W.z = 0.0D;
        else
            W.z *= 1.0399999618530273D;
    }

    private void initTargPoint(float f)
    {
        int i = AP.way.Cur();
        Vtarg.set(AP.way.last().x(), AP.way.last().y(), 0.0D);
        AP.way.setCur(i);
        Vtarg.sub(Loc);
        Vtarg.z = 0.0D;
        if(Vtarg.length() > (double)rmax)
        {
            Vtarg.normalize();
            Vtarg.scale(rmax);
        }
        Vtarg.add(Loc);
        phase = 0.0D;
        radius = 50D;
        pointQuality = -1;
    }

    private void emergencyLanding(float f)
    {
        if(first)
        {
            Kmax = Wing.new_Cya(5F) / Wing.new_Cxa(5F);
            if(Kmax > 14F)
                Kmax = 14F;
            Kmax *= 0.8F;
            rmax = 1.2F * Kmax * Alt;
            rmin = 0.6F * Kmax * Alt;
            initTargPoint(f);
            setReadyToDieSoftly(true);
            AP.setStabAll(false);
            if(TaxiMode)
            {
                setSpeedMode(8);
                smConstPower = 0.0F;
                push(44);
                pop();
            }
            dist = Alt;
            if(actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                EI.setEngineStops();
        }
        setSpeedMode(4);
        smConstSpeed = VminFLAPS * 1.25F;
        if(Alt < 500F && ((actor instanceof com.maddox.il2.objects.air.TypeGlider) || (actor instanceof com.maddox.il2.objects.air.TypeSailPlane)))
            CT.GearControl = 1.0F;
        if(Alt < 10F)
        {
            CT.AileronControl = -0.04F * Or.getKren();
            setSpeedMode(4);
            smConstSpeed = VminFLAPS * 1.1F;
            if(Alt < 5F)
                setSpeedMode(8);
            CT.BrakeControl = 0.2F;
            if(Vwld.length() < 0.30000001192092896D && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 4)
            {
                setStationedOnGround(true);
                com.maddox.il2.ai.World.cur();
                if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                {
                    push(44);
                    safe_pop();
                    if(actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                    {
                        EI.setCurControlAll(true);
                        EI.setEngineStops();
                        if(com.maddox.il2.engine.Engine.land().isWater(Loc.x, Loc.y))
                            return;
                    }
                    ((com.maddox.il2.objects.air.Aircraft)actor).hitDaSilk();
                }
                if(Group != null)
                    Group.delAircraft((com.maddox.il2.objects.air.Aircraft)actor);
                if((actor instanceof com.maddox.il2.objects.air.TypeGlider) || (actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                    return;
                com.maddox.il2.ai.World.cur();
                if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)actor).postEndAction(60D, actor, 4, null);
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, actor);
            }
        }
        dA = 0.2F * (getSpeed() - Vmin * 1.3F) - 0.8F * (getAOA() - 5F);
        if(Alt < 40F)
        {
            CT.AileronControl = -0.04F * Or.getKren();
            CT.RudderControl = 0.0F;
            if((actor instanceof com.maddox.il2.objects.air.BI_1) || (actor instanceof com.maddox.il2.objects.air.ME_163B1A))
                CT.GearControl = 1.0F;
            float f1;
            if(Gears.Pitch < 10F)
                f1 = (40F * (getSpeed() - VminFLAPS * 1.15F) - 60F * (Or.getTangage() + 3F) - 240F * (getVertSpeed() + 1.0F) - 120F * ((float)getAccel().z - 1.0F)) * 0.004F;
            else
                f1 = (40F * (getSpeed() - VminFLAPS * 1.15F) - 60F * ((Or.getTangage() - Gears.Pitch) + 10F) - 240F * (getVertSpeed() + 1.0F) - 120F * ((float)getAccel().z - 1.0F)) * 0.004F;
            if(Alt > 0.0F)
            {
                float f2 = 0.01666F * Alt;
                dA = dA * f2 + f1 * (1.0F - f2);
            } else
            {
                dA = f1;
            }
            CT.FlapsControl = 0.33F;
            if(Alt < 9F && java.lang.Math.abs(Or.getKren()) < 5F && getVertSpeed() < -0.7F)
                Vwld.z *= 0.87000000476837158D;
        } else
        {
            rmax = 1.2F * Kmax * Alt;
            rmin = 0.6F * Kmax * Alt;
            if((actor instanceof com.maddox.il2.objects.air.TypeGlider) && Alt > 200F)
                CT.RudderControl = 0.0F;
            else
            if(pointQuality < 50 && mn_time > 0.5F)
                findPointForEmLanding(f);
            if(submaneuver == 69)
            {
                Ve.sub(elLoc.getPoint(), Loc);
                double d = Ve.length();
                Or.transformInv(Ve);
                Ve.normalize();
                float f3 = 0.9F - 0.005F * Alt;
                if(f3 < -1F)
                    f3 = -1F;
                if(f3 > 0.8F)
                    f3 = 0.8F;
                if((double)(rmax * 2.0F) < d || d < (double)rmin || Ve.x < (double)f3)
                {
                    submaneuver = 0;
                    initTargPoint(f);
                }
                if(d > 88D)
                {
                    emergencyTurnToDirection(f);
                    if((double)Alt > d)
                        submaneuver = 0;
                } else
                {
                    CT.AileronControl = -0.04F * Or.getKren();
                }
            } else
            {
                CT.AileronControl = -0.04F * Or.getKren();
            }
            if(Or.getTangage() > -1F)
                dA -= 0.1F * (Or.getTangage() + 1.0F);
            if(Or.getTangage() < -10F)
                dA -= 0.1F * (Or.getTangage() + 10F);
        }
        if(Alt < 2.0F || Gears.onGround())
            dA = -2F * (Or.getTangage() - Gears.Pitch);
        double d1 = Vwld.length() / (double)Vmin;
        if(d1 > 1.0D)
            d1 = 1.0D;
        CT.ElevatorControl += (double)((dA - CT.ElevatorControl) * 3.33F * f) + 1.5D * getW().y * d1 + 0.5D * getAW().y * d1;
    }

    public boolean canITakeoff()
    {
        Po.set(Loc);
        Vpl.set(69D, 0.0D, 0.0D);
        Or.transform(Vpl);
        Po.add(Vpl);
        Pd.set(Po);
        if(actor != com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)actor, 70F))
            return false;
        if(canTakeoff)
            return true;
        if(com.maddox.il2.engine.Actor.isAlive(AP.way.takeoffAirport))
        {
            if(AP.way.takeoffAirport.takeoffRequest <= 0)
            {
                AP.way.takeoffAirport.takeoffRequest = 2000;
                canTakeoff = true;
                return true;
            } else
            {
                return false;
            }
        } else
        {
            return true;
        }
    }

    public void set_maneuver_imm(int i)
    {
        int j = maneuver;
        maneuver = i;
        if(j != maneuver)
            set_flags();
    }

    private int task;
    private int maneuver;
    private int curman;
    protected float mn_time;
    private int program[];
    private boolean bBusy;
    public boolean wasBusy;
    public boolean dontSwitch;
    public boolean aggressiveWingman;
    public boolean kamikaze;
    public boolean silence;
    public boolean bombsOut;
    private int bombsOutCounter;
    public float direction;
    public com.maddox.il2.engine.Loc rwLoc;
    private boolean first;
    private int rocketsDelay;
    private int bulletDelay;
    private int submanDelay;
    private static final int volleyL = 120;
    private float maxG;
    private float maxAOA;
    private float LandHQ;
    public float Alt;
    private float oldAOA;
    private float corrCoeff;
    private float corrElev;
    private float corrAile;
    private boolean checkGround;
    private boolean checkStrike;
    private boolean frequentControl;
    private boolean stallable;
    public com.maddox.il2.fm.FlightModel airClient;
    public com.maddox.il2.fm.FlightModel target;
    public com.maddox.il2.fm.FlightModel danger;
    private float dangerAggressiveness;
    private float oldDanCoeff;
    private int shotAtFriend;
    private float distToFriend;
    public com.maddox.il2.engine.Actor target_ground;
    public com.maddox.il2.ai.air.AirGroup Group;
    protected boolean TaxiMode;
    protected boolean finished;
    protected boolean canTakeoff;
    public com.maddox.il2.ai.air.Point_Any wayCurPos;
    protected com.maddox.il2.ai.air.Point_Any wayPrevPos;
    protected com.maddox.il2.ai.air.Point_Any airdromeWay[];
    protected int curAirdromePoi;
    public long actionTimerStart;
    public long actionTimerStop;
    protected int gattackCounter;
    private int beNearOffsPhase;
    public int submaneuver;
    private boolean dont_change_subm;
    private int tmpi;
    private int sub_Man_Count;
    private float dA;
    private float dist;
    private float man1Dist;
    private float bullTime;
    protected static final int STAY_ON_THE_TAIL = 1;
    protected static final int NOT_TOO_FAST = 2;
    protected static final int FROM_WAYPOINT = 3;
    protected static final int CONST_SPEED = 4;
    protected static final int MIN_SPEED = 5;
    protected static final int MAX_SPEED = 6;
    protected static final int CONST_POWER = 7;
    protected static final int ZERO_POWER = 8;
    protected static final int BOOST_ON = 9;
    protected static final int FOLLOW_WITHOUT_FLAPS = 10;
    protected int speedMode;
    protected float smConstSpeed;
    protected float smConstPower;
    protected com.maddox.il2.fm.FlightModel tailForStaying;
    public com.maddox.JGP.Vector3d tailOffset;
    protected int Speak5minutes;
    protected int Speak1minute;
    protected int SpeakBeginGattack;
    protected boolean WeWereInGAttack;
    protected boolean WeWereInAttack;
    protected boolean SpeakMissionAccomplished;
    public static final int ROOKIE = 0;
    public static final int NORMAL = 1;
    public static final int VETERAN = 2;
    public static final int ACE = 3;
    public static final int NO_TASK = 0;
    public static final int WAIT = 1;
    public static final int STAY_FORMATION = 2;
    public static final int FLY_WAYPOINT = 3;
    public static final int DEFENCE = 4;
    public static final int DEFENDING = 5;
    public static final int ATTACK_AIR = 6;
    public static final int ATTACK_GROUND = 7;
    public static final int NONE = 0;
    public static final int HOLD = 1;
    public static final int PULL_UP = 2;
    public static final int LEVEL_PLANE = 3;
    public static final int ROLL = 4;
    public static final int ROLL_90 = 5;
    public static final int ROLL_180 = 6;
    public static final int SPIRAL_BRAKE = 7;
    public static final int SPIRAL_UP = 8;
    public static final int SPIRAL_DOWN = 9;
    public static final int CLIMB = 10;
    public static final int DIVING_0_RPM = 11;
    public static final int DIVING_30_DEG = 12;
    public static final int DIVING_45_DEG = 13;
    public static final int TURN = 14;
    public static final int MIL_TURN = 15;
    public static final int LOOP = 16;
    public static final int LOOP_DOWN = 17;
    public static final int HALF_LOOP_UP = 18;
    public static final int HALF_LOOP_DOWN = 19;
    public static final int STALL = 20;
    public static final int WAYPOINT = 21;
    public static final int SPEEDUP = 22;
    public static final int BELL = 23;
    public static final int FOLLOW = 24;
    public static final int LANDING = 25;
    public static final int TAKEOFF = 26;
    public static final int ATTACK = 27;
    public static final int WAVEOUT = 28;
    public static final int SINUS = 29;
    public static final int ZIGZAG_UP = 30;
    public static final int ZIGZAG_DOWN = 31;
    public static final int ZIGZAG_SPIT = 32;
    public static final int HALF_LOOP_DOWN_135 = 33;
    public static final int HARTMANN_REDOUT = 34;
    public static final int ROLL_360 = 35;
    public static final int STALL_POKRYSHKIN = 36;
    public static final int BARREL_POKRYSHKIN = 37;
    public static final int SLIDE_LEVEL = 38;
    public static final int SLIDE_DESCENT = 39;
    public static final int RANVERSMAN = 40;
    public static final int CUBAN = 41;
    public static final int CUBAN_INVERT = 42;
    public static final int GATTACK = 43;
    public static final int PILOT_DEAD = 44;
    public static final int HANG_ON = 45;
    public static final int DELAY = 48;
    public static final int EMERGENCY_LANDING = 49;
    public static final int GATTACK_DIVE = 50;
    public static final int GATTACK_TORPEDO = 51;
    public static final int GATTACK_CASSETTE = 52;
    public static final int GATTACK_KAMIKAZE = 46;
    public static final int GATTACK_TINYTIM = 47;
    public static final int FAR_FOLLOW = 53;
    public static final int SPIRAL_DOWN_SLOW = 54;
    public static final int FOLLOW_SPIRAL_UP = 55;
    public static final int SINUS_SHALLOW = 56;
    public static final int GAIN = 57;
    public static final int SEPARATE = 58;
    public static final int BE_NEAR = 59;
    public static final int EVADE_UP = 60;
    public static final int EVADE_DN = 61;
    public static final int ENERGY_ATTACK = 62;
    public static final int ATTACK_BOMBER = 63;
    public static final int PARKED_STARTUP = 64;
    public static final int COVER = 65;
    public static final int TAXI = 66;
    public static final int RUN_AWAY = 67;
    public static final int FAR_COVER = 68;
    public static final int TAKEOFF_VTOL_A = 69;
    public static final int LANDING_VTOL_A = 70;
    private static final int SUB_MAN0 = 0;
    private static final int SUB_MAN1 = 1;
    private static final int SUB_MAN2 = 2;
    private static final int SUB_MAN3 = 3;
    private static final int SUB_MAN4 = 4;
    public static final int LIVE = 0;
    public static final int RETURN = 1;
    public static final int TASK = 2;
    public static final int PROTECT_LEADER = 3;
    public static final int PROTECT_WINGMAN = 4;
    public static final int PROTECT_FRIENDS = 5;
    public static final int DESTROY_ENEMIES = 6;
    public static final int KEEP_ORDER = 7;
    public float takeIntoAccount[];
    public float AccountCoeff[];
    public static final int FVSB_BOOM_ZOOM = 0;
    public static final int FVSB_BOOM_ZOOM_TO_ENGINE = 1;
    public static final int FVSB_SHALLOW_DIVE_TO_ENGINE = 2;
    public static final int FVSB_FROM_AHEAD = 3;
    public static final int FVSB_FROM_BELOW = 4;
    public static final int FVSB_AS_IT_IS = 5;
    public static final int FVSB_FROM_SIDE = 6;
    public static final int FVSB_FROM_TAIL_TO_ENGINE = 7;
    public static final int FVSB_FROM_TAIL = 8;
    public static final int FVSB_SHALLOW_DIVE = 9;
    public static final int FVSB_FROM_BOTTOM = 10;
    private static final int demultiplier = 4;
    private com.maddox.JGP.Vector3d ApproachV;
    private com.maddox.JGP.Vector3d TargV;
    private com.maddox.JGP.Vector3d TargDevV;
    private com.maddox.JGP.Vector3d TargDevVnew;
    private com.maddox.JGP.Vector3d scaledApproachV;
    private float ApproachR;
    private float TargY;
    private float TargZ;
    private float TargYS;
    private float TargZS;
    private float RandomVal;
    public com.maddox.JGP.Vector3d followOffset;
    private com.maddox.JGP.Vector3d followTargShift;
    private com.maddox.JGP.Vector3d followCurShift;
    private float raAilShift;
    private float raElevShift;
    private float raRudShift;
    private float sinKren;
    private boolean strikeEmer;
    private com.maddox.il2.fm.FlightModel strikeTarg;
    private com.maddox.JGP.Vector3d strikeV;
    private boolean direc;
    float Kmax;
    float rmin;
    float rmax;
    double phase;
    double radius;
    int pointQuality;
    int curPointQuality;
    private static com.maddox.JGP.Vector3d tmpV3d = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpV3f = new Vector3d();
    public static com.maddox.il2.engine.Orient tmpOr = new Orient();
    public com.maddox.il2.engine.Orient saveOr;
    private static com.maddox.JGP.Point3d Po = new Point3d();
    private static com.maddox.JGP.Point3d Pc = new Point3d();
    private static com.maddox.JGP.Point3d Pd = new Point3d();
    private static com.maddox.JGP.Vector3d Ve = new Vector3d();
    private com.maddox.JGP.Vector3d oldVe;
    private com.maddox.JGP.Vector3d Vtarg;
    private com.maddox.JGP.Vector3d constVtarg;
    private com.maddox.JGP.Vector3d constVtarg1;
    private static com.maddox.JGP.Vector3d Vf = new Vector3d();
    private com.maddox.JGP.Vector3d Vxy;
    private static com.maddox.JGP.Vector3d Vpl = new Vector3d();
    private com.maddox.il2.ai.AnglesFork AFo;
    private static com.maddox.JGP.Vector3f LandingOffset = new Vector3f(220F, 4F, 0.0F);
    private float headPos[];
    private float headOr[];
    private static com.maddox.JGP.Point3d P = new Point3d();
    private static com.maddox.JGP.Point2f Pcur = new Point2f();
    private static com.maddox.JGP.Vector2d Vcur = new Vector2d();
    private static com.maddox.JGP.Vector2f V_to = new Vector2f();
    private static com.maddox.JGP.Vector2d Vdiff = new Vector2d();
    private static com.maddox.il2.engine.Loc elLoc = new Loc();
    public static boolean showFM;
    private float pilotHeadT;
    private float pilotHeadY;

}
