package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
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
import com.maddox.il2.game.Mission;
import com.maddox.rts.Time;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;

public class CockpitHalifaxBMkIII extends CockpitPilot
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
  private float pictBbay;
  private float pictSupc;
  private float pictLlit;
  private float pictManf1;
  private float pictManf2;
  private float pictManf3;
  private float pictManf4;
  private boolean bNeedSetUp;
  private int iWiper;
  private static final float[] speedometerScale = { 0.0F, 16.5F, 31.0F, 60.5F, 90.0F, 120.5F, 151.5F, 182.0F, 213.5F, 244.0F, 274.0F, 303.0F, 333.5F, 369.5F, 399.0F, 434.5F, 465.5F, 496.5F, 527.5F, 558.5F, 588.5F, 626.5F };

  private static final float[] radScale = { 0.0F, 0.1F, 0.2F, 0.3F, 3.5F, 11.0F, 22.0F, 37.5F, 58.5F, 82.5F, 112.5F, 147.0F, 187.0F, 236.0F, 298.5F };

  private static final float[] boostScale = { 0.0F, 21.0F, 39.0F, 56.0F, 90.5F, 109.5F, 129.0F, 146.5F, 163.0F, 179.5F, 196.0F, 212.5F, 231.5F, 250.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };
  private Point3d tmpP;
  private Vector3d tmpV;
  private SoundFX fxw;
  private Sample wiStart;
  private Sample wiRun;
  private int wiState;

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

    for (float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x)); f <= -180.0F; f += 180.0F);
    while (f > 180.0F) f -= 180.0F;
    return f;
  }

  public CockpitHalifaxBMkIII()
  {
    super("3DO/Cockpit/CockpitHalifaxBMkIII/hier.him", "he111");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictBrake = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictBbay = 0.0F;
    this.pictSupc = 0.0F;
    this.pictLlit = 0.0F;
    this.pictManf1 = 1.0F;
    this.pictManf2 = 1.0F;
    this.pictManf3 = 1.0F;
    this.pictManf4 = 1.0F;
    this.bNeedSetUp = true;
    this.iWiper = 0;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.fxw = null;
    this.wiStart = new Sample("wip_002_s.wav", 256, 65535);
    this.wiRun = new Sample("wip_002.wav", 256, 65535);
    this.wiState = 0;
    this.cockpitNightMats = new String[] { "01", "02", "03", "04", "05", "12", "20", "23", "24", "26", "27", "01_damage", "03_damage", "04_damage", "24_damage" };

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
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Wiper1", cvt(interp(this.setNew.wiper, this.setOld.wiper, paramFloat), -1.0F, 1.0F, -61.0F, 61.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpL", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownL", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Columnbase", 12.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 45.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ColumnSwitch", 20.0F * (this.pictBrake = 0.91F * this.pictBrake + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle1", 62.720001F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle2", 62.720001F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle3", 62.720001F * interp(this.setNew.throttle3, this.setOld.throttle3, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle4", 62.720001F * interp(this.setNew.throttle4, this.setOld.throttle4, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", -20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Gear1", -55.0F * (this.pictGear = 0.9F * this.pictGear + 0.1F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl), 0.0F, 0.0F);

    if (Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap()) > 0.02F)
    {
      if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl - this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.0F)
        f1 = -0.0299F;
      else
        f1 = -0.0F;
    }
    else {
      f1 = -0.0144F;
    }
    this.pictFlap = (0.8F * this.pictFlap + 0.2F * f1);
    resetYPRmodifier();
    Cockpit.xyz[2] = this.pictFlap;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Flaps1", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1", -1000.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim2", 1000.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimAileronControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim3", 90.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimRudderControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop1", 0.0F, 45.0F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop2", 0.0F, 45.0F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop3", 0.0F, 45.0F * interp(this.setNew.prop3, this.setOld.prop3, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop4", 0.0F, 45.0F * interp(this.setNew.prop4, this.setOld.prop4, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_BombBay1", 70.0F * (this.pictBbay = 0.9F * this.pictBbay + 0.1F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("COMPASS_M", 90.0F + this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("SHKALA_DIRECTOR", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat) + this.setNew.waypointAzimuth.getDeg(paramFloat) + 90.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F, 0.0F);
    this.pictManf1 = (0.91F * this.pictManf1 + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure());
    float f1 = this.pictManf1 - 1.0F;
    float f2 = 1.0F;
    if (f1 <= 0.0F)
      f2 = -1.0F;
    f1 = Math.abs(f1);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_BOOST1", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);
    this.pictManf2 = (0.91F * this.pictManf2 + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getManifoldPressure());
    f1 = this.pictManf2 - 1.0F;
    f2 = 1.0F;
    if (f1 <= 0.0F)
      f2 = -1.0F;
    f1 = Math.abs(f1);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_BOOST2", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);
    this.pictManf3 = (0.91F * this.pictManf3 + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getManifoldPressure());
    f1 = this.pictManf3 - 1.0F;
    f2 = 1.0F;
    if (f1 <= 0.0F)
      f2 = -1.0F;
    f1 = Math.abs(f1);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_BOOST3", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);
    this.pictManf4 = (0.91F * this.pictManf4 + 0.09F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].getManifoldPressure());
    f1 = this.pictManf4 - 1.0F;
    f2 = 1.0F;
    if (f1 <= 0.0F)
      f2 = -1.0F;
    f1 = Math.abs(f1);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_BOOST4", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 763.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 763.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL3", cvt((float)Math.sqrt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel), 0.0F, (float)Math.sqrt(88.379997253417969D), 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL4", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 1022.0F, 1200.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL5", 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL6", 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL7", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 851.0F, 1123.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_FUEL8", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 851.0F, 1123.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_SHORT1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_LONG1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_SHORT2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_LONG2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_SHORT3", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_LONG3", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_SHORT4", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM_LONG4", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TEMP_OIL1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 266.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TEMP_OIL2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 100.0F, 0.0F, 266.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TEMP_RAD1", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TEMP_RAD2", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STR_OIL_LB1", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -31.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STR_OIL_LB2", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, -31.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_TURN_UP", cvt(getBall(8.0D), -8.0F, 8.0F, 31.0F, -31.0F), 0.0F, 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STREL_TURN_DOWN", cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELK_V_LONG", floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeed()), 26.822399F, 214.57919F, 0.0F, 21.0F), speedometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELK_V_SHORT", false);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STRELKA_GOS", -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.02355F, -0.02355F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("STRELKA_GOR", Cockpit.xyz, Cockpit.ypr);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("STR_CLIMB", floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FlapPos", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap(), 0.0F, 1.0F, 0.0F, 125.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1Pos", -104.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim2Pos", 208.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimAileronControl(), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL5", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL6", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_FUEL7", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_SHORT1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_LONG1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_SHORT2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_LONG2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_SHORT3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_LONG3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_SHORT4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM_LONG4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_BOOST1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_BOOST2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_BOOST3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_BOOST4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TEMP_OIL1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TEMP_OIL2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_TEMP_RAD1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELK_V_LONG", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELK_V_SHORT", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_GOR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELKA_GOS", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STREL_ALT_LONG", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STREL_ALT_SHORT", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("STRELK_TURN_UP", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FlapPos", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
    }
    retoggleLight();
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
      if (CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitHalifaxBMkIII.access$002(CockpitHalifaxBMkIII.this, CockpitHalifaxBMkIII.this.setOld);
        CockpitHalifaxBMkIII.access$102(CockpitHalifaxBMkIII.this, CockpitHalifaxBMkIII.this.setNew);
        CockpitHalifaxBMkIII.access$202(CockpitHalifaxBMkIII.this, CockpitHalifaxBMkIII.this.setTmp);
        CockpitHalifaxBMkIII.this.setNew.throttle1 = (0.85F * CockpitHalifaxBMkIII.this.setOld.throttle1 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.prop1 = (0.85F * CockpitHalifaxBMkIII.this.setOld.prop1 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.throttle2 = (0.85F * CockpitHalifaxBMkIII.this.setOld.throttle2 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.prop2 = (0.85F * CockpitHalifaxBMkIII.this.setOld.prop2 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlProp() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.throttle3 = (0.85F * CockpitHalifaxBMkIII.this.setOld.throttle3 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlThrottle() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.prop3 = (0.85F * CockpitHalifaxBMkIII.this.setOld.prop3 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].getControlProp() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.throttle4 = (0.85F * CockpitHalifaxBMkIII.this.setOld.throttle4 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].getControlThrottle() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.prop4 = (0.85F * CockpitHalifaxBMkIII.this.setOld.prop4 + CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].getControlProp() * 0.15F);
        CockpitHalifaxBMkIII.this.setNew.altimeter = CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        float f = CockpitHalifaxBMkIII.this.waypointAzimuth();
        CockpitHalifaxBMkIII.this.setNew.waypointAzimuth.setDeg(CockpitHalifaxBMkIII.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitHalifaxBMkIII.this.setOld.azimuth.getDeg(1.0F));
        CockpitHalifaxBMkIII.this.setNew.azimuth.setDeg(CockpitHalifaxBMkIII.this.setOld.azimuth.getDeg(1.0F), CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
        CockpitHalifaxBMkIII.this.setNew.vspeed = (0.99F * CockpitHalifaxBMkIII.this.setOld.vspeed + 0.01F * CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed());
        switch (CockpitHalifaxBMkIII.this.iWiper)
        {
        default:
          break;
        case 0:
          if ((Mission.curCloudsType() <= 4) || (CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() >= 220.0F) || (CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude() >= Mission.curCloudsHeight() + 300.0F)) break;
          CockpitHalifaxBMkIII.access$302(CockpitHalifaxBMkIII.this, 1); break;
        case 1:
          CockpitHalifaxBMkIII.this.setNew.wiper = (CockpitHalifaxBMkIII.this.setOld.wiper - 0.05F);
          if (CockpitHalifaxBMkIII.this.setNew.wiper < -1.03F)
            CockpitHalifaxBMkIII.access$308(CockpitHalifaxBMkIII.this);
          if (CockpitHalifaxBMkIII.this.wiState >= 2)
            break;
          if (CockpitHalifaxBMkIII.this.wiState == 0)
          {
            if (CockpitHalifaxBMkIII.this.fxw == null)
            {
              CockpitHalifaxBMkIII.access$502(CockpitHalifaxBMkIII.this, CockpitHalifaxBMkIII.this.aircraft().newSound("aircraft.wiper", false));
              if (CockpitHalifaxBMkIII.this.fxw != null)
              {
                CockpitHalifaxBMkIII.this.fxw.setParent(CockpitHalifaxBMkIII.this.aircraft().getRootFX());
                CockpitHalifaxBMkIII.this.fxw.setPosition(CockpitHalifaxBMkIII.this.sfxPos);
              }
            }
            if (CockpitHalifaxBMkIII.this.fxw != null)
              CockpitHalifaxBMkIII.this.fxw.play(CockpitHalifaxBMkIII.this.wiStart);
          }
          if (CockpitHalifaxBMkIII.this.fxw == null)
            break;
          CockpitHalifaxBMkIII.this.fxw.play(CockpitHalifaxBMkIII.this.wiRun);
          CockpitHalifaxBMkIII.access$402(CockpitHalifaxBMkIII.this, 2); break;
        case 2:
          CockpitHalifaxBMkIII.this.setNew.wiper = (CockpitHalifaxBMkIII.this.setOld.wiper + 0.05F);
          if (CockpitHalifaxBMkIII.this.setNew.wiper > 1.03F)
            CockpitHalifaxBMkIII.access$308(CockpitHalifaxBMkIII.this);
          if (CockpitHalifaxBMkIII.this.wiState <= 1) break;
          CockpitHalifaxBMkIII.access$402(CockpitHalifaxBMkIII.this, 1); break;
        case 3:
          CockpitHalifaxBMkIII.this.setNew.wiper = (CockpitHalifaxBMkIII.this.setOld.wiper - 0.05F);
          if (CockpitHalifaxBMkIII.this.setNew.wiper >= 0.02F)
            break;
          if ((CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > 250.0F) || (CockpitHalifaxBMkIII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude() > Mission.curCloudsHeight() + 400.0F))
            CockpitHalifaxBMkIII.access$308(CockpitHalifaxBMkIII.this);
          else
            CockpitHalifaxBMkIII.access$302(CockpitHalifaxBMkIII.this, 1);
          break;
        case 4:
          CockpitHalifaxBMkIII.this.setNew.wiper = CockpitHalifaxBMkIII.this.setOld.wiper;
          CockpitHalifaxBMkIII.access$302(CockpitHalifaxBMkIII.this, 0);
          CockpitHalifaxBMkIII.access$402(CockpitHalifaxBMkIII.this, 0);
          if (CockpitHalifaxBMkIII.this.fxw == null) break;
          CockpitHalifaxBMkIII.this.fxw.cancel();
        }
      }

      return true;
    }

    Interpolater()
    {
    }
  }

  private class Variables
  {
    float throttle1;
    float throttle2;
    float throttle3;
    float throttle4;
    float prop1;
    float prop2;
    float prop3;
    float prop4;
    float altimeter;
    float vspeed;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float wiper;
    private final CockpitHalifaxBMkIII this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitHalifaxBMkIII.1 arg2)
    {
      this();
    }
  }
}