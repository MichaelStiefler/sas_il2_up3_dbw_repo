package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
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
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Time;

public class CockpitI_185M71 extends CockpitPilot
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
  private float pictBDrop = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  private static final float[] manifoldScale = { 0.0F, 0.0F, 0.0F, 0.0F, 26.0F, 52.0F, 79.0F, 106.0F, 132.0F, 160.0F, 185.0F, 208.0F, 235.0F, 260.0F, 286.0F, 311.0F, 336.0F };

  private static final float[] variometerScale = { -180.0F, -90.0F, -45.0F, 0.0F, 45.0F, 90.0F, 180.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitI_185M71()
  {
    super("3DO/Cockpit/I-185M-71/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Prib_One", "Prib_Two", "Prib_Three", "Prib_Four", "Prib_Five" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.materialReplace("prib_three", "EmptyGauge");
      this.mesh.materialReplace("prib_three_night", "EmptyGauge_night");
      this.mesh.chunkVisible("zRPK10", false);
      setNightMats(true);
      setNightMats(false);
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkVisible("XGearDown_L", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XGearDown_C", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("XGearDown_R", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XGearUP_L", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XGearUP_R", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.rgear));

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

    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("FlapHandle", 5.0F, 0.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("FlapHandle", -5.0F, 0.0F, 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 0.0F);
    }
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("GearHandle", -16.0F, 0.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("GearHandle", 16.0F, 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("IgnitionSwitch", -23.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    if (this.fm.EI.engines[0].getStage() == 0) {
      this.mesh.chunkSetAngles("IgnitionSwitch", 0.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("TQHandle", 16.299999F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("MixtureHandle", -18.299999F * this.fm.EI.engines[0].getControlMix(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PropPitchHandle", -21.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ChargerHandle", this.fm.EI.engines[0].getStage() < 4 ? 0.0F : -22.0F * this.pictStage, 0.0F, 0.0F);

    if (this.fm.CT.saveWeaponControl[3] != 0) {
      this.mesh.chunkSetAngles("BSLHandle", 22.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("BSLClamp", -11.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("CStick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PShad_L", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PShad_R", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Tross_L", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Tross_R", -this.fm.CT.getRudder() * 20.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurn1a", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", -cvt(getBall(8.0D), -8.0F, 8.0F, 25.0F, -25.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zManifold1a", floatindex(cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3.0F, 16.0F), manifoldScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM1a", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM1b", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zTOilOut1a", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilPrs1a", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zGasPrs1a", cvt(this.fm.M.fuel > 1.0F ? cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3050.0F, 0.0F, 4.0F) : 0.0F, 0.0F, 8.0F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zGas1a", cvt(this.fm.M.fuel / 0.725F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPK10", cvt(interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), -25.0F, 25.0F, 35.0F, -35.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zClock1a", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zClock1b", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", floatindex(cvt(this.setNew.vspeed, -30.0F, 30.0F, 0.0F, 6.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 40.0F, -40.0F));
    this.mesh.chunkSetAngles("zAzimuth1b", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    resetYPRmodifier();
    this.setOld.xyz[1] = cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, 0.03F, -0.03F);
    this.mesh.chunkSetLocate("zHorizon1a", this.setOld.xyz, this.setOld.ypr);
    this.mesh.chunkSetAngles("zHorizon1b", this.fm.Or.getKren(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCylHead", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 300.0F, 0.0F, 70.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCarbpress", cvt(0.6F * this.fm.CT.getPower() + 0.6F * (this.fm.EI.engines[0].getw() / 3000.0F), 0.0F, 1.5F, 0.0F, 105.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOiltemper_I", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, -293.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOiltemper_II", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, -293.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOiltemper_III", cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 100.0F, 0.0F, -293.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilpress", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 296.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zPitchprop", cvt(this.fm.EI.engines[0].getPropAoA(), 0.0F, 12.0F, 0.0F, -225.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    if (this.fm.Gears.rgear) {
      xyz[0] = (0.08F * this.fm.CT.getGear());
    }
    this.mesh.chunkSetLocate("zRGear_ind", xyz, ypr);
    resetYPRmodifier();
    if (this.fm.Gears.lgear) {
      xyz[0] = (0.08F * this.fm.CT.getGear());
    }
    this.mesh.chunkSetLocate("zLGear_ind", xyz, ypr);

    float f = 25.0F;
    if (this.fm.AS.bLandingLightOn) {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("SW_LandLight", f, 0.0F, 0.0F);
    if (this.cockpitLightControl)
      f = 25.0F;
    else {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("SW_UVLight", f, 0.0F, 0.0F);
    if (this.fm.AS.bNavLightsOn)
      f = 25.0F;
    else {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("SW_NavLight", f, 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitI_185M71.this.fm != null) {
        if (CockpitI_185M71.this.bNeedSetUp) {
          CockpitI_185M71.this.reflectPlaneMats();
          CockpitI_185M71.access$102(CockpitI_185M71.this, false);
        }
        CockpitI_185M71.access$202(CockpitI_185M71.this, CockpitI_185M71.this.setOld); CockpitI_185M71.access$302(CockpitI_185M71.this, CockpitI_185M71.this.setNew); CockpitI_185M71.access$402(CockpitI_185M71.this, CockpitI_185M71.this.setTmp);

        CockpitI_185M71.this.setNew.throttle = ((10.0F * CockpitI_185M71.this.setOld.throttle + CockpitI_185M71.this.fm.CT.PowerControl) / 11.0F);
        CockpitI_185M71.this.setNew.prop = (0.85F * CockpitI_185M71.this.setOld.prop + CockpitI_185M71.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitI_185M71.this.setNew.altimeter = CockpitI_185M71.this.fm.getAltitude();
        if (Math.abs(CockpitI_185M71.this.fm.Or.getKren()) < 30.0F) {
          CockpitI_185M71.this.setNew.azimuth = ((35.0F * CockpitI_185M71.this.setOld.azimuth + -CockpitI_185M71.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitI_185M71.this.setOld.azimuth > 270.0F) && (CockpitI_185M71.this.setNew.azimuth < 90.0F)) CockpitI_185M71.this.setOld.azimuth -= 360.0F;
        if ((CockpitI_185M71.this.setOld.azimuth < 90.0F) && (CockpitI_185M71.this.setNew.azimuth > 270.0F)) CockpitI_185M71.this.setOld.azimuth += 360.0F;

        if (CockpitI_185M71.this.useRealisticNavigationInstruments())
        {
          CockpitI_185M71.this.setNew.waypointAzimuth = 0.0F;
        }
        else
        {
          CockpitI_185M71.this.setNew.waypointAzimuth = ((10.0F * CockpitI_185M71.this.setOld.waypointAzimuth + (CockpitI_185M71.this.waypointAzimuth() - CockpitI_185M71.this.setOld.azimuth)) / 11.0F);
        }
        CockpitI_185M71.this.setNew.vspeed = ((199.0F * CockpitI_185M71.this.setOld.vspeed + CockpitI_185M71.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitI_185M71.this.fm.getAltitude() > 3000.0F) {
          float f = (float)Math.sin(1.0F * CockpitI_185M71.this.cvt(CockpitI_185M71.this.fm.getOverload(), 1.0F, 8.0F, 1.0F, 0.45F) * CockpitI_185M71.this.cvt(CockpitI_185M71.this.fm.AS.astatePilotStates[0], 0.0F, 100.0F, 1.0F, 0.1F) * (0.001F * (float)Time.current()));
          if (f > 0.0F) {
            CockpitI_185M71.access$516(CockpitI_185M71.this, 0.3F);
            if (CockpitI_185M71.this.pictBlinker > 1.0F)
              CockpitI_185M71.access$502(CockpitI_185M71.this, 1.0F);
          }
          else {
            CockpitI_185M71.access$524(CockpitI_185M71.this, 0.3F);
            if (CockpitI_185M71.this.pictBlinker < 0.0F) {
              CockpitI_185M71.access$502(CockpitI_185M71.this, 0.0F);
            }
          }
        }
        CockpitI_185M71.access$602(CockpitI_185M71.this, 0.8F * CockpitI_185M71.this.pictStage + 0.1F * CockpitI_185M71.this.fm.EI.engines[0].getControlCompressor());
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
    float waypointAzimuth;
    float[] xyz;
    float[] ypr;
    private final CockpitI_185M71 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.xyz = new float[] { 0.0F, 0.0F, 0.0F };
      this.ypr = new float[] { 0.0F, 0.0F, 0.0F };
    }

    Variables(CockpitI_185M71.1 arg2)
    {
      this();
    }
  }
}