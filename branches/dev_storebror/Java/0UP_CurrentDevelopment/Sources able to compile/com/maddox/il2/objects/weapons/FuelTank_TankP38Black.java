package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankP38Black extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankP38Black()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankP38Black.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/P-38_tankBlack/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 156F);
    }
}
