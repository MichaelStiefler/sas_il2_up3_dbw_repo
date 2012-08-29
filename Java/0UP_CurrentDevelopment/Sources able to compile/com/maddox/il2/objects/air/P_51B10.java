package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_51B10 extends P_51
{

    public P_51B10()
    {
    }

    static 
    {
        java.lang.Class class1 = P_51B10.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/P-51B10(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-51B10(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-51B10(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-51B-10NA.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitP_51B.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.03F);
        P_51B10.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 3, 3, 9, 9
        });
        P_51B10.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        P_51B10.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", null, null, null, null, null, null
        });
        P_51B10.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun250lbs 1", "BombGun250lbs 1", null, null
        });
        P_51B10.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun500lbs 1", "BombGun500lbs 1", null, null
        });
        P_51B10.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null
        });
        P_51B10.weaponsRegister(class1, "2xTank", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 350", "PylonP51PLN2", "PylonP51PLN2", null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2"
        });
        P_51B10.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
