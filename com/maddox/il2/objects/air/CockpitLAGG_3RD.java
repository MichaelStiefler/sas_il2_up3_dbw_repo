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
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitLAGG_3RD extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitLAGG_3RD()
  {
    super("3do/cockpit/LaGG-3RD/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "prib_one", "prib_two", "prib_three", "prib_four", "prib_five", "shkala", "prib_one_dd", "prib_two_dd", "prib_three_dd", "Gauges_03", "Gauges_04", "Gauges_04_D", "Gauges_05" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.materialReplace("prib_three", "EmptyGauge");
      this.mesh.materialReplace("prib_three_dd", "EmptyGaugeD");
      this.mesh.materialReplace("prib_three_night", "EmptyGauge_night");
      this.mesh.materialReplace("prib_three_dd_night", "EmptyGaugeD_night");
      this.mesh.chunkVisible("zRPK10", false);
      setNightMats(true);
      setNightMats(false);
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("zGas1T", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 1000.0F, 0.0F, 73.0F), 0.0F);
    this.mesh.chunkSetAngles("zGasPres1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.6F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 1.0F, 0.0F, 64.0F), 0.0F);
    this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 272.5F), 0.0F);
    this.mesh.chunkSetAngles("zFuelPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 80.0F * this.fm.EI.engines[0].getPowerOutput() * this.fm.EI.engines[0].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 272.5F), 0.0F);
    this.mesh.chunkSetAngles("zRPM1a", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F);

    this.mesh.chunkSetAngles("richag", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("Tross_L", 0.0F, this.fm.CT.getRudder() * 15.65F, 0.0F);
    this.mesh.chunkSetAngles("Tross_R", 0.0F, this.fm.CT.getRudder() * 15.65F, 0.0F);
    this.mesh.chunkSetAngles("n_corr", 0.0F, 80.0F - interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 95.0F, 0.0F);

    this.mesh.chunkSetAngles("r_one", 0.0F, -20.0F * (this.fm.CT.WeaponControl[0] != 0 ? 1 : 0), 0.0F);
    this.mesh.chunkSetAngles("r_two", 0.0F, -20.0F * (this.fm.CT.WeaponControl[1] != 0 ? 1 : 0), 0.0F);
    this.mesh.chunkSetAngles("r_turn", 0.0F, 20.0F * this.fm.CT.BrakeControl, 0.0F);

    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("zGas1a", 0.0F, cvt(this.fm.M.fuel / 0.725F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F);

    if (((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.w.set(this.fm.getW());

      this.fm.Or.transform(this.w);
      this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);

      this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 24.0F, -24.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPK10", 0.0F, cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.2F), -25.0F, 25.0F, -35.0F, 35.0F), 0.0F);

    this.mesh.chunkVisible("Z_GearLRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearRRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearLGreen1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_GearRGreen1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Red3", this.fm.CT.getGear() == 1.0F);

    this.mesh.chunkVisible("Z_Red1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_Red2", (this.fm.EI.engines[0].tOilOut > 115.0F) || (this.fm.EI.engines[0].tOilOut < 40.0F));
    this.mesh.chunkVisible("Z_Red5", (this.fm.EI.engines[0].tWaterOut < 60.0F) || (this.fm.EI.engines[0].tWaterOut > 110.0F) || (this.fm.EI.engines[0].tOilIn < 40.0F));
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("prib_D1", false);
      this.mesh.chunkVisible("prib_DD1", true);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);

      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
      this.mesh.chunkVisible("radio", false);
      this.mesh.chunkVisible("radio_d1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("prib_D2", false);
      this.mesh.chunkVisible("prib_DD2", true);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
    }

    if (((this.fm.AS.astateCockpitState & 0x2) != 0) || ((this.fm.AS.astateCockpitState & 0x1) != 0))
    {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0);
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
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitLAGG_3RD.this.fm != null) {
        CockpitLAGG_3RD.access$102(CockpitLAGG_3RD.this, CockpitLAGG_3RD.this.setOld); CockpitLAGG_3RD.access$202(CockpitLAGG_3RD.this, CockpitLAGG_3RD.this.setNew); CockpitLAGG_3RD.access$302(CockpitLAGG_3RD.this, CockpitLAGG_3RD.this.setTmp);

        CockpitLAGG_3RD.this.setNew.throttle = ((10.0F * CockpitLAGG_3RD.this.setOld.throttle + CockpitLAGG_3RD.this.fm.CT.PowerControl) / 11.0F);
        CockpitLAGG_3RD.this.setNew.prop = ((10.0F * CockpitLAGG_3RD.this.setOld.prop + CockpitLAGG_3RD.this.fm.EI.engines[0].getControlProp()) / 11.0F);
        CockpitLAGG_3RD.this.setNew.altimeter = CockpitLAGG_3RD.this.fm.getAltitude();
        if (Math.abs(CockpitLAGG_3RD.this.fm.Or.getKren()) < 30.0F) {
          CockpitLAGG_3RD.this.setNew.azimuth = ((35.0F * CockpitLAGG_3RD.this.setOld.azimuth + -CockpitLAGG_3RD.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitLAGG_3RD.this.setOld.azimuth > 270.0F) && (CockpitLAGG_3RD.this.setNew.azimuth < 90.0F)) CockpitLAGG_3RD.this.setOld.azimuth -= 360.0F;
        if ((CockpitLAGG_3RD.this.setOld.azimuth < 90.0F) && (CockpitLAGG_3RD.this.setNew.azimuth > 270.0F)) CockpitLAGG_3RD.this.setOld.azimuth += 360.0F;

        if (!CockpitLAGG_3RD.this.useRealisticNavigationInstruments())
        {
          CockpitLAGG_3RD.this.setNew.waypointAzimuth.setDeg(CockpitLAGG_3RD.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitLAGG_3RD.this.waypointAzimuth() - CockpitLAGG_3RD.this.fm.Or.azimut());
        }

        CockpitLAGG_3RD.this.setNew.vspeed = ((199.0F * CockpitLAGG_3RD.this.setOld.vspeed + CockpitLAGG_3RD.this.fm.getVertSpeed()) / 200.0F);
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
    AnglesFork waypointAzimuth;
    private final CockpitLAGG_3RD this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitLAGG_3RD.1 arg2)
    {
      this();
    }
  }
}