package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class GLADIATOR1 extends GLADIATOR
{

    public GLADIATOR1()
    {
    }

    static 
    {
        java.lang.Class class1 = GLADIATOR1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Gladiator");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/GladiatorMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/GladiatorMkI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitGLADIATOR1.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8472F);
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryFinland);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600", "MGunBrowning303k 400", "MGunBrowning303k 400"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
