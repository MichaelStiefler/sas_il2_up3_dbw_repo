package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitF4F4 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float prevGearC;
  private LightPointActor light1;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 15.5F, 77.0F, 175.0F, 275.0F, 360.0F, 412.0F, 471.0F, 539.0F, 610.5F, 669.75F, 719.0F };

  private static final float[] variometerScale = { -175.5F, -160.5F, -145.5F, -128.0F, -100.0F, -65.5F, 0.0F, 65.5F, 100.0F, 128.0F, 145.5F, 160.5F, 175.5F };

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Blister1_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (this.fm.AS.astateBailoutStep < 2) {
      aircraft().hierMesh().chunkVisible("Blister1_D0", true);
    }
    super.doFocusLeave();
  }

  public CockpitF4F4()
  {
    super("3DO/Cockpit/F4F-4/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "DGP_02", "DGP_03", "DGP_04", "GP_01", "GP_02", "GP_03", "GP_04", "GP_05", "GP_06", "GP_07" };

    setNightMats(false);

    this.light1 = new LightPointActor(new LightPoint(), new Point3d(-0.8D, 0.0D, 1.0D));
    this.light1.light.setColor(232.0F, 75.0F, 44.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

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
    ((F4F)aircraft()); if (F4F.bChangedPit) {
      reflectPlaneToModel();
      ((F4F)aircraft()); F4F.bChangedPit = false;
    }

    float f = this.fm.CT.getWing();
    this.mesh.chunkSetAngles("WingLMid_D0", 0.0F, -110.0F * f, 0.0F);
    this.mesh.chunkSetAngles("WingRMid_D0", 0.0F, -110.0F * f, 0.0F);
    f = -50.0F * this.fm.CT.getFlap();
    this.mesh.chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    this.mesh.chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.69F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);
    this.mesh.chunkSetLocate("XGlassDamage2", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Trim1", 0.0F, 722.0F * this.fm.CT.getTrimAileronControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 0.0F, 116.0F * this.fm.CT.getTrimRudderControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 0.0F, -722.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -168.0F * this.fm.CT.FlapsControl, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 0.0F, 15840.0F * interp(this.setNew.gCrankAngle, this.setOld.gCrankAngle, paramFloat) % 360.0F, 0.0F);
    if (this.fm.CT.GearControl > this.prevGearC)
      this.mesh.chunkSetAngles("Z_Gear2", 0.0F, 62.0F, 0.0F);
    else if (this.fm.CT.GearControl < this.prevGearC) {
      this.mesh.chunkSetAngles("Z_Gear2", 0.0F, 0.0F, 0.0F);
    }
    this.prevGearC = this.fm.CT.GearControl;
    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 43.400002F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F);
    resetYPRmodifier();
    xyz[1] = (0.065F * this.setNew.prop);
    this.mesh.chunkSetLocate("Z_Prop1", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, 41.25F * cvt(this.fm.EI.engines[0].getControlMix(), 1.0F, 1.2F, 0.55F, 1.15F), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, -34.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal1", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal2", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);

    this.mesh.chunkSetAngles("Z_LeftPedal1", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal2", 0.0F, -20.0F * this.fm.CT.getRudder(), 0.0F);

    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F);
    this.mesh.chunkSetAngles("Z_Hook1", 0.0F, -60.0F * this.fm.CT.arrestorControl, 0.0F);
    this.mesh.chunkSetAngles("Z_CowlFlaps", 0.0F, 90.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 796.3598F, 0.0F, 11.0F), speedometerScale), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -10.0F, 10.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -0.0285F, 0.0285F);
    this.mesh.chunkSetLocate("Z_TurnBank4", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -90.0F + this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, 296.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 4.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, -180.0F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 1.693189F, 0.0F, 342.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 295.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_AirTemp1", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -50.0F, 50.0F, -45.0F, 45.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_CylTemp1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 20.0F, 120.0F, 0.0F, 78.5F), 0.0F);
    resetYPRmodifier();
    xyz[1] = (0.0301F * this.fm.CT.getGear());
    this.mesh.chunkSetLocate("Z_GearInd1", xyz, ypr);
    resetYPRmodifier();
    if (this.fm.Gears.bTailwheelLocked) {
      xyz[1] = -0.05955F;
    }
    this.mesh.chunkSetLocate("Z_TWheel_Lock", xyz, ypr);
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
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_CylTemp1", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_AirTemp1", false);
      this.mesh.chunkVisible("Z_Fuel1", false);
      this.mesh.chunkVisible("XHullDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XHullDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XHullDamage3", true);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
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
    if (this.cockpitLightControl) {
      setNightMats(true);
      this.light1.light.setEmit(0.005F, 1.0F);
    } else {
      setNightMats(false);
      this.light1.light.setEmit(0.0F, 0.0F);
    }
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
    boolean sfxPlaying = false;

    Interpolater() {  }

    public boolean tick() { if (CockpitF4F4.this.fm != null) {
        CockpitF4F4.access$102(CockpitF4F4.this, CockpitF4F4.this.setOld); CockpitF4F4.access$202(CockpitF4F4.this, CockpitF4F4.this.setNew); CockpitF4F4.access$302(CockpitF4F4.this, CockpitF4F4.this.setTmp);

        CockpitF4F4.this.setNew.throttle = (0.85F * CockpitF4F4.this.setOld.throttle + CockpitF4F4.this.fm.CT.PowerControl * 0.15F);
        CockpitF4F4.this.setNew.prop = (0.85F * CockpitF4F4.this.setOld.prop + CockpitF4F4.this.fm.CT.getStepControl() * 0.15F);
        CockpitF4F4.this.setNew.mix = (0.85F * CockpitF4F4.this.setOld.mix + CockpitF4F4.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitF4F4.this.setNew.altimeter = CockpitF4F4.this.fm.getAltitude();
        float f = CockpitF4F4.this.waypointAzimuth(10.0F);
        CockpitF4F4.this.setNew.azimuth.setDeg(CockpitF4F4.this.setOld.azimuth.getDeg(1.0F), CockpitF4F4.this.fm.Or.azimut());
        CockpitF4F4.this.setNew.waypointAzimuth.setDeg(CockpitF4F4.this.setOld.waypointAzimuth.getDeg(0.1F), f);
        CockpitF4F4.this.setNew.vspeed = ((199.0F * CockpitF4F4.this.setOld.vspeed + CockpitF4F4.this.fm.getVertSpeed()) / 200.0F);
      }

      boolean bool = false;

      if (CockpitF4F4.this.setNew.gCrankAngle < CockpitF4F4.this.fm.CT.getGear() - 0.05F) {
        if ((Math.abs(CockpitF4F4.this.setNew.gCrankAngle - CockpitF4F4.this.fm.CT.getGear()) > 0.05F) && (Math.abs(CockpitF4F4.this.setNew.gCrankAngle - CockpitF4F4.this.fm.CT.getGear()) < 0.9F)) {
          CockpitF4F4.this.setNew.gCrankAngle += 0.0025F;
          bool = true;
        } else {
          CockpitF4F4.this.setNew.gCrankAngle = CockpitF4F4.this.fm.CT.getGear();
          CockpitF4F4.this.setOld.gCrankAngle = CockpitF4F4.this.fm.CT.getGear();
        }
      }
      if (CockpitF4F4.this.setNew.gCrankAngle > CockpitF4F4.this.fm.CT.getGear() + 0.05F) {
        if ((Math.abs(CockpitF4F4.this.setNew.gCrankAngle - CockpitF4F4.this.fm.CT.getGear()) > 0.05F) && (Math.abs(CockpitF4F4.this.setNew.gCrankAngle - CockpitF4F4.this.fm.CT.getGear()) < 0.9F)) {
          CockpitF4F4.this.setNew.gCrankAngle -= 0.0025F;
          bool = true;
        } else {
          CockpitF4F4.this.setNew.gCrankAngle = CockpitF4F4.this.fm.CT.getGear();
          CockpitF4F4.this.setOld.gCrankAngle = CockpitF4F4.this.fm.CT.getGear();
        }
      }
      if (bool != this.sfxPlaying) {
        if (bool) CockpitF4F4.this.sfxStart(16); else
          CockpitF4F4.this.sfxStop(16);
        this.sfxPlaying = bool;
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
    float gCrankAngle;
    private final CockpitF4F4 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitF4F4.1 arg2)
    {
      this();
    }
  }
}