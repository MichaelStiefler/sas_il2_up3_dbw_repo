package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitA_20C_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Hook hook1 = null;
  private Hook hook2 = null;

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret1A", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("Turret1B", 0.0F, f2, 0.0F);

    this.mesh.chunkSetAngles("Turret1C", 0.0F, cvt(f1, -45.0F, 45.0F, -45.0F, 45.0F), 0.0F);
    this.mesh.chunkSetAngles("Turret1D", 0.0F, f2, 0.0F);
    resetYPRmodifier();
    if (f1 < 0.0F)
      xyz[0] = cvt(f1, -45.0F, 0.0F, 0.25F, 0.0F);
    else {
      xyz[0] = cvt(f1, 0.0F, 45.0F, 0.0F, 0.25F);
    }
    this.mesh.chunkSetLocate("Turret1E", xyz, ypr);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }

    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -75.0F) f1 = -75.0F;
    if (f1 > 75.0F) f1 = 75.0F;
    if (f2 > 45.0F) f2 = 45.0F;
    if (f2 < -5.0F) f2 = -5.0F;
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
        this.hook1 = new HookNamed(aircraft(), "_MGUN07");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN07");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN08");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN08");
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

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("Z_Holes1_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("Z_Holes2_D1", true);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public CockpitA_20C_TGunner()
  {
    super("3DO/Cockpit/A-20C-TGun/hier.him", "he111_gunner");
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);
  }
}