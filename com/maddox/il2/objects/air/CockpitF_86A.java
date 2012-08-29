package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.SoundFX;

public class CockpitF_86A extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  protected SoundFX tFX;
  private static final float[] fuelGallonsScale = { 0.0F, 8.25F, 17.5F, 36.5F, 54.0F, 90.0F, 108.0F };

  private static final float[] fuelGallonsAuxScale = { 0.0F, 38.0F, 62.5F, 87.0F, 104.0F };

  private static final float[] speedometerScale = { 0.0F, 42.0F, 65.5F, 88.5F, 111.3F, 134.0F, 156.5F, 181.0F, 205.0F, 227.0F, 249.39999F, 271.70001F, 294.0F, 316.5F, 339.5F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint waypoint = this.fm.AP.way.curr();
    if (waypoint == null)
      return 0.0F;
    waypoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitF_86A() {
    super("3DO/Cockpit/YP-80/hier1.him", "bf109");
    this.cockpitNightMats = new String[] { "GagePanel1", "GagePanel2", "GagePanel3", "GagePanel4", "GagePanel5", "GagePanel6", "GagePanel7", "Glass", "needles", "radio1" }; this.tFX = aircraft().newSound("extra", false);
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float f)
  {
    if ((this.fm.AS.astateCockpitState & 0x2) == 0) {
      int i = ((F_86A)aircraft()).k14Mode;
      boolean bool = i < 2;
      this.mesh.chunkVisible("Z_Z_RETICLE", bool);
      bool = i > 0;
      this.mesh.chunkVisible("Z_Z_RETICLE1", bool);
      this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);

      resetYPRmodifier();
      Cockpit.xyz[0] = this.setNew.k14w;
      for (int i_3_ = 1; i_3_ < 7; i_3_++) {
        this.mesh.chunkVisible("Z_Z_AIMMARK" + i_3_, bool);
        this.mesh.chunkSetLocate("Z_Z_AIMMARK" + i_3_, Cockpit.xyz, Cockpit.ypr);
      }
    }

    this.mesh.chunkSetAngles("Z_RightPedal", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeftPedal", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", 40.0F * this.setNew.throttle, 0.0F, 0.0F);
    resetYPRmodifier();
    float f_4_ = this.fm.EI.engines[0].getStage();
    if ((f_4_ > 0.0F) && (f_4_ < 7.0F))
      f_4_ = 0.0345F;
    else
      f_4_ = -0.05475F;
    Cockpit.xyz[2] = f_4_;
    this.mesh.chunkSetLocate("Z_EngShutOff", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Column", (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 16.0F, 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F);
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      f_4_ = 40.0F;
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      f_4_ = 20.0F;
    else
      f_4_ = 0.0F;
    this.mesh.chunkSetAngles("Z_Gear1", f_4_, 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[0] != 0)
      Cockpit.xyz[2] = -0.0029F;
    this.mesh.chunkSetLocate("Z_DropTank", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Target1", this.setNew.k14wingspan, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 30480.0F, 0.0F, 36000.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 30480.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    f_4_ = this.fm.EI.engines[0].getPowerOutput();
    this.mesh.chunkSetAngles("Z_Fuel1", cvt((float)Math.sqrt(f_4_), 0.0F, 1.0F, -59.5F, 223.0F), 0.0F, 0.0F);

    f_4_ = cvt(this.fm.M.fuel, 0.0F, 1282.0F, 0.0F, 270.0F);
    if (f_4_ < 45.0F)
      f_4_ = cvt(f_4_, 0.0F, 45.0F, -58.0F, 45.0F);
    f_4_ += 58.0F;
    this.mesh.chunkSetAngles("Z_Fuel2", f_4_, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 28.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 1100.0F, 0.0F, 322.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM2", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 5000.0F, 0.0F, 289.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 150.0F, 0.0F, 116.75F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 150.0F, 0.0F, 116.75F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Oxypres1", 142.5F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 90.0F + this.setNew.azimuth, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", 90.0F + interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, f), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w); setVol();
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(7.0D), -7.0F, 7.0F, -15.0F, 15.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank3a", this.fm.Or.getKren(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 1.5F, -1.5F));

    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.338638F, 2.370465F, 0.0F, 320.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Ny1", cvt(this.fm.getOverload(), -4.0F, 12.0F, -80.5F, 241.5F), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_GearGreen1", this.fm.CT.getGear() > 0.95F);
    this.mesh.chunkVisible("Z_GearRed1", (this.fm.CT.getGear() < 0.05F) || (!this.fm.Gears.lgear) || (!this.fm.Gears.rgear));

    this.mesh.chunkVisible("Z_LampFuelL", this.fm.M.fuel < 500.0F);
    this.mesh.chunkVisible("Z_LampFuelR", this.fm.M.fuel < 500.0F);
    this.mesh.chunkVisible("Z_LampFuelCf", this.fm.M.fuel < 125.0F);
    this.mesh.chunkVisible("Z_Trim1", Math.abs(this.fm.CT.getTrimElevatorControl()) < 0.05F);

    this.mesh.chunkVisible("Z_FireLamp", this.fm.AS.astateEngineStates[0] > 2);
  }

  private void setVol() {
    float f = ((F_86A5)aircraft()).gmnr();

    this.tFX.setVolume(Aircraft.cvt(f, 0.92F, 1.2F, 0.0F, 5.0F));
  }
  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
      this.mesh.chunkVisible("Pricel1_D0", false);
      this.mesh.chunkVisible("Pricel1_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_RETICLE1", false);
      for (int i = 1; i < 7; i++)
        this.mesh.chunkVisible("Z_Z_AIMMARK" + i, false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XHullDamage4", true);
    if (((this.fm.AS.astateCockpitState & 0x80) != 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    retoggleLight();
  }

  public void toggleLight() {
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

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float stage;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitF_86A this$0;

    private Variables()
    {
      this.this$0 = ???;
    }

    Variables(CockpitF_86A.1 x1)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitF_86A.this.fm != null) {
        CockpitF_86A.access$102(CockpitF_86A.this, CockpitF_86A.this.setOld);
        CockpitF_86A.access$202(CockpitF_86A.this, CockpitF_86A.this.setNew);
        CockpitF_86A.access$302(CockpitF_86A.this, CockpitF_86A.this.setTmp);
        CockpitF_86A.this.setNew.throttle = (0.85F * CockpitF_86A.this.setOld.throttle + CockpitF_86A.this.fm.CT.PowerControl * 0.15F);

        CockpitF_86A.this.setNew.prop = (0.85F * CockpitF_86A.this.setOld.prop + CockpitF_86A.this.fm.CT.getStepControl() * 0.15F);

        CockpitF_86A.this.setNew.stage = (0.85F * CockpitF_86A.this.setOld.stage + CockpitF_86A.this.fm.EI.engines[0].getControlCompressor() * 0.15F);

        CockpitF_86A.this.setNew.mix = (0.85F * CockpitF_86A.this.setOld.mix + CockpitF_86A.this.fm.EI.engines[0].getControlMix() * 0.15F);

        CockpitF_86A.this.setNew.altimeter = CockpitF_86A.this.fm.getAltitude();
        CockpitF_86A.this.setNew.azimuth = ((35.0F * CockpitF_86A.this.setOld.azimuth + -CockpitF_86A.this.fm.Or.getYaw()) / 36.0F);

        if ((CockpitF_86A.this.setOld.azimuth > 270.0F) && (CockpitF_86A.this.setNew.azimuth < 90.0F))
          CockpitF_86A.this.setOld.azimuth -= 360.0F;
        if ((CockpitF_86A.this.setOld.azimuth < 90.0F) && (CockpitF_86A.this.setNew.azimuth > 270.0F))
          CockpitF_86A.this.setOld.azimuth += 360.0F;
        CockpitF_86A.this.setNew.waypointAzimuth = ((10.0F * CockpitF_86A.this.setOld.waypointAzimuth + CockpitF_86A.this.waypointAzimuth() + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);

        CockpitF_86A.this.setNew.vspeed = ((199.0F * CockpitF_86A.this.setOld.vspeed + CockpitF_86A.this.fm.getVertSpeed()) / 200.0F);

        float f = ((F_86A)CockpitF_86A.this.aircraft()).k14Distance;
        CockpitF_86A.this.setNew.k14w = (5.0F * CockpitF_86A.k14TargetWingspanScale[((F_86A)CockpitF_86A.this.aircraft()).k14WingspanType] / f);

        CockpitF_86A.this.setNew.k14w = (0.9F * CockpitF_86A.this.setOld.k14w + 0.1F * CockpitF_86A.this.setNew.k14w);
        CockpitF_86A.this.setNew.k14wingspan = (0.9F * CockpitF_86A.this.setOld.k14wingspan + 0.1F * CockpitF_86A.k14TargetMarkScale[((F_86A)CockpitF_86A.this.aircraft()).k14WingspanType]);

        CockpitF_86A.this.setNew.k14mode = (0.8F * CockpitF_86A.this.setOld.k14mode + 0.2F * ((F_86A)CockpitF_86A.this.aircraft()).k14Mode);

        if (CockpitF_86A.this.tFX != null)
          if (((F_86A5)CockpitF_86A.this.aircraft()).ist()) {
            if (!CockpitF_86A.this.tFX.isPlaying())
              CockpitF_86A.this.tFX.play();
          }
          else CockpitF_86A.this.tFX.stop();
        Vector3d vector3d = CockpitF_86A.this.aircraft().FM.getW();
        double d = 0.00125D * f;
        float f_0_ = (float)Math.toDegrees(d * vector3d.z);
        float f_1_ = -(float)Math.toDegrees(d * vector3d.y);
        float f_2_ = CockpitF_86A.this.floatindex((f - 200.0F) * 0.04F, CockpitF_86A.k14BulletDrop) - CockpitF_86A.k14BulletDrop[0];
        f_1_ += (float)Math.toDegrees(Math.atan(f_2_ / f));
        CockpitF_86A.this.setNew.k14x = (0.92F * CockpitF_86A.this.setOld.k14x + 0.08F * f_0_);
        CockpitF_86A.this.setNew.k14y = (0.92F * CockpitF_86A.this.setOld.k14y + 0.08F * f_1_);
        if (CockpitF_86A.this.setNew.k14x > 7.0F)
          CockpitF_86A.this.setNew.k14x = 7.0F;
        if (CockpitF_86A.this.setNew.k14x < -7.0F)
          CockpitF_86A.this.setNew.k14x = -7.0F;
        if (CockpitF_86A.this.setNew.k14y > 7.0F)
          CockpitF_86A.this.setNew.k14y = 7.0F;
        if (CockpitF_86A.this.setNew.k14y < -7.0F)
          CockpitF_86A.this.setNew.k14y = -7.0F;
      }
      return true;
    }
  }
}