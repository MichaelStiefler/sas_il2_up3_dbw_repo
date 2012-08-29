package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class CockpitHE_111H2_Bombardier extends CockpitPilot
{
  private static final float[] angleScale = { -38.5F, 16.5F, 41.5F, 52.5F, 59.25F, 64.0F, 67.0F, 70.0F, 72.0F, 73.25F, 75.0F, 76.5F, 77.0F, 78.0F, 79.0F, 80.0F };

  private static final float[] speedometerScale = { 0.0F, 0.1F, 19.0F, 37.25F, 63.5F, 91.5F, 112.0F, 135.5F, 159.5F, 186.5F, 213.0F, 238.0F, 264.0F, 289.0F, 314.5F, 339.5F, 359.5F, 360.0F, 360.0F, 360.0F };
  private boolean bTurrVisible;
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = false;
      this.bTurrVisible = aircraft().hierMesh().isChunkVisible("Turret1C_D0");
      aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
      aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAK", false);
      aircraft().hierMesh().chunkVisible("Head1_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAL", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAL", false);
      aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = false;
    aircraft().hierMesh().chunkVisible("Turret1C_D0", this.bTurrVisible);
    aircraft().hierMesh().chunkVisible("Cockpit_D0", (aircraft().hierMesh().isChunkVisible("Nose_D0")) || (aircraft().hierMesh().isChunkVisible("Nose_D1")) || (aircraft().hierMesh().isChunkVisible("Nose_D2")));
    aircraft().hierMesh().chunkVisible("Turret1C_D0", aircraft().hierMesh().isChunkVisible("Turret1B_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAK", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
    aircraft().hierMesh().chunkVisible("Head1_FAK", aircraft().hierMesh().isChunkVisible("Head1_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAL", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAK", aircraft().hierMesh().isChunkVisible("Pilot2_D0"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAL", aircraft().hierMesh().isChunkVisible("Pilot2_D1"));
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

  public CockpitHE_111H2_Bombardier()
  {
    super("3DO/Cockpit/He-111H-2-Bombardier/hier.him", "he111");
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
    this.cockpitNightMats = new String[] { "clocks1", "clocks2", "clocks4", "clocks5" };
    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    AircraftLH.printCompassHeading = true;
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bEntered) {
      this.mesh.chunkSetAngles("zAngleMark", -floatindex(cvt(((HE_111)aircraft()).fSightCurForwardAngle, 7.0F, 140.0F, 0.7F, 14.0F), angleScale), 0.0F, 0.0F);

      boolean bool = ((HE_111)aircraft()).fSightCurReadyness > 0.93F;
      this.mesh.chunkVisible("BlackBox", true);
      this.mesh.chunkVisible("zReticle", bool);
      this.mesh.chunkVisible("zAngleMark", bool);
    } else {
      this.mesh.chunkVisible("BlackBox", false);
      this.mesh.chunkVisible("zReticle", false);
      this.mesh.chunkVisible("zAngleMark", false);
    }
    this.mesh.chunkSetAngles("zAltWheel", 0.0F, cvt(((HE_111)aircraft()).fSightCurAltitude, 0.0F, 10000.0F, 0.0F, 375.83334F), 0.0F);
    this.mesh.chunkSetAngles("zAnglePointer", 0.0F, ((HE_111)aircraft()).fSightCurForwardAngle, 0.0F);
    this.mesh.chunkSetAngles("zAngleWheel", 0.0F, -10.0F * ((HE_111)aircraft()).fSightCurForwardAngle, 0.0F);
    this.mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 400.0F, 0.0F, 16.0F), speedometerScale), 0.0F);
    this.mesh.chunkSetAngles("zSpeedPointer", 0.0F, cvt(((HE_111)aircraft()).fSightCurSpeed, 150.0F, 600.0F, 0.0F, 60.0F), 0.0F);
    this.mesh.chunkSetAngles("zSpeedWheel", 0.0F, 0.333F * ((HE_111)aircraft()).fSightCurSpeed, 0.0F);
    this.mesh.chunkSetAngles("zAlt1", 0.0F, cvt(this.fm.getAltitude(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F);
    this.mesh.chunkSetAngles("zAlt2", -cvt(this.fm.getAltitude(), 0.0F, 10000.0F, 0.0F, 180.0F), 0.0F, 0.0F);
    this.mesh.chunkVisible("zRed1", this.fm.CT.BayDoorControl > 0.66F);
    this.mesh.chunkVisible("zYellow1", this.fm.CT.BayDoorControl < 0.33F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.waypointAzimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass5", this.setNew.radioCompassAzimuth.getDeg(paramFloat * 0.02F) + 90.0F, 0.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_Compass4", -this.setNew.azimuth.getDeg(paramFloat) - 90.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_Compass3", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F, 0.0F);
    }
  }

  static
  {
    Property.set(CockpitHE_111H2_Bombardier.class, "astatePilotIndx", 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      float f1 = ((HE_111)CockpitHE_111H2_Bombardier.this.aircraft()).fSightCurForwardAngle;
      float f2 = ((HE_111)CockpitHE_111H2_Bombardier.this.aircraft()).fSightCurSideslip;

      CockpitHE_111H2_Bombardier.this.mesh.chunkSetAngles("BlackBox", -10.0F * f2, 0.0F, f1);

      if (CockpitHE_111H2_Bombardier.this.bEntered) {
        HookPilot localHookPilot = HookPilot.current;
        localHookPilot.setSimpleAimOrient(CockpitHE_111H2_Bombardier.this.aAim + 10.0F * f2, CockpitHE_111H2_Bombardier.this.tAim + f1, 0.0F);
      }

      CockpitHE_111H2_Bombardier.this.mesh.chunkSetAngles("TurretA", 0.0F, CockpitHE_111H2_Bombardier.this.aircraft().FM.turret[0].tu[0], 0.0F);
      CockpitHE_111H2_Bombardier.this.mesh.chunkSetAngles("TurretB", 0.0F, CockpitHE_111H2_Bombardier.this.aircraft().FM.turret[0].tu[1], 0.0F);

      CockpitHE_111H2_Bombardier.access$402(CockpitHE_111H2_Bombardier.this, CockpitHE_111H2_Bombardier.this.setOld);
      CockpitHE_111H2_Bombardier.access$502(CockpitHE_111H2_Bombardier.this, CockpitHE_111H2_Bombardier.this.setNew);
      CockpitHE_111H2_Bombardier.access$602(CockpitHE_111H2_Bombardier.this, CockpitHE_111H2_Bombardier.this.setTmp);

      float f3 = CockpitHE_111H2_Bombardier.this.waypointAzimuthInvertMinus(30.0F);
      if (CockpitHE_111H2_Bombardier.this.useRealisticNavigationInstruments())
      {
        CockpitHE_111H2_Bombardier.this.setNew.waypointAzimuth.setDeg(f3 - 90.0F);
        CockpitHE_111H2_Bombardier.this.setOld.waypointAzimuth.setDeg(f3 - 90.0F);
        CockpitHE_111H2_Bombardier.this.setNew.radioCompassAzimuth.setDeg(CockpitHE_111H2_Bombardier.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitHE_111H2_Bombardier.this.radioCompassAzimuthInvertMinus() - CockpitHE_111H2_Bombardier.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
      }
      else
      {
        CockpitHE_111H2_Bombardier.this.setNew.waypointAzimuth.setDeg(CockpitHE_111H2_Bombardier.this.setOld.waypointAzimuth.getDeg(0.1F), f3 - CockpitHE_111H2_Bombardier.this.setOld.azimuth.getDeg(1.0F));
      }
      CockpitHE_111H2_Bombardier.this.setNew.azimuth.setDeg(CockpitHE_111H2_Bombardier.this.setOld.azimuth.getDeg(1.0F), CockpitHE_111H2_Bombardier.this.fm.Or.azimut());

      return true;
    }
  }

  private class Variables
  {
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork radioCompassAzimuth;
    private final CockpitHE_111H2_Bombardier this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitHE_111H2_Bombardier.1 arg2)
    {
      this();
    }
  }
}