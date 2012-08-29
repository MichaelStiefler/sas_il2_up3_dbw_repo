package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB25H_RGunner extends CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("CF_D0", false);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot5_D0", false);
            aircraft().hierMesh().chunkVisible("Head5_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("CF_D0", true);
        aircraft().hierMesh().chunkVisible("Turret4B_D0", true);
        aircraft().hierMesh().chunkVisible("Turret5B_D0", true);
        aircraft().hierMesh().chunkVisible("Pilot5_D0", true);
        aircraft().hierMesh().chunkVisible("Head5_D0", true);
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretRA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretRB", 0.0F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretRC", 0.0F, -orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!isRealMode())
            return;
        if(!aiTurret().bIsOperable)
        {
            orient.setYPR(0.0F, 0.0F, 0.0F);
            return;
        }
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        if(f < -85F)
            f = -85F;
        if(f > 22F)
            f = 22F;
        if(f1 > 32F)
            f1 = 32F;
        if(f1 < -40F)
            f1 = -40F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN11");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN11");
        }
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        else
            bGunFire = flag;
        fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
    }

    public CockpitB25H_RGunner()
    {
        super("3DO/Cockpit/B-25J-RGun/hier.him", "he111_gunner");
        bNeedSetUp = true;
        hook1 = null;
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("TurretLA", 0.0F, aircraft().FM.turret[4].tu[0], 0.0F);
        mesh.chunkSetAngles("TurretLB", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
        mesh.chunkSetAngles("TurretLC", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
        mesh.chunkVisible("TurretLC", false);
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("XGlassDamage1", true);
        if((fm.AS.astateCockpitState & 8) != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XHullDamage1", true);
        }
        if((fm.AS.astateCockpitState & 0x10) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XHullDamage2", true);
        }
        if((fm.AS.astateCockpitState & 0x20) != 0)
        {
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;

    static 
    {
        com.maddox.rts.Property.set(CockpitB25H_RGunner.class, "aiTuretNum", 3);
        com.maddox.rts.Property.set(CockpitB25H_RGunner.class, "weaponControlNum", 13);
        com.maddox.rts.Property.set(CockpitB25H_RGunner.class, "astatePilotIndx", 4);
    }
}
