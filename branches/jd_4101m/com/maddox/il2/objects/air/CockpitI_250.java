package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
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
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;

public class CockpitI_250 extends CockpitPilot
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private float pictGear = 0.0F;
  private float pictVRDK = 0.0F;
  private float pictTLck = 0.0F;

  private float pictMetl = 0.0F;
  private float pictTriE = 0.0F;
  private float pictTriR = 0.0F;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitI_250()
  {
    super("3DO/Cockpit/I-250/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", "gauges_06", "DMG_gauges_01", "DMG_gauges_02", "DMG_gauges_03", "DMG_gauges_04", "DMG_gauges_05", "DMG_gauges_06" };

    setNightMats(false);

    HookNamed localHookNamed = new HookNamed(this.mesh, "LIGHTHOOK_L");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LIGHTHOOK_L", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LIGHTHOOK_R");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LIGHTHOOK_R", this.light2);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.6F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);

    this.mesh.chunkSetAngles("PedalCrossBar", 0.0F, -12.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_L", 0.0F, 12.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 0.0F, -12.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("IgnitionSwitch", 0.0F, cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, -129.0F), 0.0F);
    this.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 35.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl));
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("FlapHandle", 0.0F, -20.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("FlapHandle", 0.0F, 20.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("TQHandle1", 0.0F, 49.900002F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("PropPitchLvr", 0.0F, 49.900002F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 1.0F, 0.1035F, 0.0F);
    this.mesh.chunkSetLocate("MixLvr", xyz, ypr);
    this.mesh.chunkSetAngles("Eng2Switch", 0.0F, 65.0F * (this.pictVRDK = 0.85F * this.pictVRDK + 0.15F * (this.fm.EI.engines[1].getStage() == 6 ? 1.0F : 0.0F)), 0.0F);
    this.mesh.chunkSetAngles("TQHandle2", 0.0F, 100.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("TWLock", 0.0F, -31.0F * (this.pictTLck = 0.85F * this.pictTLck + 0.15F * (this.fm.Gears.bTailwheelLocked ? 1.0F : 0.0F)), 0.0F);
    this.mesh.chunkSetAngles("ElevTrim", 0.0F, 0.0F, 42.200001F * (this.pictTriE = 0.92F * this.pictTriE + 0.08F * this.fm.CT.getTrimElevatorControl()));
    this.mesh.chunkSetAngles("RuddrTrim", 0.0F, 79.900002F * (this.pictTriR = 0.92F * this.pictTriR + 0.08F * this.fm.CT.getTrimRudderControl()), 0.0F);
    this.mesh.chunkSetAngles("FLCSA", 0.0F, -12.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F);
    this.mesh.chunkSetAngles("FLCSB", 0.0F, 12.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F);

    this.pictMetl = (0.96F * this.pictMetl + 0.04F * (0.6F * this.fm.EI.engines[1].getThrustOutput() * this.fm.EI.engines[1].getControlThrottle() * (this.fm.EI.engines[1].getStage() == 6 ? 1.0F : 0.02F)));
    this.mesh.chunkSetAngles("NeedExhstPress2", 0.0F, cvt(this.pictMetl, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress2", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.55F : 0.0F, 0.0F, 1.0F, 0.0F, 232.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedExstT2", 0.0F, cvt(this.fm.EI.engines[1].tWaterOut, 300.0F, 900.0F, 0.0F, 239.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedMfdP2", 0.0F, cvt(this.setNew.manifold2, 0.399966F, 2.133152F, 0.0F, 338.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilT2", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 82.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedWatT", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 82.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 60000.0F, 0.0F, 2160.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_M", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 60000.0F, 0.0F, 21600.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedCompass", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedMin", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedSpeed", 0.0F, cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1200.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedClimb", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedRPMA", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedRPMB", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedMfdP1", 0.0F, cvt(this.setNew.manifold1, 0.399966F, 2.133152F, 0.0F, 335.0F), 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0204F, -0.0204F);
    this.mesh.chunkSetLocate("NeedAHCyl", xyz, ypr);
    this.mesh.chunkSetAngles("NeedAHBar", 0.0F, this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("NeedTurn", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -29.0F, 29.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilT1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress1", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, -180.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilPress1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuel", 0.0F, cvt(this.fm.M.fuel, 0.0F, 432.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkVisible("FlareGearUp_R", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearUp_L", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearDn_R", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearDn_L", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareFuel", this.fm.M.fuel < 81.0F);
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
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

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Gages6_D0", false);
      this.mesh.chunkVisible("Gages6_D1", true);
      this.mesh.chunkVisible("NeedAHCyl", false);
      this.mesh.chunkVisible("NeedAHBar", false);
      this.mesh.chunkVisible("NeedMfdP1", false);
      this.mesh.chunkVisible("NeedOilT1", false);
      this.mesh.chunkVisible("NeedFuelPress1", false);
      this.mesh.chunkVisible("NeedOilPress1", false);
      this.mesh.chunkVisible("Z_Holes6_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("GunSight", false);
      this.mesh.chunkVisible("GSGlassMain", false);
      this.mesh.chunkVisible("DGunSight", true);
    }

    if (((this.fm.AS.astateCockpitState & 0x1) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0)) {
      this.mesh.chunkVisible("OilSplats", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Gages4_D0", false);
      this.mesh.chunkVisible("Gages4_D1", true);
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("NeedAlt_M", false);
      this.mesh.chunkVisible("NeedCompass", false);
      this.mesh.chunkVisible("NeedHour", false);
      this.mesh.chunkVisible("NeedMin", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Gages1_D0", false);
      this.mesh.chunkVisible("Gages1_D1", true);
      this.mesh.chunkVisible("NeedExstT2", false);
      this.mesh.chunkVisible("NeedExhstPress2", false);
      this.mesh.chunkVisible("NeedFuelPress2", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("Gages2_D0", false);
      this.mesh.chunkVisible("Gages2_D1", true);
      this.mesh.chunkVisible("NeedOilT2", false);
      this.mesh.chunkVisible("NeedWatT", false);
      this.mesh.chunkVisible("NeedMfdP2", false);
      this.mesh.chunkVisible("DamageHull2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("Gages5_D0", false);
      this.mesh.chunkVisible("Gages5_D1", true);
      this.mesh.chunkVisible("NeedSpeed", false);
      this.mesh.chunkVisible("NeedClimb", false);
      this.mesh.chunkVisible("NeedRPMA", false);
      this.mesh.chunkVisible("NeedRPMB", false);
      this.mesh.chunkVisible("DamageHull2", true);
      this.mesh.chunkVisible("DamageHull3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("Gages7_D0", false);
      this.mesh.chunkVisible("Gages7_D1", true);
      this.mesh.chunkVisible("NeedFuel", false);
      this.mesh.chunkVisible("NeedAmmeter", false);
      this.mesh.chunkVisible("NeedVmeter", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      setNightMats(true);
      this.light1.light.setEmit(0.005F, 0.5F);
      this.light2.light.setEmit(0.005F, 0.5F);
    } else {
      setNightMats(false);
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
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
      if (CockpitI_250.this.fm != null) {
        CockpitI_250.access$102(CockpitI_250.this, CockpitI_250.this.setOld); CockpitI_250.access$202(CockpitI_250.this, CockpitI_250.this.setNew); CockpitI_250.access$302(CockpitI_250.this, CockpitI_250.this.setTmp);

        CockpitI_250.this.setNew.throttle1 = (0.85F * CockpitI_250.this.setOld.throttle1 + CockpitI_250.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitI_250.this.setNew.throttle2 = (0.85F * CockpitI_250.this.setOld.throttle2 + CockpitI_250.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitI_250.this.setNew.prop = (0.85F * CockpitI_250.this.setOld.prop + CockpitI_250.this.fm.CT.getStepControl() * 0.15F);
        CockpitI_250.this.setNew.mix = (0.85F * CockpitI_250.this.setOld.mix + CockpitI_250.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitI_250.this.setNew.altimeter = CockpitI_250.this.fm.getAltitude();
        float f = CockpitI_250.this.waypointAzimuth();
        CockpitI_250.this.setNew.waypointAzimuth.setDeg(CockpitI_250.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitI_250.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitI_250.this.setNew.azimuth.setDeg(CockpitI_250.this.setOld.azimuth.getDeg(1.0F), CockpitI_250.this.fm.Or.azimut());
        CockpitI_250.this.setNew.vspeed = ((199.0F * CockpitI_250.this.setOld.vspeed + CockpitI_250.this.fm.getVertSpeed()) / 200.0F);
        CockpitI_250.this.setNew.manifold1 = (0.8F * CockpitI_250.this.setOld.manifold1 + 0.2F * CockpitI_250.this.fm.EI.engines[0].getManifoldPressure());
        CockpitI_250.this.setNew.manifold2 = (0.8F * CockpitI_250.this.setOld.manifold2 + 0.2F * CockpitI_250.this.fm.EI.engines[1].getManifoldPressure());
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float throttle2;
    float prop;
    float mix;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float manifold1;
    float manifold2;
    private final CockpitI_250 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitI_250.1 arg2)
    {
      this();
    }
  }
}