// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   YAK_9TALBERT.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            YAK_9TX, PaintSchemeSpecial, TypeAcePlane, NetAircraft

public class YAK_9TALBERT extends com.maddox.il2.objects.air.YAK_9TX
    implements com.maddox.il2.objects.air.TypeAcePlane
{

    public YAK_9TALBERT()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.YAK_9TALBERT.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Yak-9T(ofAlbert)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-9T.fmd");
        com.maddox.il2.objects.air.YAK_9TALBERT.weaponTriggersRegister(class1, new int[] {
            0, 1
        });
        com.maddox.il2.objects.air.YAK_9TALBERT.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_CANNON01"
        });
        com.maddox.il2.objects.air.YAK_9TALBERT.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 200", "MGunNS37ki 30"
        });
        com.maddox.il2.objects.air.YAK_9TALBERT.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}