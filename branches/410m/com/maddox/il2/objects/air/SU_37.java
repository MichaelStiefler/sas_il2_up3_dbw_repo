// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SU_37.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MIG_3, PaintSchemeFMPar00, NetAircraft, PaintScheme

public class SU_37 extends com.maddox.il2.objects.air.MIG_3
{

    public SU_37()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SU_37.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MIG");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/MIG-3U(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Su-37.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitMIG_3.class);
        com.maddox.il2.objects.air.SU_37.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 3, 3, 9, 9, 
            9, 9, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.SU_37.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", 
            "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.SU_37.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKs 250", "MGunShVAKs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.SU_37.weaponsRegister(class1, "2xBK", new java.lang.String[] {
            "MGunShVAKs 250", "MGunShVAKs 250", "MGunUBk 250", "MGunUBk 250", null, null, null, null, "PylonMiG_3_BK", "PylonMiG_3_BK", 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.SU_37.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
