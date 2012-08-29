package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;

public class CockpitJU_87G1_Gunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  private Hook hook1 = null;
  private Hook hook2 = null;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = paramOrient.getYaw();
    float f2 = -paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, f2, 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Hose", -0.333F * Math.abs(f2) - 3.0F, 0.5F * f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PatronsL", 0.0F, f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PatronsL_add", 0.0F, cvt(f1, -25.0F, 0.0F, -91.0F, 0.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PatronsR", 0.0F, f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("PatronsR_add", 0.0F, cvt(f1, 0.0F, 25.0F, 0.0F, 91.0F), 0.0F);
    if (f2 < -30.0F - 5.0F * f1)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsL_add", false);
    else {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsL_add", true);
    }
    if (f2 < -30.0F + 5.0F * f1)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsR_add", false);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsR_add", true);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -25.0F) f1 = -25.0F;
    if (f1 > 25.0F) f1 = 25.0F;
    if (f2 > 45.0F) f2 = 45.0F;
    if (f2 < -10.0F) f2 = -10.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[10] = this.jdField_bGunFire_of_type_Boolean;

    if (this.jdField_bGunFire_of_type_Boolean) {
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN01");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN01");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN02");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN02");
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }
    else this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[10] = this.jdField_bGunFire_of_type_Boolean;
  }

  public CockpitJU_87G1_Gunner() {
    super("3DO/Cockpit/Ju-87D-3-Gun/hier.him", "bf109");
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay1", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay2", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay3"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay3", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay4"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay4", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay7"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Overlay7", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("OverlayD1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD2o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("OverlayD2o", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tail1_D0", localHierMesh.isChunkVisible("Tail1_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tail1_D1", localHierMesh.isChunkVisible("Tail1_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tail1_D2", localHierMesh.isChunkVisible("Tail1_D2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Tail1_D3", localHierMesh.isChunkVisible("Tail1_D3"));
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel == null) return;
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitJU_87G1_Gunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel = World.getPlayerFM();
      if (CockpitJU_87G1_Gunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel == null) return true;
      if (CockpitJU_87G1_Gunner.this.bNeedSetUp) {
        CockpitJU_87G1_Gunner.this.reflectPlaneMats();
        CockpitJU_87G1_Gunner.access$002(CockpitJU_87G1_Gunner.this, false);
      }
      ((JU_87)CockpitJU_87G1_Gunner.this.aircraft()); if (JU_87.bChangedPit) {
        CockpitJU_87G1_Gunner.this.reflectPlaneToModel();
        ((JU_87)CockpitJU_87G1_Gunner.this.aircraft()); JU_87.bChangedPit = false;
      }
      return true;
    }
  }
}