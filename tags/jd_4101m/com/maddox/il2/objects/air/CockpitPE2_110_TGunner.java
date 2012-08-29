package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitPE2_110_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private boolean[] bBombs = { false, false, false, false, false, false, false, false };
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private static final float[] speedometerScale;
  private Hook hook1 = null;

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

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkVisible("WireL_D0", aircraft().hierMesh().isChunkVisible("WireL_D0"));
    this.mesh.chunkVisible("WireR_D0", aircraft().hierMesh().isChunkVisible("WireR_D0"));

    this.mesh.chunkSetAngles("Z_Gear1", this.fm.CT.GearControl > 0.5F ? -60.0F : 0.0F, 0.0F, 0.0F);

    resetYPRmodifier();
    xyz[1] = (-0.095F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Columnbase", -8.0F * (this.pictElev = 0.65F * this.pictElev + 0.35F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", -45.0F * (this.pictAiler = 0.65F * this.pictAiler + 0.35F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Brake", 21.5F * this.fm.CT.BrakeControl, 0.0F, 0.0F);
    resetYPRmodifier();

    resetYPRmodifier();
    if (this.fm.EI.engines[0].getControlRadiator() > 0.5F) {
      xyz[1] = 0.011F;
    }
    this.mesh.chunkSetLocate("Z_RadL", xyz, ypr);
    resetYPRmodifier();
    if (this.fm.EI.engines[1].getControlRadiator() > 0.5F) {
      xyz[1] = 0.011F;
    }
    this.mesh.chunkSetLocate("Z_RadR", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter3", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter4", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, -7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer2", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.018F, -0.018F);
    this.mesh.chunkSetLocate("Z_TurnBank1", xyz, ypr);
    this.mesh.chunkSetAngles("Z_TurnBank1Q", -this.fm.Or.getKren(), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(this.w.z, -0.23562F, 0.23562F, -27.0F, 27.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", cvt(this.setNew.waypointAzimuth.getDeg(paramFloat), -90.0F, 90.0F, -55.5F, 55.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM2", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -3600.0F), 0.0F, 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_RPM3", cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_RPM4", cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, -3600.0F), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 360.0F, 0.0F, -198.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_InertGas", cvt(this.setNew.inert, 0.0F, 1.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    float f = 0.0F;
    if (this.fm.M.fuel > 1.0F) {
      f = cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 570.0F, 0.0F, 0.26F);
    }
    this.mesh.chunkSetAngles("Z_FuelPres1", cvt(f, 0.0F, 1.0F, 0.0F, -270.0F), 0.0F, 0.0F);
    f = 0.0F;
    if (this.fm.M.fuel > 1.0F) {
      f = cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 570.0F, 0.0F, 0.26F);
    }
    this.mesh.chunkSetAngles("Z_FuelPres2", cvt(f, 0.0F, 1.0F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.setNew.man1, 0.399966F, 1.599864F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pres2", cvt(this.setNew.man2, 0.399966F, 1.599864F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil2", cvt(this.fm.EI.engines[1].tOilIn, 0.0F, 160.0F, 0.0F, -75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AirPres", -116.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OxPres", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_HPres", this.fm.Gears.isHydroOperable() ? -102.0F : 0.0F, 0.0F, 0.0F);
    f = Atmosphere.temperature((float)this.fm.Loc.z) - 273.09F;
    if (f < -40.0F)
      this.mesh.chunkSetAngles("Z_AirTemp", cvt(f, -40.0F, -20.0F, 52.0F, 35.0F), 0.0F, 0.0F);
    else if (f < 0.0F)
      this.mesh.chunkSetAngles("Z_AirTemp", cvt(f, -20.0F, 0.0F, 35.0F, 0.0F), 0.0F, 0.0F);
    else if (f < 20.0F)
      this.mesh.chunkSetAngles("Z_AirTemp", cvt(f, 0.0F, 20.0F, 0.0F, -18.5F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_AirTemp", cvt(f, 20.0F, 50.0F, -18.5F, -37.0F), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_FlapPos", cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, -180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass4", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);

    this.mesh.chunkVisible("XRGearUp", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLGearUp", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("XCGearUp", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("XRGearDn", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLGearDn", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XCGearDn", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("XFlapsUp", this.fm.CT.getFlap() > 0.5F);
    this.mesh.chunkVisible("XOverG1", this.fm.getOverload() > 3.0F);
    this.mesh.chunkVisible("XOverG2", this.fm.getOverload() < -1.0F);

    this.mesh.chunkVisible("XERimCenter", Math.abs(this.fm.CT.getTrimElevatorControl()) < 0.02F);

    this.mesh.chunkVisible("XBomb1", this.bBombs[0]);
    this.mesh.chunkVisible("XBomb2", this.bBombs[1]);
    this.mesh.chunkVisible("XBomb3", this.bBombs[2]);
    this.mesh.chunkVisible("XBomb4", this.bBombs[3]);
    this.mesh.chunkVisible("XBomb5", this.bBombs[4]);
    this.mesh.chunkVisible("XBomb6", this.bBombs[5]);
    this.mesh.chunkVisible("XBomb7", this.bBombs[6]);
    this.mesh.chunkVisible("XBomb8", this.bBombs[7]);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("NDetails_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("NDetails_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurrelA", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TurrelB", -paramOrient.getTangage(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TurrelC", -cvt(paramOrient.getTangage(), -1.0F, 45.0F, -1.0F, 45.0F), 0.0F, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -110.0F) f1 = -110.0F;
    if (f1 > 88.0F) f1 = 88.0F;
    if (f2 > 55.0F) f2 = 55.0F;
    if (f2 < -1.0F) f2 = -1.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    if (this.bGunFire) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN01");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN01");
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }
    else this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Fuel1", false);
      this.mesh.chunkVisible("Z_Pres1", false);
      this.mesh.chunkVisible("Z_Altimeter3", false);
      this.mesh.chunkVisible("Z_Altimeter4", false);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage5", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage6", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x2) != 0) && 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", false);
      this.mesh.chunkVisible("Panel_D2", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Speedometer2", false);
      this.mesh.chunkVisible("Z_AirTemp", false);
      this.mesh.chunkVisible("Z_Pres2", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_RPM2", false);
      this.mesh.chunkVisible("Z_InertGas", false);
      this.mesh.chunkVisible("Z_FuelPres2", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_Oilpres2", false);
    }

    retoggleLight();
  }

  public CockpitPE2_110_TGunner()
  {
    super("3DO/Cockpit/Pe-2series110-TGun/hier.him", "he111");

    this.cockpitNightMats = new String[] { "GP_I", "GP_II", "GP_II_DM", "GP_III_DM", "GP_III", "GP_IV_DM", "GP_IV", "GP_V", "GP_VI", "GP_VII", "compass", "Eqpt_II", "Trans_II", "Trans_VI_Pilot", "Trans_VII_Pilot" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);

    speedometerScale = new float[] { 0.0F, 0.0F, 10.5F, 42.5F, 85.0F, 125.0F, 165.5F, 181.0F, 198.0F, 214.5F, 231.0F, 249.0F, 266.5F, 287.5F, 308.0F, 326.5F, 346.0F };
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitPE2_110_TGunner.this.fm != null)
      {
        CockpitPE2_110_TGunner.access$102(CockpitPE2_110_TGunner.this, CockpitPE2_110_TGunner.this.setOld); CockpitPE2_110_TGunner.access$202(CockpitPE2_110_TGunner.this, CockpitPE2_110_TGunner.this.setNew); CockpitPE2_110_TGunner.access$302(CockpitPE2_110_TGunner.this, CockpitPE2_110_TGunner.this.setTmp);
        if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3] != null) {
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 0) {
            CockpitPE2_110_TGunner.this.bBombs[1] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][0].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 1) {
            CockpitPE2_110_TGunner.this.bBombs[0] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][1].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 2) {
            CockpitPE2_110_TGunner.this.bBombs[3] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][2].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 3) {
            CockpitPE2_110_TGunner.this.bBombs[2] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][3].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 4) {
            CockpitPE2_110_TGunner.this.bBombs[5] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][4].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 5) {
            CockpitPE2_110_TGunner.this.bBombs[4] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][5].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 6) {
            CockpitPE2_110_TGunner.this.bBombs[5] = ((CockpitPE2_110_TGunner.this.bBombs[5] != 0) || (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][6].haveBullets()) ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 7) {
            CockpitPE2_110_TGunner.this.bBombs[4] = ((CockpitPE2_110_TGunner.this.bBombs[4] != 0) || (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][7].haveBullets()) ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 8) {
            CockpitPE2_110_TGunner.this.bBombs[7] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][8].haveBullets() ? 1 : 0);
          }
          if (CockpitPE2_110_TGunner.this.fm.CT.Weapons[3].length > 9) {
            CockpitPE2_110_TGunner.this.bBombs[6] = (!CockpitPE2_110_TGunner.this.fm.CT.Weapons[3][9].haveBullets() ? 1 : 0);
          }

        }

        CockpitPE2_110_TGunner.this.setNew.man1 = (0.92F * CockpitPE2_110_TGunner.this.setOld.man1 + 0.08F * CockpitPE2_110_TGunner.this.fm.EI.engines[0].getManifoldPressure());

        CockpitPE2_110_TGunner.this.setNew.man2 = (0.92F * CockpitPE2_110_TGunner.this.setOld.man2 + 0.08F * CockpitPE2_110_TGunner.this.fm.EI.engines[1].getManifoldPressure());
        CockpitPE2_110_TGunner.this.setNew.altimeter = CockpitPE2_110_TGunner.this.fm.getAltitude();

        CockpitPE2_110_TGunner.this.setNew.azimuth.setDeg(CockpitPE2_110_TGunner.this.setOld.azimuth.getDeg(1.0F), CockpitPE2_110_TGunner.this.fm.Or.azimut());

        CockpitPE2_110_TGunner.this.setNew.vspeed = ((100.0F * CockpitPE2_110_TGunner.this.setOld.vspeed + CockpitPE2_110_TGunner.this.fm.getVertSpeed()) / 101.0F);

        if (CockpitPE2_110_TGunner.this.useRealisticNavigationInstruments())
        {
          CockpitPE2_110_TGunner.this.setNew.waypointAzimuth.setDeg(CockpitPE2_110_TGunner.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitPE2_110_TGunner.this.getBeaconDirection());
        }
        else
        {
          CockpitPE2_110_TGunner.this.setNew.waypointAzimuth.setDeg(CockpitPE2_110_TGunner.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitPE2_110_TGunner.this.waypointAzimuth() - CockpitPE2_110_TGunner.this.setOld.azimuth.getDeg(1.0F));
        }

        CockpitPE2_110_TGunner.this.setNew.inert = (0.999F * CockpitPE2_110_TGunner.this.setOld.inert + 0.001F * (CockpitPE2_110_TGunner.this.fm.EI.engines[0].getStage() == 6 ? 0.867F : 0.0F));
      }

      return true;
    }
  }

  private class Variables
  {
    float man1;
    float man2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float inert;
    private final CockpitPE2_110_TGunner this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitPE2_110_TGunner.1 arg2)
    {
      this();
    }
  }
}