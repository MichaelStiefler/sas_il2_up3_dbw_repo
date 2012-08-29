package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class CockpitB25C25_TGunner extends CockpitGunner
{
  private boolean bNeedSetUp = true;
  private long prevTime = -1L;
  private float prevA0 = 0.0F;
  private Hook hook1 = null;
  private Hook hook2 = null;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      aircraft().hierMesh().chunkVisible("Turret2B_D0", false);
      return true;
    }
    return false;
  }

  protected void doFocusLeave() {
    aircraft().hierMesh().chunkVisible("Turret2B_D0", aircraft().hierMesh().isChunkVisible("Turret2A_D0"));

    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient) {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretC", 0.0F, cvt(paramOrient.getTangage(), -10.0F, 58.0F, -10.0F, 58.0F), 0.0F);
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

    if (!isRealMode()) {
      this.prevA0 = f1;
    } else {
      if (this.bNeedSetUp) {
        this.prevTime = (Time.current() - 1L);
        this.bNeedSetUp = false;
        reflectPlaneMats();
      }
      if ((f1 < -120.0F) && (this.prevA0 > 120.0F))
        f1 += 360.0F;
      else if ((f1 > 120.0F) && (this.prevA0 < -120.0F))
        this.prevA0 += 360.0F;
      float f4 = f1 - this.prevA0;
      float f5 = 0.001F * (float)(Time.current() - this.prevTime);
      float f6 = Math.abs(f4 / f5);
      if (f6 > 120.0F) {
        if (f1 > this.prevA0)
          f1 = this.prevA0 + 120.0F * f5;
        else if (f1 < this.prevA0)
          f1 = this.prevA0 - 120.0F * f5;
      }
      this.prevTime = Time.current();
      if (f2 > 73.0F)
        f2 = 73.0F;
      if (f2 < 0.0F)
        f2 = 0.0F;
      paramOrient.setYPR(f1, f2, 0.0F);
      paramOrient.wrap();
      this.prevA0 = f1;
    }
  }

  protected void interpTick() {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
      if (this.bGunFire) {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN03");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN03");
        if (this.hook2 == null)
          this.hook2 = new HookNamed(aircraft(), "_MGUN04");
        doHitMasterAircraft(aircraft(), this.hook2, "_MGUN04");
      }
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

  public void reflectWorldToInstruments(float paramFloat) {
    if (this.bNeedSetUp) {
      this.prevTime = (Time.current() - 1L);
      this.bNeedSetUp = false;
      reflectPlaneMats();
    }
  }

  protected void reflectPlaneMats() {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
  }

  public CockpitB25C25_TGunner() {
    super("3DO/Cockpit/B-25J-TGun/TGunnerB25C25.him", "bf109");
  }

  static
  {
    Property.set(CockpitB25C25_TGunner.class, "aiTuretNum", 1);

    Property.set(CockpitB25C25_TGunner.class, "weaponControlNum", 11);

    Property.set(CockpitB25C25_TGunner.class, "astatePilotIndx", 3);
  }
}