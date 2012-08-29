// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitFw189_RGunner.java

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

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitFw189_RGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = orient.getYaw();
        float f1 = -orient.getTangage();
        mesh.chunkSetAngles("TurretA", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, f1, 0.0F);
        mesh.chunkSetAngles("Hose", -0.333F * java.lang.Math.abs(f1) - 3F, 0.5F * f, 0.0F);
        mesh.chunkSetAngles("PatronsL", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("PatronsL_add", 0.0F, cvt(f, -25F, 0.0F, -91F, 0.0F), 0.0F);
        mesh.chunkSetAngles("PatronsR", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("PatronsR_add", 0.0F, cvt(f, 0.0F, 25F, 0.0F, 91F), 0.0F);
        if(f1 < -30F - 5F * f)
            mesh.chunkVisible("PatronsL_add", false);
        else
            mesh.chunkVisible("PatronsL_add", true);
        if(f1 < -30F + 5F * f)
            mesh.chunkVisible("PatronsR_add", false);
        else
            mesh.chunkVisible("PatronsR_add", true);
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
                if(f < -35F)
                    f = -35F;
                if(f > 35F)
                    f = 35F;
                if(f1 > 45F)
                    f1 = 45F;
                if(f1 < 0.0F)
                    f1 = 0.0F;
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
            fm.CT.WeaponControl[11] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN05")));
                doHitMasterAircraft(aircraft(), hook1, "_MGUN05");
                if(hook2 == null)
                    hook2 = ((com.maddox.il2.engine.Hook) (new HookNamed(((com.maddox.il2.engine.ActorMesh) (aircraft())), "_MGUN06")));
                doHitMasterAircraft(aircraft(), hook2, "_MGUN06");
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
            fm.CT.WeaponControl[11] = bGunFire;
        }
    }

    public CockpitFw189_RGunner()
    {
        super("3DO/Cockpit/He-111H-2/RGunnerFw189.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
        hook2 = null;
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
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "weaponControlNum", 11);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 1);
    }
}
