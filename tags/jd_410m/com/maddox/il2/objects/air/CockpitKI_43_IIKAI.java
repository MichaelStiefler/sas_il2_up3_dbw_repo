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

public class CockpitKI_43_IIKAI extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 7.0F, 22.0F, 61.5F, 116.0F, 175.5F, 241.0F, 298.5F, 356.70001F, 417.5F, 480.5F, 537.0F, 585.0F, 628.5F, 658.0F };

  private static final float[] oilScale = { 0.0F, -27.5F, 12.0F, 59.5F, 127.0F, 212.5F, 311.5F };

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

  public CockpitKI_43_IIKAI()
  {
    super("3DO/Cockpit/Ki-43-II(Kai)/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "D_gauge", "D_gauge1", "D_gauge2", "D_gauge4", "D_gauge5", "gauge", "gauge1", "gauge2", "gauge3", "gauge4", "gauge5", "gauge6", "radio" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      this.bNeedSetUp = false;
    }

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.59F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Radiat", 0.0F, -450.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 70.0F * this.setNew.throttle, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zPitch1", 77.0F * this.setNew.prop, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMix1", 64.099998F * this.setNew.mix, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PedalBase", 30.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 30.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 30.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 8.0F, 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[0] != 0) {
      xyz[2] = 0.0036F;
    }
    this.mesh.chunkSetLocate("Z_Trigger1", xyz, ypr);
    xyz[2] = 0.0F;
    if (this.fm.CT.saveWeaponControl[1] != 0) {
      xyz[2] = 0.00675F;
    }
    this.mesh.chunkSetLocate("Z_Trigger2", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Flaps", 90.0F * this.fm.CT.FlapsControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ReViTinter", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 90.0F), 0.0F);
    this.mesh.chunkSetAngles("Sight_rev", 0.0F, cvt(interp(this.setNew.emdimPos, this.setOld.emdimPos, paramFloat), 0.0F, 1.0F, 0.0F, 170.0F), 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 1440.0F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 14400.0F), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 700.0F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(8.0D), -8.0F, 8.0F, -14.0F, 14.0F), 0.0F, 0.0F);
    float f = this.setNew.vspeed;
    if (Math.abs(f) < 5.0F)
      this.mesh.chunkSetAngles("Z_Climb1", cvt(f, -5.0F, 5.0F, -90.0F, 90.0F), 0.0F, 0.0F);
    else if (f > 0.0F)
      this.mesh.chunkSetAngles("Z_Climb1", cvt(f, 5.0F, 30.0F, 90.0F, 180.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Climb1", cvt(f, -30.0F, -5.0F, -180.0F, -90.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Compass2", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3200.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel3", 0.0F, cvt(this.fm.M.fuel, 0.0F, 150.0F, 0.0F, 241.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, 255.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel4", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 1.0F, 0.0F, -330.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.setNew.manifold, 0.466712F, 1.533288F, -162.5F, 162.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 130.0F, 0.0F, 68.25F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 5.5F, 0.0F, 295.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 130.0F, 0.0F, 76.800003F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 200.0F, 0.0F, 64.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hydpres1", 0.0F, this.fm.Gears.isHydroOperable() ? -176.0F : 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oxypres1", 220.0F, 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_Red1", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("Z_Red2", true);
    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getGear() > 0.99F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_Pres1", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_Temp2", false);
      this.mesh.chunkVisible("Z_Gunpres1", false);
      this.mesh.chunkVisible("Z_Hydpres1", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage4", true);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot2"));
    this.mesh.materialReplace("Pilot2", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("Pilot2_D0", localHierMesh.isChunkVisible("Pilot2_D0"));
    this.mesh.chunkVisible("Pilot2_D1", localHierMesh.isChunkVisible("Pilot2_D1"));
    this.mesh.chunkVisible("Turret1B_D0", localHierMesh.isChunkVisible("Turret1B_D0"));
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitKI_43_IIKAI.this.fm != null) {
        CockpitKI_43_IIKAI.access$102(CockpitKI_43_IIKAI.this, CockpitKI_43_IIKAI.this.setOld); CockpitKI_43_IIKAI.access$202(CockpitKI_43_IIKAI.this, CockpitKI_43_IIKAI.this.setNew); CockpitKI_43_IIKAI.access$302(CockpitKI_43_IIKAI.this, CockpitKI_43_IIKAI.this.setTmp);

        if (CockpitKI_43_IIKAI.this.cockpitDimControl) {
          if (CockpitKI_43_IIKAI.this.setNew.dimPos < 1.0F) CockpitKI_43_IIKAI.this.setNew.dimPos = (CockpitKI_43_IIKAI.this.setOld.dimPos + 0.03F);
        }
        else if (CockpitKI_43_IIKAI.this.setNew.dimPos > 0.0F) CockpitKI_43_IIKAI.this.setNew.dimPos = (CockpitKI_43_IIKAI.this.setOld.dimPos - 0.03F);

        if ((CockpitKI_43_IIKAI.this.fm.AS.astateCockpitState & 0x2) != 0) {
          CockpitKI_43_IIKAI.this.setNew.emdimPos = (CockpitKI_43_IIKAI.this.setOld.emdimPos + 0.03F);
          if (CockpitKI_43_IIKAI.this.setNew.emdimPos > 1.0F) {
            CockpitKI_43_IIKAI.this.setNew.emdimPos = 1.0F;
          }

        }

        CockpitKI_43_IIKAI.this.setNew.manifold = (0.8F * CockpitKI_43_IIKAI.this.setOld.manifold + 0.2F * CockpitKI_43_IIKAI.this.fm.EI.engines[0].getManifoldPressure());
        CockpitKI_43_IIKAI.this.setNew.throttle = (0.8F * CockpitKI_43_IIKAI.this.setOld.throttle + 0.2F * CockpitKI_43_IIKAI.this.fm.CT.PowerControl);
        CockpitKI_43_IIKAI.this.setNew.prop = (0.8F * CockpitKI_43_IIKAI.this.setOld.prop + 0.2F * CockpitKI_43_IIKAI.this.fm.EI.engines[0].getControlProp());
        CockpitKI_43_IIKAI.this.setNew.mix = (0.8F * CockpitKI_43_IIKAI.this.setOld.mix + 0.2F * CockpitKI_43_IIKAI.this.fm.EI.engines[0].getControlMix());

        CockpitKI_43_IIKAI.this.setNew.man = (0.92F * CockpitKI_43_IIKAI.this.setOld.man + 0.08F * CockpitKI_43_IIKAI.this.fm.EI.engines[0].getManifoldPressure());
        CockpitKI_43_IIKAI.this.setNew.altimeter = CockpitKI_43_IIKAI.this.fm.getAltitude();

        float f = CockpitKI_43_IIKAI.this.waypointAzimuth();
        CockpitKI_43_IIKAI.this.setNew.azimuth.setDeg(CockpitKI_43_IIKAI.this.setOld.azimuth.getDeg(1.0F), CockpitKI_43_IIKAI.this.fm.Or.azimut());
        CockpitKI_43_IIKAI.this.setNew.waypointDeviation.setDeg(CockpitKI_43_IIKAI.this.setOld.waypointDeviation.getDeg(0.1F), f - CockpitKI_43_IIKAI.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-5.0F, 5.0F));
        CockpitKI_43_IIKAI.this.setNew.waypointAzimuth.setDeg(CockpitKI_43_IIKAI.this.setOld.waypointAzimuth.getDeg(1.0F), f);

        CockpitKI_43_IIKAI.this.setNew.vspeed = (0.5F * CockpitKI_43_IIKAI.this.setOld.vspeed + 0.5F * CockpitKI_43_IIKAI.this.fm.getVertSpeed());
      }

      return true;
    }
  }

  private class Variables
  {
    float dimPos;
    float emdimPos;
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float man;
    float vspeed;
    float manifold;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork waypointDeviation;
    private final CockpitKI_43_IIKAI this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.dimPos = 0.0F;
      this.emdimPos = 0.0F;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDeviation = new AnglesFork();
    }

    Variables(CockpitKI_43_IIKAI.1 arg2)
    {
      this();
    }
  }
}