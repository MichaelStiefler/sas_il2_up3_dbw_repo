// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitG4M2E_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Cockpit

public class CockpitG4M2E_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Turret3B_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.engine.ActorHMesh) (((com.maddox.il2.objects.air.Cockpit)this).aircraft())).hierMesh().chunkVisible("Turret3B_D0", true);
        super.doFocusLeave();
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret3A", 0.0F, -f, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret3B", 0.0F, f1, 0.0F);
        if(f < -33F)
            f = -33F;
        if(f > 33F)
            f = 33F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret3D", 0.0F, -f, 0.0F);
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetAngles("Turret3E", 0.0F, f1, 0.0F);
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
        if(f < -50F)
            f = -50F;
        if(f > 50F)
            f = 50F;
        if(f1 > ((com.maddox.il2.objects.air.Cockpit)this).cvt(java.lang.Math.abs(f), 0.0F, 50F, 40F, 25F))
            f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(java.lang.Math.abs(f), 0.0F, 50F, 40F, 25F);
        if(f1 < ((com.maddox.il2.objects.air.Cockpit)this).cvt(java.lang.Math.abs(f), 0.0F, 50F, -10F, -3.5F))
            f1 = ((com.maddox.il2.objects.air.Cockpit)this).cvt(java.lang.Math.abs(f), 0.0F, 50F, -10F, -3.5F);
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
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
        } else
        {
            iCocking = 0;
        }
        ((com.maddox.il2.objects.air.Cockpit)this).resetYPRmodifier();
        com.maddox.il2.objects.air.Cockpit.xyz[1] = -0.07F * (float)iCocking;
        com.maddox.il2.objects.air.Cockpit.ypr[1] = 0.0F;
        ((com.maddox.il2.objects.air.Cockpit)this).mesh.chunkSetLocate("Turret3C", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
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

    public CockpitG4M2E_TGunner()
    {
        super("3DO/Cockpit/G4M2E-TGun/hier.him", "he111_gunner");
        bNeedSetUp = true;
        iCocking = 0;
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

    public void reflectCockpitState()
    {
    }

    private boolean bNeedSetUp;
    private int iCocking;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "weaponControlNum", 12);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 3);
    }
}
