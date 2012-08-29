// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Controls.java

package com.maddox.il2.fm;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.SU_26M2;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.il2.objects.weapons.FuelTankGun;
import com.maddox.il2.objects.weapons.RocketBombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            RealFlightModel, FlightModelMain, EnginesInterface, Motor, 
//            FMMath, Mass, AircraftState

public class Controls
{

    public Controls(com.maddox.il2.fm.FlightModelMain flightmodelmain)
    {
        Sensitivity = 1.0F;
        PowerControl = 0.0F;
        GearControl = 0.0F;
        wingControl = 0.0F;
        cockpitDoorControl = 0.0F;
        arrestorControl = 0.5F;
        FlapsControl = 0.0F;
        AileronControl = 0.0F;
        ElevatorControl = 0.0F;
        RudderControl = 0.0F;
        BrakeControl = 0.0F;
        StepControl = 1.0F;
        bStepControlAuto = true;
        MixControl = 1.0F;
        MagnetoControl = 3;
        CompressorControl = 0;
        BayDoorControl = 0.0F;
        AirBrakeControl = 0.0F;
        trimAileronControl = 0.0F;
        trimElevatorControl = 0.0F;
        trimRudderControl = 0.0F;
        trimAileron = 0.0F;
        trimElevator = 0.0F;
        trimRudder = 0.0F;
        RadiatorControl = 0.0F;
        bRadiatorControlAuto = true;
        StabilizerControl = false;
        WeaponControl = new boolean[21];
        saveWeaponControl = new boolean[4];
        bHasGearControl = true;
        bHasWingControl = false;
        bHasCockpitDoorControl = false;
        bHasArrestorControl = false;
        bHasFlapsControl = true;
        bHasFlapsControlRed = false;
        bHasAileronControl = true;
        bHasElevatorControl = true;
        bHasRudderControl = true;
        bHasBrakeControl = true;
        bHasAirBrakeControl = true;
        bHasLockGearControl = true;
        bHasAileronTrim = true;
        bHasRudderTrim = true;
        bHasElevatorTrim = true;
        Weapons = new com.maddox.il2.ai.BulletEmitter[21][];
        Step = 1.0F;
        AilThr = 100F;
        AilThr3 = 1000000F;
        RudThr = 100F;
        RudThr2 = 10000F;
        ElevThr = 112F;
        ElevThr2 = 12544F;
        dvGear = 0.2F;
        dvWing = 0.1F;
        dvCockpitDoor = 0.1F;
        dvAirbrake = 0.5F;
        electricPropDn = 0;
        PowerControlArr = new float[6];
        StepControlArr = new float[6];
        FM = flightmodelmain;
        for(int i = 0; i < 6; i++)
            PowerControlArr[i] = 0.0F;

        for(int j = 0; j < 6; j++)
            StepControlArr[j] = 1.0F;

    }

    public void set(com.maddox.il2.fm.Controls controls)
    {
        PowerControl = controls.PowerControl;
        GearControl = controls.GearControl;
        arrestorControl = controls.arrestorControl;
        FlapsControl = controls.FlapsControl;
        AileronControl = controls.AileronControl;
        ElevatorControl = controls.ElevatorControl;
        RudderControl = controls.RudderControl;
        BrakeControl = controls.BrakeControl;
        BayDoorControl = controls.BayDoorControl;
        AirBrakeControl = controls.AirBrakeControl;
        dvGear = controls.dvGear;
        dvWing = controls.dvWing;
        dvCockpitDoor = controls.dvCockpitDoor;
        dvAirbrake = controls.dvAirbrake;
    }

    public void CalcTresholds()
    {
        AilThr3 = AilThr * AilThr * AilThr;
        RudThr2 = RudThr * RudThr;
        ElevThr2 = ElevThr * ElevThr;
    }

