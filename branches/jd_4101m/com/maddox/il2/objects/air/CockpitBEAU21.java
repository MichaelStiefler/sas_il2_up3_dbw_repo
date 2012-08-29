package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitBEAU21 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf1 = 1.0F;
  private float pictManf2 = 1.0F;

  private static final float[] speedometerScaleFAF = { 0.0F, 0.0F, 2.0F, 6.0F, 21.0F, 40.0F, 56.0F, 72.5F, 89.5F, 114.0F, 135.5F, 157.0F, 182.5F, 205.0F, 227.5F, 246.5F, 265.5F, 286.0F, 306.0F, 326.0F, 345.5F, 363.0F, 385.0F, 401.0F, 414.5F, 436.5F, 457.0F, 479.0F, 496.5F, 515.0F, 539.0F, 559.5F, 576.5F };

  private static final float[] variometerScale = { -158.0F, -110.0F, -63.5F, -29.0F, 0.0F, 29.0F, 63.5F, 110.0F, 158.0F };

  private static final float[] rpmScale = { 0.0F, 20.0F, 54.0F, 99.0F, 151.5F, 195.5F, 249.25F, 284.5F, 313.75F };

  private static final float[] radScale = { 0.0F, 3.0F, 7.0F, 13.5F, 21.5F, 27.0F, 34.5F, 50.5F, 71.0F, 94.0F, 125.0F, 161.0F, 202.5F, 253.0F, 315.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitBEAU21()
  {
    super("3DO/Cockpit/BeaufighterMk21/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "prib_one_fin", "prib_two", "prib_three", "prib_four", "gauges2", "prib_one_fin_damage", "prib_two_damage", "prib_three_damage", "prib_four_damage", "gauges2_damage", "PEICES1", "PEICES2" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    resetYPRmodifier();
    this.mesh.chunkSetAngles("Canopy", 0.0F, cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Trim1", 0.0F, 161.0F * this.fm.CT.getTrimAileronControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 0.0F, 332.0F * this.fm.CT.getTrimRudderControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 0.0F, 722.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -75.5F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.fm.CT.FlapsControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 0.0F, -75.5F * (this.pictGear = 0.85F * this.pictGear + 0.15F * this.fm.CT.GearControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 90.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 0.0F, 100.0F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop2", 0.0F, 100.0F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, 90.0F * this.fm.EI.engines[0].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, cvt(this.fm.EI.engines[0].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, cvt(this.fm.EI.engines[1].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, -10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 57.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Brake", 0.0F, -25.0F * this.fm.CT.getBrake(), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter3", 0.0F, cvt(interp(this.setNew.radioalt, this.setOld.radioalt, paramFloat), 0.0F, 609.59998F, 0.0F, 720.0F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 0.0F, 143.05283F, 0.0F, 32.0F), speedometerScaleFAF), 0.0F);
    }
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, -48.0F, 48.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 35.0F, -35.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0275F, -0.0275F);
    this.mesh.chunkSetLocate("Z_TurnBank4", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale), 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.waypointAzimuth.getDeg(paramFloat * 0.02F), 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getRPM(), 1000.0F, 5000.0F, 0.0F, 8.0F), rpmScale), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0)
      this.mesh.chunkSetAngles("Z_RPM2", 0.0F, floatindex(cvt(this.fm.EI.engines[1].getRPM(), 1000.0F, 5000.0F, 0.0F, 8.0F), rpmScale), 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_RPM2", 0.0F, floatindex(cvt(this.fm.EI.engines[1].getRPM(), 500.0F, 9800.0F, 0.0F, 8.0F), rpmScale), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 2332.0F, 0.0F, 77.0F));
    this.mesh.chunkSetAngles("Z_Fuel2", 0.0F, 0.0F, cvt(this.fm.M.fuel, 0.0F, 2332.0F, 0.0F, 77.0F));
    this.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 10.0F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 10.0F, 0.0F, 277.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres2", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 10.0F * this.fm.EI.engines[1].getPowerOutput() : 0.0F, 0.0F, 10.0F, 0.0F, 277.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, floatindex(cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Temp2", 0.0F, floatindex(cvt(this.fm.EI.engines[1].tOilIn, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Pres1", 0.0F, this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_Pres2", 0.0F, this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 100.0F, 0.0F, 274.0F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Oil2", 0.0F, cvt(this.fm.EI.engines[1].tOilOut, 40.0F, 100.0F, 0.0F, 274.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 12.59F, 0.0F, 277.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres2", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 12.59F, 0.0F, 277.0F), 0.0F);
    float f = 0.5F * this.fm.EI.engines[0].getRPM() + 0.5F * this.fm.EI.engines[1].getRPM();
    f = 2.5F * (float)Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f))));
    this.mesh.chunkSetAngles("Z_Suction", 0.0F, cvt(f, 0.0F, 10.0F, 0.0F, 302.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Approach", 0.0F, cvt(this.setNew.waypointDeviation.getDeg(paramFloat * 0.5F), -90.0F, 90.0F, -46.5F, 46.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_AirTemp", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -17.799999F, 60.0F, 0.0F, -109.5F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats1_D1", true);
      this.mesh.chunkVisible("Z_OilSplats2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);

      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Fuel2", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Altimeter3", false);
      this.mesh.chunkVisible("Z_Hour1", false);
      this.mesh.chunkVisible("Z_Minute1", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_FuelPres2", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    retoggleLight();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      setNightMats(true);
    }
    else
      setNightMats(false);
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

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitBEAU21.this.fm != null) {
        CockpitBEAU21.access$102(CockpitBEAU21.this, CockpitBEAU21.this.setOld); CockpitBEAU21.access$202(CockpitBEAU21.this, CockpitBEAU21.this.setNew); CockpitBEAU21.access$302(CockpitBEAU21.this, CockpitBEAU21.this.setTmp);

        CockpitBEAU21.this.setNew.throttle1 = (0.85F * CockpitBEAU21.this.setOld.throttle1 + CockpitBEAU21.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitBEAU21.this.setNew.throttle2 = (0.85F * CockpitBEAU21.this.setOld.throttle2 + CockpitBEAU21.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitBEAU21.this.setNew.prop1 = (0.85F * CockpitBEAU21.this.setOld.prop1 + CockpitBEAU21.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitBEAU21.this.setNew.prop2 = (0.85F * CockpitBEAU21.this.setOld.prop2 + CockpitBEAU21.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitBEAU21.this.setNew.altimeter = CockpitBEAU21.this.fm.getAltitude();

        CockpitBEAU21.this.setNew.azimuth.setDeg(CockpitBEAU21.this.setOld.azimuth.getDeg(1.0F), CockpitBEAU21.this.fm.Or.azimut());
        if (CockpitBEAU21.this.useRealisticNavigationInstruments())
        {
          CockpitBEAU21.this.setNew.waypointDeviation.setDeg(CockpitBEAU21.this.setOld.waypointDeviation.getDeg(0.1F), CockpitBEAU21.this.getBeaconDirection());
          CockpitBEAU21.this.setNew.waypointAzimuth.setDeg(CockpitBEAU21.this.setOld.waypointAzimuth.getDeg(0.02F), CockpitBEAU21.this.radioCompassAzimuthInvertMinus() - CockpitBEAU21.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
        }
        else
        {
          float f = CockpitBEAU21.this.waypointAzimuth();
          CockpitBEAU21.this.setNew.waypointDeviation.setDeg(CockpitBEAU21.this.setOld.waypointDeviation.getDeg(0.1F), f - CockpitBEAU21.this.setOld.azimuth.getDeg(1.0F));
          CockpitBEAU21.this.setNew.waypointAzimuth.setDeg(CockpitBEAU21.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitBEAU21.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
        }

        if ((CockpitBEAU21.this.fm.AS.astateCockpitState & 0x40) == 0)
          CockpitBEAU21.this.setNew.vspeed = ((199.0F * CockpitBEAU21.this.setOld.vspeed + CockpitBEAU21.this.fm.getVertSpeed()) / 200.0F);
        else {
          CockpitBEAU21.this.setNew.vspeed = ((1990.0F * CockpitBEAU21.this.setOld.vspeed + CockpitBEAU21.this.fm.getVertSpeed()) / 2000.0F);
        }
        World.cur(); World.land(); CockpitBEAU21.this.setNew.radioalt = (0.9F * CockpitBEAU21.this.setOld.radioalt + 0.1F * (CockpitBEAU21.this.fm.getAltitude() - Landscape.HQ_Air((float)CockpitBEAU21.this.fm.Loc.x, (float)CockpitBEAU21.this.fm.Loc.y) + World.Rnd().nextFloat(-10.0F, 10.0F)));
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float throttle2;
    float prop1;
    float prop2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    AnglesFork waypointDeviation;
    float radioalt;
    private final CockpitBEAU21 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();

      this.waypointDeviation = new AnglesFork();
    }

    Variables(CockpitBEAU21.1 arg2)
    {
      this();
    }
  }
}