package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitRE_2000 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private float flaps = 0.0F;
  private float pictManifold;
  private float pictGear = 0.0F;
  private float tailWheelLock = 1.0F;
  private int flapsDirection = 0;
  private float flapsLeverAngle = 0.0F;
  private float flapsIncrement = 10.0F;
  private LightPointActor light1;
  private LightPointActor light2;
  private float rpmGeneratedPressure = 0.0F;
  private float oilPressure = 0.0F;

  private static final float[] oilTempScale = { 0.0F, 10.4F, 20.5F, 31.0F, 45.0F, 59.0F, 79.0F, 98.0F, 124.0F, 153.0F, 191.0F, 238.0F, 297.0F };

  private float radiator = 0.0F;
  private float currentRadiator = 0.0F;
  private int radiatorDelta = 0;
  private boolean sightDamaged = false;
  private static double compassZ = -0.2D;
  private double segLen1;
  private double segLen2;
  private double compassLimit;
  private static double compassLimitAngle = 12.0D;
  private Vector3d[] compassSpeed;
  int compassFirst = 0;
  private Vector3d accel;
  private Vector3d compassNorth;
  private Vector3d compassSouth;
  private double compassAcc;
  private double compassSc;
  private int delay = 80;

  public CockpitRE_2000()
  {
    super("3DO/Cockpit/Re2000/hier.him", "bf109");

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK01");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK01", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK02");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK02", this.light2);

    this.cockpitNightMats = new String[] { "D_strum1", "D_strum2", "D_strum3", "D_strum4", "equip01", "equip04", "equip05", "gunsight", "panel1", "stick", "strum1", "strum2", "strum3", "strum4" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    float f1 = 0.0F;

    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    f1 = this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.fm.EI.engines[0].getManifoldPressure() * 76.0F;

    if (f1 < 76.0F)
      this.mesh.chunkSetAngles("Z_need_airpress", -cvt(f1, 40.0F, 76.0F, 12.0F, 210.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_need_airpress", -cvt(f1, 76.0F, 100.0F, 210.0F, 328.0F), 0.0F, 0.0F);
    }
    f1 = -15.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl);

    this.mesh.chunkSetAngles("Z_stick03", f1, 0.0F, 0.0F);

    f1 = -14.0F * (this.pictElev = 0.85F * this.pictElev + 0.2F * this.fm.CT.ElevatorControl);

    this.mesh.chunkSetAngles("Z_stick01", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_stick02", 0.0F, 0.0F, f1);

    this.mesh.chunkSetAngles("Z_gunsight_rim", 50.0F * this.setNew.stbyPosition, 0.0F, 0.0F);

    f1 = this.fm.CT.getRudder();

    this.mesh.chunkSetAngles("Z_rudder_01", -15.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_02", 15.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_03", 15.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_04", -15.0F * f1, 0.0F, 0.0F);

    f1 = interp(this.setNew.throttle, this.setOld.throttle, paramFloat);
    this.mesh.chunkSetAngles("Z_throttle_lvr", cvt(f1, 0.0F, 1.0F, 0.0F, 50.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_wep_lvr", cvt(f1, 1.0F, 1.1F, 0.0F, 40.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_mix_lvr", 40.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_pitch_lvr", 45.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_alt_01", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, -3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_alt_02", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    float f2 = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());

    if (f2 < 100.0F)
      f1 = cvt(f2, 0.0F, 100.0F, 0.0F, 41.0F);
    else if (f2 < 200.0F)
      f1 = cvt(f2, 100.0F, 200.0F, 41.0F, 110.0F);
    else if (f2 < 300.0F)
      f1 = cvt(f2, 200.0F, 300.0F, 110.0F, 144.0F);
    else if (f2 < 400.0F)
      f1 = cvt(f2, 300.0F, 400.0F, 144.0F, 212.0F);
    else if (f2 < 500.0F)
      f1 = cvt(f2, 400.0F, 500.0F, 212.0F, 292.0F);
    else {
      f1 = cvt(f2, 500.0F, 550.0F, 292.0F, 328.0F);
    }

    this.mesh.chunkSetAngles("Z_need_speed_02", -f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_need_speed_01", -f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_clock01", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_clock02", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_oiltemp_01", -floatindex(cvt(this.fm.EI.engines[0].tOilOut, 30.0F, 150.0F, 0.0F, 12.0F), oilTempScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_oiltemp_02", -floatindex(cvt(this.fm.EI.engines[0].tOilIn, 30.0F, 150.0F, 0.0F, 12.0F), oilTempScale), 0.0F, 0.0F);

    f1 = this.fm.EI.engines[0].getRPM();

    this.mesh.chunkSetAngles("Z_need_RPM", cvt(f1, 0.0F, 4000.0F, 0.0F, -322.0F), 0.0F, 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
    {
      this.rpmGeneratedPressure -= 2.0F;
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

    this.mesh.chunkSetAngles("Z_need_oilpress", cvt(f4, 0.0F, 15.0F, 0.0F, -300.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_cyltemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -77.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_fuelpress", cvt(this.rpmGeneratedPressure, 0.0F, 1800.0F, 0.0F, -200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_magneto_switch", -38.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_turnbank01", cvt(this.setNew.turn, -0.2F, 0.2F, -20.0F, 20.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_turnbank02", cvt(getBall(8.0D), -8.0F, 8.0F, 7.5F, -7.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_climb", cvt(this.setNew.vspeed, -25.0F, 25.0F, 180.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_fuel_01", cvt(this.fm.M.fuel, this.fm.M.maxFuel * 0.28F, this.fm.M.maxFuel, 0.0F, 210.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_fuel_02", cvt(this.fm.M.fuel, 0.0F, this.fm.M.maxFuel * 0.28F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_flaps_indicator", 146.0F * this.fm.CT.getFlap(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_elev_trim", 360.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_elev_indicator", -180.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_rudder_trim", -180.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.03F * this.tailWheelLock);
    this.mesh.chunkSetLocate("Z_tailwheel_lock", Cockpit.xyz, Cockpit.ypr);

    if (this.fm.CT.bHasBrakeControl)
    {
      f1 = this.fm.CT.getBrake();

      this.mesh.chunkSetAngles("Z_need_brakes_airpress02", -cvt(f1 + f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 135.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_need_brakes_airpress01", cvt(f1 - f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 135.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_need_brakes_airpress03", -150.0F + f1 * 20.0F, 0.0F, 0.0F);

      resetYPRmodifier();
      Cockpit.xyz[2] = (-0.01F * f1);
      this.mesh.chunkSetLocate("Z_brakes", Cockpit.xyz, Cockpit.ypr);
    }

    if (this.fm.EI.engines[0].getControlFeather() == 1)
    {
      this.mesh.chunkSetAngles("Z_pitch_vario", -66.0F, 0.0F, 0.0F);
    }
    else if (this.fm.CT.getStepControlAuto(0))
    {
      this.mesh.chunkSetAngles("Z_pitch_vario", 66.0F, 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_pitch_vario", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_gear_lvr", 78.0F * this.pictGear, 0.0F, 0.0F);

    if (this.fm.Gears.lgear) {
      this.mesh.chunkSetAngles("Z_gear01", 90.0F - 90.0F * this.fm.CT.getGear(), 0.0F, 0.0F);
    }
    if (this.fm.Gears.rgear) {
      this.mesh.chunkSetAngles("Z_gear02", -90.0F + 90.0F * this.fm.CT.getGear(), 0.0F, 0.0F);
    }
    this.mesh.chunkVisible("Z_gearlamp01", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_gearlamp02", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("Z_gearlamp03", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_gearlamp04", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));

    this.mesh.chunkSetAngles("Z_flaps", -this.flapsLeverAngle, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_radiatorflaps", this.radiator * -8.0F, 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = (this.fm.CT.getCockpitDoor() * 0.625F);
    this.mesh.chunkSetLocate("Z_sliding_cnp", Cockpit.xyz, Cockpit.ypr);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
    {
      this.mesh.chunkVisible("gunsight_lense", false);
      this.mesh.chunkVisible("D_gunsight_lense", true);
      this.mesh.chunkVisible("reticle", false);
      this.mesh.chunkVisible("reticlemask", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_04", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("Gauges01", false);
      this.mesh.chunkVisible("D_Gauges01", true);
      this.mesh.chunkVisible("Z_need_clock01", false);
      this.mesh.chunkVisible("Z_need_clock02", false);
      this.mesh.chunkVisible("Z_need_alt_01", false);
      this.mesh.chunkVisible("Z_need_alt_02", false);
      this.mesh.chunkVisible("Z_need_climb", false);
      this.mesh.chunkVisible("Z_need_speed_01", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_05", true);
      this.mesh.chunkVisible("XGlassHoles_03", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("XHoles_01", true);

      this.mesh.chunkVisible("Gauges02", false);
      this.mesh.chunkVisible("D_Gauges02", true);
      this.mesh.chunkVisible("Z_need_speed_02", false);
      this.mesh.chunkVisible("Z_need_brakes_airpress03", false);
      this.mesh.chunkVisible("Z_need_brakes_airpress01", false);
      this.mesh.chunkVisible("Z_need_brakes_airpress02", false);
      this.mesh.chunkVisible("Z_need_airpress", false);
      this.mesh.chunkVisible("Z_need_fuelpress", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("XHoles_02", true);

      this.mesh.chunkVisible("Gauges03", false);
      this.mesh.chunkVisible("D_Gauges03", true);
      this.mesh.chunkVisible("Z_need_oiltemp_02", false);
      this.mesh.chunkVisible("Z_need_oiltemp_01", false);
      this.mesh.chunkVisible("Z_need_oilpress", false);
      this.mesh.chunkVisible("Z_need_RPM", false);
      this.mesh.chunkVisible("Z_need_turnbank01", false);
      this.mesh.chunkVisible("Z_need_turnbank02", false);
    }

    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_01", true);
      this.mesh.chunkVisible("XGlassHoles_02", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
    {
      this.mesh.chunkVisible("ZOil", true);
    }
  }

  protected void reflectPlaneToModel()
  {
  }

  public void doToggleAim(boolean paramBoolean)
  {
    super.doToggleAim(paramBoolean);
    if ((paramBoolean) && (this.sightDamaged))
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(true);
      localHookPilot.setAim(new Point3d(-0.5D, -0.017780000343919D, 0.8799999952316284D));
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      this.light1.light.setEmit(0.003F, 0.6F);
      this.light2.light.setEmit(0.003F, 0.6F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private void initCompass()
  {
    this.accel = new Vector3d();
    this.compassSpeed = new Vector3d[4];
    this.compassSpeed[0] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[1] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[2] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[3] = new Vector3d(0.0D, 0.0D, 0.0D);

    float[] arrayOfFloat1 = { 87.0F, 77.5F, 65.300003F, 41.5F, -0.3F, -43.5F, -62.900002F, -64.0F, -66.300003F, -75.800003F };

    float[] arrayOfFloat2 = { 55.799999F, 51.5F, 47.0F, 40.099998F, 33.799999F, 33.700001F, 32.700001F, 35.099998F, 46.599998F, 61.0F };

    float f1 = cvt(Engine.land().config.declin, -90.0F, 90.0F, 9.0F, 0.0F);

    float f2 = floatindex(f1, arrayOfFloat1);
    this.compassNorth = new Vector3d(0.0D, Math.cos(0.01745277777777778D * f2), -Math.sin(0.01745277777777778D * f2));
    this.compassSouth = new Vector3d(0.0D, -Math.cos(0.01745277777777778D * f2), Math.sin(0.01745277777777778D * f2));
    float f3 = floatindex(f1, arrayOfFloat2);

    this.compassNorth.scale(f3 / 600.0F * Time.tickLenFs());
    this.compassSouth.scale(f3 / 600.0F * Time.tickLenFs());

    this.segLen1 = (2.0D * Math.sqrt(1.0D - compassZ * compassZ));
    this.segLen2 = (this.segLen1 / Math.sqrt(2.0D));
    this.compassLimit = (-1.0D * Math.sin(0.0174532888888889D * compassLimitAngle));
    this.compassLimit *= this.compassLimit;

    this.compassAcc = (4.66666666D * Time.tickLenFs());
    this.compassSc = (0.101936799D / Time.tickLenFs() / Time.tickLenFs());
  }

  private void updateCompass()
  {
    if (this.compassFirst == 0)
    {
      initCompass();
      this.fm.getLoc(this.setOld.planeLoc);
    }

    this.fm.getLoc(this.setNew.planeLoc);

    this.setNew.planeMove.set(this.setNew.planeLoc);
    this.setNew.planeMove.sub(this.setOld.planeLoc);

    this.accel.set(this.setNew.planeMove);
    this.accel.sub(this.setOld.planeMove);

    this.accel.scale(this.compassSc);

    this.accel.x = (-this.accel.x);
    this.accel.y = (-this.accel.y);
    this.accel.z = (-this.accel.z - 1.0D);
    this.accel.scale(this.compassAcc);

    if (this.accel.length() > -compassZ * 0.7D) {
      this.accel.scale(-compassZ * 0.7D / this.accel.length());
    }

    for (int i = 0; i < 4; i++)
    {
      this.compassSpeed[i].set(this.setOld.compassPoint[i]);
      this.compassSpeed[i].sub(this.setNew.compassPoint[i]);
    }

    for (i = 0; i < 4; i++)
    {
      double d1 = this.compassSpeed[i].length();
      d1 = 0.985D / (1.0D + d1 * d1 * 15.0D);

      this.compassSpeed[i].scale(d1);
    }

    Vector3d localVector3d1 = new Vector3d();
    localVector3d1.set(this.setOld.compassPoint[0]);
    localVector3d1.add(this.setOld.compassPoint[1]);
    localVector3d1.add(this.setOld.compassPoint[2]);
    localVector3d1.add(this.setOld.compassPoint[3]);
    localVector3d1.normalize();

    for (int j = 0; j < 4; j++) {
      Vector3d localVector3d3 = new Vector3d();
      double d2 = localVector3d1.dot(this.compassSpeed[j]);
      localVector3d3.set(localVector3d1);
      d2 *= 0.28D;

      localVector3d3.scale(-d2);

      this.compassSpeed[j].add(localVector3d3);
    }

    for (j = 0; j < 4; j++)
      this.compassSpeed[j].add(this.accel);
    this.compassSpeed[0].add(this.compassNorth);
    this.compassSpeed[2].add(this.compassSouth);

    for (j = 0; j < 4; j++) {
      this.setNew.compassPoint[j].set(this.setOld.compassPoint[j]);
      this.setNew.compassPoint[j].add(this.compassSpeed[j]);
    }

    localVector3d1.set(this.setNew.compassPoint[0]);
    localVector3d1.add(this.setNew.compassPoint[1]);
    localVector3d1.add(this.setNew.compassPoint[2]);
    localVector3d1.add(this.setNew.compassPoint[3]);
    localVector3d1.scale(0.25D);
    Vector3d localVector3d2 = new Vector3d(localVector3d1);
    localVector3d2.normalize();
    localVector3d2.scale(-compassZ);
    localVector3d2.sub(localVector3d1);

    for (int k = 0; k < 4; k++) {
      this.setNew.compassPoint[k].add(localVector3d2);
    }

    for (k = 0; k < 4; k++)
      this.setNew.compassPoint[k].normalize();
    for (k = 0; k < 2; k++)
    {
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);

      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);

      for (m = 0; m < 4; m++) {
        this.setNew.compassPoint[m].normalize();
      }
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);

      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      for (m = 0; m < 4; m++) {
        this.setNew.compassPoint[m].normalize();
      }

    }

    Orientation localOrientation = new Orientation();
    this.fm.getOrient(localOrientation);
    for (int m = 0; m < 4; m++) {
      this.setNew.cP[m].set(this.setNew.compassPoint[m]);
      localOrientation.transformInv(this.setNew.cP[m]);
    }

    Vector3d localVector3d4 = new Vector3d();
    localVector3d4.set(this.setNew.cP[0]);
    localVector3d4.add(this.setNew.cP[1]);
    localVector3d4.add(this.setNew.cP[2]);
    localVector3d4.add(this.setNew.cP[3]);
    localVector3d4.scale(0.25D);
    Vector3d localVector3d5 = new Vector3d();
    localVector3d5.set(localVector3d4);
    localVector3d5.normalize();

    float f = (float)(localVector3d5.x * localVector3d5.x + localVector3d5.y * localVector3d5.y);
    if ((f > this.compassLimit) || (localVector3d4.z > 0.0D))
    {
      for (int n = 0; n < 4; n++)
      {
        this.setNew.cP[n].set(this.setOld.cP[n]);
        this.setNew.compassPoint[n].set(this.setOld.cP[n]);

        localOrientation.transform(this.setNew.compassPoint[n]);
      }

      localVector3d4.set(this.setNew.cP[0]);
      localVector3d4.add(this.setNew.cP[1]);
      localVector3d4.add(this.setNew.cP[2]);
      localVector3d4.add(this.setNew.cP[3]);
      localVector3d4.scale(0.25D);
    }

    localVector3d5.set(this.setNew.cP[0]);
    localVector3d5.sub(localVector3d4);

    double d3 = -Math.atan2(localVector3d4.y, -localVector3d4.z);
    vectorRot2(localVector3d4, d3);
    vectorRot2(localVector3d5, d3);

    double d4 = Math.atan2(localVector3d4.x, -localVector3d4.z);

    vectorRot1(localVector3d5, -d4);

    double d5 = Math.atan2(localVector3d5.y, localVector3d5.x);

    this.mesh.chunkSetAngles("compass_base", 0.0F, -(float)(d3 * 180.0D / 3.1415926D), (float)(d4 * 180.0D / 3.1415926D));

    this.mesh.chunkSetAngles("compass_header", -(float)(90.0D - d5 * 180.0D / 3.1415926D), 0.0F, 0.0F);

    this.compassFirst += 1;
  }

  private void vectorRot1(Vector3d paramVector3d, double paramDouble)
  {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.x * d2 - paramVector3d.z * d1;
    paramVector3d.z = (paramVector3d.x * d1 + paramVector3d.z * d2);
    paramVector3d.x = d3;
  }

  private void vectorRot2(Vector3d paramVector3d, double paramDouble) {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.y * d2 - paramVector3d.z * d1;
    paramVector3d.z = (paramVector3d.y * d1 + paramVector3d.z * d2);
    paramVector3d.y = d3;
  }

  private void compassDist(Vector3d paramVector3d1, Vector3d paramVector3d2, double paramDouble)
  {
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(paramVector3d1);
    localVector3d.sub(paramVector3d2);
    double d = localVector3d.length();

    if (d < 1.0E-006D)
      d = 1.0E-006D;
    d = (paramDouble - d) / d / 2.0D;

    localVector3d.scale(d);
    paramVector3d1.add(localVector3d);
    paramVector3d2.sub(localVector3d);
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
    Point3d planeLoc;
    Point3d planeMove;
    Vector3d[] compassPoint;
    Vector3d[] cP;
    private final CockpitRE_2000 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();

      this.planeLoc = new Point3d();
      this.planeMove = new Point3d();
      this.compassPoint = new Vector3d[4];
      this.cP = new Vector3d[4];

      this.compassPoint[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), CockpitRE_2000.compassZ);

      this.compassPoint[1] = new Vector3d(-Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), 0.0D, CockpitRE_2000.compassZ);
      this.compassPoint[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), CockpitRE_2000.compassZ);
      this.compassPoint[3] = new Vector3d(Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), 0.0D, CockpitRE_2000.compassZ);

      this.cP[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), CockpitRE_2000.compassZ);

      this.cP[1] = new Vector3d(-Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), 0.0D, CockpitRE_2000.compassZ);
      this.cP[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), CockpitRE_2000.compassZ);
      this.cP[3] = new Vector3d(Math.sqrt(1.0D - CockpitRE_2000.compassZ * CockpitRE_2000.compassZ), 0.0D, CockpitRE_2000.compassZ);
    }

    Variables(CockpitRE_2000.1 arg2)
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
      if (CockpitRE_2000.this.bNeedSetUp)
      {
        CockpitRE_2000.this.reflectPlaneMats();
        CockpitRE_2000.access$102(CockpitRE_2000.this, false);
      }

      CockpitRE_2000.access$202(CockpitRE_2000.this, CockpitRE_2000.this.setOld);
      CockpitRE_2000.access$302(CockpitRE_2000.this, CockpitRE_2000.this.setNew);
      CockpitRE_2000.access$402(CockpitRE_2000.this, CockpitRE_2000.this.setTmp);

      if (((CockpitRE_2000.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitRE_2000.this.setNew.stbyPosition < 1.0F))
      {
        CockpitRE_2000.access$510(CockpitRE_2000.this);
        if (CockpitRE_2000.this.delay <= 0)
        {
          CockpitRE_2000.this.setNew.stbyPosition = (CockpitRE_2000.this.setOld.stbyPosition + 0.03F);
          CockpitRE_2000.this.setOld.stbyPosition = CockpitRE_2000.this.setNew.stbyPosition;
          CockpitRE_2000.access$602(CockpitRE_2000.this, true);
        }
      }

      if (CockpitRE_2000.this.fm.CT.getRadiator() < CockpitRE_2000.this.currentRadiator)
        CockpitRE_2000.access$802(CockpitRE_2000.this, -1);
      else if (CockpitRE_2000.this.fm.CT.getRadiator() > CockpitRE_2000.this.currentRadiator)
        CockpitRE_2000.access$802(CockpitRE_2000.this, 1);
      if ((CockpitRE_2000.this.radiator > 1.0F) || (CockpitRE_2000.this.radiator < -1.0F))
        CockpitRE_2000.access$802(CockpitRE_2000.this, 0);
      if (CockpitRE_2000.this.radiatorDelta != 0)
        CockpitRE_2000.access$902(CockpitRE_2000.this, CockpitRE_2000.this.radiator + CockpitRE_2000.this.radiatorDelta * 0.2F);
      else if (CockpitRE_2000.this.currentRadiator == CockpitRE_2000.this.fm.CT.getRadiator())
        CockpitRE_2000.access$902(CockpitRE_2000.this, CockpitRE_2000.this.radiator * 0.5F);
      CockpitRE_2000.access$702(CockpitRE_2000.this, CockpitRE_2000.this.fm.CT.getRadiator());

      CockpitRE_2000.this.setNew.altimeter = CockpitRE_2000.this.fm.getAltitude();

      CockpitRE_2000.this.setNew.throttle = ((10.0F * CockpitRE_2000.this.setOld.throttle + CockpitRE_2000.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);

      CockpitRE_2000.this.setNew.mix = ((10.0F * CockpitRE_2000.this.setOld.mix + CockpitRE_2000.this.fm.EI.engines[0].getControlMix()) / 11.0F);

      CockpitRE_2000.this.setNew.prop = ((10.0F * CockpitRE_2000.this.setOld.prop + CockpitRE_2000.this.fm.EI.engines[0].getControlProp()) / 11.0F);

      CockpitRE_2000.access$1002(CockpitRE_2000.this, 0.85F * CockpitRE_2000.this.pictGear + 0.15F * CockpitRE_2000.this.fm.CT.GearControl);

      CockpitRE_2000.this.w.set(CockpitRE_2000.this.fm.getW());
      CockpitRE_2000.this.fm.Or.transform(CockpitRE_2000.this.w);
      CockpitRE_2000.this.setNew.turn = ((12.0F * CockpitRE_2000.this.setOld.turn + CockpitRE_2000.this.w.z) / 13.0F);
      CockpitRE_2000.this.setNew.vspeed = ((299.0F * CockpitRE_2000.this.setOld.vspeed + CockpitRE_2000.this.fm.getVertSpeed()) / 300.0F);

      if (CockpitRE_2000.this.flaps == CockpitRE_2000.this.fm.CT.getFlap())
      {
        CockpitRE_2000.access$1302(CockpitRE_2000.this, 0);
        CockpitRE_2000.this.sfxStop(16);
      }
      else if (CockpitRE_2000.this.flaps < CockpitRE_2000.this.fm.CT.getFlap())
      {
        if (CockpitRE_2000.this.flapsDirection == 0)
          CockpitRE_2000.this.sfxStart(16);
        CockpitRE_2000.access$1202(CockpitRE_2000.this, CockpitRE_2000.this.fm.CT.getFlap());
        CockpitRE_2000.access$1302(CockpitRE_2000.this, 1);
        CockpitRE_2000.access$1402(CockpitRE_2000.this, CockpitRE_2000.this.flapsLeverAngle + CockpitRE_2000.this.flapsIncrement);
      }
      else if (CockpitRE_2000.this.flaps > CockpitRE_2000.this.fm.CT.getFlap())
      {
        if (CockpitRE_2000.this.flapsDirection == 0)
          CockpitRE_2000.this.sfxStart(16);
        CockpitRE_2000.access$1202(CockpitRE_2000.this, CockpitRE_2000.this.fm.CT.getFlap());
        CockpitRE_2000.access$1302(CockpitRE_2000.this, -1);
        CockpitRE_2000.access$1402(CockpitRE_2000.this, CockpitRE_2000.this.flapsLeverAngle - CockpitRE_2000.this.flapsIncrement);
      }

      if ((!CockpitRE_2000.this.fm.Gears.bTailwheelLocked) && (CockpitRE_2000.this.tailWheelLock < 1.0F))
      {
        CockpitRE_2000.access$1602(CockpitRE_2000.this, CockpitRE_2000.this.tailWheelLock + 0.1F);
      }
      else if ((CockpitRE_2000.this.fm.Gears.bTailwheelLocked) && (CockpitRE_2000.this.tailWheelLock > 0.0F))
      {
        CockpitRE_2000.access$1602(CockpitRE_2000.this, CockpitRE_2000.this.tailWheelLock - 0.1F);
      }
      CockpitRE_2000.this.updateCompass();
      return true;
    }
  }
}