    public void setLanded()
    {
        if(bHasGearControl)
            GearControl = Gear = 1.0F;
        else
            Gear = 1.0F;
        FlapsControl = Flaps = 0.0F;
        StepControl = 1.0F;
        bStepControlAuto = true;
        bRadiatorControlAuto = true;
        BayDoorControl = 0.0F;
        AirBrakeControl = 0.0F;
    }

    public void setFixedGear(boolean flag)
    {
        if(flag)
        {
            Gear = 1.0F;
            GearControl = 0.0F;
        }
    }

    public void setGearAirborne()
    {
        if(bHasGearControl)
            GearControl = Gear = 0.0F;
    }

    public void setGear(float f)
    {
        Gear = f;
    }

    public void setGearBraking()
    {
        Brake = 1.0F;
    }

    public void forceFlaps(float f)
    {
        Flaps = f;
    }

    public void forceGear(float f)
    {
        if(bHasGearControl)
            Gear = GearControl = f;
        else
            setFixedGear(true);
    }

    public void forceWing(float f)
    {
        wing = f;
        FM.doRequestFMSFX(1, (int)(100F * f));
        ((com.maddox.il2.objects.air.Aircraft)FM.actor).moveWingFold(f);
    }

    public void forceArrestor(float f)
    {
        arrestor = f;
        ((com.maddox.il2.objects.air.Aircraft)FM.actor).moveArrestorHook(f);
    }

