// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Maneuver.java

package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple2d;
import com.maddox.JGP.Tuple2f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
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
import com.maddox.il2.engine.Interpolate;
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
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Polares;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.objects.air.AR_234;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BI_1;
import com.maddox.il2.objects.air.BI_6;
import com.maddox.il2.objects.air.G4M2E;
import com.maddox.il2.objects.air.HE_LERCHE3;
import com.maddox.il2.objects.air.JU_88NEW;
import com.maddox.il2.objects.air.KI_46_OTSUHEI;
import com.maddox.il2.objects.air.ME_163B1A;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.Mig_17PF;
import com.maddox.il2.objects.air.SM79;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.Swordfish;
import com.maddox.il2.objects.air.TA_152C;
import com.maddox.il2.objects.air.TA_183;
import com.maddox.il2.objects.air.TypeAcePlane;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeGuidedBombCarrier;
import com.maddox.il2.objects.air.TypeHasToKG;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeSupersonic;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunNull;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.BombGunTorp45_36AV_A;
import com.maddox.il2.objects.weapons.ParaTorpedoGun;
import com.maddox.il2.objects.weapons.ToKGUtils;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.Random;

// Referenced classes of package com.maddox.il2.ai.air:
//            AutopilotAI, AirGroup, Pilot, Airdrome, 
//            Point_Null, ManString, AirGroupList, Point_Any

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
        if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeStormovik)
            return false;
        else
            return !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeTransport);
    }

    private void resetControls()
    {
        ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
    }

    private void set_flags()
    {
        if(com.maddox.il2.ai.World.cur().isDebugFM())
            printDebugFM();
        ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
        mn_time = 0.0F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 4F;
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
        if(maneuver == 20 || maneuver == 25 || maneuver == 66 || maneuver == 1 || maneuver == 26 || maneuver == 69 || maneuver == 44 || maneuver == 49 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 81 || maneuver == 46 || maneuver == 2 || maneuver == 10 || maneuver == 57 || maneuver == 64)
            setCheckGround(false);
        else
            setCheckGround(true);
        if(maneuver == 24 || maneuver == 53 || maneuver == 68 || maneuver == 59 || maneuver == 8 || maneuver == 55 || maneuver == 27 || maneuver == 62 || maneuver == 63 || maneuver == 25 || maneuver == 66 || maneuver == 43 || maneuver == 50 || maneuver == 65 || maneuver == 44 || maneuver == 21 || maneuver == 64 || maneuver == 69)
            frequentControl = true;
        else
            frequentControl = false;
        if(maneuver == 25 || maneuver == 26 || maneuver == 69 || maneuver == 70)
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
        if(maneuver == 44 || maneuver == 1 || maneuver == 48 || maneuver == 0 || maneuver == 26 || maneuver == 69 || maneuver == 64 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 52 || maneuver == 47 || maneuver == 79 || maneuver == 80)
            stallable = false;
        else
            stallable = true;
        if(maneuver == 44 || maneuver == 1 || maneuver == 26 || maneuver == 69 || maneuver == 64 || maneuver == 2 || maneuver == 57 || maneuver == 60 || maneuver == 61 || maneuver == 43 || maneuver == 50 || maneuver == 51 || maneuver == 52 || maneuver == 47 || maneuver == 29 || maneuver == 79 || maneuver == 80)
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
        windV = new Vector3d();
        ((com.maddox.il2.fm.FlightModelMain)this).AP = ((com.maddox.il2.fm.Autopilotage) (new AutopilotAI(((com.maddox.il2.fm.FlightModel) (this)))));
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
        double d = (double)(((com.maddox.il2.fm.FlightModelMain) (flightmodel)).Energy - ((com.maddox.il2.fm.FlightModelMain)this).Energy) * 0.1019D;
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
        if(f < ((com.maddox.il2.fm.FlightModelMain)this).HofVmax)
            return ((com.maddox.il2.fm.FlightModelMain)this).Vmax + (((com.maddox.il2.fm.FlightModelMain)this).VmaxH - ((com.maddox.il2.fm.FlightModelMain)this).Vmax) * (f / ((com.maddox.il2.fm.FlightModelMain)this).HofVmax);
        float f1 = (f - ((com.maddox.il2.fm.FlightModelMain)this).HofVmax) / ((com.maddox.il2.fm.FlightModelMain)this).HofVmax;
        f1 = 1.0F - 1.5F * f1;
        if(f1 < 0.0F)
            f1 = 0.0F;
        return ((com.maddox.il2.fm.FlightModelMain)this).VmaxH * f1;
    }

    public float getMinHeightTurn(float f)
    {
        return ((com.maddox.il2.fm.FlightModelMain)this).Wing.T_turn;
    }

    public void setShotAtFriend(float f)
    {
        distToFriend = f;
        shotAtFriend = 30;
    }

    public boolean hasCourseWeaponBullets()
    {
        if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI)
            return ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() != 0;
        if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.AR_234)
            return false;
        return ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0].countBullets() != 0 || ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() != 0;
    }

    public boolean hasBombs()
    {
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null)
        {
            for(int i = 0; i < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3].length; i++)
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][i] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][i].countBullets() != 0)
                    return true;

        }
        return false;
    }

    public boolean hasRockets()
    {
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2] != null)
        {
            for(int i = 0; i < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2].length; i++)
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][i] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][i].countBullets() != 0)
                    return true;

        }
        return false;
    }

    public boolean canAttack()
    {
        return (!Group.isWingman(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)) || aggressiveWingman) && ((com.maddox.il2.fm.FlightModelMain)this).isOk() && hasCourseWeaponBullets() || hasRockets();
    }

    public void update(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            headTurn(f);
        if(showFM)
            OutCT(20);
        super.update(f);
        super.callSuperUpdate = true;
        decDangerAggressiveness();
        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z < -20D)
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).postEndAction(0.0D, ((com.maddox.il2.engine.Interpolate)this).actor, 4, ((com.maddox.il2.engine.Eff3DActor) (null)));
        LandHQ = (float)com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y);
        ((com.maddox.JGP.Tuple3d) (Po)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        ((com.maddox.JGP.Tuple3d) (Po)).scale(3D);
        ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        LandHQ = (float)java.lang.Math.max(LandHQ, com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (Po)).x, ((com.maddox.JGP.Tuple3d) (Po)).y));
        Alt = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - LandHQ;
        ((com.maddox.il2.fm.FlightModelMain)this).indSpeed = ((com.maddox.il2.fm.FlightModel)this).getSpeed() * (float)java.lang.Math.sqrt(super.Density / 1.225F);
        if(!((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && ((com.maddox.il2.fm.FlightModelMain)this).isOk() && Alt > 8F)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 2.0F)
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.05F * (((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 2.0F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA < -5F)
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.05F * (-5F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
        }
        if(!frequentControl && (com.maddox.rts.Time.tickCounter() + ((com.maddox.il2.engine.Interpolate)this).actor.hashCode()) % 4 != 0)
            return;
        turnOffTheWeapon();
        maxG = 3.5F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 0.5F;
        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).wrap();
        if(mn_time > 10F && ((com.maddox.il2.fm.FlightModelMain)this).AOA > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit + 5F && isStallable() && stallable)
            safe_set_maneuver(20);
        switch(maneuver)
        {
        case 58: // ':'
        case 71: // 'G'
        case 72: // 'H'
        case 73: // 'I'
        case 74: // 'J'
        case 75: // 'K'
        case 76: // 'L'
        case 77: // 'M'
        case 78: // 'N'
        default:
            break;

        case 0: // '\0'
            target_ground = null;
            break;

        case 1: // '\001'
            ((com.maddox.il2.fm.FlightModel)this).dryFriction = 8F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
            break;

        case 48: // '0'
            if(mn_time >= 1.0F)
                pop();
            break;

        case 44: // ','
            if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && ((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() < 0.30000001192092896D && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 4)
            {
                if(Group != null)
                    Group.delAircraft((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                    break;
                com.maddox.il2.ai.World.cur();
                if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(((com.maddox.il2.fm.FlightModelMain)this).Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).postEndAction(60D, ((com.maddox.il2.engine.Interpolate)this).actor, 3, ((com.maddox.il2.engine.Eff3DActor) (null)));
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
            }
            if(first)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.07F, 0.4F);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.15F, 0.15F);
            }
            break;

        case 7: // '\007'
            wingman(false);
            setSpeedMode(9);
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).x <= 0.0D)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -1F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 1.0F;
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 1.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -1F;
            }
            float f1 = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(f1 > -90F && f1 < 90F)
            {
                float f4 = 0.01111F * (90F - java.lang.Math.abs(f1));
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.08F * f4 * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 3F);
            } else
            {
                float f5 = 0.01111F * (java.lang.Math.abs(f1) - 90F);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.08F * f5 * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 3F);
            }
            if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < 1.7F * ((com.maddox.il2.fm.FlightModelMain)this).Vmin)
                pop();
            if(mn_time > 2.2F)
                pop();
            if(danger != null && (danger instanceof com.maddox.il2.ai.air.Pilot) && ((com.maddox.il2.ai.air.Maneuver)danger).target == this && ((com.maddox.il2.fm.FlightModelMain)this).Loc.distance(((com.maddox.il2.fm.FlightModelMain) (danger)).Loc) > 400D)
                pop();
            break;

        case 8: // '\b'
            if(first && !((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 0)
                    pop();
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 1)
                    ((com.maddox.il2.fm.FlightModelMain)this).setReadyToReturn(true);
            }
            setSpeedMode(6);
            tmpOr.setYPR(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw(), 0.0F, 0.0F);
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, -6D, 10D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, 6D, 10D);
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            farTurnToDirection();
            if(Alt > 250F && mn_time > 8F || mn_time > 120F)
                pop();
            break;

        case 55: // '7'
            if(first && !((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 0)
                    pop();
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 1)
                    ((com.maddox.il2.fm.FlightModelMain)this).setReadyToReturn(true);
            }
            setSpeedMode(6);
            tmpOr.setYPR(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw(), 0.0F, 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).Leader != null && com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor) && mn_time < 2.5F)
            {
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Or)).getKren() > 0.0F)
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, -6D, 10D);
                else
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, 6D, 10D);
            } else
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, -6D, 10D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, 6D, 10D);
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            farTurnToDirection();
            if(Alt > 250F && mn_time > 8F || mn_time > 120F)
                pop();
            break;

        case 45: // '-'
            setSpeedMode(7);
            smConstPower = 0.8F;
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(dA > 0.0F)
                dA -= 25F;
            else
                dA -= 335F;
            if(dA < -180F)
                dA += 360F;
            dA *= -0.01F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 1.0F) + 0.002F * ((((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().z() - (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z) + 250F);
            if(mn_time > 60F)
            {
                mn_time = 0.0F;
                pop();
            }
            break;

        case 54: // '6'
            if(((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 2.0F))
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).scale(0.99500000476837158D);
            // fall through

        case 9: // '\t'
            if(first && !((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 0)
                    pop();
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill > 1)
                    ((com.maddox.il2.fm.FlightModelMain)this).setReadyToReturn(true);
            }
            setSpeedMode(4);
            smConstSpeed = 100F;
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(dA > 0.0F)
                dA -= 50F;
            else
                dA -= 310F;
            if(dA < -180F)
                dA += 360F;
            dA *= -0.01F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
            dA = (-10F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage()) * 0.05F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if((double)((com.maddox.il2.fm.FlightModelMain)this).getOverload() < 1.0D / java.lang.Math.abs(java.lang.Math.cos(dA)))
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.0F * f;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 1.0F * f;
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
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAltitude(true);
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if((double)((com.maddox.il2.fm.FlightModelMain)this).getOverload() < 1.0D / java.lang.Math.abs(java.lang.Math.cos(dA)))
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.0F * f;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 1.0F * f;
            if(dA > 0.0F)
                dA -= 50F;
            else
                dA -= 310F;
            if(dA < -180F)
                dA += 360F;
            dA *= -0.01F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
            if(mn_time > 5F)
                pop();
            break;

        case 4: // '\004'
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).x <= 0.0D ? -1F : 1.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.1F * (float)java.lang.Math.cos(com.maddox.il2.fm.FMMath.DEG2RAD(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()));
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            if(((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() < 220F)
            {
                push(3);
                pop();
            }
            if(mn_time > 7F)
                pop();
            break;

        case 2: // '\002'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            if(first)
            {
                wingman(false);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 10 && Alt < 80F)
                    com.maddox.il2.objects.sounds.Voice.speakPullUp((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            }
            setSpeedMode(9);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * (dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren());
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 1.0F + 0.3F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < 0.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 15F)
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, (15F - ((com.maddox.il2.fm.FlightModelMain)this).AOA) * 0.5F * f, 0.0F);
            if(Alt < 10F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.89999997615814209D;
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 1.0D)
            {
                if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider)
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
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 1.0F;
            if(mn_time > 0.15F)
                pop();
            break;

        case 61: // '='
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.4F;
            if(mn_time > 0.2F)
                pop();
            break;

        case 3: // '\003'
            if(first && program[0] == 49)
                pop();
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            dA = (((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() - 180F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() * 10F - ((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() * 5F) * 0.004F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if(((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.2F && ((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() > 0.0F)
            {
                setSpeedMode(7);
                smConstPower = 0.7F;
                pop();
            }
            break;

        case 10: // '\n'
            ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            dA = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl;
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 15F)
                dA -= (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 15F) * 0.1F * f;
            else
                dA = (((float)((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() / ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS) * 140F - 50F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() * 20F) * 0.004F;
            dA += ((float) (0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y));
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if(Alt > 250F && mn_time > 6F || mn_time > 20F)
                pop();
            break;

        case 57: // '9'
            ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
            setSpeedMode(9);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            dA = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl;
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 25F)
                dA -= (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 25F) * 0.1F * f;
            else
                dA = (((float)((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() / ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS) * 140F - 50F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() * 20F) * 0.004F;
            dA += ((float) (0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y));
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if(Alt > 150F || Alt > 100F && mn_time > 2.0F || mn_time > 3F)
                pop();
            break;

        case 11: // '\013'
            setSpeedMode(8);
            if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 90F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D || ((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() < 270F)
                    dA = -0.04F;
                else
                    dA = 0.04F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl * 0.9F + dA * 0.1F;
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.04F * (180F - java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()));
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -25F)
                    dA = 0.33F;
                else
                if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D || ((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() < 270F)
                    dA = 0.04F;
                else
                    dA = -0.04F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl * 0.9F + dA * 0.1F;
            }
            if(Alt < 120F || mn_time > 4F)
                pop();
            break;

        case 12: // '\f'
            setSpeedMode(4);
            smConstSpeed = 80F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 2.0F))
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).scale(0.99500000476837158D);
            dA = -((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z / (java.lang.Math.abs(((com.maddox.il2.fm.FlightModel)this).getSpeed()) + 1.0F) + 0.5F);
            if(dA < -0.1F)
                dA = -0.1F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl * 0.9F + dA * 0.1F + 0.3F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
            if(mn_time > 5F || Alt < 200F)
                pop();
            break;

        case 13: // '\r'
            if(first)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                submaneuver = (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter) ? 0 : 2;
            }
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA *= -0.04F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
                if(mn_time > 3F || java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) > 175F - 5F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA *= -0.04F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                setSpeedMode(8);
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -45F && ((com.maddox.il2.fm.FlightModelMain)this).getOverload() < maxG)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.5F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.5F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -44F)
                    submaneuver++;
                if((double)Alt < -5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z || mn_time > 5F)
                    pop();
                break;

            case 2: // '\002'
                setSpeedMode(8);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                dA = -((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z / (java.lang.Math.abs(((com.maddox.il2.fm.FlightModel)this).getSpeed()) + 1.0F) + 0.707F);
                if(dA < -0.75F)
                    dA = -0.75F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl * 0.9F + dA * 0.1F + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
                if((double)Alt < -5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z || mn_time > 5F)
                    pop();
                break;
            }
            if(Alt < 200F)
                pop();
            break;

        case 5: // '\005'
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(dA < 0.0F)
                dA -= 270F;
            else
                dA -= 90F;
            if(dA < -180F)
                dA += 360F;
            dA *= -0.02F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
            if(mn_time > 5F || java.lang.Math.abs(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) - 90F) < 1.0F)
                pop();
            break;

        case 6: // '\006'
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 180F;
            if(dA < -180F)
                dA += 360F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (float)((double)(-0.04F * dA) - 0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).x);
            if(mn_time > 4F || java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) > 178F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).W.x = 0.0D;
                pop();
            }
            break;

        case 35: // '#'
            if(first)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                direction = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                submaneuver = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 1 : -1;
                tmpi = 0;
                setSpeedMode(9);
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 1.0F * (float)submaneuver;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F * (float)submaneuver;
            float f2 = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(f2 > -90F && f2 < 90F)
            {
                float f6 = 0.01111F * (90F - java.lang.Math.abs(f2));
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.08F * f6 * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 3F);
            } else
            {
                float f7 = 0.01111F * (90F - java.lang.Math.abs(f2));
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.08F * f7 * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 3F);
            }
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() * direction < 0.0F)
                tmpi = 1;
            if(tmpi == 1 && (submaneuver > 0 ? ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > direction : ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() < direction) || mn_time > 17.5F)
                pop();
            break;

        case 22: // '\026'
            setSpeedMode(9);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 5F);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            if(((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmax || mn_time > 30F)
                pop();
            break;

        case 67: // 'C'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 18F;
            if(first)
            {
                sub_Man_Count = 0;
                setSpeedMode(9);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(1.1F);
            }
            if(danger != null)
            {
                float f8 = 700F - (float)((com.maddox.il2.fm.FlightModelMain) (danger)).Loc.distance(((com.maddox.il2.fm.FlightModelMain)this).Loc);
                if(f8 < 0.0F)
                    f8 = 0.0F;
                f8 *= 0.00143F;
                float f3 = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                if(sub_Man_Count == 0 || first)
                {
                    if(raAilShift < 0.0F)
                        raAilShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(0.6F, 1.0F);
                    else
                        raAilShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-1F, -0.6F);
                    raRudShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F);
                    raElevShift = f8 * com.maddox.il2.ai.World.Rnd().nextFloat(-0.8F, 0.8F);
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.9F * ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl + 0.1F * raAilShift;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.95F * ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl + 0.05F * raRudShift;
                if(f3 > -90F && f3 < 90F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 5F);
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 5F);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.1F * raElevShift;
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
                if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
                    pop();
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                setSpeedMode(6);
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < 0.75F * ((com.maddox.il2.fm.FlightModelMain)this).Vmax)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
                submaneuver = 0;
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 7F : 12F;
            switch(submaneuver)
            {
            case 0: // '\0'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 2.0F)
                    submaneuver++;
                break;

            case 1: // '\001'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 0.5F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.4F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 80F)
                    submaneuver++;
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.5F)
                    pop();
                break;

            case 2: // '\002'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS() * (((com.maddox.il2.fm.FlightModel)this).getSpeed() > 300F ? 1.0F : 0.0F);
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.4F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < 0.0F)
                    submaneuver++;
                break;

            case 3: // '\003'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS() * (((com.maddox.il2.fm.FlightModel)this).getSpeed() > 300F ? 1.0F : 0.0F);
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.2F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -60F)
                    submaneuver++;
                break;

            case 4: // '\004'
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -45F)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                    maxAOA = 3.5F;
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 0.5F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 1.0F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 3F || ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
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
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.2F)
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
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                submaneuver = 0;
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 7F : 12F;
            switch(submaneuver)
            {
            case 0: // '\0'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 180F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() : -180F + ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren());
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) > 178F)
                    submaneuver++;
                break;

            case 1: // '\001'
                setSpeedMode(7);
                smConstPower = 0.5F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.2F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -60F)
                    submaneuver++;
                break;

            case 2: // '\002'
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -45F)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                    setSpeedMode(9);
                    maxAOA = 7F;
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 0.5F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.8F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > ((com.maddox.il2.fm.FlightModelMain)this).AOA - 1.0F || ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
                    pop();
                break;
            }
            break;

        case 18: // '\022'
            if(first)
            {
                if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
                    pop();
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmax * 0.75F)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                submaneuver = 0;
                setSpeedMode(6);
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 7F : 12F;
            switch(submaneuver)
            {
            case 0: // '\0'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 2.0F)
                    submaneuver++;
                break;

            case 1: // '\001'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.4F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.8F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 80F)
                    submaneuver++;
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.5F)
                    pop();
                break;

            case 2: // '\002'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS() * (((com.maddox.il2.fm.FlightModel)this).getSpeed() > 300F ? 1.0F : 0.0F);
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.4F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < 12F)
                    submaneuver++;
                break;

            case 3: // '\003'
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 60F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 30F)
                    submaneuver++;
                break;

            case 4: // '\004'
                pop();
                break;
            }
            break;

        case 15: // '\017'
            if(first && ((com.maddox.il2.fm.FlightModel)this).getSpeed() < 0.35F * (((com.maddox.il2.fm.FlightModelMain)this).Vmin + ((com.maddox.il2.fm.FlightModelMain)this).Vmax))
            {
                pop();
            } else
            {
                push(8);
                pop();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() < 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + 30F);
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 30F);
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
            if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfBMP())
            {
                ((com.maddox.il2.fm.FlightModelMain)this).setReadyToDie(true);
                pop();
            }
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).z > 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 1.0F;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -1F;
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 4F)
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.01F * (((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 4F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA < -5F)
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.01F * (-5F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).AOA < ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 1.0F)
                pop();
            if(mn_time > 14F - (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 4F || Alt < 50F)
                pop();
            break;

        case 21: // '\025'
            ((com.maddox.il2.fm.FlightModelMain)this).AP.setWayPoint(true);
            if(((com.maddox.il2.fm.FlightModelMain)this).getAltitude() < ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().z() - 100F && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.025F;
            if(mn_time > 300F)
                pop();
            if(((com.maddox.il2.fm.FMMath)this).isTick(256, 0) && !((com.maddox.il2.engine.Interpolate)this).actor.isTaskComplete() && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLast() && ((com.maddox.il2.fm.FlightModelMain)this).AP.getWayPointDistance() < 1500F || ((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLanding()))
                com.maddox.il2.ai.World.onTaskComplete(((com.maddox.il2.engine.Interpolate)this).actor);
            if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).aircIndex() == 0 && !((com.maddox.il2.fm.FlightModelMain)this).isReadyToReturn())
            {
                com.maddox.il2.ai.World.cur();
                if(com.maddox.il2.ai.World.getPlayerAircraft() != null)
                {
                    com.maddox.il2.ai.World.cur();
                    if(((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).getRegiment() == com.maddox.il2.ai.World.getPlayerAircraft().getRegiment())
                    {
                        float f9 = 1E+012F;
                        if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3)
                        {
                            f9 = ((com.maddox.il2.fm.FlightModelMain)this).AP.getWayPointDistance();
                        } else
                        {
                            int i = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur();
                            ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3)
                                f9 = ((com.maddox.il2.fm.FlightModelMain)this).AP.getWayPointDistance();
                            ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(i);
                        }
                        if(Speak5minutes == 0 && 22000F < f9 && f9 < 30000F)
                        {
                            com.maddox.il2.objects.sounds.Voice.speak5minutes((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                            Speak5minutes = 1;
                        }
                        if(Speak1minute == 0 && f9 < 10000F)
                        {
                            com.maddox.il2.objects.sounds.Voice.speak1minute((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                            Speak1minute = 1;
                            Speak5minutes = 1;
                        }
                        if((WeWereInGAttack || WeWereInAttack) && mn_time > 5F)
                        {
                            if(!SpeakMissionAccomplished)
                            {
                                boolean flag = false;
                                int j = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur();
                                if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3 || ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getTarget() != null)
                                    break;
                                do
                                {
                                    if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() >= ((com.maddox.il2.fm.FlightModelMain)this).AP.way.size() - 1)
                                        break;
                                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                                    if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3 || ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getTarget() != null)
                                        flag = true;
                                } while(true);
                                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(j);
                                if(!flag)
                                {
                                    com.maddox.il2.objects.sounds.Voice.speakMissionAccomplished((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
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
            if(((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeBomber) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeTransport)) && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr() != null && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3 && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getTarget() == null || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.Scheme4)))
            {
                double d = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - com.maddox.il2.ai.World.land().HQ(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y);
                if(d < 0.0D)
                    d = 0.0D;
                if((double)((com.maddox.il2.fm.FlightModelMain)this).AP.getWayPointDistance() < (double)((com.maddox.il2.fm.FlightModel)this).getSpeed() * java.lang.Math.sqrt(d * 0.20386999845504761D) && !bombsOut)
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0].countBullets() != 0 && !(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara))
                        com.maddox.il2.objects.sounds.Voice.airSpeaks((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor, 85, 1);
                    bombsOut = true;
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action = 0;
                    if(Group != null)
                        Group.dropBombs();
                }
            }
            setSpeedMode(3);
            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip() && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLanding())
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.landingAirport.rebuildLandWay(((com.maddox.il2.fm.FlightModel) (this)));
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasCockpitDoorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setCockpitDoor(((com.maddox.il2.engine.Interpolate)this).actor, 1);
            }
            break;

        case 23: // '\027'
            if(first)
            {
                wingman(false);
                if(((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.25F)
                {
                    push();
                    push(22);
                    pop();
                    break;
                }
            }
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < 70F && ((com.maddox.il2.fm.FlightModelMain)this).getOverload() < maxG && ((com.maddox.il2.fm.FlightModelMain)this).AOA < 14F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.5F * f;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.5F * f;
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 1.0D)
                pop();
            break;

        case 24: // '\030'
            if(((com.maddox.il2.fm.FlightModelMain)this).Leader == null || !com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor) || !((com.maddox.il2.fm.FlightModelMain) ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.fm.FlightModelMain)this).Leader)).isOk() || ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.fm.FlightModelMain)this).Leader).isBusy() && (!(((com.maddox.il2.fm.FlightModelMain)this).Leader instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.fm.FlightModelMain)this).Leader).isRealMode()))
            {
                set_maneuver(0);
                break;
            }
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).AP.way.curr().Action != 0 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).AP.way.curr().Action != 1)
                    set_maneuver(49);
            } else
            if(((com.maddox.il2.fm.FlightModelMain)this).Leader.getSpeed() < 30F || ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Loc)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Loc)).y) < 50D)
            {
                airClient = ((com.maddox.il2.fm.FlightModelMain)this).Leader;
                push();
                push(59);
                pop();
                break;
            }
            if(!((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).AP.way.isLanding())
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).AP.way.Cur());
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Wingman != this)
                {
                    if(!bombsOut && ((com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.fm.FlightModelMain)this).Leader).bombsOut)
                    {
                        bombsOut = true;
                        for(com.maddox.il2.ai.air.Maneuver maneuver1 = this; ((com.maddox.il2.fm.FlightModelMain) (maneuver1)).Wingman != null;)
                        {
                            maneuver1 = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.fm.FlightModelMain) (maneuver1)).Wingman;
                            maneuver1.bombsOut = true;
                        }

                    }
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl != ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).CT.BayDoorControl)
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).CT.BayDoorControl;
                        for(com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)this; ((com.maddox.il2.fm.FlightModelMain) (pilot)).Wingman != null;)
                        {
                            pilot = (com.maddox.il2.ai.air.Pilot)((com.maddox.il2.fm.FlightModelMain) (pilot)).Wingman;
                            ((com.maddox.il2.fm.FlightModelMain) (pilot)).CT.BayDoorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl;
                        }

                    }
                }
            } else
            {
                if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Wingman != this)
                {
                    push(8);
                    push(8);
                    push(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextBoolean() ? 8 : 48);
                    push(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextBoolean() ? 8 : 48);
                    pop();
                }
                ((com.maddox.il2.fm.FlightModelMain)this).Leader = null;
                pop();
                break;
            }
            airClient = ((com.maddox.il2.fm.FlightModelMain)this).Leader;
            tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain) (airClient)).Vwld);
            tmpOr.increment(0.0F, ((com.maddox.il2.fm.FlightModelMain) (airClient)).getAOA(), 0.0F);
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
            Ve.x -= 300D;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (followTargShift)), ((com.maddox.JGP.Tuple3d) (followCurShift)));
            if(tmpV3f.lengthSquared() < 0.5D)
                ((com.maddox.JGP.Tuple3d) (followTargShift)).set(com.maddox.il2.ai.World.cur().rnd.nextFloat(-0F, 10F), com.maddox.il2.ai.World.cur().rnd.nextFloat(-5F, 5F), com.maddox.il2.ai.World.cur().rnd.nextFloat(-3.5F, 3.5F));
            tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(2.0F * f);
            ((com.maddox.JGP.Tuple3d) (followCurShift)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (followCurShift)));
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (Po)));
            ((com.maddox.JGP.Tuple3d) (Po)).scale(-1D);
            ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            dist = (float)Ve.length();
            if(((com.maddox.JGP.Tuple3d) (followOffset)).x > 600D)
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                Ve.x -= 0.5D * ((com.maddox.JGP.Tuple3d) (followOffset)).x;
                tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (Po)));
                ((com.maddox.JGP.Tuple3d) (Po)).scale(-1D);
                ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            }
            Ve.normalize();
            if((double)dist > 600D + ((com.maddox.JGP.Tuple3d) (Ve)).x * 400D)
            {
                push();
                push(53);
                pop();
            } else
            {
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeTransport) && (double)((com.maddox.il2.fm.FlightModelMain)this).Vmax < 70D)
                    farTurnToDirection(6.2F);
                else
                    attackTurnToDirection(dist, f, 10F);
                setSpeedMode(10);
                tailForStaying = ((com.maddox.il2.fm.FlightModelMain)this).Leader;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                ((com.maddox.JGP.Tuple3d) (tailOffset)).scale(-1D);
            }
            break;

        case 65: // 'A'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.dropFuelTanks();
            }
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (airClient)).actor) || !((com.maddox.il2.fm.FlightModelMain)this).isOk())
            {
                set_maneuver(0);
            } else
            {
                com.maddox.il2.ai.air.Maneuver maneuver2 = (com.maddox.il2.ai.air.Maneuver)airClient;
                com.maddox.il2.ai.air.Maneuver maneuver4 = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.ai.air.Maneuver)airClient).danger;
                if(maneuver2.getDangerAggressiveness() >= 1.0F - (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 0.2F && maneuver4 != null && hasCourseWeaponBullets())
                {
                    com.maddox.il2.objects.sounds.Voice.speakCheckYour6((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (maneuver2)).actor, (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (maneuver4)).actor);
                    com.maddox.il2.objects.sounds.Voice.speakHelpFromAir((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor, 1);
                    set_task(6);
                    target = ((com.maddox.il2.fm.FlightModel) (maneuver4));
                    set_maneuver(27);
                }
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                dist = (float)Ve.length();
                Ve.normalize();
                attackTurnToDirection(dist, f, 10F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 8F);
                if(Alt > 50F)
                    setSpeedMode(1);
                else
                    setSpeedMode(6);
                tailForStaying = airClient;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                ((com.maddox.JGP.Tuple3d) (tailOffset)).scale(-1D);
                if((double)dist > 600D + ((com.maddox.JGP.Tuple3d) (Ve)).x * 400D && get_maneuver() != 27)
                {
                    push();
                    push(53);
                    pop();
                }
            }
            break;

        case 53: // '5'
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (airClient)).actor) || !((com.maddox.il2.fm.FlightModelMain)this).isOk())
            {
                airClient = null;
                set_maneuver(0);
            } else
            {
                maxAOA = 5F;
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                Ve.x -= 300D;
                tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain) (airClient)).Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (Po)));
                ((com.maddox.JGP.Tuple3d) (Po)).scale(-1D);
                ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                dist = (float)Ve.length();
                Ve.normalize();
                if(((com.maddox.il2.fm.FlightModelMain)this).Vmax < 83F)
                    farTurnToDirection(8.5F);
                else
                    farTurnToDirection(7F);
                float f10 = (((com.maddox.il2.fm.FlightModelMain)this).Energy - ((com.maddox.il2.fm.FlightModelMain) (airClient)).Energy) * 0.1019F;
                if((double)f10 < -50D + ((com.maddox.JGP.Tuple3d) (followOffset)).z)
                    setSpeedMode(9);
                else
                    setSpeedMode(10);
                tailForStaying = airClient;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                ((com.maddox.JGP.Tuple3d) (tailOffset)).scale(-1D);
                if((double)dist < 500D + ((com.maddox.JGP.Tuple3d) (Ve)).x * 200D)
                {
                    pop();
                } else
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 12F && Alt > 15F)
                        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 12F - ((com.maddox.il2.fm.FlightModelMain)this).AOA, 0.0F);
                    if(mn_time > 30F && (((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D || dist > 10000F))
                        pop();
                }
            }
            break;

        case 68: // 'D'
            if(airClient == null || !com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (airClient)).actor) || !((com.maddox.il2.fm.FlightModelMain)this).isOk())
            {
                set_maneuver(0);
            } else
            {
                com.maddox.il2.ai.air.Maneuver maneuver3 = (com.maddox.il2.ai.air.Maneuver)airClient;
                com.maddox.il2.ai.air.Maneuver maneuver5 = (com.maddox.il2.ai.air.Maneuver)((com.maddox.il2.ai.air.Maneuver)airClient).danger;
                if(maneuver3.getDangerAggressiveness() >= 1.0F - (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 0.3F && maneuver5 != null && hasCourseWeaponBullets())
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3d)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (maneuver3)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (maneuver5)).Vwld)));
                    if(tmpV3d.length() < 170D)
                    {
                        set_task(6);
                        target = ((com.maddox.il2.fm.FlightModel) (maneuver5));
                        set_maneuver(27);
                    }
                }
                maxAOA = 5F;
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                Ve.x -= 300D;
                tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain) (airClient)).Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (Po)));
                ((com.maddox.JGP.Tuple3d) (Po)).scale(-1D);
                ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                dist = (float)Ve.length();
                Ve.normalize();
                if(((com.maddox.il2.fm.FlightModelMain)this).Vmax < 83F)
                    farTurnToDirection(8.5F);
                else
                    farTurnToDirection(7F);
                setSpeedMode(10);
                tailForStaying = airClient;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                ((com.maddox.JGP.Tuple3d) (tailOffset)).scale(-1D);
                if((double)dist < 500D + ((com.maddox.JGP.Tuple3d) (Ve)).x * 200D)
                {
                    pop();
                } else
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 12F && Alt > 15F)
                        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 12F - ((com.maddox.il2.fm.FlightModelMain)this).AOA, 0.0F);
                    if(mn_time > 30F && (((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D || dist > 10000F))
                        pop();
                }
            }
            break;

        case 59: // ';'
            if(airClient == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (airClient)).actor) || !((com.maddox.il2.fm.FlightModelMain)this).isOk())
            {
                airClient = null;
                set_maneuver(0);
            } else
            {
                maxAOA = 5F;
                if(first)
                    ((com.maddox.JGP.Tuple3d) (followOffset)).set(1000F * (float)java.lang.Math.sin((float)beNearOffsPhase * 0.7854F), 1000F * (float)java.lang.Math.cos((float)beNearOffsPhase * 0.7854F), -400D);
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                Ve.x -= 300D;
                tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain) (airClient)).Vwld);
                tmpOr.increment(0.0F, 4F, 0.0F);
                tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (Po)));
                ((com.maddox.JGP.Tuple3d) (Po)).scale(-1D);
                ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                dist = (float)Ve.length();
                Ve.normalize();
                farTurnToDirection();
                setSpeedMode(2);
                tailForStaying = airClient;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(((com.maddox.JGP.Tuple3d) (followOffset)));
                ((com.maddox.JGP.Tuple3d) (tailOffset)).scale(-1D);
                if(dist < 300F)
                {
                    beNearOffsPhase++;
                    if(beNearOffsPhase > 3)
                        beNearOffsPhase = 0;
                    float f12 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (followOffset)).x * ((com.maddox.JGP.Tuple3d) (followOffset)).x + ((com.maddox.JGP.Tuple3d) (followOffset)).y * ((com.maddox.JGP.Tuple3d) (followOffset)).y);
                    ((com.maddox.JGP.Tuple3d) (followOffset)).set(f12 * (float)java.lang.Math.sin((float)beNearOffsPhase * 1.5708F), f12 * (float)java.lang.Math.cos((float)beNearOffsPhase * 1.5708F), ((com.maddox.JGP.Tuple3d) (followOffset)).z);
                }
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA > 12F && Alt > 15F)
                    ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 12F - ((com.maddox.il2.fm.FlightModelMain)this).AOA, 0.0F);
                if(mn_time > 15F && (((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D || dist > 3000F))
                    pop();
                if(mn_time > 30F)
                    pop();
            }
            break;

        case 63: // '?'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.dropFuelTanks();
            }
            if(target == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor) || ((com.maddox.il2.fm.FlightModelMain) (target)).isTakenMortalDamage() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(3);
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (target)).getSpeedKMH() < 45F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).y) < 50D && ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
            {
                target_ground = ((com.maddox.il2.engine.Interpolate) (target)).actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.HE_LERCHE3) && ((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).bToFire)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).bToFire = false;
                }
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TA_183) && ((com.maddox.il2.objects.air.TA_183)((com.maddox.il2.engine.Interpolate)this).actor).bToFire)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.TA_183)((com.maddox.il2.engine.Interpolate)this).actor).bToFire = false;
                }
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TA_152C) && ((com.maddox.il2.objects.air.TA_152C)((com.maddox.il2.engine.Interpolate)this).actor).bToFire)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.TA_152C)((com.maddox.il2.engine.Interpolate)this).actor).bToFire = false;
                }
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.Mig_17PF) && ((com.maddox.il2.objects.air.Mig_17PF)((com.maddox.il2.engine.Interpolate)this).actor).bToFire)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    ((com.maddox.il2.objects.air.Mig_17PF)((com.maddox.il2.engine.Interpolate)this).actor).bToFire = false;
                }
                if(((com.maddox.JGP.Tuple3d) (TargV)).z == -400D)
                    fighterUnderBomber(f);
                else
                    fighterVsBomber(f);
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 2.0F && Alt > 15F)
                    ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.01F * (((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 2.0F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
            }
            break;

        case 27: // '\033'
            if(!(this instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)this).isRealMode())
            {
                bombsOut = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.dropFuelTanks();
            }
            if(target == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor) || ((com.maddox.il2.fm.FlightModelMain) (target)).isTakenMortalDamage() || ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() == ((com.maddox.il2.engine.Interpolate)this).actor.getArmy() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(0);
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (target)).getSpeedKMH() < 45F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).y) < 50D && ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
            {
                target_ground = ((com.maddox.il2.engine.Interpolate) (target)).actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if(first && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeAcePlane))
                {
                    if(target != null && ((com.maddox.il2.engine.Interpolate) (target)).actor != null && ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                        target.Skill = 0;
                    if(danger != null && ((com.maddox.il2.engine.Interpolate) (danger)).actor != null && ((com.maddox.il2.engine.Interpolate) (danger)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                        danger.Skill = 0;
                }
                if(((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                    ((com.maddox.il2.ai.air.Maneuver)target).danger = ((com.maddox.il2.fm.FlightModel) (this));
                if(((com.maddox.il2.fm.FMMath)this).isTick(64, 0))
                {
                    float f11 = (((com.maddox.il2.fm.FlightModelMain) (target)).Energy - ((com.maddox.il2.fm.FlightModelMain)this).Energy) * 0.1019F;
                    ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                    Ve.normalize();
                    float f13 = (470F + (float)((com.maddox.JGP.Tuple3d) (Ve)).x * 120F) - f11;
                    if(f13 < 0.0F)
                    {
                        clear_stack();
                        set_maneuver(62);
                    }
                }
                fighterVsFighter(f);
                setSpeedMode(9);
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 1.0F && Alt > 15F)
                    ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.01F * (((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit - 1.0F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
                if(mn_time > 100F)
                {
                    target = null;
                    pop();
                }
            }
            break;

        case 62: // '>'
            if(target == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor) || ((com.maddox.il2.fm.FlightModelMain) (target)).isTakenMortalDamage() || ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() == ((com.maddox.il2.engine.Interpolate)this).actor.getArmy() || !hasCourseWeaponBullets())
            {
                target = null;
                clear_stack();
                set_maneuver(0);
            } else
            if(((com.maddox.il2.fm.FlightModelMain) (target)).getSpeedKMH() < 45F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).y) < 50D && ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
            {
                target_ground = ((com.maddox.il2.engine.Interpolate) (target)).actor;
                set_task(7);
                clear_stack();
                set_maneuver(43);
            } else
            {
                if(first && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeAcePlane))
                {
                    if(target != null && ((com.maddox.il2.engine.Interpolate) (target)).actor != null && ((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                        target.Skill = 0;
                    if(danger != null && ((com.maddox.il2.engine.Interpolate) (danger)).actor != null && ((com.maddox.il2.engine.Interpolate) (danger)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                        danger.Skill = 0;
                }
                if(((com.maddox.il2.engine.Interpolate) (target)).actor.getArmy() != ((com.maddox.il2.engine.Interpolate)this).actor.getArmy())
                    ((com.maddox.il2.ai.air.Maneuver)target).danger = ((com.maddox.il2.fm.FlightModel) (this));
                goodFighterVsFighter(f);
            }
            break;

        case 70: // 'F'
            checkGround = false;
            checkStrike = false;
            frequentControl = true;
            stallable = false;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
                switch(submaneuver)
                {
                case 0: // '\0'
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                    submaneuver++;
                    sub_Man_Count = 0;
                    // fall through

                case 1: // '\001'
                    if(sub_Man_Count == 0)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = com.maddox.il2.ai.World.cur().rnd.nextFloat(-0.15F, 0.15F);
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 1.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(1.0F);
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage(), 0.0F, 60F, 1.0F, 0.0F);
                    if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 30F)
                    {
                        submaneuver++;
                        sub_Man_Count = 0;
                    }
                    sub_Man_Count++;
                    break;

                case 2: // '\002'
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.1F);
                    ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, (float)((double)f * 0.10000000000000001D * (double)sub_Man_Count * (90D - (double)((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage())), 0.0F);
                    if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 89F)
                    {
                        saveOr.set(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)));
                        submaneuver++;
                    }
                    sub_Man_Count++;
                    break;

                case 3: // '\003'
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
                    if(Alt > 10F)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.33F);
                    else
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                    if(Alt < 20F)
                    {
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < -4D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.94999999999999996D;
                        if(((com.maddox.il2.fm.FlightModelMain)this).Vwld.lengthSquared() < 1.0D)
                            ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineStops();
                    }
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.set(saveOr);
                    if(mn_time > 100F)
                    {
                        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).set(0.0D, 0.0D, 0.0D);
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 12000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                    }
                    break;
                }
            break;

        case 25: // '\031'
            wingman(false);
            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLanding())
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.landingAirport.rebuildLandWay(((com.maddox.il2.fm.FlightModel) (this)));
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl == 1.0F && ((com.maddox.il2.fm.FlightModelMain)this).CT.arrestorControl < 1.0F && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
                        ((com.maddox.il2.fm.FlightModelMain)this).AS.setArrestor(((com.maddox.il2.engine.Interpolate)this).actor, 1);
                }
                if(first)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.setWayPoint(true);
                    doDumpBombsPassively();
                    submaneuver = 0;
                }
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.HE_LERCHE3) && Alt < 50F)
                    maneuver = 70;
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getP(Po);
                int k = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur();
                float f17 = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - ((com.maddox.il2.fm.FlightModelMain)this).AP.way.last().z();
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(k);
                Alt = java.lang.Math.min(Alt, f17);
                ((com.maddox.JGP.Tuple3d) (Po)).add(0.0D, 0.0D, -3D);
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                float f20 = (float)Ve.length();
                boolean flag3 = Alt > 4.5F + ((com.maddox.il2.fm.FlightModelMain)this).Gears.H && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() < 8;
                if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                    flag3 = Alt > 4.5F + ((com.maddox.il2.fm.FlightModelMain)this).Gears.H && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() < 8;
                if(flag3)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.prev();
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getP(Pc);
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (Pc)));
                    tmpV3f.normalize();
                    if(tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Ve))) < 0.0D && f20 > 1000F && !TaxiMode)
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).AP.way.first();
                        push(10);
                        pop();
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 0.0F;
                    }
                    float f25 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Ve)));
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(-f25);
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).add(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (tmpV3f)));
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
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
                        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f31);
                    }
                    float f33 = ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS;
                    if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() >= 6)
                    {
                        if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                        {
                            if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.landingAirport))) && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.landingAirport instanceof com.maddox.il2.ai.AirportCarrier))
                            {
                                float f34 = (float)((com.maddox.il2.ai.AirportCarrier)((com.maddox.il2.fm.FlightModelMain)this).AP.way.landingAirport).speedLen();
                                if(((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS < f34 + 10F)
                                    f33 = f34 + 10F;
                            }
                        } else
                        {
                            f33 = ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 1.2F;
                        }
                        if(f33 < 14F)
                            f33 = 14F;
                    } else
                    {
                        f33 = ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 2.0F;
                    }
                    double d4 = ((com.maddox.il2.fm.FlightModelMain)this).Vwld.length();
                    double d6 = (double)f33 - d4;
                    float f35 = 2.0F * f;
                    if(d6 > (double)f35)
                        d6 = f35;
                    if(d6 < (double)(-f35))
                        d6 = -f35;
                    Ve.normalize();
                    ((com.maddox.JGP.Tuple3d) (Ve)).scale(d4);
                    ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                    ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                    float f36 = (50F * f29 - f31) * f;
                    if(Ve.length() > (double)f36)
                    {
                        Ve.normalize();
                        ((com.maddox.JGP.Tuple3d) (Ve)).scale(f36);
                    }
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).add(((com.maddox.JGP.Tuple3d) (Ve)));
                    ((com.maddox.il2.fm.FlightModelMain)this).Vwld.normalize();
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).scale(d4 + d6);
                    ((com.maddox.il2.fm.FlightModelMain)this).Loc.x += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x * (double)f;
                    ((com.maddox.il2.fm.FlightModelMain)this).Loc.y += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y * (double)f;
                    ((com.maddox.il2.fm.FlightModelMain)this).Loc.z += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z * (double)f;
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                    tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain)this).Vwld);
                    float f37 = 0.0F;
                    if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                        f37 = 0.9F * (45F - Alt);
                    else
                        f37 = 0.7F * (20F - Alt);
                    if(f37 < 0.0F)
                        f37 = 0.0F;
                    tmpOr.increment(0.0F, 4F + f37, (float)(((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 0.5D));
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.interpolate(tmpOr, 0.5F * f);
                    super.callSuperUpdate = false;
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).set(0.0D, 0.0D, 0.0D);
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F + 0.3F * f37;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = (float)(-((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 0.02D);
                    direction = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getAzimut();
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabDirection(true);
                }
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabDirection(true);
            }
            dA = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl;
            ((com.maddox.il2.fm.FlightModelMain)this).AP.update(f);
            setSpeedControl(f);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if(maneuver != 25)
                return;
            if(Alt > 60F)
            {
                if(Alt < 160F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.32F;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
                setSpeedMode(7);
                smConstPower = 0.2F;
                dA = java.lang.Math.min(130F + Alt, 270F);
                if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D || ((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() < dA)
                    dA = -1.2F * f;
                else
                    dA = 1.2F * f;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl * 0.9F + dA * 0.1F;
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 15F;
                if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() >= 6 || ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() == 0)
                {
                    if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -5F)
                        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, -((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 5F, 0.0F);
                    if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch + 10F)
                        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, -(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - (((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch + 10F)), 0.0F);
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).Vrel.length() < 1.0D)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 0.0F;
                    if(!TaxiMode)
                    {
                        setSpeedMode(8);
                        if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                        {
                            if(((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap() < 0.001F)
                                ((com.maddox.il2.fm.FlightModelMain)this).AS.setWingFold(((com.maddox.il2.engine.Interpolate)this).actor, 1);
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
                            if(((com.maddox.il2.fm.FlightModelMain)this).CT.arrestorControl == 1.0F && ((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
                                ((com.maddox.il2.fm.FlightModelMain)this).AS.setArrestor(((com.maddox.il2.engine.Interpolate)this).actor, 0);
                            com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 25000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineStops();
                            if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getPropw() < 1.0F)
                            {
                                com.maddox.il2.ai.World.cur();
                                if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                                    com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 12000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                            }
                        }
                    }
                }
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).VmaxFLAPS * 0.21F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).Vrel.length() < (double)(((com.maddox.il2.fm.FlightModelMain)this).VmaxFLAPS * 0.25F) && wayCurPos == null && !((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                {
                    TaxiMode = true;
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(0);
                    return;
                }
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 0.6F && Alt < 10F)
                {
                    setSpeedMode(8);
                    if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip() && ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasArrestorControl)
                    {
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < -5.5D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.94999998807907104D;
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.9100000262260437D;
                    } else
                    {
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < -0.60000002384185791D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.93999999761581421D;
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < -2.5D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z = -2.5D;
                        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.9100000262260437D;
                    }
                }
                float f14 = ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch - 2.0F;
                if(f14 < 5F)
                    f14 = 5F;
                if(Alt < 7F && ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < f14 || ((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.5F * f;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += ((float) (0.05000000074505806D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y));
                if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch > 5F)
                    {
                        if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch)
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.5F * f;
                        if(!((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += ((float) (0.30000001192092896D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y));
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
                    }
                    if(!TaxiMode)
                    {
                        AFo.setDeg(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getAzimut(), direction);
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 8F * AFo.getDiffRad() + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).z;
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
                    }
                }
            }
            ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getP(Po);
            return;

        case 66: // 'B'
            if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfTaxiing() || ((com.maddox.il2.fm.FlightModelMain)this).EI.getThrustOutput() < 0.01F)
            {
                set_task(3);
                maneuver = 0;
                set_maneuver(49);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
            } else
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).AS.isPilotDead(0))
                {
                    set_task(3);
                    maneuver = 0;
                    set_maneuver(44);
                    setSpeedMode(8);
                    smConstPower = 0.0F;
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(((com.maddox.il2.fm.FlightModelMain)this).Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).postEndAction(6000D, ((com.maddox.il2.engine.Interpolate)this).actor, 3, ((com.maddox.il2.engine.Eff3DActor) (null)));
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 0x493e0L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                    return;
                }
                P.x = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x;
                P.y = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y;
                P.z = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z;
                ((com.maddox.JGP.Tuple2d) (Vcur)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                if(wayCurPos == null)
                {
                    com.maddox.il2.ai.World.cur().airdrome.findTheWay((com.maddox.il2.ai.air.Pilot)this);
                    wayPrevPos = wayCurPos = getNextAirdromeWayPoint();
                }
                if(wayCurPos != null)
                {
                    com.maddox.il2.ai.air.Point_Any point_any = wayCurPos;
                    com.maddox.il2.ai.air.Point_Any point_any1 = wayPrevPos;
                    ((com.maddox.JGP.Tuple2f) (Pcur)).set((float)((com.maddox.JGP.Tuple3d) (P)).x, (float)((com.maddox.JGP.Tuple3d) (P)).y);
                    float f21 = Pcur.distance(((com.maddox.JGP.Point2f) (point_any)));
                    float f23 = Pcur.distance(((com.maddox.JGP.Point2f) (point_any1)));
                    ((com.maddox.JGP.Tuple2f) (V_to)).sub(((com.maddox.JGP.Tuple2f) (point_any)), ((com.maddox.JGP.Tuple2f) (Pcur)));
                    V_to.normalize();
                    float f26 = 5F + 0.1F * f21;
                    if(f26 > 12F)
                        f26 = 12F;
                    if(f26 > 0.9F * ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS)
                        f26 = 0.9F * ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS;
                    if(curAirdromePoi < airdromeWay.length && f21 < 15F || f21 < 4F)
                    {
                        f26 = 0.0F;
                        com.maddox.il2.ai.air.Point_Any point_any2 = getNextAirdromeWayPoint();
                        if(point_any2 == null)
                        {
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).set(((com.maddox.JGP.Tuple3d) (P)));
                            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                            if(finished)
                                return;
                            finished = true;
                            int l = 1000;
                            if(wayCurPos != null)
                                l = 0x249f00;
                            ((com.maddox.il2.engine.Interpolate)this).actor.collide(true);
                            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).set(0.0D, 0.0D, 0.0D);
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                            ((com.maddox.il2.fm.FlightModelMain)this).EI.setCurControlAll(true);
                            ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineStops();
                            com.maddox.il2.ai.World.cur();
                            if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                            {
                                com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + (long)l, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                                ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(true);
                                maneuver = 0;
                                set_maneuver(44);
                            }
                            return;
                        }
                        wayPrevPos = wayCurPos;
                        wayCurPos = point_any2;
                    }
                    ((com.maddox.JGP.Tuple2f) (V_to)).scale(f26);
                    float f30 = 2.0F * f;
                    ((com.maddox.JGP.Tuple2d) (Vdiff)).set(((com.maddox.JGP.Tuple2f) (V_to)));
                    ((com.maddox.JGP.Tuple2d) (Vdiff)).sub(((com.maddox.JGP.Tuple2d) (Vcur)));
                    float f32 = (float)Vdiff.length();
                    if(f32 > f30)
                    {
                        Vdiff.normalize();
                        ((com.maddox.JGP.Tuple2d) (Vdiff)).scale(f30);
                    }
                    ((com.maddox.JGP.Tuple2d) (Vcur)).add(((com.maddox.JGP.Tuple2d) (Vdiff)));
                    tmpOr.setYPR(com.maddox.il2.fm.FMMath.RAD2DEG((float)Vcur.direction()), ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getPitch(), 0.0F);
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.interpolate(tmpOr, 0.2F);
                    ((com.maddox.il2.fm.FlightModelMain)this).Vwld.x = ((com.maddox.JGP.Tuple2d) (Vcur)).x;
                    ((com.maddox.il2.fm.FlightModelMain)this).Vwld.y = ((com.maddox.JGP.Tuple2d) (Vcur)).y;
                    P.x += ((com.maddox.JGP.Tuple2d) (Vcur)).x * (double)f;
                    P.y += ((com.maddox.JGP.Tuple2d) (Vcur)).y * (double)f;
                } else
                {
                    wayPrevPos = wayCurPos = ((com.maddox.il2.ai.air.Point_Any) (new Point_Null((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y)));
                    com.maddox.il2.ai.World.cur();
                    if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    {
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
                        ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(true);
                    }
                }
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).set(((com.maddox.JGP.Tuple3d) (P)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                return;
            }
            break;

        case 49: // '1'
            emergencyLanding(f);
            break;

        case 64: // '@'
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                pop();
                break;
            }
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
            {
                boolean flag1 = com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier);
                if(!flag1)
                    super.callSuperUpdate = false;
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.5F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
            ((com.maddox.il2.fm.FlightModelMain)this).EI.setCurControlAll(false);
            setSpeedMode(0);
            if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).EI.setCurControl(submaneuver, true);
                if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[submaneuver].getStage() == 0)
                {
                    setSpeedMode(0);
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.05F);
                    ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[submaneuver].setControlThrottle(0.2F);
                    ((com.maddox.il2.fm.FlightModelMain)this).EI.toggle();
                    if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.BI_1) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.BI_6))
                    {
                        pop();
                        break;
                    }
                }
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[submaneuver].getStage() == 6)
            {
                setSpeedMode(0);
                ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[submaneuver].setControlThrottle(0.0F);
                submaneuver++;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                if(submaneuver > ((com.maddox.il2.fm.FlightModelMain)this).EI.getNum() - 1)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).EI.setCurControlAll(true);
                    pop();
                }
            }
            break;

        case 26: // '\032'
            float f15 = Alt;
            float f18 = 0.4F;
            if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier))
            {
                f15 -= ((com.maddox.il2.ai.AirportCarrier)((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport).height();
                f18 = 0.95F;
                if(Alt < 9F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
                    ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.84999999999999998D;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasCockpitDoorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setCockpitDoor(((com.maddox.il2.engine.Interpolate)this).actor, 1);
            }
            if(first)
            {
                setCheckGround(false);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasArrestorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setArrestor(((com.maddox.il2.engine.Interpolate)this).actor, 0);
                setSpeedMode(8);
                if(f15 > 7.21F && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() == 0)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineRunning();
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Interpolate)this).actor, "in the air - engines running!.");
                }
                if(!com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))))
                    direction = ((com.maddox.il2.engine.Interpolate)this).actor.pos.getAbsOrient().getAzimut();
                if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.HE_LERCHE3)
                {
                    maneuver = 69;
                    break;
                }
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 0)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                    setSpeedMode(8);
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F && mn_time > 1.0F || mn_time > 8F)
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
                    ((com.maddox.JGP.Tuple3d) (Po)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                    ((com.maddox.JGP.Tuple3d) (Vpl)).set(60D, 0.0D, 0.0D);
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (Vpl)));
                    ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (Vpl)));
                    ((com.maddox.JGP.Tuple3d) (Pd)).set(((com.maddox.JGP.Tuple3d) (Po)));
                    if(canTakeoff)
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(1.1F);
                        setSpeedMode(9);
                    } else
                    {
                        setSpeedMode(8);
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
                        boolean flag2 = true;
                        if(mn_time < 8F)
                            flag2 = false;
                        if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor, 70F))
                            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.G4M2E)
                            {
                                if(com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor, 70F) != ((com.maddox.il2.objects.air.G4M2E)((com.maddox.il2.engine.Interpolate)this).actor).typeDockableGetDrone())
                                    flag2 = false;
                            } else
                            {
                                flag2 = false;
                            }
                        if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && ((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport.takeoffRequest > 0)
                            flag2 = false;
                        if(flag2)
                        {
                            canTakeoff = true;
                            if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))))
                                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport.takeoffRequest = 300;
                        }
                    }
                }
                if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 6 && ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasWingControl && ((com.maddox.il2.fm.FlightModelMain)this).CT.getWing() > 0.001F)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setWingFold(((com.maddox.il2.engine.Interpolate)this).actor, 0);
            } else
            if(canTakeoff)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(1.1F);
                setSpeedMode(9);
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl == 0.0F && ((com.maddox.il2.fm.FlightModelMain)this).CT.getWing() < 0.001F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.4F;
            if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 6 && ((com.maddox.il2.fm.FlightModelMain)this).CT.getPower() > f18)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 0.0F;
                ((com.maddox.il2.fm.FlightModel)this).brakeShoe = false;
                float f22 = (((com.maddox.il2.fm.FlightModelMain)this).Vmin * ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass()) / ((com.maddox.il2.fm.FlightModelMain)this).M.massEmpty;
                float f24 = 12F * (((com.maddox.il2.fm.FlightModel)this).getSpeed() / f22 - 0.2F);
                if(((com.maddox.il2.fm.FlightModelMain)this).Gears.bIsSail)
                    f24 *= 2.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).Gears.bFrontWheel)
                    f24 = ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch + 4F;
                if(f24 < 1.0F)
                    f24 = 1.0F;
                if(f24 > 10F)
                    f24 = 10F;
                float f27 = 1.5F;
                if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier) && !((com.maddox.il2.fm.FlightModelMain)this).Gears.isUnderDeck())
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 0.0F;
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
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < f24)
                    dA = -0.7F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - f24) + f27 * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAW())).y;
                else
                    dA = -0.1F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - f24) + f27 * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAW())).y;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += (dA - ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl) * 3F * f;
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 1.0F;
            }
            AFo.setDeg(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getAzimut(), direction);
            double d1 = AFo.getDiffRad();
            if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 6)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 8F * (float)d1;
                if(d1 > -1D && d1 < 1.0D)
                {
                    if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && ((com.maddox.il2.fm.FlightModelMain)this).CT.getPower() > 0.3F)
                    {
                        double d2 = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport.ShiftFromLine(((com.maddox.il2.fm.FlightModel) (this)));
                        double d3 = 3D - 3D * java.lang.Math.abs(d1);
                        if(d3 > 1.0D)
                            d3 = 1.0D;
                        double d5 = 0.25D * d2 * d3;
                        if(d5 > 1.5D)
                            d5 = 1.5D;
                        if(d5 < -1.5D)
                            d5 = -1.5D;
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl += (float)d5;
                    }
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
                }
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.05F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + 0.3F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
            if(f15 > 5F && !((com.maddox.il2.fm.FlightModelMain)this).Gears.isUnderDeck())
                ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 0.0F;
            float f28 = 1.0F;
            if(hasBombs())
                f28 *= 1.7F;
            if(f15 > 120F * f28 || ((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.8F * f28 || f15 > 80F * f28 && ((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.6F * f28 || f15 > 40F * f28 && ((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.3F * f28 && mn_time > 60F + (float)((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).aircIndex() * 3F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 0.0F;
                rwLoc = null;
                if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider)
                    push(24);
                maneuver = 0;
                ((com.maddox.il2.fm.FlightModel)this).brakeShoe = false;
                ((com.maddox.il2.fm.FlightModel)this).turnOffCollisions = false;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasCockpitDoorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setCockpitDoor(((com.maddox.il2.engine.Interpolate)this).actor, 0);
                pop();
            }
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.05F;
                canTakeoff = true;
            }
            break;

        case 69: // 'E'
            float f16 = Alt;
            float f19 = 0.4F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 1.0F;
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).scale(0.0D);
            boolean flag4 = com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))) && (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport instanceof com.maddox.il2.ai.AirportCarrier);
            if(flag4)
            {
                f16 -= ((com.maddox.il2.ai.AirportCarrier)((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport).height();
                f19 = 0.8F;
                if(Alt < 9F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
                    ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.84999999999999998D;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasCockpitDoorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setCockpitDoor(((com.maddox.il2.engine.Interpolate)this).actor, 1);
            }
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z != 0.0D && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).suka.getPoint())).z == 0.0D)
                ((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).suka.set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)), ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)));
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z != 0.0D)
                if(((com.maddox.il2.fm.FlightModelMain)this).EI.getPowerOutput() < f19 && !flag4)
                {
                    super.callSuperUpdate = false;
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).suka.getPoint())));
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.set(((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).suka.getOrient());
                } else
                if(f16 < 100F)
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.set(((com.maddox.il2.objects.air.HE_LERCHE3)((com.maddox.il2.engine.Interpolate)this).actor).suka.getOrient());
            if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 0)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                setSpeedMode(8);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.05F && mn_time > 1.0F || mn_time > 8F)
                {
                    push();
                    push(64);
                    submaneuver = 0;
                    maneuver = 0;
                    safe_pop();
                    break;
                }
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getStage() == 6)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 1.0F;
                ((com.maddox.il2.fm.FlightModel)this).brakeShoe = false;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(1.1F);
                setSpeedMode(9);
            }
            if(f16 > 200F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 0.0F;
                rwLoc = null;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -1F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < 25F)
                    maneuver = 0;
                ((com.maddox.il2.fm.FlightModel)this).brakeShoe = false;
                ((com.maddox.il2.fm.FlightModel)this).turnOffCollisions = false;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasCockpitDoorControl)
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setCockpitDoor(((com.maddox.il2.engine.Interpolate)this).actor, 0);
                pop();
            }
            break;

        case 28: // '\034'
            if(first)
            {
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-25F, 25F);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                setSpeedMode(6);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.22F, 0.22F);
                submaneuver = 0;
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.5F)
                    pop();
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - direction);
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = 1.0F;
                maxAOA = 12F + 1.0F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.6F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 3.3F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 40F + 5.1F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-25F, 25F);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.75F, 0.75F);
                submaneuver++;
                // fall through

            case 2: // '\002'
                dA = -1F;
                maxAOA = 12F + 5F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
                if(((com.maddox.il2.fm.FlightModelMain)this).AOA < -maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.6F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 3.3F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -45F)
                    pop();
                break;
            }
            if(mn_time > 3F)
                pop();
            break;

        case 29: // '\035'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 17F;
            if(first)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                setSpeedMode(9);
                sub_Man_Count = 0;
            }
            if(danger != null)
            {
                if(sub_Man_Count == 0)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3d)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (danger)).getW())));
                    ((com.maddox.il2.fm.FlightModelMain) (danger)).Or.transform(((com.maddox.JGP.Tuple3d) (tmpV3d)));
                    if(((com.maddox.JGP.Tuple3d) (tmpV3d)).z > 0.0D)
                        sinKren = -com.maddox.il2.ai.World.Rnd().nextFloat(60F, 90F);
                    else
                        sinKren = com.maddox.il2.ai.World.Rnd().nextFloat(60F, 90F);
                }
                if(((com.maddox.il2.fm.FlightModelMain)this).Loc.distanceSquared(((com.maddox.il2.fm.FlightModelMain) (danger)).Loc) < 5000D)
                {
                    setSpeedMode(8);
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(0.0F);
                } else
                {
                    setSpeedMode(9);
                }
            } else
            {
                pop();
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.2F;
            if((double)((com.maddox.il2.fm.FlightModel)this).getSpeed() < 120D)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.33F;
            if((double)((com.maddox.il2.fm.FlightModel)this).getSpeed() < 80D)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.08F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + sinKren);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.9F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
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
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.08F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - direction);
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
                if((double)((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()))) || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -1F * (float)java.lang.Math.sin(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + 55F));
                if(mn_time > 1.5F)
                    submaneuver++;
                break;

            case 1: // '\001'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(10F, 20F);
                submaneuver++;
                // fall through

            case 2: // '\002'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
                if((double)((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()))) || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 1.0F * (float)java.lang.Math.sin(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 55F));
                if(mn_time > 4.5F)
                    submaneuver++;
                break;

            case 3: // '\003'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-20F, -10F);
                submaneuver++;
                // fall through

            case 4: // '\004'
                dA = 1.0F;
                maxAOA = 10F + 2.0F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
                if((double)((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 1.0D / java.lang.Math.abs(java.lang.Math.cos(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()))) || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -1F * (float)java.lang.Math.sin(java.lang.Math.toRadians(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + 55F));
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
            if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM() && com.maddox.il2.ai.World.Rnd().nextInt(-2, 9) < ((com.maddox.il2.fm.FlightModelMain)this).Skill)
                ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).hitDaSilk();
            if(first)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                setSpeedMode(6);
                submaneuver = 0;
                direction = 178F - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 8F) * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 14F : 24F;
            if(danger != null)
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (danger)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                dist = (float)Ve.length();
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (danger)).Vwld)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vpl)));
                Vpl.normalize();
            }
            switch(submaneuver)
            {
            case 0: // '\0'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA *= -0.08F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = dA > 0.0F ? 1.0F : -1F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.01111111F * java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren());
                if(mn_time > 2.0F || java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) > direction)
                {
                    submaneuver++;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F);
                    direction = com.maddox.il2.ai.World.Rnd().nextFloat(-60F, -30F);
                    mn_time = 0.5F;
                }
                break;

            case 1: // '\001'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - 180F;
                if(dA < -180F)
                    dA += 360F;
                dA *= -0.04F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = dA;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > direction + 5F && ((com.maddox.il2.fm.FlightModelMain)this).getOverload() < maxG && ((com.maddox.il2.fm.FlightModelMain)this).AOA < maxAOA)
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.0F * f;
                } else
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 1.0F * f;
                }
                if(mn_time > 2.0F && ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < direction + 20F)
                    submaneuver++;
                if(danger != null)
                {
                    if(((com.maddox.il2.fm.FlightModelMain)this).Skill >= 2 && ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -30F && ((com.maddox.JGP.Tuple3d) (Vpl)).x > 0.99862951040267944D)
                        submaneuver++;
                    if(((com.maddox.il2.fm.FlightModelMain)this).Skill >= 3 && java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage()) > 145F && java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain) (danger)).Or)).getTangage()) > 135F && dist < 400F)
                        submaneuver++;
                }
                break;

            case 2: // '\002'
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-60F, 60F);
                setSpeedMode(6);
                submaneuver++;
                // fall through

            case 3: // '\003'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - direction;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * dA;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = dA > 0.0F ? 1.0F : -1F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.5F;
                if(java.lang.Math.abs(dA) < 4F + 3F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill)
                    submaneuver++;
                break;

            case 4: // '\004'
                direction *= com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.0F);
                setSpeedMode(6);
                submaneuver++;
                // fall through

            case 5: // '\005'
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - direction;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * dA;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA || ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 40F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.8F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.6F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > 80F || mn_time > 4F || ((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.5F)
                    pop();
                if(danger != null && (((com.maddox.JGP.Tuple3d) (Ve)).z < -1D || dist > 600F || ((com.maddox.JGP.Tuple3d) (Vpl)).x < 0.78801000118255615D))
                    pop();
                break;
            }
            if((double)Alt < -7D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z)
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
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                submaneuver = 0;
                direction = (((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextBoolean() ? 1.0F : -1F) * com.maddox.il2.ai.World.Rnd().nextFloat(107F, 160F);
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 14F : 24F;
            switch(submaneuver)
            {
            case 0: // '\0'
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 45F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.005555556F * java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren());
                else
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > 1.0F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.2F * f;
                dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - direction;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * dA;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(dA) < 4F + 1.0F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill)
                    submaneuver++;
                break;

            case 1: // '\001'
                setSpeedMode(7);
                smConstPower = 0.5F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 1.2F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -60F)
                    submaneuver++;
                break;

            case 2: // '\002'
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -45F)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                    setSpeedMode(9);
                    maxAOA = 7F;
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.8F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.4F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > ((com.maddox.il2.fm.FlightModelMain)this).AOA - 10F || ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > -1D)
                    pop();
                break;
            }
            if((double)Alt < -7D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z)
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
                direction = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage();
                setSpeedMode(9);
            }
            dA = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() - (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 35F : -35F);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * dA;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 1.0F : -1F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -1F;
            if(direction > ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 45F || ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -60F || mn_time > 4F)
                pop();
            break;

        case 36: // '$'
        case 37: // '%'
            if(first)
            {
                if(!((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
                    pop();
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmax * 0.5F)
                {
                    pop();
                    break;
                }
                ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
                submaneuver = 0;
                direction = com.maddox.il2.ai.World.Rnd().nextFloat(-30F, 80F);
                setSpeedMode(9);
            }
            maxAOA = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 14F : 24F;
            switch(submaneuver)
            {
            case 0: // '\0'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.1F * ((com.maddox.il2.fm.FlightModelMain)this).getAOS();
                if(java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 45F)
                    submaneuver++;
                break;

            case 1: // '\001'
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.0F;
                dA = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > dA)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.4F * f;
                else
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.8F * f;
                if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > direction)
                    submaneuver++;
                if(((com.maddox.il2.fm.FlightModel)this).getSpeed() < ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.25F)
                    pop();
                break;

            case 2: // '\002'
                push(maneuver == 36 ? 7 : 35);
                pop();
                break;
            }
            break;

        case 38: // '&'
            if(first)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 1.0F : -1F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl += -0.02F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl > 0.1F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 0.1F;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl < -0.1F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.1F;
            dA = (((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH() - 180F - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() * 10F - ((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() * 5F) * 0.004F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = dA;
            if(mn_time > 3.5F)
                pop();
            break;

        case 39: // '\''
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 10F);
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl > 0.1F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.8F;
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl < -0.1F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -0.8F;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F ? 1.0F : -1F;
            if(((com.maddox.il2.fm.FlightModel)this).getSpeed() > ((com.maddox.il2.fm.FlightModelMain)this).Vmax || mn_time > 7F)
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
                    Group.setTaskAndManeuver(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor));
                    setBusy(flag5);
                    Group.grTask = j1;
                }
                if(target_ground == null || target_ground.pos == null)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.first();
                    com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getP(), ((com.maddox.il2.engine.Interpolate)this).actor.getArmy(), 7);
                    com.maddox.il2.ai.WayPoint waypoint;
                    if(airport != null)
                        waypoint = new WayPoint(((com.maddox.il2.engine.Actor) (airport)).pos.getAbsPoint());
                    else
                        waypoint = new WayPoint(((com.maddox.il2.fm.FlightModelMain)this).AP.way.first().getP());
                    waypoint.set(0.6F * ((com.maddox.il2.fm.FlightModelMain)this).Vmax);
                    waypoint.Action = 2;
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.add(waypoint);
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.last();
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
        case 79: // 'O'
        case 80: // 'P'
        case 81: // 'Q'
            if(first && !((com.maddox.il2.fm.FlightModelMain)this).isCapableOfACM())
            {
                bombsOut = true;
                ((com.maddox.il2.fm.FlightModelMain)this).setReadyToReturn(true);
                if(Group != null)
                {
                    Group.waitGroup(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor));
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
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
                        Group.setTaskAndManeuver(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor));
                        setBusy(flag6);
                    }
                }
                if(target_ground == null || target_ground.pos == null || !com.maddox.il2.engine.Actor.isAlive(target_ground))
                {
                    if(i1 == 50)
                        bombsOut = true;
                    if(Group != null)
                    {
                        Group.waitGroup(Group.numInGroup((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor));
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
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
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
                    pop();
                    sub_Man_Count = 0;
                }
                break;

            case 51: // '3'
                groundAttackTorpedo(target_ground, f);
                break;

            case 81: // 'Q'
                groundAttackTorpedoToKG(target_ground, f);
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

            case 79: // 'O'
                groundAttackHS293(target_ground, f);
                break;

            case 80: // 'P'
                groundAttackFritzX(target_ground, f);
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
            ((com.maddox.il2.fm.FlightModelMain)this).AP.update(f);
        else
            ((com.maddox.il2.fm.FlightModelMain)this).AP.update(f * 4F);
        if(bBusy)
            wasBusy = true;
        else
            wasBusy = false;
        if(shotAtFriend > 0)
            shotAtFriend--;
    }

    void OutCT(int i)
    {
        if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.game.Main3D.cur3D().viewActor())
            return;
        com.maddox.il2.engine.TextScr.output(i + 5, 45, "Alt(MSL)  " + (int)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z + "    " + (((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl > 0.0F ? "BRAKE" : ""));
        com.maddox.il2.engine.TextScr.output(i + 5, 65, "Alt(AGL)  " + (int)(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y)));
        int j = 0;
        com.maddox.il2.engine.TextScr.output(i + 225, 140, "---ENGINES (" + ((com.maddox.il2.fm.FlightModelMain)this).EI.getNum() + ")---" + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getStage());
        com.maddox.il2.engine.TextScr.output(i + 245, 120, "THTL " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlThrottle()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 100, "PROP " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlProp()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).CT.getStepControlAuto() ? " (AUTO)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 80, "MIX " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlMix()) + "%");
        com.maddox.il2.engine.TextScr.output(i + 245, 60, "RAD " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlRadiator()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
        com.maddox.il2.engine.TextScr.output(i + 245, 40, "SUPC " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlCompressor() + "x");
        com.maddox.il2.engine.TextScr.output(245, 20, "PropAoA :" + (int)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getPropAoA()));
        com.maddox.il2.engine.TextScr.output(i + 455, 120, "Cyls/Cams " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getCylinders());
        com.maddox.il2.engine.TextScr.output(i + 455, 100, "Readyness " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getReadyness()) + "%");
        com.maddox.il2.engine.TextScr.output(i + 455, 80, "PRM " + (int)((float)(int)(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getRPM() * 0.02F) * 50F) + " rpm");
        com.maddox.il2.engine.TextScr.output(i + 455, 60, "Thrust " + (int)((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getEngineForce())).x + " N");
        com.maddox.il2.engine.TextScr.output(i + 455, 40, "Fuel " + (int)((100F * ((com.maddox.il2.fm.FlightModelMain)this).M.fuel) / ((com.maddox.il2.fm.FlightModelMain)this).M.maxFuel) + "% Nitro " + (int)((100F * ((com.maddox.il2.fm.FlightModelMain)this).M.nitro) / ((com.maddox.il2.fm.FlightModelMain)this).M.maxNitro) + "%");
        com.maddox.il2.engine.TextScr.output(i + 455, 20, "MPrs " + (int)(1000F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getManifoldPressure()) + " mBar");
        com.maddox.il2.engine.TextScr.output(i + 640, 140, "---Controls---");
        com.maddox.il2.engine.TextScr.output(i + 640, 120, "A/C: " + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasAileronControl ? "" : "AIL ") + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasElevatorControl ? "" : "ELEV ") + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasRudderControl ? "" : "RUD ") + (((com.maddox.il2.fm.FlightModelMain)this).Gears.bIsHydroOperable ? "" : "GEAR "));
        com.maddox.il2.engine.TextScr.output(i + 640, 100, "ENG: " + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlThrottle() ? "" : "THTL ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlProp() ? "" : "PROP ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlMix() ? "" : "MIX ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
        com.maddox.il2.engine.TextScr.output(i + 640, 80, "PIL: (" + (int)(((com.maddox.il2.fm.FlightModelMain)this).AS.getPilotHealth(0) * 100F) + "%)");
        com.maddox.il2.engine.TextScr.output(i + 5, 105, "V   " + (int)((com.maddox.il2.fm.FlightModelMain)this).getSpeedKMH());
        com.maddox.il2.engine.TextScr.output(i + 5, 125, "AOA " + (float)(int)(((com.maddox.il2.fm.FlightModelMain)this).getAOA() * 1000F) / 1000F);
        com.maddox.il2.engine.TextScr.output(i + 5, 145, "AOS " + (float)(int)(((com.maddox.il2.fm.FlightModelMain)this).getAOS() * 1000F) / 1000F);
        com.maddox.il2.engine.TextScr.output(i + 5, 165, "Kr  " + (int)((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren());
        com.maddox.il2.engine.TextScr.output(i + 5, 185, "Ta  " + (int)((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage());
        com.maddox.il2.engine.TextScr.output(i + 250, 185, "way.speed  " + ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().getV() * 3.6F + "way.z " + ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().z() + "  mn_time = " + mn_time);
        com.maddox.il2.engine.TextScr.output(i + 5, 205, "<" + ((com.maddox.il2.engine.Interpolate)this).actor.name() + ">: " + com.maddox.il2.ai.air.ManString.tname(task) + ":" + com.maddox.il2.ai.air.ManString.name(maneuver) + " , WP=" + ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() + "(" + (((com.maddox.il2.fm.FlightModelMain)this).AP.way.size() - 1) + ")-" + com.maddox.il2.ai.air.ManString.wpname(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action));
        com.maddox.il2.engine.TextScr.output(i + 7, 225, "======= " + com.maddox.il2.ai.air.ManString.name(program[0]) + "  Sub = " + submaneuver + " follOffs.x = " + ((com.maddox.JGP.Tuple3d) (followOffset)).x + "  " + (((com.maddox.il2.ai.air.AutopilotAI)((com.maddox.il2.fm.FlightModelMain)this).AP).bWayPoint ? "Stab WPoint " : "") + (((com.maddox.il2.ai.air.AutopilotAI)((com.maddox.il2.fm.FlightModelMain)this).AP).bStabAltitude ? "Stab ALT " : "") + (((com.maddox.il2.ai.air.AutopilotAI)((com.maddox.il2.fm.FlightModelMain)this).AP).bStabDirection ? "Stab DIR " : "") + (((com.maddox.il2.ai.air.AutopilotAI)((com.maddox.il2.fm.FlightModelMain)this).AP).bStabSpeed ? "Stab SPD " : "   ") + (((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM).isDumb() ? "DUMB " : " ") + (((com.maddox.il2.fm.FlightModelMain) ((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM)).Gears.lgear ? "L " : " ") + (((com.maddox.il2.fm.FlightModelMain) ((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM)).Gears.rgear ? "R " : " ") + (((com.maddox.il2.ai.air.Maneuver) ((com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM)).TaxiMode ? "TaxiMode" : ""));
        com.maddox.il2.engine.TextScr.output(i + 7, 245, " ====== " + com.maddox.il2.ai.air.ManString.name(program[1]));
        com.maddox.il2.engine.TextScr.output(i + 7, 265, "  ===== " + com.maddox.il2.ai.air.ManString.name(program[2]));
        com.maddox.il2.engine.TextScr.output(i + 7, 285, "   ==== " + com.maddox.il2.ai.air.ManString.name(program[3]));
        com.maddox.il2.engine.TextScr.output(i + 7, 305, "    === " + com.maddox.il2.ai.air.ManString.name(program[4]));
        com.maddox.il2.engine.TextScr.output(i + 7, 325, "     == " + com.maddox.il2.ai.air.ManString.name(program[5]));
        com.maddox.il2.engine.TextScr.output(i + 7, 345, "      = " + com.maddox.il2.ai.air.ManString.name(program[6]) + "  " + (target == null ? "" : "TARGET  ") + (target_ground == null ? "" : "GROUND  ") + (danger == null ? "" : "DANGER  "));
        if(target != null && com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor))
            com.maddox.il2.engine.TextScr.output(i + 1, 365, " AT: (" + ((com.maddox.il2.engine.Interpolate) (target)).actor.name() + ") " + ((((com.maddox.il2.engine.Interpolate) (target)).actor instanceof com.maddox.il2.objects.air.Aircraft) ? "" : ((java.lang.Object) (((com.maddox.il2.engine.Interpolate) (target)).actor)).getClass().getName()));
        if(target_ground != null && com.maddox.il2.engine.Actor.isValid(target_ground))
            com.maddox.il2.engine.TextScr.output(i + 1, 385, " GT: (" + target_ground.name() + ") ..." + ((java.lang.Object) (target_ground)).getClass().getName());
        com.maddox.il2.engine.TextScr.output(400, 500, "+");
        com.maddox.il2.engine.TextScr.output(400, 400, "|");
        com.maddox.il2.engine.TextScr.output((int)(400F + 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl), (int)(500F - 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl), "+");
        com.maddox.il2.engine.TextScr.output((int)(400F + 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl), 400, "|");
        com.maddox.il2.engine.TextScr.output(250, 780, "followOffset  " + ((com.maddox.JGP.Tuple3d) (followOffset)).x + "  " + ((com.maddox.JGP.Tuple3d) (followOffset)).y + "  " + ((com.maddox.JGP.Tuple3d) (followOffset)).z + "  ");
        if(Group != null && Group.enemies != null)
            com.maddox.il2.engine.TextScr.output(250, 760, "Enemies   " + com.maddox.il2.ai.air.AirGroupList.length(Group.enemies[0]));
        com.maddox.il2.engine.TextScr.output(700, 780, "speedMode   " + speedMode);
        if(Group != null)
            com.maddox.il2.engine.TextScr.output(700, 760, "Group task  " + Group.grTaskName());
        if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLandingOnShip())
            com.maddox.il2.engine.TextScr.output(5, 460, "Landing On Carrier");
        com.maddox.il2.engine.TextScr.output(700, 740, "gattackCounter  " + gattackCounter);
        com.maddox.il2.engine.TextScr.output(5, 360, "silence = " + silence);
    }

    private void groundAttackGuns(com.maddox.il2.engine.Actor actor, float f)
    {
        if(submaneuver == 0 && sub_Man_Count == 0 && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null)
        {
            float f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / (f1 + ((com.maddox.il2.fm.FlightModel)this).getSpeed());
        }
        maxAOA = 15F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            setCheckGround(true);
            rocketsDelay = 0;
            if(sub_Man_Count == 0)
            {
                ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
                actor.getSpeed(tmpV3d);
                tmpV3f.x = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x;
                tmpV3f.y = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y;
                tmpV3f.z = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z;
                tmpV3f.z = 0.0D;
                if(tmpV3f.length() < 9.9999997473787516E-005D)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                    tmpV3f.z = 0.0D;
                }
                tmpV3f.normalize();
                Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3f)).x * 1500D;
                Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 1500D;
                Vtarg.z += 400D;
                ((com.maddox.JGP.Tuple3d) (constVtarg)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (constVtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                Ve.normalize();
                Vxy.cross(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                Vtarg.z += 100D;
                if(((com.maddox.JGP.Tuple3d) (Vxy)).z > 0.0D)
                {
                    Vtarg.x += ((com.maddox.JGP.Tuple3d) (Ve)).y * 1000D;
                    Vtarg.y -= ((com.maddox.JGP.Tuple3d) (Ve)).x * 1000D;
                } else
                {
                    Vtarg.x -= ((com.maddox.JGP.Tuple3d) (Ve)).y * 1000D;
                    Vtarg.y += ((com.maddox.JGP.Tuple3d) (Ve)).x * 1000D;
                }
                ((com.maddox.JGP.Tuple3d) (constVtarg1)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (constVtarg1)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f2 = (float)Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (constVtarg)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f3 = (float)Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.JGP.Tuple3d) (P)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            P.z += 4D;
            com.maddox.il2.engine.Engine.land();
            if(com.maddox.il2.engine.Landscape.rayHitHQ(((com.maddox.il2.fm.FlightModelMain)this).Loc, P, P))
            {
                push(0);
                push(38);
                pop();
                gattackCounter--;
                if(gattackCounter < 0)
                    gattackCounter = 0;
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            float f4 = (float)Ve.length();
            actor.getSpeed(tmpV3d);
            tmpV3f.x = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x;
            tmpV3f.y = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y;
            tmpV3f.z = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f4 * bullTime * 0.3333F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            float f5 = 0.3F * (f4 - 1000F);
            if(f5 < 0.0F)
                f5 = 0.0F;
            if(f5 > 300F)
                f5 = 300F;
            f5 += f4 * ((com.maddox.il2.fm.FlightModelMain)this).getAOA() * 0.005F;
            Ve.z += f5 + com.maddox.il2.ai.World.cur().rnd.nextFloat(-3F, 3F) * (float)(3 - ((com.maddox.il2.fm.FlightModelMain)this).Skill);
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            if(f4 < 800F && (shotAtFriend <= 0 || distToFriend > f4) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 15D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).z) < 10D)
            {
                if(f4 < 550F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
                if(f4 < 450F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = true;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1 && f4 < 500F)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                    rocketsDelay += 30;
                }
            }
            if(sub_Man_Count > 200 && ((com.maddox.JGP.Tuple3d) (Ve)).x < 200D || Alt < 40F)
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).Leader == null || !com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor))
                    com.maddox.il2.objects.sounds.Voice.speakEndGattack((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                rocketsDelay = 0;
                push(0);
                push(55);
                push(10);
                pop();
                dontSwitch = true;
                return;
            }
            Ve.normalize();
            attackTurnToDirection(f4, f, 4F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 2.0F);
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
        float f3 = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D ? 3F : 4F;
        boolean flag = false;
        boolean flag1 = false;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0].countBullets() != 0)
        {
            flag = true;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.ParaTorpedoGun)
                flag1 = true;
        } else
        if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeStormovik) && !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter) && !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeDiveBomber))
        {
            set_maneuver(0);
            return;
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        if(flag1)
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunTorp45_36AV_A)
                Ve.z = (((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - 100D) + ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextDouble() * 50D;
            else
                Ve.z = (((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - 200D) + ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextDouble() * 50D;
        float f4 = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - (float)((com.maddox.JGP.Tuple3d) (Ve)).z;
        if(f4 < 0.0F)
            f4 = 0.0F;
        float f5 = (float)java.lang.Math.sqrt(2.0F * f4 * 0.1019F) + 0.0017F * f4;
        actor.getSpeed(tmpV3d);
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && tmpV3d.length() > 20D)
        {
            target = ((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)actor)).FM;
            set_task(6);
            clear_stack();
            set_maneuver(27);
            dontSwitch = true;
            return;
        }
        float f6 = 0.5F;
        if(flag)
            f6 = (f5 + 5F) * 0.33333F;
        Vtarg.x = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        Vtarg.y = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        Vtarg.z = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (Vtarg)));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        if(flag)
            addWindCorrection();
        ((com.maddox.JGP.Tuple3d) (Ve)).add(0.0D, 0.0D, -0.5F + com.maddox.il2.ai.World.Rnd().nextFloat(-2F, 0.8F));
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        if(flag)
        {
            float f7 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() * f5 + 500F;
            if(f1 > f7)
                Ve.z += 200D;
            else
                Ve.z = 0.0D;
        }
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        Vtarg.normalize();
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        if(!flag)
        {
            groundAttackGuns(actor, f);
            return;
        }
        if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeStormovik))
        {
            groundAttackShallowDive(actor, f);
            return;
        }
        Ve.normalize();
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        Vpl.normalize();
        ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 1.0F;
        if(f1 + 4F * f5 < ((com.maddox.il2.fm.FlightModel)this).getSpeed() * f5 && ((com.maddox.JGP.Tuple3d) (Ve)).x > 0.0D && Vpl.dot(((com.maddox.JGP.Tuple3d) (Vtarg))) > 0.98000001907348633D)
        {
            if(!bombsOut)
            {
                bombsOut = true;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0].countBullets() != 0 && !(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunPara))
                    com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            }
            push(0);
            push(55);
            push(48);
            pop();
            com.maddox.il2.objects.sounds.Voice.speakEndGattack((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
        }
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
        {
            push(0);
            push(55);
            push(10);
            pop();
        }
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) > 0.10000000149011612D)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).z) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) > 0.0010000000474974513D)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -98F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x);
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
        if((double)((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z > 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).W.z = 0.0D;
        else
            ((com.maddox.il2.fm.FlightModelMain)this).W.z *= 1.0399999618530273D;
        float f2 = (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x);
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 0.0020000000949949026D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).z) < 0.0020000000949949026D)
            f2 = 0.0F;
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
        {
            f2 = 1.0F;
        } else
        {
            if((double)f2 * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y > 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).W.y = 0.0D;
            if(f2 < 0.0F)
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() < 0.1F)
                    f2 = 0.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
            } else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < 0.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
        }
        if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > f3 || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > f2)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.2F * f;
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.2F * f;
    }

    private void groundAttackKamikaze(com.maddox.il2.engine.Actor actor, float f)
    {
        if(submaneuver == 0 && sub_Man_Count == 0 && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null)
        {
            float f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            if(f1 > 0.01F)
                bullTime = 1.0F / f1;
        }
        maxAOA = 15F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            setCheckGround(true);
            rocketsDelay = 0;
            if(sub_Man_Count == 0)
            {
                ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                tmpV3f.x += com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F);
                tmpV3f.y += com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F);
                tmpV3f.z = 0.0D;
                if(tmpV3f.length() < 9.9999997473787516E-005D)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                    tmpV3f.z = 0.0D;
                }
                tmpV3f.normalize();
                Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3f)).x * 1500D;
                Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 1500D;
                Vtarg.z += 400D;
                ((com.maddox.JGP.Tuple3d) (constVtarg)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (constVtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                Ve.normalize();
                Vxy.cross(((com.maddox.JGP.Tuple3d) (Ve)), ((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                Vtarg.z += 100D;
                if(((com.maddox.JGP.Tuple3d) (Vxy)).z > 0.0D)
                {
                    Vtarg.x += ((com.maddox.JGP.Tuple3d) (Ve)).y * 1000D;
                    Vtarg.y -= ((com.maddox.JGP.Tuple3d) (Ve)).x * 1000D;
                } else
                {
                    Vtarg.x -= ((com.maddox.JGP.Tuple3d) (Ve)).y * 1000D;
                    Vtarg.y += ((com.maddox.JGP.Tuple3d) (Ve)).x * 1000D;
                }
                ((com.maddox.JGP.Tuple3d) (constVtarg1)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (constVtarg1)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f2 = (float)Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (constVtarg)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f3 = (float)Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            float f4 = (float)Ve.length();
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.MXY_7)
            {
                Ve.z += 0.01D * (double)f4;
                Vtarg.z += 0.01D * (double)f4;
            }
            actor.getSpeed(tmpV3d);
            tmpV3f.x = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x;
            tmpV3f.y = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y;
            tmpV3f.z = (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f4 * bullTime * 0.3333F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            float f5 = 0.3F * (f4 - 1000F);
            if(f5 < 0.0F)
                f5 = 0.0F;
            if(f5 > 300F)
                f5 = 300F;
            Ve.z += f5 + com.maddox.il2.ai.World.cur().rnd.nextFloat(-3F, 3F) * (float)(3 - ((com.maddox.il2.fm.FlightModelMain)this).Skill);
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            if(f4 < 50F && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 40D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).z) < 30D)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
            }
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < -50D)
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
            attackTurnToDirection(f4, f, 4F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 2.0F);
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
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            if(sub_Man_Count == 0)
            {
                ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
                actor.getSpeed(tmpV3d);
                if(tmpV3d.length() < 0.5D)
                    ((com.maddox.JGP.Tuple3d) (tmpV3d)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                tmpV3d.normalize();
                Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).x * 3000D;
                Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).y * 3000D;
                Vtarg.z += 500D;
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.z += 80D;
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d1 = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            sub_Man_Count++;
            if(d1 < 1500D)
            {
                if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 40D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).z) < 30D)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                push(0);
                push(10);
                push(48);
                pop();
                dontSwitch = true;
            }
            if(d1 < 500D && ((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
            {
                push(0);
                push(10);
                pop();
            }
            Ve.normalize();
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.80000001192092896D)
                turnToDirection(f);
            else
                attackTurnToDirection((float)d1, f, 2.0F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 1.5F);
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    private void groundAttackHS293(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(4);
        smConstSpeed = 120F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            if(sub_Man_Count == 0)
            {
                ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
                actor.getSpeed(tmpV3d);
                if(tmpV3d.length() < 0.5D)
                    ((com.maddox.JGP.Tuple3d) (tmpV3d)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                tmpV3d.normalize();
                Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).x * 3000D;
                Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).y * 3000D;
                Vtarg.z += 500D;
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            sub_Man_Count++;
            if(d < 10000D)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            Ve.normalize();
            farTurnToDirection();
            break;

        case 1: // '\001'
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.z += 2000D;
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d1 = Ve.angle(((com.maddox.il2.fm.FlightModelMain)this).Vwld);
            Ve.z = 0.0D;
            double d2 = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            sub_Man_Count++;
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)((com.maddox.il2.engine.Interpolate)this).actor;
            if(d2 > 2000D && d2 < 6500D && d1 < 0.90000000000000002D && !typeguidedbombcarrier.typeGuidedBombCgetIsGuiding())
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
                push(0);
                push(10);
                pop();
                dontSwitch = true;
            }
            if(d2 < 500D && ((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
            {
                push(0);
                push(10);
                pop();
            }
            Ve.normalize();
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 99999.800000011921D)
                turnToDirection(f);
            else
                attackTurnToDirection((float)d2, f, 2.0F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 1.5F);
            break;

        default:
            submaneuver = 0;
            sub_Man_Count = 0;
            break;
        }
    }

    private void groundAttackFritzX(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(4);
        smConstSpeed = 140F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        switch(submaneuver)
        {
        case 0: // '\0'
            if(sub_Man_Count == 0)
            {
                ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
                actor.getSpeed(tmpV3d);
                if(tmpV3d.length() < 0.5D)
                    ((com.maddox.JGP.Tuple3d) (tmpV3d)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                tmpV3d.normalize();
                Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).x * 3000D;
                Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).y * 3000D;
                Vtarg.z += 500D;
            }
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            sub_Man_Count++;
            if(d < 15000D)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            Ve.normalize();
            farTurnToDirection();
            break;

        case 1: // '\001'
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.z += 2000D;
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d1 = Ve.angle(((com.maddox.il2.fm.FlightModelMain)this).Vwld);
            Ve.z = 0.0D;
            double d2 = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            sub_Man_Count++;
            com.maddox.il2.objects.air.TypeGuidedBombCarrier typeguidedbombcarrier = (com.maddox.il2.objects.air.TypeGuidedBombCarrier)((com.maddox.il2.engine.Interpolate)this).actor;
            if(d2 < 4000D && d1 < 0.90000000000000002D && (d2 < 2000D || d1 > 0.40000000000000002D) && !typeguidedbombcarrier.typeGuidedBombCgetIsGuiding())
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
                setSpeedMode(5);
                push(0);
                push(10);
                pop();
                dontSwitch = true;
            }
            if(d2 < 500D && ((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
            {
                push(0);
                push(10);
                pop();
            }
            Ve.normalize();
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 99999.800000011921D)
                turnToDirection(f);
            else
                attackTurnToDirection((float)d2, f, 2.0F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 1.5F);
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
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        addWindCorrection();
        float f1 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        if(f1 < 0.0F)
            f1 = 0.0F;
        Ve.z += 250D;
        float f2 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y) + RandomVal * (float)(3 - ((com.maddox.il2.fm.FlightModelMain)this).Skill);
        if(((com.maddox.JGP.Tuple3d) (Ve)).z < (double)(-0.1F * f2))
            Ve.z = -0.1F * f2;
        if((double)Alt + ((com.maddox.JGP.Tuple3d) (Ve)).z < 250D)
            Ve.z = 250F - Alt;
        if(Alt < 50F)
        {
            push(10);
            pop();
        }
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 1.0F;
        float f3 = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z * 0.1019F;
        f3 += (float)java.lang.Math.sqrt(f3 * f3 + 2.0F * f1 * 0.1019F);
        float f4 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y);
        float f5 = f4 * f3 + 10F;
        actor.getSpeed(tmpV3d);
        ((com.maddox.JGP.Tuple3d) (tmpV3d)).scale((double)f3 * 0.34999999999999998D * (double)((com.maddox.il2.fm.FlightModelMain)this).Skill);
        Ve.x += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x;
        Ve.y += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y;
        Ve.z += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z;
        if(f5 >= f2)
        {
            bombsOut = true;
            com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
            push(0);
            push(55);
            push(48);
            pop();
            sub_Man_Count = 0;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        turnToDirection(f);
    }

    private void groundAttackDiveBomber(com.maddox.il2.engine.Actor actor, float f)
    {
        maxG = 5F;
        maxAOA = 10F;
        setSpeedMode(6);
        maxAOA = 4F;
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] == null || ((com.maddox.il2.fm.FlightModelMain)this).CT.getWeaponCount(3) == 0)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action == 3)
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
            set_maneuver(0);
            wingman(true);
            return;
        }
        if(Alt < 350F)
        {
            bombsOut = true;
            com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            setSpeedMode(6);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
            push(0);
            push(8);
            push(2);
            pop();
            sub_Man_Count = 0;
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f5 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        float f2 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y + ((com.maddox.JGP.Tuple3d) (Ve)).z * ((com.maddox.JGP.Tuple3d) (Ve)).z);
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        if(f1 > 1000F || submaneuver == 3 && sub_Man_Count > 100)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            actor.getSpeed(tmpV3d);
            float f6 = 0.0F;
            switch(submaneuver)
            {
            case 0: // '\0'
                f6 = f1 / 40F + 4F + Alt / 48F;
                // fall through

            case 1: // '\001'
                f6 = (f1 - man1Dist) / (float)((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() + 4F + Alt / 48F;
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
            Vtarg.x += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            Vtarg.y += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            Vtarg.z += (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z * f6 * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f4 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        if(f4 < 0.0F)
            f4 = 0.0F;
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (Vxy)));
        f5 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        f2 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y + ((com.maddox.JGP.Tuple3d) (Ve)).z * ((com.maddox.JGP.Tuple3d) (Ve)).z);
        f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        if(submaneuver < 2)
            Ve.z = 0.0D;
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
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
            ((com.maddox.JGP.Tuple3d) (Vxy)).set(com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F), com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F), com.maddox.il2.ai.World.Rnd().nextFloat(5F, f7));
            ((com.maddox.JGP.Tuple3d) (Vxy)).scale(4F - (float)((com.maddox.il2.fm.FlightModelMain)this).Skill);
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
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 1.0F;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
            push();
            push(6);
            safe_pop();
            submaneuver++;
            break;

        case 2: // '\002'
            setSpeedMode(4);
            smConstSpeed = 110F;
            sub_Man_Count++;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 1.0F;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
            float f3 = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -90F)
            {
                f3 -= 180F;
                if(f3 < -180F)
                    f3 += 360F;
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (float)((double)(-0.04F * f3) - 0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).x);
            if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() < 4F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.3F * f;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.3F * f;
            if(sub_Man_Count > 30 && ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -90F || sub_Man_Count > 150)
            {
                sub_Man_Count = 0;
                submaneuver++;
            }
            break;

        case 3: // '\003'
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 1.0F;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeFighter)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 1.0F;
            setSpeedMode(4);
            smConstSpeed = 110F;
            sub_Man_Count++;
            if(sub_Man_Count > 80)
            {
                float f9 = (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z * 0.1019F;
                f9 = f9 + (float)java.lang.Math.sqrt(f9 * f9 + 2.0F * f4 * 0.1019F) + 0.00035F * f4;
                float f10 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y);
                float f11 = f10 * f9;
                float f12 = 0.2F * (f1 - f11);
                Vxy.z += f12;
                if(((com.maddox.JGP.Tuple3d) (Vxy)).z > (double)(0.7F * f4))
                    Vxy.z = 0.7F * f4;
            }
            if(sub_Man_Count > 100 && Alt < 1000F && Vpl.dot(((com.maddox.JGP.Tuple3d) (Ve))) > 0.99000000953674316D || Alt < 600F)
            {
                bombsOut = true;
                com.maddox.il2.objects.sounds.Voice.speakAttackByBombs((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.BayDoorControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AirBrakeControl = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                push(0);
                push(8);
                push(2);
                pop();
            }
            break;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        if(submaneuver == 3)
            attackTurnToDirection(1000F, f, 30F);
        else
        if(submaneuver != 2)
            turnToDirection(f);
    }

    private void groundAttackTorpedo(com.maddox.il2.engine.Actor actor, float f)
    {
        float f4 = 50F;
        maxG = 5F;
        maxAOA = 8F;
        float f5 = 0.0F;
        setSpeedMode(4);
        java.lang.Class class1 = null;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.TorpedoGun)
        {
            com.maddox.il2.objects.weapons.TorpedoGun torpedogun = (com.maddox.il2.objects.weapons.TorpedoGun)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0];
            class1 = (java.lang.Class)com.maddox.rts.Property.value(((java.lang.Object) (torpedogun)).getClass(), "bulletClass", ((java.lang.Object) (null)));
        }
        smConstSpeed = 100F;
        if(class1 != null)
        {
            smConstSpeed = com.maddox.rts.Property.floatValue(class1, "dropSpeed", 100F) / 3.7F;
            f4 = com.maddox.rts.Property.floatValue(class1, "dropAltitude", 50F) + 10F;
        }
        if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.Swordfish)
        {
            setSpeedMode(9);
            f4 *= 0.75F;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        double d = ((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y;
        float f1 = (float)java.lang.Math.sqrt(d);
        if(first)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            submaneuver = 0;
        }
        if(submaneuver == 1 && sub_Man_Count == 0)
        {
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)));
            actor.getSpeed(tmpV3d);
            if(tmpV3f.length() > 9.9999997473787516E-005D)
                tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vxy)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 3000D, -((com.maddox.JGP.Tuple3d) (tmpV3f)).x * 3000D, 0.0D);
            direc = Vxy.dot(((com.maddox.JGP.Tuple3d) (Ve))) > 0.0D;
            if(direc)
                ((com.maddox.JGP.Tuple3d) (Vxy)).scale(-1D);
            ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (Vxy)));
            Vtarg.x += ((com.maddox.JGP.Tuple3d) (tmpV3d)).x * 80D;
            Vtarg.y += ((com.maddox.JGP.Tuple3d) (tmpV3d)).y * 80D;
            Vtarg.z = 80D;
            ((com.maddox.JGP.Tuple3d) (TargDevV)).set((double)com.maddox.il2.ai.World.cur().rnd.nextFloat(-16F, 16F) * (3.5D - (double)((com.maddox.il2.fm.FlightModelMain)this).Skill), (double)com.maddox.il2.ai.World.cur().rnd.nextFloat(-16F, 16F) * (3.5D - (double)((com.maddox.il2.fm.FlightModelMain)this).Skill), 0.0D);
        }
        if(submaneuver == 2)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            actor.getSpeed(tmpV3d);
            float f6 = 20F;
            if(class1 != null)
                f6 = com.maddox.rts.Property.floatValue(class1, "velocity", 1.0F);
            float f8 = actor.collisionR();
            if(f8 > 80F)
                f5 = 50F;
            if(f8 > 130F)
                f5 = 100F;
            if(f8 < 25F)
                f5 = -50F;
            float f11 = 950F;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.JU_88NEW)
                f11 += 90F;
            double d1 = java.lang.Math.sqrt(0.20399999999999999D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
            double d2 = 1.0D * d1 * (double)((com.maddox.il2.fm.FlightModel)this).getSpeed();
            double d3 = ((double)(f11 + f5) - d2) / (double)f6;
            Vtarg.x += (float)(((com.maddox.JGP.Tuple3d) (tmpV3d)).x * d3);
            Vtarg.y += (float)(((com.maddox.JGP.Tuple3d) (tmpV3d)).y * d3);
            Vtarg.z = f4;
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z < 30D)
                Vtarg.z += 3D * (30D - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
            ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (TargDevV)));
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f2 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y + ((com.maddox.JGP.Tuple3d) (Ve)).z * ((com.maddox.JGP.Tuple3d) (Ve)).z);
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        float f3 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        Vpl.normalize();
        if(Alt < f4 - 5F)
        {
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z += (f4 - Alt) * 0.25F;
            if(Alt < 8F)
                set_maneuver(2);
            if(Alt < 20F && f2 < 75F)
                set_maneuver(2);
        } else
        if(Alt > f4 + 5F && submaneuver == 1 && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z--;
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
            addWindCorrection();
            break;

        case 1: // '\001'
            setSpeedMode(4);
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.Swordfish)
                setSpeedMode(9);
            sub_Man_Count++;
            if(f2 < 1200F || f2 < 2000F && com.maddox.il2.game.ZutiSupportMethods.isStaticActor(actor))
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            addWindCorrection();
            break;

        case 2: // '\002'
            setSpeedMode(4);
            if(f2 < 800F + f5)
            {
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeHasToKG) && ((com.maddox.il2.objects.air.TypeHasToKG)((com.maddox.il2.engine.Interpolate)this).actor).isSalvo())
                {
                    int i = 0;
                    float f9 = actor.collisionR() * 1.8F;
                    i = (int)java.lang.Math.toDegrees(java.lang.Math.atan(f9 / 800F));
                    i += com.maddox.il2.ai.World.Rnd().nextInt(-2, 2);
                    if(i < 3)
                        i = 3;
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setSpreadAngle(i);
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
                setSpeedMode(6);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                submaneuver++;
                sub_Man_Count = 0;
                break;
            }
            if(!com.maddox.il2.game.ZutiSupportMethods.isStaticActor(actor))
                break;
            float f7 = com.maddox.rts.Property.floatValue(class1, "velocity", 20F);
            float f10 = com.maddox.rts.Property.floatValue(class1, "traveltime", 100F);
            float f12 = f7 * f10 - 150F;
            if(f2 < f12 && com.maddox.il2.ai.World.land().isWater(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.engine.Interpolate)this).actor.pos.getAbsPoint())).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.engine.Interpolate)this).actor.pos.getAbsPoint())).y))
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
                setSpeedMode(6);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 3: // '\003'
            setSpeedMode(9);
            sub_Man_Count++;
            if(sub_Man_Count > 150)
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
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        if(submaneuver == 3)
        {
            if(sub_Man_Count < 20)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(1.0D, 0.090000003576278687D, 0.029999999329447746D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(1.0D, 0.090000003576278687D, 0.0099999997764825821D);
            if(!direc)
                Ve.y *= -1D;
        }
        Ve.normalize();
        turnToDirection(f);
    }

    private void groundAttackTorpedoToKG(com.maddox.il2.engine.Actor actor, float f)
    {
        float f2 = 50F;
        maxG = 5F;
        maxAOA = 8F;
        setSpeedMode(4);
        java.lang.Class class1 = null;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.TorpedoGun)
        {
            com.maddox.il2.objects.weapons.TorpedoGun torpedogun = (com.maddox.il2.objects.weapons.TorpedoGun)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0];
            class1 = (java.lang.Class)com.maddox.rts.Property.value(((java.lang.Object) (torpedogun)).getClass(), "bulletClass", ((java.lang.Object) (null)));
        }
        smConstSpeed = 100F;
        if(class1 != null)
        {
            smConstSpeed = com.maddox.rts.Property.floatValue(class1, "dropSpeed", 100F) / 3.7F;
            f2 = com.maddox.rts.Property.floatValue(class1, "dropAltitude", 50F) + 10F;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        if(first)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            submaneuver = 0;
        }
        if(submaneuver == 1 && sub_Man_Count == 0)
        {
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (Vtarg)));
            if(tmpV3f.length() > 9.9999997473787516E-005D)
                tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vxy)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)).y * 3000D, -((com.maddox.JGP.Tuple3d) (tmpV3f)).x * 3000D, 0.0D);
            direc = Vxy.dot(((com.maddox.JGP.Tuple3d) (Ve))) > 0.0D;
            if(direc)
                ((com.maddox.JGP.Tuple3d) (Vxy)).scale(-1D);
            ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (Vxy)));
            Vtarg.z = 80D;
        }
        if(submaneuver == 2)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.z = f2;
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z < 30D)
                Vtarg.z += 3D * (30D - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y + ((com.maddox.JGP.Tuple3d) (Ve)).z * ((com.maddox.JGP.Tuple3d) (Ve)).z);
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        Vpl.normalize();
        if(Alt < f2 - 5F)
        {
            if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z += (f2 - Alt) * 0.25F;
            if(Alt < 8F)
                set_maneuver(2);
            if(Alt < 20F && f1 < 75F)
                set_maneuver(2);
        } else
        if(Alt > f2 + 5F && submaneuver == 1 && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z > 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z--;
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
            addWindCorrection();
            break;

        case 1: // '\001'
            setSpeedMode(4);
            sub_Man_Count++;
            if(f1 < 4000F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            addWindCorrection();
            break;

        case 2: // '\002'
            setSpeedMode(4);
            double d = Ve.angle(((com.maddox.il2.fm.FlightModelMain)this).Vwld);
            float f3 = 180F - (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw() - actor.pos.getAbsOrient().getYaw());
            if(f3 < -180F)
                f3 += 360F;
            else
            if(f3 > 180F)
                f3 -= 360F;
            float f4 = com.maddox.rts.Property.floatValue(class1, "velocity", 20F);
            float f5 = com.maddox.rts.Property.floatValue(class1, "traveltime", 100F);
            float f6 = f4 * f5 - 10F;
            if(f6 > 2700F)
                f6 = 2700F;
            double d1 = (java.lang.Math.abs(f3) - 90F) * 8F;
            if(d1 < 0.0D)
                d1 = 0.0D;
            if(((com.maddox.il2.fm.FlightModelMain)this).Skill == 2)
                d1 += 100D;
            if((double)f1 < (double)f6 - d1 && f1 > 1800F && d < 0.20000000000000001D)
            {
                actor.getSpeed(tmpV3d);
                float f7 = 1.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill == 2)
                    f7 = com.maddox.il2.ai.World.Rnd().nextFloat(0.8F, 1.2F);
                else
                if(((com.maddox.il2.fm.FlightModelMain)this).Skill == 3)
                    f7 = com.maddox.il2.ai.World.Rnd().nextFloat(0.9F, 1.1F);
                f7 = (float)((double)f7 + 0.10000000000000001D);
                com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(((com.maddox.il2.fm.FlightModel) (this)), f3, (float)(1.95D * tmpV3d.length()) * f7);
                if(((com.maddox.il2.objects.air.TypeHasToKG)((com.maddox.il2.engine.Interpolate)this).actor).isSalvo())
                {
                    int i = 0;
                    float f8 = actor.collisionR() * 1.8F;
                    i = (int)java.lang.Math.toDegrees(java.lang.Math.atan((double)f8 / ((double)f6 - d1)));
                    i += com.maddox.il2.ai.World.Rnd().nextInt(-2, 2);
                    if(i < 1)
                        i = 1;
                    ((com.maddox.il2.fm.FlightModelMain)this).AS.setSpreadAngle(i);
                }
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
                setSpeedMode(6);
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.next();
                push(15);
                submaneuver++;
                sub_Man_Count = 0;
                break;
            }
            if(f1 < 1500F)
            {
                com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(((com.maddox.il2.fm.FlightModel) (this)), 0.0F, 0.0F);
                set_maneuver(51);
            }
            break;

        case 3: // '\003'
            setSpeedMode(9);
            push(15);
            pop();
            task = 61;
            sub_Man_Count++;
            boolean flag = false;
            for(int j = 0; j < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3].length; j++)
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][j] != null && !(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][j] instanceof com.maddox.il2.objects.weapons.BombGunNull) && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][j].countBullets() != 0)
                    flag = true;

            if(sub_Man_Count > 800 || !flag)
            {
                task = 3;
                push(21);
                push(8);
                pop();
                submaneuver = 0;
                sub_Man_Count = 0;
            }
            break;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        if(submaneuver == 3)
        {
            if(sub_Man_Count < 20)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(1.0D, 0.090000003576278687D, 0.029999999329447746D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(1.0D, 0.090000003576278687D, 0.0099999997764825821D);
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
        ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        if(submaneuver == 3 && sub_Man_Count > 0 && sub_Man_Count < 45 && f1 > 200F)
        {
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vxy)));
            float f4 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(-f4);
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).add(((com.maddox.JGP.Tuple3d) (Ve)));
            float f7 = (float)tmpV3f.length();
            float f6;
            if(f7 > 150F)
                f6 = 7.5F / f7;
            else
                f6 = 0.05F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f6);
            tmpV3f.z = 0.0D;
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        }
        if(f1 <= 200F)
            sub_Man_Count = 50;
        if(first)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            submaneuver = 0;
        }
        if(submaneuver == 1 && sub_Man_Count == 0)
        {
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            actor.getSpeed(tmpV3d);
            if(tmpV3d.length() < 0.5D)
                ((com.maddox.JGP.Tuple3d) (tmpV3d)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            tmpV3d.normalize();
            ((com.maddox.JGP.Tuple3d) (Vxy)).set((float)((com.maddox.JGP.Tuple3d) (tmpV3d)).x, (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).y, (float)((com.maddox.JGP.Tuple3d) (tmpV3d)).z);
            Vtarg.x -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).x * 3000D;
            Vtarg.y -= ((com.maddox.JGP.Tuple3d) (tmpV3d)).y * 3000D;
            Vtarg.z += 250D;
        }
        if(submaneuver == 2 && sub_Man_Count == 0)
        {
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.x -= ((com.maddox.JGP.Tuple3d) (Vxy)).x * 1000D;
            Vtarg.y -= ((com.maddox.JGP.Tuple3d) (Vxy)).y * 1000D;
            Vtarg.z += 50D;
        }
        if(submaneuver == 3 && sub_Man_Count == 0)
        {
            checkGround = false;
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (actor.pos.getAbsPoint())));
            Vtarg.x += ((com.maddox.JGP.Tuple3d) (Vxy)).x * 1000D;
            Vtarg.y += ((com.maddox.JGP.Tuple3d) (Vxy)).y * 1000D;
            Vtarg.z += 50D;
        }
        ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        addWindCorrection();
        float f2 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y + ((com.maddox.JGP.Tuple3d) (Ve)).z * ((com.maddox.JGP.Tuple3d) (Ve)).z);
        ((com.maddox.JGP.Tuple3d) (Vf)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        float f3 = (float)(-((com.maddox.JGP.Tuple3d) (Ve)).z);
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
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
            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.80000001192092896D;
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.y *= 0.89999997615814209D;
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            float f5 = sub_Man_Count;
            if(f5 < 100F)
                f5 = 100F;
            if(Alt > 45F)
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z -= 0.002F * (Alt - 45F) * f5;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z -= 0.005F * (Alt - 45F) * f5;
            if(Alt < 0.0F)
                Alt = 0.0F;
            if(f2 < 1080F + ((com.maddox.il2.fm.FlightModel)this).getSpeed() * (float)java.lang.Math.sqrt((2.0F * Alt) / 9.81F))
                bombsOut = true;
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D || f2 < 350F || sub_Man_Count > 160)
            {
                push(0);
                push(10);
                push(10);
                pop();
            }
            break;
        }
        if(submaneuver == 0)
            ((com.maddox.JGP.Tuple3d) (Ve)).set(1.0D, 0.0D, 0.0D);
        turnToDirection(f);
    }

    public void goodFighterVsFighter(float f)
    {
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        float f2 = (float)Ve.length();
        float f4 = 1.0F / f2;
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.JGP.Tuple3d) (Vtarg)).scale(f4);
        float f5 = (((com.maddox.il2.fm.FlightModelMain)this).Energy - ((com.maddox.il2.fm.FlightModelMain) (target)).Energy) * 0.1019F;
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        if(sub_Man_Count == 0)
        {
            float f6 = 0.0F;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() > 0)
                f6 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null)
                f6 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0]).bulletSpeed();
            if(f6 > 0.01F)
                bullTime = 1.0F / f6;
            submanDelay = 0;
        }
        if(f1 < 1500F)
        {
            float f7 = 0.0F;
            float f9 = 0.0F;
            if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > -0.20000000298023224D)
            {
                f7 = 3F * ((float)((com.maddox.JGP.Tuple3d) (Vtarg)).x + 0.2F);
                ((com.maddox.JGP.Tuple3d) (Vxy)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Vxy)).scale(1.0D);
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vxy)));
                ((com.maddox.JGP.Tuple3d) (Vxy)).add(((com.maddox.JGP.Tuple3d) (Ve)));
                Vxy.normalize();
                f9 = 10F * (float)(((com.maddox.JGP.Tuple3d) (Vxy)).x - ((com.maddox.JGP.Tuple3d) (Vtarg)).x);
                if(f9 < -1F)
                    f9 = -1F;
                if(f9 > 1.0F)
                    f9 = 1.0F;
            } else
            {
                f7 = 3F * ((float)((com.maddox.JGP.Tuple3d) (Vtarg)).x + 0.2F);
            }
            if(submaneuver == 4 && ((com.maddox.JGP.Tuple3d) (Vtarg)).x < 0.60000002384185791D && (double)f2 < 300D)
            {
                submaneuver = 1;
                submanDelay = 30;
            }
            if(submaneuver != 4 && (double)f5 > 300D && ((com.maddox.JGP.Tuple3d) (Vtarg)).x > 0.75D)
            {
                submaneuver = 4;
                submanDelay = 240;
            }
            float f11 = 0.0015F * f5 + 0.0006F * f1 + f7 + 0.5F * f9;
            if(f11 > 0.9F && submanDelay == 0)
            {
                if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > 0.5D || (double)f1 * 2D < (double)f2)
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
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 800D);
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
            if(((com.maddox.JGP.Tuple3d) (Vtarg)).x < 0.5D)
            {
                if(submanDelay == 0)
                {
                    submaneuver = 3;
                    submanDelay = 120;
                }
            } else
            if(f5 > 600F && submaneuver == 0 || f5 > 800F)
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 800D);
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
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            }
        } else
        {
            ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 1000D);
            if(submanDelay == 0)
            {
                submaneuver = 0;
                submanDelay = 180;
            }
        }
        switch(submaneuver)
        {
        case 0: // '\0'
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            tmpV3f.z = 0.0D;
            float f3 = (float)tmpV3f.length();
            tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)));
            Vtarg.z = 0.0D;
            float f8 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Vtarg)));
            float f10 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - f8;
            if(f10 < 10F)
                f10 = 10F;
            float f12 = f3 / f10;
            if(f12 < 0.0F)
                f12 = 0.0F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(((com.maddox.JGP.Tuple3d) (Vtarg)), f12);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            setSpeedMode(9);
            farTurnToDirection();
            sub_Man_Count++;
            break;

        case 1: // '\001'
            setSpeedMode(9);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 5F);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            break;

        case 2: // '\002'
            setSpeedMode(9);
            tmpOr.setYPR(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw(), 0.0F, 0.0F);
            float f13 = 6F;
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, -f13, 10D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, f13, 10D);
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            farTurnToDirection();
            break;

        case 3: // '\003'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            setSpeedMode(9);
            tmpOr.setYPR(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw(), 0.0F, 0.0F);
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            Ve.z = 0.0D;
            Ve.normalize();
            Ve.z = 0.40000000000000002D;
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            attackTurnToDirection(1000F, f, 15F);
            break;

        case 4: // '\004'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            boomAttack(f);
            setSpeedMode(9);
            break;

        default:
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            fighterVsFighter(f);
            break;
        }
        if(submanDelay > 0)
            submanDelay--;
    }

    public void bNZFighterVsFighter(float f)
    {
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        dist = (float)Ve.length();
        float f1 = (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Ve)).x * ((com.maddox.JGP.Tuple3d) (Ve)).x + ((com.maddox.JGP.Tuple3d) (Ve)).y * ((com.maddox.JGP.Tuple3d) (Ve)).y);
        float f2 = 1.0F / dist;
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.JGP.Tuple3d) (Vtarg)).scale(f2);
        float f3 = (((com.maddox.il2.fm.FlightModelMain)this).Energy - ((com.maddox.il2.fm.FlightModelMain) (target)).Energy) * 0.1019F;
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        if(sub_Man_Count == 0)
        {
            float f4 = 0.0F;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() > 0)
                f4 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null)
                f4 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0]).bulletSpeed();
            if(f4 > 0.01F)
                bullTime = 1.0F / f4;
            submanDelay = 0;
        }
        if(f1 < 1500F)
        {
            float f5 = 0.0F;
            float f7 = 0.0F;
            if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > -0.20000000298023224D)
            {
                f5 = 3F * ((float)((com.maddox.JGP.Tuple3d) (Vtarg)).x + 0.2F);
                ((com.maddox.JGP.Tuple3d) (Vxy)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Vxy)).scale(1.0D);
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vxy)));
                ((com.maddox.JGP.Tuple3d) (Vxy)).add(((com.maddox.JGP.Tuple3d) (Ve)));
                Vxy.normalize();
                f7 = 20F * (float)(((com.maddox.JGP.Tuple3d) (Vxy)).x - ((com.maddox.JGP.Tuple3d) (Vtarg)).x);
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
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 800D);
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
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 800D);
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
                ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
                if(submanDelay == 0)
                {
                    submaneuver = 0;
                    submanDelay = 120;
                }
            }
        } else
        {
            ((com.maddox.JGP.Tuple3d) (Ve)).set(0.0D, 0.0D, 1000D);
            if(submanDelay == 0)
            {
                submaneuver = 0;
                submanDelay = 180;
            }
        }
        switch(submaneuver)
        {
        case 0: // '\0'
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            tmpV3f.z = 0.0D;
            dist = (float)tmpV3f.length();
            tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)));
            Vtarg.z = 0.0D;
            float f6 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Vtarg)));
            float f8 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - f6;
            if(f8 < 10F)
                f8 = 10F;
            float f10 = dist / f8;
            if(f10 < 0.0F)
                f10 = 0.0F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(((com.maddox.JGP.Tuple3d) (Vtarg)), f10);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            setSpeedMode(9);
            farTurnToDirection();
            sub_Man_Count++;
            break;

        case 1: // '\001'
            setSpeedMode(9);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -0.04F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 5F);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            break;

        case 2: // '\002'
            setSpeedMode(9);
            tmpOr.setYPR(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw(), 0.0F, 0.0F);
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() > 0.0F)
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, -6D, 10D);
            else
                ((com.maddox.JGP.Tuple3d) (Ve)).set(100D, 6D, 10D);
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            farTurnToDirection();
            break;

        case 3: // '\003'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            fighterVsFighter(f);
            setSpeedMode(6);
            break;

        default:
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            fighterVsFighter(f);
            break;
        }
        if(submanDelay > 0)
            submanDelay--;
    }

    public void setBomberAttackType(int i)
    {
        float f;
        if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor))
            f = ((com.maddox.il2.engine.Interpolate) (target)).actor.collisionR();
        else
            f = 15F;
        setRandomTargDeviation(0.8F);
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 150F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 1: // '\001'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain) (target)).EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, ((com.maddox.il2.fm.FlightModelMain) (target)).EI.getNum() - 1)].getEnginePos())));
            TargV.x--;
            ApproachR = 150F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 2: // '\002'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain) (target)).EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, ((com.maddox.il2.fm.FlightModelMain) (target)).EI.getNum() - 1)].getEnginePos())));
            TargV.x--;
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        case 3: // '\003'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(2600D, 0.0D, 300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 800F;
            TargY = 25F;
            TargZ = 15F;
            TargYS = 3F;
            TargZS = 3F;
            break;

        case 4: // '\004'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-400F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, -200F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 1.3F * f;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 5: // '\005'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(0.0D, 0.0D, 0.0D);
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 600F;
            TargY = 25F;
            TargZ = 12F;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 6: // '\006'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(600D, (float)(600 - com.maddox.il2.ai.World.Rnd().nextInt(0, 1) * 1200) + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 300D);
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 1.2F * f;
            TargYS = 0.26F * f;
            TargZS = 0.26F * f;
            break;

        case 7: // '\007'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain) (target)).EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, ((com.maddox.il2.fm.FlightModelMain) (target)).EI.getNum() - 1)].getEnginePos())));
            ApproachR = 200F;
            TargY = 10F;
            TargZ = 10F;
            TargYS = 2.0F;
            TargZS = 2.0F;
            break;

        case 8: // '\b'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 200F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.2F * f;
            TargZS = 0.2F * f;
            break;

        case 9: // '\t'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, 600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(0.0D, 0.0D, 0.0D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.2F * f;
            TargZS = 0.2F * f;
            break;

        case 10: // '\n'
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-600F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F), 0.0D, -300F + com.maddox.il2.ai.World.Rnd().nextFloat(-100F, 100F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(100D, 0.0D, -400D);
            ApproachR = 300F;
            TargY = 2.1F * f;
            TargZ = 3.9F * f;
            TargYS = 0.43F * f;
            TargZS = 0.43F * f;
            break;

        default:
            ((com.maddox.JGP.Tuple3d) (ApproachV)).set(-1000F + com.maddox.il2.ai.World.Rnd().nextFloat(-200F, 200F), 0.0D, 100F + com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F));
            ((com.maddox.JGP.Tuple3d) (TargV)).set(((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain) (target)).EI.engines[com.maddox.il2.ai.World.Rnd().nextInt(0, ((com.maddox.il2.fm.FlightModelMain) (target)).EI.getNum() - 1)].getEnginePos())));
            ApproachR = 200F;
            TargY = 10F;
            TargZ = 10F;
            TargYS = 2.0F;
            TargZS = 2.0F;
            break;
        }
        float f1 = 0.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() > 0)
            f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
        else
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null)
            f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0]).bulletSpeed();
        if(f1 > 0.01F)
            bullTime = 1.0F / f1;
    }

    public void fighterVsBomber(float f)
    {
        maxAOA = 15F;
        tmpOr.setAT0(((com.maddox.il2.fm.FlightModelMain) (target)).Vwld);
        switch(submaneuver)
        {
        case 0: // '\0'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 4F;
            rocketsDelay = 0;
            bulletDelay = 0;
            double d = 0.0001D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)).z;
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            tmpOr.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (scaledApproachV)).set(((com.maddox.JGP.Tuple3d) (ApproachV)));
            scaledApproachV.x -= 700D * d;
            scaledApproachV.z += 500D * d;
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (scaledApproachV)));
            Ve.normalize();
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(((com.maddox.JGP.Tuple3d) (scaledApproachV)), -1D);
            tmpV3f.normalize();
            double d1 = Ve.dot(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            Ve.normalize();
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)));
            tmpV3f.normalize();
            double d2 = Ve.dot(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (scaledApproachV)));
            Ve.x += 60D * (2D * (1.0D - d1) + 4D * (1.0D - d2));
            tmpOr.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d3 = ((com.maddox.JGP.Tuple3d) (Ve)).z;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            tmpV3f.z = 0.0D;
            float f1 = (float)tmpV3f.length();
            float f4 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)));
            Vtarg.z = 0.0D;
            float f7 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Vtarg)));
            float f8 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - f7;
            if(f8 < 10F)
                f8 = 10F;
            float f9 = f1 / f8;
            if(f9 < 0.0F)
                f9 = 0.0F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(((com.maddox.JGP.Tuple3d) (Vtarg)), f9 * f4);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (TargV)));
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f2 = (float)Ve.length();
            float f5 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            float f10 = f2 * bullTime * 0.0025F;
            if(f10 > 0.05F)
                f10 = 0.05F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * f10 * f5);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)((com.maddox.JGP.Tuple3d) (Ve)).x, f2, ((com.maddox.il2.fm.FlightModel) (this)));
            if(f2 > 3200F || ((com.maddox.JGP.Tuple3d) (Vtarg)).z > 1500D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                set_maneuver(0);
                break;
            }
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.012F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f2)) * (0.30000001192092896D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                Ve.normalize();
            }
            attackTurnToDirection(f2, f, 10F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 8F);
            setSpeedMode(4);
            smConstSpeed = 180F;
            if(f2 < 400F)
            {
                submaneuver++;
                sub_Man_Count = 0;
            }
            break;

        case 2: // '\002'
            ((com.maddox.il2.fm.FlightModelMain)this).minElevCoeff = 20F;
            if(rocketsDelay > 0)
                rocketsDelay--;
            if(bulletDelay > 0)
                bulletDelay--;
            if(bulletDelay == 120)
                bulletDelay = 0;
            setRandomTargDeviation(0.8F);
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (TargV)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (TargDevV)));
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            float f3 = (float)Ve.length();
            float f6 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            float f11 = 0.0002F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f3 * (bullTime + f11) * f6);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vpl)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f3 * bullTime * f6);
            ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            if(f3 > 4000F || ((com.maddox.JGP.Tuple3d) (Vtarg)).z > 2000D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                set_maneuver(0);
                break;
            }
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vtarg)));
            if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > 0.0D && f3 < 500F && (shotAtFriend <= 0 || distToFriend > f3) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).y) < (double)(TargY - TargYS * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).z) < (double)(TargZ - TargZS * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill) && bulletDelay < 120)
            {
                if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = true;
                bulletDelay += 2;
                if(bulletDelay >= 118)
                {
                    int i = (int)(((com.maddox.il2.fm.FlightModelMain) (target)).getW().length() * 150D);
                    if(i > 60)
                        i = 60;
                    bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * ((com.maddox.il2.fm.FlightModelMain)this).Skill, 2 * i * ((com.maddox.il2.fm.FlightModelMain)this).Skill);
                }
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                    if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).y) < (double)(TargY - TargYS * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).z) < (double)(TargZ - TargZS * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill))
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                    rocketsDelay += 120;
                }
                ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.8F, f3, ((com.maddox.il2.fm.FlightModel) (this)));
                ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(((com.maddox.il2.engine.Interpolate)this).actor);
            }
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)((com.maddox.JGP.Tuple3d) (Ve)).x, f3, ((com.maddox.il2.fm.FlightModel) (this)));
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f3)) * (0.30000001192092896D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                Ve.normalize();
            }
            attackTurnToDirection(f3, f, 10F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 8F);
            if(target.getSpeed() > 70F)
            {
                setSpeedMode(2);
                tailForStaying = target;
                ((com.maddox.JGP.Tuple3d) (tailOffset)).set(-20D, 0.0D, 0.0D);
            } else
            {
                setSpeedMode(4);
                smConstSpeed = 70F;
            }
            if(strikeEmer)
            {
                ((com.maddox.JGP.Tuple3d) (Vpl)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (strikeTarg)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vpl)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                if(((com.maddox.JGP.Tuple3d) (tmpV3f)).x < 0.0D)
                    return;
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (strikeTarg)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(0.69999998807907104D);
                ((com.maddox.JGP.Tuple3d) (Vpl)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vpl)));
                push();
                if(((com.maddox.JGP.Tuple3d) (Vpl)).z < 0.0D)
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (ApproachV)));
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            tmpV3f.z = 0.0D;
            float f1 = (float)tmpV3f.length();
            float f4 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            tmpV3f.normalize();
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)));
            Vtarg.z = 0.0D;
            float f7 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Vtarg)));
            float f8 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - f7;
            if(f8 < 10F)
                f8 = 10F;
            float f9 = f1 / f8;
            if(f9 < 0.0F)
                f9 = 0.0F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(((com.maddox.JGP.Tuple3d) (Vtarg)), f9 * f4);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (TargV)));
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f2 = (float)Ve.length();
            float f5 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            float f10 = f2 * bullTime * 0.0025F;
            if(f10 > 0.02F)
                f10 = 0.02F;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * f10 * f5);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)((com.maddox.JGP.Tuple3d) (Ve)).x, f2, ((com.maddox.il2.fm.FlightModel) (this)));
            if(f2 > 3200F || ((com.maddox.JGP.Tuple3d) (Vtarg)).z > 1500D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                break;
            }
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f2)) * (0.30000001192092896D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (TargV)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (TargDevV)));
            ((com.maddox.il2.fm.FlightModelMain) (target)).Or.transform(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            float f3 = (float)Ve.length();
            float f6 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
            ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f3 * bullTime * f6);
            ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
            ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            if(f3 > 4000F || ((com.maddox.JGP.Tuple3d) (Vtarg)).z > 2000D)
            {
                submaneuver = 0;
                sub_Man_Count = 0;
                break;
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).AS.astatePilotStates[1] < 100 && f3 < 330F && java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 3F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
            Ve.normalize();
            if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.30000001192092896D)
            {
                Vtarg.z += (double)(0.01F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f3)) * (0.30000001192092896D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                Ve.normalize();
            }
            attackTurnToDirection(f3, f, 6F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 3F);
            setSpeedMode(1);
            tailForStaying = target;
            ((com.maddox.JGP.Tuple3d) (tailOffset)).set(-190D, 0.0D, 0.0D);
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
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() > 0)
                f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null)
                f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0]).bulletSpeed();
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
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        setRandomTargDeviation(0.3F);
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (TargDevV)));
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        float f2 = (float)Ve.length();
        float f3 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        float f4 = 0.0002F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * (bullTime + f4) * f3);
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vpl)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * bullTime * f3);
        ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vpl)));
        float f5 = (float)(-((com.maddox.JGP.Tuple3d) (Vpl)).x);
        if(f5 < 0.001F)
            f5 = 0.001F;
        Vpl.normalize();
        if(((com.maddox.JGP.Tuple3d) (Vpl)).x < -0.93999999761581421D && f2 / f5 < 1.5F * (float)(3 - ((com.maddox.il2.fm.FlightModelMain)this).Skill))
        {
            push();
            set_maneuver(14);
            return;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vtarg)));
        if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > 0.0D && f2 < 500F && (shotAtFriend <= 0 || distToFriend > f2))
        {
            if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).y) < 13D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).z) < 13D)
                ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.95F, f2, ((com.maddox.il2.fm.FlightModel) (this)));
            if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).y) < (double)(12.5F - 3.5F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).z) < 12.5D - 3.5D * (double)((com.maddox.il2.fm.FlightModelMain)this).Skill && bulletDelay < 120)
            {
                if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
                bulletDelay += 2;
                if(bulletDelay >= 118)
                {
                    int i = (int)(((com.maddox.il2.fm.FlightModelMain) (target)).getW().length() * 150D);
                    if(i > 60)
                        i = 60;
                    bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * ((com.maddox.il2.fm.FlightModelMain)this).Skill, 2 * i * ((com.maddox.il2.fm.FlightModelMain)this).Skill);
                }
                if(f2 < 400F)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = true;
                    if(f2 < 100F && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                    {
                        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).y) < 4D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).z) < 4D)
                            ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                        com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                        rocketsDelay += 120;
                    }
                }
                ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(((com.maddox.il2.engine.Interpolate)this).actor);
            }
        }
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)((com.maddox.JGP.Tuple3d) (Ve)).x, f2, ((com.maddox.il2.fm.FlightModel) (this)));
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.30000001192092896D)
        {
            Vtarg.z += (double)(0.01F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f2)) * (0.30000001192092896D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
        }
        attackTurnToDirection(f2, f, 10F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 8F);
        if(Alt > 150F && ((com.maddox.JGP.Tuple3d) (Ve)).x > 0.59999999999999998D && (double)f2 < 70D)
        {
            setSpeedMode(1);
            tailForStaying = target;
            ((com.maddox.JGP.Tuple3d) (tailOffset)).set(-20D, 0.0D, 0.0D);
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
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0].countBullets() > 0)
                f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[1][0]).bulletSpeed();
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0] != null)
                f1 = ((com.maddox.il2.engine.GunGeneric)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[0][0]).bulletSpeed();
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
        ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        setRandomTargDeviation(0.3F);
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (TargDevV)));
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        float f2 = (float)Ve.length();
        float f3 = 0.55F + 0.15F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        float f4 = 0.000333F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill;
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * (bullTime + f4) * f3);
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vpl)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(f2 * bullTime * f3);
        ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vpl)));
        float f5 = (float)(-((com.maddox.JGP.Tuple3d) (Vpl)).x);
        if(f5 < 0.001F)
            f5 = 0.001F;
        Vpl.normalize();
        if(((com.maddox.JGP.Tuple3d) (Vpl)).x < -0.93999999761581421D && f2 / f5 < 1.5F * (float)(3 - ((com.maddox.il2.fm.FlightModelMain)this).Skill))
        {
            push();
            set_maneuver(14);
            return;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vtarg)));
        if(((com.maddox.JGP.Tuple3d) (Vtarg)).x > 0.0D && f2 < 700F && (shotAtFriend <= 0 || distToFriend > f2) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).y) < (double)(15.5F - 3.5F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill) && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Vtarg)).z) < 15.5D - 3.5D * (double)((com.maddox.il2.fm.FlightModelMain)this).Skill && bulletDelay < 120)
        {
            ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, 0.8F, f2, ((com.maddox.il2.fm.FlightModel) (this)));
            if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.KI_46_OTSUHEI))
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = true;
            bulletDelay += 2;
            if(bulletDelay >= 118)
            {
                int i = (int)(((com.maddox.il2.fm.FlightModelMain) (target)).getW().length() * 150D);
                if(i > 60)
                    i = 60;
                bulletDelay += com.maddox.il2.ai.World.Rnd().nextInt(i * ((com.maddox.il2.fm.FlightModelMain)this).Skill, 2 * i * ((com.maddox.il2.fm.FlightModelMain)this).Skill);
            }
            if(f2 < 500F)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = true;
                if(f2 < 100F && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][0] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2][((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[2].length - 1].countBullets() != 0 && rocketsDelay < 1)
                {
                    ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                    if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).y) < 4D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (tmpV3f)).z) < 4D)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = true;
                    com.maddox.il2.objects.sounds.Voice.speakAttackByRockets((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                    rocketsDelay += 120;
                }
            }
            ((com.maddox.il2.ai.air.Pilot)target).setAsDanger(((com.maddox.il2.engine.Interpolate)this).actor);
        }
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
        Ve.normalize();
        ((com.maddox.il2.ai.air.Maneuver)target).incDangerAggressiveness(1, (float)((com.maddox.JGP.Tuple3d) (Ve)).x, f2, ((com.maddox.il2.fm.FlightModel) (this)));
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.89999997615814209D)
        {
            Vtarg.z += (double)(0.03F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * (800F + f2)) * (0.89999997615814209D - ((com.maddox.JGP.Tuple3d) (Ve)).x);
            ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (Vtarg)));
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
            Ve.normalize();
        }
        attackTurnToDirection(f2, f, 10F + (float)((com.maddox.il2.fm.FlightModelMain)this).Skill * 8F);
    }

    private void turnToDirection(float f)
    {
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) > 0.10000000149011612D)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).z) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -10F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x);
        if((double)((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z > 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).W.z = 0.0D;
        else
            ((com.maddox.il2.fm.FlightModelMain)this).W.z *= 1.0399999618530273D;
        float f1 = (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x);
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 0.0020000000949949026D && java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).z) < 0.0020000000949949026D)
            f1 = 0.0F;
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
        {
            f1 = 1.0F;
        } else
        {
            if((double)f1 * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y > 0.0D)
                ((com.maddox.il2.fm.FlightModelMain)this).W.y = 0.0D;
            if(f1 < 0.0F)
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() < 0.1F)
                    f1 = 0.0F;
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > 0.0F)
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
            } else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < 0.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 0.0F;
        }
        if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > maxG || ((com.maddox.il2.fm.FlightModelMain)this).AOA > maxAOA || ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > f1)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.3F * f;
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.3F * f;
    }

    private void farTurnToDirection()
    {
        farTurnToDirection(4F);
    }

    private void farTurnToDirection(float f)
    {
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(1.0D, 0.0D, 0.0D);
        tmpV3f.cross(((com.maddox.JGP.Tuple3d) (Vpl)), ((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -10F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 1.0F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y;
        float f7 = (((com.maddox.il2.fm.FlightModel)this).getSpeed() / ((com.maddox.il2.fm.FlightModelMain)this).Vmax) * 45F;
        if(f7 > 85F)
            f7 = 85F;
        float f8 = (float)((com.maddox.JGP.Tuple3d) (Ve)).x;
        if(f8 < 0.0F)
            f8 = 0.0F;
        float f1;
        if(((com.maddox.JGP.Tuple3d) (tmpV3f)).z >= 0.0D)
            f1 = (-0.02F * (f7 + ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) * (1.0F - f8) - 0.05F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage()) + 1.0F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
        else
            f1 = -0.02F * (-f7 + ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) * (1.0F - f8) + 0.05F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 1.0F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
        float f2 = (-(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) + 1.0F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
        float f4 = -0.1F * (((com.maddox.il2.fm.FlightModelMain)this).getAOA() - 10F) + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
        float f5;
        if(((com.maddox.JGP.Tuple3d) (Ve)).z > 0.0D)
            f5 = -0.1F * (((com.maddox.il2.fm.FlightModelMain)this).getAOA() - f) + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
        else
            f5 = 1.0F * (float)((com.maddox.JGP.Tuple3d) (Ve)).z + 0.5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y;
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) < 0.0020000000949949026D)
        {
            f2 = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
        }
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        float f9 = ((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (tmpV3f)).y, ((com.maddox.JGP.Tuple3d) (tmpV3f)).x) * 180F) / 3.1415F;
        float f10 = 1.0F - java.lang.Math.abs(f9 - ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getYaw()) * 0.01F;
        if(f10 < 0.0F || ((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0D)
            f10 = 0.0F;
        float f3 = f10 * f2 + (1.0F - f10) * f1;
        float f6 = f10 * f5 + (1.0F - f10) * f4;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = f3;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = f6;
    }

    private void attackTurnToDirection(float f, float f1, float f2)
    {
        if(((com.maddox.JGP.Tuple3d) (Ve)).x < 0.0099999997764825821D)
            Ve.x = 0.0099999997764825821D;
        if(sub_Man_Count == 0)
            ((com.maddox.JGP.Tuple3d) (oldVe)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        if(((com.maddox.JGP.Tuple3d) (Ve)).x > 0.94999998807907104D)
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = (float)(-30D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 1.5D * (((com.maddox.JGP.Tuple3d) (Ve)).y - ((com.maddox.JGP.Tuple3d) (oldVe)).y));
            float f3;
            if(((com.maddox.JGP.Tuple3d) (Ve)).z > 0.0D || ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl > 0.9F)
            {
                f3 = (float)(10D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 6D * (((com.maddox.JGP.Tuple3d) (Ve)).z - ((com.maddox.JGP.Tuple3d) (oldVe)).z));
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (-30F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.02F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) + 5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
            } else
            {
                f3 = (float)(5D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 6D * (((com.maddox.JGP.Tuple3d) (Ve)).z - ((com.maddox.JGP.Tuple3d) (oldVe)).z));
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (-5F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.02F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) + 5F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
            }
            if(((com.maddox.JGP.Tuple3d) (Ve)).x > (double)(1.0F - 0.005F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill))
            {
                tmpOr.set(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)));
                tmpOr.increment((float)java.lang.Math.toDegrees(java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x)), (float)java.lang.Math.toDegrees(java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x)), 0.0F);
                ((com.maddox.il2.fm.FlightModelMain)this).Or.interpolate(tmpOr, 0.1F);
            }
            if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl - f3) < f2 * f1)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = f3;
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < f3)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += f2 * f1;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= f2 * f1;
        } else
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).Skill >= 2 && ((com.maddox.JGP.Tuple3d) (Ve)).z > 0.5D && f < 600F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.1F;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
            float f5 = 0.6F - (float)((com.maddox.JGP.Tuple3d) (Ve)).z;
            if(f5 < 0.0F)
                f5 = 0.0F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = (float)(-30D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) * (double)f5 + 1.0D * (((com.maddox.JGP.Tuple3d) (Ve)).y - ((com.maddox.JGP.Tuple3d) (oldVe)).y) * ((com.maddox.JGP.Tuple3d) (Ve)).x + 0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z);
            float f4;
            if(((com.maddox.JGP.Tuple3d) (Ve)).z > 0.0D)
            {
                f4 = (float)(10D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 6D * (((com.maddox.JGP.Tuple3d) (Ve)).z - ((com.maddox.JGP.Tuple3d) (oldVe)).z) + 0.5D * (double)(float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y);
                if(f4 < 0.0F)
                    f4 = 0.0F;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (float)((-20D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).z) - 0.050000000000000003D * (double)((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) + 5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x);
            } else
            {
                f4 = (float)(-5D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).z, ((com.maddox.JGP.Tuple3d) (Ve)).x) + 6D * (((com.maddox.JGP.Tuple3d) (Ve)).z - ((com.maddox.JGP.Tuple3d) (oldVe)).z) + 0.5D * (double)(float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y);
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = (float)((-20D * java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).z) - 0.050000000000000003D * (double)((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) + 5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x);
            }
            if(f4 < 0.0F)
                f4 = 0.0F;
            if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl - f4) < f2 * f1)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = f4;
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < f4)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += 0.3F * f1;
            else
                ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl -= 0.3F * f1;
        }
        float f6 = 0.054F * (600F - f);
        if(f6 < 4F)
            f6 = 4F;
        if(f6 > ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit)
            f6 = ((com.maddox.il2.fm.FlightModelMain)this).AOA_Crit;
        if(((com.maddox.il2.fm.FlightModelMain)this).AOA > f6 - 1.5F)
            ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.16F * (f6 - 1.5F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
        if(((com.maddox.il2.fm.FlightModelMain)this).AOA < -5F)
            ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment(0.0F, 0.12F * (-5F - ((com.maddox.il2.fm.FlightModelMain)this).AOA), 0.0F);
        ((com.maddox.JGP.Tuple3d) (oldVe)).set(((com.maddox.JGP.Tuple3d) (Ve)));
        oldAOA = ((com.maddox.il2.fm.FlightModelMain)this).AOA;
        sub_Man_Count++;
        ((com.maddox.il2.fm.FlightModelMain)this).W.x *= 0.94999999999999996D;
    }

    private void doCheckStrike()
    {
        if(((com.maddox.il2.fm.FlightModelMain)this).M.massEmpty > 5000F && !((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLanding())
            return;
        if(maneuver == 24 && strikeTarg == ((com.maddox.il2.fm.FlightModelMain)this).Leader)
            return;
        if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeDockable) && ((com.maddox.il2.objects.air.TypeDockable)((com.maddox.il2.engine.Interpolate)this).actor).typeDockableIsDocked())
            return;
        ((com.maddox.JGP.Tuple3d) (Vpl)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (strikeTarg)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (Vpl)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        if(((com.maddox.JGP.Tuple3d) (tmpV3f)).x < 0.0D)
            return;
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (strikeTarg)).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
        ((com.maddox.JGP.Tuple3d) (tmpV3f)).scale(0.69999998807907104D);
        ((com.maddox.JGP.Tuple3d) (Vpl)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vpl)));
        push();
        if(((com.maddox.JGP.Tuple3d) (Vpl)).z > 0.0D)
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
        float f1 = com.maddox.il2.fm.FMMath.positiveSquareEquation(-0.5F * com.maddox.il2.ai.World.g(), (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z, f);
        if(f1 < 0.0F)
            return -1000000F;
        else
            return f1 * (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).x + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).y);
    }

    protected void wingman(boolean flag)
    {
        if(((com.maddox.il2.fm.FlightModelMain)this).Wingman == null)
        {
            return;
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain)this).Wingman.Leader = ((com.maddox.il2.fm.FlightModel) (flag ? ((com.maddox.il2.fm.FlightModel) (this)) : null));
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
        for(int i = 0; i < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons.length; i++)
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i].length > 0)
            {
                for(int j = 0; j < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i].length; j++)
                    if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.BombGun)
                        if((((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.BombGunPara) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.SM79) && (((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i][j] instanceof com.maddox.il2.objects.weapons.TorpedoGun))
                        {
                            flag = true;
                        } else
                        {
                            ((com.maddox.il2.objects.weapons.BombGun)((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i][j]).setBombDelay(3.3E+007F);
                            if(((com.maddox.il2.engine.Interpolate)this).actor == com.maddox.il2.ai.World.getPlayerAircraft() && !com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[i][j].loadBullets(0);
                        }

            }

        if(!flag)
            bombsOut = true;
    }

    protected void printDebugFM()
    {
        java.lang.System.out.print("<" + ((com.maddox.il2.engine.Interpolate)this).actor.name() + "> " + com.maddox.il2.ai.air.ManString.tname(task) + ":" + com.maddox.il2.ai.air.ManString.name(maneuver) + (target != null ? "T" : "t") + (danger != null ? "D" : "d") + (target_ground != null ? "G " : "g "));
        switch(maneuver)
        {
        case 21: // '\025'
            java.lang.System.out.println(": WP=" + ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur() + "(" + (((com.maddox.il2.fm.FlightModelMain)this).AP.way.size() - 1) + ")-" + com.maddox.il2.ai.air.ManString.wpname(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Action));
            if(target_ground != null)
                java.lang.System.out.println(" GT=" + ((java.lang.Object) (target_ground)).getClass().getName() + "(" + target_ground.name() + ")");
            break;

        case 27: // '\033'
            if(target == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor))
                java.lang.System.out.println(" T=null");
            else
                java.lang.System.out.println(" T=" + ((java.lang.Object) (((com.maddox.il2.engine.Interpolate) (target)).actor)).getClass().getName() + "(" + ((com.maddox.il2.engine.Interpolate) (target)).actor.name() + ")");
            break;

        case 43: // '+'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
            if(target_ground == null || !com.maddox.il2.engine.Actor.isValid(target_ground))
                java.lang.System.out.println(" GT=null");
            else
                java.lang.System.out.println(" GT=" + ((java.lang.Object) (target_ground)).getClass().getName() + "(" + target_ground.name() + ")");
            break;

        default:
            java.lang.System.out.println("");
            if(target == null || !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate) (target)).actor))
            {
                java.lang.System.out.println(" T=null");
                break;
            }
            java.lang.System.out.println(" T=" + ((java.lang.Object) (((com.maddox.il2.engine.Interpolate) (target)).actor)).getClass().getName() + "(" + ((com.maddox.il2.engine.Interpolate) (target)).actor.name() + ")");
            if(target_ground == null || !com.maddox.il2.engine.Actor.isValid(target_ground))
                java.lang.System.out.println(" GT=null");
            else
                java.lang.System.out.println(" GT=" + ((java.lang.Object) (target_ground)).getClass().getName() + "(" + target_ground.name() + ")");
            break;
        }
    }

    protected void headTurn(float f)
    {
        if(((com.maddox.il2.engine.Interpolate)this).actor == com.maddox.il2.game.Main3D.cur3D().viewActor() && ((com.maddox.il2.fm.FlightModelMain)this).AS.astatePilotStates[0] < 90)
        {
            boolean flag = false;
            switch(get_task())
            {
            case 2: // '\002'
                if(((com.maddox.il2.fm.FlightModelMain)this).Leader != null)
                {
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Loc)));
                    flag = true;
                }
                break;

            case 6: // '\006'
                if(target != null)
                {
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (target)).Loc)));
                    flag = true;
                }
                break;

            case 5: // '\005'
                if(airClient != null)
                {
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (airClient)).Loc)));
                    flag = true;
                }
                break;

            case 4: // '\004'
                if(danger != null)
                {
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (danger)).Loc)));
                    flag = true;
                }
                break;

            case 7: // '\007'
                if(target_ground != null)
                {
                    ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (target_ground.pos.getAbsPoint())));
                    flag = true;
                }
                break;
            }
            float f1;
            float f2;
            if(flag)
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
                pilotHeadT += 90F * (pilotHeadT > f1 ? -1F : 1.0F) * f;
            else
                pilotHeadT = f1;
            if(java.lang.Math.abs(pilotHeadY - f2) > 2.0F)
                pilotHeadY += 60F * (pilotHeadY > f2 ? -1F : 1.0F) * f;
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
            ((com.maddox.il2.engine.ActorHMesh)((com.maddox.il2.engine.Interpolate)this).actor).hierMesh().chunkSetLocate("Head1_D0", headPos, headOr);
        }
    }

    protected void turnOffTheWeapon()
    {
        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[0] = false;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[1] = false;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[2] = false;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = false;
        if(bombsOut)
        {
            bombsOutCounter++;
            if(bombsOutCounter > 128)
            {
                bombsOutCounter = 0;
                bombsOut = false;
            }
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.WeaponControl[3] = true;
            else
                bombsOut = false;
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3] != null && ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][0] != null)
            {
                int i = 0;
                for(int j = 0; j < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3].length; j++)
                    i += ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][j].countBullets();

                if(i == 0)
                {
                    bombsOut = false;
                    for(int k = 0; k < ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3].length; k++)
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons[3][k].loadBullets(0);

                }
            }
        }
    }

    protected void turnOnChristmasTree(boolean flag)
    {
        if(flag)
        {
            if(((com.maddox.JGP.Tuple3f) (com.maddox.il2.ai.World.Sun().ToSun)).z < -0.22F)
                ((com.maddox.il2.fm.FlightModelMain)this).AS.setNavLightsState(true);
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AS.setNavLightsState(false);
        }
    }

    protected void turnOnCloudShine(boolean flag)
    {
        if(flag)
        {
            if(((com.maddox.JGP.Tuple3f) (com.maddox.il2.ai.World.Sun().ToSun)).z < -0.22F)
                ((com.maddox.il2.fm.FlightModelMain)this).AS.setLandingLightState(true);
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AS.setLandingLightState(false);
        }
    }

    protected void doCheckGround(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl > 1.0F)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl < -1F)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -1F;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl > 1.0F)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl < -1F)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = -1F;
        float f4 = 0.0003F * ((com.maddox.il2.fm.FlightModelMain)this).M.massEmpty;
        float f5 = 10F;
        float f6 = 80F;
        if(maneuver == 24)
        {
            f5 = 15F;
            f6 = 120F;
        }
        float f7 = (float)(-((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z) * f5 * f4;
        float f8 = 1.0F + 7F * ((((com.maddox.il2.fm.FlightModelMain)this).indSpeed - ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed) / ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed);
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
            f2 = -0.12F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - 5F) + 3F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y;
            f3 = -0.07F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren() + 3F * (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x;
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
            corrCoeff *= ((float) (1.0D - (double)(f9 * f)));
        if(f2 < 0.0F)
        {
            if(corrElev > f2)
                corrElev = f2;
            if(corrElev < f2)
                corrElev *= ((float) (1.0D - (double)(f9 * f)));
        } else
        {
            if(corrElev < f2)
                corrElev = f2;
            if(corrElev > f2)
                corrElev *= ((float) (1.0D - (double)(f9 * f)));
        }
        if(f3 < 0.0F)
        {
            if(corrAile > f3)
                corrAile = f3;
            if(corrAile < f3)
                corrAile *= ((float) (1.0D - (double)(f9 * f)));
        } else
        {
            if(corrAile < f3)
                corrAile = f3;
            if(corrAile > f3)
                corrAile *= ((float) (1.0D - (double)(f9 * f)));
        }
        ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = corrCoeff * corrAile + (1.0F - corrCoeff) * ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl = corrCoeff * corrElev + (1.0F - corrCoeff) * ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl;
        if(Alt < 15F && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z < 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.85000002384185791D;
        if(-((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).z * 1.5D > (double)Alt || Alt < 10F)
        {
            if(maneuver == 27 || maneuver == 43 || maneuver == 21 || maneuver == 24 || maneuver == 68 || maneuver == 53)
                push();
            push(2);
            pop();
            submaneuver = 0;
            sub_Man_Count = 0;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).W.x *= 0.94999999999999996D;
    }

    protected void setSpeedControl(float f)
    {
        float f1 = 0.8F;
        float f4 = 0.02F;
        float f5 = 1.5F;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.setAfterburnerControl(false);
        switch(speedMode)
        {
        case 1: // '\001'
            if(tailForStaying == null)
            {
                f1 = 1.0F;
            } else
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Or.transform(((com.maddox.JGP.Tuple3d) (tailOffset)), ((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                float f14 = (float)((com.maddox.JGP.Tuple3d) (Ve)).z;
                float f6 = 0.005F * (200F - (float)Ve.length());
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                float f10 = (float)((com.maddox.JGP.Tuple3d) (Ve)).x;
                Ve.normalize();
                f6 = java.lang.Math.max(f6, (float)((com.maddox.JGP.Tuple3d) (Ve)).x);
                if(f6 < 0.0F)
                    f6 = 0.0F;
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                Ve.normalize();
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Vwld)));
                tmpV3f.normalize();
                float f8 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Ve)));
                if(f8 < 0.0F)
                    f8 = 0.0F;
                f6 *= f8;
                if(f6 > 0.0F && f10 < 1000F)
                {
                    if(f10 > 300F)
                        f10 = 300F;
                    float f17 = 0.6F;
                    if(((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).VmaxH == ((com.maddox.il2.fm.FlightModelMain)this).VmaxH)
                        f17 = ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).CT.getPower();
                    if(((com.maddox.il2.fm.FlightModelMain)this).Vmax < 83F)
                    {
                        float f19 = f14;
                        if(f19 > 0.0F)
                        {
                            if(f19 > 20F)
                                f19 = 20F;
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z += 0.01F * f19;
                        }
                    }
                    float f12;
                    if(f10 > 0.0F)
                        f12 = 0.007F * f10 + 0.005F * f14;
                    else
                        f12 = 0.03F * f10 + 0.001F * f14;
                    float f20 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - tailForStaying.getSpeed();
                    float f22 = -0.3F * f20;
                    float f25 = -3F * (((com.maddox.il2.fm.FlightModelMain)this).getForwAccel() - ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).getForwAccel());
                    if(f20 > 27F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
                        f1 = 0.0F;
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.02F * f20 + 0.02F * -f10;
                        f1 = f17 + f12 + f22 + f25;
                    }
                } else
                {
                    f1 = 1.1F;
                }
            }
            break;

        case 2: // '\002'
            f1 = (float)(1.0D - 8.0000000000000007E-005D * (0.5D * ((com.maddox.il2.fm.FlightModelMain)this).Vwld.lengthSquared() - 9.8000000000000007D * ((com.maddox.JGP.Tuple3d) (Ve)).z - 0.5D * ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Vwld.lengthSquared()));
            break;

        case 3: // '\003'
            f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl;
            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Speed < 10F)
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().set(1.7F * ((com.maddox.il2.fm.FlightModelMain)this).Vmin);
            float f18 = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Speed / ((com.maddox.il2.fm.FlightModelMain)this).VmaxH;
            f1 = 0.2F + 0.8F * (float)java.lang.Math.pow(f18, 1.5D);
            f1 += 0.1F * (((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().Speed - com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z, ((com.maddox.il2.fm.FlightModel)this).getSpeed())) - 3F * ((com.maddox.il2.fm.FlightModelMain)this).getForwAccel();
            if(((com.maddox.il2.fm.FlightModelMain)this).getAltitude() < ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().z() - 70F)
                f1 += ((com.maddox.il2.fm.FlightModelMain)this).AP.way.curr().z() - 70F - ((com.maddox.il2.fm.FlightModelMain)this).getAltitude();
            if(f1 > 0.93F)
                f1 = 0.93F;
            if(f1 < 0.35F && !((com.maddox.il2.fm.FlightModelMain)this).AP.way.isLanding())
                f1 = 0.35F;
            break;

        case 4: // '\004'
            f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl;
            f1 = (float)((double)f1 + ((double)(f4 * (smConstSpeed - com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z, ((com.maddox.il2.fm.FlightModel)this).getSpeed()))) - ((double)f5 * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getLocalAccel())).x) / 9.8100004196166992D) * (double)f);
            if(f1 > 1.0F)
                f1 = 1.0F;
            break;

        case 5: // '\005'
            f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 1.0F;
            f1 += (f4 * (1.3F * ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS - com.maddox.il2.fm.Pitot.Indicator((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z, ((com.maddox.il2.fm.FlightModel)this).getSpeed())) - f5 * ((com.maddox.il2.fm.FlightModelMain)this).getForwAccel()) * f;
            break;

        case 8: // '\b'
            f1 = 0.0F;
            break;

        case 6: // '\006'
            f1 = 1.0F;
            break;

        case 9: // '\t'
            f1 = 1.1F;
            ((com.maddox.il2.fm.FlightModelMain)this).CT.setAfterburnerControl(true);
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
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Loc)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Or.transform(((com.maddox.JGP.Tuple3d) (tailOffset)), ((com.maddox.JGP.Tuple3d) (tmpV3f)));
                ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (tmpV3f)));
                float f15 = (float)((com.maddox.JGP.Tuple3d) (Ve)).z;
                float f7 = 0.005F * (200F - (float)Ve.length());
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                float f11 = (float)((com.maddox.JGP.Tuple3d) (Ve)).x;
                Ve.normalize();
                f7 = java.lang.Math.max(f7, (float)((com.maddox.JGP.Tuple3d) (Ve)).x);
                if(f7 < 0.0F)
                    f7 = 0.0F;
                ((com.maddox.JGP.Tuple3d) (Ve)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                Ve.normalize();
                ((com.maddox.JGP.Tuple3d) (tmpV3f)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).Vwld)));
                tmpV3f.normalize();
                float f9 = (float)tmpV3f.dot(((com.maddox.JGP.Tuple3d) (Ve)));
                if(f9 < 0.0F)
                    f9 = 0.0F;
                f7 *= f9;
                if(f7 > 0.0F && f11 < 1000F)
                {
                    if(f11 > 300F)
                        f11 = 300F;
                    float f21 = 0.6F;
                    if(((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).VmaxH == ((com.maddox.il2.fm.FlightModelMain)this).VmaxH)
                        f21 = ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).CT.getPower();
                    if(((com.maddox.il2.fm.FlightModelMain)this).Vmax < 83F)
                    {
                        float f23 = f15;
                        if(f23 > 0.0F)
                        {
                            if(f23 > 20F)
                                f23 = 20F;
                            ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z += 0.01F * f23;
                        }
                    }
                    float f13;
                    if(f11 > 0.0F)
                        f13 = 0.007F * f11 + 0.005F * f15;
                    else
                        f13 = 0.03F * f11 + 0.001F * f15;
                    float f24 = ((com.maddox.il2.fm.FlightModel)this).getSpeed() - tailForStaying.getSpeed();
                    float f26 = -0.3F * f24;
                    float f27 = -3F * (((com.maddox.il2.fm.FlightModelMain)this).getForwAccel() - ((com.maddox.il2.fm.FlightModelMain) (tailForStaying)).getForwAccel());
                    if(f24 > 27F)
                    {
                        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).scale(0.98000001907348633D);
                        f1 = 0.0F;
                    } else
                    {
                        float f28 = 1.0F - 0.02F * (0.02F * f24 + 0.02F * -f11);
                        if(f28 < 0.98F)
                            f28 = 0.98F;
                        if(f28 > 1.0F)
                            f28 = 1.0F;
                        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).scale(f28);
                        f1 = f21 + f13 + f26 + f27;
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
        if((double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl - f1) < 0.5D * (double)f)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(f1);
        else
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl < f1)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(((com.maddox.il2.fm.FlightModelMain)this).CT.getPowerControl() + 0.5F * f);
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(((com.maddox.il2.fm.FlightModelMain)this).CT.getPowerControl() - 0.5F * f);
        float f16 = ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getCriticalW();
        if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getw() > 0.9F * f16)
        {
            float f2 = (10F * (f16 - ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getw())) / f16;
            if(f2 < ((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(f2);
        }
        if(((com.maddox.il2.fm.FlightModelMain)this).indSpeed > 0.8F * ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed)
        {
            float f3 = (1.0F * (((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed - ((com.maddox.il2.fm.FlightModelMain)this).indSpeed)) / ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed;
            if(f3 < ((com.maddox.il2.fm.FlightModelMain)this).CT.PowerControl)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.setPowerControl(f3);
        }
    }

    private void setRandomTargDeviation(float f)
    {
        if(((com.maddox.il2.fm.FMMath)this).isTick(16, 0))
        {
            float f1 = f * (8F - 1.5F * (float)((com.maddox.il2.fm.FlightModelMain)this).Skill);
            ((com.maddox.JGP.Tuple3d) (TargDevVnew)).set(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
        }
        ((com.maddox.JGP.Tuple3d) (TargDevV)).interpolate(((com.maddox.JGP.Tuple3d) (TargDevVnew)), 0.01F);
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
            ((com.maddox.JGP.Tuple3d) (Po)).set(((com.maddox.JGP.Tuple3d) (Vtarg)).x + radius * java.lang.Math.sin(phase), ((com.maddox.JGP.Tuple3d) (Vtarg)).y + radius * java.lang.Math.cos(phase), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
            radius += 0.01D * (double)rmax;
            phase += 0.29999999999999999D;
            ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (Po)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            double d = Ve.length();
            ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
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
            if((double)rmax > d && d > (double)(rmin * f2) && ((com.maddox.JGP.Tuple3d) (Ve)).x > (double)f1 && isConvenientPoint())
            {
                submaneuver = 69;
                ((com.maddox.JGP.Tuple3d) (point3d)).set(((com.maddox.JGP.Tuple3d) (Po)));
                pointQuality = curPointQuality;
                return;
            }
        }

    }

    private boolean isConvenientPoint()
    {
        Po.z = com.maddox.il2.engine.Engine.cur.land.HQ_Air(((com.maddox.JGP.Tuple3d) (Po)).x, ((com.maddox.JGP.Tuple3d) (Po)).y);
        curPointQuality = 50;
        for(int i = -1; i < 2; i++)
        {
            for(int j = -1; j < 2; j++)
            {
                com.maddox.il2.engine.Engine _tmp = com.maddox.il2.engine.Engine.cur;
                if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (Po)).x + 500D * (double)i, ((com.maddox.JGP.Tuple3d) (Po)).y + 500D * (double)j))
                {
                    if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                        curPointQuality--;
                } else
                if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                    curPointQuality--;
                if(com.maddox.il2.engine.Engine.cur.land.HQ_ForestHeightHere(((com.maddox.JGP.Tuple3d) (Po)).x + 500D * (double)i, ((com.maddox.JGP.Tuple3d) (Po)).y + 500D * (double)j) > 1.0D)
                    curPointQuality -= 2;
                if(com.maddox.il2.engine.Engine.cur.land.EQN(((com.maddox.JGP.Tuple3d) (Po)).x + 500D * (double)i, ((com.maddox.JGP.Tuple3d) (Po)).y + 500D * (double)j, Ve) > 1110.949951171875D)
                    curPointQuality -= 2;
            }

        }

        if(curPointQuality < 0)
            curPointQuality = 0;
        return curPointQuality > pointQuality;
    }

    private void emergencyTurnToDirection(float f)
    {
        if(java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Ve)).y) > 0.10000000149011612D)
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).z) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        else
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x) - 0.016F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
        ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = -10F * (float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Ve)).y, ((com.maddox.JGP.Tuple3d) (Ve)).x);
        if((double)((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z > 0.0D)
            ((com.maddox.il2.fm.FlightModelMain)this).W.z = 0.0D;
        else
            ((com.maddox.il2.fm.FlightModelMain)this).W.z *= 1.0399999618530273D;
    }

    private void initTargPoint(float f)
    {
        int i = ((com.maddox.il2.fm.FlightModelMain)this).AP.way.Cur();
        ((com.maddox.JGP.Tuple3d) (Vtarg)).set(((com.maddox.il2.fm.FlightModelMain)this).AP.way.last().x(), ((com.maddox.il2.fm.FlightModelMain)this).AP.way.last().y(), 0.0D);
        ((com.maddox.il2.fm.FlightModelMain)this).AP.way.setCur(i);
        ((com.maddox.JGP.Tuple3d) (Vtarg)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        Vtarg.z = 0.0D;
        if(Vtarg.length() > (double)rmax)
        {
            Vtarg.normalize();
            ((com.maddox.JGP.Tuple3d) (Vtarg)).scale(rmax);
        }
        ((com.maddox.JGP.Tuple3d) (Vtarg)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        phase = 0.0D;
        radius = 50D;
        pointQuality = -1;
    }

    private void emergencyLanding(float f)
    {
        if(first)
        {
            Kmax = ((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cya(5F) / ((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cxa(5F);
            if(Kmax > 14F)
                Kmax = 14F;
            Kmax *= 0.8F;
            rmax = 1.2F * Kmax * Alt;
            rmin = 0.6F * Kmax * Alt;
            initTargPoint(f);
            ((com.maddox.il2.fm.FlightModelMain)this).setReadyToDieSoftly(true);
            ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
            if(TaxiMode)
            {
                setSpeedMode(8);
                smConstPower = 0.0F;
                push(44);
                pop();
            }
            dist = Alt;
            if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineStops();
        }
        setSpeedMode(4);
        smConstSpeed = ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 1.25F;
        if(Alt < 500F && ((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane)))
            ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
        if(Alt < 10F)
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            setSpeedMode(4);
            smConstSpeed = ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 1.1F;
            if(Alt < 5F)
                setSpeedMode(8);
            ((com.maddox.il2.fm.FlightModelMain)this).CT.BrakeControl = 0.2F;
            if(((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() < 0.30000001192092896D && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 4)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(true);
                com.maddox.il2.ai.World.cur();
                if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                {
                    push(44);
                    safe_pop();
                    if(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane)
                    {
                        ((com.maddox.il2.fm.FlightModelMain)this).EI.setCurControlAll(true);
                        ((com.maddox.il2.fm.FlightModelMain)this).EI.setEngineStops();
                        if(com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y))
                            return;
                    }
                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).hitDaSilk();
                }
                if(Group != null)
                    Group.delAircraft((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSailPlane))
                    return;
                com.maddox.il2.ai.World.cur();
                if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.World.getPlayerAircraft())
                    if(com.maddox.il2.ai.Airport.distToNearestAirport(((com.maddox.il2.fm.FlightModelMain)this).Loc) > 900D)
                        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).postEndAction(60D, ((com.maddox.il2.engine.Interpolate)this).actor, 4, ((com.maddox.il2.engine.Eff3DActor) (null)));
                    else
                        com.maddox.rts.MsgDestroy.Post(com.maddox.rts.Time.current() + 30000L, ((java.lang.Object) (((com.maddox.il2.engine.Interpolate)this).actor)));
            }
        }
        dA = 0.2F * (((com.maddox.il2.fm.FlightModel)this).getSpeed() - ((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.3F) - 0.8F * (((com.maddox.il2.fm.FlightModelMain)this).getAOA() - 5F);
        if(Alt < 40F)
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.BI_1) || (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.ME_163B1A))
                ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
            float f1;
            if(((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch < 10F)
                f1 = (40F * (((com.maddox.il2.fm.FlightModel)this).getSpeed() - ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 1.15F) - 60F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 3F) - 240F * (((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() + 1.0F) - 120F * ((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAccel())).z - 1.0F)) * 0.004F;
            else
                f1 = (40F * (((com.maddox.il2.fm.FlightModel)this).getSpeed() - ((com.maddox.il2.fm.FlightModelMain)this).VminFLAPS * 1.15F) - 60F * ((((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch) + 10F) - 240F * (((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() + 1.0F) - 120F * ((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAccel())).z - 1.0F)) * 0.004F;
            if(Alt > 0.0F)
            {
                float f2 = 0.01666F * Alt;
                dA = dA * f2 + f1 * (1.0F - f2);
            } else
            {
                dA = f1;
            }
            ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.33F;
            if(Alt < 9F && java.lang.Math.abs(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren()) < 5F && ((com.maddox.il2.fm.FlightModelMain)this).getVertSpeed() < -0.7F)
                ((com.maddox.il2.fm.FlightModelMain)this).Vwld.z *= 0.87000000476837158D;
        } else
        {
            rmax = 1.2F * Kmax * Alt;
            rmin = 0.6F * Kmax * Alt;
            if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGlider) && Alt > 200F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl = 0.0F;
            else
            if(pointQuality < 50 && mn_time > 0.5F)
                findPointForEmLanding(f);
            if(submaneuver == 69)
            {
                ((com.maddox.JGP.Tuple3d) (Ve)).sub(((com.maddox.JGP.Tuple3d) (elLoc.getPoint())), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
                double d = Ve.length();
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Ve)));
                Ve.normalize();
                float f3 = 0.9F - 0.005F * Alt;
                if(f3 < -1F)
                    f3 = -1F;
                if(f3 > 0.8F)
                    f3 = 0.8F;
                if((double)(rmax * 2.0F) < d || d < (double)rmin || ((com.maddox.JGP.Tuple3d) (Ve)).x < (double)f3)
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
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
                }
            } else
            {
                ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl = -0.04F * ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            }
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() > -1F)
                dA -= 0.1F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 1.0F);
            if(((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() < -10F)
                dA -= 0.1F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() + 10F);
        }
        if(Alt < 2.0F || ((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
            dA = -2F * (((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getTangage() - ((com.maddox.il2.fm.FlightModelMain)this).Gears.Pitch);
        double d1 = ((com.maddox.il2.fm.FlightModelMain)this).Vwld.length() / (double)((com.maddox.il2.fm.FlightModelMain)this).Vmin;
        if(d1 > 1.0D)
            d1 = 1.0D;
        ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl += ((float) ((double)((dA - ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl) * 3.33F * f) + 1.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.AIFlightModel)this).getW())).y * d1 + 0.5D * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAW())).y * d1));
    }

    public boolean canITakeoff()
    {
        ((com.maddox.JGP.Tuple3d) (Po)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        ((com.maddox.JGP.Tuple3d) (Vpl)).set(69D, 0.0D, 0.0D);
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (Vpl)));
        ((com.maddox.JGP.Tuple3d) (Po)).add(((com.maddox.JGP.Tuple3d) (Vpl)));
        ((com.maddox.JGP.Tuple3d) (Pd)).set(((com.maddox.JGP.Tuple3d) (Po)));
        if(((com.maddox.il2.engine.Interpolate)this).actor != com.maddox.il2.ai.War.getNearestFriendAtPoint(Pd, (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor, 70F))
            return false;
        if(canTakeoff)
            return true;
        if(com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Actor) (((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport))))
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport.takeoffRequest <= 0)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).AP.way.takeoffAirport.takeoffRequest = 2000;
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

    private void addWindCorrection()
    {
label0:
        {
            if(com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence)
            {
                com.maddox.il2.ai.World.cur();
                if(!com.maddox.il2.ai.World.wind().noWind)
                    break label0;
            }
            return;
        }
        double d = Ve.length();
        com.maddox.il2.ai.World.cur();
        com.maddox.il2.ai.World.wind().getVectorAI(((com.maddox.il2.engine.Interpolate)this).actor.pos.getAbsPoint(), windV);
        ((com.maddox.JGP.Tuple3d) (windV)).scale(-1D);
        Ve.normalize();
        ((com.maddox.JGP.Tuple3d) (Ve)).scale(((com.maddox.il2.fm.FlightModel)this).getSpeed());
        ((com.maddox.JGP.Tuple3d) (Ve)).add(((com.maddox.JGP.Tuple3d) (windV)));
        Ve.normalize();
        ((com.maddox.JGP.Tuple3d) (Ve)).scale(d);
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
    public static final int GATTACK_HS293 = 79;
    public static final int GATTACK_FRITZX = 80;
    public static final int GATTACK_TORPEDO_TOKG = 81;
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
    com.maddox.JGP.Vector3d windV;

}
