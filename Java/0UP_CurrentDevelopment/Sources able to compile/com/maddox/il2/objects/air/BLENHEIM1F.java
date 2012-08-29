package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class BLENHEIM1F extends BLENHEIM
    implements TypeStormovik, TypeFighter
{

    public BLENHEIM1F()
    {
    }

    static 
    {
        java.lang.Class class1 = BLENHEIM1F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "BlenheimIF");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BlenheimMkIF/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1937F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Blenheim_MkIF.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitBLENHEIM1F.class, CockpitBLENHEIM_TGunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 10, 0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunVikkersKt 2600", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
