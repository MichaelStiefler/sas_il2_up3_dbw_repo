// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MC_202_12.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            MC_202xyz, PaintSchemeFCSPar02, PaintSchemeFMPar01, NetAircraft

public class MC_202_12 extends com.maddox.il2.objects.air.MC_202xyz
{

    public MC_202_12()
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
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "M.C.202");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/MC-202_XII(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MC-202_XII(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MC-202.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitMC_202.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.81305F);
        com.maddox.il2.objects.air.MC_202_12.weaponTriggersRegister(class1, new int[] {
            1, 1, 0, 0
        });
        com.maddox.il2.objects.air.MC_202_12.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.MC_202_12.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127siMC202 370", "MGunBredaSAFAT127siMC202 370", "MGunBredaSAFAT77k 500", "MGunBredaSAFAT77k 500"
        });
        com.maddox.il2.objects.air.MC_202_12.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
