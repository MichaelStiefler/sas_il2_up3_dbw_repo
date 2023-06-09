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

public class CockpitB25J extends CockpitPilot
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

  private B_25J1 b25J = null;

  private static final float[] speedometerScale = { 0.0F, 2.5F, 59.0F, 126.0F, 155.5F, 223.5F, 240.0F, 254.5F, 271.0F, 285.0F, 296.5F, 308.5F, 324.0F, 338.5F };

  private static final float[] variometerScale = { -180.0F, -157.0F, -130.0F, -108.0F, -84.0F, -46.5F, 0.0F, 46.5F, 84.0F, 108.0F, 130.0F, 157.0F, 180.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitB25J()
  {
    super("3DO/Cockpit/B-25J/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "texture01_dmg", "texture01", "texture02_dmg", "texture02", "texture03_dmg", "texture03", "texture04_dmg", "texture04", "texture05_dmg", "texture05", "texture06_dmg", "texture06", "texture21_dmg", "texture21", "texture25" };

    setNightMats(false);

    if ((aircraft() instanceof B_25J1))
    {
      this.b25J = ((B_25J1)aircraft());
    }

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Z_Column", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AroneL", 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 68.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AroneR", 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 68.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, -10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_RPedalStep", 0.0F, -10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LPedalStep", 0.0F, 10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_PedalWireR", 0.0F, 0.0F, -10.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_PedalWireL", 0.0F, 0.0F, 10.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("zFlaps1", 0.0F, 38.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.25F * this.fm.CT.FlapsControl), 0.0F);
    this.mesh.chunkSetAngles("zOilFlap1", 0.0F, -35.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);
    this.mesh.chunkSetAngles("zOilFlap2", 0.0F, -35.0F * this.fm.EI.engines[1].getControlRadiator(), 0.0F);
    this.mesh.chunkSetAngles("zMix1", 0.0F, -45.799999F * this.fm.EI.engines[0].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("zMix2", 0.0F, -45.799999F * this.fm.EI.engines[1].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("zPitch1", 0.0F, -54.0F * interp(this.setNew.prop1, this.setOld.prop1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zPitch2", 0.0F, -54.0F * interp(this.setNew.prop2, this.setOld.prop2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zThrottle1", 0.0F, -49.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zThrottle2", 0.0F, -49.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zCompressor1", 0.0F, cvt(this.fm.EI.engines[0].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50.0F), 0.0F);
    this.mesh.chunkSetAngles("zCompressor2", 0.0F, cvt(this.fm.EI.engines[1].getControlCompressor(), 0.0F, 1.0F, 0.0F, -50.0F), 0.0F);

    if (this.fm.Gears.cgear) {
      resetYPRmodifier();
      xyz[1] = cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 0.0135F);
      this.mesh.chunkSetLocate("Z_Gearc1", xyz, ypr);
    }
    if (this.fm.Gears.lgear) {
      resetYPRmodifier();
      xyz[1] = cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 0.0135F);
      this.mesh.chunkSetLocate("Z_GearL1", xyz, ypr);
    }
    if (this.fm.Gears.rgear) {
      resetYPRmodifier();
      xyz[1] = cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, 0.0135F);
      this.mesh.chunkSetLocate("Z_GearR1", xyz, ypr);
    }
    this.mesh.chunkSetAngles("zFlaps2", 0.0F, 90.0F * this.fm.CT.getFlap(), 0.0F);
    this.mesh.chunkSetAngles("zHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("zMinute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zSecond", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zAH1", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0365F, -0.0365F);
    this.mesh.chunkSetLocate("zAH2", xyz, ypr);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F);
    }
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("zTurnBank", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.mesh.chunkSetAngles("zBall", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -11.5F, 11.5F), 0.0F);

    this.mesh.chunkSetAngles("zPDI", 0.0F, cvt(this.setNew.PDI, -30.0F, 30.0F, -46.5F, 46.5F), 0.0F);

    this.mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zAlt1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);

    this.mesh.chunkSetAngles("zCompass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zCompass2", 0.0F, -0.5F * this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.mesh.chunkSetAngles("zCompass3", 0.0F, this.setNew.radioCompassAzimuth.getDeg(paramFloat * 0.02F), 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("zMagnetic", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("zNavP", 0.0F, this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F);
    }

    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("zRPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4500.0F, 0.0F, 323.0F), 0.0F);
      this.mesh.chunkSetAngles("zRPM2", 0.0F, cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4500.0F, 0.0F, 323.0F), 0.0F);
      this.mesh.chunkSetAngles("Z_Pres1", 0.0F, this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
      this.mesh.chunkSetAngles("Z_Pres2", 0.0F, this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.3386378F, 2.539784F, 0.0F, 346.5F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres2", 0.0F, cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Carbair1", 0.0F, -cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -50.0F, 150.0F, -25.0F, 75.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Carbair2", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -50.0F, 150.0F, -25.0F, 75.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, -cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 100.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", 0.0F, cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 350.0F, 0.0F, 100.0F), 0.0F);
    this.mesh.chunkSetAngles("zOilTemp1", 0.0F, -cvt(this.fm.EI.engines[0].tOilOut, -50.0F, 150.0F, -25.0F, 75.0F), 0.0F);
    this.mesh.chunkSetAngles("zOilTemp2", 0.0F, cvt(this.fm.EI.engines[1].tOilOut, -50.0F, 150.0F, -25.0F, 75.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 13.49F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres2", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 13.49F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Brkpres1", 0.0F, 118.0F * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("zFuel1", 0.0F, -cvt(this.fm.M.fuel, 0.0F, 2332.0F, 0.0F, 95.0F), 0.0F);
    this.mesh.chunkSetAngles("zFuel2", 0.0F, cvt(this.fm.M.fuel, 0.0F, 2332.0F, 0.0F, 95.0F), 0.0F);
    this.mesh.chunkSetAngles("zFuel3", 0.0F, -cvt(this.fm.M.fuel, 0.0F, 1916.0F, 0.0F, 90.5F), 0.0F);
    this.mesh.chunkSetAngles("zFuel4", 0.0F, cvt(this.fm.M.fuel, 0.0F, 1916.0F, 0.0F, 90.5F), 0.0F);
    this.mesh.chunkSetAngles("zFuel5", 0.0F, -cvt(this.fm.M.fuel, 1916.0F, 3084.0F, 0.0F, 102.5F), 0.0F);
    this.mesh.chunkSetAngles("zFuel6", 0.0F, cvt(this.fm.M.fuel, 1916.0F, 3084.0F, 0.0F, 102.5F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("zFreeAir", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -70.0F, 150.0F, -26.6F, 57.0F), 0.0F);
    }
    this.mesh.chunkSetAngles("zHydPres", 0.0F, this.fm.Gears.bIsHydroOperable ? 165.5F : 0.0F, 0.0F);
    float f = 0.5F * this.fm.EI.engines[0].getRPM() + 0.5F * this.fm.EI.engines[1].getRPM();
    f = 2.5F * (float)Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f))));
    this.mesh.chunkSetAngles("zSuction", 0.0F, cvt(f, 0.0F, 10.0F, 0.0F, 297.0F), 0.0F);

    this.mesh.chunkSetAngles("BL_Vert", cvt(this.setNew.ilsLoc, -3.5F, 3.5F, -40.0F, 40.0F), 0.0F, 0.0F);

    if (this.setNew.ilsGS >= 0.0F)
      this.mesh.chunkSetAngles("BL_Horiz", cvt(this.setNew.ilsGS, 0.0F, 0.5F, 0.0F, 40.0F), 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("BL_Horiz", cvt(this.setNew.ilsGS, -0.3F, 0.0F, -40.0F, 0.0F), 0.0F, 0.0F);
    if (this.fm.AS.isAAFIAS) {
      this.mesh.chunkVisible("BL_Light", isOnBlindLandingMarker());
    }

    this.mesh.chunkVisible("Z_GearRed1", (this.fm.CT.getGear() > 0.01F) && (this.fm.CT.getGear() < 0.99F));
    this.mesh.chunkVisible("Z_GearCGreen1", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.cgear));
    this.mesh.chunkVisible("Z_GearLGreen1", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearRGreen1", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x2) != 0)) {
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage", true);
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("zCompass3", false);
      this.mesh.chunkVisible("Z_FuelPres1", false);
      this.mesh.chunkVisible("Z_FuelPres2", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_Oilpres2", false);
      this.mesh.chunkVisible("zOilTemp1", false);
      this.mesh.chunkVisible("zOilTemp2", false);
      this.mesh.chunkVisible("Z_Brkpres1", false);
      this.mesh.chunkVisible("zHydPres", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
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
      if (CockpitB25J.this.fm != null) {
        CockpitB25J.access$102(CockpitB25J.this, CockpitB25J.this.setOld); CockpitB25J.access$202(CockpitB25J.this, CockpitB25J.this.setNew); CockpitB25J.access$302(CockpitB25J.this, CockpitB25J.this.setTmp);

        CockpitB25J.this.setNew.throttle1 = (0.85F * CockpitB25J.this.setOld.throttle1 + CockpitB25J.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitB25J.this.setNew.throttle2 = (0.85F * CockpitB25J.this.setOld.throttle2 + CockpitB25J.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitB25J.this.setNew.prop1 = (0.85F * CockpitB25J.this.setOld.prop1 + CockpitB25J.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitB25J.this.setNew.prop2 = (0.85F * CockpitB25J.this.setOld.prop2 + CockpitB25J.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitB25J.this.setNew.altimeter = CockpitB25J.this.fm.getAltitude();

        float f = CockpitB25J.this.waypointAzimuth();
        CockpitB25J.this.setNew.azimuth.setDeg(CockpitB25J.this.setOld.azimuth.getDeg(1.0F), CockpitB25J.this.fm.Or.azimut());

        if (CockpitB25J.this.useRealisticNavigationInstruments())
        {
          CockpitB25J.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitB25J.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
          CockpitB25J.this.setNew.radioCompassAzimuth.setDeg(CockpitB25J.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitB25J.this.radioCompassAzimuthInvertMinus() - CockpitB25J.this.setOld.azimuth.getDeg(1.0F) - 90.0F);

          if ((CockpitB25J.this.fm.AS.listenLorenzBlindLanding) && (CockpitB25J.this.fm.AS.isAAFIAS))
          {
            CockpitB25J.this.setNew.ilsLoc = ((10.0F * CockpitB25J.this.setOld.ilsLoc + CockpitB25J.this.getBeaconDirection()) / 11.0F);
            CockpitB25J.this.setNew.ilsGS = ((10.0F * CockpitB25J.this.setOld.ilsGS + CockpitB25J.this.getGlidePath()) / 11.0F);
          }
          else
          {
            CockpitB25J.this.setNew.ilsLoc = 0.0F;
            CockpitB25J.this.setNew.ilsGS = 0.0F;
          }
        }
        else
        {
          CockpitB25J.this.setNew.waypointAzimuth.setDeg(CockpitB25J.this.setOld.waypointAzimuth.getDeg(0.1F), f);
          CockpitB25J.this.setNew.radioCompassAzimuth.setDeg(CockpitB25J.this.setOld.radioCompassAzimuth.getDeg(0.1F), f - CockpitB25J.this.setOld.azimuth.getDeg(0.1F) - 90.0F);
        }
        if (CockpitB25J.this.b25J != null) {
          CockpitB25J.this.setNew.PDI = CockpitB25J.this.b25J.fSightCurSideslip;
        }
        CockpitB25J.this.setNew.vspeed = ((199.0F * CockpitB25J.this.setOld.vspeed + CockpitB25J.this.fm.getVertSpeed()) / 200.0F);
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
    AnglesFork radioCompassAzimuth;
    float vspeed;
    float PDI;
    float ilsLoc;
    float ilsGS;
    private final CockpitB25J this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitB25J.1 arg2)
    {
      this();
    }
  }
}