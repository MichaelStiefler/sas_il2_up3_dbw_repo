// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_2I.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_2, PaintSchemeFMPar01, PaintSchemeFCSPar01, TypeFighter, 
//            NetAircraft

public class IL_2I extends com.maddox.il2.objects.air.IL_2
    implements com.maddox.il2.objects.air.TypeFighter
{

    public IL_2I()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_2I.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IL2");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Il-2I(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3do/plane/Il-2I/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-2I.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitIL_2_1942.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81F);
        com.maddox.rts.Property.set(class1, "Handicap", 1.1F);
        com.maddox.il2.objects.air.IL_2I.weaponTriggersRegister(class1, new int[] {
            1, 1
        });
        com.maddox.il2.objects.air.IL_2I.weaponHooksRegister(class1, new java.lang.String[] {
            "_Cannon01", "_Cannon02"
        });
        com.maddox.il2.objects.air.IL_2I.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVYak 150", "MGunVYak 150"
        });
        com.maddox.il2.objects.air.IL_2I.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
