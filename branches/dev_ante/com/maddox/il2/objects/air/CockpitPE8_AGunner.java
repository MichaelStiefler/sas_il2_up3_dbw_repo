package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitPE8_AGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;
  private float cockPos = 0.0F;
  private float a2 = 0.0F;

  protected boolean doFocusEnter() {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Turret2E_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (isFocused()) {
      aircraft().hierMesh().chunkVisible("Turret2E_D0", aircraft().isChunkAnyDamageVisible("Tail1_D"));

      super.doFocusLeave();
    }
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
  }

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret2A", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("Turret2B", 0.0F, f2, 0.0F);
    if ((Math.abs(f1) > 2.0F) || (Math.abs(f2) > 2.0F)) {
      this.a2 = (float)Math.toDegrees(Math.atan2(f1, f2));
      this.a2 *= cvt(f2, 0.0F, 55.0F, 1.0F, 0.75F);
    }
    if (f1 < -33.0F)
      f1 = -33.0F;
    if (f1 > 33.0F)
      f1 = 33.0F;
    if (f2 < -23.0F)
      f2 = -23.0F;
    if (f2 > 33.0F)
      f2 = 33.0F;
    this.mesh.chunkSetAngles("Turret2C", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("Turret2D", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -45.0F)
          f1 = -45.0F;
        if (f1 > 45.0F)
          f1 = 45.0F;
        if (f2 > 25.0F)
          f2 = 25.0F;
        if (f2 < -45.0F)
          f2 = -45.0F;
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
      this.cockPos = (0.5F * this.cockPos + 0.5F * this.a2);
      this.mesh.chunkSetAngles("Turret2E", 0.0F, this.cockPos, 0.0F);
      this.mesh.chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * this.fm.CT.getRudder(), 0.0F);

      this.mesh.chunkSetAngles("VatorL_D0", 0.0F, -30.0F * this.fm.CT.getElevator(), 0.0F);

      this.mesh.chunkSetAngles("VatorR_D0", 0.0F, -30.0F * this.fm.CT.getElevator(), 0.0F);
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }
      else this.bGunFire = paramBoolean;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public CockpitPE8_AGunner() {
    super("3DO/Cockpit/G4M1-11-AGun/AGunnerPE8.him", "he111");
  }

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  public void reflectCockpitState()
  {
  }

  static {
    Property.set(CLASS.THIS(), "aiTuretNum", 2);
    Property.set(CLASS.THIS(), "weaponControlNum", 12);
    Property.set(CLASS.THIS(), "astatePilotIndx", 4);
  }
}