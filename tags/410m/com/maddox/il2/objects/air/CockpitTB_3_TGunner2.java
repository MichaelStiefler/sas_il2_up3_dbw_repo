// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitTB_3_TGunner2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitTB_3_TGunner2 extends com.maddox.il2.objects.air.CockpitGunner
{

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        com.maddox.il2.engine.Loc loc = new Loc();
        aircraft().hierMesh().setCurChunk("Turret2B_D0");
        aircraft().hierMesh().getChunkLocObj(loc);
        mesh.chunkSetAngles("Turret2A", -loc.getOrient().getAzimut(), 0.0F, 0.0F);
        mesh.chunkSetAngles("Turret2B", 0.0F, -loc.getOrient().getTangage(), 0.0F);
        mesh.chunkSetAngles("Turret2C", 0.0F, loc.getOrient().getAzimut(), 0.0F);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot2"));
        mesh.materialReplace("Pilot2", mat);
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = -orient.getTangage();
        mesh.chunkSetAngles("Turret3A", f, 0.0F, 0.0F);
        mesh.chunkSetAngles("Turret3B", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Turret3C", 0.0F, f, 0.0F);
        float f2 = 0.01905F * (float)java.lang.Math.sin(java.lang.Math.toRadians(f));
        float f3 = 0.01905F * (float)java.lang.Math.cos(java.lang.Math.toRadians(f));
        f = (float)java.lang.Math.toDegrees(java.lang.Math.atan(f2 / (f3 + 0.3565F)));
        mesh.chunkSetAngles("Turret3D", 0.0F, -f, 0.0F);
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
        if(f1 < -47F)
            f1 = -47F;
        if(f1 > 47F)
            f1 = 47F;
        if(f < -68F)
        {
            if(f1 < -32F)
                f1 = -32F;
        } else
        if(f < -22F)
        {
            if(f1 < 0.5347826F * f + 4.365217F)
                f1 = 0.5347826F * f + 4.365217F;
        } else
        if(f < 27F)
        {
            if(f1 < -0.3387755F * f - 14.85306F)
                f1 = -0.3387755F * f - 14.85306F;
        } else
        if(f < 40F)
        {
            if(f1 < -1.769231F * f + 23.76923F)
                f1 = -1.769231F * f + 23.76923F;
        } else
        if(f < 137F)
        {
            if(f1 < -47F)
                f1 = -47F;
        } else
        if(f < 152F)
        {
            if(f1 < 1.0F * f - 184F)
                f1 = 1.0F * f - 184F;
        } else
        if(f1 < -32F)
            f1 = -32F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected boolean doFocusEnter()
    {
        if(super.doFocusEnter())
        {
            ((com.maddox.il2.engine.ActorHMesh)(com.maddox.il2.engine.ActorHMesh)fm.actor).hierMesh().chunkVisible("Turret2C_D0", false);
            return true;
        } else
        {
            return false;
        }
    }

    protected void doFocusLeave()
    {
        ((com.maddox.il2.engine.ActorHMesh)(com.maddox.il2.engine.ActorHMesh)fm.actor).hierMesh().chunkVisible("Turret2C_D0", ((com.maddox.il2.engine.ActorHMesh)(com.maddox.il2.engine.ActorHMesh)fm.actor).hierMesh().isChunkVisible("Turret2B_D0"));
        super.doFocusLeave();
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
                hook1 = new HookNamed(aircraft(), "_MGUN05");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN05");
            if(hook2 == null)
                hook2 = new HookNamed(aircraft(), "_MGUN06");
            doHitMasterAircraft(aircraft(), hook2, "_MGUN06");
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

    public CockpitTB_3_TGunner2()
    {
        super("3DO/Cockpit/TB-3-TGun2/hier.him", "i16");
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_TGunner2.class, "aiTuretNum", 2);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_TGunner2.class, "weaponControlNum", 12);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_TGunner2.class, "astatePilotIndx", 6);
    }
}
