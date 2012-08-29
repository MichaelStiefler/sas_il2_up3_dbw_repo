package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitHE_111H2_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Hook hook1 = null;
  private int iCocking = 0;
  private int iOldVisDrums = 3;
  private int iNewVisDrums = 3;

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -paramOrient.getTangage());
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -42.0F) f1 = -42.0F;
    if (f1 > 42.0F) f1 = 42.0F;
    if (f2 > 60.0F) f2 = 60.0F;
    if (f2 < -3.0F) f2 = -3.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;

    if (this.jdField_bGunFire_of_type_Boolean) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN02");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN02");
      if (this.iCocking > 0) this.iCocking = 0; else
        this.iCocking = 1;
    }
    else {
      this.iCocking = 0;
    }
    this.iNewVisDrums = (int)(this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.countBullets() / 333.0F);
    if (this.iNewVisDrums < this.iOldVisDrums) {
      this.iOldVisDrums = this.iNewVisDrums;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Drum1", this.iNewVisDrums > 2);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Drum2", this.iNewVisDrums > 1);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Drum3", this.iNewVisDrums > 0);
      sfxClick(13);
    }

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CockingLever", -0.75F * this.iCocking, 0.0F, 0.0F);
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }
    else this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D2o", localMat);
  }

  public CockpitHE_111H2_TGunner()
  {
    super("3DO/Cockpit/He-111H-2-TGun/hier.him", "he111_gunner");
  }

  static
  {
    Property.set(CockpitHE_111H2_TGunner.class, "aiTuretNum", 1);
    Property.set(CockpitHE_111H2_TGunner.class, "weaponControlNum", 11);
    Property.set(CockpitHE_111H2_TGunner.class, "astatePilotIndx", 2);
  }
}