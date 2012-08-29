package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitL_5 extends CockpitPilot
{
  private boolean bNeedSetUp;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictGear;
  private float pictFlap;
  private Gun[] gun = { null, null, null, null };

  private BulletEmitter[] bgun = { null, null, null, null };

  private BulletEmitter[] rgun = { null, null, null, null };

  private BulletEmitter[] tgun = { null, null };

  private static final float[] speedometerScale = { 0.0F, 15.5F, 77.0F, 175.0F, 275.0F, 360.0F, 412.0F, 471.0F, 539.0F, 610.5F, 669.75F, 719.0F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };
  private Point3d tmpP;
  private Vector3d tmpV;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("WingLMid_D0", false);
      aircraft().hierMesh().chunkVisible("WingRMid_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("WingLMid_D0", true);
    aircraft().hierMesh().chunkVisible("WingRMid_D0", true);
    super.doFocusLeave();
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

  public CockpitL_5()
  {
    super("3DO/Cockpit/Sentinel/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictGear = 0.0F;
    this.pictFlap = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.cockpitNightMats = new String[] { "Instrumentos001", "Instrumentos002", "Instrumentos003" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    resetYPRmodifier();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Trim_Ele", 0.0F, 0.0F, 722.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pedal_D", 0.0F, 0.0F, -15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pedal_I", 0.0F, 0.0F, 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Acelerador", 0.0F, 0.0F, -70.0F * this.setNew.throttle);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Palanca", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 10.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("z_Flap", 0.0F, 0.0F, 18.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.95F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl));
    resetYPRmodifier();
    resetYPRmodifier();
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() > 0) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() < 3))
      Cockpit.xyz[1] = 0.02825F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Starter", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Magneto", 0.0F, 60.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Radiador", 0.0F, 0.0F, 3.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Hora", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Minuto", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimetro1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 36000.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimetro2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 3600.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ASI", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 2500.0F, 0.0F, 504.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 250.0F, 0.0F, 97.5F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilTemp", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 20.0F, 120.0F, 0.0F, 78.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oilpres", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 256.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass", this.setNew.azimuth, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 180.0F, 0.0F, 275.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 180.0F, 0.0F, 275.0F), 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Palo", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Bola", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -15.0F, 15.0F), 0.0F);
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

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitL_5.access$002(CockpitL_5.this, CockpitL_5.this.setOld);
        CockpitL_5.access$102(CockpitL_5.this, CockpitL_5.this.setNew);
        CockpitL_5.access$202(CockpitL_5.this, CockpitL_5.this.setTmp);
        CockpitL_5.this.setNew.throttle = (0.85F * CockpitL_5.this.setOld.throttle + CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl * 0.15F);
        CockpitL_5.this.setNew.prop = (0.85F * CockpitL_5.this.setOld.prop + CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getStepControl() * 0.15F);
        CockpitL_5.this.setNew.stage = (0.85F * CockpitL_5.this.setOld.stage + CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlCompressor() * 0.15F);
        CockpitL_5.this.setNew.mix = (0.85F * CockpitL_5.this.setOld.mix + CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix() * 0.15F);
        CockpitL_5.this.setNew.altimeter = CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        CockpitL_5.this.setNew.azimuth = ((35.0F * CockpitL_5.this.setOld.azimuth + -CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw()) / 36.0F);
        if ((CockpitL_5.this.setOld.azimuth > 270.0F) && (CockpitL_5.this.setNew.azimuth < 90.0F))
          CockpitL_5.this.setOld.azimuth -= 360.0F;
        if ((CockpitL_5.this.setOld.azimuth < 90.0F) && (CockpitL_5.this.setNew.azimuth > 270.0F))
          CockpitL_5.this.setOld.azimuth += 360.0F;
        CockpitL_5.this.setNew.waypointAzimuth = ((10.0F * CockpitL_5.this.setOld.waypointAzimuth + CockpitL_5.this.waypointAzimuth() + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        CockpitL_5.this.setNew.vspeed = ((199.0F * CockpitL_5.this.setOld.vspeed + CockpitL_5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
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
    float prop;
    float mix;
    float stage;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitL_5 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitL_5.1 arg2)
    {
      this();
    }
  }
}