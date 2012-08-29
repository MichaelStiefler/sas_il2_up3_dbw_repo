package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;

public class CockpitAR_234B2 extends CockpitPilot
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictGear = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };
  private float saveFov;
  private boolean bEntered = false;

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitAR_234B2()
  {
    super("3DO/Cockpit/Ar-234B-2/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "D_gauges_1_TR", "D_gauges_2_TR", "D_gauges_3_TR", "D_gauges_4_TR", "D_gauges_6_TR", "gauges_1_TR", "gauges_2_TR", "gauges_3_TR", "gauges_4_TR", "gauges_5_TR", "gauges_6_TR", "gauges_7_TR", "gauges_8_TR", "gauges_9_TR" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("zAltWheel", 0.0F, cvt(((AR_234B2)aircraft()).fSightCurAltitude, 0.0F, 6000.0F, 0.0F, 225.0F), 0.0F);
    this.mesh.chunkSetAngles("zAnglePointer", 0.0F, cvt(((AR_234B2)aircraft()).fSightCurForwardAngle, 0.0F, 60.0F, 105.0F, 0.0F), 0.0F);
    this.mesh.chunkSetAngles("zAngleWheel", 0.0F, -10.0F * ((AR_234B2)aircraft()).fSightCurForwardAngle, 0.0F);
    this.mesh.chunkSetAngles("zSpeedPointer", 0.0F, cvt(((AR_234B2)aircraft()).fSightCurSpeed, 150.0F, 600.0F, 0.0F, 60.0F), 0.0F);
    this.mesh.chunkSetAngles("zSpeedWheel", 0.0F, 0.333F * ((AR_234B2)aircraft()).fSightCurSpeed, 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getRudder(), -1.0F, 1.0F, -0.03F, 0.03F);
    this.mesh.chunkSetLocate("PedalL", xyz, ypr);
    xyz[1] = (-xyz[1]);
    this.mesh.chunkSetLocate("PedalR", xyz, ypr);
    this.pictGear = (0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl);
    this.mesh.chunkSetAngles("ruchkaShassi", cvt(this.pictGear, 0.0F, 1.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ruchkaGaza1", cvt(interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 1.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ruchkaGaza2", cvt(interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 1.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ruchkaSopla", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ruchkaFuel1", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ruchkaFuel2", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ETrim", -30.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("RTrim", -300.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("os_V", -15.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Srul", 60.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.cockpitLightControl) {
      xyz[2] = 0.00365F;
    }
    this.mesh.chunkSetLocate("Z_lightSW", xyz, ypr);
    xyz[2] = (this.fm.CT.saveWeaponControl[0] != 0 ? 0.0059F : 0.0F);
    this.mesh.chunkSetLocate("r_one", xyz, ypr);
    xyz[2] = (this.fm.CT.saveWeaponControl[3] != 0 ? 0.0059F : 0.0F);
    this.mesh.chunkSetLocate("r_two", xyz, ypr);

    resetYPRmodifier();
    xyz[1] = cvt(-this.fm.CT.trimElevator, -0.5F, 0.5F, -0.0475F, 0.0475F);
    this.mesh.chunkSetLocate("Need_ETrim", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(-this.fm.CT.trimRudder, -0.5F, 0.5F, -0.029F, 0.029F);
    this.mesh.chunkSetLocate("Need_RTrim", xyz, ypr);
    this.mesh.chunkSetAngles("zClock1a", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zClock1b", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zClock1c", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAirTemp", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 213.09F, 313.09F, -30.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zHydropress", this.fm.Gears.isHydroOperable() ? 120.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMCompc", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -22.200001F, 22.200001F, -22.200001F, 22.200001F));
    this.mesh.chunkSetAngles("zMCompa", cvt(this.fm.Or.getKren(), -22.200001F, 22.200001F, -22.200001F, 22.200001F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zMCompb", -90.0F - this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt1a", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt1b", cvt(this.setNew.altimeter, 0.0F, 14000.0F, 0.0F, 315.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zRPM1", 22.5F + floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zRPM2", 22.5F + floatindex(cvt(this.fm.EI.engines[1].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasPrs1a", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasPrs1b", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasPrs2a", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[1].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasPrs2b", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[1].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, -180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilPrs1a", cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilPrs2a", cvt(1.0F + 0.005F * this.fm.EI.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelPrs1a", cvt(this.fm.M.fuel > 1.0F ? 2.2F * this.fm.EI.engines[0].getPowerOutput() + 0.005F * this.fm.EI.engines[0].tOilOut : 0.0F, 0.0F, 10.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelPrs2a", cvt(this.fm.M.fuel > 1.0F ? 2.2F * this.fm.EI.engines[1].getPowerOutput() + 0.005F * this.fm.EI.engines[1].tOilOut : 0.0F, 0.0F, 10.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasTemp1a", cvt(this.fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasTemp2a", cvt(this.fm.EI.engines[1].tWaterOut, 300.0F, 1000.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zHorizon1b", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.025F, -0.025F);
    this.mesh.chunkSetLocate("zHorizon1a", xyz, ypr);
    this.mesh.chunkSetAngles("zSlide1a", cvt(getBall(6.0D), -6.0F, 6.0F, -15.0F, 15.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRoll1a", cvt(this.setNew.turn, -0.23562F, 0.23562F, 26.5F, -26.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", this.setNew.vspeed >= 0.0F ? floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : -floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("zAzimuth1b", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zAzimuth1a", -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("zAzimuth1a", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zAzimuth1b", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Course1a", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Course1b", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Air1", 135.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelQ1", cvt(this.fm.M.fuel, 0.0F, 2400.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelQ2", cvt(this.fm.M.fuel, 0.0F, 4000.0F, 0.0F, 60.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_Red1", this.fm.getSpeedKMH() < 160.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.M.fuel < 600.0F);
    this.mesh.chunkVisible("Z_Red3", this.fm.M.fuel < 250.0F);
    this.mesh.chunkVisible("Z_Red4", this.fm.CT.getFlap() < 0.1F);
    this.mesh.chunkVisible("Z_Red5", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("Z_Red6", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("Z_Red7", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("Z_Red8", this.fm.CT.getFlap() < 0.1F);
    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getFlap() > 0.665F);
    this.mesh.chunkVisible("Z_Green2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_Green3", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.cgear));
    this.mesh.chunkVisible("Z_Green4", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("Z_Green5", this.fm.CT.getFlap() > 0.665F);
    this.mesh.chunkVisible("Z_White1", this.fm.CT.getFlap() > 0.265F);
    this.mesh.chunkVisible("Z_White2", this.fm.CT.getFlap() > 0.265F);

    this.mesh.chunkSetAngles("Z_Course1b", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Course1a", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight() {
    if (this.cockpitLightControl) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
      this.mesh.chunkVisible("Z_Holes3_D1", true);
      this.mesh.chunkVisible("Z_Holes4_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zHorizon1a", false);
      this.mesh.chunkVisible("zHorizon1b", false);
      this.mesh.chunkVisible("zSlide1a", false);
      this.mesh.chunkVisible("zRoll1a", false);
      this.mesh.chunkVisible("zRPM1", false);
      this.mesh.chunkVisible("zVariometer1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Z_Holes5_D1", true);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("zGasPrs1b", false);
      this.mesh.chunkVisible("zGasPrs2b", false);
      this.mesh.chunkVisible("zSpeed1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("pribors4_d1", true);
      this.mesh.chunkVisible("pribors4", false);
      this.mesh.chunkVisible("zGasPrs2a", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
      this.mesh.chunkVisible("zOilPrs2a", false);
      this.mesh.chunkVisible("zFuelPrs1a", false);
      this.mesh.chunkVisible("zFuelPrs2a", false);
      this.mesh.chunkVisible("zGasTemp1a", false);
      this.mesh.chunkVisible("zGasTemp2a", false);
      this.mesh.chunkVisible("zFuelQ1", false);
      this.mesh.chunkVisible("zFuelQ2", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("Z_Holes6_D1", true);
      this.mesh.chunkVisible("pribors3_d1", true);
      this.mesh.chunkVisible("pribors3", false);
      this.mesh.chunkVisible("Z_Course1a", false);
      this.mesh.chunkVisible("Z_Course1b", false);
      this.mesh.chunkVisible("zRPM2", false);
      this.mesh.chunkVisible("zGasPrs1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0);
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    leave();
    super.doFocusLeave();
  }

  private void enter()
  {
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 33.3");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(180.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.mesh.chunkVisible("Peribox", true);
    this.mesh.chunkVisible("zReticle2", true);
  }
  private void leave() {
    if (!this.bEntered) return;
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
    CmdEnv.top().exec("fov " + this.saveFov);
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(false);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    localHookPilot.setSimpleUse(false);
    boolean bool = HotKeyEnv.isEnabled("aircraftView");
    HotKeyEnv.enable("PanView", bool);
    HotKeyEnv.enable("SnapView", bool);
    this.bEntered = false;
    this.mesh.chunkVisible("Peribox", false);
    this.mesh.chunkVisible("zReticle2", false);
  }
  public void doToggleAim(boolean paramBoolean) {
    if (!isFocused()) return;
    if (isToggleAim() == paramBoolean) return;
    if (paramBoolean) enter(); else
      leave(); 
  }

  public void destroy() {
    leave();
    super.destroy();
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitAR_234B2.this.fm != null) {
        CockpitAR_234B2.access$102(CockpitAR_234B2.this, CockpitAR_234B2.this.setOld); CockpitAR_234B2.access$202(CockpitAR_234B2.this, CockpitAR_234B2.this.setNew); CockpitAR_234B2.access$302(CockpitAR_234B2.this, CockpitAR_234B2.this.setTmp);

        CockpitAR_234B2.this.setNew.altimeter = CockpitAR_234B2.this.fm.getAltitude();
        CockpitAR_234B2.this.setNew.throttle1 = (0.92F * CockpitAR_234B2.this.setOld.throttle1 + 0.08F * CockpitAR_234B2.this.fm.EI.engines[0].getControlThrottle());
        CockpitAR_234B2.this.setNew.throttle2 = (0.92F * CockpitAR_234B2.this.setOld.throttle2 + 0.08F * CockpitAR_234B2.this.fm.EI.engines[1].getControlThrottle());
        CockpitAR_234B2.this.setNew.vspeed = ((499.0F * CockpitAR_234B2.this.setOld.vspeed + CockpitAR_234B2.this.fm.getVertSpeed()) / 500.0F);

        float f = CockpitAR_234B2.this.waypointAzimuth();
        if (CockpitAR_234B2.this.useRealisticNavigationInstruments())
        {
          CockpitAR_234B2.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitAR_234B2.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitAR_234B2.this.setNew.waypointAzimuth.setDeg(CockpitAR_234B2.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitAR_234B2.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitAR_234B2.this.setNew.azimuth.setDeg(CockpitAR_234B2.this.setOld.azimuth.getDeg(1.0F), CockpitAR_234B2.this.fm.Or.azimut());

        CockpitAR_234B2.this.w.set(CockpitAR_234B2.this.fm.getW());
        CockpitAR_234B2.this.fm.Or.transform(CockpitAR_234B2.this.w);
        CockpitAR_234B2.this.setNew.turn = ((12.0F * CockpitAR_234B2.this.setOld.turn + CockpitAR_234B2.this.w.z) / 13.0F);

        CockpitAR_234B2.this.setNew.beaconDirection = ((10.0F * CockpitAR_234B2.this.setOld.beaconDirection + CockpitAR_234B2.this.getBeaconDirection()) / 11.0F);
        CockpitAR_234B2.this.setNew.beaconRange = ((10.0F * CockpitAR_234B2.this.setOld.beaconRange + CockpitAR_234B2.this.getBeaconRange()) / 11.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle1;
    float throttle2;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float turn;
    float beaconDirection;
    float beaconRange;
    float vspeed;
    private final CockpitAR_234B2 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitAR_234B2.1 arg2)
    {
      this();
    }
  }
}