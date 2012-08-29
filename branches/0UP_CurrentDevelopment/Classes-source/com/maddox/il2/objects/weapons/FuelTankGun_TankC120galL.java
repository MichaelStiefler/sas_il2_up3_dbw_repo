// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTankGun_TankC120galL.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTankGun

public class FuelTankGun_TankC120galL extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_TankC120galL()
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
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTankGun_TankC120galL.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_TankC120galL.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(var_class, "external", 1);
    }
}
