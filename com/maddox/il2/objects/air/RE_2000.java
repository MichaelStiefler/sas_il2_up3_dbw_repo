// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RE_2000.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            RE_2000xyz, PaintSchemeFMPar02, PaintSchemeBMPar09, NetAircraft, 
//            Aircraft

public class RE_2000 extends com.maddox.il2.objects.air.RE_2000xyz
{

    public RE_2000()
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
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "RE.2000");
        com.maddox.rts.Property.set(class1, "meshName_hu", "3DO/Plane/RE-2000(hu)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_hu", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/RE-2000(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/RE-2000(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/RE-2000.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitRE_2000.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9119F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_BOMB100KG01", "_BOMB100KG02", "_BOMBCASSETTE01", "_BOMBCASSETTE02"
        });
        com.maddox.il2.objects.air.RE_2000.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, null, null
        });
        com.maddox.il2.objects.air.RE_2000.weaponsRegister(class1, "2x100_Kg_Bombs", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", "BombGunIT_100_M 1", "BombGunIT_100_M 1", null, null
        });
        com.maddox.il2.objects.air.RE_2000.weaponsRegister(class1, "4xCassette", new java.lang.String[] {
            "MGunBredaSAFAT127re 300", "MGunBredaSAFAT127re 300", null, null, "BombGunSpezzoniera 44", "BombGunSpezzoniera 44"
        });
        com.maddox.il2.objects.air.RE_2000.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
