package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
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

public class CockpitJU_88A4_BGunnerTorp extends CockpitGunner
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
  protected SoundFX buzzerFX;
  private static final float[] speedometerScale = { 0.0F, 16.0F, 35.5F, 60.5F, 88.0F, 112.5F, 136.0F, 159.5F, 186.5F, 211.5F, 240.0F, 268.0F, 295.5F, 321.0F, 347.0F };

  private static final float[] speedometerScale2 = { 0.0F, 23.5F, 47.5F, 72.0F, 95.5F, 120.0F, 144.5F, 168.5F, 193.0F, 217.0F, 241.0F, 265.0F, 288.0F, 311.5F, 335.5F };

  private static final float[] frAirTempScale = { 76.5F, 68.0F, 57.0F, 44.5F, 29.5F, 14.5F, 1.5F, -10.0F, -19.0F };
  private Point3d tmpP;
  private Vector3d tmpV;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("fakeNose_D0", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D1", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D2", false);
      aircraft().hierMesh().chunkVisible("fakeNose_D3", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (!isFocused())
    {
      return;
    }

    aircraft().hierMesh().chunkVisible("fakeNose_D0", aircraft().hierMesh().isChunkVisible("Nose_D0"));
    aircraft().hierMesh().chunkVisible("fakeNose_D1", aircraft().hierMesh().isChunkVisible("Nose_D1"));
    aircraft().hierMesh().chunkVisible("fakeNose_D2", aircraft().hierMesh().isChunkVisible("Nose_D2"));
    aircraft().hierMesh().chunkVisible("fakeNose_D3", aircraft().hierMesh().isChunkVisible("Nose_D3"));
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret4A", 0.0F, -f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret4B", 0.0F, f2, 0.0F);
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
    if (f1 < -35.0F)
      f1 = -35.0F;
    if (f1 > 35.0F)
      f1 = 35.0F;
    if (f2 > -0.48F)
      f2 = -0.48F;
    if (f2 < -35.0F)
      f2 = -35.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.jdField_bGunFire_of_type_Boolean = false;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.jdField_bGunFire_of_type_Boolean = false;
    else
      this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  public CockpitJU_88A4_BGunnerTorp()
  {
    super("3DO/Cockpit/Ju-88A-4-BGun/hier.him", "he111");
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
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.cockpitNightMats = new String[] { "88a4_I_Set2", "88a4_I_Set3", "88a4_I_Set4", "88a4_I_Set6", "88a4_SlidingGlass", "88gardinen", "Peil1", "Peil2", "skala" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics != null)
      this.jdField_acoustics_of_type_ComMaddoxSoundAcoustics.globFX = new ReverbFXRoom(0.45F);
    this.buzzerFX = aircraft().newSound("models.buzzthru", false);
  }

  public void toggleDim()
  {
    this.jdField_cockpitDimControl_of_type_Boolean = (!this.jdField_cockpitDimControl_of_type_Boolean);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Trim1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl(), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn1", 7.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn2", 52.200001F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.1F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zPedalL", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.1F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zPedalR", Cockpit.xyz, Cockpit.ypr);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret1A", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret1B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[1], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret2A", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret2B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[1].tu[1], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret4A", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zTurret4B", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.turret[3].tu[1], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zHour2", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMinute2", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 6000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt3", ((JU_88A4torp)aircraft()).fDiveRecoveryAlt * 360.0F / 6000.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.z, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 50.0F, 750.0F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -15.0F, 15.0F, -151.0F, 151.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zWaterTemp1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 64.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zWaterTemp2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tWaterOut, 0.0F, 120.0F, 0.0F, 64.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilPress1", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getReadyness(), 0.0F, 7.47F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zOilPress2", cvt(1.0F + 0.05F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].tOilOut * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getReadyness(), 0.0F, 7.47F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFuelPress1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFuelPress2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 0.5F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPM1", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getRPM(), 600.0F, 3600.0F, 0.0F, 331.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zRPM2", cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getRPM(), 600.0F, 3600.0F, 0.0F, 331.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ATA1", this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("ATA2", this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 325.5F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zFuelPress", cvt(this.setNew.cons, 100.0F, 500.0F, 0.0F, 240.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass3", this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass4", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass5", 180.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass6", 0.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass7", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass8", this.setNew.waypointAzimuth.getDeg(paramFloat) + this.setNew.azimuth.getDeg(paramFloat) + 90.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zHORIZ1", -this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -45.0F, 45.0F, 0.045F, -0.045F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zHORIZ2", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = (0.02125F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimElevatorControl());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zTRIM1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.02125F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimAileronControl());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zTRIM2", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.02125F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getTrimRudderControl());
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zTRIM3", Cockpit.xyz, Cockpit.ypr);
    this.w.set(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getW());
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.w);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

    for (float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x)); f <= -180.0F; f += 180.0F);
    while (f > 180.0F) f -= 180.0F;
    return f;
  }

  public void reflectCockpitState()
  {
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage5", true);
    if (((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x40) == 0) || 
      ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0))
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage4", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage5", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage6", true);
    retoggleLight();
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight()
  {
    if (this.jdField_cockpitLightControl_of_type_Boolean)
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
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 3);
    Property.set(CLASS.THIS(), "weaponControlNum", 13);
    Property.set(CLASS.THIS(), "astatePilotIndx", 3);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
      {
        CockpitJU_88A4_BGunnerTorp.access$002(CockpitJU_88A4_BGunnerTorp.this, CockpitJU_88A4_BGunnerTorp.this.setOld);
        CockpitJU_88A4_BGunnerTorp.access$102(CockpitJU_88A4_BGunnerTorp.this, CockpitJU_88A4_BGunnerTorp.this.setNew);
        CockpitJU_88A4_BGunnerTorp.access$202(CockpitJU_88A4_BGunnerTorp.this, CockpitJU_88A4_BGunnerTorp.this.setTmp);
        CockpitJU_88A4_BGunnerTorp.this.setNew.throttle1 = (0.85F * CockpitJU_88A4_BGunnerTorp.this.setOld.throttle1 + CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlThrottle() * 0.15F);
        CockpitJU_88A4_BGunnerTorp.this.setNew.prop1 = (0.85F * CockpitJU_88A4_BGunnerTorp.this.setOld.prop1 + CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlProp() * 0.15F);
        CockpitJU_88A4_BGunnerTorp.this.setNew.throttle2 = (0.85F * CockpitJU_88A4_BGunnerTorp.this.setOld.throttle2 + CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlThrottle() * 0.15F);
        CockpitJU_88A4_BGunnerTorp.this.setNew.prop2 = (0.85F * CockpitJU_88A4_BGunnerTorp.this.setOld.prop2 + CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].getControlProp() * 0.15F);
        CockpitJU_88A4_BGunnerTorp.this.setNew.altimeter = CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getAltitude();
        float f1 = CockpitJU_88A4_BGunnerTorp.this.waypointAzimuth();
        CockpitJU_88A4_BGunnerTorp.this.setNew.waypointAzimuth.setDeg(CockpitJU_88A4_BGunnerTorp.this.setOld.waypointAzimuth.getDeg(0.1F), f1 - CockpitJU_88A4_BGunnerTorp.this.setOld.azimuth.getDeg(1.0F));
        CockpitJU_88A4_BGunnerTorp.this.setNew.azimuth.setDeg(CockpitJU_88A4_BGunnerTorp.this.setOld.azimuth.getDeg(1.0F), CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.azimut());
        CockpitJU_88A4_BGunnerTorp.this.setNew.vspeed = ((199.0F * CockpitJU_88A4_BGunnerTorp.this.setOld.vspeed + CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getVertSpeed()) / 200.0F);
        float f2 = CockpitJU_88A4_BGunnerTorp.this.prevFuel - CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel;
        CockpitJU_88A4_BGunnerTorp.access$302(CockpitJU_88A4_BGunnerTorp.this, CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel);
        f2 /= 0.72F;
        f2 /= Time.tickLenFs();
        f2 *= 3600.0F;
        CockpitJU_88A4_BGunnerTorp.this.setNew.cons = (0.91F * CockpitJU_88A4_BGunnerTorp.this.setOld.cons + 0.09F * f2);
        if (CockpitJU_88A4_BGunnerTorp.this.buzzerFX != null)
          if ((CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Loc.z < ((JU_88A4torp)CockpitJU_88A4_BGunnerTorp.this.aircraft()).fDiveRecoveryAlt) && (((JU_88A4torp)CockpitJU_88A4_BGunnerTorp.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.actor).diveMechStage == 1)) {
            CockpitJU_88A4_BGunnerTorp.this.buzzerFX.play();
          }
          else if (CockpitJU_88A4_BGunnerTorp.this.buzzerFX.isPlaying())
            CockpitJU_88A4_BGunnerTorp.this.buzzerFX.stop();
      }
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
    float cons;
    private final CockpitJU_88A4_BGunnerTorp this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitJU_88A4_BGunnerTorp.1 arg2)
    {
      this();
    }
  }
}