// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BLENHEIM4F.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            BLENHEIM, PaintSchemeBMPar00, TypeStormovik, TypeFighter, 
//            NetAircraft, Aircraft

public class BLENHEIM4F extends com.maddox.il2.objects.air.BLENHEIM
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeFighter
{

    public BLENHEIM4F()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BLENHEIM4F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "BlenheimIF");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/BlenheimMkIVF/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1937F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Blenheim_MkIV.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.73425F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitBLENHEIM4F.class, com.maddox.il2.objects.air.CockpitBLENHEIM4F_TGunner.class
        })));
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 10, 10, 1, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_ExternalDev01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunVikkersKt 2600", "MGunVikkersKt 2600", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20mm", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunVikkersKt 2600", "MGunVikkersKt 2600", "MGunHispanoMkIkpzl 180", "PylonBlenheim20"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
