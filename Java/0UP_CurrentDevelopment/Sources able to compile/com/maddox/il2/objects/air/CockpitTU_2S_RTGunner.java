package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class CockpitTU_2S_RTGunner extends CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurrelA", 0.0F, orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurrelB", 0.0F, orient.getTangage(), 0.0F);
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
                if(f < -45F)
                    f = -45F;
                if(f > 45F)
                    f = 45F;
                if(f1 > 45F)
                    f1 = 45F;
                if(f1 < -12F)
                    f1 = -12F;
                if(java.lang.Math.abs(f) < 3.5F)
                {
                    if(f1 < -2.5F)
                        f1 = -2.5F;
                } else
                if(java.lang.Math.abs(f) < 18.5F && f1 < -2.5F - 0.6333333F * (java.lang.Math.abs(f) - 3.5F))
                    f1 = -2.5F - 0.6333333F * (java.lang.Math.abs(f) - 3.5F);
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
                    hook1 = new HookNamed(aircraft(), "_MGUN03");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
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

    public CockpitTU_2S_RTGunner()
    {
        super("3do/cockpit/Il-2-Gun/RTGunnerTU2S.him", "il2rear");
        hook1 = null;
    }

    private com.maddox.il2.engine.Hook hook1;
    private static final float speedometerScale[] = {
        0.0F, 0.0F, 10.5F, 42.5F, 85F, 125F, 165.5F, 181F, 198F, 214.5F, 
        231F, 249F, 266.5F, 287.5F, 308F, 326.5F, 346F
    };

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 1);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 11);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 2);
    }
}
