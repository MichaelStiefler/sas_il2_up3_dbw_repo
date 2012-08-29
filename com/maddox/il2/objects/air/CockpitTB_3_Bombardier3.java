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

public class CockpitTB_3_Bombardier3 extends CockpitPilot
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
    if (super.doFocusEnter())
    {
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

  public CockpitTB_3_Bombardier3()
  {
    super("3DO/Cockpit/TB-3-Bombardier/hier.him", "he111");
    try {
      Loc localLoc = new Loc();
      HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "CAMERAAIM");
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

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage1", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage2", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage3", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("HullDamage4", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", false);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", false);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bEntered) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BlackBox", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zReticle", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleCurve", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleM", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleKM", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleKMH", true);
    } else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("BlackBox", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zReticle", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleCurve", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleM", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleKM", false);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zScaleKMH", false);
    }
  }

  static
  {
    Property.set(CockpitTB_3_Bombardier3.class, "astatePilotIndx", 3);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitTB_3_Bombardier3.this.resetYPRmodifier();
      float f1 = ((TB_3_4M_34R)CockpitTB_3_Bombardier3.this.aircraft()).fSightCurSpeed;
      float f2 = ((TB_3_4M_34R)CockpitTB_3_Bombardier3.this.aircraft()).fSightCurAltitude;
      CockpitTB_3_Bombardier3.access$002(CockpitTB_3_Bombardier3.this, (19.0F * CockpitTB_3_Bombardier3.this.curAlt + f2) / 20.0F);
      CockpitTB_3_Bombardier3.access$102(CockpitTB_3_Bombardier3.this, (19.0F * CockpitTB_3_Bombardier3.this.curSpd + f1) / 20.0F);
      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zScaleKM", 0.04F * CockpitTB_3_Bombardier3.this.curAlt, 0.0F, 0.0F);
      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zScaleM", 0.36F * CockpitTB_3_Bombardier3.this.curAlt, 0.0F, 0.0F);
      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zScaleKMH", -0.8F * (CockpitTB_3_Bombardier3.this.curSpd - 50.0F), 0.0F, 0.0F);
      float f3 = 0.5F * (float)Math.tan(Math.atan(83.333335876464844D * Math.sqrt(2.0F * CockpitTB_3_Bombardier3.this.curAlt / Atmosphere.g()) / CockpitTB_3_Bombardier3.this.curAlt));
      float f4 = (float)Math.tan(Math.atan(CockpitTB_3_Bombardier3.this.curSpd / 3.6F * Math.sqrt(2.0F * CockpitTB_3_Bombardier3.this.curAlt / Atmosphere.g()) / CockpitTB_3_Bombardier3.this.curAlt));
      Cockpit.xyz[0] = (-0.0005F * CockpitTB_3_Bombardier3.access$000(CockpitTB_3_Bombardier3.this));
      Cockpit.xyz[1] = (-1.0F * (f3 - f4));

      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("zScaleCurve", Cockpit.xyz, Cockpit.ypr);
      if (Math.abs(CockpitTB_3_Bombardier3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 30.0D) {
        CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Compass1", 0.0F, -CockpitTB_3_Bombardier3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F);
      }
      if (Math.abs(CockpitTB_3_Bombardier3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) > 3.5D) {
        CockpitTB_3_Bombardier3.access$224(CockpitTB_3_Bombardier3.this, 0.0004F * CockpitTB_3_Bombardier3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
        if (CockpitTB_3_Bombardier3.this.pencilDisp > 0.1725F) {
          CockpitTB_3_Bombardier3.access$202(CockpitTB_3_Bombardier3.this, 0.1725F);
        }
        if (CockpitTB_3_Bombardier3.this.pencilDisp < -0.2529F) {
          CockpitTB_3_Bombardier3.access$202(CockpitTB_3_Bombardier3.this, -0.2529F);
        }
        Cockpit.xyz[0] = 0.0F;
        Cockpit.xyz[1] = CockpitTB_3_Bombardier3.access$200(CockpitTB_3_Bombardier3.this);
        CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Z_Pencil1", Cockpit.xyz, Cockpit.ypr);
        CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_Pencilrot1", 0.0F, 11459.156F * CockpitTB_3_Bombardier3.this.pencilDisp, 0.0F);
      }

      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_ANO1", 0.0F, CockpitTB_3_Bombardier3.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.bNavLightsOn ? -50.0F : 0.0F, 0.0F);
      CockpitTB_3_Bombardier3.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Z_CockpLight1", 0.0F, CockpitTB_3_Bombardier3.this.cockpitLightControl ? -50.0F : 0.0F, 0.0F);
      return true;
    }
  }
}