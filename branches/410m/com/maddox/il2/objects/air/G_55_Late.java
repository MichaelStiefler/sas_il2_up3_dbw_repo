// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   G_55_Late.java

package com.maddox.il2.objects.air;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            G_55xyz, PaintSchemeBMPar09, PaintSchemeFMPar02, NetAircraft, 
//            Aircraft

public class G_55_Late extends com.maddox.il2.objects.air.G_55xyz
{

    public G_55_Late()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "G.55");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/G-55(it)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/G-55(multi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/G-55-late.fmd");
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9119F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03"
        });
        com.maddox.il2.objects.air.G_55_Late.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55 300", "MGunMG15120t 200", "MGunMG15120kh 250", "MGunMG15120kh 250"
        });
        com.maddox.il2.objects.air.G_55_Late.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null
        });
    }
}
