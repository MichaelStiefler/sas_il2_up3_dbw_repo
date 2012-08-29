package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitIAR80 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictRadiator;
  private float pictManifold;
  public Vector3f w = new Vector3f();

  private static final float[] oilTScale = { 0.0F, 32.0F, 50.049999F, 78.0F, 123.5F, 180.0F };

  private static final float[] fuelScale = { 0.0F, 10.5F, 30.0F, 71.0F, 114.0F, 148.5F, 175.5F, 202.5F, 232.0F, 258.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitIAR80()
  {
    super("3DO/Cockpit/IAR-80/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges6", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkVisible("Z_GearLRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearRRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearLGreen1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_GearRGreen1", this.fm.CT.getGear() == 1.0F);

    this.mesh.chunkSetAngles("Stick", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    resetYPRmodifier();
    if (this.fm.CT.WeaponControl[0] != 0) {
      xyz[1] = -0.01F;
    }
    this.mesh.chunkSetLocate("Fire1", xyz, ypr);
    this.mesh.chunkSetAngles("Brake", 0.0F, 0.0F, 11.2F * this.fm.CT.getBrake());
    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);

    this.mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, 22.200001F - 80.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));
    this.mesh.chunkSetAngles("Throttle_rod", 0.0F, 0.0F, -22.200001F + 80.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));

    this.mesh.chunkSetAngles("Magneto", 0.0F, 0.0F, 16.666668F * this.fm.EI.engines[0].getControlMagnetos());

    this.mesh.chunkSetAngles("Flaps", 0.0F, 0.0F, -50.0F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.fm.CT.FlapsControl));
    if (this.fm.Gears.isHydroOperable())
      this.mesh.chunkSetAngles("Gears", 0.0F, 0.0F, -50.0F * (this.pictGear = 0.85F * this.pictGear + 0.15F * this.fm.CT.GearControl));
    else {
      this.mesh.chunkSetAngles("H-manual", 0.0F, 0.0F, this.fm.CT.GearControl < 0.1F ? 0.0F : -22.5F);
    }
    this.mesh.chunkSetAngles("Radiator", 0.0F, 0.0F, -50.0F * (this.pictRadiator = 0.85F * this.pictRadiator + 0.15F * this.fm.EI.engines[0].getControlRadiator()));

    float f = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());
    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, f > 100.0F ? cvt(f, 100.0F, 800.0F, 22.5F, 337.5F) : cvt(f, 0.0F, 100.0F, 0.0F, 22.5F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(this.fm.getAltitude(), 0.0F, 16000.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zClock1c", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zTOil1a", 0.0F, (f = this.fm.EI.engines[0].tOilOut) > 100.0F ? 180.0F + 3.725F * (f - 100.0F) : floatindex(cvt(f, 0.0F, 100.0F, 0.0F, 5.0F), oilTScale), 0.0F);

    this.mesh.chunkSetAngles("zGasPrs1a", 0.0F, floatindex(cvt(1.388889F * this.fm.M.fuel, 0.0F, 450.0F, 0.0F, 9.0F), fuelScale), 0.0F);

    this.mesh.chunkSetAngles("zTure1a", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 500.0F, 3000.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -20.0F, 20.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.setNew.turn, -0.23562F, 0.23562F, 18.0F, -18.0F), 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F);

    this.mesh.chunkSetAngles("zPitch1a", 0.0F, 270.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F);

    this.mesh.chunkSetAngles("zPitch1b", 0.0F, 105.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F);

    this.mesh.chunkSetAngles("zPress1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.67F, 0.0F, -180.0F), 0.0F);

    this.mesh.chunkSetAngles("zPress1b", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 20.0F, 0.0F, 124.0F), 0.0F);

    this.mesh.chunkSetAngles("zPressure1a", 0.0F, cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.fm.EI.engines[0].getManifoldPressure(), 0.266644F, 1.866508F, 0.0F, 360.0F), 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Azimuth1", this.setNew.azimuth.getDeg(f) - this.setNew.waypointAzimuth.getDeg(f), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.waypointAzimuth.getDeg(f), 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(f), 0.0F);
      this.mesh.chunkSetAngles("Z_Azimuth1", this.setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
    }
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(30.0F);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x2) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x1) != 0)) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0)) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitIAR80.access$102(CockpitIAR80.this, CockpitIAR80.this.setOld); CockpitIAR80.access$202(CockpitIAR80.this, CockpitIAR80.this.setNew); CockpitIAR80.access$302(CockpitIAR80.this, CockpitIAR80.this.setTmp);

      CockpitIAR80.this.setNew.throttle = ((10.0F * CockpitIAR80.this.setOld.throttle + CockpitIAR80.this.fm.CT.PowerControl) / 11.0F);
      CockpitIAR80.this.setNew.vspeed = ((199.0F * CockpitIAR80.this.setOld.vspeed + CockpitIAR80.this.fm.getVertSpeed()) / 200.0F);

      float f = CockpitIAR80.this.waypointAzimuth();
      if (CockpitIAR80.this.useRealisticNavigationInstruments())
      {
        CockpitIAR80.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitIAR80.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
      }
      else
      {
        CockpitIAR80.this.setNew.waypointAzimuth.setDeg(CockpitIAR80.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitIAR80.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitIAR80.this.setNew.azimuth.setDeg(CockpitIAR80.this.setOld.azimuth.getDeg(1.0F), CockpitIAR80.this.fm.Or.azimut());

      CockpitIAR80.this.w.set(CockpitIAR80.this.fm.getW());
      CockpitIAR80.this.fm.Or.transform(CockpitIAR80.this.w);
      CockpitIAR80.this.setNew.turn = ((12.0F * CockpitIAR80.this.setOld.turn + CockpitIAR80.this.w.z) / 13.0F);

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float turn;
    float vspeed;
    private final CockpitIAR80 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitIAR80.1 arg2)
    {
      this();
    }
  }
}