package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;

public class BombsightNordenCockpitLogic
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };
  private float m_SaveFov;
  private float m_aAim;
  private float m_tAim;
  private float m_kAim;
  private Cockpit m_Pit;

  public BombsightNordenCockpitLogic()
  {
    this.m_Pit = null;
    this.m_aAim = 0.0F;
    this.m_tAim = 0.0F;
    this.m_kAim = 0.0F;
  }

  public BombsightNordenCockpitLogic(Cockpit paramCockpit)
  {
    this.m_Pit = paramCockpit;
    this.m_aAim = 0.0F;
    this.m_tAim = 0.0F;
    this.m_kAim = 0.0F;
  }

  public void setKoeffs(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.m_aAim = paramFloat1;
    this.m_tAim = paramFloat2;
    this.m_kAim = paramFloat3;
  }

  public void OnTick(boolean paramBoolean)
  {
    if (null == this.m_Pit)
      return;
    float f1 = BombsightNorden.fSightCurForwardAngle;
    float f2 = BombsightNorden.fSightCurSideslip;

    float f3 = this.m_Pit.aircraft().FM.Or.getTangage();
    float f4 = 7.5F;
    float f5 = 0.0F;

    if (f3 > 0.0F)
      f5 = f3 < f4 ? f3 : f4;
    else {
      f5 = f3 > -f4 ? f3 : -f4;
    }
    this.m_Pit.mesh.chunkSetAngles("BlackBox", 0.0F, -f2, f1 - f5);

    if (paramBoolean)
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.setSimpleAimOrient(this.m_aAim + f2, this.m_tAim + f1 - f5, 0.0F);
    }
  }

  public void reflectWorldToInstruments(boolean paramBoolean)
  {
    if (null == this.m_Pit)
      return;
    if (paramBoolean)
    {
      this.m_Pit.mesh.chunkSetAngles("zAngleMark", -this.m_Pit.floatindex(this.m_Pit.cvt(BombsightNorden.fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);
      boolean bool = BombsightNorden.fSightCurReadyness > 0.93F;
      this.m_Pit.mesh.chunkVisible("BlackBox", true);
      this.m_Pit.mesh.chunkVisible("zReticle", bool);
      this.m_Pit.mesh.chunkVisible("zAngleMark", bool);
    }
    else {
      this.m_Pit.mesh.chunkVisible("BlackBox", false);
      this.m_Pit.mesh.chunkVisible("zReticle", false);
      this.m_Pit.mesh.chunkVisible("zAngleMark", false);
    }
  }

  public void enter()
  {
    this.m_SaveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 23.913");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(this.m_aAim, this.m_tAim, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
  }

  public void leave()
  {
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
    CmdEnv.top().exec("fov " + this.m_SaveFov);
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(false);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    localHookPilot.setSimpleUse(false);
    boolean bool = HotKeyEnv.isEnabled("aircraftView");
    HotKeyEnv.enable("PanView", bool);
    HotKeyEnv.enable("SnapView", bool);
  }
}