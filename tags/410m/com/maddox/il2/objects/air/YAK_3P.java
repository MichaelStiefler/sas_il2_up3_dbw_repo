// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_3P.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar05, PaintSchemeFCSPar05, NetAircraft

public class YAK_3P extends com.maddox.il2.objects.air.YAK
{

    public YAK_3P()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        f1 = java.lang.Math.max(-f * 1500F, -60F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 82.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -85F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.YAK_3P.moveGear(hierMesh(), f);
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("OilRad_D0", 0.0F, FM.EI.engines[0].getControlRadiator() * 25F, 0.0F);
        hierMesh().chunkSetAngles("Water_luk", 0.0F, FM.EI.engines[0].getControlRadiator() * 12F, 0.0F);
        super.update(f);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_3P.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-3(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_fr", "3DO/Plane/Yak-3(fr)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fr", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_3.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.6576F);
        com.maddox.il2.objects.air.YAK_3P.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        com.maddox.il2.objects.air.YAK_3P.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        com.maddox.il2.objects.air.YAK_3P.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunB20si 130", "MGunB20si 130", "MGunB20ki 120"
        });
        com.maddox.il2.objects.air.YAK_3P.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}