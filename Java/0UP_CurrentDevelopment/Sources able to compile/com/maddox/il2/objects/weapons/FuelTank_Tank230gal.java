package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank230gal extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank230gal()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Tank230gal.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/TankF84/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 545F);
    }
}
