// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RealFlightModel.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.F4U;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeGSuit;
import com.maddox.il2.objects.air.TypeSupersonic;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.SoundFX;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Random;

// Referenced classes of package com.maddox.il2.fm:
//            Autopilot, FlightModelMain, AIFlightModel, Autopilotage, 
//            Gear, FMMath, FlightModel, Controls, 
//            Squares, Mass, EnginesInterface, Motor, 
//            AircraftState, Polares, Atmosphere, Wind, 
//            Supersonic, Arm

public class RealFlightModel extends com.maddox.il2.ai.air.Pilot
{

    public RealFlightModel(java.lang.String s)
    {
        super(s);
        RealMode = true;
        indSpeed = 0.0F;
        Cwl = new Vector3d();
        Cwr = new Vector3d();
        Chl = new Vector3d();
        Chr = new Vector3d();
        Cv = new Vector3d();
        Fwl = new Vector3d();
        Fwr = new Vector3d();
        Fhl = new Vector3d();
        Fhr = new Vector3d();
        Fv = new Vector3d();
        superFuel = 10F;
        shakeLevel = 0.0F;
        producedShakeLevel = 0.0F;
        lastAcc = 1.0F;
        ailerInfluence = 1.0F;
        rudderInfluence = 1.0F;
        indiffDnTime = 4F;
        knockDnTime = 0.0F;
        indiffUpTime = 4F;
        knockUpTime = 0.0F;
        saveDeep = 0.0F;
        su26add = 0.0D;
        spinCoeff = 0.0D;
        bSound = true;
        Current_G_Limit = 8F;
        cycleCounter = 0;
        timeCounter = 0.0F;
        gearCutCounter = 0;
        bGearCut = false;
        max_G_Cycle = 1.0F;
        maxSpeed = 0.0F;
        airborneState = 0;
        airborneStartPoint = new Point3d();
        TmpP = new Point3d();
        Vn = new Vector3d();
        TmpV = new Vector3d();
        TmpVd = new Vector3d();
        plAccel = new Vector3d();
        ((com.maddox.il2.fm.FlightModelMain)this).AP = ((com.maddox.il2.fm.Autopilotage) (new Autopilot(((com.maddox.il2.fm.FlightModel) (this)))));
        Realism = com.maddox.il2.ai.World.cur().diffCur;
        maxSpeed = ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed;
    }

    public com.maddox.JGP.Vector3d getW()
    {
        return RealMode ? ((com.maddox.il2.fm.FlightModelMain)this).W : ((com.maddox.il2.fm.AIFlightModel)this).Wtrue;
    }

