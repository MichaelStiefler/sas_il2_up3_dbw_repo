package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB29SP_AGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      reflectPlaneToModel();
      this.bNeedSetUp = false;
    }

    float f = this.fm.CT.getElevator();
    this.mesh.chunkSetAngles("VatorL_D0", 0.0F, -30.0F * f, 0.0F);
    this.mesh.chunkSetAngles("VatorR_D0", 0.0F, -30.0F * f, 0.0F);
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.mesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
  }

  protected void reflectPlaneToModel() {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("VatorL_D0", localHierMesh.isChunkVisible("VatorL_D0"));
    this.mesh.chunkVisible("VatorL_D1", localHierMesh.isChunkVisible("VatorL_D1"));
    this.mesh.chunkVisible("VatorL_CAP", localHierMesh.isChunkVisible("VatorL_CAP"));
    this.mesh.chunkVisible("VatorR_D0", localHierMesh.isChunkVisible("VatorR_D0"));
    this.mesh.chunkVisible("VatorR_D1", localHierMesh.isChunkVisible("VatorR_D1"));
    this.mesh.chunkVisible("VatorR_CAP", localHierMesh.isChunkVisible("VatorR_CAP"));
  }

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretC", 0.0F, cvt(paramOrient.getYaw(), -38.0F, 38.0F, -15.0F, 15.0F), 0.0F);

    this.mesh.chunkSetAngles("TurretD", 0.0F, cvt(paramOrient.getTangage(), -43.0F, 43.0F, -10.0F, 10.0F), 0.0F);

    this.mesh.chunkSetAngles("TurretE", -paramOrient.getYaw(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("TurretF", 0.0F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretG", -cvt(paramOrient.getYaw(), -33.0F, 33.0F, -33.0F, 33.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("TurretH", 0.0F, cvt(paramOrient.getTangage(), -10.0F, 32.0F, -10.0F, 32.0F), 0.0F);

    resetYPRmodifier();
    Cockpit.xyz[0] = cvt(Math.max(Math.abs(paramOrient.getYaw()), Math.abs(paramOrient.getTangage())), 0.0F, 20.0F, 0.0F, 0.3F);

    this.mesh.chunkSetLocate("TurretI", Cockpit.xyz, Cockpit.ypr);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -38.0F)
          f1 = -38.0F;
        if (f1 > 38.0F)
          f1 = 38.0F;
        if (f2 > 43.0F)
          f2 = 43.0F;
        if (f2 < -41.0F)
          f2 = -41.0F;
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public void doGunFire(boolean paramBoolean) {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }
      else this.bGunFire = paramBoolean;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public CockpitB29SP_AGunner() {
    super("3DO/Cockpit/B-25J-AGun/AGunnerB29SP.him", "bf109");
  }

  static
  {
    Property.set(CockpitB29SP_AGunner.class, "aiTuretNum", 4);

    Property.set(CockpitB29SP_AGunner.class, "weaponControlNum", 14);

    Property.set(CockpitB29SP_AGunner.class, "astatePilotIndx", 4);
  }
}