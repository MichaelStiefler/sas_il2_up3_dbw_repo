package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
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
import com.maddox.sound.ReverbFXRoom;

public class CockpitD3A1 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private boolean bNeedSetUp = true;

  private static final float[] speedometerScale = { 0.0F, 13.0F, 28.5F, 62.0F, 105.0F, 157.5F, 213.0F, 273.5F, 332.0F, 388.0F, 445.70001F, 499.0F, 549.5F, 591.5F, 633.0F, 671.0F, 688.5F, 698.0F };

  private static final float[] oilScale = { 0.0F, -27.5F, 12.0F, 59.5F, 127.0F, 212.5F, 311.5F };
  private float saveFov;
  private boolean bEntered = false;

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

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
    if (!isFocused()) return;
    leave();
    super.doFocusLeave();
  }

  private void enter()
  {
    this.saveFov = Main3D.FOVX;
    CmdEnv.top().exec("fov 31");
    Main3D.cur3D().aircraftHotKeys.setEnableChangeFov(false);
    HookPilot localHookPilot = HookPilot.current;
    if (localHookPilot.isPadlock())
      localHookPilot.stopPadlock();
    localHookPilot.doAim(true);
    localHookPilot.setSimpleUse(true);
    localHookPilot.setSimpleAimOrient(0.0F, 0.0F, 0.0F);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
    this.bEntered = true;
    this.mesh.chunkVisible("SuperReticle", true);
    this.mesh.chunkVisible("Z_BoxTinter", true);
  }
  private void leave() {
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
    this.mesh.chunkVisible("SuperReticle", false);
    this.mesh.chunkVisible("Z_BoxTinter", false);
  }
  public void destroy() {
    leave();
    super.destroy();
  }

  public void doToggleAim(boolean paramBoolean) {
    if (!isFocused()) return;
    if (isToggleAim() == paramBoolean) return;
    if (paramBoolean) enter(); else
      leave();
  }

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(5.0F);
  }

  public CockpitD3A1()
  {
    super("3DO/Cockpit/D3A1/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "gauges1_d1", "gauges1", "gauges2_d1", "gauges2", "gauges3_d1", "gauges3", "gauges4_d1", "gauges4", "gauges5", "gauges6", "gauges7", "turnbank_d1", "turnbank" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null) {
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
    }
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    ((D3A1)aircraft()); if (D3A1.bChangedPit) {
      reflectPlaneToModel();
      ((D3A1)aircraft()); D3A1.bChangedPit = false;
    }

    resetYPRmodifier();
    xyz[2] = cvt(this.fm.CT.getCockpitDoor(), 0.01F, 0.99F, 0.0F, 0.44F);
    this.mesh.chunkSetLocate("Canopy", xyz, ypr);

    this.mesh.chunkSetAngles("Z_ReViTinter", cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_BoxTinter", cvt(interp(this.setNew.dimPos, this.setOld.dimPos, paramFloat), 0.0F, 1.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 64.5F * this.setNew.throttle, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 58.25F * this.setNew.prop, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 48.0F * this.setNew.mix, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal", 10.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Columnbase", (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 8.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 8.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30000.0F, 0.0F, 21600.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30000.0F, 0.0F, 2160.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer1", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 340.0F, 0.0F, 17.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", cvt(-this.fm.Or.getKren(), -45.0F, 45.0F, -45.0F, 45.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass4", -90.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass5", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass6", cvt(this.setNew.waypointDeviation.getDeg(paramFloat * 0.2F), -25.0F, 25.0F, -45.0F, 45.0F), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[2] = cvt(this.setNew.vspeed, -15.0F, 15.0F, -0.053F, 0.053F);
    this.mesh.chunkSetLocate("Z_Climb1", xyz, ypr);

    this.mesh.chunkSetAngles("Z_RPM1", cvt(this.fm.EI.engines[0].getRPM(), 500.0F, 4500.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Manifold1", cvt(this.setNew.man, 0.400051F, 1.333305F, -202.5F, 112.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank0", 0.0F, -this.fm.Or.getKren(), 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(this.fm.Or.getTangage(), -52.0F, 52.0F, 26.0F, -26.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("Z_TurnBank3", cvt(this.w.z, -0.23562F, 0.23562F, 30.0F, -30.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TurnBank4", cvt(getBall(6.0D), -6.0F, 6.0F, 14.0F, -14.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Minute1", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", floatindex(cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 120.0F, 0.0F, 7.0F), oilScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp2", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, 90.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 5.5F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_fuelpress1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 2.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel, 0.0F, 500.0F, 0.0F, 235.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Fuel2", cvt(this.fm.M.fuel, 0.0F, 160.0F, 0.0F, 256.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oxypres1", 90.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oxyquan1", 90.0F, 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Z_OilSplats1_D1", true);
      this.mesh.chunkVisible("Z_OilSplats2_D1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage4", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("Z_Speedometer1", false);
      this.mesh.chunkVisible("Z_TurnBank3", false);
      this.mesh.chunkVisible("Z_Hour1", false);
      this.mesh.chunkVisible("Z_Minute1", false);
      this.mesh.chunkVisible("Z_Oil1", false);
      this.mesh.chunkVisible("Z_fuelpress1", false);
      this.mesh.chunkVisible("Z_Temp1", false);
      this.mesh.chunkVisible("Z_Manifold1", false);
      this.mesh.chunkVisible("Z_RPM1", false);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot2"));
    this.mesh.materialReplace("Pilot2", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("Pilot2_D0", localHierMesh.isChunkVisible("Pilot2_D0"));
    this.mesh.chunkVisible("Pilot2_D1", localHierMesh.isChunkVisible("Pilot2_D1"));
    this.mesh.chunkVisible("Turret1B_D0", localHierMesh.isChunkVisible("Turret1B_D0"));
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitD3A1.this.fm != null) {
        CockpitD3A1.access$102(CockpitD3A1.this, CockpitD3A1.this.setOld); CockpitD3A1.access$202(CockpitD3A1.this, CockpitD3A1.this.setNew); CockpitD3A1.access$302(CockpitD3A1.this, CockpitD3A1.this.setTmp);

        if (CockpitD3A1.this.cockpitDimControl) {
          if (CockpitD3A1.this.setNew.dimPos < 1.0F) CockpitD3A1.this.setNew.dimPos = (CockpitD3A1.this.setOld.dimPos + 0.03F);
        }
        else if (CockpitD3A1.this.setNew.dimPos > 0.0F) CockpitD3A1.this.setNew.dimPos = (CockpitD3A1.this.setOld.dimPos - 0.03F);

        CockpitD3A1.this.setNew.throttle = (0.8F * CockpitD3A1.this.setOld.throttle + 0.2F * CockpitD3A1.this.fm.CT.PowerControl);
        CockpitD3A1.this.setNew.prop = (0.8F * CockpitD3A1.this.setOld.prop + 0.2F * CockpitD3A1.this.fm.EI.engines[0].getControlProp());
        CockpitD3A1.this.setNew.mix = (0.8F * CockpitD3A1.this.setOld.mix + 0.2F * CockpitD3A1.this.fm.EI.engines[0].getControlMix());

        CockpitD3A1.this.setNew.man = (0.92F * CockpitD3A1.this.setOld.man + 0.08F * CockpitD3A1.this.fm.EI.engines[0].getManifoldPressure());
        CockpitD3A1.this.setNew.altimeter = CockpitD3A1.this.fm.getAltitude();

        float f = CockpitD3A1.this.waypointAzimuth();
        CockpitD3A1.this.setNew.azimuth.setDeg(CockpitD3A1.this.setOld.azimuth.getDeg(1.0F), CockpitD3A1.this.fm.Or.azimut());

        if (CockpitD3A1.this.useRealisticNavigationInstruments())
        {
          CockpitD3A1.this.setNew.waypointDeviation.setDeg(CockpitD3A1.this.setOld.waypointDeviation.getDeg(1.0F), CockpitD3A1.this.getBeaconDirection());
          CockpitD3A1.this.setNew.waypointAzimuth.setDeg(CockpitD3A1.this.setOld.waypointAzimuth.getDeg(1.0F), f);
        }
        else
        {
          CockpitD3A1.this.setNew.waypointDeviation.setDeg(CockpitD3A1.this.setOld.waypointDeviation.getDeg(0.1F), f - CockpitD3A1.this.setOld.azimuth.getDeg(1.0F));
          CockpitD3A1.this.setNew.waypointAzimuth.setDeg(CockpitD3A1.this.setOld.waypointAzimuth.getDeg(0.1F), CockpitD3A1.this.fm.Or.azimut() + 90.0F);
        }

        CockpitD3A1.this.setNew.vspeed = (0.5F * CockpitD3A1.this.setOld.vspeed + 0.5F * CockpitD3A1.this.fm.getVertSpeed());

        CockpitD3A1.this.mesh.chunkSetAngles("Turret1A_D0", 0.0F, -CockpitD3A1.this.aircraft().FM.turret[0].tu[0], 0.0F);
        CockpitD3A1.this.mesh.chunkSetAngles("Turret1B_D0", 0.0F, CockpitD3A1.this.aircraft().FM.turret[0].tu[1], 0.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float dimPos;
    float throttle;
    float prop;
    float mix;
    float altimeter;
    float man;
    float vspeed;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork waypointDeviation;
    private final CockpitD3A1 this$0;

    private Variables()
    {
      this.this$0 = this$1;
      this.dimPos = 0.0F;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.waypointDeviation = new AnglesFork();
    }

    Variables(CockpitD3A1.1 arg2)
    {
      this();
    }
  }
}