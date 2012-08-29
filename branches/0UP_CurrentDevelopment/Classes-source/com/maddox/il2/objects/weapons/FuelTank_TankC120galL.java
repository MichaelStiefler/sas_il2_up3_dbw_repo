// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank_TankC120galL.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_TankC120galL extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankC120galL()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankC120galL.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/F86_120galCombatTankL/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 359.3F);
    }
}
