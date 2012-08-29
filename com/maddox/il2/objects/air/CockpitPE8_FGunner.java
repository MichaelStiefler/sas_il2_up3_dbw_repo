package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitPE8_FGunner extends CockpitGunner
{
  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    float f1 = -paramOrient.getYaw();
    float f2 = paramOrient.getTangage();
    this.mesh.chunkSetAngles("TurretA", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, f2, 0.0F);
    if (f1 > 15.0F)
      f1 = 15.0F;
    if (f2 < -21.0F)
      f2 = -21.0F;
    this.mesh.chunkSetAngles("CameraRodA", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("CameraRodB", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -25.0F)
          f1 = -25.0F;
        if (f1 > 25.0F)
          f1 = 25.0F;
        if (f2 > 20.0F)
          f2 = 20.0F;
        if (f2 < -40.0F)
          f2 = -40.0F;
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
      if (this.bGunFire)
        this.mesh.chunkSetAngles("Butona", 0.15F, 0.0F, 0.0F);
      else
        this.mesh.chunkSetAngles("Butona", 0.0F, 0.0F, 0.0F);
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

  public CockpitPE8_FGunner() {
    super("3DO/Cockpit/He-111H-6-NGun/FGunnerPE8.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat) {
    this.mesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10.0F * this.fm.CT.ElevatorControl);

    this.mesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40.0F * this.fm.CT.AileronControl);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("ZHolesL_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("ZHolesL_D2", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("ZHolesR_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("ZHolesR_D2", true);
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("ZHolesF_D1", true);
    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("zOil_D1", true);
  }

  static
  {
    Property.set(CockpitPE8_FGunner.class, "aiTuretNum", 0);

    Property.set(CockpitPE8_FGunner.class, "weaponControlNum", 10);

    Property.set(CockpitPE8_FGunner.class, "astatePilotIndx", 2);
  }
}