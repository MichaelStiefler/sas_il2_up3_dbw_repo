// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   F2A_B339.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F2A, PaintSchemeBMPar01, NetAircraft, PaintScheme

public class F2A_B339 extends com.maddox.il2.objects.air.F2A
{

    public F2A_B339()
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Buffalo");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/BuffaloMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar01())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1939F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F2A-1.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitB339.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.032F);
        com.maddox.il2.objects.air.F2A_B339.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        com.maddox.il2.objects.air.F2A_B339.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.F2A_B339.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", null, null
        });
        com.maddox.il2.objects.air.F2A_B339.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50si 500", "MGunBrowning50si 500", "MGunBrowning50k 250", "MGunBrowning50k 250", "BombGun100Lbs 1", "BombGun100Lbs 1"
        });
        com.maddox.il2.objects.air.F2A_B339.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}