package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitCW_21 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float pictFlap = 0.0F;
  private float pictManifold;
  private float rpmGeneratedPressure = 0.0F;
  private float oilPressure = 0.0F;
  private float flapsLever = 0.0F;
  private float flaps = 0.0F;
  private float gearsLever = 0.0F;
  private float gears = 0.0F;
  private boolean warningLightsOK = true;

  public CockpitCW_21()
  {
    super("3DO/Cockpit/CW-21/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "CLOCKS1", "CLOCK2", "CLOCK3", "CLOCK5", "Compass", "STUFF4", "STUFF5" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    ((CW_21)aircraft()); if (CW_21.bChangedPit) {
      reflectPlaneToModel();
      ((CW_21)aircraft()); CW_21.bChangedPit = false;
    }

    this.mesh.chunkSetAngles("Z_NeedManifoldPres", cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.fm.EI.engines[0].getManifoldPressure() * 76.0F, 30.0F, 120.0F, 15.0F, 285.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Stick", -14.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, -14.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl));

    this.mesh.chunkSetAngles("Z_PedalR1", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PedalL1", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    float f1 = this.fm.CT.getBrake() * 45.0F;

    this.mesh.chunkSetAngles("Z_PedalR2", 15.0F * this.fm.CT.getRudder() + f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PedalL2", -15.0F * this.fm.CT.getRudder() + f1, 0.0F, 0.0F);

    f1 = 70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat);

    this.mesh.chunkSetAngles("Z_LevelThrottle", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RodThrottle", -f1, 0.0F, 0.0F);

    f1 = 70.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat);

    this.mesh.chunkSetAngles("Z_LevelMixture", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RodMixture", -f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeverRPM", 70.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeverSuperc", this.pictSupc * 70.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RodSuperc", -this.pictSupc * 70.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedAlt", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    float f2 = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());

    if (f2 < 150.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 0.0F, 150.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    }
    else if (f2 < 300.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 150.0F, 300.0F, 90.0F, 198.0F), 0.0F, 0.0F);
    }
    else if (f2 < 400.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 300.0F, 400.0F, 198.0F, 270.0F), 0.0F, 0.0F);
    }
    else if (f2 < 550.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 400.0F, 550.0F, 270.0F, 378.0F), 0.0F, 0.0F);
    }
    else if (f2 < 700.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 550.0F, 700.0F, 378.0F, 489.0F), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_NeedSpeed", cvt(f2, 700.0F, 900.0F, 489.0F, 630.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_NeedCompass", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    f1 = this.fm.EI.engines[0].getRPM();

    this.mesh.chunkSetAngles("Z_NeedRPM", cvt(f1, 500.0F, 3500.0F, 0.0F, 540.0F), 0.0F, 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
    {
      this.rpmGeneratedPressure -= 0.5F;
    }
    else if (f1 < this.rpmGeneratedPressure)
    {
      this.rpmGeneratedPressure -= (this.rpmGeneratedPressure - f1) * 0.01F;
    }
    else
    {
      this.rpmGeneratedPressure += (f1 - this.rpmGeneratedPressure) * 0.001F;
    }

    if (this.rpmGeneratedPressure < 800.0F)
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 0.0F, 800.0F, 0.0F, 4.0F);
    }
    else if (this.rpmGeneratedPressure < 1800.0F)
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 800.0F, 1800.0F, 4.0F, 5.0F);
    }
    else
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 1800.0F, 2750.0F, 5.0F, 5.8F);
    }

    float f3 = 0.0F;
    if (this.fm.EI.engines[0].tOilOut > 90.0F)
    {
      f3 = cvt(this.fm.EI.engines[0].tOilOut, 90.0F, 110.0F, 1.1F, 1.5F);
    }
    else if (this.fm.EI.engines[0].tOilOut < 50.0F)
    {
      f3 = cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 50.0F, 2.0F, 0.9F);
    }
    else
    {
      f3 = cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }

    float f4 = f3 * this.fm.EI.engines[0].getReadyness() * this.oilPressure;

    this.mesh.chunkSetAngles("Z_NeedOilPres", cvt(f4, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedClockHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedClockMinute", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedFuelPres", -cvt(this.rpmGeneratedPressure, 0.0F, 2000.0F, 0.0F, 120.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedHydrPres", cvt(this.rpmGeneratedPressure, 0.0F, 4200.0F, 0.0F, 270.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedOilTemp", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedCylTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 110.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedTurn", -cvt(this.setNew.turn, -0.2F, 0.2F, -45.0F, 45.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedBall", -cvt(getBall(8.0D), -8.0F, 8.0F, 10.0F, -10.0F), 0.0F, 0.0F);

    if (Math.abs(this.setNew.vspeed) <= 5.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedVSpeed", cvt(this.setNew.vspeed, -5.0F, 5.0F, -90.0F, 90.0F), 0.0F, 0.0F);
    }
    else if (this.setNew.vspeed > 5.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedVSpeed", cvt(this.setNew.vspeed, 5.0F, 20.0F, 90.0F, 180.0F), 0.0F, 0.0F);
    }
    else if (this.setNew.vspeed < -5.0F)
    {
      this.mesh.chunkSetAngles("Z_NeedVSpeed", cvt(this.setNew.vspeed, -20.0F, -5.0F, -180.0F, -90.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_NeedFuel", cvt(this.fm.M.fuel, 0.0F, 280.0F, 0.0F, 54.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Ignition", 20.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    resetYPRmodifier();
    Aircraft.xyz[1] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.xyz[0] = (this.fm.CT.getCockpitDoor() * -0.49F);

    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    this.mesh.chunkSetLocate("Canopy", Aircraft.xyz, Aircraft.ypr);

    if (this.fm.AS.bLandingLightOn)
      this.mesh.chunkSetAngles("Z_Switch3", 45.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("Z_Switch3", 0.0F, 0.0F, 0.0F);
    if (this.fm.AS.bNavLightsOn)
      this.mesh.chunkSetAngles("Z_Switch2", 45.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("Z_Switch2", 0.0F, 0.0F, 0.0F);
    if (this.cockpitLightControl)
      this.mesh.chunkSetAngles("Z_Switch7", 45.0F, 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Switch7", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_LeverFlaps", -this.flapsLever, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeverGear", this.gearsLever, 0.0F, 0.0F);

    if (this.warningLightsOK)
    {
      this.mesh.chunkVisible("WarningLight1", this.rpmGeneratedPressure < 700.0F);
      this.mesh.chunkVisible("WarningLight2", (f4 > 11.0F) || (f4 < 3.0F));
      this.mesh.chunkVisible("WarningLight3", this.fm.M.fuel < 0.1F);
      this.mesh.chunkVisible("WarningLight4", this.setNew.mix < 0.1D);
      this.mesh.chunkVisible("WarningLight5", this.setNew.prop < 0.1F);
      this.mesh.chunkVisible("WarningLight6", this.pictSupc > 0.1F);
      this.mesh.chunkVisible("WarningLight7", this.fm.CT.getFlap() > 0.01F);
      this.mesh.chunkVisible("WarningLight8", this.fm.CT.getGear() == 0.0F);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("CanopyGlassDamage", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Glass_damage", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("Gauges1_D0", false);
      this.mesh.chunkVisible("Gauges1_D1", true);
      this.mesh.chunkVisible("Z_NeedCylTemp", false);
      this.mesh.chunkVisible("Z_NeedSpeed", false);
      this.mesh.chunkVisible("Z_NeedBall", false);
      this.mesh.chunkVisible("Z_NeedTurn", false);
      if (World.Rnd().nextFloat() < 0.15D)
      {
        this.warningLightsOK = false;
        this.mesh.chunkVisible("WarningLights", false);
        this.mesh.chunkVisible("WarningLight1", false);
        this.mesh.chunkVisible("WarningLight2", false);
        this.mesh.chunkVisible("WarningLight3", false);
        this.mesh.chunkVisible("WarningLight4", false);
        this.mesh.chunkVisible("WarningLight5", false);
        this.mesh.chunkVisible("WarningLight6", false);
        this.mesh.chunkVisible("WarningLight7", false);
        this.mesh.chunkVisible("WarningLight8", false);
      }
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0)) {
      this.mesh.chunkVisible("GlassOil", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0)) {
      this.mesh.chunkVisible("Gauges2_D0", false);
      this.mesh.chunkVisible("Gauges2_D1", true);
      this.mesh.chunkVisible("Z_NeedVSpeed", false);
      this.mesh.chunkVisible("Z_NeedRPM", false);
      this.mesh.chunkVisible("Z_NeedAlt", false);
      if (World.Rnd().nextFloat() < 0.15D)
      {
        this.warningLightsOK = false;
        this.mesh.chunkVisible("WarningLights", false);
        this.mesh.chunkVisible("WarningLight1", false);
        this.mesh.chunkVisible("WarningLight2", false);
        this.mesh.chunkVisible("WarningLight3", false);
        this.mesh.chunkVisible("WarningLight4", false);
        this.mesh.chunkVisible("WarningLight5", false);
        this.mesh.chunkVisible("WarningLight6", false);
        this.mesh.chunkVisible("WarningLight7", false);
        this.mesh.chunkVisible("WarningLight8", false);
      }
    }
  }

  protected void reflectPlaneToModel() {
    HierMesh localHierMesh = aircraft().hierMesh();

    this.mesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  static
  {
    Property.set(CockpitCW_21.class, "normZN", 0.8F);
    Property.set(CockpitCW_21.class, "gsZN", 0.8F);
  }

  private class Variables
  {
    float altimeter;
    AnglesFork azimuth;
    float throttle;
    float mix;
    float prop;
    float turn;
    float vspeed;
    float stbyPosition;
    private final CockpitCW_21 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitCW_21.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitCW_21.this.bNeedSetUp) {
        CockpitCW_21.this.reflectPlaneMats();
        CockpitCW_21.access$102(CockpitCW_21.this, false);
      }
      ((CW_21)CockpitCW_21.this.aircraft()); if (CW_21.bChangedPit) {
        CockpitCW_21.this.reflectPlaneToModel();
        ((CW_21)CockpitCW_21.this.aircraft()); CW_21.bChangedPit = false;
      }

      CockpitCW_21.access$202(CockpitCW_21.this, CockpitCW_21.this.setOld);
      CockpitCW_21.access$302(CockpitCW_21.this, CockpitCW_21.this.setNew);
      CockpitCW_21.access$402(CockpitCW_21.this, CockpitCW_21.this.setTmp);
      if (((CockpitCW_21.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitCW_21.this.setNew.stbyPosition < 1.0F))
      {
        CockpitCW_21.this.setNew.stbyPosition = (CockpitCW_21.this.setOld.stbyPosition + 0.0125F);
        CockpitCW_21.this.setOld.stbyPosition = CockpitCW_21.this.setNew.stbyPosition;
      }
      CockpitCW_21.this.setNew.altimeter = CockpitCW_21.this.fm.getAltitude();

      CockpitCW_21.this.setNew.azimuth.setDeg(CockpitCW_21.this.setOld.azimuth.getDeg(1.0F), CockpitCW_21.this.fm.Or.azimut());

      CockpitCW_21.this.setNew.throttle = ((10.0F * CockpitCW_21.this.setOld.throttle + CockpitCW_21.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);

      CockpitCW_21.this.setNew.mix = ((10.0F * CockpitCW_21.this.setOld.mix + CockpitCW_21.this.fm.EI.engines[0].getControlMix()) / 11.0F);

      CockpitCW_21.this.setNew.prop = ((8.0F * CockpitCW_21.this.setOld.prop + CockpitCW_21.this.fm.CT.getStepControl()) / 9.0F);

      CockpitCW_21.this.w.set(CockpitCW_21.this.fm.getW());
      CockpitCW_21.this.fm.Or.transform(CockpitCW_21.this.w);
      CockpitCW_21.this.setNew.turn = ((12.0F * CockpitCW_21.this.setOld.turn + CockpitCW_21.this.w.z) / 13.0F);
      CockpitCW_21.this.setNew.vspeed = ((299.0F * CockpitCW_21.this.setOld.vspeed + CockpitCW_21.this.fm.getVertSpeed()) / 300.0F);

      CockpitCW_21.access$602(CockpitCW_21.this, 0.8F * CockpitCW_21.this.pictSupc + 0.2F * CockpitCW_21.this.fm.EI.engines[0].getControlCompressor());

      float f = 12.0F;

      if ((CockpitCW_21.this.flapsLever != 0.0F) && (CockpitCW_21.this.flaps == CockpitCW_21.this.fm.CT.getFlap()))
      {
        CockpitCW_21.access$702(CockpitCW_21.this, CockpitCW_21.this.flapsLever * 0.8F);
        if (Math.abs(CockpitCW_21.this.flapsLever) < 0.1F)
          CockpitCW_21.access$702(CockpitCW_21.this, 0.0F);
      }
      else if (CockpitCW_21.this.flaps < CockpitCW_21.this.fm.CT.getFlap())
      {
        CockpitCW_21.access$802(CockpitCW_21.this, CockpitCW_21.this.fm.CT.getFlap());
        CockpitCW_21.access$702(CockpitCW_21.this, CockpitCW_21.this.flapsLever + 2.0F);
        if (CockpitCW_21.this.flapsLever > f)
          CockpitCW_21.access$702(CockpitCW_21.this, f);
      }
      else if (CockpitCW_21.this.flaps > CockpitCW_21.this.fm.CT.getFlap())
      {
        CockpitCW_21.access$802(CockpitCW_21.this, CockpitCW_21.this.fm.CT.getFlap());
        CockpitCW_21.access$702(CockpitCW_21.this, CockpitCW_21.this.flapsLever - 2.0F);
        if (CockpitCW_21.this.flapsLever < -f) {
          CockpitCW_21.access$702(CockpitCW_21.this, -f);
        }
      }
      if ((CockpitCW_21.this.gearsLever != 0.0F) && (CockpitCW_21.this.gears == CockpitCW_21.this.fm.CT.getGear()))
      {
        CockpitCW_21.access$902(CockpitCW_21.this, CockpitCW_21.this.gearsLever * 0.8F);
        if (Math.abs(CockpitCW_21.this.gearsLever) < 0.1F)
          CockpitCW_21.access$902(CockpitCW_21.this, 0.0F);
      }
      else if (CockpitCW_21.this.gears < CockpitCW_21.this.fm.CT.getGear())
      {
        CockpitCW_21.access$1002(CockpitCW_21.this, CockpitCW_21.this.fm.CT.getGear());
        CockpitCW_21.access$902(CockpitCW_21.this, CockpitCW_21.this.gearsLever + 2.0F);
        if (CockpitCW_21.this.gearsLever > f)
          CockpitCW_21.access$902(CockpitCW_21.this, f);
      }
      else if (CockpitCW_21.this.gears > CockpitCW_21.this.fm.CT.getGear())
      {
        CockpitCW_21.access$1002(CockpitCW_21.this, CockpitCW_21.this.fm.CT.getGear());
        CockpitCW_21.access$902(CockpitCW_21.this, CockpitCW_21.this.gearsLever - 2.0F);
        if (CockpitCW_21.this.gearsLever < -f) {
          CockpitCW_21.access$902(CockpitCW_21.this, -f);
        }
      }

      return true;
    }
  }
}