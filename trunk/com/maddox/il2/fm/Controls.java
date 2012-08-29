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

public class Controls
{
  public float Sensitivity = 1.0F;
  public float PowerControl = 0.0F;
  public boolean afterburnerControl;
  public float GearControl = 0.0F;
  public float wingControl = 0.0F;
  public float cockpitDoorControl = 0.0F;
  public float arrestorControl = 0.5F;
  public float FlapsControl = 0.0F;
  public float AileronControl = 0.0F;
  public float ElevatorControl = 0.0F;
  public float RudderControl = 0.0F;
  public float BrakeControl = 0.0F;
  private float StepControl = 1.0F;
  private boolean bStepControlAuto = true;
  private float MixControl = 1.0F;
  private int MagnetoControl = 3;
  private int CompressorControl = 0;
  public float BayDoorControl = 0.0F;
  public float AirBrakeControl = 0.0F;
  private float trimAileronControl = 0.0F;
  private float trimElevatorControl = 0.0F;
  private float trimRudderControl = 0.0F;
  public float trimAileron = 0.0F;
  public float trimElevator = 0.0F;
  public float trimRudder = 0.0F;
  private float RadiatorControl = 0.0F;
  private boolean bRadiatorControlAuto = true;
  public boolean StabilizerControl = false;
  public boolean[] WeaponControl = new boolean[21];

  public boolean[] saveWeaponControl = new boolean[4];

  public boolean bHasGearControl = true;
  public boolean bHasWingControl = false;
  public boolean bHasCockpitDoorControl = false;
  public boolean bHasArrestorControl = false;
  public boolean bHasFlapsControl = true;
  public boolean bHasFlapsControlRed = false;
  public boolean bHasAileronControl = true;
  public boolean bHasElevatorControl = true;
  public boolean bHasRudderControl = true;
  public boolean bHasBrakeControl = true;

  public boolean bHasAirBrakeControl = true;

  public boolean bHasLockGearControl = true;
  public boolean bHasAileronTrim = true;
  public boolean bHasRudderTrim = true;
  public boolean bHasElevatorTrim = true;

  public BulletEmitter[][] Weapons = new BulletEmitter[21][];
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
  private float Step = 1.0F;
  private float radiator;
  private float airBrake;
  private float Ev;
  private int tick;
  public float AilThr = 100.0F;
  public float AilThr3 = 1000000.0F;
  public float RudThr = 100.0F;
  public float RudThr2 = 10000.0F;
  public float ElevThr = 112.0F;
  public float ElevThr2 = 12544.0F;

  public float dvGear = 0.2F;
  public float dvWing = 0.1F;
  public float dvCockpitDoor = 0.1F;
  public float dvAirbrake = 0.5F;
  public int electricPropUp;
  public int electricPropDn = 0;
  public boolean bUseElectricProp;
  private final int POWERCONTROLNUM = 6;
  public float[] PowerControlArr = new float[6];
  public float[] StepControlArr = new float[6];
  private FlightModelMain FM;
  private static float tmpF;
  private static Vector3d tmpV3d = new Vector3d();

  public Controls(FlightModelMain paramFlightModelMain)
  {
    this.FM = paramFlightModelMain;

    for (int i = 0; i < 6; i++) {
      this.PowerControlArr[i] = 0.0F;
    }
    for (i = 0; i < 6; i++)
      this.StepControlArr[i] = 1.0F;
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

  public void CalcTresholds() {
    this.AilThr3 = (this.AilThr * this.AilThr * this.AilThr);
    this.RudThr2 = (this.RudThr * this.RudThr);
    this.ElevThr2 = (this.ElevThr * this.ElevThr);
  }

  public void setLanded()
  {
    if (this.bHasGearControl)
      this.GearControl = (this.Gear = 1.0F);
    else {
      this.Gear = 1.0F;
    }
    this.FlapsControl = (this.Flaps = 0.0F);
    this.StepControl = 1.0F;
    this.bStepControlAuto = true;
    this.bRadiatorControlAuto = true;
    this.BayDoorControl = 0.0F;
    this.AirBrakeControl = 0.0F;
  }

  public void setFixedGear(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.Gear = 1.0F;
      this.GearControl = 0.0F;
    }
  }

