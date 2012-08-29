package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitMC_202 extends CockpitPilot
{
  private boolean bNeedSetUp = true;

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
  private BulletEmitter[] gun = { null, null, null, null };

  private static final float[] speedometerScale = { 0.0F, 38.5F, 77.0F, 100.5F, 125.0F, 147.0F, 169.5F, 193.5F, 216.5F, 240.5F, 265.0F, 287.5F, 303.5F };

  private static final float[] waterTempScale = { 0.0F, 20.5F, 37.0F, 52.0F, 73.5F, 95.5F, 120.0F, 150.0F, 192.0F, 245.0F, 302.0F };

  private static final float[] oilTempScale = { 0.0F, 33.0F, 80.0F, 153.0F, 301.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  public CockpitMC_202()
  {
    super("3DO/Cockpit/MC-202/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gs_lthr", "mat2_tr", "strum1dmg", "strum2dmg", "strum3dmg", "strum4dmg", "strum1", "strum2", "strum3", "strum4", "strumsxdmg", "strumsx" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN03");
      this.gun[3] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN04");
    }

    this.mesh.chunkSetAngles("Z_Column", 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 8.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("Pedals", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle", 29.540001F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSwitch", cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 14.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mix", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PropPitch1", 33.5F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_PropPitch2", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap1", 45.0F * (this.pictFlap = 0.75F * this.pictFlap + 0.25F * this.fm.CT.FlapsControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", -32.0F * (this.pictGear = 0.8F * this.pictGear + 0.2F * this.fm.CT.GearControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim1", -76.5F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06325F);
    this.mesh.chunkSetLocate("Z_OilRad1", xyz, ypr);
    xyz[2] = cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.01625F);
    this.mesh.chunkSetLocate("Z_OilRad2", xyz, ypr);
    this.mesh.chunkSetAngles("Z_OilRad3", 0.0F, 0.0F, 0.0F);

    if (this.gun[0].haveBullets()) {
      this.mesh.chunkSetAngles("Z_AmmoCounter1", 0.36F * this.gun[0].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AmmoCounter2", 3.6F * this.gun[0].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AmmoCounter3", 36.0F * this.gun[0].countBullets(), 0.0F, 0.0F);
    }
    if (this.gun[1].haveBullets()) {
      this.mesh.chunkSetAngles("Z_AmmoCounter4", 0.36F * this.gun[1].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AmmoCounter5", 3.6F * this.gun[1].countBullets(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AmmoCounter6", 36.0F * this.gun[1].countBullets(), 0.0F, 0.0F);
    }
    if (this.gun[2].haveBullets()) {
      this.mesh.chunkSetAngles("Z_AmmoCounter7", cvt(this.gun[2].countBullets(), 0.0F, 500.0F, 7.0F, 358.0F), 0.0F, 0.0F);
    }
    if (this.gun[3].haveBullets()) {
      this.mesh.chunkSetAngles("Z_AmmoCounter8", cvt(this.gun[3].countBullets(), 0.0F, 500.0F, 7.0F, 358.0F), 0.0F, 0.0F);
    }
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, -0.06055F);
    this.mesh.chunkSetLocate("Z_Cooling", xyz, ypr);
    resetYPRmodifier();
    xyz[2] = cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 0.05475F);
    this.mesh.chunkSetLocate("Z_FlapPos", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 100.0F, 700.0F, 0.0F, 12.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -25.0F, 25.0F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 322.5F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Turn1", cvt(this.w.z, -0.23562F, 0.23562F, 21.0F, -21.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turn2", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, -11.0F, 11.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_ManfoldPress", 0.0F, this.pictManf = 0.9F * this.pictManf + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.5F, 2.0F, 0.0F, 300.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_EngTemp1", floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 30.0F, 130.0F, 0.0F, 10.0F), waterTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilTemp1", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 30.0F, 150.0F, 0.0F, 4.0F), oilTempScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 184.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_OilPress2", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AirPress1", 135.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AirPress2", 135.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPress1", cvt(this.fm.M.fuel > 1.0F ? 0.32F : 0.0F, 0.0F, 1.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_FuelQuantity1", cvt(this.fm.M.fuel / this.fm.M.maxFuel, 0.0F, 1.0F, 0.0F, 244.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.0135F, -0.0135F);
    this.mesh.chunkSetLocate("Z_Horison1c", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Horison1a", this.fm.Or.getKren(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Horison1b", 0.0F, 0.0F, 0.0F);

    this.mesh.chunkVisible("XLampGunL", this.gun[0].countBullets() < 90);
    this.mesh.chunkVisible("XLampGunR", this.gun[1].countBullets() < 90);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkVisible("XLampGearUpR", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.rgear));
      this.mesh.chunkVisible("XLampGearUpL", (this.fm.CT.getGear() < 0.01F) || (!this.fm.Gears.lgear));
      this.mesh.chunkVisible("XLampGearDownR", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.rgear));
      this.mesh.chunkVisible("XLampGearDownL", (this.fm.CT.getGear() > 0.99F) && (this.fm.Gears.lgear));
    } else {
      this.mesh.chunkVisible("XLampGearUpR", false);
      this.mesh.chunkVisible("XLampGearUpL", false);
      this.mesh.chunkVisible("XLampGearDownR", false);
      this.mesh.chunkVisible("XLampGearDownL", false);
    }
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
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }

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
      if (CockpitMC_202.this.fm != null) {
        CockpitMC_202.access$102(CockpitMC_202.this, CockpitMC_202.this.setOld); CockpitMC_202.access$202(CockpitMC_202.this, CockpitMC_202.this.setNew); CockpitMC_202.access$302(CockpitMC_202.this, CockpitMC_202.this.setTmp);

        CockpitMC_202.this.setNew.throttle = (0.85F * CockpitMC_202.this.setOld.throttle + CockpitMC_202.this.fm.CT.PowerControl * 0.15F);
        CockpitMC_202.this.setNew.prop = (0.85F * CockpitMC_202.this.setOld.prop + CockpitMC_202.this.fm.CT.getStepControl() * 0.15F);
        CockpitMC_202.this.setNew.altimeter = CockpitMC_202.this.fm.getAltitude();
        float f = CockpitMC_202.this.waypointAzimuth();
        CockpitMC_202.this.setNew.waypointAzimuth.setDeg(CockpitMC_202.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitMC_202.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-10.0F, 10.0F));
        CockpitMC_202.this.setNew.azimuth.setDeg(CockpitMC_202.this.setOld.azimuth.getDeg(1.0F), CockpitMC_202.this.fm.Or.azimut());
        CockpitMC_202.this.setNew.vspeed = ((199.0F * CockpitMC_202.this.setOld.vspeed + CockpitMC_202.this.fm.getVertSpeed()) / 200.0F);
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
    private final CockpitMC_202 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitMC_202.1 arg2)
    {
      this();
    }
  }
}