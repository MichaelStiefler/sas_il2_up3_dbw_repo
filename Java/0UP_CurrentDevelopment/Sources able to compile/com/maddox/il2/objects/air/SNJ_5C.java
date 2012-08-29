package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class SNJ_5C extends SBD
{

    public SNJ_5C()
    {
    }

    static 
    {
        java.lang.Class class1 = SNJ_5C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "SNJ");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SNJ_5C(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/SNJ_5C(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1935F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1975.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SNJ.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSNJ.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1058F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 10, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}
