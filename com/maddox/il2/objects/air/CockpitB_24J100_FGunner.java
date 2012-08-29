package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB_24J100_FGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;
  private Hook hook2;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Body", 180.0F, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretC", 0.0F, cvt(paramOrient.getYaw(), -38.0F, 38.0F, -15.0F, 15.0F), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretE", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretF", 0.0F, paramOrient.getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretG", -cvt(paramOrient.getYaw(), -33.0F, 33.0F, -33.0F, 33.0F), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretH", 0.0F, cvt(paramOrient.getTangage(), -10.0F, 32.0F, -10.0F, 32.0F), 0.0F);
    resetYPRmodifier();
    Cockpit.xyz[0] = cvt(Math.max(Math.abs(paramOrient.getYaw()), Math.abs(paramOrient.getTangage())), 0.0F, 20.0F, 0.0F, 0.3F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("TurretI", Cockpit.xyz, Cockpit.ypr);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (isRealMode())
      if (!aiTurret().bIsOperable)
      {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      }
      else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -38.0F)
          f1 = -38.0F;
        if (f1 > 38.0F)
          f1 = 38.0F;
        if (f2 > 38.0F)
          f2 = 38.0F;
        if (f2 < -41.0F)
          f2 = -41.0F;
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
      if (this.jdField_bGunFire_of_type_Boolean)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN01");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN01");
        if (this.hook2 == null)
          this.hook2 = new HookNamed(aircraft(), "_MGUN02");
        doHitMasterAircraft(aircraft(), this.hook2, "_MGUN02");
      }
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      else
        this.jdField_bGunFire_of_type_Boolean = paramBoolean;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
  }

  public CockpitB_24J100_FGunner()
  {
    super("3DO/Cockpit/ConsolidatedA6CTurret/B24J100FGunner.him", "bf109");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.hook2 = null;
  }

  static
  {
    Property.set(CockpitB_24J100_FGunner.class, "aiTuretNum", 0);
    Property.set(CockpitB_24J100_FGunner.class, "weaponControlNum", 10);
    Property.set(CockpitB_24J100_FGunner.class, "astatePilotIndx", 2);
  }
}