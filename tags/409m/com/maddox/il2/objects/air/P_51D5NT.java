// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_51D5NT.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_51, PaintSchemeFMPar05, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class P_51D5NT extends com.maddox.il2.objects.air.P_51
{

    public P_51D5NT()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_51D5NT.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-51D-5NT(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-51D-5NT(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "noseart", 1);
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-51D-20.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_51D20N9.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1188F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 3, 3, 
            9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun250lbs 1", "BombGun250lbs 1", 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun500lbs 1", "BombGun500lbs 1", 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xTank", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", null, null, 
            "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
