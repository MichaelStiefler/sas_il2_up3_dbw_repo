// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   YAK_9UT_NS45.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK, PaintSchemeFMPar04, TypeBNZFighter, NetAircraft

public class YAK_9UT_NS45 extends com.maddox.il2.objects.air.YAK
    implements com.maddox.il2.objects.air.TypeBNZFighter
{

    public YAK_9UT_NS45()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 80F * f, 0.0F);
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
        com.maddox.il2.objects.air.YAK_9UT_NS45.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("OilRad_D0", 0.0F, FM.EI.engines[0].getControlRadiator() * 15F, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_9UT_NS45.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Yak-9UT(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1945F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1952.8F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Yak_9UT_NS45.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitYAK_9U.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.6432F);
        com.maddox.il2.objects.air.YAK_9UT_NS45.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        com.maddox.il2.objects.air.YAK_9UT_NS45.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        com.maddox.il2.objects.air.YAK_9UT_NS45.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKsi 120", "MGunShVAKsi 120", "MGunNS45ki 29"
        });
        com.maddox.il2.objects.air.YAK_9UT_NS45.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}