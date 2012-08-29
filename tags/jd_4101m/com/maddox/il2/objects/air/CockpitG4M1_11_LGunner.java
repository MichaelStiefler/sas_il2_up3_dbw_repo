package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitG4M1_11_LGunner extends CockpitGunner
{
  Mat curMat = null;
  Mat newMat;
  private int iCocking = 0;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("RearAXX_D0", false);
      if (this.curMat == null) {
        this.curMat = aircraft().hierMesh().material(aircraft().hierMesh().materialFind("Pilot2"));
        this.newMat = ((Mat)this.curMat.Clone());
        this.newMat.setLayer(0);
        this.newMat.set(0, false);
      }
      aircraft().hierMesh().materialReplace("Pilot2", this.newMat);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    if (!isFocused()) return;
    aircraft().hierMesh().materialReplace("Pilot2", this.curMat);
    aircraft().hierMesh().chunkVisible("RearAXX_D0", aircraft().isChunkAnyDamageVisible("CF_D"));
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();

    this.mesh.chunkSetAngles("Turret5A", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("Turret5B", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -30.0F) f1 = -30.0F;
    if (f1 > 60.0F) f1 = 60.0F;
    if (f2 < -45.0F) f2 = -45.0F;
    if (f2 > 45.0F) f2 = 45.0F;
    if (f1 < 0.0F) {
      if (f2 < cvt(f1, -30.0F, 0.0F, -6.0F, -23.0F)) {
        f2 = cvt(f1, -30.0F, 0.0F, -6.0F, -23.0F);
      }
      if (f2 > cvt(f1, -30.0F, 0.0F, 22.0F, 33.0F))
        f2 = cvt(f1, -30.0F, 0.0F, 22.0F, 33.0F);
    }
    else if (f1 < 30.0F) {
      if (f2 < cvt(f1, 0.0F, 30.0F, -23.0F, -16.0F)) {
        f2 = cvt(f1, 0.0F, 30.0F, -23.0F, -16.0F);
      }
      if (f2 > cvt(f1, 0.0F, 10.0F, 33.0F, 45.0F)) {
        f2 = cvt(f1, 0.0F, 10.0F, 33.0F, 45.0F);
      }
    }
    else if (f2 < cvt(f1, 30.0F, 60.0F, -16.0F, -10.0F)) {
      f2 = cvt(f1, 30.0F, 60.0F, -16.0F, -10.0F);
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    this.mesh.chunkSetAngles("Turret4A", 0.0F, -aircraft().FM.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("Turret4B", 0.0F, aircraft().FM.turret[3].tu[1], 0.0F);

    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    if (this.bGunFire) {
      if (this.iCocking > 0) this.iCocking = 0; else
        this.iCocking = 1;
    }
    else this.iCocking = 0;

    resetYPRmodifier();
    xyz[1] = (-0.07F * this.iCocking);
    this.mesh.chunkSetLocate("Turret5C", xyz, ypr);
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

  public CockpitG4M1_11_LGunner() {
    super("3DO/Cockpit/G4M1-11-LGun/hier.him", "he111_gunner");
  }

  public void reflectCockpitState()
  {
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 4);
    Property.set(CLASS.THIS(), "weaponControlNum", 14);
    Property.set(CLASS.THIS(), "astatePilotIndx", 4);
  }
}