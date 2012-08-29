package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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

public class CockpitMIG_3op extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private static final float[] speedometerScale = { 0.0F, 0.0F, 15.5F, 50.0F, 95.5F, 137.0F, 182.5F, 212.0F, 230.0F, 242.0F, 254.5F, 267.5F, 279.0F, 292.0F, 304.0F, 317.0F, 329.5F, 330.0F };

  public CockpitMIG_3op()
  {
    super("3do/cockpit/MiG-3op/hier.him", "bf109");
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    this.mesh.chunkSetAngles("richag", 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F);

    this.mesh.chunkSetAngles("Ped_Base", 0.0F, -this.fm.CT.getRudder() * 15.0F, 0.0F);

    this.mesh.chunkSetAngles("PedalL", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalR", 0.0F, this.fm.CT.getRudder() * 15.0F, 0.0F);
    this.mesh.chunkSetAngles("Drossel", 0.0F, -20.0F + interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 60.0F * 0.91F, 0.0F);

    this.mesh.chunkSetAngles("Forsaj", 0.0F, interp(this.setNew.prop, this.setOld.prop, paramFloat) * 55.0F * 0.91F, 0.0F);

    this.mesh.chunkSetAngles("r_one", 0.0F, -20.0F * (this.fm.CT.WeaponControl[0] != 0 ? 1 : 0), 0.0F);

    this.mesh.chunkSetAngles("r_two", 0.0F, -20.0F * (this.fm.CT.WeaponControl[1] != 0 ? 1 : 0), 0.0F);

    this.mesh.chunkSetAngles("r_turn", 0.0F, -20.0F * this.fm.CT.BrakeControl, 0.0F);
    if (this.fm.Gears.isHydroOperable()) {
      this.mesh.chunkSetAngles("zGearLever1a", 30.0F - 60.0F * this.fm.CT.GearControl, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("zAlt1a", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt1b", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1a", 0.0F, cvt(this.fm.Or.getTangage(), -40.0F, 40.0F, -40.0F, 40.0F), 0.0F);

    this.mesh.chunkSetAngles("zAzimuth1b", 0.0F, interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("zHorizon1b", 0.0F, -this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("zManifold1a", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.4F, 2.133F, 0.0F, 334.28601F), 0.0F);

    this.mesh.chunkSetAngles("zGas1a", 0.0F, cvt(this.fm.M.fuel / 0.72F, 0.0F, 300.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1a", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurn1a", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F);

    this.mesh.chunkSetAngles("zSlide1a", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -24.0F, 24.0F), 0.0F);

    this.mesh.chunkSetAngles("zVariometer1a", 0.0F, cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1a", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("zRPM1b", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zTOilOut1a", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zOilPrs1a", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("zGasPrs1a", 0.0F, cvt(this.fm.M.fuel > 1.0F ? cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3050.0F, 0.0F, 4.0F) : 0.0F, 0.0F, 8.0F, 0.0F, -180.0F), 0.0F);

    this.mesh.chunkSetAngles("zTWater1a", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, -86.0F), 0.0F);

    this.mesh.chunkSetAngles("zTOilIn1a", 0.0F, cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 120.0F, 0.0F, -86.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zClock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zFlapPos1a", 0.0F, 0.0F, cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 9.0F));

    this.mesh.chunkVisible("Z_GearLGreen1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearRGreen1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearLRed1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_GearRRed1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_Red1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_Red2", this.fm.EI.engines[0].tWaterOut > 110.0F);
  }

  public void reflectCockpitState() {
    if (((this.fm.AS.astateCockpitState & 0x4) != 0) || ((this.fm.AS.astateCockpitState & 0x10) != 0))
    {
      this.mesh.chunkVisible("prib_D1", false);
      this.mesh.chunkVisible("prib_N1", false);
      this.mesh.chunkVisible("prib_DD1", true);
      this.mesh.chunkVisible("zAzimuth1a", false);
      this.mesh.chunkVisible("zAzimuth1b", false);
      this.mesh.chunkVisible("zHorizon1a", false);
      this.mesh.chunkVisible("zHorizon1b", false);
      this.mesh.chunkVisible("zManifold1a", false);
      this.mesh.chunkVisible("zVariometer1a", false);
      this.mesh.chunkVisible("zSpeed1a", false);
      this.mesh.chunkVisible("zTOilIn1a", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || ((this.fm.AS.astateCockpitState & 0x20) != 0))
    {
      this.mesh.chunkVisible("prib_D2", false);
      this.mesh.chunkVisible("prib_N2", false);
      this.mesh.chunkVisible("prib_DD2", true);
      this.mesh.chunkVisible("zRPM1a", false);
      this.mesh.chunkVisible("zRPM1b", false);
      this.mesh.chunkVisible("zAlt1a", false);
      this.mesh.chunkVisible("zAlt1b", false);
      this.mesh.chunkVisible("zGas1a", false);
      this.mesh.chunkVisible("zTurn1a", false);
      this.mesh.chunkVisible("zTOilOut1a", false);
      this.mesh.chunkVisible("zOilPrs1a", false);
      this.mesh.chunkVisible("zGasPrs1a", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("panel_d", false);
      this.mesh.chunkVisible("panel_n", false);
      this.mesh.chunkVisible("panel_dd", true);
      this.mesh.chunkVisible("zTWater1a", false);
    }
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) && ((this.fm.AS.astateCockpitState & 0x10) == 0))
    {
      this.mesh.chunkVisible("prib_D1", !this.cockpitLightControl);
      this.mesh.chunkVisible("prib_N1", this.cockpitLightControl);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) && ((this.fm.AS.astateCockpitState & 0x20) == 0))
    {
      this.mesh.chunkVisible("prib_D2", !this.cockpitLightControl);
      this.mesh.chunkVisible("prib_N2", this.cockpitLightControl);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkVisible("panel_d", !this.cockpitLightControl);
      this.mesh.chunkVisible("panel_n", this.cockpitLightControl);
    }
    if (this.cockpitLightControl)
      this.mesh.materialReplace("Strelki", "Strelki_n");
    else
      this.mesh.materialReplace("Strelki", "Strelki");
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    float azimuth;
    float vspeed;
    private final CockpitMIG_3op this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitMIG_3op.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitMIG_3op.this.fm != null) {
        CockpitMIG_3op.access$102(CockpitMIG_3op.this, CockpitMIG_3op.this.setOld);
        CockpitMIG_3op.access$202(CockpitMIG_3op.this, CockpitMIG_3op.this.setNew);
        CockpitMIG_3op.access$302(CockpitMIG_3op.this, CockpitMIG_3op.this.setTmp);
        CockpitMIG_3op.this.setNew.throttle = ((10.0F * CockpitMIG_3op.this.setOld.throttle + CockpitMIG_3op.this.fm.CT.PowerControl) / 11.0F);

        CockpitMIG_3op.this.setNew.prop = ((10.0F * CockpitMIG_3op.this.setOld.prop + CockpitMIG_3op.this.fm.EI.engines[0].getControlProp()) / 11.0F);

        CockpitMIG_3op.this.setNew.altimeter = CockpitMIG_3op.this.fm.getAltitude();
        if (Math.abs(CockpitMIG_3op.this.fm.Or.getKren()) < 30.0F) {
          CockpitMIG_3op.this.setNew.azimuth = ((35.0F * CockpitMIG_3op.this.setOld.azimuth + CockpitMIG_3op.this.fm.Or.azimut()) / 36.0F);
        }
        if ((CockpitMIG_3op.this.setOld.azimuth > 270.0F) && (CockpitMIG_3op.this.setNew.azimuth < 90.0F))
          CockpitMIG_3op.this.setOld.azimuth -= 360.0F;
        if ((CockpitMIG_3op.this.setOld.azimuth < 90.0F) && (CockpitMIG_3op.this.setNew.azimuth > 270.0F))
          CockpitMIG_3op.this.setOld.azimuth += 360.0F;
        CockpitMIG_3op.this.setNew.vspeed = ((199.0F * CockpitMIG_3op.this.setOld.vspeed + CockpitMIG_3op.this.fm.getVertSpeed()) / 200.0F);
      }

      return true;
    }
  }
}