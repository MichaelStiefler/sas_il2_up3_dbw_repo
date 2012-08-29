package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
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
import com.maddox.util.HashMapExt;

public class CockpitI_15bis extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private Vector3f w;
  private boolean bNeedSetUp;
  private float pictAiler;
  private float pictElev;
  private float pictSupc;
  private LightPointActor[] lights = { null, null, null, null };

  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 45.0F, 75.5F, 107.0F, 137.5F, 170.0F, 206.5F, 243.75F, 286.5F, 329.5F, 374.5F };

  private static final float[] rpmScale = { 0.0F, 5.5F, 18.5F, 59.0F, 99.5F, 134.5F, 165.75F, 198.0F, 228.0F, 255.5F, 308.0F, 345.0F };

  private static final float[] manifoldScale = { 0.0F, 0.0F, 0.0F, 0.0F, 26.0F, 52.0F, 79.0F, 106.0F, 132.0F, 160.0F, 185.0F, 208.0F, 235.0F, 260.0F, 286.0F, 311.0F, 336.0F };
  private float saveFov;
  private boolean bEntered;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      leave();
      super.doFocusLeave();
    }
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
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Fonar", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("target", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Pricel_D0", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("truba", false);
  }

  private void leave()
  {
    if (this.bEntered)
    {
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
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Fonar", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("target", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Pricel_D0", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("truba", true);
    }
  }

  public void destroy()
  {
    leave();
    super.destroy();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
      if (paramBoolean)
        enter();
      else
        leave();
  }

  public CockpitI_15bis()
  {
    super("3DO/Cockpit/I-15bis/hier.him", "u2");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.bNeedSetUp = true;
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictSupc = 0.0F;
    this.bEntered = false;
    for (int i = 0; i < 4; i++)
    {
      HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "_light0" + (i + 1));
      Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
      this.lights[i] = new LightPointActor(new LightPoint(), localLoc.getPoint());
      this.lights[i].light.setColor(0.8980392F, 0.8117647F, 0.6235294F);
      this.lights[i].light.setEmit(0.0F, 0.0F);
      this.pos.base().draw.lightMap().put("_light0" + (i + 1), this.lights[i]);
    }

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("crank", 40.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("cr_tross", 0.0F, 0.0F, -40.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("handle", 65.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("hand_tross", 0.0F, 0.0F, -65.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("lever", -50.0F * this.pictSupc, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("lev_tross", 0.0F, 0.0F, 50.0F * this.pictSupc);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("magto", 44.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("prop", -2160.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 10.2F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_Base", 0.0F, -15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalR", 0.0F, 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_trossL", -15.65F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_trossR", -15.65F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 600.0F, 0.0F, 12.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage(), -40.0F, 40.0F, 40.0F, -40.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) == 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 2400.0F, 0.0F, 11.0F), rpmScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.setNew.turn, -0.2F, 0.2F, 26.0F, -26.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 26.0F, -26.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 8.0F, 0.0F, 180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTwater1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 300.0F, 0.0F, -60.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zManifold1a", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3.0F, 16.0F), manifoldScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire1", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[0] != 0 ? -15.0F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire2", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[2] != 0 ? -15.0F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire3", 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire4", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] != 0 ? -15.0F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire5", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[1] != 0 ? -15.0F : 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_MASK", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel_d1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1_dd", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zSpeed1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zTOilOut1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOilPrs1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zGasPrs1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zVariometer1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zManifold1a", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2_dd", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1b", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zTurn1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zSlide1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zTwater1a", false);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) == 0) || (
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)));
  }

  protected void reflectPlaneToModel() {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLMid_D1", localHierMesh.isChunkVisible("WingLMid_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLMid_D2", localHierMesh.isChunkVisible("WingLMid_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingLMid_D3", localHierMesh.isChunkVisible("WingLMid_D3"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRMid_D1", localHierMesh.isChunkVisible("WingRMid_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRMid_D2", localHierMesh.isChunkVisible("WingRMid_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WingRMid_D3", localHierMesh.isChunkVisible("WingRMid_D3"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("CF_D3", localHierMesh.isChunkVisible("CF_D3"));
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D0o", localMat);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    sfxClick(1);
    int i;
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      for (i = 0; i < 4; i++) {
        this.lights[i].light.setEmit(0.5F, 0.5F);
      }
    }
    else
      for (i = 0; i < 4; i++)
        this.lights[i].light.setEmit(0.0F, 0.0F);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitI_15bis.this.bNeedSetUp)
      {
        CockpitI_15bis.this.reflectPlaneMats();
        CockpitI_15bis.access$002(CockpitI_15bis.this, false);
      }
      CockpitI_15bis.access$102(CockpitI_15bis.this, CockpitI_15bis.this.setOld);
      CockpitI_15bis.access$202(CockpitI_15bis.this, CockpitI_15bis.this.setNew);
      CockpitI_15bis.access$302(CockpitI_15bis.this, CockpitI_15bis.this.setTmp);
      CockpitI_15bis.this.setNew.altimeter = CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
      if (Math.abs(CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0F)
        CockpitI_15bis.this.setNew.azimuth = ((21.0F * CockpitI_15bis.this.setOld.azimuth + CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.azimut()) / 22.0F);
      if ((CockpitI_15bis.this.setOld.azimuth > 270.0F) && (CockpitI_15bis.this.setNew.azimuth < 90.0F))
        CockpitI_15bis.this.setOld.azimuth -= 360.0F;
      if ((CockpitI_15bis.this.setOld.azimuth < 90.0F) && (CockpitI_15bis.this.setNew.azimuth > 270.0F))
        CockpitI_15bis.this.setOld.azimuth += 360.0F;
      CockpitI_15bis.this.setNew.throttle = ((10.0F * CockpitI_15bis.this.setOld.throttle + CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle()) / 11.0F);
      CockpitI_15bis.this.setNew.mix = ((10.0F * CockpitI_15bis.this.setOld.mix + CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix()) / 11.0F);
      CockpitI_15bis.this.setNew.prop = CockpitI_15bis.this.setOld.prop;
      if (CockpitI_15bis.this.setNew.prop < CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() - 0.01F)
        CockpitI_15bis.this.setNew.prop += 0.0025F;
      if (CockpitI_15bis.this.setNew.prop > CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() + 0.01F)
        CockpitI_15bis.this.setNew.prop -= 0.0025F;
      CockpitI_15bis.this.w.set(CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
      CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(CockpitI_15bis.this.w);
      CockpitI_15bis.this.setNew.turn = ((12.0F * CockpitI_15bis.this.setOld.turn + CockpitI_15bis.this.w.z) / 13.0F);
      CockpitI_15bis.this.setNew.vspeed = ((299.0F * CockpitI_15bis.this.setOld.vspeed + CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 300.0F);
      CockpitI_15bis.access$502(CockpitI_15bis.this, 0.8F * CockpitI_15bis.this.pictSupc + 0.2F * CockpitI_15bis.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlCompressor());
      return true;
    }

    Interpolater()
    {
    }
  }

  private class Variables
  {
    float altimeter;
    float azimuth;
    float throttle;
    float mix;
    float prop;
    float turn;
    float vspeed;
    private final CockpitI_15bis this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitI_15bis.1 arg2)
    {
      this();
    }
  }
}