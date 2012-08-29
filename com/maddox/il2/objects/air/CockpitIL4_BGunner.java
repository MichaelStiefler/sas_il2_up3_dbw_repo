package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitIL4_BGunner extends CockpitGunner
{
  private Hook hook1 = null;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("Turret1A", -14.2F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("Turret1B", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -40.0F)
          f1 = -40.0F;
        if (f1 > 40.0F)
          f1 = 40.0F;
        if (f2 > -10.0F)
          f2 = -10.0F;
        if (f2 < -65.0F)
          f2 = -65.0F;
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
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN09");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN09");
      }
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

  public void reflectCockpitState() {
    if (this.fm.AS.astateCockpitState != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage1", true);
      this.mesh.chunkVisible("XHullDamage2", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
  }

  public CockpitIL4_BGunner() {
    super("3DO/Cockpit/A-20G-BGun/BGunnerIL4.him", "he111_gunner");
  }

  static
  {
    Property.set(CockpitIL4_BGunner.class, "aiTuretNum", 2);

    Property.set(CockpitIL4_BGunner.class, "weaponControlNum", 12);

    Property.set(CockpitIL4_BGunner.class, "astatePilotIndx", 2);
  }
}