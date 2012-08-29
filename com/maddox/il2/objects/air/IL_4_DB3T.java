// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_4_DB3T.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            IL_4, PaintSchemeBMPar01, TypeBomber, NetAircraft, 
//            Aircraft

public class IL_4_DB3T extends com.maddox.il2.objects.air.IL_4
    implements com.maddox.il2.objects.air.TypeBomber
{

    public IL_4_DB3T()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_4_DB3T.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "DB-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/DB-3T/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/DB-3T.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "torp1", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
