package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class HurricaneMkIId extends Hurricane
    implements TypeStormovik, TypeStormovikArmored
{

    public HurricaneMkIId()
    {
    }

    static 
    {
        java.lang.Class class1 = HurricaneMkIId.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Hurri");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/HurricaneMkIId(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/HurricaneMkIId.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHURRII.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.66895F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", "MGunVickers40mmAPk 15", "MGunVickers40mmAPk 15"
        });
        Aircraft.weaponsRegister(class1, "2xVickerS40mmHE", new java.lang.String[] {
            "MGunBrowning303Trak 334", "MGunBrowning303Trak 334", "MGunVickers40mmk 15", "MGunVickers40mmk 15"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
