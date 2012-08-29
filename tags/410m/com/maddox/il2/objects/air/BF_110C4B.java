// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_110C4B.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BF_110, PaintSchemeBMPar01, NetAircraft

public class BF_110C4B extends com.maddox.il2.objects.air.BF_110
{

    public BF_110C4B()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_110C4B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf-110");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-110C-4B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-110C-4.fmd");
        com.maddox.il2.objects.air.BF_110C4B.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 10, 3, 3
        });
        com.maddox.il2.objects.air.BF_110C4B.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.BF_110C4B.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", null, null
        });
        com.maddox.il2.objects.air.BF_110C4B.weaponsRegister(class1, "2sc250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunSC250", "BombGunSC250"
        });
        com.maddox.il2.objects.air.BF_110C4B.weaponsRegister(class1, "2ab250", new java.lang.String[] {
            "MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1000", "MGunMG17ki 1048", "MGunMGFFki 180", "MGunMGFFki 180", "MGunMG15t 750", "BombGunAB250", "BombGunAB250"
        });
        com.maddox.il2.objects.air.BF_110C4B.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
