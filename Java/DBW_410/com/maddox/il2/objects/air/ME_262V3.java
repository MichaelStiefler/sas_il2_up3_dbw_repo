// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_262V3.java

package com.maddox.il2.objects.air;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeBMPar03, NetAircraft, Aircraft

public class ME_262V3 extends com.maddox.il2.objects.air.ME_262
{

    public ME_262V3()
    {
    }
    
    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_262V3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me 262");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-262V-3/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "yearService", 1943.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-262A-1a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitME_262.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.74615F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
    }
}
