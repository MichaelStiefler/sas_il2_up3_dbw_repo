package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;
import com.maddox.util.HashMapExt;

public class CockpitHE_111H2_BGunner extends CockpitGunner
{
  private LightPointActor light1;
  private LightPointActor light2;
  private Hook hook1 = null;
  private int iCocking = 0;
  private int iOldVisDrums = 2;
  private int iNewVisDrums = 2;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretBA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretBB", 0.0F, paramOrient.getTangage(), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient) {
    if (!isRealMode()) return;
    if (!aiTurret().bIsOperable) {
      paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      return;
    }
    float f1 = paramOrient.getYaw(); float f2 = paramOrient.getTangage();
    if (f1 < -35.0F) f1 = -35.0F;
    if (f1 > 40.0F) f1 = 40.0F;
    if (f2 > 46.0F) f2 = 46.0F;
    if (f2 < -30.0F) f2 = -30.0F;
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
      if (this.hook1 == null) {
        this.hook1 = new HookNamed(aircraft(), "_MGUN03");
      }
      doHitMasterAircraft(aircraft(), this.hook1, "_MGUN03");
      if (this.iCocking > 0) this.iCocking = 0; else
        this.iCocking = 1;
      this.iNewVisDrums = (int)(this.emitter.countBullets() / 250.0F);
      if (this.iNewVisDrums < this.iOldVisDrums) {
        this.iOldVisDrums = this.iNewVisDrums;
        this.mesh.chunkVisible("DrumB1", this.iNewVisDrums > 1);
        this.mesh.chunkVisible("DrumB2", this.iNewVisDrums > 0);
        sfxClick(13);
      }
    } else {
      this.iCocking = 0;
    }
    resetYPRmodifier();
    xyz[0] = (-0.07F * this.iCocking);
    this.mesh.chunkSetLocate("LeverB", xyz, ypr);
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

  public CockpitHE_111H2_BGunner() {
    super("3DO/Cockpit/He-111H-2-BGun/hier.him", "he111_gunner");

    HookNamed localHookNamed = new HookNamed(this.mesh, "LIGHT1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(203.0F, 198.0F, 161.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LIGHT1", this.light1);

    localHookNamed = new HookNamed(this.mesh, "LIGHT2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(203.0F, 198.0F, 161.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LIGHT2", this.light2);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl) {
      this.light1.light.setEmit(0.004F, 6.05F);
      this.light2.light.setEmit(1.1F, 0.2F);
      this.mesh.chunkVisible("Flare", true);
      setNightMats(true);
    } else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      this.mesh.chunkVisible("Flare", false);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (this.fm.AS.astateCockpitState != 0)
      this.mesh.chunkVisible("Holes_D1", true);
  }

  static
  {
    Property.set(CockpitHE_111H2_BGunner.class, "aiTuretNum", 2);
    Property.set(CockpitHE_111H2_BGunner.class, "weaponControlNum", 12);
    Property.set(CockpitHE_111H2_BGunner.class, "astatePilotIndx", 3);
  }
}