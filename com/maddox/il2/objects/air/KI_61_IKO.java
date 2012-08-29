// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_61_IKO.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_61, PaintSchemeFMPar03, PaintSchemeBCSPar01, Aircraft, 
//            NetAircraft

public class KI_61_IKO extends com.maddox.il2.objects.air.KI_61
{

    public KI_61_IKO()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.9F, 0.0F, 72F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.21F, 0.0F, 57F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.21F, 0.0F, -57F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.82F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.82F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.16F, 0.0F, 86F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.34F, 0.91F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.34F, 0.91F, 0.0F, 90F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.34F, 0.4F, 0.0F, -86F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.KI_61_IKO.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC5_D0", 0.0F, -f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_61_IKO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-61");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-61-I(Ko)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-61-I(Ko)(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-61-IKo.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitKI_61.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81055F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", "BombGun250kgJ 1", "BombGun250kgJ 1", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x150dt", new java.lang.String[] {
            "MGunHo103si 250", "MGunHo103si 250", "MGunBrowning303k_jap 500", "MGunBrowning303k_jap 500", null, null, "FuelTankGun_TankKi61Underwing", "FuelTankGun_TankKi61Underwing"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
