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
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;

public class CockpitJU_88A4_RGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf1 = 1.0F;
  private float pictManf2 = 1.0F;
  private float prevFuel = 0.0F;
  protected SoundFX buzzerFX;
  private static final float[] speedometerScale = { 0.0F, 16.0F, 35.5F, 60.5F, 88.0F, 112.5F, 136.0F, 159.5F, 186.5F, 211.5F, 240.0F, 268.0F, 295.5F, 321.0F, 347.0F };

  private static final float[] speedometerScale2 = { 0.0F, 23.5F, 47.5F, 72.0F, 95.5F, 120.0F, 144.5F, 168.5F, 193.0F, 217.0F, 241.0F, 265.0F, 288.0F, 311.5F, 335.5F };

  private static final float[] frAirTempScale = { 76.5F, 68.0F, 57.0F, 44.5F, 29.5F, 14.5F, 1.5F, -10.0F, -19.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("fakeNose_D0", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D1", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D2", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D3", false);
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
      aircraft().hierMesh().chunkVisible("BlisterTop_D0", false);
      aircraft().hierMesh().chunkVisible("DummyBlister_D0", true);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    aircraft().hierMesh().chunkVisible("fakeNose_D0", aircraft().hierMesh().isChunkVisible("Nose_D0"));
    aircraft().hierMesh().chunkVisible("fakeNose_D1", aircraft().hierMesh().isChunkVisible("Nose_D1"));
    aircraft().hierMesh().chunkVisible("fakeNose_D2", aircraft().hierMesh().isChunkVisible("Nose_D2"));
    aircraft().hierMesh().chunkVisible("fakeNose_D3", aircraft().hierMesh().isChunkVisible("Nose_D3"));
    aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret2B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
    aircraft().hierMesh().chunkVisible("BlisterTop_D0", true);
    aircraft().hierMesh().chunkVisible("DummyBlister_D0", false);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("zTurret3A", 0.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("zTurret3B", 0.0F, f2, 0.0F);
    if (f1 > 10.0F) f1 = 10.0F;
    if (f1 < -25.0F) f1 = -25.0F;
    if (f2 < -10.0F) f2 = -10.0F;
    if (f2 > 15.0F) f2 = 15.0F;
    if ((f1 > 0.0F) && 
      (f2 < -4.5F)) {
      f2 = -4.5F;
    }

    this.mesh.chunkSetAngles("CameraRodA", 0.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("CameraRodB", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -45.0F) f1 = -45.0F;
    if (f1 > 25.0F) f1 = 25.0F;
    if (f2 > 60.0F) f2 = 60.0F;
    if (f2 < -10.0F) f2 = -10.0F;
    if (f1 < -2.0F) {
      if (f2 < cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F))
        f2 = cvt(f1, -6.8F, -2.0F, -10.0F, -2.99F);
    }
    else if (f1 < 0.5F) {
      if (f2 < cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F))
        f2 = cvt(f1, -2.0F, 0.5F, -2.99F, -2.3F);
    }
    else if (f1 < 5.3F) {
      if (f2 < cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F)) {
        f2 = cvt(f1, 0.5F, 5.3F, -2.3F, -2.3F);
      }
    }
    else if (f2 < cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F)) {
      f2 = cvt(f1, 5.3F, 25.0F, -2.3F, -7.2F);
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
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

  public CockpitJU_88A4_RGunner()
  {
    super("3DO/Cockpit/Ju-88A-4-RGun/hier.him", "he111");

    this.cockpitNightMats = new String[] { "88a4_I_Set1", "88a4_I_Set2", "88a4_I_Set3", "88a4_I_Set4", "88a4_I_Set5", "88a4_I_Set6", "88a4_SlidingGlass", "88gardinen", "lofte7_02", "Peil1", "Pedal", "skala" };

    setNightMats(false);
    this.setNew.dimPosition = (this.setOld.dimPosition = 1.0F);
    this.cockpitDimControl = (!this.cockpitDimControl);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    this.buzzerFX = aircraft().newSound("models.buzzthru", false);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Z_Trim1", cvt(this.fm.CT.getTrimElevatorControl(), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 130.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zColumn1", 7.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zColumn2", 52.200001F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = (-0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalL", xyz, ypr);
    xyz[2] = (0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalR", xyz, ypr);

    this.mesh.chunkSetAngles("zTurret1A", 0.0F, this.fm.turret[0].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret1B", 0.0F, this.fm.turret[0].tu[1], 0.0F);

    this.mesh.chunkSetAngles("zTurret2A", 0.0F, this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);

    this.mesh.chunkSetAngles("zTurret4A", 0.0F, this.fm.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret4B", 0.0F, this.fm.turret[3].tu[1], 0.0F);

    this.mesh.chunkSetAngles("z_Object20", cvt(((JU_88A4)aircraft()).fSightCurSpeed, 400.0F, 800.0F, 87.0F, -63.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TempMeter", floatindex(cvt(Atmosphere.temperature((float)this.fm.Loc.z), 213.09F, 293.09F, 0.0F, 8.0F), frAirTempScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Radiator_Sw1", cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Radiator_Sw2", cvt(this.fm.EI.engines[1].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSw1", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 100.0F, 0.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSw2", cvt(this.fm.EI.engines[1].getControlMagnetos(), 0.0F, 3.0F, 100.0F, 0.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zHour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMinute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zHour2", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMinute2", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 6000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt3", ((JU_88A4)aircraft()).fDiveRecoveryAlt * 360.0F / 6000.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 50.0F, 750.0F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zSpeed2", floatindex(cvt(this.fm.getSpeedKMH(), 50.0F, 750.0F, 0.0F, 14.0F), speedometerScale2), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -15.0F, 15.0F, -151.0F, 151.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zWaterTemp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 64.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zWaterTemp2", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 120.0F, 0.0F, 64.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilPress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.47F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zOilPress2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.47F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelPress1", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelPress2", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zRPM1", cvt(this.fm.EI.engines[0].getRPM(), 600.0F, 3600.0F, 0.0F, 331.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zRPM2", cvt(this.fm.EI.engines[1].getRPM(), 600.0F, 3600.0F, 0.0F, 331.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ATA1", this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ATA2", this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuel1", cvt(this.fm.M.fuel, 0.0F, 1008.0F, 0.0F, 77.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuel2", cvt(this.fm.M.fuel, 0.0F, 1008.0F, 0.0F, 77.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuelPress", cvt(this.setNew.cons, 100.0F, 500.0F, 0.0F, 240.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) - 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass5", this.setNew.radioCompassAzimuth.getDeg(paramFloat * 0.02F) + 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass7", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass8", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) + 90.0F, 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass5", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass7", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass8", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) + 90.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("zHORIZ1", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.045F, -0.045F);
    this.mesh.chunkSetLocate("zHORIZ2", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = (0.02125F * this.fm.CT.getTrimElevatorControl());
    this.mesh.chunkSetLocate("zTRIM1", xyz, ypr);
    xyz[2] = (0.02125F * this.fm.CT.getTrimAileronControl());
    this.mesh.chunkSetLocate("zTRIM2", xyz, ypr);
    xyz[2] = (0.02125F * this.fm.CT.getTrimRudderControl());
    this.mesh.chunkSetLocate("zTRIM3", xyz, ypr);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(20.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x2) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage5", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XGlassDamage5", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage6", true);
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 2);
    Property.set(CLASS.THIS(), "weaponControlNum", 12);
    Property.set(CLASS.THIS(), "astatePilotIndx", 2);
    Property.set(CLASS.THIS(), "normZN", 0.75F);
    Property.set(CLASS.THIS(), "gsZN", 0.75F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitJU_88A4_RGunner.this.fm != null) {
        CockpitJU_88A4_RGunner.access$102(CockpitJU_88A4_RGunner.this, CockpitJU_88A4_RGunner.this.setOld); CockpitJU_88A4_RGunner.access$202(CockpitJU_88A4_RGunner.this, CockpitJU_88A4_RGunner.this.setNew); CockpitJU_88A4_RGunner.access$302(CockpitJU_88A4_RGunner.this, CockpitJU_88A4_RGunner.this.setTmp);

        CockpitJU_88A4_RGunner.this.setNew.throttle1 = (0.85F * CockpitJU_88A4_RGunner.this.setOld.throttle1 + CockpitJU_88A4_RGunner.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitJU_88A4_RGunner.this.setNew.prop1 = (0.85F * CockpitJU_88A4_RGunner.this.setOld.prop1 + CockpitJU_88A4_RGunner.this.fm.EI.engines[0].getControlProp() * 0.15F);

        CockpitJU_88A4_RGunner.this.setNew.throttle2 = (0.85F * CockpitJU_88A4_RGunner.this.setOld.throttle2 + CockpitJU_88A4_RGunner.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitJU_88A4_RGunner.this.setNew.prop2 = (0.85F * CockpitJU_88A4_RGunner.this.setOld.prop2 + CockpitJU_88A4_RGunner.this.fm.EI.engines[1].getControlProp() * 0.15F);

        CockpitJU_88A4_RGunner.this.setNew.altimeter = CockpitJU_88A4_RGunner.this.fm.getAltitude();

        float f1 = CockpitJU_88A4_RGunner.this.waypointAzimuth();
        if (CockpitJU_88A4_RGunner.this.useRealisticNavigationInstruments())
        {
          CockpitJU_88A4_RGunner.this.setNew.waypointAzimuth.setDeg(f1 - 90.0F);
          CockpitJU_88A4_RGunner.this.setOld.waypointAzimuth.setDeg(f1 - 90.0F);
          CockpitJU_88A4_RGunner.this.setNew.radioCompassAzimuth.setDeg(CockpitJU_88A4_RGunner.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitJU_88A4_RGunner.this.radioCompassAzimuthInvertMinus() - CockpitJU_88A4_RGunner.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
        }
        else
        {
          CockpitJU_88A4_RGunner.this.setNew.waypointAzimuth.setDeg(CockpitJU_88A4_RGunner.this.setOld.waypointAzimuth.getDeg(0.1F), f1 - CockpitJU_88A4_RGunner.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitJU_88A4_RGunner.this.setNew.azimuth.setDeg(CockpitJU_88A4_RGunner.this.setOld.azimuth.getDeg(1.0F), CockpitJU_88A4_RGunner.this.fm.Or.azimut());

        CockpitJU_88A4_RGunner.this.setNew.vspeed = ((199.0F * CockpitJU_88A4_RGunner.this.setOld.vspeed + CockpitJU_88A4_RGunner.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitJU_88A4_RGunner.this.cockpitDimControl) {
          if (CockpitJU_88A4_RGunner.this.setNew.dimPosition > 0.0F) CockpitJU_88A4_RGunner.this.setNew.dimPosition = (CockpitJU_88A4_RGunner.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitJU_88A4_RGunner.this.setNew.dimPosition < 1.0F) CockpitJU_88A4_RGunner.this.setNew.dimPosition = (CockpitJU_88A4_RGunner.this.setOld.dimPosition + 0.05F);

        float f2 = CockpitJU_88A4_RGunner.this.prevFuel - CockpitJU_88A4_RGunner.this.fm.M.fuel;
        CockpitJU_88A4_RGunner.access$402(CockpitJU_88A4_RGunner.this, CockpitJU_88A4_RGunner.this.fm.M.fuel);
        f2 /= 0.72F;
        f2 /= Time.tickLenFs();
        f2 *= 3600.0F;
        CockpitJU_88A4_RGunner.this.setNew.cons = (0.91F * CockpitJU_88A4_RGunner.this.setOld.cons + 0.09F * f2);

        if (CockpitJU_88A4_RGunner.this.buzzerFX != null) {
          if ((CockpitJU_88A4_RGunner.this.fm.Loc.z < ((JU_88A4)CockpitJU_88A4_RGunner.this.aircraft()).fDiveRecoveryAlt) && (((JU_88A4)(JU_88A4)CockpitJU_88A4_RGunner.this.fm.actor).diveMechStage == 1))
            CockpitJU_88A4_RGunner.this.buzzerFX.play();
          else if (CockpitJU_88A4_RGunner.this.buzzerFX.isPlaying()) {
            CockpitJU_88A4_RGunner.this.buzzerFX.stop();
          }

        }

      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float prop1;
    float throttle2;
    float prop2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    float cons;
    AnglesFork radioCompassAzimuth;
    private final CockpitJU_88A4_RGunner this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();

      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitJU_88A4_RGunner.1 arg2)
    {
      this();
    }
  }
}