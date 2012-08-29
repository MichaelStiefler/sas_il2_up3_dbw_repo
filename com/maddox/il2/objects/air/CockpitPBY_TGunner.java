// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitPBY_TGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, Aircraft

public class CockpitPBY_TGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        float f = -orient.getYaw();
        float f1 = orient.getTangage();
        mesh.chunkSetAngles("Turret1A", 0.0F, f, 0.0F);
        mesh.chunkSetAngles("Turret1B", 0.0F, f1, 0.0F);
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
        if(f < -91F)
            f = -91F;
        if(f > 90F)
            f = 90F;
        if(f1 > 55F)
            f1 = 55F;
        if(f1 < 0.0F)
            f1 = 0.0F;
        orient.setYPR(f, f1, 0.0F);
        orient.wrap();
    }

    protected void reflectPlaneMats()
    {
        com.maddox.il2.engine.HierMesh hiermesh = aircraft().hierMesh();
        com.maddox.il2.engine.Mat mat = hiermesh.material(hiermesh.materialFind("Gloss1D0o"));
        mesh.materialReplace("Gloss1D0o", mat);
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

    public CockpitPBY_TGunner()
    {
        super("3DO/Cockpit/PBN1-NGun/hier.him", "he111_gunner");
        iCocking = 0;
    }

    private int iCocking;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "weaponControlNum", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 2);
    }
}
