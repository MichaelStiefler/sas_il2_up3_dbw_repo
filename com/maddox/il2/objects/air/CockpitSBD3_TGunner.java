package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitSBD3_TGunner extends CockpitGunner
{
  private static final float[] angles = { 4.0F, 5.5F, 5.5F, 7.0F, 10.5F, 15.5F, 24.0F, 33.0F, 40.0F, 46.0F, 52.5F, 59.0F, 64.5F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 66.5F, 62.5F, 55.0F, 49.5F, -40.0F, -74.5F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F };

  private Hook hook1 = null;
  private Hook hook2 = null;

  private boolean bNeedSetUp = true;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Turret1B_D0", aircraft().hierMesh().isChunkVisible("Turret1A_D0"));
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("Turret_Base", 0.0F, paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("MGun", 0.0F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("Turret_Base2", 0.0F, paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("MGun2", 0.0F, cvt(paramOrient.getTangage(), -20.0F, 45.0F, -20.0F, 45.0F), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }

    float f1 = paramOrient.getYaw();
    if (f1 < -135.0F) f1 = -135.0F;
    if (f1 > 135.0F) f1 = 135.0F;

    float f2 = paramOrient.getTangage();
    if (f2 > 45.0F) f2 = 45.0F;
    if (f2 < -69.0F) f2 = -69.0F;

    float f3 = Math.abs(f1);
    while (f3 > 180.0F) {
      f3 -= 180.0F;
    }
    if (f2 < -floatindex(cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), angles)) {
      f2 = -floatindex(cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), angles);
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (this.bGunFire)
      this.mesh.chunkSetAngles("Trigger", 0.0F, 17.5F, 0.0F);
    else {
      this.mesh.chunkSetAngles("Trigger", 0.0F, 0.0F, 0.0F);
    }

    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    if (this.bGunFire) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN03");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN03");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN04");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN04");
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }
    else this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public CockpitSBD3_TGunner() {
    super("3DO/Cockpit/SBD-3-TGun/hier.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  static
  {
    Property.set(CockpitSBD3_TGunner.class, "aiTuretNum", 0);
    Property.set(CockpitSBD3_TGunner.class, "weaponControlNum", 10);
    Property.set(CockpitSBD3_TGunner.class, "astatePilotIndx", 1);
  }
}