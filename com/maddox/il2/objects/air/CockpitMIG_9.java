package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitMIG_9 extends CockpitPilot
{
  private boolean bNeedSetUp = true;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictETP = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;

  private float pictTLck = 0.0F;

  private float pictMet1 = 0.0F;
  private float pictMet2 = 0.0F;
  private float pictETrm = 0.0F;

  private static final float[] rpmScale = { 0.0F, 8.0F, 23.5F, 40.0F, 58.5F, 81.0F, 104.5F, 130.2F, 158.5F, 187.0F, 217.5F, 251.10001F, 281.5F, 289.5F, 295.5F };

  private static final float[] fuelScale = { 0.0F, 18.5F, 49.0F, 80.0F, 87.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitMIG_9()
  {
    super("3DO/Cockpit/MiG-9/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauges_01", "gauges_02", "gauges_03", "gauges_04", "gauges_05", "Dgauges_01", "Dgauges_02", "Dgauges_03", "Dgauges_05" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    ((MIG_9)aircraft()); if (MIG_9.bChangedPit) {
      reflectPlaneToModel();
      ((MIG_9)aircraft()); MIG_9.bChangedPit = false;
    }

    resetYPRmodifier();
    float f = this.fm.CT.getCockpitDoor();
    if (f < 0.1F)
      xyz[1] = cvt(f, 0.01F, 0.08F, 0.0F, 0.1F);
    else {
      xyz[1] = cvt(f, 0.17F, 0.99F, 0.1F, 0.6F);
    }
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);
    this.mesh.chunkSetAngles("CnOpenLvr", cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.08F, 0.0F, -94.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("GearHandle", 0.0F, 0.0F, 50.0F * (this.pictGear = 0.82F * this.pictGear + 0.18F * this.fm.CT.GearControl));
    this.mesh.chunkSetAngles("FlapHandle", 0.0F, 0.0F, 111.0F * (this.pictFlap = 0.88F * this.pictFlap + 0.12F * this.fm.CT.FlapsControl));
    this.mesh.chunkSetAngles("TQHandle1", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat));
    this.mesh.chunkSetAngles("TQHandle2", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat));
    this.mesh.chunkSetAngles("NossleLvr1", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat));
    this.mesh.chunkSetAngles("NossleLvr2", 0.0F, 0.0F, -40.909F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat));
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
    xyz[1] = cvt(this.fm.CT.getRudder(), -1.0F, 1.0F, -0.035F, 0.035F);
    this.mesh.chunkSetLocate("Pedal_L", xyz, ypr);
    xyz[1] = (-xyz[1]);
    this.mesh.chunkSetLocate("Pedal_R", xyz, ypr);
    this.mesh.chunkSetAngles("FLCSA", 0.0F, 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    this.mesh.chunkSetAngles("FLCSB", 0.0F, 10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F);

    this.mesh.chunkSetAngles("NeedRPM1", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4500.0F, 0.0F, 14.0F), rpmScale), 0.0F);
    this.pictMet1 = (0.96F * this.pictMet1 + 0.04F * (0.6F * this.fm.EI.engines[0].getThrustOutput() * this.fm.EI.engines[0].getControlThrottle() * (this.fm.EI.engines[0].getStage() == 6 ? 1.0F : 0.02F)));
    this.mesh.chunkSetAngles("NeedExhstPress1", 0.0F, cvt(this.pictMet1, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress1", cvt(this.fm.M.fuel > 1.0F ? 0.55F : 0.0F, 0.0F, 1.0F, 0.0F, 284.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedExstT1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 1200.0F, 0.0F, 112.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilP1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 6.46F, 0.0F, 270.0F), 0.0F);

    this.mesh.chunkSetAngles("NeedRPM2", 0.0F, floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4500.0F, 0.0F, 14.0F), rpmScale), 0.0F);
    this.pictMet2 = (0.96F * this.pictMet2 + 0.04F * (0.6F * this.fm.EI.engines[1].getThrustOutput() * this.fm.EI.engines[1].getControlThrottle() * (this.fm.EI.engines[1].getStage() == 6 ? 1.0F : 0.02F)));
    this.mesh.chunkSetAngles("NeedExhstPress2", 0.0F, cvt(this.pictMet2, 0.0F, 1.0F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedFuelPress2", cvt(this.fm.M.fuel > 1.0F ? 0.55F : 0.0F, 0.0F, 1.0F, 0.0F, 284.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedExstT2", 0.0F, cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 1200.0F, 0.0F, 112.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedOilP2", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 6.46F, 0.0F, 270.0F), 0.0F);

    this.mesh.chunkSetAngles("NeedFuel1", 0.0F, floatindex(cvt(this.fm.M.fuel, 0.0F, 864.0F, 0.0F, 4.0F), fuelScale), 0.0F);
    this.mesh.chunkSetAngles("NeedFuel2", 0.0F, floatindex(cvt(this.fm.M.fuel, 864.0F, 1728.0F, 0.0F, 4.0F), fuelScale), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 60000.0F, 0.0F, 2160.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedAlt_M", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 60000.0F, 0.0F, 21600.0F), 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("NeedCompassB", 0.0F, this.setNew.azimuth.getDeg(paramFloat) - this.setNew.compassRim.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("NeedCompassA", 0.0F, -this.setNew.compassRim.getDeg(paramFloat), 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("NeedCompassA", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("NeedCompassB", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("NeedSpeed", 0.0F, cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1200.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedClimb", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("NeedAHCyl", 0.0F, -this.fm.Or.getKren() + 180.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAHBar", 0.0F, 0.0F, -this.fm.Or.getTangage());
    this.mesh.chunkSetAngles("NeedTurn", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedDF", 0.0F, cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.2F), -90.0F, 90.0F, -16.5F, 16.5F), 0.0F);
    this.mesh.chunkSetAngles("NeedHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedMin", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("NeedStarter1", cvt(this.setNew.starter1, 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedStarter2", cvt(this.setNew.starter2, 0.0F, 1.0F, 0.0F, -120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedEmrgAirP", -63.5F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("NeedAirSysP", this.fm.Gears.isHydroOperable() ? -133.5F : 0.0F, 0.0F, 0.0F);

    this.mesh.chunkVisible("FlareGearUp_R", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearUp_L", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearUp_C", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("FlareGearDn_R", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("FlareGearDn_L", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("FlareGearDn_C", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("FlareFuel", this.fm.M.fuel < 296.10001F);
  }

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(10.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }

    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("DamageGlass2", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
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
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Gages3_D0", false);
      this.mesh.chunkVisible("Gages3_D1", true);
      this.mesh.chunkVisible("NeedHour", false);
      this.mesh.chunkVisible("NeedMin", false);
      this.mesh.chunkVisible("NeedRPM1", false);
      this.mesh.chunkVisible("NeedExhstPress1", false);
      this.mesh.chunkVisible("DamageHull3", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("Gages4_D0", false);
      this.mesh.chunkVisible("Gages4_D1", true);
      this.mesh.chunkVisible("NeedRPM2", false);
      this.mesh.chunkVisible("NeedOilP1", false);
      this.mesh.chunkVisible("NeedFuel1", false);
      this.mesh.chunkVisible("NeedExstT1", false);
      this.mesh.chunkVisible("DamageHull2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("Gages5_D0", false);
      this.mesh.chunkVisible("Gages5_D1", true);
      this.mesh.chunkVisible("NeedOilP2", false);
      this.mesh.chunkVisible("NeedExhstPress2", false);
      this.mesh.chunkVisible("NeedExstT2", false);
      this.mesh.chunkVisible("NeedFuel2", false);
      this.mesh.chunkVisible("", false);
      this.mesh.chunkVisible("", false);
      this.mesh.chunkVisible("", false);
      this.mesh.chunkVisible("", false);
    }
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.mesh.materialReplace("Matt1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLIn_D0", localHierMesh.isChunkVisible("WingLIn_D0"));
    this.mesh.chunkVisible("WingLIn_D1", localHierMesh.isChunkVisible("WingLIn_D1"));
    this.mesh.chunkVisible("WingLIn_D2", localHierMesh.isChunkVisible("WingLIn_D2"));
    this.mesh.chunkVisible("WingLIn_D3", localHierMesh.isChunkVisible("WingLIn_D3"));
    this.mesh.chunkVisible("WingLIn_CAP", localHierMesh.isChunkVisible("WingLIn_CAP"));
    this.mesh.chunkVisible("WingRIn_D0", localHierMesh.isChunkVisible("WingRIn_D0"));
    this.mesh.chunkVisible("WingRIn_D1", localHierMesh.isChunkVisible("WingRIn_D1"));
    this.mesh.chunkVisible("WingRIn_D2", localHierMesh.isChunkVisible("WingRIn_D2"));
    this.mesh.chunkVisible("WingRIn_D3", localHierMesh.isChunkVisible("WingRIn_D3"));
    this.mesh.chunkVisible("WingRIn_CAP", localHierMesh.isChunkVisible("WingRIn_CAP"));
    this.mesh.chunkVisible("Wire_D0", localHierMesh.isChunkVisible("Wire_D0"));
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      setNightMats(true);
    }
    else
    {
      setNightMats(false);
    }
  }

  private void retoggleLight()
  {
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
      if (CockpitMIG_9.this.fm != null) {
        CockpitMIG_9.access$102(CockpitMIG_9.this, CockpitMIG_9.this.setOld); CockpitMIG_9.access$202(CockpitMIG_9.this, CockpitMIG_9.this.setNew); CockpitMIG_9.access$302(CockpitMIG_9.this, CockpitMIG_9.this.setTmp);

        CockpitMIG_9.this.setNew.throttle1 = (0.85F * CockpitMIG_9.this.setOld.throttle1 + CockpitMIG_9.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitMIG_9.this.setNew.throttle2 = (0.85F * CockpitMIG_9.this.setOld.throttle2 + CockpitMIG_9.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitMIG_9.this.setNew.starter1 = (0.94F * CockpitMIG_9.this.setOld.starter1 + 0.06F * ((CockpitMIG_9.this.fm.EI.engines[0].getStage() > 0) && (CockpitMIG_9.this.fm.EI.engines[0].getStage() < 6) ? 1.0F : 0.0F));
        CockpitMIG_9.this.setNew.starter2 = (0.94F * CockpitMIG_9.this.setOld.starter2 + 0.06F * ((CockpitMIG_9.this.fm.EI.engines[1].getStage() > 0) && (CockpitMIG_9.this.fm.EI.engines[1].getStage() < 6) ? 1.0F : 0.0F));

        CockpitMIG_9.this.setNew.altimeter = CockpitMIG_9.this.fm.getAltitude();

        if (CockpitMIG_9.this.useRealisticNavigationInstruments())
        {
          CockpitMIG_9.this.setNew.waypointAzimuth.setDeg(CockpitMIG_9.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitMIG_9.this.getBeaconDirection());
          float f = CockpitMIG_9.this.waypointAzimuth();
          CockpitMIG_9.this.setNew.compassRim.setDeg(f - 90.0F);
          CockpitMIG_9.this.setOld.compassRim.setDeg(f - 90.0F);
        }
        else
        {
          CockpitMIG_9.this.setNew.waypointAzimuth.setDeg(CockpitMIG_9.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitMIG_9.this.waypointAzimuth() - CockpitMIG_9.this.setOld.azimuth.getDeg(1.0F));
          CockpitMIG_9.this.setNew.compassRim.setDeg(0.0F);
          CockpitMIG_9.this.setOld.compassRim.setDeg(0.0F);
        }

        CockpitMIG_9.this.setNew.azimuth.setDeg(CockpitMIG_9.this.setOld.azimuth.getDeg(1.0F), CockpitMIG_9.this.fm.Or.azimut());
        CockpitMIG_9.this.setNew.vspeed = ((199.0F * CockpitMIG_9.this.setOld.vspeed + CockpitMIG_9.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float throttle2;
    float starter1;
    float starter2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork compassRim;
    float vspeed;
    private final CockpitMIG_9 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.compassRim = new AnglesFork();
    }

    Variables(CockpitMIG_9.1 arg2)
    {
      this();
    }
  }
}