package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitTB_3_RGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;

  Loc l = new Loc();

  private Hook hook1 = null;
  private Hook hook2 = null;

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }
    if ((aircraft() instanceof TB_3_4M_34R_SPB)) { ((TB_3_4M_34R_SPB)aircraft()); if (TB_3_4M_34R_SPB.bChangedPit) {
        reflectPlaneToModel();
        ((TB_3_4M_34R_SPB)aircraft()); TB_3_4M_34R_SPB.bChangedPit = false;
      } }
    if ((aircraft() instanceof TB_3_4M_34R)) { ((TB_3_4M_34R)aircraft()); if (TB_3_4M_34R.bChangedPit) {
        reflectPlaneToModel();
        ((TB_3_4M_34R)aircraft()); TB_3_4M_34R.bChangedPit = false;
      } }
    aircraft().hierMesh().setCurChunk("VatorL_D0");
    aircraft().hierMesh().getChunkLocObj(this.l);
    this.mesh.chunkSetAngles("VatorL_D0", 0.0F, this.l.getOrient().getTangage(), 0.0F);
    aircraft().hierMesh().setCurChunk("VatorR_D0");
    aircraft().hierMesh().getChunkLocObj(this.l);
    this.mesh.chunkSetAngles("VatorR_D0", 0.0F, this.l.getOrient().getTangage(), 0.0F);
    aircraft().hierMesh().setCurChunk("Rudder1_D0");
    aircraft().hierMesh().getChunkLocObj(this.l);
    this.mesh.chunkSetAngles("Rudder1_D0", 0.0F, this.l.getOrient().getAzimut(), 0.0F);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt1D0o"));
    this.mesh.materialReplace("Matt1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Overlay8"));
    this.mesh.materialReplace("Overlay8", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("OverlayD2o"));
    this.mesh.materialReplace("OverlayD2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot2"));
    this.mesh.materialReplace("Pilot2", localMat);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("StabL_D0", localHierMesh.isChunkVisible("StabL_D0"));
    this.mesh.chunkVisible("StabL_D1", localHierMesh.isChunkVisible("StabL_D1"));
    this.mesh.chunkVisible("StabL_D2", localHierMesh.isChunkVisible("StabL_D2"));
    this.mesh.chunkVisible("StabL_D3", localHierMesh.isChunkVisible("StabL_D3"));
    this.mesh.chunkVisible("StabL_CAP", localHierMesh.isChunkVisible("StabL_CAP"));
    this.mesh.chunkVisible("StabR_D0", localHierMesh.isChunkVisible("StabR_D0"));
    this.mesh.chunkVisible("StabR_D1", localHierMesh.isChunkVisible("StabR_D1"));
    this.mesh.chunkVisible("StabR_D2", localHierMesh.isChunkVisible("StabR_D2"));
    this.mesh.chunkVisible("StabR_D3", localHierMesh.isChunkVisible("StabR_D3"));
    this.mesh.chunkVisible("StabR_CAP", localHierMesh.isChunkVisible("StabR_CAP"));
    this.mesh.chunkVisible("VatorL_D0", localHierMesh.isChunkVisible("VatorL_D0"));
    this.mesh.chunkVisible("VatorL_D1", localHierMesh.isChunkVisible("VatorL_D1"));
    this.mesh.chunkVisible("VatorR_D0", localHierMesh.isChunkVisible("VatorR_D0"));
    this.mesh.chunkVisible("VatorR_D1", localHierMesh.isChunkVisible("VatorR_D1"));
    this.mesh.chunkVisible("Rudder1_D0", localHierMesh.isChunkVisible("Rudder1_D0"));
    this.mesh.chunkVisible("Rudder1_D1", localHierMesh.isChunkVisible("Rudder1_D1"));
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = -paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret3A", f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turret3B", 0.0F, -f2, 0.0F);
    this.mesh.chunkSetAngles("Turret3C", 0.0F, f1, 0.0F);
    float f3 = 0.01905F * (float)Math.sin(Math.toRadians(f1));
    float f4 = 0.01905F * (float)Math.cos(Math.toRadians(f1));
    f1 = (float)Math.toDegrees(Math.atan(f3 / (f4 + 0.3565F)));
    this.mesh.chunkSetAngles("Turret3D", 0.0F, -f1, 0.0F);
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
    if (f1 < -90.0F) {
      f1 = -90.0F;
    }
    if (f1 > 90.0F) {
      f1 = 90.0F;
    }

    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick()
  {
    if (!isRealMode()) {
      return;
    }
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    if (this.bGunFire) {
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
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }
    else this.bGunFire = paramBoolean;
    this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
  }

  public CockpitTB_3_RGunner()
  {
    super("3DO/Cockpit/TB-3-RGun/hier.him", "i16");
  }

  static
  {
    Property.set(CockpitTB_3_RGunner.class, "aiTuretNum", 2);
    Property.set(CockpitTB_3_RGunner.class, "weaponControlNum", 12);
    Property.set(CockpitTB_3_RGunner.class, "astatePilotIndx", 6);
  }
}