package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
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

public class CockpitPE2_Bombardier extends CockpitPilot
{
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      enter();
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
    CmdEnv.top().exec("fov 50.0");
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

  public void doToggleAim(boolean paramBoolean)
  {
  }

  public CockpitPE2_Bombardier()
  {
    super("3DO/Cockpit/Pe-2-Bombardier/hier.him", "he111");
    try {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.mesh, "CAMERA");
      localHookNamed.computePos(this, this.pos.getAbs(), localLoc);
      this.aAim = localLoc.getOrient().getAzimut();
      this.tAim = localLoc.getOrient().getTangage();
      this.kAim = localLoc.getOrient().getKren();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("zMark1", ((PE_2)aircraft()).fSightCurForwardAngle * 3.675F, 0.0F, 0.0F);
    float f = cvt(((PE_2)aircraft()).fSightSetForwardAngle, -15.0F, 75.0F, -15.0F, 75.0F);
    this.mesh.chunkSetAngles("zMark2", f * 3.675F, 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[0] = cvt(this.fm.Or.getKren() * Math.abs(this.fm.Or.getKren()), -1225.0F, 1225.0F, -0.23F, 0.23F);
    xyz[1] = cvt((this.fm.Or.getTangage() - 1.0F) * Math.abs(this.fm.Or.getTangage() - 1.0F), -1225.0F, 1225.0F, 0.23F, -0.23F);
    ypr[0] = cvt(this.fm.Or.getKren(), -45.0F, 45.0F, -180.0F, 180.0F);
    this.mesh.chunkSetLocate("zBulb", xyz, ypr);
    resetYPRmodifier();
    xyz[0] = cvt(xyz[0], -0.23F, 0.23F, 0.0095F, -0.0095F);
    xyz[1] = cvt(xyz[1], -0.23F, 0.23F, 0.0095F, -0.0095F);
    this.mesh.chunkSetLocate("zRefraction", xyz, ypr);
  }

  static
  {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitPE2_Bombardier.this.bEntered) {
        float f1 = ((PE_2)CockpitPE2_Bombardier.this.aircraft()).fSightCurForwardAngle;
        float f2 = -((PE_2)CockpitPE2_Bombardier.this.aircraft()).fSightCurSideslip;
        CockpitPE2_Bombardier.this.mesh.chunkSetAngles("BlackBox", f2, 0.0F, -f1);
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(-f2, CockpitPE2_Bombardier.this.tAim + f1, 0.0F);
      }
      return true;
    }
  }
}