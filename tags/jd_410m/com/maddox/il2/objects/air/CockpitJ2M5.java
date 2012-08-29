package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitJ2M5 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictMix = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictManf = 1.0F;
  private float pictFuel = 0.0F;
  private float pictMetl = 0.0F;
  private float pictTriE = 0.0F;
  private float pictTriR = 0.0F;
  private float pictSupc = 0.0F;
  private float pictBoox = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 109.5F, 220.5F, 337.0F, 433.5F, 513.0F, 605.5F, 301.5F };

  private static final float[] frAirTempScale = { 0.0F, 27.5F, 49.5F, 66.0F, 82.0F, 100.0F };

  private static final float[] oilTempScale = { 0.0F, 43.5F, 95.5F, 172.0F, 262.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitJ2M5()
  {
    super("3DO/Cockpit/J2M5/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "Arrows_Segment", "D_g_ind_01", "D_g_ind_02", "D_g_ind_03", "D_g_ind_04", "D_g_ind_05", "D_g_ind_06", "D_g_ind_07", "g_ind_01", "g_ind_02", "g_ind_03", "g_ind_04", "g_ind_05", "g_ind_06", "g_ind_07" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Z_IronSight", 0.0F, -72.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 0.07F);
    this.mesh.chunkSetLocate("Z_SightTint", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Trim1", 720.0F * (this.pictTriE = 0.92F * this.pictTriE + 0.08F * this.fm.CT.getTrimElevatorControl()), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 160.0F * (this.pictTriR = 0.92F * this.pictTriR + 0.08F * this.fm.CT.getTrimRudderControl()), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_CowlFlaps1", 175.5F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      f = -32.0F;
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      f = 32.0F;
    else {
      f = 0.0F;
    }
    this.mesh.chunkSetAngles("Z_Gear1", this.pictGear = 0.8F * this.pictGear + 0.2F * f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", 70.449997F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 95.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 79.0F * (this.pictMix = 0.8F * this.pictMix + 0.2F * this.fm.EI.engines[0].getControlMix()), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", -32.0F * (this.pictSupc = 0.91F * this.pictSupc + 0.09F * this.fm.EI.engines[0].getControlCompressor()), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Boost", -32.0F * (this.pictBoox = 0.91F * this.pictBoox + 0.09F * (this.fm.EI.engines[0].getControlAfterburner() ? 1.0F : 0.0F)), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Rudder", 16.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 40.0F * this.fm.CT.getBrake() * cvt(this.fm.CT.getRudder(), -0.5F, 1.0F, 0.0F, 1.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 40.0F * this.fm.CT.getBrake() * cvt(this.fm.CT.getRudder(), -1.0F, 0.5F, 1.0F, 0.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", -18.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", -14.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trigger", this.fm.CT.saveWeaponControl[1] != 0 ? -16.5F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", -90.0F * this.fm.CT.FlapsControl, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilCooler", -175.5F * this.fm.EI.engines[0].getControlRadiator(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Magneto", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 76.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 92.599976F, 740.7998F, 0.0F, 7.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.02705F, -0.02705F);
    this.mesh.chunkSetLocate("Z_TurnBank1a", xyz, ypr);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(this.w.z, -0.23562F, 0.23562F, -21.0F, 21.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8.0D), -8.0F, 8.0F, 12.0F, -12.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -10.0F, 10.0F, 180.0F, -180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 500.0F, 3500.0F, 0.0F, -315.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 50.0F, 403.20001F, 0.0F, -240.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", cvt(this.fm.M.fuel, 0.0F, 43.200001F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres1", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, -278.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_HydPres1", this.fm.Gears.isHydroOperable() ? -116.0F : 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut * this.fm.EI.engines[0].getPowerOutput() * 3.5F, 500.0F, 900.0F, 0.0F, -65.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pres1", cvt(this.setNew.manifold, 0.200068F, 1.799932F, 164.0F, -164.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", -floatindex(cvt(this.fm.EI.engines[0].tOilOut, 30.0F, 110.0F, 0.0F, 4.0F), oilTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Methanol", cvt(this.fm.M.nitro, 0.0F, 80.0F, 0.0F, -69.0F), 0.0F, 0.0F);
    float f = 0.0F;
    if (this.fm.EI.engines[0].getControlAfterburner()) {
      f = 0.025F;
      if ((this.fm.EI.engines[0].getControlThrottle() > 1.0F) && (this.fm.M.nitro > 0.05F)) {
        f = 0.68F;
      }
    }
    this.pictMetl = (0.9F * this.pictMetl + 0.1F * f);
    this.mesh.chunkSetAngles("Z_Methanol_Pres", cvt(this.pictMetl, 0.0F, 1.0F, 0.0F, -276.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_CylHead_Temp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -64.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FireExt_Quan", cvt(this.fm.EI.engines[0].getExtinguishers(), 0.0F, 11.0F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Ext_Air_Temp", -floatindex(cvt(Atmosphere.temperature((float)this.fm.Loc.z), 233.09F, 333.09F, 0.0F, 5.0F), frAirTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim1a", 27.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2a", 27.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("XLampGearUpR", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearUpL", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
    this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
    this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
  }

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    float f = (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
    while (f <= -180.0F) {
      f += 180.0F;
    }
    while (f > 180.0F) {
      f -= 180.0F;
    }
    return f;
  }

  public void reflectCockpitState()
  {
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
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
      if (CockpitJ2M5.this.fm != null) {
        CockpitJ2M5.access$102(CockpitJ2M5.this, CockpitJ2M5.this.setOld); CockpitJ2M5.access$202(CockpitJ2M5.this, CockpitJ2M5.this.setNew); CockpitJ2M5.access$302(CockpitJ2M5.this, CockpitJ2M5.this.setTmp);

        CockpitJ2M5.this.setNew.throttle = (0.85F * CockpitJ2M5.this.setOld.throttle + CockpitJ2M5.this.fm.CT.PowerControl * 0.15F);
        CockpitJ2M5.this.setNew.prop = (0.85F * CockpitJ2M5.this.setOld.prop + CockpitJ2M5.this.fm.CT.getStepControl() * 0.15F);
        CockpitJ2M5.this.setNew.altimeter = CockpitJ2M5.this.fm.getAltitude();
        float f = CockpitJ2M5.this.waypointAzimuth();
        CockpitJ2M5.this.setNew.waypointAzimuth.setDeg(CockpitJ2M5.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitJ2M5.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitJ2M5.this.setNew.azimuth.setDeg(CockpitJ2M5.this.setOld.azimuth.getDeg(1.0F), CockpitJ2M5.this.fm.Or.azimut());
        CockpitJ2M5.this.setNew.vspeed = ((199.0F * CockpitJ2M5.this.setOld.vspeed + CockpitJ2M5.this.fm.getVertSpeed()) / 200.0F);
        CockpitJ2M5.this.setNew.manifold = (0.8F * CockpitJ2M5.this.setOld.manifold + 0.2F * CockpitJ2M5.this.fm.EI.engines[0].getManifoldPressure());
        if (CockpitJ2M5.this.cockpitDimControl) {
          if (CockpitJ2M5.this.setNew.dimPosition > 0.0F) CockpitJ2M5.this.setNew.dimPosition = (CockpitJ2M5.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitJ2M5.this.setNew.dimPosition < 1.0F) CockpitJ2M5.this.setNew.dimPosition = (CockpitJ2M5.this.setOld.dimPosition + 0.05F);

      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float manifold;
    float dimPosition;
    private final CockpitJ2M5 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitJ2M5.1 arg2)
    {
      this();
    }
  }
}