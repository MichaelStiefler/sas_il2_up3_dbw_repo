// Source File Name: CockpitF_86Flate.java
// Author:           
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Time;
import com.maddox.il2.fm.FlightModelMain;

public class CockpitF_86Flate extends CockpitPilot {

  class Interpolater extends InterpolateRef {

    public boolean tick() {
      if (fm != null) {
        setTmp = setOld;
        setOld = setNew;
        setNew = setTmp;
        setNew.throttle = 0.85F * setOld.throttle
                + ((FlightModelMain) (fm)).CT.PowerControl * 0.15F;
        setNew.prop = 0.85F * setOld.prop
                + ((FlightModelMain) (fm)).CT.getStepControl() * 0.15F;
        setNew.stage = 0.85F
                * setOld.stage
                + (float) ((FlightModelMain) (fm)).EI.engines[0].getControlCompressor() * 0.15F;
        setNew.mix = 0.85F
                * setOld.mix
                + ((FlightModelMain) (fm)).EI.engines[0].getControlMix() * 0.15F;
        setNew.altimeter = fm.getAltitude();
        float a = waypointAzimuth();
        if (useRealisticNavigationInstruments()) {
          setNew.waypointAzimuth.setDeg(a - 90F);
          setOld.waypointAzimuth.setDeg(a - 90F);
        } else {
          setNew.waypointAzimuth.setDeg(setOld.waypointAzimuth.getDeg(0.1F), a - setOld.azimuth.getDeg(1.0F));
        }
        setNew.azimuth.setDeg(setOld.azimuth.getDeg(1.0F), fm.Or.azimut());
        setNew.vspeed = (199F * setOld.vspeed + fm.getVertSpeed()) / 200F;
        float f = ((F_86F) aircraft()).k14Distance;
        setNew.k14w = (5F * CockpitF_86Flate.k14TargetWingspanScale[((F_86F) aircraft()).k14WingspanType])
                / f;
        setNew.k14w = 0.9F * setOld.k14w + 0.1F * setNew.k14w;
        setNew.k14wingspan = 0.9F
                * setOld.k14wingspan
                + 0.1F
                * CockpitF_86Flate.k14TargetMarkScale[((F_86F) aircraft()).k14WingspanType];
        setNew.k14mode = 0.8F * setOld.k14mode + 0.2F
                * (float) ((F_86F) aircraft()).k14Mode;
        Vector3d vector3d = ((SndAircraft) (aircraft())).FM.getW();
        double d = 0.00125D * (double) f;
        float f1 = (float) Math.toDegrees(d * ((Tuple3d) (vector3d)).z);
        float f2 = -(float) Math.toDegrees(d * ((Tuple3d) (vector3d)).y);
        float f3 = floatindex((f - 200F) * 0.04F,
                CockpitF_86Flate.k14BulletDrop)
                - CockpitF_86Flate.k14BulletDrop[0];
        f2 += (float) Math.toDegrees(Math.atan(f3 / f));
        setNew.k14x = 0.92F * setOld.k14x + 0.08F * f1;
        setNew.k14y = 0.92F * setOld.k14y + 0.08F * f2;
        if (setNew.k14x > 7F) {
          setNew.k14x = 7F;
        }
        if (setNew.k14x < -7F) {
          setNew.k14x = -7F;
        }
        if (setNew.k14y > 7F) {
          setNew.k14y = 7F;
        }
        if (setNew.k14y < -7F) {
          setNew.k14y = -7F;
        }
        buzzerFX(fm.getSpeed() < 63F
                && ((FlightModelMain) (fm)).Gears.nOfGearsOnGr < 1);
        setNew.beaconDirection = (10F * setOld.beaconDirection + getBeaconDirection()) / 11F;
        setNew.beaconRange = (10F * setOld.beaconRange + getBeaconRange()) / 11F;
        if (fm.EI.engines[0].getStage() < 6) {
          if (setNew.stbyPosition > 0.0F) {
            setNew.stbyPosition = setOld.stbyPosition - 0.002F;
          }
        } else if (setNew.stbyPosition < 1.0F) {
          setNew.stbyPosition = setOld.stbyPosition + 0.002F;
        }
        if (setNew.stbyPosition2 < 1.0F) {
          setNew.stbyPosition2 = setOld.stbyPosition2 + 0.002F;
        }
      }
      return true;
    }

    Interpolater() {
    }
  }

