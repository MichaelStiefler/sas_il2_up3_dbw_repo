package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank100gal extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank100gal()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Tank100gal.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Tank100gal/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 195F);
    }
}