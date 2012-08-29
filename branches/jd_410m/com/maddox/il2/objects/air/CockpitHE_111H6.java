package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
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
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitHE_111H6 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private Loc tmpLoc = new Loc();
  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  public Vector3f w = new Vector3f();

  private static final float[] speedometerScale = { 0.0F, 0.1F, 19.0F, 37.25F, 63.5F, 91.5F, 112.0F, 135.5F, 159.5F, 186.5F, 213.0F, 238.0F, 264.0F, 289.0F, 314.5F, 339.5F, 359.5F, 360.0F, 360.0F, 360.0F };

  private static final float[] rpmScale = { 0.0F, 11.25F, 54.0F, 111.0F, 171.5F, 229.5F, 282.5F, 334.0F, 342.5F, 342.5F };

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = false;
      aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
      aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAK", false);
      aircraft().hierMesh().chunkVisible("Head1_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAL", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAL", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = true;
    aircraft().hierMesh().chunkVisible("Cockpit_D0", (aircraft().hierMesh().isChunkVisible("Nose_D0")) || (aircraft().hierMesh().isChunkVisible("Nose_D1")) || (aircraft().hierMesh().isChunkVisible("Nose_D2")));
    aircraft().hierMesh().chunkVisible("Turret1C_D0", aircraft().hierMesh().isChunkVisible("Turret1B_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAK", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
    aircraft().hierMesh().chunkVisible("Head1_FAK", aircraft().hierMesh().isChunkVisible("Head1_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAL", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAK", aircraft().hierMesh().isChunkVisible("Pilot2_D0"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAL", aircraft().hierMesh().isChunkVisible("Pilot2_D1"));
    super.doFocusLeave();
  }

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(30.0F);
  }

  public CockpitHE_111H6()
  {
    super("3DO/Cockpit/He-111H-6/hier.him", "he111");

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(218.0F, 143.0F, 128.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    this.cockpitNightMats = new String[] { "clocks1", "clocks2", "clocks2DMG", "clocks3", "clocks3DMG", "clocks4", "clocks5", "clocks6", "AFN-1", "TorpBox", "Z_Angle" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public CockpitHE_111H6(String paramString)
  {
    super(paramString, "he111");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.tmpLoc = new Loc();
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(218.0F, 143.0F, 128.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.cockpitNightMats = new String[] { "clocks1", "clocks2", "clocks2DMG", "clocks3", "clocks3DMG", "clocks4", "clocks5", "clocks6", "AFN-1" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm.isTick(44, 0)) {
      if ((this.fm.AS.astateCockpitState & 0x8) == 0) {
        this.mesh.chunkVisible("Z_GearLRed1", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
        this.mesh.chunkVisible("Z_GearRRed1", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
        this.mesh.chunkVisible("Z_GearLGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
        this.mesh.chunkVisible("Z_GearRGreen1", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));
      } else {
        this.mesh.chunkVisible("Z_GearLRed1", false);
        this.mesh.chunkVisible("Z_GearRRed1", false);
        this.mesh.chunkVisible("Z_GearLGreen1", false);
        this.mesh.chunkVisible("Z_GearRGreen1", false);
      }
      if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
        this.mesh.chunkVisible("zFuelWarning1", this.fm.M.fuel < 600.0F);
        this.mesh.chunkVisible("zFuelWarning2", this.fm.M.fuel < 600.0F);
      }
    }

    this.mesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));
    this.mesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl));
    if (this.fm.CT.getRudder() > 0.0F) {
      this.mesh.chunkSetAngles("zPedalL", 0.0F, 0.0F, -10.0F * this.fm.CT.getRudder());
      this.mesh.chunkSetAngles("zPedalR", 0.0F, 0.0F, -45.0F * this.fm.CT.getRudder());
    } else {
      this.mesh.chunkSetAngles("zPedalL", 0.0F, 0.0F, -45.0F * this.fm.CT.getRudder());
      this.mesh.chunkSetAngles("zPedalR", 0.0F, 0.0F, -10.0F * this.fm.CT.getRudder());
    }

    this.mesh.chunkSetAngles("zTurretA", 0.0F, this.fm.turret[0].tu[0], 0.0F);
    this.mesh.chunkSetAngles("zTurretB", 0.0F, this.fm.turret[0].tu[1], 0.0F);

    this.mesh.chunkSetAngles("zOilFlap1", 0.0F, 0.0F, -50.0F * this.fm.EI.engines[0].getControlRadiator());
    this.mesh.chunkSetAngles("zOilFlap2", 0.0F, 0.0F, -50.0F * this.fm.EI.engines[1].getControlRadiator());
    this.mesh.chunkSetAngles("zMix1", 0.0F, 0.0F, -30.0F * this.fm.EI.engines[0].getControlMix());
    this.mesh.chunkSetAngles("zMix2", 0.0F, 0.0F, -30.0F * this.fm.EI.engines[1].getControlMix());
    this.mesh.chunkSetAngles("zFlaps1", 0.0F, 0.0F, -45.0F * this.fm.CT.FlapsControl);
    if (this.fm.EI.engines[0].getControlProp() >= 0.0F) this.mesh.chunkSetAngles("zPitch1", 0.0F, 0.0F, -65.0F * this.fm.EI.engines[0].getControlProp());
    if (this.fm.EI.engines[1].getControlProp() >= 0.0F) this.mesh.chunkSetAngles("zPitch2", 0.0F, 0.0F, -65.0F * this.fm.EI.engines[1].getControlProp());
    this.mesh.chunkSetAngles("zThrottle1", 0.0F, 0.0F, -33.599998F * interp(this.setNew.throttlel, this.setOld.throttlel, paramFloat));
    this.mesh.chunkSetAngles("zThrottle2", 0.0F, 0.0F, -33.599998F * interp(this.setNew.throttler, this.setOld.throttler, paramFloat));
    this.mesh.chunkSetAngles("zCompressor1", 0.0F, 0.0F, -25.0F * this.fm.EI.engines[0].getControlCompressor());
    this.mesh.chunkSetAngles("zCompressor2", 0.0F, 0.0F, -25.0F * this.fm.EI.engines[1].getControlCompressor());

    this.mesh.chunkSetAngles("zHour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zMinute", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("zSecond", cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAH1", 0.0F, 0.0F, this.fm.Or.getKren());
    this.mesh.chunkSetAngles("zAH2", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -30.0F, 30.0F, -6.5F, 6.5F));

    this.mesh.chunkSetAngles("zTurnBank", cvt(this.setNew.turn, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);

    float f1 = getBall(4.5D);
    this.mesh.chunkSetAngles("zBall", cvt(f1, -4.0F, 4.0F, -8.0F, 8.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zBall2", cvt(f1, -4.5F, 4.5F, -9.0F, 9.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zVSI", cvt(this.setNew.vspeed, -15.0F, 15.0F, -160.0F, 160.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zSpeed", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 400.0F, 0.0F, 16.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("zCompass", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zRepeater", -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.waypointAzimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_Compass5", this.setNew.radioCompassAzimuth.getDeg(paramFloat * 0.02F) + 90.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zMagnetic", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zNavP", -(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) - this.setNew.azimuth.getDeg(paramFloat)), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("zRepeater", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zCompass", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("zMagnetic", -this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("zNavP", -this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("zRPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zRPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zBoost1", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zBoost2", cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 332.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOilTemp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 130.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOilTemp2", cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 100.0F, 0.0F, 130.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCoolant1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 100.0F, 0.0F, 126.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zCoolant2", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 100.0F, 0.0F, 126.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP1-1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP1-2", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP1-3", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP1-4", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP2-1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP2-2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP2-3", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 3.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zOFP2-4", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut, 0.0F, 15.0F, 0.0F, 135.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFuel1", cvt(this.fm.M.fuel / 0.72F, 0.0F, 2000.0F, 0.0F, 140.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFuel2", cvt(this.fm.M.fuel / 0.72F, 0.0F, 2000.0F, 0.0F, 140.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zExtT", cvt(Atmosphere.temperature((float)this.fm.Loc.z), 223.09F, 323.09F, -145.0F, 145.0F), 0.0F, 0.0F);

    float f2 = (float)Math.toDegrees(this.fm.EI.engines[0].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin());
    f2 = (int)(-f2 / 0.2F) * 0.2F;
    this.mesh.chunkSetAngles("zProp1-1", f2 * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zProp1-2", f2 * 5.0F, 0.0F, 0.0F);

    f2 = (float)Math.toDegrees(this.fm.EI.engines[1].getPropPhi() - this.fm.EI.engines[0].getPropPhiMin());
    f2 = (int)(-f2 / 0.2F) * 0.2F;
    this.mesh.chunkSetAngles("zProp2-1", f2 * 60.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zProp2-2", f2 * 5.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFlapsIL", 145.0F * this.fm.CT.getFlap(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zFlapsIR", 145.0F * this.fm.CT.getFlap(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("AFN1", 0.0F, cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -14.0F, 14.0F), 0.0F);
    this.mesh.chunkSetAngles("AFN2", 0.0F, cvt(this.setNew.beaconRange, 0.0F, 1.0F, 26.5F, -26.5F), 0.0F);

    this.mesh.chunkVisible("AFN1_RED", isOnBlindLandingMarker());
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("ZHolesL_D1", true);
      this.mesh.chunkVisible("PanelL_D1", true);
      this.mesh.chunkVisible("PanelL_D0", false);
      this.mesh.chunkVisible("zVSI", false);
      this.mesh.chunkVisible("zBlip1", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("ZHolesL_D2", true);
      this.mesh.chunkVisible("PanelFloat_D1", true);
      this.mesh.chunkVisible("PanelFloat_D0", false);
      this.mesh.chunkVisible("zProp1-1", false);
      this.mesh.chunkVisible("zProp1-2", false);
      this.mesh.chunkVisible("zProp2-1", false);
      this.mesh.chunkVisible("zProp2-2", false);
      this.mesh.chunkVisible("zFlapsIL", false);
      this.mesh.chunkVisible("zFlapsIR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("ZHolesR_D1", true);
      this.mesh.chunkVisible("PanelR_D1", true);
      this.mesh.chunkVisible("PanelR_D0", false);
      this.mesh.chunkVisible("zRPM1", false);
      this.mesh.chunkVisible("zBoost2", false);
      this.mesh.chunkVisible("zOilTemp2", false);
      this.mesh.chunkVisible("zCoolant1", false);
      this.mesh.chunkVisible("zOFP1-1", false);
      this.mesh.chunkVisible("zOFP1-2", false);
      this.mesh.chunkVisible("zFlapsIR", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("ZHolesR_D2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("ZHolesF_D1", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("PanelT_D1", true);
      this.mesh.chunkVisible("PanelT_D0", false);
      this.mesh.chunkVisible("zFuel2", false);
      this.mesh.chunkVisible("zOFP1-3", false);
      this.mesh.chunkVisible("zOFP1-4", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("zOil_D1", true);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.0032F, 7.2F);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
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
      CockpitHE_111H6.access$102(CockpitHE_111H6.this, CockpitHE_111H6.this.setOld); CockpitHE_111H6.access$202(CockpitHE_111H6.this, CockpitHE_111H6.this.setNew); CockpitHE_111H6.access$302(CockpitHE_111H6.this, CockpitHE_111H6.this.setTmp);

      CockpitHE_111H6.this.setNew.altimeter = CockpitHE_111H6.this.fm.getAltitude();
      CockpitHE_111H6.this.setNew.throttlel = ((10.0F * CockpitHE_111H6.this.setOld.throttlel + CockpitHE_111H6.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);
      CockpitHE_111H6.this.setNew.throttler = ((10.0F * CockpitHE_111H6.this.setOld.throttler + CockpitHE_111H6.this.fm.EI.engines[1].getControlThrottle()) / 11.0F);

      CockpitHE_111H6.this.w.set(CockpitHE_111H6.this.fm.getW());
      CockpitHE_111H6.this.fm.Or.transform(CockpitHE_111H6.this.w);
      CockpitHE_111H6.this.setNew.turn = ((12.0F * CockpitHE_111H6.this.setOld.turn + CockpitHE_111H6.this.w.z) / 13.0F);

      float f = CockpitHE_111H6.this.waypointAzimuth();
      if (CockpitHE_111H6.this.useRealisticNavigationInstruments())
      {
        CockpitHE_111H6.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitHE_111H6.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        CockpitHE_111H6.this.setNew.radioCompassAzimuth.setDeg(CockpitHE_111H6.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitHE_111H6.this.radioCompassAzimuthInvertMinus() - CockpitHE_111H6.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
      }
      else
      {
        CockpitHE_111H6.this.setNew.waypointAzimuth.setDeg(CockpitHE_111H6.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitHE_111H6.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitHE_111H6.this.setNew.azimuth.setDeg(CockpitHE_111H6.this.setOld.azimuth.getDeg(1.0F), CockpitHE_111H6.this.fm.Or.azimut());

      CockpitHE_111H6.this.setNew.vspeed = ((499.0F * CockpitHE_111H6.this.setOld.vspeed + CockpitHE_111H6.this.fm.getVertSpeed()) / 500.0F);

      CockpitHE_111H6.this.setNew.beaconDirection = ((10.0F * CockpitHE_111H6.this.setOld.beaconDirection + CockpitHE_111H6.this.getBeaconDirection()) / 11.0F);
      CockpitHE_111H6.this.setNew.beaconRange = ((10.0F * CockpitHE_111H6.this.setOld.beaconRange + CockpitHE_111H6.this.getBeaconRange()) / 11.0F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    float throttlel;
    float throttler;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork radioCompassAzimuth;
    float beaconDirection;
    float beaconRange;
    float turn;
    float vspeed;
    private final CockpitHE_111H6 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitHE_111H6.1 arg2)
    {
      this();
    }
  }
}