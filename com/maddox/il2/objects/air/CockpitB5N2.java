package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitB5N2 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictGear = 0.0F;
  private float pictTE = 0.0F;
  private float pictTR = 0.0F;
  private static final float[] speedometerScale = { 0.0F, -2.0F, -7.5F, 22.0F, 65.5F, 109.0F, 153.0F, 197.0F, 242.5F, 290.0F, 338.0F, 374.0F, 407.0F, 439.0F, 471.0F, 503.0F, 536.5F, 570.0F, 606.0F, 643.5F, 676.0F };

  private static final float[] revolutionsScale = { 0.0F, 20.0F, 75.0F, 125.0F, 180.0F, 220.0F, 285.0F, 335.0F };

  private static final float[] oilTScale = { 0.0F, 20.0F, 70.5F, 122.5F, 180.0F, 237.5F, 290.5F, 338.0F };

  private static final float[] frAirTempScale = { 0.0F, 20.5F, 37.0F, 48.5F, 60.5F, 75.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);

    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) f += 180.0F;

    while (f > 180.0F) f -= 180.0F;

    return f;
  }

  public CockpitB5N2() {
    super("3DO/Cockpit/N1K2-Ja/CockpitB5N2.him", "bf109");
    this.cockpitNightMats = new String[] { "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_05", "Gauges_06", "Gauges_07", "DGauges_01", "DGauges_02", "DGauges_03", "DGauges_04", "DGauges_05", "DGauges_06" };

    setNightMats(false);
    this.cockpitDimControl = (!this.cockpitDimControl);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    this.mesh.chunkSetAngles("CanopyOpenLvr", cvt(this.fm.CT.getCockpitDoor(), 0.0F, 0.1F, 0.0F, 63.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.1F, 0.99F, 0.0F, 0.61F);
    this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.fm.CT.getCockpitDoor(), 0.0F, 0.1F, 0.0F, 0.0132F);

    this.mesh.chunkSetLocate("CanopyOpenRodL", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = cvt(this.fm.CT.getCockpitDoor(), 0.0F, 0.1F, 0.0F, -0.0128F);

    this.mesh.chunkSetLocate("CanopyOpenRodR", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("GSDimmArm", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 64.0F, 0.0F), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, -0.0594F, 0.0F);

    this.mesh.chunkSetLocate("GSDimmBase", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("IgnitionSwitch", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 113.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("GearHandle", cvt(this.pictGear = 0.9F * this.pictGear + 0.1F * this.fm.CT.GearControl, 0.0F, 1.0F, 0.0F, -31.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("GearHandleMan", 0.0F, 0.0F, 0.0F);
    if (this.fm.CT.FlapsControl != 0.19F) {
      this.mesh.chunkSetAngles("FlapHandle", -48.0F * this.setNew.flap, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("TQHandle", 48.0F * this.setNew.throttle, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TRigger", this.fm.CT.saveWeaponControl[1] != 0 ? -12.0F : 0.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("PropPitchLvr", 51.0F * this.setNew.pitch, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("MixLvr", 45.0F * this.setNew.mix, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("ChargerLvr", cvt(this.fm.EI.engines[0].getControlCompressor(), 0.0F, 2.0F, 0.0F, 49.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ChargerAutoLvr", cvt(this.fm.EI.engines[0].getControlCompressor(), 0.0F, 2.0F, 0.0F, 49.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("CowlFlapLvr", -750.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("OilCoolerLvr", -450.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("PedalCrossBar", 25.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Pedal_L", 35.0F * this.fm.CT.getBrake(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 35.0F * this.fm.CT.getBrake(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("FLCS", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, 0.0F, 20.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("FLCSRod", 0.0F, 0.0F, -20.0F * this.pictElev);
    this.mesh.chunkSetAngles("ElevTrim", 450.0F * (this.pictTE = 0.9F * this.pictTE + 0.1F * this.fm.CT.trimElevator), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RuddrTrim", -450.0F * (this.pictTR = 0.9F * this.pictTR + 0.1F * this.fm.CT.trimRudder), 0.0F, 0.0F);

    if (this.cockpitLightControl)
      this.mesh.chunkSetAngles("SW_UVLight", 0.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("SW_UVLight", 0.0F, 0.0F, -54.0F);
    resetYPRmodifier();
    float f = this.pictTE;
    if (f < 0.0F)
      Cockpit.xyz[1] = cvt(f, -0.25F, 0.0F, -0.02305F, 0.0F);
    else
      Cockpit.xyz[1] = cvt(f, 0.0F, 0.5F, 0.0F, 0.04985F);
    this.mesh.chunkSetLocate("NeedElevTrim", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("NeedRuddrTrim", cvt(this.pictTR, -0.5F, 0.5F, 90.0F, -90.0F), 0.0F, 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || ((this.fm.AS.astateCockpitState & 0x10) == 0) || ((this.fm.AS.astateCockpitState & 0x4) == 0))
    {
      this.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -this.fm.Or.getKren(), 0.0F);
      this.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 20.0F, -20.0F));
    }

    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || ((this.fm.AS.astateCockpitState & 0x8) == 0) || ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.mesh.chunkSetAngles("NeedCompass_A", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -20.0F, 20.0F, 20.0F, -20.0F));

      this.mesh.chunkSetAngles("NeedCompass_B", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("NeedAlt_Km", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 1440.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedAlt_M", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 14400.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedBank", cvt(getBall(8.0D), -8.0F, 8.0F, 10.0F, -10.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("NeedTurn", cvt(this.w.z, -0.23562F, 0.23562F, -25.0F, 25.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedClimb", cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedCylTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 360.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedExhTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 324.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedFuelA", cvt(this.fm.M.fuel, 171.0F, 600.0F, 0.0F, 290.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedFuelB", cvt(this.fm.M.fuel, 0.0F, 171.0F, 0.0F, 153.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedFuelPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.6F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedMin", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedManPress", cvt(this.setNew.manifold, 0.33339F, 1.66661F, -162.5F, 162.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedOilPress", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedOilTemp", -floatindex(cvt(this.fm.EI.engines[0].tOilOut, 30.0F, 110.0F, 0.0F, 8.0F), oilTScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedRPM", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 7.0F), revolutionsScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 740.7998F, 0.0F, 20.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedVMPressA", cvt(this.fm.M.nitro, 0.0F, 3.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedVMPressB", cvt((float)Math.sqrt(this.fm.M.nitro), 0.0F, 8.0F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedExternalT", floatindex(cvt(Atmosphere.temperature((float)this.fm.Loc.z), 233.09F, 333.09F, 0.0F, 5.0F), frAirTempScale), 0.0F, 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x10) == 0) {
      this.mesh.chunkSetAngles("NeedDF", cvt(this.setNew.waypointAzimuth.getDeg(paramFloat), -90.0F, 90.0F, -33.0F, 33.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkVisible("FlareGearDn_L", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));

    this.mesh.chunkVisible("FlareGearDn_R", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));

    this.mesh.chunkVisible("FlareGearDn_C", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("FlareGearUp_L", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));

    this.mesh.chunkVisible("FlareGearUp_R", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));

    this.mesh.chunkVisible("FlareGearUp_C", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("FlareBoostRed", this.fm.M.fuel > 52.5F);
    this.mesh.chunkVisible("FlareBoostGreen", this.fm.M.fuel < 52.5F);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("DamageHull2", true);
      this.mesh.chunkVisible("Gages5_D0", false);
      this.mesh.chunkVisible("Gages5_D1", true);
      this.mesh.chunkVisible("NeedFuelA", false);
      this.mesh.chunkVisible("NeedFuelB", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("DamageHull3", true);
      this.mesh.chunkVisible("Gages2_D0", false);
      this.mesh.chunkVisible("Gages2_D1", true);
      this.mesh.chunkVisible("NeedCylTemp", false);
      this.mesh.chunkVisible("NeedFuelPress", false);
      this.mesh.chunkVisible("NeedOilPress", false);
      this.mesh.chunkVisible("NeedOilTemp", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("DamageHull2", true);
      this.mesh.chunkVisible("Gages4_D0", false);
      this.mesh.chunkVisible("Gages4_D1", true);
      this.mesh.chunkVisible("NeedSpeed", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("DamageHull3", true);
      this.mesh.chunkVisible("Gages3_D0", false);
      this.mesh.chunkVisible("Gages3_D1", true);
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("NeedAlt_M", false);
      this.mesh.chunkVisible("NeedClimb", false);
      this.mesh.chunkVisible("NeedVMPressA", false);
      this.mesh.chunkVisible("NeedVMPressB", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("OilSplats", true);
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("GunSight_T3", false);
      this.mesh.chunkVisible("DGS_Lenz", true);
      this.mesh.chunkVisible("GSGlassMain", false);
      this.mesh.chunkVisible("GSDimmArm", false);
      this.mesh.chunkVisible("GSDimmBase", false);
      this.mesh.chunkVisible("GSGlassDimm", false);
      this.mesh.chunkVisible("DGunSight_T3", true);
      this.mesh.chunkVisible("DGS_Lenz", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("DamageGlass1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("DamageGlass2", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("DamageHull1", true);
      this.mesh.chunkVisible("Gages1_D0", false);
      this.mesh.chunkVisible("Gages1_D1", true);
      this.mesh.chunkVisible("NeedRPM", false);
      this.mesh.chunkVisible("NeedManPress", false);
      this.mesh.chunkVisible("NeedExhTemp", false);
      this.mesh.chunkVisible("NeedTurn", false);
      this.mesh.chunkVisible("NeedBank", false);
    }
    retoggleLight();
  }

  public void toggleDim() {
    this.cockpitDimControl = (!this.cockpitDimControl);
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
    float flap;
    float throttle;
    float pitch;
    float mix;
    float gear;
    float tlock;
    float altimeter;
    float manifold;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    private final CockpitB5N2 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitB5N2.1 arg2)
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
      if (CockpitB5N2.this.fm != null) {
        CockpitB5N2.access$102(CockpitB5N2.this, CockpitB5N2.this.setOld);
        CockpitB5N2.access$202(CockpitB5N2.this, CockpitB5N2.this.setNew);
        CockpitB5N2.access$302(CockpitB5N2.this, CockpitB5N2.this.setTmp);
        CockpitB5N2.this.setNew.flap = (0.88F * CockpitB5N2.this.setOld.flap + 0.12F * CockpitB5N2.this.fm.CT.FlapsControl);
        CockpitB5N2.this.setNew.tlock = (0.7F * CockpitB5N2.this.setOld.tlock + 0.3F * (CockpitB5N2.this.fm.Gears.bTailwheelLocked ? 1.0F : 0.0F));

        if (CockpitB5N2.this.cockpitDimControl) {
          if (CockpitB5N2.this.setNew.dimPosition > 0.0F)
            CockpitB5N2.this.setNew.dimPosition = (CockpitB5N2.this.setOld.dimPosition - 0.05F);
        } else if (CockpitB5N2.this.setNew.dimPosition < 1.0F)
          CockpitB5N2.this.setNew.dimPosition = (CockpitB5N2.this.setOld.dimPosition + 0.05F);
        if ((CockpitB5N2.this.fm.CT.GearControl < 0.5F) && (CockpitB5N2.this.setNew.gear < 1.0F))
          CockpitB5N2.this.setNew.gear = (CockpitB5N2.this.setOld.gear + 0.02F);
        if ((CockpitB5N2.this.fm.CT.GearControl > 0.5F) && (CockpitB5N2.this.setNew.gear > 0.0F))
          CockpitB5N2.this.setNew.gear = (CockpitB5N2.this.setOld.gear - 0.02F);
        CockpitB5N2.this.setNew.throttle = (0.9F * CockpitB5N2.this.setOld.throttle + 0.1F * CockpitB5N2.this.fm.CT.PowerControl);

        CockpitB5N2.this.setNew.manifold = (0.8F * CockpitB5N2.this.setOld.manifold + 0.2F * CockpitB5N2.this.fm.EI.engines[0].getManifoldPressure());

        CockpitB5N2.this.setNew.pitch = (0.9F * CockpitB5N2.this.setOld.pitch + 0.1F * CockpitB5N2.this.fm.EI.engines[0].getControlProp());

        CockpitB5N2.this.setNew.mix = (0.9F * CockpitB5N2.this.setOld.mix + 0.1F * CockpitB5N2.this.fm.EI.engines[0].getControlMix());

        CockpitB5N2.this.setNew.altimeter = CockpitB5N2.this.fm.getAltitude();
        CockpitB5N2.this.setNew.azimuth.setDeg(CockpitB5N2.this.setOld.azimuth.getDeg(1.0F), CockpitB5N2.this.fm.Or.azimut());

        float f = CockpitB5N2.this.waypointAzimuth();
        CockpitB5N2.this.setNew.waypointAzimuth.setDeg(CockpitB5N2.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitB5N2.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-3.0F, 3.0F));

        CockpitB5N2.this.setNew.vspeed = ((199.0F * CockpitB5N2.this.setOld.vspeed + CockpitB5N2.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }
}