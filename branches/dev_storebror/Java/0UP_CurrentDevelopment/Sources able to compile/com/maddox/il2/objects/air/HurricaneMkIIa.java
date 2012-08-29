package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIIa extends Hurricane
    implements TypeFighter
{

    public HurricaneMkIIa()
    {
    }

    static 
    {
        java.lang.Class class1 = HurricaneMkIIa.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "HurriMkIIa");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIIa(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/HurricaneMkIIa(Multi1)/hierRAF.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIIa.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 334", "MGunBrowning303k 356", "MGunBrowning303k 334", "MGunBrowning303k 350", "MGunBrowning303k 334", "MGunBrowning303k 334"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