  private class Variables {

    float throttle;
    float prop;
    float mix;
    float stage;
    float altimeter;
    float vspeed;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float beaconDirection;
    float beaconRange;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    float stbyPosition;
    float stbyPosition2;

    private Variables() {
      azimuth = new AnglesFork();
      waypointAzimuth = new AnglesFork();
    }
  }

  protected float waypointAzimuth() {
    return waypointAzimuthInvertMinus(30F);
  }

  public CockpitF_86Flate() {
    super("3DO/Cockpit/F-86F/F-86.him", "bf109");
    setOld = new Variables();
    setNew = new Variables();
    w = new Vector3f();
    pictAiler = 0.0F;
    pictElev = 0.0F;
    super.cockpitNightMats = (new String[]{"Sabre_Compass1", "Needles86",
              "Gaug86_01", "Gaug86_02", "Gaug86_03", "Gaug86_04",
              "Gaug86_05", "Gaug86_06"});
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    loadBuzzerFX();
  }

  private int iLockState() {
    if (!(super.aircraft() instanceof TypeGuidedMissileCarrier)) {
      return 0;
    }
    return ((TypeGuidedMissileCarrier) super.aircraft()).getGuidedMissileUtils().getMissileLockState();
  }

  private float machNumber() {
    return ((F_86F) super.aircraft()).calculateMach();
  }

