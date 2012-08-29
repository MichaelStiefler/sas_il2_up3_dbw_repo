// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LA_7KOJEDUB.java

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            LA_X, PaintSchemeSpecial, TypeAcePlane, NetAircraft

public class LA_7KOJEDUB extends com.maddox.il2.objects.air.LA_X
    implements com.maddox.il2.objects.air.TypeAcePlane
{

    public LA_7KOJEDUB()
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
        java.lang.Class class1 = com.maddox.il2.objects.air.LA_7KOJEDUB.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "La");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/La-7(ofKojedub)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/La-7.fmd");
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponTriggersRegister(class1, new int[] {
            1, 1, 3, 3, 9, 9
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null, null, null, null
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", "BombGunFAB50 1", "BombGunFAB50 1", null, null
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", "BombGunFAB100 1", "BombGunFAB100 1", null, null
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        com.maddox.il2.objects.air.LA_7KOJEDUB.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
