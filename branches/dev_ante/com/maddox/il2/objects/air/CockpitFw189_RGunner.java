package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitFw189_RGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;
  private Hook hook2;

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
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsL_add", true);
    if (f2 < -30.0F + 5.0F * f1)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsR_add", false);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("PatronsR_add", true);
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
        if (f1 < -35.0F)
          f1 = -35.0F;
        if (f1 > 35.0F)
          f1 = 35.0F;
        if (f2 > 45.0F)
          f2 = 45.0F;
        if (f2 < 0.0F)
          f2 = 0.0F;
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
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[11] = this.jdField_bGunFire_of_type_Boolean;
      if (this.jdField_bGunFire_of_type_Boolean)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN05");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN05");
        if (this.hook2 == null)
          this.hook2 = new HookNamed(aircraft(), "_MGUN06");
        doHitMasterAircraft(aircraft(), this.hook2, "_MGUN06");
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
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[11] = this.jdField_bGunFire_of_type_Boolean;
    }
  }

  public CockpitFw189_RGunner()
  {
    super("3DO/Cockpit/Fw189/RGunnerFw189.him", "bf109");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.hook2 = null;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 1);
    Property.set(CLASS.THIS(), "weaponControlNum", 11);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);
  }
}