package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitMC_200_VII extends CockpitPilot
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictMix = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf = 1.0F;
  private float pictFuel = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 29.0F, 63.5F, 98.5F, 115.5F, 132.5F, 165.5F, 202.5F, 241.0F, 280.0F, 316.0F };

  private static final float[] oilTempScale = { 0.0F, 33.0F, 80.0F, 153.0F, 301.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitMC_200_VII()
  {
    super("3DO/Cockpit/MC-200_VII/hier.him", "i16");

    this.cockpitNightMats = new String[] { "mat2_tr", "strum1dmg", "strum1", "strum2dmg", "strum2", "strum4dmg", "strum4", "strumsxdmg", "strumsx" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Pedals", 15.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Throttle", 46.360001F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_MagnetoSwitch", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Mix", 46.25F * (this.pictMix = 0.85F * this.pictMix + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlMix()), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_PropPitch1", 54.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Flap1", 40.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.25F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Gear1", 90.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1", -76.5F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilRad1", 91.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilRad2", 111.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 50.0F, 550.0F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer2", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 50.0F, 550.0F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", 0.0F, -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", 0.0F, -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(this.setNew.vspeed, -25.0F, 25.0F, 180.0F, -180.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 3000.0F, 0.0F, -327.0F), 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turn1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -21.0F, 21.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -12.0F, 12.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ManfoldPress", 0.0F, this.pictManf = 0.9F * this.pictManf + 0.1F * cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, -317.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilTemp1", 0.0F, -floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut, 30.0F, 150.0F, 0.0F, 4.0F), oilTempScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilPress1", 0.0F, -cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 301.5F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OilPress2", 0.0F, -cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPress1", 0.0F, -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, 301.5F), 0.0F);
    float f = cvt(Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()), 0.0F, 12.0F, 1.0F, 0.0F);
    this.pictFuel = (0.92F * this.pictFuel + 0.08F * cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 234.0F, cvt(f, 0.0F, 1.0F, 0.0F, 24.5F), cvt(f, 0.0F, 1.0F, 215.0F, 205.0F)));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelQuantity1", -this.pictFuel, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_EngTemp1", 0.0F, -cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 74.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirPress1", 0.0F, -135.0F, 0.0F);

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpR", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F) || (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpL", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F) || (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownR", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownL", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
    } else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearUpL", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownR", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLampGearDownL", false);
    }
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) {
      f += 180.0F;
    }
    while (f > 180.0F) {
      f -= 180.0F;
    }
    return f;
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_OilSplats_D1", true);
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
    if (this.jdField_cockpitLightControl_of_type_Boolean) {
      setNightMats(true);
    }
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
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
      if (CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null) {
        CockpitMC_200_VII.access$102(CockpitMC_200_VII.this, CockpitMC_200_VII.this.setOld); CockpitMC_200_VII.access$202(CockpitMC_200_VII.this, CockpitMC_200_VII.this.setNew); CockpitMC_200_VII.access$302(CockpitMC_200_VII.this, CockpitMC_200_VII.this.setTmp);

        CockpitMC_200_VII.this.setNew.throttle = (0.85F * CockpitMC_200_VII.this.setOld.throttle + CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl * 0.15F);
        CockpitMC_200_VII.this.setNew.prop = (0.85F * CockpitMC_200_VII.this.setOld.prop + CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getStepControl() * 0.15F);
        CockpitMC_200_VII.this.setNew.altimeter = CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        float f = CockpitMC_200_VII.this.waypointAzimuth();
        CockpitMC_200_VII.this.setNew.waypointAzimuth.setDeg(CockpitMC_200_VII.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitMC_200_VII.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitMC_200_VII.this.setNew.azimuth.setDeg(CockpitMC_200_VII.this.setOld.azimuth.getDeg(1.0F), CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
        CockpitMC_200_VII.this.setNew.vspeed = ((199.0F * CockpitMC_200_VII.this.setOld.vspeed + CockpitMC_200_VII.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    private final CockpitMC_200_VII this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitMC_200_VII.1 arg2)
    {
      this();
    }
  }
}