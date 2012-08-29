package com.maddox.il2.objects.air;

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

public class CockpitPBY_Bombardier extends CockpitPilot
{
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered;

  static
  {
    Property.set(CLASS.THIS(), "astatePilotIndx", 0);
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot hookpilot = HookPilot.current;
      hookpilot.doAim(false);
      enter();
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      leave();
      super.doFocusLeave();
    }
  }

  private void enter()
  {
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 50.0");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot hookpilot = HookPilot.current;
    if (hookpilot.isPadlock())
    {
      hookpilot.stopPadlock();
    }
    hookpilot.doAim(true);
    hookpilot.setSimpleUse(true);
    hookpilot.setSimpleAimOrient(this.aAim, this.tAim, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
  }

  private void leave()
  {
    if (this.bEntered)
    {
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
    }
  }

  public void destroy()
  {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean flag)
  {
  }

  public CockpitPBY_Bombardier()
  {
    super("3DO/Cockpit/Pe-2-Bombardier/BombardierPBY.him", "he111");
    this.bEntered = false;
    try
    {
      Loc loc = new Loc();
      HookNamed hooknamed = new HookNamed(this.mesh, "CAMERA");
      hooknamed.computePos(this, this.pos.getAbs(), loc);
      this.aAim = loc.getOrient().getAzimut();
      this.tAim = loc.getOrient().getTangage();
      this.kAim = loc.getOrient().getKren();
    }
    catch (Exception exception)
    {
      System.out.println(exception.getMessage());
      exception.printStackTrace();
    }
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float f)
  {
    this.mesh.chunkSetAngles("zMark1", ((PBY)aircraft()).fSightCurForwardAngle * 3.675F, 0.0F, 0.0F);
    float f1 = cvt(((PBY)aircraft()).fSightSetForwardAngle, -15.0F, 75.0F, -15.0F, 75.0F);
    this.mesh.chunkSetAngles("zMark2", f1 * 3.675F, 0.0F, 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[0] = cvt(this.fm.Or.getKren() * Math.abs(this.fm.Or.getKren()), -1225.0F, 1225.0F, -0.23F, 0.23F);
    Cockpit.xyz[1] = cvt((this.fm.Or.getTangage() - 1.0F) * Math.abs(this.fm.Or.getTangage() - 1.0F), -1225.0F, 1225.0F, 0.23F, -0.23F);
    Cockpit.ypr[0] = cvt(this.fm.Or.getKren(), -45.0F, 45.0F, -180.0F, 180.0F);
    this.mesh.chunkSetLocate("zBulb", Cockpit.xyz, Cockpit.ypr);
    resetYPRmodifier();
    Cockpit.xyz[0] = cvt(Cockpit.xyz[0], -0.23F, 0.23F, 0.0095F, -0.0095F);
    Cockpit.xyz[1] = cvt(Cockpit.xyz[1], -0.23F, 0.23F, 0.0095F, -0.0095F);
    this.mesh.chunkSetLocate("zRefraction", Cockpit.xyz, Cockpit.ypr);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      if (CockpitPBY_Bombardier.this.bEntered)
      {
        float f = ((PBY)CockpitPBY_Bombardier.this.aircraft()).fSightCurForwardAngle;
        float f1 = -((PBY)CockpitPBY_Bombardier.this.aircraft()).fSightCurSideslip;
        CockpitPBY_Bombardier.this.mesh.chunkSetAngles("BlackBox", f1, 0.0F, -f);
        HookPilot hookpilot = HookPilot.current;
        hookpilot.setSimpleAimOrient(-f1, CockpitPBY_Bombardier.this.tAim + f, 0.0F);
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}