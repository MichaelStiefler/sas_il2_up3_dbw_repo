// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_9M.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK_9TX, PaintSchemeFMPar04, PaintSchemeFCSPar05, NetAircraft

public class YAK_9M extends com.maddox.il2.objects.air.YAK_9TX
{

    public YAK_9M()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_9M.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-9M(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "meshName_fr", "3DO/Plane/Yak-9M(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fr", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1952.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-9M.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYAK_9T.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.661F);
        com.maddox.il2.objects.air.YAK_9M.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 9
        });
        com.maddox.il2.objects.air.YAK_9M.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01", "_CANNON02", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.YAK_9M.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 200", "MGunShVAKki 120", null, null
        });
        com.maddox.il2.objects.air.YAK_9M.weaponsRegister(class1, "ns37", new java.lang.String[] {
            "MGunUBsi 220", null, "MGunNS37ki 30", "PylonMG15120Internal"
        });
        com.maddox.il2.objects.air.YAK_9M.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