  public void setGearAirborne() {
    if (this.bHasGearControl)
      this.GearControl = (this.Gear = 0.0F);
  }

  public void setGear(float paramFloat) {
    this.Gear = paramFloat;
  }
  public void setGearBraking() {
    this.Brake = 1.0F;
  }
  public void forceFlaps(float paramFloat) {
    this.Flaps = paramFloat;
  }

  public void forceGear(float paramFloat) {
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

  public void forceArrestor(float paramFloat) {
    this.arrestor = paramFloat;
    ((Aircraft)this.FM.actor).moveArrestorHook(paramFloat);
  }

  public void setPowerControl(float paramFloat)
  {
    if (paramFloat < 0.0F) paramFloat = 0.0F;
    if (paramFloat > 1.1F) paramFloat = 1.1F;
    this.PowerControl = paramFloat;

    for (int i = 0; i < 6; i++)
      if ((i < this.FM.EI.getNum()) && (this.FM.EI.bCurControl[i] != 0))
        this.PowerControlArr[i] = paramFloat;
  }

  public void setPowerControl(float paramFloat, int paramInt)
  {
    if (paramFloat < 0.0F) {
      paramFloat = 0.0F;
    }
    if (paramFloat > 1.1F) {
      paramFloat = 1.1F;
    }
    this.PowerControlArr[paramInt] = paramFloat;
    if (paramInt == 0)
      this.PowerControl = paramFloat;
  }

  public float getPowerControl() {
    return this.PowerControl;
  }

  public void setStepControl(float paramFloat)
  {
    if (!this.bUseElectricProp)
    {
      if (paramFloat > 1.0F) paramFloat = 1.0F;
      if (paramFloat < 0.0F) paramFloat = 0.0F;
      this.StepControl = paramFloat;

      for (int i = 0; i < 6; i++) {
        if ((i >= this.FM.EI.getNum()) || (this.FM.EI.bCurControl[i] == 0))
          continue;
        this.StepControlArr[i] = paramFloat;
      }

      HUD.log(AircraftHotKeys.hudLogPowerId, "PropPitch", new Object[] { new Integer(Math.round(getStepControl() * 100.0F)) });
    }
  }

  public void setStepControl(float paramFloat, int paramInt)
  {
    if (!this.bUseElectricProp)
    {
      if (paramFloat > 1.0F) {
        paramFloat = 1.0F;
      }
      if (paramFloat < 0.0F) {
        paramFloat = 0.0F;
      }
      this.StepControlArr[paramInt] = paramFloat;

      if (!getStepControlAuto(paramInt))
        HUD.log(AircraftHotKeys.hudLogPowerId, "PropPitch", new Object[] { new Integer(Math.round(getStepControl(paramInt) * 100.0F)) });
    }
  }

  public void setStepControlAuto(boolean paramBoolean)
  {
    this.bStepControlAuto = paramBoolean;
  }
  public float getStepControl() {
    return this.StepControl;
  }
  public boolean getStepControlAuto() {
    return this.bStepControlAuto;
  }

  public boolean getStepControlAuto(int paramInt)
  {
    if (paramInt < this.FM.EI.getNum())
    {
      return (!this.FM.EI.engines[paramInt].isHasControlProp()) || (this.FM.EI.engines[paramInt].getControlPropAuto());
    }

    return true;
  }

  public float getStepControl(int paramInt)
  {
    return this.StepControlArr[paramInt];
  }

  public void setElectricPropUp(boolean paramBoolean)
  {
    if (this.bUseElectricProp)
    {
      if (paramBoolean)
      {
        this.electricPropUp = 1;
      }
      else
        this.electricPropUp = 0;
    }
  }

  public void setElectricPropDn(boolean paramBoolean)
  {
    if (this.bUseElectricProp)
    {
      if (paramBoolean)
      {
        this.electricPropDn = 1;
      }
      else
        this.electricPropDn = 0;
    }
  }

  public void setRadiatorControl(float paramFloat) {
    if (paramFloat > 1.0F) paramFloat = 1.0F;
    if (paramFloat < 0.0F) paramFloat = 0.0F;
    this.RadiatorControl = paramFloat;
  }
  public void setRadiatorControlAuto(boolean paramBoolean, EnginesInterface paramEnginesInterface) {
    this.bRadiatorControlAuto = paramBoolean;
    if (paramEnginesInterface.getFirstSelected() != null)
      this.radiator = paramEnginesInterface.getFirstSelected().getControlRadiator();
  }

  public float getRadiatorControl() {
    return this.RadiatorControl;
  }
  public boolean getRadiatorControlAuto() {
    return this.bRadiatorControlAuto;
  }
  public float getRadiator() {
    return this.radiator;
  }

  public void setMixControl(float paramFloat) {
    if (paramFloat < 0.0F) paramFloat = 0.0F;
    if (paramFloat > 1.2F) paramFloat = 1.2F;
    this.MixControl = paramFloat;
  }
  public float getMixControl() {
    return this.MixControl;
  }

  public void setAfterburnerControl(boolean paramBoolean) {
    this.afterburnerControl = paramBoolean;
  }
  public boolean getAfterburnerControl() {
    return this.afterburnerControl;
  }

  public void setMagnetoControl(int paramInt) {
    if (paramInt < 0) paramInt = 0;
    if (paramInt > 3) paramInt = 3;
    this.MagnetoControl = paramInt;
  }
  public int getMagnetoControl() {
    return this.MagnetoControl;
  }

  public void setCompressorControl(int paramInt) {
    if (paramInt < 0) paramInt = 0;
    if (paramInt > this.FM.EI.engines[0].compressorMaxStep) paramInt = this.FM.EI.engines[0].compressorMaxStep;
    this.CompressorControl = paramInt;
  }
  public int getCompressorControl() {
    return this.CompressorControl;
  }

  public void setTrimAileronControl(float paramFloat) {
    this.trimAileronControl = paramFloat;
  }
  public float getTrimAileronControl() {
    return this.trimAileronControl;
  }
  public void setTrimElevatorControl(float paramFloat) {
    this.trimElevatorControl = paramFloat;
  }
  public float getTrimElevatorControl() {
    return this.trimElevatorControl;
  }
  public void setTrimRudderControl(float paramFloat) {
    this.trimRudderControl = paramFloat;
  }
  public float getTrimRudderControl() {
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
  public float getGear() {
    return this.Gear; } 
  public float getWing() { return this.wing; } 
  public float getCockpitDoor() {
    return this.cockpitDoor;
  }
  public void forceCockpitDoor(float paramFloat) {
    this.cockpitDoor = paramFloat;
  }
  public float getArrestor() { return this.arrestor; } 
  public float getFlap() { return this.Flaps; } 
  public float getAileron() { return this.Ailerons; } 
  public float getElevator() { return this.Elevators; } 
  public float getRudder() { return this.Rudder; } 
  public float getBrake() { return this.Brake; } 
  public float getPower() { return this.Power; } 
  public float getStep() { return this.Step; } 
  public float getBayDoor() {
    return this.BayDoorControl; } 
  public float getAirBrake() { return this.airBrake; }

  private float filter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    float f1 = (float)Math.exp(-paramFloat1 / paramFloat4);

    float f2 = paramFloat2 + (paramFloat3 - paramFloat2) * f1;
    if (f2 < paramFloat2) { f2 += paramFloat5 * paramFloat1; if (f2 > paramFloat2) f2 = paramFloat2; 
    }
    else if (f2 > paramFloat2) { f2 -= paramFloat5 * paramFloat1; if (f2 < paramFloat2) f2 = paramFloat2; 
    }
    return f2;
  }

  private float clamp01(float paramFloat) {
    if (paramFloat < 0.0F) paramFloat = 0.0F; else if (paramFloat > 1.0F) paramFloat = 1.0F;
    return paramFloat;
  }

  private float clamp0115(float paramFloat) {
    if (paramFloat < 0.0F) paramFloat = 0.0F; else if (paramFloat > 1.1F) paramFloat = 1.1F;
    return paramFloat;
  }

  private float clamp11(float paramFloat) {
    if (paramFloat < -1.0F) paramFloat = -1.0F; else if (paramFloat > 1.0F) paramFloat = 1.0F;
    return paramFloat;
  }

  private float clampA(float paramFloat1, float paramFloat2) {
    if (paramFloat1 < -paramFloat2) paramFloat1 = -paramFloat2; else if (paramFloat1 > paramFloat2) paramFloat1 = paramFloat2;
    return paramFloat1;
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
    if (paramFloat2 > this.AilThr) f1 = Math.max(0.2F, this.AilThr3 / (f4 * paramFloat2));
    if (f4 > this.RudThr2) f2 = Math.max(0.2F, this.RudThr2 / f4);
    if (f4 > this.ElevThr2) f3 = Math.max(0.4F, this.ElevThr2 / f4);
    f1 *= this.Sensitivity;
    f2 *= this.Sensitivity;
    f3 *= this.Sensitivity;

    if ((this.Elevators >= 0.0F) && 
      (!(this.FM.actor instanceof SU_26M2)))
    {
      f3 = f2;
    }
    int k;
    if (!paramBoolean2) {
      if ((this.FM instanceof RealFlightModel))
      {
        float f5 = 0.0F;
        for (k = 0; k < paramEnginesInterface.getNum(); k++)
        {
          this.PowerControlArr[k] = clamp0115(this.PowerControlArr[k]);
          paramEnginesInterface.engines[k].setControlThrottle(this.PowerControlArr[k]);
          if (this.PowerControlArr[k] > f5) {
            f5 = this.PowerControlArr[k];
          }
        }
        if (paramBoolean1) {
          this.Power = f5;
        } else {
          this.Power = filter(paramFloat1, f5, this.Power, 5.0F, 0.01F * paramFloat1);
          paramEnginesInterface.setThrottle(this.Power);
        }
      }
      else
      {
        this.PowerControl = clamp0115(this.PowerControl);

        if (paramBoolean1)
        {
          this.Power = this.PowerControl;
        }
        else this.Power = filter(paramFloat1, this.PowerControl, this.Power, 5.0F, 0.01F * paramFloat1);

        paramEnginesInterface.setThrottle(this.Power);
      }

    }

    if (!paramBoolean2) {
      paramEnginesInterface.setAfterburnerControl(this.afterburnerControl);
    }

    if (!paramBoolean2) {
      this.StepControl = clamp01(this.StepControl);
      int i;
      if ((this.bUseElectricProp) && ((this.FM instanceof RealFlightModel)))
      {
        paramEnginesInterface.setPropAuto(this.bStepControlAuto);

        i = this.electricPropUp - this.electricPropDn;
        if (i < 0)
          HUD.log(AircraftHotKeys.hudLogPowerId, "elPropDn");
        else if (i > 0)
          HUD.log(AircraftHotKeys.hudLogPowerId, "elPropUp");
        paramEnginesInterface.setPropDelta(i);
      }

      if ((this.bStepControlAuto) && (paramEnginesInterface.getFirstSelected() != null)) {
        if (paramEnginesInterface.isSelectionAllowsAutoProp()) {
          paramEnginesInterface.setPropAuto(true);
        } else {
          paramEnginesInterface.setPropAuto(false);
          this.bStepControlAuto = false;
        }

      }
      else if ((this.FM instanceof RealFlightModel))
      {
        if (!this.bUseElectricProp) {
          for (i = 0; i < paramEnginesInterface.getNum(); i++)
          {
            this.StepControlArr[i] = clamp01(this.StepControlArr[i]);
            paramEnginesInterface.engines[i].setControlPropAuto(false);
            paramEnginesInterface.engines[i].setControlProp(this.StepControlArr[i]);
          }

        }

      }
      else
      {
        this.Step = filter(paramFloat1, this.StepControl, this.Step, 0.2F, 0.02F);
        paramEnginesInterface.setPropAuto(false);
        paramEnginesInterface.setProp(this.Step);
      }

    }

    this.RadiatorControl = clamp01(this.RadiatorControl);
    this.radiator = filter(paramFloat1, this.RadiatorControl, this.radiator, 999.90002F, 0.2F);
    if ((this.bRadiatorControlAuto) && (paramEnginesInterface.getFirstSelected() != null)) {
      if (paramEnginesInterface.isSelectionAllowsAutoRadiator()) {
        paramEnginesInterface.updateRadiator(paramFloat1);
      } else {
        paramEnginesInterface.setRadiator(this.radiator);
        this.bRadiatorControlAuto = false;
      }
    }
    else paramEnginesInterface.setRadiator(this.radiator);

    if (!paramBoolean2) {
      paramEnginesInterface.setMagnetos(this.MagnetoControl);
    }

    if ((!paramBoolean2) && (paramBoolean1))
    {
      paramEnginesInterface.setCompressorStep(this.CompressorControl);
    }

    if (!paramBoolean2) {
      paramEnginesInterface.setMix(this.MixControl);
    }

    if ((this.bHasGearControl) || (paramBoolean2)) {
      this.GearControl = clamp01(this.GearControl);

      this.Gear = filter(paramFloat1, this.GearControl, this.Gear, 999.90002F, this.dvGear);
    }

    if ((this.bHasAirBrakeControl) || (paramBoolean2))
    {
      this.airBrake = filter(paramFloat1, this.AirBrakeControl, this.airBrake, 999.90002F, this.dvAirbrake);
    }

    if (this.bHasWingControl) {
      this.wing = filter(paramFloat1, this.wingControl, this.wing, 999.90002F, this.dvWing);
      if ((this.wing > 0.01F) && (this.wing < 0.99F)) {
        this.FM.doRequestFMSFX(1, (int)(100.0F * this.wing));
      }

    }

    if (this.bHasCockpitDoorControl) {
      this.cockpitDoor = filter(paramFloat1, this.cockpitDoorControl, this.cockpitDoor, 999.90002F, this.dvCockpitDoor);
    }

    if ((this.bHasArrestorControl) || (paramBoolean2))
    {
      if ((this.arrestorControl == 0.0F) || (this.arrestorControl == 1.0F)) {
        this.arrestor = filter(paramFloat1, this.arrestorControl, this.arrestor, 999.90002F, 0.2F);
      }

    }

    if ((this.bHasFlapsControl) || (paramBoolean2)) {
      this.FlapsControl = clamp01(this.FlapsControl);
      if (this.Flaps > this.FlapsControl)
        this.Flaps = filter(paramFloat1, this.FlapsControl, this.Flaps, 999.0F, Aircraft.cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.25F));
      else {
        this.Flaps = filter(paramFloat1, this.FlapsControl, this.Flaps, 999.0F, Aircraft.cvt(this.FM.getSpeedKMH(), 150.0F, 280.0F, 0.15F, 0.02F));
      }

    }

    if (this.StabilizerControl)
    {
      this.AileronControl = (-0.2F * this.FM.Or.getKren() - 2.0F * (float)this.FM.getW().x);
      tmpV3d.set(this.FM.Vwld);
      tmpV3d.normalize();
      float f6 = (float)(-500.0D * (tmpV3d.z - 0.001D));
      if (f6 > 0.8F) f6 = 0.8F;
      if (f6 < -0.8F) f6 = -0.8F;
      this.ElevatorControl = (f6 - 0.2F * this.FM.Or.getTangage() - 0.05F * this.FM.AOA + 25.0F * (float)this.FM.getW().y);
      this.RudderControl = (-0.2F * this.FM.AOS + 20.0F * (float)this.FM.getW().z);
    }

    if ((this.bHasAileronControl) || (paramBoolean2)) {
      this.trimAileron = filter(paramFloat1, this.trimAileronControl, this.trimAileron, 999.90002F, 0.25F);
      this.AileronControl = clamp11(this.AileronControl);
      tmpF = clampA(this.AileronControl, f1);
      this.Ailerons = filter(paramFloat1, (1.0F + (this.trimAileron * tmpF > 0.0F ? -1.0F : 1.0F) * Math.abs(this.trimAileron)) * tmpF + this.trimAileron, this.Ailerons, 0.2F * (1.0F + 0.3F * Math.abs(this.AileronControl)), 0.025F);
    }

    if ((this.bHasElevatorControl) || (paramBoolean2)) {
      this.trimElevator = filter(paramFloat1, this.trimElevatorControl, this.trimElevator, 999.90002F, 0.25F);
      this.ElevatorControl = clamp11(this.ElevatorControl);
      tmpF = clampA(this.ElevatorControl, f3);
      this.Ev = filter(paramFloat1, (1.0F + (this.trimElevator * tmpF > 0.0F ? -1.0F : 1.0F) * Math.abs(this.trimElevator)) * tmpF + this.trimElevator, this.Ev, 0.3F * (1.0F + 0.3F * Math.abs(this.ElevatorControl)), 0.022F);

      if ((this.FM.actor instanceof SU_26M2))
        this.Elevators = clamp11(this.Ev);
      else {
        this.Elevators = clamp11(this.Ev - 0.25F * (1.0F - f3));
      }

    }

    if ((this.bHasRudderControl) || (paramBoolean2)) {
      this.trimRudder = filter(paramFloat1, this.trimRudderControl, this.trimRudder, 999.90002F, 0.25F);
      this.RudderControl = clamp11(this.RudderControl);
      tmpF = clampA(this.RudderControl, f2);
      this.Rudder = filter(paramFloat1, (1.0F + (this.trimRudder * tmpF > 0.0F ? -1.0F : 1.0F) * Math.abs(this.trimRudder)) * tmpF + this.trimRudder, this.Rudder, 0.35F * (1.0F + 0.3F * Math.abs(this.RudderControl)), 0.025F);
    }

    this.BrakeControl = clamp01(this.BrakeControl);
    if ((this.bHasBrakeControl) || (paramBoolean2)) {
      if (this.BrakeControl > this.Brake)
        this.Brake += 0.3F * paramFloat1;
      else
        this.Brake = this.BrakeControl;
    }
    else {
      this.Brake = 0.0F;
    }

    if (this.tick == Time.tickCounter()) return;
    this.tick = Time.tickCounter();

    this.CTL = (byte)((this.GearControl > 0.5F ? 1 : 0) | (this.FlapsControl > 0.2F ? 2 : 0) | (this.BrakeControl > 0.2F ? 4 : 0) | (this.RadiatorControl > 0.5F ? 8 : 0) | (this.BayDoorControl > 0.5F ? 16 : 0) | (this.AirBrakeControl > 0.5F ? 32 : 0));

    this.WCT = (byte)(this.WCT & 0xFFFFFFFC);
    this.TWCT &= 252;
    int j = 0; for (int m = 1; (j < this.WeaponControl.length) && (m < 256); m <<= 1) {
      if (this.WeaponControl[j] != 0) { this.WCT = (byte)(this.WCT | m); this.TWCT |= m;
      }
      j++;
    }

    for (j = 0; j < 4; j++) {
      this.saveWeaponControl[j] = this.WeaponControl[j];
    }
    for (j = 0; j < this.Weapons.length; j++)
      if (this.Weapons[j] != null)
        switch (j)
        {
        case 2:
        case 3:
          int n = this.WeaponControl[j] != 0 ? 1 : 0;
          if (n == 0) continue;
          for (k = 0; k < this.Weapons[j].length; k += 2) {
            if (((this.Weapons[j][k] instanceof FuelTankGun)) || 
              (!this.Weapons[j][k].haveBullets())) continue;
            this.Weapons[j][k].shots(n);
            if (this.Weapons[j][k].getHookName().startsWith("_BombSpawn")) {
              this.BayDoorControl = 1.0F;
            }
            if (((this.Weapons[j][k] instanceof BombGun)) && (!((BombGun)this.Weapons[j][k]).isCassette())) {
              break;
            }
            if (((this.Weapons[j][k] instanceof RocketGun)) && (!((RocketGun)this.Weapons[j][k]).isCassette())) {
              break;
            }
            if (((this.Weapons[j][k] instanceof RocketBombGun)) && (!((RocketBombGun)this.Weapons[j][k]).isCassette()))
            {
              break;
            }
          }
          for (k = 1; k < this.Weapons[j].length; k += 2) {
            if (((this.Weapons[j][k] instanceof FuelTankGun)) || 
              (!this.Weapons[j][k].haveBullets())) continue;
            this.Weapons[j][k].shots(n);
            if (((this.Weapons[j][k] instanceof BombGun)) && (!((BombGun)this.Weapons[j][k]).isCassette())) {
              break;
            }
            if (((this.Weapons[j][k] instanceof RocketGun)) && (!((RocketGun)this.Weapons[j][k]).isCassette())) {
              break;
            }
            if (((this.Weapons[j][k] instanceof RocketBombGun)) && (!((RocketBombGun)this.Weapons[j][k]).isCassette()))
            {
              break;
            }
          }
          this.WeaponControl[j] = false;
          break;
        default:
          int i1 = 0;
          for (k = 0; k < this.Weapons[j].length; k++) {
            this.Weapons[j][k].shots(this.WeaponControl[j] != 0 ? -1 : 0);
            i1 = (i1 != 0) || (this.Weapons[j][k].haveBullets()) ? 1 : 0;
          }
          if ((this.WeaponControl[j] == 0) || (i1 != 0) || (!this.FM.isPlayers())) continue;
          ForceFeedback.fxTriggerShake(j, false);
        }
  }

