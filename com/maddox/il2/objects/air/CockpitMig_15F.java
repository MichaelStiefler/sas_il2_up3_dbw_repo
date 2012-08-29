package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.AudioStream;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.sound.SoundFX;

public class CockpitMig_15F extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private float pictAiler;
  private float pictElev;
  private float pictETP;
  private float pictFlap;
  private float pictGear;
  private float pictTLck;
  private float pictMet1;
  private float pictMet2;
  private float pictETrm;
  private static final float[] rpmScale = { 0.0F, 8.0F, 23.5F, 40.0F, 58.5F, 81.0F, 104.5F, 130.2F, 158.5F, 187.0F, 217.5F, 251.10001F, 281.5F, 289.5F, 295.5F };

  private static final float[] fuelScale = { 0.0F, 18.5F, 49.0F, 80.0F, 87.0F };
  private Point3d tmpP;
  private Vector3d tmpV;
  protected SoundFX tFX;

  public CockpitMig_15F()
  {
    super("3DO/Cockpit/MiG-9/hier1.him", "bf109");

    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictETP = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictTLck = 0.0F;
    this.pictMet1 = 0.0F;
    this.pictMet2 = 0.0F;
    this.pictETrm = 0.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.cockpitNightMats = new String[] { "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", "Dgauges_01", "Dgauges_02", "Dgauges_03", "Dgauges_05" }; this.tFX = aircraft().newSound("extra", false);
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float f) {
    if (Mig_15F.bChangedPit)
    {
      Mig_15F.bChangedPit = false;
    }

    resetYPRmodifier();
    float f1 = this.fm.CT.getCockpitDoor();
    if (f1 < 0.1F)
      Cockpit.xyz[1] = cvt(f1, 0.01F, 0.08F, 0.0F, 0.1F);
    else {
      Cockpit.xyz[1] = cvt(f1, 0.17F, 0.99F, 0.1F, 0.6F);
    }

    this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("CnOpenLvr", cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.08F, 0.0F, -94.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 50.0F * (this.pictGear = 0.82F * this.pictGear + 0.18F * this.fm.CT.GearControl));
    this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 111.0F * (this.pictFlap = 0.88F * this.pictFlap + 0.12F * this.fm.CT.FlapsControl));
    this.mesh.chunkSetAngles("TQHandle1", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle, this.setOld.throttle, f));
    this.mesh.chunkSetAngles("TQHandle2", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle, this.setOld.throttle, f));
    this.mesh.chunkSetAngles("NossleLvr1", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle, this.setOld.throttle, f));
    this.mesh.chunkSetAngles("NossleLvr2", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle, this.setOld.throttle, f));
    this.mesh.chunkSetAngles("Lvr1", 0.0F, 0.0F, -25.0F * (this.pictTLck = 0.85F * this.pictTLck + 0.15F * (this.fm.Gears.bTailwheelLocked ? 1.0F : 0.0F)));
    if (this.fm.CT.getTrimElevatorControl() != this.pictETP) {
      if (this.fm.CT.getTrimElevatorControl() - this.pictETP > 0.0F) {
        this.mesh.chunkSetAngles("ElevTrim", 0.0F, -30.0F, 0.0F);
        this.pictETrm = (float)Time.current();
      } else {
        this.mesh.chunkSetAngles("ElevTrim", 0.0F, 30.0F, 0.0F);
        this.pictETrm = (float)Time.current();
      }
      this.pictETP = this.fm.CT.getTrimElevatorControl();
    } else if ((float)Time.current() > this.pictETrm + 500.0F) {
      this.mesh.chunkSetAngles("ElevTrim", 0.0F, 0.0F, 0.0F);
      this.pictETrm = (float)(Time.current() + 500000L);
    }
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.CT.getRudder(), -1.0F, 1.0F, -0.035F, 0.035F);
    this.mesh.chunkSetLocate("Pedal_L", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[1] = (-Cockpit.xyz[1]);
    this.mesh.chunkSetLocate("Pedal_R", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("FLCSA", 0.0F, 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    this.mesh.chunkSetAngles("FLCSB", 0.0F, 10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F);
    this.mesh.chunkSetAngles("NeedRPM1", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4500.0F, 0.0F, 14.0F), rpmScale), 0.0F);
    this.pictMet1 = (0.96F * this.pictMet1 + 0.024F * this.fm.EI.engines[0].getThrustOutput() * this.fm.EI.engines[0].getControlThrottle() * (this.fm.EI.engines[0].getStage() != 6 ? 0.02F : 1.0F));
    this.mesh.chunkSetAngles("NeedExhstPress1", 0.0F, cvt(this.pictMet1, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress1", cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.55F, 0.0F, 1.0F, 0.0F, 284.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedExstT1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 1200.0F, 0.0F, 112.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilP1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 6.46F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuel1", 0.0F, floatindex(cvt(this.fm.M.fuel, 0.0F, 864.0F, 0.0F, 4.0F), fuelScale), 0.0F);
    this.mesh.chunkSetAngles("NeedFuel2", 0.0F, floatindex(cvt(this.fm.M.fuel, 864.0F, 1728.0F, 0.0F, 4.0F), fuelScale), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 60000.0F, 0.0F, 2160.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_M", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 60000.0F, 0.0F, 21600.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedCompassA", 0.0F, -this.setNew.azimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("NeedCompassB", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedSpeed", 0.0F, cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1200.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedClimb", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -this.fm.Or.getKren() + 180.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, -this.fm.Or.getTangage());
    this.mesh.chunkSetAngles("NeedTurn", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedDF", 0.0F, cvt(this.setNew.waypointAzimuth.getDeg(f), -90.0F, 90.0F, -16.5F, 16.5F), 0.0F);
    this.mesh.chunkSetAngles("NeedHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedMin", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedStarter1", cvt(this.setNew.starter, 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedStarter2", cvt(this.setNew.starter, 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedEmrgAirP", -63.5F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAirSysP", this.fm.Gears.isHydroOperable() ? -133.5F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkVisible("FlareGearUp_R", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearUp_L", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearUp_C", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("FlareGearDn_R", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearDn_L", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearDn_C", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("FlareFuel", this.fm.M.fuel < 296.10001F); setVol();
  }

  private void setVol() {
    float f = ((Mig_15F)aircraft()).gmnr();

    this.tFX.setVolume(Aircraft.cvt(f, 0.92F, 1.2F, 0.0F, 5.0F));
  }

  protected float waypointAzimuth()
  {
    WayPoint waypoint = this.fm.AP.way.curr();
    if (waypoint == null)
      return 0.0F;
    waypoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
    {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("DamageGlass2", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) != 0) || ((this.fm.AS.astateCockpitState & 0x40) != 0))
    {
      this.mesh.chunkVisible("Gages1_D0", false);
      this.mesh.chunkVisible("Gages1_D1", true);
      this.mesh.chunkVisible("NeedSpeed", false);
      this.mesh.chunkVisible("NeedClimb", false);
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("NeedAlt_M", false);
      this.mesh.chunkVisible("NeedDF", false);
      this.mesh.chunkVisible("NeedCompassA", false);
      this.mesh.chunkVisible("NeedCompassB", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("Gages3_D0", false);
      this.mesh.chunkVisible("Gages3_D1", true);
      this.mesh.chunkVisible("NeedHour", false);
      this.mesh.chunkVisible("NeedMin", false);
      this.mesh.chunkVisible("NeedRPM1", false);
      this.mesh.chunkVisible("NeedExhstPress1", false);
      this.mesh.chunkVisible("DamageHull3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("Gages4_D0", false);
      this.mesh.chunkVisible("Gages4_D1", true);
      this.mesh.chunkVisible("NeedRPM2", false);
      this.mesh.chunkVisible("NeedOilP1", false);
      this.mesh.chunkVisible("NeedFuel1", false);
      this.mesh.chunkVisible("NeedExstT1", false);
      this.mesh.chunkVisible("DamageHull2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("Gages5_D0", false);
      this.mesh.chunkVisible("Gages5_D1", true);
      this.mesh.chunkVisible("NeedOilP2", false);
      this.mesh.chunkVisible("NeedExhstPress2", false);
      this.mesh.chunkVisible("NeedExstT2", false);
      this.mesh.chunkVisible("NeedFuel2", false);
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

  public void doToggleDim()
  {
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitMig_15F.this.fm != null) {
        CockpitMig_15F.access$102(CockpitMig_15F.this, CockpitMig_15F.this.setOld);
        CockpitMig_15F.access$202(CockpitMig_15F.this, CockpitMig_15F.this.setNew);
        CockpitMig_15F.access$302(CockpitMig_15F.this, CockpitMig_15F.this.setTmp);
        CockpitMig_15F.this.setNew.throttle = (0.9F * CockpitMig_15F.this.setOld.throttle + CockpitMig_15F.this.fm.CT.PowerControl * 0.1F);
        CockpitMig_15F.this.setNew.starter = (0.94F * CockpitMig_15F.this.setOld.starter + 0.06F * ((CockpitMig_15F.this.fm.EI.engines[0].getStage() > 0) && (CockpitMig_15F.this.fm.EI.engines[0].getStage() < 6) ? 1.0F : 0.0F));
        CockpitMig_15F.this.setNew.altimeter = CockpitMig_15F.this.fm.getAltitude();
        CockpitMig_15F.this.setNew.azimuth.setDeg(CockpitMig_15F.this.setOld.azimuth.getDeg(1.0F), CockpitMig_15F.this.fm.Or.azimut());
        float f = CockpitMig_15F.this.waypointAzimuth();
        CockpitMig_15F.this.setNew.waypointAzimuth.setDeg(CockpitMig_15F.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitMig_15F.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitMig_15F.this.setNew.vspeed = ((199.0F * CockpitMig_15F.this.setOld.vspeed + CockpitMig_15F.this.fm.getVertSpeed()) / 200.0F);
        if (CockpitMig_15F.this.tFX != null)
          if (((Mig_15F)CockpitMig_15F.this.aircraft()).ist()) {
            if (!CockpitMig_15F.this.tFX.isPlaying())
              CockpitMig_15F.this.tFX.play();
          }
          else CockpitMig_15F.this.tFX.stop();
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float vspeed;
    float starter;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    private final CockpitMig_15F this$0;

    private Variables()
    {
      this.this$0 = ???;

      this.throttle = 0.0F;
      this.starter = 0.0F;
      this.altimeter = 0.0F;
      this.vspeed = 0.0F;
      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitMig_15F.1 x1)
    {
      this();
    }
  }
}