package com.maddox.il2.objects.air;

import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
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

public class CockpitSBD3 extends CockpitPilot
{
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  public Vector3f w;
  private float pictAiler;
  private float pictElev;
  private static final float[] speedometerScale = { 0.0F, 16.5F, 79.5F, 143.0F, 206.5F, 229.5F, 251.0F, 272.5F, 294.0F, 316.0F, 339.5F };

  private static final float[] variometerScale = { 0.0F, 25.0F, 49.5F, 64.0F, 78.5F, 89.5F, 101.0F, 112.0F, 123.0F, 134.5F, 145.5F, 157.0F, 168.0F, 168.0F };
  private float saveFov;
  private boolean bEntered;

  public CockpitSBD3()
  {
    super("3DO/Cockpit/SBD-3/hier.him", "bf109");
    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.w = new Vector3f();
    this.pictAiler = 0.0F;
    this.pictElev = 0.0F;
    this.bEntered = false;
    this.cockpitNightMats = new String[] { "GagePanel1", "GagePanel1_D1", "GagePanel2", "GagePanel2_D1", "GagePanel3", "GagePanel3_D1", "GagePanel4", "GagePanel4_D1", "misc2" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  public void reflectWorldToInstruments(float f)
  {
    resetYPRmodifier();
    Cockpit.xyz[1] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.7F);
    this.mesh.chunkSetLocate("Canopy", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Trim1", 0.0F, 1444.0F * this.fm.CT.getTrimAileronControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 0.0F, 1444.0F * this.fm.CT.getTrimRudderControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 0.0F, 1444.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);
    this.mesh.chunkSetAngles("Z_Flaps1", 0.0F, -77.0F * this.setNew.flaps, 0.0F);
    this.mesh.chunkSetAngles("Z_Gear1", 0.0F, -77.0F * this.setNew.gear, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 0.0F, -40.0F * this.setNew.throttle, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 0.0F, -68.0F * this.setNew.prop, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 0.0F, -55.0F * this.setNew.mix, 0.0F);
    this.mesh.chunkSetAngles("Z_Supercharger1", 0.0F, -55.0F * this.fm.EI.engines[0].getControlCompressor(), 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_LeftPedal", 0.0F, 20.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_DiveBrake1", 0.0F, -77.0F * this.setNew.divebrake, 0.0F);
    this.mesh.chunkSetAngles("Z_CowlFlap1", 0.0F, -28.0F * this.fm.EI.engines[0].getControlRadiator(), 0.0F);
    if ((this.fm.CT.Weapons[3] != null) && (!this.fm.CT.Weapons[3][0].haveBullets()))
      this.mesh.chunkSetAngles("Z_Bomb1", 0.0F, 35.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 30480.0F, 0.0F, -3600.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, f), 0.0F, 30480.0F, 0.0F, -36000.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 925.99982F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -16.0F, 16.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Skid", 0.0F, cvt(getBall(6.0D), -6.0F, 6.0F, -27.0F, 27.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", 0.0F, -this.fm.Or.getKren(), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[2] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.025F, -0.025F);
    this.mesh.chunkSetLocate("Z_TurnBank4", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, this.setNew.vspeed < 0.0F ? floatindex(cvt(-this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale) : -floatindex(cvt(this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -this.setNew.azimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", 0.0F, this.setNew.azimuth.getDeg(f), 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, -360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, -360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, -360.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel3", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel4", 0.0F, cvt(this.fm.M.fuel, 0.0F, 400.0F, 0.0F, -87.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_FuelPres1", 0.0F, cvt(this.fm.M.fuel <= 1.0F ? 0.0F : 0.26F, 0.0F, 0.3F, 0.0F, -180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -74.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, -180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Pres1", 0.0F, cvt(this.setNew.man, 0.338638F, 2.539784F, 0.0F, -344.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 200.0F, 0.0F, -180.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, -301.0F), 0.0F);
    float f1 = this.fm.EI.engines[0].getRPM();
    f1 = 2.5F * (float)Math.sqrt(Math.sqrt(Math.sqrt(Math.sqrt(f1))));
    this.mesh.chunkSetAngles("Z_Suction1", 0.0F, cvt(f1, 0.0F, 10.0F, 0.0F, -300.0F), 0.0F);
    if (this.fm.Gears.lgear)
    {
      resetYPRmodifier();
      Cockpit.xyz[0] = (-0.133F * this.fm.CT.getGear());
      this.mesh.chunkSetLocate("Z_GearL1", Cockpit.xyz, Cockpit.ypr);
    }
    if (this.fm.Gears.rgear)
    {
      resetYPRmodifier();
      Cockpit.xyz[0] = (-0.133F * this.fm.CT.getGear());
      this.mesh.chunkSetLocate("Z_GearR1", Cockpit.xyz, Cockpit.ypr);
    }
    resetYPRmodifier();
    Cockpit.xyz[0] = (-0.118F * this.fm.CT.getFlap());
    this.mesh.chunkSetLocate("Z_Flap1", Cockpit.xyz, Cockpit.ypr);
    this.mesh.chunkSetAngles("Z_EnginePrim", 0.0F, this.fm.EI.engines[0].getStage() <= 0 ? 0.0F : -36.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_MagSwitch", 0.0F, cvt(this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 3.0F, 0.0F, -111.0F), 0.0F);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot hookpilot = HookPilot.current;
      hookpilot.doAim(false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (!isFocused())
    {
      return;
    }

    leave();
    super.doFocusLeave();
  }

  private void enter()
  {
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 31");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
    HookPilot hookpilot = HookPilot.current;
    if (hookpilot.isPadlock())
      hookpilot.stopPadlock();
    hookpilot.doAim(true);
    hookpilot.setSimpleUse(true);
    hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.mesh.chunkVisible("SuperReticle", true);
    this.mesh.chunkVisible("SprPricel", true);
    this.mesh.chunkVisible("SprSight", true);
    this.mesh.chunkVisible("Pricel", false);
    this.mesh.chunkVisible("Sight", false);
  }

  private void leave()
  {
    if (!this.bEntered)
    {
      return;
    }

    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
    CmdEnv.top().exec("fov " + this.saveFov);
    HookPilot hookpilot = HookPilot.current;
    hookpilot.doAim(false);
    hookpilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    hookpilot.setSimpleUse(false);
    boolean flag = HotKeyEnv.isEnabled("aircraftView");
    HotKeyEnv.enable("PanView", flag);
    HotKeyEnv.enable("SnapView", flag);
    this.bEntered = false;
    this.mesh.chunkVisible("SuperReticle", false);
    this.mesh.chunkVisible("SprPricel", false);
    this.mesh.chunkVisible("SprSight", false);
    this.mesh.chunkVisible("Pricel", true);
    this.mesh.chunkVisible("Sight", true);
  }

  public void destroy()
  {
    leave();
    super.destroy();
  }

  public void doToggleAim(boolean flag)
  {
    if (!isFocused())
      return;
    if (isToggleAim() == flag)
      return;
    if (flag)
      enter();
    else
      leave();
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

  private class Variables
  {
    float flaps;
    float gear;
    float throttle;
    float prop;
    float mix;
    float divebrake;
    float altimeter;
    float man;
    float vspeed;
    AnglesFork azimuth;
    private final CockpitSBD3 this$0;

    private Variables()
    {
      this.this$0 = ???;
      this.azimuth = new AnglesFork();
    }

    Variables(CockpitSBD3.1 x1)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitSBD3.this.fm != null)
      {
        CockpitSBD3.access$002(CockpitSBD3.this, CockpitSBD3.this.setOld);
        CockpitSBD3.access$102(CockpitSBD3.this, CockpitSBD3.this.setNew);
        CockpitSBD3.access$202(CockpitSBD3.this, CockpitSBD3.this.setTmp);
        CockpitSBD3.this.setNew.flaps = (0.9F * CockpitSBD3.this.setOld.flaps + 0.1F * CockpitSBD3.this.fm.CT.FlapsControl);
        CockpitSBD3.this.setNew.gear = (0.7F * CockpitSBD3.this.setOld.gear + 0.3F * CockpitSBD3.this.fm.CT.GearControl);
        CockpitSBD3.this.setNew.throttle = (0.8F * CockpitSBD3.this.setOld.throttle + 0.2F * CockpitSBD3.this.fm.CT.PowerControl);
        CockpitSBD3.this.setNew.prop = (0.8F * CockpitSBD3.this.setOld.prop + 0.2F * CockpitSBD3.this.fm.EI.engines[0].getControlProp());
        CockpitSBD3.this.setNew.mix = (0.8F * CockpitSBD3.this.setOld.mix + 0.2F * CockpitSBD3.this.fm.EI.engines[0].getControlMix());
        CockpitSBD3.this.setNew.divebrake = (0.8F * CockpitSBD3.this.setOld.divebrake + 0.2F * CockpitSBD3.this.fm.CT.AirBrakeControl);
        CockpitSBD3.this.setNew.man = (0.92F * CockpitSBD3.this.setOld.man + 0.08F * CockpitSBD3.this.fm.EI.engines[0].getManifoldPressure());
        CockpitSBD3.this.setNew.altimeter = CockpitSBD3.this.fm.getAltitude();
        CockpitSBD3.this.setNew.azimuth.setDeg(CockpitSBD3.this.setOld.azimuth.getDeg(1.0F), CockpitSBD3.this.fm.Or.azimut());
        CockpitSBD3.this.setNew.vspeed = ((399.0F * CockpitSBD3.this.setOld.vspeed + CockpitSBD3.this.fm.getVertSpeed()) / 400.0F);
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}