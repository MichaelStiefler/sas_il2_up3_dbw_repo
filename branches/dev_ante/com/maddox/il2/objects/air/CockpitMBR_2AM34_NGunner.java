package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitMBR_2AM34_NGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;
  private Hook hook2;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1A", f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1B", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret2B", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1C", 0.0F, -f1, 0.0F);
    float f3 = 0.01905F * (float)Math.sin(Math.toRadians(f1));
    float f4 = 0.01905F * (float)Math.cos(Math.toRadians(f1));
    f1 = (float)Math.toDegrees(Math.atan(f3 / (f4 + 0.3565F)));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1D", 0.0F, f1, 0.0F);
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
        float f3 = Math.abs(f1);
        if (f2 < -47.0F)
          f2 = -47.0F;
        if (f2 > 47.0F)
          f2 = 47.0F;
        if (f3 < 147.0F)
        {
          if (f2 < 0.5964912F * f3 - 117.6842F)
            f2 = 0.5964912F * f3 - 117.6842F;
        }
        else if (f3 < 157.0F)
        {
          if (f2 < 0.3F * f3 - 74.099998F)
            f2 = 0.3F * f3 - 74.099998F;
        }
        else if (f2 < 0.217391F * f3 - 61.13044F)
          f2 = 0.217391F * f3 - 61.13044F;
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

  public CockpitMBR_2AM34_NGunner()
  {
    super("3DO/Cockpit/TB-3-NGun/NGunnerMBR2AM34.him", "i16");
    this.bNeedSetUp = true;
    this.hook1 = null;
    this.hook2 = null;
    BulletEmitter[] arrayOfBulletEmitter = aircraft().FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[weaponControlNum()];
    if (arrayOfBulletEmitter != null)
    {
      for (int i = 0; i < arrayOfBulletEmitter.length; i++)
        if ((arrayOfBulletEmitter[i] instanceof Actor))
          ((Actor)arrayOfBulletEmitter[i]).visibilityAsBase(false);
    }
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try
    {
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
    Property.set(CockpitMBR_2AM34_NGunner.class, "aiTuretNum", 0);
    Property.set(CockpitMBR_2AM34_NGunner.class, "weaponControlNum", 10);
    Property.set(CockpitMBR_2AM34_NGunner.class, "astatePilotIndx", 1);
  }
}