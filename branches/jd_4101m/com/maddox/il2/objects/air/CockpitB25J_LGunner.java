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

public class CockpitB25J_LGunner extends CockpitGunner
{
  private Hook hook1 = null;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretLA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretLB", 0.0F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretLC", 0.0F, -paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }

    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -34.0F) f1 = -34.0F;
    if (f1 > 30.0F) f1 = 30.0F;
    if (f2 > 32.0F) f2 = 32.0F;
    if (f2 < -30.0F) f2 = -30.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    if (this.bGunFire) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN12");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN12");
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

  public CockpitB25J_LGunner() {
    super("3DO/Cockpit/B-25J-LGun/hier.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("TurretRA", 0.0F, aircraft().FM.turret[3].tu[0], 0.0F);
    this.mesh.chunkSetAngles("TurretRB", 0.0F, aircraft().FM.turret[3].tu[1], 0.0F);
    this.mesh.chunkSetAngles("TurretRC", 0.0F, aircraft().FM.turret[3].tu[1], 0.0F);
    this.mesh.chunkVisible("TurretRC", false);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
  }

  static
  {
    Property.set(CockpitB25J_LGunner.class, "aiTuretNum", 4);
    Property.set(CockpitB25J_LGunner.class, "weaponControlNum", 14);
    Property.set(CockpitB25J_LGunner.class, "astatePilotIndx", 6);
  }
}