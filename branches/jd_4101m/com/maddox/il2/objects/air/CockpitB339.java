package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitB339 extends CockpitPilot
{
  private Gun[] gun = { null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private float pictAiler;
  private float pictElev;
  private float pictThtl;
  private float pictMix;
  private float pictProp;
  private static final float[] speedometerScale = { 0.0F, 0.5F, 1.0F, 2.0F, 5.5F, 14.0F, 20.0F, 26.0F, 33.5F, 42.0F, 50.5F, 60.5F, 71.5F, 81.5F, 95.199997F, 108.5F, 122.5F, 137.0F, 152.0F, 166.7F, 182.0F, 198.0F, 214.5F, 231.0F, 247.5F, 263.5F, 278.5F, 294.0F, 307.0F, 317.0F, 330.5F, 343.0F, 355.5F, 367.5F, 379.5F, 391.5F, 404.0F, 417.0F, 430.5F, 444.0F, 458.0F, 473.5F, 487.5F, 503.5F, 519.5F, 535.5F, 552.0F, 569.5F, 586.0F, 602.5F, 619.0F, 631.5F, 643.0F, 648.5F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null) return 0.0F;
    localWayPoint.getP(this.tmpP);
    this.tmpV.sub(this.tmpP, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(-this.tmpV.y, this.tmpV.x));
  }

  public CockpitB339()
  {
    super("3DO/Cockpit/B-339/hier.him", "bf109");
    this.cockpitNightMats = new String[] { "F2ABoxes", "F2Acables", "F2Agauges", "F2Agauges1", "F2Agauges3", "F2AWindshields", "F2Azegary4" };

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
    }

    resetYPRmodifier();
    xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.725F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);

    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 25.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, 10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    resetYPRmodifier();
    xyz[0] = cvt(this.pictAiler, -1.0F, 1.0F, -0.054F, 0.054F);
    this.mesh.chunkSetLocate("Z_ColumnR", xyz, ypr);
    this.mesh.chunkSetLocate("Z_ColumnL", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getRudder(), -1.0F, 1.0F, -0.0575F, 0.0575F);
    this.mesh.chunkSetLocate("Z_Pedal_L", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getRudder(), -1.0F, 1.0F, 0.0575F, -0.0575F);
    this.mesh.chunkSetLocate("Z_Pedal_R", xyz, ypr);
    resetYPRmodifier();
    if ((this.fm.EI.engines[0].getStage() > 0) && (this.fm.EI.engines[0].getStage() < 3)) {
      xyz[1] = 0.02825F;
    }
    this.mesh.chunkSetLocate("Z_Starter", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Ign_Switch", 0.0F, -20.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F);
    this.mesh.chunkSetAngles("Z_Throttle", 0.0F, 100.0F * (this.pictThtl = 0.9F * this.pictThtl + 0.1F * this.fm.EI.engines[0].getControlThrottle()), 0.0F);
    this.mesh.chunkSetAngles("Z_mixture", 0.0F, 91.666672F * (this.pictMix = 0.9F * this.pictMix + 0.1F * this.fm.EI.engines[0].getControlMix()), 0.0F);
    resetYPRmodifier();
    float tmp539_538 = (0.9F * this.pictProp + 0.1F * this.fm.EI.engines[0].getControlProp()); this.pictProp = tmp539_538; xyz[1] = cvt(tmp539_538, 0.0F, 1.0F, 0.0F, -0.035F);
    this.mesh.chunkSetLocate("Z_Pitch", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Booster_Lever", 0.0F, -5.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_TL_lock", 0.0F, this.fm.Gears.bTailwheelLocked ? -30.0F : 0.0F, 0.0F);
    if ((this.fm.CT.FlapsControl == 0.0F) && (this.fm.CT.getFlap() != 0.0F))
      this.mesh.chunkSetAngles("Z_Flaps", 0.0F, 0.0F, 0.0F);
    else if ((this.fm.CT.FlapsControl == 1.0F) && (this.fm.CT.getFlap() != 1.0F))
      this.mesh.chunkSetAngles("Z_Flaps", 0.0F, -70.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Flaps", 0.0F, -35.0F, 0.0F);
    }
    if ((this.fm.CT.GearControl == 0.0F) && (this.fm.CT.getGear() != 0.0F))
      this.mesh.chunkSetAngles("Z_gearlever", 0.0F, 4.0F, 0.0F);
    else if ((this.fm.CT.GearControl == 1.0F) && (this.fm.CT.getGear() != 1.0F))
      this.mesh.chunkSetAngles("Z_gearlever", 0.0F, -70.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_gearlever", 0.0F, -35.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Compass", 90.0F + interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getGear(), 0.0F, 1.0F, 0.0F, -0.135F);
    this.mesh.chunkSetLocate("Z_Gear", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, -0.04155F, 0.0211F);
    this.mesh.chunkSetLocate("Z_Flap", xyz, ypr);
    if ((this.gun[0] != null) && (this.gun[0].haveBullets())) {
      this.mesh.chunkSetAngles("Z_Ammo_W1", 0.0F, -0.36F * this.gun[0].countBullets(), 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W2", 0.0F, -3.6F * this.gun[0].countBullets(), 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W3", 0.0F, -36.0F * this.gun[0].countBullets(), 0.0F);
    }
    if ((this.gun[1] != null) && (this.gun[1].haveBullets())) {
      this.mesh.chunkSetAngles("Z_Ammo_W4", 0.0F, -0.36F * this.gun[1].countBullets(), 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W5", 0.0F, -3.6F * this.gun[1].countBullets(), 0.0F);
      this.mesh.chunkSetAngles("Z_Ammo_W6", 0.0F, -36.0F * this.gun[1].countBullets(), 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Manifold", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386378F, 1.693189F, 20.0F, 340.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt_Large", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt_Small", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 981.55975F, 0.0F, 53.0F), speedometerScale), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_Turn", cvt(this.w.z, -0.23562F, 0.23562F, 22.5F, -22.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Bank", cvt(getBall(8.0D), -8.0F, 8.0F, 12.0F, -12.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Climb", cvt(this.setNew.vspeed, -20.0F, 20.0F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Clock_Min", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Clock_H", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Hor_Handle", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.019F, -0.019F);
    this.mesh.chunkSetLocate("Z_Hor_Handle2", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Temp_Handle", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 400.0F, 0.0F, 100.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp_Eng", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 170.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil_Eng", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_Eng", cvt(this.fm.M.fuel > 1.0F ? 10.0F * this.fm.EI.engines[0].getPowerOutput() : 0.0F, 0.0F, 20.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    if (this.fm.EI.engines[0].getRPM() < 1000.0F)
      this.mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 1000.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_Tahometr_Eng", cvt(this.fm.EI.engines[0].getRPM(), 1000.0F, 3500.0F, 90.0F, 540.0F), 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_carbmixtemp", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 213.09F, 333.09F, -180.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_1", cvt(this.fm.M.fuel, 0.0F, 504.0F, 0.0F, -272.5F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel_2", cvt(this.fm.M.fuel, 0.0F, 504.0F, 0.0F, -272.5F), 0.0F, 0.0F);
    if (this.fm.Gears.isHydroOperable())
      this.mesh.chunkSetAngles("Z_hydropress", 133.0F, 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_hydropress", 1.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkVisible("XLampGear_1", (!this.fm.Gears.lgear) || (!this.fm.Gears.lgear));
  }

  public void reflectCockpitState()
  {
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitB339.this.fm != null) {
        CockpitB339.access$102(CockpitB339.this, CockpitB339.this.setOld);
        CockpitB339.access$202(CockpitB339.this, CockpitB339.this.setNew);
        CockpitB339.access$302(CockpitB339.this, CockpitB339.this.setTmp);
        CockpitB339.this.setNew.throttle = ((10.0F * CockpitB339.this.setOld.throttle + CockpitB339.this.fm.CT.PowerControl) / 11.0F);
        CockpitB339.this.setNew.altimeter = CockpitB339.this.fm.getAltitude();
        if (Math.abs(CockpitB339.this.fm.Or.getKren()) < 30.0F) {
          CockpitB339.this.setNew.azimuth = ((35.0F * CockpitB339.this.setOld.azimuth + -CockpitB339.this.fm.Or.getYaw()) / 36.0F);
        }
        if ((CockpitB339.this.setOld.azimuth > 270.0F) && (CockpitB339.this.setNew.azimuth < 90.0F)) CockpitB339.this.setOld.azimuth -= 360.0F;
        if ((CockpitB339.this.setOld.azimuth < 90.0F) && (CockpitB339.this.setNew.azimuth > 270.0F)) CockpitB339.this.setOld.azimuth += 360.0F;
        CockpitB339.this.setNew.waypointAzimuth = ((10.0F * CockpitB339.this.setOld.waypointAzimuth + (CockpitB339.this.waypointAzimuth() - CockpitB339.this.setOld.azimuth) + World.Rnd().nextFloat(-30.0F, 30.0F)) / 11.0F);
        CockpitB339.this.setNew.vspeed = ((199.0F * CockpitB339.this.setOld.vspeed + CockpitB339.this.fm.getVertSpeed()) / 200.0F);
      }
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    float azimuth;
    float vspeed;
    float waypointAzimuth;
    private final CockpitB339 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitB339.1 arg2) { this();
    }
  }
}