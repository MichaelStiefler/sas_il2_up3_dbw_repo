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

public class CockpitP_40M extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private long t1 = 0L;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 17.0F, 56.5F, 107.5F, 157.0F, 204.0F, 220.5F, 238.5F, 256.5F, 274.5F, 293.0F, 311.0F, 330.0F, 342.0F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitP_40M()
  {
    super("3DO/Cockpit/P-40M/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Textur1", "Textur2", "Textur25", "Textur3", "Textur4", "Textur6", "Textur7", "Textur9" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Z_Throttle1", -66.809998F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    if (this.fm.CT.getStepControlAuto())
      this.mesh.chunkSetAngles("Z_Pitch1", -70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Pitch1", -70.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Mixt1", -70.800003F * this.fm.EI.engines[0].getControlMix(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Columnbase", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 16.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F, -20.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LeftPedal2", 0.0F, 0.0F, 23.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LPedalStep", 0.0F, 0.0F, -20.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F, 20.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RightPedal2", 0.0F, 0.0F, -23.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RPedalStep", 0.0F, 0.0F, 20.0F * this.fm.CT.getRudder());

    this.mesh.chunkSetAngles("Z_SW_LandLights", this.fm.AS.bLandingLightOn ? 60.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_SW_UVLights", this.cockpitLightControl ? 60.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_SW_NavLights", this.fm.AS.bNavLightsOn ? 60.0F : 0.0F, 0.0F, 0.0F);

    this.mesh.chunkVisible("XLampCoolant1", this.fm.EI.engines[0].tOilOut > this.fm.EI.engines[0].tOilCritMax);
    this.mesh.chunkVisible("XLampFuel1", this.fm.M.fuel < 40.099998F);
    this.mesh.chunkVisible("XLampGear1", (this.fm.Gears.isAnyDamaged()) || (!this.fm.Gears.isOperable()));

    this.mesh.chunkSetAngles("Z_Altimeter1", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -10800.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", -cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, -1080.0F), 0.0F, 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 25.0F, -25.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0321F, -0.0321F);
    this.mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
    this.mesh.chunkSetAngles("Z_TurnBank3", -cvt(getBall(7.0D), -7.0F, 7.0F, -14.0F, 14.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Carbair1", cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -50.0F, 50.0F, -60.0F, 60.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Flaps1", cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Gearc1", cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F, 0.0F);
    if (this.fm.Gears.lgear) {
      this.mesh.chunkSetAngles("Z_GearL1", cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F, 0.0F);
    }
    if (this.fm.Gears.rgear) {
      this.mesh.chunkSetAngles("Z_GearR1", cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Coolant1", cvt(this.fm.EI.engines[0].tWaterOut, 40.0F, 160.0F, 0.0F, 130.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 4.0F, 0.0F, -180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 343.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Heading1", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 280.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Gauge", false);
      this.mesh.chunkVisible("Gauge_D1", true);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_Carbair1", false);
      this.mesh.chunkVisible("Z_Coolant1", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("HullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.chunkVisible("HullDamage4", true);
    }
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

  private void retoggleLight() {
    if (this.cockpitLightControl) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitP_40M.this.fm != null) {
        CockpitP_40M.access$102(CockpitP_40M.this, CockpitP_40M.this.setOld); CockpitP_40M.access$202(CockpitP_40M.this, CockpitP_40M.this.setNew); CockpitP_40M.access$302(CockpitP_40M.this, CockpitP_40M.this.setTmp);

        CockpitP_40M.this.setNew.throttle = ((10.0F * CockpitP_40M.this.setOld.throttle + CockpitP_40M.this.fm.CT.PowerControl) / 11.0F);
        CockpitP_40M.this.setNew.prop = ((8.0F * CockpitP_40M.this.setOld.prop + CockpitP_40M.this.fm.CT.getStepControl()) / 9.0F);
        CockpitP_40M.this.setNew.altimeter = CockpitP_40M.this.fm.getAltitude();
        if (Math.abs(CockpitP_40M.this.fm.Or.getKren()) < 45.0F) {
          CockpitP_40M.this.setNew.azimuth = ((35.0F * CockpitP_40M.this.setOld.azimuth + -CockpitP_40M.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitP_40M.this.setOld.azimuth > 270.0F) && (CockpitP_40M.this.setNew.azimuth < 90.0F)) CockpitP_40M.this.setOld.azimuth -= 360.0F;
        if ((CockpitP_40M.this.setOld.azimuth < 90.0F) && (CockpitP_40M.this.setNew.azimuth > 270.0F)) CockpitP_40M.this.setOld.azimuth += 360.0F;
        CockpitP_40M.this.setNew.waypointAzimuth = ((10.0F * CockpitP_40M.this.setOld.waypointAzimuth + (CockpitP_40M.this.waypointAzimuth() - CockpitP_40M.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        CockpitP_40M.this.setNew.vspeed = ((199.0F * CockpitP_40M.this.setOld.vspeed + CockpitP_40M.this.fm.getVertSpeed()) / 200.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitP_40M this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitP_40M.1 arg2) { this();
    }
  }
}