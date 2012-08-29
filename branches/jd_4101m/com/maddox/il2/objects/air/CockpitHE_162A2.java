package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;

public class CockpitHE_162A2 extends CockpitPilot
{
  private float tmp;
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictManifold = 0.0F;
  private Loc tmpL = new Loc();
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { -1.0F, 3.5F, 48.5F, 97.5F, 144.0F, 198.0F, 253.0F, 305.0F, 358.0F, 407.0F, 419.5F };

  private static final float[] vsiNeedleScale = { 0.0F, 48.0F, 82.0F, 96.5F, 111.0F, 120.5F, 130.0F, 130.0F };

  private static final float[] rpmScale = { 0.0F, 0.0F, 0.0F, 16.5F, 34.5F, 55.0F, 77.5F, 104.0F, 133.5F, 162.5F, 192.0F, 224.0F, 254.0F, 255.5F, 260.0F };

  public CockpitHE_162A2()
  {
    super("3DO/Cockpit/He-162A-2/hier.him", "bf109");
    this.setNew.dimPosition = 1.0F;

    this.cockpitNightMats = new String[] { "gauges1", "gauges2", "gauges3", "gauges4", "gauges5", "gauges6", "gauges1_d1", "gauges2_d1", "gauges3_d1", "gauges4_d1", "gauges5_d1", "gauges6_d1", "turnbank", "aiguill1", "Oxi_Press" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, -interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 58.0F);
    this.mesh.chunkSetAngles("sunOFF", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, -44.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Fuel_Tank", 0.0F, 0.0F, this.fm.EI.engines[0].getStage() == 0 ? 38.0F : 0.0F);
    this.mesh.chunkSetAngles("Landing_Gear", 0.0F, 0.0F, -24.0F + 24.0F * this.fm.CT.GearControl);
    this.mesh.chunkSetAngles("ElevatorCrank", 3600.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = (0.1F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("PedalL", xyz, ypr);
    xyz[1] = (-xyz[1]);
    this.mesh.chunkSetLocate("PedalR", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1000.0F, 0.0F, 10.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer2", floatindex(cvt(this.fm.getSpeedKMH(), 0.0F, 1000.0F, 0.0F, 10.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM", floatindex(cvt(this.fm.EI.engines[0].getRPM() * 10.0F * 0.25F, 2000.0F, 14000.0F, 2.0F, 14.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_FuelContent", cvt(0.6F * this.fm.EI.engines[0].getPowerOutput(), 0.0F, 0.92F, 0.0F, 272.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_GasTemp", cvt(this.fm.EI.engines[0].tWaterOut, 300.0F, 1000.0F, 0.0F, 83.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPressure", cvt(1.0F + 0.005F * this.fm.EI.engines[0].tOilOut, 0.0F, 3.2F, 0.0F, 278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPressure", cvt(this.fm.M.fuel, 0.0F, 500.0F, 0.0F, 77.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", this.setNew.vspeed >= 0.0F ? floatindex(cvt(this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale) : -floatindex(cvt(-this.setNew.vspeed, 0.0F, 30.0F, 0.0F, 6.0F), vsiNeedleScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("z_Slide1a", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -13.5F, 13.5F), 0.0F);

    if (aircraft().isFMTrackMirror()) {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -6.0F, 6.0F, 24.0F, -24.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.mesh.chunkSetAngles("Z_Azimuth1", f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCompassOil1", cvt(this.fm.Or.getTangage(), -30.0F, 30.0F, -30.0F, 30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zCompassOil3", cvt(this.fm.Or.getKren(), -45.0F, 45.0F, -45.0F, 45.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zCompassOil2", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    aircraft().hierMesh().setCurChunk("GearC1_D0");
    aircraft().hierMesh().getChunkLocObj(this.tmpL);
    this.mesh.chunkSetAngles("GearC1_D0", 0.0F, this.tmpL.getOrient().getTangage(), 0.0F);
    this.mesh.chunkSetAngles("GearC2_D0", 0.0F, 120.0F * this.fm.CT.getGear(), 0.0F);
    float f = Math.max(-this.fm.CT.getGear() * 1500.0F, -110.0F);
    this.mesh.chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);

    resetYPRmodifier();
    if (aircraft().FM.CT.getGear() > 0.99F) {
      xyz[1] = cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
      ypr[1] = (40.0F * aircraft().FM.CT.getRudder());
      this.mesh.chunkSetLocate("GearC25_D0", xyz, ypr);
      this.mesh.chunkSetAngles("GearC27_D0", 0.0F, cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15.0F), 0.0F);
      this.mesh.chunkSetAngles("GearC28_D0", 0.0F, cvt(aircraft().FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30.0F), 0.0F);
    } else {
      this.mesh.chunkSetAngles("GearC25_D0", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("GearC27_D0", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("GearC28_D0", 0.0F, 0.0F, 0.0F);
    }

    if (this.fm.CT.Weapons[0] != null) {
      resetYPRmodifier();
      xyz[1] = cvt(this.fm.CT.Weapons[0][0].countBullets(), 0.0F, 120.0F, 0.0F, 0.0415F);
      this.mesh.chunkSetLocate("Zammo_counter1", xyz, ypr);
      xyz[1] = cvt(this.fm.CT.Weapons[0][1].countBullets(), 0.0F, 120.0F, 0.0F, 0.0415F);
      this.mesh.chunkSetLocate("Zammo_counter2", xyz, ypr);
    }

    this.mesh.chunkSetAngles("Z_AFN1", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AFN2", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
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
    {
      setNightMats(false);
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("pribors1", false);
      this.mesh.chunkVisible("pribors1_d1", true);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_GasTemp", false);
      this.mesh.chunkVisible("Z_Hour1", false);
      this.mesh.chunkVisible("Z_Minute1", false);
      this.mesh.chunkVisible("Z_Second1", false);
      this.mesh.chunkVisible("Z_OilPressure", false);
      this.mesh.chunkVisible("Z_FuelContent", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("pribors2", false);
      this.mesh.chunkVisible("pribors2_d1", true);
      this.mesh.chunkVisible("z_Slide1a", false);
      this.mesh.chunkVisible("Z_Azimuth1", false);
      this.mesh.chunkVisible("Z_FuelPressure", false);
      this.mesh.chunkVisible("Z_RPM", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x80) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0))));
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitHE_162A2.this.bNeedSetUp) {
        CockpitHE_162A2.this.reflectPlaneMats();
        CockpitHE_162A2.access$102(CockpitHE_162A2.this, false);
      }

      CockpitHE_162A2.access$202(CockpitHE_162A2.this, CockpitHE_162A2.this.setOld); CockpitHE_162A2.access$302(CockpitHE_162A2.this, CockpitHE_162A2.this.setNew); CockpitHE_162A2.access$402(CockpitHE_162A2.this, CockpitHE_162A2.this.setTmp);

      CockpitHE_162A2.this.setNew.vspeed = ((299.0F * CockpitHE_162A2.this.setOld.vspeed + CockpitHE_162A2.this.fm.getVertSpeed()) / 300.0F);
      CockpitHE_162A2.this.setNew.altimeter = CockpitHE_162A2.this.fm.getAltitude();
      if (CockpitHE_162A2.this.cockpitDimControl) {
        if (CockpitHE_162A2.this.setNew.dimPosition > 0.0F) CockpitHE_162A2.this.setNew.dimPosition = (CockpitHE_162A2.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitHE_162A2.this.setNew.dimPosition < 1.0F) CockpitHE_162A2.this.setNew.dimPosition = (CockpitHE_162A2.this.setOld.dimPosition + 0.05F);

      CockpitHE_162A2.this.setNew.throttle = ((10.0F * CockpitHE_162A2.this.setOld.throttle + CockpitHE_162A2.this.fm.CT.PowerControl) / 11.0F);
      CockpitHE_162A2.this.setNew.mix = ((8.0F * CockpitHE_162A2.this.setOld.mix + CockpitHE_162A2.this.fm.EI.engines[0].getControlMix()) / 9.0F);
      CockpitHE_162A2.this.setNew.azimuth = CockpitHE_162A2.this.fm.Or.getYaw();
      if ((CockpitHE_162A2.this.setOld.azimuth > 270.0F) && (CockpitHE_162A2.this.setNew.azimuth < 90.0F)) CockpitHE_162A2.this.setOld.azimuth -= 360.0F;
      if ((CockpitHE_162A2.this.setOld.azimuth < 90.0F) && (CockpitHE_162A2.this.setNew.azimuth > 270.0F)) CockpitHE_162A2.this.setOld.azimuth += 360.0F;

      CockpitHE_162A2.this.setNew.beaconDirection = ((10.0F * CockpitHE_162A2.this.setOld.beaconDirection + CockpitHE_162A2.this.getBeaconDirection()) / 11.0F);
      CockpitHE_162A2.this.setNew.beaconRange = ((10.0F * CockpitHE_162A2.this.setOld.beaconRange + CockpitHE_162A2.this.getBeaconRange()) / 11.0F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    float azimuth;
    float beaconDirection;
    float beaconRange;
    float mix;
    float vspeed;
    private final CockpitHE_162A2 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitHE_162A2.1 arg2) { this();
    }
  }
}