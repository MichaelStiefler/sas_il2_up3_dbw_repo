// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111P2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_111, PaintSchemeBMPar02, NetAircraft, Aircraft

public class HE_111P2 extends com.maddox.il2.objects.air.HE_111
{

    public HE_111P2()
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
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111P2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-2/hier_He111P2.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1939.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111P2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111H2.class, com.maddox.il2.objects.air.CockpitHE_111H2_Bombardier.class, com.maddox.il2.objects.air.CockpitHE_111H2_NGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_TGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.742F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 3, 3, 3, 3, 3, 
            3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", 
            "_BombSpawn06", "_BombSpawn07", "_BombSpawn08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16xSC50", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2", 
            "BombGunSC50 2", "BombGunSC50 2", "BombGunSC50 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "32xSC50", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4", 
            "BombGunSC50 4", "BombGunSC50 4", "BombGunSC50 4"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "16xSC70", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2", 
            "BombGunSC70 2", "BombGunSC70 2", "BombGunSC70 2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24xSC70", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3", 
            "BombGunSC70 3", "BombGunSC70 3", "BombGunSC70 3"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4XSC250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "8XSC250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
