package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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

public class CockpitKI_84_IB extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] vsiNeedleScale = { -200.0F, -160.0F, -125.0F, -90.0F, 90.0F, 125.0F, 160.0F, 200.0F };

  private static final float[] speedometerScale = { 0.0F, 10.0F, 35.0F, 70.0F, 105.0F, 145.0F, 190.0F, 230.0F, 275.0F, 315.0F, 360.0F, 397.5F, 435.0F, 470.0F, 505.0F, 537.5F, 570.0F, 600.0F, 630.0F, 655.0F, 680.0F };

  private static final float[] revolutionsScale = { 0.0F, 20.0F, 75.0F, 125.0F, 180.0F, 220.0F, 285.0F, 335.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) {
      f += 180.0F;
    }
    while (f > 180.0F) {
      f -= 180.0F;
    }
    return f;
  }

  public CockpitKI_84_IB()
  {
    super("3DO/Cockpit/Ki-84-Ib/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "GP_I", "GP_II", "GP_III", "GP_IV", "GP_V", "GP_VI", "GP_VII" };

    setNightMats(false);
    this.cockpitDimControl = (!this.cockpitDimControl);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("FLCS", -20.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 20.0F);

    resetYPRmodifier();
    xyz[1] = cvt(this.setNew.gear, 0.0F, 0.05F, 0.0F, -0.008F);
    if (this.setNew.gear > 0.85F) {
      xyz[1] = 0.0F;
    }
    this.mesh.chunkSetLocate("GearHandleKnob", xyz, ypr);
    this.mesh.chunkSetAngles("GearLockHandle", cvt(this.setNew.gear, 0.1F, 0.25F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("GearLockSpring", 0.0F, 0.0F, cvt(this.setNew.gear, 0.1F, 0.25F, 0.0F, 45.0F));
    this.mesh.chunkSetAngles("GearHandle", cvt(this.setNew.gear, 0.5F, 1.0F, 0.0F, 90.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, -60.0F * this.setNew.flap);
    resetYPRmodifier();
    if (Math.abs(this.setNew.flap - this.setOld.flap) > 0.001F) {
      xyz[2] = -0.008F;
    }
    this.mesh.chunkSetLocate("FlapHandleKnob", xyz, ypr);
    this.mesh.chunkSetAngles("TWheelLockLvr", -30.0F * this.setNew.tlock, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ChargerLvr", 0.0F, 0.0F, 40.0F * this.fm.EI.engines[0].getControlCompressor());
    this.mesh.chunkSetAngles("TQHandle", 0.0F, 0.0F, 54.545399F * this.setNew.throttle);
    this.mesh.chunkSetAngles("PropPitchLvr", 0.0F, 0.0F, 60.0F * this.setNew.pitch);
    this.mesh.chunkSetAngles("MixLvr", 0.0F, 0.0F, 60.0F * cvt(this.setNew.mix, 1.0F, 1.2F, 0.5F, 1.0F));
    this.mesh.chunkSetAngles("PedalCrossBar", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_L", 15.0F * this.fm.CT.getBrake(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 15.0F * this.fm.CT.getBrake(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("RudderCable_L", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("RudderCable_R", -15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PriTrigger", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[0] != 0 ? 15.0F : 0.0F);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[1] != 0) {
      xyz[2] = -0.005F;
    }
    this.mesh.chunkSetLocate("SecTrigger", xyz, ypr);
    if (this.fm.CT.saveWeaponControl[2] != 0) {
      xyz[2] = -0.005F;
    }
    this.mesh.chunkSetLocate("TreTrigger", xyz, ypr);
    this.mesh.chunkSetAngles("CowlFlapLvr", 0.0F, 0.0F, 50.0F * this.fm.EI.engines[0].getControlRadiator());
    this.mesh.chunkSetAngles("OilCoolerLvr", -86.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("FBoxSW_ANO", 0.0F, 0.0F, this.fm.AS.bNavLightsOn ? 50.0F : 0.0F);
    this.mesh.chunkSetAngles("FBoxSW_LandLt", 0.0F, 0.0F, this.fm.AS.bLandingLightOn ? 50.0F : 0.0F);
    this.mesh.chunkSetAngles("FBoxSW_Starter", 0.0F, 0.0F, this.fm.EI.engines[0].getStage() > 0 ? 50.0F : 0.0F);
    this.mesh.chunkSetAngles("FBoxSW_UVLight", 0.0F, 0.0F, this.cockpitLightControl ? 50.0F : 0.0F);
    this.mesh.chunkSetAngles("GSDimmArm", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -55.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 0.05F);
    this.mesh.chunkSetLocate("GSDimmBase", xyz, ypr);

    this.mesh.chunkSetAngles("NeedCylTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 360.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedExhTemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 324.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedOilTemp", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 130.0F, 0.0F, 75.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedOilPress", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.6F, 0.0F, 305.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedVMPress", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("IgnitionSwitch", this.fm.EI.engines[0].getStage() == 0 ? 0.0F : cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 60.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("NeedTurn", cvt(this.w.z, -0.23562F, 0.23562F, -25.0F, 25.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedBank", cvt(getBall(8.0D), -8.0F, 8.0F, 10.0F, -10.0F), 0.0F, 0.0F);
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || ((this.fm.AS.astateCockpitState & 0x10) == 0) || ((this.fm.AS.astateCockpitState & 0x4) == 0))
    {
      this.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -this.fm.Or.getKren(), 0.0F);
      this.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 20.0F, -20.0F));
    }
    this.mesh.chunkSetAngles("NeedClimb", floatindex(cvt(this.setNew.vspeed, -30.0F, 30.0F, 0.5F, 6.5F), vsiNeedleScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("NeedSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1000.0F, 0.0F, 20.0F), speedometerScale), 0.0F, 0.0F);
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || ((this.fm.AS.astateCockpitState & 0x8) == 0) || ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.mesh.chunkSetAngles("NeedCompass_A", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -20.0F, 20.0F, 20.0F, -20.0F));
      this.mesh.chunkSetAngles("NeedCompass_B", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("NeedAlt_Km", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_M", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedMin", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedManPress", cvt(this.setNew.manifold, 0.4000511F, 1.799932F, -144.0F, 192.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedRPM", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 7.0F), revolutionsScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedFuel", cvt(this.fm.M.fuel, 0.0F, 525.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("FlareFuelLow", (this.fm.M.fuel < 52.5F) && ((this.fm.AS.astateCockpitState & 0x40) == 0));
    this.mesh.chunkVisible("FlareGearDn_A", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("FlareGearDn_B", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("FlareGearUp_A", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("FlareGearUp_B", this.fm.CT.getGear() < 0.01F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.materialReplace("GP_II", "DGP_II");
      this.mesh.chunkVisible("NeedManPress", false);
      this.mesh.chunkVisible("NeedRPM", false);
      this.mesh.chunkVisible("NeedFuel", false);
      retoggleLight();
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.materialReplace("GP_III", "DGP_III");
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("NeedAlt_M", false);
      this.mesh.chunkVisible("NeedSpeed", false);
      retoggleLight();
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.materialReplace("GP_IV", "DGP_IV");
      this.mesh.chunkVisible("NeedCylTemp", false);
      this.mesh.chunkVisible("NeedOilTemp", false);
      this.mesh.chunkVisible("NeedVAmmeter", false);
      this.mesh.materialReplace("GP_IV_night", "DGP_IV_night");
      retoggleLight();
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.materialReplace("GP_V", "DGP_V");
      this.mesh.chunkVisible("NeedExhTemp", false);
      this.mesh.chunkVisible("NeedOilPress", false);
      this.mesh.chunkVisible("NeedHour", false);
      this.mesh.chunkVisible("NeedMin", false);
      this.mesh.chunkVisible("NeedTurn", false);
      this.mesh.chunkVisible("NeedBank", false);
      retoggleLight();
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("OilSplats", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("GunSight_T3", false);
      this.mesh.chunkVisible("GS_Lenz", false);
      this.mesh.chunkVisible("GSGlassMain", false);
      this.mesh.chunkVisible("GSDimmArm", false);
      this.mesh.chunkVisible("GSDimmBase", false);
      this.mesh.chunkVisible("GSGlassDimm", false);
      this.mesh.chunkVisible("DGunSight_T3", true);
      this.mesh.chunkVisible("DGS_Lenz", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);

      this.mesh.chunkVisible("DamageGlass2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("DamageGlass1", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.materialReplace("GP_VI", "DGP_VI");
      this.mesh.chunkVisible("NeedFuelPress", false);
      this.mesh.chunkVisible("NeedVMPress", false);
      this.mesh.chunkVisible("NeedClimb", false);
      this.mesh.materialReplace("GP_VI_night", "DGP_VI_night");
      retoggleLight();

      this.mesh.chunkVisible("DamageGlass4", true);
    }
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
      setNightMats(true);
    }
    else
    {
      setNightMats(false);
    }
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
      if (CockpitKI_84_IB.this.fm != null) {
        CockpitKI_84_IB.access$102(CockpitKI_84_IB.this, CockpitKI_84_IB.this.setOld); CockpitKI_84_IB.access$202(CockpitKI_84_IB.this, CockpitKI_84_IB.this.setNew); CockpitKI_84_IB.access$302(CockpitKI_84_IB.this, CockpitKI_84_IB.this.setTmp);

        CockpitKI_84_IB.this.setNew.flap = (0.88F * CockpitKI_84_IB.this.setOld.flap + 0.12F * CockpitKI_84_IB.this.fm.CT.FlapsControl);
        CockpitKI_84_IB.this.setNew.tlock = (0.7F * CockpitKI_84_IB.this.setOld.tlock + 0.3F * (CockpitKI_84_IB.this.fm.Gears.bTailwheelLocked ? 1.0F : 0.0F));

        if (CockpitKI_84_IB.this.cockpitDimControl) {
          if (CockpitKI_84_IB.this.setNew.dimPosition > 0.0F) CockpitKI_84_IB.this.setNew.dimPosition = (CockpitKI_84_IB.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitKI_84_IB.this.setNew.dimPosition < 1.0F) CockpitKI_84_IB.this.setNew.dimPosition = (CockpitKI_84_IB.this.setOld.dimPosition + 0.05F);

        if ((CockpitKI_84_IB.this.fm.CT.GearControl < 0.5F) && (CockpitKI_84_IB.this.setNew.gear < 1.0F)) {
          CockpitKI_84_IB.this.setNew.gear = (CockpitKI_84_IB.this.setOld.gear + 0.02F);
        }
        if ((CockpitKI_84_IB.this.fm.CT.GearControl > 0.5F) && (CockpitKI_84_IB.this.setNew.gear > 0.0F)) {
          CockpitKI_84_IB.this.setNew.gear = (CockpitKI_84_IB.this.setOld.gear - 0.02F);
        }
        CockpitKI_84_IB.this.setNew.throttle = (0.9F * CockpitKI_84_IB.this.setOld.throttle + 0.1F * CockpitKI_84_IB.this.fm.CT.PowerControl);
        CockpitKI_84_IB.this.setNew.manifold = (0.8F * CockpitKI_84_IB.this.setOld.manifold + 0.2F * CockpitKI_84_IB.this.fm.EI.engines[0].getManifoldPressure());
        CockpitKI_84_IB.this.setNew.pitch = (0.9F * CockpitKI_84_IB.this.setOld.pitch + 0.1F * CockpitKI_84_IB.this.fm.EI.engines[0].getControlProp());
        CockpitKI_84_IB.this.setNew.mix = (0.9F * CockpitKI_84_IB.this.setOld.mix + 0.1F * CockpitKI_84_IB.this.fm.EI.engines[0].getControlMix());
        CockpitKI_84_IB.this.setNew.altimeter = CockpitKI_84_IB.this.fm.getAltitude();

        CockpitKI_84_IB.this.setNew.azimuth.setDeg(CockpitKI_84_IB.this.setOld.azimuth.getDeg(1.0F), CockpitKI_84_IB.this.fm.Or.azimut());

        CockpitKI_84_IB.this.setNew.vspeed = ((199.0F * CockpitKI_84_IB.this.setOld.vspeed + CockpitKI_84_IB.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
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
    float vspeed;
    float dimPosition;
    private final CockpitKI_84_IB this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitKI_84_IB.1 arg2)
    {
      this();
    }
  }
}