package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitGO_229 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerIndScale = { 0.0F, 0.0F, 0.0F, 17.0F, 35.5F, 57.5F, 76.0F, 95.0F, 112.0F };

  private static final float[] speedometerTruScale = { 0.0F, 32.75F, 65.5F, 98.25F, 131.0F, 164.0F, 200.0F, 237.0F, 270.5F, 304.0F, 336.0F };

  private static final float[] variometerScale = { 0.0F, 13.5F, 27.0F, 43.5F, 90.0F, 142.5F, 157.0F, 170.5F, 184.0F, 201.5F, 214.5F, 226.0F, 239.5F, 253.0F, 266.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  private static final float[] fuelScale = { 0.0F, 11.0F, 31.0F, 57.0F, 84.0F, 103.5F };

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitGO_229()
  {
    super("3DO/Cockpit/Go-229/hier.him", "he111");

    this.cockpitNightMats = new String[] { "2petitsb_d1", "2petitsb", "aiguill1", "badinetm_d1", "badinetm", "baguecom", "brasdele", "comptemu_d1", "comptemu", "petitfla_d1", "petitfla", "turnbank" };

    setNightMats(false);

    this.setNew.dimPosition = 1.0F;

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (this.fm.isTick(44, 0))
    {
      this.mesh.chunkVisible("Z_GearLGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
      this.mesh.chunkVisible("Z_GearRGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
      this.mesh.chunkVisible("Z_GearCGreen1", this.fm.CT.getGear() == 1.0F);
      this.mesh.chunkVisible("Z_GearLRed1", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
      this.mesh.chunkVisible("Z_GearCRed1", this.fm.CT.getGear() == 0.0F);

      this.mesh.chunkVisible("Z_FuelLampL", this.fm.M.fuel < 300.0F);
      this.mesh.chunkVisible("Z_FuelLampR", this.fm.M.fuel < 300.0F);
      this.mesh.chunkVisible("Z_Fire", false);
    }
    this.mesh.chunkVisible("Z_FlapEin", this.fm.CT.getFlap() < 0.05F);
    this.mesh.chunkVisible("Z_FlapStart", (this.fm.CT.getFlap() > 0.28F) && (this.fm.CT.getFlap() < 0.38F));
    this.mesh.chunkVisible("Z_FlapAus", this.fm.CT.getFlap() > 0.95F);

    this.mesh.chunkSetAngles("zColumn1", 0.0F, 10.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("zColumn2", 0.0F, -10.0F * this.pictAiler, 0.0F);

    this.mesh.chunkSetAngles("Z_PedalStrut", 10.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", -10.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", -10.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFlaps1", 0.0F, 0.0F, -32.0F + 42.5F * this.fm.CT.FlapsControl);

    this.mesh.chunkSetAngles("zThrottle1", 0.0F, 0.0F, 20.5F - 32.0F * interp(this.setNew.throttlel, this.setOld.throttlel, paramFloat));
    this.mesh.chunkSetAngles("zThrottle2", 0.0F, 0.0F, 20.5F - 32.0F * interp(this.setNew.throttler, this.setOld.throttler, paramFloat));

    this.mesh.chunkSetAngles("zGear1", 0.0F, 0.0F, -35.5F + 35.5F * this.fm.CT.GearControl);
    this.mesh.chunkSetAngles("zAirBrake1", 0.0F, 0.0F, 32.0F * this.fm.CT.AirBrakeControl);

    this.mesh.chunkSetAngles("Z_TurnBank1", this.fm.Or.getTangage(), 0.0F, this.fm.Or.getKren());
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, -cvt(getBall(6.0D), -6.0F, 6.0F, -7.5F, 7.5F));
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F));

    this.mesh.chunkSetAngles("zSpeed1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 100.0F, 400.0F, 2.0F, 8.0F), speedometerIndScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed2", floatindex(cvt(this.fm.getSpeedKMH(), 100.0F, 1000.0F, 1.0F, 10.0F), speedometerTruScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 16000.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("zRepeater", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zCompass", -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("zCompass", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zRepeater", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("zRPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zRPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFuel1", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zFuel2", floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 1000.0F, 0.0F, 5.0F), fuelScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zExtT", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 273.09F, 373.09F, -26.0F, 144.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_GasTempL", cvt(this.fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_GasTempR", cvt(this.fm.EI.engines[1].tWaterOut, 300.0F, 1000.0F, 0.0F, 96.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPressureL", cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPressureR", cvt(1.0F + 0.005F * this.fm.EI.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPressure", cvt(this.fm.M.fuel > 1.0F ? 80.0F * this.fm.EI.engines[0].getPowerOutput() * this.fm.EI.engines[0].getReadyness() : 0.0F, 0.0F, 160.0F, 0.0F, 278.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -20.0F, 50.0F, 0.0F, 14.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -45.0F), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.chunkVisible("XGlassDamage4", true);

      this.mesh.chunkVisible("Speedometer1", false);
      this.mesh.chunkVisible("Speedometer1_D1", true);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Speedometer2", false);

      this.mesh.chunkVisible("RPML", false);
      this.mesh.chunkVisible("RPML_D1", true);
      this.mesh.chunkVisible("Z_RPML", false);

      this.mesh.chunkVisible("FuelRemainV", false);
      this.mesh.chunkVisible("FuelRemainV_D1", true);
      this.mesh.chunkVisible("Z_FuelRemainV", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
      this.mesh.chunkVisible("XGlassDamage3", true);

      this.mesh.chunkVisible("Altimeter1", false);
      this.mesh.chunkVisible("Altimeter1_D1", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);

      this.mesh.chunkVisible("GasPressureL", false);
      this.mesh.chunkVisible("GasPressureL_D1", true);
      this.mesh.chunkVisible("Z_GasPressureL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("XGlassDamage4", true);

      this.mesh.chunkVisible("RPMR", false);
      this.mesh.chunkVisible("RPMR_D1", true);
      this.mesh.chunkVisible("Z_RPMR", false);

      this.mesh.chunkVisible("FuelPressR", false);
      this.mesh.chunkVisible("FuelPressR_D1", true);
      this.mesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("XGlassDamage3", true);

      this.mesh.chunkVisible("GasPressureR", false);
      this.mesh.chunkVisible("GasPressureR_D1", true);
      this.mesh.chunkVisible("Z_GasPressureR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);

      this.mesh.chunkVisible("Climb", false);
      this.mesh.chunkVisible("Climb_D1", true);
      this.mesh.chunkVisible("Z_Climb1", false);

      this.mesh.chunkVisible("FuelPressR", false);
      this.mesh.chunkVisible("FuelPressR_D1", true);
      this.mesh.chunkVisible("Z_FuelPressR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("HullDamage2", true);

      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Z_ReViTint", false);
      this.mesh.chunkVisible("Revi_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);

      this.mesh.chunkVisible("FuelPressL", false);
      this.mesh.chunkVisible("FuelPressL_D1", true);
      this.mesh.chunkVisible("Z_FuelPressL", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);

      this.mesh.chunkVisible("Altimeter1", false);
      this.mesh.chunkVisible("Altimeter1_D1", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);

      this.mesh.chunkVisible("Climb", false);
      this.mesh.chunkVisible("Climb_D1", true);
      this.mesh.chunkVisible("Z_Climb1", false);

      this.mesh.chunkVisible("AFN", false);
      this.mesh.chunkVisible("AFN_D1", true);
      this.mesh.chunkVisible("Z_AFN1", false);
      this.mesh.chunkVisible("Z_AFN2", false);

      this.mesh.chunkVisible("FuelPressL", false);
      this.mesh.chunkVisible("FuelPressL_D1", true);
      this.mesh.chunkVisible("Z_FuelPressL", false);

      this.mesh.chunkVisible("FuelRemainIn", false);
      this.mesh.chunkVisible("FuelRemainIn_D1", true);
      this.mesh.chunkVisible("Z_FuelRemainIn", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0);
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
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

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitGO_229.access$102(CockpitGO_229.this, CockpitGO_229.this.setOld); CockpitGO_229.access$202(CockpitGO_229.this, CockpitGO_229.this.setNew); CockpitGO_229.access$302(CockpitGO_229.this, CockpitGO_229.this.setTmp);

      CockpitGO_229.this.setNew.altimeter = CockpitGO_229.this.fm.getAltitude();
      CockpitGO_229.this.setNew.throttlel = ((10.0F * CockpitGO_229.this.setOld.throttlel + CockpitGO_229.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitGO_229.this.setNew.throttler = ((10.0F * CockpitGO_229.this.setOld.throttler + CockpitGO_229.this.fm.EI.engines[1].getControlThrottle()) / 11.0F);

      float f = CockpitGO_229.this.waypointAzimuth();
      if (CockpitGO_229.this.useRealisticNavigationInstruments())
      {
        CockpitGO_229.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitGO_229.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
      }
      else
      {
        CockpitGO_229.this.setNew.waypointAzimuth.setDeg(CockpitGO_229.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitGO_229.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitGO_229.this.setNew.azimuth.setDeg(CockpitGO_229.this.setOld.azimuth.getDeg(1.0F), CockpitGO_229.this.fm.Or.azimut());

      CockpitGO_229.this.setNew.vspeed = ((299.0F * CockpitGO_229.this.setOld.vspeed + CockpitGO_229.this.fm.getVertSpeed()) / 300.0F);

      if (CockpitGO_229.this.cockpitDimControl) {
        if (CockpitGO_229.this.setNew.dimPosition > 0.0F) CockpitGO_229.this.setNew.dimPosition = (CockpitGO_229.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitGO_229.this.setNew.dimPosition < 1.0F) CockpitGO_229.this.setNew.dimPosition = (CockpitGO_229.this.setOld.dimPosition + 0.05F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttlel;
    float throttler;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    private final CockpitGO_229 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitGO_229.1 arg2)
    {
      this();
    }
  }
}