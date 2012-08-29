// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitTB_3_RGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
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
//            CockpitGunner, TB_3_4M_34R_SPB, TB_3_4M_34R, Aircraft

public class CockpitTB_3_RGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
        if(aircraft() instanceof com.maddox.il2.objects.air.TB_3_4M_34R_SPB)
        {
            com.maddox.il2.objects.air.TB_3_4M_34R_SPB _tmp = (com.maddox.il2.objects.air.TB_3_4M_34R_SPB)aircraft();
            if(com.maddox.il2.objects.air.TB_3_4M_34R_SPB.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.TB_3_4M_34R_SPB _tmp1 = (com.maddox.il2.objects.air.TB_3_4M_34R_SPB)aircraft();
                com.maddox.il2.objects.air.TB_3_4M_34R_SPB.bChangedPit = false;
            }
        }
        if(aircraft() instanceof com.maddox.il2.objects.air.TB_3_4M_34R)
        {
            com.maddox.il2.objects.air.TB_3_4M_34R _tmp2 = (com.maddox.il2.objects.air.TB_3_4M_34R)aircraft();
            if(com.maddox.il2.objects.air.TB_3_4M_34R.bChangedPit)
            {
                reflectPlaneToModel();
                com.maddox.il2.objects.air.TB_3_4M_34R _tmp3 = (com.maddox.il2.objects.air.TB_3_4M_34R)aircraft();
                com.maddox.il2.objects.air.TB_3_4M_34R.bChangedPit = false;
            }
        }
        aircraft().hierMesh().setCurChunk("VatorL_D0");
        aircraft().hierMesh().getChunkLocObj(l);
        mesh.chunkSetAngles("VatorL_D0", 0.0F, l.getOrient().getTangage(), 0.0F);
        aircraft().hierMesh().setCurChunk("VatorR_D0");
        aircraft().hierMesh().getChunkLocObj(l);
        mesh.chunkSetAngles("VatorR_D0", 0.0F, l.getOrient().getTangage(), 0.0F);
        aircraft().hierMesh().setCurChunk("Rudder1_D0");
        aircraft().hierMesh().getChunkLocObj(l);
        mesh.chunkSetAngles("Rudder1_D0", 0.0F, l.getOrient().getAzimut(), 0.0F);
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss2D2o"));
        mesh.materialReplace("Gloss2D2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt1D0o"));
        mesh.materialReplace("Matt1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Matt2D0o"));
        mesh.materialReplace("Matt2D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Overlay8"));
        mesh.materialReplace("Overlay8", mat);
        mat = hiermesh.material(hiermesh.materialFind("OverlayD2o"));
        mesh.materialReplace("OverlayD2o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Pilot2"));
        mesh.materialReplace("Pilot2", mat);
    }

    protected void reflectPlaneToModel()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        mesh.chunkVisible("StabL_D0", hiermesh.isChunkVisible("StabL_D0"));
        mesh.chunkVisible("StabL_D1", hiermesh.isChunkVisible("StabL_D1"));
        mesh.chunkVisible("StabL_D2", hiermesh.isChunkVisible("StabL_D2"));
        mesh.chunkVisible("StabL_D3", hiermesh.isChunkVisible("StabL_D3"));
        mesh.chunkVisible("StabL_CAP", hiermesh.isChunkVisible("StabL_CAP"));
        mesh.chunkVisible("StabR_D0", hiermesh.isChunkVisible("StabR_D0"));
        mesh.chunkVisible("StabR_D1", hiermesh.isChunkVisible("StabR_D1"));
        mesh.chunkVisible("StabR_D2", hiermesh.isChunkVisible("StabR_D2"));
        mesh.chunkVisible("StabR_D3", hiermesh.isChunkVisible("StabR_D3"));
        mesh.chunkVisible("StabR_CAP", hiermesh.isChunkVisible("StabR_CAP"));
        mesh.chunkVisible("VatorL_D0", hiermesh.isChunkVisible("VatorL_D0"));
        mesh.chunkVisible("VatorL_D1", hiermesh.isChunkVisible("VatorL_D1"));
        mesh.chunkVisible("VatorR_D0", hiermesh.isChunkVisible("VatorR_D0"));
        mesh.chunkVisible("VatorR_D1", hiermesh.isChunkVisible("VatorR_D1"));
        mesh.chunkVisible("Rudder1_D0", hiermesh.isChunkVisible("Rudder1_D0"));
        mesh.chunkVisible("Rudder1_D1", hiermesh.isChunkVisible("Rudder1_D1"));
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = -orient.getTangage();
        mesh.chunkSetAngles("Turret3A", f, 0.0F, 0.0F);
        mesh.chunkSetAngles("Turret3B", 0.0F, -f1, 0.0F);
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
        if(f < -90F)
            f = -90F;
        if(f > 90F)
            f = 90F;
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

    public CockpitTB_3_RGunner()
    {
        super("3DO/Cockpit/TB-3-RGun/hier.him", "i16");
        bNeedSetUp = true;
        l = new Loc();
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
    com.maddox.il2.engine.Loc l;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_RGunner.class, "aiTuretNum", 2);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_RGunner.class, "weaponControlNum", 12);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitTB_3_RGunner.class, "astatePilotIndx", 6);
    }
}
