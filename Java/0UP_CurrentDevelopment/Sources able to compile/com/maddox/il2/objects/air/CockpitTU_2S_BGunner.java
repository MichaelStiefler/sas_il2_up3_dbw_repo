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

public class CockpitTU_2S_BGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("Turret1A", -14.2F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(isRealMode())
            if(!aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = orient.getYaw();
                float f1 = orient.getTangage();
                if(f < -30F)
                    f = -30F;
                if(f > 30F)
                    f = 30F;
                if(f1 > 15F)
                    f1 = 15F;
                if(f1 < -45F)
                    f1 = -45F;
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = new HookNamed(aircraft(), "_MGUN09");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN09");
            }
        }
    }

    public void doGunFire(boolean flag)
    {
        if(isRealMode())
        {
            if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
                bGunFire = false;
            else
                bGunFire = flag;
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    public void reflectCockpitState()
    {
        if(fm.AS.astateCockpitState != 0)
        {
            mesh.chunkVisible("XGlassDamage1", true);
            mesh.chunkVisible("XGlassDamage2", true);
            mesh.chunkVisible("XHullDamage1", true);
            mesh.chunkVisible("XHullDamage2", true);
            mesh.chunkVisible("XHullDamage3", true);
        }
    }

    public CockpitTU_2S_BGunner()
    {
        super("3DO/Cockpit/A-20G-BGun/BGunnerTU2S.him", "he111_gunner");
        hook1 = null;
    }

    private com.maddox.il2.engine.Hook hook1;

    static 
    {
        com.maddox.rts.Property.set(CockpitTU_2S_BGunner.class, "aiTuretNum", 2);
        com.maddox.rts.Property.set(CockpitTU_2S_BGunner.class, "weaponControlNum", 12);
        com.maddox.rts.Property.set(CockpitTU_2S_BGunner.class, "astatePilotIndx", 2);
    }
}
