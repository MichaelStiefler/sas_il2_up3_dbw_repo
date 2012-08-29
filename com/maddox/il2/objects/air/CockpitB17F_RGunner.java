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

public class CockpitB17F_RGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Tail1_D0", false);
      aircraft().hierMesh().chunkVisible("Turret6B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret7B_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot7_D0", false);
      aircraft().hierMesh().chunkVisible("Head7_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot8_D0", false);
      aircraft().hierMesh().chunkVisible("Head8_D0", false);
      return true;
    }

    return false;
  }

  protected void doFocusLeave()
  {
    aircraft().hierMesh().chunkVisible("Tail1_D0", true);
    aircraft().hierMesh().chunkVisible("Turret6B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret7B_D0", true);
    aircraft().hierMesh().chunkVisible("Pilot7_D0", true);
    aircraft().hierMesh().chunkVisible("Head7_D0", true);
    aircraft().hierMesh().chunkVisible("Pilot8_D0", true);
    aircraft().hierMesh().chunkVisible("Head8_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretRA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretRB", 25.0F, paramOrient.getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretRC", 0.0F, -paramOrient.getTangage(), 0.0F);
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
        if (f1 < -75.0F)
          f1 = -75.0F;
        if (f1 > 20.0F)
          f1 = 20.0F;
        if (f2 > 32.0F)
          f2 = 32.0F;
        if (f2 < -40.0F)
          f2 = -40.0F;
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
          this.hook1 = new HookNamed(aircraft(), "_MGUN11");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN11");
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

  public CockpitB17F_RGunner()
  {
    super("3DO/Cockpit/B-25J-RGun/RGunnerB17F.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.hook1 = null;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretLA", 0.0F, aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].tu[0], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretLB", 0.0F, aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].tu[1], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretLC", 0.0F, aircraft().jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[4].tu[1], 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("TurretLC", false);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage1", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0)
    {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XGlassDamage2", true);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("XHullDamage3", true);
    }
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
    Property.set(CockpitB17F_RGunner.class, "aiTuretNum", 6);
    Property.set(CockpitB17F_RGunner.class, "weaponControlNum", 15);
    Property.set(CockpitB17F_RGunner.class, "astatePilotIndx", 6);
  }
}