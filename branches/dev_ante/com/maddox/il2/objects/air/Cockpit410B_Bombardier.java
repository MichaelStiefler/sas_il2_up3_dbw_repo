package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Cockpit410B_Bombardier extends CockpitPilot
{
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private float aDiv;
  private float tDiv;
  private float kDiv;
  private float alpha;
  private boolean bEntered = false;
  private boolean bBAiming = false;
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      enter();
      go_top();
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (isFocused()) {
      leave();
      super.doFocusLeave();
    }
  }

  private void enter() {
    this.saveFov = Main3D.FOVX;
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
  }

  private void leave() {
    if (this.bEntered) {
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
  }

  private void go_top() {
    this.bBAiming = false;
    CmdEnv.top().exec("fov 30.0");
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(false);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(this.aDiv, this.tDiv, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
  }

  public void destroy() {
    super.destroy();
    leave();
  }
  public Cockpit410B_Bombardier() { super("3DO/Cockpit/Ar-234B-2-Bombardier/410.him", "he111");
    HookNamed localHookNamed;
    try { Loc localLoc1 = new Loc();
      localHookNamed = new HookNamed(this.mesh, "CAMERAAIM");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc1);
      this.aAim = localLoc1.getOrient().getAzimut();
      this.tAim = localLoc1.getOrient().getTangage();
      this.kAim = localLoc1.getOrient().getKren();
    } catch (Exception localException1) {
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
    }
    try {
      Loc localLoc2 = new Loc();
      localHookNamed = new HookNamed(this.mesh, "CAMERA");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc2);
      this.aDiv = localLoc2.getOrient().getAzimut();
      this.tDiv = localLoc2.getOrient().getTangage();
      this.kDiv = localLoc2.getOrient().getKren();
    } catch (Exception localException2) {
      System.out.println(localException2.getMessage());
      localException2.printStackTrace();
    }
    interpPut(new Interpolater(), null, Time.current(), null); }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bBAiming) {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((ME_410B)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);

      boolean bool = ((ME_410B)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("zReticle", bool);
      this.mesh.chunkVisible("zAngleMark", bool);
    } else {
      this.mesh.chunkSetAngles("zGSDimm", -this.alpha, 0.0F, 0.0F);
    }
  }

  static {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      float f1 = ((ME_410B)Cockpit410B_Bombardier.this.aircraft()).fSightCurForwardAngle;

      float f2 = ((ME_410B)Cockpit410B_Bombardier.this.aircraft()).fSightCurSideslip;

      Cockpit410B_Bombardier.this.mesh.chunkSetAngles("BlackBox", 0.0F, -f2, f1);
      if ((Cockpit410B_Bombardier.this.bEntered) && (Cockpit410B_Bombardier.this.bBAiming)) {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(Cockpit410B_Bombardier.this.aAim + f2, Cockpit410B_Bombardier.this.tAim + f1, 0.0F);
      }
      float f3 = Cockpit410B_Bombardier.this.fm.getAltitude();
      float f4 = (float)(-(Math.abs(Cockpit410B_Bombardier.this.fm.Vwld.length()) * Math.sin(Math.toRadians(Math.abs(Cockpit410B_Bombardier.this.fm.Or.getTangage())))) * 0.1018999963998795D);

      f4 += (float)Math.sqrt(f4 * f4 + 2.0F * f3 * 0.1019F);

      float f5 = Math.abs((float)Cockpit410B_Bombardier.this.fm.Vwld.length()) * (float)Math.cos(Math.toRadians(Math.abs(Cockpit410B_Bombardier.this.fm.Or.getTangage())));

      float f6 = f5 * f4 + 10.0F - 10.0F;
      Cockpit410B_Bombardier.access$402(Cockpit410B_Bombardier.this, 90.0F - Math.abs(Cockpit410B_Bombardier.this.fm.Or.getTangage()) - (float)Math.toDegrees(Math.atan(f6 / f3)));

      return true;
    }
  }
}