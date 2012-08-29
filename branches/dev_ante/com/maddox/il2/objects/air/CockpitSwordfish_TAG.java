package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitSwordfish_TAG extends CockpitGunner
{
  private boolean bTurrVisible;
  private float saveFov;
  private float aAim;
  private float tAim;
  private float kAim;
  private boolean bEntered = false;
  private float elevTurretA;
  private float yawTurretA = 0.0F;
  private float cockpitAirBrakePos = 0.0F;

  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();
  private boolean bNeedSetUp;
  private Hook hook1;
  private int iCocking;
  private int iOldVisDrums;
  private int iNewVisDrums;
  private float pictLever;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(false);

      ((Swordfish)this.fm.actor).bPitUnfocused = false;

      aircraft().hierMesh().chunkVisible("Gunsight_D0", false);

      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    if (isFocused())
    {
      ((Swordfish)this.fm.actor).bPitUnfocused = true;

      aircraft().hierMesh().chunkVisible("Gunsight_D0", true);

      super.doFocusLeave();
    }
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.cockpitAirBrakePos = (0.9F * this.cockpitAirBrakePos + 0.1F * ((Swordfish)this.fm.actor).airBrakePos);

    this.elevTurretA = (70.0F * (1.0F - this.cockpitAirBrakePos));

    this.mesh.chunkSetAngles("TurrBase", 0.0F, this.elevTurretA, 0.0F);
    this.mesh.chunkSetAngles("TurrBase1", this.elevTurretA, 0.0F, 0.0F);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.yawTurretA = paramOrient.getYaw();
    this.mesh.chunkSetAngles("Turret1A", 0.0F, -this.yawTurretA, 0.0F);
    this.mesh.chunkSetAngles("Turret1B", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode()) {
      return;
    }

    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -70.0F)
      f1 = -70.0F;
    if (f1 > 70.0F)
      f1 = 70.0F;
    if (f2 > 70.0F)
      f2 = 70.0F;
    if (f2 < -45.0F)
      f2 = -45.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    if (this.bGunFire)
    {
      if (this.hook1 == null)
        this.hook1 = new HookNamed(aircraft(), "_turret1");
      doHitMasterAircraft(aircraft(), this.hook1, "_turret1");
      if (this.iCocking > 0)
        this.iCocking = 0;
      else
        this.iCocking = 1;
    }
    else {
      this.iCocking = 0;
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.bGunFire = false;
    else
      this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
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
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);

    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.mesh.materialReplace("Matt1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D2o"));
    this.mesh.materialReplace("Matt1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
  }

  public CockpitSwordfish_TAG()
  {
    super("3DO/Cockpit/SwordfishTAG/hier.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.iCocking = 0;
    this.iOldVisDrums = 3;
    this.iNewVisDrums = 3;
    this.pictLever = 0.0F;
  }

  static
  {
    Property.set(CockpitSwordfish_TAG.class, "aiTuretNum", 0);
    Property.set(CockpitSwordfish_TAG.class, "weaponControlNum", 10);
    Property.set(CockpitSwordfish_TAG.class, "astatePilotIndx", 2);
  }
}