package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitBEAU10Gun extends CockpitGunner
{
  private boolean bNeedSetUp = true;
  private int iCocking = 0;

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret3A", 0.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("Turret3B", 0.0F, -6.0F + f2, 0.0F);
    if (f1 < -33.0F)
      f1 = -33.0F;
    if (f1 > 33.0F)
      f1 = 33.0F;
    this.mesh.chunkSetAngles("Turret3D", 0.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("Turret3E", 0.0F, f2, 0.0F);
  }

  protected boolean doFocusEnter() {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
    super.doFocusLeave();
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -50.0F)
          f1 = -50.0F;
        if (f1 > 50.0F)
          f1 = 50.0F;
        if (f2 > cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F))
          f2 = cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F);
        if (f2 < cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F))
          f2 = cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F);
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
      if (this.bGunFire) {
        if (this.iCocking > 0)
          this.iCocking = 0;
        else
          this.iCocking = 1;
      }
      else this.iCocking = 0;
      resetYPRmodifier();
      Cockpit.xyz[1] = (-0.07F * this.iCocking);
      Cockpit.ypr[1] = 0.0F;
      this.mesh.chunkSetLocate("Turret3C", Cockpit.xyz, Cockpit.ypr);
    }
  }

  public void doGunFire(boolean paramBoolean) {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }
      else this.bGunFire = paramBoolean;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public CockpitBEAU10Gun() {
    super("3DO/Cockpit/G4M1-11-TGun/BeaufighterMk10.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectCockpitState()
  {
  }

  static {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);
  }
}