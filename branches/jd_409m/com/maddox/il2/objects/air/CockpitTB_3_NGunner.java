package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitTB_3_NGunner extends CockpitGunner
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

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1A", f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1B", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1C", 0.0F, -f1, 0.0F);
    float f3 = 0.01905F * (float)Math.sin(Math.toRadians(f1));
    float f4 = 0.01905F * (float)Math.cos(Math.toRadians(f1));
    f1 = (float)Math.toDegrees(Math.atan(f3 / (f4 + 0.3565F)));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1D", 0.0F, f1, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage(); float f3 = Math.abs(f1);

    if (f2 < -47.0F) {
      f2 = -47.0F;
    }
    if (f2 > 47.0F) {
      f2 = 47.0F;
    }

    if (f3 < 147.0F) {
      if (f2 < 0.5964912F * f3 - 117.68421F)
        f2 = 0.5964912F * f3 - 117.68421F;
    }
    else if (f3 < 157.0F) {
      if (f2 < 0.3F * f3 - 74.099998F) {
        f2 = 0.3F * f3 - 74.099998F;
      }
    }
    else if (f2 < 0.217391F * f3 - 61.130436F) {
      f2 = 0.217391F * f3 - 61.130436F;
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode()) {
      return;
    }
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;

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
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  public CockpitTB_3_NGunner()
  {
    super("3DO/Cockpit/TB-3-NGun/hier.him", "i16");
    BulletEmitter[] arrayOfBulletEmitter = aircraft().FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[weaponControlNum()];
    if (arrayOfBulletEmitter != null)
      for (int i = 0; i < arrayOfBulletEmitter.length; i++)
        if ((arrayOfBulletEmitter[i] instanceof Actor))
          ((Actor)arrayOfBulletEmitter[i]).visibilityAsBase(false);
  }

  static
  {
    Property.set(CockpitTB_3_NGunner.class, "aiTuretNum", 0);
    Property.set(CockpitTB_3_NGunner.class, "weaponControlNum", 10);
    Property.set(CockpitTB_3_NGunner.class, "astatePilotIndx", 2);
  }
}