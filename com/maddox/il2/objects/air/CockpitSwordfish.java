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
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitSwordfish extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictBrake = 0.0F;
  private float pictFlap = 0.0F;
  private float pictSupc = 0.0F;
  private float pictLlit = 0.0F;
  private float pictManf = 1.0F;
  private boolean bNeedSetUp = true;
  private static final float[] speedometerScale = { 0.0F, 15.5F, 76.0F, 153.5F, 234.0F, 304.0F, 372.5F, 440.0F, 504.0F, 566.0F, 630.0F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 30.5F, 40.5F, 51.5F, 68.0F, 89.0F, 114.0F, 145.5F, 181.0F, 222.0F, 270.5F, 331.5F };

  private static final float[] rpmScale = { 0.0F, 15.0F, 32.0F, 69.5F, 106.5F, 143.0F, 180.0F, 217.5F, 253.0F, 290.0F, 327.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };

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

  public CockpitSwordfish() {
    super("3DO/Cockpit/TempestMkV/swordhier.him", "bf109");
    this.cockpitNightMats = new String[] { "TEMPPIT5-op", "TEMPPIT6-op", "TEMPPIT14-op", "TEMPPIT18-op", "TEMPPIT22-op", "TEMPPIT28-op", "TEMPPIT38-op", "TEMPPIT1-tr", "TEMPPIT2-tr", "TEMPPIT3-tr", "TEMPPIT4-tr", "TEMPPIT5-tr", "TEMPPIT6-tr", "TEMPPIT14-tr", "TEMPPIT18-tr", "TEMPPIT22-tr", "TEMPPIT28-tr", "TEMPPIT38-tr", "TEMPPIT1_damage", "TEMPPIT3_damage" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.mesh.chunkVisible("XLampGearUpR", ((this.fm.CT.getGear() > 0.01F) && (this.fm.CT.getGear() < 0.99F)) || (!this.fm.Gears.rgear));

    this.mesh.chunkVisible("XLampGearUpL", ((this.fm.CT.getGear() > 0.01F) && (this.fm.CT.getGear() < 0.99F)) || (!this.fm.Gears.lgear));

    this.mesh.chunkVisible("XLampGearUpC", (this.fm.CT.getGear() > 0.01F) && (this.fm.CT.getGear() < 0.99F));

    this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));

    this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));

    this.mesh.chunkVisible("XLampGearDownC", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkSetAngles("Z_Columnbase", 16.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Column", 45.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Elev", -16.0F * this.pictElev, 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.pictAiler, -1.0F, 1.0F, -0.027F, 0.027F);
    this.mesh.chunkSetLocate("Z_Shlang01", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (-Cockpit.xyz[2]);
    this.mesh.chunkSetLocate("Z_Shlang02", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Throtle1", 65.449997F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_BasePedal", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F, -20.0F * this.fm.CT.getRudder());

    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F, -20.0F * this.fm.CT.getRudder());

    this.mesh.chunkSetAngles("Z_Gear1", cvt(this.setNew.gearPhi, 0.2F, 0.8F, 0.0F, 116.0F), 0.0F, 0.0F);

    if (this.setNew.gearPhi < 0.5F) {
      this.mesh.chunkSetAngles("Z_Gear2", cvt(this.setNew.gearPhi, 0.0F, 0.2F, 0.0F, -65.0F), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Gear2", cvt(this.setNew.gearPhi, 0.8F, 1.0F, -65.0F, 0.0F), 0.0F, 0.0F);
    }

    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        f = 24.0F;
      else
        f = -24.0F;
    }
    else f = 0.0F;
    this.mesh.chunkSetAngles("Z_Flaps1", this.pictFlap = 0.8F * this.pictFlap + 0.2F * f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Trim1", 1000.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Prop1", 72.5F * this.setNew.prop, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Surch1", cvt(this.pictSupc = 0.8F * this.pictSupc + 0.1F * this.fm.EI.engines[0].getControlCompressor(), 0.0F, 1.0F, 0.0F, 60.0F), 0.0F, 0.0F);

    float f = 0.0F;
    if (this.fm.AS.bLandingLightOn)
      f = 66.0F;
    this.mesh.chunkSetAngles("Z_Land1", this.pictLlit = 0.85F * this.pictLlit + 0.15F * f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("COMPASS_M", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELK_V_LONG", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 257.22217F, 0.0F, 10.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("SHKALA_DIRECTOR", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_VY", -floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_BOOST", cvt(this.pictManf = 0.91F * this.pictManf + 0.09F * this.fm.EI.engines[0].getManifoldPressure(), 0.7242097F, 2.103161F, 60.0F, -240.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_FUEL1", cvt(this.fm.M.fuel, 88.379997F, 350.23001F, 0.0F, -306.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_FUEL2", cvt((float)Math.sqrt(this.fm.M.fuel), 0.0F, (float)Math.sqrt(88.379997253417969D), 0.0F, -317.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_FUEL3", cvt(this.fm.M.fuel, 88.379997F, 170.2F, 0.0F, -311.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_FUEL4", cvt(this.fm.M.fuel, 88.379997F, 170.2F, 0.0F, -311.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_RPM", -floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 5000.0F, 0.0F, 10.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELK_TEMP_OIL", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 160.0F, 0.0F, -306.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELK_TEMP_RAD", -floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STR_OIL_LB", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -36.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELK_TURN_UP", -cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("STREL_TURN_DOWN", -cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_GOR", this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.022F, -0.022F);

    this.mesh.chunkSetLocate("STRELKA_GOS", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("STRELKA_HOUR", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_MINUTE", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_SECUND", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("XGlassDamage4", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XGlassDamage3", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("STRELK_V_LONG", false);
      this.mesh.chunkVisible("STREL_ALT_LONG", false);
      this.mesh.chunkVisible("STREL_ALT_SHORT", false);
      this.mesh.chunkVisible("STRELKA_VY", false);
      this.mesh.chunkVisible("STRELKA_RPM", false);
      this.mesh.chunkVisible("STRELK_TEMP_RAD", false);
      this.mesh.chunkVisible("STRELK_TEMP_OIL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    retoggleLight();
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
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
    float altimeter;
    float azimuth;
    float vspeed;
    float gearPhi;
    float waypointAzimuth;
    private final CockpitSwordfish this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitSwordfish.1 arg2)
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
      if (CockpitSwordfish.this.fm != null) {
        CockpitSwordfish.access$102(CockpitSwordfish.this, CockpitSwordfish.this.setOld);
        CockpitSwordfish.access$202(CockpitSwordfish.this, CockpitSwordfish.this.setNew);
        CockpitSwordfish.access$302(CockpitSwordfish.this, CockpitSwordfish.this.setTmp);
        CockpitSwordfish.this.setNew.throttle = (0.92F * CockpitSwordfish.this.setOld.throttle + 0.08F * CockpitSwordfish.this.fm.CT.PowerControl);

        CockpitSwordfish.this.setNew.prop = (0.92F * CockpitSwordfish.this.setOld.prop + 0.08F * CockpitSwordfish.this.fm.EI.engines[0].getControlProp());

        CockpitSwordfish.this.setNew.mix = (0.92F * CockpitSwordfish.this.setOld.mix + 0.08F * CockpitSwordfish.this.fm.EI.engines[0].getControlMix());

        CockpitSwordfish.this.setNew.altimeter = CockpitSwordfish.this.fm.getAltitude();
        CockpitSwordfish.this.setNew.azimuth = (0.97F * CockpitSwordfish.this.setOld.azimuth + 0.03F * -CockpitSwordfish.this.fm.Or.getYaw());

        if ((CockpitSwordfish.this.setOld.azimuth > 270.0F) && (CockpitSwordfish.this.setNew.azimuth < 90.0F))
          CockpitSwordfish.this.setOld.azimuth -= 360.0F;
        if ((CockpitSwordfish.this.setOld.azimuth < 90.0F) && (CockpitSwordfish.this.setNew.azimuth > 270.0F))
          CockpitSwordfish.this.setOld.azimuth += 360.0F;
        CockpitSwordfish.this.setNew.waypointAzimuth = (0.91F * CockpitSwordfish.this.setOld.waypointAzimuth + 0.09F * (CockpitSwordfish.this.waypointAzimuth() - CockpitSwordfish.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F));

        CockpitSwordfish.this.setNew.vspeed = (0.99F * CockpitSwordfish.this.setOld.vspeed + 0.01F * CockpitSwordfish.this.fm.getVertSpeed());

        if (CockpitSwordfish.this.fm.CT.GearControl < 0.5F) {
          if (CockpitSwordfish.this.setNew.gearPhi > 0.0F)
            CockpitSwordfish.this.setNew.gearPhi = (CockpitSwordfish.this.setOld.gearPhi - 0.021F);
        } else if (CockpitSwordfish.this.setNew.gearPhi < 1.0F)
          CockpitSwordfish.this.setNew.gearPhi = (CockpitSwordfish.this.setOld.gearPhi + 0.021F);
      }
      return true;
    }
  }
}