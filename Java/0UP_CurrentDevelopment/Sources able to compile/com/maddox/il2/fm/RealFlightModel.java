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
        super.AP = new Autopilot(this);
        Realism = com.maddox.il2.ai.World.cur().diffCur;
        maxSpeed = super.VmaxAllowed;
    }

    public com.maddox.JGP.Vector3d getW()
    {
        return RealMode ? super.W : super.Wtrue;
    }

    private void flutter()
    {
        if(Realism.Flutter_Effect)
            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "CF_D0", "CF_D0");
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
            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, s, s);
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
            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, s, s);
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
            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, s, s);
        }
    }

    private void dangerEM()
    {
        if((long)com.maddox.rts.Time.tickCounter() < lastDangerTick + 1L)
            return;
        lastDangerTick = com.maddox.rts.Time.tickCounter();
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.War.GetNearestEnemy(super.actor, -1, 700F);
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        TmpVd.set(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Loc);
        TmpVd.sub(super.Loc);
        super.Or.transformInv(TmpVd);
        TmpVd.normalize();
        if(((com.maddox.JGP.Tuple3d) (TmpVd)).x < 0.97999999999999998D)
            return;
        if(!(((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            return;
        } else
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM;
            pilot.setAsDanger(super.actor);
            return;
        }
    }

    private void dangerEMAces()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.ai.War.GetNearestEnemy(super.actor, -1, 300F);
        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft))
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
        TmpVd.set(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Loc);
        TmpVd.sub(super.Loc);
        super.Or.transformInv(TmpVd);
        TmpVd.normalize();
        if(((com.maddox.JGP.Tuple3d) (TmpV)).x < 0.97999999999999998D)
            return;
        if(!(((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            return;
        } else
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM;
            pilot.setAsDanger(super.actor);
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
            super.AP.setStabAll(false);
    }

    private void checkAirborneState()
    {
        if(com.maddox.il2.ai.World.getPlayerFM() != this)
            return;
        if(!com.maddox.il2.engine.Actor.isAlive(super.actor))
            return;
        switch(airborneState)
        {
        default:
            break;

        case 0: // '\0'
            if((double)getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (super.Loc)).x, ((com.maddox.JGP.Tuple3d) (super.Loc)).y) > 40D)
            {
                airborneState = 2;
                setWasAirborne(true);
                setStationedOnGround(false);
                com.maddox.il2.ai.EventLog.onAirInflight((com.maddox.il2.objects.air.Aircraft)super.actor);
                if(!com.maddox.il2.game.Mission.hasRadioStations)
                    com.maddox.rts.CmdEnv.top().exec("music RAND music/inflight");
            } else
            {
                airborneState = 1;
                setStationedOnGround(true);
                if(!com.maddox.il2.game.Mission.hasRadioStations)
                    com.maddox.rts.CmdEnv.top().exec("music RAND music/takeoff");
            }
            setCrossCountry(false);
            break;

        case 1: // '\001'
            if(super.Vrel.length() > (double)super.Vmin)
                setStationedOnGround(false);
            if((double)getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (super.Loc)).x, ((com.maddox.JGP.Tuple3d) (super.Loc)).y) <= 40D || super.Vrel.length() <= (double)(super.Vmin * 1.15F))
                break;
            airborneState = 2;
            setStationedOnGround(false);
            setNearAirdrome(false);
            setWasAirborne(true);
            airborneStartPoint.set(super.Loc);
            com.maddox.il2.ai.World.cur().scoreCounter.playerTakeoff();
            com.maddox.il2.ai.EventLog.onAirInflight((com.maddox.il2.objects.air.Aircraft)super.actor);
            if(!com.maddox.il2.game.Mission.hasRadioStations)
                com.maddox.rts.CmdEnv.top().exec("music RAND music/inflight");
            break;

        case 2: // '\002'
            if(!isCrossCountry() && super.Loc.distance(airborneStartPoint) > 50000D)
            {
                setCrossCountry(true);
                com.maddox.il2.ai.World.cur().scoreCounter.playerDoCrossCountry();
            }
            if(!super.Gears.onGround || super.Vrel.length() >= 1.0D)
                break;
            airborneState = 1;
            setStationedOnGround(true);
            if(!com.maddox.il2.game.Mission.hasRadioStations)
                com.maddox.rts.CmdEnv.top().exec("music RAND music/takeoff");
            if(com.maddox.il2.ai.Airport.distToNearestAirport(super.Loc) > 1500D)
            {
                com.maddox.il2.ai.World.cur().scoreCounter.playerLanding(true);
                setNearAirdrome(false);
            } else
            {
                com.maddox.il2.ai.World.cur().scoreCounter.playerLanding(false);
                setNearAirdrome(true);
            }
            break;
        }
        com.maddox.il2.game.Mission.initRadioSounds();
    }

    private void initSound(com.maddox.il2.engine.Actor actor)
    {
        structuralFX = ((com.maddox.il2.objects.air.Aircraft)actor).newSound("models.structuralFX", false);
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
        if(super.actor.isNetMirror())
        {
            ((com.maddox.il2.objects.air.NetAircraft.Mirror)super.actor.net).fmUpdate(f);
            return;
        }
        if(getSound())
            initSound(super.actor);
        super.V2 = (float)super.Vflow.lengthSquared();
        super.V = (float)java.lang.Math.sqrt(super.V2);
        if(super.V * f > 5F)
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
            if(isTick(44, 0))
                checkAirborneState();
            if(com.maddox.il2.ai.World.cur().diffCur.Blackouts_N_Redouts)
                calcOverLoad(f, false);
            super.producedAM.set(0.0D, 0.0D, 0.0D);
            super.producedAF.set(0.0D, 0.0D, 0.0D);
            return;
        }
        moveCarrier();
        decDangerAggressiveness();
        if(((com.maddox.JGP.Tuple3d) (super.Loc)).z < -20D)
            ((com.maddox.il2.objects.air.Aircraft)super.actor).postEndAction(0.0D, super.actor, 4, null);
        if(!isOk() && super.Group != null)
            super.Group.delAircraft((com.maddox.il2.objects.air.Aircraft)super.actor);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.air.Maneuver.showFM && super.actor == com.maddox.il2.game.Main3D.cur3D().viewActor())
        {
            float f6 = (((float)((com.maddox.JGP.Tuple3d) (super.W)).x / (super.CT.getAileron() * 111.111F * super.SensRoll)) * super.Sq.squareWing) / 0.8F;
            if(java.lang.Math.abs(f6) > 50F)
                f6 = 0.0F;
            float f8 = (((float)((com.maddox.JGP.Tuple3d) (super.W)).y / (-super.CT.getElevator() * 111.111F * super.SensPitch)) * super.Sq.squareWing) / 0.27F;
            if(java.lang.Math.abs(f8) > 50F)
                f8 = 0.0F;
            float f10 = (((float)((com.maddox.JGP.Tuple3d) (super.W)).z / ((super.AOS - super.CT.getRudder() * 12F) * 111.111F * super.SensYaw)) * super.Sq.squareWing) / 0.15F;
            if(java.lang.Math.abs(f10) > 50F)
                f10 = 0.0F;
            com.maddox.il2.engine.TextScr.output(5, 60, "~S RUDDR = " + (float)(int)(f10 * 100F) / 100F);
            com.maddox.il2.engine.TextScr.output(5, 80, "~S VATOR = " + (float)(int)(f8 * 100F) / 100F);
            com.maddox.il2.engine.TextScr.output(5, 100, "~S AERON = " + (float)(int)(f6 * 100F) / 100F);
            java.lang.String s = "";
            for(int i = 0; (float)i < shakeLevel * 10.5F; i++)
                s = s + ">";

            com.maddox.il2.engine.TextScr.output(5, 120, "SHAKE LVL -" + shakeLevel);
            com.maddox.il2.engine.TextScr.output(5, 670, "Pylon = " + super.M.pylonCoeff);
            com.maddox.il2.engine.TextScr.output(5, 640, "WIND = " + (float)(int)(super.Vwind.length() * 10D) / 10F + " " + (float)(int)(((com.maddox.JGP.Tuple3d) (super.Vwind)).z * 10D) / 10F + " m/s");
            com.maddox.il2.engine.TextScr.output(5, 140, "BRAKE = " + super.CT.getBrake());
            int j = 0;
            com.maddox.il2.engine.TextScr.output(225, 140, "---ENGINES (" + super.EI.getNum() + ")---" + super.EI.engines[j].getStage());
            com.maddox.il2.engine.TextScr.output(245, 120, "THTL " + (int)(100F * super.EI.engines[j].getControlThrottle()) + "%" + (super.EI.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 100, "PROP " + (int)(100F * super.EI.engines[j].getControlProp()) + "%" + (super.CT.getStepControlAuto() ? " (AUTO)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 80, "MIX " + (int)(100F * super.EI.engines[j].getControlMix()) + "%");
            com.maddox.il2.engine.TextScr.output(245, 60, "RAD " + (int)(100F * super.EI.engines[j].getControlRadiator()) + "%" + (super.CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
            com.maddox.il2.engine.TextScr.output(245, 40, "SUPC " + super.EI.engines[j].getControlCompressor() + "x");
            com.maddox.il2.engine.TextScr.output(245, 20, "PropAoA :" + (int)java.lang.Math.toDegrees(super.EI.engines[j].getPropAoA()));
            com.maddox.il2.engine.TextScr.output(245, 0, "PropPhi :" + (int)java.lang.Math.toDegrees(super.EI.engines[j].getPropPhi()));
            com.maddox.il2.engine.TextScr.output(455, 120, "Cyls/Cams " + super.EI.engines[j].getCylindersOperable() + "/" + super.EI.engines[j].getCylinders());
            com.maddox.il2.engine.TextScr.output(455, 100, "Readyness " + (int)(100F * super.EI.engines[j].getReadyness()) + "%");
            com.maddox.il2.engine.TextScr.output(455, 80, "PRM " + (int)((float)(int)(super.EI.engines[j].getRPM() * 0.02F) * 50F) + " rpm");
            com.maddox.il2.engine.TextScr.output(455, 60, "Thrust " + (int)((com.maddox.JGP.Tuple3f) (super.EI.engines[j].getEngineForce())).x + " N");
            com.maddox.il2.engine.TextScr.output(455, 40, "Fuel " + (int)((100F * super.M.fuel) / super.M.maxFuel) + "% Nitro " + (int)((100F * super.M.nitro) / super.M.maxNitro) + "%");
            com.maddox.il2.engine.TextScr.output(455, 20, "MPrs " + (int)(1000F * super.EI.engines[j].getManifoldPressure()) + " mBar");
            com.maddox.il2.engine.TextScr.output(640, 140, "---Controls---");
            com.maddox.il2.engine.TextScr.output(640, 120, "A/C: " + (super.CT.bHasAileronControl ? "" : "AIL ") + (super.CT.bHasElevatorControl ? "" : "ELEV ") + (super.CT.bHasRudderControl ? "" : "RUD ") + (super.Gears.bIsHydroOperable ? "" : "GEAR "));
            com.maddox.il2.engine.TextScr.output(640, 100, "ENG: " + (super.EI.engines[j].isHasControlThrottle() ? "" : "THTL ") + (super.EI.engines[j].isHasControlProp() ? "" : "PROP ") + (super.EI.engines[j].isHasControlMix() ? "" : "MIX ") + (super.EI.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (super.EI.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
            com.maddox.il2.engine.TextScr.output(640, 80, "PIL: (" + (int)(super.AS.getPilotHealth(0) * 100F) + "%)");
            com.maddox.il2.engine.TextScr.output(640, 60, "Sens: " + super.CT.Sensitivity);
            com.maddox.il2.engine.TextScr.output(400, 500, "+");
            com.maddox.il2.engine.TextScr.output(400, 400, "|");
            com.maddox.il2.engine.TextScr.output((int)(400F + 200F * super.CT.AileronControl), (int)(500F - 200F * super.CT.ElevatorControl), "+");
            com.maddox.il2.engine.TextScr.output((int)(400F + 200F * super.CT.RudderControl), 400, "|");
            com.maddox.il2.engine.TextScr.output(5, 200, "AOA = " + super.AOA);
            com.maddox.il2.engine.TextScr.output(5, 220, "Mass = " + super.M.getFullMass());
            com.maddox.il2.engine.TextScr.output(5, 320, "AERON TR = " + super.CT.trimAileron);
            com.maddox.il2.engine.TextScr.output(5, 300, "VATOR TR = " + super.CT.trimElevator);
            com.maddox.il2.engine.TextScr.output(5, 280, "RUDDR TR = " + super.CT.trimRudder);
            com.maddox.il2.engine.TextScr.output(245, 160, " pF = " + super.EI.engines[0].zatizeni * 100D + "%/hr");
            hpOld = hpOld * 0.95F + (0.05F * super.EI.engines[0].w * super.EI.engines[0].engineMoment) / 746F;
            com.maddox.il2.engine.TextScr.output(245, 180, " hp = " + hpOld);
            com.maddox.il2.engine.TextScr.output(245, 200, " eMoment = " + super.EI.engines[0].engineMoment);
            com.maddox.il2.engine.TextScr.output(245, 220, " pMoment = " + super.EI.engines[0].propMoment);
        }
        if(!Realism.Limited_Fuel)
            superFuel = super.M.fuel = java.lang.Math.max(superFuel, super.M.fuel);
        super.AP.update(f);
        ((com.maddox.il2.objects.air.Aircraft)super.actor).netUpdateWayPoint();
        super.CT.update(f, (float)((com.maddox.JGP.Tuple3d) (super.Vflow)).x, super.EI, true);
        float f7 = (float)(((com.maddox.JGP.Tuple3d) (super.Vflow)).x * ((com.maddox.JGP.Tuple3d) (super.Vflow)).x) / 11000F;
        if(f7 > 1.0F)
            f7 = 1.0F;
        com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringGain(f7);
        if(super.CT.saveWeaponControl[0] || super.CT.saveWeaponControl[1] || super.CT.saveWeaponControl[2])
            dangerEM();
        super.Wing.setFlaps(super.CT.getFlap());
        FMupdate(f);
        super.EI.update(f);
        super.Gravity = super.M.getFullMass() * com.maddox.il2.fm.Atmosphere.g();
        super.M.computeFullJ(super.J, super.J0);
        if(Realism.G_Limits)
        {
            if(super.G_ClassCoeff < 0.0F || !((com.maddox.il2.objects.air.Aircraft)super.actor instanceof com.maddox.il2.objects.air.TypeBomber))
                Current_G_Limit = super.ReferenceForce / super.M.getFullMass() - super.M.pylonCoeff;
            else
                Current_G_Limit = super.ReferenceForce / super.M.getFullMass();
            setLimitLoad(Current_G_Limit);
        }
        if(isTick(44, 0))
        {
            super.AS.update(f * 44F);
            ((com.maddox.il2.objects.air.Aircraft)super.actor).rareAction(f * 44F, true);
            super.M.computeParasiteMass(super.CT.Weapons);
            super.Sq.computeParasiteDrag(super.CT, super.CT.Weapons);
            checkAirborneState();
            putScareShpere();
            dangerEMAces();
            if(super.turnOffCollisions && !super.Gears.onGround && (double)getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (super.Loc)).x, ((com.maddox.JGP.Tuple3d) (super.Loc)).y) > 30D)
                super.turnOffCollisions = false;
        }
        super.Or.wrap();
        if(Realism.Wind_N_Turbulence)
            com.maddox.il2.ai.World.wind().getVector(super.Loc, super.Vwind);
        else
            super.Vwind.set(0.0D, 0.0D, 0.0D);
        super.Vair.sub(super.Vwld, super.Vwind);
        super.Or.transformInv(super.Vair, super.Vflow);
        super.Density = com.maddox.il2.fm.Atmosphere.density((float)((com.maddox.JGP.Tuple3d) (super.Loc)).z);
        super.AOA = com.maddox.il2.fm.FMMath.RAD2DEG(-(float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (super.Vflow)).z, ((com.maddox.JGP.Tuple3d) (super.Vflow)).x));
        super.AOS = com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (super.Vflow)).y, ((com.maddox.JGP.Tuple3d) (super.Vflow)).x));
        indSpeed = getSpeed() * (float)java.lang.Math.sqrt(super.Density / 1.225F);
        super.Mach = super.V / com.maddox.il2.fm.Atmosphere.sonicSpeed((float)((com.maddox.JGP.Tuple3d) (super.Loc)).z);
        float fDragFactor = 1.0F;
        float fDragParasiteFactor = 1.0F;
        if(super.Ss.allParamsSet)
        {
            float fMachDrag = super.Ss.getDragFactorForMach(super.Mach);
            fDragFactor = (float)java.lang.Math.sqrt(fMachDrag);
            fDragParasiteFactor = (float)java.lang.Math.pow(fMachDrag, 5D);
        } else
        if(super.Ss.getDragFactorForMach(super.Mach) <= 1.0F);
        if(super.Mach > 0.8F)
            super.Mach = 0.8F;
        super.Kq = 1.0F / (float)java.lang.Math.sqrt(1.0F - super.Mach * super.Mach);
        super.q_ = super.Density * super.V2 * 0.5F;
        double d1 = ((com.maddox.JGP.Tuple3d) (super.Loc)).z - super.Gears.screenHQ;
        if(d1 < 0.0D)
            d1 = 0.0D;
        float f1 = super.CT.getAileron() * 14F;
        f1 = super.Arms.WING_V * (float)java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(super.AOS)) + super.SensRoll * ailerInfluence * (1.0F - 0.1F * super.CT.getFlap()) * f1;
        double d2 = 0.0D;
        double d4 = 0.0D;
        if(super.EI.engines[0].getType() < 2)
        {
            d2 = super.EI.engines[0].addVflow;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = 0.5D * super.EI.engines[0].addVside;
        }
        Vn.set(-super.Arms.GCENTER, 0.84999999999999998D * (double)super.Arms.WING_END, -0.5D);
        Vn.cross(super.W, Vn);
        Vn.add(super.Vflow);
        float f12 = f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x));
        Vn.x += 0.070000000000000007D * d2;
        double d = Vn.lengthSquared();
        d *= 0.5F * super.Density;
        f7 = f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z + 0.070000000000000007D * d4 * (double)super.EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x));
        float f14 = 0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwl.x = -d * (double)(super.Wing.new_Cx(f7) + f14 + super.GearCX * super.CT.getGear() + super.radiatorCX * (super.EI.getRadiatorPos() + super.CT.getCockpitDoor()) + super.Sq.dragAirbrakeCx * super.CT.getAirBrake());
        Cwl.z = d * (double)super.Wing.new_Cy(f7) * (double)super.Kq;
        if(super.fmsfxCurrentType != 0)
        {
            if(super.fmsfxCurrentType == 1)
                Cwl.z *= com.maddox.il2.objects.air.Aircraft.cvt(super.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(super.fmsfxCurrentType == 2)
            {
                Cwl.z = 0.0D;
                if(com.maddox.rts.Time.current() >= super.fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
        }
        Vn.set(-super.Arms.GCENTER, -super.Arms.WING_END, -0.5D);
        Vn.cross(super.W, Vn);
        Vn.add(super.Vflow);
        float f13 = -f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x));
        Vn.x += 0.070000000000000007D * d2;
        d = Vn.lengthSquared();
        d *= 0.5F * super.Density;
        float f9 = -f1 - com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z - 0.070000000000000007D * d4 * (double)super.EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x));
        f14 = -0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwr.x = -d * (double)(super.Wing.new_Cx(f9) + f14 + super.GearCX * super.CT.getGear() + super.radiatorCX * super.EI.getRadiatorPos() + super.Sq.dragAirbrakeCx * super.CT.getAirBrake());
        Cwr.z = d * (double)super.Wing.new_Cy(f9) * (double)super.Kq;
        if(super.fmsfxCurrentType != 0)
        {
            if(super.fmsfxCurrentType == 1)
                Cwr.z *= com.maddox.il2.objects.air.Aircraft.cvt(super.fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(super.fmsfxCurrentType == 3)
            {
                Cwr.z = 0.0D;
                if(com.maddox.rts.Time.current() >= super.fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
        }
        Cwl.y = -d * (double)super.Fusel.new_Cy(super.AOS);
        Cwl.x -= d * (double)super.Fusel.new_Cx(super.AOS);
        Cwr.y = -d * (double)super.Fusel.new_Cy(super.AOS);
        Cwr.x -= d * (double)super.Fusel.new_Cx(super.AOS);
        float f15 = super.Wing.get_AOA_CRYT();
        double d7 = 1.0D;
        double d8 = 0.5D + 0.40000000000000002D * (double)super.EI.getPowerOutput();
        double d9 = 1.2D + 0.40000000000000002D * (double)super.EI.getPowerOutput();
        if(spinCoeff < d8)
            spinCoeff = d8;
        if(spinCoeff > d9)
            spinCoeff = d9;
        f7 = f12;
        f9 = f13;
        if(!Realism.Stalls_N_Spins || super.Gears.isUnderDeck())
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
            if((double)super.Sq.squareRudders > 0.0D && (double)java.lang.Math.abs(super.CT.RudderControl) > 0.5D && (double)super.CT.RudderControl * ((com.maddox.JGP.Tuple3d) (super.W)).z > 0.0D)
                spinCoeff -= 0.29999999999999999D * (double)f;
            float f16;
            if(f7 > f9)
                f16 = f7;
            else
                f16 = f9;
            super.turbCoeff = 0.8F * (f16 - f15);
            if(super.turbCoeff < 1.0F)
                super.turbCoeff = 1.0F;
            if(super.turbCoeff > 15F)
                super.turbCoeff = 15F;
            d7 = 1.0D - 0.20000000000000001D * (double)(f16 - f15);
            if(d7 < 0.20000000000000001D)
                d7 = 0.20000000000000001D;
            d7 /= super.turbCoeff;
            double d12 = d * (double)super.turbCoeff * spinCoeff;
            float f17 = getAltitude() - (float)com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (super.Loc)).x, ((com.maddox.JGP.Tuple3d) (super.Loc)).y);
            if(f17 < 10F)
                d12 *= 0.1F * f17;
            if(f7 > f9)
            {
                Cwr.x += 0.019999999552965164D * d12 * (double)super.Sq.spinCxloss;
                Cwl.x -= 0.25D * d12 * (double)super.Sq.spinCxloss;
                Cwr.z += 0.019999999552965164D * d12 * (double)super.Sq.spinCyloss;
                Cwl.z -= 0.10000000149011612D * d12 * (double)super.Sq.spinCyloss;
            } else
            {
                Cwl.x += 0.019999999552965164D * d12 * (double)super.Sq.spinCxloss;
                Cwr.x -= 0.25D * d12 * (double)super.Sq.spinCxloss;
                Cwl.z += 0.019999999552965164D * d12 * (double)super.Sq.spinCyloss;
                Cwr.z -= 0.10000000149011612D * d12 * (double)super.Sq.spinCyloss;
            }
            rudderInfluence = 1.0F + 0.035F * super.turbCoeff;
        } else
        {
            super.turbCoeff = 1.0F;
            d7 = 1.0D;
            spinCoeff -= 0.20000000000000001D * (double)f;
            ailerInfluence = 1.0F;
            rudderInfluence = 1.0F;
        }
        if(isTick(15, 0))
            if(java.lang.Math.abs(f7 - f9) > 5F)
                com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringZero((f9 - f7) * 0.04F, 0.0F);
            else
                com.maddox.il2.objects.effects.ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
        if(d1 < 0.40000000000000002D * (double)super.Length)
        {
            double d10 = 1.0D - d1 / (0.40000000000000002D * (double)super.Length);
            double d13 = 1.0D + 0.20000000000000001D * d10;
            double d16 = 1.0D + 0.20000000000000001D * d10;
            Cwl.z *= d13;
            Cwl.x *= d16;
            Cwr.z *= d13;
            Cwr.x *= d16;
        }
        f1 = super.CT.getElevator() * (super.CT.getElevator() > 0.0F ? 28F : 20F);
        Vn.set(-super.Arms.VER_STAB, 0.0D, 0.0D);
        Vn.cross(super.W, Vn);
        Vn.add(super.Vflow);
        double d11 = java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Vn)).y * ((com.maddox.JGP.Tuple3d) (Vn)).y + ((com.maddox.JGP.Tuple3d) (Vn)).z * ((com.maddox.JGP.Tuple3d) (Vn)).z);
        d2 = 0.0D;
        d4 = 0.0D;
        if(super.EI.engines[0].getType() < 2)
        {
            double d14 = 1.0D + 0.040000000000000001D * (double)super.Arms.RUDDER;
            d14 = 1.0D / (d14 * d14);
            double d17 = ((com.maddox.JGP.Tuple3d) (Vn)).x + d14 * super.EI.engines[0].addVflow;
            if(d17 < 0.20000000000000001D)
                d17 = 0.20000000000000001D;
            double d19 = 1.0D - (1.5D * d11) / d17;
            if(d19 < 0.0D)
                d19 = 0.0D;
            double d3 = d19 * d14 * super.EI.engines[0].addVflow;
            Vn.x += d3;
            double d21 = java.lang.Math.min(0.0011000000000000001D * ((com.maddox.JGP.Tuple3d) (Vn)).x * ((com.maddox.JGP.Tuple3d) (Vn)).x, 1.0D);
            if(((com.maddox.JGP.Tuple3d) (Vn)).x < 0.0D)
                d21 = 0.0D;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = d19 * d21 * super.EI.engines[0].addVside;
        }
        double d15 = (double)super.Density * Vn.lengthSquared() * 0.5D;
        if(super.EI.getNum() == 1 && super.EI.engines[0].getType() < 2)
        {
            f7 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z - 0.35999999999999999D * d4 * (double)super.EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * super.V - super.SensPitch * f1;
            f9 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z + 0.35999999999999999D * d4 * (double)super.EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * super.V - super.SensPitch * f1;
        } else
        {
            f7 = f9 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).z, ((com.maddox.JGP.Tuple3d) (Vn)).x)) - 2.0F - 0.002F * super.V - super.SensPitch * f1;
        }
        Chl.x = -d15 * (double)super.Tail.new_Cx(f7);
        Chl.z = d15 * (double)super.Tail.new_Cy(f7);
        Chr.x = -d15 * (double)super.Tail.new_Cx(f9);
        Chr.z = d15 * (double)super.Tail.new_Cy(f9);
        Chl.y = Chr.y = 0.0D;
        f1 = super.CT.getRudder() * (super.Sq.squareRudders < 0.05F ? 0.0F : 28F);
        float f11;
        if(super.EI.engines[0].getType() < 2)
            f11 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).y - 0.5D * d4 * (double)super.EI.getPropDirSign(), ((com.maddox.JGP.Tuple3d) (Vn)).x)) + super.SensYaw * rudderInfluence * f1;
        else
            f11 = -com.maddox.il2.fm.FMMath.RAD2DEG((float)java.lang.Math.atan2(((com.maddox.JGP.Tuple3d) (Vn)).y, ((com.maddox.JGP.Tuple3d) (Vn)).x)) + super.SensYaw * rudderInfluence * f1;
        Cv.x = -d15 * (double)super.Tail.new_Cx(f11);
        Cv.y = d15 * (double)super.Tail.new_Cy(f11);
        Cv.z = 0.0D;
        if(!Realism.Stalls_N_Spins)
            Cv.y += ((com.maddox.JGP.Tuple3d) (Cv)).y;
        Vn.set(super.Vflow);
        d = (double)super.Density * Vn.lengthSquared() * 0.5D;
        Fwl.scale(super.Sq.liftWingLIn + super.Sq.liftWingLMid + super.Sq.liftWingLOut, Cwl);
        Fwr.scale(super.Sq.liftWingRIn + super.Sq.liftWingRMid + super.Sq.liftWingROut, Cwr);
        Fwl.x -= d * (double)(super.Sq.dragParasiteCx * fDragParasiteFactor + super.Sq.dragProducedCx) * 0.5D;
        Fwr.x -= d * (double)(super.Sq.dragParasiteCx * fDragParasiteFactor + super.Sq.dragProducedCx) * 0.5D;
        Fhl.scale((super.Sq.liftStab + super.Sq.squareElevators) * 0.5F, Chl);
        Fhr.scale((super.Sq.liftStab + super.Sq.squareElevators) * 0.5F, Chr);
        Fv.scale(0.2F + super.Sq.liftKeel * 1.5F + super.Sq.squareRudders, Cv);
        Fwl.x *= fDragFactor;
        Fwr.x *= fDragFactor;
        Fhl.x *= fDragFactor;
        Fhr.x *= fDragFactor;
        Fv.x *= fDragFactor;
        super.AF.set(Fwl);
        super.AF.add(Fwr);
        if(com.maddox.il2.fm.FMMath.isNAN(super.AF))
        {
            super.AF.set(0.0D, 0.0D, 0.0D);
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("AF isNAN");
        } else
        if(super.AF.length() > (double)(super.Gravity * 50F))
        {
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("A > 50.0");
            super.AF.normalize();
            super.AF.scale(super.Gravity * 50F);
        } else
        {
            if(Realism.G_Limits)
            {
                if((getOverload() > getUltimateLoad() + com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) || getOverload() < super.Negative_G_Limit - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.5F)) && !super.Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                    if(super.cutPart < 0)
                        cutWing();
                    else
                        cutPart(super.cutPart);
                if(getOverload() > Current_G_Limit || getOverload() < super.Negative_G_Limit)
                {
                    float f19 = java.lang.Math.abs(getOverload());
                    if(f19 > max_G_Cycle)
                        max_G_Cycle = f19;
                    timeCounter += f;
                    if(timeCounter > 0.75F)
                    {
                        cycleCounter++;
                        if(cycleCounter > 1)
                        {
                            float f18;
                            if(getOverload() > 1.0F)
                                f18 = (max_G_Cycle - Current_G_Limit) / Current_G_Limit;
                            else
                                f18 = (max_G_Cycle + super.Negative_G_Limit) / super.Negative_G_Limit;
                            f18 *= f18;
                            setSafetyFactor(f18);
                            if(structuralFX != null)
                                structuralFX.play();
                            super.VmaxAllowed = maxSpeed * (getSafetyFactor() * 0.3F + 0.55F);
                            rD = com.maddox.il2.ai.World.Rnd().nextFloat();
                            if(rD < 0.001F)
                            {
                                if(super.CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                                    gearCutCounter++;
                                }
                                super.Wing.CxMin_0 += 6F * rD;
                                setSafetyFactor(250F * rD);
                                super.CT.bHasGearControl = false;
                            } else
                            if(rD < 0.002F)
                            {
                                if(super.CT.bHasGearControl)
                                {
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 2;
                                }
                                super.Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                super.CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0025F)
                            {
                                if(super.CT.bHasGearControl)
                                {
                                    super.CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                                    super.CT.forceGear(1.0F);
                                    gearCutCounter += 2;
                                }
                                super.Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                super.CT.bHasGearControl = false;
                            } else
                            if(rD < 0.003F)
                            {
                                if(super.CT.bHasGearControl)
                                {
                                    super.CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                                    super.CT.forceGear(1.0F);
                                    gearCutCounter++;
                                }
                                super.Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                super.CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0035F)
                            {
                                if(super.CT.bHasGearControl)
                                {
                                    super.CT.dvGear = 1.0F;
                                    super.CT.forceGear(1.0F);
                                    super.CT.GearControl = 1.0F;
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 3;
                                }
                                super.Wing.CxMin_0 += 8F * rD;
                                setSafetyFactor(125F * rD);
                                super.CT.bHasGearControl = false;
                            } else
                            if(rD < 0.04F)
                                super.SensYaw *= 0.68F;
                            else
                            if(rD < 0.05F)
                                super.SensPitch *= 0.68F;
                            else
                            if(rD < 0.06F)
                                super.SensRoll *= 0.68F;
                            else
                            if(rD < 0.061F)
                                super.CT.dropFuelTanks();
                            else
                            if(rD < 0.065F)
                                super.CT.bHasFlapsControl = false;
                            else
                            if(rD >= 0.5F)
                                if(rD < 0.6F)
                                    super.Wing.CxMin_0 += 0.011F * rD;
                                else
                                if((int)super.M.getFullMass() % 2 == 0)
                                {
                                    super.Sq.getClass();
                                    super.Sq.liftWingROut *= 0.95F - 0.2F * rD;
                                    super.Wing.CxMin_0 += 0.011F * rD;
                                } else
                                {
                                    super.Sq.getClass();
                                    super.Sq.liftWingLOut *= 0.95F - 0.2F * rD;
                                    super.Wing.CxMin_0 += 0.011F * rD;
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
            if((super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && getOverload() > 15F && !super.Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                cutWing();
            else
            if(getOverload() > 13.5F && !super.Gears.onGround() && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98)
                cutWing();
            if(indSpeed > 112.5F && com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 98 && super.CT.getGear() > 0.3F && super.CT.GearControl == 1.0F)
            {
                if(super.CT.getGear() >= 0.1F && super.CT.GearControl != 0.0F && !bGearCut)
                    if(!(super.actor instanceof com.maddox.il2.objects.air.F4U) && !(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    } else
                    if(indSpeed > 180F && !(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    }
                if(indSpeed > 350F && !(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearR2_D0", "GearR2_D0");
                        gearCutCounter++;
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                    {
                        ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "GearL2_D0", "GearL2_D0");
                        gearCutCounter += 2;
                    }
                }
                if(gearCutCounter > 2)
                {
                    bGearCut = true;
                    super.CT.bHasGearControl = false;
                }
            }
            if(indSpeed > 60.5F && super.CT.getWing() > 0.1F)
            {
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 90 && ((com.maddox.il2.objects.air.Aircraft)super.actor).isChunkAnyDamageVisible("WingLMid"))
                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "WingLMid_D0", "WingLMid_D0");
                if(com.maddox.il2.ai.World.Rnd().nextInt(0, 100) > 90 && ((com.maddox.il2.objects.air.Aircraft)super.actor).isChunkAnyDamageVisible("WingRMid"))
                    ((com.maddox.il2.objects.air.Aircraft)super.actor).msgCollision(super.actor, "WingRMid_D0", "WingRMid_D0");
            }
            if(!(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && indSpeed > 81F && super.CT.bHasFlapsControl && super.CT.FlapsControl > 0.21F && (indSpeed - 81F) * super.CT.getFlap() > 8F)
            {
                if(com.maddox.il2.ai.World.getPlayerAircraft() == super.actor && super.CT.bHasFlapsControl)
                    com.maddox.il2.game.HUD.log("FailedFlaps");
                super.CT.bHasFlapsControl = false;
                super.CT.FlapsControl = 0.0F;
            }
            if(indSpeed > super.VmaxAllowed && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 16F) < indSpeed - super.VmaxAllowed && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 2)
                flutterDamage();
            if(!(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic))
            {
                if(indSpeed > 610F)
                {
                    if(com.maddox.il2.ai.World.cur().isDebugFM())
                        java.lang.System.out.println("*** Sonic overspeed....");
                    flutter();
                }
            } else
            if(!(super.actor instanceof com.maddox.il2.objects.air.TypeSupersonic) && indSpeed > 310F)
            {
                if(com.maddox.il2.ai.World.cur().isDebugFM())
                    java.lang.System.out.println("*** Sonic overspeed....");
                flutter();
            }
        }
        super.AM.set(0.0D, 0.0D, 0.0D);
        if(java.lang.Math.abs(super.AOA) < 12F)
        {
            float f2 = super.Or.getKren();
            if(f2 > 30F)
                f2 = 30F;
            else
            if(f2 < -30F)
                f2 = -30F;
            f2 = (float)((double)f2 * (java.lang.Math.min(((com.maddox.JGP.Tuple3d) (super.Vflow)).x - 50D, 50D) * 0.0030000000260770321D));
            super.AM.add(-f2 * 0.01F * super.Gravity, 0.0D, 0.0D);
        }
        if(!getOp(19))
        {
            super.AM.y += (double)(8F * super.Sq.squareWing) * ((com.maddox.JGP.Tuple3d) (super.Vflow)).x;
            super.AM.z += 200F * super.Sq.squareWing * super.EI.getPropDirSign();
        }
        double d18 = (double)super.CT.getFlap() * 3D;
        if(d18 > 1.0D)
            d18 = 1.0D;
        double d20 = 0.0111D * (double)java.lang.Math.abs(super.AOA);
        if(super.Wing.AOACritL < super.AOA && super.AOA < super.Wing.AOACritH)
            d20 = 0.0D;
        else
        if(super.AOA >= super.Wing.AOACritH)
            d20 = java.lang.Math.min(d20, 0.29999999999999999D * (double)(super.AOA - super.Wing.AOACritH));
        else
        if(super.Wing.AOACritL <= super.AOA)
            d20 = java.lang.Math.min(d20, 0.29999999999999999D * (double)(super.Wing.AOACritL - super.AOA));
        double d22 = (double)super.Arms.GCENTER + (double)super.Arms.GC_FLAPS_SHIFT * d18 * (1.0D - d20) + (double)super.Arms.GC_AOA_SHIFT * d20;
        TmpV.set(-d22, (double)super.Arms.WING_MIDDLE * (1.3D + 1.0D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(super.AOS))), -super.Arms.GCENTER_Z);
        TmpV.cross(TmpV, Fwl);
        super.AM.add(TmpV);
        TmpV.set(-d22, (double)(-super.Arms.WING_MIDDLE) * (1.3D - 1.0D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(super.AOS))), -super.Arms.GCENTER_Z);
        TmpV.cross(TmpV, Fwr);
        super.AM.add(TmpV);
        super.AM.x += su26add;
        TmpV.set(-super.Arms.HOR_STAB, 1.0D, 0.0D);
        TmpV.cross(TmpV, Fhl);
        super.AM.add(TmpV);
        TmpV.set(-super.Arms.HOR_STAB, -1D, 0.0D);
        TmpV.cross(TmpV, Fhr);
        super.AM.add(TmpV);
        TmpV.set(-super.Arms.VER_STAB, 0.0D, 1.0D);
        TmpV.cross(TmpV, Fv);
        super.AM.add(TmpV);
        double d23 = 1.0D - 1.0000000000000001E-005D * (double)indSpeed;
        if(d23 < 0.80000000000000004D)
            d23 = 0.80000000000000004D;
        super.W.scale(d23);
        if(!Realism.Stalls_N_Spins)
            super.AM.y += ((com.maddox.JGP.Tuple3d) (super.AF)).z * 0.5D * java.lang.Math.sin(com.maddox.il2.fm.FMMath.DEG2RAD(java.lang.Math.abs(super.AOA)));
        if(super.W.lengthSquared() > 25D)
            super.W.scale(5D / super.W.length());
        if(!Realism.Stalls_N_Spins && ((com.maddox.JGP.Tuple3d) (super.Vflow)).x > 20D)
            super.W.z += super.AOS * f;
        super.AF.add(super.producedAF);
        super.AM.add(super.producedAM);
        super.producedAF.set(0.0D, 0.0D, 0.0D);
        super.producedAM.set(0.0D, 0.0D, 0.0D);
        super.AF.add(super.EI.producedF);
        super.AM.add(super.EI.producedM);
        if(com.maddox.il2.ai.World.cur().diffCur.Torque_N_Gyro_Effects)
        {
            super.GM.set(super.EI.getGyro());
            super.GM.scale(d7);
            super.AM.add(super.GM);
        }
        super.GF.set(0.0D, 0.0D, 0.0D);
        super.GM.set(0.0D, 0.0D, 0.0D);
        if(com.maddox.rts.Time.tickCounter() % 2 != 0)
            super.Gears.roughness = super.Gears.plateFriction(this);
        super.Gears.ground(this, true);
        int k = 5;
        if(super.GF.lengthSquared() == 0.0D && super.GM.lengthSquared() == 0.0D)
            k = 1;
        super.SummF.add(super.AF, super.GF);
        super.ACmeter.set(super.SummF);
        super.ACmeter.scale(1.0F / super.Gravity);
        TmpV.set(0.0D, 0.0D, -super.Gravity);
        super.Or.transformInv(TmpV);
        super.GF.add(TmpV);
        super.SummF.add(super.AF, super.GF);
        super.SummM.add(super.AM, super.GM);
        double d24 = 1.0D / (double)super.M.mass;
        super.LocalAccel.scale(d24, super.SummF);
        if(java.lang.Math.abs(getRollAcceleration()) > 50000.5F)
        {
            com.maddox.il2.objects.effects.ForceFeedback.fxPunch(((com.maddox.JGP.Tuple3d) (super.SummM)).x > 0.0D ? 0.9F : -0.9F, 0.0F, 1.0F);
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Punched (Axial = " + ((com.maddox.JGP.Tuple3d) (super.SummM)).x + ")");
        }
        if(java.lang.Math.abs(getOverload() - lastAcc) > 0.5F)
        {
            com.maddox.il2.objects.effects.ForceFeedback.fxPunch(com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F), -0.9F, getSpeed() * 0.05F);
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("Punched (Lat = " + java.lang.Math.abs(getOverload() - lastAcc) + ")");
        }
        lastAcc = getOverload();
        if(com.maddox.il2.fm.FMMath.isNAN(super.AM))
        {
            super.AM.set(0.0D, 0.0D, 0.0D);
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("AM isNAN");
        } else
        if(super.AM.length() > (double)(super.Gravity * 150F))
        {
            flutter();
            if(com.maddox.il2.ai.World.cur().isDebugFM())
                java.lang.System.out.println("SummM > 150g");
            super.AM.normalize();
            super.AM.scale(super.Gravity * 150F);
        }
        super.dryFriction -= 0.01D;
        if(super.Gears.gearsChanged)
            super.dryFriction = 1.0F;
        if(super.Gears.nOfPoiOnGr > 0)
            super.dryFriction += 0.02F;
        if(super.dryFriction < 1.0F)
            super.dryFriction = 1.0F;
        if(super.dryFriction > 32F)
            super.dryFriction = 32F;
        float f20 = 4F * (0.25F - super.EI.getPowerOutput());
        if(f20 < 0.0F)
            f20 = 0.0F;
        f20 *= f20;
        f20 *= super.dryFriction;
        float f21 = f20 * super.M.mass * super.M.mass;
        if(!super.brakeShoe && (super.Gears.nOfPoiOnGr == 0 && super.Gears.nOfGearsOnGr < 3 || f20 == 0.0F || super.SummM.lengthSquared() > (double)(2.0F * f21) || super.SummF.lengthSquared() > (double)(80F * f21) || super.W.lengthSquared() > (double)(0.00014F * f20) || super.Vwld.lengthSquared() > (double)(0.09F * f20)))
        {
            double d25 = 1.0D / (double)k;
            for(int l = 0; l < k; l++)
            {
                super.SummF.add(super.AF, super.GF);
                super.SummM.add(super.AM, super.GM);
                super.AW.x = ((((com.maddox.JGP.Tuple3d) (super.J)).y - ((com.maddox.JGP.Tuple3d) (super.J)).z) * ((com.maddox.JGP.Tuple3d) (super.W)).y * ((com.maddox.JGP.Tuple3d) (super.W)).z + ((com.maddox.JGP.Tuple3d) (super.SummM)).x) / ((com.maddox.JGP.Tuple3d) (super.J)).x;
                super.AW.y = ((((com.maddox.JGP.Tuple3d) (super.J)).z - ((com.maddox.JGP.Tuple3d) (super.J)).x) * ((com.maddox.JGP.Tuple3d) (super.W)).z * ((com.maddox.JGP.Tuple3d) (super.W)).x + ((com.maddox.JGP.Tuple3d) (super.SummM)).y) / ((com.maddox.JGP.Tuple3d) (super.J)).y;
                super.AW.z = ((((com.maddox.JGP.Tuple3d) (super.J)).x - ((com.maddox.JGP.Tuple3d) (super.J)).y) * ((com.maddox.JGP.Tuple3d) (super.W)).x * ((com.maddox.JGP.Tuple3d) (super.W)).y + ((com.maddox.JGP.Tuple3d) (super.SummM)).z) / ((com.maddox.JGP.Tuple3d) (super.J)).z;
                TmpV.scale(d25 * (double)f, super.AW);
                super.W.add(TmpV);
                super.Or.transform(super.W, Vn);
                TmpV.scale(d25 * (double)f, super.W);
                super.Or.increment((float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).z)), (float)(-com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).y)), (float)com.maddox.il2.fm.FMMath.RAD2DEG(((com.maddox.JGP.Tuple3d) (TmpV)).x));
                super.Or.transformInv(Vn, super.W);
                TmpV.scale(d24, super.SummF);
                super.Or.transform(TmpV);
                super.Accel.set(TmpV);
                TmpV.scale(d25 * (double)f);
                super.Vwld.add(TmpV);
                TmpV.scale(d25 * (double)f, super.Vwld);
                TmpP.set(TmpV);
                super.Loc.add(TmpP);
                super.GF.set(0.0D, 0.0D, 0.0D);
                super.GM.set(0.0D, 0.0D, 0.0D);
                if(l < k - 1)
                {
                    super.Gears.ground(this, true);
                    TmpV.set(0.0D, 0.0D, -super.Gravity);
                    super.Or.transformInv(TmpV);
                    super.GF.add(TmpV);
                }
            }

            for(int i1 = 0; i1 < 3; i1++)
            {
                super.Gears.gWheelAngles[i1] = (super.Gears.gWheelAngles[i1] + (float)java.lang.Math.toDegrees(java.lang.Math.atan((super.Gears.gVelocity[i1] * (double)f) / 0.375D))) % 360F;
                super.Gears.gVelocity[i1] *= 0.94999998807907104D;
            }

            super.HM.chunkSetAngles("GearL1_D0", 0.0F, -super.Gears.gWheelAngles[0], 0.0F);
            super.HM.chunkSetAngles("GearR1_D0", 0.0F, -super.Gears.gWheelAngles[1], 0.0F);
            super.HM.chunkSetAngles("GearC1_D0", 0.0F, -super.Gears.gWheelAngles[2], 0.0F);
        }
        if(super.Leader != null && isTick(128, 97) && com.maddox.il2.engine.Actor.isAlive(((com.maddox.il2.engine.Interpolate) (super.Leader)).actor) && !super.Gears.onGround)
        {
            float f22 = (float)super.Loc.distance(((com.maddox.il2.fm.FlightModelMain) (super.Leader)).Loc);
            if(f22 > 3000F)
                com.maddox.il2.objects.sounds.Voice.speakDeviateBig((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.Leader)).actor);
            else
            if(f22 > 1700F)
                com.maddox.il2.objects.sounds.Voice.speakDeviateSmall((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate) (super.Leader)).actor);
        }
        shakeLevel = 0.0F;
        if(super.Gears.onGround())
        {
            shakeLevel += (30D * super.Gears.roughness * super.Vrel.length()) / (double)super.M.mass;
        } else
        {
            if(indSpeed > 10F)
            {
                float f23 = (float)java.lang.Math.sin(java.lang.Math.toRadians(java.lang.Math.abs(super.AOA)));
                if(f23 > 0.02F)
                {
                    f23 *= f23;
                    shakeLevel += 0.07F * (f23 - 0.0004F) * (indSpeed - 10F);
                    if(isTick(30, 0) && shakeLevel > 0.6F)
                        com.maddox.il2.game.HUD.log(stallStringID, "Stall");
                }
            }
            if(indSpeed > 35F)
            {
                if(super.CT.bHasGearControl && (super.Gears.lgear || super.Gears.rgear) && super.CT.getGear() > 0.0F)
                    shakeLevel += 0.004F * super.CT.getGear() * (indSpeed - 35F);
                if(super.CT.getFlap() > 0.0F)
                    shakeLevel += 0.004F * super.CT.getFlap() * (indSpeed - 35F);
            }
        }
        if(indSpeed > super.VmaxAllowed * 0.8F)
            shakeLevel = 0.01F * (indSpeed - super.VmaxAllowed * 0.8F);
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
        if((com.maddox.il2.objects.air.Aircraft)super.actor instanceof com.maddox.il2.objects.air.TypeGSuit)
            ((com.maddox.il2.objects.air.TypeGSuit)super.actor).getGFactors(theGFactors);
        fGPosLimit *= theGFactors.getPosGToleranceFactor();
        fGNegLimit *= theGFactors.getNegGToleranceFactor();
        if(f > 1.0F)
            f = 1.0F;
        if(super.Gears.onGround() || !flag)
        {
            plAccel.set(0.0D, 0.0D, 0.0D);
        } else
        {
            plAccel.set(getAccel());
            plAccel.scale(0.10199999809265137D);
        }
        plAccel.z += 0.5D;
        super.Or.transformInv(plAccel);
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
            super.CT.Sensitivity = 1.0F - (saveDeep - 0.8F);
            if(super.CT.Sensitivity < 0.0F)
                super.CT.Sensitivity = 0.0F;
        } else
        if(saveDeep < -0.4F)
        {
            super.CT.Sensitivity = 1.0F + (saveDeep + 0.4F);
            if(super.CT.Sensitivity < 0.0F)
                super.CT.Sensitivity = 0.0F;
        } else
        {
            super.CT.Sensitivity = 1.0F;
        }
        super.CT.Sensitivity *= super.AS.getPilotHealth(0);
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
        super.producedAM.x += ((com.maddox.JGP.Tuple3d) (vector3d)).x;
        super.producedAM.y += ((com.maddox.JGP.Tuple3d) (vector3d)).y;
        super.producedAM.z += ((com.maddox.JGP.Tuple3d) (vector3d)).z;
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
