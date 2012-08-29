// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   A5M4.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            A5M, PaintSchemeFMPar01, PaintSchemeFCSPar05, NetAircraft

public class A5M4 extends com.maddox.il2.objects.air.A5M
{

    public A5M4()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A5M");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A5M4(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/A5M4(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A5M4.fmd");
        com.maddox.il2.objects.air.A5M4.weaponTriggersRegister(class1, new int[] {
            0, 0, 9, 9
        });
        com.maddox.il2.objects.air.A5M4.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.A5M4.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVikkersKs 500", "MGunVikkersKs 500", null, null
        });
        com.maddox.il2.objects.air.A5M4.weaponsRegister(class1, "1xdt", new java.lang.String[] {
            "MGunVikkersKs 500", "MGunVikkersKs 500", "PylonA5MPLN1", "FuelTankGun_TankA5M"
        });
        com.maddox.il2.objects.air.A5M4.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
