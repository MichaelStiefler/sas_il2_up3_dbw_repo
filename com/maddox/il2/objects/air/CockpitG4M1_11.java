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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitG4M1_11 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 6.5F, 30.0F, 66.0F, 105.0F, 158.5F, 212.0F, 272.5F, 333.0F, 384.0F, 432.5F, 479.5F, 526.5F, 573.5F, 624.5F, 674.0F };

  private static final float[] oilTempScale = { 0.0F, 4.0F, 17.5F, 38.0F, 63.0F, 90.5F, 115.0F, 141.3F, 180.0F, 221.7F, 269.5F, 311.0F, 357.0F };

  private static final float[] rpmScale = { 0.0F, 10.0F, 75.0F, 126.5F, 179.5F, 232.0F, 284.5F, 336.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(5.0F);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("Cockpit_D0", (aircraft().hierMesh().isChunkVisible("CF_D0")) || (aircraft().hierMesh().isChunkVisible("CF_D1")) || (aircraft().hierMesh().isChunkVisible("CF_D2")) || (aircraft().hierMesh().isChunkVisible("CF_D3")));

    super.doFocusLeave();
  }

  public CockpitG4M1_11()
  {
    super("3DO/Cockpit/G4M1-11/hier.him", "he111");

    this.cockpitNightMats = new String[] { "gauges5", "GP1_d1", "GP1", "GP2_d1", "GP2", "GP3", "GP4_d1", "GP4", "GP5_d1", "GP5", "GP6_d1", "GP6", "GP7", "GP9", "throttle", "Volt_Tacho" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    this.mesh.chunkSetAngles("Z_ColumnL", 0.0F, 0.0F, -60.599998F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl));
    this.mesh.chunkSetAngles("Z_ColumnR", 0.0F, 0.0F, -60.599998F * this.pictAiler);
    this.mesh.chunkSetAngles("Z_Throtle1", 40.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle2", 40.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 75.5F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop2", 75.5F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 62.900002F * interp(this.setNew.mix1, this.setOld.mix1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture2", 62.900002F * interp(this.setNew.mix2, this.setOld.mix2, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPedal", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RPedalStep1", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RPedalStep2", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LPedal", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LPedalStep1", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LPedalStep2", 0.0F, 0.0F, -22.200001F * this.fm.CT.getRudder());

    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass4", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Z_Compass5", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) + 90.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AH1", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -0.03F, 0.03F);
    this.mesh.chunkSetLocate("Z_AH2", xyz, ypr);

    this.mesh.chunkSetAngles("Z_AH3", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -0.03F, 0.03F);
    this.mesh.chunkSetLocate("Z_AH4", xyz, ypr);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    float f2 = getBall(7.0D);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(f2, -5.0F, 5.0F, 8.5F, -8.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(f2, -7.0F, 7.0F, 16.0F, -16.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank4", cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank5", cvt(f2, -7.0F, 7.0F, 16.0F, -16.0F), 0.0F, 0.0F);

    float f3 = floatindex(cvt(0.539957F * Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 300.0F, 0.0F, 15.0F), speedometerScale);

    this.mesh.chunkSetAngles("Z_Speedometer1", f3, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer2", f3, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 1440.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 1440.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp3", cvt(this.fm.EI.engines[1].tOilIn, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp4", cvt(this.fm.EI.engines[0].tOilIn, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    float f1 = Atmosphere.temperature((float)this.fm.Loc.z) - 273.09F + 44.400002F * this.fm.EI.engines[0].getPowerOutput();
    if (f1 < 0.0F)
      this.mesh.chunkSetAngles("Z_Temp5", cvt(f1, -40.0F, 0.0F, 0.0F, 45.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Temp5", cvt(f1, 0.0F, 60.0F, 45.0F, 90.0F), 0.0F, 0.0F);
    }
    f1 = Atmosphere.temperature((float)this.fm.Loc.z) - 273.09F + 44.400002F * this.fm.EI.engines[1].getPowerOutput();
    if (f1 < 0.0F)
      this.mesh.chunkSetAngles("Z_Temp6", cvt(f1, -40.0F, 0.0F, 0.0F, 45.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Temp6", cvt(f1, 0.0F, 60.0F, 45.0F, 90.0F), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Temp7", floatindex(cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 120.0F, 0.0F, 12.0F), oilTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp8", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 12.0F), oilTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap1", cvt(this.fm.CT.getFlap(), 0.0F, 0.75F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap2", 57.0F * this.fm.CT.getFlap(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt((float)Math.sqrt(this.fm.M.fuel), 0.0F, 34.640999F, 0.0F, 225.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", cvt((float)Math.sqrt(this.fm.M.fuel), 0.0F, 34.640999F, 0.0F, 225.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel3", cvt((float)Math.sqrt(this.fm.M.fuel), 0.0F, 38.729F, 0.0F, 225.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel4", cvt((float)Math.sqrt(this.fm.M.fuel), 26.924999F, 38.729F, 0.0F, 225.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel5", cvt((float)Math.sqrt(this.fm.M.fuel), 26.924999F, 38.729F, 0.0F, 225.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.setNew.vspeed, -15.0F, 15.0F, -0.055F, 0.055F);
    this.mesh.chunkSetLocate("Z_Climb1", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Climb2", cvt(this.setNew.vspeed, -10.0F, 10.0F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold1", cvt(this.setNew.man1, 0.33339F, 1.66661F, -162.0F, 162.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold2", cvt(this.setNew.man2, 0.33339F, 1.66661F, -162.0F, 162.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_fuelpress1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.5F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_fuelpress2", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.5F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 7.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 3500.0F, 0.0F, 7.0F), rpmScale), 0.0F, 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkVisible("Z_GearGreen1", this.fm.CT.getGear() > 0.99F);
      this.mesh.chunkVisible("Z_GearGreen2", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
      this.mesh.chunkVisible("Z_GearGreen3", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
      this.mesh.chunkVisible("Z_GearRed1", this.fm.CT.getGear() < 0.01F);
      this.mesh.chunkVisible("Z_GearRed2", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
      this.mesh.chunkVisible("Z_GearRed3", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    }

    this.mesh.chunkSetAngles("Z_AFN12", 0.0F, cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -13.0F, 13.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_AFN11", 0.0F, cvt(this.setNew.beaconRange, 0.0F, 1.0F, 28.0F, -28.0F), 0.0F);
    this.mesh.chunkVisible("AFN1_RED", isOnBlindLandingMarker());
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage5", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage5", true);
      this.mesh.chunkVisible("XHullDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Fuel1", false);
      this.mesh.chunkVisible("Z_Fuel2", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("Z_TurnBank3", false);
      this.mesh.chunkVisible("Z_TurnBank4", false);
      this.mesh.chunkVisible("Z_TurnBank5", false);
      this.mesh.chunkVisible("Z_Flap2", false);
      this.mesh.chunkVisible("Z_GearGreen1", false);
      this.mesh.chunkVisible("Z_GearGreen2", false);
      this.mesh.chunkVisible("Z_GearGreen3", false);
      this.mesh.chunkVisible("Z_GearRed1", false);
      this.mesh.chunkVisible("Z_GearRed2", false);
      this.mesh.chunkVisible("Z_GearRed3", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_RPM2", false);
      this.mesh.chunkVisible("Z_Oil1", false);
      this.mesh.chunkVisible("Z_Oil2", false);
      this.mesh.chunkVisible("Z_fuelpress1", false);
      this.mesh.chunkVisible("Z_fuelpress2", false);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_Temp2", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("XHullDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage3", true);
      this.mesh.chunkVisible("XHullDamage5", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage5", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    retoggleLight();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
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
      if (CockpitG4M1_11.this.fm != null) {
        CockpitG4M1_11.access$102(CockpitG4M1_11.this, CockpitG4M1_11.this.setOld); CockpitG4M1_11.access$202(CockpitG4M1_11.this, CockpitG4M1_11.this.setNew); CockpitG4M1_11.access$302(CockpitG4M1_11.this, CockpitG4M1_11.this.setTmp);

        CockpitG4M1_11.this.setNew.throttle1 = (0.9F * CockpitG4M1_11.this.setOld.throttle1 + 0.1F * CockpitG4M1_11.this.fm.EI.engines[0].getControlThrottle());
        CockpitG4M1_11.this.setNew.prop1 = (0.9F * CockpitG4M1_11.this.setOld.prop1 + 0.1F * CockpitG4M1_11.this.fm.EI.engines[0].getControlProp());
        CockpitG4M1_11.this.setNew.mix1 = (0.8F * CockpitG4M1_11.this.setOld.mix1 + 0.2F * CockpitG4M1_11.this.fm.EI.engines[0].getControlMix());
        CockpitG4M1_11.this.setNew.throttle2 = (0.9F * CockpitG4M1_11.this.setOld.throttle2 + 0.1F * CockpitG4M1_11.this.fm.EI.engines[1].getControlThrottle());
        CockpitG4M1_11.this.setNew.prop2 = (0.9F * CockpitG4M1_11.this.setOld.prop2 + 0.1F * CockpitG4M1_11.this.fm.EI.engines[1].getControlProp());
        CockpitG4M1_11.this.setNew.mix2 = (0.8F * CockpitG4M1_11.this.setOld.mix2 + 0.2F * CockpitG4M1_11.this.fm.EI.engines[1].getControlMix());
        CockpitG4M1_11.this.setNew.man1 = (0.92F * CockpitG4M1_11.this.setOld.man1 + 0.08F * CockpitG4M1_11.this.fm.EI.engines[0].getManifoldPressure());
        CockpitG4M1_11.this.setNew.man2 = (0.92F * CockpitG4M1_11.this.setOld.man2 + 0.08F * CockpitG4M1_11.this.fm.EI.engines[1].getManifoldPressure());
        CockpitG4M1_11.this.setNew.altimeter = CockpitG4M1_11.this.fm.getAltitude();

        CockpitG4M1_11.this.setNew.azimuth.setDeg(CockpitG4M1_11.this.setOld.azimuth.getDeg(1.0F), CockpitG4M1_11.this.fm.Or.azimut());

        CockpitG4M1_11.this.setNew.vspeed = ((199.0F * CockpitG4M1_11.this.setOld.vspeed + CockpitG4M1_11.this.fm.getVertSpeed()) / 200.0F);

        if (CockpitG4M1_11.this.useRealisticNavigationInstruments())
        {
          CockpitG4M1_11.this.setNew.waypointAzimuth.setDeg(CockpitG4M1_11.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitG4M1_11.this.waypointAzimuth());
        }
        else
        {
          CockpitG4M1_11.this.setNew.waypointAzimuth.setDeg(CockpitG4M1_11.this.setOld.waypointAzimuth.getDeg(1.0F), CockpitG4M1_11.this.fm.Or.azimut() + 90.0F);
        }

        CockpitG4M1_11.this.setNew.beaconDirection = ((10.0F * CockpitG4M1_11.this.setOld.beaconDirection + CockpitG4M1_11.this.getBeaconDirection()) / 11.0F);
        CockpitG4M1_11.this.setNew.beaconRange = ((10.0F * CockpitG4M1_11.this.setOld.beaconRange + CockpitG4M1_11.this.getBeaconRange()) / 11.0F);
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
    float beaconDirection;
    float beaconRange;
    AnglesFork waypointAzimuth;
    float vspeed;
    private final CockpitG4M1_11 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();

      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitG4M1_11.1 arg2)
    {
      this();
    }
  }
}