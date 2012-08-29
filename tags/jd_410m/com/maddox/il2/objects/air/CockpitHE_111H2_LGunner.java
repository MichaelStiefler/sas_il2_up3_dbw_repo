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

public class CockpitHE_111H2_LGunner extends CockpitGunner
{
  private Hook hook1 = null;
  private int iCocking = 0;
  private int iOldVisDrums = 2;
  private int iNewVisDrums = 2;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretLA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretLB", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -55.0F) f1 = -55.0F;
    if (f1 > 23.0F) f1 = 23.0F;
    if (f2 > 30.0F) f2 = 30.0F;
    if (f2 < -40.0F) f2 = -40.0F;
    if (f2 < -55.0F - 0.5F * f1) f2 = -55.0F - 0.5F * f1;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    if (this.bGunFire) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN04");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN04");
      if (this.iCocking > 0) this.iCocking = 0; else
        this.iCocking = 1;
      this.iNewVisDrums = (int)(this.emitter.countBullets() / 250.0F);
      if (this.iNewVisDrums < this.iOldVisDrums) {
        this.iOldVisDrums = this.iNewVisDrums;
        this.mesh.chunkVisible("DrumL1", this.iNewVisDrums > 1);
        this.mesh.chunkVisible("DrumL2", this.iNewVisDrums > 0);
        sfxClick(13);
      }
    } else {
      this.iCocking = 0;
    }
    resetYPRmodifier();
    xyz[0] = (-0.07F * this.iCocking);
    this.mesh.chunkSetLocate("LeverL", xyz, ypr);
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

  public CockpitHE_111H2_LGunner() {
    super("3DO/Cockpit/He-111H-2-LGun/hier.him", "he111_gunner");
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.mesh.chunkVisible("Flare", true);
      setNightMats(true);
    } else {
      this.mesh.chunkVisible("Flare", false);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (this.fm.AS.astateCockpitState != 0)
      this.mesh.chunkVisible("Holes_D1", true);
  }

  static
  {
    Property.set(CockpitHE_111H2_LGunner.class, "aiTuretNum", 3);
    Property.set(CockpitHE_111H2_LGunner.class, "weaponControlNum", 13);
    Property.set(CockpitHE_111H2_LGunner.class, "astatePilotIndx", 4);
  }
}