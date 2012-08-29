package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitF84G1 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  protected boolean bHasBoosters = true;
  protected long boosterFireOutTime = -1L;
  private float pictGear = 0.0F;
  private float pictFlap = 0.0F;

  private Gun[] gun = { null, null, null, null };
  private BulletEmitter[] bgun = { null, null, null, null };
  private BulletEmitter[] rgun = { null, null, null, null };
  private BulletEmitter[] tgun = { null, null };
  private static final float[] fuelGallonsScale = { 0.0F, 8.25F, 17.5F, 36.5F, 54.0F, 90.0F, 108.0F };

  private static final float[] fuelGallonsAuxScale = { 0.0F, 38.0F, 62.5F, 87.0F, 104.0F };

  private static final float[] speedometerScale = { 0.0F, 42.0F, 65.5F, 88.5F, 111.3F, 134.0F, 156.5F, 181.0F, 205.0F, 227.0F, 249.39999F, 271.70001F, 294.0F, 316.5F, 339.5F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitF84G1() {
    super("3DO/Cockpit/F84G/hier.him", "bf109");
    this.cockpitNightMats = new String[] { "Instrumentos1", "Instrumentos2", "Instrumentos3", "Instrumentos4", "Instrumentos5", "Instrumentos6", "Instrumentos7", "GagePanel3", "GagePanel5", "needles" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (Math.abs(this.fm.CT.getTrimAileronControl() - this.fm.CT.trimAileron) > 1.0E-006F)
    {
      if (this.fm.CT.getTrimAileronControl() - this.fm.CT.trimAileron > 0.0F)
        this.mesh.chunkSetAngles("A_Trim1", 0.0F, 10.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("A_Trim1", 0.0F, 10.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("A_Trim1", 0.0F, 0.0F, 0.0F);
    if (Math.abs(this.fm.CT.getTrimRudderControl() - this.fm.CT.trimRudder) > 1.0E-006F)
    {
      if (this.fm.CT.getTrimRudderControl() - this.fm.CT.trimRudder > 0.0F)
        this.mesh.chunkSetAngles("A_Trim2", 0.0F, 10.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("A_Trim2", 0.0F, 10.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("A_Trim2", 0.0F, 0.0F, 0.0F);

    resetYPRmodifier();

    this.mesh.chunkSetAngles("Trim_Rud", -60.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Trim_Ele", 0.0F, 0.0F, 722.0F * this.fm.CT.getTrimElevatorControl());
    this.mesh.chunkSetAngles("Trim_Ail", 0.0F, 722.0F * this.fm.CT.getTrimAileronControl(), 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x2) == 0) {
      int i = ((F84G1)aircraft()).k14Mode;
      boolean bool = i < 2;
      this.mesh.chunkVisible("Z_Z_RETICLE", bool);
      bool = i > 0;
      this.mesh.chunkVisible("Z_Z_RETICLE1", bool);
      this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);

      resetYPRmodifier();
      Cockpit.xyz[0] = this.setNew.k14w;
      for (int j = 1; j < 7; j++) {
        this.mesh.chunkVisible("Z_Z_AIMMARK" + j, bool);
        this.mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz, Cockpit.ypr);
      }

    }

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.0F, 0.2F, 0.0F, 0.625F);

    this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_RightPedal", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeftPedal", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 0.0F, -70.0F * this.setNew.throttle);

    resetYPRmodifier();
    float f = this.fm.EI.engines[0].getStage();
    if ((f > 0.0F) && (f < 7.0F))
      f = 0.0345F;
    else
      f = -0.05475F;
    Cockpit.xyz[2] = f;

    this.mesh.chunkSetAngles("Z_Column", (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F, 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F);

    this.mesh.chunkSetAngles("z_Flap", 0.0F, -38.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.95F * this.fm.CT.FlapsControl), 0.0F);

    this.mesh.chunkSetAngles("Palanca_Tren", 0.0F, 0.0F, 46.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl));

    this.mesh.chunkSetAngles("Z_Target1", 0.0F, 0.0F, this.setNew.k14wingspan);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 36000.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1126.541F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(this.fm.getSpeedKMH(), 0.0F, 1126.541F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    f = this.fm.EI.engines[0].getPowerOutput();

    this.mesh.chunkSetAngles("Z_Fuel1", cvt((float)Math.sqrt(f), 0.0F, 1.0F, -59.5F, 123.0F), 0.0F, 0.0F);

    f = cvt(this.fm.M.fuel, 0.0F, 2760.0F, 0.0F, 270.0F);
    if (f < 45.0F)
      f = cvt(f, 0.0F, 45.0F, -58.0F, 45.0F);
    f += 58.0F;
    this.mesh.chunkSetAngles("Z_Fuel2", f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 28.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 1100.0F, 0.0F, 322.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM2", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 5000.0F, 0.0F, 289.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 150.0F, 0.0F, 116.75F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 150.0F, 0.0F, 116.75F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Oxypres1", 142.5F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 90.0F + this.setNew.azimuth, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", 90.0F + interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(7.0D), -7.0F, 7.0F, -15.0F, 15.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank3a", this.fm.Or.getKren(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 1.5F, -1.5F));

    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.338638F, 2.370465F, 0.0F, 320.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Ny1", cvt(this.fm.getOverload(), -4.0F, 12.0F, -80.5F, 241.5F), 0.0F, 0.0F);

    this.mesh.chunkVisible("A_Tren", this.fm.CT.getGear() > 0.95F);

    this.mesh.chunkVisible("A_Brake", this.fm.CT.AirBrakeControl > 0.5F);
    this.mesh.chunkVisible("A_Eng_Fire", this.fm.AS.astateEngineStates[0] > 2);
    this.mesh.chunkVisible("A_Eng_OH", this.fm.EI.engines[0].tOilOut > 125.0D);
    this.mesh.chunkVisible("A_ATO", (this.fm.EI.getPowerOutput() > 0.8F) && (this.fm.Gears.onGround()));
    this.mesh.chunkVisible("A_Fuel1", this.fm.M.fuel < 455.0F);
    this.mesh.chunkVisible("A_Fuel2", this.fm.M.fuel < 226.0F);

    if (this.tgun[0] == null) {
      this.tgun[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalDev07");
    }

    if (this.tgun[0] != null)
      this.mesh.chunkVisible("A_Fuel3", this.tgun[0].countBullets() > 0);
    this.mesh.chunkVisible("TankMain", this.tgun[0].countBullets() == 0);
    this.mesh.chunkVisible("TankDrop", this.tgun[0].countBullets() > 0);

    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN06");
    }

    if (this.bgun[0] == null) {
      this.bgun[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb01");
      this.bgun[1] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalBomb03");
    }

    if (this.rgun[0] == null) {
      this.rgun[0] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock02");
      this.rgun[1] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock22");
      this.rgun[2] = ((Aircraft)this.fm.actor).getBulletEmitterByHookName("_ExternalRock09");
    }

    if (this.gun[0] != null)
      this.mesh.chunkVisible("A_Guns_A", this.gun[0].countBullets() > 0);
    this.mesh.chunkVisible("A_Guns_E", this.gun[0].countBullets() < 50);

    if (this.bgun[0] != null)
      this.mesh.chunkVisible("A_Bomb_I", this.bgun[0].countBullets() > 0);
    if (this.bgun[1] != null) {
      this.mesh.chunkVisible("A_Bomb_O", this.bgun[1].countBullets() > 0);
    }
    if (this.rgun[0] != null)
      this.mesh.chunkVisible("A_Rock_I2", this.rgun[0].countBullets() > 0);
    if (this.rgun[1] != null)
      this.mesh.chunkVisible("A_Rock_I", this.rgun[1].countBullets() > 0);
    if (this.rgun[2] != null)
      this.mesh.chunkVisible("A_Rock_O", this.rgun[2].countBullets() > 0);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("Mira", false);
      this.mesh.chunkVisible("Mira_D", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_RETICLE1", false);
      for (int i = 1; i < 7; i++)
        this.mesh.chunkVisible("Z_Z_AIMMARK" + i, false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0)) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0);
    retoggleLight();
  }

  public void toggleLight() {
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

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float stage;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitF84G1 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitF84G1.1 arg2)
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
      if (CockpitF84G1.this.fm != null) {
        CockpitF84G1.access$102(CockpitF84G1.this, CockpitF84G1.this.setOld);
        CockpitF84G1.access$202(CockpitF84G1.this, CockpitF84G1.this.setNew);
        CockpitF84G1.access$302(CockpitF84G1.this, CockpitF84G1.this.setTmp);
        CockpitF84G1.this.setNew.throttle = (0.85F * CockpitF84G1.this.setOld.throttle + CockpitF84G1.this.fm.CT.PowerControl * 0.15F);

        CockpitF84G1.this.setNew.prop = (0.85F * CockpitF84G1.this.setOld.prop + CockpitF84G1.this.fm.CT.getStepControl() * 0.15F);

        CockpitF84G1.this.setNew.stage = (0.85F * CockpitF84G1.this.setOld.stage + CockpitF84G1.this.fm.EI.engines[0].getControlCompressor() * 0.15F);

        CockpitF84G1.this.setNew.mix = (0.85F * CockpitF84G1.this.setOld.mix + CockpitF84G1.this.fm.EI.engines[0].getControlMix() * 0.15F);

        CockpitF84G1.this.setNew.altimeter = CockpitF84G1.this.fm.getAltitude();
        CockpitF84G1.this.setNew.azimuth = ((35.0F * CockpitF84G1.this.setOld.azimuth + -CockpitF84G1.this.fm.Or.getYaw()) / 36.0F);

        if ((CockpitF84G1.this.setOld.azimuth > 270.0F) && (CockpitF84G1.this.setNew.azimuth < 90.0F))
          CockpitF84G1.this.setOld.azimuth -= 360.0F;
        if ((CockpitF84G1.this.setOld.azimuth < 90.0F) && (CockpitF84G1.this.setNew.azimuth > 270.0F))
          CockpitF84G1.this.setOld.azimuth += 360.0F;
        CockpitF84G1.this.setNew.waypointAzimuth = ((10.0F * CockpitF84G1.this.setOld.waypointAzimuth + CockpitF84G1.this.waypointAzimuth() + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);

        CockpitF84G1.this.setNew.vspeed = ((199.0F * CockpitF84G1.this.setOld.vspeed + CockpitF84G1.this.fm.getVertSpeed()) / 200.0F);

        float f1 = ((F84G1)CockpitF84G1.this.aircraft()).k14Distance;
        CockpitF84G1.this.setNew.k14w = (5.0F * CockpitF84G1.k14TargetWingspanScale[((F84G1)CockpitF84G1.this.aircraft()).k14WingspanType] / f1);

        CockpitF84G1.this.setNew.k14w = (0.9F * CockpitF84G1.this.setOld.k14w + 0.1F * CockpitF84G1.this.setNew.k14w);
        CockpitF84G1.this.setNew.k14wingspan = (0.9F * CockpitF84G1.this.setOld.k14wingspan + 0.1F * CockpitF84G1.k14TargetMarkScale[((F84G1)CockpitF84G1.this.aircraft()).k14WingspanType]);

        CockpitF84G1.this.setNew.k14mode = (0.8F * CockpitF84G1.this.setOld.k14mode + 0.2F * ((F84G1)CockpitF84G1.this.aircraft()).k14Mode);

        Vector3d localVector3d = CockpitF84G1.this.aircraft().FM.getW();
        double d = 0.00125D * f1;
        float f2 = (float)Math.toDegrees(d * localVector3d.z);
        float f3 = -(float)Math.toDegrees(d * localVector3d.y);
        float f4 = CockpitF84G1.this.floatindex((f1 - 200.0F) * 0.04F, CockpitF84G1.k14BulletDrop) - CockpitF84G1.k14BulletDrop[0];

        f3 += (float)Math.toDegrees(Math.atan(f4 / f1));
        CockpitF84G1.this.setNew.k14x = (0.92F * CockpitF84G1.this.setOld.k14x + 0.08F * f2);
        CockpitF84G1.this.setNew.k14y = (0.92F * CockpitF84G1.this.setOld.k14y + 0.08F * f3);
        if (CockpitF84G1.this.setNew.k14x > 7.0F)
          CockpitF84G1.this.setNew.k14x = 7.0F;
        if (CockpitF84G1.this.setNew.k14x < -7.0F)
          CockpitF84G1.this.setNew.k14x = -7.0F;
        if (CockpitF84G1.this.setNew.k14y > 7.0F)
          CockpitF84G1.this.setNew.k14y = 7.0F;
        if (CockpitF84G1.this.setNew.k14y < -7.0F)
          CockpitF84G1.this.setNew.k14y = -7.0F;
      }
      return true;
    }
  }
}