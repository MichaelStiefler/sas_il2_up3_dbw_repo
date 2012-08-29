package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitJU_88A4torp_NGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictManf1;
  private float pictManf2;
  private float prevFuel;
  private boolean bBeaconKeysEnabled;
  private static final float[] speedometerScale = { 0.0F, 16.0F, 35.5F, 60.5F, 88.0F, 112.5F, 136.0F, 159.5F, 186.5F, 211.5F, 240.0F, 268.0F, 295.5F, 321.0F, 347.0F };

  private static final float[] speedometerScale2 = { 0.0F, 23.5F, 47.5F, 72.0F, 95.5F, 120.0F, 144.5F, 168.5F, 193.0F, 217.0F, 241.0F, 265.0F, 288.0F, 311.5F, 335.5F };

  private static final float[] frAirTempScale = { 76.5F, 68.0F, 57.0F, 44.5F, 29.5F, 14.5F, 1.5F, -10.0F, -19.0F };

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
      aircraft().hierMesh().chunkVisible("MGFF2", false);
      aircraft().hierMesh().chunkVisible("BlisterTop_D0", false);
      aircraft().hierMesh().chunkVisible("DummyBlister_D0", true);
      this.bBeaconKeysEnabled = ((AircraftLH)aircraft()).bWantBeaconKeys;
      ((AircraftLH)aircraft()).bWantBeaconKeys = true;
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (isFocused()) {
      aircraft().hierMesh().chunkVisible("fakeNose_D0", aircraft().hierMesh().isChunkVisible("Nose_D0"));
      aircraft().hierMesh().chunkVisible("fakeNose_D1", aircraft().hierMesh().isChunkVisible("Nose_D1"));
      aircraft().hierMesh().chunkVisible("fakeNose_D2", aircraft().hierMesh().isChunkVisible("Nose_D2"));
      aircraft().hierMesh().chunkVisible("fakeNose_D3", aircraft().hierMesh().isChunkVisible("Nose_D3"));
      aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
      aircraft().hierMesh().chunkVisible("MGFF2", true);
      aircraft().hierMesh().chunkVisible("BlisterTop_D0", true);
      aircraft().hierMesh().chunkVisible("DummyBlister_D0", false);
      ((AircraftLH)aircraft()).bWantBeaconKeys = this.bBeaconKeysEnabled;
      super.doFocusLeave();
    }
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("zTurret1A", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("zTurret1B", 0.0F, f2, 0.0F);
    if (f1 > 10.0F)
      f1 = 10.0F;
    if (f1 < -10.0F)
      f1 = -10.0F;
    if (f2 < -10.0F)
      f2 = -10.0F;
    if (f2 > 15.0F)
      f2 = 15.0F;
    if ((f1 <= 10.0F) && (f1 >= 0.0F) && (f2 < Aircraft.cvt(f1, 0.0F, 10.0F, -10.0F, 0.0F)))
      f2 = Aircraft.cvt(f1, 0.0F, 10.0F, -10.0F, 0.0F);
    this.mesh.chunkSetAngles("CameraRodA", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("CameraRodB", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode())
      return;
    if (!aiTurret().bIsOperable)
    {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -30.0F)
      f1 = -30.0F;
    if (f1 > 35.0F)
      f1 = 35.0F;
    if (f2 > 35.0F)
      f2 = 35.0F;
    if (f2 < -10.0F)
      f2 = -10.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    else
      this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public CockpitJU_88A4torp_NGunner()
  {
    super("3DO/Cockpit/Ju-88A-4-NGun/hierA4torp.him", "he111");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictManf1 = 1.0F;
    this.pictManf2 = 1.0F;
    this.prevFuel = 0.0F;
    this.cockpitNightMats = new String[] { "88a4_I_Set1", "88a4_I_Set2", "88a4_I_Set3", "88a4_I_Set4", "88a4_I_Set5", "88a4_I_Set6", "88a4_SlidingGlass", "88gardinen", "lofte7_02", "Peil1", "Pedal", "skala", "alt4" };

    setNightMats(false);
    this.setNew.dimPosition = (this.setOld.dimPosition = 0.0F);
    this.cockpitDimControl = (!this.cockpitDimControl);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    AircraftLH.printCompassHeading = true;
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    resetYPRmodifier();
    Cockpit.xyz[2] = (0.06815F * interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat));

    this.mesh.chunkSetLocate("Revisun", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("zColumn1", 7.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zColumn2", 52.200001F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalL", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalR", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("zTurret2A", 0.0F, this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);
    this.mesh.chunkSetAngles("zTurret3A", 0.0F, this.fm.turret[2].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret3B", 0.0F, this.fm.turret[2].tu[1], 0.0F);
    this.mesh.chunkSetAngles("zTurret4A", 0.0F, this.fm.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret4B", 0.0F, this.fm.turret[3].tu[1], 0.0F);
    resetYPRmodifier();
    float tmp392_391 = (0.85F * this.pictFlap + 0.00948F * this.fm.CT.FlapsControl); this.pictFlap = tmp392_391; Cockpit.xyz[2] = tmp392_391;
    this.mesh.chunkSetLocate("zFlaps1", Cockpit.xyz, Cockpit.ypr);
    float tmp438_437 = (0.85F * this.pictGear + 0.007095F * this.fm.CT.GearControl); this.pictGear = tmp438_437; Cockpit.xyz[2] = tmp438_437;
    this.mesh.chunkSetLocate("zGear1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1134F * this.setNew.prop1);
    this.mesh.chunkSetLocate("zPitch1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1134F * this.setNew.prop2);
    this.mesh.chunkSetLocate("zPitch2", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1031F * this.setNew.throttle1);
    this.mesh.chunkSetLocate("zThrottle1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1031F * this.setNew.throttle2);
    this.mesh.chunkSetLocate("zThrottle2", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Trim1", cvt(this.fm.CT.getTrimElevatorControl(), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F, 0.0F);

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
    this.mesh.chunkSetAngles("zAlt3", ((JU_88A4torp)aircraft()).fDiveRecoveryAlt * 360.0F / 6000.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt4", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 313.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 50.0F, 750.0F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);

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
    Cockpit.xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.045F, -0.045F);
    this.mesh.chunkSetLocate("zHORIZ2", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = (0.02125F * this.fm.CT.getTrimElevatorControl());
    this.mesh.chunkSetLocate("zTRIM1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.02125F * this.fm.CT.getTrimAileronControl());
    this.mesh.chunkSetLocate("zTRIM2", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.02125F * this.fm.CT.getTrimRudderControl());
    this.mesh.chunkSetLocate("zTRIM3", Cockpit.xyz, Cockpit.ypr);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);
    this.mesh.chunkVisible("XLampGearUpL", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearUpR", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearUpC", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("XLampGearDownC", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("XLampFlap1", this.fm.CT.getFlap() < 0.1F);
    this.mesh.chunkVisible("XLampFlap2", (this.fm.CT.getFlap() > 0.1F) && (this.fm.CT.getFlap() < 0.5F));
    this.mesh.chunkVisible("XLampFlap3", this.fm.CT.getFlap() > 0.5F);
    this.mesh.chunkVisible("XLamp1", false);
    this.mesh.chunkVisible("XLamp2", true);
    this.mesh.chunkVisible("XLamp3", true);
    this.mesh.chunkVisible("XLamp4", false);

    this.mesh.chunkSetAngles("zAH1", 0.0F, cvt(this.setNew.beaconDirection, -45.0F, 45.0F, 14.0F, -14.0F), 0.0F);
    this.mesh.chunkSetAngles("zAH2", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 26.5F, -26.5F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN1_RED", isOnBlindLandingMarker());
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(20.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x2) != 0))
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("XGlassDamage5", true);
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0))
      this.mesh.chunkVisible("XGlassDamage3", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XGlassDamage5", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage6", true);
    retoggleLight();
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);
    Property.set(CLASS.THIS(), "normZN", 0.75F);
    Property.set(CLASS.THIS(), "gsZN", 0.75F);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitJU_88A4torp_NGunner.this.fm != null)
      {
        CockpitJU_88A4torp_NGunner.access$002(CockpitJU_88A4torp_NGunner.this, CockpitJU_88A4torp_NGunner.this.setOld);
        CockpitJU_88A4torp_NGunner.access$102(CockpitJU_88A4torp_NGunner.this, CockpitJU_88A4torp_NGunner.this.setNew);
        CockpitJU_88A4torp_NGunner.access$202(CockpitJU_88A4torp_NGunner.this, CockpitJU_88A4torp_NGunner.this.setTmp);
        CockpitJU_88A4torp_NGunner.this.setNew.throttle1 = (0.85F * CockpitJU_88A4torp_NGunner.this.setOld.throttle1 + CockpitJU_88A4torp_NGunner.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitJU_88A4torp_NGunner.this.setNew.prop1 = (0.85F * CockpitJU_88A4torp_NGunner.this.setOld.prop1 + CockpitJU_88A4torp_NGunner.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitJU_88A4torp_NGunner.this.setNew.throttle2 = (0.85F * CockpitJU_88A4torp_NGunner.this.setOld.throttle2 + CockpitJU_88A4torp_NGunner.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitJU_88A4torp_NGunner.this.setNew.prop2 = (0.85F * CockpitJU_88A4torp_NGunner.this.setOld.prop2 + CockpitJU_88A4torp_NGunner.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitJU_88A4torp_NGunner.this.setNew.altimeter = CockpitJU_88A4torp_NGunner.this.fm.getAltitude();
        float f1 = CockpitJU_88A4torp_NGunner.this.waypointAzimuth();
        if (CockpitJU_88A4torp_NGunner.this.useRealisticNavigationInstruments())
        {
          CockpitJU_88A4torp_NGunner.this.setNew.waypointAzimuth.setDeg(f1 - 90.0F);
          CockpitJU_88A4torp_NGunner.this.setOld.waypointAzimuth.setDeg(f1 - 90.0F);
          CockpitJU_88A4torp_NGunner.this.setNew.radioCompassAzimuth.setDeg(CockpitJU_88A4torp_NGunner.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitJU_88A4torp_NGunner.this.radioCompassAzimuthInvertMinus() - CockpitJU_88A4torp_NGunner.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
        }
        else
        {
          CockpitJU_88A4torp_NGunner.this.setNew.waypointAzimuth.setDeg(CockpitJU_88A4torp_NGunner.this.setOld.waypointAzimuth.getDeg(0.1F), f1 - CockpitJU_88A4torp_NGunner.this.setOld.azimuth.getDeg(1.0F));
        }
        CockpitJU_88A4torp_NGunner.this.setNew.azimuth.setDeg(CockpitJU_88A4torp_NGunner.this.setOld.azimuth.getDeg(1.0F), CockpitJU_88A4torp_NGunner.this.fm.Or.azimut());

        CockpitJU_88A4torp_NGunner.this.setNew.vspeed = ((199.0F * CockpitJU_88A4torp_NGunner.this.setOld.vspeed + CockpitJU_88A4torp_NGunner.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitJU_88A4torp_NGunner.this.cockpitDimControl)
        {
          if (CockpitJU_88A4torp_NGunner.this.setNew.dimPosition > 0.0F)
            CockpitJU_88A4torp_NGunner.this.setNew.dimPosition -= 0.05F;
        }
        else if (CockpitJU_88A4torp_NGunner.this.setNew.dimPosition < 1.0F)
          CockpitJU_88A4torp_NGunner.this.setNew.dimPosition += 0.05F;
        float f2 = CockpitJU_88A4torp_NGunner.this.prevFuel - CockpitJU_88A4torp_NGunner.this.fm.M.fuel;
        CockpitJU_88A4torp_NGunner.access$302(CockpitJU_88A4torp_NGunner.this, CockpitJU_88A4torp_NGunner.this.fm.M.fuel);
        f2 /= 0.72F;
        f2 /= Time.tickLenFs();
        f2 *= 3600.0F;
        CockpitJU_88A4torp_NGunner.this.setNew.cons = (0.91F * CockpitJU_88A4torp_NGunner.this.setOld.cons + 0.09F * f2);
      }

      CockpitJU_88A4torp_NGunner.this.setNew.beaconDirection = ((10.0F * CockpitJU_88A4torp_NGunner.this.setOld.beaconDirection + CockpitJU_88A4torp_NGunner.this.getBeaconDirection()) / 11.0F);
      CockpitJU_88A4torp_NGunner.this.setNew.beaconRange = ((10.0F * CockpitJU_88A4torp_NGunner.this.setOld.beaconRange + CockpitJU_88A4torp_NGunner.this.getBeaconRange()) / 11.0F);

      return true;
    }

    Interpolater()
    {
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
    float beaconDirection;
    float beaconRange;
    private final CockpitJU_88A4torp_NGunner this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();

      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitJU_88A4torp_NGunner.1 arg2)
    {
      this();
    }
  }
}