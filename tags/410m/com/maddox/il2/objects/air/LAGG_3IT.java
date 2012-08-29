// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LAGG_3IT.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            LAGG_3, PaintSchemeFMPar01, NetAircraft

public class LAGG_3IT extends com.maddox.il2.objects.air.LAGG_3
{

    public LAGG_3IT()
    {
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.LAGG_3IT.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.2F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.LAGG_3IT.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.2F), 0.0F);
        }
        super.update(f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", -75F * f, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1200F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.LAGG_3IT.moveGear(hierMesh(), f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.LAGG_3IT.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "LaGG");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/LaGG-3IT/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/LaGG-3IT.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitLAGG_3SERIES66.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.69445F);
        com.maddox.il2.objects.air.LAGG_3IT.weaponTriggersRegister(class1, new int[] {
            0, 1
        });
        com.maddox.il2.objects.air.LAGG_3IT.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01"
        });
        com.maddox.il2.objects.air.LAGG_3IT.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBs 220", "MGunNS37ki 22"
        });
        com.maddox.il2.objects.air.LAGG_3IT.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
