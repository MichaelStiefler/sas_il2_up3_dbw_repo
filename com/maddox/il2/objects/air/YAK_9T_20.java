// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   YAK_9T_20.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK_9TX, PaintSchemeFMPar03, PaintSchemeFCSPar05, NetAircraft

public class YAK_9T_20 extends com.maddox.il2.objects.air.YAK_9TX
{

    public YAK_9T_20()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_9T_20.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Yak-9T(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar03())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_fr", "3DO/Plane/Yak-9T(fr)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_fr", ((java.lang.Object) (new PaintSchemeFCSPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Yak_9T_20.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitYAK_9T.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.661F);
        com.maddox.il2.objects.air.YAK_9T_20.weaponTriggersRegister(class1, new int[] {
            0, 1
        });
        com.maddox.il2.objects.air.YAK_9T_20.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01"
        });
        com.maddox.il2.objects.air.YAK_9T_20.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 220", "MGunNS37ki 30"
        });
        com.maddox.il2.objects.air.YAK_9T_20.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
