package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitTB_3_TGunner2 extends CockpitGunner
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
    Loc localLoc = new Loc();
    aircraft().hierMesh().setCurChunk("Turret2B_D0");
    aircraft().hierMesh().getChunkLocObj(localLoc);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret2A", -localLoc.getOrient().getAzimut(), 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret2B", 0.0F, -localLoc.getOrient().getTangage(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret2C", 0.0F, localLoc.getOrient().getAzimut(), 0.0F);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot2"));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.materialReplace("Pilot2", localMat);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = -paramOrient.getTangage();
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3A", f1, 0.0F, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3B", 0.0F, f2, 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3C", 0.0F, f1, 0.0F);
    float f3 = 0.01905F * (float)Math.sin(Math.toRadians(f1));
    float f4 = 0.01905F * (float)Math.cos(Math.toRadians(f1));
    f1 = (float)Math.toDegrees(Math.atan(f3 / (f4 + 0.3565F)));
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("Turret3D", 0.0F, -f1, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();

    if (f2 < -47.0F) {
      f2 = -47.0F;
    }
    if (f2 > 47.0F) {
      f2 = 47.0F;
    }

    if (f1 < -68.0F) {
      if (f2 < -32.0F)
        f2 = -32.0F;
    }
    else if (f1 < -22.0F) {
      if (f2 < 0.5347826F * f1 + 4.365217F)
        f2 = 0.5347826F * f1 + 4.365217F;
    }
    else if (f1 < 27.0F) {
      if (f2 < -0.3387755F * f1 - 14.853062F)
        f2 = -0.3387755F * f1 - 14.853062F;
    }
    else if (f1 < 40.0F) {
      if (f2 < -1.769231F * f1 + 23.76923F)
        f2 = -1.769231F * f1 + 23.76923F;
    }
    else if (f1 < 137.0F) {
      if (f2 < -47.0F)
        f2 = -47.0F;
    }
    else if (f1 < 152.0F) {
      if (f2 < 1.0F * f1 - 184.0F) {
        f2 = 1.0F * f1 - 184.0F;
      }
    }
    else if (f2 < -32.0F) {
      f2 = -32.0F;
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((ActorHMesh)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().chunkVisible("Turret2C_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    ((ActorHMesh)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().chunkVisible("Turret2C_D0", ((ActorHMesh)this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().isChunkVisible("Turret2B_D0"));
    super.doFocusLeave();
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
        this.hook1 = new HookNamed(aircraft(), "_MGUN05");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN05");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN06");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN06");
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

  public CockpitTB_3_TGunner2()
  {
    super("3DO/Cockpit/TB-3-TGun2/hier.him", "i16");
  }

  static
  {
    Property.set(CockpitTB_3_TGunner2.class, "aiTuretNum", 2);
    Property.set(CockpitTB_3_TGunner2.class, "weaponControlNum", 12);
    Property.set(CockpitTB_3_TGunner2.class, "astatePilotIndx", 6);
  }
}