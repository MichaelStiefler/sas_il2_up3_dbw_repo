package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitH8K1_NGunner extends CockpitGunner
{
  private int iCocking = 0;

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret1A", 25.0F, -f1, 0.0F);
    this.mesh.chunkSetAngles("Turret1B", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -35.0F)
          f1 = -35.0F;
        if (f1 > 35.0F)
          f1 = 35.0F;
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
      this.mesh.chunkSetLocate("Turret1C", Cockpit.xyz, Cockpit.ypr);
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

  public CockpitH8K1_NGunner() {
    super("3DO/Cockpit/G4M1-11-NGun/NGunnerH8K1.him", "he111");
  }

  public void reflectCockpitState()
  {
  }

  static {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 2);
  }
}