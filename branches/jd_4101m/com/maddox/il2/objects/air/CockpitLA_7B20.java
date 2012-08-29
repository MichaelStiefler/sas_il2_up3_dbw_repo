package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Time;

public class CockpitLA_7B20 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private long t1 = 0L;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictBlinker = 0.0F;
  private float pictStage = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  private static final float[] manifoldScale = { 0.0F, 0.0F, 0.0F, 0.0F, 26.0F, 52.0F, 79.0F, 106.0F, 132.0F, 160.0F, 185.0F, 208.0F, 235.0F, 260.0F, 286.0F, 311.0F, 336.0F };

  private static final float[] variometerScale = { -180.0F, -90.0F, -45.0F, 0.0F, 45.0F, 90.0F, 180.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(5.0F);
  }

  public CockpitLA_7B20()
  {
    super("3DO/Cockpit/La-7B20/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Prib_One", "Prib_Two", "Prib_Three", "Prib_Four", "Prib_Five", "Shkala128" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm.CT.getStepControlAuto())
      this.mesh.chunkSetAngles("PropPitchHandle", -70.0F + 70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("PropPitchHandle", -70.0F + 70.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("TQHandle", -54.545456F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("MixtureHandle", 50.0F * this.fm.EI.engines[0].getControlMix(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ChargerHandle", this.fm.EI.engines[0].getStage() < 4 ? 0.0F : 70.0F - 60.0F * this.pictStage, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("CStick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", -this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", -this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Tross_L", 0.0F, this.fm.CT.getRudder() * 15.65F, 0.0F);
    this.mesh.chunkSetAngles("Tross_R", 0.0F, this.fm.CT.getRudder() * 15.65F, 0.0F);

    this.mesh.chunkSetAngles("IgnitionSwitch", 0.0F, -40.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F);

    this.mesh.chunkSetAngles("SW_LandLight", this.fm.AS.bLandingLightOn ? 60.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SW_UVLight", this.cockpitLightControl ? 60.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SW_Radio", 60.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SW_NavLight", this.fm.AS.bNavLightsOn ? 60.0F : 0.0F, 0.0F, 0.0F);

    resetYPRmodifier();
    xyz[2] = (0.01F * this.pictBlinker);
    this.mesh.chunkSetLocate("zBlinkerUp", xyz, ypr);
    this.mesh.chunkSetLocate("zBlinkerDn", xyz, ypr);

    resetYPRmodifier();
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("GearHandle", -45.0F, 0.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("GearHandle", 45.0F, 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 0.0F);
    }
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("FlapHandle", 30.0F, 0.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("FlapHandle", -30.0F, 0.0F, 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("zAlt1a", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 40.0F, -40.0F));
    this.mesh.chunkSetAngles("zAzimuth1b", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.setOld.xyz[1] = cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 0.03F, -0.03F);
    this.mesh.chunkSetLocate("zHorizon1a", this.setOld.xyz, this.setOld.ypr);
    this.mesh.chunkSetAngles("zHorizon1b", this.fm.Or.getKren(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zManifold1a", floatindex(cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3.0F, 16.0F), manifoldScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zGas1a", cvt(this.fm.M.fuel / 0.725F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCylHead", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 300.0F, 0.0F, 70.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurn1a", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", -cvt(getBall(8.0D), -8.0F, 8.0F, 25.0F, -25.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zTOilOut1a", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOilPrs1a", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zGasPrs1a", cvt(this.fm.M.fuel > 1.0F ? cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3050.0F, 0.0F, 4.0F) : 0.0F, 0.0F, 8.0F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", floatindex(cvt(this.setNew.vspeed, -30.0F, 30.0F, 0.0F, 6.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM1a", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM1b", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("zClock1a", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zClock1b", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zRPK10", cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.2F), -25.0F, 25.0F, -35.0F, 35.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkVisible("XGearUP_L", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XGearUP_R", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XGearDown_L", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XGearDown_R", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XGearDown_C", this.fm.CT.getGear() == 1.0F);

    if (this.t1 < Time.current()) {
      BulletEmitter localBulletEmitter = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
      if (localBulletEmitter != GunEmpty.get())
        this.mesh.chunkVisible("XBombOnboard_L", localBulletEmitter.haveBullets());
      else {
        this.mesh.chunkVisible("XBombOnboard_L", false);
      }
      localBulletEmitter = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb02");
      if (localBulletEmitter != GunEmpty.get())
        this.mesh.chunkVisible("XBombOnboard_R", localBulletEmitter.haveBullets());
      else {
        this.mesh.chunkVisible("XBombOnboard_R", false);
      }
      this.t1 = (Time.current() + 500L);
    }
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.materialReplace("Prib_One", "DPrib_One");
      this.mesh.materialReplace("Prib_One_night", "DPrib_One_night");
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zRPM1b", false);
      this.mesh.chunkVisible("zTOilOut1a", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
      this.mesh.chunkVisible("zGasPrs1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.materialReplace("Prib_Two", "DPrib_Two");
      this.mesh.materialReplace("Prib_Two_night", "DPrib_Two_night");
      this.mesh.chunkVisible("zManifold1a", false);
      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zGas1a", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zSlide1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.materialReplace("Prib_Three", "DPrib_Three");
      this.mesh.materialReplace("Prib_Three_night", "DPrib_Three_night");
      this.mesh.chunkVisible("zHorizon1a", false);
      this.mesh.chunkVisible("zHorizon1b", false);
      this.mesh.materialReplace("Prib_Four", "DPrib_Four");
      this.mesh.materialReplace("Prib_Four_night", "DPrib_Four_night");
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0))
    {
      this.mesh.chunkVisible("PBP-1b", false);
      this.mesh.chunkVisible("PBP-1b_D0", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Z_Holes1_D0", true);
      this.mesh.chunkVisible("Z_Holes2_D0", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D0", true);
    }
    retoggleLight();
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
      if (CockpitLA_7B20.this.fm != null) {
        CockpitLA_7B20.access$102(CockpitLA_7B20.this, CockpitLA_7B20.this.setOld); CockpitLA_7B20.access$202(CockpitLA_7B20.this, CockpitLA_7B20.this.setNew); CockpitLA_7B20.access$302(CockpitLA_7B20.this, CockpitLA_7B20.this.setTmp);

        CockpitLA_7B20.this.setNew.throttle = ((10.0F * CockpitLA_7B20.this.setOld.throttle + CockpitLA_7B20.this.fm.CT.PowerControl) / 11.0F);
        CockpitLA_7B20.this.setNew.prop = ((8.0F * CockpitLA_7B20.this.setOld.prop + CockpitLA_7B20.this.fm.CT.getStepControl()) / 9.0F);
        CockpitLA_7B20.this.setNew.altimeter = CockpitLA_7B20.this.fm.getAltitude();
        if (Math.abs(CockpitLA_7B20.this.fm.Or.getKren()) < 30.0F) {
          CockpitLA_7B20.this.setNew.azimuth = ((35.0F * CockpitLA_7B20.this.setOld.azimuth + -CockpitLA_7B20.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitLA_7B20.this.setOld.azimuth > 270.0F) && (CockpitLA_7B20.this.setNew.azimuth < 90.0F)) CockpitLA_7B20.this.setOld.azimuth -= 360.0F;
        if ((CockpitLA_7B20.this.setOld.azimuth < 90.0F) && (CockpitLA_7B20.this.setNew.azimuth > 270.0F)) CockpitLA_7B20.this.setOld.azimuth += 360.0F;

        if (CockpitLA_7B20.this.useRealisticNavigationInstruments())
        {
          CockpitLA_7B20.this.setNew.waypointAzimuth.setDeg(CockpitLA_7B20.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitLA_7B20.this.getBeaconDirection());
        }
        else
        {
          CockpitLA_7B20.this.setNew.waypointAzimuth.setDeg(CockpitLA_7B20.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitLA_7B20.this.waypointAzimuth() - CockpitLA_7B20.this.fm.Or.azimut());
        }

        CockpitLA_7B20.this.setNew.vspeed = ((199.0F * CockpitLA_7B20.this.setOld.vspeed + CockpitLA_7B20.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitLA_7B20.this.fm.getAltitude() > 3000.0F) {
          float f = (float)Math.sin(1.0F * CockpitLA_7B20.this.cvt(CockpitLA_7B20.this.fm.getOverload(), 1.0F, 8.0F, 1.0F, 0.45F) * CockpitLA_7B20.this.cvt(CockpitLA_7B20.this.fm.AS.astatePilotStates[0], 0.0F, 100.0F, 1.0F, 0.1F) * (0.001F * (float)Time.current()));
          if (f > 0.0F) {
            CockpitLA_7B20.access$416(CockpitLA_7B20.this, 0.3F);
            if (CockpitLA_7B20.this.pictBlinker > 1.0F)
              CockpitLA_7B20.access$402(CockpitLA_7B20.this, 1.0F);
          }
          else {
            CockpitLA_7B20.access$424(CockpitLA_7B20.this, 0.3F);
            if (CockpitLA_7B20.this.pictBlinker < 0.0F) {
              CockpitLA_7B20.access$402(CockpitLA_7B20.this, 0.0F);
            }
          }
        }
        CockpitLA_7B20.access$502(CockpitLA_7B20.this, 0.8F * CockpitLA_7B20.this.pictStage + 0.1F * CockpitLA_7B20.this.fm.EI.engines[0].getControlCompressor());
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    AnglesFork waypointAzimuth;
    float[] xyz;
    float[] ypr;
    private final CockpitLA_7B20 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.waypointAzimuth = new AnglesFork();
      this.xyz = new float[] { 0.0F, 0.0F, 0.0F };
      this.ypr = new float[] { 0.0F, 0.0F, 0.0F };
    }

    Variables(CockpitLA_7B20.1 arg2)
    {
      this();
    }
  }
}