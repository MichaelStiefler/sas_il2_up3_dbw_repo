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
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitI_16TYPE18 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 44.0F, 74.5F, 106.0F, 136.3F, 169.5F, 207.5F, 245.0F, 287.5F, 330.0F };

  private static final float[] fuelQuantityScale = { 0.0F, 38.5F, 74.5F, 98.5F, 122.0F, 143.0F, 163.0F, 182.5F, 203.0F, 221.0F, 239.5F, 256.0F, 274.0F, 295.0F, 295.0F, 295.0F };

  private static final float[] engineRPMScale = { 0.0F, 16.0F, 18.0F, 59.5F, 100.5F, 135.5F, 166.5F, 198.5F, 227.0F, 255.0F, 281.5F, 307.0F, 317.0F, 327.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth() { WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitI_16TYPE18()
  {
    super("3DO/Cockpit/I-16/hier.him", "i16");

    this.cockpitNightMats = new String[] { "prib_one", "prib_one_dd", "prib_two", "prib_two_dd", "prib_three", "prib_three_dd", "prib_four", "prib_four_dd", "shkala", "oxigen" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("Fire1", 0.0F, -20.0F * (this.fm.CT.WeaponControl[0] != 0 ? 1 : 0), 0.0F);
    this.mesh.chunkSetAngles("Fire2", 0.0F, -20.0F * (this.fm.CT.WeaponControl[1] != 0 ? 1 : 0), 0.0F);

    this.mesh.chunkSetAngles("Thtl", 30.0F - 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Prop", interp(this.setNew.prop, this.setOld.prop, paramFloat) * -57.0F + 30.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Thtl_Rod", -30.0F + 57.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Prop_Rod", interp(this.setNew.prop, this.setOld.prop, paramFloat) * 57.0F - 30.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Gear_Crank", 15840.0F * interp(this.setNew.gCrankAngle, this.setOld.gCrankAngle, paramFloat) % 360.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -90.0F - interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("zGas1a", 0.0F, floatindex(cvt(this.fm.M.fuel, 0.0F, 190.0F, 0.0F, 14.0F), fuelQuantityScale), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 550.0F, 0.0F, 11.0F), speedometerScale), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x4) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.w.set(this.fm.getW());
      this.fm.Or.transform(this.w);
      this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);

      this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);

      this.mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 125.0F, 0.0F, 275.0F), 0.0F);

      this.mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 275.0F), 0.0F);

      this.mesh.chunkSetAngles("zPressAir1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 10.0F, 0.0F, 275.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.mesh.chunkSetAngles("zRPS1a", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 2400.0F, 0.0F, 13.0F), engineRPMScale), 0.0F);

      this.mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);

      this.mesh.chunkSetAngles("zTCil1a", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -88.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zPressOil1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 270.0F), 0.0F);

    this.mesh.chunkVisible("Z_Red1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Green2", this.fm.CT.getGear() == 1.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_dd", true);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zPressOil1a", false);
      this.mesh.chunkVisible("zVariometer1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_dd", true);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
      this.mesh.chunkVisible("zManifold1a", false);
      this.mesh.chunkVisible("zGas1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
  }

  protected void reflectPlaneMats()
  {
    this.mesh.chunkVisible("ritedoor", false);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  class Interpolater extends InterpolateRef
  {
    boolean sfxPlaying = false;

    Interpolater() {  }

    public boolean tick() { if (CockpitI_16TYPE18.this.fm != null) {
        if (CockpitI_16TYPE18.this.bNeedSetUp) {
          CockpitI_16TYPE18.this.reflectPlaneMats();
          CockpitI_16TYPE18.access$102(CockpitI_16TYPE18.this, false);
        }
        CockpitI_16TYPE18.access$202(CockpitI_16TYPE18.this, CockpitI_16TYPE18.this.setOld); CockpitI_16TYPE18.access$302(CockpitI_16TYPE18.this, CockpitI_16TYPE18.this.setNew); CockpitI_16TYPE18.access$402(CockpitI_16TYPE18.this, CockpitI_16TYPE18.this.setTmp);

        CockpitI_16TYPE18.this.setNew.throttle = ((10.0F * CockpitI_16TYPE18.this.setOld.throttle + CockpitI_16TYPE18.this.fm.CT.PowerControl) / 11.0F);
        CockpitI_16TYPE18.this.setNew.prop = ((10.0F * CockpitI_16TYPE18.this.setOld.prop + CockpitI_16TYPE18.this.fm.EI.engines[0].getControlProp()) / 11.0F);
        CockpitI_16TYPE18.this.setNew.altimeter = CockpitI_16TYPE18.this.fm.getAltitude();
        if (Math.abs(CockpitI_16TYPE18.this.fm.Or.getKren()) < 30.0F) {
          CockpitI_16TYPE18.this.setNew.azimuth = ((35.0F * CockpitI_16TYPE18.this.setOld.azimuth + -CockpitI_16TYPE18.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitI_16TYPE18.this.setOld.azimuth > 270.0F) && (CockpitI_16TYPE18.this.setNew.azimuth < 90.0F)) CockpitI_16TYPE18.this.setOld.azimuth -= 360.0F;
        if ((CockpitI_16TYPE18.this.setOld.azimuth < 90.0F) && (CockpitI_16TYPE18.this.setNew.azimuth > 270.0F)) CockpitI_16TYPE18.this.setOld.azimuth += 360.0F;
        CockpitI_16TYPE18.this.setNew.waypointAzimuth = ((10.0F * CockpitI_16TYPE18.this.setOld.waypointAzimuth + (CockpitI_16TYPE18.this.waypointAzimuth() - CockpitI_16TYPE18.this.setOld.azimuth) + World.Rnd().nextFloat(-10.0F, 10.0F)) / 11.0F);
        CockpitI_16TYPE18.this.setNew.vspeed = ((199.0F * CockpitI_16TYPE18.this.setOld.vspeed + CockpitI_16TYPE18.this.fm.getVertSpeed()) / 200.0F);

        boolean bool = false;

        if (CockpitI_16TYPE18.this.setNew.gCrankAngle < CockpitI_16TYPE18.this.fm.CT.getGear() - 0.005F) {
          if (Math.abs(CockpitI_16TYPE18.this.setNew.gCrankAngle - CockpitI_16TYPE18.this.fm.CT.getGear()) < 0.33F) {
            CockpitI_16TYPE18.this.setNew.gCrankAngle += 0.0025F;
            bool = true;
          } else {
            CockpitI_16TYPE18.this.setNew.gCrankAngle = CockpitI_16TYPE18.this.fm.CT.getGear();
            CockpitI_16TYPE18.this.setOld.gCrankAngle = CockpitI_16TYPE18.this.fm.CT.getGear();
          }
        }
        if (CockpitI_16TYPE18.this.setNew.gCrankAngle > CockpitI_16TYPE18.this.fm.CT.getGear() + 0.005F) {
          if (Math.abs(CockpitI_16TYPE18.this.setNew.gCrankAngle - CockpitI_16TYPE18.this.fm.CT.getGear()) < 0.33F) {
            CockpitI_16TYPE18.this.setNew.gCrankAngle -= 0.0025F;
            bool = true;
          } else {
            CockpitI_16TYPE18.this.setNew.gCrankAngle = CockpitI_16TYPE18.this.fm.CT.getGear();
            CockpitI_16TYPE18.this.setOld.gCrankAngle = CockpitI_16TYPE18.this.fm.CT.getGear();
          }
        }
        if (bool != this.sfxPlaying) {
          if (bool) CockpitI_16TYPE18.this.sfxStart(16); else
            CockpitI_16TYPE18.this.sfxStop(16);
          this.sfxPlaying = bool;
        }
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
    float gCrankAngle;
    private final CockpitI_16TYPE18 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.gCrankAngle = 0.0F;
    }

    Variables(CockpitI_16TYPE18.1 arg2)
    {
      this();
    }
  }
}