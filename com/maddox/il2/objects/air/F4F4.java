// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F4F4.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F4F, PaintSchemeFMPar03, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class F4F4 extends com.maddox.il2.objects.air.F4F
{

    public F4F4()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.F4F4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F4F");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/F4F-4(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/F4F-4(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F4F-4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitF4F4.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.28265F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            9, 9, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", null, null, null, null, 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x58dt", new java.lang.String[] {
            "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "PylonF4FPLN1", "PylonF4FPLN1", "FuelTankGun_TankF4F", "FuelTankGun_TankF4F", 
            null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", null, null, null, null, 
            "PylonF4FPLN2", "PylonF4FPLN2", "BombGunFAB50 1", "BombGunFAB50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x1002x58dt", new java.lang.String[] {
            "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "MGunBrowning50kWF 240", "PylonF4FPLN1", "PylonF4FPLN1", "FuelTankGun_TankF4F", "FuelTankGun_TankF4F", 
            "PylonF4FPLN2", "PylonF4FPLN2", "BombGunFAB50 1", "BombGunFAB50 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
