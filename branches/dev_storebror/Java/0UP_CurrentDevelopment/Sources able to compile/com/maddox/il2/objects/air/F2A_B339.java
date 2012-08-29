package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class F2A_B339 extends F2A
{

    public F2A_B339()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Buffalo");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BuffaloMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryBritain);
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F2A-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitB339.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.032F);
        F2A_B339.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        F2A_B339.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02"
        });
        F2A_B339.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", null, null
        });
        F2A_B339.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", "BombGun100Lbs 1", "BombGun100Lbs 1"
        });
        F2A_B339.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
