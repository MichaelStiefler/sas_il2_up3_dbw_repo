// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MC_200_1.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MC_200xyz, PaintSchemeFCSPar02, PaintSchemeFMPar01, NetAircraft, 
//            Aircraft

public class MC_200_1 extends com.maddox.il2.objects.air.MC_200xyz
{

    public MC_200_1()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "M.C.200");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/MC-200_I(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MC-200_I(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MC-200.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
