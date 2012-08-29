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

public class CockpitB_24J100_LGunner extends CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Tail1_D0", false);
            aircraft().hierMesh().chunkVisible("Turret5B_D0", false);
            aircraft().hierMesh().chunkVisible("Turret4B_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot7_D0", false);
            aircraft().hierMesh().chunkVisible("Head7_D0", false);
            aircraft().hierMesh().chunkVisible("Pilot8_D0", false);
            aircraft().hierMesh().chunkVisible("Head8_D0", false);
            return true;
        } else
        {
            return false;
        }
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

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretLA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretLB", -5.3F, orient.getTangage(), 0.0F);
        mesh.chunkSetAngles("TurretLC", 0.0F, -orient.getTangage(), 0.0F);
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
                if(f < -34F)
                    f = -34F;
                if(f > 50F)
                    f = 50F;
                if(f1 > 32F)
                    f1 = 32F;
                if(f1 < -30F)
                    f1 = -30F;
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
                    hook1 = new HookNamed(aircraft(), "_MGUN07");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN07");
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

    public CockpitB_24J100_LGunner()
    {
        super("3DO/Cockpit/B-25J-LGun/LGunnerB24.him", "he111_gunner");
        bNeedSetUp = true;
        hook1 = null;
    }

    public void reflectWorldToInstruments(float f)
    {
        mesh.chunkSetAngles("TurretRA", 0.0F, aircraft().FM.turret[4].tu[0], 0.0F);
        mesh.chunkSetAngles("TurretRB", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
        mesh.chunkSetAngles("TurretRC", 0.0F, aircraft().FM.turret[4].tu[1], 0.0F);
        mesh.chunkVisible("TurretRC", false);
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
        com.maddox.rts.Property.set(CockpitB_24J100_LGunner.class, "aiTuretNum", 3);
        com.maddox.rts.Property.set(CockpitB_24J100_LGunner.class, "weaponControlNum", 13);
        com.maddox.rts.Property.set(CockpitB_24J100_LGunner.class, "astatePilotIndx", 5);
    }
}