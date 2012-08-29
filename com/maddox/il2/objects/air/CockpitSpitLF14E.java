package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitSpitLF14E extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler;
  private float pictElev;
  private float pictBrake = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf = 1.0F;
  private boolean bNeedSetUp = true;
  private static final float[] speedometerScale = { 0.0F, 7.5F, 17.5F, 37.0F, 63.0F, 88.5F, 114.5F, 143.0F, 171.5F, 202.5F, 228.5F, 255.5F, 282.0F, 309.0F, 336.5F, 366.5F, 394.0F, 421.0F, 448.5F, 474.5F, 500.5F, 530.0F, 557.5F, 584.0F, 609.0F, 629.0F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F, 125.0F, 161.0F, 202.5F, 253.0F, 315.5F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 22.0F, 58.0F, 103.5F, 152.5F, 193.5F, 245.0F, 281.5F, 311.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };

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

  public CockpitSpitLF14E() {
    super("3DO/Cockpit/SpitfireMkLFXIVE/hier.him", "bf109");
    this.cockpitNightMats = new String[] { "COMPASS", "BORT2", "prib_five", "prib_five_damage", "prib_one", "prib_one_damage", "prib_three", "prib_three_damage", "prib_two", "prib_two_damage", "text13", "text15" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  protected boolean doFocusEnter() {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Blister2_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("Blister2_D0", true);
    super.doFocusLeave();
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    int i = ((SPITFIRELF14E)aircraft()).k14Mode;
    boolean bool = i < 2;
    this.mesh.chunkVisible("Z_Z_RETICLE", bool);
    bool = i > 0;
    this.mesh.chunkVisible("Z_Z_RETICLE1", bool);
    this.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, this.setNew.k14x, this.setNew.k14y);
    resetYPRmodifier();
    Cockpit.xyz[0] = this.setNew.k14w;
    for (i = 1; i < 7; i++) {
      this.mesh.chunkVisible("Z_Z_AIMMARK" + i, bool);
      this.mesh.chunkSetLocate("Z_Z_AIMMARK" + i, Cockpit.xyz, Cockpit.ypr);
    }
    resetYPRmodifier();
    int j = 1;
    try {
      if (this.fm.CT.bMoveSideDoor) {
        this.mesh.chunkSetAngles("Door", 0.0F, -170.0F * this.fm.CT.getCockpitDoor(), 0.0F);
        j = 0;
      }
    }
    catch (Throwable localThrowable) {
    }
    if (j != 0) {
      Cockpit.xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.55F);
      this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    }
    this.mesh.chunkVisible("XLampGearUpL", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("XLampGearDownL", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("XLampboost", this.fm.EI.engines[0].getControlCompressor() > 0);

    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F);

    this.mesh.chunkSetAngles("Z_Column", -30.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Target1", this.setNew.k14wingspan, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01b", -9.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang01c", -12.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02b", -7.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang02c", -15.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03a", -5.0F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03b", -8.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Shlang03c", -18.5F * this.pictAiler, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Stick_Shtok01", 0.0F, 0.0F, 8.0F * this.pictElev);
    this.mesh.chunkSetAngles("Z_ColumnSwitch", -18.0F * (this.pictBrake = 0.89F * this.pictBrake + 0.11F * this.fm.CT.BrakeControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", cvt(interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 1.1F, 20.0F, -30.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_BasePedal", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = (0.0578F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-0.0578F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Gear1", -160.0F + 160.0F * (this.pictGear = 0.89F * this.pictGear + 0.11F * this.fm.CT.GearControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Flaps1", 160.0F * (this.pictFlap = 0.89F * this.pictFlap + 0.11F * this.fm.CT.FlapsControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Trim1", 1000.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Trim2", 1000.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Prop1", -30.0F * this.setNew.prop, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("COMPASS_M", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_LONG", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F));

    this.mesh.chunkSetAngles("STREL_ALT_SHORT", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F));

    this.mesh.chunkSetAngles("STREL_ALT_SHRT1", 0.0F, 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -108.0F));

    this.mesh.chunkSetAngles("STRELKA_BOOST", 0.0F, 0.0F, -cvt(this.pictManf = 0.91F * this.pictManf + 0.09F * this.fm.EI.engines[0].getManifoldPressure(), 0.5173668F, 2.72369F, -70.0F, 250.0F));

    this.mesh.chunkSetAngles("STRELKA_FUEL", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 378.54001F, 0.0F, 68.0F));

    this.mesh.chunkSetAngles("STRELKA_RPM", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 1000.0F, 5000.0F, 2.0F, 10.0F), rpmScale));

    this.mesh.chunkSetAngles("STRELK_TEMP_OIL", 0.0F, 0.0F, -cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 100.0F, 0.0F, 271.0F));

    this.mesh.chunkSetAngles("STRELK_TEMP_RAD", 0.0F, 0.0F, -floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale));

    this.mesh.chunkSetAngles("STR_OIL_LB", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -37.0F), 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("STREL_TURN_DOWN", 0.0F, 0.0F, -cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F));

    this.mesh.chunkSetAngles("STRELK_TURN_UP", 0.0F, 0.0F, -cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F));

    this.mesh.chunkVisible("STRELK_V_SHORT", false);
    this.mesh.chunkSetAngles("STRELK_V_LONG", 0.0F, 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 223.52003F, 0.0F, 25.0F), speedometerScale));

    this.mesh.chunkSetAngles("STRELKA_VY", 0.0F, 0.0F, -floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale));

    this.mesh.chunkSetAngles("STRELKA_GOR", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.032F, -0.032F);

    this.mesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("STR_CLIMB", 0.0F, 0.0F, cvt(this.fm.CT.trimElevator, -0.5F, 0.5F, -35.23F, 35.23F));
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
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

  static
  {
    Property.set(CockpitSpitLF14E.class, "normZN", 0.72F);

    Property.set(CockpitSpitLF14E.class, "gsZN", 0.66F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitSpitLF14E.this.fm != null) {
        CockpitSpitLF14E.access$102(CockpitSpitLF14E.this, CockpitSpitLF14E.this.setOld);
        CockpitSpitLF14E.access$202(CockpitSpitLF14E.this, CockpitSpitLF14E.this.setNew);
        CockpitSpitLF14E.access$302(CockpitSpitLF14E.this, CockpitSpitLF14E.this.setTmp);
        CockpitSpitLF14E.this.setNew.throttle = (0.92F * CockpitSpitLF14E.this.setOld.throttle + 0.08F * CockpitSpitLF14E.this.fm.CT.PowerControl);

        CockpitSpitLF14E.this.setNew.prop = (0.92F * CockpitSpitLF14E.this.setOld.prop + 0.08F * CockpitSpitLF14E.this.fm.EI.engines[0].getControlProp());

        CockpitSpitLF14E.this.setNew.mix = (0.92F * CockpitSpitLF14E.this.setOld.mix + 0.08F * CockpitSpitLF14E.this.fm.EI.engines[0].getControlMix());

        CockpitSpitLF14E.this.setNew.altimeter = CockpitSpitLF14E.this.fm.getAltitude();
        if (Math.abs(CockpitSpitLF14E.this.fm.Or.getKren()) < 30.0F) {
          CockpitSpitLF14E.this.setNew.azimuth = (0.97F * CockpitSpitLF14E.this.setOld.azimuth + 0.03F * -CockpitSpitLF14E.this.fm.Or.getYaw());
        }
        if ((CockpitSpitLF14E.this.setOld.azimuth > 270.0F) && (CockpitSpitLF14E.this.setNew.azimuth < 90.0F))
          CockpitSpitLF14E.this.setOld.azimuth -= 360.0F;
        if ((CockpitSpitLF14E.this.setOld.azimuth < 90.0F) && (CockpitSpitLF14E.this.setNew.azimuth > 270.0F))
          CockpitSpitLF14E.this.setOld.azimuth += 360.0F;
        CockpitSpitLF14E.this.setNew.waypointAzimuth = (0.91F * CockpitSpitLF14E.this.setOld.waypointAzimuth + 0.09F * (CockpitSpitLF14E.this.waypointAzimuth() - CockpitSpitLF14E.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F));

        CockpitSpitLF14E.this.setNew.vspeed = (0.99F * CockpitSpitLF14E.this.setOld.vspeed + 0.01F * CockpitSpitLF14E.this.fm.getVertSpeed());

        float f1 = ((SPITFIRELF14E)CockpitSpitLF14E.this.aircraft()).k14Distance;

        CockpitSpitLF14E.this.setNew.k14w = (5.0F * CockpitSpitLF14E.k14TargetWingspanScale[((SPITFIRELF14E)CockpitSpitLF14E.this.aircraft()).k14WingspanType] / f1);

        CockpitSpitLF14E.this.setNew.k14w = (0.9F * CockpitSpitLF14E.this.setOld.k14w + 0.1F * CockpitSpitLF14E.this.setNew.k14w);
        CockpitSpitLF14E.this.setNew.k14wingspan = (0.9F * CockpitSpitLF14E.this.setOld.k14wingspan + 0.1F * CockpitSpitLF14E.k14TargetMarkScale[((SPITFIRELF14E)CockpitSpitLF14E.this.aircraft()).k14WingspanType]);

        CockpitSpitLF14E.this.setNew.k14mode = (0.8F * CockpitSpitLF14E.this.setOld.k14mode + 0.2F * ((SPITFIRELF14E)CockpitSpitLF14E.this.aircraft()).k14Mode);

        Vector3d localVector3d = CockpitSpitLF14E.this.aircraft().FM.getW();
        double d = 0.00125D * f1;
        float f2 = (float)Math.toDegrees(d * localVector3d.z);
        float f3 = -(float)Math.toDegrees(d * localVector3d.y);
        float f4 = CockpitSpitLF14E.this.floatindex((f1 - 200.0F) * 0.04F, CockpitSpitLF14E.k14BulletDrop) - CockpitSpitLF14E.k14BulletDrop[0];

        f3 += (float)Math.toDegrees(Math.atan(f4 / f1));
        CockpitSpitLF14E.this.setNew.k14x = (0.92F * CockpitSpitLF14E.this.setOld.k14x + 0.08F * f2);
        CockpitSpitLF14E.this.setNew.k14y = (0.92F * CockpitSpitLF14E.this.setOld.k14y + 0.08F * f3);
        if (CockpitSpitLF14E.this.setNew.k14x > 7.0F)
          CockpitSpitLF14E.this.setNew.k14x = 7.0F;
        if (CockpitSpitLF14E.this.setNew.k14x < -7.0F)
          CockpitSpitLF14E.this.setNew.k14x = -7.0F;
        if (CockpitSpitLF14E.this.setNew.k14y > 7.0F)
          CockpitSpitLF14E.this.setNew.k14y = 7.0F;
        if (CockpitSpitLF14E.this.setNew.k14y < -7.0F)
          CockpitSpitLF14E.this.setNew.k14y = -7.0F;
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitSpitLF14E this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitSpitLF14E.1 arg2)
    {
      this();
    }
  }
}