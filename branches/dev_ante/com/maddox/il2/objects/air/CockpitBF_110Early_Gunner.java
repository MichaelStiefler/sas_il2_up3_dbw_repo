package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.Turret;

public class CockpitBF_110Early_Gunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private static final float[][] scalePatronsR = { { 0.0F, 0.0F, 0.0F }, { 0.02F, 0.0F, 0.018F }, { 0.061F, 0.044F, 0.061F }, { 0.083F, 0.069F, 0.083F } };

  private static final float[][] scalePatronsL = { { 0.0F, 0.0F, 0.0F }, { 0.02F, 0.0F, 0.18F }, { 0.061F, 0.044F, 0.061F }, { 0.083F, 0.069F, 0.083F } };

  private static final float[][] scalePatronsR1 = { { 5.5F, 2.0F, -2.5F }, { 13.5F, 0.0F, -1.5F }, { 12.0F, 0.0F, -1.0F }, { 15.0F, 4.0F, 2.0F } };

  private static final float[][] scalePatronsR2 = { { 4.0F, 0.0F, -3.0F }, { 4.5F, 0.0F, -3.5F }, { 9.0F, 0.5F, -3.5F }, { 10.0F, 0.0F, -4.5F } };

  private static final float[][] scalePatronsL1 = { { -4.5F, 2.0F, 4.0F }, { -4.5F, 0.0F, 9.0F }, { -3.0F, 0.0F, 10.5F }, { -3.0F, 4.0F, 15.0F } };

  private static final float[][] scalePatronsL2 = { { 0.0F, 0.0F, 3.0F }, { -2.0F, 0.0F, 9.0F }, { -1.0F, 0.0F, 2.5F }, { -4.0F, 0.0F, 8.0F } };

  private static final float[][] scaleHylse1 = { { 6.0F, 7.0F, 6.0F }, { 0.0F, 0.0F, 0.0F }, { -8.0F, 0.0F, -8.0F }, { -17.0F, 0.0F, -17.0F } };

  private static final float[][] scaleHylse2 = { { -8.0F, 0.0F, 8.0F }, { -7.0F, 0.0F, 7.0F }, { -8.0F, 0.0F, 8.0F }, { -1.0F, 0.0F, 1.0F } };
  private Hook hook1;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Wire_D0", false);
      aircraft().hierMesh().chunkVisible("CF_D0", false);
      aircraft().hierMesh().chunkVisible("Nose_D0", false);
      aircraft().hierMesh().chunkVisible("Blister1_D0", false);
      aircraft().hierMesh().chunkVisible("Blister2_D0", false);
      aircraft().hierMesh().chunkVisible("Blister3_D0", false);
      aircraft().hierMesh().chunkVisible("Blister4_D0", false);
      aircraft().hierMesh().chunkVisible("Blister5_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Wire_D0", true);
    aircraft().hierMesh().chunkVisible("CF_D0", true);
    aircraft().hierMesh().chunkVisible("Nose_D0", true);
    aircraft().hierMesh().chunkVisible("Blister1_D0", true);
    aircraft().hierMesh().chunkVisible("Blister2_D0", true);
    aircraft().hierMesh().chunkVisible("Blister3_D0", true);
    aircraft().hierMesh().chunkVisible("Blister4_D0", true);
    aircraft().hierMesh().chunkVisible("Blister5_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", -f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -f2 + -15.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretC", 0.0F, -FMMath.clamp(f1, -cvt(f2, -19.0F, 12.0F, 5.0F, 35.0F), cvt(f2, -19.0F, 12.0F, 5.0F, 35.0F)), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretD", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (isRealMode())
      if (!aiTurret().bIsOperable)
      {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      }
      else {
        float f1 = -paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f2 < -19.0F)
          f2 = -19.0F;
        if (f2 > 30.0F)
          f2 = 30.0F;
        float f3;
        if (f2 < 0.0F) {
          f3 = cvt(f2, -19.0F, 0.0F, 20.0F, 30.0F);
        }
        else if (f2 < 12.0F)
          f3 = cvt(f2, 0.0F, 12.0F, 30.0F, 35.0F);
        else
          f3 = cvt(f2, 12.0F, 30.0F, 35.0F, 40.0F);
        if (f1 < 0.0F)
        {
          if (f1 < -f3)
            f1 = -f3;
        }
        else if (f1 > f3)
          f1 = f3;
        paramOrient.setYPR(-f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[10] = this.jdField_bGunFire_of_type_Boolean;
      Orient localOrient = hookGunner().getGunMove();
      float f1 = localOrient.getYaw();
      float f2 = localOrient.getTangage();
      if (this.jdField_bGunFire_of_type_Boolean)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN05");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN05");
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
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[10] = this.jdField_bGunFire_of_type_Boolean;
    }
  }

  public CockpitBF_110Early_Gunner()
  {
    super("3DO/Cockpit/Bf-110FAM-Gun/hier.him", "bf109");
    this.bNeedSetUp = true;
    this.hook1 = null;
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot1"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Pilot1", localMat);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel != null)
    {
      if (this.bNeedSetUp)
      {
        reflectPlaneMats();
        this.bNeedSetUp = false;
      }
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Head1_D0", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Head1_D1", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
    }
  }

  class Interpolater extends InterpolateRef
  {
    public boolean tick()
    {
      CockpitBF_110Early_Gunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel = World.getPlayerFM();
      if (CockpitBF_110Early_Gunner.this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel == null)
        return true;
      if (CockpitBF_110Early_Gunner.this.bNeedSetUp)
      {
        CockpitBF_110Early_Gunner.this.reflectPlaneMats();
        CockpitBF_110Early_Gunner.access$002(CockpitBF_110Early_Gunner.this, false);
      }
      return true;
    }

    Interpolater()
    {
    }
  }
}