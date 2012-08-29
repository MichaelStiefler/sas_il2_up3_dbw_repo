// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111H2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_111, PaintSchemeBMPar02, NetAircraft

public class HE_111H2 extends com.maddox.il2.objects.air.HE_111
{

    public HE_111H2()
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay5_D0", 0.0F, 74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay6_D0", 0.0F, -94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay7_D0", 0.0F, 74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay8_D0", 0.0F, -94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay9_D0", 0.0F, -74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay10_D0", 0.0F, 94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay11_D0", 0.0F, -74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay12_D0", 0.0F, 94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay13_D0", 0.0F, -74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay14_D0", 0.0F, 94F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay15_D0", 0.0F, -74F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay16_D0", 0.0F, 94F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111H2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1939.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111H-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111H2.class, com.maddox.il2.objects.air.CockpitHE_111H2_Bombardier.class, com.maddox.il2.objects.air.CockpitHE_111H2_NGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_TGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_BGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_RGunner.class
        });
        com.maddox.il2.objects.air.HE_111H2.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 
            3, 3, 3
        });
        com.maddox.il2.objects.air.HE_111H2.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", 
            "_BombSpawn06", "_BombSpawn07", "_BombSpawn08"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "30xSC50", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", 
            "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "20xSC70", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", 
            "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "2SC250A", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC50 2", 
            "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "2SC250B", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC70 2", 
            "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "4xSC250", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "4SC250A", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 2", 
            "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "4SC250B", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC70 2", 
            "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "8xSC250", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.HE_111H2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
