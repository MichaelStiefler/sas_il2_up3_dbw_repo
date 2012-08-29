// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CR_42.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CR_42X, PaintSchemeFMPar00, PaintSchemeFCSPar01, NetAircraft

public class CR_42 extends com.maddox.il2.objects.air.CR_42X
{

    public CR_42()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.CR_42.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "CR.42");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/CR42(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/CR42(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/CR42.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitCR42.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.742F);
        com.maddox.il2.objects.air.CR_42.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3
        });
        com.maddox.il2.objects.air.CR_42.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", null, null
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "2sc50", new java.lang.String[] {
            "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGunSC50 1", "BombGunSC50 1"
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "2sc70", new java.lang.String[] {
            "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGunSC70 1", "BombGunSC70 1"
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "2x50", new java.lang.String[] {
            "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGun50kg 1", "BombGun50kg 1"
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBredaSAFAT127siCR42 400", "MGunBredaSAFAT127siCR42 400", "BombGun100kg 1", "BombGun100kg 1"
        });
        com.maddox.il2.objects.air.CR_42.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
