// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitLanc_FGunner.java

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

public class CockpitLanc_FGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Body", 180F, 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretC", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(orient.getYaw(), -38F, 38F, -15F, 15F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretE", -orient.getYaw(), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretF", 0.0F, orient.getTangage(), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretG", -((com.maddox.il2.objects.air.Cockpit)this).cvt(orient.getYaw(), -33F, 33F, -33F, 33F), 0.0F, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("TurretH", 0.0F, ((com.maddox.il2.objects.air.Cockpit)this).cvt(orient.getTangage(), -10F, 32F, -10F, 32F), 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[0] = ((com.maddox.il2.objects.air.Cockpit)this).cvt(java.lang.Math.max(java.lang.Math.abs(orient.getYaw()), java.lang.Math.abs(orient.getTangage())), 0.0F, 20F, 0.0F, 0.3F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("TurretI", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
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
                float f1 = orient.getTangage();
                if(f < -38F)
                    f = -38F;
                if(f > 38F)
                    f = 38F;
                if(f1 > 38F)
                    f1 = 38F;
                if(f1 < -41F)
                    f1 = -41F;
                orient.setYPR(f, f1, 0.0F);
                orient.wrap();
            }
    }

    protected void interpTick()
    {
        if(((com.maddox.il2.objects.air.CockpitGunner)this).isRealMode())
        {
            if(super.emitter == null || !super.emitter.haveBullets() || !((com.maddox.il2.objects.air.CockpitGunner)this).aiTurret().bIsOperable)
                super.bGunFire = false;
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.air.Cockpit)this).fm)).CT.WeaponControl[((com.maddox.il2.objects.air.CockpitGunner)this).weaponControlNum()] = super.bGunFire;
            if(super.bGunFire)
            {
                if(hook1 == null)
                    hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())), "_MGUN05")));
                ((com.maddox.il2.objects.air.CockpitGunner)this).doHitMasterAircraft(((com.maddox.il2.objects.air.Cockpit)this).aircraft(), hook1, "_MGUN05");
                if(hook2 == null)
                    hook2 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())), "_MGUN06")));
                ((com.maddox.il2.objects.air.CockpitGunner)this).doHitMasterAircraft(((com.maddox.il2.objects.air.Cockpit)this).aircraft(), hook2, "_MGUN06");
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

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.materialReplace("Gloss1D0o", mat);
    }

    public CockpitLanc_FGunner()
    {
        super("3DO/Cockpit/Lanc-NGun/hier.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
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

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitLanc_FGunner.class)))), "aiTuretNum", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitLanc_FGunner.class)))), "weaponControlNum", 12);
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitLanc_FGunner.class)))), "astatePilotIndx", 2);
    }
}
