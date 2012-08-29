package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitTA_152C1 extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null, null };
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private BulletEmitter[] bomb = { null, null, null, null };
  private float pictAiler;
  private float pictElev;
  private float pictGear;
  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 53.0F, 108.0F, 170.0F, 229.0F, 282.0F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 16.0F, 35.0F, 52.5F, 72.0F, 72.0F };

  private static final float[] manPrsScale = { 0.0F, 0.0F, 0.0F, 15.5F, 71.0F, 125.0F, 180.0F, 235.0F, 290.0F, 245.0F, 247.0F, 247.0F };

  private static final float[] oilfuelNeedleScale = { 0.0F, 38.0F, 84.0F, 135.5F, 135.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };
  private Point3d tmpP;
  private Vector3d tmpV;

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
    {
      return 0.0F;
    }

    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(Cockpit.V.y, Cockpit.V.x));
  }

  public CockpitTA_152C1()
  {
    super("3DO/Cockpit/Ta-152C1/hier.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictGear = 0.0F;
    this.setNew.dimPosition = 1.0F;
    this.cockpitNightMats = new String[] { "D9GP1", "D9GP2", "Ta152GP3", "A5GP3Km", "Ta152GP4_MW50", "Ta152GP5", "A4GP6", "Ta152Trans2", "D9GP2" };

    setNightMats(false);
    this.cockpitDimControl = (!this.cockpitDimControl);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    int i = ((TA_152C1)aircraft()).k14Mode;
    boolean bool = i < 2;
    this.mesh.chunkVisible("Z_Z_RETICLE", bool);
    bool = i > 0;
    this.mesh.chunkVisible("Z_Z_RETICLE1", bool);
    this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);
    resetYPRmodifier();
    Cockpit.xyz[0] = this.setNew.k14w;
    for (int j = 1; j < 7; j++)
    {
      this.mesh.chunkVisible("Z_Z_AIMMARK" + j, bool);
      this.mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
    }

    if (this.gun[0] == null)
    {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON03");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON04");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON05");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON06");
    }
    if (this.bomb[0] == null)
    {
      for (int k = 0; k < this.bomb.length; k++) {
        this.bomb[k] = GunEmpty.get();
      }
      if (((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05") != GunEmpty.get())
      {
        this.bomb[1] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
        this.bomb[2] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
      }
      else if (((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02") != GunEmpty.get())
      {
        this.bomb[1] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02");
        this.bomb[2] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02");
      }
      if (((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock01") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock01");
        this.bomb[3] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock02");
      }
      else if (((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03");
        this.bomb[3] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb04");
      }
      else if (((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev05") != GunEmpty.get())
      {
        this.bomb[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev05");
        this.bomb[3] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev06");
      }
    }
    resetYPRmodifier();
    float f1;
    if (this.fm.CT.GearControl == 0.0F)
      f1 = 0.0205F;
    else
      f1 = 0.0F;
    this.pictGear = (0.93F * this.pictGear + 0.07F * f1);
    Cockpit.xyz[2] = this.pictGear;
    this.mesh.chunkSetLocate("FahrHandle", Cockpit.xyz, Cockpit.ypr);
    if (((this.fm.AS.astateCockpitState & 0x20) == 0) && ((this.fm.AS.astateCockpitState & 0x80) == 0))
    {
      this.mesh.chunkSetAngles("NeedleALT", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, -180.0F));
    }
    this.mesh.chunkSetAngles("NeedleManPress", -cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 15.0F, 345.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
      this.mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 4.0F), fuelScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleWaterTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleOilTemp", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    if (((this.fm.AS.astateCockpitState & 0x2) == 0) && ((this.fm.AS.astateCockpitState & 0x1) == 0) && ((this.fm.AS.astateCockpitState & 0x4) == 0))
    {
      this.mesh.chunkSetAngles("NeedleFuelPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      float f2;
      if (aircraft().isFMTrackMirror())
      {
        f2 = aircraft().fmTrack().getCockpitAzimuthSpeed();
      }
      else {
        f2 = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, 20.0F, -20.0F);
        if (aircraft().fmTrack() != null)
          aircraft().fmTrack().setCockpitAzimuthSpeed(f2);
      }
      this.mesh.chunkSetAngles("NeedleAHTurn", 0.0F, f2, 0.0F);
      this.mesh.chunkSetAngles("NeedleAHBank", 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, 11.0F, -11.0F), 0.0F);
      this.mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, this.fm.Or.getKren());
      this.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 12.0F, -12.0F));
    }
    if (((this.fm.AS.astateCockpitState & 0x20) == 0) && ((this.fm.AS.astateCockpitState & 0x80) == 0))
      this.mesh.chunkSetAngles("NeedleCD", this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.mesh.chunkSetAngles("RepeaterOuter", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("RepeaterPlane", interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
      this.mesh.chunkSetAngles("NeedleTrimmung", this.fm.CT.getTrimElevatorControl() * 25.0F * 2.0F, 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.gun[1] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.046F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_L", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[2] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[2].countBullets(), 0.0F, 500.0F, -0.046F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_R", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[0] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.019F, 0.024F);
      this.mesh.chunkSetLocate("RC_MG151_L", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.gun[3] != null)
    {
      Cockpit.xyz[0] = cvt(this.gun[3].countBullets(), 0.0F, 500.0F, -0.019F, 0.024F);
      this.mesh.chunkSetLocate("RC_MG151_R", Cockpit.xyz, Cockpit.ypr);
    }
    this.mesh.chunkSetAngles("IgnitionSwitch", 24.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 20.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (this.fm.CT.WeaponControl[1] != 0 ? -0.004F : 0.0F);
    this.mesh.chunkSetLocate("SecTrigger", Cockpit.xyz, Cockpit.ypr);
    Cockpit.ypr[0] = (interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 34.0F * 0.91F);
    Cockpit.xyz[2] = (Cockpit.ypr[0] > 7.0F ? -0.006F : 0.0F);
    this.mesh.chunkSetLocate("ThrottleQuad", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("RPedalBase", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F);
    this.mesh.chunkSetAngles("RPedalStrut", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F);
    this.mesh.chunkSetAngles("RPedal", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F - this.fm.CT.getBrake() * 15.0F);
    this.mesh.chunkSetAngles("LPedalBase", 0.0F, 0.0F, -this.fm.CT.getRudder() * 15.0F);
    this.mesh.chunkSetAngles("LPedalStrut", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F);
    this.mesh.chunkSetAngles("LPedal", 0.0F, 0.0F, this.fm.CT.getRudder() * 15.0F - this.fm.CT.getBrake() * 15.0F);
    this.mesh.chunkVisible("XLampTankSwitch", this.fm.M.fuel > 144.0F);
    this.mesh.chunkVisible("XLampFuelLow", this.fm.M.fuel < 43.200001F);
    this.mesh.chunkVisible("XLampGearL_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearL_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearR_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearR_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearC_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearC_2", this.fm.CT.getGear() > 0.95F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (this.fm.CT.GearControl < 0.5F ? 0.02F : 0.0F);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
    if (this.cockpitLightControl)
    {
      setNightMats(false);
      setNightMats(true);
    }
    else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0) || ((this.fm.AS.astateCockpitState & 0x4) != 0))
    {
      this.mesh.chunkVisible("Revi16", false);
      this.mesh.chunkVisible("Revi16Tinter", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("DRevi16", true);
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.materialReplace("D9GP1", "DD9GP1");
      this.mesh.materialReplace("D9GP1_night", "DD9GP1_night");
      this.mesh.chunkVisible("NeedleManPress", false);
      this.mesh.chunkVisible("NeedleRPM", false);
      this.mesh.chunkVisible("RepeaterOuter", false);
      this.mesh.chunkVisible("RepeaterPlane", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) != 0) || ((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.materialReplace("D9GP2", "DD9GP2");
      this.mesh.materialReplace("D9GP2_night", "DD9GP2_night");
      this.mesh.chunkVisible("NeedleAHCyl", false);
      this.mesh.chunkVisible("NeedleAHBank", false);
      this.mesh.chunkVisible("NeedleAHBar", false);
      this.mesh.chunkVisible("NeedleAHTurn", false);
      this.mesh.chunkVisible("NeedleFuelPress", false);
      this.mesh.chunkVisible("NeedleOilPress", false);
      this.mesh.materialReplace("Ta152GP4_MW50", "DTa152GP4");
      this.mesh.materialReplace("Ta152GP4_MW50_night", "DTa152GP4_night");
      this.mesh.chunkVisible("NeedleFuel", false);
      resetYPRmodifier();
      Cockpit.xyz[0] = -0.001F;
      Cockpit.xyz[1] = 0.008F;
      Cockpit.xyz[2] = 0.025F;
      Cockpit.ypr[0] = 3.0F;
      Cockpit.ypr[1] = -6.0F;
      Cockpit.ypr[2] = 1.5F;
      this.mesh.chunkSetLocate("IPCentral", Cockpit.xyz, Cockpit.ypr);
    }
    if (((this.fm.AS.astateCockpitState & 0x20) != 0) || ((this.fm.AS.astateCockpitState & 0x80) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.materialReplace("Ta152GP3", "DTa152GP3");
      this.mesh.materialReplace("Ta152GP3_night", "DTa152GP3_night");
      this.mesh.chunkVisible("NeedleKMH", false);
      this.mesh.chunkVisible("NeedleCD", false);
      this.mesh.chunkVisible("NeedleAlt", false);
      this.mesh.chunkVisible("NeedleAltKM", false);
      this.mesh.materialReplace("Ta152Trans2", "DTa152Trans2");
      this.mesh.materialReplace("Ta152Trans2_night", "DTa152Trans2_night");
      this.mesh.chunkVisible("NeedleWaterTemp", false);
      this.mesh.chunkVisible("NeedleOilTemp", false);
    }
    retoggleLight();
  }

  static
  {
    Property.set(CockpitTA_152C1.class, "normZN", 0.72F);
    Property.set(CockpitTA_152C1.class, "gsZN", 0.66F);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitTA_152C1.this.fm != null)
      {
        CockpitTA_152C1.access$002(CockpitTA_152C1.this, CockpitTA_152C1.this.setOld);
        CockpitTA_152C1.access$102(CockpitTA_152C1.this, CockpitTA_152C1.this.setNew);
        CockpitTA_152C1.access$202(CockpitTA_152C1.this, CockpitTA_152C1.this.setTmp);
        CockpitTA_152C1.this.setNew.altimeter = CockpitTA_152C1.this.fm.getAltitude();
        if (CockpitTA_152C1.this.cockpitDimControl)
        {
          if (CockpitTA_152C1.this.setNew.dimPosition > 0.0F)
            CockpitTA_152C1.this.setNew.dimPosition = (CockpitTA_152C1.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitTA_152C1.this.setNew.dimPosition < 1.0F)
          CockpitTA_152C1.this.setNew.dimPosition = (CockpitTA_152C1.this.setOld.dimPosition + 0.05F);
        CockpitTA_152C1.this.setNew.throttle = ((10.0F * CockpitTA_152C1.this.setOld.throttle + CockpitTA_152C1.this.fm.CT.PowerControl) / 11.0F);
        CockpitTA_152C1.this.setNew.vspeed = ((499.0F * CockpitTA_152C1.this.setOld.vspeed + CockpitTA_152C1.this.fm.getVertSpeed()) / 500.0F);
        CockpitTA_152C1.this.setNew.azimuth = CockpitTA_152C1.this.fm.Or.getYaw();
        if ((CockpitTA_152C1.this.setOld.azimuth > 270.0F) && (CockpitTA_152C1.this.setNew.azimuth < 90.0F))
          CockpitTA_152C1.this.setOld.azimuth -= 360.0F;
        if ((CockpitTA_152C1.this.setOld.azimuth < 90.0F) && (CockpitTA_152C1.this.setNew.azimuth > 270.0F))
          CockpitTA_152C1.this.setOld.azimuth += 360.0F;
        CockpitTA_152C1.this.setNew.waypointAzimuth = ((10.0F * CockpitTA_152C1.this.setOld.waypointAzimuth + (CockpitTA_152C1.this.waypointAzimuth() - CockpitTA_152C1.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        float f1 = ((TA_152C1)CockpitTA_152C1.this.aircraft()).k14Distance;
        CockpitTA_152C1.this.setNew.k14w = (5.0F * CockpitTA_152C1.k14TargetWingspanScale[((TA_152C1)CockpitTA_152C1.this.aircraft()).k14WingspanType] / f1);
        CockpitTA_152C1.this.setNew.k14w = (0.9F * CockpitTA_152C1.this.setOld.k14w + 0.1F * CockpitTA_152C1.this.setNew.k14w);
        CockpitTA_152C1.this.setNew.k14wingspan = (0.9F * CockpitTA_152C1.this.setOld.k14wingspan + 0.1F * CockpitTA_152C1.k14TargetMarkScale[((TA_152C1)CockpitTA_152C1.this.aircraft()).k14WingspanType]);
        CockpitTA_152C1.this.setNew.k14mode = (0.8F * CockpitTA_152C1.this.setOld.k14mode + 0.2F * ((TA_152C1)CockpitTA_152C1.this.aircraft()).k14Mode);
        Vector3d localVector3d = CockpitTA_152C1.this.aircraft().FM.getW();
        double d = 0.00125D * f1;
        float f2 = (float)Math.toDegrees(d * localVector3d.z);
        float f3 = -(float)Math.toDegrees(d * localVector3d.y);
        float f4 = CockpitTA_152C1.this.floatindex((f1 - 200.0F) * 0.04F, CockpitTA_152C1.k14BulletDrop) - CockpitTA_152C1.k14BulletDrop[0];
        f3 += (float)Math.toDegrees(Math.atan(f4 / f1));
        CockpitTA_152C1.this.setNew.k14x = (0.92F * CockpitTA_152C1.this.setOld.k14x + 0.08F * f2);
        CockpitTA_152C1.this.setNew.k14y = (0.92F * CockpitTA_152C1.this.setOld.k14y + 0.08F * f3);
        if (CockpitTA_152C1.this.setNew.k14x > 7.0F)
          CockpitTA_152C1.this.setNew.k14x = 7.0F;
        if (CockpitTA_152C1.this.setNew.k14x < -7.0F)
          CockpitTA_152C1.this.setNew.k14x = -7.0F;
        if (CockpitTA_152C1.this.setNew.k14y > 7.0F)
          CockpitTA_152C1.this.setNew.k14y = 7.0F;
        if (CockpitTA_152C1.this.setNew.k14y < -7.0F)
          CockpitTA_152C1.this.setNew.k14y = -7.0F;
      }
      return true;
    }

    Interpolater()
    {
    }
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
    private final CockpitTA_152C1 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitTA_152C1.1 arg2)
    {
      this();
    }
  }
}