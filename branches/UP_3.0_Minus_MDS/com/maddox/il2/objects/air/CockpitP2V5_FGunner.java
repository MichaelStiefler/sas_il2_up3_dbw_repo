// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   CockpitP2V5_FGunner.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner

public class CockpitP2V5_FGunner extends com.maddox.il2.objects.air.CockpitGunner
{

    public void moveGun(com.maddox.il2.engine.Orient orient)
    {
        super.moveGun(orient);
        mesh.chunkSetAngles("Body", 180F, 0.0F, 180F);
        mesh.chunkSetAngles("TurretA", orient.getYaw(), 180F, 180F);
        mesh.chunkSetAngles("TurretB", 180F, -orient.getTangage(), 180F);
    }

    public void clipAnglesGun(com.maddox.il2.engine.Orient orient)
    {
        float f = orient.getYaw();
        float f1 = orient.getTangage();
        float f2 = java.lang.Math.abs(f);
        for(; f < -180F; f += 360F);
        for(; f > 180F; f -= 360F);
        for(; prevA0 < -180F; prevA0 += 360F);
        for(; prevA0 > 180F; prevA0 -= 360F);
        if(!isRealMode())
        {
            prevA0 = f;
        } else
        {
            if(bNeedSetUp)
            {
                prevTime = com.maddox.rts.Time.current() - 1L;
                bNeedSetUp = false;
            }
            if(f < -120F && prevA0 > 120F)
                f += 360F;
            else
            if(f > 120F && prevA0 < -120F)
                prevA0 += 360F;
            float f3 = f - prevA0;
            float f4 = 0.001F * (float)(com.maddox.rts.Time.current() - prevTime);
            float f5 = java.lang.Math.abs(f3 / f4);
            if(f5 > 120F)
                if(f > prevA0)
                    f = prevA0 + 120F * f4;
                else
                if(f < prevA0)
                    f = prevA0 - 120F * f4;
            prevTime = com.maddox.rts.Time.current();
            if(f1 > 5F)
                f1 = 5F;
            if(f1 < -85F)
                f1 = -85F;
            orient.setYPR(f, f1, 0.0F);
            orient.wrap();
            prevA0 = f;
        }
    }

    public void reflectCockpitState()
    {
        if((fm.AS.astateCockpitState & 4) != 0)
            mesh.chunkVisible("Z_Holes1_D1", true);
        if((fm.AS.astateCockpitState & 0x10) != 0)
            mesh.chunkVisible("Z_Holes2_D1", true);
    }

    public CockpitP2V5_FGunner()
    {
        super("3DO/Cockpit/P2V5-FGun/FGunnerP2V5.him", "he111_gunner");
        bNeedSetUp = true;
        prevTime = -1L;
        prevA0 = 0.0F;
        hook1 = null;
        hook2 = null;
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
    private long prevTime;
    private float prevA0;
    private com.maddox.il2.engine.Hook hook1;
    private com.maddox.il2.engine.Hook hook2;
    static java.lang.Class class$com$maddox$il2$objects$air$CockpitP2V5_FGunner;

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "aiTuretNum", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (com.maddox.rts.CLASS.THIS())), "astatePilotIndx", 1);
    }
}
