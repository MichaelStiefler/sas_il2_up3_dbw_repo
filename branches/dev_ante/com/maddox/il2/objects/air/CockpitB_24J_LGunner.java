package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB_24J_LGunner extends CockpitGunner
{
  private boolean bNeedSetUp;
  private Hook hook1;

  protected boolean doFocusEnter()
  {
    if (super.doFocusEnter())
    {
      aircraft().hierMesh().chunkVisible("Tail1_D0", false);
      aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
      aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
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
    aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
    aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
    aircraft().hierMesh().chunkVisible("Pilot7_D0", true);
    aircraft().hierMesh().chunkVisible("Head7_D0", true);
    aircraft().hierMesh().chunkVisible("Pilot8_D0", true);
    aircraft().hierMesh().chunkVisible("Head8_D0", true);
    super.doFocusLeave();
  }

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretLA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretLB", -5.3F, paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretLC", 0.0F, -paramOrient.getTangage(), 0.0F);
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
        if (f1 < -34.0F)
          f1 = -34.0F;
        if (f1 > 50.0F)
          f1 = 50.0F;
        if (f2 > 32.0F)
          f2 = 32.0F;
        if (f2 < -30.0F)
          f2 = -30.0F;
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode())
    {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.bGunFire = false;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
      if (this.bGunFire)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN07");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN07");
      }
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (isRealMode())
    {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.bGunFire = false;
      else
        this.bGunFire = paramBoolean;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public CockpitB_24J_LGunner()
  {
    super("3DO/Cockpit/B-25J-LGun/LGunnerB24new.him", "he111_gunner");
    this.bNeedSetUp = true;
    this.hook1 = null;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("TurretRA", 0.0F, aircraft().FM.turret[4].tu[0], 0.0F);
    this.mesh.chunkSetAngles("TurretRB", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
    this.mesh.chunkSetAngles("TurretRC", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
    this.mesh.chunkVisible("TurretRC", false);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
    {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XHullDamage3", true);
    }
  }

  static
  {
    Property.set(CockpitB_24J_LGunner.class, "aiTuretNum", 3);
    Property.set(CockpitB_24J_LGunner.class, "weaponControlNum", 13);
    Property.set(CockpitB_24J_LGunner.class, "astatePilotIndx", 5);
  }
}