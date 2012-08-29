package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitBEAU1Gun extends CockpitGunner
{
  private boolean bNeedSetUp;
  private int iCocking;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3A", 0.0F, -f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3B", 0.0F, -6.0F + f2, 0.0F);
    if (f1 < -33.0F)
      f1 = -33.0F;
    if (f1 > 33.0F)
      f1 = 33.0F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3D", 0.0F, -f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3E", 0.0F, f2, 0.0F);
  }

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
    aircraft().hierMesh().chunkVisible("Turret1B_D0", true);
    super.doFocusLeave();
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
        if (f1 < -50.0F)
          f1 = -50.0F;
        if (f1 > 50.0F)
          f1 = 50.0F;
        if (f2 > cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F))
          f2 = cvt(Math.abs(f1), 0.0F, 50.0F, 40.0F, 25.0F);
        if (f2 < cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F))
          f2 = cvt(Math.abs(f1), 0.0F, 50.0F, -10.0F, -3.5F);
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
        if (this.iCocking > 0)
          this.iCocking = 0;
        else
          this.iCocking = 1;
      }
      else {
        this.iCocking = 0;
      }
      resetYPRmodifier();
      Cockpit.xyz[1] = (-0.07F * this.iCocking);
      Cockpit.ypr[1] = 0.0F;
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("Turret3C", Cockpit.xyz, Cockpit.ypr);
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

  public CockpitBEAU1Gun()
  {
    super("3DO/Cockpit/G4M1-11-TGun/BeaufighterMk1.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.iCocking = 0;
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

  public void reflectCockpitState()
  {
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 0);
    Property.set(CLASS.THIS(), "weaponControlNum", 10);
    Property.set(CLASS.THIS(), "astatePilotIndx", 1);
  }
}