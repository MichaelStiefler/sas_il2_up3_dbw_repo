package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitKI_27KO extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictRad = 0.0F;
  private float pictGun = 0.0F;
  private float pictFlap = 0.0F;
  private boolean bNeedSetUp = true;
  private long oldTime = -1L;

  private static final float[] speedometerScale = { 0.0F, 6.5F, 16.5F, 49.0F, 91.5F, 143.5F, 199.0F, 260.0F, 318.0F, 376.5F, 433.0F, 484.0F, 534.0F, 576.0F, 620.0F, 660.0F };

  private static final float[] oilScale = { 0.0F, -27.5F, 12.0F, 59.5F, 127.0F, 212.5F, 311.5F };
  private float saveFov;
  private boolean bEntered = false;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

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
    CmdEnv.top().exec("fov 31");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("SuperReticle", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_BoxTinter", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("EDET", false);
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
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("SuperReticle", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_BoxTinter", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("EDET", true);
  }
  public void destroy() {
    leave();
    super.destroy();
  }

  public void doToggleAim(boolean paramBoolean) {
    if (!isFocused()) return;
    if (isToggleAim() == paramBoolean) return;
    if (paramBoolean) enter(); else
      leave();
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) {
      f += 180.0F;
    }
    while (f > 180.0F) {
      f -= 180.0F;
    }
    return f;
  }

  public CockpitKI_27KO()
  {
    super("3DO/Cockpit/Ki-27(Ko)/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauge1", "gauge2", "gauge3", "gauge4", "gauge1_d", "gauge2_d", "gauge3_d", "gauge4_d", "Arrows", "Digits" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    ((KI_27)aircraft()); if (KI_27.bChangedPit) {
      reflectPlaneToModel();
      ((KI_27)aircraft()); KI_27.bChangedPit = false;
    }

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor(), 0.05F, 0.95F, 0.0F, 0.55F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ReViTinter", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_BoxTinter", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -180.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ColumnBase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 20.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ColumnWire", 0.0F, this.pictElev * 20.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PedalBase", 0.0F, -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", 0.0F, 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", 0.0F, 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightWire", -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftWire", -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Radiat", 0.0F, -450.0F * (this.pictRad = 0.9F * this.pictRad + 0.1F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator()), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle1", 0.0F, cvt(this.setNew.throttle, 0.0F, 1.1F, -38.0F, 38.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle2", 0.0F, 30.0F * (this.pictGun = 0.8F * this.pictGun + 0.2F * (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[0] != 0 ? 1.0F : 0.0F)), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPitch1", 0.0F, cvt(this.setNew.prop, 0.0F, 1.0F, -38.0F, 38.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTrim1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator, -0.5F, 0.5F, 35.0F, -35.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTrim2", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator, -0.5F, 0.5F, -35.0F, 35.0F), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.setNew.mix, 0.0F, 1.2F, 0.03675F, 0.0F);
    long l1 = Time.current();
    long l2 = l1 - this.oldTime;
    this.oldTime = l1;
    float f2 = (float)l2 * 0.00016F;
    if (this.pictFlap < this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl) {
      if (this.pictFlap + f2 >= this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl)
        this.pictFlap = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl;
      else
        this.pictFlap += f2;
    }
    else if (this.pictFlap - f2 <= this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl)
      this.pictFlap = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl;
    else {
      this.pictFlap -= f2;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Flaps", 0.0F, -3450.0F * this.pictFlap, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mag1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 3.0F, 76.5F, -28.5F), 0.0F, 0.0F);

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 300.0F, 0.0F, 15.0F), speedometerScale), 0.0F, 0.0F);

    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, -30.0F, 30.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6.0D), -6.0F, 6.0F, 6.0F, -6.0F), 0.0F, 0.0F);
    float f1 = this.setNew.vspeed;
    if (Math.abs(f1) < 5.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", cvt(f1, -5.0F, 5.0F, 90.0F, -90.0F), 0.0F, 0.0F);
    else if (f1 > 0.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", cvt(f1, 5.0F, 30.0F, -90.0F, -180.0F), 0.0F, 0.0F);
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", cvt(f1, -30.0F, -5.0F, 180.0F, 90.0F), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Clock_H", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Clock_Min", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);
    f1 = cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 8.1F, 0.0F, 20.0F);
    for (int i = 1; i < 20; i++) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilP" + (i < 10 ? "0" + i : new StringBuffer().append("").append(i).toString()), f1 > 20 - i);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Manipres", cvt(this.setNew.manifold, 0.33339F, 1.66661F, 150.0F, -150.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 200.0F, 3000.0F, -8.5F, -323.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuelpres", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 2.0F, 0.0F, -360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oiltemp1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 130.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 5.5F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Tempcyl", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 360.0F, 0.0F, -90.599998F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 108.0F, -41.0F, -320.0F), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage5", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage7", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TurnBank1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TurnBank2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Climb1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Manipres", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Fuel", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Fuelpres", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Oiltemp1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Oilpres1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Clock_H", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Clock_Min", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Tempcyl", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage4", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage6", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage5", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage4", true);
    }
  }

  public void toggleDim()
  {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLIn_CAP", localHierMesh.isChunkVisible("WingLIn_CAP"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLIn_D0", localHierMesh.isChunkVisible("WingLIn_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLIn_D1", localHierMesh.isChunkVisible("WingLIn_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLIn_D2", localHierMesh.isChunkVisible("WingLIn_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLIn_D3", localHierMesh.isChunkVisible("WingLIn_D3"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRIn_CAP", localHierMesh.isChunkVisible("WingRIn_CAP"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRIn_D0", localHierMesh.isChunkVisible("WingRIn_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRIn_D1", localHierMesh.isChunkVisible("WingRIn_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRIn_D2", localHierMesh.isChunkVisible("WingRIn_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRIn_D3", localHierMesh.isChunkVisible("WingRIn_D3"));
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null) {
        CockpitKI_27KO.access$102(CockpitKI_27KO.this, CockpitKI_27KO.this.setOld); CockpitKI_27KO.access$202(CockpitKI_27KO.this, CockpitKI_27KO.this.setNew); CockpitKI_27KO.access$302(CockpitKI_27KO.this, CockpitKI_27KO.this.setTmp);

        if (CockpitKI_27KO.this.cockpitDimControl) {
          if (CockpitKI_27KO.this.setNew.dimPos < 1.0F) CockpitKI_27KO.this.setNew.dimPos = (CockpitKI_27KO.this.setOld.dimPos + 0.03F);
        }
        else if (CockpitKI_27KO.this.setNew.dimPos > 0.0F) CockpitKI_27KO.this.setNew.dimPos = (CockpitKI_27KO.this.setOld.dimPos - 0.03F);

        CockpitKI_27KO.this.setNew.manifold = (0.8F * CockpitKI_27KO.this.setOld.manifold + 0.2F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure());
        CockpitKI_27KO.this.setNew.throttle = (0.8F * CockpitKI_27KO.this.setOld.throttle + 0.2F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl);
        CockpitKI_27KO.this.setNew.prop = (0.8F * CockpitKI_27KO.this.setOld.prop + 0.2F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp());
        CockpitKI_27KO.this.setNew.mix = (0.8F * CockpitKI_27KO.this.setOld.mix + 0.2F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix());

        CockpitKI_27KO.this.setNew.man = (0.92F * CockpitKI_27KO.this.setOld.man + 0.08F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure());
        CockpitKI_27KO.this.setNew.altimeter = CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();

        float f = CockpitKI_27KO.this.waypointAzimuth();
        CockpitKI_27KO.this.setNew.azimuth.setDeg(CockpitKI_27KO.this.setOld.azimuth.getDeg(1.0F), CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
        CockpitKI_27KO.this.setNew.waypointDeviation.setDeg(CockpitKI_27KO.this.setOld.waypointDeviation.getDeg(0.1F), f - CockpitKI_27KO.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-5.0F, 5.0F));
        CockpitKI_27KO.this.setNew.waypointAzimuth.setDeg(CockpitKI_27KO.this.setOld.waypointAzimuth.getDeg(1.0F), f);

        CockpitKI_27KO.this.setNew.vspeed = (0.5F * CockpitKI_27KO.this.setOld.vspeed + 0.5F * CockpitKI_27KO.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed());
      }

      return true;
    }
  }

  private class Variables
  {
    float dimPos;
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float man;
    float vspeed;
    float manifold;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork waypointDeviation;
    private final CockpitKI_27KO this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.dimPos = 0.0F;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDeviation = new AnglesFork();
    }

    Variables(CockpitKI_27KO.1 arg2)
    {
      this();
    }
  }
}