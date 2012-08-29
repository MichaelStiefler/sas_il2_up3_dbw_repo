package com.maddox.il2.fm;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.P_51Mustang;
import com.maddox.il2.objects.air.SU_26M2;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.il2.objects.weapons.FuelTankGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGun4andHalfInch;
import com.maddox.rts.Time;

public class Controls
{
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
  public boolean[] WeaponControl;
  public boolean[] saveWeaponControl;
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
  public BulletEmitter[][] Weapons;
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
  private FlightModelMain FM;
  private static float tmpF;
  private static Vector3d tmpV3d = new Vector3d();

  public boolean bHasBayDoors = false;
  private float fSaveCockpitDoor;
  private float fSaveCockpitDoorControl;
  private float fSaveSideDoor;
  private float fSaveSideDoorControl;
  public boolean bMoveSideDoor = false;

  private int COCKPIT_DOOR = 1;
  private int SIDE_DOOR = 2;

  private int LEFT = 1;
  private int RIGHT = 2;
  private int iToggleRocketSide = this.LEFT;
  private int iToggleBombSide = this.LEFT;
  private long lWeaponTime = System.currentTimeMillis();
  private boolean bIsMustang = false;

  public Controls(FlightModelMain paramFlightModelMain)
  {
    this.Sensitivity = 1.0F;
    this.PowerControl = 0.0F;
    this.GearControl = 0.0F;
    this.wingControl = 0.0F;
    this.cockpitDoorControl = 0.0F;
    this.arrestorControl = 0.5F;
    this.FlapsControl = 0.0F;
    this.AileronControl = 0.0F;
    this.ElevatorControl = 0.0F;
    this.RudderControl = 0.0F;
    this.BrakeControl = 0.0F;
    this.StepControl = 1.0F;
    this.bStepControlAuto = true;
    this.MixControl = 1.0F;
    this.MagnetoControl = 3;
    this.CompressorControl = 0;
    this.BayDoorControl = 0.0F;
    this.AirBrakeControl = 0.0F;
    this.trimAileronControl = 0.0F;
    this.trimElevatorControl = 0.0F;
    this.trimRudderControl = 0.0F;
    this.trimAileron = 0.0F;
    this.trimElevator = 0.0F;
    this.trimRudder = 0.0F;
    this.RadiatorControl = 0.0F;
    this.bRadiatorControlAuto = true;
    this.StabilizerControl = false;
    this.WeaponControl = new boolean[20];
    this.saveWeaponControl = new boolean[4];
    this.bHasGearControl = true;
    this.bHasWingControl = false;
    this.bHasCockpitDoorControl = false;
    this.bHasArrestorControl = false;
    this.bHasFlapsControl = true;
    this.bHasFlapsControlRed = false;
    this.bHasAileronControl = true;
    this.bHasElevatorControl = true;
    this.bHasRudderControl = true;
    this.bHasBrakeControl = true;
    this.bHasAirBrakeControl = true;
    this.bHasLockGearControl = true;
    this.bHasAileronTrim = true;
    this.bHasRudderTrim = true;
    this.bHasElevatorTrim = true;
    this.Weapons = new BulletEmitter[20][];
    this.Step = 1.0F;
    this.AilThr = 100.0F;
    this.AilThr3 = 1000000.0F;
    this.RudThr = 100.0F;
    this.RudThr2 = 10000.0F;
    this.ElevThr = 112.0F;
    this.ElevThr2 = 12544.0F;
    this.dvGear = 0.2F;
    this.dvWing = 0.1F;
    this.dvCockpitDoor = 0.1F;
    this.dvAirbrake = 0.5F;
    this.FM = paramFlightModelMain;
  }

  public void set(Controls paramControls)
  {
    this.PowerControl = paramControls.PowerControl;
    this.GearControl = paramControls.GearControl;
    this.arrestorControl = paramControls.arrestorControl;
    this.FlapsControl = paramControls.FlapsControl;
    this.AileronControl = paramControls.AileronControl;
    this.ElevatorControl = paramControls.ElevatorControl;
    this.RudderControl = paramControls.RudderControl;
    this.BrakeControl = paramControls.BrakeControl;
    this.BayDoorControl = paramControls.BayDoorControl;
    this.AirBrakeControl = paramControls.AirBrakeControl;
    this.dvGear = paramControls.dvGear;
    this.dvWing = paramControls.dvWing;
    this.dvCockpitDoor = paramControls.dvCockpitDoor;
    this.dvAirbrake = paramControls.dvAirbrake;
  }

