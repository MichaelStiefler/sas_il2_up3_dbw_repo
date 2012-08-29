package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitBF_210 extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictManifold1 = 0.0F;
  private float pictManifold2 = 0.0F;
  private static final float[] speedometerScale = { 0.0F, -12.333333F, 18.5F, 37.0F, 62.5F, 90.0F, 110.5F, 134.0F, 158.5F, 186.0F, 212.5F, 238.5F, 265.0F, 289.5F, 315.0F, 339.5F, 346.0F, 346.0F };

  private static final float[] rpmScale = { 0.0F, 36.5F, 70.0F, 111.0F, 149.5F, 186.5F, 233.5F, 282.5F, 308.0F, 318.5F };

  private static final float[] oilTScale = { 0.0F, 24.5F, 47.5F, 74.0F, 102.5F, 139.0F, 188.0F, 227.5F, 290.5F };

  private static final float[] variometerScale = { -130.5F, -119.5F, -109.5F, -96.0F, -83.0F, -49.5F, 0.0F, 49.5F, 83.0F, 96.0F, 109.5F, 119.5F, 130.5F };

  protected float waypointAzimuth()
  {
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint == null)
      return 0.0F;
    localWayPoint.getP(Cockpit.P1);
    Cockpit.V.sub(Cockpit.P1, this.fm.Loc);
    return (float)(57.295779513082323D * Math.atan2(Cockpit.V.y, Cockpit.V.x));
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    boolean bool = (localHierMesh.isChunkVisible("Engine1_D0")) || (localHierMesh.isChunkVisible("Engine1_D1")) || (localHierMesh.isChunkVisible("Engine1_D2"));

    this.mesh.chunkVisible("EnginLeft", bool);
    this.mesh.chunkVisible("Z_Temp4", bool);
    this.mesh.chunkVisible("Z_Temp6", bool);
    this.mesh.chunkVisible("Z_Fuelpress1", bool);
    this.mesh.chunkVisible("Z_OilPress1", bool);
    this.mesh.chunkVisible("Z_Oiltemp1", bool);
    bool = (localHierMesh.isChunkVisible("Engine2_D0")) || (localHierMesh.isChunkVisible("Engine2_D1")) || (localHierMesh.isChunkVisible("Engine2_D2"));

    this.mesh.chunkVisible("EnginRight", bool);
    this.mesh.chunkVisible("Z_Temp5", bool);
    this.mesh.chunkVisible("Z_Temp7", bool);
    this.mesh.chunkVisible("Z_Fuelpress2", bool);
    this.mesh.chunkVisible("Z_OilPress2", bool);
    this.mesh.chunkVisible("Z_Oiltemp2", bool);
  }

  public CockpitBF_210() {
    super("3DO/Cockpit/Bf-110G/hier.him", "bf109");
    this.setNew.dimPosition = 0.0F;
    this.cockpitDimControl = (!this.cockpitDimControl);
    this.cockpitNightMats = new String[] { "bague1", "bague2", "boitier", "cadran1", "cadran2", "cadran3", "cadran4", "cadran5", "cadran6", "cadran7", "cadran8", "consoledr2", "enggauge", "fils", "gauche", "skala" };

    setNightMats(false);
    interpPut(new CockpitPilot.Interpolater(this), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON02");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
    }
    resetYPRmodifier();
    Cockpit.xyz[2] = (0.06815F * interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat));

    this.mesh.chunkSetLocate("Revisun", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkVisible("Z_GearLGreen", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));

    this.mesh.chunkVisible("Z_GearRGreen", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));

    this.mesh.chunkVisible("Z_GearLRed", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearRRed", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_FuelL1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_FuelL2", this.fm.M.fuel < 102.0F);
    this.mesh.chunkVisible("Z_FuelR1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_FuelR2", this.fm.M.fuel < 102.0F);
    if (this.gun[0] != null)
      this.mesh.chunkVisible("Z_AmmoL", this.gun[0].countBullets() == 0);
    if (this.gun[1] != null)
      this.mesh.chunkVisible("Z_AmmoR", this.gun[1].countBullets() == 0);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Column", 0.0F, -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F);

    resetYPRmodifier();
    if (this.fm.CT.saveWeaponControl[1] != 0)
      Cockpit.xyz[2] = 0.00545F;
    this.mesh.chunkSetLocate("Z_Columnbutton1", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[2] = (-0.05F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_LeftPedal", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[2] = (0.05F * this.fm.CT.getRudder());
    this.mesh.chunkSetLocate("Z_RightPedal", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Throttle1", interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat) * 52.200001F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle2", interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat) * 52.200001F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Mixture1", interp(this.setNew.mix1, this.setOld.mix2, paramFloat) * 52.200001F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Mixture2", interp(this.setNew.mix1, this.setOld.mix2, paramFloat) * 52.200001F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Pitch1", this.fm.EI.engines[0].getControlProp() * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Pitch2", this.fm.EI.engines[1].getControlProp() * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Radiat1", 0.0F, 0.0F, this.fm.EI.engines[0].getControlRadiator() * 15.0F);

    this.mesh.chunkSetAngles("Z_Radiat2", 0.0F, 0.0F, this.fm.EI.engines[1].getControlRadiator() * 15.0F);

    this.mesh.chunkSetAngles("Z_Compass1", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Azimuth1", -interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    if (this.gun[0] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(this.gun[0].countBullets(), 0.0F, 500.0F, 13.0F, 0.0F), 0.0F, 0.0F);
    }

    if (this.gun[2] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(this.gun[2].countBullets(), 0.0F, 500.0F, 13.0F, 0.0F), 0.0F, 0.0F);
    }

    if (this.gun[1] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter3", cvt(this.gun[1].countBullets(), 0.0F, 500.0F, 13.0F, 0.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    if (aircraft().isFMTrackMirror()) {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -3.0F, 3.0F, 21.0F, -21.0F);

      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.mesh.chunkSetAngles("Z_TurnBank1", f, 0.0F, 0.0F);
    float f = getBall(4.0D);
    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(f, -4.0F, 4.0F, 10.0F, -10.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(f, -3.8F, 3.8F, 9.0F, -9.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank4", cvt(f, -3.3F, 3.3F, 7.5F, -7.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Horizon1", 0.0F, 0.0F, this.fm.Or.getKren());
    this.mesh.chunkSetAngles("Z_Horizon2", cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -23.0F, 23.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", floatindex(cvt(this.setNew.vspeed, -30.0F, 30.0F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ATA1", cvt(this.pictManifold1 = 0.75F * this.pictManifold1 + 0.25F * this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ATA2", cvt(this.pictManifold2 = 0.75F * this.pictManifold2 + 0.25F * this.fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.radioalt, this.setOld.radioalt, paramFloat), 0.0F, 750.0F, 0.0F, 228.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt3", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 313.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 66.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 233.09F, 313.09F, -42.5F, 42.400002F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 68.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp3", cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 120.0F, 0.0F, 68.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AirPressure1", 170.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Autopilot1", -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Autopilot2", interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp4", -(float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp6", -(float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp5", -(float)Math.toDegrees(this.fm.EI.engines[1].getPropPhi() - this.fm.EI.engines[1].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp7", -(float)Math.toDegrees(this.fm.EI.engines[1].getPropPhi() - this.fm.EI.engines[1].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Fuelpress1", cvt(this.fm.M.fuel > 1.0F ? 0.77F : 0.0F, 0.0F, 2.0F, 0.0F, 160.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Fuelpress2", cvt(this.fm.M.fuel > 1.0F ? 0.77F : 0.0F, 0.0F, 2.0F, 0.0F, 160.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 160.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_OilPress2", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, 160.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Oiltemp1", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 120.0F, 0.0F, 8.0F), oilTScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Oiltemp2", floatindex(cvt(this.fm.EI.engines[1].tOilOut, 40.0F, 120.0F, 0.0F, 8.0F), oilTScale), 0.0F, 0.0F);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("ReviSun", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("HullDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("HullDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XGlassDamage3", true);
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("HullDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage4", true);
  }

  private class Variables
  {
    float altimeter;
    float throttle1;
    float throttle2;
    float dimPosition;
    float azimuth;
    float waypointAzimuth;
    float mix1;
    float mix2;
    float vspeed;
    float radioalt;
    private final CockpitBF_210 this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitBF_210.1 arg2)
    {
      this();
    }
  }
}