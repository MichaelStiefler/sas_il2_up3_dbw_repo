// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_16TYPE24SAFONOV.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_16, PaintSchemeSpecial, TypeAcePlane, NetAircraft

public class I_16TYPE24SAFONOV extends com.maddox.il2.objects.air.I_16
    implements com.maddox.il2.objects.air.TypeAcePlane
{

    public I_16TYPE24SAFONOV()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.Skill = 3;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_16TYPE24SAFONOV.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type24(ofSafonov)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type24(ofSafonov).fmd");
        com.maddox.il2.objects.air.I_16TYPE24SAFONOV.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1
        });
        com.maddox.il2.objects.air.I_16TYPE24SAFONOV.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.I_16TYPE24SAFONOV.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 240", "MGunShKASsi 240", "MGunShVAKk 120", "MGunShVAKk 120"
        });
        com.maddox.il2.objects.air.I_16TYPE24SAFONOV.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
