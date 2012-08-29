package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitHE_111H2_NGunner extends CockpitGunner
{
  private int iCocking = 0;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter()) {
      ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = false;
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
    ((HE_111)(HE_111)this.fm.actor).bPitUnfocused = true;
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
    this.mesh.chunkSetAngles("TurretA", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, f2, 0.0F);
    if (f1 > 15.0F) f1 = 15.0F;
    if (f2 < -21.0F) f2 = -21.0F;
    this.mesh.chunkSetAngles("CameraRodA", 0.0F, f1, 0.0F);
    this.mesh.chunkSetAngles("CameraRodB", 0.0F, f2, 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -35.0F) f1 = -35.0F;
    if (f1 > 15.0F) f1 = 15.0F;
    if (f2 > 13.0F) f2 = 13.0F;
    if (f2 < -27.0F) f2 = -27.0F;
    paramOrient.setYPR(f1, f2, 0.0F);
    paramOrient.wrap();
  }

  protected void interpTick() {
    if (!isRealMode()) return;
    if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
    {
      this.bGunFire = false;
    }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;

    if (this.bGunFire) {
      if (this.iCocking > 0) this.iCocking = 0; else
        this.iCocking = 1;
    }
    else this.iCocking = 0;

    resetYPRmodifier();
    xyz[0] = (-0.07F * this.iCocking);
    ypr[1] = 0.0F;
    this.mesh.chunkSetLocate("Lever", xyz, ypr);
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

  public CockpitHE_111H2_NGunner() {
    super("3DO/Cockpit/He-111H-2-NGun/hier.him", "he111_gunner");
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("zColumn1", 0.0F, 0.0F, -10.0F * this.fm.CT.ElevatorControl);
    this.mesh.chunkSetAngles("zColumn2", 0.0F, 0.0F, -40.0F * this.fm.CT.AileronControl);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("ZHolesL_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("ZHolesL_D2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("ZHolesR_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("ZHolesR_D2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("ZHolesF_D1", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("zOil_D1", true);
  }

  static
  {
    Property.set(CockpitHE_111H2_NGunner.class, "aiTuretNum", 0);
    Property.set(CockpitHE_111H2_NGunner.class, "weaponControlNum", 10);
    Property.set(CockpitHE_111H2_NGunner.class, "astatePilotIndx", 1);
  }
}