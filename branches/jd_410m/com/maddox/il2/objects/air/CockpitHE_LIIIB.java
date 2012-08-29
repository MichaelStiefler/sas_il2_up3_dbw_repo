package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;
import java.io.PrintStream;

public class CockpitHE_LIIIB extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictTriA = 0.0F;
  private float pictTriE = 0.0F;
  private float pictTriR = 0.0F;

  private float pictManf1 = 1.0F;
  private float pictManf2 = 1.0F;

  private static final float[] speedometerScale = { 0.0F, 21.0F, 69.5F, 116.0F, 163.0F, 215.5F, 266.5F, 318.5F, 378.0F, 430.5F };

  private static final float[] variometerScale = { 0.0F, 47.0F, 82.0F, 97.0F, 112.0F, 111.7F, 132.0F };

  private static final float[] rpmScale = { 0.0F, 2.5F, 19.0F, 50.5F, 102.5F, 173.0F, 227.0F, 266.5F, 297.0F };
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    leave();
    super.doFocusLeave();
  }

  private void enter()
  {
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock()) {
      localHookPilot.stopPadlock();
    }
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(this.aAim, this.tAim, this.kAim);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
  }

  private void leave()
  {
    if (!this.bEntered) return;
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(false);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    localHookPilot.setSimpleUse(false);
    boolean bool = HotKeyEnv.isEnabled("aircraftView");
    HotKeyEnv.enable("PanView", bool);
    HotKeyEnv.enable("SnapView", bool);
    this.bEntered = false;
  }
  public void destroy() {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean) {
    if (!isFocused()) return;
    if (isToggleAim() == paramBoolean) return;
    if (paramBoolean) enter(); else
      leave();
  }

  public CockpitHE_LIIIB()
  {
    super("3DO/Cockpit/He-LercheIIIb/hier.him", "he111");
    try
    {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.mesh, "CAMERAAIM");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc);
      this.aAim = localLoc.getOrient().getAzimut();
      this.tAim = localLoc.getOrient().getTangage();
      this.kAim = localLoc.getOrient().getKren();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }

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
    this.mesh.chunkSetAngles("Z_Stick", -(this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 10.0F, 0.0F, -(this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);
    this.mesh.chunkSetAngles("Z_THTL1", 25.0F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_THTL2", 25.0F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 120.0F, 0.0F, 57.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpress1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut * this.fm.EI.engines[0].getReadyness(), 0.0F, 7.45F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oilpress2", cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut * this.fm.EI.engines[1].getReadyness(), 0.0F, 7.45F, 0.0F, 58.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM1", floatindex(cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM2", floatindex(cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 4000.0F, 0.0F, 8.0F), rpmScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ATA1", this.pictManf1 = 0.9F * this.pictManf1 + 0.1F * cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ATA2", this.pictManf2 = 0.9F * this.pictManf2 + 0.1F * cvt(this.fm.EI.engines[1].getManifoldPressure(), 0.6F, 1.8F, 0.0F, 329.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 864.0F, -8.0F, 80.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.w.z, -0.23562F, 0.23562F, 33.0F, -33.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(getBall(8.0D), -8.0F, 8.0F, -15.0F, 15.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, 0.0F, this.fm.Or.getKren());
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.024F, -0.024F);
    this.mesh.chunkSetLocate("Z_TurnBank2a", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Speed1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 900.0F, 0.0F, 9.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt1", cvt(interp(this.setNew.radioalt, this.setOld.radioalt, paramFloat), 6.27F, 206.27F, 0.0F, -100.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Alt3", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 315.0F), 0.0F, 0.0F);
    float f = this.setNew.vspeed > 0.0F ? 1.0F : -1.0F;
    this.mesh.chunkSetAngles("Z_Climb1", f * floatindex(cvt(Math.abs(this.setNew.vspeed), 0.0F, 30.0F, 0.0F, 6.0F), variometerScale), 0.0F, 0.0F);

    this.mesh.chunkVisible("Z_EnRoute", false);
    this.mesh.chunkVisible("Z_TD1", this.setNew.radioalt < 6.5F);
    this.mesh.chunkVisible("Z_TD2", this.setNew.radioalt < 6.5F);
    this.mesh.chunkVisible("Z_TD3", this.setNew.radioalt < 6.5F);
    this.mesh.chunkVisible("Z_Stabilizer1", this.fm.CT.AirBrakeControl > 0.5F);
    this.mesh.chunkVisible("Z_Stabilizer2", (this.fm.CT.getAirBrake() > 0.99F) && (this.fm.Or.getTangage() > 30.0F));
    this.mesh.chunkVisible("Z_Power25", this.fm.EI.getPowerOutput() > 0.25F);
    this.mesh.chunkVisible("Z_LoFuel", this.fm.M.fuel < 65.0F);
    this.mesh.chunkVisible("Z_Fire1", (this.fm.AS.astateEngineStates[0] > 2) || (this.fm.AS.astateEngineStates[1] > 2));
    this.mesh.chunkVisible("Z_Fire2", (this.fm.AS.astateEngineStates[0] > 2) || (this.fm.AS.astateEngineStates[1] > 2));
    this.mesh.chunkVisible("Z_Fire3", (this.fm.AS.astateEngineStates[0] > 2) || (this.fm.AS.astateEngineStates[1] > 2));
    this.mesh.chunkVisible("Z_MPlus", (this.fm.EI.engines[0].getStage() > 0) || (this.fm.EI.engines[1].getStage() > 0));
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
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Revi_D0", false);
      this.mesh.chunkVisible("Revisun", false);
      this.mesh.chunkVisible("Revi_D1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x1) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x40) != 0)) {
      this.mesh.chunkVisible("PanelL_D0", false);
      this.mesh.chunkVisible("PanelL_D1", true);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
      this.mesh.chunkVisible("Z_TurnBank2a", false);
      this.mesh.chunkVisible("Z_TurnBank3", false);
      this.mesh.chunkVisible("Z_Alt2", false);
      this.mesh.chunkVisible("Z_Alt3", false);
      this.mesh.chunkVisible("Z_Speed1", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x4) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x8) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x80) == 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) != 0))))) {
      this.mesh.chunkVisible("PanelR_D0", false);
      this.mesh.chunkVisible("PanelR_D1", true);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_Temp2", false);
      this.mesh.chunkVisible("Z_Oilpress1", false);
      this.mesh.chunkVisible("Z_Oilpress2", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_RPM2", false);
      this.mesh.chunkVisible("Z_ATA1", false);
      this.mesh.chunkVisible("Z_ATA2", false);
      this.mesh.chunkVisible("Z_Fuel1", false);
    }

    retoggleLight();
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
      if (CockpitHE_LIIIB.this.fm != null) {
        CockpitHE_LIIIB.access$102(CockpitHE_LIIIB.this, CockpitHE_LIIIB.this.setOld); CockpitHE_LIIIB.access$202(CockpitHE_LIIIB.this, CockpitHE_LIIIB.this.setNew); CockpitHE_LIIIB.access$302(CockpitHE_LIIIB.this, CockpitHE_LIIIB.this.setTmp);

        CockpitHE_LIIIB.this.setNew.throttle1 = (0.85F * CockpitHE_LIIIB.this.setOld.throttle1 + CockpitHE_LIIIB.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitHE_LIIIB.this.setNew.prop1 = (0.85F * CockpitHE_LIIIB.this.setOld.prop1 + CockpitHE_LIIIB.this.fm.EI.engines[0].getControlProp() * 0.15F);
        CockpitHE_LIIIB.this.setNew.mix1 = (0.85F * CockpitHE_LIIIB.this.setOld.mix1 + CockpitHE_LIIIB.this.fm.EI.engines[0].getControlMix() * 0.15F);
        CockpitHE_LIIIB.this.setNew.throttle2 = (0.85F * CockpitHE_LIIIB.this.setOld.throttle2 + CockpitHE_LIIIB.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitHE_LIIIB.this.setNew.prop2 = (0.85F * CockpitHE_LIIIB.this.setOld.prop2 + CockpitHE_LIIIB.this.fm.EI.engines[1].getControlProp() * 0.15F);
        CockpitHE_LIIIB.this.setNew.mix2 = (0.85F * CockpitHE_LIIIB.this.setOld.mix2 + CockpitHE_LIIIB.this.fm.EI.engines[1].getControlMix() * 0.15F);
        CockpitHE_LIIIB.this.setNew.altimeter = CockpitHE_LIIIB.this.fm.getAltitude();
        float f = CockpitHE_LIIIB.this.waypointAzimuth();
        CockpitHE_LIIIB.this.setNew.waypointAzimuth.setDeg(CockpitHE_LIIIB.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitHE_LIIIB.this.setOld.azimuth.getDeg(1.0F) + World.Rnd().nextFloat(-2.0F, 2.0F));
        CockpitHE_LIIIB.this.setNew.azimuth.setDeg(CockpitHE_LIIIB.this.setOld.azimuth.getDeg(1.0F), CockpitHE_LIIIB.this.fm.Or.azimut());
        CockpitHE_LIIIB.this.setNew.vspeed = (0.8F * CockpitHE_LIIIB.this.setOld.vspeed + 0.2F * CockpitHE_LIIIB.this.fm.getVertSpeed());
        if (CockpitHE_LIIIB.this.cockpitDimControl) {
          if (CockpitHE_LIIIB.this.setNew.dimPosition > 0.0F) CockpitHE_LIIIB.this.setNew.dimPosition = (CockpitHE_LIIIB.this.setOld.dimPosition - 0.05F);
        }
        else if (CockpitHE_LIIIB.this.setNew.dimPosition < 1.0F) CockpitHE_LIIIB.this.setNew.dimPosition = (CockpitHE_LIIIB.this.setOld.dimPosition + 0.05F);

        World.cur(); World.land(); CockpitHE_LIIIB.this.setNew.radioalt = (0.5F * CockpitHE_LIIIB.this.setOld.radioalt + 0.5F * (CockpitHE_LIIIB.this.fm.getAltitude() - Landscape.HQ_Air((float)CockpitHE_LIIIB.this.fm.Loc.x, (float)CockpitHE_LIIIB.this.fm.Loc.y)));
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float prop1;
    float mix1;
    float throttle2;
    float prop2;
    float mix2;
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float vspeed;
    float dimPosition;
    float radioalt;
    private final CockpitHE_LIIIB this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitHE_LIIIB.1 arg2)
    {
      this();
    }
  }
}