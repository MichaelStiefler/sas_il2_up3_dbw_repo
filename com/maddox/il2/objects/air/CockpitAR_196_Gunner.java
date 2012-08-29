package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitAR_196_Gunner extends CockpitGunner
{
  private static final float[] angles = { 4.0F, 5.5F, 5.5F, 7.0F, 10.5F, 15.5F, 24.0F, 33.0F, 40.0F, 46.0F, 52.5F, 59.0F, 64.5F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 66.5F, 62.5F, 55.0F, 49.5F, -40.0F, -74.5F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F };
  private Hook hook1;
  private boolean bNeedSetUp;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Turret1B_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Turret1B_D0", aircraft().hierMesh().isChunkVisible("Turret1A_D0"));
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret_Base", 0.0F, paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("MGun", 0.0F, paramOrient.getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret_Base2", 0.0F, paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("MGun2", 0.0F, cvt(paramOrient.getTangage(), -20.0F, 45.0F, -20.0F, 45.0F), 0.0F);
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
        if (f1 < -90.0F)
          f1 = -90.0F;
        if (f1 > 90.0F)
          f1 = 90.0F;
        float f2 = paramOrient.getTangage();
        if (f2 > 60.0F)
          f2 = 60.0F;
        if (f2 < -30.0F) {
          f2 = -30.0F;
        }
        for (float f3 = Math.abs(f1); f3 > 180.0F; f3 -= 180.0F);
        if (f2 < -floatindex(cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), angles))
          f2 = -floatindex(cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), angles);
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (this.jdField_bGunFire_of_type_Boolean)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Trigger", 0.0F, 17.5F, 0.0F);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Trigger", 0.0F, 0.0F, 0.0F);
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
      if (this.jdField_bGunFire_of_type_Boolean)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN02");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN02");
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

  public CockpitAR_196_Gunner()
  {
    super("3DO/Cockpit/SBD-3-TGun/AR_196_Gun.him", "he111_gunner");
    this.hook1 = null;
    this.bNeedSetUp = true;
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

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Property.set(CockpitAR_196_Gunner.class, "aiTuretNum", 0);
    Property.set(CockpitAR_196_Gunner.class, "weaponControlNum", 10);
    Property.set(CockpitAR_196_Gunner.class, "astatePilotIndx", 1);
  }
}