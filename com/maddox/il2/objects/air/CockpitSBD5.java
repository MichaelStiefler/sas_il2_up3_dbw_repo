package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitSBD5 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 16.5F, 79.5F, 143.0F, 206.5F, 229.5F, 251.0F, 272.5F, 294.0F, 316.0F, 339.5F };

  private static final float[] variometerScale = { 0.0F, 25.0F, 49.5F, 64.0F, 78.5F, 89.5F, 101.0F, 112.0F, 123.0F, 134.5F, 145.5F, 157.0F, 168.0F, 168.0F };

  public CockpitSBD5()
  {
    super("3DO/Cockpit/SBD-5/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "GagePanel1", "GagePanel1_D1", "GagePanel2", "GagePanel2_D1", "GagePanel3", "GagePanel3_D1", "GagePanel4", "GagePanel4_D1", "misc2" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.7F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1", 0.0F, 1444.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimAileronControl(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim2", 0.0F, 1444.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimRudderControl(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim3", 0.0F, 1444.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Flaps1", 0.0F, -77.0F * this.setNew.flaps, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Gear1", 0.0F, -77.0F * this.setNew.gear, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throtle1", 0.0F, -40.0F * this.setNew.throttle, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Prop1", 0.0F, -68.0F * this.setNew.prop, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mixture1", 0.0F, -55.0F * this.setNew.mix, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Supercharger1", 0.0F, -55.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlCompressor(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RightPedal", 0.0F, 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_LeftPedal", 0.0F, 20.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl) * 8.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 8.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_DiveBrake1", 0.0F, -77.0F * this.setNew.divebrake, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_CowlFlap1", 0.0F, -28.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].haveBullets())) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Bomb1", 0.0F, 35.0F, 0.0F);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, -3600.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, -36000.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 925.99976F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -16.0F, 16.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank3", 0.0F, -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.025F, -0.025F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_TurnBank4", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", 0.0F, this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale) : floatindex(cvt(-this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel3", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel4", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.3F, 0.0F, -180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -74.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Temp2", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, -180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(this.setNew.man, 0.3386378F, 2.539784F, 0.0F, -344.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 0.0F, 200.0F, 0.0F, -180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -301.0F), 0.0F);
    float f = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM();
    f = 2.5F * (float)Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f))));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Suction1", 0.0F, cvt(f, 0.0F, 10.0F, 0.0F, -300.0F), 0.0F);
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear) {
      resetYPRmodifier();
      Cockpit.xyz[0] = (-0.133F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_GearL1", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear) {
      resetYPRmodifier();
      Cockpit.xyz[0] = (-0.133F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_GearR1", Cockpit.xyz, Cockpit.ypr);
    }
    resetYPRmodifier();
    Cockpit.xyz[0] = (-0.118F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Flap1", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_EnginePrim", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() > 0 ? -36.0F : 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_MagSwitch", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, -111.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null) {
        CockpitSBD5.access$102(CockpitSBD5.this, CockpitSBD5.this.setOld); CockpitSBD5.access$202(CockpitSBD5.this, CockpitSBD5.this.setNew); CockpitSBD5.access$302(CockpitSBD5.this, CockpitSBD5.this.setTmp);

        CockpitSBD5.this.setNew.flaps = (0.9F * CockpitSBD5.this.setOld.flaps + 0.1F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl);
        CockpitSBD5.this.setNew.gear = (0.7F * CockpitSBD5.this.setOld.gear + 0.3F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl);
        CockpitSBD5.this.setNew.throttle = (0.8F * CockpitSBD5.this.setOld.throttle + 0.2F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl);
        CockpitSBD5.this.setNew.prop = (0.8F * CockpitSBD5.this.setOld.prop + 0.2F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp());
        CockpitSBD5.this.setNew.mix = (0.8F * CockpitSBD5.this.setOld.mix + 0.2F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix());
        CockpitSBD5.this.setNew.divebrake = (0.8F * CockpitSBD5.this.setOld.divebrake + 0.2F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl);
        CockpitSBD5.this.setNew.man = (0.92F * CockpitSBD5.this.setOld.man + 0.08F * CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure());
        CockpitSBD5.this.setNew.altimeter = CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();

        CockpitSBD5.this.setNew.azimuth.setDeg(CockpitSBD5.this.setOld.azimuth.getDeg(1.0F), CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());

        CockpitSBD5.this.setNew.vspeed = ((399.0F * CockpitSBD5.this.setOld.vspeed + CockpitSBD5.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 400.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float flaps;
    float gear;
    float throttle;
    float prop;
    float mix;
    float divebrake;
    float altimeter;
    float man;
    float vspeed;
    AnglesFork azimuth;
    private final CockpitSBD5 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitSBD5.1 arg2)
    {
      this();
    }
  }
}