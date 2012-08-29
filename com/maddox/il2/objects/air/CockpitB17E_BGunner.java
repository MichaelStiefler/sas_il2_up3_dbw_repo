package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitB17E_BGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private long prevTime;
  private float prevA0;
  private Hook hook1;
  private Hook hook2;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Turret5A_D0", false);
      aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Turret5A_D0", true);
    aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1A", paramOrient.getYaw(), 180.0F, 180.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1B", 180.0F, -paramOrient.getTangage(), 180.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    float f1 = paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    float f3 = Math.abs(f1);
    while (f1 < -180.0F) f1 += 360.0F;
    while (f1 > 180.0F) f1 -= 360.0F;
    while (this.prevA0 < -180.0F) this.prevA0 += 360.0F;
    while (this.prevA0 > 180.0F) this.prevA0 -= 360.0F;
    if (!isRealMode())
    {
      this.prevA0 = f1;
    }
    else {
      if (this.bNeedSetUp)
      {
        this.prevTime = (Time.current() - 1L);
        this.bNeedSetUp = false;
      }
      if ((f1 < -120.0F) && (this.prevA0 > 120.0F)) {
        f1 += 360.0F;
      }
      else if ((f1 > 120.0F) && (this.prevA0 < -120.0F))
        this.prevA0 += 360.0F;
      float f4 = f1 - this.prevA0;
      float f5 = 0.001F * (float)(Time.current() - this.prevTime);
      float f6 = Math.abs(f4 / f5);
      if (f6 > 120.0F)
        if (f1 > this.prevA0) {
          f1 = this.prevA0 + 120.0F * f5;
        }
        else if (f1 < this.prevA0)
          f1 = this.prevA0 - 120.0F * f5;
      this.prevTime = Time.current();
      if (f2 > 0.0F)
        f2 = 0.0F;
      if (f2 < -85.0F)
        f2 = -85.0F;
      paramOrient.setYPR(f1, f2, 0.0F);
      paramOrient.wrap();
      this.prevA0 = f1;
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
          this.hook1 = new HookNamed(aircraft(), "_MGUN07");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN07");
        if (this.hook2 == null)
          this.hook2 = new HookNamed(aircraft(), "_MGUN08");
        doHitMasterAircraft(aircraft(), this.hook2, "_MGUN08");
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

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
  }

  public CockpitB17E_BGunner()
  {
    super("3DO/Cockpit/A-20G-TGun/BGunnerB17E.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.prevTime = -1L;
    this.prevA0 = 0.0F;
    this.hook1 = null;
    this.hook2 = null;
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
    Property.set(CockpitB17E_BGunner.class, "aiTuretNum", 4);
    Property.set(CockpitB17E_BGunner.class, "weaponControlNum", 14);
    Property.set(CockpitB17E_BGunner.class, "astatePilotIndx", 7);
  }
}