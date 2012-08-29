// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_210CA1ZSTR.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_210, PaintSchemeBMPar05, TypeFighter, TypeBNZFighter, 
//            TypeStormovik, TypeStormovikArmored, NetAircraft

public class ME_210CA1ZSTR extends com.maddox.il2.objects.air.ME_210
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeStormovikArmored
{

    public ME_210CA1ZSTR()
    {
    }

    protected void moveBayDoor(float f)
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
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_210CA1ZSTR.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me-210");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-210Ca-1Zerstorer/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
        com.maddox.il2.objects.air.ME_210CA1ZSTR.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 10, 10, 1
        });
        com.maddox.il2.objects.air.ME_210CA1ZSTR.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_CANNON03"
        });
        com.maddox.il2.objects.air.ME_210CA1ZSTR.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "MGunPaK40 42"
        });
        com.maddox.il2.objects.air.ME_210CA1ZSTR.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null
        });
    }
}
