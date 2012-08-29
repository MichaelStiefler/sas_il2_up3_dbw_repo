package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitTB_3_Bombardier3_T_DZZ extends CockpitPilot
{
  private float pencilDisp = 0.0F;
  private float curAlt = 300.0F;
  private float curSpd = 50.0F;
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
    CmdEnv.top().exec("fov 45.0");
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

  public void destroy() {
    super.destroy();
    leave();
  }

  public void doToggleAim(boolean paramBoolean) {
    if ((isFocused()) && (isToggleAim() != paramBoolean))
      if (paramBoolean)
        enter();
      else
        leave();
  }

  public CockpitTB_3_Bombardier3_T_DZZ()
  {
    super("3DO/Cockpit/TB-3-Bombardier/hier.him", "he111");
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
    this.cockpitNightMats = new String[] { "BombGauges", "Gauge03" };
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("HullDamage1", false);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("HullDamage2", false);
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("HullDamage3", false);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("HullDamage4", false);
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", false);
      this.mesh.chunkVisible("XGlassDamage2", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
      this.mesh.chunkVisible("XGlassDamage3", false);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bEntered) {
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", true);
      this.mesh.chunkVisible("zScaleCurve", true);
      this.mesh.chunkVisible("zScaleM", true);
      this.mesh.chunkVisible("zScaleKM", true);
      this.mesh.chunkVisible("zScaleKMH", true);
    } else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zScaleCurve", false);
      this.mesh.chunkVisible("zScaleM", false);
      this.mesh.chunkVisible("zScaleKM", false);
      this.mesh.chunkVisible("zScaleKMH", false);
    }
  }

  static
  {
    Property.set(CockpitTB_3_Bombardier3_T_DZZ.class, "astatePilotIndx", 3);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitTB_3_Bombardier3_T_DZZ.this.resetYPRmodifier();
      float f1 = ((TB_3_4M_34R_T_DZZ)CockpitTB_3_Bombardier3_T_DZZ.this.aircraft()).fSightCurSpeed;

      float f2 = ((TB_3_4M_34R_T_DZZ)CockpitTB_3_Bombardier3_T_DZZ.this.aircraft()).fSightCurAltitude;

      CockpitTB_3_Bombardier3_T_DZZ.access$002(CockpitTB_3_Bombardier3_T_DZZ.this, (19.0F * CockpitTB_3_Bombardier3_T_DZZ.this.curAlt + f2) / 20.0F);
      CockpitTB_3_Bombardier3_T_DZZ.access$102(CockpitTB_3_Bombardier3_T_DZZ.this, (19.0F * CockpitTB_3_Bombardier3_T_DZZ.this.curSpd + f1) / 20.0F);
      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("zScaleKM", 0.04F * CockpitTB_3_Bombardier3_T_DZZ.this.curAlt, 0.0F, 0.0F);
      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("zScaleM", 0.36F * CockpitTB_3_Bombardier3_T_DZZ.this.curAlt, 0.0F, 0.0F);
      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("zScaleKMH", -0.8F * (CockpitTB_3_Bombardier3_T_DZZ.this.curSpd - 50.0F), 0.0F, 0.0F);

      float f3 = 0.5F * (float)Math.tan(Math.atan(83.333335876464844D * Math.sqrt(2.0F * CockpitTB_3_Bombardier3_T_DZZ.this.curAlt / Atmosphere.g()) / CockpitTB_3_Bombardier3_T_DZZ.this.curAlt));

      float f4 = (float)Math.tan(Math.atan(CockpitTB_3_Bombardier3_T_DZZ.this.curSpd / 3.6F * Math.sqrt(2.0F * CockpitTB_3_Bombardier3_T_DZZ.this.curAlt / Atmosphere.g()) / CockpitTB_3_Bombardier3_T_DZZ.this.curAlt));

      Cockpit.xyz[0] = (-0.0005F * CockpitTB_3_Bombardier3_T_DZZ.access$000(CockpitTB_3_Bombardier3_T_DZZ.this));
      Cockpit.xyz[1] = (-1.0F * (f3 - f4));
      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetLocate("zScaleCurve", Cockpit.xyz, Cockpit.ypr);
      if (Math.abs(CockpitTB_3_Bombardier3_T_DZZ.this.fm.Or.getKren()) < 30.0D)
        CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -CockpitTB_3_Bombardier3_T_DZZ.this.fm.Or.getYaw(), 0.0F);
      if (Math.abs(CockpitTB_3_Bombardier3_T_DZZ.this.fm.Or.getKren()) > 3.5D) {
        CockpitTB_3_Bombardier3_T_DZZ.access$224(CockpitTB_3_Bombardier3_T_DZZ.this, 0.0004F * CockpitTB_3_Bombardier3_T_DZZ.this.fm.Or.getKren());

        if (CockpitTB_3_Bombardier3_T_DZZ.this.pencilDisp > 0.1725F)
          CockpitTB_3_Bombardier3_T_DZZ.access$202(CockpitTB_3_Bombardier3_T_DZZ.this, 0.1725F);
        if (CockpitTB_3_Bombardier3_T_DZZ.this.pencilDisp < -0.2529F)
          CockpitTB_3_Bombardier3_T_DZZ.access$202(CockpitTB_3_Bombardier3_T_DZZ.this, -0.2529F);
        Cockpit.xyz[0] = 0.0F;
        Cockpit.xyz[1] = CockpitTB_3_Bombardier3_T_DZZ.access$200(CockpitTB_3_Bombardier3_T_DZZ.this);
        CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetLocate("Z_Pencil1", Cockpit.xyz, Cockpit.ypr);
        CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("Z_Pencilrot1", 0.0F, 11459.156F * CockpitTB_3_Bombardier3_T_DZZ.this.pencilDisp, 0.0F);
      }

      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("Z_ANO1", 0.0F, CockpitTB_3_Bombardier3_T_DZZ.this.fm.AS.bNavLightsOn ? -50.0F : 0.0F, 0.0F);

      CockpitTB_3_Bombardier3_T_DZZ.this.mesh.chunkSetAngles("Z_CockpLight1", 0.0F, CockpitTB_3_Bombardier3_T_DZZ.this.cockpitLightControl ? -50.0F : 0.0F, 0.0F);

      return true;
    }
  }
}