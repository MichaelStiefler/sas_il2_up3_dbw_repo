package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankF4U extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankF4U()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankF4U.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/TankF4U/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 445F);
    }
}
