package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class RE_2000 extends RE_2000xyz
{

    public RE_2000()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "RE.2000");
        com.maddox.rts.Property.set(class1, "meshName_hu", "3DO/Plane/RE-2000(hu)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/RE-2000(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/RE-2000(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/RE-2000.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitRE_2000.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9119F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_BOMB100KG01", "_BOMB100KG02", "_BOMBCASSETTE01", "_BOMBCASSETTE02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x100_Kg_Bombs", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null
        });
        Aircraft.weaponsRegister(class1, "4xCassette", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, "BombGunSpezzoniera 44", "BombGunSpezzoniera 44"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
