// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitIL2_Gunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner

public class CockpitIL2_Gunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurrelA", 0.0F, orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurrelB", 0.0F, orient.getTangage(), 0.0F);
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
                hook1 = new HookNamed(aircraft(), "_MGUN03");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
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

    public CockpitIL2_Gunner()
    {
        super("3do/cockpit/Il-2-Gun/hier.him", "il2rear");
        hook1 = null;
    }

    private com.maddox.il2.engine.Hook hook1;
}
