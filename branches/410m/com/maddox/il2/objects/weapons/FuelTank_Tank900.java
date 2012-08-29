// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank_Tank900.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_Tank900 extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank900()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Tank900.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/FuelTank-900/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.9F);
        com.maddox.rts.Property.set(class1, "massa", 690);
    }
}
