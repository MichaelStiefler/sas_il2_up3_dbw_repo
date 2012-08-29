// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank_200galL.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_200galL extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_200galL()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTank_200galL.class;
        com.maddox.rts.Property.set(var_class, "mesh", "3DO/Arms/200galL/mono.sim");
        com.maddox.rts.Property.set(var_class, "kalibr", 0.6F);
        com.maddox.rts.Property.set(var_class, "massa", 598.8F);
    }
}
