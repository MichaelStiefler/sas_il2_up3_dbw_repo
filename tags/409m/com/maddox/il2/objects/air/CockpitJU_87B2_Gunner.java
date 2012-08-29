// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitJU_87B2_Gunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Cockpit, Aircraft, JU_87

public class CockpitJU_87B2_Gunner extends com.maddox.il2.objects.air.CockpitGunner
{
    class Interpolater extends com.maddox.il2.engine.InterpolateRef
    {

        public boolean tick()
        {
            fm = com.maddox.il2.ai.World.getPlayerFM();
            if(fm == null)
                return true;
            if(bNeedSetUp)
            {
                reflectPlaneMats();
                bNeedSetUp = false;
            }
            com.maddox.il2.objects.air.JU_87 _tmp = (com.maddox.il2.objects.air.JU_87)aircraft();
            if(com.maddox.il2.objects.air.JU_87.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.JU_87 _tmp1 = (com.maddox.il2.objects.air.JU_87)aircraft();
                com.maddox.il2.objects.air.JU_87.bChangedPit = false;
            }
            return true;
        }

        Interpolater()
        {
        }
    }


    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", 0.0F, -orient.getYaw(), 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, orient.getTangage(), 0.0F);
        resetYPRmodifier();
        if(orient.getTangage() > 20F)
        {
            com.maddox.il2.objects.air.Cockpit.xyz[0] = ((orient.getTangage() - 20F) * 0.75F * 6.4F) / 25F / 20F;
            com.maddox.il2.objects.air.Cockpit.xyz[2] = ((orient.getTangage() - 20F) * 0.17F * 6.4F) / 25F / 20F;
        }
        mesh.chunkSetLocate("IBone", com.maddox.il2.objects.air.Cockpit.xyz, com.maddox.il2.objects.air.Cockpit.ypr);
        mesh.chunkSetAngles("CasingsTube", 0.0F, -0.5F * orient.getTangage() + 7.5F, 0.33F * orient.getYaw());
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
        if(f < -25F)
            f = -25F;
        if(f > 25F)
            f = 25F;
        if(f1 > 45F)
            f1 = 45F;
        if(f1 < -10F)
            f1 = -10F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void interpTick()
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        fm.CT.WeaponControl[10] = bGunFire;
        if(bGunFire)
        {
            if(hook1 == null)
                hook1 = new HookNamed(aircraft(), "_MGUN03");
            doHitMasterAircraft(aircraft(), hook1, "_MGUN03");
            if(iCocking > 0)
                iCocking = 0;
            else
                iCocking = 1;
        } else
        {
            iCocking = 0;
        }
        iNewVisDrums = (int)((float)emitter.countBullets() / 75F);
        if(iNewVisDrums < iOldVisDrums)
        {
            iOldVisDrums = iNewVisDrums;
            mesh.chunkVisible("Drum1", iNewVisDrums > 3);
            mesh.chunkVisible("Drum2", iNewVisDrums > 2);
            mesh.chunkVisible("Drum3", iNewVisDrums > 1);
            mesh.chunkVisible("Drum4", iNewVisDrums > 0);
            sfxClick(13);
        }
        mesh.chunkSetAngles("CockingLever", -0.75F * (float)iCocking, 0.0F, 0.0F);
    }

    public void doGunFire(boolean flag)
    {
        if(!isRealMode())
            return;
        if(emitter == null || !emitter.haveBullets() || !aiTurret().bIsOperable)
            bGunFire = false;
        else
            bGunFire = flag;
        fm.CT.WeaponControl[10] = bGunFire;
    }

    public CockpitJU_87B2_Gunner()
    {
        super("3DO/Cockpit/Ju-87B-2-Gun/hier.him", "bf109");
        bNeedSetUp = true;
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 99;
        iNewVisDrums = 99;
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D1o"));
        mesh.materialReplace("Matt1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D2o"));
        mesh.materialReplace("Matt1D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay1"));
        mesh.materialReplace("Overlay1", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay2"));
        mesh.materialReplace("Overlay2", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay3"));
        mesh.materialReplace("Overlay3", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay4"));
        mesh.materialReplace("Overlay4", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay7"));
        mesh.materialReplace("Overlay7", mat);
        mat = hiermesh.material(hiermesh.materialFind("OverlayD1o"));
        mesh.materialReplace("OverlayD1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("OverlayD2o"));
        mesh.materialReplace("OverlayD2o", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("Tail1_D0", hiermesh.isChunkVisible("Tail1_D0"));
        mesh.chunkVisible("Tail1_D1", hiermesh.isChunkVisible("Tail1_D1"));
        mesh.chunkVisible("Tail1_D2", hiermesh.isChunkVisible("Tail1_D2"));
        mesh.chunkVisible("Tail1_D3", hiermesh.isChunkVisible("Tail1_D3"));
    }

    public void reflectWorldToInstruments(float f)
    {
        if(fm == null)
            return;
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;


}
