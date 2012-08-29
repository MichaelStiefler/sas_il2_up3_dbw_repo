// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SPITFIRE5BLF18LBS.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE, PaintSchemeFMPar04, NetAircraft, Aircraft

public class SPITFIRE5BLF18LBS extends com.maddox.il2.objects.air.SPITFIRE
{

    public SPITFIRE5BLF18LBS()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE5BLF18LBS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/SpitfireMkVb(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_gb", "3DO/Plane/SpitfireMkVb(GB)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_gb", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1942F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/spitfirelfvb+18lbs.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSpit5B.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 60", "MGunHispanoMkIki 60"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
