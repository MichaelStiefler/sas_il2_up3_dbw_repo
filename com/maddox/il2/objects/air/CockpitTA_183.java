package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitTA_183 extends CockpitPilot
{
  private boolean bNeedSetUp = true;
  private Gun[] gun = { null, null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictGear = 0.0F;
  private float pictFlap = 0.0F;
  private float pictAftb = 0.0F;
  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(Cockpit.V.y, Cockpit.V.x));
  }

  public CockpitTA_183()
  {
    super("3DO/Cockpit/Ta-183/hier.him", "bf109");
    this.setNew.dimPosition = 0.0F;
    this.cockpitDimControl = this.cockpitDimControl;
    this.cockpitNightMats = new String[] { "D_gauges1_TR", "D_gauges2_TR", "D_gauges3_TR", "gauges1_TR", "gauges2_TR", "gauges3_TR", "gauges4_TR", "gauges5_TR", "gauges6_TR", "gauges7_TR", "gauges8_TR", "gauges9_TR" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    int i = ((TA_183)aircraft()).k14Mode;
    boolean bool = i < 2;
    this.mesh.chunkVisible("Z_Z_RETICLE", bool);
    bool = i > 0;
    this.mesh.chunkVisible("Z_Z_RETICLE1", bool);
    this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);
    resetYPRmodifier();
    Cockpit.xyz[0] = this.setNew.k14w;
    for (i = 1; i < 7; i++) {
      this.mesh.chunkVisible("Z_Z_AIMMARK" + i, bool);
      this.mesh.chunkSetLocate("Z_Z_AIMMARK" + i, Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN03");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN04");
    }
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -0.07115F);

    this.mesh.chunkSetLocate("EZ42Dimm", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("EZ42Filter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -85.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("EZ42FLever", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("EZ42Range", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("EZ42Size", 0.0F, 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.ypr[0] = (interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 59.090908F);

    Cockpit.xyz[2] = cvt(Cockpit.ypr[0], 7.5F, 12.5F, 0.0F, -0.0054F);
    this.mesh.chunkSetLocate("ThrottleQuad", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F);

    this.mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F);

    this.mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F - this.fm.CT.getBrake() * 15.0F);

    this.mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F);

    this.mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F);

    this.mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F - this.fm.CT.getBrake() * 15.0F);

    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 20.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = (this.fm.CT.WeaponControl[1] != 0 ? -0.004F : 0.0F);
    this.mesh.chunkSetLocate("SecTrigger", Cockpit.xyz, Cockpit.ypr);
    this.pictGear = (0.91F * this.pictGear + 0.09F * this.fm.CT.GearControl);
    this.mesh.chunkSetAngles("ZGear", cvt(this.pictGear, 0.0F, 1.0F, -26.5F, 26.5F), 0.0F, 0.0F);

    this.pictFlap = (0.91F * this.pictFlap + 0.09F * this.fm.CT.FlapsControl);
    this.mesh.chunkSetAngles("ZFlaps", cvt(this.pictFlap, 0.0F, 1.0F, -26.5F, 26.5F), 0.0F, 0.0F);

    this.pictAftb = (0.91F * this.pictAftb + 0.09F * (this.fm.CT.PowerControl > 1.0F ? 1.0F : 0.0F));

    this.mesh.chunkSetAngles("ZLever", cvt(this.pictAftb, 0.0F, 1.0F, -29.5F, 29.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_X4Stick", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleVSI", this.setNew.vspeed >= 0.0F ? floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : -floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleKMH", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleRPM", 55.0F + floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleALT", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleALTKm", cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RepeaterOuter", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RepeaterPlane", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleGasPress", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleOilPress", cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 270.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleOxPress", 135.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleGasTemp", cvt(this.fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 51.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleFuel", cvt(this.fm.M.fuel / 0.72F, 0.0F, 1200.0F, 0.0F, 48.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleNahe1", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleNahe2", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleTrimmung", this.fm.CT.getTrimElevatorControl() * 25.0F * 2.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleAHCyl", -this.fm.Or.getKren(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -14.0F, 14.0F));

    this.mesh.chunkSetAngles("NeedleAHTurn", cvt(getBall(6.0D), -6.0F, 6.0F, -12.5F, 12.5F), 0.0F, 0.0F);
    float f;
    if (aircraft().isFMTrackMirror()) {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, -26.5F, 26.5F);

      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.mesh.chunkSetAngles("NeedleAHBank", -f, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleAirPress", 135.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zClock1a", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zClock1b", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zClock1c", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.039F, 0.0F);

    this.mesh.chunkSetLocate("RC_MG17_L", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = cvt(this.gun[2].countBullets(), 0.0F, 500.0F, -0.039F, 0.0F);

    this.mesh.chunkSetLocate("RC_MG17_R", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.039F, 0.0F);

    this.mesh.chunkSetLocate("RC_MG151_L", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = cvt(this.gun[3].countBullets(), 0.0F, 500.0F, -0.039F, 0.0F);

    this.mesh.chunkSetLocate("RC_MG151_R", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkVisible("XLampFuelLow", this.fm.M.fuel < 43.200001F);
    this.mesh.chunkVisible("XLampSpeedWorn", this.fm.getSpeedKMH() < 200.0F);
    this.mesh.chunkVisible("XLampGearL_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearL_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.lgear));

    this.mesh.chunkVisible("XLampGearR_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearR_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.rgear));

    this.mesh.chunkVisible("XLampGearC_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearC_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.cgear));

    this.mesh.chunkVisible("XLampMissileL_1", aircraft().getGunByHookName("_ExternalRock25").haveBullets());

    this.mesh.chunkVisible("XLampMissileL_2", aircraft().getGunByHookName("_ExternalRock27").haveBullets());

    this.mesh.chunkVisible("XLampMissileR_1", aircraft().getGunByHookName("_ExternalRock28").haveBullets());

    this.mesh.chunkVisible("XLampMissileR_2", aircraft().getGunByHookName("_ExternalRock26").haveBullets());

    this.mesh.chunkVisible("XLampMissile", false);
  }

  public void toggleDim() {
    this.cockpitDimControl = (!this.cockpitDimControl);
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

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("EZ42", false);
      this.mesh.chunkVisible("EZ42Dimm", false);
      this.mesh.chunkVisible("EZ42Filter", false);
      this.mesh.chunkVisible("EZ42FLever", false);
      this.mesh.chunkVisible("EZ42Range", false);
      this.mesh.chunkVisible("EZ42Size", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("DEZ42", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
      this.mesh.chunkVisible("IPCGages", false);
      this.mesh.chunkVisible("IPCGages_D1", true);
      this.mesh.chunkVisible("NeedleALT", false);
      this.mesh.chunkVisible("NeedleALTKm", false);
      this.mesh.chunkVisible("NeedleAHCyl", false);
      this.mesh.chunkVisible("NeedleAHBank", false);
      this.mesh.chunkVisible("NeedleAHBar", false);
      this.mesh.chunkVisible("NeedleAHTurn", false);
      this.mesh.chunkVisible("zClock1a", false);
      this.mesh.chunkVisible("zClock1b", false);
      this.mesh.chunkVisible("zClock1c", false);
      this.mesh.chunkVisible("RepeaterOuter", false);
      this.mesh.chunkVisible("RepeaterInner", false);
      this.mesh.chunkVisible("RepeaterPlane", false);
      this.mesh.chunkVisible("NeedleNahe1", false);
      this.mesh.chunkVisible("NeedleNahe2", false);
      this.mesh.chunkVisible("NeedleFuel", false);
      this.mesh.chunkVisible("NeedleGasPress", false);
      this.mesh.chunkVisible("NeedleGasTemp", false);
      this.mesh.chunkVisible("NeedleOilPress", false);
      this.mesh.chunkVisible("NeedleRPM", false);
      this.mesh.chunkVisible("NeedleKMH", false);
      this.mesh.chunkVisible("NeedleVSI", false);
      this.mesh.chunkVisible("XHullDamage3", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage2", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    retoggleLight();
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitTA_183 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitTA_183.1 arg2)
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
      if (CockpitTA_183.this.fm != null) {
        CockpitTA_183.access$102(CockpitTA_183.this, CockpitTA_183.this.setOld);
        CockpitTA_183.access$202(CockpitTA_183.this, CockpitTA_183.this.setNew);
        CockpitTA_183.access$302(CockpitTA_183.this, CockpitTA_183.this.setTmp);
        CockpitTA_183.this.setNew.altimeter = CockpitTA_183.this.fm.getAltitude();
        if (CockpitTA_183.this.cockpitDimControl) {
          if (CockpitTA_183.this.setNew.dimPosition > 0.0F)
            CockpitTA_183.this.setNew.dimPosition = (CockpitTA_183.this.setOld.dimPosition - 0.05F);
        } else if (CockpitTA_183.this.setNew.dimPosition < 1.0F)
          CockpitTA_183.this.setNew.dimPosition = (CockpitTA_183.this.setOld.dimPosition + 0.05F);
        CockpitTA_183.this.setNew.throttle = (0.92F * CockpitTA_183.this.setOld.throttle + 0.08F * CockpitTA_183.this.fm.CT.PowerControl);

        CockpitTA_183.this.setNew.vspeed = ((499.0F * CockpitTA_183.this.setOld.vspeed + CockpitTA_183.this.fm.getVertSpeed()) / 500.0F);

        CockpitTA_183.this.setNew.azimuth = CockpitTA_183.this.fm.Or.getYaw();
        if ((CockpitTA_183.this.setOld.azimuth > 270.0F) && (CockpitTA_183.this.setNew.azimuth < 90.0F))
          CockpitTA_183.this.setOld.azimuth -= 360.0F;
        if ((CockpitTA_183.this.setOld.azimuth < 90.0F) && (CockpitTA_183.this.setNew.azimuth > 270.0F))
          CockpitTA_183.this.setOld.azimuth += 360.0F;
        CockpitTA_183.this.setNew.waypointAzimuth = ((10.0F * CockpitTA_183.this.setOld.waypointAzimuth + (CockpitTA_183.this.waypointAzimuth() - CockpitTA_183.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);

        float f1 = ((TA_183)CockpitTA_183.this.aircraft()).k14Distance;

        CockpitTA_183.this.setNew.k14w = (5.0F * CockpitTA_183.k14TargetWingspanScale[((TA_183)CockpitTA_183.this.aircraft()).k14WingspanType] / f1);

        CockpitTA_183.this.setNew.k14w = (0.9F * CockpitTA_183.this.setOld.k14w + 0.1F * CockpitTA_183.this.setNew.k14w);
        CockpitTA_183.this.setNew.k14wingspan = (0.9F * CockpitTA_183.this.setOld.k14wingspan + 0.1F * CockpitTA_183.k14TargetMarkScale[((TA_183)CockpitTA_183.this.aircraft()).k14WingspanType]);

        CockpitTA_183.this.setNew.k14mode = (0.8F * CockpitTA_183.this.setOld.k14mode + 0.2F * ((TA_183)CockpitTA_183.this.aircraft()).k14Mode);

        Vector3d localVector3d = CockpitTA_183.this.aircraft().FM.getW();

        double d = 0.00125D * f1;
        float f2 = (float)Math.toDegrees(d * localVector3d.z);
        float f3 = -(float)Math.toDegrees(d * localVector3d.y);
        float f4 = CockpitTA_183.this.floatindex((f1 - 200.0F) * 0.04F, CockpitTA_183.k14BulletDrop) - CockpitTA_183.k14BulletDrop[0];

        f3 += (float)Math.toDegrees(Math.atan(f4 / f1));
        CockpitTA_183.this.setNew.k14x = (0.92F * CockpitTA_183.this.setOld.k14x + 0.08F * f2);
        CockpitTA_183.this.setNew.k14y = (0.92F * CockpitTA_183.this.setOld.k14y + 0.08F * f3);
        if (CockpitTA_183.this.setNew.k14x > 7.0F)
          CockpitTA_183.this.setNew.k14x = 7.0F;
        if (CockpitTA_183.this.setNew.k14x < -7.0F)
          CockpitTA_183.this.setNew.k14x = -7.0F;
        if (CockpitTA_183.this.setNew.k14y > 7.0F)
          CockpitTA_183.this.setNew.k14y = 7.0F;
        if (CockpitTA_183.this.setNew.k14y < -7.0F)
          CockpitTA_183.this.setNew.k14y = -7.0F;
      }
      return true;
    }
  }
}