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

public class CockpitFW200_BGunner extends CockpitGunner
{
  private LightPointActor light1;
  private LightPointActor light2;
  private Hook hook1;
  private int iCocking;
  private int iOldVisDrums;
  private int iNewVisDrums;

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretBA", -40.0F, -paramOrient.getYaw(), 0.0F);
    this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetAngles("TurretBB", 0.0F, paramOrient.getTangage(), 0.0F);
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
        if (f1 < -40.0F)
          f1 = -40.0F;
        if (f1 > 40.0F)
          f1 = 40.0F;
        if (f2 > 0.0F)
          f2 = 0.0F;
        if (f2 < -40.0F)
          f2 = -40.0F;
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
      if (this.jdField_bGunFire_of_type_Boolean)
      {
        if (this.hook1 == null)
          this.hook1 = new HookNamed(aircraft(), "_MGUN03");
        doHitMasterAircraft(aircraft(), this.hook1, "_MGUN03");
        if (this.iCocking > 0)
          this.iCocking = 0;
        else
          this.iCocking = 1;
        this.iNewVisDrums = (int)(this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.countBullets() / 250.0F);
        if (this.iNewVisDrums < this.iOldVisDrums)
        {
          this.iOldVisDrums = this.iNewVisDrums;
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("DrumB1", this.iNewVisDrums > 1);
          this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("DrumB2", this.iNewVisDrums > 0);
          sfxClick(13);
        }
      }
      else {
        this.iCocking = 0;
      }
      resetYPRmodifier();
      Cockpit.xyz[0] = (-0.07F * this.iCocking);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkSetLocate("LeverB", Cockpit.xyz, Cockpit.ypr);
    }
  }

  public void doGunFire(boolean paramBoolean)
  {
    if (isRealMode())
    {
      if ((this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter == null) || (!this.jdField_emitter_of_type_ComMaddoxIl2AiBulletEmitter.haveBullets()) || (!aiTurret().bIsOperable))
        this.jdField_bGunFire_of_type_Boolean = false;
      else
        this.jdField_bGunFire_of_type_Boolean = paramBoolean;
      this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[weaponControlNum()] = this.jdField_bGunFire_of_type_Boolean;
    }
  }

  public CockpitFW200_BGunner()
  {
    super("3DO/Cockpit/He-111H-2-BGun/BGunnerFW200.him", "he111_gunner");
    this.hook1 = null;
    this.iCocking = 0;
    this.iOldVisDrums = 2;
    this.iNewVisDrums = 2;
    HookNamed localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LIGHT1");
    Loc localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light1 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light1.light.setColor(203.0F, 198.0F, 161.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LIGHT1", this.light1);
    localHookNamed = new HookNamed(this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh, "LIGHT2");
    localLoc = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    localHookNamed.computePos(this, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F), localLoc);
    this.light2 = new LightPointActor(new LightPoint(), localLoc.getPoint());
    this.light2.light.setColor(203.0F, 198.0F, 161.0F);
    this.light2.light.setEmit(0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base().draw.lightMap().put("LIGHT2", this.light2);
  }

  public void toggleLight()
  {
    this.jdField_cockpitLightControl_of_type_Boolean = (!this.jdField_cockpitLightControl_of_type_Boolean);
    if (this.jdField_cockpitLightControl_of_type_Boolean)
    {
      this.light1.light.setEmit(0.004F, 6.05F);
      this.light2.light.setEmit(1.1F, 0.2F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Flare", true);
      setNightMats(true);
    }
    else {
      this.light1.light.setEmit(0.0F, 0.0F);
      this.light2.light.setEmit(0.0F, 0.0F);
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Flare", false);
      setNightMats(false);
    }
  }

  public void reflectCockpitState()
  {
    if (this.jdField_fm_of_type_ComMaddoxIl2FmFlightModel.AS.astateCockpitState != 0)
      this.jdField_mesh_of_type_ComMaddoxIl2EngineHierMesh.chunkVisible("Holes_D1", true);
  }

  static Class _mthclass$(String paramString)
  {
    Class localClass;
    try {
      localClass = Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
    return localClass;
  }

  static
  {
    Property.set(CockpitFW200_BGunner.class, "aiTuretNum", 5);
    Property.set(CockpitFW200_BGunner.class, "weaponControlNum", 15);
    Property.set(CockpitFW200_BGunner.class, "astatePilotIndx", 7);
  }
}