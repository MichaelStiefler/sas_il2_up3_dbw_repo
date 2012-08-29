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

public class CockpitTB_3_TGunner1 extends CockpitGunner
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
    aircraft().hierMesh().setCurChunk("Turret3B_D0");
    aircraft().hierMesh().getChunkLocObj(localLoc);
    this.mesh.chunkSetAngles("Turret3A", 180.0F - localLoc.getOrient().getAzimut(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turret3B", 0.0F, -localLoc.getOrient().getTangage(), 0.0F);
    this.mesh.chunkSetAngles("Turret3C", 0.0F, localLoc.getOrient().getAzimut(), 0.0F);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Pilot2"));
    this.mesh.materialReplace("Pilot2", localMat);
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = -paramOrient.getTangage();
    this.mesh.chunkSetAngles("Turret2A", f1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Turret2B", 0.0F, f2, 0.0F);
    this.mesh.chunkSetAngles("Turret2C", 0.0F, -f1, 0.0F);
    float f3 = 0.01905F * (float)Math.sin(Math.toRadians(f1));
    float f4 = 0.01905F * (float)Math.cos(Math.toRadians(f1));
    f1 = (float)Math.toDegrees(Math.atan(f3 / (f4 + 0.3565F)));
    this.mesh.chunkSetAngles("Turret2D", 0.0F, f1, 0.0F);
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

    if (f1 < -38.0F) {
      if (f2 < -32.0F)
        f2 = -32.0F;
    }
    else if (f1 < -16.0F) {
      if (f2 < 0.5909091F * f1 - 9.545455F)
        f2 = 0.5909091F * f1 - 9.545455F;
    }
    else if (f1 < 35.0F) {
      if (f2 < -19.0F)
        f2 = -19.0F;
    }
    else if (f1 < 44.0F) {
      if (f2 < -3.111111F * f1 + 89.888885F)
        f2 = -3.111111F * f1 + 89.888885F;
    }
    else if (f1 < 139.0F) {
      if (f2 < -47.0F)
        f2 = -47.0F;
    }
    else if (f1 < 150.0F) {
      if (f2 < 1.363636F * f1 - 236.54546F) {
        f2 = 1.363636F * f1 - 236.54546F;
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
      ((ActorHMesh)(ActorHMesh)this.fm.actor).hierMesh().chunkVisible("Turret3C_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    ((ActorHMesh)(ActorHMesh)this.fm.actor).hierMesh().chunkVisible("Turret3C_D0", ((ActorHMesh)(ActorHMesh)this.fm.actor).hierMesh().isChunkVisible("Turret3B_D0"));
    super.doFocusLeave();
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
        this.hook1 = new HookNamed(aircraft(), "_MGUN03");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN03");
      if (this.hook2 == null) {
        this.hook2 = new HookNamed(aircraft(), "_MGUN04");
      }
      doHitMasterAircraft(aircraft(), this.hook2, "_MGUN04");
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

  public CockpitTB_3_TGunner1()
  {
    super("3DO/Cockpit/TB-3-TGun1/hier.him", "i16");
  }

  static
  {
    Property.set(CockpitTB_3_TGunner1.class, "aiTuretNum", 1);
    Property.set(CockpitTB_3_TGunner1.class, "weaponControlNum", 11);
    Property.set(CockpitTB_3_TGunner1.class, "astatePilotIndx", 5);
  }
}