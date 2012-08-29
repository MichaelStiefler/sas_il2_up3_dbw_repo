package com.maddox.il2.objects.air;

import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitBF_110G_Gunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;
  private Variables setOld;
  private Variables setNew;
  private Variables setTmp;
  private LightPointActor light1;
  private boolean bAiming = true;
  private Hook ObserverHook = null;
  private boolean bBeaconKeysEnabled;
  private static final float[][] scalePatronsR = { { 0.0F, 0.0F, 0.0F }, { 0.02F, 0.0F, 0.018F }, { 0.061F, 0.044F, 0.061F }, { 0.083F, 0.069F, 0.083F } };

  private static final float[][] scalePatronsL = { { 0.0F, 0.0F, 0.0F }, { 0.02F, 0.0F, 0.18F }, { 0.061F, 0.044F, 0.061F }, { 0.083F, 0.069F, 0.083F } };

  private static final float[][] scalePatronsR1 = { { 5.5F, 2.0F, -2.5F }, { 13.5F, 0.0F, -1.5F }, { 12.0F, 0.0F, -1.0F }, { 15.0F, 4.0F, 2.0F } };

  private static final float[][] scalePatronsR2 = { { 4.0F, 0.0F, -3.0F }, { 4.5F, 0.0F, -3.5F }, { 9.0F, 0.5F, -3.5F }, { 10.0F, 0.0F, -4.5F } };

  private static final float[][] scalePatronsL1 = { { -4.5F, 2.0F, 4.0F }, { -4.5F, 0.0F, 9.0F }, { -3.0F, 0.0F, 10.5F }, { -3.0F, 4.0F, 15.0F } };

  private static final float[][] scalePatronsL2 = { { 0.0F, 0.0F, 3.0F }, { -2.0F, 0.0F, 9.0F }, { -1.0F, 0.0F, 2.5F }, { -4.0F, 0.0F, 8.0F } };

  private static final float[][] scaleHylse1 = { { 6.0F, 7.0F, 6.0F }, { 0.0F, 0.0F, 0.0F }, { -8.0F, 0.0F, -8.0F }, { -17.0F, 0.0F, -17.0F } };

  private static final float[][] scaleHylse2 = { { -8.0F, 0.0F, 8.0F }, { -7.0F, 0.0F, 7.0F }, { -8.0F, 0.0F, 8.0F }, { -1.0F, 0.0F, 1.0F } };

  private Hook hook1 = null;
  private Hook hook2 = null;

  protected boolean doFocusEnter()
  {
    this.bBeaconKeysEnabled = ((AircraftLH)aircraft()).bWantBeaconKeys;
    ((AircraftLH)aircraft()).bWantBeaconKeys = true;
    if (super.doFocusEnter())
    {
      HierMesh localHierMesh = aircraft().hierMesh();

      localHierMesh.chunkVisible("Interior_D0", false);
      localHierMesh.chunkVisible("Blister1_D0", false);
      localHierMesh.chunkVisible("Blister2_D0", false);
      localHierMesh.chunkVisible("Blister3_D0", false);
      localHierMesh.chunkVisible("Turret1B_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    HierMesh localHierMesh = aircraft().hierMesh();

    localHierMesh.chunkVisible("Interior_D0", true);
    localHierMesh.chunkVisible("Blister1_D0", true);
    localHierMesh.chunkVisible("Blister2_D0", true);
    localHierMesh.chunkVisible("Blister3_D0", true);
    localHierMesh.chunkVisible("Turret1B_D0", true);
    super.doFocusLeave();
    ((AircraftLH)aircraft()).bWantBeaconKeys = this.bBeaconKeysEnabled;
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);

    if (!isToggleAim()) {
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();

    this.mesh.chunkSetAngles("TurretA", 0.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, f2, 0.0F);
    this.mesh.chunkSetAngles("TurretC", 0.0F, -FMMath.clamp(f1, -cvt(f2, -19.0F, 12.0F, 5.0F, 35.0F), cvt(f2, -19.0F, 12.0F, 5.0F, 35.0F)), 0.0F);
    this.mesh.chunkSetAngles("TurretD", 0.0F, f2, 0.0F);
    float f3;
    int i;
    float f4;
    if (f2 < 0.0F) {
      f3 = cvt(f2, -19.0F, 0.0F, 20.0F, 30.0F);
      i = 0;
      f4 = (f2 + 19.0F) / 19.0F;
    } else if (f2 < 12.0F) {
      f3 = cvt(f2, 0.0F, 12.0F, 30.0F, 35.0F);
      i = 1;
      f4 = (f2 - 0.0F) / 12.0F;
    } else {
      f3 = cvt(f2, 12.0F, 30.0F, 35.0F, 40.0F);
      i = 2;
      f4 = (f2 - 12.0F) / 18.0F;
    }
    float f5 = f1 / f3;
    f5 += 1.0F;

    float f6 = floatindex(f5, scalePatronsR[i]);
    float f7 = floatindex(f5, scalePatronsR[(i + 1)]);
    float f8 = FMMath.interpolate(f6, f7, f4);
    resetYPRmodifier();
    xyz[1] = f8;
    this.mesh.chunkSetLocate("PatronsR", xyz, ypr);

    f6 = floatindex(f5, scalePatronsL[i]);
    f7 = floatindex(f5, scalePatronsL[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    resetYPRmodifier();
    xyz[1] = f8;
    this.mesh.chunkSetLocate("PatronsL", xyz, ypr);

    f6 = floatindex(f5, scalePatronsR1[i]);
    f7 = floatindex(f5, scalePatronsR1[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("PatronsR1", 0.0F, -f8, 0.0F);

    f6 = floatindex(f5, scalePatronsR2[i]);
    f7 = floatindex(f5, scalePatronsR2[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("PatronsR2", 0.0F, -f8, 0.0F);

    f6 = floatindex(f5, scalePatronsL1[i]);
    f7 = floatindex(f5, scalePatronsL1[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("PatronsL1", 0.0F, -f8, 0.0F);

    f6 = floatindex(f5, scalePatronsL2[i]);
    f7 = floatindex(f5, scalePatronsL2[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("PatronsL2", 0.0F, -f8, 0.0F);

    f6 = floatindex(f5, scaleHylse1[i]);
    f7 = floatindex(f5, scaleHylse1[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("Hylse1", 0.0F, -f8, 0.0F);

    f6 = floatindex(f5, scaleHylse2[i]);
    f7 = floatindex(f5, scaleHylse2[(i + 1)]);
    f8 = FMMath.interpolate(f6, f7, f4);
    this.mesh.chunkSetAngles("Hylse2", 0.0F, -f8, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = -paramOrient.getYaw(); float f2 = paramOrient.getTangage();

    if (f2 < -19.0F) {
      f2 = -19.0F;
    }
    if (f2 > 30.0F)
      f2 = 30.0F;
    float f3;
    if (f2 < 0.0F)
      f3 = cvt(f2, -19.0F, 0.0F, 20.0F, 30.0F);
    else if (f2 < 12.0F)
      f3 = cvt(f2, 0.0F, 12.0F, 30.0F, 35.0F);
    else {
      f3 = cvt(f2, 12.0F, 30.0F, 35.0F, 40.0F);
    }

    if (f1 < 0.0F) {
      if (f1 < -f3) {
        f1 = -f3;
      }
    }
    else if (f1 > f3) {
      f1 = f3;
    }

    paramOrient.setYPR(-f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[10] = this.bGunFire;

    Orient localOrient = hookGunner().getGunMove();
    float f1 = localOrient.getYaw(); float f2 = localOrient.getTangage();

    if (this.bGunFire) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN05");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN05");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN06");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN06");
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if ((!isRealMode()) || (!isToggleAim()))
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }
    else this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[10] = this.bGunFire;
  }

  public CockpitBF_110G_Gunner() {
    super("3DO/Cockpit/Bf-110G-Gun/hier.him", "bf109");

    this.setOld = new Variables(null);
    this.setNew = new Variables(null);
    this.cockpitNightMats = new String[] { "cadran1", "radio", "bague2" };
    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);

    HookNamed localHookNamed = new HookNamed(this.mesh, "LAMPHOOK01");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);

    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(126.0F, 232.0F, 245.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);
    this.ObserverHook = new HookNamed(this.mesh, "CAMERAAIM");
    AircraftLH.printCompassHeading = true;
    this.bBeaconKeysEnabled = ((AircraftLH)aircraft()).bWantBeaconKeys;
    ((AircraftLH)aircraft()).bWantBeaconKeys = true;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot1"));
    this.mesh.materialReplace("Pilot1", localMat);
  }

  public boolean isToggleAim()
  {
    return this.bAiming;
  }

  public void doToggleAim(boolean paramBoolean)
  {
    this.bAiming = (!this.bAiming);
    super.doToggleAim(paramBoolean);
  }

  public Hook getHookCameraGun()
  {
    if (this.bAiming) {
      return super.getHookCameraGun();
    }
    return this.ObserverHook;
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
    {
      this.light1.light.setEmit(0.004F, 1.0F);
      setNightMats(true);
    }
    else
    {
      this.light1.light.setEmit(0.0F, 0.0F);
      setNightMats(false);
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.fm == null) return;
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkVisible("Head1_D0", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
    this.mesh.chunkVisible("Head1_D1", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));

    this.mesh.chunkSetAngles("zAlt2", cvt(interp(this.setNew.altimeter, this.setNew.altimeter, paramFloat), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("zAlt1", cvt(interp(this.setNew.altimeter, this.setNew.altimeter, paramFloat), 0.0F, 14000.0F, 0.0F, 313.0F), 0.0F, 0.0F);

    if (useRealisticNavigationInstruments())
    {
      this.mesh.chunkSetAngles("Z_CompassRim", 0.0F, -this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("Z_CompassPlane", 0.0F, this.setNew.azimuth.getDeg(paramFloat) - this.setNew.waypointAzimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("Z_CompassNeedle", 0.0F, this.setNew.radioCompassAzimuth.getDeg(paramFloat * 0.02F) + 90.0F, 0.0F);
    }
    else
    {
      this.mesh.chunkSetAngles("Z_CompassRim", 0.0F, -this.setNew.azimuth.getDeg(paramFloat), 0.0F);
      this.mesh.chunkSetAngles("Z_CompassPlane", 0.0F, this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F), 0.0F);
      this.mesh.chunkSetAngles("Z_CompassNeedle", 0.0F, 0.0F, 0.0F);
    }

    if (aircraft().FM.AS.listenLorenzBlindLanding)
    {
      this.mesh.chunkSetAngles("Z_AFN12", 0.0F, cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -14.0F, 14.0F), 0.0F);

      this.mesh.chunkSetAngles("Z_AFN11", 0.0F, cvt(this.setNew.beaconRange, 0.0F, 1.0F, 26.5F, -26.5F), 0.0F);

      this.mesh.chunkSetAngles("Z_AFN22", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AFN21", 0.0F, 20.0F, 0.0F);
      this.mesh.chunkVisible("AFN1_RED", isOnBlindLandingMarker());
    }
    else
    {
      this.mesh.chunkSetAngles("Z_AFN22", 0.0F, cvt(this.setNew.beaconDirection, -45.0F, 45.0F, -20.0F, 20.0F), 0.0F);

      this.mesh.chunkSetAngles("Z_AFN21", 0.0F, cvt(this.setNew.beaconRange, 0.0F, 1.0F, 20.0F, -20.0F), 0.0F);

      this.mesh.chunkSetAngles("Z_AFN12", 0.0F, 0.0F, 0.0F);
      this.mesh.chunkSetAngles("Z_AFN11", 0.0F, -26.5F, 0.0F);
      this.mesh.chunkVisible("AFN1_RED", false);
    }
  }

  static
  {
    Property.set(CockpitBF_110G_Gunner.class, "normZN", 0.8F);
    Property.set(CockpitBF_110G_Gunner.class, "gsZN", 0.8F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitBF_110G_Gunner.access$102(CockpitBF_110G_Gunner.this, CockpitBF_110G_Gunner.this.setOld);
      CockpitBF_110G_Gunner.access$202(CockpitBF_110G_Gunner.this, CockpitBF_110G_Gunner.this.setNew);
      CockpitBF_110G_Gunner.access$302(CockpitBF_110G_Gunner.this, CockpitBF_110G_Gunner.this.setTmp);
      CockpitBF_110G_Gunner.this.setNew.altimeter = CockpitBF_110G_Gunner.this.fm.getAltitude();

      if (CockpitBF_110G_Gunner.this.fm == null)
        return true;
      if (CockpitBF_110G_Gunner.this.bNeedSetUp) {
        CockpitBF_110G_Gunner.this.reflectPlaneMats();
        CockpitBF_110G_Gunner.access$402(CockpitBF_110G_Gunner.this, false);
      }

      float f = CockpitBF_110G_Gunner.this.waypointAzimuthInvertMinus(20.0F);
      if (CockpitBF_110G_Gunner.this.useRealisticNavigationInstruments())
      {
        CockpitBF_110G_Gunner.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
        CockpitBF_110G_Gunner.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        CockpitBF_110G_Gunner.this.setNew.radioCompassAzimuth.setDeg(CockpitBF_110G_Gunner.this.setOld.radioCompassAzimuth.getDeg(0.02F), CockpitBF_110G_Gunner.this.radioCompassAzimuthInvertMinus() - CockpitBF_110G_Gunner.this.setOld.azimuth.getDeg(1.0F) - 90.0F);
      }
      else
      {
        CockpitBF_110G_Gunner.this.setNew.waypointAzimuth.setDeg(CockpitBF_110G_Gunner.this.setOld.waypointAzimuth.getDeg(0.1F), f - CockpitBF_110G_Gunner.this.setOld.azimuth.getDeg(1.0F));
      }

      CockpitBF_110G_Gunner.this.setNew.azimuth.setDeg(CockpitBF_110G_Gunner.this.setOld.azimuth.getDeg(1.0F), CockpitBF_110G_Gunner.this.fm.Or.azimut());

      CockpitBF_110G_Gunner.this.setNew.beaconDirection = ((10.0F * CockpitBF_110G_Gunner.this.setOld.beaconDirection + CockpitBF_110G_Gunner.this.getBeaconDirection()) / 11.0F);
      CockpitBF_110G_Gunner.this.setNew.beaconRange = ((10.0F * CockpitBF_110G_Gunner.this.setOld.beaconRange + CockpitBF_110G_Gunner.this.getBeaconRange()) / 11.0F);

      return true;
    }
  }

  private class Variables
  {
    float altimeter;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    AnglesFork radioCompassAzimuth;
    float beaconDirection;
    float beaconRange;
    private final CockpitBF_110G_Gunner this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
      this.radioCompassAzimuth = new AnglesFork();
    }

    Variables(CockpitBF_110G_Gunner.1 arg2)
    {
      this();
    }
  }
}