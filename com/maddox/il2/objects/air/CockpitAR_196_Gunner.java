// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitAR_196_Gunner.java

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

public class CockpitAR_196_Gunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Turret1B_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Turret1B_D0", ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().isChunkVisible("Turret1A_D0"));
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret_Base", 0.0F, orient.getYaw(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("MGun", 0.0F, orient.getTangage(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret_Base2", 0.0F, orient.getYaw(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("MGun2", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(orient.getTangage(), -20F, 45F, -20F, 45F), 0.0F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        if(((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
            if(!((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
            {
                orient.setYPR(0.0F, 0.0F, 0.0F);
            } else
            {
                float f = orient.getYaw();
                if(f < -90F)
                    f = -90F;
                if(f > 90F)
                    f = 90F;
                float f1 = orient.getTangage();
                if(f1 > 60F)
                    f1 = 60F;
                if(f1 < -30F)
                    f1 = -30F;
                float f2;
                for(f2 = java.lang.Math.abs(f); f2 > 180F; f2 -= 180F);
                if(f1 < -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 180F, 0.0F, 36F), angles))
                    f1 = -((com.maddox.il2.objects.air.Cockpit)this).floatindex(((com.maddox.il2.objects.air.Cockpit)this).cvt(f2, 0.0F, 180F, 0.0F, 36F), angles);
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(super.bGunFire)
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Trigger", 0.0F, 17.5F, 0.0F);
        else
            ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Trigger", 0.0F, 0.0F, 0.0F);
        if(((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
        {
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
                ((com.maddox.il2.objects.air.CockpitGunner)this).doHitMasterAircraft(((com.maddox.il2.objects.air.Cockpit)this).aircraft(), hook2, "_MGUN04");
            }
        }
    }

    public void doGunFire(boolean flag)
    {
        if(((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
        {
            if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
                super.bGunFire = false;
            else
                super.bGunFire = flag;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
        }
    }

    public CockpitAR_196_Gunner()
    {
        super("3DO/Cockpit/AR_196A3_Gun/AR_196_Gun.him", "he111_gunner");
        hook1 = null;
        hook2 = null;
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
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return class1;
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

    private static final float angles[] = {
        4F, 5.5F, 5.5F, 7F, 10.5F, 15.5F, 24F, 33F, 40F, 46F, 
        52.5F, 59F, 64.5F, 69F, 69F, 69F, 69F, 69F, 69F, 69F, 
        69F, 69F, 69F, 66.5F, 62.5F, 55F, 49.5F, -40F, -74.5F, -77F, 
        -77F, -77F, -77F, -77F, -77F, -77F, -77F
    };
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;
    private boolean bNeedSetUp;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitAR_196_Gunner.class)))), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitAR_196_Gunner.class)))), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitAR_196_Gunner.class)))), "astatePilotIndx", 1);
    }
}
