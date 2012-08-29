// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   P_40SUKAISVOLOCHC.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_40SUKAISVOLOCH, PaintSchemeFMPar02, PaintSchemeFCSPar02, NetAircraft

public class P_40SUKAISVOLOCHC extends com.maddox.il2.objects.air.P_40SUKAISVOLOCH
{

    public P_40SUKAISVOLOCHC()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.04F, 0.5F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.02F, 0.09F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.02F, 0.09F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.01F, 0.79F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL21_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.01F, 0.79F, 0.0F, 94F), 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.01F, 0.39F, 0.0F, -53F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.01F, 0.11F, 0.0F, -100F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.01F, 0.79F, 0.0F, 100F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.21F, 0.99F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR21_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.21F, 0.99F, 0.0F, -94F), 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.21F, 0.59F, 0.0F, -53F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.21F, 0.31F, 0.0F, -100F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.cvt(f, 0.21F, 0.99F, 0.0F, 100F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "P-40");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-40C(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/P-40C(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFCSPar02())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1941F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-40C.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitP_40C.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.0728F);
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 3, 9
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalBomb01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", null, null, null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponsRegister(class1, "1x75dt", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", null, "FuelTankGun_Tank75gal"
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponsRegister(class1, "1x100", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGun100Lbs 1", null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunBrowning50si 250", "MGunBrowning50si 250", "MGunBrowning303k 300", "MGunBrowning303k 240", "MGunBrowning303k 300", "MGunBrowning303k 240", "PylonP39PLN1", "BombGun250lbs 1", null
        });
        com.maddox.il2.objects.air.P_40SUKAISVOLOCHC.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
