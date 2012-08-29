package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitCR42 extends CockpitPilot
{
  private Gun[] gun = { null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float pictFlap = 0.0F;
  private int delay = 80;
  private boolean sightDamaged = false;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 38.0F, 76.5F, 116.0F, 156.0F, 195.0F, 234.0F, 271.0F, 308.5F, 326.0F };

  private static final float[] oilScale = { 0.0F, 36.5F, 53.5F, 103.0F, 194.5F, 332.0F };

  public CockpitCR42()
  {
    super("3DO/Cockpit/CR42/hier.him", "u2");

    this.cockpitNightMats = new String[] { "z_clocks", "z_clocks2", "z_clocks3", "z_clocks4", "z_clocks5" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    if (((CR_42)aircraft()).bChangedPit) {
      reflectPlaneToModel();
      ((CR_42)aircraft()).bChangedPit = false;
    }
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
    }

    this.mesh.chunkSetAngles("Z_Column", 4.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 2.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[0] != 0) {
      xyz[2] = -0.01115F;
    }
    this.mesh.chunkSetLocate("Z_Column_but", xyz, ypr);

    this.mesh.chunkSetAngles("Pedals", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_trossL", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_trossR", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle", 100.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSwitch", 27.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mix", 100.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PropPitch1", 90.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Flap", -180.0F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.fm.CT.FlapsControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim", 1444.0F * this.fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = (0.015F * this.fm.CT.getTrimAileronControl());
    this.mesh.chunkSetLocate("Z_Trim1", xyz, ypr);

    this.mesh.chunkSetAngles("Z_OilRad", 70.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OverPres", 70.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 500.0F, 0.0F, 10.0F), speedometerScale), 0.0F, 0.0F);

    float f = interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat);
    if (f < 5000.0F)
      this.mesh.chunkSetAngles("Z_Altimeter1", cvt(f, 0.0F, 5000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Altimeter1", cvt(f, 5000.0F, 11000.0F, 360.0F, 720.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 5000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_EngTemp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 150.0F, 0.0F, 305.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilTemp1", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 150.0F, 0.0F, 5.0F), oilScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPress1", cvt(this.fm.M.fuel > 1.0F ? 3.0F : 0.0F, 0.0F, 6.0F, 0.0F, 297.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_FuelQuantity1", cvt(this.fm.M.fuel, 0.0F, 252.00002F, 0.0F, -316.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    if (this.gun[0] != null) this.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(this.gun[0].countBullets(), 0.0F, 600.0F, 0.0F, 338.0F), 0.0F, 0.0F);
    if (this.gun[1] != null) this.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(this.gun[1].countBullets(), 0.0F, 600.0F, 0.0F, 338.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Turn1", cvt(this.setNew.turn, -0.2F, 0.2F, 35.0F, -35.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -16.4F, 16.4F), 0.0F);

    this.mesh.chunkSetAngles("Z_ManfoldPress", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, 330.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_gunsight_rim", 50.0F * this.setNew.stbyPosition, 0.0F, 0.0F);
  }

  public void doToggleAim(boolean paramBoolean)
  {
    super.doToggleAim(paramBoolean);
    if ((paramBoolean) && (this.sightDamaged))
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(true);
      localHookPilot.setAim(new Point3d(-1.5D, 0.0D, 0.7297000288963318D));
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("glass", false);
      this.mesh.chunkVisible("glass_d1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);

      this.mesh.chunkVisible("gunsight_lense", false);
      this.mesh.chunkVisible("D_gunsight_lense", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("panel", false);
      this.mesh.chunkVisible("panel_d1", true);
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Turn1", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XHullDamage3", true);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.mesh.chunkVisible("WingLMid_D1", localHierMesh.isChunkVisible("WingLMid_D1"));
    this.mesh.chunkVisible("WingLMid_D2", localHierMesh.isChunkVisible("WingLMid_D2"));
    this.mesh.chunkVisible("WingLMid_D3", localHierMesh.isChunkVisible("WingLMid_D3"));
    this.mesh.chunkVisible("WingLMid_CAP", localHierMesh.isChunkVisible("WingLMid_CAP"));
    this.mesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
    this.mesh.chunkVisible("WingRMid_D1", localHierMesh.isChunkVisible("WingRMid_D1"));
    this.mesh.chunkVisible("WingRMid_D2", localHierMesh.isChunkVisible("WingRMid_D2"));
    this.mesh.chunkVisible("WingRMid_D3", localHierMesh.isChunkVisible("WingRMid_D3"));
    this.mesh.chunkVisible("WingRMid_CAP", localHierMesh.isChunkVisible("WingRMid_CAP"));
    this.mesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.mesh.materialReplace("Matt1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      setNightMats(true);
    }
    else
    {
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
      if (CockpitCR42.this.bNeedSetUp) {
        CockpitCR42.this.reflectPlaneMats();
        CockpitCR42.access$102(CockpitCR42.this, false);
      }
      if (((CR_42)CockpitCR42.this.aircraft()).bChangedPit) {
        CockpitCR42.this.reflectPlaneToModel();
        ((CR_42)CockpitCR42.this.aircraft()).bChangedPit = false;
      }

      CockpitCR42.access$202(CockpitCR42.this, CockpitCR42.this.setOld); CockpitCR42.access$302(CockpitCR42.this, CockpitCR42.this.setNew); CockpitCR42.access$402(CockpitCR42.this, CockpitCR42.this.setTmp);

      if (((CockpitCR42.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitCR42.this.setNew.stbyPosition < 1.0F))
      {
        CockpitCR42.access$510(CockpitCR42.this);
        if (CockpitCR42.this.delay <= 0)
        {
          CockpitCR42.this.setNew.stbyPosition = (CockpitCR42.this.setOld.stbyPosition + 0.03F);
          CockpitCR42.this.setOld.stbyPosition = CockpitCR42.this.setNew.stbyPosition;
          CockpitCR42.access$602(CockpitCR42.this, true);
        }
      }

      CockpitCR42.this.setNew.altimeter = CockpitCR42.this.fm.getAltitude();
      if (Math.abs(CockpitCR42.this.fm.Or.getKren()) < 30.0F) {
        CockpitCR42.this.setNew.azimuth = ((21.0F * CockpitCR42.this.setOld.azimuth + CockpitCR42.this.fm.Or.azimut()) / 22.0F);
      }
      if ((CockpitCR42.this.setOld.azimuth > 270.0F) && (CockpitCR42.this.setNew.azimuth < 90.0F)) CockpitCR42.this.setOld.azimuth -= 360.0F;
      if ((CockpitCR42.this.setOld.azimuth < 90.0F) && (CockpitCR42.this.setNew.azimuth > 270.0F)) CockpitCR42.this.setOld.azimuth += 360.0F;
      CockpitCR42.this.setNew.throttle = ((10.0F * CockpitCR42.this.setOld.throttle + CockpitCR42.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitCR42.this.setNew.mix = ((10.0F * CockpitCR42.this.setOld.mix + CockpitCR42.this.fm.EI.engines[0].getControlMix()) / 11.0F);
      CockpitCR42.this.setNew.prop = CockpitCR42.this.setOld.prop;
      if (CockpitCR42.this.setNew.prop < CockpitCR42.this.fm.EI.engines[0].getControlProp() - 0.01F) {
        CockpitCR42.this.setNew.prop += 0.0025F;
      }
      if (CockpitCR42.this.setNew.prop > CockpitCR42.this.fm.EI.engines[0].getControlProp() + 0.01F) {
        CockpitCR42.this.setNew.prop -= 0.0025F;
      }
      CockpitCR42.this.w.set(CockpitCR42.this.fm.getW());
      CockpitCR42.this.fm.Or.transform(CockpitCR42.this.w);
      CockpitCR42.this.setNew.turn = ((12.0F * CockpitCR42.this.setOld.turn + CockpitCR42.this.w.z) / 13.0F);
      CockpitCR42.this.setNew.vspeed = ((299.0F * CockpitCR42.this.setOld.vspeed + CockpitCR42.this.fm.getVertSpeed()) / 300.0F);

      CockpitCR42.access$802(CockpitCR42.this, 0.8F * CockpitCR42.this.pictSupc + 0.2F * CockpitCR42.this.fm.EI.engines[0].getControlCompressor());

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
    float stbyPosition;
    private final CockpitCR42 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitCR42.1 arg2) { this();
    }
  }
}