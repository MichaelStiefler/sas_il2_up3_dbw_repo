package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitMBR_2AM34_NGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("Turret1A", f, 0.0F, 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Turret2B", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Turret1C", 0.0F, -f, 0.0F);
        float f2 = 0.01905F * (float)java.lang.Math.sin(java.lang.Math.toRadians(f));
        float f3 = 0.01905F * (float)java.lang.Math.cos(java.lang.Math.toRadians(f));
        f = (float)java.lang.Math.toDegrees(java.lang.Math.atan(f2 / (f3 + 0.3565F)));
        mesh.chunkSetAngles("Turret1D", 0.0F, f, 0.0F);
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
                float f2 = java.lang.Math.abs(f);
                if(f1 < -47F)
                    f1 = -47F;
                if(f1 > 47F)
                    f1 = 47F;
                if(f2 < 147F)
                {
                    if(f1 < 0.5964912F * f2 - 117.6842F)
                        f1 = 0.5964912F * f2 - 117.6842F;
                } else
                if(f2 < 157F)
                {
                    if(f1 < 0.3F * f2 - 74.1F)
                        f1 = 0.3F * f2 - 74.1F;
                } else
                if(f1 < 0.2173913F * f2 - 61.13044F)
                    f1 = 0.2173913F * f2 - 61.13044F;
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
                    hook1 = new HookNamed(aircraft(), "_MGUN01");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN01");
                if(hook2 == null)
                    hook2 = new HookNamed(aircraft(), "_MGUN02");
                doHitMasterAircraft(aircraft(), hook2, "_MGUN02");
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

    public CockpitMBR_2AM34_NGunner()
    {
        super("3DO/Cockpit/TB-3-NGun/NGunnerMBR2AM34.him", "i16");
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = aircraft().FM.CT.Weapons[weaponControlNum()];
        if(abulletemitter != null)
        {
            for(int i = 0; i < abulletemitter.length; i++)
                if(abulletemitter[i] instanceof com.maddox.il2.engine.Actor)
                    ((com.maddox.il2.engine.Actor)abulletemitter[i]).visibilityAsBase(false);

        }
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(CockpitMBR_2AM34_NGunner.class, "aiTuretNum", 0);
        com.maddox.rts.Property.set(CockpitMBR_2AM34_NGunner.class, "weaponControlNum", 10);
        com.maddox.rts.Property.set(CockpitMBR_2AM34_NGunner.class, "astatePilotIndx", 2);
    }
}
