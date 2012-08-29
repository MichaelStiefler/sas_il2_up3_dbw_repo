package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitME_262 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private Gun[] gun = { null, null, null, null };
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bU4 = false;

  private static final float[] speedometerIndScale = { 0.0F, 0.0F, 0.0F, 17.0F, 35.5F, 57.5F, 76.0F, 95.0F, 112.0F };

  private static final float[] speedometerTruScale = { 0.0F, 32.75F, 65.5F, 98.25F, 131.0F, 164.0F, 200.0F, 237.0F, 270.5F, 304.0F, 336.0F };

  private static final float[] variometerScale = { 0.0F, 13.5F, 27.0F, 43.5F, 90.0F, 142.5F, 157.0F, 170.5F, 184.0F, 201.5F, 214.5F, 226.0F, 239.5F, 253.0F, 266.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private static final float[] fuelScale = { 0.0F, 11.0F, 31.0F, 57.0F, 84.0F, 103.5F };

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitME_262()
  {
    super("3DO/Cockpit/Me-262/hier.him", "he111");

    this.cockpitNightMats = new String[] { "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", "petitfla", "turnbank" };

    setNightMats(false);

    this.setNew.dimPosition = 1.0F;

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON03");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON01");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON02");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON04");
    }

    if (this.fm.isTick(44, 0))
    {
      this.mesh.chunkVisible("Z_GearLGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
      this.mesh.chunkVisible("Z_GearRGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
      this.mesh.chunkVisible("Z_GearCGreen1", this.fm.CT.getGear() == 1.0F);
      this.mesh.chunkVisible("Z_GearLRed1", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
      this.mesh.chunkVisible("Z_GearRRed1", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
      this.mesh.chunkVisible("Z_GearCRed1", this.fm.CT.getGear() == 0.0F);

      if (!this.bU4) {
        this.mesh.chunkVisible("Z_GunLamp01", !this.gun[0].haveBullets());
        this.mesh.chunkVisible("Z_GunLamp02", !this.gun[1].haveBullets());
        this.mesh.chunkVisible("Z_GunLamp03", !this.gun[2].haveBullets());
        this.mesh.chunkVisible("Z_GunLamp04", !this.gun[3].haveBullets());
      }
      this.mesh.chunkVisible("Z_MachLamp", this.fm.getSpeed() / Atmosphere.sonicSpeed((float)this.fm.Loc.z) > 0.8F);
      this.mesh.chunkVisible("Z_CabinLamp", this.fm.Loc.z > 12000.0D);
      this.mesh.chunkVisible("Z_FuelLampV", this.fm.M.fuel < 300.0F);
      this.mesh.chunkVisible("Z_FuelLampIn", this.fm.M.fuel < 300.0F);
    }

    this.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    xyz[1] = ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F) ? -0.0107F : 0.0F);
    this.mesh.chunkSetLocate("Z_GearEin", xyz, ypr);
    xyz[1] = ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F) ? -0.0107F : 0.0F);
    this.mesh.chunkSetLocate("Z_GearAus", xyz, ypr);
    resetYPRmodifier();
    xyz[1] = (this.fm.CT.FlapsControl < this.fm.CT.getFlap() ? -0.0107F : 0.0F);
    this.mesh.chunkSetLocate("Z_FlapEin", xyz, ypr);
    xyz[1] = (this.fm.CT.FlapsControl > this.fm.CT.getFlap() ? -0.0107F : 0.0F);
    this.mesh.chunkSetLocate("Z_FlapAus", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Column", 10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[0] != 0) {
      xyz[2] = -0.0025F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton1", xyz, ypr);
    resetYPRmodifier();
    if ((this.fm.CT.saveWeaponControl[2] != 0) || (this.fm.CT.saveWeaponControl[3] != 0)) {
      xyz[2] = -0.00325F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton2", xyz, ypr);

    this.mesh.chunkSetAngles("Z_PedalStrut", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", -20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", -20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ThrottleL", 0.0F, -75.0F * interp(this.setNew.throttlel, this.setOld.throttlel, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_ThrottleR", 0.0F, -75.0F * interp(this.setNew.throttler, this.setOld.throttler, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelLeverL", this.fm.EI.engines[0].getControlMagnetos() == 3 ? 6.5F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelLeverR", this.fm.EI.engines[1].getControlMagnetos() == 3 ? 6.5F : 0.0F, 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = (0.03675F * this.fm.CT.getTrimElevatorControl());
    this.mesh.chunkSetLocate("Z_TailTrim", xyz, ypr);

    if ((this.fm.CT.Weapons[3] != null) && (!this.fm.CT.Weapons[3][0].haveBullets())) {
      this.mesh.chunkSetAngles("Z_Bombbutton", 0.0F, 53.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(this.gun[1].countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(this.gun[2].countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 100.0F, 400.0F, 2.0F, 8.0F), speedometerIndScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(this.fm.getSpeedKMH(), 100.0F, 1000.0F, 1.0F, 10.0F), speedometerTruScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 16000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank1", this.fm.Or.getTangage(), 0.0F, this.fm.Or.getKren());
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, -7.5F, 7.5F));
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F));

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -20.0F, 50.0F, 0.0F, 14.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPML", floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPMR", floatindex(cvt(this.fm.EI.engines[1].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_GasPressureL", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_GasPressureR", cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[1].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 273.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_GasTempL", cvt(this.fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_GasTempR", cvt(this.fm.EI.engines[1].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * this.fm.EI.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPressL", cvt(this.fm.M.fuel > 1.0F ? 80.0F * this.fm.EI.engines[0].getPowerOutput() * this.fm.EI.engines[0].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPressR", cvt(this.fm.M.fuel > 1.0F ? 80.0F * this.fm.EI.engines[1].getPowerOutput() * this.fm.EI.engines[1].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelRemainV", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelRemainIn", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AFN1", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AFN2", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.chunkVisible("XGlassDamage4", true);

      this.mesh.chunkVisible("Speedometer1", false);
      this.mesh.chunkVisible("Speedometer1_D1", true);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Speedometer2", false);

      this.mesh.chunkVisible("RPML", false);
      this.mesh.chunkVisible("RPML_D1", true);
      this.mesh.chunkVisible("Z_RPML", false);

      this.mesh.chunkVisible("FuelRemainV", false);
      this.mesh.chunkVisible("FuelRemainV_D1", true);
      this.mesh.chunkVisible("Z_FuelRemainV", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
      this.mesh.chunkVisible("XGlassDamage3", true);

      this.mesh.chunkVisible("Altimeter1", false);
      this.mesh.chunkVisible("Altimeter1_D1", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);

      this.mesh.chunkVisible("GasPressureL", false);
      this.mesh.chunkVisible("GasPressureL_D1", true);
      this.mesh.chunkVisible("Z_GasPressureL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("XGlassDamage4", true);

      this.mesh.chunkVisible("RPMR", false);
      this.mesh.chunkVisible("RPMR_D1", true);
      this.mesh.chunkVisible("Z_RPMR", false);

      this.mesh.chunkVisible("FuelPressR", false);
      this.mesh.chunkVisible("FuelPressR_D1", true);
      this.mesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("XGlassDamage3", true);

      this.mesh.chunkVisible("GasPressureR", false);
      this.mesh.chunkVisible("GasPressureR_D1", true);
      this.mesh.chunkVisible("Z_GasPressureR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);

      this.mesh.chunkVisible("Climb", false);
      this.mesh.chunkVisible("Climb_D1", true);
      this.mesh.chunkVisible("Z_Climb1", false);

      this.mesh.chunkVisible("FuelPressR", false);
      this.mesh.chunkVisible("FuelPressR_D1", true);
      this.mesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("HullDamage2", true);

      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Revi_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);

      this.mesh.chunkVisible("FuelPressL", false);
      this.mesh.chunkVisible("FuelPressL_D1", true);
      this.mesh.chunkVisible("Z_FuelPressL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);

      this.mesh.chunkVisible("Altimeter1", false);
      this.mesh.chunkVisible("Altimeter1_D1", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);

      this.mesh.chunkVisible("Climb", false);
      this.mesh.chunkVisible("Climb_D1", true);
      this.mesh.chunkVisible("Z_Climb1", false);

      this.mesh.chunkVisible("AFN", false);
      this.mesh.chunkVisible("AFN_D1", true);
      this.mesh.chunkVisible("Z_AFN1", false);
      this.mesh.chunkVisible("Z_AFN2", false);

      this.mesh.chunkVisible("FuelPressL", false);
      this.mesh.chunkVisible("FuelPressL_D1", true);
      this.mesh.chunkVisible("Z_FuelPressL", false);

      this.mesh.chunkVisible("FuelRemainIn", false);
      this.mesh.chunkVisible("FuelRemainIn_D1", true);
      this.mesh.chunkVisible("Z_FuelRemainIn", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0);
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    if ((aircraft() instanceof ME_262A1AU4)) {
      this.mesh.chunkVisible("Z_Ammo262U4", true);
      this.bU4 = true;
    }
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      setNightMats(true);
    }
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
      CockpitME_262.access$102(CockpitME_262.this, CockpitME_262.this.setOld); CockpitME_262.access$202(CockpitME_262.this, CockpitME_262.this.setNew); CockpitME_262.access$302(CockpitME_262.this, CockpitME_262.this.setTmp);

      CockpitME_262.this.setNew.altimeter = CockpitME_262.this.fm.getAltitude();
      CockpitME_262.this.setNew.throttlel = ((10.0F * CockpitME_262.this.setOld.throttlel + CockpitME_262.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitME_262.this.setNew.throttler = ((10.0F * CockpitME_262.this.setOld.throttler + CockpitME_262.this.fm.EI.engines[1].getControlThrottle()) / 11.0F);

      float f = CockpitME_262.this.waypointAzimuth();
      if (CockpitME_262.this.useRealisticNavigationInstruments())
      {
        CockpitME_262.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitME_262.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
      }
      else
      {
        CockpitME_262.this.setNew.waypointAzimuth.setDeg(CockpitME_262.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitME_262.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitME_262.this.setNew.azimuth.setDeg(CockpitME_262.this.setOld.azimuth.getDeg(1.0F), CockpitME_262.this.fm.Or.azimut());

      CockpitME_262.this.setNew.vspeed = ((299.0F * CockpitME_262.this.setOld.vspeed + CockpitME_262.this.fm.getVertSpeed()) / 300.0F);

      if (CockpitME_262.this.cockpitDimControl) {
        if (CockpitME_262.this.setNew.dimPosition > 0.0F) CockpitME_262.this.setNew.dimPosition = (CockpitME_262.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitME_262.this.setNew.dimPosition < 1.0F) CockpitME_262.this.setNew.dimPosition = (CockpitME_262.this.setOld.dimPosition + 0.05F);

      CockpitME_262.this.setNew.beaconDirection = ((10.0F * CockpitME_262.this.setOld.beaconDirection + CockpitME_262.this.getBeaconDirection()) / 11.0F);
      CockpitME_262.this.setNew.beaconRange = ((10.0F * CockpitME_262.this.setOld.beaconRange + CockpitME_262.this.getBeaconRange()) / 11.0F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttlel;
    float throttler;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    float beaconDirection;
    float beaconRange;
    private final CockpitME_262 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitME_262.1 arg2)
    {
      this();
    }
  }
}