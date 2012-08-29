package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BLENHEIM4F extends BLENHEIM
    implements TypeStormovik, TypeFighter
{

    public BLENHEIM4F()
    {
    }

    static 
    {
        java.lang.Class class1 = BLENHEIM4F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "BlenheimIF");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BlenheimMkIVF/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Blenheim_MkIV.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBLENHEIM4F.class, CockpitBLENHEIM4F_TGunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 10, 10, 1, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunVikkersKt 2600", "MGunVikkersKt 2600", null, null
        });
        Aircraft.weaponsRegister(class1, "20mm", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunVikkersKt 2600", "MGunVikkersKt 2600", "MGunHispanoMkIkpzl 180", "PylonBlenheim20"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