  public void CalcTresholds()
  {
    this.AilThr3 = (this.AilThr * this.AilThr * this.AilThr);
    this.RudThr2 = (this.RudThr * this.RudThr);
    this.ElevThr2 = (this.ElevThr * this.ElevThr);
  }

  public void setLanded()
  {
    if (this.bHasGearControl)
      this.GearControl = (this.Gear = 1.0F);
    else
      this.Gear = 1.0F;
    this.FlapsControl = (this.Flaps = 0.0F);
    this.StepControl = 1.0F;
    this.bStepControlAuto = true;
    this.bRadiatorControlAuto = true;
    this.BayDoorControl = 0.0F;
    this.AirBrakeControl = 0.0F;
  }

  public void setFixedGear(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.Gear = 1.0F;
      this.GearControl = 0.0F;
    }
  }

  public void setGearAirborne()
  {
    if (this.bHasGearControl)
      this.GearControl = (this.Gear = 0.0F);
  }

  public void setGear(float paramFloat)
  {
    this.Gear = paramFloat;
  }

  public void setGearBraking()
  {
    this.Brake = 1.0F;
  }

  public void forceFlaps(float paramFloat)
  {
    this.Flaps = paramFloat;
  }

  public void forceGear(float paramFloat)
  {
    if (this.bHasGearControl)
      this.Gear = (this.GearControl = paramFloat);
    else
      setFixedGear(true);
  }

  public void forceWing(float paramFloat)
  {
    this.wing = paramFloat;
    this.FM.doRequestFMSFX(1, (int)(100.0F * paramFloat));
    ((Aircraft)this.FM.actor).moveWingFold(paramFloat);
  }

  public void forceArrestor(float paramFloat)
  {
    this.arrestor = paramFloat;
    ((Aircraft)this.FM.actor).moveArrestorHook(paramFloat);
  }

  public void setPowerControl(float paramFloat)
  {
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (paramFloat > 1.1F)
      paramFloat = 1.1F;
    this.PowerControl = paramFloat;
  }

  public float getPowerControl()
  {
    return this.PowerControl;
  }

  public void setStepControl(float paramFloat)
  {
    if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    this.StepControl = paramFloat;
  }

  public void setStepControlAuto(boolean paramBoolean)
  {
    this.bStepControlAuto = paramBoolean;
  }

  public float getStepControl()
  {
    return this.StepControl;
  }

  public boolean getStepControlAuto()
  {
    return this.bStepControlAuto;
  }

  public void setRadiatorControl(float paramFloat)
  {
    if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    this.RadiatorControl = paramFloat;
  }

  public void setRadiatorControlAuto(boolean paramBoolean, EnginesInterface paramEnginesInterface)
  {
    this.bRadiatorControlAuto = paramBoolean;
    if (paramEnginesInterface.getFirstSelected() != null)
      this.radiator = paramEnginesInterface.getFirstSelected().getControlRadiator();
  }

  public float getRadiatorControl()
  {
    return this.RadiatorControl;
  }

  public boolean getRadiatorControlAuto()
  {
    return this.bRadiatorControlAuto;
  }

  public float getRadiator()
  {
    return this.radiator;
  }

  public void setMixControl(float paramFloat)
  {
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (paramFloat > 1.2F)
      paramFloat = 1.2F;
    this.MixControl = paramFloat;
  }

  public float getMixControl()
  {
    return this.MixControl;
  }

  public void setAfterburnerControl(boolean paramBoolean)
  {
    this.afterburnerControl = paramBoolean;
  }

  public boolean getAfterburnerControl()
  {
    return this.afterburnerControl;
  }

  public void setMagnetoControl(int paramInt)
  {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > 3)
      paramInt = 3;
    this.MagnetoControl = paramInt;
  }

  public int getMagnetoControl()
  {
    return this.MagnetoControl;
  }

  public void setCompressorControl(int paramInt)
  {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > this.FM.EI.engines[0].compressorMaxStep)
      paramInt = this.FM.EI.engines[0].compressorMaxStep;
    this.CompressorControl = paramInt;
  }

  public int getCompressorControl()
  {
    return this.CompressorControl;
  }

  public void setTrimAileronControl(float paramFloat)
  {
    this.trimAileronControl = paramFloat;
  }

  public float getTrimAileronControl()
  {
    return this.trimAileronControl;
  }

  public void setTrimElevatorControl(float paramFloat)
  {
    this.trimElevatorControl = paramFloat;
  }

  public float getTrimElevatorControl()
  {
    return this.trimElevatorControl;
  }

  public void setTrimRudderControl(float paramFloat)
  {
    this.trimRudderControl = paramFloat;
  }

  public float getTrimRudderControl()
  {
    return this.trimRudderControl;
  }

  public void interpolate(Controls paramControls, float paramFloat)
  {
    this.PowerControl = FMMath.interpolate(this.PowerControl, paramControls.PowerControl, paramFloat);
    this.FlapsControl = FMMath.interpolate(this.FlapsControl, paramControls.FlapsControl, paramFloat);
    this.AileronControl = FMMath.interpolate(this.AileronControl, paramControls.AileronControl, paramFloat);
    this.ElevatorControl = FMMath.interpolate(this.ElevatorControl, paramControls.ElevatorControl, paramFloat);
    this.RudderControl = FMMath.interpolate(this.RudderControl, paramControls.RudderControl, paramFloat);
    this.BrakeControl = FMMath.interpolate(this.BrakeControl, paramControls.BrakeControl, paramFloat);
  }

  public float getGear()
  {
    return this.Gear;
  }

  public float getWing()
  {
    return this.wing;
  }

  public float getCockpitDoor()
  {
    return this.cockpitDoor;
  }

  public void forceCockpitDoor(float paramFloat)
  {
    this.cockpitDoor = paramFloat;
  }

  public float getArrestor()
  {
    return this.arrestor;
  }

  public float getFlap()
  {
    return this.Flaps;
  }

  public float getAileron()
  {
    return this.Ailerons;
  }

  public float getElevator()
  {
    return this.Elevators;
  }

  public float getRudder()
  {
    return this.Rudder;
  }

  public float getBrake()
  {
    return this.Brake;
  }

  public float getPower()
  {
    return this.Power;
  }

  public float getStep()
  {
    return this.Step;
  }

  public float getBayDoor()
  {
    return this.BayDoorControl;
  }

  public float getAirBrake()
  {
    return this.airBrake;
  }

  private float filter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f1 = (float)Math.exp(-paramFloat1 / paramFloat4);
    float f2 = paramFloat2 + (paramFloat3 - paramFloat2) * f1;
    if (f2 < paramFloat2)
    {
      f2 += paramFloat5 * paramFloat1;
      if (f2 > paramFloat2)
        f2 = paramFloat2;
    }
    else if (f2 > paramFloat2)
    {
      f2 -= paramFloat5 * paramFloat1;
      if (f2 < paramFloat2)
        f2 = paramFloat2;
    }
    return f2;
  }

  private float clamp01(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      paramFloat = 0.0F;
    }
    else if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    return paramFloat;
  }

  private float clamp0115(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      paramFloat = 0.0F;
    }
    else if (paramFloat > 1.1F)
      paramFloat = 1.1F;
    return paramFloat;
  }

  private float clamp11(float paramFloat)
  {
    if (paramFloat < -1.0F) {
      paramFloat = -1.0F;
    }
    else if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    return paramFloat;
  }

  private float clampA(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < -paramFloat2) {
      paramFloat1 = -paramFloat2;
    }
    else if (paramFloat1 > paramFloat2)
      paramFloat1 = paramFloat2;
    return paramFloat1;
  }

  public void setActiveDoor(int paramInt)
  {
    if ((paramInt != this.SIDE_DOOR) && (this.bMoveSideDoor)) {
      this.fSaveSideDoor = this.cockpitDoor;
      this.fSaveSideDoorControl = this.cockpitDoorControl;
      this.cockpitDoor = this.fSaveCockpitDoor;
      this.cockpitDoorControl = this.fSaveCockpitDoorControl;
      this.bMoveSideDoor = false;
    }
    else if ((paramInt == this.SIDE_DOOR) && (!this.bMoveSideDoor)) {
      this.fSaveCockpitDoor = this.cockpitDoor;
      this.fSaveCockpitDoorControl = this.cockpitDoorControl;
      this.cockpitDoor = this.fSaveSideDoor;
      this.cockpitDoorControl = this.fSaveSideDoorControl;
      this.bMoveSideDoor = true;
    }
  }

  public void update(float paramFloat1, float paramFloat2, EnginesInterface paramEnginesInterface, boolean paramBoolean)
  {
    update(paramFloat1, paramFloat2, paramEnginesInterface, paramBoolean, false);
  }

  public void update(float paramFloat1, float paramFloat2, EnginesInterface paramEnginesInterface, boolean paramBoolean1, boolean paramBoolean2)
  {
    float f1 = 1.0F;
    float f2 = 1.0F;
    float f3 = 1.0F;
    float f4 = paramFloat2 * paramFloat2;
    if (paramFloat2 > this.AilThr)
      f1 = Math.max(0.2F, this.AilThr3 / (f4 * paramFloat2));
    if (f4 > this.RudThr2)
      f2 = Math.max(0.2F, this.RudThr2 / f4);
    if (f4 > this.ElevThr2)
      f3 = Math.max(0.4F, this.ElevThr2 / f4);
    f1 *= this.Sensitivity;
    f2 *= this.Sensitivity;
    f3 *= this.Sensitivity;
    if ((this.Elevators >= 0.0F) && (!(this.FM.actor instanceof SU_26M2)))
      f3 = f2;
    if (!paramBoolean2)
    {
      this.PowerControl = clamp0115(this.PowerControl);
      if (paramBoolean1)
        this.Power = this.PowerControl;
      else
        this.Power = filter(paramFloat1, this.PowerControl, this.Power, 5.0F, 0.01F * paramFloat1);
      paramEnginesInterface.setThrottle(this.Power);
    }
    if (!paramBoolean2)
      paramEnginesInterface.setAfterburnerControl(this.afterburnerControl);
    if (!paramBoolean2)
    {
      this.StepControl = clamp01(this.StepControl);
      if ((this.bStepControlAuto) && (paramEnginesInterface.getFirstSelected() != null))
      {
        if (paramEnginesInterface.isSelectionAllowsAutoProp())
        {
          paramEnginesInterface.setPropAuto(true);
        }
        else {
          paramEnginesInterface.setPropAuto(false);
          this.bStepControlAuto = false;
        }
      }
      else {
        this.Step = filter(paramFloat1, this.StepControl, this.Step, 0.2F, 0.02F);
        paramEnginesInterface.setPropAuto(false);
        paramEnginesInterface.setProp(this.Step);
      }
    }
    this.RadiatorControl = clamp01(this.RadiatorControl);
    this.radiator = filter(paramFloat1, this.RadiatorControl, this.radiator, 999.90002F, 0.2F);
    if ((this.bRadiatorControlAuto) && (paramEnginesInterface.getFirstSelected() != null))
    {
      if (paramEnginesInterface.isSelectionAllowsAutoRadiator())
      {
        paramEnginesInterface.updateRadiator(paramFloat1);
      }
      else {
        paramEnginesInterface.setRadiator(this.radiator);
        this.bRadiatorControlAuto = false;
      }
    }
    else {
      paramEnginesInterface.setRadiator(this.radiator);
    }
    if (!paramBoolean2)
      paramEnginesInterface.setMagnetos(this.MagnetoControl);
    if (!paramBoolean2)
      paramEnginesInterface.setCompressorStep(this.CompressorControl);
    if (!paramBoolean2)
      paramEnginesInterface.setMix(this.MixControl);
    if ((this.bHasGearControl) || (paramBoolean2))
    {
      this.GearControl = clamp01(this.GearControl);
      this.Gear = filter(paramFloat1, this.GearControl, this.Gear, 999.90002F, this.dvGear);
    }
    if ((this.bHasAirBrakeControl) || (paramBoolean2))
      this.airBrake = filter(paramFloat1, this.AirBrakeControl, this.airBrake, 999.90002F, this.dvAirbrake);
    if (this.bHasWingControl)
    {
      this.wing = filter(paramFloat1, this.wingControl, this.wing, 999.90002F, this.dvWing);
      if ((this.wing > 0.01F) && (this.wing < 0.99F))
        this.FM.doRequestFMSFX(1, (int)(100.0F * this.wing));
    }
    if (this.bHasCockpitDoorControl)
      this.cockpitDoor = filter(paramFloat1, this.cockpitDoorControl, this.cockpitDoor, 999.90002F, this.dvCockpitDoor);
    if (((this.bHasArrestorControl) || (paramBoolean2)) && ((this.arrestorControl == 0.0F) || (this.arrestorControl == 1.0F)))
      this.arrestor = filter(paramFloat1, this.arrestorControl, this.arrestor, 999.90002F, 0.2F);
    if ((this.bHasFlapsControl) || (paramBoolean2))
    {
      this.FlapsControl = clamp01(this.FlapsControl);
      if (this.Flaps > this.FlapsControl)
        this.Flaps = filter(paramFloat1, this.FlapsControl, this.Flaps, 999.0F, Aircraft.cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.25F));
      else
        this.Flaps = filter(paramFloat1, this.FlapsControl, this.Flaps, 999.0F, Aircraft.cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.02F));
    }
    if (this.StabilizerControl)
    {
      this.AileronControl = (-0.2F * this.FM.Or.getKren() - 2.0F * (float)this.FM.getW().x);
      tmpV3d.set(this.FM.Vwld);
      tmpV3d.normalize();
      float f5 = (float)(-500.0D * (tmpV3d.z - 0.001D));
      if (f5 > 0.8F)
        f5 = 0.8F;
      if (f5 < -0.8F)
        f5 = -0.8F;
      this.ElevatorControl = (f5 - 0.2F * this.FM.Or.getTangage() - 0.05F * this.FM.AOA + 25.0F * (float)this.FM.getW().y);
      this.RudderControl = (-0.2F * this.FM.AOS + 20.0F * (float)this.FM.getW().z);
    }
    if ((this.bHasAileronControl) || (paramBoolean2))
    {
      this.trimAileron = filter(paramFloat1, this.trimAileronControl, this.trimAileron, 999.90002F, 0.25F);
      this.AileronControl = clamp11(this.AileronControl);
      tmpF = clampA(this.AileronControl, f1);
      this.Ailerons = filter(paramFloat1, (1.0F + (this.trimAileron * tmpF <= 0.0F ? 1.0F : -1.0F) * Math.abs(this.trimAileron)) * tmpF + this.trimAileron, this.Ailerons, 0.2F * (1.0F + 0.3F * Math.abs(this.AileronControl)), 0.025F);
    }
    if ((this.bHasElevatorControl) || (paramBoolean2))
    {
      this.trimElevator = filter(paramFloat1, this.trimElevatorControl, this.trimElevator, 999.90002F, 0.25F);
      this.ElevatorControl = clamp11(this.ElevatorControl);
      tmpF = clampA(this.ElevatorControl, f3);
      this.Ev = filter(paramFloat1, (1.0F + (this.trimElevator * tmpF <= 0.0F ? 1.0F : -1.0F) * Math.abs(this.trimElevator)) * tmpF + this.trimElevator, this.Ev, 0.3F * (1.0F + 0.3F * Math.abs(this.ElevatorControl)), 0.022F);
      if ((this.FM.actor instanceof SU_26M2))
        this.Elevators = clamp11(this.Ev);
      else
        this.Elevators = clamp11(this.Ev - 0.25F * (1.0F - f3));
    }
    if ((this.bHasRudderControl) || (paramBoolean2))
    {
      this.trimRudder = filter(paramFloat1, this.trimRudderControl, this.trimRudder, 999.90002F, 0.25F);
      this.RudderControl = clamp11(this.RudderControl);
      tmpF = clampA(this.RudderControl, f2);
      this.Rudder = filter(paramFloat1, (1.0F + (this.trimRudder * tmpF <= 0.0F ? 1.0F : -1.0F) * Math.abs(this.trimRudder)) * tmpF + this.trimRudder, this.Rudder, 0.35F * (1.0F + 0.3F * Math.abs(this.RudderControl)), 0.025F);
    }
    this.BrakeControl = clamp01(this.BrakeControl);
    if ((this.bHasBrakeControl) || (paramBoolean2))
    {
      if (this.BrakeControl > this.Brake)
        this.Brake += 0.3F * paramFloat1;
      else
        this.Brake = this.BrakeControl;
    }
    else {
      this.Brake = 0.0F;
    }
    if (this.tick == Time.tickCounter())
      return;
    this.tick = Time.tickCounter();
    this.CTL = (byte)((this.GearControl <= 0.5F ? 0 : 1) | (this.FlapsControl <= 0.2F ? 0 : 2) | (this.BrakeControl <= 0.2F ? 0 : 4) | (this.RadiatorControl <= 0.5F ? 0 : 8) | (this.BayDoorControl <= 0.5F ? 0 : 16) | (this.AirBrakeControl <= 0.5F ? 0 : 32));
    this.WCT = (byte)(this.WCT & 0xFC);
    this.TWCT &= 252;
    int i = 0;
    for (int j = 1; (i < this.WeaponControl.length) && (j < 256); j <<= 1)
    {
      if (this.WeaponControl[i] != 0)
      {
        this.WCT = (byte)(this.WCT | j);
        this.TWCT |= j;
      }
      i++;
    }

    for (int k = 0; k < 4; k++) {
      this.saveWeaponControl[k] = this.WeaponControl[k];
    }
    for (int m = 0; m < this.Weapons.length; m++)
      if (this.Weapons[m] != null)
      {
        int i1;
        switch (m)
        {
        case 2:
        case 3:
          int n = this.WeaponControl[m] != 0 ? 1 : 0;
          if (n == 0) {
            continue;
          }
          try
          {
            if (((Aircraft)this.FM.actor instanceof P_51Mustang))
              this.bIsMustang = true;
          }
          catch (Throwable localThrowable)
          {
          }
          if ((this.bIsMustang) && (System.currentTimeMillis() <= this.lWeaponTime + 250L / ()Time.speed())) continue;
          i1 = -1;
          for (int i2 = 0; i2 < this.Weapons[m].length; i2 += 2)
          {
            if (((this.Weapons[m][i2] instanceof FuelTankGun)) || (!this.Weapons[m][i2].haveBullets()))
              continue;
            if ((this.bHasBayDoors) && (this.Weapons[m][i2].getHookName().startsWith("_BombSpawn"))) {
              if (this.BayDoorControl == 1.0F)
                this.Weapons[m][i2].shots(n);
            } else {
              if ((!this.bIsMustang) || ((this.Weapons[m][i2] instanceof RocketGun4andHalfInch)) || (((this.Weapons[m][i2] instanceof RocketGun)) && (this.iToggleRocketSide == this.LEFT)) || (((this.Weapons[m][i2] instanceof BombGun)) && (this.iToggleBombSide == this.LEFT)))
              {
                this.Weapons[m][i2].shots(n);
              }
              if (this.Weapons[m][i2].getHookName().startsWith("_BombSpawn"))
                this.BayDoorControl = 1.0F;
            }
            if (((!(this.Weapons[m][i2] instanceof BombGun)) || (((BombGun)this.Weapons[m][i2]).isCassette())) && ((!(this.Weapons[m][i2] instanceof RocketGun)) || (((RocketGun)this.Weapons[m][i2]).isCassette())))
              continue;
            i1 = i2;
            this.lWeaponTime = System.currentTimeMillis();
            break;
          }

          for (int i3 = 1; i3 < this.Weapons[m].length; i3 += 2)
          {
            if (((this.Weapons[m][i3] instanceof FuelTankGun)) || (!this.Weapons[m][i3].haveBullets()))
              continue;
            if ((this.bHasBayDoors) && (this.Weapons[m][i3].getHookName().startsWith("_BombSpawn"))) {
              if (this.BayDoorControl == 1.0F)
                this.Weapons[m][i3].shots(n);
            }
            else if ((!this.bIsMustang) || ((this.Weapons[m][i3] instanceof RocketGun4andHalfInch)) || (((this.Weapons[m][i3] instanceof RocketGun)) && (this.iToggleRocketSide == this.RIGHT)) || (((this.Weapons[m][i3] instanceof BombGun)) && (this.iToggleBombSide == this.RIGHT)))
            {
              this.Weapons[m][i3].shots(n);
            }
            if (((!(this.Weapons[m][i3] instanceof BombGun)) || (((BombGun)this.Weapons[m][i3]).isCassette())) && ((!(this.Weapons[m][i3] instanceof RocketGun)) || (((RocketGun)this.Weapons[m][i3]).isCassette())))
              continue;
            i1 = i3;
            this.lWeaponTime = System.currentTimeMillis();
            break;
          }

          if (i1 != -1) {
            if ((this.Weapons[m][i1] instanceof BombGun)) {
              if (this.iToggleBombSide == this.LEFT) this.iToggleBombSide = this.RIGHT; else {
                this.iToggleBombSide = this.LEFT;
              }
            }
            else if (!(this.Weapons[m][i1] instanceof RocketGun4andHalfInch)) {
              if (this.iToggleRocketSide == this.LEFT) this.iToggleRocketSide = this.RIGHT; else {
                this.iToggleRocketSide = this.LEFT;
              }
            }
          }

          if (this.bIsMustang) continue;
          this.WeaponControl[m] = false; break;
        default:
          for (i1 = 0; i1 < this.Weapons[m].length; i1++)
            this.Weapons[m][i1].shots(this.WeaponControl[m] != 0 ? -1 : 0);
        }
      }
  }

  public float getWeaponMass()
  {
    int i = this.Weapons.length;
    float f = 0.0F;
    for (int j = 0; j < i; j++) {
      if (this.Weapons[j] == null)
        continue;
      int k = this.Weapons[j].length;
      for (int m = 0; m < k; m++)
      {
        BulletEmitter localBulletEmitter = this.Weapons[j][m];
        if ((localBulletEmitter == null) || ((localBulletEmitter instanceof FuelTankGun)))
          continue;
        int n = localBulletEmitter.countBullets();
        if (n < 0)
        {
          n = 1;
          if (((localBulletEmitter instanceof BombGun)) && (((BombGun)localBulletEmitter).isCassette()))
            n = 10;
        }
        f += localBulletEmitter.bulletMassa() * n;
      }

    }

    return f;
  }

  public int getWeaponCount(int paramInt)
  {
    if ((paramInt >= this.Weapons.length) || (this.Weapons[paramInt] == null))
      return 0;
    int i = this.Weapons[paramInt].length;
    int j;
    int k = j = 0;
    for (; j < i; j++)
    {
      BulletEmitter localBulletEmitter = this.Weapons[paramInt][j];
      if ((localBulletEmitter != null) && (!(localBulletEmitter instanceof FuelTankGun))) {
        k += localBulletEmitter.countBullets();
      }
    }
    return k;
  }

  public boolean dropFuelTanks()
  {
    int i = 0;
    for (int j = 0; j < this.Weapons.length; j++) {
      if (this.Weapons[j] == null)
        continue;
      for (int k = 0; k < this.Weapons[j].length; k++) {
        if ((!(this.Weapons[j][k] instanceof FuelTankGun)) || (!this.Weapons[j][k].haveBullets()))
          continue;
        this.Weapons[j][k].shots(1);
        i = 1;
      }

    }

    if (i != 0)
    {
      ((Aircraft)this.FM.actor).replicateDropFuelTanks();
      this.FM.M.onFuelTanksChanged();
    }
    return i;
  }

  public FuelTank[] getFuelTanks()
  {
    int i = 0;
    for (int j = 0; j < this.Weapons.length; j++) {
      if (this.Weapons[j] == null)
        continue;
      for (int k = 0; k < this.Weapons[j].length; k++) {
        if ((this.Weapons[j][k] instanceof FuelTankGun)) {
          i++;
        }
      }
    }
    FuelTank[] arrayOfFuelTank = new FuelTank[i];
    int m;
    for (int n = m = 0; n < this.Weapons.length; n++) {
      if (this.Weapons[n] == null)
        continue;
      for (int i1 = 0; i1 < this.Weapons[n].length; i1++) {
        if ((this.Weapons[n][i1] instanceof FuelTankGun)) {
          arrayOfFuelTank[(m++)] = ((FuelTankGun)this.Weapons[n][i1]).getFuelTank();
        }
      }
    }
    return arrayOfFuelTank;
  }

  public void resetControl(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      this.AileronControl = 0.0F;
      this.Ailerons = 0.0F;
      this.trimAileron = 0.0F;
      break;
    case 1:
      this.ElevatorControl = 0.0F;
      this.Elevators = 0.0F;
      this.trimElevator = 0.0F;
      break;
    case 2:
      this.RudderControl = 0.0F;
      this.Rudder = 0.0F;
      this.trimRudder = 0.0F;
    }
  }
}