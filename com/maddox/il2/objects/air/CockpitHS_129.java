package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunBK374Hs129;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitHS_129 extends CockpitPilot
{
  private Gun[] mainGuns = { null, null, null, null };
  private float ammoCountGun0 = 0.0F;
  private float ammoCountGun1 = 0.0F;
  private float ammoCountGun2 = 0.0F;
  private float ammoCountGun3 = 0.0F;
  private Gun[] MG17s = { null, null, null, null };
  private Gun cannon;
  private BombGun[] bombs = { null, null, null, null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictManifold1 = 0.0F;
  private float pictManifold2 = 0.0F;
  private boolean bNeedSetUp = true;
  private LightPointActor light1;
  private static final float[] speedometerScale = { 0.0F, -12.333333F, 18.5F, 37.0F, 62.5F, 90.0F, 110.5F, 134.0F, 158.5F, 186.0F, 212.5F, 238.5F, 265.0F, 289.5F, 315.0F, 339.5F, 346.0F, 346.0F };

  private static final float[] rpmScale = { 0.0F, 7.0F, 24.5F, 60.0F, 99.0F, 140.0F, 180.2F, 221.5F, 260.0F, 297.5F, 334.5F };

  float oilPressure1 = 0.0F;
  float rpmGeneratedPressure1 = 0.0F;
  float oilPressure2 = 0.0F;
  float rpmGeneratedPressure2 = 0.0F;
  HS_129 ac = null;
  private float gearsLever = 0.0F;
  private float gears = 0.0F;
  private int oldbullets1 = -1;
  private int oldbullets2 = -1;
  private int oldbullets3 = -1;
  private int oldbullets4 = -1;
  private boolean gunLight1 = false;
  private boolean gunLight2 = false;
  private boolean gunLight3 = false;
  private boolean gunLight4 = false;
  private boolean cannonLight = false;
  private long shotTime = -1L;
  private long reloadTimeNeeded = 0L;
  private float engine1PropPitch = 0.0F;
  private float engine2PropPitch = 0.0F;
  private float engine1PitchMode = 0.0F;
  private float engine2PitchMode = 0.0F;
  private float magneto1 = 0.0F;
  private float magneto2 = 0.0F;
  private float elevatorTrim = 0.0F;
  private float currentElevatorTrim = 0.0F;
  private int etDelta = 0;
  private float rudderTrim = 0.0F;
  private float currentRudderTrim = 0.0F;
  private int rtDelta = 0;
  private int tClap = -1;
  private float pictClap = 0.0F;
  private float selectorAngle = 10.0F;
  private boolean isSlideRight = false;

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    if ((localHierMesh.isChunkVisible("engine1_d2")) || (localHierMesh.isChunkVisible("WingLIn_D2")) || (localHierMesh.isChunkVisible("WingLIn_D3")))
    {
      this.mesh.chunkVisible("gaugesEx_01", false);
      this.mesh.chunkVisible("D_gaugesEx_01", true);
      this.mesh.chunkVisible("Z_need_RPM_01", false);
      this.mesh.chunkVisible("ZFlare_Fuel_01", false);
      this.mesh.chunkVisible("Z_need_temp_01", false);
      this.mesh.chunkVisible("Z_fuel_01", false);
      this.mesh.chunkVisible("Z_need_oilpress_b_01", false);
      this.mesh.chunkVisible("Z_need_oilpress_a_01", false);
      this.mesh.chunkVisible("Z_need_oilsystem", false);
    }
    if ((localHierMesh.isChunkVisible("engine2_d2")) || (localHierMesh.isChunkVisible("WingRIn_D2")) || (localHierMesh.isChunkVisible("WingRIn_D3")))
    {
      this.mesh.chunkVisible("gaugesEx_02", false);
      this.mesh.chunkVisible("D_gaugesEx_02", true);
      this.mesh.chunkVisible("Z_need_RPM_02", false);
      this.mesh.chunkVisible("ZFlare_Fuel_02", false);
      this.mesh.chunkVisible("Z_need_temp_02", false);
      this.mesh.chunkVisible("Z_fuel_02", false);
      this.mesh.chunkVisible("Z_need_oilpress_b_02", false);
      this.mesh.chunkVisible("Z_need_oilpress_a_02", false);
    }
    if ((!localHierMesh.isChunkVisible("WingLIn_D0")) && (!localHierMesh.isChunkVisible("WingLIn_D1")) && (!localHierMesh.isChunkVisible("WingLIn_D2")) && (!localHierMesh.isChunkVisible("WingLIn_D3")))
    {
      this.mesh.chunkVisible("gaugesEx_01", false);
      this.mesh.chunkVisible("D_gaugesEx_01", false);
      this.mesh.chunkVisible("Z_need_RPM_01", false);
      this.mesh.chunkVisible("ZFlare_Fuel_01", false);
      this.mesh.chunkVisible("Z_need_temp_01", false);
      this.mesh.chunkVisible("Z_fuel_01", false);
      this.mesh.chunkVisible("Z_need_oilpress_b_01", false);
      this.mesh.chunkVisible("Z_need_oilpress_a_01", false);
      this.mesh.chunkVisible("Z_need_oilsystem", false);
    }
    if ((!localHierMesh.isChunkVisible("WingRIn_D0")) && (!localHierMesh.isChunkVisible("WingRIn_D1")) && (!localHierMesh.isChunkVisible("WingRIn_D2")) && (!localHierMesh.isChunkVisible("WingRIn_D3")))
    {
      this.mesh.chunkVisible("gaugesEx_02", false);
      this.mesh.chunkVisible("D_gaugesEx_02", false);
      this.mesh.chunkVisible("Z_need_RPM_02", false);
      this.mesh.chunkVisible("ZFlare_Fuel_02", false);
      this.mesh.chunkVisible("Z_need_temp_02", false);
      this.mesh.chunkVisible("Z_fuel_02", false);
      this.mesh.chunkVisible("Z_need_oilpress_b_02", false);
      this.mesh.chunkVisible("Z_need_oilpress_a_02", false);
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public CockpitHS_129(String paramString)
  {
    super(paramString, "bf109");
    this.setNew.dimPosition = 0.0F;
    this.cockpitDimControl = (!this.cockpitDimControl);
    this.cockpitNightMats = new String[] { "gauges_1_TR", "gauges_2_TR", "gauges_3_TR", "gauges_4_TR", "gauges_6_TR", "gauges_7_TR", "D_gauges_1_TR", "D_gauges_2_TR", "D_gauges_3_TR", "D_gauges_4_TR", "D_gauges_5_TR", "D_gauges_7_TR", "equip01_TR", "equip03_TR" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    loadBuzzerFX();
    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK01");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    randomizeGlassDamage();

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    this.ac = ((HS_129)aircraft());
    this.ac.registerPit(this);
    try
    {
      if (!(this.ac.getGunByHookName("_CANNON01") instanceof GunEmpty))
      {
        this.mainGuns[0] = this.ac.getGunByHookName("_CANNON01");
        this.mainGuns[3] = this.ac.getGunByHookName("_CANNON02");
        this.ammoCountGun0 = 500.0F;
        this.ammoCountGun3 = 500.0F;
      }
    }
    catch (Exception localException1)
    {
    }
    try {
      if (!(this.ac.getGunByHookName("_MGUN01") instanceof GunEmpty))
      {
        if ((this.ac instanceof HS_129B2))
        {
          this.mainGuns[1] = this.ac.getGunByHookName("_MGUN01");
          this.mainGuns[2] = this.ac.getGunByHookName("_MGUN02");
          this.ammoCountGun1 = 1000.0F;
          this.ammoCountGun2 = 1000.0F;
        }
        else
        {
          this.mainGuns[0] = this.ac.getGunByHookName("_MGUN01");
          this.mainGuns[2] = this.ac.getGunByHookName("_MGUN02");
          this.ammoCountGun0 = 500.0F;
          this.ammoCountGun2 = 500.0F;
        }
      }
    }
    catch (Exception localException2)
    {
    }
    try {
      if (!(this.ac.getGunByHookName("_MGUN03") instanceof GunEmpty))
      {
        this.MG17s[0] = this.ac.getGunByHookName("_MGUN03");
        this.MG17s[1] = this.ac.getGunByHookName("_MGUN04");
        this.MG17s[2] = this.ac.getGunByHookName("_MGUN05");
        this.MG17s[3] = this.ac.getGunByHookName("_MGUN06");
        this.mesh.chunkVisible("X_4xMG17_gauge", true);
        this.mesh.chunkVisible("X_noExGuns", false);
      }
    }
    catch (Exception localException3)
    {
    }
    try {
      if (!(this.ac.getGunByHookName("_CANNON03") instanceof GunEmpty))
      {
        this.cannon = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON03");
        this.reloadTimeNeeded = 131L;
        this.oldbullets1 = this.cannon.countBullets();
        this.mesh.chunkVisible("X_biggun_light", true);
        this.mesh.chunkVisible("X_noExGuns", false);
      }
    }
    catch (Exception localException4)
    {
    }
    try {
      if ((!(this.ac.getGunByHookName("_CANNON04") instanceof GunEmpty)) && (this.cannon == null))
      {
        this.cannon = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON04");
        this.reloadTimeNeeded = 43L;
        this.oldbullets1 = this.cannon.countBullets();
        this.mesh.chunkVisible("X_biggun_light", true);
        this.mesh.chunkVisible("X_noExGuns", false);
      }
    }
    catch (Exception localException5)
    {
    }
    try {
      if ((!(this.ac.getGunByHookName("_HEAVYCANNON01") instanceof GunEmpty)) && (this.cannon == null))
      {
        this.cannon = ((Aircraft)this.fm.actor).getGunByHookName("_HEAVYCANNON01");
        if ((this.cannon instanceof MGunBK374Hs129)) {
          this.reloadTimeNeeded = 700L;
        }
        else {
          this.mainGuns[1] = this.ac.getGunByHookName("_HEAVYCANNON01");
          this.cannon = null;
          this.ammoCountGun1 = 16.0F;
        }

        this.oldbullets1 = this.cannon.countBullets();
        this.mesh.chunkVisible("X_biggun_light", true);
        this.mesh.chunkVisible("X_noExGuns", false);
      }
    }
    catch (Exception localException6)
    {
    }
    try
    {
      this.bombs[0] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb07"));
    }
    catch (Exception localException7)
    {
    }
    try {
      this.bombs[1] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb08"));
    }
    catch (Exception localException8)
    {
    }
    try {
      this.bombs[2] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb05"));
    }
    catch (Exception localException9)
    {
    }
    try {
      this.bombs[3] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb06"));
    }
    catch (Exception localException10)
    {
    }
    try {
      this.bombs[4] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03"));
    }
    catch (Exception localException11)
    {
    }
    try {
      this.bombs[5] = ((BombGun)((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb04"));
    }
    catch (Exception localException12)
    {
    }

    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    float f1 = interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat);

    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.09F * f1);
    this.mesh.chunkSetLocate("Z_revidim_01", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_revidim_02", 0.0F, f1 * -23.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_revidim_03", 0.0F, f1 * 23.0F, 0.0F);

    this.mesh.chunkVisible("ZFlare_Gear_03", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("ZFlare_Gear_04", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("ZFlare_Gear_01", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("ZFlare_Gear_02", (this.fm.CT.getGear() == 0.0F) && (this.fm.Gears.rgear));

    this.mesh.chunkVisible("ZFlare_Fuel_01", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("ZFlare_Fuel_02", this.fm.M.fuel < 36.0F);

    this.mesh.chunkSetAngles("stick", -14.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.ElevatorControl), 14.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.AileronControl), 0.0F);

    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[3] != 0) {
      Cockpit.xyz[2] = -0.003F;
    }
    this.mesh.chunkSetLocate("Z_trigger_02", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();

    float f2 = 0.0F;
    int i = 0;
    if ((this.fm.CT.saveWeaponControl[0] != 0) || (this.fm.CT.saveWeaponControl[1] != 0) || (this.fm.CT.saveWeaponControl[3] != 0))
    {
      if ((this.fm.CT.saveWeaponControl[0] != 0) || (this.fm.CT.saveWeaponControl[1] != 0))
        f2 = 20.0F;
      this.tClap = (Time.tickCounter() + World.Rnd().nextInt(500, 1000));

      if ((this.fm.CT.saveWeaponControl[0] != 0) && (this.fm.CT.saveWeaponControl[1] == 0))
        this.selectorAngle = 24.0F;
      else if ((this.fm.CT.saveWeaponControl[0] == 0) && (this.fm.CT.saveWeaponControl[1] != 0))
        this.selectorAngle = 43.0F;
      else
        this.selectorAngle = 10.0F;
    }
    if (Time.tickCounter() < this.tClap)
      i = 1;
    this.mesh.chunkSetAngles("Z_trigger_01", -(240.0F + f2) * (this.pictClap = 0.85F * this.pictClap + 0.15F * i), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_trigger_03", this.selectorAngle, 0.0F, 0.0F);

    Cockpit.xyz[2] = (0.05F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_ruddersupp_L", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.05F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_ruddersupp_R", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_rudder_R", this.fm.CT.getBrake() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_L", this.fm.CT.getBrake() * 15.0F, 0.0F, 0.0F);

    Cockpit.xyz[1] = (0.05F * this.fm.CT.getFlap());
    this.mesh.chunkSetLocate("Z_indicator_flaps", Cockpit.xyz, Cockpit.ypr);

    float f3 = interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat);
    this.mesh.chunkSetAngles("Z_throttle_01", cvt(f3, 0.0F, 1.0F, 0.0F, 42.5F), 0.0F, 0.0F);

    float f4 = interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat);
    this.mesh.chunkSetAngles("Z_throttle_02", cvt(f4, 0.0F, 1.0F, 0.0F, 42.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_wep", cvt(Math.max(f3, f4), 1.0F, 1.1F, 0.0F, 18.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_mixture", interp(this.setNew.mix1, this.setOld.mix1, paramFloat) * 19.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Gear", -this.gearsLever, 0.0F, 0.0F);

    if (this.gearsLever < -10.0F)
      this.mesh.chunkSetAngles("Z_gear_safety", cvt(this.gearsLever, -13.0F, -10.0F, 0.0F, 30.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_gear_safety", cvt(this.gearsLever, -10.0F, -2.5F, 30.0F, 0.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_flaps", interp(this.setNew.flaps, this.setOld.flaps, paramFloat) * -28.5F, 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_need_kompass_03", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F + this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_need_kompass_02", this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_need_kompass_02", this.setNew.azimuth.getDeg(paramFloat) + 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_need_kompass_03", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_magn_01", -26.0F * this.magneto1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_magn_02", -26.0F * this.magneto2, 0.0F, 0.0F);

    float f5 = 0.0F;
    float f6 = 0.046F;
    if (this.mainGuns[0] != null)
    {
      f5 = cvt(this.mainGuns[0].countBullets(), 0.0F, this.ammoCountGun0, f6, 0.0F);
      resetYPRmodifier();
      Cockpit.xyz[1] = (-f5);
      this.mesh.chunkSetLocate("Z_ammo_counter_01", Cockpit.xyz, Cockpit.ypr);
    }
    else
    {
      this.mesh.chunkVisible("Z_ammo_counter_01", false);
    }

    if (this.mainGuns[1] != null)
    {
      f5 = cvt(this.mainGuns[1].countBullets(), 0.0F, this.ammoCountGun1, f6, 0.0F);
      resetYPRmodifier();
      Cockpit.xyz[1] = (-f5);
      this.mesh.chunkSetLocate("Z_ammo_counter_02", Cockpit.xyz, Cockpit.ypr);
    }
    else
    {
      this.mesh.chunkVisible("Z_ammo_counter_02", false);
    }

    if (this.mainGuns[2] != null)
    {
      f5 = cvt(this.mainGuns[2].countBullets(), 0.0F, this.ammoCountGun2, f6, 0.0F);
      resetYPRmodifier();
      Cockpit.xyz[1] = (-f5);
      this.mesh.chunkSetLocate("Z_ammo_counter_03", Cockpit.xyz, Cockpit.ypr);
    }
    else
    {
      this.mesh.chunkVisible("Z_ammo_counter_03", false);
    }

    if (this.mainGuns[3] != null)
    {
      f5 = cvt(this.mainGuns[3].countBullets(), 0.0F, this.ammoCountGun3, f6, 0.0F);
      resetYPRmodifier();
      Cockpit.xyz[1] = (-f5);
      this.mesh.chunkSetLocate("Z_ammo_counter_04", Cockpit.xyz, Cockpit.ypr);
    }
    else if (this.ammoCountGun0 == 1000.0F)
    {
      this.mesh.chunkVisible("Z_ammo_counter_04", false);
    }

    if (this.cannon != null)
    {
      if (this.cannon.countBullets() == 0)
      {
        this.cannonLight = true;
      }
      else if (this.oldbullets1 != this.cannon.countBullets())
      {
        if (this.shotTime == -1L)
        {
          this.shotTime = Time.current();
          this.cannonLight = true;
        }
        else if (Time.current() - this.shotTime >= this.reloadTimeNeeded)
        {
          this.oldbullets1 = this.cannon.countBullets();
          this.shotTime = -1L;
          this.cannonLight = false;
        }
        else
        {
          this.cannonLight = true;
        }
      }
      else
      {
        this.cannonLight = false;
      }
    }

    if (this.MG17s[0] != null)
    {
      this.mesh.chunkVisible("ZFlare_Ordn_03", !this.gunLight1);
      this.mesh.chunkVisible("ZFlare_Ordn_01", !this.gunLight2);
      this.mesh.chunkVisible("ZFlare_Ordn_02", !this.gunLight3);
      this.mesh.chunkVisible("ZFlare_Ordn_04", !this.gunLight4);
    }
    if (this.cannon != null) {
      this.mesh.chunkVisible("ZFlare_bigguns", !this.cannonLight);
    }
    this.mesh.chunkSetAngles("Z_need_clock_01", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_clock_02", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_clock_03", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_turnbank_02", cvt(this.setNew.turn, -0.23562F, 0.23562F, -21.0F, 21.0F), 0.0F, 0.0F);

    float f7 = getBall(4.0D);
    this.mesh.chunkSetAngles("Z_turnbank_01", cvt(f7, -4.0F, 4.0F, 8.0F, -8.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_climb", cvt(this.setNew.vspeed, -15.0F, 15.0F, 135.0F, -135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_speed", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_pressure_01", -cvt(this.pictManifold1, 0.6F, 1.8F, 15.0F, 345.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_pressure_02", -cvt(this.pictManifold2, 0.6F, 1.8F, 15.0F, 345.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_alt_01", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_alt_02", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 313.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_fuel_01", -cvt(this.fm.M.fuel * 0.4001F, 0.0F, 205.0F, 0.0F, 96.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_fuel_02", -cvt(this.fm.M.fuel * 0.4001F, 0.0F, 205.0F, 0.0F, 96.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_temp_01", -cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 160.0F, 0.0F, 332.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_temp_02", -cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 160.0F, 0.0F, 332.0F), 0.0F, 0.0F);

    float f8 = this.fm.EI.engines[0].getRPM();

    this.mesh.chunkSetAngles("Z_need_RPM_01", -floatindex(cvt(f8, 0.0F, 3500.0F, 0.0F, 10.0F), rpmScale), 0.0F, 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
    {
      this.rpmGeneratedPressure1 -= 2.0F;
    }
    else if (f8 < this.rpmGeneratedPressure1)
    {
      this.rpmGeneratedPressure1 -= (this.rpmGeneratedPressure1 - f8) * 0.01F;
    }
    else
    {
      this.rpmGeneratedPressure1 += (f8 - this.rpmGeneratedPressure1) * 0.001F;
    }

    if (this.rpmGeneratedPressure1 < 800.0F)
    {
      this.oilPressure1 = cvt(this.rpmGeneratedPressure1, 0.0F, 800.0F, 0.0F, 4.0F);
    }
    else if (this.rpmGeneratedPressure1 < 1800.0F)
    {
      this.oilPressure1 = cvt(this.rpmGeneratedPressure1, 800.0F, 1800.0F, 4.0F, 5.0F);
    }
    else
    {
      this.oilPressure1 = cvt(this.rpmGeneratedPressure1, 1800.0F, 3500.0F, 5.0F, 6.0F);
    }

    float f9 = 0.0F;
    if (this.fm.EI.engines[0].tOilOut > 90.0F)
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 90.0F, 110.0F, 1.1F, 1.5F);
    }
    else if (this.fm.EI.engines[0].tOilOut < 50.0F)
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 50.0F, 2.0F, 0.9F);
    }
    else
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }

    float f10 = f9 * this.fm.EI.engines[0].getReadyness() * this.oilPressure1;
    this.mesh.chunkSetAngles("Z_need_oilpress_b_01", cvt(f10, 0.0F, 10.0F, 0.0F, 140.0F), 0.0F, 0.0F);

    f8 = this.fm.EI.engines[1].getRPM();

    this.mesh.chunkSetAngles("Z_need_RPM_02", -floatindex(cvt(f8, 0.0F, 3500.0F, 0.0F, 10.0F), rpmScale), 0.0F, 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
    {
      this.rpmGeneratedPressure2 -= 2.0F;
    }
    else if (f8 < this.rpmGeneratedPressure2)
    {
      this.rpmGeneratedPressure2 -= (this.rpmGeneratedPressure2 - f8) * 0.01F;
    }
    else
    {
      this.rpmGeneratedPressure2 += (f8 - this.rpmGeneratedPressure2) * 0.001F;
    }

    if (this.rpmGeneratedPressure2 < 800.0F)
    {
      this.oilPressure2 = cvt(this.rpmGeneratedPressure2, 0.0F, 800.0F, 0.0F, 4.0F);
    }
    else if (this.rpmGeneratedPressure2 < 1800.0F)
    {
      this.oilPressure2 = cvt(this.rpmGeneratedPressure2, 800.0F, 1800.0F, 4.0F, 5.0F);
    }
    else
    {
      this.oilPressure2 = cvt(this.rpmGeneratedPressure2, 1800.0F, 3500.0F, 5.0F, 6.0F);
    }

    f9 = 0.0F;
    if (this.fm.EI.engines[0].tOilOut > 90.0F)
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 90.0F, 110.0F, 1.1F, 1.5F);
    }
    else if (this.fm.EI.engines[0].tOilOut < 50.0F)
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 50.0F, 2.0F, 0.9F);
    }
    else
    {
      f9 = cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }

    float f11 = f9 * this.fm.EI.engines[0].getReadyness() * this.oilPressure2;
    this.mesh.chunkSetAngles("Z_need_oilpress_b_02", cvt(f11, 0.0F, 10.0F, 0.0F, 140.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_oilpress_a_01", -cvt(this.rpmGeneratedPressure1, 0.0F, 1800.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_oilpress_a_02", -cvt(this.rpmGeneratedPressure2, 0.0F, 1800.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_need_oilsystem", -cvt((this.rpmGeneratedPressure1 + this.rpmGeneratedPressure2) / 2.0F, 0.0F, 2000.0F, 48.0F, 250.0F), 0.0F, 0.0F);

    if (!this.ac.sideWindow)
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = (this.fm.CT.getCockpitDoor() * 1.0F);
      if (Aircraft.xyz[1] < 0.01D)
      {
        Aircraft.xyz[1] = 0.0F;
      }
      this.mesh.chunkSetLocate("Z_canopy", Aircraft.xyz, Aircraft.ypr);
    }
    else
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = (this.fm.CT.getCockpitDoor() * 0.33F);
      if (Aircraft.xyz[1] < 0.01D)
      {
        Aircraft.xyz[1] = 0.0F;
      }
      if (this.isSlideRight)
      {
        this.mesh.chunkSetLocate("Sliding_glass_01", Aircraft.xyz, Aircraft.ypr);
        this.mesh.chunkSetAngles("Z_glass_opener01", cvt(Aircraft.xyz[1], 0.0F, 0.05F, 0.0F, -27.0F), 0.0F, 0.0F);
      }
      else
      {
        this.mesh.chunkSetLocate("Sliding_glass", Aircraft.xyz, Aircraft.ypr);
        this.mesh.chunkSetAngles("Z_glass_opener", cvt(Aircraft.xyz[1], 0.0F, 0.05F, 0.0F, -27.0F), 0.0F, 0.0F);
      }

    }

    if (this.fm.EI.engines[0].getExtinguishers() == 0)
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = 0.05F;
      this.mesh.chunkSetLocate("Z_extinguisher_01", Aircraft.xyz, Aircraft.ypr);
    }
    if (this.fm.EI.engines[1].getExtinguishers() == 0)
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = 0.05F;
      this.mesh.chunkSetLocate("Z_extinguisher_02", Aircraft.xyz, Aircraft.ypr);
    }

    this.mesh.chunkSetAngles("Z_need_radio_01", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, 20.0F, -20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_need_radio_02", cvt(this.setNew.beaconRange, 0.0F, 1.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkVisible("ZFlare_Radio_01", isOnBlindLandingMarker());

    this.mesh.chunkVisible("ZFlare_Pitch_02", (this.fm.EI.engines[0].getElPropPos() < 1.0F) && (this.fm.EI.engines[0].getControlPropDelta() == 1));
    this.mesh.chunkVisible("ZFlare_Pitch_01", (this.fm.EI.engines[0].getElPropPos() > 0.0F) && (this.fm.EI.engines[0].getControlPropDelta() == -1));

    this.mesh.chunkVisible("ZFlare_Pitch_04", (this.fm.EI.engines[1].getElPropPos() < 1.0F) && (this.fm.EI.engines[1].getControlPropDelta() == 1));
    this.mesh.chunkVisible("ZFlare_Pitch_03", (this.fm.EI.engines[1].getElPropPos() > 0.0F) && (this.fm.EI.engines[1].getControlPropDelta() == -1));

    this.mesh.chunkSetAngles("Z_knobPITCH_01", this.engine1PropPitch * -15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_knobPITCH_02", this.engine2PropPitch * -15.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_knobPITCH_03", this.engine1PitchMode * 17.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_knobPITCH_04", this.engine2PitchMode * 17.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_elevator_trim", this.elevatorTrim * 24.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_trim", this.rudderTrim * 24.0F, 0.0F, 0.0F);
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
      this.light1.light.setEmit(0.003F, 1.0F);
      setNightMats(true);
    }
    else {
      this.light1.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private void randomizeGlassDamage()
  {
    double d = Math.random();
    if (d < 0.3D)
      this.mesh.materialReplace("XArmor_glass_DMG", "XArmor_glass_DMG2");
    else if (d < 0.6D)
      this.mesh.materialReplace("XArmor_glass_DMG", "XArmor_glass_DMG3");
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
    {
      this.mesh.chunkVisible("Xfront_glass_D0", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_03", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("XHoles_01", true);
      this.mesh.chunkVisible("gauges1_d0", false);
      this.mesh.chunkVisible("D_gauges1_d0", true);
      this.mesh.chunkVisible("Z_need_pressure_01", false);
      this.mesh.chunkVisible("Z_need_pressure_02", false);
      this.mesh.chunkVisible("Z_need_clock_03", false);
      this.mesh.chunkVisible("Z_need_clock_02", false);
      this.mesh.chunkVisible("Z_need_clock_01", false);
      this.mesh.chunkVisible("Z_need_speed", false);
      this.mesh.chunkVisible("Z_need_kompass_03", false);
      this.mesh.chunkVisible("Z_need_kompass_02", false);
      this.mesh.chunkVisible("XHoles_01", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_02", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("XGlassHoles_01", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("D_revi", true);
      this.mesh.chunkVisible("D_reviglass", true);
      this.mesh.chunkVisible("D_dimmerglas", true);
      this.mesh.chunkVisible("revi_mounting", false);
      this.mesh.chunkVisible("revi", false);
      this.mesh.chunkVisible("Z_revidim_01", false);
      this.mesh.chunkVisible("Z_revidim_02", false);
      this.mesh.chunkVisible("Z_revidim_03", false);
      this.mesh.chunkVisible("reviglass", false);
      this.mesh.chunkVisible("reticle", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("XHoles_02", true);
      this.mesh.chunkVisible("gauges2_d0", false);
      this.mesh.chunkVisible("D_gauges2_d0", true);
      this.mesh.chunkVisible("Z_need_climb", false);
      this.mesh.chunkVisible("Z_need_turnbank_02", false);
      this.mesh.chunkVisible("Z_turnbank_01", false);
      this.mesh.chunkVisible("Z_need_alt_01", false);
      this.mesh.chunkVisible("Z_need_alt_02", false);
      this.mesh.chunkVisible("Z_need_radio_02", false);
      this.mesh.chunkVisible("Z_need_radio_01", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
    {
      if (this.fm.AS.astateOilStates[0] == 0)
      {
        this.mesh.chunkVisible("ZOil_01", false);
        this.mesh.chunkVisible("ZOil_03", false);
      }
      else
      {
        this.mesh.chunkVisible("ZOil_01", true);
        this.mesh.chunkVisible("ZOil_03", true);
      }

      if (this.fm.AS.astateOilStates[1] == 0)
      {
        this.mesh.chunkVisible("ZOil_02", false);
        this.mesh.chunkVisible("ZOil_04", false);
      }
      else
      {
        this.mesh.chunkVisible("ZOil_02", true);
        this.mesh.chunkVisible("ZOil_04", true);
      }
    }
  }

  protected boolean doFocusEnter()
  {
    boolean bool = super.doFocusEnter();
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("Revi_D0", false);
    return bool;
  }

  protected void doFocusLeave()
  {
    super.doFocusLeave();
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("Revi_D0", true);
  }

  public boolean isViewRight()
  {
    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    HookPilot.current.computePos(this, localLoc1, localLoc2);
    float f = localLoc2.getOrient().getYaw();
    if (f < 0.0F)
      this.isSlideRight = true;
    else
      this.isSlideRight = false;
    return this.isSlideRight;
  }

  private class Variables
  {
    float altimeter;
    float throttle1;
    float throttle2;
    float flaps;
    float dimPosition;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float turn;
    float mix1;
    float mix2;
    float vspeed;
    float beaconDirection;
    float beaconRange;
    private final CockpitHS_129 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitHS_129.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if ((CockpitHS_129.this.ac != null) && (CockpitHS_129.this.ac.bChangedPit))
      {
        CockpitHS_129.this.reflectPlaneToModel();
        CockpitHS_129.this.ac.bChangedPit = false;
      }

      if (CockpitHS_129.this.bNeedSetUp)
      {
        CockpitHS_129.this.reflectPlaneMats();
        CockpitHS_129.access$102(CockpitHS_129.this, false);
      }

      CockpitHS_129.access$202(CockpitHS_129.this, CockpitHS_129.this.setOld);
      CockpitHS_129.access$302(CockpitHS_129.this, CockpitHS_129.this.setNew);
      CockpitHS_129.access$402(CockpitHS_129.this, CockpitHS_129.this.setTmp);
      CockpitHS_129.this.setNew.altimeter = CockpitHS_129.this.fm.getAltitude();
      if (!CockpitHS_129.this.cockpitDimControl)
      {
        if (CockpitHS_129.this.setNew.dimPosition > 0.0F)
          CockpitHS_129.this.setNew.dimPosition -= 0.05F;
      }
      else if (CockpitHS_129.this.setNew.dimPosition < 1.0F) {
        CockpitHS_129.this.setNew.dimPosition += 0.05F;
      }

      CockpitHS_129.this.setNew.throttle1 = (0.91F * CockpitHS_129.this.setOld.throttle1 + 0.09F * CockpitHS_129.this.fm.EI.engines[0].getControlThrottle());

      CockpitHS_129.this.setNew.throttle2 = (0.91F * CockpitHS_129.this.setOld.throttle2 + 0.09F * CockpitHS_129.this.fm.EI.engines[1].getControlThrottle());

      CockpitHS_129.this.setNew.mix1 = (0.88F * CockpitHS_129.this.setOld.mix1 + 0.12F * CockpitHS_129.this.fm.EI.engines[0].getControlMix());

      CockpitHS_129.this.setNew.mix2 = (0.88F * CockpitHS_129.this.setOld.mix2 + 0.12F * CockpitHS_129.this.fm.EI.engines[1].getControlMix());

      CockpitHS_129.access$502(CockpitHS_129.this, 0.75F * CockpitHS_129.this.pictManifold1 + 0.25F * CockpitHS_129.this.fm.EI.engines[0].getManifoldPressure());
      CockpitHS_129.access$602(CockpitHS_129.this, 0.75F * CockpitHS_129.this.pictManifold2 + 0.25F * CockpitHS_129.this.fm.EI.engines[1].getManifoldPressure());

      if ((CockpitHS_129.this.gearsLever != 0.0F) && (CockpitHS_129.this.gears == CockpitHS_129.this.fm.CT.getGear()))
      {
        CockpitHS_129.access$702(CockpitHS_129.this, CockpitHS_129.this.gearsLever * 0.8F);
        if (Math.abs(CockpitHS_129.this.gearsLever) < 0.1F)
          CockpitHS_129.access$702(CockpitHS_129.this, 0.0F);
      }
      else if (CockpitHS_129.this.gears < CockpitHS_129.this.fm.CT.getGear())
      {
        CockpitHS_129.access$802(CockpitHS_129.this, CockpitHS_129.this.fm.CT.getGear());
        CockpitHS_129.access$702(CockpitHS_129.this, CockpitHS_129.this.gearsLever + 2.0F);
        if (CockpitHS_129.this.gearsLever > 14.0F)
          CockpitHS_129.access$702(CockpitHS_129.this, 14.0F);
      }
      else if (CockpitHS_129.this.gears > CockpitHS_129.this.fm.CT.getGear())
      {
        CockpitHS_129.access$802(CockpitHS_129.this, CockpitHS_129.this.fm.CT.getGear());
        CockpitHS_129.access$702(CockpitHS_129.this, CockpitHS_129.this.gearsLever - 2.0F);
        if (CockpitHS_129.this.gearsLever < -14.0F) {
          CockpitHS_129.access$702(CockpitHS_129.this, -14.0F);
        }
      }
      float f1 = CockpitHS_129.this.waypointAzimuth();
      if (CockpitHS_129.this.useRealisticNavigationInstruments())
      {
        CockpitHS_129.this.setNew.waypointAzimuth.setDeg(f1);
        CockpitHS_129.this.setOld.waypointAzimuth.setDeg(f1);
      }
      else
      {
        CockpitHS_129.this.setNew.waypointAzimuth.setDeg(CockpitHS_129.this.setOld.waypointAzimuth.getDeg(0.1F), f1 - CockpitHS_129.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitHS_129.this.setNew.azimuth.setDeg(CockpitHS_129.this.setOld.azimuth.getDeg(1.0F), CockpitHS_129.this.fm.Or.azimut());

      CockpitHS_129.this.w.set(CockpitHS_129.this.fm.getW());
      CockpitHS_129.this.fm.Or.transform(CockpitHS_129.this.w);
      CockpitHS_129.this.setNew.turn = ((12.0F * CockpitHS_129.this.setOld.turn + CockpitHS_129.this.w.z) / 13.0F);

      CockpitHS_129.this.setNew.vspeed = ((199.0F * CockpitHS_129.this.setOld.vspeed + CockpitHS_129.this.fm.getVertSpeed()) / 200.0F);

      CockpitHS_129.this.setNew.beaconDirection = ((10.0F * CockpitHS_129.this.setOld.beaconDirection + CockpitHS_129.this.getBeaconDirection()) / 11.0F);
      CockpitHS_129.this.setNew.beaconRange = ((10.0F * CockpitHS_129.this.setOld.beaconRange + CockpitHS_129.this.getBeaconRange()) / 11.0F);

      f1 = CockpitHS_129.this.fm.CT.FlapsControl;
      float f2 = 0.0F;
      if (f1 < 0.2F)
        f2 = 1.5F;
      else if (f1 < 0.3333333F)
        f2 = 2.0F;
      else
        f2 = 1.0F;
      CockpitHS_129.this.setNew.flaps = (0.91F * CockpitHS_129.this.setOld.flaps + 0.09F * f1 * f2);

      if (CockpitHS_129.this.MG17s[0] != null)
      {
        if ((CockpitHS_129.this.MG17s[0].countBullets() == 0) || (CockpitHS_129.this.oldbullets1 != CockpitHS_129.this.MG17s[0].countBullets()))
        {
          CockpitHS_129.access$1002(CockpitHS_129.this, CockpitHS_129.this.MG17s[0].countBullets());
          CockpitHS_129.access$1102(CockpitHS_129.this, true);
        }
        else {
          CockpitHS_129.access$1102(CockpitHS_129.this, false);
        }
        if ((CockpitHS_129.this.MG17s[1].countBullets() == 0) || (CockpitHS_129.this.oldbullets2 != CockpitHS_129.this.MG17s[1].countBullets()))
        {
          CockpitHS_129.access$1202(CockpitHS_129.this, CockpitHS_129.this.MG17s[1].countBullets());
          CockpitHS_129.access$1302(CockpitHS_129.this, true);
        }
        else {
          CockpitHS_129.access$1302(CockpitHS_129.this, false);
        }
        if ((CockpitHS_129.this.MG17s[2].countBullets() == 0) || (CockpitHS_129.this.oldbullets3 != CockpitHS_129.this.MG17s[2].countBullets()))
        {
          CockpitHS_129.access$1402(CockpitHS_129.this, CockpitHS_129.this.MG17s[2].countBullets());
          CockpitHS_129.access$1502(CockpitHS_129.this, true);
        }
        else {
          CockpitHS_129.access$1502(CockpitHS_129.this, false);
        }
        if ((CockpitHS_129.this.MG17s[3].countBullets() == 0) || (CockpitHS_129.this.oldbullets4 != CockpitHS_129.this.MG17s[3].countBullets()))
        {
          CockpitHS_129.access$1602(CockpitHS_129.this, CockpitHS_129.this.MG17s[3].countBullets());
          CockpitHS_129.access$1702(CockpitHS_129.this, true);
        }
        else {
          CockpitHS_129.access$1702(CockpitHS_129.this, false);
        }

      }

      if (CockpitHS_129.this.bombs[0] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_01", CockpitHS_129.this.bombs[0].haveBullets());
      }
      if (CockpitHS_129.this.bombs[1] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_02", CockpitHS_129.this.bombs[1].haveBullets());
      }

      if (CockpitHS_129.this.bombs[2] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_04", CockpitHS_129.this.bombs[2].haveBullets());
      }

      if (CockpitHS_129.this.bombs[3] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_03", CockpitHS_129.this.bombs[3].haveBullets());
      }

      if (CockpitHS_129.this.bombs[4] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_06", CockpitHS_129.this.bombs[4].haveBullets());
      }

      if (CockpitHS_129.this.bombs[5] != null) {
        CockpitHS_129.this.mesh.chunkVisible("ZFlare_Bombs_05", CockpitHS_129.this.bombs[5].haveBullets());
      }

      if (CockpitHS_129.this.fm.CT.getStepControlAuto(0))
      {
        if (CockpitHS_129.this.engine1PitchMode < 1.0F)
          CockpitHS_129.access$1902(CockpitHS_129.this, CockpitHS_129.this.engine1PitchMode + 0.5F);
      }
      else if (CockpitHS_129.this.engine1PitchMode > -1.0F) {
        CockpitHS_129.access$1902(CockpitHS_129.this, CockpitHS_129.this.engine1PitchMode - 0.5F);
      }
      if (CockpitHS_129.this.fm.CT.getStepControlAuto(1))
      {
        if (CockpitHS_129.this.engine2PitchMode < 1.0F)
          CockpitHS_129.access$2002(CockpitHS_129.this, CockpitHS_129.this.engine2PitchMode + 0.5F);
      }
      else if (CockpitHS_129.this.engine2PitchMode > -1.0F) {
        CockpitHS_129.access$2002(CockpitHS_129.this, CockpitHS_129.this.engine2PitchMode - 0.5F);
      }

      CockpitHS_129.access$2102(CockpitHS_129.this, 0.5F * CockpitHS_129.this.magneto1 + 0.5F * CockpitHS_129.this.fm.EI.engines[0].getControlMagnetos());
      CockpitHS_129.access$2202(CockpitHS_129.this, 0.5F * CockpitHS_129.this.magneto2 + 0.5F * CockpitHS_129.this.fm.EI.engines[1].getControlMagnetos());

      CockpitHS_129.access$2302(CockpitHS_129.this, 0.5F * CockpitHS_129.this.engine1PropPitch + 0.5F * CockpitHS_129.this.fm.EI.engines[0].getControlPropDelta());
      CockpitHS_129.access$2402(CockpitHS_129.this, 0.5F * CockpitHS_129.this.engine2PropPitch + 0.5F * CockpitHS_129.this.fm.EI.engines[1].getControlPropDelta());

      if (CockpitHS_129.this.fm.CT.trimElevator < CockpitHS_129.this.currentElevatorTrim)
        CockpitHS_129.access$2602(CockpitHS_129.this, -1);
      else if (CockpitHS_129.this.fm.CT.trimElevator > CockpitHS_129.this.currentElevatorTrim)
        CockpitHS_129.access$2602(CockpitHS_129.this, 1);
      if ((CockpitHS_129.this.elevatorTrim > 1.0F) || (CockpitHS_129.this.elevatorTrim < -1.0F))
        CockpitHS_129.access$2602(CockpitHS_129.this, 0);
      if (CockpitHS_129.this.etDelta != 0)
        CockpitHS_129.access$2702(CockpitHS_129.this, CockpitHS_129.this.elevatorTrim + CockpitHS_129.this.etDelta * 0.2F);
      else if (CockpitHS_129.this.currentElevatorTrim == CockpitHS_129.this.fm.CT.trimElevator)
        CockpitHS_129.access$2702(CockpitHS_129.this, CockpitHS_129.this.elevatorTrim * 0.5F);
      CockpitHS_129.access$2502(CockpitHS_129.this, CockpitHS_129.this.fm.CT.trimElevator);

      if (CockpitHS_129.this.fm.CT.trimRudder < CockpitHS_129.this.currentRudderTrim)
        CockpitHS_129.access$2902(CockpitHS_129.this, 1);
      else if (CockpitHS_129.this.fm.CT.trimRudder > CockpitHS_129.this.currentRudderTrim)
        CockpitHS_129.access$2902(CockpitHS_129.this, -1);
      if ((CockpitHS_129.this.rudderTrim > 1.0F) || (CockpitHS_129.this.rudderTrim < -1.0F))
        CockpitHS_129.access$2902(CockpitHS_129.this, 0);
      if (CockpitHS_129.this.rtDelta != 0)
        CockpitHS_129.access$3002(CockpitHS_129.this, CockpitHS_129.this.rudderTrim + CockpitHS_129.this.rtDelta * 0.2F);
      else if (CockpitHS_129.this.currentRudderTrim == CockpitHS_129.this.fm.CT.trimRudder)
        CockpitHS_129.access$3002(CockpitHS_129.this, CockpitHS_129.this.rudderTrim * 0.5F);
      CockpitHS_129.access$2802(CockpitHS_129.this, CockpitHS_129.this.fm.CT.trimRudder);

      CockpitHS_129.this.buzzerFX((CockpitHS_129.this.fm.CT.getGear() < 0.999999F) && (CockpitHS_129.this.fm.CT.getFlap() > 0.0F));

      return true;
    }
  }
}