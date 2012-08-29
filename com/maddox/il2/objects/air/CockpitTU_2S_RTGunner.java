package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitTU_2S_RTGunner extends CockpitGunner
{
  private Hook hook1;
  private static final float[] speedometerScale = { 0.0F, 0.0F, 10.5F, 42.5F, 85.0F, 125.0F, 165.5F, 181.0F, 198.0F, 214.5F, 231.0F, 249.0F, 266.5F, 287.5F, 308.0F, 326.5F, 346.0F };

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelA", 0.0F, paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurrelB", 0.0F, paramOrient.getTangage(), 0.0F);
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
        if (f1 < -45.0F)
          f1 = -45.0F;
        if (f1 > 45.0F)
          f1 = 45.0F;
        if (f2 > 45.0F)
          f2 = 45.0F;
        if (f2 < -12.0F)
          f2 = -12.0F;
        if (Math.abs(f1) < 3.5F)
        {
          if (f2 < -2.5F)
            f2 = -2.5F;
        }
        else if ((Math.abs(f1) < 18.5F) && (f2 < -2.5F - 0.6333333F * (Math.abs(f1) - 3.5F)))
          f2 = -2.5F - 0.6333333F * (Math.abs(f1) - 3.5F);
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

  public CockpitTU_2S_RTGunner()
  {
    super("3do/cockpit/Il-2-Gun/RTGunnerTU2S.him", "il2rear");
    this.hook1 = null;
  }

  static
  {
    Property.set(CLASS.THIS(), "aiTuretNum", 1);
    Property.set(CLASS.THIS(), "weaponControlNum", 11);
    Property.set(CLASS.THIS(), "astatePilotIndx", 2);
  }
}