// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_80A.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_80, PaintSchemeFMPar05, PaintSchemeFMPar06, NetAircraft, 
//            Aircraft

public class P_80A extends com.maddox.il2.objects.air.P_80
{

    public P_80A()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_80A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "YP-80");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-80(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-80(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/P-80(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "yearService", 1944.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.3F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-80A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitYP_80.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.965F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
