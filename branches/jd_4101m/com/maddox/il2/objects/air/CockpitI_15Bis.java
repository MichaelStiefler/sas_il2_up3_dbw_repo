package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Time;

public class CockpitI_15Bis extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float saveFov;
  private float scopeZoomArea = 15.0F;
  private boolean isIron = false;
  private boolean bEntered = false;
  private float rpmGeneratedPressure = 0.0F;
  private float oilPressure = 0.0F;
  private LightPointActor[] lights = { null, null, null, null };
  private static final float[] speedometerScale = { 0.0F, 0.0F, 18.0F, 45.0F, 75.5F, 107.0F, 137.5F, 170.0F, 206.5F, 243.75F, 286.5F, 329.5F, 374.5F };

  private static final float[] rpmScale = { 0.0F, 5.5F, 18.5F, 59.0F, 99.5F, 134.5F, 165.75F, 198.0F, 228.0F, 255.5F, 308.0F, 345.0F };

  private static final float[] manifoldScale = { 0.0F, 0.0F, 0.0F, 0.0F, 26.0F, 52.0F, 79.0F, 106.0F, 132.0F, 160.0F, 185.0F, 208.0F, 235.0F, 260.0F, 286.0F, 311.0F, 336.0F };

  private boolean isSlideRight = false;

  public CockpitI_15Bis()
  {
    super("3DO/Cockpit/I-15Bis/hier.him", "u2");

    this.cockpitNightMats = new String[] { "prib_four_damage", "prib_four", "PRIB_ONE", "prib_one_damage", "PRIB_three", "prib_three_damage", "PRIB_TWO", "prib_two_damage", "Shkala128" };
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    this.mesh.chunkSetAngles("Z_Mix1", 50.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mix2", 0.0F, 0.0F, -50.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat));
    this.mesh.chunkSetAngles("Z_Throtle1", 60.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle2", 0.0F, 0.0F, -60.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));

    this.mesh.chunkSetAngles("Z_Supc1", -50.0F * this.pictSupc, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Supc2", 0.0F, 0.0F, 50.0F * this.pictSupc);

    this.mesh.chunkSetAngles("Z_Rad1", -60.0F * interp(this.setNew.radiator, this.setOld.radiator, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Rad2", 0.0F, 0.0F, -60.0F * interp(this.setNew.radiator, this.setOld.radiator, paramFloat));

    this.mesh.chunkSetAngles("Z_Magto", 44.0F * this.fm.EI.engines[0].getControlMagnetos(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Stick1", 0.0F, 16.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 10.2F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("Z_Stick2", 0.0F, 0.0F, 10.2F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl));

    this.mesh.chunkSetAngles("Z_Ped_Base", 0.0F, -15.0F * this.fm.CT.getRudder(), 0.0F);

    this.mesh.chunkSetAngles("Z_PedalL", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_PedalR", 0.0F, 15.0F * this.fm.CT.getRudder(), 0.0F);
    this.mesh.chunkSetAngles("Z_Ped_trossL", -15.65F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Ped_trossR", -15.65F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Speedometer1", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 600.0F, 0.0F, 12.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", 0.0F, -interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter2", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank2", 0.0F, cvt(this.setNew.turn, -0.2F, 0.2F, 26.0F, -26.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank1", 0.0F, cvt(getBall(8.0D), -8.0F, 8.0F, 26.0F, -26.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", 0.0F, cvt(this.setNew.vspeed, -30.0F, 30.0F, -180.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Oil1", 0.0F, cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 125.0F, 0.0F, 180.0F), 0.0F);

    float f1 = this.fm.EI.engines[0].getRPM();

    this.mesh.chunkSetAngles("Z_RPM1", 0.0F, floatindex(cvt(f1, 0.0F, 2400.0F, 0.0F, 11.0F), rpmScale), 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
    {
      this.rpmGeneratedPressure -= 2.0F;
    }
    else if (f1 < this.rpmGeneratedPressure)
    {
      this.rpmGeneratedPressure -= (this.rpmGeneratedPressure - f1) * 0.01F;
    }
    else
    {
      this.rpmGeneratedPressure += (f1 - this.rpmGeneratedPressure) * 0.001F;
    }

    if (this.rpmGeneratedPressure < 800.0F)
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 0.0F, 800.0F, 0.0F, 4.0F);
    }
    else if (this.rpmGeneratedPressure < 1800.0F)
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 800.0F, 1800.0F, 4.0F, 5.0F);
    }
    else
    {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 1800.0F, 2750.0F, 5.0F, 5.8F);
    }

    float f2 = 0.0F;
    if (this.fm.EI.engines[0].tOilOut > 90.0F)
    {
      f2 = cvt(this.fm.EI.engines[0].tOilOut, 90.0F, 110.0F, 1.1F, 1.5F);
    }
    else if (this.fm.EI.engines[0].tOilOut < 50.0F)
    {
      f2 = cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 50.0F, 2.0F, 0.9F);
    }
    else
    {
      f2 = cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }

    float f3 = f2 * this.fm.EI.engines[0].getReadyness() * this.oilPressure;

    this.mesh.chunkSetAngles("Z_Oilpres1", 0.0F, cvt(f3, 0.0F, 7.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Fuelpres1", 0.0F, -cvt(this.rpmGeneratedPressure, 0.0F, 1800.0F, 0.0F, 120.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Fuel", 0.0F, cvt(this.fm.M.fuel, 0.0F, 234.0F, 0.0F, 180.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Clock1a", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Clock1b", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Temp1", 0.0F, cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 300.0F, 0.0F, -60.0F), 0.0F);

    this.mesh.chunkSetAngles("Z_Pres1", 0.0F, floatindex(cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.399966F, 2.133152F, 3.0F, 16.0F), manifoldScale), 0.0F);

    this.mesh.chunkSetAngles("Z_Fire1", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[0] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Z_Fire5", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[0] != 0 ? -15.0F : 0.0F);

    this.mesh.chunkSetAngles("Z_Fire3", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[2] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Z_Bomb", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[3] != 0 ? 15.0F : 0.0F);

    this.mesh.chunkSetAngles("Z_Fire4", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[1] != 0 ? -15.0F : 0.0F);
    this.mesh.chunkSetAngles("Z_Fire2", 0.0F, 0.0F, this.fm.CT.saveWeaponControl[1] != 0 ? -15.0F : 0.0F);

    f1 = cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, -130.0F);
    this.mesh.chunkSetAngles("Z_Tinter", 0.0F, 0.0F, f1);
    this.mesh.chunkSetAngles("Z_Tinter_I", 0.0F, 0.0F, f1);
    this.mesh.chunkSetAngles("Z_BoxTinter", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("Z_GS", f1 / 2.0F, 0.0F, 0.0F);

    f1 = this.fm.CT.getCockpitDoor();
    this.mesh.chunkSetAngles("Z_BlisterR1", 0.0F, 0.0F, f1 * 177.7F);
    this.mesh.chunkSetAngles("Z_BlisterR2", 0.0F, 0.0F, f1 * 15.6F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("Z_OilSplats_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("Gauges1_D0", false);
      this.mesh.chunkVisible("Gauges1_D1", true);
      this.mesh.chunkVisible("Z_Climb1", false);
      this.mesh.chunkVisible("Z_Fuel", false);
      this.mesh.chunkVisible("Z_Clock1a", false);
      this.mesh.chunkVisible("Z_Clock1b", false);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_Pres1", false);
      this.mesh.chunkVisible("Z_Oilpres1", false);
      this.mesh.chunkVisible("Z_Fuelpres1", false);
      this.mesh.chunkVisible("Z_Oil1", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("Gauges2_D0", false);
      this.mesh.chunkVisible("Gauges2_D1", true);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("Z_Altimeter1", false);
      this.mesh.chunkVisible("Z_Altimeter2", false);
      this.mesh.chunkVisible("Z_TurnBank1", false);
      this.mesh.chunkVisible("Z_TurnBank2", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) != 0) || (
      ((this.fm.AS.astateCockpitState & 0x10) != 0) || 
      ((this.fm.AS.astateCockpitState & 0x20) == 0)));
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("CF_D0_00", localHierMesh.isChunkVisible("CF_D0"));
    this.mesh.chunkVisible("CF_D1_00", localHierMesh.isChunkVisible("CF_D1"));
    this.mesh.chunkVisible("CF_D2_00", localHierMesh.isChunkVisible("CF_D2"));

    this.mesh.chunkVisible("WingLMid_D0_00", !localHierMesh.isChunkVisible("WingLMid_Cap"));
    this.mesh.chunkVisible("WingRMid_D0_00", !localHierMesh.isChunkVisible("WingRMid_Cap"));
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    setNightMats(this.cockpitLightControl);
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void doToggleAim(boolean paramBoolean)
  {
    if (isFocused())
    {
      if ((paramBoolean) && (!this.isIron))
      {
        Loc localLoc1 = new Loc();
        Loc localLoc2 = new Loc();
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.computePos(this, localLoc1, localLoc2);

        float f = localLoc2.getOrient().getYaw();
        if ((localHookPilot.isPadlock()) || ((f > -this.scopeZoomArea) && (f < this.scopeZoomArea) && (isToggleAim() != paramBoolean)))
        {
          enterScope();
        }
        else if (f < -this.scopeZoomArea)
        {
          localHookPilot.doAim(true);
          localHookPilot.setAim(new Point3d(-1.679999947547913D, -0.07999999821186066D, 0.8479999899864197D));
          this.isIron = true;
        }
        else if (f > this.scopeZoomArea)
        {
          localHookPilot.doAim(true);
          localHookPilot.setAim(new Point3d(-1.679999947547913D, 0.07999999821186066D, 0.8479999899864197D));
          this.isIron = true;
        }
      }
      else
      {
        this.isIron = false;
        leave();
      }
    }
  }

  private void enterScope() {
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.setAim(new Point3d(0.1840000003576279D, 0.0D, 0.8481000065803528D));
    localHookPilot.doAim(true);
    this.bEntered = true;
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 31");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.mesh.chunkVisible("SuperReticle", true);
    this.mesh.chunkVisible("Z_BoxTinter", true);
  }

  private void leave()
  {
    HookPilot localHookPilot = HookPilot.current;
    localHookPilot.doAim(false);
    localHookPilot.setAim(new Point3d(0.1983000040054321D, -0.004399999976158142D, 0.8481000065803528D));
    if (this.bEntered)
    {
      Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(true);
      CmdEnv.top().exec("fov " + this.saveFov);
      localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
      localHookPilot.setSimpleUse(false);
      boolean bool = HotKeyEnv.isEnabled("aircraftView");
      HotKeyEnv.enable("PanView", bool);
      HotKeyEnv.enable("SnapView", bool);
      this.mesh.chunkVisible("SuperReticle", false);
      this.mesh.chunkVisible("Z_BoxTinter", false);
    }
  }

  protected boolean doFocusEnter()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("Wire_D0", false);
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    HierMesh localHierMesh = aircraft().hierMesh();
    localHierMesh.chunkVisible("Wire_D0", true);
    if (isFocused())
    {
      leave();
      super.doFocusLeave();
    }
  }

  public boolean isViewRight()
  {
    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    HookPilot.current.computePos(this, localLoc1, localLoc2);
    float f = localLoc2.getOrient().getYaw();
    if (f < 0.0F)
      this.isSlideRight = true;
    else
      this.isSlideRight = false;
    return this.isSlideRight;
  }

  private class Variables
  {
    float altimeter;
    float azimuth;
    float throttle;
    float mix;
    float prop;
    float turn;
    float vspeed;
    float dimPos;
    float radiator;
    private final CockpitI_15Bis this$0;

    private Variables()
    {
      this.this$0 = this$1;
    }

    Variables(CockpitI_15Bis.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitI_15Bis.this.bNeedSetUp) {
        CockpitI_15Bis.this.reflectPlaneMats();
        CockpitI_15Bis.access$102(CockpitI_15Bis.this, false);
      }
      if (((I_15xyz)CockpitI_15Bis.this.aircraft() == null) || 
        (I_15xyz.bChangedPit)) {
        CockpitI_15Bis.this.reflectPlaneToModel();
        if ((I_15xyz)CockpitI_15Bis.this.aircraft() != null);
        I_15xyz.bChangedPit = false;
      }
      CockpitI_15Bis.access$202(CockpitI_15Bis.this, CockpitI_15Bis.this.setOld);
      CockpitI_15Bis.access$302(CockpitI_15Bis.this, CockpitI_15Bis.this.setNew);
      CockpitI_15Bis.access$402(CockpitI_15Bis.this, CockpitI_15Bis.this.setTmp);

      if (CockpitI_15Bis.this.cockpitDimControl)
      {
        if (CockpitI_15Bis.this.setNew.dimPos < 1.0F)
          CockpitI_15Bis.this.setNew.dimPos = (CockpitI_15Bis.this.setOld.dimPos + 0.05F);
      }
      else if (CockpitI_15Bis.this.setNew.dimPos > 0.0F) {
        CockpitI_15Bis.this.setNew.dimPos = (CockpitI_15Bis.this.setOld.dimPos - 0.05F);
      }
      CockpitI_15Bis.this.setNew.altimeter = CockpitI_15Bis.this.fm.getAltitude();
      if (Math.abs(CockpitI_15Bis.this.fm.Or.getKren()) < 30.0F) {
        CockpitI_15Bis.this.setNew.azimuth = ((21.0F * CockpitI_15Bis.this.setOld.azimuth + CockpitI_15Bis.this.fm.Or.azimut()) / 22.0F);
      }
      if ((CockpitI_15Bis.this.setOld.azimuth > 270.0F) && (CockpitI_15Bis.this.setNew.azimuth < 90.0F))
        CockpitI_15Bis.this.setOld.azimuth -= 360.0F;
      if ((CockpitI_15Bis.this.setOld.azimuth < 90.0F) && (CockpitI_15Bis.this.setNew.azimuth > 270.0F))
        CockpitI_15Bis.this.setOld.azimuth += 360.0F;
      CockpitI_15Bis.this.setNew.throttle = ((10.0F * CockpitI_15Bis.this.setOld.throttle + CockpitI_15Bis.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);

      CockpitI_15Bis.this.setNew.mix = ((10.0F * CockpitI_15Bis.this.setOld.mix + CockpitI_15Bis.this.fm.EI.engines[0].getControlMix()) / 11.0F);

      CockpitI_15Bis.this.setNew.prop = CockpitI_15Bis.this.setOld.prop;
      if (CockpitI_15Bis.this.setNew.prop < CockpitI_15Bis.this.fm.EI.engines[0].getControlProp() - 0.01F)
        CockpitI_15Bis.this.setNew.prop += 0.0025F;
      if (CockpitI_15Bis.this.setNew.prop > CockpitI_15Bis.this.fm.EI.engines[0].getControlProp() + 0.01F)
        CockpitI_15Bis.this.setNew.prop -= 0.0025F;
      CockpitI_15Bis.this.w.set(CockpitI_15Bis.this.fm.getW());
      CockpitI_15Bis.this.fm.Or.transform(CockpitI_15Bis.this.w);
      CockpitI_15Bis.this.setNew.turn = ((12.0F * CockpitI_15Bis.this.setOld.turn + CockpitI_15Bis.this.w.z) / 13.0F);
      CockpitI_15Bis.this.setNew.vspeed = ((299.0F * CockpitI_15Bis.this.setOld.vspeed + CockpitI_15Bis.this.fm.getVertSpeed()) / 300.0F);

      CockpitI_15Bis.access$602(CockpitI_15Bis.this, 0.8F * CockpitI_15Bis.this.pictSupc + 0.2F * CockpitI_15Bis.this.fm.EI.engines[0].getControlCompressor());

      CockpitI_15Bis.this.setNew.radiator = ((10.0F * CockpitI_15Bis.this.setOld.radiator + CockpitI_15Bis.this.fm.CT.getRadiatorControl()) / 11.0F);

      if (((I_15xyz)CockpitI_15Bis.this.aircraft()).blisterRemoved)
      {
        CockpitI_15Bis.this.mesh.chunkVisible("Z_BlisterR1", false);
        CockpitI_15Bis.this.mesh.chunkVisible("Z_BlisterR2", false);
      }

      return true;
    }
  }
}