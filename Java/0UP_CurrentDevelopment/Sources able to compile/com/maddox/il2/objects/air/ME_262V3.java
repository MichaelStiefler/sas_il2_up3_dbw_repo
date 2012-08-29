package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class ME_262V3 extends ME_262
{

    public ME_262V3()
    {
    }

    static 
    {
        java.lang.Class class1 = ME_262V3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-262V-3/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1943.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-262A-1a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitME_262.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74615F);
        Aircraft.weaponTriggersRegister(class1, new int[2]);
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
