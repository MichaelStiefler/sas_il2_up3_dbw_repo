package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
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

public class CockpitP_38L extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 18.5F, 62.0F, 107.0F, 152.5F, 198.5F, 238.5F, 252.0F, 265.0F, 278.5F, 292.0F, 305.5F, 319.0F, 331.5F, 343.0F };

  private static final float[] variometerScale = { -170.0F, -147.0F, -124.0F, -101.0F, -78.0F, -48.0F, 0.0F, 48.0F, 78.0F, 101.0F, 124.0F, 147.0F, 170.0F };

  private static final float[] k14TargetMarkScale = { -0.0F, -4.5F, -27.5F, -42.5F, -56.5F, -61.5F, -70.0F, -95.0F, -102.5F, -106.0F };

  private static final float[] k14TargetWingspanScale = { 9.9F, 10.52F, 13.8F, 16.34F, 19.0F, 20.0F, 22.0F, 29.25F, 30.0F, 32.849998F };

  private static final float[] k14BulletDrop = { 5.812F, 6.168F, 6.508F, 6.978F, 7.24F, 7.576F, 7.849F, 8.108F, 8.473F, 8.699F, 8.911F, 9.111F, 9.384F, 9.554F, 9.787F, 9.928001F, 9.992F, 10.282F, 10.381F, 10.513F, 10.603F, 10.704F, 10.739F, 10.782F, 10.789F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitP_38L()
  {
    super("3DO/Cockpit/P-38L/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauges1", "gauges1_dam", "gauges2", "gauges2_dam", "gauges3", "gauges3_dam", "gauges4", "swbox", "swbox2" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("BMR_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("BMR_D0", aircraft().hierMesh().isChunkVisible("Blister1_D0"));
    super.doFocusLeave();
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    if (((P_38L)aircraft()).bChangedPit) {
      reflectPlaneToModel();
      ((P_38L)aircraft()).bChangedPit = false;
    }

    this.mesh.chunkSetAngles("Z_Trim1", -1722.0F * this.setNew.trim, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Flaps1", 60.0F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.fm.CT.FlapsControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 90.0F * (this.pictGear = 0.85F * this.pictGear + 0.15F * this.fm.CT.GearControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 93.099998F * this.setNew.throttle1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle2", 93.099998F * this.setNew.throttle2, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 95.0F * this.setNew.prop1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop2", 95.0F * this.setNew.prop2, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 90.0F * this.setNew.mix1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture2", 90.0F * this.setNew.mix2, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 0.0F, 16.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_RPedalStep", 0.0F, 0.0F, 16.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 0.0F, -16.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_LPedalStep", 0.0F, 0.0F, -16.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 70.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[0] != 0) {
      xyz[2] = 0.01065F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton1", xyz, ypr);
    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[1] != 0) {
      xyz[2] = 0.01065F;
    }
    this.mesh.chunkSetLocate("Z_Columnbutton2", xyz, ypr);
    this.mesh.chunkSetAngles("Priceltarg1", this.setNew.k14wingspan, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 36000.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 1126.5409F, 0.0F, 14.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 90.0F + this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);

    float f = 0.0F;
    if (this.fm.AS.bLandingLightOn) {
      f -= 35.0F;
    }
    if (this.fm.AS.bNavLightsOn) {
      f -= 5.0F;
    }
    if (this.cockpitLightControl) {
      f -= 2.87F;
    }
    this.mesh.chunkSetAngles("Z_Amper1", cvt(f + cvt(this.fm.EI.engines[0].getRPM(), 150.0F, 2380.0F, 0.0F, 41.099998F), -20.0F, 130.0F, -11.5F, 81.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Amper2", cvt(f + cvt(this.fm.EI.engines[1].getRPM(), 150.0F, 2380.0F, 0.0F, 41.099998F), -20.0F, 130.0F, -11.5F, 81.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Carbair1", cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F + 25.0F * this.fm.EI.engines[0].getPowerOutput(), -70.0F, 150.0F, -38.5F, 87.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Carbair2", cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F + 25.0F * this.fm.EI.engines[1].getPowerOutput(), -70.0F, 150.0F, -38.5F, 87.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Coolant1", cvt(this.fm.EI.engines[0].tWaterOut, -70.0F, 150.0F, -38.5F, 87.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Coolant2", cvt(this.fm.EI.engines[1].tWaterOut, -70.0F, 150.0F, -38.5F, 87.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 131.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 131.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.031F, -0.031F);
    this.mesh.chunkSetLocate("Z_TurnBank2", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 245.2F, 0.0F, 120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", cvt(this.fm.M.fuel, 0.0F, 245.2F, 0.0F, 120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel3", cvt(this.fm.M.fuel, 245.2F, 490.39999F, 0.0F, 120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel4", cvt(this.fm.M.fuel, 245.2F, 490.39999F, 0.0F, 120.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(7.0D), -7.0F, 7.0F, -16.0F, 16.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank4", cvt(this.w.z, -0.23562F, 0.23562F, 29.5F, -29.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pres1", 73.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 2.370465F, 0.0F, 320.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold2", cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.3386378F, 2.370465F, 0.0F, 320.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 28.0F, 0.0F, 164.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 28.0F, 0.0F, 164.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_fuelpress1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.4F, 0.0F, 164.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_fuelpress2", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.4F, 0.0F, 164.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4500.0F, 0.0F, 331.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM2", cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4500.0F, 0.0F, 331.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_GearGreen1", this.fm.CT.getGear() > 0.95F);
    this.mesh.chunkVisible("Z_GearRed1", (this.fm.CT.getGear() < 0.05F) || (!this.fm.Gears.lgear) || (!this.fm.Gears.rgear));
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x2) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x1) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0))) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0))) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0));
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLIn_D0", localHierMesh.isChunkVisible("WingLIn_D0"));
    this.mesh.chunkVisible("WingLIn_D1", localHierMesh.isChunkVisible("WingLIn_D1"));
    this.mesh.chunkVisible("WingLIn_D2", localHierMesh.isChunkVisible("WingLIn_D2"));
    this.mesh.chunkVisible("WingRIn_D0", localHierMesh.isChunkVisible("WingRIn_D0"));
    this.mesh.chunkVisible("WingRIn_D1", localHierMesh.isChunkVisible("WingRIn_D1"));
    this.mesh.chunkVisible("WingRIn_D2", localHierMesh.isChunkVisible("WingRIn_D2"));
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitP_38L.this.bNeedSetUp) {
        CockpitP_38L.this.reflectPlaneMats();
        CockpitP_38L.access$102(CockpitP_38L.this, false);
      }
      if (((P_38L)CockpitP_38L.this.aircraft()).bChangedPit) {
        CockpitP_38L.this.reflectPlaneToModel();
        ((P_38L)CockpitP_38L.this.aircraft()).bChangedPit = false;
      }
      if (CockpitP_38L.this.fm != null) {
        CockpitP_38L.access$202(CockpitP_38L.this, CockpitP_38L.this.setOld); CockpitP_38L.access$302(CockpitP_38L.this, CockpitP_38L.this.setNew); CockpitP_38L.access$402(CockpitP_38L.this, CockpitP_38L.this.setTmp);

        CockpitP_38L.this.setNew.trim = (0.92F * CockpitP_38L.this.setOld.trim + 0.08F * CockpitP_38L.this.fm.CT.getTrimElevatorControl());
        CockpitP_38L.this.setNew.throttle1 = (0.85F * CockpitP_38L.this.setOld.throttle1 + CockpitP_38L.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitP_38L.this.setNew.throttle2 = (0.85F * CockpitP_38L.this.setOld.throttle2 + CockpitP_38L.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitP_38L.this.setNew.prop1 = (0.85F * CockpitP_38L.this.setOld.prop1 + CockpitP_38L.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitP_38L.this.setNew.prop2 = (0.85F * CockpitP_38L.this.setOld.prop2 + CockpitP_38L.this.fm.EI.engines[1].getControlProp() * 0.15F);

        CockpitP_38L.this.setNew.mix1 = (0.85F * CockpitP_38L.this.setOld.mix1 + CockpitP_38L.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitP_38L.this.setNew.mix2 = (0.85F * CockpitP_38L.this.setOld.mix2 + CockpitP_38L.this.fm.EI.engines[1].getControlMix() * 0.15F);
        CockpitP_38L.this.setNew.altimeter = CockpitP_38L.this.fm.getAltitude();

        float f = CockpitP_38L.this.waypointAzimuth();
        CockpitP_38L.this.setNew.azimuth.setDeg(CockpitP_38L.this.setOld.azimuth.getDeg(1.0F), CockpitP_38L.this.fm.Or.azimut());
        CockpitP_38L.this.setNew.waypointAzimuth.setDeg(CockpitP_38L.this.setOld.waypointAzimuth.getDeg(1.0F), f);

        if (CockpitP_38L.this.useRealisticNavigationInstruments())
        {
          CockpitP_38L.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitP_38L.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitP_38L.this.setNew.waypointAzimuth.setDeg(CockpitP_38L.this.setOld.waypointAzimuth.getDeg(0.1F), f);
        }

        CockpitP_38L.this.setNew.vspeed = ((199.0F * CockpitP_38L.this.setOld.vspeed + CockpitP_38L.this.fm.getVertSpeed()) / 200.0F);
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
    float altimeter;
    float vspeed;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float trim;
    float k14wingspan;
    float k14mode;
    float k14x;
    float k14y;
    float k14w;
    private final CockpitP_38L this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitP_38L.1 arg2)
    {
      this();
    }
  }
}