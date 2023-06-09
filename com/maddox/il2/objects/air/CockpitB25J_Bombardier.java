package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitB25J_Bombardier extends CockpitPilot
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  private static final float[] speedometerScale = { 0.0F, 2.5F, 54.0F, 104.0F, 154.5F, 205.5F, 224.0F, 242.0F, 259.5F, 277.5F, 296.25F, 314.0F, 334.0F, 344.5F };
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;

  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private static Point3d P1 = new Point3d();
  private static Vector3d V = new Vector3d();

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
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 23.913");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(this.aAim, this.tAim, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
  }

  private void leave()
  {
    if (!this.bEntered) return;
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

  public CockpitB25J_Bombardier()
  {
    super("3DO/Cockpit/B-25J-Bombardier/hier.him", "bf109");
    try {
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

    this.cockpitNightMats = new String[] { "textrbm9", "texture25" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(this.fm.getSpeedKMH(), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("zHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.mesh.chunkSetAngles("zMinute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.mesh.chunkSetAngles("zSecond", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zCompass1", 0.0F, this.setNew.azimuth.getDeg(paramFloat), 0.0F);
    this.mesh.chunkSetAngles("zCompass2", 0.0F, this.setNew.waypointAzimuth.getDeg(0.1F), 0.0F);

    if (this.bEntered) {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((B_25J1)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);

      boolean bool = ((B_25J1)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", bool);
      this.mesh.chunkVisible("zAngleMark", bool);
    } else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zAngleMark", false);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XHullDamage2", true);
  }

  static
  {
    Property.set(CockpitB25J_Bombardier.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitB25J_Bombardier.access$102(CockpitB25J_Bombardier.this, CockpitB25J_Bombardier.this.setOld);
      CockpitB25J_Bombardier.access$202(CockpitB25J_Bombardier.this, CockpitB25J_Bombardier.this.setNew);
      CockpitB25J_Bombardier.access$302(CockpitB25J_Bombardier.this, CockpitB25J_Bombardier.this.setTmp);

      float f1 = ((B_25J1)CockpitB25J_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((B_25J1)CockpitB25J_Bombardier.this.aircraft()).fSightCurSideslip;

      CockpitB25J_Bombardier.this.mesh.chunkSetAngles("BlackBox", -10.0F * f2, 0.0F, f1);

      if (CockpitB25J_Bombardier.this.bEntered) {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitB25J_Bombardier.this.aAim + 10.0F * f2, CockpitB25J_Bombardier.this.tAim + f1, 0.0F);
      }

      CockpitB25J_Bombardier.this.mesh.chunkSetAngles("TurretA", 0.0F, CockpitB25J_Bombardier.this.aircraft().FM.turret[0].tu[0], 0.0F);
      CockpitB25J_Bombardier.this.mesh.chunkSetAngles("TurretB", 0.0F, CockpitB25J_Bombardier.this.aircraft().FM.turret[0].tu[1], 0.0F);

      float f3 = CockpitB25J_Bombardier.this.waypointAzimuth();
      CockpitB25J_Bombardier.this.setNew.azimuth.setDeg(CockpitB25J_Bombardier.this.setOld.azimuth.getDeg(1.0F), CockpitB25J_Bombardier.this.fm.Or.azimut());
      if (CockpitB25J_Bombardier.this.useRealisticNavigationInstruments())
      {
        CockpitB25J_Bombardier.this.setNew.waypointAzimuth.setDeg(f3 - 90.0F);
        CockpitB25J_Bombardier.this.setOld.waypointAzimuth.setDeg(f3 - 90.0F);
      }
      else
      {
        CockpitB25J_Bombardier.this.setNew.waypointAzimuth.setDeg(CockpitB25J_Bombardier.this.setOld.waypointAzimuth.getDeg(0.1F), f3);
      }

      return true;
    }
  }

  private class Variables
  {
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    private final CockpitB25J_Bombardier this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitB25J_Bombardier.1 arg2)
    {
      this();
    }
  }
}