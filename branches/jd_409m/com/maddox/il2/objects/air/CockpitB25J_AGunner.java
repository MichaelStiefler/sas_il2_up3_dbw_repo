package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB25J_AGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }
    ((B_25J1)aircraft()); if (B_25J1.bChangedPit) {
      reflectPlaneToModel();
      ((B_25J1)aircraft()); B_25J1.bChangedPit = false;
    }

    float f = this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("VatorL_D0", 0.0F, -30.0F * f, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("VatorR_D0", 0.0F, -30.0F * f, 0.0F);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Matt1D0o", localMat);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorL_D0", localHierMesh.isChunkVisible("VatorL_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorL_D1", localHierMesh.isChunkVisible("VatorL_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorL_CAP", localHierMesh.isChunkVisible("VatorL_CAP"));

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorR_D0", localHierMesh.isChunkVisible("VatorR_D0"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorR_D1", localHierMesh.isChunkVisible("VatorR_D1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("VatorR_CAP", localHierMesh.isChunkVisible("VatorR_CAP"));
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretC", 0.0F, cvt(paramOrient.getYaw(), -38.0F, 38.0F, -15.0F, 15.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretD", 0.0F, cvt(paramOrient.getTangage(), -43.0F, 43.0F, -10.0F, 10.0F), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretE", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretF", 0.0F, paramOrient.getTangage(), 0.0F);

    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretG", -cvt(paramOrient.getYaw(), -33.0F, 33.0F, -33.0F, 33.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretH", 0.0F, cvt(paramOrient.getTangage(), -10.0F, 32.0F, -10.0F, 32.0F), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[0] = cvt(Math.max(Math.abs(paramOrient.getYaw()), Math.abs(paramOrient.getTangage())), 0.0F, 20.0F, 0.0F, 0.3F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("TurretI", Cockpit.xyz, Cockpit.ypr);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }

    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    if (f1 < -38.0F) f1 = -38.0F;
    if (f1 > 38.0F) f1 = 38.0F;
    if (f2 > 43.0F) f2 = 43.0F;
    if (f2 < -41.0F) f2 = -41.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
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

  public CockpitB25J_AGunner() {
    super("3DO/Cockpit/B-25J-AGun/hier.him", "bf109");
  }

  static
  {
    Property.set(CockpitB25J_AGunner.class, "aiTuretNum", 2);
    Property.set(CockpitB25J_AGunner.class, "weaponControlNum", 12);
    Property.set(CockpitB25J_AGunner.class, "astatePilotIndx", 4);
  }
}