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

public class CockpitTBM1_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;
  private long prevTime;
  private float prevA0;

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
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1A", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret1B", 0.0F, paramOrient.getTangage(), 0.0F);
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
      return;
    }
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
    if (f2 > 85.0F)
      f2 = 85.0F;
    if (f2 < cvt(f3, 137.0F, 180.0F, -0.3F, 42.0F))
      f2 = cvt(f3, 137.0F, 180.0F, -0.3F, 42.0F);
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
    this.prevA0 = f1;
  }

  protected void interpTick()
  {
    if (!isRealMode())
      return;
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

  public void doGunFire(boolean paramBoolean)
  {
    if (!isRealMode())
      return;
    if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
      this.jdField_bGunFire_of_type_Boolean = false;
    else
      this.jdField_bGunFire_of_type_Boolean = paramBoolean;
    this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
  }

  public void reflectCockpitState()
  {
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x4) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes1_D1", true);
    if ((this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateCockpitState & 0x10) != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Z_Holes2_D1", true);
  }

  public CockpitTBM1_TGunner()
  {
    super("3DO/Cockpit/TBM-TGun/hier.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.prevTime = -1L;
    this.prevA0 = 0.0F;
    this.hook1 = null;
  }

  static
  {
    Property.set(CockpitTBM1_TGunner.class, "aiTuretNum", 0);
    Property.set(CockpitTBM1_TGunner.class, "weaponControlNum", 10);
    Property.set(CockpitTBM1_TGunner.class, "astatePilotIndx", 1);
  }
}