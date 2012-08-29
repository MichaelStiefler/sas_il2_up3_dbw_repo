// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GO_229A1.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            GO_229, PaintSchemeFMPar06, TypeFighter, TypeBNZFighter, 
//            NetAircraft

public class GO_229A1 extends com.maddox.il2.objects.air.GO_229
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public GO_229A1()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.GO_229A1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Go-229");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Go-229A-1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1946.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1999F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ho-229.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitGO_229.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.51305F);
        com.maddox.il2.objects.air.GO_229A1.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.GO_229A1.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.GO_229A1.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK103k 120", "MGunMK103k 120"
        });
        com.maddox.il2.objects.air.GO_229A1.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