    public void setPowerControl(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.1F)
            f = 1.1F;
        PowerControl = f;
        for(int i = 0; i < 6; i++)
            if(i < FM.EI.getNum() && FM.EI.bCurControl[i])
                PowerControlArr[i] = f;

    }

    public void setPowerControl(float f, int i)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.1F)
            f = 1.1F;
        PowerControlArr[i] = f;
        if(i == 0)
            PowerControl = f;
    }

    public float getPowerControl()
    {
        return PowerControl;
    }

    public void setStepControl(float f)
    {
        if(!bUseElectricProp)
        {
            if(f > 1.0F)
                f = 1.0F;
            if(f < 0.0F)
                f = 0.0F;
            StepControl = f;
            for(int i = 0; i < 6; i++)
                if(i < FM.EI.getNum() && FM.EI.bCurControl[i])
                    StepControlArr[i] = f;

            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "PropPitch", new java.lang.Object[] {
                new Integer(java.lang.Math.round(getStepControl() * 100F))
            });
        }
    }

    public void setStepControl(float f, int i)
    {
        if(!bUseElectricProp)
        {
            if(f > 1.0F)
                f = 1.0F;
            if(f < 0.0F)
                f = 0.0F;
            StepControlArr[i] = f;
            if(!getStepControlAuto(i - 1))
                com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "PropPitch", new java.lang.Object[] {
                    new Integer(java.lang.Math.round(getStepControl(i) * 100F))
                });
        }
    }

    public void setStepControlAuto(boolean flag)
    {
        bStepControlAuto = flag;
    }

    public float getStepControl()
    {
        return StepControl;
    }

    public boolean getStepControlAuto()
    {
        return bStepControlAuto;
    }

    public boolean getStepControlAuto(int i)
    {
        if(i < FM.EI.getNum())
            return !FM.EI.engines[i].isHasControlProp() || FM.EI.engines[i].getControlPropAuto();
        else
            return true;
    }

    public float getStepControl(int i)
    {
        return StepControlArr[i];
    }

    public void setElectricPropUp(boolean flag)
    {
        if(bUseElectricProp)
            if(flag)
                electricPropUp = 1;
            else
                electricPropUp = 0;
    }

    public void setElectricPropDn(boolean flag)
    {
        if(bUseElectricProp)
            if(flag)
                electricPropDn = 1;
            else
                electricPropDn = 0;
    }

    public void setRadiatorControl(float f)
    {
        if(f > 1.0F)
            f = 1.0F;
        if(f < 0.0F)
            f = 0.0F;
        RadiatorControl = f;
    }

    public void setRadiatorControlAuto(boolean flag, com.maddox.il2.fm.EnginesInterface enginesinterface)
    {
        bRadiatorControlAuto = flag;
        if(enginesinterface.getFirstSelected() != null)
            radiator = enginesinterface.getFirstSelected().getControlRadiator();
    }

    public float getRadiatorControl()
    {
        return RadiatorControl;
    }

    public boolean getRadiatorControlAuto()
    {
        return bRadiatorControlAuto;
    }

    public float getRadiator()
    {
        return radiator;
    }

    public void setMixControl(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 1.2F)
            f = 1.2F;
        MixControl = f;
    }

    public float getMixControl()
    {
        return MixControl;
    }

    public void setAfterburnerControl(boolean flag)
    {
        afterburnerControl = flag;
    }

    public boolean getAfterburnerControl()
    {
        return afterburnerControl;
    }

    public void setMagnetoControl(int i)
    {
        if(i < 0)
            i = 0;
        if(i > 3)
            i = 3;
        MagnetoControl = i;
    }

    public int getMagnetoControl()
    {
        return MagnetoControl;
    }

    public void setCompressorControl(int i)
    {
        if(i < 0)
            i = 0;
        if(i > FM.EI.engines[0].compressorMaxStep)
            i = FM.EI.engines[0].compressorMaxStep;
        CompressorControl = i;
    }

    public int getCompressorControl()
    {
        return CompressorControl;
    }

    public void setTrimAileronControl(float f)
    {
        trimAileronControl = f;
    }

    public float getTrimAileronControl()
    {
        return trimAileronControl;
    }

    public void setTrimElevatorControl(float f)
    {
        trimElevatorControl = f;
    }

    public float getTrimElevatorControl()
    {
        return trimElevatorControl;
    }

    public void setTrimRudderControl(float f)
    {
        trimRudderControl = f;
    }

    public float getTrimRudderControl()
    {
        return trimRudderControl;
    }

    public void interpolate(com.maddox.il2.fm.Controls controls, float f)
    {
        PowerControl = com.maddox.il2.fm.FMMath.interpolate(PowerControl, controls.PowerControl, f);
        FlapsControl = com.maddox.il2.fm.FMMath.interpolate(FlapsControl, controls.FlapsControl, f);
        AileronControl = com.maddox.il2.fm.FMMath.interpolate(AileronControl, controls.AileronControl, f);
        ElevatorControl = com.maddox.il2.fm.FMMath.interpolate(ElevatorControl, controls.ElevatorControl, f);
        RudderControl = com.maddox.il2.fm.FMMath.interpolate(RudderControl, controls.RudderControl, f);
        BrakeControl = com.maddox.il2.fm.FMMath.interpolate(BrakeControl, controls.BrakeControl, f);
    }

    public float getGear()
    {
        return Gear;
    }

    public float getWing()
    {
        return wing;
    }

    public float getCockpitDoor()
    {
        return cockpitDoor;
    }

    public void forceCockpitDoor(float f)
    {
        cockpitDoor = f;
    }

    public float getArrestor()
    {
        return arrestor;
    }

    public float getFlap()
    {
        return Flaps;
    }

    public float getAileron()
    {
        return Ailerons;
    }

    public float getElevator()
    {
        return Elevators;
    }

    public float getRudder()
    {
        return Rudder;
    }

    public float getBrake()
    {
        return Brake;
    }

    public float getPower()
    {
        return Power;
    }

    public float getStep()
    {
        return Step;
    }

    public float getBayDoor()
    {
        return BayDoorControl;
    }

    public float getAirBrake()
    {
        return airBrake;
    }

    private float filter(float f, float f1, float f2, float f3, float f4)
    {
        float f5 = (float)java.lang.Math.exp(-f / f3);
        float f6 = f1 + (f2 - f1) * f5;
        if(f6 < f1)
        {
            f6 += f4 * f;
            if(f6 > f1)
                f6 = f1;
        } else
        if(f6 > f1)
        {
            f6 -= f4 * f;
            if(f6 < f1)
                f6 = f1;
        }
        return f6;
    }

    private float clamp01(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        else
        if(f > 1.0F)
            f = 1.0F;
        return f;
    }

    private float clamp0115(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        else
        if(f > 1.1F)
            f = 1.1F;
        return f;
    }

    private float clamp11(float f)
    {
        if(f < -1F)
            f = -1F;
        else
        if(f > 1.0F)
            f = 1.0F;
        return f;
    }

    private float clampA(float f, float f1)
    {
        if(f < -f1)
            f = -f1;
        else
        if(f > f1)
            f = f1;
        return f;
    }

    public void update(float f, float f1, com.maddox.il2.fm.EnginesInterface enginesinterface, boolean flag)
    {
        update(f, f1, enginesinterface, flag, false);
    }

    public void update(float f, float f1, com.maddox.il2.fm.EnginesInterface enginesinterface, boolean flag, boolean flag1)
    {
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 1.0F;
        float f5 = f1 * f1;
        if(f1 > AilThr)
            f2 = java.lang.Math.max(0.2F, AilThr3 / (f5 * f1));
        if(f5 > RudThr2)
            f3 = java.lang.Math.max(0.2F, RudThr2 / f5);
        if(f5 > ElevThr2)
            f4 = java.lang.Math.max(0.4F, ElevThr2 / f5);
        f2 *= Sensitivity;
        f3 *= Sensitivity;
        f4 *= Sensitivity;
        if(Elevators >= 0.0F && !(FM.actor instanceof com.maddox.il2.objects.air.SU_26M2))
            f4 = f3;
        if(!flag1)
            if(FM instanceof com.maddox.il2.fm.RealFlightModel)
            {
                float f6 = 0.0F;
                for(int j1 = 0; j1 < enginesinterface.getNum(); j1++)
                {
                    PowerControlArr[j1] = clamp0115(PowerControlArr[j1]);
                    enginesinterface.engines[j1].setControlThrottle(PowerControlArr[j1]);
                    if(PowerControlArr[j1] > f6)
                        f6 = PowerControlArr[j1];
                }

                Power = f6;
            } else
            {
                PowerControl = clamp0115(PowerControl);
                if(flag)
                    Power = PowerControl;
                else
                    Power = filter(f, PowerControl, Power, 5F, 0.01F * f);
                enginesinterface.setThrottle(Power);
            }
        if(!flag1)
            enginesinterface.setAfterburnerControl(afterburnerControl);
        if(!flag1)
        {
            StepControl = clamp01(StepControl);
            if(bUseElectricProp && (FM instanceof com.maddox.il2.fm.RealFlightModel))
            {
                enginesinterface.setPropAuto(bStepControlAuto);
                int i = electricPropUp - electricPropDn;
                if(i < 0)
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "elPropDn");
                else
                if(i > 0)
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "elPropUp");
                enginesinterface.setPropDelta(i);
            }
            if(bStepControlAuto && enginesinterface.getFirstSelected() != null)
            {
                if(enginesinterface.isSelectionAllowsAutoProp())
                {
                    enginesinterface.setPropAuto(true);
                } else
                {
                    enginesinterface.setPropAuto(false);
                    bStepControlAuto = false;
                }
            } else
            if(FM instanceof com.maddox.il2.fm.RealFlightModel)
            {
                if(!bUseElectricProp)
                {
                    for(int j = 0; j < enginesinterface.getNum(); j++)
                    {
                        StepControlArr[j] = clamp01(StepControlArr[j]);
                        enginesinterface.engines[j].setControlPropAuto(false);
                        enginesinterface.engines[j].setControlProp(StepControlArr[j]);
                    }

                }
            } else
            {
                Step = filter(f, StepControl, Step, 0.2F, 0.02F);
                enginesinterface.setPropAuto(false);
                enginesinterface.setProp(Step);
            }
        }
        RadiatorControl = clamp01(RadiatorControl);
        radiator = filter(f, RadiatorControl, radiator, 999.9F, 0.2F);
        if(bRadiatorControlAuto && enginesinterface.getFirstSelected() != null)
        {
            if(enginesinterface.isSelectionAllowsAutoRadiator())
            {
                enginesinterface.updateRadiator(f);
            } else
            {
                enginesinterface.setRadiator(radiator);
                bRadiatorControlAuto = false;
            }
        } else
        {
            enginesinterface.setRadiator(radiator);
        }
        if(!flag1)
            enginesinterface.setMagnetos(MagnetoControl);
        if(!flag1 && flag)
            enginesinterface.setCompressorStep(CompressorControl);
        if(!flag1)
            enginesinterface.setMix(MixControl);
        if(bHasGearControl || flag1)
        {
            GearControl = clamp01(GearControl);
            Gear = filter(f, GearControl, Gear, 999.9F, dvGear);
        }
        if(bHasAirBrakeControl || flag1)
            airBrake = filter(f, AirBrakeControl, airBrake, 999.9F, dvAirbrake);
        if(bHasWingControl)
        {
            wing = filter(f, wingControl, wing, 999.9F, dvWing);
            if(wing > 0.01F && wing < 0.99F)
                FM.doRequestFMSFX(1, (int)(100F * wing));
        }
        if(bHasCockpitDoorControl)
            cockpitDoor = filter(f, cockpitDoorControl, cockpitDoor, 999.9F, dvCockpitDoor);
        if((bHasArrestorControl || flag1) && (arrestorControl == 0.0F || arrestorControl == 1.0F))
            arrestor = filter(f, arrestorControl, arrestor, 999.9F, 0.2F);
        if(bHasFlapsControl || flag1)
        {
            FlapsControl = clamp01(FlapsControl);
            if(Flaps > FlapsControl)
                Flaps = filter(f, FlapsControl, Flaps, 999F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeedKMH(), 150F, 280F, 0.15F, 0.25F));
            else
                Flaps = filter(f, FlapsControl, Flaps, 999F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeedKMH(), 150F, 280F, 0.15F, 0.02F));
        }
        if(StabilizerControl)
        {
            AileronControl = -0.2F * FM.Or.getKren() - 2.0F * (float)FM.getW().x;
            tmpV3d.set(FM.Vwld);
            tmpV3d.normalize();
            float f7 = (float)(-500D * (tmpV3d.z - 0.001D));
            if(f7 > 0.8F)
                f7 = 0.8F;
            if(f7 < -0.8F)
                f7 = -0.8F;
            ElevatorControl = (f7 - 0.2F * FM.Or.getTangage() - 0.05F * FM.AOA) + 25F * (float)FM.getW().y;
            RudderControl = -0.2F * FM.AOS + 20F * (float)FM.getW().z;
        }
        if(bHasAileronControl || flag1)
        {
            trimAileron = filter(f, trimAileronControl, trimAileron, 999.9F, 0.25F);
            AileronControl = clamp11(AileronControl);
            tmpF = clampA(AileronControl, f2);
            Ailerons = filter(f, (1.0F + (trimAileron * tmpF <= 0.0F ? 1.0F : -1F) * java.lang.Math.abs(trimAileron)) * tmpF + trimAileron, Ailerons, 0.2F * (1.0F + 0.3F * java.lang.Math.abs(AileronControl)), 0.025F);
        }
        if(bHasElevatorControl || flag1)
        {
            trimElevator = filter(f, trimElevatorControl, trimElevator, 999.9F, 0.25F);
            ElevatorControl = clamp11(ElevatorControl);
            tmpF = clampA(ElevatorControl, f4);
            Ev = filter(f, (1.0F + (trimElevator * tmpF <= 0.0F ? 1.0F : -1F) * java.lang.Math.abs(trimElevator)) * tmpF + trimElevator, Ev, 0.3F * (1.0F + 0.3F * java.lang.Math.abs(ElevatorControl)), 0.022F);
            if(FM.actor instanceof com.maddox.il2.objects.air.SU_26M2)
                Elevators = clamp11(Ev);
            else
                Elevators = clamp11(Ev - 0.25F * (1.0F - f4));
        }
        if(bHasRudderControl || flag1)
        {
            trimRudder = filter(f, trimRudderControl, trimRudder, 999.9F, 0.25F);
            RudderControl = clamp11(RudderControl);
            tmpF = clampA(RudderControl, f3);
            Rudder = filter(f, (1.0F + (trimRudder * tmpF <= 0.0F ? 1.0F : -1F) * java.lang.Math.abs(trimRudder)) * tmpF + trimRudder, Rudder, 0.35F * (1.0F + 0.3F * java.lang.Math.abs(RudderControl)), 0.025F);
        }
        BrakeControl = clamp01(BrakeControl);
        if(bHasBrakeControl || flag1)
        {
            if(BrakeControl > Brake)
                Brake = Brake + 0.3F * f;
            else
                Brake = BrakeControl;
        } else
        {
            Brake = 0.0F;
        }
        if(tick == com.maddox.rts.Time.tickCounter())
            return;
        tick = com.maddox.rts.Time.tickCounter();
        CTL = (byte)((GearControl <= 0.5F ? 0 : 1) | (FlapsControl <= 0.2F ? 0 : 2) | (BrakeControl <= 0.2F ? 0 : 4) | (RadiatorControl <= 0.5F ? 0 : 8) | (BayDoorControl <= 0.5F ? 0 : 0x10) | (AirBrakeControl <= 0.5F ? 0 : 0x20));
        WCT &= 0xfc;
        TWCT &= 0xfc;
        int k = 0;
        for(int j2 = 1; k < WeaponControl.length && j2 < 256; j2 <<= 1)
        {
            if(WeaponControl[k])
            {
                WCT |= j2;
                TWCT |= j2;
            }
            k++;
        }

        for(int l = 0; l < 4; l++)
            saveWeaponControl[l] = WeaponControl[l];

        for(int i1 = 0; i1 < Weapons.length; i1++)
        {
            if(Weapons[i1] == null)
                continue;
            switch(i1)
            {
            case 2: // '\002'
            case 3: // '\003'
                int k2 = WeaponControl[i1] ? 1 : 0;
                if(k2 == 0)
                    continue;
                for(int k1 = 0; k1 < Weapons[i1].length; k1 += 2)
                {
                    if((Weapons[i1][k1] instanceof com.maddox.il2.objects.weapons.FuelTankGun) || !Weapons[i1][k1].haveBullets())
                        continue;
                    Weapons[i1][k1].shots(k2);
                    if(Weapons[i1][k1].getHookName().startsWith("_BombSpawn"))
                        BayDoorControl = 1.0F;
                    if((Weapons[i1][k1] instanceof com.maddox.il2.objects.weapons.BombGun) && !((com.maddox.il2.objects.weapons.BombGun)Weapons[i1][k1]).isCassette() || (Weapons[i1][k1] instanceof com.maddox.il2.objects.weapons.RocketGun) && !((com.maddox.il2.objects.weapons.RocketGun)Weapons[i1][k1]).isCassette() || (Weapons[i1][k1] instanceof com.maddox.il2.objects.weapons.RocketBombGun) && !((com.maddox.il2.objects.weapons.RocketBombGun)Weapons[i1][k1]).isCassette())
                        break;
                }

                for(int l1 = 1; l1 < Weapons[i1].length; l1 += 2)
                {
                    if((Weapons[i1][l1] instanceof com.maddox.il2.objects.weapons.FuelTankGun) || !Weapons[i1][l1].haveBullets())
                        continue;
                    Weapons[i1][l1].shots(k2);
                    if((Weapons[i1][l1] instanceof com.maddox.il2.objects.weapons.BombGun) && !((com.maddox.il2.objects.weapons.BombGun)Weapons[i1][l1]).isCassette() || (Weapons[i1][l1] instanceof com.maddox.il2.objects.weapons.RocketGun) && !((com.maddox.il2.objects.weapons.RocketGun)Weapons[i1][l1]).isCassette() || (Weapons[i1][l1] instanceof com.maddox.il2.objects.weapons.RocketBombGun) && !((com.maddox.il2.objects.weapons.RocketBombGun)Weapons[i1][l1]).isCassette())
                        break;
                }

                WeaponControl[i1] = false;
                break;

            default:
                boolean flag2 = false;
                for(int i2 = 0; i2 < Weapons[i1].length; i2++)
                {
                    Weapons[i1][i2].shots(WeaponControl[i1] ? -1 : 0);
                    flag2 = flag2 || Weapons[i1][i2].haveBullets();
                }

                if(WeaponControl[i1] && !flag2 && FM.isPlayers())
                    com.maddox.il2.objects.effects.ForceFeedback.fxTriggerShake(i1, false);
                break;
            }
        }

    }

    public float getWeaponMass()
    {
        int i = Weapons.length;
        float f = 0.0F;
        for(int k = 0; k < i; k++)
        {
            if(Weapons[k] == null)
                continue;
            int l = Weapons[k].length;
            for(int j = 0; j < l; j++)
            {
                com.maddox.il2.ai.BulletEmitter bulletemitter = Weapons[k][j];
                if(bulletemitter == null || (bulletemitter instanceof com.maddox.il2.objects.weapons.FuelTankGun))
                    continue;
                int i1 = bulletemitter.countBullets();
                if(i1 < 0)
                {
                    i1 = 1;
                    if((bulletemitter instanceof com.maddox.il2.objects.weapons.BombGun) && ((com.maddox.il2.objects.weapons.BombGun)bulletemitter).isCassette())
                        i1 = 10;
                }
                f += bulletemitter.bulletMassa() * (float)i1;
            }

        }

        return f;
    }

    public int getWeaponCount(int i)
    {
        if(i >= Weapons.length || Weapons[i] == null)
            return 0;
        int l = Weapons[i].length;
        int k;
        int j = k = 0;
        for(; k < l; k++)
        {
            com.maddox.il2.ai.BulletEmitter bulletemitter = Weapons[i][k];
            if(bulletemitter != null && !(bulletemitter instanceof com.maddox.il2.objects.weapons.FuelTankGun))
                j += bulletemitter.countBullets();
        }

        return j;
    }

    public boolean dropFuelTanks()
    {
        boolean flag = false;
        for(int i = 0; i < Weapons.length; i++)
        {
            if(Weapons[i] == null)
                continue;
            for(int j = 0; j < Weapons[i].length; j++)
                if((Weapons[i][j] instanceof com.maddox.il2.objects.weapons.FuelTankGun) && Weapons[i][j].haveBullets())
                {
                    Weapons[i][j].shots(1);
                    flag = true;
                }

        }

        if(flag)
        {
            ((com.maddox.il2.objects.air.Aircraft)FM.actor).replicateDropFuelTanks();
            FM.M.onFuelTanksChanged();
        }
        return flag;
    }

    public boolean dropExternalStores(boolean flag)
    {
        boolean flag1 = ((com.maddox.il2.objects.air.Aircraft)FM.actor).dropExternalStores(flag);
        if(flag1)
        {
            FM.AS.externalStoresDropped = true;
            ((com.maddox.il2.objects.air.Aircraft)FM.actor).replicateDropExternalStores();
        }
        return flag1;
    }

    public com.maddox.il2.objects.weapons.FuelTank[] getFuelTanks()
    {
        int i1 = 0;
        for(int i = 0; i < Weapons.length; i++)
        {
            if(Weapons[i] == null)
                continue;
            for(int k = 0; k < Weapons[i].length; k++)
                if(Weapons[i][k] instanceof com.maddox.il2.objects.weapons.FuelTankGun)
                    i1++;

        }

        com.maddox.il2.objects.weapons.FuelTank afueltank[] = new com.maddox.il2.objects.weapons.FuelTank[i1];
        int j1;
        for(int j = j1 = 0; j < Weapons.length; j++)
        {
            if(Weapons[j] == null)
                continue;
            for(int l = 0; l < Weapons[j].length; l++)
                if(Weapons[j][l] instanceof com.maddox.il2.objects.weapons.FuelTankGun)
                    afueltank[j1++] = ((com.maddox.il2.objects.weapons.FuelTankGun)Weapons[j][l]).getFuelTank();

        }

        return afueltank;
    }

    public void resetControl(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            AileronControl = 0.0F;
            Ailerons = 0.0F;
            trimAileron = 0.0F;
            break;

        case 1: // '\001'
            ElevatorControl = 0.0F;
            Elevators = 0.0F;
            trimElevator = 0.0F;
            break;

        case 2: // '\002'
            RudderControl = 0.0F;
            Rudder = 0.0F;
            trimRudder = 0.0F;
            break;
        }
    }

    public float Sensitivity;
    public float PowerControl;
    public boolean afterburnerControl;
    public float GearControl;
    public float wingControl;
    public float cockpitDoorControl;
    public float arrestorControl;
    public float FlapsControl;
    public float AileronControl;
    public float ElevatorControl;
    public float RudderControl;
    public float BrakeControl;
    private float StepControl;
    private boolean bStepControlAuto;
    private float MixControl;
    private int MagnetoControl;
    private int CompressorControl;
    public float BayDoorControl;
    public float AirBrakeControl;
    private float trimAileronControl;
    private float trimElevatorControl;
    private float trimRudderControl;
    public float trimAileron;
    public float trimElevator;
    public float trimRudder;
    private float RadiatorControl;
    private boolean bRadiatorControlAuto;
    public boolean StabilizerControl;
    public boolean WeaponControl[];
    public boolean saveWeaponControl[];
    public boolean bHasGearControl;
    public boolean bHasWingControl;
    public boolean bHasCockpitDoorControl;
    public boolean bHasArrestorControl;
    public boolean bHasFlapsControl;
    public boolean bHasFlapsControlRed;
    public boolean bHasAileronControl;
    public boolean bHasElevatorControl;
    public boolean bHasRudderControl;
    public boolean bHasBrakeControl;
    public boolean bHasAirBrakeControl;
    public boolean bHasLockGearControl;
    public boolean bHasAileronTrim;
    public boolean bHasRudderTrim;
    public boolean bHasElevatorTrim;
    public com.maddox.il2.ai.BulletEmitter Weapons[][];
    public byte CTL;
    public byte WCT;
    public int TWCT;
    private float Power;
    private float Gear;
    private float wing;
    private float cockpitDoor;
    private float arrestor;
    private float Flaps;
    private float Ailerons;
    private float Elevators;
    private float Rudder;
    private float Brake;
    private float Step;
    private float radiator;
    private float airBrake;
    private float Ev;
    private int tick;
    public float AilThr;
    public float AilThr3;
    public float RudThr;
    public float RudThr2;
    public float ElevThr;
    public float ElevThr2;
    public float dvGear;
    public float dvWing;
    public float dvCockpitDoor;
    public float dvAirbrake;
    public int electricPropUp;
    public int electricPropDn;
    public boolean bUseElectricProp;
    private final int POWERCONTROLNUM = 6;
    public float PowerControlArr[];
    public float StepControlArr[];
    private com.maddox.il2.fm.FlightModelMain FM;
    private static float tmpF;
    private static com.maddox.JGP.Vector3d tmpV3d = new Vector3d();

}
