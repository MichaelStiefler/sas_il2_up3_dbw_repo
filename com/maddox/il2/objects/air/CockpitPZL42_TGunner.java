// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitPZL42_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Cockpit

public class CockpitPZL42_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret1A", 0.0F, orient.getYaw(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret1B", 0.0F, orient.getTangage(), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
        {
            orient.setYPR(0.0F, 0.0F, 0.0F);
            return;
        }
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        if(f < -130F)
            f = -130F;
        if(f > 130F)
            f = 130F;
        if(f1 > 50F)
            f1 = 50F;
        if(f1 < -15F)
            f1 = -15F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
            super.bGunFire = false;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
        if(super.bGunFire)
        {
            if(hook1 == null)
                hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())), "_MGUN03")));
            ((com.maddox.il2.objects.air.CockpitGunner)this).doHitMasterAircraft(((com.maddox.il2.objects.air.Cockpit)this).aircraft(), hook1, "_MGUN03");
            if(hook2 == null)
                hook2 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())), "_MGUN04")));
            ((com.maddox.il2.objects.air.CockpitGunner)this).doHitMasterAircraft(((com.maddox.il2.objects.air.Cockpit)this).aircraft(), hook1, "_MGUN04");
        }
    }

    public void doGunFire(boolean flag)
    {
        if(!((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
            super.bGunFire = false;
        else
            super.bGunFire = flag;
        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
    }

    public CockpitPZL42_TGunner()
    {
        super("3DO/Cockpit/PZL42_TGun/hier.him", "i16");
        hook1 = null;
        bNeedSetUp = true;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss2D0o", mat);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;
    private boolean bNeedSetUp;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitPZL42_TGunner.class)))), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitPZL42_TGunner.class)))), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitPZL42_TGunner.class)))), "astatePilotIndx", 1);
    }
}
