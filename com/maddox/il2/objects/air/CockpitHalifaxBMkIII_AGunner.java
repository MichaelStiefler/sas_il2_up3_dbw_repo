package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitHalifaxBMkIII_AGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;
  private Hook hook2;
  private Hook hook3;
  private Hook hook4;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Turret3B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret3C_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Turret3B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret3C_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
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
          this.hook1 = new HookNamed(aircraft(), "_MGUN05");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN05");
        if (this.hook2 == null)
          this.hook2 = new HookNamed(aircraft(), "_MGUN06");
        doHitMasterAircraft(aircraft(), this.hook2, "_MGUN06");
        if (this.hook3 == null)
          this.hook3 = new HookNamed(aircraft(), "_MGUN09");
        doHitMasterAircraft(aircraft(), this.hook3, "_MGUN09");
        if (this.hook4 == null)
          this.hook4 = new HookNamed(aircraft(), "_MGUN10");
        doHitMasterAircraft(aircraft(), this.hook4, "_MGUN10");
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

  public CockpitHalifaxBMkIII_AGunner()
  {
    super("3DO/Cockpit/BoultonPaulQuadTurret/HalifaxBMkIIIAGunner.him", "bf109");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.hook2 = null;
    this.hook3 = null;
    this.hook4 = null;
  }

  static
  {
    Property.set(CockpitHalifaxBMkIII_AGunner.class, "aiTuretNum", 2);
    Property.set(CockpitHalifaxBMkIII_AGunner.class, "weaponControlNum", 12);
    Property.set(CockpitHalifaxBMkIII_AGunner.class, "astatePilotIndx", 4);
  }
}