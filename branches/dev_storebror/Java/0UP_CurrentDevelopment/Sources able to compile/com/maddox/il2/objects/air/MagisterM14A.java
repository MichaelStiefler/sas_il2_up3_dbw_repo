package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MagisterM14A extends Magister
{

    public MagisterM14A()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Milesmag");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Magister(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MagM14A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitMagisterM14A.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74185F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 0"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
