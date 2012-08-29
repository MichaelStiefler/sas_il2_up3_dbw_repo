package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitMXY_7 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
    {
      return 0.0F;
    }

    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(-Cockpit.V.y, Cockpit.V.x));
  }

  public CockpitMXY_7()
  {
    super("3DO/Cockpit/BI-1/CockpitMXY7.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.cockpitNightMats = new String[] { "ONE", "TWO", "THREE" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Ped_Base", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalL", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PedalR", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder() * 15.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.ypr[1] = (-80.080002F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    Cockpit.xyz[1] = (Cockpit.ypr[1] >= -33.0F ? 0.0F : -0.0065F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Throttle", Cockpit.xyz, Cockpit.ypr);
    if (Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap()) > 0.02F)
    {
      if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.0F)
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Lever_Flaps", 15.0F, 0.0F, 0.0F);
      else
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Lever_Flaps", -20.0F, 0.0F, 0.0F);
    }
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Lever_Flaps", 0.0F, 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed1a", 0.0F, cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 1200.0F, 0.0F, 360.0F), 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.47124F, 0.47124F, 40.0F, -40.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSlide1a", cvt(getBall(8.0D), -8.0F, 8.0F, 22.5F, -22.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zGas1a", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.M.fuel / 0.72F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("ONE", "ONE_D1");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("ONE_night", "ONE_D1_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Dash", "Dash_D1");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1a", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAlt1b", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zSpeed1a", false);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) || ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("THREE", "THREE_D1");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("THREE_night", "THREE_D1_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zSlide1a", false);
    }
    retoggleLight();
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      setNightMats(true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red11", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red14", true);
    }
    else {
      setNightMats(false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red11", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Red14", false);
    }
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

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitMXY_7.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitMXY_7.access$002(CockpitMXY_7.this, CockpitMXY_7.this.setOld);
        CockpitMXY_7.access$102(CockpitMXY_7.this, CockpitMXY_7.this.setNew);
        CockpitMXY_7.access$202(CockpitMXY_7.this, CockpitMXY_7.this.setTmp);
        CockpitMXY_7.this.setNew.throttle = ((10.0F * CockpitMXY_7.this.setOld.throttle + CockpitMXY_7.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.CT.PowerControl) / 11.0F);
        CockpitMXY_7.this.setNew.altimeter = CockpitMXY_7.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
      }
      return true;
    }

    Interpolater()
    {
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    private final CockpitMXY_7 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitMXY_7.1 arg2)
    {
      this();
    }
  }
}