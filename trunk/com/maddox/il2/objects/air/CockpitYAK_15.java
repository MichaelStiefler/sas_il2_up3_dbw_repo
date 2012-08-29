package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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

public class CockpitYAK_15 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();

  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  public CockpitYAK_15()
  {
    super("3DO/Cockpit/Yak-15/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Gauges_01", "Gauges_02", "Gauges_03", "Gauges_04", "Gauges_02_D", "Gauges_03_D", "Gauges_04_D", "Gauges_05", "Gauges_05_D" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm.Gears.isHydroOperable()) {
      this.mesh.chunkSetAngles("shassy_R", 0.0F, 50.0F, 0.0F);
      this.mesh.chunkSetAngles("zAirpressa", 0.0F, 150.0F, 0.0F);
    } else {
      this.mesh.chunkSetAngles("zAirpressa", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("shassy_R", 0.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("zRPM1a", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.M.fuel, 72.0F, 360.0F, 0.0F, 0.0312F);
    this.mesh.chunkSetLocate("zFuelL", xyz, ypr);
    xyz[1] = cvt(this.fm.M.fuel, 72.0F, 360.0F, 0.0F, 0.0312F);
    this.mesh.chunkSetLocate("zFuelR", xyz, ypr);
    xyz[1] = cvt(this.fm.M.fuel, 0.0F, 72.0F, 0.0F, 0.0312F);
    this.mesh.chunkSetLocate("zFuelC", xyz, ypr);

    this.mesh.chunkSetAngles("zGas1T", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 1000.0F, 0.0F, 73.0F), 0.0F);
    this.mesh.chunkSetAngles("zGasPres1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 64.0F), 0.0F);
    this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 272.5F), 0.0F);
    this.mesh.chunkSetAngles("zFuelPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 80.0F * this.fm.EI.engines[0].getPowerOutput() * this.fm.EI.engines[0].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 272.5F), 0.0F);

    this.mesh.chunkSetAngles("richaga", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F, 0.0F);
    this.mesh.chunkSetAngles("richagb", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("norm_gaz", 0.0F, interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 29.5F, 0.0F);

    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("shassy", 0.0F, 24.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("shassy", 0.0F, -24.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("shassy", 0.0F, 0.0F, 0.0F);
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        this.mesh.chunkSetAngles("shitki", 0.0F, -20.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("shitki", 0.0F, 20.0F, 0.0F);
    }
    else this.mesh.chunkSetAngles("shitki", 0.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -this.setNew.azimuth.getDeg(paramFloat * 0.1F) - 90.0F, 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x20) == 0) && ((this.fm.AS.astateCockpitState & 0x40) == 0))
    {
      this.w.set(this.fm.getW());

      this.fm.Or.transform(this.w);
      this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 24.0F, -24.0F), 0.0F);

      this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);

      this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

      this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkVisible("Z_Red1", false);
    this.mesh.chunkVisible("Z_Red2", false);
    this.mesh.chunkVisible("Z_Green1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Green2", this.fm.CT.getGear() == 1.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("Z_Holes3_D1", true);
      this.mesh.chunkVisible("panel_d1", true);
      this.mesh.chunkVisible("pribors4", false);
      this.mesh.chunkVisible("pribors4_d1", true);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zFuelPrs1a", false);
      this.mesh.chunkVisible("zFuelL", false);
      this.mesh.chunkVisible("zFuelR", false);
      this.mesh.chunkVisible("zFuelC", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0) || ((this.fm.AS.astateCockpitState & 0x40) != 0))
    {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
      this.mesh.chunkVisible("pribors3", false);
      this.mesh.chunkVisible("pribors3_d1", true);
      this.mesh.chunkVisible("zClock1a", false);
      this.mesh.chunkVisible("zClock1b", false);
      this.mesh.chunkVisible("zVariometer1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
    {
      this.mesh.chunkVisible("pribors5", false);
      this.mesh.chunkVisible("pribors5_d1", true);
      this.mesh.chunkVisible("zGas1T", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
    }
    retoggleLight();
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
      if (CockpitYAK_15.this.fm != null) {
        CockpitYAK_15.access$102(CockpitYAK_15.this, CockpitYAK_15.this.setOld); CockpitYAK_15.access$202(CockpitYAK_15.this, CockpitYAK_15.this.setNew); CockpitYAK_15.access$302(CockpitYAK_15.this, CockpitYAK_15.this.setTmp);

        CockpitYAK_15.this.setNew.throttle = ((10.0F * CockpitYAK_15.this.setOld.throttle + CockpitYAK_15.this.fm.CT.PowerControl) / 11.0F);
        CockpitYAK_15.this.setNew.prop = ((10.0F * CockpitYAK_15.this.setOld.prop + CockpitYAK_15.this.fm.EI.engines[0].getControlProp()) / 11.0F);
        CockpitYAK_15.this.setNew.altimeter = CockpitYAK_15.this.fm.getAltitude();
        if (Math.abs(CockpitYAK_15.this.fm.Or.getKren()) < 30.0F) {
          CockpitYAK_15.this.setNew.azimuth.setDeg(CockpitYAK_15.this.setOld.azimuth.getDeg(0.1F), CockpitYAK_15.this.fm.Or.azimut());
        }

        CockpitYAK_15.this.setNew.vspeed = ((199.0F * CockpitYAK_15.this.setOld.vspeed + CockpitYAK_15.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    AnglesFork azimuth;
    float vspeed;
    private final CockpitYAK_15 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitYAK_15.1 arg2)
    {
      this();
    }
  }
}