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

public class CockpitI_16TYPE6 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean hasTubeSight = true;
  private boolean bEntered = false;
  private float saveFov;
  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 44.0F, 74.5F, 106.0F, 136.3F, 169.5F, 207.5F, 245.0F, 287.5F, 330.0F };

  private static final float[] fuelQuantityScale = { 0.0F, 38.5F, 74.5F, 98.5F, 122.0F, 143.0F, 163.0F, 182.5F, 203.0F, 221.0F, 239.5F, 256.0F, 274.0F, 295.0F, 295.0F, 295.0F };

  private static final float[] engineRPMScale = { 18.0F, 38.0F, 61.0F, 81.0F, 102.0F, 120.0F, 137.0F, 152.0F, 167.0F, 183.0F, 198.0F, 213.0F, 227.0F, 241.0F, 254.0F, 267.0F, 280.0F, 292.0F, 306.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitI_16TYPE6() {
    super("3DO/Cockpit/I-16/hier_type6.him", "i16");

    if ((aircraft() instanceof I_16TYPE6))
    {
      ((I_16TYPE6)aircraft()).registerPit(this);
    }
    else if ((aircraft() instanceof I_16TYPE6_SKIS))
    {
      ((I_16TYPE6_SKIS)aircraft()).registerPit(this);
    }
    this.cockpitNightMats = new String[] { "prib_one", "prib_one_dd", "prib_two", "prib_two_dd", "prib_three", "prib_three_dd", "prib_four", "prib_four_dd", "shkala", "oxigen" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_Base", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalL", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalR", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Fire1", 0.0F, -20.0F * (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] != 0 ? 1 : 0), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Thtl", 30.0F - 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Thtl_Rod", -30.0F + 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

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
      if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM() > 400.0F)
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 400.0F, 2200.0F, 0.0F, 18.0F), engineRPMScale), 0.0F);
      }
      else
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPS1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 400.0F, 0.0F, 18.0F), 0.0F);
      }

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTCil1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -95.0F), 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPressOil1a", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 270.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Green1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Green2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    float f;
    if (this.hasTubeSight)
    {
      f = cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -90.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap", 0.0F, f, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap_big", 0.0F, f, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap_inside", 0.0F, f, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_sight_cap_inside", 0.0F, f, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("SightCapSwitch", f * 0.75F, 0.0F, 0.0F);

      if (f <= -88.0F)
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_inside", false);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_inside", true);
    }
    else
    {
      f = cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 90.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("DarkGlass", 0.0F, f, 0.0F);
    }
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
    {
      if ((!this.hasTubeSight) && (World.Rnd().nextInt(0, 99) < 15))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel_D1", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", false);
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
    {
      if ((!this.hasTubeSight) && (World.Rnd().nextInt(0, 99) < 10))
      {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel_D1", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", false);
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
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
  }

  public void toggleLight() {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void setTubeSight(boolean paramBoolean)
  {
    this.hasTubeSight = paramBoolean;

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tubeSight", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tubeSightLens", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tube_inside", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TubeSightEyePiece", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("tube_mask", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_sight_cap_inside", paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("SightCapSwitch", paramBoolean);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", !paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_MASK", !paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pricel", !paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("DarkGlass", !paramBoolean);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("target", !paramBoolean);
  }

  public void destroy() {
    leave(false);
    super.destroy();
  }

  public void toggleDim()
  {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      if ((!this.hasTubeSight) && (this.bEntered))
        enter();
      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      leave(false);
      super.doFocusLeave();
    }
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

  private void leave(boolean paramBoolean)
  {
    if (this.bEntered)
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      if (paramBoolean)
        this.bEntered = false;
      if (this.hasTubeSight)
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

  private void enter()
  {
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(true);
    this.bEntered = true;
    if (!this.hasTubeSight)
    {
      localHookPilot.setAim(new Point3d(-1.1D, -0.001D, 0.873D));
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

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float gCrankAngle;
    float dimPos;
    private final CockpitI_16TYPE6 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.gCrankAngle = 0.0F;
      this.dimPos = 1.0F;
    }

    Variables(CockpitI_16TYPE6.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    boolean sfxPlaying = false;

    Interpolater() {  }

    public boolean tick() { if (CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null) {
        if (CockpitI_16TYPE6.this.bNeedSetUp) {
          CockpitI_16TYPE6.this.reflectPlaneMats();
          CockpitI_16TYPE6.access$102(CockpitI_16TYPE6.this, false);
        }
        CockpitI_16TYPE6.access$202(CockpitI_16TYPE6.this, CockpitI_16TYPE6.this.setOld);
        CockpitI_16TYPE6.access$302(CockpitI_16TYPE6.this, CockpitI_16TYPE6.this.setNew);
        CockpitI_16TYPE6.access$402(CockpitI_16TYPE6.this, CockpitI_16TYPE6.this.setTmp);
        CockpitI_16TYPE6.this.setNew.throttle = ((10.0F * CockpitI_16TYPE6.this.setOld.throttle + CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl) / 11.0F);

        CockpitI_16TYPE6.this.setNew.prop = ((10.0F * CockpitI_16TYPE6.this.setOld.prop + CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlProp()) / 11.0F);

        CockpitI_16TYPE6.this.setNew.altimeter = CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (Math.abs(CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0F) {
          CockpitI_16TYPE6.this.setNew.azimuth = ((35.0F * CockpitI_16TYPE6.this.setOld.azimuth + -CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw()) / 36.0F);
        }
        if ((CockpitI_16TYPE6.this.setOld.azimuth > 270.0F) && (CockpitI_16TYPE6.this.setNew.azimuth < 90.0F))
          CockpitI_16TYPE6.this.setOld.azimuth -= 360.0F;
        if ((CockpitI_16TYPE6.this.setOld.azimuth < 90.0F) && (CockpitI_16TYPE6.this.setNew.azimuth > 270.0F))
          CockpitI_16TYPE6.this.setOld.azimuth += 360.0F;
        CockpitI_16TYPE6.this.setNew.waypointAzimuth = ((10.0F * CockpitI_16TYPE6.this.setOld.waypointAzimuth + (CockpitI_16TYPE6.this.waypointAzimuth() - CockpitI_16TYPE6.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F)) / 11.0F);

        CockpitI_16TYPE6.this.setNew.vspeed = ((199.0F * CockpitI_16TYPE6.this.setOld.vspeed + CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);

        boolean bool = false;
        if (CockpitI_16TYPE6.this.setNew.gCrankAngle < CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() - 0.005F) {
          if (Math.abs(CockpitI_16TYPE6.this.setNew.gCrankAngle - CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear()) < 0.33F)
          {
            CockpitI_16TYPE6.this.setNew.gCrankAngle += 0.0025F;
            bool = true;
          } else {
            CockpitI_16TYPE6.this.setNew.gCrankAngle = CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
            CockpitI_16TYPE6.this.setOld.gCrankAngle = CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
          }
        }
        if (CockpitI_16TYPE6.this.setNew.gCrankAngle > CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() + 0.005F) {
          if (Math.abs(CockpitI_16TYPE6.this.setNew.gCrankAngle - CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear()) < 0.33F)
          {
            CockpitI_16TYPE6.this.setNew.gCrankAngle -= 0.0025F;
            bool = true;
          } else {
            CockpitI_16TYPE6.this.setNew.gCrankAngle = CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
            CockpitI_16TYPE6.this.setOld.gCrankAngle = CockpitI_16TYPE6.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear();
          }
        }

        if (CockpitI_16TYPE6.this.cockpitDimControl)
        {
          if (CockpitI_16TYPE6.this.setNew.dimPos < 1.0F)
            CockpitI_16TYPE6.this.setNew.dimPos = (CockpitI_16TYPE6.this.setOld.dimPos + 0.03F);
        }
        else if (CockpitI_16TYPE6.this.setNew.dimPos > 0.0F) {
          CockpitI_16TYPE6.this.setNew.dimPos = (CockpitI_16TYPE6.this.setOld.dimPos - 0.03F);
        }
        if (bool != this.sfxPlaying) {
          if (bool)
            CockpitI_16TYPE6.this.sfxStart(16);
          else
            CockpitI_16TYPE6.this.sfxStop(16);
          this.sfxPlaying = bool;
        }
      }
      return true;
    }
  }
}