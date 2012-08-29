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
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitME_262 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private Gun[] gun = { null, null, null, null };
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bU4 = false;

  private static final float[] speedometerIndScale = { 0.0F, 0.0F, 0.0F, 17.0F, 35.5F, 57.5F, 76.0F, 95.0F, 112.0F };

  private static final float[] speedometerTruScale = { 0.0F, 32.75F, 65.5F, 98.25F, 131.0F, 164.0F, 200.0F, 237.0F, 270.5F, 304.0F, 336.0F };

  private static final float[] variometerScale = { 0.0F, 13.5F, 27.0F, 43.5F, 90.0F, 142.5F, 157.0F, 170.5F, 184.0F, 201.5F, 214.5F, 226.0F, 239.5F, 253.0F, 266.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private static final float[] fuelScale = { 0.0F, 11.0F, 31.0F, 57.0F, 84.0F, 103.5F };

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    return (float)(57.295779513082323D * Math.atan2(this.tmpV.y, this.tmpV.x));
  }

  public CockpitME_262()
  {
    super("3DO/Cockpit/Me-262/hier.him", "he111");

    this.cockpitNightMats = new String[] { "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", "petitfla", "turnbank" };

    setNightMats(false);

    this.setNew.dimPosition = 1.0F;

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).getGunByHookName("_CANNON03");
      this.gun[1] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).getGunByHookName("_CANNON01");
      this.gun[2] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).getGunByHookName("_CANNON02");
      this.gun[3] = ((Aircraft)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).getGunByHookName("_CANNON04");
    }

    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.isTick(44, 0))
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLGreen1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRGreen1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearCGreen1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 1.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearLRed1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isAnyDamaged()));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearRRed1", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isAnyDamaged()));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GearCRed1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() == 0.0F);

      if (!this.bU4) {
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GunLamp01", !this.gun[0].haveBullets());
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GunLamp02", !this.gun[1].haveBullets());
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GunLamp03", !this.gun[2].haveBullets());
        this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GunLamp04", !this.gun[3].haveBullets());
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_MachLamp", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeed() / Atmosphere.sonicSpeed((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) > 0.8F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_CabinLamp", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double > 12000.0D);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelLampV", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 300.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelLampIn", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel < 300.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl == 0.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() != 0.0F) ? -0.0107F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_GearEin", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[1] = ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl == 1.0F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() != 1.0F) ? -0.0107F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_GearAus", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[1] = (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl < this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() ? -0.0107F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_FlapEin", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[1] = (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl > this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() ? -0.0107F : 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_FlapAus", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl));

    resetYPRmodifier();
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[0] != 0) {
      Cockpit.xyz[2] = -0.0025F;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Columnbutton1", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[2] != 0) || (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.saveWeaponControl[3] != 0)) {
      Cockpit.xyz[2] = -0.00325F;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Columnbutton2", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PedalStrut", 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", -20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", -20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ThrottleL", 0.0F, -75.0F * interp(this.setNew.throttlel, this.setOld.throttlel, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ThrottleR", 0.0F, -75.0F * interp(this.setNew.throttler, this.setOld.throttler, paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelLeverL", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos() == 3 ? 6.5F : 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelLeverR", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlMagnetos() == 3 ? 6.5F : 0.0F, 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = (0.03675F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_TailTrim", Cockpit.xyz, Cockpit.ypr);

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AmmoCounter1", cvt(this.gun[1].countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AmmoCounter2", cvt(this.gun[2].countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 100.0F, 400.0F, 2.0F, 8.0F), speedometerIndScale), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH(), 100.0F, 1000.0F, 1.0F, 10.0F), speedometerTruScale), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 16000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, -7.5F, 7.5F));
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -20.0F, 50.0F, 0.0F, 14.0F), variometerScale), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPML", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPMR", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_GasPressureL", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.6F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_GasPressureR", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.6F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_GasTempL", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_GasTempR", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPressL", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 80.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPowerOutput() * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPressR", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 80.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getPowerOutput() * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelRemainV", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelRemainIn", floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Speedometer1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Speedometer1_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer2", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPML", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPML_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPML", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelRemainV", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelRemainV_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelRemainV", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Altimeter1_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter2", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GasPressureL", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GasPressureL_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GasPressureL", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPMR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("RPMR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPMR", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GasPressureR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("GasPressureR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_GasPressureR", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Climb", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Climb_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Climb1", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressR_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage2", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Revi_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_RETICLE", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Z_MASK", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressL", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressL_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelPressL", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", true);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Altimeter1_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter2", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Climb", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Climb_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Climb1", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("AFN", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("AFN_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_AFN1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_AFN2", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressL", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelPressL_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelPressL", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelRemainIn", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("FuelRemainIn_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelRemainIn", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0);
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    if ((aircraft() instanceof ME_262A1AU4)) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Ammo262U4", true);
      this.bU4 = true;
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
      setNightMats(true);
    }
    else
      setNightMats(false);
  }

  private void retoggleLight() {
    if (this.jdField_cockpitLightControl_of_type_Boolean) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
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
      CockpitME_262.access$102(CockpitME_262.this, CockpitME_262.this.setOld); CockpitME_262.access$202(CockpitME_262.this, CockpitME_262.this.setNew); CockpitME_262.access$302(CockpitME_262.this, CockpitME_262.this.setTmp);

      CockpitME_262.this.setNew.altimeter = CockpitME_262.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
      CockpitME_262.this.setNew.throttlel = ((10.0F * CockpitME_262.this.setOld.throttlel + CockpitME_262.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle()) / 11.0F);
      CockpitME_262.this.setNew.throttler = ((10.0F * CockpitME_262.this.setOld.throttler + CockpitME_262.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle()) / 11.0F);
      CockpitME_262.this.setNew.azimuth = CockpitME_262.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getYaw();
      if ((CockpitME_262.this.setOld.azimuth > 270.0F) && (CockpitME_262.this.setNew.azimuth < 90.0F)) CockpitME_262.this.setOld.azimuth -= 360.0F;
      if ((CockpitME_262.this.setOld.azimuth < 90.0F) && (CockpitME_262.this.setNew.azimuth > 270.0F)) CockpitME_262.this.setOld.azimuth += 360.0F;
      CockpitME_262.this.setNew.waypointAzimuth = ((10.0F * CockpitME_262.this.setOld.waypointAzimuth + (CockpitME_262.this.waypointAzimuth() - CockpitME_262.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
      CockpitME_262.this.setNew.vspeed = ((299.0F * CockpitME_262.this.setOld.vspeed + CockpitME_262.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 300.0F);

      if (CockpitME_262.this.cockpitDimControl) {
        if (CockpitME_262.this.setNew.dimPosition > 0.0F) CockpitME_262.this.setNew.dimPosition = (CockpitME_262.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitME_262.this.setNew.dimPosition < 1.0F) CockpitME_262.this.setNew.dimPosition = (CockpitME_262.this.setOld.dimPosition + 0.05F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttlel;
    float throttler;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    float dimPosition;
    private final CockpitME_262 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitME_262.1 arg2) { this();
    }
  }
}