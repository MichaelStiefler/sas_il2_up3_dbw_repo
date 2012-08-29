package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitG4M1_11_Bombardier extends CockpitPilot
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("NoseAXX_D0", false);

      aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", false);

      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;

    aircraft().hierMesh().chunkVisible("NoseAXX_D0", aircraft().isChunkAnyDamageVisible("CF_D"));
    aircraft().hierMesh().chunkVisible("Pilot3_D0", aircraft().FM.AS.astatePilotStates[2] < 95);
    aircraft().hierMesh().chunkVisible("Pilot3_D1", (aircraft().FM.AS.astatePilotStates[2] > 95) && (aircraft().FM.AS.astateBailoutStep < 12));

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

  public CockpitG4M1_11_Bombardier()
  {
    super("3DO/Cockpit/G4M1-11-Bombardier/hier.him", "he111");
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

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bEntered) {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((G4M)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);

      boolean bool = ((G4M)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", bool);
      this.mesh.chunkVisible("zAngleMark", bool);
    } else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zAngleMark", false);
    }
  }

  static
  {
    Property.set(CockpitG4M1_11_Bombardier.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      float f1 = ((G4M)CockpitG4M1_11_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((G4M)CockpitG4M1_11_Bombardier.this.aircraft()).fSightCurSideslip;

      CockpitG4M1_11_Bombardier.this.mesh.chunkSetAngles("BlackBox", -10.0F * f2, 0.0F, f1);
      if (CockpitG4M1_11_Bombardier.this.bEntered) {
        HookPilot localHookPilot = HookPilot.current;

        localHookPilot.setSimpleAimOrient(CockpitG4M1_11_Bombardier.this.aAim + 10.0F * f2, CockpitG4M1_11_Bombardier.this.tAim + f1, 0.0F);
      }

      CockpitG4M1_11_Bombardier.this.mesh.chunkSetAngles("Turret1A", 0.0F, -CockpitG4M1_11_Bombardier.this.aircraft().FM.turret[0].tu[0], 0.0F);
      CockpitG4M1_11_Bombardier.this.mesh.chunkSetAngles("Turret1B", 0.0F, CockpitG4M1_11_Bombardier.this.aircraft().FM.turret[0].tu[1], 0.0F);

      return true;
    }
  }
}