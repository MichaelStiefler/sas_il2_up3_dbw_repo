package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitTBF1C_Bombardier extends CockpitPilot
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  private static final float[] speedometerScale = { 0.0F, 2.5F, 54.0F, 104.0F, 154.5F, 205.5F, 224.0F, 242.0F, 259.5F, 277.5F, 296.25F, 314.0F, 334.0F, 344.5F };
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
    return true;
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
  }

  public CockpitTBF1C_Bombardier()
  {
    super("3DO/Cockpit/TBM-Bombardier/hier.him", "bf109");
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
    this.cockpitNightMats = new String[] { "textrbm9", "texture25" };

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

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH()), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH(), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zMinute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zSecond", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCompass1", 0.0F, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.Or.getAzimut(), 0.0F);
    WayPoint localWayPoint = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AP.way.curr();
    if (localWayPoint != null)
    {
      localWayPoint.getP(P1);
      V.sub(P1, this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      float f = (float)(57.295779513082323D * Math.atan2(V.x, V.y));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zCompass2", 0.0F, 90.0F + f, 0.0F);
    }
    if (this.bEntered)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((TBF1C)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);
      boolean bool = ((TBF1C)aircraft()).fSightCurReadyness > 0.93F;
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

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x2) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage3", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage2", true);
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Property.set(CockpitTBF1C_Bombardier.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      float f1 = ((TBF1C)CockpitTBF1C_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((TBF1C)CockpitTBF1C_Bombardier.this.aircraft()).fSightCurSideslip;
      CockpitTBF1C_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("BlackBox", 0.0F, -f2, f1);
      if (CockpitTBF1C_Bombardier.this.bEntered)
      {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitTBF1C_Bombardier.this.aAim + f2, CockpitTBF1C_Bombardier.this.tAim + f1, 0.0F);
      }
      CockpitTBF1C_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, CockpitTBF1C_Bombardier.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[0], 0.0F);
      CockpitTBF1C_Bombardier.this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, CockpitTBF1C_Bombardier.this.aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].tu[1], 0.0F);
      return true;
    }

    Interpolater()
    {
    }
  }
}