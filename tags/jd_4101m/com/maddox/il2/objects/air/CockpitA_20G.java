package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
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
import com.maddox.sound.ReverbFXRoom;

public class CockpitA_20G extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 16.5F, 79.5F, 143.0F, 206.5F, 229.5F, 251.0F, 272.5F, 294.0F, 316.0F, 339.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(5.0F);
  }

  public CockpitA_20G()
  {
    super("3DO/Cockpit/A-20G/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "256-10_Gauges6_DMG", "256-10_Gauges6", "256-16_Gauges7_DMG", "256-16_Gauges7", "256-5_Gauges1_DMG", "256-5_Gauges1", "256-6_Gauges2_DMG", "256-6_Gauges2", "256-7_Gauges3_DMG", "256-7_Gauges3", "256-8_Gauges4_DMG", "256-8_Gauges4", "256-9_Gauges5_DMG", "256-9_Gauges5", "256-18", "256-21", "256-22", "128_Gauge_DF", "128_Gauge_DF_DMG" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    resetYPRmodifier();
    this.mesh.chunkSetAngles("Canopy", 0.0F, cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, -120.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, -70.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle2", 0.0F, -70.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 0.0F, -70.0F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop2", 0.0F, -70.0F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, -47.0F * interp(this.setNew.mix1, this.setOld.mix1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture2", 0.0F, -47.0F * interp(this.setNew.mix2, this.setOld.mix2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, 73.0F - 73.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger2", 0.0F, 73.0F - 73.0F * this.fm.EI.engines[1].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_BombBay1", 0.0F, 40.0F * this.fm.CT.BayDoorControl, 0.0F);
    resetYPRmodifier();
    xyz[1] = (-0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_LeftPedal1", xyz, ypr);
    xyz[1] = (0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal1", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Column1", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.8F);
    this.mesh.chunkSetAngles("Z_Column2", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 72.5F, 0.0F);

    if (this.fm.Gears.lgear) {
      this.mesh.chunkSetAngles("Z_GearLInd", 0.0F, 86.0F * this.fm.CT.getGear(), 0.0F);
    }
    if (this.fm.Gears.rgear) {
      this.mesh.chunkSetAngles("Z_GearRInd", 0.0F, -86.0F * this.fm.CT.getGear(), 0.0F);
    }
    if (this.fm.Gears.cgear) {
      this.mesh.chunkSetAngles("Z_GearCInd", 0.0F, 86.0F * this.fm.CT.getGear(), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_FlapInd", 0.0F, -67.5F * this.fm.CT.getFlap(), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 804.67212F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -16.0F, 16.0F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(this.setNew.vspeed, -10.159F, 10.159F, -180.0F, 180.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 5000.0F, 0.0F, 305.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_RPM2", 0.0F, cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 5000.0F, 0.0F, 305.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.fm.M.fuel, 0.0F, 1344.0F, 0.0F, 70.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.3F, 0.0F, 301.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres2", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.3F, 0.0F, 301.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 87.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", 0.0F, cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 350.0F, 0.0F, 87.5F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0)
      this.mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(this.setNew.man1, 0.3386378F, 1.693189F, 0.0F, 285.5F), 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(this.setNew.man1, 0.3386378F, World.Rnd().nextFloat(1.0F, 2.0F), 0.0F, 285.5F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Pres2", 0.0F, cvt(this.setNew.man2, 0.3386378F, 1.693189F, 0.0F, 285.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 150.0F, 0.0F, 77.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oil2", 0.0F, cvt(this.fm.EI.engines[1].tOilOut, 50.0F, 150.0F, 0.0F, 77.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 302.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres2", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 302.5F), 0.0F);
    this.mesh.chunkSetAngles("Z_CarbIn1", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -15.0F, 55.0F, -35.0F, 35.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_CarbIn2", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -15.0F, 55.0F, -35.0F, 35.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Hydro1", 0.0F, cvt(this.fm.Gears.isHydroOperable() ? 0.8F : 0.0F, 0.0F, 1.0F, 0.0F, 180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_OxyPres1", 0.0F, 228.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OxyQ1", 0.0F, 279.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AH1", 0.0F, this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -0.025F, 0.025F);
    this.mesh.chunkSetLocate("Z_AH2", xyz, ypr);

    this.mesh.chunkSetAngles("Z_DF", 0.0F, cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.2F), -25.0F, 25.0F, -30.0F, 30.0F), 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Z_Pres2", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_FuelPres1", false);
      this.mesh.chunkVisible("Panel_D1", true);

      this.mesh.chunkVisible("Z_DF", false);
      this.mesh.chunkVisible("DF_gauge_D0", false);
      this.mesh.chunkVisible("DF_gauge_D1", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage3", true);
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
      if (CockpitA_20G.this.fm != null) {
        CockpitA_20G.access$102(CockpitA_20G.this, CockpitA_20G.this.setOld); CockpitA_20G.access$202(CockpitA_20G.this, CockpitA_20G.this.setNew); CockpitA_20G.access$302(CockpitA_20G.this, CockpitA_20G.this.setTmp);

        CockpitA_20G.this.setNew.throttle1 = (0.9F * CockpitA_20G.this.setOld.throttle1 + 0.1F * CockpitA_20G.this.fm.EI.engines[0].getControlThrottle());
        CockpitA_20G.this.setNew.prop1 = (0.9F * CockpitA_20G.this.setOld.prop1 + 0.1F * CockpitA_20G.this.fm.EI.engines[0].getControlProp());
        CockpitA_20G.this.setNew.mix1 = (0.8F * CockpitA_20G.this.setOld.mix1 + 0.2F * CockpitA_20G.this.fm.EI.engines[0].getControlMix());
        CockpitA_20G.this.setNew.throttle2 = (0.9F * CockpitA_20G.this.setOld.throttle2 + 0.1F * CockpitA_20G.this.fm.EI.engines[1].getControlThrottle());
        CockpitA_20G.this.setNew.prop2 = (0.9F * CockpitA_20G.this.setOld.prop2 + 0.1F * CockpitA_20G.this.fm.EI.engines[1].getControlProp());
        CockpitA_20G.this.setNew.mix2 = (0.8F * CockpitA_20G.this.setOld.mix2 + 0.2F * CockpitA_20G.this.fm.EI.engines[1].getControlMix());
        CockpitA_20G.this.setNew.man1 = (0.92F * CockpitA_20G.this.setOld.man1 + 0.08F * CockpitA_20G.this.fm.EI.engines[0].getManifoldPressure());
        CockpitA_20G.this.setNew.man2 = (0.92F * CockpitA_20G.this.setOld.man2 + 0.08F * CockpitA_20G.this.fm.EI.engines[1].getManifoldPressure());
        CockpitA_20G.this.setNew.altimeter = CockpitA_20G.this.fm.getAltitude();

        if (CockpitA_20G.this.useRealisticNavigationInstruments())
        {
          CockpitA_20G.this.setNew.waypointAzimuth.setDeg(CockpitA_20G.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitA_20G.this.getBeaconDirection());
        }
        else
        {
          CockpitA_20G.this.setNew.waypointAzimuth.setDeg(CockpitA_20G.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitA_20G.this.waypointAzimuth() - CockpitA_20G.this.fm.Or.azimut());
        }

        CockpitA_20G.this.setNew.azimuth.setDeg(CockpitA_20G.this.setOld.azimuth.getDeg(1.0F), CockpitA_20G.this.fm.Or.azimut());

        CockpitA_20G.this.setNew.vspeed = ((199.0F * CockpitA_20G.this.setOld.vspeed + CockpitA_20G.this.fm.getVertSpeed()) / 200.0F);
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
    float mix1;
    float mix2;
    float man1;
    float man2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    private final CockpitA_20G this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();

      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitA_20G.1 arg2)
    {
      this();
    }
  }
}