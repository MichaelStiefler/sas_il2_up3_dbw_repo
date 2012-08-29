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
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitDXXI_SARJA3_EARLY extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float flaps = 0.0F;
  private float pictManifold;
  private boolean bEntered = false;
  private float saveFov;
  private boolean hasRevi = false;
  private float tailWheelLock = 1.0F;
  private int flapsDirection = 0;
  private float flapsPump = 0.0F;
  private float flapsPumpIncrement = 0.1F;
  private LightPointActor light1;
  private LightPointActor light2;
  private LightPointActor light3;
  private LightPointActor light4;
  private LightPointActor light5;
  private DXXI_SARJA3_EARLY ac = null;
  private float rpmGeneratedPressure = 0.0F;
  private float oilPressure = 0.0F;

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

  public CockpitDXXI_SARJA3_EARLY()
  {
    super("3DO/Cockpit/DXXI_SARJA3_EARLY/hier.him", "bf109");
    this.ac = ((DXXI_SARJA3_EARLY)aircraft());
    this.ac.registerPit(this);

    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK01");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK01", this.light1);

    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK02");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK02", this.light2);

    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK03");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light3 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light3.light.setColor(126.0F, 232.0F, 245.0F);
    this.light3.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK03", this.light3);

    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK04");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light4 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light4.light.setColor(126.0F, 232.0F, 245.0F);
    this.light4.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK04", this.light4);

    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK05");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light5 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light5.light.setColor(126.0F, 232.0F, 245.0F);
    this.light5.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK05", this.light5);

    this.cockpitNightMats = new String[] { "gyro", "gauge_speed", "gauge_alt", "gauge_fuel", "gauges_various_1", "gauges_various_2", "LABELS1", "gauges_various_3", "gauges_various_4", "gauges_various_3_dam", "gauge_alt_dam", "gauges_various_2_dam" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    if (World.cur().isHakenAllowed()) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("LABELS3", "LABELS3_HAKEN");
    }
    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void setRevi()
  {
    this.hasRevi = true;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("reticle", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("reticlemask", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi_D0", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tubeSight", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tubeSightLens", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tube_inside", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tube_mask", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_inside", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassTube", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassRevi", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_reviIron", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_reviDimmer", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_reviDimmerLever", true);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("PanelC", "PanelCRevi");
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    float f1 = 0.0F;

    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    if (((DXXI_SARJA3_EARLY)aircraft()).jdField_bChangedPit_of_type_Boolean)
    {
      reflectPlaneToModel();
      ((DXXI_SARJA3_EARLY)aircraft()).jdField_bChangedPit_of_type_Boolean = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_reviIron", 90.0F * this.setNew.stbyPosition, 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_manifold", cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure() * 76.0F, 30.0F, 120.0F, 22.0F, 296.0F), 0.0F, 0.0F);

    f1 = -15.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_stick_horiz_axis", f1, 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_aileron_rods", -f1 / 14.0F, 0.0F, 0.0F);

    f1 = 14.0F * (this.pictElev = 0.85F * this.pictElev + 0.2F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Stick", f1, 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_elev_wire1", -f1, 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_elev_wire2", -f1, 0.0F, 0.0F);

    f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_wheel_break_valve", -12.0F * f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_pedal_L", 24.0F * f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_pedal_R", -24.0F * f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_rudder_rod_L", -25.0F * f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_rudder_rod_R", 25.0F * f1, 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle", -70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mixture", -70.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_alt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_alt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    float f2 = Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH());

    if (f2 < 80.0F) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 0.0F, 80.0F, 0.0F, -11.0F), 0.0F, 0.0F);
    }
    else if (f2 < 120.0F) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 80.0F, 120.0F, -11.0F, -40.0F), 0.0F, 0.0F);
    }
    else if (f2 < 160.0F) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 120.0F, 160.0F, -40.0F, -78.5F), 0.0F, 0.0F);
    }
    else if (f2 < 200.0F) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 160.0F, 200.0F, -78.5F, -130.5F), 0.0F, 0.0F);
    }
    else if (f2 < 360.0F) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 200.0F, 360.0F, -130.5F, -329.0F), 0.0F, 0.0F);
    }
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_speed", cvt(f2, 360.0F, 600.0F, -329.0F, -550.0F), 0.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_gyro", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_clock_hour", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_clock_minute", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_clock_sec", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_fuel", -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel, 0.0F, 300.0F, 0.0F, 52.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_oiltemp", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 329.0F), 0.0F, 0.0F);

    f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM();

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_rpm", cvt(f1, 440.0F, 3320.0F, 0.0F, -332.0F), 0.0F, 0.0F);

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() < -110.0F) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 110.0F))
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
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut > 90.0F)
    {
      f3 = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 90.0F, 110.0F, 1.1F, 1.5F);
    }
    else if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut < 50.0F)
    {
      f3 = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 50.0F, 2.0F, 0.9F);
    }
    else
    {
      f3 = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }

    float f4 = f3 * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() * this.oilPressure;

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_oilpressure", cvt(f4, 0.0F, 7.0F, 0.0F, 315.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_cylheadtemp", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 110.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_fuelpressure", cvt(this.rpmGeneratedPressure, 0.0F, 1800.0F, 0.0F, 65.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_magneto", -30.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    resetYPRmodifier();

    Cockpit.xyz[1] = (-cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -20.0F, 20.0F, 0.04F, -0.04F));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Need_red_liquid", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_Turn", -cvt(this.setNew.turn, -0.2F, 0.2F, -30.0F, 30.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_bank", -cvt(getBall(8.0D), -8.0F, 8.0F, 14.0F, -14.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_variometer", -cvt(this.setNew.vspeed, -20.0F, 20.0F, 180.0F, -180.0F), 0.0F, 0.0F);

    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_oxygeneflow", 0.0F, 0.0F, 0.0F);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_oxygeneflow", 200.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_oxygenetank", 250.0F, 0.0F, 0.0F);

    if (this.ac.jdField_tiltCanopyOpened_of_type_Boolean)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CanopyL1", this.ac.jdField_canopyF_of_type_Float * 125.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CanopyL2", this.ac.jdField_canopyF_of_type_Float * 80.0F, 0.0F, 0.0F);
    }
    else
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CanopyL1", 0.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CanopyL2", 0.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sliding_window_L", cvt(this.ac.jdField_canopyF_of_type_Float, 0.0F, 1.0F, 0.0F, 0.75F), 0.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flaps_indicator", 0.7F * this.flaps, 0.0F, 0.0F);

    if (this.flapsDirection == 1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flaps_valve", -33.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flapsLeverKnob", 33.0F, 0.0F, 0.0F);
    }
    else if (this.flapsDirection == -1)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flaps_valve", 33.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flapsLeverKnob", -33.0F, 0.0F, 0.0F);
    }
    else
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flaps_valve", 0.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flapsLeverKnob", 0.0F, 0.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_trim_indicator", 1.9F * -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_trim_wheel", 600.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);

    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasBrakeControl)
    {
      f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake();

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_break_handle", f1 * 20.0F, 0.0F, 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_breakpressureR", cvt(f1 + f1 * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_breakpressureL", -cvt(f1 - f1 * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_breakpressure1", -150.0F + f1 * 20.0F, 0.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_flaps_pump", -this.flapsPump * 40.0F, 0.0F, 0.0F);

    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bLandingLightOn)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_landing_light", -35.0F, 0.0F, 0.0F);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_landing_light", 0.0F, 0.0F, 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bNavLightsOn)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_navigation_light", -35.0F, 0.0F, 0.0F);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_navigation_light", 0.0F, 0.0F, 0.0F);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_cockpit_light", -35.0F, 0.0F, 0.0F);
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_switch_cockpit_light", 0.0F, 0.0F, 0.0F);
    }
    if (this.tailWheelLock >= 1.0F)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57.0F, 0.0F, 7.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_tailwheel_lever_wire", this.tailWheelLock * 57.0F, 0.0F, 7.0F);
    }
    else
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57.0F, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_tailwheel_lever_wire", this.tailWheelLock * 57.0F, 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_wheelLockKnob", this.tailWheelLock * 57.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_extinguisher", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getExtinguishers() * 95.0F, 0.0F, 0.0F);

    if (this.hasRevi)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_reviDimmer", -cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -90.0F), 0.0F, 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_reviDimmerLever", -cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 0.004F), 0.0F, 0.0F);
    }
    else
    {
      f1 = cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -130.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap", f1, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap_big", f1, 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap_inside", f1, 0.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_trigger", 1.6F * (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[0] != 0 ? 1 : 0), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
    {
      if (this.hasRevi)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("reticle", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("reticlemask", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi_D1", true);
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageFront2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageRear", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageFront", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageRear", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Gauges_d0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Gauges_d1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageFront", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_manifold", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_oilpressure", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_rpm", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_alt1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_alt2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_variometer", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_clock_sec", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_clock_minute", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_clock_hour", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_clock_timer", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Need_cylheadtemp", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageLeft", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageLeft", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageLeft", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageLeft2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageLeft", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageRight", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageRight", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GlassDamageRight", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamageRight", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
    {
      if (this.hasRevi)
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("OilRevi", true);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Oil", true);
    }
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
    if (this.ac.blisterRemoved)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CanopyL1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CanopyL2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sliding_window_L", false);
      doToggleUp(false);
    }
  }

  public void doToggleUp(boolean paramBoolean)
  {
    if (this.ac.jdField_tiltCanopyOpened_of_type_Boolean)
      super.doToggleUp(paramBoolean);
  }

  private void enter()
  {
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(true);
    this.bEntered = true;
    if (this.hasRevi)
    {
      localHookPilot.setAim(new Point3d(-0.87D, 0.0028D, 0.8028D));
    }
    else
    {
      this.saveFov = Main3D.FOVX;
      CmdEnv.top().exec("fov 31");
      Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
      if (localHookPilot.isPadlock())
        localHookPilot.stopPadlock();
      localHookPilot.setSimpleUse(true);
      localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
      HotKeyEnv.enable("PanView", false);
      HotKeyEnv.enable("SnapView", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("superretic", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_big", true);
    }
  }

  private void leave(boolean paramBoolean)
  {
    if (this.bEntered)
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      if (paramBoolean)
        this.bEntered = false;
      if (!this.hasRevi)
      {
        Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
        CmdEnv.top().exec("fov " + this.saveFov);
        localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
        localHookPilot.setSimpleUse(false);
        boolean bool = HotKeyEnv.isEnabled("aircraftView");
        HotKeyEnv.enable("PanView", bool);
        HotKeyEnv.enable("SnapView", bool);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("superretic", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_big", false);
      }
    }
  }

  public void destroy()
  {
    leave(false);
    super.destroy();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
    {
      if (paramBoolean)
        enter();
      else
        leave(true);
    }
  }

  public void toggleDim()
  {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D2o", localMat);
  }

  protected boolean doFocusEnter()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("tail1_internal_d0", false);
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      if ((this.hasRevi) && (this.bEntered))
        enter();
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("tail1_internal_d0", true);
    if (isFocused())
    {
      leave(false);
      super.doFocusLeave();
    }
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.003F, 0.2F);
      this.light2.light.setEmit(0.004F, 0.2F);
      this.light3.light.setEmit(0.004F, 0.2F);
      this.light4.light.setEmit(0.001F, 0.1F);
      this.light5.light.setEmit(0.003F, 0.2F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      this.light3.light.setEmit(0.0F, 0.0F);
      this.light4.light.setEmit(0.0F, 0.0F);
      this.light5.light.setEmit(0.0F, 0.0F);
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
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getLoc(this.setOld.planeLoc);
    }

    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getLoc(this.setNew.planeLoc);

    this.setNew.planeMove.set(this.setNew.planeLoc);
    this.setNew.planeMove.sub(this.setOld.planeLoc);

    this.accel.set(this.setNew.planeMove);
    this.accel.sub(this.setOld.planeMove);

    this.accel.scale(this.compassSc);

    this.accel.jdField_x_of_type_Double = (-this.accel.jdField_x_of_type_Double);
    this.accel.jdField_y_of_type_Double = (-this.accel.jdField_y_of_type_Double);
    this.accel.jdField_z_of_type_Double = (-this.accel.jdField_z_of_type_Double - 1.0D);
    this.accel.scale(this.compassAcc);

    if (this.accel.length() > -compassZ * 0.7D) {
      this.accel.scale(-compassZ * 0.7D / this.accel.length());
    }

    for (int i = 0; i < 4; i++)
    {
      this.compassSpeed[i].set(this.setOld.compassPoint[i]);
      this.compassSpeed[i].sub(this.setNew.compassPoint[i]);
    }

    for (int j = 0; j < 4; j++)
    {
      double d1 = this.compassSpeed[j].length();
      d1 = 0.985D / (1.0D + d1 * d1 * 15.0D);

      this.compassSpeed[j].scale(d1);
    }

    Vector3d localVector3d1 = new Vector3d();
    localVector3d1.set(this.setOld.compassPoint[0]);
    localVector3d1.add(this.setOld.compassPoint[1]);
    localVector3d1.add(this.setOld.compassPoint[2]);
    localVector3d1.add(this.setOld.compassPoint[3]);
    localVector3d1.normalize();

    for (int k = 0; k < 4; k++) {
      Vector3d localVector3d2 = new Vector3d();
      double d2 = localVector3d1.dot(this.compassSpeed[k]);
      localVector3d2.set(localVector3d1);
      d2 *= 0.28D;

      localVector3d2.scale(-d2);

      this.compassSpeed[k].add(localVector3d2);
    }

    for (int m = 0; m < 4; m++)
      this.compassSpeed[m].add(this.accel);
    this.compassSpeed[0].add(this.compassNorth);
    this.compassSpeed[2].add(this.compassSouth);

    for (int n = 0; n < 4; n++) {
      this.setNew.compassPoint[n].set(this.setOld.compassPoint[n]);
      this.setNew.compassPoint[n].add(this.compassSpeed[n]);
    }

    localVector3d1.set(this.setNew.compassPoint[0]);
    localVector3d1.add(this.setNew.compassPoint[1]);
    localVector3d1.add(this.setNew.compassPoint[2]);
    localVector3d1.add(this.setNew.compassPoint[3]);
    localVector3d1.scale(0.25D);
    Vector3d localVector3d3 = new Vector3d(localVector3d1);
    localVector3d3.normalize();
    localVector3d3.scale(-compassZ);
    localVector3d3.sub(localVector3d1);

    for (int i1 = 0; i1 < 4; i1++) {
      this.setNew.compassPoint[i1].add(localVector3d3);
    }

    for (int i2 = 0; i2 < 4; i2++)
      this.setNew.compassPoint[i2].normalize();
    for (int i3 = 0; i3 < 2; i3++)
    {
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);

      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);

      for (int i4 = 0; i4 < 4; i4++) {
        this.setNew.compassPoint[i4].normalize();
      }
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);

      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      for (i5 = 0; i5 < 4; i5++) {
        this.setNew.compassPoint[i5].normalize();
      }

    }

    Orientation localOrientation = new Orientation();
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getOrient(localOrientation);
    for (int i5 = 0; i5 < 4; i5++) {
      this.setNew.cP[i5].set(this.setNew.compassPoint[i5]);
      localOrientation.transformInv(this.setNew.cP[i5]);
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

    float f = (float)(localVector3d5.jdField_x_of_type_Double * localVector3d5.jdField_x_of_type_Double + localVector3d5.jdField_y_of_type_Double * localVector3d5.jdField_y_of_type_Double);
    if ((f > this.compassLimit) || (localVector3d4.jdField_z_of_type_Double > 0.0D))
    {
      for (int i6 = 0; i6 < 4; i6++)
      {
        this.setNew.cP[i6].set(this.setOld.cP[i6]);
        this.setNew.compassPoint[i6].set(this.setOld.cP[i6]);

        localOrientation.transform(this.setNew.compassPoint[i6]);
      }

      localVector3d4.set(this.setNew.cP[0]);
      localVector3d4.add(this.setNew.cP[1]);
      localVector3d4.add(this.setNew.cP[2]);
      localVector3d4.add(this.setNew.cP[3]);
      localVector3d4.scale(0.25D);
    }

    localVector3d5.set(this.setNew.cP[0]);
    localVector3d5.sub(localVector3d4);

    double d3 = -Math.atan2(localVector3d4.jdField_y_of_type_Double, -localVector3d4.jdField_z_of_type_Double);
    vectorRot2(localVector3d4, d3);
    vectorRot2(localVector3d5, d3);

    double d4 = Math.atan2(localVector3d4.jdField_x_of_type_Double, -localVector3d4.jdField_z_of_type_Double);

    vectorRot1(localVector3d5, -d4);

    double d5 = Math.atan2(localVector3d5.jdField_y_of_type_Double, localVector3d5.jdField_x_of_type_Double);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_compassBase", -(float)(d3 * 180.0D / 3.1415926D), -(float)(d4 * 180.0D / 3.1415926D), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Need_compass", 0.0F, (float)(90.0D - d5 * 180.0D / 3.1415926D), 0.0F);

    this.compassFirst += 1;
  }

  private void vectorRot1(Vector3d paramVector3d, double paramDouble)
  {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.jdField_x_of_type_Double * d2 - paramVector3d.jdField_z_of_type_Double * d1;
    paramVector3d.jdField_z_of_type_Double = (paramVector3d.jdField_x_of_type_Double * d1 + paramVector3d.jdField_z_of_type_Double * d2);
    paramVector3d.jdField_x_of_type_Double = d3;
  }

  private void vectorRot2(Vector3d paramVector3d, double paramDouble) {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.jdField_y_of_type_Double * d2 - paramVector3d.jdField_z_of_type_Double * d1;
    paramVector3d.jdField_z_of_type_Double = (paramVector3d.jdField_y_of_type_Double * d1 + paramVector3d.jdField_z_of_type_Double * d2);
    paramVector3d.jdField_y_of_type_Double = d3;
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
    float dimPos;
    Point3d planeLoc;
    Point3d planeMove;
    Vector3d[] compassPoint;
    Vector3d[] cP;
    private final CockpitDXXI_SARJA3_EARLY this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();

      this.planeLoc = new Point3d();
      this.planeMove = new Point3d();
      this.compassPoint = new Vector3d[4];
      this.cP = new Vector3d[4];

      this.compassPoint[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), CockpitDXXI_SARJA3_EARLY.compassZ);

      this.compassPoint[1] = new Vector3d(-Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), 0.0D, CockpitDXXI_SARJA3_EARLY.compassZ);
      this.compassPoint[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), CockpitDXXI_SARJA3_EARLY.compassZ);
      this.compassPoint[3] = new Vector3d(Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), 0.0D, CockpitDXXI_SARJA3_EARLY.compassZ);

      this.cP[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), CockpitDXXI_SARJA3_EARLY.compassZ);

      this.cP[1] = new Vector3d(-Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), 0.0D, CockpitDXXI_SARJA3_EARLY.compassZ);
      this.cP[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), CockpitDXXI_SARJA3_EARLY.compassZ);
      this.cP[3] = new Vector3d(Math.sqrt(1.0D - CockpitDXXI_SARJA3_EARLY.compassZ * CockpitDXXI_SARJA3_EARLY.compassZ), 0.0D, CockpitDXXI_SARJA3_EARLY.compassZ);
    }

    Variables(CockpitDXXI_SARJA3_EARLY.1 arg2)
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
      if (CockpitDXXI_SARJA3_EARLY.this.bNeedSetUp)
      {
        CockpitDXXI_SARJA3_EARLY.this.reflectPlaneMats();
        CockpitDXXI_SARJA3_EARLY.access$102(CockpitDXXI_SARJA3_EARLY.this, false);
      }
      if ((CockpitDXXI_SARJA3_EARLY.this.ac != null) && (CockpitDXXI_SARJA3_EARLY.this.ac.jdField_bChangedPit_of_type_Boolean))
      {
        CockpitDXXI_SARJA3_EARLY.this.reflectPlaneToModel();
        CockpitDXXI_SARJA3_EARLY.this.ac.jdField_bChangedPit_of_type_Boolean = false;
      }
      CockpitDXXI_SARJA3_EARLY.access$302(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.setOld);
      CockpitDXXI_SARJA3_EARLY.access$402(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.setNew);
      CockpitDXXI_SARJA3_EARLY.access$502(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.setTmp);
      if (((CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x2) != 0) && (CockpitDXXI_SARJA3_EARLY.this.setNew.stbyPosition < 1.0F))
      {
        CockpitDXXI_SARJA3_EARLY.this.setNew.stbyPosition = (CockpitDXXI_SARJA3_EARLY.this.setOld.stbyPosition + 0.0125F);
        CockpitDXXI_SARJA3_EARLY.this.setOld.stbyPosition = CockpitDXXI_SARJA3_EARLY.this.setNew.stbyPosition;
      }
      CockpitDXXI_SARJA3_EARLY.this.setNew.altimeter = CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();

      CockpitDXXI_SARJA3_EARLY.this.setNew.azimuth.setDeg(CockpitDXXI_SARJA3_EARLY.this.setOld.azimuth.getDeg(1.0F), CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.azimut());

      CockpitDXXI_SARJA3_EARLY.this.setNew.throttle = ((10.0F * CockpitDXXI_SARJA3_EARLY.this.setOld.throttle + CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle()) / 11.0F);

      CockpitDXXI_SARJA3_EARLY.this.setNew.mix = ((10.0F * CockpitDXXI_SARJA3_EARLY.this.setOld.mix + CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix()) / 11.0F);

      CockpitDXXI_SARJA3_EARLY.this.setNew.prop = CockpitDXXI_SARJA3_EARLY.this.setOld.prop;
      if (CockpitDXXI_SARJA3_EARLY.this.setNew.prop < CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() - 0.01F)
        CockpitDXXI_SARJA3_EARLY.this.setNew.prop += 0.0025F;
      if (CockpitDXXI_SARJA3_EARLY.this.setNew.prop > CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() + 0.01F)
        CockpitDXXI_SARJA3_EARLY.this.setNew.prop -= 0.0025F;
      CockpitDXXI_SARJA3_EARLY.this.w.set(CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
      CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(CockpitDXXI_SARJA3_EARLY.this.w);
      CockpitDXXI_SARJA3_EARLY.this.setNew.turn = ((12.0F * CockpitDXXI_SARJA3_EARLY.this.setOld.turn + CockpitDXXI_SARJA3_EARLY.this.w.z) / 13.0F);
      CockpitDXXI_SARJA3_EARLY.this.setNew.vspeed = ((299.0F * CockpitDXXI_SARJA3_EARLY.this.setOld.vspeed + CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 300.0F);
      CockpitDXXI_SARJA3_EARLY.access$702(CockpitDXXI_SARJA3_EARLY.this, 0.8F * CockpitDXXI_SARJA3_EARLY.this.pictSupc + 0.2F * CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlCompressor());

      if (CockpitDXXI_SARJA3_EARLY.this.cockpitDimControl)
      {
        if (CockpitDXXI_SARJA3_EARLY.this.setNew.dimPos < 1.0F)
          CockpitDXXI_SARJA3_EARLY.this.setNew.dimPos = (CockpitDXXI_SARJA3_EARLY.this.setOld.dimPos + 0.03F);
      }
      else if (CockpitDXXI_SARJA3_EARLY.this.setNew.dimPos > 0.0F) {
        CockpitDXXI_SARJA3_EARLY.this.setNew.dimPos = (CockpitDXXI_SARJA3_EARLY.this.setOld.dimPos - 0.03F);
      }
      if (CockpitDXXI_SARJA3_EARLY.this.flaps == CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap())
      {
        CockpitDXXI_SARJA3_EARLY.access$902(CockpitDXXI_SARJA3_EARLY.this, 0);
        CockpitDXXI_SARJA3_EARLY.this.sfxStop(16);
      }
      else if (CockpitDXXI_SARJA3_EARLY.this.flaps < CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap())
      {
        if (CockpitDXXI_SARJA3_EARLY.this.flapsDirection == 0)
          CockpitDXXI_SARJA3_EARLY.this.sfxStart(16);
        CockpitDXXI_SARJA3_EARLY.access$802(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
        CockpitDXXI_SARJA3_EARLY.access$1002(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.flapsPump + CockpitDXXI_SARJA3_EARLY.this.flapsPumpIncrement);
        CockpitDXXI_SARJA3_EARLY.access$902(CockpitDXXI_SARJA3_EARLY.this, 1);
        if ((CockpitDXXI_SARJA3_EARLY.this.flapsPump < 0.0F) || (CockpitDXXI_SARJA3_EARLY.this.flapsPump > 1.0F))
          CockpitDXXI_SARJA3_EARLY.access$1102(CockpitDXXI_SARJA3_EARLY.this, -CockpitDXXI_SARJA3_EARLY.this.flapsPumpIncrement);
      }
      else if (CockpitDXXI_SARJA3_EARLY.this.flaps > CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap())
      {
        if (CockpitDXXI_SARJA3_EARLY.this.flapsDirection == 0)
          CockpitDXXI_SARJA3_EARLY.this.sfxStart(16);
        CockpitDXXI_SARJA3_EARLY.access$802(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
        CockpitDXXI_SARJA3_EARLY.access$1002(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.flapsPump + CockpitDXXI_SARJA3_EARLY.this.flapsPumpIncrement);
        CockpitDXXI_SARJA3_EARLY.access$902(CockpitDXXI_SARJA3_EARLY.this, -1);
        if ((CockpitDXXI_SARJA3_EARLY.this.flapsPump < 0.0F) || (CockpitDXXI_SARJA3_EARLY.this.flapsPump > 1.0F)) {
          CockpitDXXI_SARJA3_EARLY.access$1102(CockpitDXXI_SARJA3_EARLY.this, -CockpitDXXI_SARJA3_EARLY.this.flapsPumpIncrement);
        }
      }
      if ((!CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked) && (CockpitDXXI_SARJA3_EARLY.this.tailWheelLock < 1.0F))
      {
        CockpitDXXI_SARJA3_EARLY.access$1202(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.tailWheelLock + 0.05F);
      }
      else if ((CockpitDXXI_SARJA3_EARLY.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked) && (CockpitDXXI_SARJA3_EARLY.this.tailWheelLock > 0.0F))
      {
        CockpitDXXI_SARJA3_EARLY.access$1202(CockpitDXXI_SARJA3_EARLY.this, CockpitDXXI_SARJA3_EARLY.this.tailWheelLock - 0.05F);
      }
      CockpitDXXI_SARJA3_EARLY.this.updateCompass();
      return true;
    }
  }
}