    private void flutter()
    {
        if(Realism.Flutter_Effect)
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "CF_D0", "CF_D0");
    }

    private void flutterDamage()
    {
        if(Realism.Flutter_Effect)
        {
            java.lang.String s;
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 29))
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
            case 20: // '\024'
                s = "AroneL";
                break;

            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
            case 21: // '\025'
                s = "AroneR";
                break;

            case 8: // '\b'
            case 9: // '\t'
            case 10: // '\n'
            case 22: // '\026'
                s = "VatorL";
                break;

            case 11: // '\013'
            case 12: // '\f'
            case 13: // '\r'
            case 23: // '\027'
                s = "VatorR";
                break;

            case 24: // '\030'
            case 25: // '\031'
            case 26: // '\032'
                s = "Rudder1";
                break;

            case 27: // '\033'
            case 28: // '\034'
            case 29: // '\035'
                s = "Rudder2";
                break;

            case 14: // '\016'
                s = "WingLOut";
                break;

            case 15: // '\017'
                s = "WingROut";
                break;

            case 16: // '\020'
                s = "WingLMid";
                break;

            case 17: // '\021'
                s = "WingRMid";
                break;

            case 18: // '\022'
                s = "WingLIn";
                break;

            case 19: // '\023'
                s = "WingRIn";
                break;

            default:
                s = "CF";
                break;
            }
            s = s + "_D0";
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, s, s);
        }
    }

    private void cutWing()
    {
        if(Realism.Flutter_Effect)
        {
            java.lang.String s;
            switch(com.maddox.il2.ai.World.Rnd().nextInt(0, 8))
            {
            case 0: // '\0'
                s = "Tail1";
                break;

            case 1: // '\001'
            case 2: // '\002'
                s = "WingRMid";
                break;

            case 3: // '\003'
            case 4: // '\004'
                s = "WingLMid";
                break;

            case 5: // '\005'
            case 6: // '\006'
                s = "WingLIn";
                break;

            default:
                s = "WingRIn";
                break;
            }
            s = s + "_D0";
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, s, s);
        }
    }

    private void cutPart(int i)
    {
        if(Realism.Flutter_Effect)
        {
            java.lang.String s;
            switch(i)
            {
            case 0: // '\0'
                s = "WingLOut";
                break;

            case 1: // '\001'
                s = "WingLMid";
                break;

            case 2: // '\002'
                s = "WingLIn";
                break;

            case 3: // '\003'
                s = "WingRIn";
                break;

            case 4: // '\004'
                s = "WingRMid";
                break;

            case 5: // '\005'
                s = "WingROut";
                break;

            case 6: // '\006'
                s = "Tail1";
                break;

            default:
                s = "Tail1";
                break;
            }
            s = s + "_D0";
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, s, s);
        }
    }

    private void dangerEM()
    {
        if((long)com.maddox.rts.Time.tickCounter() < lastDangerTick + 1L)
            return;
        lastDangerTick = com.maddox.rts.Time.tickCounter();
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.War.GetNearestEnemy(((com.maddox.il2.engine.Interpolate)this).actor, -1, 700F);
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        ((com.maddox.JGP.Tuple3d) (TmpVd)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Loc)));
        ((com.maddox.JGP.Tuple3d) (TmpVd)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpVd)));
        TmpVd.normalize();
        if(((com.maddox.JGP.Tuple3d) (TmpVd)).x < 0.97999999999999998D)
            return;
        if(!(((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            return;
        } else
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM;
            pilot.setAsDanger(((com.maddox.il2.engine.Interpolate)this).actor);
            return;
        }
    }

    private void dangerEMAces()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.War.GetNearestEnemy(((com.maddox.il2.engine.Interpolate)this).actor, -1, 300F);
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        ((com.maddox.JGP.Tuple3d) (TmpVd)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Loc)));
        ((com.maddox.JGP.Tuple3d) (TmpVd)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpVd)));
        TmpVd.normalize();
        if(((com.maddox.JGP.Tuple3d) (TmpV)).x < 0.97999999999999998D)
            return;
        if(!(((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            return;
        } else
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM;
            pilot.setAsDanger(((com.maddox.il2.engine.Interpolate)this).actor);
            return;
        }
    }

    private float MulForce(float f)
    {
        if(f < 40F || f > 180F)
            return 1.0F;
        else
            return 1.0F + (70F - java.lang.Math.abs(f - 110F)) * 0.04F;
    }

    public boolean isRealMode()
    {
        return RealMode;
    }

    public void setRealMode(boolean flag)
    {
        if(RealMode == flag)
            return;
        RealMode = flag;
        if(RealMode)
            ((com.maddox.il2.fm.FlightModelMain)this).AP.setStabAll(false);
    }

    private void checkAirborneState()
    {
        if(com.maddox.il2.ai.World.getPlayerFM() != this)
            return;
        if(!com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate)this).actor))
            return;
        switch(airborneState)
        {
        default:
            break;

        case 0: // '\0'
            if((double)((com.maddox.il2.fm.FlightModelMain)this).getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y) > 40D)
            {
                airborneState = 2;
                ((com.maddox.il2.fm.FlightModelMain)this).setWasAirborne(true);
                ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(false);
                com.maddox.il2.ai.EventLog.onAirInflight((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
                if(!com.maddox.il2.game.Mission.hasRadioStations)
                    com.maddox.rts.CmdEnv.top().exec("music RAND music/inflight");
            } else
            {
                airborneState = 1;
                ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(true);
                if(!com.maddox.il2.game.Mission.hasRadioStations)
                    com.maddox.rts.CmdEnv.top().exec("music RAND music/takeoff");
            }
            ((com.maddox.il2.fm.FlightModelMain)this).setCrossCountry(false);
            break;

        case 1: // '\001'
            if(((com.maddox.il2.fm.FlightModelMain)this).Vrel.length() > (double)((com.maddox.il2.fm.FlightModelMain)this).Vmin)
                ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(false);
            if((double)((com.maddox.il2.fm.FlightModelMain)this).getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y) <= 40D || ((com.maddox.il2.fm.FlightModelMain)this).Vrel.length() <= (double)(((com.maddox.il2.fm.FlightModelMain)this).Vmin * 1.15F))
                break;
            airborneState = 2;
            ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(false);
            ((com.maddox.il2.fm.FlightModelMain)this).setNearAirdrome(false);
            ((com.maddox.il2.fm.FlightModelMain)this).setWasAirborne(true);
            ((com.maddox.JGP.Tuple3d) (airborneStartPoint)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)));
            com.maddox.il2.ai.World.cur().scoreCounter.playerTakeoff();
            com.maddox.il2.ai.EventLog.onAirInflight((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
            if(!com.maddox.il2.game.Mission.hasRadioStations)
                com.maddox.rts.CmdEnv.top().exec("music RAND music/inflight");
            break;

        case 2: // '\002'
            if(!((com.maddox.il2.fm.FlightModelMain)this).isCrossCountry() && ((com.maddox.il2.fm.FlightModelMain)this).Loc.distance(airborneStartPoint) > 50000D)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).setCrossCountry(true);
                com.maddox.il2.ai.World.cur().scoreCounter.playerDoCrossCountry();
            }
            if(!((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround || ((com.maddox.il2.fm.FlightModelMain)this).Vrel.length() >= 1.0D)
                break;
            airborneState = 1;
            ((com.maddox.il2.fm.FlightModelMain)this).setStationedOnGround(true);
            if(!com.maddox.il2.game.Mission.hasRadioStations)
                com.maddox.rts.CmdEnv.top().exec("music RAND music/takeoff");
            if(com.maddox.il2.ai.Airport.distToNearestAirport(((com.maddox.il2.fm.FlightModelMain)this).Loc) > 1500D)
            {
                com.maddox.il2.ai.World.cur().scoreCounter.playerLanding(true);
                ((com.maddox.il2.fm.FlightModelMain)this).setNearAirdrome(false);
            } else
            {
                com.maddox.il2.ai.World.cur().scoreCounter.playerLanding(false);
                ((com.maddox.il2.fm.FlightModelMain)this).setNearAirdrome(true);
            }
            break;
        }
        com.maddox.il2.game.Mission.initRadioSounds();
    }

    private void initSound(com.maddox.il2.engine.Actor actor)
    {
        structuralFX = ((com.maddox.il2.engine.Actor) ((com.maddox.il2.objects.air.Aircraft)actor)).newSound("models.structuralFX", false);
        setSound(false);
    }

    private void setSound(boolean flag)
    {
        bSound = flag;
    }

    private boolean getSound()
    {
        return bSound;
    }

    public void update(float f)
    {
        if(((com.maddox.il2.engine.Interpolate)this).actor.isNetMirror())
        {
            ((com.maddox.il2.objects.air.NetAircraft.Mirror)((com.maddox.il2.engine.Interpolate)this).actor.net).fmUpdate(f);
            return;
        }
        if(getSound())
            initSound(((com.maddox.il2.engine.Interpolate)this).actor);
        ((com.maddox.il2.fm.FlightModelMain)this).V2 = (float)((com.maddox.il2.fm.FlightModelMain)this).Vflow.lengthSquared();
        ((com.maddox.il2.fm.FlightModelMain)this).V = (float)java.lang.Math.sqrt(((com.maddox.il2.fm.FlightModelMain)this).V2);
        if(((com.maddox.il2.fm.FlightModelMain)this).V * f > 5F)
        {
            update(f * 0.5F);
            update(f * 0.5F);
            return;
        }
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        if(!RealMode)
        {
            shakeLevel = 0.0F;
            super.update(f);
            if(((com.maddox.il2.fm.FMMath)this).isTick(44, 0))
                checkAirborneState();
            if(com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts)
                calcOverLoad(f, false);
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAM)).set(0.0D, 0.0D, 0.0D);
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAF)).set(0.0D, 0.0D, 0.0D);
            return;
        }
        ((com.maddox.il2.fm.FlightModel)this).moveCarrier();
        ((com.maddox.il2.ai.air.Maneuver)this).decDangerAggressiveness();
        if(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z < -20D)
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).postEndAction(0.0D, ((com.maddox.il2.engine.Interpolate)this).actor, 4, ((com.maddox.il2.engine.Eff3DActor) (null)));
        if(!((com.maddox.il2.fm.FlightModelMain)this).isOk() && ((com.maddox.il2.ai.air.Maneuver)this).Group != null)
            ((com.maddox.il2.ai.air.Maneuver)this).Group.delAircraft((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.air.Maneuver.showFM && ((com.maddox.il2.engine.Interpolate)this).actor == com.maddox.il2.game.Main3D.cur3D().viewActor())
        {
            float f6 = (((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x / (((com.maddox.il2.fm.FlightModelMain)this).CT.getAileron() * 111.111F * ((com.maddox.il2.fm.FlightModelMain)this).SensRoll)) * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) / 0.8F;
            if(java.lang.Math.abs(f6) > 50F)
                f6 = 0.0F;
            float f8 = (((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y / (-((com.maddox.il2.fm.FlightModelMain)this).CT.getElevator() * 111.111F * ((com.maddox.il2.fm.FlightModelMain)this).SensPitch)) * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) / 0.27F;
            if(java.lang.Math.abs(f8) > 50F)
                f8 = 0.0F;
            float f10 = (((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z / ((((com.maddox.il2.fm.FlightModelMain)this).AOS - ((com.maddox.il2.fm.FlightModelMain)this).CT.getRudder() * 12F) * 111.111F * ((com.maddox.il2.fm.FlightModelMain)this).SensYaw)) * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) / 0.15F;
            if(java.lang.Math.abs(f10) > 50F)
                f10 = 0.0F;
            com.maddox.il2.engine.TextScr.output(5, 60, "~S RUDDR = " + (float)(int)(f10 * 100F) / 100F);
            com.maddox.il2.engine.TextScr.output(5, 80, "~S VATOR = " + (float)(int)(f8 * 100F) / 100F);
            com.maddox.il2.engine.TextScr.output(5, 100, "~S AERON = " + (float)(int)(f6 * 100F) / 100F);
            java.lang.String s = "";
            for(int i = 0; (float)i < shakeLevel * 10.5F; i++)
                s = s + ">";

            com.maddox.il2.engine.TextScr.output(5, 120, "SHAKE LVL -" + shakeLevel);
            com.maddox.il2.engine.TextScr.output(5, 670, "Pylon = " + ((com.maddox.il2.fm.FlightModelMain)this).M.pylonCoeff);
            com.maddox.il2.engine.TextScr.output(5, 640, "WIND = " + (float)(int)(((com.maddox.il2.fm.FlightModelMain)this).Vwind.length() * 10D) / 10F + " " + (float)(int)(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwind)).z * 10D) / 10F + " m/s");
            com.maddox.il2.engine.TextScr.output(5, 140, "BRAKE = " + ((com.maddox.il2.fm.FlightModelMain)this).CT.getBrake());
            int j = 0;
            com.maddox.il2.engine.TextScr.output(225, 140, "---ENGINES (" + ((com.maddox.il2.fm.FlightModelMain)this).EI.getNum() + ")---" + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getStage());
            com.maddox.il2.engine.TextScr.output(245, 120, "THTL " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlThrottle()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 100, "PROP " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlProp()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).CT.getStepControlAuto() ? " (AUTO)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 80, "MIX " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlMix()) + "%");
            com.maddox.il2.engine.TextScr.output(245, 60, "RAD " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlRadiator()) + "%" + (((com.maddox.il2.fm.FlightModelMain)this).CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 40, "SUPC " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getControlCompressor() + "x");
            com.maddox.il2.engine.TextScr.output(245, 20, "PropAoA :" + (int)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getPropAoA()));
            com.maddox.il2.engine.TextScr.output(245, 0, "PropPhi :" + (int)java.lang.Math.toDegrees(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getPropPhi()));
            com.maddox.il2.engine.TextScr.output(455, 120, "Cyls/Cams " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getCylinders());
            com.maddox.il2.engine.TextScr.output(455, 100, "Readyness " + (int)(100F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getReadyness()) + "%");
            com.maddox.il2.engine.TextScr.output(455, 80, "PRM " + (int)((float)(int)(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getRPM() * 0.02F) * 50F) + " rpm");
            com.maddox.il2.engine.TextScr.output(455, 60, "Thrust " + (int)((com.maddox.JGP.Tuple3f) (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getEngineForce())).x + " N");
            com.maddox.il2.engine.TextScr.output(455, 40, "Fuel " + (int)((100F * ((com.maddox.il2.fm.FlightModelMain)this).M.fuel) / ((com.maddox.il2.fm.FlightModelMain)this).M.maxFuel) + "% Nitro " + (int)((100F * ((com.maddox.il2.fm.FlightModelMain)this).M.nitro) / ((com.maddox.il2.fm.FlightModelMain)this).M.maxNitro) + "%");
            com.maddox.il2.engine.TextScr.output(455, 20, "MPrs " + (int)(1000F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].getManifoldPressure()) + " mBar");
            com.maddox.il2.engine.TextScr.output(640, 140, "---Controls---");
            com.maddox.il2.engine.TextScr.output(640, 120, "A/C: " + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasAileronControl ? "" : "AIL ") + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasElevatorControl ? "" : "ELEV ") + (((com.maddox.il2.fm.FlightModelMain)this).CT.bHasRudderControl ? "" : "RUD ") + (((com.maddox.il2.fm.FlightModelMain)this).Gears.bIsHydroOperable ? "" : "GEAR "));
            com.maddox.il2.engine.TextScr.output(640, 100, "ENG: " + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlThrottle() ? "" : "THTL ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlProp() ? "" : "PROP ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlMix() ? "" : "MIX ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (((com.maddox.il2.fm.FlightModelMain)this).EI.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
            com.maddox.il2.engine.TextScr.output(640, 80, "PIL: (" + (int)(((com.maddox.il2.fm.FlightModelMain)this).AS.getPilotHealth(0) * 100F) + "%)");
            com.maddox.il2.engine.TextScr.output(640, 60, "Sens: " + ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity);
            com.maddox.il2.engine.TextScr.output(400, 500, "+");
            com.maddox.il2.engine.TextScr.output(400, 400, "|");
            com.maddox.il2.engine.TextScr.output((int)(400F + 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.AileronControl), (int)(500F - 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.ElevatorControl), "+");
            com.maddox.il2.engine.TextScr.output((int)(400F + 200F * ((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl), 400, "|");
            com.maddox.il2.engine.TextScr.output(5, 200, "AOA = " + ((com.maddox.il2.fm.FlightModelMain)this).AOA);
            com.maddox.il2.engine.TextScr.output(5, 220, "Mass = " + ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass());
            com.maddox.il2.engine.TextScr.output(5, 320, "AERON TR = " + ((com.maddox.il2.fm.FlightModelMain)this).CT.trimAileron);
            com.maddox.il2.engine.TextScr.output(5, 300, "VATOR TR = " + ((com.maddox.il2.fm.FlightModelMain)this).CT.trimElevator);
            com.maddox.il2.engine.TextScr.output(5, 280, "RUDDR TR = " + ((com.maddox.il2.fm.FlightModelMain)this).CT.trimRudder);
            com.maddox.il2.engine.TextScr.output(245, 160, " pF = " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].zatizeni * 100D + "%/hr");
            hpOld = hpOld * 0.95F + (0.05F * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].w * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].engineMoment) / 746F;
            com.maddox.il2.engine.TextScr.output(245, 180, " hp = " + hpOld);
            com.maddox.il2.engine.TextScr.output(245, 200, " eMoment = " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].engineMoment);
            com.maddox.il2.engine.TextScr.output(245, 220, " pMoment = " + ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].propMoment);
        }
        if(!Realism.Limited_Fuel)
            superFuel = ((com.maddox.il2.fm.FlightModelMain)this).M.fuel = java.lang.Math.max(superFuel, ((com.maddox.il2.fm.FlightModelMain)this).M.fuel);
        ((com.maddox.il2.fm.FlightModelMain)this).AP.update(f);
        ((com.maddox.il2.objects.air.NetAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).netUpdateWayPoint();
        ((com.maddox.il2.fm.FlightModelMain)this).CT.update(f, (float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x, ((com.maddox.il2.fm.FlightModelMain)this).EI, true);
        float f7 = (float)(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x) / 11000F;
        if(f7 > 1.0F)
            f7 = 1.0F;
        com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringGain(f7);
        if(((com.maddox.il2.fm.FlightModelMain)this).CT.saveWeaponControl[0] || ((com.maddox.il2.fm.FlightModelMain)this).CT.saveWeaponControl[1] || ((com.maddox.il2.fm.FlightModelMain)this).CT.saveWeaponControl[2])
            dangerEM();
        ((com.maddox.il2.fm.FlightModelMain)this).Wing.setFlaps(((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap());
        ((com.maddox.il2.fm.FlightModel)this).FMupdate(f);
        ((com.maddox.il2.fm.FlightModelMain)this).EI.update(f);
        ((com.maddox.il2.fm.FlightModelMain)this).Gravity = ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass() * com.maddox.il2.fm.Atmosphere.g();
        ((com.maddox.il2.fm.FlightModelMain)this).M.computeFullJ(((com.maddox.il2.fm.FlightModelMain)this).J, ((com.maddox.il2.fm.FlightModelMain)this).J0);
        if(Realism.G_Limits)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).G_ClassCoeff < 0.0F || !((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeBomber))
                Current_G_Limit = ((com.maddox.il2.fm.FlightModelMain)this).ReferenceForce / ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass() - ((com.maddox.il2.fm.FlightModelMain)this).M.pylonCoeff;
            else
                Current_G_Limit = ((com.maddox.il2.fm.FlightModelMain)this).ReferenceForce / ((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass();
            ((com.maddox.il2.fm.FlightModelMain)this).setLimitLoad(Current_G_Limit);
        }
        if(((com.maddox.il2.fm.FMMath)this).isTick(44, 0))
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AS.update(f * 44F);
            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).rareAction(f * 44F, true);
            ((com.maddox.il2.fm.FlightModelMain)this).M.computeParasiteMass(((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons);
            ((com.maddox.il2.fm.FlightModelMain)this).Sq.computeParasiteDrag(((com.maddox.il2.fm.FlightModelMain)this).CT, ((com.maddox.il2.fm.FlightModelMain)this).CT.Weapons);
            checkAirborneState();
            ((com.maddox.il2.fm.FlightModel)this).putScareShpere();
            dangerEMAces();
            if(((com.maddox.il2.fm.FlightModel)this).turnOffCollisions && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround && (double)((com.maddox.il2.fm.FlightModelMain)this).getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y) > 30D)
                ((com.maddox.il2.fm.FlightModel)this).turnOffCollisions = false;
        }
        ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).wrap();
        if(Realism.Wind_N_Turbulence)
            com.maddox.il2.ai.World.wind().getVector(((com.maddox.il2.fm.FlightModelMain)this).Loc, ((com.maddox.il2.fm.FlightModelMain)this).Vwind);
        else
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwind)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vair)).sub(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwind)));
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vair)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        ((com.maddox.il2.fm.AIFlightModel)this).Density = com.maddox.il2.fm.Atmosphere.density((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
        ((com.maddox.il2.fm.FlightModelMain)this).AOA = com.maddox.il2.fm.FMMath.RAD2DEG(-(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).z, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x));
        ((com.maddox.il2.fm.FlightModelMain)this).AOS = com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).y, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x));
        indSpeed = ((com.maddox.il2.fm.FlightModel)this).getSpeed() * (float)java.lang.Math.sqrt(((com.maddox.il2.fm.AIFlightModel)this).Density / 1.225F);
        ((com.maddox.il2.fm.FlightModelMain)this).Mach = ((com.maddox.il2.fm.FlightModelMain)this).V / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z);
        float fDragFactor = 1.0F;
        float fDragParasiteFactor = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).Ss.allParamsSet)
        {
            float fMachDrag = ((com.maddox.il2.fm.FlightModelMain)this).Ss.getDragFactorForMach(((com.maddox.il2.fm.FlightModelMain)this).Mach);
            fDragFactor = (float)java.lang.Math.sqrt(fMachDrag);
            fDragParasiteFactor = (float)java.lang.Math.pow(fMachDrag, 5D);
        } else
        if(((com.maddox.il2.fm.FlightModelMain)this).Ss.getDragFactorForMach(((com.maddox.il2.fm.FlightModelMain)this).Mach) <= 1.0F);
        if(((com.maddox.il2.fm.FlightModelMain)this).Mach > 0.8F)
            ((com.maddox.il2.fm.FlightModelMain)this).Mach = 0.8F;
        ((com.maddox.il2.fm.AIFlightModel)this).Kq = 1.0F / (float)java.lang.Math.sqrt(1.0F - ((com.maddox.il2.fm.FlightModelMain)this).Mach * ((com.maddox.il2.fm.FlightModelMain)this).Mach);
        ((com.maddox.il2.fm.FlightModelMain)this).q_ = ((com.maddox.il2.fm.AIFlightModel)this).Density * ((com.maddox.il2.fm.FlightModelMain)this).V2 * 0.5F;
        double d1 = ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).z - ((com.maddox.il2.fm.FlightModelMain)this).Gears.screenHQ;
        if(d1 < 0.0D)
            d1 = 0.0D;
        float f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.getAileron() * 14F;
        f1 = ((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_V * (float)java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(((com.maddox.il2.fm.FlightModelMain)this).AOS)) + ((com.maddox.il2.fm.FlightModelMain)this).SensRoll * ailerInfluence * (1.0F - 0.1F * ((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap()) * f1;
        double d2 = 0.0D;
        double d4 = 0.0D;
        if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getType() < 2)
        {
            d2 = ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].addVflow;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = 0.5D * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].addVside;
        }
        ((com.maddox.JGP.Tuple3d) (Vn)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.GCENTER, 0.84999999999999998D * (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END, -0.5D);
        Vn.cross(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)), ((com.maddox.JGP.Tuple3d) (Vn)));
        ((com.maddox.JGP.Tuple3d) (Vn)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        float f12 = f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x));
        Vn.x += 0.070000000000000007D * d2;
        double d = Vn.lengthSquared();
        d *= 0.5F * ((com.maddox.il2.fm.AIFlightModel)this).Density;
        f7 = f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z + 0.070000000000000007D * d4 * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x));
        float f14 = 0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwl.x = -d * (double)(((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cx(f7) + f14 + ((com.maddox.il2.fm.FlightModelMain)this).GearCX * ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() + ((com.maddox.il2.fm.FlightModelMain)this).radiatorCX * (((com.maddox.il2.fm.FlightModelMain)this).EI.getRadiatorPos() + ((com.maddox.il2.fm.FlightModelMain)this).CT.getCockpitDoor()) + ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragAirbrakeCx * ((com.maddox.il2.fm.FlightModelMain)this).CT.getAirBrake());
        Cwl.z = d * (double)((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cy(f7) * (double)((com.maddox.il2.fm.AIFlightModel)this).Kq;
        if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType != 0)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 1)
                Cwl.z *= com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain)this).fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 2)
            {
                Cwl.z = 0.0D;
                if(com.maddox.rts.Time.current() >= ((com.maddox.il2.fm.FlightModelMain)this).fmsfxTimeDisable)
                    ((com.maddox.il2.fm.FlightModelMain)this).doRequestFMSFX(0, 0);
            }
        }
        ((com.maddox.JGP.Tuple3d) (Vn)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.GCENTER, -((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_END, -0.5D);
        Vn.cross(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)), ((com.maddox.JGP.Tuple3d) (Vn)));
        ((com.maddox.JGP.Tuple3d) (Vn)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        float f13 = -f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x));
        Vn.x += 0.070000000000000007D * d2;
        d = Vn.lengthSquared();
        d *= 0.5F * ((com.maddox.il2.fm.AIFlightModel)this).Density;
        float f9 = -f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z - 0.070000000000000007D * d4 * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x));
        f14 = -0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwr.x = -d * (double)(((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cx(f9) + f14 + ((com.maddox.il2.fm.FlightModelMain)this).GearCX * ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() + ((com.maddox.il2.fm.FlightModelMain)this).radiatorCX * ((com.maddox.il2.fm.FlightModelMain)this).EI.getRadiatorPos() + ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragAirbrakeCx * ((com.maddox.il2.fm.FlightModelMain)this).CT.getAirBrake());
        Cwr.z = d * (double)((com.maddox.il2.fm.FlightModelMain)this).Wing.new_Cy(f9) * (double)((com.maddox.il2.fm.AIFlightModel)this).Kq;
        if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType != 0)
        {
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 1)
                Cwr.z *= com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain)this).fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(((com.maddox.il2.fm.FlightModelMain)this).fmsfxCurrentType == 3)
            {
                Cwr.z = 0.0D;
                if(com.maddox.rts.Time.current() >= ((com.maddox.il2.fm.FlightModelMain)this).fmsfxTimeDisable)
                    ((com.maddox.il2.fm.FlightModelMain)this).doRequestFMSFX(0, 0);
            }
        }
        Cwl.y = -d * (double)((com.maddox.il2.fm.FlightModelMain)this).Fusel.new_Cy(((com.maddox.il2.fm.FlightModelMain)this).AOS);
        Cwl.x -= d * (double)((com.maddox.il2.fm.FlightModelMain)this).Fusel.new_Cx(((com.maddox.il2.fm.FlightModelMain)this).AOS);
        Cwr.y = -d * (double)((com.maddox.il2.fm.FlightModelMain)this).Fusel.new_Cy(((com.maddox.il2.fm.FlightModelMain)this).AOS);
        Cwr.x -= d * (double)((com.maddox.il2.fm.FlightModelMain)this).Fusel.new_Cx(((com.maddox.il2.fm.FlightModelMain)this).AOS);
        float f15 = ((com.maddox.il2.fm.FlightModelMain)this).Wing.get_AOA_CRYT();
        double d7 = 1.0D;
        double d8 = 0.5D + 0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPowerOutput();
        double d9 = 1.2D + 0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPowerOutput();
        if(spinCoeff < d8)
            spinCoeff = d8;
        if(spinCoeff > d9)
            spinCoeff = d9;
        f7 = f12;
        f9 = f13;
        if(!Realism.Stalls_N_Spins || ((com.maddox.il2.fm.FlightModelMain)this).Gears.isUnderDeck())
        {
            if(f7 > f9)
            {
                if(((com.maddox.JGP.Tuple3d) (Cwl)).z < ((com.maddox.JGP.Tuple3d) (Cwr)).z)
                {
                    double d5 = ((com.maddox.JGP.Tuple3d) (Cwl)).z;
                    Cwl.z = ((com.maddox.JGP.Tuple3d) (Cwr)).z;
                    Cwr.z = d5;
                }
            } else
            if(((com.maddox.JGP.Tuple3d) (Cwl)).z > ((com.maddox.JGP.Tuple3d) (Cwr)).z)
            {
                double d6 = ((com.maddox.JGP.Tuple3d) (Cwl)).z;
                Cwl.z = ((com.maddox.JGP.Tuple3d) (Cwr)).z;
                Cwr.z = d6;
            }
        } else
        if(f7 > f15 || f9 > f15)
        {
            spinCoeff += 0.20000000000000001D * (double)f;
            if((double)((com.maddox.il2.fm.FlightModelMain)this).Sq.squareRudders > 0.0D && (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl) > 0.5D && (double)((com.maddox.il2.fm.FlightModelMain)this).CT.RudderControl * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z > 0.0D)
                spinCoeff -= 0.29999999999999999D * (double)f;
            float f16;
            if(f7 > f9)
                f16 = f7;
            else
                f16 = f9;
            ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff = 0.8F * (f16 - f15);
            if(((com.maddox.il2.fm.FlightModelMain)this).turbCoeff < 1.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff = 1.0F;
            if(((com.maddox.il2.fm.FlightModelMain)this).turbCoeff > 15F)
                ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff = 15F;
            d7 = 1.0D - 0.20000000000000001D * (double)(f16 - f15);
            if(d7 < 0.20000000000000001D)
                d7 = 0.20000000000000001D;
            d7 /= ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff;
            double d12 = d * (double)((com.maddox.il2.fm.FlightModelMain)this).turbCoeff * spinCoeff;
            float f17 = ((com.maddox.il2.fm.FlightModelMain)this).getAltitude() - (float)com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).x, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).y);
            if(f17 < 10F)
                d12 *= 0.1F * f17;
            if(f7 > f9)
            {
                Cwr.x += 0.019999999552965164D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCxloss;
                Cwl.x -= 0.25D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCxloss;
                Cwr.z += 0.019999999552965164D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCyloss;
                Cwl.z -= 0.10000000149011612D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCyloss;
            } else
            {
                Cwl.x += 0.019999999552965164D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCxloss;
                Cwr.x -= 0.25D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCxloss;
                Cwl.z += 0.019999999552965164D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCyloss;
                Cwr.z -= 0.10000000149011612D * d12 * (double)((com.maddox.il2.fm.FlightModelMain)this).Sq.spinCyloss;
            }
            rudderInfluence = 1.0F + 0.035F * ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff;
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain)this).turbCoeff = 1.0F;
            d7 = 1.0D;
            spinCoeff -= 0.20000000000000001D * (double)f;
            ailerInfluence = 1.0F;
            rudderInfluence = 1.0F;
        }
        if(((com.maddox.il2.fm.FMMath)this).isTick(15, 0))
            if(java.lang.Math.abs(f7 - f9) > 5F)
                com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringZero((f9 - f7) * 0.04F, 0.0F);
            else
                com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
        if(d1 < 0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length)
        {
            double d10 = 1.0D - d1 / (0.40000000000000002D * (double)((com.maddox.il2.fm.FlightModelMain)this).Length);
            double d13 = 1.0D + 0.20000000000000001D * d10;
            double d16 = 1.0D + 0.20000000000000001D * d10;
            Cwl.z *= d13;
            Cwl.x *= d16;
            Cwr.z *= d13;
            Cwr.x *= d16;
        }
        f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.getElevator() * (((com.maddox.il2.fm.FlightModelMain)this).CT.getElevator() > 0.0F ? 28F : 20F);
        ((com.maddox.JGP.Tuple3d) (Vn)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.VER_STAB, 0.0D, 0.0D);
        Vn.cross(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)), ((com.maddox.JGP.Tuple3d) (Vn)));
        ((com.maddox.JGP.Tuple3d) (Vn)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        double d11 = java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Vn)).y * ((com.maddox.JGP.Tuple3d) (Vn)).y + ((com.maddox.JGP.Tuple3d) (Vn)).z * ((com.maddox.JGP.Tuple3d) (Vn)).z);
        d2 = 0.0D;
        d4 = 0.0D;
        if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getType() < 2)
        {
            double d14 = 1.0D + 0.040000000000000001D * (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.RUDDER;
            d14 = 1.0D / (d14 * d14);
            double d17 = ((com.maddox.JGP.Tuple3d) (Vn)).x + d14 * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].addVflow;
            if(d17 < 0.20000000000000001D)
                d17 = 0.20000000000000001D;
            double d19 = 1.0D - (1.5D * d11) / d17;
            if(d19 < 0.0D)
                d19 = 0.0D;
            double d3 = d19 * d14 * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].addVflow;
            Vn.x += d3;
            double d21 = java.lang.Math.min(0.0011000000000000001D * ((com.maddox.JGP.Tuple3d) (Vn)).x * ((com.maddox.JGP.Tuple3d) (Vn)).x, 1.0D);
            if(((com.maddox.JGP.Tuple3d) (Vn)).x < 0.0D)
                d21 = 0.0D;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = d19 * d21 * ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].addVside;
        }
        double d15 = (double)((com.maddox.il2.fm.AIFlightModel)this).Density * Vn.lengthSquared() * 0.5D;
        if(((com.maddox.il2.fm.FlightModelMain)this).EI.getNum() == 1 && ((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getType() < 2)
        {
            f7 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z - 0.35999999999999999D * d4 * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * ((com.maddox.il2.fm.FlightModelMain)this).V - ((com.maddox.il2.fm.FlightModelMain)this).SensPitch * f1;
            f9 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z + 0.35999999999999999D * d4 * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * ((com.maddox.il2.fm.FlightModelMain)this).V - ((com.maddox.il2.fm.FlightModelMain)this).SensPitch * f1;
        } else
        {
            f7 = f9 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * ((com.maddox.il2.fm.FlightModelMain)this).V - ((com.maddox.il2.fm.FlightModelMain)this).SensPitch * f1;
        }
        Chl.x = -d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cx(f7);
        Chl.z = d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cy(f7);
        Chr.x = -d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cx(f9);
        Chr.z = d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cy(f9);
        Chl.y = Chr.y = 0.0D;
        f1 = ((com.maddox.il2.fm.FlightModelMain)this).CT.getRudder() * (((com.maddox.il2.fm.FlightModelMain)this).Sq.squareRudders < 0.05F ? 0.0F : 28F);
        float f11;
        if(((com.maddox.il2.fm.FlightModelMain)this).EI.engines[0].getType() < 2)
            f11 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).y - 0.5D * d4 * (double)((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) + ((com.maddox.il2.fm.FlightModelMain)this).SensYaw * rudderInfluence * f1;
        else
            f11 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).y, ((com.maddox.JGP.Tuple3d) (Vn)).x)) + ((com.maddox.il2.fm.FlightModelMain)this).SensYaw * rudderInfluence * f1;
        Cv.x = -d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cx(f11);
        Cv.y = d15 * (double)((com.maddox.il2.fm.FlightModelMain)this).Tail.new_Cy(f11);
        Cv.z = 0.0D;
        if(!Realism.Stalls_N_Spins)
            Cv.y += ((com.maddox.JGP.Tuple3d) (Cv)).y;
        ((com.maddox.JGP.Tuple3d) (Vn)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)));
        d = (double)((com.maddox.il2.fm.AIFlightModel)this).Density * Vn.lengthSquared() * 0.5D;
        ((com.maddox.JGP.Tuple3d) (Fwl)).scale(((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut, ((com.maddox.JGP.Tuple3d) (Cwl)));
        ((com.maddox.JGP.Tuple3d) (Fwr)).scale(((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRIn + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingRMid + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut, ((com.maddox.JGP.Tuple3d) (Cwr)));
        Fwl.x -= d * (double)(((com.maddox.il2.fm.FlightModelMain)this).Sq.dragParasiteCx * fDragParasiteFactor + ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragProducedCx) * 0.5D;
        Fwr.x -= d * (double)(((com.maddox.il2.fm.FlightModelMain)this).Sq.dragParasiteCx * fDragParasiteFactor + ((com.maddox.il2.fm.FlightModelMain)this).Sq.dragProducedCx) * 0.5D;
        ((com.maddox.JGP.Tuple3d) (Fhl)).scale((((com.maddox.il2.fm.FlightModelMain)this).Sq.liftStab + ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareElevators) * 0.5F, ((com.maddox.JGP.Tuple3d) (Chl)));
        ((com.maddox.JGP.Tuple3d) (Fhr)).scale((((com.maddox.il2.fm.FlightModelMain)this).Sq.liftStab + ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareElevators) * 0.5F, ((com.maddox.JGP.Tuple3d) (Chr)));
        ((com.maddox.JGP.Tuple3d) (Fv)).scale(0.2F + ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftKeel * 1.5F + ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareRudders, ((com.maddox.JGP.Tuple3d) (Cv)));
        Fwl.x *= fDragFactor;
        Fwr.x *= fDragFactor;
        Fhl.x *= fDragFactor;
        Fhr.x *= fDragFactor;
        Fv.x *= fDragFactor;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).set(((com.maddox.JGP.Tuple3d) (Fwl)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).add(((com.maddox.JGP.Tuple3d) (Fwr)));
        if(com.maddox.il2.fm.FMMath.isNAN(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF))))
        {
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).set(0.0D, 0.0D, 0.0D);
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("AF isNAN");
        } else
        if(((com.maddox.il2.fm.FlightModelMain)this).AF.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 50F))
        {
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("A > 50.0");
            ((com.maddox.il2.fm.FlightModelMain)this).AF.normalize();
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).scale(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 50F);
        } else
        {
            if(Realism.G_Limits)
            {
                if((((com.maddox.il2.fm.FlightModelMain)this).getOverload() > ((com.maddox.il2.fm.FlightModelMain)this).getUltimateLoad() + com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) || ((com.maddox.il2.fm.FlightModelMain)this).getOverload() < ((com.maddox.il2.fm.FlightModelMain)this).Negative_G_Limit - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.5F)) && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                    if(((com.maddox.il2.fm.FlightModelMain)this).cutPart < 0)
                        cutWing();
                    else
                        cutPart(((com.maddox.il2.fm.FlightModelMain)this).cutPart);
                if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > Current_G_Limit || ((com.maddox.il2.fm.FlightModelMain)this).getOverload() < ((com.maddox.il2.fm.FlightModelMain)this).Negative_G_Limit)
                {
                    float f19 = java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).getOverload());
                    if(f19 > max_G_Cycle)
                        max_G_Cycle = f19;
                    timeCounter += f;
                    if(timeCounter > 0.75F)
                    {
                        cycleCounter++;
                        if(cycleCounter > 1)
                        {
                            float f18;
                            if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 1.0F)
                                f18 = (max_G_Cycle - Current_G_Limit) / Current_G_Limit;
                            else
                                f18 = (max_G_Cycle + ((com.maddox.il2.fm.FlightModelMain)this).Negative_G_Limit) / ((com.maddox.il2.fm.FlightModelMain)this).Negative_G_Limit;
                            f18 *= f18;
                            ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(f18);
                            if(structuralFX != null)
                                ((com.maddox.sound.AudioStream) (structuralFX)).play();
                            ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed = maxSpeed * (((com.maddox.il2.fm.FlightModelMain)this).getSafetyFactor() * 0.3F + 0.55F);
                            rD = ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat();
                            if(rD < 0.001F)
                            {
                                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                                    gearCutCounter++;
                                }
                                ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 6F * rD;
                                ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(250F * rD);
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                            } else
                            if(rD < 0.002F)
                            {
                                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 2;
                                }
                                ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 3F * rD;
                                ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(125F * rD);
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0025F)
                            {
                                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.forceGear(1.0F);
                                    gearCutCounter += 2;
                                }
                                ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 3F * rD;
                                ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(125F * rD);
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                            } else
                            if(rD < 0.003F)
                            {
                                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.forceGear(1.0F);
                                    gearCutCounter++;
                                }
                                ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 3F * rD;
                                ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(125F * rD);
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0035F)
                            {
                                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.dvGear = 1.0F;
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.forceGear(1.0F);
                                    ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 3;
                                }
                                ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 8F * rD;
                                ((com.maddox.il2.fm.FlightModelMain)this).setSafetyFactor(125F * rD);
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                            } else
                            if(rD < 0.04F)
                                ((com.maddox.il2.fm.FlightModelMain)this).SensYaw *= 0.68F;
                            else
                            if(rD < 0.05F)
                                ((com.maddox.il2.fm.FlightModelMain)this).SensPitch *= 0.68F;
                            else
                            if(rD < 0.06F)
                                ((com.maddox.il2.fm.FlightModelMain)this).SensRoll *= 0.68F;
                            else
                            if(rD < 0.061F)
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.dropFuelTanks();
                            else
                            if(rD < 0.065F)
                                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasFlapsControl = false;
                            else
                            if(rD >= 0.5F)
                                if(rD < 0.6F)
                                    ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 0.011F * rD;
                                else
                                if((int)((com.maddox.il2.fm.FlightModelMain)this).M.getFullMass() % 2 == 0)
                                {
                                    ((java.lang.Object) (((com.maddox.il2.fm.FlightModelMain)this).Sq)).getClass();
                                    ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingROut *= 0.95F - 0.2F * rD;
                                    ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 0.011F * rD;
                                } else
                                {
                                    ((java.lang.Object) (((com.maddox.il2.fm.FlightModelMain)this).Sq)).getClass();
                                    ((com.maddox.il2.fm.FlightModelMain)this).Sq.liftWingLOut *= 0.95F - 0.2F * rD;
                                    ((com.maddox.il2.fm.FlightModelMain)this).Wing.CxMin_0 += 0.011F * rD;
                                }
                        }
                        timeCounter = 0.0F;
                        max_G_Cycle = 1.0F;
                    }
                } else
                {
                    timeCounter = 0.0F;
                    max_G_Cycle = 1.0F;
                }
            } else
            if((((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && ((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 15F && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                cutWing();
            else
            if(((com.maddox.il2.fm.FlightModelMain)this).getOverload() > 13.5F && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                cutWing();
            if(indSpeed > 112.5F && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98 && ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() > 0.3F && ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl == 1.0F)
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() >= 0.1F && ((com.maddox.il2.fm.FlightModelMain)this).CT.GearControl != 0.0F && !bGearCut)
                    if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.F4U) && !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    } else
                    if(indSpeed > 180F && !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    }
                if(indSpeed > 350F && !(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearR2_D0", "GearR2_D0");
                        gearCutCounter++;
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "GearL2_D0", "GearL2_D0");
                        gearCutCounter += 2;
                    }
                }
                if(gearCutCounter > 2)
                {
                    bGearCut = true;
                    ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl = false;
                }
            }
            if(indSpeed > 60.5F && ((com.maddox.il2.fm.FlightModelMain)this).CT.getWing() > 0.1F)
            {
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 90 && ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).isChunkAnyDamageVisible("WingLMid"))
                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "WingLMid_D0", "WingLMid_D0");
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 90 && ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).isChunkAnyDamageVisible("WingRMid"))
                    ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor).msgCollision(((com.maddox.il2.engine.Interpolate)this).actor, "WingRMid_D0", "WingRMid_D0");
            }
            if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && indSpeed > 81F && ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasFlapsControl && ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl > 0.21F && (indSpeed - 81F) * ((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap() > 8F)
            {
                if(com.maddox.il2.ai.World.getPlayerAircraft() == ((com.maddox.il2.engine.Interpolate)this).actor && ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasFlapsControl)
                    com.maddox.il2.game.HUD.log("FailedFlaps");
                ((com.maddox.il2.fm.FlightModelMain)this).CT.bHasFlapsControl = false;
                ((com.maddox.il2.fm.FlightModelMain)this).CT.FlapsControl = 0.0F;
            }
            if(indSpeed > ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 16F) < indSpeed - ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 2)
                flutterDamage();
            if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
            {
                if(indSpeed > 610F)
                {
                    if(com.maddox.il2.ai.World.cur().isDebugFM())
                        java.lang.System.out.println("*** Sonic overspeed....");
                    flutter();
                }
            } else
            if(!(((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && indSpeed > 310F)
            {
                if(com.maddox.il2.ai.World.cur().isDebugFM())
                    java.lang.System.out.println("*** Sonic overspeed....");
                flutter();
            }
        }
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).set(0.0D, 0.0D, 0.0D);
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA) < 12F)
        {
            float f2 = ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).getKren();
            if(f2 > 30F)
                f2 = 30F;
            else
            if(f2 < -30F)
                f2 = -30F;
            f2 = (float)((double)f2 * (java.lang.Math.min(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x - 50D, 50D) * 0.0030000000260770321D));
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(-f2 * 0.01F * ((com.maddox.il2.fm.FlightModelMain)this).Gravity, 0.0D, 0.0D);
        }
        if(!((com.maddox.il2.fm.FlightModelMain)this).getOp(19))
        {
            ((com.maddox.il2.fm.FlightModelMain)this).AM.y += (double)(8F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x;
            ((com.maddox.il2.fm.FlightModelMain)this).AM.z += 200F * ((com.maddox.il2.fm.FlightModelMain)this).Sq.squareWing * ((com.maddox.il2.fm.FlightModelMain)this).EI.getPropDirSign();
        }
        double d18 = (double)((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap() * 3D;
        if(d18 > 1.0D)
            d18 = 1.0D;
        double d20 = 0.0111D * (double)java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA);
        if(((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritL < ((com.maddox.il2.fm.FlightModelMain)this).AOA && ((com.maddox.il2.fm.FlightModelMain)this).AOA < ((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritH)
            d20 = 0.0D;
        else
        if(((com.maddox.il2.fm.FlightModelMain)this).AOA >= ((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritH)
            d20 = java.lang.Math.min(d20, 0.29999999999999999D * (double)(((com.maddox.il2.fm.FlightModelMain)this).AOA - ((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritH));
        else
        if(((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritL <= ((com.maddox.il2.fm.FlightModelMain)this).AOA)
            d20 = java.lang.Math.min(d20, 0.29999999999999999D * (double)(((com.maddox.il2.fm.FlightModelMain)this).Wing.AOACritL - ((com.maddox.il2.fm.FlightModelMain)this).AOA));
        double d22 = (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.GCENTER + (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.GC_FLAPS_SHIFT * d18 * (1.0D - d20) + (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.GC_AOA_SHIFT * d20;
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(-d22, (double)((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE * (1.3D + 1.0D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(((com.maddox.il2.fm.FlightModelMain)this).AOS))), -((com.maddox.il2.fm.FlightModelMain)this).Arms.GCENTER_Z);
        TmpV.cross(((com.maddox.JGP.Tuple3d) (TmpV)), ((com.maddox.JGP.Tuple3d) (Fwl)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(-d22, (double)(-((com.maddox.il2.fm.FlightModelMain)this).Arms.WING_MIDDLE) * (1.3D - 1.0D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(((com.maddox.il2.fm.FlightModelMain)this).AOS))), -((com.maddox.il2.fm.FlightModelMain)this).Arms.GCENTER_Z);
        TmpV.cross(((com.maddox.JGP.Tuple3d) (TmpV)), ((com.maddox.JGP.Tuple3d) (Fwr)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.il2.fm.FlightModelMain)this).AM.x += su26add;
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.HOR_STAB, 1.0D, 0.0D);
        TmpV.cross(((com.maddox.JGP.Tuple3d) (TmpV)), ((com.maddox.JGP.Tuple3d) (Fhl)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.HOR_STAB, -1D, 0.0D);
        TmpV.cross(((com.maddox.JGP.Tuple3d) (TmpV)), ((com.maddox.JGP.Tuple3d) (Fhr)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(-((com.maddox.il2.fm.FlightModelMain)this).Arms.VER_STAB, 0.0D, 1.0D);
        TmpV.cross(((com.maddox.JGP.Tuple3d) (TmpV)), ((com.maddox.JGP.Tuple3d) (Fv)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        double d23 = 1.0D - 1.0000000000000001E-005D * (double)indSpeed;
        if(d23 < 0.80000000000000004D)
            d23 = 0.80000000000000004D;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).scale(d23);
        if(!Realism.Stalls_N_Spins)
            ((com.maddox.il2.fm.FlightModelMain)this).AM.y += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).z * 0.5D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA)));
        if(((com.maddox.il2.fm.FlightModelMain)this).W.lengthSquared() > 25D)
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).scale(5D / ((com.maddox.il2.fm.FlightModelMain)this).W.length());
        if(!Realism.Stalls_N_Spins && ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vflow)).x > 20D)
            ((com.maddox.il2.fm.FlightModelMain)this).W.z += ((com.maddox.il2.fm.FlightModelMain)this).AOS * f;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAM)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAF)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).producedAM)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).EI.producedF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).EI.producedM)));
        if(com.maddox.il2.ai.World.cur().diffCur.Torque_N_Gyro_Effects)
        {
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).EI.getGyro())));
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).scale(d7);
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)));
        }
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).set(0.0D, 0.0D, 0.0D);
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).set(0.0D, 0.0D, 0.0D);
        if(com.maddox.rts.Time.tickCounter() % 2 != 0)
            ((com.maddox.il2.fm.FlightModelMain)this).Gears.roughness = ((com.maddox.il2.fm.FlightModelMain)this).Gears.plateFriction(((com.maddox.il2.fm.FlightModel) (this)));
        ((com.maddox.il2.fm.FlightModelMain)this).Gears.ground(((com.maddox.il2.fm.FlightModel) (this)), true);
        int k = 5;
        if(((com.maddox.il2.fm.FlightModelMain)this).GF.lengthSquared() == 0.0D && ((com.maddox.il2.fm.FlightModelMain)this).GM.lengthSquared() == 0.0D)
            k = 1;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).ACmeter)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).ACmeter)).scale(1.0F / ((com.maddox.il2.fm.FlightModelMain)this).Gravity);
        ((com.maddox.JGP.Tuple3d) (TmpV)).set(0.0D, 0.0D, -((com.maddox.il2.fm.FlightModelMain)this).Gravity);
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)));
        double d24 = 1.0D / (double)((com.maddox.il2.fm.FlightModelMain)this).M.mass;
        ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).LocalAccel)).scale(d24, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).getRollAcceleration()) > 50000.5F)
        {
            com.maddox.il2.objects.effects.ForceFeedback.fxPunch(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).x > 0.0D ? 0.9F : -0.9F, 0.0F, 1.0F);
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Punched (Axial = " + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).x + ")");
        }
        if(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).getOverload() - lastAcc) > 0.5F)
        {
            com.maddox.il2.objects.effects.ForceFeedback.fxPunch(com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F), -0.9F, ((com.maddox.il2.fm.FlightModel)this).getSpeed() * 0.05F);
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Punched (Lat = " + java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).getOverload() - lastAcc) + ")");
        }
        lastAcc = ((com.maddox.il2.fm.FlightModelMain)this).getOverload();
        if(com.maddox.il2.fm.FMMath.isNAN(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM))))
        {
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).set(0.0D, 0.0D, 0.0D);
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("AM isNAN");
        } else
        if(((com.maddox.il2.fm.FlightModelMain)this).AM.length() > (double)(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 150F))
        {
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("SummM > 150g");
            ((com.maddox.il2.fm.FlightModelMain)this).AM.normalize();
            ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)).scale(((com.maddox.il2.fm.FlightModelMain)this).Gravity * 150F);
        }
        ((com.maddox.il2.fm.FlightModel)this).dryFriction -= 0.01D;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.gearsChanged)
            ((com.maddox.il2.fm.FlightModel)this).dryFriction = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfPoiOnGr > 0)
            ((com.maddox.il2.fm.FlightModel)this).dryFriction += 0.02F;
        if(((com.maddox.il2.fm.FlightModel)this).dryFriction < 1.0F)
            ((com.maddox.il2.fm.FlightModel)this).dryFriction = 1.0F;
        if(((com.maddox.il2.fm.FlightModel)this).dryFriction > 32F)
            ((com.maddox.il2.fm.FlightModel)this).dryFriction = 32F;
        float f20 = 4F * (0.25F - ((com.maddox.il2.fm.FlightModelMain)this).EI.getPowerOutput());
        if(f20 < 0.0F)
            f20 = 0.0F;
        f20 *= f20;
        f20 *= ((com.maddox.il2.fm.FlightModel)this).dryFriction;
        float f21 = f20 * ((com.maddox.il2.fm.FlightModelMain)this).M.mass * ((com.maddox.il2.fm.FlightModelMain)this).M.mass;
        if(!((com.maddox.il2.fm.FlightModel)this).brakeShoe && (((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfPoiOnGr == 0 && ((com.maddox.il2.fm.FlightModelMain)this).Gears.nOfGearsOnGr < 3 || f20 == 0.0F || ((com.maddox.il2.fm.FlightModelMain)this).SummM.lengthSquared() > (double)(2.0F * f21) || ((com.maddox.il2.fm.FlightModelMain)this).SummF.lengthSquared() > (double)(80F * f21) || ((com.maddox.il2.fm.FlightModelMain)this).W.lengthSquared() > (double)(0.00014F * f20) || ((com.maddox.il2.fm.FlightModelMain)this).Vwld.lengthSquared() > (double)(0.09F * f20)))
        {
            double d25 = 1.0D / (double)k;
            for(int l = 0; l < k; l++)
            {
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AF)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).add(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AM)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)));
                ((com.maddox.il2.fm.FlightModelMain)this).AW.x = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).x) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x;
                ((com.maddox.il2.fm.FlightModelMain)this).AW.y = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).z * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).y) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y;
                ((com.maddox.il2.fm.FlightModelMain)this).AW.z = ((((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).x - ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).y) * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).x * ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).y + ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummM)).z) / ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).J)).z;
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d25 * (double)f, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).AW)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)), ((com.maddox.JGP.Tuple3d) (Vn)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d25 * (double)f, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)));
                ((com.maddox.il2.engine.Orient) (((com.maddox.il2.fm.FlightModelMain)this).Or)).increment((float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).z)), (float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).y)), (float)com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).x));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (Vn)), ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).W)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d24, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).SummF)));
                ((com.maddox.il2.fm.FlightModelMain)this).Or.transform(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Accel)).set(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d25 * (double)f);
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (TmpV)).scale(d25 * (double)f, ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Vwld)));
                ((com.maddox.JGP.Tuple3d) (TmpP)).set(((com.maddox.JGP.Tuple3d) (TmpV)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).Loc)).add(((com.maddox.JGP.Tuple3d) (TmpP)));
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).set(0.0D, 0.0D, 0.0D);
                ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GM)).set(0.0D, 0.0D, 0.0D);
                if(l < k - 1)
                {
                    ((com.maddox.il2.fm.FlightModelMain)this).Gears.ground(((com.maddox.il2.fm.FlightModel) (this)), true);
                    ((com.maddox.JGP.Tuple3d) (TmpV)).set(0.0D, 0.0D, -((com.maddox.il2.fm.FlightModelMain)this).Gravity);
                    ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (TmpV)));
                    ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).GF)).add(((com.maddox.JGP.Tuple3d) (TmpV)));
                }
            }

            for(int i1 = 0; i1 < 3; i1++)
            {
                ((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[i1] = (((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[i1] + (float)java.lang.Math.toDegrees(java.lang.Math.atan((((com.maddox.il2.fm.FlightModelMain)this).Gears.gVelocity[i1] * (double)f) / 0.375D))) % 360F;
                ((com.maddox.il2.fm.FlightModelMain)this).Gears.gVelocity[i1] *= 0.94999998807907104D;
            }

            ((com.maddox.il2.fm.FlightModel)this).HM.chunkSetAngles("GearL1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[0], 0.0F);
            ((com.maddox.il2.fm.FlightModel)this).HM.chunkSetAngles("GearR1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[1], 0.0F);
            ((com.maddox.il2.fm.FlightModel)this).HM.chunkSetAngles("GearC1_D0", 0.0F, -((com.maddox.il2.fm.FlightModelMain)this).Gears.gWheelAngles[2], 0.0F);
        }
        if(((com.maddox.il2.fm.FlightModelMain)this).Leader != null && ((com.maddox.il2.fm.FMMath)this).isTick(128, 97) && com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor) && !((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround)
        {
            float f22 = (float)((com.maddox.il2.fm.FlightModelMain)this).Loc.distance(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).Loc);
            if(f22 > 3000F)
                com.maddox.il2.objects.sounds.Voice.speakDeviateBig((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor);
            else
            if(f22 > 1700F)
                com.maddox.il2.objects.sounds.Voice.speakDeviateSmall((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.fm.FlightModelMain)this).Leader)).actor);
        }
        shakeLevel = 0.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround())
        {
            shakeLevel += ((float) ((30D * ((com.maddox.il2.fm.FlightModelMain)this).Gears.roughness * ((com.maddox.il2.fm.FlightModelMain)this).Vrel.length()) / (double)((com.maddox.il2.fm.FlightModelMain)this).M.mass));
        } else
        {
            if(indSpeed > 10F)
            {
                float f23 = (float)java.lang.Math.sin(java.lang.Math.toRadians(java.lang.Math.abs(((com.maddox.il2.fm.FlightModelMain)this).AOA)));
                if(f23 > 0.02F)
                {
                    f23 *= f23;
                    shakeLevel += 0.07F * (f23 - 0.0004F) * (indSpeed - 10F);
                    if(((com.maddox.il2.fm.FMMath)this).isTick(30, 0) && shakeLevel > 0.6F)
                        com.maddox.il2.game.HUD.log(stallStringID, "Stall");
                }
            }
            if(indSpeed > 35F)
            {
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.bHasGearControl && (((com.maddox.il2.fm.FlightModelMain)this).Gears.lgear || ((com.maddox.il2.fm.FlightModelMain)this).Gears.rgear) && ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() > 0.0F)
                    shakeLevel += 0.004F * ((com.maddox.il2.fm.FlightModelMain)this).CT.getGear() * (indSpeed - 35F);
                if(((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap() > 0.0F)
                    shakeLevel += 0.004F * ((com.maddox.il2.fm.FlightModelMain)this).CT.getFlap() * (indSpeed - 35F);
            }
        }
        if(indSpeed > ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed * 0.8F)
            shakeLevel = 0.01F * (indSpeed - ((com.maddox.il2.fm.FlightModelMain)this).VmaxAllowed * 0.8F);
        if(com.maddox.il2.ai.World.cur().diffCur.Head_Shake)
        {
            shakeLevel += producedShakeLevel;
            producedShakeLevel *= 0.9F;
        }
        if(shakeLevel > 1.0F)
            shakeLevel = 1.0F;
        com.maddox.il2.objects.effects.ForceFeedback.fxShake(shakeLevel);
        if(com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts)
            calcOverLoad(f, true);
    }

    private void calcOverLoad(float f, boolean flag)
    {
        com.maddox.il2.objects.air.TypeGSuit.GFactors theGFactors = new com.maddox.il2.objects.air.TypeGSuit.GFactors();
        float fGPosLimit = 2.3F;
        float fGNegLimit = 2.3F;
        if((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGSuit)
            ((com.maddox.il2.objects.air.TypeGSuit)((com.maddox.il2.engine.Interpolate)this).actor).getGFactors(theGFactors);
        fGPosLimit *= theGFactors.getPosGToleranceFactor();
        fGNegLimit *= theGFactors.getNegGToleranceFactor();
        if(f > 1.0F)
            f = 1.0F;
        if(((com.maddox.il2.fm.FlightModelMain)this).Gears.onGround() || !flag)
        {
            ((com.maddox.JGP.Tuple3d) (plAccel)).set(0.0D, 0.0D, 0.0D);
        } else
        {
            ((com.maddox.JGP.Tuple3d) (plAccel)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain)this).getAccel())));
            ((com.maddox.JGP.Tuple3d) (plAccel)).scale(0.10199999809265137D);
        }
        plAccel.z += 0.5D;
        ((com.maddox.il2.fm.FlightModelMain)this).Or.transformInv(((com.maddox.JGP.Tuple3d) (plAccel)));
        float f1 = -0.5F + (float)((com.maddox.JGP.Tuple3d) (plAccel)).z;
        deep = 0.0F;
        if(f1 < -0.6F)
            deep = f1 + 0.6F;
        if(f1 > 2.2F)
            deep = f1 - 2.2F;
        if(knockDnTime > 0.0F)
            knockDnTime -= f * theGFactors.getPosGRecoveryFactor();
        if(knockUpTime > 0.0F)
            knockUpTime -= f * theGFactors.getNegGRecoveryFactor();
        if(indiffDnTime < 4F * theGFactors.getPosGTimeFactor())
            indiffDnTime += f * theGFactors.getPosGRecoveryFactor();
        if(indiffUpTime < 4F * theGFactors.getNegGTimeFactor())
            indiffUpTime += 0.3F * f * theGFactors.getNegGRecoveryFactor();
        java.text.DecimalFormat twoPlaces = new DecimalFormat("+000.00;-000.00");
        if(deep > 0.0F)
        {
            if(indiffDnTime > 0.0F)
                indiffDnTime -= 0.8F * deep * f;
            if(deep > fGPosLimit && knockDnTime < 18.4F)
                knockDnTime += 0.75F * deep * f;
            if(indiffDnTime > 0.1F)
            {
                currDeep = 0.0F;
            } else
            {
                currDeep = deep * 0.08F * 3.5F;
                if(currDeep > 0.8F)
                    currDeep = 0.8F;
            }
        } else
        if(deep < 0.0F)
        {
            deep = -deep;
            if(deep < 0.84F)
                deep = 0.84F;
            if(indiffUpTime > 0.0F)
                indiffUpTime -= 1.2F * deep * f;
            if(deep > fGNegLimit && knockUpTime < 16.1F)
                knockUpTime += deep * f;
            if(indiffUpTime > 0.1F)
                currDeep = 0.0F;
            else
                currDeep = deep * 0.42F * 0.88F;
            currDeep = -currDeep;
        } else
        {
            currDeep = 0.0F;
        }
        if(knockUpTime > 10.81F)
            currDeep = -0.88F;
        if(knockDnTime > 14.03F)
            currDeep = 3.5F;
        if(currDeep > 3.5F)
            currDeep = 3.5F;
        if(currDeep < -0.88F)
            currDeep = -0.88F;
        if(saveDeep > 0.8F)
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity = 1.0F - (saveDeep - 0.8F);
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity < 0.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity = 0.0F;
        } else
        if(saveDeep < -0.4F)
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity = 1.0F + (saveDeep + 0.4F);
            if(((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity < 0.0F)
                ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity = 0.0F;
        } else
        {
            ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity = 1.0F;
        }
        ((com.maddox.il2.fm.FlightModelMain)this).CT.Sensitivity *= ((com.maddox.il2.fm.FlightModelMain)this).AS.getPilotHealth(0);
        if(saveDeep < currDeep)
        {
            saveDeep += 0.3F * f;
            if(saveDeep > currDeep)
                saveDeep = currDeep;
        } else
        {
            saveDeep -= 0.2F * f;
            if(saveDeep < currDeep)
                saveDeep = currDeep;
        }
    }

    public void gunMomentum(com.maddox.JGP.Vector3d vector3d, boolean flag)
    {
        ((com.maddox.il2.fm.FlightModelMain)this).producedAM.x += ((com.maddox.JGP.Tuple3d) (vector3d)).x;
        ((com.maddox.il2.fm.FlightModelMain)this).producedAM.y += ((com.maddox.JGP.Tuple3d) (vector3d)).y;
        ((com.maddox.il2.fm.FlightModelMain)this).producedAM.z += ((com.maddox.JGP.Tuple3d) (vector3d)).z;
        float f = (float)vector3d.length() * 3.5E-005F;
        if(flag && f > 0.5F)
            f *= 0.05F;
        if(producedShakeLevel < f)
            producedShakeLevel = f;
    }

    public boolean RealMode;
    public float indSpeed;
    private static int stallStringID = com.maddox.il2.game.HUD.makeIdLog();
    public com.maddox.il2.ai.DifficultySettings Realism;
    com.maddox.JGP.Vector3d Cwl;
    com.maddox.JGP.Vector3d Cwr;
    com.maddox.JGP.Vector3d Chl;
    com.maddox.JGP.Vector3d Chr;
    com.maddox.JGP.Vector3d Cv;
    com.maddox.JGP.Vector3d Fwl;
    com.maddox.JGP.Vector3d Fwr;
    com.maddox.JGP.Vector3d Fhl;
    com.maddox.JGP.Vector3d Fhr;
    com.maddox.JGP.Vector3d Fv;
    private float superFuel;
    private long lastDangerTick;
    public float shakeLevel;
    public float producedShakeLevel;
    private float lastAcc;
    private float ailerInfluence;
    private float rudderInfluence;
    private float oldTime;
    private float deep;
    private float currDeep;
    private float indiffDnTime;
    private float knockDnTime;
    private float indiffUpTime;
    private float knockUpTime;
    private final float MAX_DN_OL = 3.5F;
    private final float MAX_UP_OL = 0.88F;
    public float saveDeep;
    private double su26add;
    private double spinCoeff;
    private com.maddox.sound.SoundFX structuralFX;
    private boolean bSound;
    private float rD;
    public float Current_G_Limit;
    private int cycleCounter;
    private float timeCounter;
    private int gearCutCounter;
    private boolean bGearCut;
    private float max_G_Cycle;
    private float maxSpeed;
    private float hpOld;
    private int airborneState;
    private com.maddox.JGP.Point3d airborneStartPoint;
    private com.maddox.JGP.Point3d TmpP;
    private com.maddox.JGP.Vector3d Vn;
    private com.maddox.JGP.Vector3d TmpV;
    private com.maddox.JGP.Vector3d TmpVd;
    private com.maddox.JGP.Vector3d plAccel;

}
