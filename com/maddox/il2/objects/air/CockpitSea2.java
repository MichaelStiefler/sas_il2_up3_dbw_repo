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
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitSea2 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictBrake;
  private float pictFlap;
  private float pictGear;
  private float pictManf;
  private boolean bNeedSetUp;
  private static final float[] speedometerScale = { 0.0F, 7.5F, 17.5F, 37.0F, 63.0F, 88.5F, 114.5F, 143.0F, 171.5F, 202.5F, 228.5F, 255.5F, 282.0F, 309.0F, 336.5F, 366.5F, 394.0F, 421.0F, 448.5F, 474.5F, 500.5F, 530.0F, 557.5F, 584.0F, 609.0F, 629.0F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F, 125.0F, 161.0F, 202.5F, 253.0F, 315.5F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 22.0F, 58.0F, 103.5F, 152.5F, 193.5F, 245.0F, 281.5F, 311.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };
  private Point3d tmpP;
  private Vector3d tmpV;

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

  public CockpitSea2()
  {
    super("3DO/Cockpit/SeafireMkIII/Seafire2.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictBrake = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictManf = 1.0F;
    this.bNeedSetUp = true;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.cockpitNightMats = new String[] { "COMPASS", "BORT2", "prib_five", "prib_five_damage", "prib_one", "prib_one_damage", "prib_three", "prib_three_damage", "prib_two", "prib_two_damage", "text13", "text15" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.2F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    Object localObject1;
    Object localObject2;
    if ((aircraft() instanceof SEAFIRE1))
    {
      localObject1 = (SEAFIRE1)aircraft();
      if (SEAFIRE1.bChangedPit)
      {
        reflectPlaneToModel();
        localObject2 = (SEAFIRE1)aircraft();
        SEAFIRE1.bChangedPit = false;
      }
    }
    else if ((aircraft() instanceof SEAFIRE2C))
    {
      localObject1 = (SEAFIRE2C)aircraft();
      if (SEAFIRE2C.bChangedPit)
      {
        reflectPlaneToModel();
        localObject2 = (SEAFIRE2C)aircraft();
        SEAFIRE2C.bChangedPit = false;
      }
    }
    else if ((aircraft() instanceof SEAFIRE2C4))
    {
      localObject1 = (SEAFIRE2C4)aircraft();
      if (SEAFIRE2C4.bChangedPit)
      {
        reflectPlaneToModel();
        localObject2 = (SEAFIRE2C4)aircraft();
        SEAFIRE2C4.bChangedPit = false;
      }
    }
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl, 0.01F, 0.99F, 0.0F, 0.03F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Arrester1", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampArrest", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor() > 0.9F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Blister_D0", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpL", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownL", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampFuel", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 0.25F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Columnbase", 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", -30.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang01a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang01b", -9.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang01c", -12.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang02a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang02b", -7.5F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang02c", -15.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang03a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang03b", -8.5F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Shlang03c", -18.5F * this.pictAiler, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Stick_Shtok01", 0.0F, 0.0F, 8.0F * this.pictElev);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ColumnSwitch", -18.0F * (this.pictBrake = 0.89F * this.pictBrake + 0.11F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle1", -64.545403F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_BasePedal", 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (0.0578F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.0578F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Gear1", -160.0F + 160.0F * (this.pictGear = 0.89F * this.pictGear + 0.11F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Flaps1", 160.0F * (this.pictFlap = 0.89F * this.pictFlap + 0.11F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1", 1000.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim2", 1000.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimRudderControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop1", -90.0F * this.setNew.prop, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mixture1", -60.0F * this.setNew.mix, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("COMPASS_M", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("SHKALA_DIRECTOR", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -108.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -cvt(this.pictManf = 0.91F * this.pictManf + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.5173668F, 2.72369F, -70.0F, 250.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 378.54001F, 0.0F, 68.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 1000.0F, 5000.0F, 2.0F, 10.0F), rpmScale));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 50.0F, 100.0F, 0.0F, 271.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STR_OIL_LB", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -37.0F), 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELK_V_SHORT", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeed()), 0.0F, 257.2222F, 0.0F, 25.0F), speedometerScale));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_GOR", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.032F, -0.032F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_HOUR", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_MINUTE", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_SECUND", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STR_CLIMB", 0.0F, 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.trimElevator, -0.5F, 0.5F, -35.23F, 35.23F));
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState & 0x80) != 0)
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay6"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay6", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay7"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay7", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("OverlayD1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("OverlayD2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      setNightMats(false);
      setNightMats(true);
    }
    else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitSea2 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitSea2.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitSea2.access$002(CockpitSea2.this, CockpitSea2.this.setOld);
        CockpitSea2.access$102(CockpitSea2.this, CockpitSea2.this.setNew);
        CockpitSea2.access$202(CockpitSea2.this, CockpitSea2.this.setTmp);
        CockpitSea2.this.setNew.throttle = (0.92F * CockpitSea2.this.setOld.throttle + 0.08F * CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl);
        CockpitSea2.this.setNew.prop = (0.92F * CockpitSea2.this.setOld.prop + 0.08F * CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp());
        CockpitSea2.this.setNew.mix = (0.92F * CockpitSea2.this.setOld.mix + 0.08F * CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix());
        CockpitSea2.this.setNew.altimeter = CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (Math.abs(CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0F)
          CockpitSea2.this.setNew.azimuth = (0.97F * CockpitSea2.this.setOld.azimuth + 0.03F * -CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw());
        if ((CockpitSea2.this.setOld.azimuth > 270.0F) && (CockpitSea2.this.setNew.azimuth < 90.0F))
          CockpitSea2.this.setOld.azimuth -= 360.0F;
        if ((CockpitSea2.this.setOld.azimuth < 90.0F) && (CockpitSea2.this.setNew.azimuth > 270.0F))
          CockpitSea2.this.setOld.azimuth += 360.0F;
        CockpitSea2.this.setNew.waypointAzimuth = (0.91F * CockpitSea2.this.setOld.waypointAzimuth + 0.09F * (CockpitSea2.this.waypointAzimuth() - CockpitSea2.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitSea2.this.setNew.vspeed = (0.99F * CockpitSea2.this.setOld.vspeed + 0.01F * CockpitSea2.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed());
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}