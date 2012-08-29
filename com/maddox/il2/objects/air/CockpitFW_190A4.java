package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
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
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitFW_190A4 extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null, null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, 18.5F, 67.0F, 117.0F, 164.0F, 215.0F, 267.0F, 320.0F, 379.0F, 427.0F, 428.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 53.0F, 108.0F, 170.0F, 229.0F, 282.0F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 16.0F, 35.0F, 52.5F, 72.0F, 72.0F };

  private static final float[] manPrsScale = { 0.0F, 0.0F, 0.0F, 15.5F, 71.0F, 125.0F, 180.0F, 235.0F, 290.0F, 245.0F, 247.0F, 247.0F };

  private static final float[] oilfuelNeedleScale = { 0.0F, 38.0F, 84.0F, 135.5F, 135.0F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] oilTempScale = { 0.0F, 23.0F, 52.0F, 81.0F, 81.0F };

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitFW_190A4()
  {
    super("3DO/Cockpit/FW-190A-4/hier.him", "bf109");
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

    this.cockpitNightMats = new String[] { "A4GP1", "A4GP2", "A4GP3", "A4GP4", "A4GP5", "A4GP6", "A5GP3Km" };
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
      this.gun[4] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON01");
      this.gun[5] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON02");
    }

    this.mesh.chunkSetAngles("NeedleALT", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleALTKm", 0.0F, 0.0F, cvt(this.setNew.altimeter, 0.0F, 10000.0F, 0.0F, -180.0F));

    this.mesh.chunkSetAngles("NeedleManPress", -cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 336.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleKMH", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleRPM", -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleFuel", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 4.0F), fuelScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleOilTemp", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 3.0F), oilTempScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleFuelPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleOilPress", -cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleAHTurn", cvt(this.setNew.turn, -0.23562F, 0.23562F, -50.0F, 50.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleAHBank", cvt(getBall(7.0D), -7.0F, 7.0F, 11.0F, -11.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleAHCyl", 0.0F, 0.0F, this.fm.Or.getKren());

    this.mesh.chunkSetAngles("NeedleAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 12.0F, -12.0F));

    this.mesh.chunkSetAngles("NeedleCD", this.setNew.vspeed >= 0.0F ? -floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);

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

    this.mesh.chunkSetAngles("NeedleHBSmall", -105.0F + (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleHBLarge", -270.0F + (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleTrimmung", this.fm.CT.getTrimElevatorControl() * 25.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedleHClock", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleMClock", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedleSClock", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    if (this.gun[0] != null) {
      xyz[0] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_L", xyz, ypr);
      this.mesh.chunkVisible("XLampMG17_1", !this.gun[0].haveBullets());
    }

    if (this.gun[1] != null) {
      xyz[0] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.044F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG17_R", xyz, ypr);
      this.mesh.chunkVisible("XLampMG17_2", !this.gun[1].haveBullets());
    }
    if (this.gun[4] != null) {
      xyz[0] = cvt(this.gun[4].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG151_L", xyz, ypr);
    }
    if (this.gun[5] != null) {
      xyz[0] = cvt(this.gun[5].countBullets(), 0.0F, 200.0F, -0.017F, 0.0F);
      this.mesh.chunkSetLocate("RC_MG151_R", xyz, ypr);
    }
    if (this.gun[2] != null) {
      xyz[0] = cvt(this.gun[2].countBullets(), 0.0F, 55.0F, -0.018F, 0.0F);
      this.mesh.chunkSetLocate("RC_MGFF_WingL", xyz, ypr);
    }
    if (this.gun[3] != null) {
      xyz[0] = cvt(this.gun[3].countBullets(), 0.0F, 55.0F, -0.018F, 0.0F);
      this.mesh.chunkSetLocate("RC_MGFF_WingR", xyz, ypr);
    }

    this.mesh.chunkSetAngles("IgnitionSwitch", 24.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Revi16Tinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);

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
    this.mesh.chunkVisible("XLampFlapLPos_3", this.fm.CT.getFlap() > 0.9F);
    this.mesh.chunkVisible("XLampFlapLPos_2", (this.fm.CT.getFlap() > 0.1F) && (this.fm.CT.getFlap() < 0.5F));
    this.mesh.chunkVisible("XLampFlapLPos_1", this.fm.CT.getFlap() < 0.1F);
    this.mesh.chunkVisible("XLampFlapRPos_3", this.fm.CT.getFlap() > 0.9F);
    this.mesh.chunkVisible("XLampFlapRPos_2", (this.fm.CT.getFlap() > 0.1F) && (this.fm.CT.getFlap() < 0.5F));
    this.mesh.chunkVisible("XLampFlapRPos_1", this.fm.CT.getFlap() < 0.1F);

    this.mesh.chunkVisible("XLampGearL_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearL_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearR_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearR_2", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearC_1", this.fm.CT.getGear() < 0.05F);
    this.mesh.chunkVisible("XLampGearC_2", this.fm.CT.getGear() > 0.95F);

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
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0) || ((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0))
    {
      if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
        this.mesh.chunkVisible("Revi16", false);
        this.mesh.chunkVisible("Revi16Tinter", false);
        this.mesh.chunkVisible("Z_Z_MASK", false);
        this.mesh.chunkVisible("Z_Z_RETICLE", false);
        this.mesh.chunkVisible("DRevi16", true);
      }
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.materialReplace("A4GP1", "DA4GP1");
      this.mesh.materialReplace("A4GP1_night", "DA4GP1_night");
      this.mesh.chunkVisible("NeedleRPM", false);
      this.mesh.chunkVisible("NeedleManPress", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0) || ((this.fm.AS.astateCockpitState & 0x40) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.materialReplace("A4GP2", "DA4GP2");
      this.mesh.materialReplace("A4GP2_night", "DA4GP2_night");
      this.mesh.chunkVisible("NeedleAHBank", false);
      resetYPRmodifier();

      xyz[0] = -0.01F;
      xyz[1] = -0.01F;
      ypr[0] = -5.0F;
      ypr[2] = -5.0F;
      this.mesh.chunkSetLocate("IPCentral", xyz, ypr);
      this.mesh.chunkVisible("NeedleOilPress", false);
      this.mesh.chunkVisible("NeedleFuelPress", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.materialReplace("A4GP3", "DA4GP3");
      this.mesh.materialReplace("A4GP3_night", "DA4GP3_night");
      this.mesh.chunkVisible("NeedleALT", false);
      this.mesh.chunkVisible("NeedleKMH", false);
    }
    retoggleLight();
  }

  static
  {
    Property.set(CockpitFW_190A4.class, "normZN", 0.72F);
    Property.set(CockpitFW_190A4.class, "gsZN", 0.66F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitFW_190A4.this.fm != null) {
        CockpitFW_190A4.access$102(CockpitFW_190A4.this, CockpitFW_190A4.this.setOld); CockpitFW_190A4.access$202(CockpitFW_190A4.this, CockpitFW_190A4.this.setNew); CockpitFW_190A4.access$302(CockpitFW_190A4.this, CockpitFW_190A4.this.setTmp);

        CockpitFW_190A4.this.setNew.altimeter = CockpitFW_190A4.this.fm.getAltitude();
        if (CockpitFW_190A4.this.cockpitDimControl) {
          if (CockpitFW_190A4.this.setNew.dimPosition > 0.0F) CockpitFW_190A4.this.setNew.dimPosition = (CockpitFW_190A4.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitFW_190A4.this.setNew.dimPosition < 1.0F) CockpitFW_190A4.this.setNew.dimPosition = (CockpitFW_190A4.this.setOld.dimPosition + 0.05F);

        CockpitFW_190A4.this.setNew.throttle = ((10.0F * CockpitFW_190A4.this.setOld.throttle + CockpitFW_190A4.this.fm.CT.PowerControl) / 11.0F);
        CockpitFW_190A4.this.setNew.vspeed = ((499.0F * CockpitFW_190A4.this.setOld.vspeed + CockpitFW_190A4.this.fm.getVertSpeed()) / 500.0F);

        float f = CockpitFW_190A4.this.waypointAzimuth();
        if (CockpitFW_190A4.this.useRealisticNavigationInstruments())
        {
          CockpitFW_190A4.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitFW_190A4.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitFW_190A4.this.setNew.waypointAzimuth.setDeg(CockpitFW_190A4.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitFW_190A4.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitFW_190A4.this.setNew.azimuth.setDeg(CockpitFW_190A4.this.setOld.azimuth.getDeg(1.0F), CockpitFW_190A4.this.fm.Or.azimut());

        CockpitFW_190A4.this.w.set(CockpitFW_190A4.this.fm.getW());
        CockpitFW_190A4.this.fm.Or.transform(CockpitFW_190A4.this.w);
        CockpitFW_190A4.this.setNew.turn = ((12.0F * CockpitFW_190A4.this.setOld.turn + CockpitFW_190A4.this.w.z) / 13.0F);

        CockpitFW_190A4.this.setNew.beaconDirection = ((10.0F * CockpitFW_190A4.this.setOld.beaconDirection + CockpitFW_190A4.this.getBeaconDirection()) / 11.0F);
        CockpitFW_190A4.this.setNew.beaconRange = ((10.0F * CockpitFW_190A4.this.setOld.beaconRange + CockpitFW_190A4.this.getBeaconRange()) / 11.0F);
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
    private final CockpitFW_190A4 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitFW_190A4.1 arg2)
    {
      this();
    }
  }
}