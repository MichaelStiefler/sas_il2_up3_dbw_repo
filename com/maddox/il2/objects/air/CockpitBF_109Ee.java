package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
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
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitBF_109Ee extends CockpitPilot
{
  private float tmp;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler;
  private float pictElev;
  private float pictManifold;
  private boolean bNeedSetUp;
  private int type;
  private static final int TYPE_E1 = 0;
  private static final int TYPE_E3 = 1;
  private static final int TYPE_B2 = 2;
  private static final float[] speedometerScale = { 0.0F, -12.33333F, 18.5F, 37.0F, 62.5F, 90.0F, 110.5F, 134.0F, 158.5F, 186.0F, 212.5F, 238.5F, 265.0F, 289.5F, 315.0F, 339.5F, 346.0F, 346.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 54.0F, 111.0F, 171.5F, 229.5F, 282.5F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 9.0F, 21.0F, 29.5F, 37.0F, 48.0F, 61.5F, 75.5F, 92.0F, 92.0F };
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
    return (float)(57.295779513082323D * Math.atan2(this.tmpV.y, this.tmpV.x));
  }

  public CockpitBF_109Ee()
  {
    super("3DO/Cockpit/Bf-109E-7/hier.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictManifold = 0.0F;
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.setNew.dimPosition = 1.0F;
    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(227.0F, 65.0F, 33.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LAMPHOOK2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(227.0F, 65.0F, 33.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK2", this.light2);
    this.cockpitNightMats = new String[] { "ZClocks1", "ZClocks1DMG", "ZClocks2", "ZClocks3", "FW190A4Compass", "oxigen" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    loadBuzzerFX();
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ReviTinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -30.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 40.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ATA1", 15.5F + cvt(this.pictManifold = 0.75F * this.pictManifold + 0.25F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM1", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelQuantity1", -44.5F + floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 8.0F), fuelScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Iengtemprad1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 160.0F, 0.0F, 58.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_EngTemp1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 58.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPress1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    float f;
    if (aircraft().isFMTrackMirror())
    {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    }
    else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -3.0F, 3.0F, 30.0F, -30.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", f, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6.0D), -6.0F, 6.0F, -7.0F, 7.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Azimuth1", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PropPitch1", 270.0F - (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PropPitch2", 105.0F - (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 36.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLGreen1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRGreen1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 15.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PedalStrut", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PedalStrut2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle", interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 57.727268F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle_D1", interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 27.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle_tube", -interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 57.727268F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mix", interp(this.setNew.mix, this.setOld.mix, paramFloat) * 70.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mix_tros", -interp(this.setNew.mix, this.setOld.mix, paramFloat) * 70.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_MagnetoSwitch", -45.0F + 28.333F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Top", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TopE4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1E4_D1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2E4_D1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", false);
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
    {
      this.light1.light.setEmit(0.005F, 0.5F);
      this.light2.light.setEmit(0.005F, 0.5F);
      setNightMats(true);
    }
    else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Armor_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      if ((this.type == 0) || (this.type == 1))
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1E4_D1", true);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage4", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Throttle", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Throttle_tube", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Throttle_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Throttle_TD1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      if ((this.type == 0) || (this.type == 1))
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplatE4_D1", true);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage3", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_HullDamage2", true);
      if ((this.type == 0) || (this.type == 1))
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2E4_D1", true);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    }
  }

  protected void reflectPlaneMats()
  {
    if (Actor.isValid(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor))
    {
      if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BF_109E1)) {
        this.type = 0;
      }
      else if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BF_109E3)) {
        this.type = 1;
      }
      else if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BF_109B2))
        this.type = 2;
      switch (this.type)
      {
      case 0:
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Body", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BodyE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Top", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TopE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4_D0", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4B_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE7_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7z", false);
        break;
      case 1:
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Body", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BodyE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Top", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TopE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4_D0", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4B_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE7_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7z", false);
        break;
      case 2:
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Body", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BodyE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Top", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TopE4", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4_D0", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE4B_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PanelE7_D0", false);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7", true);
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("oxigen-7z", false);
      }
    }
  }

  static
  {
    Property.set(CockpitBF_109Ee.class, "normZN", 0.48F);
    Property.set(CockpitBF_109Ee.class, "gsZN", 0.43F);
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    float azimuth;
    float waypointAzimuth;
    float mix;
    private final CockpitBF_109Ee this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitBF_109Ee.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitBF_109Ee.this.bNeedSetUp)
      {
        CockpitBF_109Ee.this.reflectPlaneMats();
        CockpitBF_109Ee.access$002(CockpitBF_109Ee.this, false);
      }
      CockpitBF_109Ee.access$102(CockpitBF_109Ee.this, CockpitBF_109Ee.this.setOld);
      CockpitBF_109Ee.access$202(CockpitBF_109Ee.this, CockpitBF_109Ee.this.setNew);
      CockpitBF_109Ee.access$302(CockpitBF_109Ee.this, CockpitBF_109Ee.this.setTmp);
      CockpitBF_109Ee.this.setNew.altimeter = CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
      if (CockpitBF_109Ee.this.cockpitDimControl)
      {
        if (CockpitBF_109Ee.this.setNew.dimPosition > 0.0F)
          CockpitBF_109Ee.this.setNew.dimPosition = (CockpitBF_109Ee.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitBF_109Ee.this.setNew.dimPosition < 1.0F)
        CockpitBF_109Ee.this.setNew.dimPosition = (CockpitBF_109Ee.this.setOld.dimPosition + 0.05F);
      CockpitBF_109Ee.this.setNew.throttle = ((10.0F * CockpitBF_109Ee.this.setOld.throttle + CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl) / 11.0F);
      CockpitBF_109Ee.this.setNew.mix = ((8.0F * CockpitBF_109Ee.this.setOld.mix + CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlMix()) / 9.0F);
      CockpitBF_109Ee.this.setNew.azimuth = CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
      if ((CockpitBF_109Ee.this.setOld.azimuth > 270.0F) && (CockpitBF_109Ee.this.setNew.azimuth < 90.0F))
        CockpitBF_109Ee.this.setOld.azimuth -= 360.0F;
      if ((CockpitBF_109Ee.this.setOld.azimuth < 90.0F) && (CockpitBF_109Ee.this.setNew.azimuth > 270.0F))
        CockpitBF_109Ee.this.setOld.azimuth += 360.0F;
      CockpitBF_109Ee.this.setNew.waypointAzimuth = ((10.0F * CockpitBF_109Ee.this.setOld.waypointAzimuth + (CockpitBF_109Ee.this.waypointAzimuth() - CockpitBF_109Ee.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
      CockpitBF_109Ee.this.buzzerFX((CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.999999F) && (CockpitBF_109Ee.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.0F));
      return true;
    }

    Interpolater()
    {
    }
  }
}