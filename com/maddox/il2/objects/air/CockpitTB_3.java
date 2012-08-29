package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitTB_3 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 52.0F, 91.0F, 139.5F, 190.0F, 249.5F, 308.0F, 360.0F };

  private static final float[] engineRPMScale = { 0.0F, 0.0F, 0.0F, 40.0F, 80.5F, 115.3F, 145.5F, 177.60001F, 206.5F, 234.5F, 261.0F, 287.0F, 320.0F, 358.5F };

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(-Cockpit.V.y, Cockpit.V.x));
  }

  public CockpitTB_3()
  {
    super("3DO/Cockpit/TB-3/hier.him", "i16");

    this.light1 = new LightPointActor(new LightPoint(), new Point3d(3.67495D, 0.72745D, 1.04095D));
    this.light2 = new LightPointActor(new LightPoint(), new Point3d(3.67495D, -0.77925D, 1.04095D));
    this.light1.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
    this.light2.light.setColor(0.9607843F, 0.8666667F, 0.7411765F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "Bombgauges", "Gauge02", "Gauge03", "Instr01", "Instr01_dd", "Instr02", "Instr02_dd", "oxigen" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AroneL", 0.0F, -115.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AroneR", 0.0F, -115.0F * this.pictAiler, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", 0.0F, -25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", 0.0F, 25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPedalStep", 0.0F, -25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LPedalStep", 0.0F, 25.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    for (int i = 0; i < 4; i++) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle" + (i + 1), 0.0F, -90.0F * interp(this.setNew.throttle[i], this.setOld.throttle[i], paramFloat), 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtlev" + (i + 1), 0.0F, -90.0F * interp(this.setNew.throttle[i], this.setOld.throttle[i], paramFloat), 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", 0.0F, interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter3", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter4", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 50.0F, 400.0F, 1.0F, 8.0F), speedometerScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer2", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 50.0F, 400.0F, 1.0F, 8.0F), speedometerScale), 0.0F);

    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.jdField_z_of_type_Float, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(this.w.jdField_z_of_type_Float, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Horizon1", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Horizon2", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -35.0F, 35.0F, 0.028F, -0.028F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Tangage1", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Tangage2", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Variometr", 0.0F, cvt(this.setNew.vspeed, -40.0F, 40.0F, -180.0F, 180.0F), 0.0F);

    for (i = 0; i < 4; i++) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM" + (i + 1), 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[i].getRPM(), 400.0F, 2400.0F, 2.0F, 13.0F), engineRPMScale), 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPK1", 0.0F, cvt(interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), -25.0F, 25.0F, 30.0F, -30.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPK2", 0.0F, cvt(interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), -25.0F, 25.0F, 30.0F, -30.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage4", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Instr01", "Instr01_dd");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Instr01_night", "Instr01_dd_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Instr02", "Instr02_dd");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Instr02_night", "Instr02_dd_night");
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TurnBank1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TurnBank2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Variometr", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter4", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
    }
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((ActorHMesh)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().chunkVisible("Windscreen_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    ((ActorHMesh)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().chunkVisible("Windscreen_D0", true);
    super.doFocusLeave();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean) {
      this.light1.light.setEmit(0.98F, 0.45F);
      this.light2.light.setEmit(0.98F, 0.45F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null) {
        if (CockpitTB_3.this.bNeedSetUp) {
          CockpitTB_3.this.reflectPlaneMats();
          CockpitTB_3.access$102(CockpitTB_3.this, false);
        }
        CockpitTB_3.access$202(CockpitTB_3.this, CockpitTB_3.this.setOld); CockpitTB_3.access$302(CockpitTB_3.this, CockpitTB_3.this.setNew); CockpitTB_3.access$402(CockpitTB_3.this, CockpitTB_3.this.setTmp);

        for (int i = 0; i < 4; i++) {
          CockpitTB_3.this.setNew.throttle[i] = ((10.0F * CockpitTB_3.access$300(CockpitTB_3.this).throttle[i] + CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.EI.engines[i].getControlThrottle()) / 11.0F);
        }
        CockpitTB_3.this.setNew.altimeter = CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        if (Math.abs(CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0F) {
          CockpitTB_3.this.setNew.azimuth = ((35.0F * CockpitTB_3.this.setOld.azimuth + -CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw()) / 36.0F);
        }
        if ((CockpitTB_3.this.setOld.azimuth > 270.0F) && (CockpitTB_3.this.setNew.azimuth < 90.0F)) CockpitTB_3.this.setOld.azimuth -= 360.0F;
        if ((CockpitTB_3.this.setOld.azimuth < 90.0F) && (CockpitTB_3.this.setNew.azimuth > 270.0F)) CockpitTB_3.this.setOld.azimuth += 360.0F;
        CockpitTB_3.this.setNew.waypointAzimuth = ((10.0F * CockpitTB_3.this.setOld.waypointAzimuth + (CockpitTB_3.this.waypointAzimuth() - CockpitTB_3.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F)) / 11.0F);
        CockpitTB_3.this.setNew.vspeed = ((199.0F * CockpitTB_3.this.setOld.vspeed + CockpitTB_3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float[] throttle;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitTB_3 this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.throttle = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
    }

    Variables(CockpitTB_3.1 arg2)
    {
      this();
    }
  }
}