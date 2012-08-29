// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitG4M2E_AGunner.java

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

public class CockpitG4M2E_AGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            aircraft().hierMesh().chunkVisible("Turret6B_D0", false);
            aircraft().hierMesh().chunkVisible("Tail1_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        aircraft().hierMesh().chunkVisible("Turret6B_D0", true);
        aircraft().hierMesh().chunkVisible("Tail1_D0", true);
        super.doFocusLeave();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        super.mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        super.mesh.materialReplace("Matt1D0o", mat);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        super.mesh.chunkSetAngles("Turret2A", 0.0F, f, 0.0F);
        super.mesh.chunkSetAngles("Turret2B", 0.0F, f1, 0.0F);
        if(java.lang.Math.abs(f) > 2.0F || java.lang.Math.abs(f1) > 2.0F)
        {
            a2 = (float)java.lang.Math.toDegrees(java.lang.Math.atan2(f, f1));
            a2 *= cvt(f1, 0.0F, 55F, 1.0F, 0.75F);
        }
        if(f < -33F)
            f = -33F;
        if(f > 33F)
            f = 33F;
        if(f1 < -23F)
            f1 = -23F;
        if(f1 > 33F)
            f1 = 33F;
        super.mesh.chunkSetAngles("Turret2C", 0.0F, f, 0.0F);
        super.mesh.chunkSetAngles("Turret2D", 0.0F, f1, 0.0F);
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
        if(f1 > 25F)
            f1 = 25F;
        if(f1 < -45F)
            f1 = -45F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !aiTurret().bIsOperable)
            super.bGunFire = false;
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
        cockPos = 0.5F * cockPos + 0.5F * a2;
        super.mesh.chunkSetAngles("Turret2E", 0.0F, cockPos, 0.0F);
        super.mesh.chunkSetAngles("Rudder1_D0", 0.0F, -30F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getRudder(), 0.0F);
        super.mesh.chunkSetAngles("VatorL_D0", 0.0F, -30F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getElevator(), 0.0F);
        super.mesh.chunkSetAngles("VatorR_D0", 0.0F, -30F * ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.getElevator(), 0.0F);
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(super.emitter == null || !super.emitter.haveBullets() || !aiTurret().bIsOperable)
            super.bGunFire = false;
        else
            super.bGunFire = flag;
        ((com.maddox.il2.fm.FlightModelMain) (super.fm)).CT.WeaponControl[weaponControlNum()] = super.bGunFire;
    }

    public CockpitG4M2E_AGunner()
    {
        super("3DO/Cockpit/G4M2E-AGun/hier.him", "he111");
        bNeedSetUp = true;
        cockPos = 0.0F;
        a2 = 0.0F;
    }

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    public void reflectCockpitState()
    {
    }

    private boolean bNeedSetUp;
    private float cockPos;
    private float a2;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "aiTuretNum", 5);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "weaponControlNum", 15);
        com.maddox.rts.Property.set(com.maddox.rts.CLASS.THIS(), "astatePilotIndx", 5);
    }
}
