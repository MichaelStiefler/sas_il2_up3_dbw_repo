package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitF6F5 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 17.0F, 56.5F, 107.5F, 157.0F, 204.0F, 220.5F, 238.5F, 256.5F, 274.5F, 293.0F, 311.0F, 330.0F, 342.0F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  public CockpitF6F5()
  {
    super("3DO/Cockpit/F6F-5/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Image11_Damage", "Image11", "Image12_Damage", "Image12", "Image14", "Image27_Damage", "Image27", "Image09" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }

    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    ((F6F)aircraft()); if (F6F.bChangedPit) {
      reflectPlaneToModel();
      ((F6F)aircraft()); F6F.bChangedPit = false;
    }

    float f = this.fm.CT.getWing();
    this.mesh.chunkSetAngles("WingLMid_D0", 100.0F * f, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("WingRMid_D0", -100.0F * f, 0.0F, 0.0F);
    f = -50.0F * this.fm.CT.getFlap();
    this.mesh.chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.625F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Trim2", 180.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 180.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 90.0F * this.fm.CT.FlapsControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 70.0F * this.fm.CT.GearControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 50.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 0.0F, 82.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, 50.0F * this.fm.EI.engines[0].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_L", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal1_L", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal2_L", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal1_R", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal2_R", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 7.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, 25.0F - 25.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_CowlFlap1", 0.0F, -70.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);
    this.mesh.chunkSetAngles("Z_OilCooler1", 0.0F, -70.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 963.03973F, 0.0F, 13.0F), speedometerScale), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(7.0D), -7.0F, 7.0F, 16.0F, -16.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.025F, -0.025F);
    this.mesh.chunkSetLocate("Z_TurnBank4", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 270.0F + this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 700.0F, 0.0F, 74.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 343.75F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oxy1", 120.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Coolant1", cvt(this.fm.EI.engines[0].tWaterOut, 40.0F, 160.0F, 0.0F, 120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 295.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AmmoCtr1", cvt(aircraft().getGunByHookName("_MGUN01").countBullets(), -500.0F, 1200.0F, -103.0F, 235.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AmmoCtr2", cvt(aircraft().getGunByHookName("_MGUN02").countBullets(), -500.0F, 1200.0F, -103.0F, 235.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres", cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 4.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPres", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FlapInd1", cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    if (this.fm.Gears.lgear) {
      this.mesh.chunkSetAngles("Z_GearInd1", 0.0F, cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 65.0F), 0.0F);
    }
    if (this.fm.Gears.rgear) {
      this.mesh.chunkSetAngles("Z_GearInd2", 0.0F, cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, -65.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_GearInd3", 0.0F, cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_AirPres", cvt(12.4F, 0.0F, 20.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_HydroPres", cvt(this.fm.Gears.isHydroOperable() ? 7.5F : 0.0F, 0.0F, 20.0F, 0.0F, 180.0F), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Hour1", false);
      this.mesh.chunkVisible("Z_Minute1", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_Fuel1", false);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_OilPres", false);
      this.mesh.chunkVisible("Z_FuelPres", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x8) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0))));
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.mesh.materialReplace("Matt1D1o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLIn_D0", localHierMesh.isChunkVisible("WingLIn_D0"));
    this.mesh.chunkVisible("WingLIn_D1", localHierMesh.isChunkVisible("WingLIn_D1"));
    this.mesh.chunkVisible("WingLIn_D2", localHierMesh.isChunkVisible("WingLIn_D2"));
    this.mesh.chunkVisible("WingLIn_D3", localHierMesh.isChunkVisible("WingLIn_D3"));
    this.mesh.chunkVisible("WingRIn_D0", localHierMesh.isChunkVisible("WingRIn_D0"));
    this.mesh.chunkVisible("WingRIn_D1", localHierMesh.isChunkVisible("WingRIn_D1"));
    this.mesh.chunkVisible("WingRIn_D2", localHierMesh.isChunkVisible("WingRIn_D2"));
    this.mesh.chunkVisible("WingRIn_D3", localHierMesh.isChunkVisible("WingRIn_D3"));

    this.mesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.mesh.chunkVisible("WingLMid_D1", localHierMesh.isChunkVisible("WingLMid_D1"));
    this.mesh.chunkVisible("WingLMid_D2", localHierMesh.isChunkVisible("WingLMid_D2"));
    this.mesh.chunkVisible("WingLMid_D3", localHierMesh.isChunkVisible("WingLMid_D3"));
    this.mesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
    this.mesh.chunkVisible("WingRMid_D1", localHierMesh.isChunkVisible("WingRMid_D1"));
    this.mesh.chunkVisible("WingRMid_D2", localHierMesh.isChunkVisible("WingRMid_D2"));
    this.mesh.chunkVisible("WingRMid_D3", localHierMesh.isChunkVisible("WingRMid_D3"));

    this.mesh.chunkVisible("Flap01_D0", localHierMesh.isChunkVisible("Flap01_D0"));
    this.mesh.chunkVisible("Flap02_D0", localHierMesh.isChunkVisible("Flap02_D0"));
    this.mesh.chunkVisible("Flap03_D0", localHierMesh.isChunkVisible("Flap03_D0"));
    this.mesh.chunkVisible("Flap04_D0", localHierMesh.isChunkVisible("Flap04_D0"));
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight() {
    if (this.cockpitLightControl) {
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
      if (CockpitF6F5.this.fm != null) {
        CockpitF6F5.access$102(CockpitF6F5.this, CockpitF6F5.this.setOld); CockpitF6F5.access$202(CockpitF6F5.this, CockpitF6F5.this.setNew); CockpitF6F5.access$302(CockpitF6F5.this, CockpitF6F5.this.setTmp);

        CockpitF6F5.this.setNew.throttle = (0.85F * CockpitF6F5.this.setOld.throttle + CockpitF6F5.this.fm.CT.PowerControl * 0.15F);
        CockpitF6F5.this.setNew.prop = (0.85F * CockpitF6F5.this.setOld.prop + CockpitF6F5.this.fm.CT.getStepControl() * 0.15F);
        CockpitF6F5.this.setNew.mix = (0.85F * CockpitF6F5.this.setOld.mix + CockpitF6F5.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitF6F5.this.setNew.altimeter = CockpitF6F5.this.fm.getAltitude();
        float f = CockpitF6F5.this.waypointAzimuth(10.0F);
        CockpitF6F5.this.setNew.azimuth.setDeg(CockpitF6F5.this.setOld.azimuth.getDeg(1.0F), CockpitF6F5.this.fm.Or.azimut());
        CockpitF6F5.this.setNew.waypointAzimuth.setDeg(CockpitF6F5.this.setOld.waypointAzimuth.getDeg(0.1F), f);
        CockpitF6F5.this.setNew.vspeed = ((199.0F * CockpitF6F5.this.setOld.vspeed + CockpitF6F5.this.fm.getVertSpeed()) / 200.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    private final CockpitF6F5 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitF6F5.1 arg2)
    {
      this();
    }
  }
}