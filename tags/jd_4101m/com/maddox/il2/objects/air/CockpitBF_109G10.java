package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitBF_109G10 extends CockpitPilot
{
  private float tmp;
  private Gun[] gun = { null, null, null };
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private LightPointActor light2;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictManifold = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, -12.333333F, 18.5F, 37.0F, 62.5F, 90.0F, 110.5F, 134.0F, 158.5F, 186.0F, 212.5F, 238.5F, 265.0F, 289.5F, 315.0F, 339.5F, 346.0F, 346.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 54.0F, 111.0F, 171.5F, 229.5F, 282.5F, 334.0F, 342.5F, 342.5F };

  private static final float[] fuelScale = { 0.0F, 9.0F, 21.0F, 29.5F, 37.0F, 48.0F, 61.5F, 75.5F, 92.0F, 92.0F };

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitBF_109G10()
  {
    super("3DO/Cockpit/Bf-109G-10/hier.him", "bf109");
    this.setNew.dimPosition = 1.0F;

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "ZClocks1", "ZClocks1DMG", "ZClocks2", "ZClocks3", "FW190A4Compass" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    loadBuzzerFX();
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_CANNON01");
      this.gun[2] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
    }

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ReviTint", cvt(interp(this.setNew.dimPosition, this.setOld.dimPosition, paramFloat), 0.0F, 1.0F, 0.0F, 45.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_ATA1", cvt(this.pictManifold = 0.75F * this.pictManifold + 0.25F * this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 800.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_FuelQuantity1", -44.5F + floatindex(cvt(this.fm.M.fuel / 0.72F, 0.0F, 400.0F, 0.0F, 8.0F), fuelScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_EngTemp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 160.0F, 0.0F, 58.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_FuelPress1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_OilPress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Azimuth1", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("Z_Azimuth1", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_PropPitch1", 270.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_PropPitch2", 105.0F - (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin()) * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.setNew.turn, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank2", cvt(getBall(6.0D), -6.0F, 6.0F, -4.5F, 4.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Horizon1", 0.0F, 0.0F, this.fm.Or.getKren());

    this.mesh.chunkSetAngles("Z_Horizon2", cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, -13.0F, 13.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_FuelWarning1", this.fm.M.fuel < 36.0F);
    this.mesh.chunkVisible("Z_GearLRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearRRed1", this.fm.CT.getGear() == 0.0F);
    this.mesh.chunkVisible("Z_GearLGreen1", this.fm.CT.getGear() == 1.0F);
    this.mesh.chunkVisible("Z_GearRGreen1", this.fm.CT.getGear() == 1.0F);

    if (this.gun[0] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter1", cvt(this.gun[0].countBullets(), 0.0F, 500.0F, 15.0F, 0.0F), 0.0F, 0.0F);
    }
    if (this.gun[1] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter2", cvt(this.gun[1].countBullets(), 0.0F, 500.0F, 15.0F, 0.0F), 0.0F, 0.0F);
    }
    if (this.gun[2] != null) {
      this.mesh.chunkSetAngles("Z_AmmoCounter3", cvt(this.gun[2].countBullets(), 0.0F, 500.0F, 15.0F, 0.0F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Second1", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Column", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("Z_PedalStrut", this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", -this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", -this.fm.CT.getRudder() * 15.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", this.fm.CT.getAileron() * 15.0F, 0.0F, this.fm.CT.getElevator() * 10.0F);
    if ((this.fm.AS.astateCockpitState & 0x8) == 0) {
      this.mesh.chunkSetAngles("Z_Throttle", interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 68.181816F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_Mix", interp(this.setNew.mix, this.setOld.mix, paramFloat) * 62.5F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagnetoSwitch", -45.0F + 28.333F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_AFN22", cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_AFN21", cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("AFN2_RED", isOnBlindLandingMarker());
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.005F, 0.5F);
      this.light2.light.setEmit(0.005F, 0.5F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Holes3_D1", true);
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Z_ReviTint", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("Z_Holes2_D1", true);
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("PoppedPanel_D0", false);
      this.mesh.chunkVisible("Z_Repeater1", false);
      this.mesh.chunkVisible("Z_Azimuth1", false);
      this.mesh.chunkVisible("Z_Compass1", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("PoppedPanel_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x80) != 0))) {
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("Radio_D0", false);
      this.mesh.chunkVisible("Radio_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Blister1_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    aircraft().hierMesh().chunkVisible("Blister1_D0", true);
    super.doFocusLeave();
  }

  static
  {
    Property.set(CockpitBF_109G10.class, "normZN", 0.5F);
    Property.set(CockpitBF_109G10.class, "gsZN", 0.5F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitBF_109G10.access$102(CockpitBF_109G10.this, CockpitBF_109G10.this.setOld); CockpitBF_109G10.access$202(CockpitBF_109G10.this, CockpitBF_109G10.this.setNew); CockpitBF_109G10.access$302(CockpitBF_109G10.this, CockpitBF_109G10.this.setTmp);

      CockpitBF_109G10.this.setNew.altimeter = CockpitBF_109G10.this.fm.getAltitude();
      if (CockpitBF_109G10.this.cockpitDimControl) {
        if (CockpitBF_109G10.this.setNew.dimPosition > 0.0F) CockpitBF_109G10.this.setNew.dimPosition = (CockpitBF_109G10.this.setOld.dimPosition - 0.05F);
      }
      else if (CockpitBF_109G10.this.setNew.dimPosition < 1.0F) CockpitBF_109G10.this.setNew.dimPosition = (CockpitBF_109G10.this.setOld.dimPosition + 0.05F);

      CockpitBF_109G10.this.setNew.throttle = ((10.0F * CockpitBF_109G10.this.setOld.throttle + CockpitBF_109G10.this.fm.CT.PowerControl) / 11.0F);
      CockpitBF_109G10.this.setNew.mix = ((8.0F * CockpitBF_109G10.this.setOld.mix + CockpitBF_109G10.this.fm.EI.engines[0].getControlMix()) / 9.0F);

      float f = CockpitBF_109G10.this.waypointAzimuth();
      if (CockpitBF_109G10.this.useRealisticNavigationInstruments())
      {
        CockpitBF_109G10.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitBF_109G10.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
      }
      else
      {
        CockpitBF_109G10.this.setNew.waypointAzimuth.setDeg(CockpitBF_109G10.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitBF_109G10.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitBF_109G10.this.setNew.azimuth.setDeg(CockpitBF_109G10.this.setOld.azimuth.getDeg(1.0F), CockpitBF_109G10.this.fm.Or.azimut());

      CockpitBF_109G10.this.w.set(CockpitBF_109G10.this.fm.getW());
      CockpitBF_109G10.this.fm.Or.transform(CockpitBF_109G10.this.w);
      CockpitBF_109G10.this.setNew.turn = ((12.0F * CockpitBF_109G10.this.setOld.turn + CockpitBF_109G10.this.w.z) / 13.0F);

      CockpitBF_109G10.this.setNew.beaconDirection = ((10.0F * CockpitBF_109G10.this.setOld.beaconDirection + CockpitBF_109G10.this.getBeaconDirection()) / 11.0F);
      CockpitBF_109G10.this.setNew.beaconRange = ((10.0F * CockpitBF_109G10.this.setOld.beaconRange + CockpitBF_109G10.this.getBeaconRange()) / 11.0F);

      CockpitBF_109G10.this.buzzerFX((CockpitBF_109G10.this.fm.CT.getGear() < 0.999999F) && (CockpitBF_109G10.this.fm.CT.getFlap() > 0.0F));

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float dimPosition;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float turn;
    float beaconDirection;
    float beaconRange;
    float mix;
    private final CockpitBF_109G10 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitBF_109G10.1 arg2)
    {
      this();
    }
  }
}