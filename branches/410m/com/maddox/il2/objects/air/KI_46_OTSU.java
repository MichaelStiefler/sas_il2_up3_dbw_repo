// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_46_OTSU.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            KI_46, PaintSchemeFMPar02, TypeFighter, NetAircraft

public class KI_46_OTSU extends com.maddox.il2.objects.air.KI_46
    implements com.maddox.il2.objects.air.TypeFighter
{

    public KI_46_OTSU()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_46_OTSU.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-46");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-46(Otsu)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-46-IIIKai.fmd");
        com.maddox.il2.objects.air.KI_46_OTSU.weaponTriggersRegister(class1, new int[] {
            1, 1
        });
        com.maddox.il2.objects.air.KI_46_OTSU.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.KI_46_OTSU.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHo5ki 200", "MGunHo5ki 200"
        });
        com.maddox.il2.objects.air.KI_46_OTSU.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
