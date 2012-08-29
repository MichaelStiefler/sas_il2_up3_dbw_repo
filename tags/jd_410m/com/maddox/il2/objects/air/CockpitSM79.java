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
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitSM79 extends CockpitPilot
{
  private LightPointActor light1;
  private LightPointActor light2;
  private boolean firstEnter = true;

  private static final float[] speedometerScale = { 0.0F, 10.0F, 20.0F, 30.0F, 50.0F, 68.0F, 88.0F, 109.0F, 126.0F, 142.0F, 159.0F, 176.0F, 190.0F, 206.0F, 220.0F, 238.0F, 253.0F, 270.0F, 285.0F, 300.0F, 312.0F, 325.0F, 337.0F, 350.0F, 360.0F };

  private static final float[] oilTempScale = { 0.0F, 26.0F, 54.0F, 95.0F, 154.0F, 244.0F, 359.0F };
  private boolean bNeedSetUp;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictManf1;
  private float pictManf2;
  private Point3d tmpP;
  private Vector3d tmpV;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      ((SM79)this.fm.actor).bPitUnfocused = false;

      aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", false);

      aircraft().hierMesh().chunkVisible("fakePilot3_D0", true);

      if (!((SM79)this.fm.actor).bPilot3Killed)
      {
        this.mesh.chunkVisible("TailGunner", true);
      }

      if (this.firstEnter)
      {
        if (aircraft().thisWeaponsName.startsWith("1x"))
        {
          this.mesh.chunkVisible("Torpsight_Base", true);
          this.mesh.chunkVisible("Torpsight_Knob", true);
          this.mesh.chunkVisible("Torpsight_Rot", true);
        }
        this.firstEnter = false;
      }

      this.mesh.chunkVisible("Tur2_DoorR_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.mesh.chunkVisible("Tur2_DoorR_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.mesh.chunkVisible("Tur2_DoorL_Int", aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));
      this.mesh.chunkVisible("Tur2_DoorL_open_Int", !aircraft().hierMesh().isChunkVisible("Tur2_DoorR_D0"));

      this.mesh.chunkVisible("Tur2_Door1_Int", true);
      this.mesh.chunkVisible("Tur2_Door2_Int", true);
      this.mesh.chunkVisible("Tur2_Door3_Int", true);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", false);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", false);
      aircraft().hierMesh().chunkVisible("WingWireL_D0", false);

      aircraft().hierMesh().chunkVisible("Interior1_D0", false);

      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      ((SM79)this.fm.actor).bPitUnfocused = true;

      boolean bool = aircraft().isChunkAnyDamageVisible("Tail1_D");

      this.mesh.chunkVisible("TailGunner", false);
      if (!this.fm.AS.isPilotParatrooper(2))
      {
        aircraft().hierMesh().chunkVisible("Pilot3_D0", !((SM79)this.fm.actor).bPilot3Killed);
        aircraft().hierMesh().chunkVisible("Pilot3_D1", ((SM79)this.fm.actor).bPilot3Killed);
      }

      if (bool)
      {
        aircraft().hierMesh().chunkVisible("Tur1_DoorL_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));

        aircraft().hierMesh().chunkVisible("Tur1_DoorR_D0", !aircraft().hierMesh().isChunkVisible("Tur1_DoorR_open_D0"));
      }

      aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
      aircraft().hierMesh().chunkVisible("Turret2B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret3B_D0", bool);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", bool);

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", !this.mesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", this.mesh.isChunkVisible("Tur2_DoorR_open_Int"));

      aircraft().hierMesh().chunkVisible("Tur2_Door1_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door2_D0", true);
      aircraft().hierMesh().chunkVisible("Tur2_Door3_D0", true);
      aircraft().hierMesh().chunkVisible("WingWireL_D0", true);

      this.mesh.chunkVisible("Tur2_Door1_Int", false);
      this.mesh.chunkVisible("Tur2_Door2_Int", false);
      this.mesh.chunkVisible("Tur2_Door3_Int", false);
      this.mesh.chunkVisible("Tur2_DoorL_open_Int", false);
      this.mesh.chunkVisible("Tur2_DoorR_open_Int", false);

      aircraft().hierMesh().chunkVisible("Interior1_D0", true);

      super.doFocusLeave();
    }
  }

  public void toggleDim() {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  protected void mydebugcockpit(String paramString)
  {
  }

  protected float waypointAzimuth()
  {
    return waypointAzimuthInvertMinus(5.0F);
  }

  public CockpitSM79()
  {
    super("3DO/Cockpit/SM79Pilot/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.pictFlap = 0.0F;
    this.pictGear = 0.0F;
    this.pictManf1 = 1.0F;
    this.pictManf2 = 1.0F;
    this.tmpP = new Point3d();
    this.tmpV = new Vector3d();

    HookNamed localHookNamed1 = new HookNamed(this.mesh, "LAMPHOOK1");
    Loc localLoc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed1.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc1);

    this.light1 = new LightPointActor(new LightPoint(), localLoc1.getPoint());
    this.light1.light.setColor(109.0F, 99.0F, 90.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    HookNamed localHookNamed2 = new HookNamed(this.mesh, "LAMPHOOK2");
    Loc localLoc2 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed2.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc2);

    this.light2 = new LightPointActor(new LightPoint(), localLoc2.getPoint());
    this.light2.light.setColor(109.0F, 99.0F, 90.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK2", this.light2);

    this.cockpitNightMats = new String[] { "SM79_gauges_1", "SM79_gauges_2", "SM79_gauges_3", "SM79_gauges_1_dmg", "Ita_Needles" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    loadBuzzerFX();
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (aircraft().thisWeaponsName.startsWith("1x"))
    {
      this.mesh.chunkSetAngles("Torpsight_Rot", 0.0F, ((SM79)aircraft()).fSightCurSideslip, 0.0F);

      this.mesh.chunkSetAngles("Torpsight_Knob", 0.0F, 2.0F * ((SM79)aircraft()).fSightCurSideslip, 0.0F);
    }

    aircraft().hierMesh().chunkVisible("Tur2_DoorL_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorL_open_D0", false);
    aircraft().hierMesh().chunkVisible("Tur2_DoorR_open_D0", false);

    float f1 = this.fm.CT.getCockpitDoor();
    if (f1 < 0.99F)
    {
      if (!this.mesh.isChunkVisible("Tur2_DoorR_Int"))
      {
        this.mesh.chunkVisible("Tur2_DoorR_Int", true);
        this.mesh.chunkVisible("Tur2_DoorR_open_Int", false);
        this.mesh.chunkVisible("Tur2_DoorL_Int", true);
        this.mesh.chunkVisible("Tur2_DoorL_open_Int", false);
      }
      f2 = 13.8F * f1;
      this.mesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -f2, 0.0F);
      f2 = 8.8F * f1;
      this.mesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -f2, 0.0F);
      f2 = 3.1F * f1;
      this.mesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -f2, 0.0F);
      f2 = 14.0F * f1;
      this.mesh.chunkSetAngles("Tur2_DoorL_Int", 0.0F, -f2, 0.0F);
      this.mesh.chunkSetAngles("Tur2_DoorR_Int", 0.0F, f2, 0.0F);
    }
    else
    {
      this.mesh.chunkVisible("Tur2_DoorR_Int", false);
      this.mesh.chunkVisible("Tur2_DoorR_open_Int", true);
      this.mesh.chunkVisible("Tur2_DoorL_Int", false);
      this.mesh.chunkVisible("Tur2_DoorL_open_Int", true);
      this.mesh.chunkSetAngles("Tur2_Door1_Int", 0.0F, -13.8F, 0.0F);
      this.mesh.chunkSetAngles("Tur2_Door2_Int", 0.0F, -8.8F, 0.0F);
      this.mesh.chunkSetAngles("Tur2_Door3_Int", 0.0F, -3.1F, 0.0F);
    }

    this.mesh.chunkSetAngles("Mirino_02", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 80.0F), 0.0F);

    this.mesh.chunkSetAngles("ZSpeedL", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("ZSpeedR", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 460.0F, 0.0F, 23.0F), speedometerScale), 0.0F);

    this.mesh.chunkVisible("ZlampRedL", (this.fm.CT.getPowerControl() < 0.15F) && (this.fm.CT.getGear() < 0.99F));
    this.mesh.chunkVisible("ZlampRedR", (this.fm.CT.getPowerControl() < 0.15F) && (this.fm.CT.getGear() < 0.99F));
    buzzerFX((this.fm.CT.getPowerControl() < 0.15F) && (this.fm.CT.getGear() < 0.99F));
    this.mesh.chunkVisible("ZlampGreenL", (this.fm.CT.getPowerControl() < 0.15F) && (this.fm.CT.getGear() > 0.99F));
    this.mesh.chunkVisible("ZlampGreenR", (this.fm.CT.getPowerControl() < 0.15F) && (this.fm.CT.getGear() > 0.99F));
    this.mesh.chunkSetAngles("Mplane_LandingGear_L", 0.0F, 0.0F, 10.0F * this.fm.CT.getGear());
    this.mesh.chunkSetAngles("Mplane_LandingGear_R", 0.0F, 0.0F, 10.0F * this.fm.CT.getGear());
    this.mesh.chunkSetAngles("Mplane_LandingGear_L1", 0.0F, 90.0F * this.fm.CT.getGear(), 0.0F);
    this.mesh.chunkSetAngles("Mplane_LandingGear_R1", 0.0F, -90.0F * this.fm.CT.getGear(), 0.0F);

    this.mesh.chunkSetAngles("Zgear", 30.0F * (this.pictGear = 0.89F * this.pictGear + 0.11F * this.fm.CT.GearControl), 0.0F, 0.0F);

    float f2 = interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat) * 0.036F;
    this.mesh.chunkSetAngles("Zalt1L", 0.0F, f2 * 10.0F, 0.0F);
    this.mesh.chunkSetAngles("Zalt1R", 0.0F, f2 * 10.0F, 0.0F);

    this.mesh.chunkSetAngles("Zalt2L", 0.0F, f2, 0.0F);
    this.mesh.chunkSetAngles("Zalt2R", 0.0F, f2, 0.0F);

    this.mesh.chunkSetAngles("ZclockHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("ZclockMin", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    float f3 = 0.104F;

    this.mesh.chunkSetAngles("ZRPML", 0.0F, this.fm.EI.engines[0].getRPM() * f3, 0.0F);
    this.mesh.chunkSetAngles("ZRPMC", 0.0F, this.fm.EI.engines[1].getRPM() * f3, 0.0F);
    this.mesh.chunkSetAngles("ZRPMR", 0.0F, this.fm.EI.engines[2].getRPM() * f3, 0.0F);

    this.mesh.chunkSetAngles("ZbarometrL", 0.0F, cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);
    this.mesh.chunkSetAngles("ZbarometrC", 0.0F, cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);
    this.mesh.chunkSetAngles("ZbarometrR", 0.0F, cvt(this.fm.EI.engines[2].getManifoldPressure(), 0.0F, 2.0F, 0.0F, 312.0F), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrEL", 0.0F, floatindex(cvt(this.fm.EI.engines[0].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrUL", 0.0F, floatindex(cvt(this.fm.EI.engines[0].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrEC", 0.0F, floatindex(cvt(this.fm.EI.engines[1].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrUC", 0.0F, floatindex(cvt(this.fm.EI.engines[1].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrER", 0.0F, floatindex(cvt(this.fm.EI.engines[2].tOilIn, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZbarometrUR", 0.0F, floatindex(cvt(this.fm.EI.engines[2].tOilOut, 40.0F, 160.0F, 0.0F, 6.0F), oilTempScale), 0.0F);

    this.mesh.chunkSetAngles("ZoilL", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);
    this.mesh.chunkSetAngles("ZoilC", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);
    this.mesh.chunkSetAngles("ZoilR", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[2].tOilOut * this.fm.EI.engines[2].getReadyness(), 0.0F, 10.0F, 0.0F, 300.0F), 0.0F);

    this.mesh.chunkSetAngles("ZfuelL", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("ZfuelC", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);
    this.mesh.chunkSetAngles("ZfuelR", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.78F, 0.0F, 3.25F, 0.0F, 270.0F), 0.0F);

    float f4 = this.setNew.vspeed * 18.0F;
    this.mesh.chunkSetAngles("ZclimbL", 0.0F, f4, 0.0F);
    this.mesh.chunkSetAngles("ZclimbR", 0.0F, f4, 0.0F);

    this.mesh.chunkSetAngles("ZThrottleL", 0.0F, -49.0F * interp(this.setNew.throttleL, this.setOld.throttleL, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("ZThrottleC", 0.0F, -49.0F * interp(this.setNew.throttleC, this.setOld.throttleC, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("ZThrottleR", 0.0F, -49.0F * interp(this.setNew.throttleR, this.setOld.throttleR, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("MixturreL", 0.0F, 35.0F * interp(this.setNew.MixL, this.setOld.MixL, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("MixturreC", 0.0F, 35.0F * interp(this.setNew.MixC, this.setOld.MixC, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("MixturreR", 0.0F, 35.0F * interp(this.setNew.MixR, this.setOld.MixR, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("PitchL", 0.0F, -40.0F * interp(this.setNew.propL, this.setOld.propL, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("PitchC", 0.0F, -40.0F * interp(this.setNew.propC, this.setOld.propC, paramFloat), 0.0F);
    this.mesh.chunkSetAngles("PitchR", 0.0F, -40.0F * interp(this.setNew.propR, this.setOld.propR, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Wheel_PilotL", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 68.0F, 0.0F);
    this.mesh.chunkSetAngles("Wheel_PilotR", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 68.0F, 0.0F);

    this.mesh.chunkSetAngles("Wheel_Stick", 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_BrkpresL", 0.0F, 7.0F * this.fm.CT.getBrake(), 0.0F);
    this.mesh.chunkSetAngles("Z_BrkpresR", 0.0F, -7.0F * this.fm.CT.getBrake(), 0.0F);

    resetYPRmodifier();
    float f5 = 0.07F * this.fm.CT.getRudder();
    Cockpit.xyz[1] = f5;
    this.mesh.chunkSetLocate("Pedal_LeftL", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetLocate("Pedal_LeftR", Cockpit.xyz, Cockpit.ypr);
    Cockpit.xyz[1] = (-f5);
    this.mesh.chunkSetLocate("Pedal_RightL", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetLocate("Pedal_RightR", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("ZmagnetoL", 0.0F, cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);
    this.mesh.chunkSetAngles("ZmagnetoC", 0.0F, cvt(this.fm.EI.engines[1].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);
    this.mesh.chunkSetAngles("ZmagnetoR", 0.0F, cvt(this.fm.EI.engines[2].getControlMagnetos(), 0.0F, 3.0F, 0.0F, 110.0F), 0.0F);

    this.mesh.chunkSetAngles("Zdirectional_GiroC", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Zdirectional_GiroL", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("Zdirectional_GiroR", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);

    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Zbank2L", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.mesh.chunkSetAngles("Zbank1L", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -20.5F, 20.5F), 0.0F);
    this.mesh.chunkSetAngles("Zbank2R", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 23.0F, -23.0F), 0.0F);
    this.mesh.chunkSetAngles("Zbank1R", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -20.5F, 20.5F), 0.0F);

    this.mesh.chunkSetAngles("ZAH1R", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.011F, -0.015F);
    this.mesh.chunkSetLocate("ZcompassR", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("zAH1L", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.02F, -0.02F);
    this.mesh.chunkSetLocate("ZcompassL", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_plane_FuelGaugeL", 0.0F, cvt(this.fm.M.fuel, 0.0F, 1720.0F, 0.0F, 245.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_plane_FuelGaugeR", 0.0F, cvt(this.fm.M.fuel, 0.0F, 1720.0F, 0.0F, 245.0F), 0.0F);

    this.mesh.chunkSetAngles("Ztrim1", 0.0F, cvt(interp(this.setNew.elevTrim, this.setOld.elevTrim, paramFloat), -0.5F, 0.5F, -750.0F, 750.0F), 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[1] = (-cvt(interp(this.setNew.elevTrim, this.setOld.elevTrim, paramFloat), -0.5F, 0.5F, -0.057F, 0.058F));
    this.mesh.chunkSetLocate("Z_Trim_Indicator", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_OAT", 0.0F, cvt(Atmosphere.temperature((float)this.fm.Loc.z) - 273.14999F, -40.0F, 40.0F, 0.0F, 93.0F), 0.0F);
    float f6;
    if (useRealisticNavigationInstruments())
    {
      f6 = -cvt(this.setNew.beaconDirection, -55.0F, 55.0F, -55.0F, 55.0F);
      this.mesh.chunkSetAngles("Zcourse", 0.0F, f6, 0.0F);
    }
    else
    {
      f6 = -cvt(this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), -55.0F, 55.0F, -55.0F, 55.0F);
      this.mesh.chunkSetAngles("Zcourse", 0.0F, f6, 0.0F);
    }
  }

  public void reflectCockpitState()
  {
    if (!this.fm.AS.isPilotDead(2))
      this.mesh.chunkVisible("TailGunner", true);
    else {
      this.mesh.chunkVisible("TailGunner", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Line01", false);
      this.mesh.chunkVisible("Panel_dmg", true);
      this.mesh.chunkVisible("PressL_dmg", true);
      this.mesh.chunkVisible("XGlassHoles1", true);
      this.mesh.chunkVisible("RPMC_dmg", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Line01", false);
      this.mesh.chunkVisible("Panel_dmg", true);
      this.mesh.chunkVisible("AltL_dmg", true);
      this.mesh.chunkVisible("RPMC_dmg", true);
      this.mesh.chunkVisible("OilTempC_dmg", true);
      this.mesh.chunkVisible("XGlassHoles2", true);
      this.mesh.chunkVisible("XGlassHoles1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("Line01", false);
      this.mesh.chunkVisible("Panel_dmg", true);
      this.mesh.chunkVisible("ClockL_dmg", true);
      this.mesh.chunkVisible("XGlassHoles2", true);
      this.mesh.chunkVisible("XGlassHoles1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) != 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0))
    {
      this.mesh.chunkVisible("XGlassHoles2", true);
      this.mesh.chunkVisible("PressL_dmg", true);
      this.mesh.chunkVisible("OilTempC_dmg", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("Line01", false);
      this.mesh.chunkVisible("Panel_dmg", true);
      this.mesh.chunkVisible("ClockL_dmg", true);
      this.mesh.chunkVisible("AltL_dmg", true);
      this.mesh.chunkVisible("XGlassHoles2", true);
      this.mesh.chunkVisible("XGlassHoles1", true);
      this.mesh.chunkVisible("RPMC_dmg", true);
      this.mesh.chunkVisible("PressL_dmg", true);
    }
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      this.light1.light.setEmit(0.0032F, 7.2F);
      this.light2.light.setEmit(0.003F, 7.0F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private void retoggleLight()
  {
    if (this.cockpitLightControl)
    {
      setNightMats(false);
      setNightMats(true);
    }
    else {
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
  }

  private class Variables
  {
    float dimPos;
    float throttleC;
    float throttleR;
    float throttleL;
    float propC;
    float propL;
    float propR;
    float MixC;
    float MixL;
    float MixR;
    float altimeter;
    float elevTrim;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float beaconDirection;
    private final CockpitSM79 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.dimPos = 0.0F;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitSM79.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitSM79.this.fm != null)
      {
        CockpitSM79.access$002(CockpitSM79.this, CockpitSM79.this.setOld);
        CockpitSM79.access$102(CockpitSM79.this, CockpitSM79.this.setNew);
        CockpitSM79.access$202(CockpitSM79.this, CockpitSM79.this.setTmp);

        if (CockpitSM79.this.cockpitDimControl)
        {
          if (CockpitSM79.this.setNew.dimPos < 1.0F)
            CockpitSM79.this.setNew.dimPos = (CockpitSM79.this.setOld.dimPos + 0.03F);
        } else if (CockpitSM79.this.setNew.dimPos > 0.0F) {
          CockpitSM79.this.setNew.dimPos = (CockpitSM79.this.setOld.dimPos - 0.03F);
        }
        CockpitSM79.this.setNew.throttleC = (0.85F * CockpitSM79.this.setOld.throttleC + CockpitSM79.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.throttleL = (0.85F * CockpitSM79.this.setOld.throttleL + CockpitSM79.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.throttleR = (0.85F * CockpitSM79.this.setOld.throttleR + CockpitSM79.this.fm.EI.engines[2].getControlThrottle() * 0.15F);
        CockpitSM79.this.setNew.propC = (0.85F * CockpitSM79.this.setOld.propC + CockpitSM79.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.propL = (0.85F * CockpitSM79.this.setOld.propL + CockpitSM79.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.propR = (0.85F * CockpitSM79.this.setOld.propR + CockpitSM79.this.fm.EI.engines[2].getControlProp() * 0.15F);
        CockpitSM79.this.setNew.MixC = (0.85F * CockpitSM79.this.setOld.MixC + CockpitSM79.this.fm.EI.engines[1].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.MixL = (0.85F * CockpitSM79.this.setOld.MixL + CockpitSM79.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.MixR = (0.85F * CockpitSM79.this.setOld.MixR + CockpitSM79.this.fm.EI.engines[2].getControlMix() * 0.15F);
        CockpitSM79.this.setNew.elevTrim = (0.85F * CockpitSM79.this.setOld.elevTrim + CockpitSM79.this.fm.CT.getTrimElevatorControl() * 0.15F);
        CockpitSM79.this.setNew.altimeter = CockpitSM79.this.fm.getAltitude();
        CockpitSM79.this.setNew.azimuth.setDeg(CockpitSM79.this.setOld.azimuth.getDeg(1.0F), 90.0F + CockpitSM79.this.fm.Or.azimut());
        float f = CockpitSM79.this.waypointAzimuth();

        CockpitSM79.this.setNew.vspeed = ((199.0F * CockpitSM79.this.setOld.vspeed + CockpitSM79.this.fm.getVertSpeed()) / 200.0F);

        if (CockpitSM79.this.useRealisticNavigationInstruments())
        {
          CockpitSM79.this.setNew.beaconDirection = ((10.0F * CockpitSM79.this.setOld.beaconDirection + CockpitSM79.this.getBeaconDirection()) / 11.0F);
        }
        else
        {
          CockpitSM79.this.setNew.waypointAzimuth.setDeg(CockpitSM79.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitSM79.this.setOld.azimuth.getDeg(1.0F) + 90.0F);
        }
      }

      return true;
    }

    Interpolater()
    {
    }
  }
}