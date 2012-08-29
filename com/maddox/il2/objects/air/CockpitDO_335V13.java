package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitDO_335V13 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictTriA = 0.0F;
  private float pictTriE = 0.0F;
  private float pictTriR = 0.0F;

  private float pictManf1 = 1.0F;
  private float pictManf2 = 1.0F;

  private BulletEmitter[] gun = { null, null, null, null, null };

  private static final float[] speedometerScale = { 0.0F, 21.0F, 69.5F, 116.0F, 163.0F, 215.5F, 266.5F, 318.5F, 378.0F, 430.5F };

  private static final float[] variometerScale = { 0.0F, 47.0F, 82.0F, 97.0F, 112.0F, 111.7F, 132.0F };

  private static final float[] rpmScale = { 0.0F, 2.5F, 19.0F, 50.5F, 102.5F, 173.0F, 227.0F, 266.5F, 297.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitDO_335V13()
  {
    super("3DO/Cockpit/Do-335V-13/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "1", "2", "3", "4", "5", "8", "9", "11", "alt_km", "kompass", "ok42", "D1", "D2", "D3", "D4", "D5", "D8", "D9" };

    setNightMats(false);
    this.setNew.dimPosition = 1.0F;
    this.cockpitDimControl = (!this.cockpitDimControl);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    AircraftLH.printCompassHeading = true;
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN03");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN04");
      this.gun[4] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN05");
    }

    this.mesh.chunkSetAngles("Revisun", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 12.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", -45.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbutton1", this.fm.CT.saveWeaponControl[0] != 0 ? -14.5F : 0.0F, 0.0F, 0.0F);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[1] != 0) {
      xyz[2] = -0.005F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton2", xyz, ypr);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[3] != 0) {
      xyz[2] = -0.0035F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton3", xyz, ypr);
    resetYPRmodifier();

    this.mesh.chunkSetLocate("Z_Columnbutton4", xyz, ypr);
    resetYPRmodifier();

    this.mesh.chunkSetLocate("Z_Columnbutton5", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = (0.12F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_LeftPedal", xyz, ypr);
    this.mesh.chunkSetAngles("Z_LeftPedal1", -32.700001F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    xyz[2] = (-0.12F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal", xyz, ypr);
    this.mesh.chunkSetAngles("Z_RightPedal1", -32.700001F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle1", 37.279999F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle2", 37.279999F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ThrottleLock", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 34.369999F * interp(this.setNew.mix1, this.setOld.mix1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture2", 34.369999F * interp(this.setNew.mix2, this.setOld.mix2, paramFloat), 0.0F, 0.0F);
    if (this.fm.EI.engines[0].getControlFeather() == 0)
      this.mesh.chunkSetAngles("Z_Pitch1", 23.0F + 46.5F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Pitch1", 0.0F, 0.0F, 0.0F);
    }
    if (this.fm.EI.engines[1].getControlFeather() == 0)
      this.mesh.chunkSetAngles("Z_Pitch2", 23.0F + 46.5F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Pitch2", 0.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Radiat1", 30.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Radiat2", 30.0F * this.fm.EI.engines[1].getControlRadiator(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear", 50.0F * this.fm.CT.GearControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap", 50.0F * this.fm.CT.FlapsControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Magneto1", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Magneto2", cvt(this.fm.EI.engines[1].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = (0.065F * this.fm.CT.BayDoorControl);
    this.mesh.chunkSetLocate("Z_BombBay", xyz, ypr);

    resetYPRmodifier();
    xyz[2] = cvt(this.gun[0].countBullets(), 0.0F, 500.0F, -0.00465F, 0.04175F);
    this.mesh.chunkSetLocate("Z_AmmoCounter1", xyz, ypr);
    xyz[2] = cvt(this.gun[2].countBullets(), 0.0F, 100.0F, -0.00465F, 0.04175F);
    this.mesh.chunkSetLocate("Z_AmmoCounter2", xyz, ypr);
    xyz[2] = cvt(this.gun[1].countBullets(), 0.0F, 500.0F, -0.00465F, 0.04175F);
    this.mesh.chunkSetLocate("Z_AmmoCounter3", xyz, ypr);
    xyz[2] = cvt(this.gun[3].countBullets(), 0.0F, 100.0F, -0.00465F, 0.04175F);
    this.mesh.chunkSetLocate("Z_AmmoCounter4", xyz, ypr);
    xyz[2] = cvt(this.gun[4].countBullets(), 0.0F, 100.0F, -0.00465F, 0.04175F);
    this.mesh.chunkSetLocate("Z_AmmoCounter5", xyz, ypr);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Compass2", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass1", this.setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", 90.0F - this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", -this.setNew.waypointAzimuth.getDeg(0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", 90.0F - this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 33.0F, -33.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.024F, -0.024F);
    this.mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    float f = this.setNew.vspeed > 0.0F ? 1.0F : -1.0F;
    this.mesh.chunkSetAngles("Z_Climb1", f * floatindex(cvt(Math.abs(this.setNew.vspeed), 0.0F, 30.0F, 0.0F, 6.0F), variometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("RPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("RPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ATA1", this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ATA2", this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oiltemp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oiltemp2", cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 864.0F, 0.0F, 80.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuelpress1", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuelpress2", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpress2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_NeedPitch1", 270.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedPitch2", 105.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedPitch3", 270.0F - (float)Math.toDegrees(this.fm.EI.engines[1].getPropPhi() - this.fm.EI.engines[1].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_NeedPitch4", 105.0F - (float)Math.toDegrees(this.fm.EI.engines[1].getPropPhi() - this.fm.EI.engines[1].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp3", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 213.09F, 313.09F, -45.0F, 30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("z_Alt1", cvt(interp(this.setNew.radioalt, this.setOld.radioalt, paramFloat), 0.0F, 750.0F, 0.0F, 232.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("z_Alt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("z_Alt3", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 315.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("z_HydroPress1", this.fm.Gears.isHydroOperable() ? 130.0F : 0.0F, 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_GearLGreen", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearRGreen", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("Z_GearCGreen", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("Z_GearLRed", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearRRed", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("Z_GearCRed", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("Z_FlapCombat", this.fm.CT.getFlap() > 0.1F);
    this.mesh.chunkVisible("Z_FlapTakeOff", this.fm.CT.getFlap() > 0.265F);
    this.mesh.chunkVisible("Z_FlapLanding", this.fm.CT.getFlap() > 0.665F);
    this.mesh.chunkVisible("Z_Ammo1", this.gun[0].countBullets() == 0);
    this.mesh.chunkVisible("Z_Ammo2", this.gun[2].countBullets() == 0);
    this.mesh.chunkVisible("Z_Ammo3", this.gun[1].countBullets() == 0);
    this.mesh.chunkVisible("Z_Ammo4", this.gun[3].countBullets() == 0);
    this.mesh.chunkVisible("Z_Ammo5", this.gun[4].countBullets() == 0);
    this.mesh.chunkVisible("Z_Stall", this.fm.getSpeedKMH() < 145.0F);
    this.mesh.chunkVisible("Z_FuelWhite", true);
    this.mesh.chunkVisible("Z_FuelRed", this.fm.M.fuel < 256.07999F);
    this.mesh.chunkVisible("Z_Warning1", false);
    this.mesh.chunkVisible("Z_Warning2", false);
    this.mesh.chunkVisible("Z_Warning3", false);
    this.mesh.chunkVisible("Z_Warning4", false);
    this.mesh.chunkVisible("Z_Warning5", false);
    this.mesh.chunkVisible("Z_Warning6", false);
    this.mesh.chunkVisible("Z_Warning7", false);
    this.mesh.chunkVisible("Z_Warning8", false);
    this.mesh.chunkVisible("Z_Warning9", false);

    this.mesh.chunkSetAngles("Z_AFN22", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AFN21", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(15.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }

    retoggleLight();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      setNightMats(true);
    }
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
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
      if (CockpitDO_335V13.this.fm != null) {
        CockpitDO_335V13.access$102(CockpitDO_335V13.this, CockpitDO_335V13.this.setOld); CockpitDO_335V13.access$202(CockpitDO_335V13.this, CockpitDO_335V13.this.setNew); CockpitDO_335V13.access$302(CockpitDO_335V13.this, CockpitDO_335V13.this.setTmp);

        CockpitDO_335V13.this.setNew.throttle1 = (0.85F * CockpitDO_335V13.this.setOld.throttle1 + CockpitDO_335V13.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitDO_335V13.this.setNew.prop1 = (0.85F * CockpitDO_335V13.this.setOld.prop1 + CockpitDO_335V13.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitDO_335V13.this.setNew.mix1 = (0.85F * CockpitDO_335V13.this.setOld.mix1 + CockpitDO_335V13.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitDO_335V13.this.setNew.throttle2 = (0.85F * CockpitDO_335V13.this.setOld.throttle2 + CockpitDO_335V13.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitDO_335V13.this.setNew.prop2 = (0.85F * CockpitDO_335V13.this.setOld.prop2 + CockpitDO_335V13.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitDO_335V13.this.setNew.mix2 = (0.85F * CockpitDO_335V13.this.setOld.mix2 + CockpitDO_335V13.this.fm.EI.engines[1].getControlMix() * 0.15F);
        CockpitDO_335V13.this.setNew.altimeter = CockpitDO_335V13.this.fm.getAltitude();

        float f = CockpitDO_335V13.this.waypointAzimuth();
        if (CockpitDO_335V13.this.useRealisticNavigationInstruments())
        {
          CockpitDO_335V13.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitDO_335V13.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitDO_335V13.this.setNew.waypointAzimuth.setDeg(CockpitDO_335V13.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitDO_335V13.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitDO_335V13.this.setNew.azimuth.setDeg(CockpitDO_335V13.this.setOld.azimuth.getDeg(1.0F), CockpitDO_335V13.this.fm.Or.azimut());

        CockpitDO_335V13.this.setNew.vspeed = ((199.0F * CockpitDO_335V13.this.setOld.vspeed + CockpitDO_335V13.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitDO_335V13.this.cockpitDimControl) {
          if (CockpitDO_335V13.this.setNew.dimPosition > 0.0F) CockpitDO_335V13.this.setNew.dimPosition = (CockpitDO_335V13.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitDO_335V13.this.setNew.dimPosition < 1.0F) CockpitDO_335V13.this.setNew.dimPosition = (CockpitDO_335V13.this.setOld.dimPosition + 0.05F);

        World.cur(); World.land(); CockpitDO_335V13.this.setNew.radioalt = (0.9F * CockpitDO_335V13.this.setOld.radioalt + 0.1F * (CockpitDO_335V13.this.fm.getAltitude() - Landscape.HQ_Air((float)CockpitDO_335V13.this.fm.Loc.x, (float)CockpitDO_335V13.this.fm.Loc.y)));
      }

      CockpitDO_335V13.this.setNew.beaconDirection = ((10.0F * CockpitDO_335V13.this.setOld.beaconDirection + CockpitDO_335V13.this.getBeaconDirection()) / 11.0F);
      CockpitDO_335V13.this.setNew.beaconRange = ((10.0F * CockpitDO_335V13.this.setOld.beaconRange + CockpitDO_335V13.this.getBeaconRange()) / 11.0F);

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float prop1;
    float mix1;
    float throttle2;
    float prop2;
    float mix2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    float radioalt;
    float beaconDirection;
    float beaconRange;
    private final CockpitDO_335V13 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitDO_335V13.1 arg2)
    {
      this();
    }
  }
}