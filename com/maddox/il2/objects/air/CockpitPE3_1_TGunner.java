package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitPE3_1_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private boolean[] bBombs = { false, false, false, false, false, false, false, false, false, false };
  private BulletEmitter[] eBombs = { null, null, null, null, null, null, null, null, null, null };
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private static final float[] speedometerScale;
  private Hook hook1 = null;

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WireL_D0", aircraft().hierMesh().isChunkVisible("WireL_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("WireR_D0", aircraft().hierMesh().isChunkVisible("WireR_D0"));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Gear1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl > 0.5F ? -60.0F : 0.0F, 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = (-0.095F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Columnbase", -8.0F * (this.pictElev = 0.65F * this.pictElev + 0.35F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Column", -45.0F * (this.pictAiler = 0.65F * this.pictAiler + 0.35F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Brake", 21.5F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl, 0.0F, 0.0F);
    resetYPRmodifier();

    resetYPRmodifier();
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator() > 0.5F) {
      Cockpit.xyz[1] = 0.011F;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_RadL", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlRadiator() > 0.5F) {
      Cockpit.xyz[1] = 0.011F;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_RadR", Cockpit.xyz, Cockpit.ypr);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter3", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Altimeter4", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.018F, -0.018F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_TurnBank1", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1Q", -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F, 0.0F);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank2", cvt(this.w.z, -0.23562F, 0.23562F, -27.0F, 27.0F), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", cvt(this.setNew.waypointAzimuth.getDeg(paramFloat), -90.0F, 90.0F, -55.5F, 55.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -3600.0F), 0.0F, 0.0F);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM3", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_RPM4", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, -3600.0F), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Fuel1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel, 0.0F, 360.0F, 0.0F, -198.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_InertGas", cvt(this.setNew.inert, 0.0F, 1.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    float f = 0.0F;
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F) {
      f = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 0.0F, 570.0F, 0.0F, 0.26F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPres1", cvt(f, 0.0F, 1.0F, 0.0F, -270.0F), 0.0F, 0.0F);
    f = 0.0F;
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F) {
      f = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 0.0F, 570.0F, 0.0F, 0.26F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FuelPres2", cvt(f, 0.0F, 1.0F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Temp1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Temp2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tWaterOut, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pres1", cvt(this.setNew.man1, 0.399966F, 1.599864F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pres2", cvt(this.setNew.man2, 0.399966F, 1.599864F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oil1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilIn, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oil2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilIn, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Oilpres2", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirPres", -116.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_OxPres", 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_HPres", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isHydroOperable() ? -102.0F : 0.0F, 0.0F, 0.0F);
    f = Atmosphere.temperature((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 273.09F;
    if (f < -40.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirTemp", cvt(f, -40.0F, -20.0F, 52.0F, 35.0F), 0.0F, 0.0F);
    else if (f < 0.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirTemp", cvt(f, -20.0F, 0.0F, 35.0F, 0.0F), 0.0F, 0.0F);
    else if (f < 20.0F)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirTemp", cvt(f, 0.0F, 20.0F, 0.0F, -18.5F), 0.0F, 0.0F);
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_AirTemp", cvt(f, 20.0F, 50.0F, -18.5F, -37.0F), 0.0F, 0.0F);
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_FlapPos", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap(), 0.0F, 1.0F, 0.0F, -180.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass4", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XRGearUp", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F) || (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLGearUp", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F) || (!this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XCGearUp", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XRGearDn", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XLGearDn", (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F) && (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XCGearDn", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.99F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XFlapsUp", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() > 0.5F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XOverG1", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getOverload() > 3.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XOverG2", this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getOverload() < -1.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XERimCenter", Math.abs(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl()) < 0.02F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb1", this.bBombs[0]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb2", this.bBombs[1]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb3", this.bBombs[2]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb4", this.bBombs[3]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb5", this.bBombs[4]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb6", this.bBombs[5]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb7", this.bBombs[6]);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XBomb8", this.bBombs[7]);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("NDetails_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("NDetails_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelB", 0.0F, paramOrient.getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelC", 0.0F, -cvt(paramOrient.getYaw(), -45.0F, 27.0F, -45.0F, 27.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelD", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -45.0F) f1 = -45.0F;
    if (f1 > 45.0F) f1 = 45.0F;
    if (f2 > 45.0F) f2 = 45.0F;
    if (f2 < -1.0F) f2 = -1.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;

    if (this.jdField_bGunFire_of_type_Boolean) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN01");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN01");
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }
    else this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Fuel1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Pres1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter3", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter4", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage5", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage6", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0) && 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) != 0)) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D0", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Panel_D2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Altimeter2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Speedometer1", false);

      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_AirTemp", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Pres2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_RPM2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_InertGas", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_FuelPres2", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Oilpres1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Oilpres2", false);
    }

    retoggleLight();
  }

  public CockpitPE3_1_TGunner()
  {
    super("3DO/Cockpit/Pe-3series1-TGun/hier.him", "he111");

    this.cockpitNightMats = new String[] { "GP_I", "GP_II", "GP_II_DM", "GP_III_DM", "GP_III", "GP_IV_DM", "GP_IV", "GP_V", "GP_VI", "compass", "Eqpt_II", "Trans_II", "Trans_VI_Pilot", "Trans_VII_Pilot" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);

    speedometerScale = new float[] { 0.0F, 0.0F, 10.5F, 42.5F, 85.0F, 125.0F, 165.5F, 181.0F, 198.0F, 214.5F, 231.0F, 249.0F, 266.5F, 287.5F, 308.0F, 326.5F, 346.0F };
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitPE3_1_TGunner.access$102(CockpitPE3_1_TGunner.this, CockpitPE3_1_TGunner.this.setOld); CockpitPE3_1_TGunner.access$202(CockpitPE3_1_TGunner.this, CockpitPE3_1_TGunner.this.setNew); CockpitPE3_1_TGunner.access$302(CockpitPE3_1_TGunner.this, CockpitPE3_1_TGunner.this.setTmp);
        if (CockpitPE3_1_TGunner.this.eBombs[0] == null) {
          CockpitPE3_1_TGunner.this.eBombs[0] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_ExternalBomb01");
          CockpitPE3_1_TGunner.this.eBombs[1] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_ExternalBomb02");
          CockpitPE3_1_TGunner.this.eBombs[2] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_ExternalBomb03");
          CockpitPE3_1_TGunner.this.eBombs[3] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_ExternalBomb04");
          CockpitPE3_1_TGunner.this.eBombs[4] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn01");
          CockpitPE3_1_TGunner.this.eBombs[5] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn02");
          CockpitPE3_1_TGunner.this.eBombs[6] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn03");
          CockpitPE3_1_TGunner.this.eBombs[7] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn04");
          CockpitPE3_1_TGunner.this.eBombs[8] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn05");
          CockpitPE3_1_TGunner.this.eBombs[9] = CockpitPE3_1_TGunner.this.aircraft().getBulletEmitterByHookName("_BombSpawn06");
        }
        for (int i = 0; i < 10; i++) {
          CockpitPE3_1_TGunner.this.bBombs[i] = (!CockpitPE3_1_TGunner.this.eBombs[i].haveBullets() ? 1 : 0);
        }

        CockpitPE3_1_TGunner.this.setNew.man1 = (0.92F * CockpitPE3_1_TGunner.this.setOld.man1 + 0.08F * CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure());

        CockpitPE3_1_TGunner.this.setNew.man2 = (0.92F * CockpitPE3_1_TGunner.this.setOld.man2 + 0.08F * CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getManifoldPressure());
        CockpitPE3_1_TGunner.this.setNew.altimeter = CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();

        CockpitPE3_1_TGunner.this.setNew.azimuth.setDeg(CockpitPE3_1_TGunner.this.setOld.azimuth.getDeg(1.0F), CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());

        CockpitPE3_1_TGunner.this.setNew.vspeed = ((100.0F * CockpitPE3_1_TGunner.this.setOld.vspeed + CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 101.0F);

        float f = CockpitPE3_1_TGunner.this.waypointAzimuth();

        CockpitPE3_1_TGunner.this.setNew.waypointAzimuth.setDeg(CockpitPE3_1_TGunner.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitPE3_1_TGunner.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));

        CockpitPE3_1_TGunner.this.setNew.waypointDirection.setDeg(CockpitPE3_1_TGunner.this.setOld.waypointDirection.getDeg(1.0F), f);

        CockpitPE3_1_TGunner.this.setNew.inert = (0.999F * CockpitPE3_1_TGunner.this.setOld.inert + 0.001F * (CockpitPE3_1_TGunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6 ? 0.867F : 0.0F));
      }

      return true;
    }
  }

  private class Variables
  {
    float man1;
    float man2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork waypointDirection;
    float vspeed;
    float inert;
    private final CockpitPE3_1_TGunner this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDirection = new AnglesFork();
    }

    Variables(CockpitPE3_1_TGunner.1 arg2)
    {
      this();
    }
  }
}