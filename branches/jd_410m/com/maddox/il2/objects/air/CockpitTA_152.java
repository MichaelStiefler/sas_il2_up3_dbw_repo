package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
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
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitTA_152 extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private BulletEmitter[] bomb = { null, null, null, null };
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 53.0F, 108.0F, 170.0F, 229.0F, 282.0F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 16.0F, 35.0F, 52.5F, 72.0F, 72.0F };

  private static final float[] manPrsScale = { 0.0F, 0.0F, 0.0F, 15.5F, 71.0F, 125.0F, 180.0F, 235.0F, 290.0F, 245.0F, 247.0F, 247.0F };

  private static final float[] oilfuelNeedleScale = { 0.0F, 38.0F, 84.0F, 135.5F, 135.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitTA_152()
  {
    super("3DO/Cockpit/Ta-152H-1/hier.him", "bf109");
    this.setNew.dimPosition = 1.0F;

    HookNamed localHookNamed = new HookNamed(this.mesh, "LIGHTHOOK_L");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LIGHTHOOK_R");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "D9GP1", "D9GP2", "Ta152GP3", "A5GP3Km", "Ta152GP4_MW50", "Ta152GP5", "A4GP6", "Ta152Trans2", "D9GP2" };
    setNightMats(false);
    this.cockpitDimControl = (!this.cockpitDimControl);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON03");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON04");
    }
    if (this.bomb[0] == null) {
      for (int i = 0; i < this.bomb.length; i++) {
        this.bomb[i] = GunEmpty.get();
      }
      if (((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05") != GunEmpty.get()) {
        this.bomb[1] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
        this.bomb[2] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05");
      } else if (((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02") != GunEmpty.get()) {
        this.bomb[1] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02");
        this.bomb[2] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev02");
      }
      if (((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock01") != GunEmpty.get()) {
        this.bomb[0] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock01");
        this.bomb[3] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock02");
      } else if (((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03") != GunEmpty.get()) {
        this.bomb[0] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03");
        this.bomb[3] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb04");
      } else if (((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev05") != GunEmpty.get()) {
        this.bomb[0] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev05");
        this.bomb[3] = ((Aircraft)(Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev06");
      }
    }

    resetYPRmodifier();
    if (this.fm.CT.GearControl == 0.0F) {
      xyz[2] = 0.02F;
    }
    this.mesh.chunkSetLocate("FahrHandle", xyz, ypr);

    if (((this.fm.AS.astateCockpitState & 0x20) == 0) && ((this.fm.AS.astateCockpitState & 0x80) == 0))
    {
      this.mesh.chunkSetAngles("NeedleALT", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, -180.0F));
    }

    this.mesh.chunkSetAngles("NeedleManPress", -cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    }

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
      this.mesh.chunkSetAngles("NeedleAHTurn", 0.0F, cvt(this.setNew.turn, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F);

      this.mesh.chunkSetAngles("NeedleAHBank", 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, 11.0F, -11.0F), 0.0F);

      this.mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, this.fm.Or.getKren());

      this.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 12.0F, -12.0F));
    }

    if (((this.fm.AS.astateCockpitState & 0x20) == 0) && ((this.fm.AS.astateCockpitState & 0x80) == 0))
    {
      this.mesh.chunkSetAngles("NeedleCD", this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);
    }

    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      if (useRealisticNavigationInstruments())
      {
        this.mesh.chunkSetAngles("RepeaterPlane", -this.setNew.azimuth.getDeg(paramFloat) + this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
        this.mesh.chunkSetAngles("RepeaterOuter", this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      }
      else
      {
        this.mesh.chunkSetAngles("RepeaterOuter", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
        this.mesh.chunkSetAngles("RepeaterPlane", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      }

    }

    if (((this.fm.AS.astateCockpitState & 0x40) == 0) && ((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.mesh.chunkSetAngles("NeedleTrimmung", this.fm.CT.getTrimElevatorControl() * 25.0F, 0.0F, 0.0F);
    }

    float f = this.fm.M.nitro / this.fm.M.maxNitro;
    f = (float)Math.sqrt(f);
    this.mesh.chunkSetAngles("NeedleMW50Press", cvt(f, 0.0F, 0.5F, 0.0F, 260.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    if (this.gun[0] != null) {
      xyz[0] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_L", xyz, ypr);
    }
    if (this.gun[1] != null) {
      xyz[0] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_R", xyz, ypr);
    }
    if (this.gun[2] != null) {
      xyz[0] = cvt(this.gun[2].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG151_L", xyz, ypr);
    }
    if (this.gun[3] != null) {
      xyz[0] = cvt(this.gun[3].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG151_R", xyz, ypr);
    }

    this.mesh.chunkSetAngles("IgnitionSwitch", 24.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 20.0F);

    resetYPRmodifier();
    xyz[2] = (this.fm.CT.WeaponControl[1] != 0 ? -0.004F : 0.0F);
    this.mesh.chunkSetLocate("SecTrigger", xyz, ypr);
    ypr[0] = (interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 34.0F * 0.91F);
    xyz[2] = (ypr[0] > 7.0F ? -0.006F : 0.0F);
    this.mesh.chunkSetLocate("ThrottleQuad", xyz, ypr);

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
    xyz[2] = (this.fm.CT.GearControl < 0.5F ? 0.02F : 0.0F);

    this.mesh.chunkSetAngles("NeedleNahe1", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, 20.0F, -20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleNahe2", cvt(this.setNew.beaconRange, 0.0F, 1.0F, -20.0F, 20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.005F, 0.75F);
      this.light2.light.setEmit(0.005F, 0.75F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
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
      xyz[0] = -0.001F;
      xyz[1] = 0.008F;
      xyz[2] = 0.025F;
      ypr[0] = 3.0F;
      ypr[1] = -6.0F;
      ypr[2] = 1.5F;
      this.mesh.chunkSetLocate("IPCentral", xyz, ypr);
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
    Property.set(CockpitTA_152.class, "normZN", 0.74F);

    Property.set(CockpitTA_152.class, "gsZN", 0.66F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitTA_152.this.fm != null) {
        CockpitTA_152.access$102(CockpitTA_152.this, CockpitTA_152.this.setOld); CockpitTA_152.access$202(CockpitTA_152.this, CockpitTA_152.this.setNew); CockpitTA_152.access$302(CockpitTA_152.this, CockpitTA_152.this.setTmp);

        CockpitTA_152.this.setNew.altimeter = CockpitTA_152.this.fm.getAltitude();
        if (CockpitTA_152.this.cockpitDimControl) {
          if (CockpitTA_152.this.setNew.dimPosition > 0.0F) CockpitTA_152.this.setNew.dimPosition = (CockpitTA_152.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitTA_152.this.setNew.dimPosition < 1.0F) CockpitTA_152.this.setNew.dimPosition = (CockpitTA_152.this.setOld.dimPosition + 0.05F);

        CockpitTA_152.this.setNew.throttle = ((10.0F * CockpitTA_152.this.setOld.throttle + CockpitTA_152.this.fm.CT.PowerControl) / 11.0F);
        CockpitTA_152.this.setNew.vspeed = ((499.0F * CockpitTA_152.this.setOld.vspeed + CockpitTA_152.this.fm.getVertSpeed()) / 500.0F);

        float f = CockpitTA_152.this.waypointAzimuth();
        if (CockpitTA_152.this.useRealisticNavigationInstruments())
        {
          CockpitTA_152.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitTA_152.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitTA_152.this.setNew.waypointAzimuth.setDeg(CockpitTA_152.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitTA_152.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitTA_152.this.setNew.azimuth.setDeg(CockpitTA_152.this.setOld.azimuth.getDeg(1.0F), CockpitTA_152.this.fm.Or.azimut());

        CockpitTA_152.this.w.set(CockpitTA_152.this.fm.getW());
        CockpitTA_152.this.fm.Or.transform(CockpitTA_152.this.w);
        CockpitTA_152.this.setNew.turn = ((12.0F * CockpitTA_152.this.setOld.turn + CockpitTA_152.this.w.z) / 13.0F);

        CockpitTA_152.this.setNew.beaconDirection = ((10.0F * CockpitTA_152.this.setOld.beaconDirection + CockpitTA_152.this.getBeaconDirection()) / 11.0F);
        CockpitTA_152.this.setNew.beaconRange = ((10.0F * CockpitTA_152.this.setOld.beaconRange + CockpitTA_152.this.getBeaconRange()) / 11.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float beaconDirection;
    float beaconRange;
    float turn;
    float vspeed;
    private final CockpitTA_152 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitTA_152.1 arg2)
    {
      this();
    }
  }
}