package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitHE_111H6_NGunner extends CockpitGunner
{
  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((HE_111)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = false;
      aircraft().hierMesh().chunkVisible("Cockpit_D0", false);
      aircraft().hierMesh().chunkVisible("Turret1C_D0", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAK", false);
      aircraft().hierMesh().chunkVisible("Head1_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot1_FAL", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAK", false);
      aircraft().hierMesh().chunkVisible("Pilot2_FAL", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    ((HE_111)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).bPitUnfocused = true;
    aircraft().hierMesh().chunkVisible("Cockpit_D0", (aircraft().hierMesh().isChunkVisible("Nose_D0")) || (aircraft().hierMesh().isChunkVisible("Nose_D1")) || (aircraft().hierMesh().isChunkVisible("Nose_D2")));
    aircraft().hierMesh().chunkVisible("Turret1C_D0", aircraft().hierMesh().isChunkVisible("Turret1B_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAK", aircraft().hierMesh().isChunkVisible("Pilot1_D0"));
    aircraft().hierMesh().chunkVisible("Head1_FAK", aircraft().hierMesh().isChunkVisible("Head1_D0"));
    aircraft().hierMesh().chunkVisible("Pilot1_FAL", aircraft().hierMesh().isChunkVisible("Pilot1_D1"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAK", aircraft().hierMesh().isChunkVisible("Pilot2_D0"));
    aircraft().hierMesh().chunkVisible("Pilot2_FAL", aircraft().hierMesh().isChunkVisible("Pilot2_D1"));
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretA", 0.0F, f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretB", 0.0F, f2, 0.0F);
    if (f1 > 15.0F) f1 = 15.0F;
    if (f2 < -21.0F) f2 = -21.0F;
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CameraRodA", 0.0F, f1, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("CameraRodB", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -25.0F) f1 = -25.0F;
    if (f1 > 15.0F) f1 = 15.0F;
    if (f2 > 0.0F) f2 = 0.0F;
    if (f2 < -40.0F) f2 = -40.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.jdField_bGunFire_of_type_Boolean = false;
    }this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;

    if (this.jdField_bGunFire_of_type_Boolean)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Butona", 0.15F, 0.0F, 0.0F);
    else
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Butona", 0.0F, 0.0F, 0.0F);
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

  public CockpitHE_111H6_NGunner() {
    super("3DO/Cockpit/He-111H-6-NGun/hier.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40.0F * this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl);
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesL_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x8) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesL_D2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesR_D1", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x20) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesR_D2", true);
    }
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x1) != 0) {
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("ZHolesF_D1", true);
    }

    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x80) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("zOil_D1", true);
  }

  static
  {
    Property.set(CockpitHE_111H6_NGunner.class, "aiTuretNum", 0);
    Property.set(CockpitHE_111H6_NGunner.class, "weaponControlNum", 10);
    Property.set(CockpitHE_111H6_NGunner.class, "astatePilotIndx", 1);
  }
}