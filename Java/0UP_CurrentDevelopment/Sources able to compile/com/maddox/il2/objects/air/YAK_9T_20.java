package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class YAK_9T_20 extends YAK_9TX
{

    public YAK_9T_20()
    {
    }

    static 
    {
        java.lang.Class class1 = YAK_9T_20.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-9T(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_fr", "3DO/Plane/Yak-9T(fr)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fr", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak_9T_20.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitYAK_9T.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.661F);
        YAK_9T_20.weaponTriggersRegister(class1, new int[] {
            0, 1
        });
        YAK_9T_20.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01"
        });
        YAK_9T_20.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 220", "MGunNS37ki 30"
        });
        YAK_9T_20.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
