package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitI_153 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private LightPointActor[] lights = { null, null, null, null };

  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 45.0F, 75.5F, 107.0F, 137.5F, 170.0F, 206.5F, 243.75F, 286.5F, 329.5F, 374.5F };

  private static final float[] rpmScale = { 0.0F, 5.5F, 18.5F, 59.0F, 99.5F, 134.5F, 165.75F, 198.0F, 228.0F, 255.5F, 308.0F, 345.0F };

  private static final float[] manifoldScale = { 0.0F, 0.0F, 0.0F, 0.0F, 26.0F, 52.0F, 79.0F, 106.0F, 132.0F, 160.0F, 185.0F, 208.0F, 235.0F, 260.0F, 286.0F, 311.0F, 336.0F };

  public CockpitI_153()
  {
    super("3DO/Cockpit/I-153/hier.him", "u2");

    for (int i = 0; i < 4; i++) {
      HookNamed localHookNamed = new HookNamed(this.mesh, "_light0" + (i + 1));
      Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
      this.lights[i] = new LightPointActor(new LightPoint(), localLoc.getPoint());
      this.lights[i].light.setColor(0.8980392F, 0.8117647F, 0.6235294F);
      this.lights[i].light.setEmit(0.0F, 0.0F);
      this.pos.base().draw.lightMap().put("_light0" + (i + 1), this.lights[i]);
    }

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("crank", 40.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("cr_tross", 0.0F, 0.0F, -40.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat));
    this.mesh.chunkSetAngles("handle", 65.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("hand_tross", 0.0F, 0.0F, -65.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    this.mesh.chunkSetAngles("lever", -50.0F * this.pictSupc, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("lev_tross", 0.0F, 0.0F, 50.0F * this.pictSupc);
    this.mesh.chunkSetAngles("magto", 44.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("prop", -2160.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Stick", 0.0F, 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 10.2F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Ped_trossL", -15.65F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_trossR", -15.65F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 600.0F, 0.0F, 12.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 40.0F, -40.0F), 0.0F);
    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x4) == 0) {
      this.mesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 2400.0F, 0.0F, 11.0F), rpmScale), 0.0F);
    }

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.setNew.turn, -0.2F, 0.2F, 26.0F, -26.0F), 0.0F);
    this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 26.0F, -26.0F), 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 8.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zTwater1a", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 300.0F, 0.0F, -60.0F), 0.0F);

    this.mesh.chunkSetAngles("zManifold1a", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3.0F, 16.0F), manifoldScale), 0.0F);

    this.mesh.chunkSetAngles("Fire1", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[0] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Fire2", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[2] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Fire3", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Fire4", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[3] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Fire5", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[1] != 0 ? -15.0F : 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("pricel", false);
      this.mesh.chunkVisible("pricel_d1", true);
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_dd", true);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("zTOilOut1a", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
      this.mesh.chunkVisible("zGasPrs1a", false);
      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zManifold1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_dd", true);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zSlide1a", false);
      this.mesh.chunkVisible("zTwater1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0)));
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.mesh.chunkVisible("WingLMid_D1", localHierMesh.isChunkVisible("WingLMid_D1"));
    this.mesh.chunkVisible("WingLMid_D2", localHierMesh.isChunkVisible("WingLMid_D2"));
    this.mesh.chunkVisible("WingLMid_D3", localHierMesh.isChunkVisible("WingLMid_D3"));
    this.mesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
    this.mesh.chunkVisible("WingRMid_D1", localHierMesh.isChunkVisible("WingRMid_D1"));
    this.mesh.chunkVisible("WingRMid_D2", localHierMesh.isChunkVisible("WingRMid_D2"));
    this.mesh.chunkVisible("WingRMid_D3", localHierMesh.isChunkVisible("WingRMid_D3"));
    this.mesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
    this.mesh.chunkVisible("CF_D3", localHierMesh.isChunkVisible("CF_D3"));
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);

    if ((aircraft() instanceof I_153P))
      this.mesh.chunkVisible("guns_down", false);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    sfxClick(1);
    int i;
    if (this.cockpitLightControl) {
      for (i = 0; i < 4; i++)
        this.lights[i].light.setEmit(0.5F, 0.5F);
    }
    else
      for (i = 0; i < 4; i++)
        this.lights[i].light.setEmit(0.0F, 0.0F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitI_153.this.bNeedSetUp) {
        CockpitI_153.this.reflectPlaneMats();
        CockpitI_153.access$102(CockpitI_153.this, false);
      }
      ((I_153_M62)CockpitI_153.this.aircraft()); if (I_153_M62.bChangedPit) {
        CockpitI_153.this.reflectPlaneToModel();
        ((I_153_M62)CockpitI_153.this.aircraft()); I_153_M62.bChangedPit = false;
      }

      CockpitI_153.access$202(CockpitI_153.this, CockpitI_153.this.setOld); CockpitI_153.access$302(CockpitI_153.this, CockpitI_153.this.setNew); CockpitI_153.access$402(CockpitI_153.this, CockpitI_153.this.setTmp);

      CockpitI_153.this.setNew.altimeter = CockpitI_153.this.fm.getAltitude();
      if (Math.abs(CockpitI_153.this.fm.Or.getKren()) < 30.0F) {
        CockpitI_153.this.setNew.azimuth = ((21.0F * CockpitI_153.this.setOld.azimuth + CockpitI_153.this.fm.Or.azimut()) / 22.0F);
      }
      if ((CockpitI_153.this.setOld.azimuth > 270.0F) && (CockpitI_153.this.setNew.azimuth < 90.0F)) CockpitI_153.this.setOld.azimuth -= 360.0F;
      if ((CockpitI_153.this.setOld.azimuth < 90.0F) && (CockpitI_153.this.setNew.azimuth > 270.0F)) CockpitI_153.this.setOld.azimuth += 360.0F;
      CockpitI_153.this.setNew.throttle = ((10.0F * CockpitI_153.this.setOld.throttle + CockpitI_153.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitI_153.this.setNew.mix = ((10.0F * CockpitI_153.this.setOld.mix + CockpitI_153.this.fm.EI.engines[0].getControlMix()) / 11.0F);
      CockpitI_153.this.setNew.prop = CockpitI_153.this.setOld.prop;
      if (CockpitI_153.this.setNew.prop < CockpitI_153.this.fm.EI.engines[0].getControlProp() - 0.01F) {
        CockpitI_153.this.setNew.prop += 0.0025F;
      }
      if (CockpitI_153.this.setNew.prop > CockpitI_153.this.fm.EI.engines[0].getControlProp() + 0.01F) {
        CockpitI_153.this.setNew.prop -= 0.0025F;
      }
      CockpitI_153.this.w.set(CockpitI_153.this.fm.getW());
      CockpitI_153.this.fm.Or.transform(CockpitI_153.this.w);
      CockpitI_153.this.setNew.turn = ((12.0F * CockpitI_153.this.setOld.turn + CockpitI_153.this.w.z) / 13.0F);
      CockpitI_153.this.setNew.vspeed = ((299.0F * CockpitI_153.this.setOld.vspeed + CockpitI_153.this.fm.getVertSpeed()) / 300.0F);

      CockpitI_153.access$602(CockpitI_153.this, 0.8F * CockpitI_153.this.pictSupc + 0.2F * CockpitI_153.this.fm.EI.engines[0].getControlCompressor());

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float azimuth;
    float throttle;
    float mix;
    float prop;
    float turn;
    float vspeed;
    private final CockpitI_153 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitI_153.1 arg2) { this();
    }
  }
}