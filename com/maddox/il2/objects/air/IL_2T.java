// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_2T.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_2, PaintSchemeBMPar03, PaintSchemeBCSPar03, NetAircraft

public class IL_2T extends com.maddox.il2.objects.air.IL_2
{

    public IL_2T()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_2T.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IL2");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Il-2T(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3do/plane/Il-2T/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeBCSPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Il-2M3.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitIL_2_1942.class, com.maddox.il2.objects.air.CockpitIL2_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81F);
        com.maddox.rts.Property.set(class1, "Handicap", 1.2F);
        com.maddox.il2.objects.air.IL_2T.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 3
        });
        com.maddox.il2.objects.air.IL_2T.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb07"
        });
        com.maddox.il2.objects.air.IL_2T.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASi 250", "MGunShKASi 250", "MGunUBt 150", null
        });
        com.maddox.il2.objects.air.IL_2T.weaponsRegister(class1, "1x45-12", new java.lang.String[] {
            "MGunShKASi 250", "MGunShKASi 250", "MGunUBt 150", "BombGun4512"
        });
        com.maddox.il2.objects.air.IL_2T.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