  public float getWeaponMass()
  {
    int i = this.Weapons.length;

    float f = 0.0F;

    for (int k = 0; k < i; k++)
      if (this.Weapons[k] != null) {
        int m = this.Weapons[k].length;
        for (int j = 0; j < m; j++) {
          BulletEmitter localBulletEmitter = this.Weapons[k][j];
          if ((localBulletEmitter != null) && (!(localBulletEmitter instanceof FuelTankGun))) {
            int n = localBulletEmitter.countBullets();
            if (n < 0) {
              n = 1;
              if (((localBulletEmitter instanceof BombGun)) && (((BombGun)localBulletEmitter).isCassette())) {
                n = 10;
              }
            }
            f += localBulletEmitter.bulletMassa() * n;
          }
        }
      }
    return f;
  }

  public int getWeaponCount(int paramInt)
  {
    if ((paramInt >= this.Weapons.length) || (this.Weapons[paramInt] == null)) return 0;
    int k = this.Weapons[paramInt].length;
    int j;
    for (int i = j = 0; j < k; j++) {
      BulletEmitter localBulletEmitter = this.Weapons[paramInt][j];
      if ((localBulletEmitter == null) || ((localBulletEmitter instanceof FuelTankGun))) continue; i += localBulletEmitter.countBullets();
    }
    return i;
  }

