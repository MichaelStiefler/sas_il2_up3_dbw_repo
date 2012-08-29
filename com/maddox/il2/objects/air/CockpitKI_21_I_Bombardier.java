package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
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

public class CockpitKI_21_I_Bombardier extends CockpitPilot
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Pilot3_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", false);
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      aircraft().hierMesh().chunkVisible("Pilot3_D0", aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[2] < 95);
      aircraft().hierMesh().chunkVisible("Pilot3_D1", (aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[2] > 95) && (aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateBailoutStep < 12));
      leave();
      super.doFocusLeave();
    }
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
    }
  }

  public void destroy()
  {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
      if (paramBoolean)
        enter();
      else
        leave();
  }

  public CockpitKI_21_I_Bombardier()
  {
    super("3DO/Cockpit/G4M1-11-Bombardier/BombardierKI21I.him", "he111");
    this.bEntered = false;
    try
    {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "CAMERAAIM");
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
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bEntered)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((KI_21_I)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);
      boolean bool = ((KI_21_I)aircraft()).fSightCurReadyness > 0.93F;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BlackBox", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zReticle", bool);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAngleMark", bool);
    }
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BlackBox", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zReticle", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zAngleMark", false);
    }
  }

  static
  {
    Property.set(CockpitKI_21_I_Bombardier.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      float f1 = ((KI_21_I)CockpitKI_21_I_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((KI_21_I)CockpitKI_21_I_Bombardier.this.aircraft()).fSightCurSideslip;
      CockpitKI_21_I_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("BlackBox", 0.0F, -f2, f1);
      if (CockpitKI_21_I_Bombardier.this.bEntered)
      {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitKI_21_I_Bombardier.this.aAim + f2, CockpitKI_21_I_Bombardier.this.tAim + f1, 0.0F);
      }
      CockpitKI_21_I_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1A", 0.0F, -CockpitKI_21_I_Bombardier.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[0], 0.0F);
      CockpitKI_21_I_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1B", 0.0F, CockpitKI_21_I_Bombardier.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[1], 0.0F);
      return true;
    }

    Interpolater()
    {
    }
  }
}