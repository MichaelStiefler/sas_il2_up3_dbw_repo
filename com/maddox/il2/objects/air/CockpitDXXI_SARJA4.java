package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
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
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

public class CockpitDXXI_SARJA4 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float flaps = 0.0F;
  private float pictManifold;
  private boolean bEntered = false;
  private float saveFov;
  private boolean hasRevi = true;
  private float tailWheelLock = 1.0F;
  private int flapsDirection = 0;
  private float flapsPump = 0.0F;
  private float flapsPumpIncrement = 0.1F;
  private LightPointActor light1;
  private LightPointActor light2;
  private LightPointActor light3;
  private LightPointActor light4;
  private LightPointActor light5;

  public CockpitDXXI_SARJA4()
  {
    super("3DO/Cockpit/DXXI_SARJA3_EARLY/hier.him", "bf109");
    setRevi();

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK01");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK01", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK02");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(126.0F, 232.0F, 245.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK02", this.light2);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK03");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light3 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light3.light.setColor(126.0F, 232.0F, 245.0F);
    this.light3.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK03", this.light3);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK04");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light4 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light4.light.setColor(126.0F, 232.0F, 245.0F);
    this.light4.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK04", this.light4);

    localHookNamed = new HookNamed(this.mesh, "LAMPHOOK05");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light5 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light5.light.setColor(126.0F, 232.0F, 245.0F);
    this.light5.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK05", this.light5);

    this.cockpitNightMats = new String[] { "gauge_speed", "gauge_alt", "gauge_fuel", "gauges_various_1", "gauges_various_2", "LABELS1", "gauges_various_3", "gauges_various4", "gauges_various_3_dam", "gauge_alt_dam", "gauges_various_2_dam" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void setRevi()
  {
    this.hasRevi = true;
    this.mesh.chunkVisible("reticle", true);
    this.mesh.chunkVisible("reticlemask", true);
    this.mesh.chunkVisible("Revi_D0", true);
    this.mesh.chunkVisible("Z_sight_cap", false);
    this.mesh.chunkVisible("tubeSight", false);
    this.mesh.chunkVisible("tubeSightLens", false);
    this.mesh.chunkVisible("tube_inside", false);
    this.mesh.chunkVisible("tube_mask", false);
    this.mesh.chunkVisible("Z_sight_cap_inside", false);
    this.mesh.chunkVisible("GlassTube", false);
    this.mesh.chunkVisible("GlassRevi", true);
    this.mesh.chunkVisible("Z_reviIron", true);
    this.mesh.chunkVisible("Z_reviDimmer", true);
    this.mesh.chunkVisible("Z_reviDimmerLever", true);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    float f1 = 0.0F;

    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Z_reviIron", 90.0F * this.setNew.stbyPosition, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_manifold", cvt(this.pictManifold = 0.85F * this.pictManifold + 0.15F * this.fm.EI.engines[0].getManifoldPressure() * 76.0F, 30.0F, 120.0F, 22.0F, 296.0F), 0.0F, 0.0F);

    f1 = -15.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl);

    this.mesh.chunkSetAngles("Z_stick_horiz_axis", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_aileron_rods", -f1 / 14.0F, 0.0F, 0.0F);

    f1 = 14.0F * (this.pictElev = 0.85F * this.pictElev + 0.2F * this.fm.CT.ElevatorControl);

    this.mesh.chunkSetAngles("Z_Stick", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_elev_wire1", -f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_elev_wire2", -f1, 0.0F, 0.0F);

    f1 = this.fm.CT.getRudder();
    this.mesh.chunkSetAngles("Z_wheel_break_valve", -12.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_pedal_L", 24.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_pedal_R", -24.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_rod_L", -25.0F * f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_rudder_rod_R", 25.0F * f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throttle", -70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Mixture", -70.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_alt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_alt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    float f2 = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());

    if (f2 > 360.0F) {
      this.mesh.chunkSetAngles("Z_Need_speed", cvt(f2, 360.0F, 600.0F, -329.0F, -550.0F), 0.0F, 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("Z_Need_speed", cvt(f2, 60.0F, 360.0F, 0.0F, -329.0F), 0.0F, 0.0F);
    }

    f1 = this.setNew.azimuth.getDeg(paramFloat);
    this.mesh.chunkSetAngles("Z_Need_compass", f1, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_rpm", cvt(this.fm.EI.engines[0].getRPM(), 440.0F, 3320.0F, 0.0F, -332.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_clock_hour", -cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_clock_minute", -cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_clock_sec", -cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_fuel", -cvt(this.fm.M.fuel, 0.0F, 300.0F, 0.0F, 52.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_oiltemp", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 329.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_oilpressure", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.0F, 0.0F, -315.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_cylheadtemp", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 110.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_fuelpressure", cvt(this.fm.M.fuel > 1.0F ? 0.78F : 0.0F, 0.0F, 4.0F, 0.0F, 100.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_magneto", -30.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    resetYPRmodifier();

    Cockpit.xyz[1] = (-cvt(this.fm.Or.getTangage(), -20.0F, 20.0F, 0.04F, -0.04F));

    this.mesh.chunkSetLocate("Z_Need_red_liquid", Cockpit.xyz, Cockpit.ypr);

    this.mesh.chunkSetAngles("Z_Need_Turn", cvt(this.setNew.turn, -0.2F, 0.2F, -22.5F, 22.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_bank", -cvt(getBall(8.0D), -8.0F, 8.0F, 16.9F, -16.9F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_variometer", -cvt(this.setNew.vspeed, -20.0F, 20.0F, 180.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_oxygeneflow", -260.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Need_oxygenetank", -320.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_flaps_indicator", 0.7F * this.flaps, 0.0F, 0.0F);

    if (this.flapsDirection == 1)
    {
      this.mesh.chunkSetAngles("Z_flaps_valve", -33.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_flapsLeverKnob", -33.0F, 0.0F, 0.0F);
    }
    else if (this.flapsDirection == -1)
    {
      this.mesh.chunkSetAngles("Z_flaps_valve", 33.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_flapsLeverKnob", 33.0F, 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_flaps_valve", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_flapsLeverKnob", 0.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_trim_indicator", 1.9F * -this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_trim_wheel", 600.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    if (this.fm.CT.bHasBrakeControl)
    {
      f1 = this.fm.CT.getBrake();

      this.mesh.chunkSetAngles("Z_break_handle", f1 * 20.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_Need_breakpressureR", cvt(f1 + f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_Need_breakpressureL", -cvt(f1 - f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_Need_breakpressure1", -150.0F + f1 * 20.0F, 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_flaps_pump", -this.flapsPump * 40.0F, 0.0F, 0.0F);

    if (this.fm.AS.bLandingLightOn)
      this.mesh.chunkSetAngles("Z_switch_landing_light", -35.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("Z_switch_landing_light", 0.0F, 0.0F, 0.0F);
    if (this.fm.AS.bNavLightsOn)
      this.mesh.chunkSetAngles("Z_switch_navigation_light", -35.0F, 0.0F, 0.0F);
    else
      this.mesh.chunkSetAngles("Z_switch_navigation_light", 0.0F, 0.0F, 0.0F);
    if (this.cockpitLightControl)
      this.mesh.chunkSetAngles("Z_switch_cockpit_light", -35.0F, 0.0F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Z_switch_cockpit_light", 0.0F, 0.0F, 0.0F);
    }

    if (this.tailWheelLock >= 1.0F)
    {
      this.mesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57.0F, 0.0F, 7.0F);
      this.mesh.chunkSetAngles("Z_tailwheel_lever_wire", this.tailWheelLock * 57.0F, 0.0F, 7.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_tailwheel", this.tailWheelLock * 57.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_tailwheel_lever_wire", this.tailWheelLock * 57.0F, 0.0F, 0.0F);
    }
    this.mesh.chunkSetAngles("Z_wheelLockKnob", this.tailWheelLock * 57.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Need_extinguisher", this.fm.EI.engines[0].getExtinguishers() * 95.0F, 0.0F, 0.0F);

    if (this.hasRevi)
    {
      this.mesh.chunkSetAngles("Z_reviDimmer", -cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -90.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_reviDimmerLever", -cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 0.004F), 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_sight_cap", cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -130.0F), 0.0F, 0.0F);

      this.mesh.chunkSetAngles("Z_sight_cap_big", 0.0F, cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -130.0F), 0.0F);

      this.mesh.chunkSetAngles("Z_sight_cap_inside", cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -130.0F), 0.0F, 0.0F);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
    {
      if (this.hasRevi)
      {
        this.mesh.chunkVisible("reticle", false);
        this.mesh.chunkVisible("reticlemask", false);
        this.mesh.chunkVisible("Revi_D0", false);
        this.mesh.chunkVisible("Revi_D1", true);
      }
      this.mesh.chunkVisible("GlassDamageFront2", true);
      this.mesh.chunkVisible("HullDamageRear", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("GlassDamageFront", true);
      this.mesh.chunkVisible("HullDamageRear", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("Gauges_d0", false);
      this.mesh.chunkVisible("Gauges_d1", true);
      this.mesh.chunkVisible("HullDamageFront", true);
      this.mesh.chunkVisible("Z_Need_manifold", false);
      this.mesh.chunkVisible("Z_Need_oilpressure", false);
      this.mesh.chunkVisible("Z_Need_rpm", false);
      this.mesh.chunkVisible("Z_Need_alt1", false);
      this.mesh.chunkVisible("Z_Need_alt2", false);
      this.mesh.chunkVisible("Z_Need_variometer", false);
      this.mesh.chunkVisible("Z_Need_clock_sec", false);
      this.mesh.chunkVisible("Z_Need_clock_minute", false);
      this.mesh.chunkVisible("Z_Need_clock_hour", false);
      this.mesh.chunkVisible("Z_Need_clock_timer", false);
      this.mesh.chunkVisible("Z_Need_cylheadtemp", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("GlassDamageLeft", true);
      this.mesh.chunkVisible("HullDamageLeft", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("GlassDamageLeft", true);
      this.mesh.chunkVisible("HullDamageLeft", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("GlassDamageRight", true);
      this.mesh.chunkVisible("HullDamageRight", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("GlassDamageRight", true);
      this.mesh.chunkVisible("HullDamageRight", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
    {
      if (this.hasRevi)
        this.mesh.chunkVisible("OilRevi", true);
      else
        this.mesh.chunkVisible("Oil", true);
    }
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("CF_D0", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2", localHierMesh.isChunkVisible("CF_D2"));
  }

  public void doToggleUp(boolean paramBoolean)
  {
    super.doToggleUp(paramBoolean);
    System.out.println("TOGGLE UP");
  }

  private void enter()
  {
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 31");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.mesh.chunkVisible("superretic", true);
    this.mesh.chunkVisible("Z_BoxTinter", true);
  }

  private void leave() {
    if (this.bEntered)
    {
      Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
      CmdEnv.top().exec("fov " + this.saveFov);
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
      localHookPilot.setSimpleUse(false);
      boolean bool = HotKeyEnv.isEnabled("aircraftView");
      HotKeyEnv.enable("PanView", bool);
      HotKeyEnv.enable("SnapView", bool);
      this.bEntered = false;
      this.mesh.chunkVisible("superretic", false);
      this.mesh.chunkVisible("Z_BoxTinter", false);
    }
  }

  public void destroy() {
    leave();
    super.destroy();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
    {
      if (paramBoolean)
        enter();
      else
        leave();
    }
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.mesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
  }

  protected boolean doFocusEnter()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("tail1_internal_d0", false);
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("tail1_internal_d0", true);
    if (isFocused()) {
      leave();
      super.doFocusLeave();
    }
  }

  public boolean isToggleUp()
  {
    System.out.println("isToggleUp");
    return super.isToggleUp();
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      this.light1.light.setEmit(0.005F, 0.2F);
      this.light2.light.setEmit(0.005F, 0.2F);
      this.light3.light.setEmit(0.005F, 0.2F);
      this.light4.light.setEmit(0.002F, 0.1F);
      this.light5.light.setEmit(0.005F, 0.2F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      this.light3.light.setEmit(0.0F, 0.0F);
      this.light4.light.setEmit(0.0F, 0.0F);
      this.light5.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  private class Variables
  {
    float altimeter;
    AnglesFork azimuth;
    float throttle;
    float mix;
    float prop;
    float turn;
    float vspeed;
    float stbyPosition;
    float dimPos;
    private final CockpitDXXI_SARJA4 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
    }

    Variables(CockpitDXXI_SARJA4.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitDXXI_SARJA4.this.bNeedSetUp)
      {
        CockpitDXXI_SARJA4.this.reflectPlaneMats();
        CockpitDXXI_SARJA4.access$102(CockpitDXXI_SARJA4.this, false);
      }

      CockpitDXXI_SARJA4.access$202(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.setOld);
      CockpitDXXI_SARJA4.access$302(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.setNew);
      CockpitDXXI_SARJA4.access$402(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.setTmp);
      if (((CockpitDXXI_SARJA4.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitDXXI_SARJA4.this.setNew.stbyPosition < 1.0F))
      {
        CockpitDXXI_SARJA4.this.setNew.stbyPosition = (CockpitDXXI_SARJA4.this.setOld.stbyPosition + 0.0125F);
        CockpitDXXI_SARJA4.this.setOld.stbyPosition = CockpitDXXI_SARJA4.this.setNew.stbyPosition;
      }
      CockpitDXXI_SARJA4.this.setNew.altimeter = CockpitDXXI_SARJA4.this.fm.getAltitude();

      if ((Math.abs(CockpitDXXI_SARJA4.this.fm.Or.getKren()) < 30.0F) && (Math.abs(CockpitDXXI_SARJA4.this.fm.Or.tangage()) < 30.0F)) {
        CockpitDXXI_SARJA4.this.setNew.azimuth.setDeg(CockpitDXXI_SARJA4.this.setOld.azimuth.getDeg(1.0F), CockpitDXXI_SARJA4.this.fm.Or.azimut());
      }

      CockpitDXXI_SARJA4.this.setNew.throttle = ((10.0F * CockpitDXXI_SARJA4.this.setOld.throttle + CockpitDXXI_SARJA4.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);

      CockpitDXXI_SARJA4.this.setNew.mix = ((10.0F * CockpitDXXI_SARJA4.this.setOld.mix + CockpitDXXI_SARJA4.this.fm.EI.engines[0].getControlMix()) / 11.0F);

      CockpitDXXI_SARJA4.this.setNew.prop = CockpitDXXI_SARJA4.this.setOld.prop;
      if (CockpitDXXI_SARJA4.this.setNew.prop < CockpitDXXI_SARJA4.this.fm.EI.engines[0].getControlProp() - 0.01F)
        CockpitDXXI_SARJA4.this.setNew.prop += 0.0025F;
      if (CockpitDXXI_SARJA4.this.setNew.prop > CockpitDXXI_SARJA4.this.fm.EI.engines[0].getControlProp() + 0.01F)
        CockpitDXXI_SARJA4.this.setNew.prop -= 0.0025F;
      CockpitDXXI_SARJA4.this.w.set(CockpitDXXI_SARJA4.this.fm.getW());
      CockpitDXXI_SARJA4.this.fm.Or.transform(CockpitDXXI_SARJA4.this.w);
      CockpitDXXI_SARJA4.this.setNew.turn = ((12.0F * CockpitDXXI_SARJA4.this.setOld.turn + CockpitDXXI_SARJA4.this.w.z) / 13.0F);
      CockpitDXXI_SARJA4.this.setNew.vspeed = ((299.0F * CockpitDXXI_SARJA4.this.setOld.vspeed + CockpitDXXI_SARJA4.this.fm.getVertSpeed()) / 300.0F);

      CockpitDXXI_SARJA4.access$602(CockpitDXXI_SARJA4.this, 0.8F * CockpitDXXI_SARJA4.this.pictSupc + 0.2F * CockpitDXXI_SARJA4.this.fm.EI.engines[0].getControlCompressor());

      if (CockpitDXXI_SARJA4.this.cockpitDimControl) {
        if (CockpitDXXI_SARJA4.this.setNew.dimPos < 1.0F)
          CockpitDXXI_SARJA4.this.setNew.dimPos = (CockpitDXXI_SARJA4.this.setOld.dimPos + 0.03F);
      } else if (CockpitDXXI_SARJA4.this.setNew.dimPos > 0.0F) {
        CockpitDXXI_SARJA4.this.setNew.dimPos = (CockpitDXXI_SARJA4.this.setOld.dimPos - 0.03F);
      }
      if (((CockpitDXXI_SARJA4.this.flaps > CockpitDXXI_SARJA4.this.fm.CT.FlapsControl - 0.05D) && (CockpitDXXI_SARJA4.this.flaps < CockpitDXXI_SARJA4.this.fm.CT.FlapsControl + 0.05D)) || (CockpitDXXI_SARJA4.this.fm.CT.bHasFlapsControlRed))
      {
        CockpitDXXI_SARJA4.access$802(CockpitDXXI_SARJA4.this, 0);
      }
      else if (CockpitDXXI_SARJA4.this.flaps < CockpitDXXI_SARJA4.this.fm.CT.FlapsControl)
      {
        CockpitDXXI_SARJA4.access$702(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.flaps + 0.00095F);
        CockpitDXXI_SARJA4.access$902(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.flapsPump + CockpitDXXI_SARJA4.this.flapsPumpIncrement);
        CockpitDXXI_SARJA4.access$802(CockpitDXXI_SARJA4.this, 1);
        if ((CockpitDXXI_SARJA4.this.flapsPump < 0.0F) || (CockpitDXXI_SARJA4.this.flapsPump > 1.0F))
          CockpitDXXI_SARJA4.access$1002(CockpitDXXI_SARJA4.this, -CockpitDXXI_SARJA4.this.flapsPumpIncrement);
      }
      else if (CockpitDXXI_SARJA4.this.flaps > CockpitDXXI_SARJA4.this.fm.CT.FlapsControl)
      {
        CockpitDXXI_SARJA4.access$702(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.flaps - 0.005F);
        CockpitDXXI_SARJA4.access$902(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.flapsPump + CockpitDXXI_SARJA4.this.flapsPumpIncrement);
        CockpitDXXI_SARJA4.access$802(CockpitDXXI_SARJA4.this, -1);
        if ((CockpitDXXI_SARJA4.this.flapsPump < 0.0F) || (CockpitDXXI_SARJA4.this.flapsPump > 1.0F)) {
          CockpitDXXI_SARJA4.access$1002(CockpitDXXI_SARJA4.this, -CockpitDXXI_SARJA4.this.flapsPumpIncrement);
        }
      }

      if ((!CockpitDXXI_SARJA4.this.fm.Gears.bTailwheelLocked) && (CockpitDXXI_SARJA4.this.tailWheelLock < 1.0F))
      {
        CockpitDXXI_SARJA4.access$1102(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.tailWheelLock + 0.05F);
      }
      else if ((CockpitDXXI_SARJA4.this.fm.Gears.bTailwheelLocked) && (CockpitDXXI_SARJA4.this.tailWheelLock > 0.0F))
      {
        CockpitDXXI_SARJA4.access$1102(CockpitDXXI_SARJA4.this, CockpitDXXI_SARJA4.this.tailWheelLock - 0.05F);
      }

      return true;
    }
  }
}