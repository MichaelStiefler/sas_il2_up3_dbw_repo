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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitME_163 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictRudd = 0.0F;
  private float pictThtl = 0.0F;
  private float pictFlap = 0.0F;
  private float pictTurbo = 0.0F;
  private float pictTout = 0.0F;
  private float pictCons = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerIndScale = { 0.0F, 0.0F, 0.0F, 17.0F, 35.5F, 57.5F, 76.0F, 95.0F, 112.0F };

  private static final float[] speedometerTruScale = { 0.0F, 32.75F, 65.5F, 98.25F, 131.0F, 164.0F, 200.0F, 237.0F, 270.5F, 304.0F, 336.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  public CockpitME_163()
  {
    super("3DO/Cockpit/Me-163B-1a/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "2petitsb_d1", "2petitsb", "aiguill1", "comptemu_d1", "comptemu", "petitfla_d1", "petitfla", "turnbank", "oxyregul", "oxygaug", "pompeco" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    this.setNew.dimPosition = 1.0F;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 40.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_GearLGreen1", (this.fm.CT.getGear() > 0.95F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearLRed1", (this.fm.CT.getGear() < 0.05F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearCGreen1", this.fm.CT.getGear() > 0.95F);
    this.mesh.chunkVisible("Z_GearCRed1", this.fm.CT.getGear() < 0.05F);

    this.mesh.chunkVisible("Z_GunLamp01", !aircraft().getGunByHookName("_CANNON01").haveBullets());
    this.mesh.chunkVisible("Z_GunLamp02", !aircraft().getGunByHookName("_CANNON02").haveBullets());

    this.mesh.chunkVisible("Z_FuelLampV", this.fm.M.fuel < 0.25F * this.fm.M.maxFuel);

    this.mesh.chunkSetAngles("zCompassOil1", cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -45.0F, 45.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zCompassOil2", 0.0F, 0.0F, cvt(this.fm.Or.getKren(), -45.0F, 45.0F, 45.0F, -45.0F));
    this.mesh.chunkSetAngles("zCompassOil3", 0.0F, -this.setNew.azimuth, 0.0F);

    if (this.fm.CT.getGear() - this.fm.CT.GearControl > 0.02F)
      f = 30.0F;
    else if (this.fm.CT.getGear() - this.fm.CT.GearControl < -0.02F)
      f = -30.0F;
    else {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("Z_Gear", f, 0.0F, 0.0F);
    if (!((ME_163B1A)aircraft()).bCartAttached) {
      this.mesh.chunkSetAngles("Z_Gearskid", 0.0F, 90.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Column", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 18.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 18.0F);

    resetYPRmodifier();
    if (this.fm.CT.WeaponControl[1] != 0) {
      xyz[2] = -0.0025F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton1", xyz, ypr);

    this.mesh.chunkSetAngles("Z_PedalStrutL", -25.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PedalStrutR", -25.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", -25.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", -25.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle", -30.0F + 60.0F * (this.pictThtl = 0.72F * this.pictThtl + 0.21F * this.fm.CT.PowerControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle1", -30.0F + 60.0F * this.pictThtl, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Trimlever", -6200.0F * this.fm.CT.trimElevator, 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = (-0.01825F * this.fm.CT.trimElevator);
    this.mesh.chunkSetLocate("Z_Trimposit", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Flaplever", 150.0F * (this.pictFlap = 0.6F * this.pictFlap + 0.4F * this.fm.CT.FlapsControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AroneleverL", 30.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AroneleverR", -30.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AroneL", (this.pictAiler < 0.0F ? 25.0F : 35.0F) * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AroneR", (this.pictAiler < 0.0F ? -35.0F : -25.0F) * this.pictAiler, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(aircraft().getGunByHookName("_CANNON01").countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(aircraft().getGunByHookName("_CANNON02").countBullets(), 0.0F, 100.0F, 0.0F, -7.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank1", this.fm.Or.getTangage(), 0.0F, this.fm.Or.getKren());
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, -7.5F, 7.5F));
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F));

    if (this.setNew.vspeed < 0.0F)
      f = cvt(this.setNew.vspeed, -25.0F, 0.0F, -45.0F, 0.0F);
    else if (this.setNew.vspeed < 100.0F)
      f = cvt(this.setNew.vspeed, 0.0F, 100.0F, 0.0F, 180.0F);
    else if (this.setNew.vspeed < 125.0F)
      f = cvt(this.setNew.vspeed, 100.0F, 125.0F, 180.0F, 223.0F);
    else {
      f = cvt(this.setNew.vspeed, 125.0F, 150.0F, 223.0F, 258.0F);
    }
    this.mesh.chunkSetAngles("Z_Climb1", f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 100.0F, 400.0F, 2.0F, 8.0F), speedometerIndScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speed2", floatindex(cvt(this.fm.getSpeedKMH(), 100.0F, 1000.0F, 1.0F, 10.0F), speedometerTruScale), 0.0F, 0.0F);

    if (this.fm.EI.engines[0].getReadyness() > 0.0F) {
      f = 6022.0F;
      if (this.fm.EI.engines[0].getPowerOutput() > 0.0F)
        f = 8200.0F;
    }
    else {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("RPM", floatindex(cvt(this.pictTurbo = 0.92F * this.pictTurbo + 0.08F * f, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_FuelPress1", cvt(this.pictTurbo, 0.0F, 10000.0F, 0.0F, 270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPress2", cvt(this.pictTout = 0.92F * this.pictTout + 0.08F * this.fm.EI.engines[0].getPowerOutput(), 0.0F, 1.1F, 0.0F, 270.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 16000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAltCtr2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    float f = (this.setOld.fuel - this.setNew.fuel) / Time.tickLenFs() * 60.0F;
    this.mesh.chunkSetAngles("Z_Fuelconsum1", cvt(this.pictCons = 0.75F * this.pictCons + 0.25F * f, 0.0F, 20.0F, 0.0F, 282.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 1000.0F, 0.0F, 85.0F), 0.0F, 0.0F);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XGlassDamage5", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("HullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage2", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("HullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
    }
    retoggleLight();
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
      if (CockpitME_163.this.bNeedSetUp) {
        CockpitME_163.this.reflectPlaneMats();
        CockpitME_163.access$102(CockpitME_163.this, false);
      }
      if (CockpitME_163.this.fm != null) {
        CockpitME_163.access$202(CockpitME_163.this, CockpitME_163.this.setOld); CockpitME_163.access$302(CockpitME_163.this, CockpitME_163.this.setNew); CockpitME_163.access$402(CockpitME_163.this, CockpitME_163.this.setTmp);
        if (Math.abs(CockpitME_163.this.fm.Or.getKren()) < 80.0F) {
          CockpitME_163.this.setNew.azimuth = ((35.0F * CockpitME_163.this.setOld.azimuth + -CockpitME_163.this.fm.Or.getYaw()) / 36.0F);
        }
        CockpitME_163.this.setNew.vspeed = ((98.0F * CockpitME_163.this.setOld.vspeed + CockpitME_163.this.fm.getVertSpeed()) / 99.0F);

        CockpitME_163.this.setNew.altimeter = CockpitME_163.this.fm.getAltitude();
        CockpitME_163.this.setNew.fuel = CockpitME_163.this.fm.M.fuel;
        if (CockpitME_163.this.cockpitDimControl) {
          if (CockpitME_163.this.setNew.dimPosition > 0.0F) CockpitME_163.this.setNew.dimPosition = (CockpitME_163.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitME_163.this.setNew.dimPosition < 1.0F) CockpitME_163.this.setNew.dimPosition = (CockpitME_163.this.setOld.dimPosition + 0.05F);

      }

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float azimuth;
    float vspeed;
    float fuel;
    float dimPosition;
    private final CockpitME_163 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitME_163.1 arg2) { this();
    }
  }
}