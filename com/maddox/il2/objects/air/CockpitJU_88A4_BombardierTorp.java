package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;
import java.io.PrintStream;

public class CockpitJU_88A4_BombardierTorp extends CockpitPilot
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
  private static final float[] speedometerScale = { 
    0.0F, 16.0F, 35.5F, 60.5F, 88.0F, 112.5F, 136.0F, 159.5F, 186.5F, 211.5F, 
    240.0F, 268.0F, 295.5F, 321.0F, 347.0F };

  private static final float[] speedometerScale2 = { 
    0.0F, 23.5F, 47.5F, 72.0F, 95.5F, 120.0F, 144.5F, 168.5F, 193.0F, 217.0F, 
    241.0F, 265.0F, 288.0F, 311.5F, 335.5F };

  private static final float[] angleScale = { 
    -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 
    75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  private static final float[] frAirTempScale = { 
    76.5F, 68.0F, 57.0F, 44.5F, 29.5F, 14.5F, 1.5F, -10.0F, -19.0F };
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered;
  private Point3d tmpP;
  private Vector3d tmpV;

  static
  {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot hookpilot = HookPilot.current;
      hookpilot.doAim(false);
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

    leave();
    aircraft().hierMesh().chunkVisible("fakeNose_D0", aircraft().hierMesh().isChunkVisible("Nose_D0"));
    aircraft().hierMesh().chunkVisible("fakeNose_D1", aircraft().hierMesh().isChunkVisible("Nose_D1"));
    aircraft().hierMesh().chunkVisible("fakeNose_D2", aircraft().hierMesh().isChunkVisible("Nose_D2"));
    aircraft().hierMesh().chunkVisible("fakeNose_D3", aircraft().hierMesh().isChunkVisible("Nose_D3"));
    super.doFocusLeave();
  }

  private void enter()
  {
  }

  private void leave()
  {
    if (!this.bEntered)
    {
      return;
    }

    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
    CmdEnv.top().exec("fov " + this.saveFov);
    HookPilot hookpilot = HookPilot.current;
    hookpilot.doAim(false);
    hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    hookpilot.setSimpleUse(false);
    boolean flag = HotKeyEnv.isEnabled("aircraftView");
    HotKeyEnv.enable("PanView", flag);
    HotKeyEnv.enable("SnapView", flag);
    this.bEntered = false;
  }

  public void destroy()
  {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean flag)
  {
    if (!isFocused())
      return;
  }

  public CockpitJU_88A4_BombardierTorp()
  {
    super("3DO/Cockpit/Ju-88A-4-Bombardier/torphier.him", "he111");
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
    this.bEntered = false;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    try
    {
      Loc loc = new Loc();
      HookNamed hooknamed = new HookNamed(this.mesh, "CAMERAAIM");
      hooknamed.computePos(this, this.pos.getAbs(), loc);
      this.aAim = loc.getOrient().getAzimut();
      this.tAim = loc.getOrient().getTangage();
      this.kAim = loc.getOrient().getKren();
    }
    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
      exception.printStackTrace();
    }
    this.cockpitNightMats = new String[] { 
      "88a4_I_Set1", "88a4_I_Set2", "88a4_I_Set3", "88a4_I_Set4", "88a4_I_Set5", "88a4_I_Set6", "88a4_SlidingGlass", "88gardinen", "lofte7_02", "Peil1", 
      "Peil2", "skala" };

    setNightMats(false);
    this.setNew.dimPosition = (this.setOld.dimPosition = 1.0F);
    this.cockpitDimControl = (!this.cockpitDimControl);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
    {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    this.buzzerFX = aircraft().newSound("models.buzzthru", false);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void reflectWorldToInstruments(float f)
  {
    if (this.bEntered)
    {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((JU_88A4torp)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);
      boolean flag = ((JU_88A4torp)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", flag);
      this.mesh.chunkVisible("zAngleMark", flag);
    }
    else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zAngleMark", false);
    }
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.mesh.chunkSetAngles("Z_Trim1", cvt(this.fm.CT.getTrimElevatorControl(), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ReviTinter", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, f), 0.0F, 1.0F, 0.0F, 130.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zColumn1", 7.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zColumn2", 52.200001F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalL", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("zPedalR", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("zTurret1A", 0.0F, this.fm.turret[0].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret1B", 0.0F, this.fm.turret[0].tu[1], 0.0F);
    this.mesh.chunkSetAngles("zTurret2A", 0.0F, this.fm.turret[1].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret2B", 0.0F, this.fm.turret[1].tu[1], 0.0F);
    this.mesh.chunkSetAngles("zTurret3A", 0.0F, this.fm.turret[2].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret3B", 0.0F, this.fm.turret[2].tu[1], 0.0F);
    this.mesh.chunkSetAngles("zTurret4A", 0.0F, this.fm.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurret4B", 0.0F, this.fm.turret[3].tu[1], 0.0F);
    resetYPRmodifier();
    float tmp642_641 = (0.85F * this.pictFlap + 0.00948F * this.fm.CT.FlapsControl); this.pictFlap = tmp642_641; Cockpit.xyz[2] = tmp642_641;
    this.mesh.chunkSetLocate("zFlaps1", Cockpit.xyz, Cockpit.ypr);
    float tmp691_690 = (0.85F * this.pictGear + 0.007095F * this.fm.CT.GearControl); this.pictGear = tmp691_690; Cockpit.xyz[2] = tmp691_690;
    this.mesh.chunkSetLocate("zGear1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1134F * this.setNew.prop1);
    this.mesh.chunkSetLocate("zPitch1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1134F * this.setNew.prop2);
    this.mesh.chunkSetLocate("zPitch2", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1031F * this.setNew.throttle1);
    this.mesh.chunkSetLocate("zThrottle1", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.1031F * this.setNew.throttle2);
    this.mesh.chunkSetLocate("zThrottle2", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("z_Object20", cvt(((JU_88A4torp)aircraft()).fSightCurSpeed, 400.0F, 800.0F, 87.0F, -63.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TempMeter", floatindex(cvt(Atmosphere.temperature((float)this.fm.Loc.z), 213.09F, 293.09F, 0.0F, 8.0F), frAirTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Radiator_Sw1", cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Radiator_Sw2", cvt(this.fm.EI.engines[1].getControlRadiator(), 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSw1", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 100.0F, 0.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSw2", cvt(this.fm.EI.engines[1].getControlMagnetos(), 0.0F, 3.0F, 100.0F, 0.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zHour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMinute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zBall", 0.0F, cvt(getBall(4.0D), -4.0F, 4.0F, -9.0F, 9.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 6000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zAlt3", ((JU_88A4torp)aircraft()).fDiveRecoveryAlt * 360.0F / 6000.0F, 0.0F, 0.0F);
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
    this.mesh.chunkSetAngles("Z_Compass1", -this.setNew.azimuth.getDeg(f), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", this.setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.azimuth.getDeg(f), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass5", 180.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass6", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass7", this.setNew.azimuth.getDeg(f), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass8", this.setNew.waypointAzimuth.getDeg(f) + this.setNew.azimuth.getDeg(f) + 90.0F, 0.0F, 0.0F);
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
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(4.0D), -4.0F, 4.0F, -9.0F, 9.0F), 0.0F);
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
  }

  protected float waypointAzimuth()
  {
    WayPoint waypoint = this.fm.AP.way.curr();
    if (waypoint == null)
    {
      return 0.0F;
    }
    waypoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);

    for (float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x)); f <= -180.0F; f += 180.0F);
    while (f > 180.0F) f -= 180.0F;
    return f;
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x2) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage5", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0))
    {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
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
    if (this.cockpitLightControl)
    {
      setNightMats(true);
    }
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
    HierMesh hiermesh = aircraft().hierMesh();
    Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", mat);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      float f = ((JU_88A4torp)CockpitJU_88A4_BombardierTorp.this.aircraft()).fSightCurForwardAngle;
      float f1 = ((JU_88A4torp)CockpitJU_88A4_BombardierTorp.this.aircraft()).fSightCurSideslip;
      CockpitJU_88A4_BombardierTorp.this.mesh.chunkSetAngles("BlackBox", 0.0F, -f1, f);
      if (CockpitJU_88A4_BombardierTorp.this.bEntered)
      {
        HookPilot hookpilot = HookPilot.current;
        hookpilot.setSimpleAimOrient(CockpitJU_88A4_BombardierTorp.this.aAim + f1, CockpitJU_88A4_BombardierTorp.this.tAim + f, 0.0F);
      }
      if (CockpitJU_88A4_BombardierTorp.this.fm != null)
      {
        CockpitJU_88A4_BombardierTorp.this.setTmp = CockpitJU_88A4_BombardierTorp.this.setOld;
        CockpitJU_88A4_BombardierTorp.this.setOld = CockpitJU_88A4_BombardierTorp.this.setNew;
        CockpitJU_88A4_BombardierTorp.this.setNew = CockpitJU_88A4_BombardierTorp.this.setTmp;
        CockpitJU_88A4_BombardierTorp.this.setNew.throttle1 = (0.85F * CockpitJU_88A4_BombardierTorp.this.setOld.throttle1 + CockpitJU_88A4_BombardierTorp.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitJU_88A4_BombardierTorp.this.setNew.prop1 = (0.85F * CockpitJU_88A4_BombardierTorp.this.setOld.prop1 + CockpitJU_88A4_BombardierTorp.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitJU_88A4_BombardierTorp.this.setNew.throttle2 = (0.85F * CockpitJU_88A4_BombardierTorp.this.setOld.throttle2 + CockpitJU_88A4_BombardierTorp.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitJU_88A4_BombardierTorp.this.setNew.prop2 = (0.85F * CockpitJU_88A4_BombardierTorp.this.setOld.prop2 + CockpitJU_88A4_BombardierTorp.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitJU_88A4_BombardierTorp.this.setNew.altimeter = CockpitJU_88A4_BombardierTorp.this.fm.getAltitude();
        float f2 = CockpitJU_88A4_BombardierTorp.this.waypointAzimuth();
        CockpitJU_88A4_BombardierTorp.this.setNew.waypointAzimuth.setDeg(CockpitJU_88A4_BombardierTorp.this.setOld.waypointAzimuth.getDeg(0.1F), f2 - CockpitJU_88A4_BombardierTorp.this.setOld.azimuth.getDeg(1.0F));
        CockpitJU_88A4_BombardierTorp.this.setNew.azimuth.setDeg(CockpitJU_88A4_BombardierTorp.this.setOld.azimuth.getDeg(1.0F), CockpitJU_88A4_BombardierTorp.this.fm.Or.azimut());
        CockpitJU_88A4_BombardierTorp.this.setNew.vspeed = ((199.0F * CockpitJU_88A4_BombardierTorp.this.setOld.vspeed + CockpitJU_88A4_BombardierTorp.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitJU_88A4_BombardierTorp.this.cockpitDimControl)
        {
          if (CockpitJU_88A4_BombardierTorp.this.setNew.dimPosition > 0.0F)
          {
            CockpitJU_88A4_BombardierTorp.this.setNew.dimPosition = (CockpitJU_88A4_BombardierTorp.this.setOld.dimPosition - 0.05F);
          }
        }
        else if (CockpitJU_88A4_BombardierTorp.this.setNew.dimPosition < 1.0F)
        {
          CockpitJU_88A4_BombardierTorp.this.setNew.dimPosition = (CockpitJU_88A4_BombardierTorp.this.setOld.dimPosition + 0.05F);
        }
        float f3 = CockpitJU_88A4_BombardierTorp.this.prevFuel - CockpitJU_88A4_BombardierTorp.this.fm.M.fuel;
        CockpitJU_88A4_BombardierTorp.this.prevFuel = CockpitJU_88A4_BombardierTorp.this.fm.M.fuel;
        f3 /= 0.72F;
        f3 /= Time.tickLenFs();
        f3 *= 3600.0F;
        CockpitJU_88A4_BombardierTorp.this.setNew.cons = (0.91F * CockpitJU_88A4_BombardierTorp.this.setOld.cons + 0.09F * f3);
        if (CockpitJU_88A4_BombardierTorp.this.buzzerFX != null)
        {
          return false;
        }
      }
      return false;
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
    final CockpitJU_88A4_BombardierTorp this$0;

    private Variables()
    {
      this.this$0 = ???;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(Variables arg2)
    {
      this();
    }
  }
}