  public boolean dropFuelTanks()
  {
    int k = 0;
    for (int i = 0; i < this.Weapons.length; i++) {
      if (this.Weapons[i] != null) {
        for (int j = 0; j < this.Weapons[i].length; j++) {
          if ((!(this.Weapons[i][j] instanceof FuelTankGun)) || 
            (!this.Weapons[i][j].haveBullets())) continue;
          this.Weapons[i][j].shots(1);
          k = 1;
        }
      }
    }
    if (k != 0) {
      ((Aircraft)this.FM.actor).replicateDropFuelTanks();
      this.FM.M.onFuelTanksChanged();
    }
    return k;
  }

  public boolean dropExternalStores(boolean paramBoolean)
  {
    boolean bool = ((Aircraft)this.FM.actor).dropExternalStores(paramBoolean);
    if (bool)
    {
      this.FM.AS.externalStoresDropped = true;
      ((Aircraft)this.FM.actor).replicateDropExternalStores();
    }
    return bool;
  }

  public FuelTank[] getFuelTanks()
  {
    int k = 0;
    int j;
    for (int i = 0; i < this.Weapons.length; i++) {
      if (this.Weapons[i] != null) {
        for (j = 0; j < this.Weapons[i].length; j++) {
          if ((this.Weapons[i][j] instanceof FuelTankGun)) {
            k++;
          }
        }
      }
    }
    FuelTank[] arrayOfFuelTank = new FuelTank[k];
    for (i = k = 0; i < this.Weapons.length; i++) {
      if (this.Weapons[i] != null) {
        for (j = 0; j < this.Weapons[i].length; j++)
          if ((this.Weapons[i][j] instanceof FuelTankGun))
            arrayOfFuelTank[(k++)] = ((FuelTankGun)this.Weapons[i][j]).getFuelTank();
      }
    }
    return arrayOfFuelTank;
  }

  public void resetControl(int paramInt) {
    switch (paramInt) {
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