package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitJ8A extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float pictFlap = 0.0F;
  private float pictManifold;

  public CockpitJ8A()
  {
    super("3DO/Cockpit/J8A/hier.him", "u2");

    this.cockpitNightMats = new String[] { "PRIB_01", "PRIB_02", "PRIB_03", "PRIB_04", "PRIB_05", "PRIB_06", "PRIB_07" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    if (((GLADIATOR1J8A)aircraft()).bChangedPit) {
      reflectPlaneToModel();
      ((GLADIATOR1J8A)aircraft()).bChangedPit = false;
    }

    this.mesh.chunkSetAngles("Z_ReVi", 0.0F, 90.0F * this.setNew.stbyPosition, 0.0F);

    this.mesh.chunkSetAngles("Z_ManPrs1", 0.0F, 0.0F, 4.0F - cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.fm.EI.engines[0].getManifoldPressure(), 0.600034F, 1.66661F, -124.8F, 208.0F));

    this.mesh.chunkSetAngles("Flap01_D0", 0.0F, -50.0F * this.fm.CT.getFlap(), 0.0F);
    this.mesh.chunkSetAngles("Flap04_D0", 0.0F, -50.0F * this.fm.CT.getFlap(), 0.0F);

    this.mesh.chunkSetAngles("Z_Column", 0.0F, 14.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 14.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("Z_PedalStrut", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle", 0.0F, 0.0F, -51.799999F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    this.mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, -52.299999F * interp(this.setNew.mix, this.setOld.mix, paramFloat));

    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 16000.0F, 0.0F, -720.0F));

    float f = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());
    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, 0.0F, cvt(f, 60.0F, 450.0F, 0.0F, -340.5F));

    this.mesh.chunkSetAngles("Z_Compass1", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 1200.0F, 3400.0F, 0.0F, -328.0F));

    this.mesh.chunkSetAngles("Z_OilPress1", 0.0F, 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 12.0F, 0.0F, -309.0F));

    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F));
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F));
    this.mesh.chunkSetAngles("Z_Second1", 0.0F, 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F));

    this.mesh.chunkSetAngles("Z_FuelPress1", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 282.0F, 0.0F, 71.0F));

    this.mesh.chunkSetAngles("Z_OilTemp1", 0.0F, 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 140.0F, 0.0F, -315.5F));

    resetYPRmodifier();

    xyz[0] = (-cvt(this.fm.Or.getTangage(), -20.0F, 20.0F, 0.042F, -0.042F));
    this.mesh.chunkSetLocate("Z_AoA1", xyz, ypr);

    this.mesh.chunkSetAngles("Turn1", 0.0F, 0.0F, cvt(this.setNew.turn, -0.2F, 0.2F, -22.5F, 22.5F));
    this.mesh.chunkSetAngles("Turn2", 0.0F, 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 16.9F, -16.9F));

    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, 0.0F, cvt(this.setNew.vspeed, -15.0F, 15.0F, 180.0F, -180.0F));

    this.mesh.chunkSetAngles("Z_Oxypres1", 0.0F, 0.0F, -260.0F);
    this.mesh.chunkSetAngles("Z_Oxypres2", 0.0F, 0.0F, -320.0F);

    Aircraft.xyz[0] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    Aircraft.xyz[1] = (this.fm.CT.getCockpitDoor() * -0.6F);
    this.mesh.chunkSetLocate("XGlassDamage3", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = (this.fm.CT.getCockpitDoor() * 0.6F);
    this.mesh.chunkSetLocate("Canopy", Aircraft.xyz, Aircraft.ypr);
    this.mesh.chunkSetLocate("CanopyGlass", Aircraft.xyz, Aircraft.ypr);
    this.mesh.chunkSetLocate("XGlassDamage2", Aircraft.xyz, Aircraft.ypr);

    if (this.fm.CT.bHasBrakeControl)
    {
      f = this.fm.CT.getBrake();

      this.mesh.chunkSetAngles("Z_brake_handle", f * 20.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_brake_pressR", cvt(f + f * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_brake_pressL", cvt(f - f * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_brake_press", -150.0F + f * 40.0F, 0.0F, 0.0F);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Revi_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x40) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x4) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x8) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x80) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0)))))));
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
    this.mesh.chunkVisible("WingLOut_D0", localHierMesh.isChunkVisible("WingLOut_D0"));
    this.mesh.chunkVisible("WingLOut_D1", localHierMesh.isChunkVisible("WingLOut_D1"));
    this.mesh.chunkVisible("WingLOut_D2", localHierMesh.isChunkVisible("WingLOut_D2"));
    this.mesh.chunkVisible("WingLOut_D3", localHierMesh.isChunkVisible("WingLOut_D3"));
    this.mesh.chunkVisible("WingROut_D0", localHierMesh.isChunkVisible("WingROut_D0"));
    this.mesh.chunkVisible("WingROut_D1", localHierMesh.isChunkVisible("WingROut_D1"));
    this.mesh.chunkVisible("WingROut_D2", localHierMesh.isChunkVisible("WingROut_D2"));
    this.mesh.chunkVisible("WingROut_D3", localHierMesh.isChunkVisible("WingROut_D3"));
    this.mesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
    this.mesh.chunkVisible("CF_D3", localHierMesh.isChunkVisible("CF_D3"));
    this.mesh.chunkVisible("WireL_D0", localHierMesh.isChunkVisible("WireL_D0"));
    this.mesh.chunkVisible("WireR_D0", localHierMesh.isChunkVisible("WireR_D0"));
    this.mesh.chunkVisible("Flap01_D0", localHierMesh.isChunkVisible("Flap01_D0"));
    this.mesh.chunkVisible("Flap04_D0", localHierMesh.isChunkVisible("Flap04_D0"));
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.mesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
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
      if (CockpitJ8A.this.bNeedSetUp) {
        CockpitJ8A.this.reflectPlaneMats();
        CockpitJ8A.access$102(CockpitJ8A.this, false);
      }
      if (((GLADIATOR1J8A)CockpitJ8A.this.aircraft()).bChangedPit) {
        CockpitJ8A.this.reflectPlaneToModel();
        ((GLADIATOR1J8A)CockpitJ8A.this.aircraft()).bChangedPit = false;
      }

      CockpitJ8A.access$202(CockpitJ8A.this, CockpitJ8A.this.setOld); CockpitJ8A.access$302(CockpitJ8A.this, CockpitJ8A.this.setNew); CockpitJ8A.access$402(CockpitJ8A.this, CockpitJ8A.this.setTmp);

      if (((CockpitJ8A.this.fm.AS.astateCockpitState & 0x2) != 0) && 
        (CockpitJ8A.this.setNew.stbyPosition < 1.0F)) {
        CockpitJ8A.this.setNew.stbyPosition = (CockpitJ8A.this.setOld.stbyPosition + 0.0125F);
        CockpitJ8A.this.setOld.stbyPosition = CockpitJ8A.this.setNew.stbyPosition;
      }

      CockpitJ8A.this.setNew.altimeter = CockpitJ8A.this.fm.getAltitude();
      if (Math.abs(CockpitJ8A.this.fm.Or.getKren()) < 30.0F) {
        CockpitJ8A.this.setNew.azimuth = ((21.0F * CockpitJ8A.this.setOld.azimuth + CockpitJ8A.this.fm.Or.azimut()) / 22.0F);
      }
      if ((CockpitJ8A.this.setOld.azimuth > 270.0F) && (CockpitJ8A.this.setNew.azimuth < 90.0F)) CockpitJ8A.this.setOld.azimuth -= 360.0F;
      if ((CockpitJ8A.this.setOld.azimuth < 90.0F) && (CockpitJ8A.this.setNew.azimuth > 270.0F)) CockpitJ8A.this.setOld.azimuth += 360.0F;
      CockpitJ8A.this.setNew.throttle = ((10.0F * CockpitJ8A.this.setOld.throttle + CockpitJ8A.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitJ8A.this.setNew.mix = ((10.0F * CockpitJ8A.this.setOld.mix + CockpitJ8A.this.fm.EI.engines[0].getControlMix()) / 11.0F);
      CockpitJ8A.this.setNew.prop = CockpitJ8A.this.setOld.prop;
      if (CockpitJ8A.this.setNew.prop < CockpitJ8A.this.fm.EI.engines[0].getControlProp() - 0.01F) {
        CockpitJ8A.this.setNew.prop += 0.0025F;
      }
      if (CockpitJ8A.this.setNew.prop > CockpitJ8A.this.fm.EI.engines[0].getControlProp() + 0.01F) {
        CockpitJ8A.this.setNew.prop -= 0.0025F;
      }
      CockpitJ8A.this.w.set(CockpitJ8A.this.fm.getW());
      CockpitJ8A.this.fm.Or.transform(CockpitJ8A.this.w);
      CockpitJ8A.this.setNew.turn = ((12.0F * CockpitJ8A.this.setOld.turn + CockpitJ8A.this.w.z) / 13.0F);
      CockpitJ8A.this.setNew.vspeed = ((299.0F * CockpitJ8A.this.setOld.vspeed + CockpitJ8A.this.fm.getVertSpeed()) / 300.0F);

      CockpitJ8A.access$602(CockpitJ8A.this, 0.8F * CockpitJ8A.this.pictSupc + 0.2F * CockpitJ8A.this.fm.EI.engines[0].getControlCompressor());

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
    private final CockpitJ8A this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitJ8A.1 arg2) { this();
    }
  }
}