// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 9/01/2011 2:43:55 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RealFlightModel.java

package com.maddox.il2.fm;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.*;
import com.maddox.il2.game.*;
import com.maddox.il2.objects.air.*;
import com.maddox.il2.objects.air.TypeGSuit.GFactors;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.fm:
//            Autopilot, FlightModel, Autopilotage, Gear, 
//            Controls, Squares, Mass, EnginesInterface, 
//            Motor, AircraftState, Polares, Atmosphere, 
//            Wind, Arm

public class RealFlightModel extends Pilot
{

    public RealFlightModel(String s)
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
        AP = new Autopilot(this);
        Realism = World.cur().diffCur;
        maxSpeed = VmaxAllowed;
    }

    public Vector3d getW()
    {
        return RealMode ? W : Wtrue;
    }

    private void flutter()
    {
        if(Realism.Flutter_Effect)
            ((Aircraft)actor).msgCollision(actor, "CF_D0", "CF_D0");
    }

    private void flutterDamage()
    {
        if(Realism.Flutter_Effect)
        {
            String s;
            switch(World.Rnd().nextInt(0, 29))
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
            ((Aircraft)actor).msgCollision(actor, s, s);
        }
    }

    private void cutWing()
    {
        if(Realism.Flutter_Effect)
        {
            String s;
            switch(World.Rnd().nextInt(0, 8))
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
            ((Aircraft)actor).msgCollision(actor, s, s);
        }
    }

    private void cutPart(int i)
    {
        if(Realism.Flutter_Effect)
        {
            String s;
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
            ((Aircraft)actor).msgCollision(actor, s, s);
        }
    }

    private void dangerEM()
    {
        if((long)Time.tickCounter() < lastDangerTick + 1L)
            return;
        lastDangerTick = Time.tickCounter();
        Actor actor = War.GetNearestEnemy(this.actor, -1, 700F);
        if(!(actor instanceof Aircraft))
            return;
        Aircraft aircraft = (Aircraft)actor;
        TmpVd.set(aircraft.FM.Loc);
        TmpVd.sub(Loc);
        Or.transformInv(TmpVd);
        TmpVd.normalize();
        if(TmpVd.x < 0.97999999999999998D)
            return;
        if(!(aircraft.FM instanceof Pilot))
        {
            return;
        } else
        {
            Pilot pilot = (Pilot)aircraft.FM;
            pilot.setAsDanger(this.actor);
            return;
        }
    }

    private void dangerEMAces()
    {
        Actor actor = War.GetNearestEnemy(this.actor, -1, 300F);
        if(!(actor instanceof Aircraft))
            return;
        Aircraft aircraft = (Aircraft)actor;
        TmpVd.set(aircraft.FM.Loc);
        TmpVd.sub(Loc);
        Or.transformInv(TmpVd);
        TmpVd.normalize();
        if(TmpV.x < 0.97999999999999998D)
            return;
        if(!(aircraft.FM instanceof Pilot))
        {
            return;
        } else
        {
            Pilot pilot = (Pilot)aircraft.FM;
            pilot.setAsDanger(this.actor);
            return;
        }
    }

    private float MulForce(float f)
    {
        if(f < 40F || f > 180F)
            return 1.0F;
        else
            return 1.0F + (70F - Math.abs(f - 110F)) * 0.04F;
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
            AP.setStabAll(false);
    }

    private void checkAirborneState()
    {
        if(World.getPlayerFM() != this)
            return;
        if(!Actor.isAlive(actor))
            return;
        switch(airborneState)
        {
        default:
            break;

        case 0: // '\0'
            if((double)getAltitude() - Engine.land().HQ_Air(Loc.x, Loc.y) > 40D)
            {
                airborneState = 2;
                setWasAirborne(true);
                setStationedOnGround(false);
                EventLog.onAirInflight((Aircraft)actor);
                if(!Mission.hasRadioStations)
                    CmdEnv.top().exec("music RAND music/inflight");
            } else
            {
                airborneState = 1;
                setStationedOnGround(true);
                if(!Mission.hasRadioStations)
                    CmdEnv.top().exec("music RAND music/takeoff");
            }
            setCrossCountry(false);
            break;

        case 1: // '\001'
            if(Vrel.length() > (double)Vmin)
                setStationedOnGround(false);
            if((double)getAltitude() - Engine.land().HQ_Air(Loc.x, Loc.y) <= 40D || Vrel.length() <= (double)(Vmin * 1.15F))
                break;
            airborneState = 2;
            setStationedOnGround(false);
            setNearAirdrome(false);
            setWasAirborne(true);
            airborneStartPoint.set(Loc);
            World.cur().scoreCounter.playerTakeoff();
            EventLog.onAirInflight((Aircraft)actor);
            if(!Mission.hasRadioStations)
                CmdEnv.top().exec("music RAND music/inflight");
            break;

        case 2: // '\002'
            if(!isCrossCountry() && Loc.distance(airborneStartPoint) > 50000D)
            {
                setCrossCountry(true);
                World.cur().scoreCounter.playerDoCrossCountry();
            }
            if(!Gears.onGround || Vrel.length() >= 1.0D)
                break;
            airborneState = 1;
            setStationedOnGround(true);
            if(!Mission.hasRadioStations)
                CmdEnv.top().exec("music RAND music/takeoff");
            if(Airport.distToNearestAirport(Loc) > 1500D)
            {
                World.cur().scoreCounter.playerLanding(true);
                setNearAirdrome(false);
            } else
            {
                World.cur().scoreCounter.playerLanding(false);
                setNearAirdrome(true);
            }
            break;
        }
        Mission.initRadioSounds();
    }

    private void initSound(Actor actor)
    {
        structuralFX = ((Aircraft)actor).newSound("models.structuralFX", false);
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
        if(actor.isNetMirror())
        {
            ((com.maddox.il2.objects.air.NetAircraft.Mirror)actor.net).fmUpdate(f);
            return;
        }
        if(getSound())
            initSound(actor);
        V2 = (float)Vflow.lengthSquared();
        V = (float)Math.sqrt(V2);
        if(V * f > 5F)
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
            if(World.cur().diffCur.Blackouts_N_Redouts)
                calcOverLoad(f, false);
            producedAM.set(0.0D, 0.0D, 0.0D);
            producedAF.set(0.0D, 0.0D, 0.0D);
            return;
        }
        moveCarrier();
        decDangerAggressiveness();
        if(Loc.z < -20D)
            ((Aircraft)actor).postEndAction(0.0D, actor, 4, null);
        if(!isOk() && Group != null)
            Group.delAircraft((Aircraft)actor);
        if(Config.isUSE_RENDER() && showFM && actor == Main3D.cur3D().viewActor())
        {
            float f6 = (((float)W.x / (CT.getAileron() * 111.111F * SensRoll)) * Sq.squareWing) / 0.8F;
            if(Math.abs(f6) > 50F)
                f6 = 0.0F;
            float f8 = (((float)W.y / (-CT.getElevator() * 111.111F * SensPitch)) * Sq.squareWing) / 0.27F;
            if(Math.abs(f8) > 50F)
                f8 = 0.0F;
            float f10 = (((float)W.z / ((AOS - CT.getRudder() * 12F) * 111.111F * SensYaw)) * Sq.squareWing) / 0.15F;
            if(Math.abs(f10) > 50F)
                f10 = 0.0F;
            TextScr.output(5, 60, "~S RUDDR = " + (float)(int)(f10 * 100F) / 100F);
            TextScr.output(5, 80, "~S VATOR = " + (float)(int)(f8 * 100F) / 100F);
            TextScr.output(5, 100, "~S AERON = " + (float)(int)(f6 * 100F) / 100F);
            String s = "";
            for(int i = 0; (float)i < shakeLevel * 10.5F; i++)
                s = s + ">";

            TextScr.output(5, 120, "SHAKE LVL -" + shakeLevel);
            TextScr.output(5, 670, "Pylon = " + M.pylonCoeff);
            TextScr.output(5, 640, "WIND = " + (float)(int)(Vwind.length() * 10D) / 10F + " " + (float)(int)(Vwind.z * 10D) / 10F + " m/s");
            TextScr.output(5, 140, "BRAKE = " + CT.getBrake());
            int j = 0;
            TextScr.output(225, 140, "---ENGINES (" + EI.getNum() + ")---" + EI.engines[j].getStage());
            TextScr.output(245, 120, "THTL " + (int)(100F * EI.engines[j].getControlThrottle()) + "%" + (EI.engines[j].getControlAfterburner() ? " (NITROS)" : ""));
            TextScr.output(245, 100, "PROP " + (int)(100F * EI.engines[j].getControlProp()) + "%" + (CT.getStepControlAuto() ? " (AUTO)" : ""));
            TextScr.output(245, 80, "MIX " + (int)(100F * EI.engines[j].getControlMix()) + "%");
            TextScr.output(245, 60, "RAD " + (int)(100F * EI.engines[j].getControlRadiator()) + "%" + (CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
            TextScr.output(245, 40, "SUPC " + EI.engines[j].getControlCompressor() + "x");
            TextScr.output(245, 20, "PropAoA :" + (int)Math.toDegrees(EI.engines[j].getPropAoA()));
            TextScr.output(245, 0, "PropPhi :" + (int)Math.toDegrees(EI.engines[j].getPropPhi()));
            TextScr.output(455, 120, "Cyls/Cams " + EI.engines[j].getCylindersOperable() + "/" + EI.engines[j].getCylinders());
            TextScr.output(455, 100, "Readyness " + (int)(100F * EI.engines[j].getReadyness()) + "%");
            TextScr.output(455, 80, "PRM " + (int)((float)(int)(EI.engines[j].getRPM() * 0.02F) * 50F) + " rpm");
            TextScr.output(455, 60, "Thrust " + (int)EI.engines[j].getEngineForce().x + " N");
            TextScr.output(455, 40, "Fuel " + (int)((100F * M.fuel) / M.maxFuel) + "% Nitro " + (int)((100F * M.nitro) / M.maxNitro) + "%");
            TextScr.output(455, 20, "MPrs " + (int)(1000F * EI.engines[j].getManifoldPressure()) + " mBar");
            TextScr.output(640, 140, "---Controls---");
            TextScr.output(640, 120, "A/C: " + (CT.bHasAileronControl ? "" : "AIL ") + (CT.bHasElevatorControl ? "" : "ELEV ") + (CT.bHasRudderControl ? "" : "RUD ") + (Gears.bIsHydroOperable ? "" : "GEAR "));
            TextScr.output(640, 100, "ENG: " + (EI.engines[j].isHasControlThrottle() ? "" : "THTL ") + (EI.engines[j].isHasControlProp() ? "" : "PROP ") + (EI.engines[j].isHasControlMix() ? "" : "MIX ") + (EI.engines[j].isHasControlCompressor() ? "" : "SUPC ") + (EI.engines[j].isPropAngleDeviceOperational() ? "" : "GVRNR "));
            TextScr.output(640, 80, "PIL: (" + (int)(AS.getPilotHealth(0) * 100F) + "%)");
            TextScr.output(640, 60, "Sens: " + CT.Sensitivity);
            TextScr.output(400, 500, "+");
            TextScr.output(400, 400, "|");
            TextScr.output((int)(400F + 200F * CT.AileronControl), (int)(500F - 200F * CT.ElevatorControl), "+");
            TextScr.output((int)(400F + 200F * CT.RudderControl), 400, "|");
            TextScr.output(5, 200, "AOA = " + AOA);
            TextScr.output(5, 220, "Mass = " + M.getFullMass());
            TextScr.output(5, 320, "AERON TR = " + CT.trimAileron);
            TextScr.output(5, 300, "VATOR TR = " + CT.trimElevator);
            TextScr.output(5, 280, "RUDDR TR = " + CT.trimRudder);
            TextScr.output(245, 160, " pF = " + EI.engines[0].zatizeni * 100D + "%/hr");
            hpOld = hpOld * 0.95F + (0.05F * EI.engines[0].w * EI.engines[0].engineMoment) / 746F;
            TextScr.output(245, 180, " hp = " + hpOld);
            TextScr.output(245, 200, " eMoment = " + EI.engines[0].engineMoment);
            TextScr.output(245, 220, " pMoment = " + EI.engines[0].propMoment);
        }
        if(!Realism.Limited_Fuel)
            superFuel = M.fuel = Math.max(superFuel, M.fuel);
        AP.update(f);
        ((Aircraft)actor).netUpdateWayPoint();
        CT.update(f, (float)Vflow.x, EI, true);
        float f7 = (float)(Vflow.x * Vflow.x) / 11000F;
        if(f7 > 1.0F)
            f7 = 1.0F;
        ForceFeedback.fxSetSpringGain(f7);
        if(CT.saveWeaponControl[0] || CT.saveWeaponControl[1] || CT.saveWeaponControl[2])
            dangerEM();
        Wing.setFlaps(CT.getFlap());
        FMupdate(f);
        EI.update(f);
        Gravity = M.getFullMass() * Atmosphere.g();
        M.computeFullJ(J, J0);
        if(Realism.G_Limits)
        {
            if(G_ClassCoeff < 0.0F || !((Aircraft)actor instanceof TypeBomber))
                Current_G_Limit = ReferenceForce / M.getFullMass() - M.pylonCoeff;
            else
                Current_G_Limit = ReferenceForce / M.getFullMass();
            setLimitLoad(Current_G_Limit);
        }
        if(isTick(44, 0))
        {
            AS.update(f * 44F);
            ((Aircraft)actor).rareAction(f * 44F, true);
            M.computeParasiteMass(CT.Weapons);
            Sq.computeParasiteDrag(CT, CT.Weapons);
            checkAirborneState();
            putScareShpere();
            dangerEMAces();
            if(turnOffCollisions && !Gears.onGround && (double)getAltitude() - Engine.land().HQ_Air(Loc.x, Loc.y) > 30D)
                turnOffCollisions = false;
        }
        Or.wrap();
        if(Realism.Wind_N_Turbulence)
            World.wind().getVector(Loc, Vwind);
        else
            Vwind.set(0.0D, 0.0D, 0.0D);
        Vair.sub(Vwld, Vwind);
        Or.transformInv(Vair, Vflow);
        Density = Atmosphere.density((float)Loc.z);
        AOA = RAD2DEG(-(float)Math.atan2(Vflow.z, Vflow.x));
        AOS = RAD2DEG((float)Math.atan2(Vflow.y, Vflow.x));
        indSpeed = getSpeed() * (float)Math.sqrt(Density / 1.225F);
        Mach = V / Atmosphere.sonicSpeed((float)Loc.z);
        float fDragFactor = 1.0F;
        float fDragParasiteFactor = 1.0F;
        
        if (this.Ss.allParamsSet) { // calculate Mach Drag if parameters are set
          float fMachDrag = this.Ss.getDragFactorForMach(Mach);
          fDragFactor = (float)Math.sqrt(fMachDrag);
          fDragParasiteFactor = (float)Math.pow(fMachDrag, 5);
//          Density *= this.Ss.getDragFactorForMach(Mach);
//          NetSafeLog.training(super.actor, "YM: " + this.Ss.getDragFactorForMach(Mach)
//                  +" Mach: " + Mach);
        } else {
//          Density *= this.Ss.getDragFactorForMach(Mach);
          if (this.Ss.getDragFactorForMach(Mach) > 1.0F) {
//            NetSafeLog.training(super.actor, "CAUTION: Default Mach Drag Parameters used!");
          }
//          NetSafeLog.training(super.actor, "NM: " + this.Ss.getDragFactorForMach(Mach)
//                  +" Mach: " + Mach);
        }
        if(Mach > 0.8F)
            Mach = 0.8F;
        Kq = 1.0F / (float)Math.sqrt(1.0F - Mach * Mach);
        q_ = Density * V2 * 0.5F;
        double d1 = Loc.z - Gears.screenHQ;
        if(d1 < 0.0D)
            d1 = 0.0D;
        float f1 = CT.getAileron() * 14F;
        f1 = Arms.WING_V * (float)Math.sin(DEG2RAD(AOS)) + SensRoll * ailerInfluence * (1.0F - 0.1F * CT.getFlap()) * f1;
        double d2 = 0.0D;
        double d4 = 0.0D;
        if(EI.engines[0].getType() < 2)
        {
            d2 = EI.engines[0].addVflow;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = 0.5D * EI.engines[0].addVside;
        }
        Vn.set(-Arms.GCENTER, 0.84999999999999998D * (double)Arms.WING_END, -0.5D);
        Vn.cross(W, Vn);
        Vn.add(Vflow);
        float f12 = f1 - RAD2DEG((float)Math.atan2(Vn.z, Vn.x));
        Vn.x += 0.070000000000000007D * d2;
        double d = Vn.lengthSquared();
        d *= 0.5F * Density;
        f7 = f1 - RAD2DEG((float)Math.atan2(Vn.z + 0.070000000000000007D * d4 * (double)EI.getPropDirSign(), Vn.x));
        float f14 = 0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwl.x = -d * (double)(Wing.new_Cx(f7) + f14 + GearCX * CT.getGear() + radiatorCX * (EI.getRadiatorPos() + CT.getCockpitDoor()) + Sq.dragAirbrakeCx * CT.getAirBrake() + Sq.dragChuteCx * CT.getDragChute());
        Cwl.z = d * (double)Wing.new_Cy(f7) * (double)Kq;
        if(fmsfxCurrentType != 0)
        {
            if(fmsfxCurrentType == 1)
                Cwl.z *= Aircraft.cvt(fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(fmsfxCurrentType == 2)
            {
                Cwl.z = 0.0D;
                if(Time.current() >= fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
        }
        Vn.set(-Arms.GCENTER, -Arms.WING_END, -0.5D);
        Vn.cross(W, Vn);
        Vn.add(Vflow);
        float f13 = -f1 - RAD2DEG((float)Math.atan2(Vn.z, Vn.x));
        Vn.x += 0.070000000000000007D * d2;
        d = Vn.lengthSquared();
        d *= 0.5F * Density;
        float f9 = -f1 - RAD2DEG((float)Math.atan2(Vn.z - 0.070000000000000007D * d4 * (double)EI.getPropDirSign(), Vn.x));
        f14 = -0.015F * f1;
        if(f14 < 0.0F)
            f14 *= 0.18F;
        Cwr.x = -d * (double)(Wing.new_Cx(f9) + f14 + GearCX * CT.getGear() + radiatorCX * EI.getRadiatorPos() + Sq.dragAirbrakeCx * CT.getAirBrake() + Sq.dragChuteCx * CT.getDragChute());
        Cwr.z = d * (double)Wing.new_Cy(f9) * (double)Kq;
        if(fmsfxCurrentType != 0)
        {
            if(fmsfxCurrentType == 1)
                Cwr.z *= Aircraft.cvt(fmsfxPrevValue, 0.003F, 0.8F, 1.0F, 0.0F);
            if(fmsfxCurrentType == 3)
            {
                Cwr.z = 0.0D;
                if(Time.current() >= fmsfxTimeDisable)
                    doRequestFMSFX(0, 0);
            }
        }
        Cwl.y = -d * (double)Fusel.new_Cy(AOS);
        Cwl.x -= d * (double)Fusel.new_Cx(AOS);
        Cwr.y = -d * (double)Fusel.new_Cy(AOS);
        Cwr.x -= d * (double)Fusel.new_Cx(AOS);
        float f15 = Wing.get_AOA_CRYT();
        double d7 = 1.0D;
        double d8 = 0.5D + 0.40000000000000002D * (double)EI.getPowerOutput();
        double d9 = 1.2D + 0.40000000000000002D * (double)EI.getPowerOutput();
        if(spinCoeff < d8)
            spinCoeff = d8;
        if(spinCoeff > d9)
            spinCoeff = d9;
        f7 = f12;
        f9 = f13;
        if(!Realism.Stalls_N_Spins || Gears.isUnderDeck())
        {
            if(f7 > f9)
            {
                if(Cwl.z < Cwr.z)
                {
                    double d5 = Cwl.z;
                    Cwl.z = Cwr.z;
                    Cwr.z = d5;
                }
            } else
            if(Cwl.z > Cwr.z)
            {
                double d6 = Cwl.z;
                Cwl.z = Cwr.z;
                Cwr.z = d6;
            }
        } else
        if(f7 > f15 || f9 > f15)
        {
            spinCoeff += 0.20000000000000001D * (double)f;
            if((double)Sq.squareRudders > 0.0D && (double)Math.abs(CT.RudderControl) > 0.5D && (double)CT.RudderControl * W.z > 0.0D)
                spinCoeff -= 0.29999999999999999D * (double)f;
            float f16;
            if(f7 > f9)
                f16 = f7;
            else
                f16 = f9;
            turbCoeff = 0.8F * (f16 - f15);
            if(turbCoeff < 1.0F)
                turbCoeff = 1.0F;
            if(turbCoeff > 15F)
                turbCoeff = 15F;
            d7 = 1.0D - 0.20000000000000001D * (double)(f16 - f15);
            if(d7 < 0.20000000000000001D)
                d7 = 0.20000000000000001D;
            d7 /= turbCoeff;
            double d12 = d * (double)turbCoeff * spinCoeff;
            float f17 = getAltitude() - (float)Engine.land().HQ_Air(Loc.x, Loc.y);
            if(f17 < 10F)
                d12 *= 0.1F * f17;
            if(f7 > f9)
            {
                Cwr.x += 0.019999999552965164D * d12 * (double)Sq.spinCxloss;
                Cwl.x -= 0.25D * d12 * (double)Sq.spinCxloss;
                Cwr.z += 0.019999999552965164D * d12 * (double)Sq.spinCyloss;
                Cwl.z -= 0.10000000149011612D * d12 * (double)Sq.spinCyloss;
            } else
            {
                Cwl.x += 0.019999999552965164D * d12 * (double)Sq.spinCxloss;
                Cwr.x -= 0.25D * d12 * (double)Sq.spinCxloss;
                Cwl.z += 0.019999999552965164D * d12 * (double)Sq.spinCyloss;
                Cwr.z -= 0.10000000149011612D * d12 * (double)Sq.spinCyloss;
            }
            rudderInfluence = 1.0F + 0.035F * turbCoeff;
        } else
        {
            turbCoeff = 1.0F;
            d7 = 1.0D;
            spinCoeff -= 0.20000000000000001D * (double)f;
            ailerInfluence = 1.0F;
            rudderInfluence = 1.0F;
        }
        if(isTick(15, 0))
            if(Math.abs(f7 - f9) > 5F)
                ForceFeedback.fxSetSpringZero((f9 - f7) * 0.04F, 0.0F);
            else
                ForceFeedback.fxSetSpringZero(0.0F, 0.0F);
        if(d1 < 0.40000000000000002D * (double)Length)
        {
            double d10 = 1.0D - d1 / (0.40000000000000002D * (double)Length);
            double d13 = 1.0D + 0.20000000000000001D * d10;
            double d16 = 1.0D + 0.20000000000000001D * d10;
            Cwl.z *= d13;
            Cwl.x *= d16;
            Cwr.z *= d13;
            Cwr.x *= d16;
        }
        f1 = CT.getElevator() * (CT.getElevator() <= 0.0F ? 20F : 28F);
        Vn.set(-Arms.VER_STAB, 0.0D, 0.0D);
        Vn.cross(W, Vn);
        Vn.add(Vflow);
        double d11 = Math.sqrt(Vn.y * Vn.y + Vn.z * Vn.z);
        d2 = 0.0D;
        d4 = 0.0D;
        if(EI.engines[0].getType() < 2)
        {
            double d14 = 1.0D + 0.040000000000000001D * (double)Arms.RUDDER;
            d14 = 1.0D / (d14 * d14);
            double d17 = Vn.x + d14 * EI.engines[0].addVflow;
            if(d17 < 0.20000000000000001D)
                d17 = 0.20000000000000001D;
            double d19 = 1.0D - (1.5D * d11) / d17;
            if(d19 < 0.0D)
                d19 = 0.0D;
            double d3 = d19 * d14 * EI.engines[0].addVflow;
            Vn.x += d3;
            double d21 = Math.min(0.0011000000000000001D * Vn.x * Vn.x, 1.0D);
            if(Vn.x < 0.0D)
                d21 = 0.0D;
            if(Realism.Torque_N_Gyro_Effects)
                d4 = d19 * d21 * EI.engines[0].addVside;
        }
        double d15 = (double)Density * Vn.lengthSquared() * 0.5D;
        if(EI.getNum() == 1 && EI.engines[0].getType() < 2)
        {
            f7 = -RAD2DEG((float)Math.atan2(Vn.z - 0.35999999999999999D * d4 * (double)EI.getPropDirSign(), Vn.x)) - 2.0F - 0.002F * V - SensPitch * f1;
            f9 = -RAD2DEG((float)Math.atan2(Vn.z + 0.35999999999999999D * d4 * (double)EI.getPropDirSign(), Vn.x)) - 2.0F - 0.002F * V - SensPitch * f1;
        } else
        {
            f7 = f9 = -RAD2DEG((float)Math.atan2(Vn.z, Vn.x)) - 2.0F - 0.002F * V - SensPitch * f1;
        }
        Chl.x = -d15 * (double)Tail.new_Cx(f7);
        Chl.z = d15 * (double)Tail.new_Cy(f7);
        Chr.x = -d15 * (double)Tail.new_Cx(f9);
        Chr.z = d15 * (double)Tail.new_Cy(f9);
        Chl.y = Chr.y = 0.0D;
        f1 = CT.getRudder() * (Sq.squareRudders >= 0.05F ? 28F : 0.0F);
        float f11;
        if(EI.engines[0].getType() < 2)
            f11 = -RAD2DEG((float)Math.atan2(Vn.y - 0.5D * d4 * (double)EI.getPropDirSign(), Vn.x)) + SensYaw * rudderInfluence * f1;
        else
            f11 = -RAD2DEG((float)Math.atan2(Vn.y, Vn.x)) + SensYaw * rudderInfluence * f1;
        Cv.x = -d15 * (double)Tail.new_Cx(f11);
        Cv.y = d15 * (double)Tail.new_Cy(f11);
        Cv.z = 0.0D;
        if(!Realism.Stalls_N_Spins)
            Cv.y += Cv.y;
        Vn.set(Vflow);
        d = (double)Density * Vn.lengthSquared() * 0.5D;
        Fwl.scale(Sq.liftWingLIn + Sq.liftWingLMid + Sq.liftWingLOut, Cwl);
        Fwr.scale(Sq.liftWingRIn + Sq.liftWingRMid + Sq.liftWingROut, Cwr);
        Fwl.x -= d * (double)((Sq.dragParasiteCx * fDragParasiteFactor) + Sq.dragProducedCx) * 0.5D;
        Fwr.x -= d * (double)((Sq.dragParasiteCx * fDragParasiteFactor) + Sq.dragProducedCx) * 0.5D;
        Fhl.scale((Sq.liftStab + Sq.squareElevators) * 0.5F, Chl);
        Fhr.scale((Sq.liftStab + Sq.squareElevators) * 0.5F, Chr);
        Fv.scale(0.2F + Sq.liftKeel * 1.5F + Sq.squareRudders, Cv);
        Fwl.x *= fDragFactor;
        Fwr.x *= fDragFactor;
        Fhl.x *= fDragFactor;
        Fhr.x *= fDragFactor;
        Fv.x *= fDragFactor;
        AF.set(Fwl);
        AF.add(Fwr);
        if(isNAN(AF))
        {
            AF.set(0.0D, 0.0D, 0.0D);
            flutter();
            if(World.cur().isDebugFM())
                System.out.println("AF isNAN");
        } else
        if(AF.length() > (double)(Gravity * 50F))
        {
            flutter();
            if(World.cur().isDebugFM())
                System.out.println("A > 50.0");
            AF.normalize();
            AF.scale(Gravity * 50F);
        } else
        {
            if(Realism.G_Limits)
            {
                if((getOverload() > getUltimateLoad() + World.Rnd().nextFloat(0.0F, 1.0F) || getOverload() < Negative_G_Limit - World.Rnd().nextFloat(0.0F, 0.5F)) && !Gears.onGround() && World.Rnd().nextInt(0, 100) > 98)
                    if(cutPart < 0)
                        cutWing();
                    else
                        cutPart(cutPart);
                if(getOverload() > Current_G_Limit || getOverload() < Negative_G_Limit)
                {
                    float f19 = Math.abs(getOverload());
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
                                f18 = (max_G_Cycle + Negative_G_Limit) / Negative_G_Limit;
                            f18 *= f18;
                            setSafetyFactor(f18);
                            if(structuralFX != null)
                                structuralFX.play();
                            VmaxAllowed = maxSpeed * (getSafetyFactor() * 0.3F + 0.55F);
                            rD = World.Rnd().nextFloat();
                            if(rD < 0.001F)
                            {
                                if(CT.bHasGearControl)
                                {
                                    ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                                    gearCutCounter++;
                                }
                                Wing.CxMin_0 += 6F * rD;
                                setSafetyFactor(250F * rD);
                                CT.bHasGearControl = false;
                            } else
                            if(rD < 0.002F)
                            {
                                if(CT.bHasGearControl)
                                {
                                    ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 2;
                                }
                                Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0025F)
                            {
                                if(CT.bHasGearControl)
                                {
                                    CT.GearControl = 1.0F;
                                    ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                                    CT.forceGear(1.0F);
                                    gearCutCounter += 2;
                                }
                                Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                CT.bHasGearControl = false;
                            } else
                            if(rD < 0.003F)
                            {
                                if(CT.bHasGearControl)
                                {
                                    CT.GearControl = 1.0F;
                                    ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                                    CT.forceGear(1.0F);
                                    gearCutCounter++;
                                }
                                Wing.CxMin_0 += 3F * rD;
                                setSafetyFactor(125F * rD);
                                CT.bHasGearControl = false;
                            } else
                            if(rD < 0.0035F)
                            {
                                if(CT.bHasGearControl)
                                {
                                    CT.dvGear = 1.0F;
                                    CT.forceGear(1.0F);
                                    CT.GearControl = 1.0F;
                                    ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                                    ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                                    gearCutCounter += 3;
                                }
                                Wing.CxMin_0 += 8F * rD;
                                setSafetyFactor(125F * rD);
                                CT.bHasGearControl = false;
                            } else
                            if(rD < 0.04F)
                                SensYaw *= 0.68F;
                            else
                            if(rD < 0.05F)
                                SensPitch *= 0.68F;
                            else
                            if(rD < 0.06F)
                                SensRoll *= 0.68F;
                            else
                            if(rD < 0.061F)
                                CT.dropFuelTanks();
                            else
                            if(rD < 0.065F)
                                CT.bHasFlapsControl = false;
                            else
                            if(rD >= 0.5F)
                                if(rD < 0.6F)
                                    Wing.CxMin_0 += 0.011F * rD;
                                else
                                if((int)M.getFullMass() % 2 == 0)
                                {
                                    Sq.getClass();
                                    Sq.liftWingROut *= 0.95F - 0.2F * rD;
                                    Wing.CxMin_0 += 0.011F * rD;
                                } else
                                {
                                    Sq.getClass();
                                    Sq.liftWingLOut *= 0.95F - 0.2F * rD;
                                    Wing.CxMin_0 += 0.011F * rD;
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
            if((super.actor instanceof TypeSupersonic) && getOverload() > 15F && !super.Gears.onGround() && World.Rnd().nextInt(0, 100) > 98)
            	cutWing();
            else
            if(getOverload() > 13.5F && !super.Gears.onGround() && World.Rnd().nextInt(0, 100) > 98)
                 cutWing();
            if(indSpeed > 112.5F && World.Rnd().nextInt(0, 100) > 98 && super.CT.getGear() > 0.3F && super.CT.GearControl == 1.0F)
            {
                if(CT.getGear() >= 0.1F && CT.GearControl != 0.0F && !bGearCut)
                    if(!(actor instanceof F4U) && !(super.actor instanceof TypeSupersonic))
                    {
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    } else
                    if(indSpeed > 180F && !(super.actor instanceof TypeSupersonic))
                    {
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    }
                    if(indSpeed > 350F && !(super.actor instanceof TypeSupersonic))
                    {
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 1)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearR2_D0", "GearR2_D0");
                            gearCutCounter++;
                        }
                        if(World.Rnd().nextInt(0, 100) > 76 && gearCutCounter != 2)
                        {
                            ((Aircraft)actor).msgCollision(actor, "GearL2_D0", "GearL2_D0");
                            gearCutCounter += 2;
                        }
                    }
                if(gearCutCounter > 2)
                {
                    bGearCut = true;
                    CT.bHasGearControl = false;
                }
            }
            if(indSpeed > 60.5F && CT.getWing() > 0.1F)
            {
                if(World.Rnd().nextInt(0, 100) > 90 && ((Aircraft)actor).isChunkAnyDamageVisible("WingLMid"))
                    ((Aircraft)actor).msgCollision(actor, "WingLMid_D0", "WingLMid_D0");
                if(World.Rnd().nextInt(0, 100) > 90 && ((Aircraft)actor).isChunkAnyDamageVisible("WingRMid"))
                    ((Aircraft)actor).msgCollision(actor, "WingRMid_D0", "WingRMid_D0");
            }
            if(!(super.actor instanceof TypeSupersonic) && indSpeed > 81F && super.CT.bHasFlapsControl && super.CT.FlapsControl > 0.21F && (indSpeed - 81F) * super.CT.getFlap() > 8F)
            {
                if(World.getPlayerAircraft() == actor && CT.bHasFlapsControl)
                    HUD.log("FailedFlaps");
                CT.bHasFlapsControl = false;
                CT.FlapsControl = 0.0F;
            }
            if(indSpeed > VmaxAllowed && World.Rnd().nextFloat(0.0F, 16F) < indSpeed - VmaxAllowed && World.Rnd().nextInt(0, 99) < 2)
                flutterDamage();
            if(!(super.actor instanceof TypeSupersonic))
            {
                if(indSpeed > 610F)
                {
                    if(World.cur().isDebugFM())
                        System.out.println("*** Sonic overspeed....");
                    flutter();
                }
            } else
            if(!(super.actor instanceof TypeSupersonic) && indSpeed > 310F)
            {
                if(World.cur().isDebugFM())
                    System.out.println("*** Sonic overspeed....");
                flutter();
            }
        }
        AM.set(0.0D, 0.0D, 0.0D);
        if(Math.abs(AOA) < 12F)
        {
            float f2 = Or.getKren();
            if(f2 > 30F)
                f2 = 30F;
            else
            if(f2 < -30F)
                f2 = -30F;
            f2 = (float)((double)f2 * (Math.min(Vflow.x - 50D, 50D) * 0.0030000000260770321D));
            AM.add(-f2 * 0.01F * Gravity, 0.0D, 0.0D);
        }
        if(!getOp(19))
        {
            AM.y += (double)(8F * Sq.squareWing) * Vflow.x;
            AM.z += 200F * Sq.squareWing * EI.getPropDirSign();
        }
        double d18 = (double)CT.getFlap() * 3D;
        if(d18 > 1.0D)
            d18 = 1.0D;
        double d20 = 0.0111D * (double)Math.abs(AOA);
        if(Wing.AOACritL < AOA && AOA < Wing.AOACritH)
            d20 = 0.0D;
        else
        if(AOA >= Wing.AOACritH)
            d20 = Math.min(d20, 0.29999999999999999D * (double)(AOA - Wing.AOACritH));
        else
        if(Wing.AOACritL <= AOA)
            d20 = Math.min(d20, 0.29999999999999999D * (double)(Wing.AOACritL - AOA));
        double d22 = (double)Arms.GCENTER + (double)Arms.GC_FLAPS_SHIFT * d18 * (1.0D - d20) + (double)Arms.GC_AOA_SHIFT * d20;
        TmpV.set(-d22, (double)Arms.WING_MIDDLE * (1.3D + 1.0D * Math.sin(DEG2RAD(AOS))), -Arms.GCENTER_Z);
        TmpV.cross(TmpV, Fwl);
        AM.add(TmpV);
        TmpV.set(-d22, (double)(-Arms.WING_MIDDLE) * (1.3D - 1.0D * Math.sin(DEG2RAD(AOS))), -Arms.GCENTER_Z);
        TmpV.cross(TmpV, Fwr);
        AM.add(TmpV);
        AM.x += su26add;
        TmpV.set(-Arms.HOR_STAB, 1.0D, 0.0D);
        TmpV.cross(TmpV, Fhl);
        AM.add(TmpV);
        TmpV.set(-Arms.HOR_STAB, -1D, 0.0D);
        TmpV.cross(TmpV, Fhr);
        AM.add(TmpV);
        TmpV.set(-Arms.VER_STAB, 0.0D, 1.0D);
        TmpV.cross(TmpV, Fv);
        AM.add(TmpV);
        double d23 = 1.0D - 1.0000000000000001E-005D * (double)indSpeed;
        if(d23 < 0.80000000000000004D)
            d23 = 0.80000000000000004D;
        W.scale(d23);
        if(!Realism.Stalls_N_Spins)
            AM.y += AF.z * 0.5D * Math.sin(DEG2RAD(Math.abs(AOA)));
        if(W.lengthSquared() > 25D)
            W.scale(5D / W.length());
        if(!Realism.Stalls_N_Spins && Vflow.x > 20D)
            W.z += AOS * f;
        AF.add(producedAF);
        AM.add(producedAM);
        producedAF.set(0.0D, 0.0D, 0.0D);
        producedAM.set(0.0D, 0.0D, 0.0D);
        AF.add(EI.producedF);
        AM.add(EI.producedM);
        if(World.cur().diffCur.Torque_N_Gyro_Effects)
        {
            GM.set(EI.getGyro());
            GM.scale(d7);
            AM.add(GM);
        }
        GF.set(0.0D, 0.0D, 0.0D);
        GM.set(0.0D, 0.0D, 0.0D);
        if(Time.tickCounter() % 2 != 0)
            Gears.roughness = Gears.plateFriction(this);
        Gears.ground(this, true);
        int k = 5;
        if(GF.lengthSquared() == 0.0D && GM.lengthSquared() == 0.0D)
            k = 1;
        SummF.add(AF, GF);
        ACmeter.set(SummF);
        ACmeter.scale(1.0F / Gravity);
        TmpV.set(0.0D, 0.0D, -Gravity);
        Or.transformInv(TmpV);
        GF.add(TmpV);
        SummF.add(AF, GF);
        SummM.add(AM, GM);
        double d24 = 1.0D / (double)M.mass;
        LocalAccel.scale(d24, SummF);
        if(Math.abs(getRollAcceleration()) > 50000.5F)
        {
            ForceFeedback.fxPunch(SummM.x <= 0.0D ? -0.9F : 0.9F, 0.0F, 1.0F);
            if(World.cur().isDebugFM())
                System.out.println("Punched (Axial = " + SummM.x + ")");
        }
        if(Math.abs(getOverload() - lastAcc) > 0.5F)
        {
            ForceFeedback.fxPunch(World.Rnd().nextFloat(-0.5F, 0.5F), -0.9F, getSpeed() * 0.05F);
            if(World.cur().isDebugFM())
                System.out.println("Punched (Lat = " + Math.abs(getOverload() - lastAcc) + ")");
        }
        lastAcc = getOverload();
        if(isNAN(AM))
        {
            AM.set(0.0D, 0.0D, 0.0D);
            flutter();
            if(World.cur().isDebugFM())
                System.out.println("AM isNAN");
        } else
        if(AM.length() > (double)(Gravity * 150F))
        {
            flutter();
            if(World.cur().isDebugFM())
                System.out.println("SummM > 150g");
            AM.normalize();
            AM.scale(Gravity * 150F);
        }
        dryFriction -= 0.01D;
        if(Gears.gearsChanged)
            dryFriction = 1.0F;
        if(Gears.nOfPoiOnGr > 0)
            dryFriction += 0.02F;
        if(dryFriction < 1.0F)
            dryFriction = 1.0F;
        if(dryFriction > 32F)
            dryFriction = 32F;
        float f20 = 4F * (0.25F - EI.getPowerOutput());
        if(f20 < 0.0F)
            f20 = 0.0F;
        f20 *= f20;
        f20 *= dryFriction;
        float f21 = f20 * M.mass * M.mass;
        if(!brakeShoe && (Gears.nOfPoiOnGr == 0 && Gears.nOfGearsOnGr < 3 || f20 == 0.0F || SummM.lengthSquared() > (double)(2.0F * f21) || SummF.lengthSquared() > (double)(80F * f21) || W.lengthSquared() > (double)(0.00014F * f20) || Vwld.lengthSquared() > (double)(0.09F * f20)))
        {
            double d25 = 1.0D / (double)k;
            for(int l = 0; l < k; l++)
            {
                SummF.add(AF, GF);
                SummM.add(AM, GM);
                AW.x = ((J.y - J.z) * W.y * W.z + SummM.x) / J.x;
                AW.y = ((J.z - J.x) * W.z * W.x + SummM.y) / J.y;
                AW.z = ((J.x - J.y) * W.x * W.y + SummM.z) / J.z;
                TmpV.scale(d25 * (double)f, AW);
                W.add(TmpV);
                Or.transform(W, Vn);
                TmpV.scale(d25 * (double)f, W);
                Or.increment((float)(-RAD2DEG(TmpV.z)), (float)(-RAD2DEG(TmpV.y)), (float)RAD2DEG(TmpV.x));
                Or.transformInv(Vn, W);
                TmpV.scale(d24, SummF);
                Or.transform(TmpV);
                Accel.set(TmpV);
                TmpV.scale(d25 * (double)f);
                Vwld.add(TmpV);
                TmpV.scale(d25 * (double)f, Vwld);
                TmpP.set(TmpV);
                Loc.add(TmpP);
                GF.set(0.0D, 0.0D, 0.0D);
                GM.set(0.0D, 0.0D, 0.0D);
                if(l < k - 1)
                {
                    Gears.ground(this, true);
                    TmpV.set(0.0D, 0.0D, -Gravity);
                    Or.transformInv(TmpV);
                    GF.add(TmpV);
                }
            }

            for(int i1 = 0; i1 < 3; i1++)
            {
                Gears.gWheelAngles[i1] = (Gears.gWheelAngles[i1] + (float)Math.toDegrees(Math.atan((Gears.gVelocity[i1] * (double)f) / 0.375D))) % 360F;
                Gears.gVelocity[i1] *= 0.94999998807907104D;
            }

            HM.chunkSetAngles("GearL1_D0", 0.0F, -Gears.gWheelAngles[0], 0.0F);
            HM.chunkSetAngles("GearR1_D0", 0.0F, -Gears.gWheelAngles[1], 0.0F);
            HM.chunkSetAngles("GearC1_D0", 0.0F, -Gears.gWheelAngles[2], 0.0F);
        }
        if(Leader != null && isTick(128, 97) && Actor.isAlive(Leader.actor) && !Gears.onGround)
        {
            float f22 = (float)Loc.distance(Leader.Loc);
            if(f22 > 3000F)
                Voice.speakDeviateBig((Aircraft)Leader.actor);
            else
            if(f22 > 1700F)
                Voice.speakDeviateSmall((Aircraft)Leader.actor);
        }
        shakeLevel = 0.0F;
        if(Gears.onGround())
        {
            shakeLevel += (30D * Gears.roughness * Vrel.length()) / (double)M.mass;
        } else
        {
            if(indSpeed > 10F)
            {
                float f23 = (float)Math.sin(Math.toRadians(Math.abs(AOA)));
                if(f23 > 0.02F)
                {
                    f23 *= f23;
                    shakeLevel += 0.07F * (f23 - 0.0004F) * (indSpeed - 10F);
                    if(isTick(30, 0) && shakeLevel > 0.6F)
                        HUD.log(stallStringID, "Stall");
                }
            }
            if(indSpeed > 35F)
            {
                if(CT.bHasGearControl && (Gears.lgear || Gears.rgear) && CT.getGear() > 0.0F)
                    shakeLevel += 0.004F * CT.getGear() * (indSpeed - 35F);
                if(CT.getFlap() > 0.0F)
                    shakeLevel += 0.004F * CT.getFlap() * (indSpeed - 35F);
            }
        }
        if(indSpeed > VmaxAllowed * 0.8F)
            shakeLevel = 0.01F * (indSpeed - VmaxAllowed * 0.8F);
        if(World.cur().diffCur.Head_Shake)
        {
            shakeLevel += producedShakeLevel;
            producedShakeLevel *= 0.9F;
        }
        if(shakeLevel > 1.0F)
            shakeLevel = 1.0F;
        ForceFeedback.fxShake(shakeLevel);
        if(World.cur().diffCur.Blackouts_N_Redouts)
            calcOverLoad(f, true);
    }

    private void calcOverLoad(float f, boolean flag)
    {
        GFactors theGFactors = new GFactors();
        float fGPosLimit = 2.3F;
        float fGNegLimit = 2.3F;
        if ((Aircraft)actor instanceof TypeGSuit) {
          ((TypeGSuit)actor).getGFactors(theGFactors);
        }
        fGPosLimit *= theGFactors.getPosGToleranceFactor();
        fGNegLimit *= theGFactors.getNegGToleranceFactor();
        if(f > 1.0F)
            f = 1.0F;
        if(Gears.onGround() || !flag)
        {
            plAccel.set(0.0D, 0.0D, 0.0D);
        } else
        {
            plAccel.set(getAccel());
            plAccel.scale(0.10199999809265137D);
        }
        plAccel.z += 0.5D;
        Or.transformInv(plAccel);
        float f1 = -0.5F + (float)plAccel.z;
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
        java.text.DecimalFormat twoPlaces = new java.text.DecimalFormat("+000.00;-000.00");
//        HUD.training("kDT:" + twoPlaces.format(knockDnTime)
//                   + " kUT:" + twoPlaces.format(knockUpTime)
//                   + " iDT:" + twoPlaces.format(indiffDnTime)
//                   + " iUT:" + twoPlaces.format(indiffUpTime));
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
            CT.Sensitivity = 1.0F - (saveDeep - 0.8F);
            if(CT.Sensitivity < 0.0F)
                CT.Sensitivity = 0.0F;
        } else
        if(saveDeep < -0.4F)
        {
            CT.Sensitivity = 1.0F + (saveDeep + 0.4F);
            if(CT.Sensitivity < 0.0F)
                CT.Sensitivity = 0.0F;
        } else
        {
            CT.Sensitivity = 1.0F;
        }
        CT.Sensitivity *= AS.getPilotHealth(0);
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

    public void gunMomentum(Vector3d vector3d, boolean flag)
    {
        producedAM.x += vector3d.x;
        producedAM.y += vector3d.y;
        producedAM.z += vector3d.z;
        float f = (float)vector3d.length() * 3.5E-005F;
        if(flag && f > 0.5F)
            f *= 0.05F;
        if(producedShakeLevel < f)
            producedShakeLevel = f;
    }

    public boolean RealMode;
    public float indSpeed;
    private static int stallStringID = HUD.makeIdLog();
    public DifficultySettings Realism;
    Vector3d Cwl;
    Vector3d Cwr;
    Vector3d Chl;
    Vector3d Chr;
    Vector3d Cv;
    Vector3d Fwl;
    Vector3d Fwr;
    Vector3d Fhl;
    Vector3d Fhr;
    Vector3d Fv;
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
    private SoundFX structuralFX;
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
    private Point3d airborneStartPoint;
    private Point3d TmpP;
    private Vector3d Vn;
    private Vector3d TmpV;
    private Vector3d TmpVd;
    private Vector3d plAccel;

}