  public void reflectWorldToInstruments(float f) {
    if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 2) == 0) {
      if (F_86F.hunted != null) {
        super.mesh.chunkVisible("Z_RadarTarget", true);
      } else {
        super.mesh.chunkVisible("Z_RadarTarget", false);
      }

      int i = ((F_86F) aircraft()).k14Mode;
      super.mesh.chunkVisible("Z_Z_RETICLE", false);
      boolean flag = i < 2;
      {
        super.mesh.chunkVisible("Z_CollimateurLamp", true);
        super.mesh.chunkVisible("Z_GunLamp", true);
        super.mesh.chunkVisible("Colli01", false);
        super.mesh.chunkVisible("Colli02", true);
        super.mesh.chunkVisible("Z_Z_RETICLE1", flag);
        super.mesh.chunkVisible("Gun01", false);
        super.mesh.chunkVisible("Gun02", true);
        super.mesh.chunkVisible("Contact02a", false);
        super.mesh.chunkVisible("Contact02b", true);
        resetYPRmodifier();
        Cockpit.xyz[0] = setNew.k14w;
        for (int j = 1; j < 11; j++) {
          super.mesh.chunkVisible("Z_Z_AIMMARK" + j, flag);
          super.mesh.chunkSetLocate("Z_Z_AIMMARK" + j, Cockpit.xyz,
                  Cockpit.ypr);
        }
        if (i == 1) {
          super.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F,
                  setNew.k14x, setNew.k14y);
          super.mesh.chunkVisible("Z_CageSelect1", false);
          super.mesh.chunkVisible("Z_CageSelect2", true);
        }
        if (i == 0) {
          super.mesh.chunkSetAngles("Z_Z_RETICLE1", 0.0F, 0.0F, 0.0F);
          super.mesh.chunkVisible("Z_CageSelect1", true);
          super.mesh.chunkVisible("Z_CageSelect2", false);
        }
      }
      if (!flag) {
        super.mesh.chunkVisible("Z_SightCaged", false);
        super.mesh.chunkVisible("Z_SightUncaged", false);
        super.mesh.chunkVisible("Z_CollimateurLamp", false);
        super.mesh.chunkVisible("Z_GunLamp", false);
        mesh.chunkVisible("Colli01", true);
        mesh.chunkVisible("Colli02", false);
        super.mesh.chunkVisible("Gun01", true);
        super.mesh.chunkVisible("Gun02", false);
        super.mesh.chunkVisible("Contact02a", true);
        super.mesh.chunkVisible("Contact02b", false);
      }
    }
    mesh.chunkSetAngles("Z_PedalRight", 10F * fm.CT.getRudder(), 0.0F,
            0.0F);
    mesh.chunkSetAngles("Z_PedalLeft", -10F * fm.CT.getRudder(), 0.0F,
            0.0F);
    mesh.chunkSetAngles("Z_Throtle86", 50F * setNew.throttle, 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Flaps1", -62F * fm.CT.FlapsControl, 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_AirBrakes", 29F * fm.CT.AirBrakeControl, 0.0F,
            0.0F);
    mesh.chunkSetAngles("Z_Hydro-Pres86", 0.0F, cvt(interp(setNew.stbyPosition, setOld.stbyPosition, f), 0.0F, 1.0F, -253F, 10F), 0.0F);
    mesh.chunkSetAngles("Z_Ammeter86", 0.0F, cvt(interp(setNew.stbyPosition2, setOld.stbyPosition2, f), 0.0F, 1.0F, -55F, 30F), 0.0F);
    resetYPRmodifier();
    float f1 = fm.EI.engines[0].getStage();
    if (f1 > 0.0F && f1 < 7F) {
      f1 = 0.0345F;
    } else {
      f1 = -0.05475F;
    }
    Cockpit.xyz[2] = f1;
    mesh.chunkSetAngles("Z_Stick01", (pictElev = 0.7F * pictElev + 0.15F
            * fm.CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F
            * pictAiler + 0.15F * fm.CT.AileronControl) * 15F);
    mesh.chunkSetAngles("Z_Stick02", (pictElev = 0.7F * pictElev + 0.15F
            * fm.CT.ElevatorControl) * 16F, 0.0F, -(pictAiler = 0.85F
            * pictAiler + 0.15F * fm.CT.AileronControl) * 15F);
    resetYPRmodifier();
    if (fm.CT.saveWeaponControl[0]) {
      Cockpit.xyz[2] = -0.0029F;
    }
    mesh.chunkSetAngles("Z_Target1", -1.2F * setNew.k14wingspan, 0.0F,
            0.0F);
    if (machNumber() < 0.95F || machNumber() > 1.0F) {
      mesh.chunkSetAngles("Z_Speedometer86-1", floatindex(cvt(Pitot.Indicator((float) fm.Loc.z, fm.getSpeedKMH()) * 1.131F, 74.07994F,
              1222.31894F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Speedometer86-2", floatindex(cvt(((machNumber() * 1.4F) - 0.4F), 0.3F, 1.5F,
              0.0F, 14F), speedometerScale), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Speedometer86-3", floatindex(cvt(fm.getSpeedKMH() * 1.131F, 74.07994F,
              1222.31894F, 0.0F, 14F), speedometerScale), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Climb86", floatindex(cvt(setNew.vspeed, -30.48F,
              30.48F, 0.0F, 12F), variometerScale), 0.0F, 0.0F);
      Cockpit.xyz[1] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0362F, -0.0362F);
      mesh.chunkSetAngles("Z_Altimeter86_1", cvt(interp(setNew.altimeter,
              setOld.altimeter, f), 0.0F, 30480F, 0.0F, 36000F), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Altimeter86_2", cvt(interp(setNew.altimeter,
              setOld.altimeter, f), 0.0F, 30480F, 0.0F, 3600F), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Altimeter86_3", cvt(interp(setNew.altimeter,
              setOld.altimeter, f), 0.0F, 30480F, 0.0F, 360F), 0.0F, 0.0F);
      mesh.chunkSetAngles("Z_Altimeter86_4", cvt(interp(setNew.altimeter,
              setOld.altimeter, f), 0.0F, 30480F, -180F, 145F), 0.0F, 0.0F);
      resetYPRmodifier();
    }
    mesh.chunkSetAngles("Z_Hour86", cvt(World.getTimeofDay(), 0.0F, 24F,
            0.0F, 720F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Minute86", cvt(World.getTimeofDay() % 1.0F,
            0.0F, 1.0F, 0.0F, 360F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Second86", cvt(
            ((World.getTimeofDay() % 1.0F) * 60F) % 1.0F, 0.0F, 1.0F, 0.0F,
            360F), 0.0F, 0.0F);
    f1 = fm.EI.engines[0].getPowerOutput();
    mesh.chunkSetAngles("Z_Fuel-Pres86", cvt((float) Math.sqrt(f1), 0.0F,
            1.0F, -155F, 155F), 0.0F, 0.0F);
    f1 = cvt(fm.M.fuel, 0.0F, 1000F, 0.0F, 270F);
    if (f1 < 45F) {
      f1 = cvt(f1, 0.0F, 45F, -58F, 45F);
    }
    f1 += 58F;
    mesh.chunkSetAngles("Z_Oilpres86", cvt(1.0F + 0.05F
            * fm.EI.engines[0].tOilOut * fm.EI.engines[0].getReadyness(),
            0.0F, 18F, 115F, 410F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_RPM86-1", cvt(fm.EI.engines[0].getRPM(), 0.0F,
            2550F, -110F, 180F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_RPM86-2", cvt(fm.EI.engines[0].getRPM() * 0.955F, 2550F,
            3500F, -10F, 335F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Suction86", cvt(fm.EI.engines[0].getRPM(), 0.0F,
            3500F, 100F, 395F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Exhaust_Temp", cvt(fm.EI.engines[0].tOilOut,
            20F, 95F, -150F, -25F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Compass-Emerg1", cvt(fm.Or.getTangage(), -30F,
            30F, -30F, 30F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Compass-Emerg3", cvt(fm.Or.getKren(), -45F, 45F,
            -45F, 45F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_Compass-Emerg2", setNew.azimuth.getDeg(f), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_TurnBank86-1", cvt(w.z, -0.23562F, 0.23562F,
            22.5F, -22.5F), 0.0F, 0.0F);
    w.set(fm.getW());
    fm.Or.transform(w);
    mesh.chunkSetAngles("Z_Fuel86",
            cvt(fm.M.fuel, 0.0F, 1650F, -150F, 150F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_TurnBank86-2", cvt(getBall(7D), -7F, 7F, -15F,
            15F), 0.0F, 0.0F);
    mesh.chunkSetAngles("Z_G-Factor", cvt(fm.getOverload(), -4F, 12F,
            -80.5F, 241.5F), 0.0F, 0.0F);
    mesh.chunkVisible("Z_Gear86Green1", fm.CT.getGear() > 0.95F);
    mesh.chunkVisible("Z_Gear86Green2", fm.CT.getGear() > 0.95F);
    mesh.chunkVisible("Z_Gear86Green3", fm.CT.getGear() > 0.95F);
    mesh.chunkVisible("Z_Gear86Red1", fm.CT.getGear() < 0.05F
            || !fm.Gears.lgear || !fm.Gears.rgear);
    mesh.chunkVisible("Z_Gear86Red2", fm.CT.getGear() < 0.05F
            || !fm.Gears.lgear || !fm.Gears.rgear);
    mesh.chunkVisible("Z_Gear86Red3", fm.CT.getGear() < 0.05F
            || !fm.Gears.lgear || !fm.Gears.rgear);
    mesh.chunkVisible("Z_FuelLamp", fm.M.fuel < 200F);
    if (fm.EI.engines[0].getRPM() > 50F) {
      mesh.chunkVisible("Z_TrimLamp", Math.abs(fm.CT.getTrimElevatorControl()) < 0.05F);
    }
    if (fm.EI.engines[0].getRPM() < 50F) {
      mesh.chunkVisible("Z_TrimLamp", false);
    }
    if (fm.Gears.nOfGearsOnGr < 3) {
      mesh.chunkVisible("Z_TrimLamp", false);
    }
    mesh.chunkVisible("Z_FireLamp1", fm.AS.astateEngineStates[0] > 2);
    mesh.chunkVisible("Z_FireLamp2", fm.AS.astateEngineStates[0] > 2);
    mesh.chunkSetAngles("Z_horizont1a", 0.0F, -fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(fm.Or.getTangage(), -45F, 45F, 0.0328F, -0.0328F);
    mesh.chunkSetLocate("Z_horizont1b", Cockpit.xyz, Cockpit.ypr);

    if (useRealisticNavigationInstruments()) {
      mesh.chunkSetAngles("Z_Azimuth86_2",
              (setNew.azimuth.getDeg(f) - 270) + setNew.beaconDirection,
              0.0F, 0.0F);
    } else {
      mesh.chunkSetAngles("Z_Azimuth86_2", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);
    }
    mesh.chunkSetAngles("Z_Compass86_1", 0.0F, -setNew.azimuth.getDeg(f),
            0.0F);
    mesh.chunkSetAngles("Z_Azimuth86_1", setNew.waypointAzimuth.getDeg(f * 0.1F), 0.0F, 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(f1, 0.01F, 0.08F, 0.0F, 0.1F);
    super.mesh.chunkSetAngles("Z_GearHandle", 0.0F, 0.0F,
            50F * (pictGear = 0.82F * pictGear + 0.18F
            * ((FlightModelMain) (super.fm)).CT.GearControl));
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(
            ((FlightModelMain) (super.fm)).CT.getCockpitDoor(), 0.01F,
            0.99F, -0.625F, 0.0F);
    super.mesh.chunkSetLocate("Canopy-Open1", Cockpit.xyz, Cockpit.ypr);
    super.mesh.chunkSetLocate("Canopy-Open2", Cockpit.xyz, Cockpit.ypr);
    super.mesh.chunkSetLocate("Canopy-Open3", Cockpit.xyz, Cockpit.ypr);
    super.mesh.chunkSetLocate("Canopy-Open4", Cockpit.xyz, Cockpit.ypr);
    super.mesh.chunkSetLocate("Canopy-Open5", Cockpit.xyz, Cockpit.ypr);
    super.mesh.chunkSetLocate("XGlassDamage4", Cockpit.xyz, Cockpit.ypr);
    if (((FlightModelMain) (super.fm)).AS.bIsAboutToBailout) {
      super.mesh.chunkVisible("Canopy-Open1", false);
      super.mesh.chunkVisible("Canopy-Open2", false);
      super.mesh.chunkVisible("Canopy-Open3", false);
      super.mesh.chunkVisible("Canopy-Open4", false);
      super.mesh.chunkVisible("Canopy-Open5", false);
    }
    if (fm.Gears.nOfGearsOnGr > 0) {
      mesh.chunkVisible("ContactHydro1", true);
      mesh.chunkVisible("ContactHydro2", false);
    } else if (fm.Gears.nOfGearsOnGr == 0) {
      if (fm.EI.engines[0].getStage() < 6) {
        mesh.chunkVisible("ContactHydro1", false);
        mesh.chunkVisible("ContactHydro2", true);

      } else if (fm.EI.engines[0].getStage() > 0) {
        mesh.chunkVisible("ContactHydro1", true);
        mesh.chunkVisible("ContactHydro2", false);

      }
    }
    if (fm.EI.isSelectionHasControlExtinguisher()) {
      mesh.chunkVisible("Extinguisher1", true);
      mesh.chunkVisible("Extinguisher2", false);
    }
    if (!fm.EI.isSelectionHasControlExtinguisher()) {
      mesh.chunkVisible("Extinguisher1", false);
      mesh.chunkVisible("Extinguisher2", true);
    }
    if (fm.CT.cockpitDoorControl == 0.0F) {
      mesh.chunkVisible("Canopy_Contact2", false);
      mesh.chunkVisible("Canopy_Contact1", true);
    }
    if (fm.CT.cockpitDoorControl == 1.0F) {
      mesh.chunkVisible("Canopy_Contact1", false);
      mesh.chunkVisible("Canopy_Contact2", true);
    }
    if (fm.actor instanceof TypeGuidedMissileCarrier) {
      mesh.chunkVisible("MissileControl", true);
    } else {
      mesh.chunkVisible("MissileControl", false);
    }
    if (fm.actor instanceof TypeGuidedMissileCarrier) {
      if (iLockState() == 2) {
        mesh.chunkVisible("Z_Locked", true);
      } else {
        mesh.chunkVisible("Z_Locked", false);
      }
      if (fm.getOverload() > 2.0F || fm.getOverload() < 0.0F) {
        mesh.chunkVisible("Z_OverG", true);
      } else {
        mesh.chunkVisible("Z_OverG", false);
      }
    }
  }

  public void reflectCockpitState() {
    if ((((FlightModelMain) (super.fm)).AS.astateCockpitState & 2) != 0) {
      super.mesh.chunkVisible("XGlassDamage2", true);
      super.mesh.chunkVisible("Glass_Dam", true);
      super.mesh.chunkVisible("Z_Z_RETICLE", false);
      super.mesh.chunkVisible("Z_Z_RETICLE1", false);
      for (int i = 1; i < 11; i++) {
        super.mesh.chunkVisible("Z_Z_AIMMARK" + i, false);
      }

      mesh.chunkVisible("Gaug86_02", false);
      mesh.chunkVisible("Gaug86_02_Dam", true);
      mesh.chunkVisible("Gaug86_03", false);
      mesh.chunkVisible("Gaug86_03_Dam", true);
      mesh.chunkVisible("XGlassDamage3", true);
      mesh.chunkVisible("Z_InstrLamp", true);
      mesh.chunkVisible("Z_Speedometer86-1", false);
      mesh.chunkVisible("Z_Speedometer86-3", false);
      mesh.chunkVisible("Z_Altimeter86_1", false);
      mesh.chunkVisible("Z_Altimeter86_2", false);
      mesh.chunkVisible("Z_Altimeter86_3", false);
      mesh.chunkVisible("Z_Altimeter86_4", false);
      mesh.chunkVisible("Z_Compass86_1", false);
      mesh.chunkVisible("Z_Azimuth86_1", false);
      mesh.chunkVisible("Z_Azimuth86_2", false);
      mesh.chunkVisible("Z_horizont1b", false);
      mesh.chunkVisible("Z_Compass-Emerg1", false);
      mesh.chunkVisible("Z_Compass-Emerg3", false);
      mesh.chunkVisible("Z_Compass-Emerg2", false);
      mesh.chunkVisible("Z_horizont1a", false);
    }
    if ((fm.AS.astateCockpitState & 1) != 0) {
      mesh.chunkVisible("XGlassDamage1", true);
      mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((fm.AS.astateCockpitState & 0x40) != 0) {
      mesh.chunkVisible("Gaug86_02", false);
      mesh.chunkVisible("Gaug86_02_Dam", true);
      mesh.chunkVisible("Gaug86_03", false);
      mesh.chunkVisible("Gaug86_03_Dam", true);
      mesh.chunkVisible("XGlassDamage3", true);
      mesh.chunkVisible("Z_InstrLamp", true);
      mesh.chunkVisible("Z_Speedometer86-1", false);
      mesh.chunkVisible("Z_Speedometer86-3", false);
      mesh.chunkVisible("Z_Altimeter86_1", false);
      mesh.chunkVisible("Z_Altimeter86_2", false);
      mesh.chunkVisible("Z_Altimeter86_3", false);
      mesh.chunkVisible("Z_Altimeter86_4", false);
      mesh.chunkVisible("Z_Compass86_1", false);
      mesh.chunkVisible("Z_Azimuth86_1", false);
      mesh.chunkVisible("Z_Azimuth86_2", false);
      mesh.chunkVisible("Z_horizont1b", false);
      mesh.chunkVisible("Z_Compass-Emerg1", false);
      mesh.chunkVisible("Z_Compass-Emerg3", false);
      mesh.chunkVisible("Z_Compass-Emerg2", false);
      mesh.chunkVisible("Z_horizont1a", false);
    }
    if ((fm.AS.astateCockpitState & 4) != 0) {
      mesh.chunkVisible("XGlassDamage3", true);
      mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((fm.AS.astateCockpitState & 8) != 0) {
      mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((fm.AS.astateCockpitState & 0x80) == 0)
			;
    if ((fm.AS.astateCockpitState & 0x10) != 0) {
      mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((fm.AS.astateCockpitState & 0x20) != 0) {
      mesh.chunkVisible("XGlasslDamage2", true);
      mesh.chunkVisible("XGlassDamage4", true);
    }
    retoggleLight();
  }

  public void toggleLight() {
    super.cockpitLightControl = !super.cockpitLightControl;
    if (super.cockpitLightControl) {
      setNightMats(true);
    } else {
      setNightMats(false);
    }
  }

  private void retoggleLight() {
    if (super.cockpitLightControl) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
      setNightMats(false);
    }
  }
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictGear;
  private static final float speedometerScale[] = {0.0F, 22.5F, 45.0F, 67.5F,
    90F, 112.5F, 135F, 157.5F, 180F, 202.5F, 225F, 247.5F, 270F, 292.5F,
    315F, 336.5F, 360F};
  private static final float variometerScale[] = {-170F, -147F, -124F,
    -101F, -78F, -48F, 0.0F, 48F, 78F, 101F, 124F, 147F, 170F};
  private static final float k14TargetMarkScale[] = {0.0F, -4.5F, -27.5F,
    -42.5F, -56.5F, -61.5F, -70F, -95F, -102.5F, -106F};
  private static final float k14TargetWingspanScale[] = {9.9F, 10.52F,
    13.8F, 16.34F, 19F, 20F, 22F, 29.25F, 30F, 32.85F};
  private static final float k14BulletDrop[] = {5.812F, 6.168F, 6.508F,
    6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F,
    9.111F, 9.384F, 9.554F, 9.787F, 9.928F, 9.992F, 10.282F, 10.381F,
    10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F};
}