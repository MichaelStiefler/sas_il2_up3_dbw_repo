package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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

public class CockpitR_10 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private boolean bNeedSetUp;
  private float pictAiler;
  private float pictElev;
  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 44.0F, 74.5F, 106.0F, 136.3F, 169.5F, 207.5F, 245.0F, 287.5F, 330.0F };

  private static final float[] fuelQuantityScale = { 0.0F, 38.5F, 74.5F, 98.5F, 122.0F, 143.0F, 163.0F, 182.5F, 203.0F, 221.0F, 239.5F, 256.0F, 274.0F, 295.0F, 295.0F, 295.0F };

  private static final float[] engineRPMScale = { 0.0F, 16.0F, 18.0F, 59.5F, 100.5F, 135.5F, 166.5F, 198.5F, 227.0F, 255.0F, 281.5F, 307.0F, 317.0F, 327.0F };
  private Point3d tmpP;
  private Vector3d tmpV;
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

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
    {
      return 0.0F;
    }

    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitR_10()
  {
    super("3DO/Cockpit/R10/CockpitR_10.him", "i16");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.bNeedSetUp = true;
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.bEntered = false;
    this.cockpitNightMats = new String[] { "prib_one", "prib_one_dd", "prib_two", "prib_two_dd", "prib_three", "prib_three_dd", "prib_four", "prib_four_dd", "shkala", "oxigen" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_Base", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalL", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalR", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire1", 0.0F, -20.0F * (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] != 0 ? 1.0F : 0.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire2", 0.0F, -20.0F * (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] != 0 ? 1.0F : 0.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Thtl", 30.0F - 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Prop", interp(this.setNew.prop, this.setOld.prop, paramFloat) * -57.0F + 30.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Thtl_Rod", -30.0F + 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Prop_Rod", interp(this.setNew.prop, this.setOld.prop, paramFloat) * 57.0F - 30.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Gear_Crank", 15840.0F * interp(this.setNew.gCrankAngle, this.setOld.gCrankAngle, paramFloat) % 360.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAzimuth1b", 0.0F, -90.0F - interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zGas1a", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 190.0F, 0.0F, 14.0F), fuelQuantityScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 550.0F, 0.0F, 11.0F), speedometerScale), 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) == 0))
    {
      this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilIn, 0.0F, 125.0F, 0.0F, 275.0F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 275.0F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPressAir1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 10.0F, 0.0F, 275.0F), 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) == 0) && ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) == 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 2400.0F, 0.0F, 13.0F), engineRPMScale), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTCil1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -88.0F), 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPressOil1a", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 270.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Green1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Green2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1_dd", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zSpeed1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1b", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zPressOil1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zVariometer1a", false);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2_dd", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAzimuth1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAzimuth1b", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zManifold1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zGas1a", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
  }

  protected void reflectPlaneMats()
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ritedoor", false);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float gCrankAngle;
    private final CockpitR_10 this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.gCrankAngle = 0.0F;
    }

    Variables(CockpitR_10.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    boolean sfxPlaying;

    public boolean tick()
    {
      if (CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        if (CockpitR_10.this.bNeedSetUp)
        {
          CockpitR_10.this.reflectPlaneMats();
          CockpitR_10.access$002(CockpitR_10.this, false);
        }
        CockpitR_10.access$102(CockpitR_10.this, CockpitR_10.this.setOld);
        CockpitR_10.access$202(CockpitR_10.this, CockpitR_10.this.setNew);
        CockpitR_10.access$302(CockpitR_10.this, CockpitR_10.this.setTmp);
        CockpitR_10.this.setNew.throttle = ((10.0F * CockpitR_10.this.setOld.throttle + CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl) / 11.0F);
        CockpitR_10.this.setNew.prop = ((10.0F * CockpitR_10.this.setOld.prop + CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlProp()) / 11.0F);
        CockpitR_10.this.setNew.altimeter = CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (Math.abs(CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0F)
          CockpitR_10.this.setNew.azimuth = ((35.0F * CockpitR_10.this.setOld.azimuth + -CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw()) / 36.0F);
        if ((CockpitR_10.this.setOld.azimuth > 270.0F) && (CockpitR_10.this.setNew.azimuth < 90.0F))
          CockpitR_10.this.setOld.azimuth -= 360.0F;
        if ((CockpitR_10.this.setOld.azimuth < 90.0F) && (CockpitR_10.this.setNew.azimuth > 270.0F))
          CockpitR_10.this.setOld.azimuth += 360.0F;
        CockpitR_10.this.setNew.waypointAzimuth = ((10.0F * CockpitR_10.this.setOld.waypointAzimuth + (CockpitR_10.this.waypointAzimuth() - CockpitR_10.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F)) / 11.0F);
        CockpitR_10.this.setNew.vspeed = ((199.0F * CockpitR_10.this.setOld.vspeed + CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
        boolean bool = false;
        if (CockpitR_10.this.setNew.gCrankAngle < CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() - 0.005F)
          if (Math.abs(CockpitR_10.this.setNew.gCrankAngle - CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear()) < 0.33F)
          {
            CockpitR_10.this.setNew.gCrankAngle += 0.0025F;
            bool = true;
          }
          else {
            CockpitR_10.this.setNew.gCrankAngle = CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
            CockpitR_10.this.setOld.gCrankAngle = CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
          }
        if (CockpitR_10.this.setNew.gCrankAngle > CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() + 0.005F)
          if (Math.abs(CockpitR_10.this.setNew.gCrankAngle - CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear()) < 0.33F)
          {
            CockpitR_10.this.setNew.gCrankAngle -= 0.0025F;
            bool = true;
          }
          else {
            CockpitR_10.this.setNew.gCrankAngle = CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
            CockpitR_10.this.setOld.gCrankAngle = CockpitR_10.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
          }
        if (bool != this.sfxPlaying)
        {
          if (bool)
            CockpitR_10.this.sfxStart(16);
          else
            CockpitR_10.this.sfxStop(16);
          this.sfxPlaying = bool;
        }
      }
      return true;
    }

    Interpolater()
    {
      this.sfxPlaying = false;
    }
  }
}