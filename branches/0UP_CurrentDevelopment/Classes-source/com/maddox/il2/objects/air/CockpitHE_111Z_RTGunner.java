// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHE_111Z_RTGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitHE_111Z_RTGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void reflectWorldToInstruments(float f)
    {
        if(bNeedSetUp)
        {
            reflectPlaneMats();
            bNeedSetUp = false;
        }
    }

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("TurretA", -orient.getYaw(), 0.0F, 0.0F);
        mesh.chunkSetAngles("TurretB", 0.0F, 0.0F, -orient.getTangage());
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
                if(f < -42F)
                    f = -42F;
                if(f > 42F)
                    f = 42F;
                if(f1 > 60F)
                    f1 = 60F;
                if(f1 < -3F)
                    f1 = -3F;
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
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
            if(bGunFire)
            {
                if(hook1 == null)
                    hook1 = new HookNamed(aircraft(), "_MGUN02");
                doHitMasterAircraft(aircraft(), hook1, "_MGUN02");
                if(iCocking > 0)
                    iCocking = 0;
                else
                    iCocking = 1;
            } else
            {
                iCocking = 0;
            }
            iNewVisDrums = (int)((float)emitter.countBullets() / 333F);
            if(iNewVisDrums < iOldVisDrums)
            {
                iOldVisDrums = iNewVisDrums;
                mesh.chunkVisible("Drum1", iNewVisDrums > 2);
                mesh.chunkVisible("Drum2", iNewVisDrums > 1);
                mesh.chunkVisible("Drum3", iNewVisDrums > 0);
                sfxClick(13);
            }
            mesh.chunkSetAngles("CockingLever", -0.75F * (float)iCocking, 0.0F, 0.0F);
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
            fm.CT.WeaponControl[weaponControlNum()] = bGunFire;
        }
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D1o"));
        mesh.materialReplace("Gloss1D1o", mat);
        mat = hiermesh.material(hiermesh.materialFind("Gloss1D2o"));
        mesh.materialReplace("Gloss1D2o", mat);
    }

    public CockpitHE_111Z_RTGunner()
    {
        super("3DO/Cockpit/He-111H-2-TGun/RTGunnerHE111Z.him", "he111_gunner");
        bNeedSetUp = true;
        hook1 = null;
        iCocking = 0;
        iOldVisDrums = 3;
        iNewVisDrums = 3;
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
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private boolean bNeedSetUp;
    private com.maddox.il2.engine.Hook hook1;
    private int iCocking;
    private int iOldVisDrums;
    private int iNewVisDrums;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111Z_RTGunner.class, "aiTuretNum", 1);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111Z_RTGunner.class, "weaponControlNum", 11);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHE_111Z_RTGunner.class, "astatePilotIndx", 2);
    }
}
