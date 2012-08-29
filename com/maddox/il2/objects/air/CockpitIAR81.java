package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitIAR81 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictRadiator;
  private float pictManifold;
  private static final float[] oilTScale = { 0.0F, 32.0F, 50.049999F, 78.0F, 123.5F, 180.0F };

  private static final float[] fuelScale = { 0.0F, 10.5F, 30.0F, 71.0F, 114.0F, 148.5F, 175.5F, 202.5F, 232.0F, 258.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitIAR81()
  {
    super("3DO/Cockpit/IAR-81A/hier.him", "bf109");

    this.setNew.dimPosition = 1.0F;

    this.cockpitNightMats = new String[] { "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges6", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLGreen1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRGreen1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("sun_off", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 165.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 15.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);

    resetYPRmodifier();
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] != 0) {
      Cockpit.xyz[1] = -0.01F;
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_Base", 0.0F, -15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalR", 0.0F, 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Throttle", 0.0F, 0.0F, 22.200001F - 80.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Throttle_rod", 0.0F, 0.0F, -22.200001F + 80.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Magneto", 0.0F, 0.0F, 16.666668F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos());

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Flaps", 0.0F, 0.0F, -50.0F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl));
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Gears.isHydroOperable())
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Gears", 0.0F, 0.0F, -50.0F * (this.pictGear = 0.85F * this.pictGear + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl));
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("H-manual", 0.0F, 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl < 0.1F ? 0.0F : -22.5F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Radiator", 0.0F, 0.0F, -50.0F * (this.pictRadiator = 0.85F * this.pictRadiator + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator()));

    float f1 = Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed1a", 0.0F, f1 > 100.0F ? cvt(f1, 100.0F, 800.0F, 22.5F, 337.5F) : cvt(f1, 0.0F, 100.0F, 0.0F, 22.5F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude(), 0.0F, 16000.0F, 0.0F, 720.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zClock1c", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTOil1a", 0.0F, (f1 = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut) > 100.0F ? 180.0F + 3.725F * (f1 - 100.0F) : floatindex(cvt(f1, 0.0F, 100.0F, 0.0F, 5.0F), oilTScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zGasPrs1a", 0.0F, floatindex(cvt(1.388889F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 450.0F, 0.0F, 9.0F), fuelScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTure1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 500.0F, 3000.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -20.0F, 20.0F, -180.0F, 180.0F), 0.0F);
    float f2;
    if (aircraft().isFMTrackMirror()) {
      f2 = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f2 = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, 18.0F, -18.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f2);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurn1a", 0.0F, f2, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPitch1a", 0.0F, 270.0F - (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin()) * 60.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPitch1b", 0.0F, 105.0F - (float)Math.toDegrees(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhi() - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropPhiMin()) * 5.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPress1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.67F, 0.0F, -180.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPress1b", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 20.0F, 0.0F, 124.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zPressure1a", 0.0F, cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.266644F, 1.866508F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Azimuth1", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(this.tmpV.y, this.tmpV.x));
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

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors1_d1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("pribors2_d1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitIAR81.access$102(CockpitIAR81.this, CockpitIAR81.this.setOld); CockpitIAR81.access$202(CockpitIAR81.this, CockpitIAR81.this.setNew); CockpitIAR81.access$302(CockpitIAR81.this, CockpitIAR81.this.setTmp);

      CockpitIAR81.this.setNew.throttle = ((10.0F * CockpitIAR81.this.setOld.throttle + CockpitIAR81.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl) / 11.0F);
      CockpitIAR81.this.setNew.vspeed = ((199.0F * CockpitIAR81.this.setOld.vspeed + CockpitIAR81.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
      CockpitIAR81.this.setNew.azimuth = CockpitIAR81.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
      if ((CockpitIAR81.this.setOld.azimuth > 270.0F) && (CockpitIAR81.this.setNew.azimuth < 90.0F)) CockpitIAR81.this.setOld.azimuth -= 360.0F;
      if ((CockpitIAR81.this.setOld.azimuth < 90.0F) && (CockpitIAR81.this.setNew.azimuth > 270.0F)) CockpitIAR81.this.setOld.azimuth += 360.0F;
      CockpitIAR81.this.setNew.waypointAzimuth = ((10.0F * CockpitIAR81.this.setOld.waypointAzimuth + (CockpitIAR81.this.waypointAzimuth() - CockpitIAR81.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
      if (CockpitIAR81.this.cockpitDimControl) {
        if (CockpitIAR81.this.setNew.dimPosition > 0.0F) CockpitIAR81.this.setNew.dimPosition = (CockpitIAR81.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitIAR81.this.setNew.dimPosition < 1.0F) CockpitIAR81.this.setNew.dimPosition = (CockpitIAR81.this.setOld.dimPosition + 0.05F);

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    float dimPosition;
    private final CockpitIAR81 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitIAR81.1 arg2) { this();
    }
  }
}