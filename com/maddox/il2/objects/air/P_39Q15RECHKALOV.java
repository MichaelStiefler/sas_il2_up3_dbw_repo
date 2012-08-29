// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_39Q15RECHKALOV.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_39, PaintSchemeSpecial, TypeAcePlane, NetAircraft

public class P_39Q15RECHKALOV extends com.maddox.il2.objects.air.P_39
    implements com.maddox.il2.objects.air.TypeAcePlane
{

    public P_39Q15RECHKALOV()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.P_39Q15RECHKALOV.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P39");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/P-39Q-15(ofRechkalov)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-39Q-15(ofRechkalov).fmd");
        com.maddox.il2.objects.air.P_39Q15RECHKALOV.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 3
        });
        com.maddox.il2.objects.air.P_39Q15RECHKALOV.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.P_39Q15RECHKALOV.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunM4ki 60", null
        });
        com.maddox.il2.objects.air.P_39Q15RECHKALOV.weaponsRegister(class1, "1xFAB250", new java.lang.String[] {
            "MGunBrowning50si 320", "MGunBrowning50si 320", "MGunM4ki 60", "BombGunFAB250 1"
        });
        com.maddox.il2.objects.air.P_39Q15RECHKALOV.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
