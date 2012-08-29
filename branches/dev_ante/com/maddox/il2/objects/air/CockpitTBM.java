package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitTBM extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictManf;
  private boolean bNeedSetUp;
  private static final float[] speedometerScale = { 
    0.0F, 15.5F, 77.0F, 175.0F, 275.0F, 360.0F, 412.0F, 471.0F, 539.0F, 610.5F, 
    669.75F, 719.0F };

  private static final float[] variometerScale = { 
    -175.5F, -160.5F, -145.5F, -128.0F, -100.0F, -65.5F, 0.0F, 65.5F, 100.0F, 128.0F, 
    145.5F, 160.5F, 175.5F };

  public CockpitTBM()
  {
    super("3DO/Cockpit/TBM/hier.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictManf = 1.0F;
    this.bNeedSetUp = true;
    this.cockpitNightMats = new String[] { 
      "aigtemp", "instru01", "instru01_D", "instru02", "instru02_D", "instru03", "instru03_D", "instru04", "instru04_D", "instru05", 
      "instru05_D" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float f) {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.625F);
    Cockpit.xyz[2] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.06845F);
    Cockpit.ypr[2] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 1.0F);
    this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_Trim1", 0.0F, 722.0F * this.fm.CT.getTrimAileronControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 0.0F, 722.0F * this.fm.CT.getTrimRudderControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 0.0F, 722.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -37.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.25F * this.fm.CT.FlapsControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 0.0F, 46.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, 51.799999F * interp(this.setNew.throttle, this.setOld.throttle, f), 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 0.0F, 35.0F - 35.0F * interp(this.setNew.prop, this.setOld.prop, f), 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, 47.0F * this.fm.EI.engines[0].getControlMix(), 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, -15.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, -10.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F);
    this.mesh.chunkSetAngles("Z_WingFold1", 0.0F, 54.0F * this.fm.CT.wingControl, 0.0F);
    this.mesh.chunkSetAngles("Z_Hook1", 0.0F, -57.0F * this.fm.CT.arrestorControl, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 796.3598F, 0.0F, 11.0F), speedometerScale), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(7.0D), -7.0F, 7.0F, -10.0F, 10.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -0.0285F, 0.0285F);
    this.mesh.chunkSetLocate("Z_TurnBank4", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_TurnBank5", 0.0F, -this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, floatindex(cvt(this.setNew.vspeed, -30.48F, 30.48F, 0.0F, 12.0F), variometerScale), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -90.0F + this.setNew.waypointAzimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.azimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", 0.0F, -this.setNew.azimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_RPM2", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.fm.M.fuel, 0.0F, 600.0F, 0.0F, 300.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 1.0F, 0.0F, 255.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TankPres1", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 1.0F, 0.0F, 255.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_HydPres1", 0.0F, this.fm.Gears.bIsHydroOperable ? 200.0F : 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 97.5F), 0.0F);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("Z_Pres1", 0.0F, this.pictManf = 0.9F * this.pictManf + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.338638F, 2.539784F, 0.0F, 344.5F), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 20.0F, 120.0F, 0.0F, 78.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 256.0F), 0.0F);
    resetYPRmodifier();
    if (this.fm.Gears.cgear) {
      Cockpit.xyz[2] = (-0.028F * this.fm.CT.getGear());
    }
    this.mesh.chunkSetLocate("Z_GearInd1", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    if (this.fm.Gears.lgear) {
      Cockpit.xyz[2] = (-0.028F * this.fm.CT.getGear());
    }
    this.mesh.chunkSetLocate("Z_GearInd2", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    if (this.fm.Gears.rgear) {
      Cockpit.xyz[2] = (-0.028F * this.fm.CT.getGear());
    }
    this.mesh.chunkSetLocate("Z_GearInd3", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkVisible("Z_WaterInjL1", this.fm.EI.engines[0].getControlAfterburner());
    this.mesh.chunkVisible("Z_StallWarnL1", this.fm.getAOA() > this.fm.AOA_Crit);
    this.mesh.chunkVisible("Z_CarbAirL1", false);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Pricel_D0", false);
      this.mesh.chunkVisible("Pricel_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_RPM2", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_Hour1", false);
      this.mesh.chunkVisible("Z_Minute1", false);
      this.mesh.chunkVisible("Z_FuelPres1", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh hiermesh = aircraft().hierMesh();
    Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", mat);
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
    public boolean tick()
    {
      if (CockpitTBM.this.fm != null) {
        CockpitTBM.this.setTmp = CockpitTBM.this.setOld; CockpitTBM.this.setOld = CockpitTBM.this.setNew; CockpitTBM.this.setNew = CockpitTBM.this.setTmp;

        CockpitTBM.this.setNew.throttle = (0.85F * CockpitTBM.this.setOld.throttle + CockpitTBM.this.fm.CT.PowerControl * 0.15F);
        CockpitTBM.this.setNew.prop = (0.85F * CockpitTBM.this.setOld.prop + CockpitTBM.this.fm.CT.getStepControl() * 0.15F);
        CockpitTBM.this.setNew.altimeter = CockpitTBM.this.fm.getAltitude();
        float f = CockpitTBM.this.waypointAzimuth();
        CockpitTBM.this.setNew.azimuth.setDeg(CockpitTBM.this.setOld.azimuth.getDeg(1.0F), CockpitTBM.this.fm.Or.azimut());
        CockpitTBM.this.setNew.waypointAzimuth.setDeg(CockpitTBM.this.setOld.waypointAzimuth.getDeg(1.0F), f);
        CockpitTBM.this.setNew.vspeed = ((199.0F * CockpitTBM.this.setOld.vspeed + CockpitTBM.this.fm.getVertSpeed()) / 200.0F);
      }

      return true; } 
    Interpolater() {  }  } 
  private class Variables { float throttle;
    float prop;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    final CockpitTBM this$0;

    private Variables() { this.this$0 = ???;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(Variables arg2)
    {
      this();
    }
  }
}