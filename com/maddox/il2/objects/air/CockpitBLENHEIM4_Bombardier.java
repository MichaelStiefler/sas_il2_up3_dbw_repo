package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
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
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitBLENHEIM4_Bombardier extends CockpitPilot
{
  public Vector3f w;
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  private static final float[] speedometerScale = { 0.0F, 17.5F, 82.0F, 143.5F, 205.0F, 226.5F, 248.5F, 270.0F, 292.0F, 315.0F, 338.5F };
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered;
  private static Point3d P1 = new Point3d();
  private static Vector3d V = new Vector3d();

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
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
    if (!this.bEntered)
    {
      return;
    }

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

  public void destroy()
  {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if (!isFocused())
      return;
    if (isToggleAim() == paramBoolean)
      return;
    if (paramBoolean)
      enter();
    else
      leave();
  }

  public CockpitBLENHEIM4_Bombardier()
  {
    super("3DO/Cockpit/A-20C-Bombardier/BombardierBLENHEIM4.him", "he111");
    this.w = new Vector3f();
    this.bEntered = false;
    try
    {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.mesh, "CAMERAAIM");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc);
      this.aAim = localLoc.getOrient().getAzimut();
      this.tAim = localLoc.getOrient().getTangage();
      this.kAim = localLoc.getOrient().getKren();
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    this.cockpitNightMats = new String[] { "4_gauges" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
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
    this.mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 804.67212F, 0.0F, 10.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.mesh.chunkSetAngles("zCompass1", 0.0F, this.fm.Or.getAzimut(), 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(this.w.z, -0.23562F, 0.23562F, 22.0F, -22.0F), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(this.fm.getAOS(), -8.0F, 8.0F, -12.0F, 12.0F), 0.0F);
    if (this.bEntered)
    {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((BLENHEIM4)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);
      boolean bool = ((BLENHEIM4)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", bool);
      this.mesh.chunkVisible("zAngleMark", bool);
    }
    else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zAngleMark", false);
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
      this.mesh.chunkVisible("XGlassDamage3", true);
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("XGlassDamage4", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XGlassDamage4", true);
  }

  static
  {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      float f1 = ((BLENHEIM4)CockpitBLENHEIM4_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((BLENHEIM4)CockpitBLENHEIM4_Bombardier.this.aircraft()).fSightCurSideslip;
      CockpitBLENHEIM4_Bombardier.this.mesh.chunkSetAngles("BlackBox", 0.0F, -f2, f1);
      if (CockpitBLENHEIM4_Bombardier.this.bEntered)
      {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitBLENHEIM4_Bombardier.this.aAim + f2, CockpitBLENHEIM4_Bombardier.this.tAim + f1, 0.0F);
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}