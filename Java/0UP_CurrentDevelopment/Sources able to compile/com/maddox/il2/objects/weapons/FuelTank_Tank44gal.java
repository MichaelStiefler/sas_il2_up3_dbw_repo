package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank44gal extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank44gal()
    {
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTank_Tank44gal.class;
        com.maddox.rts.Property.set(var_class, "mesh", "3DO/Arms/Tank44gal/mono.sim");
        com.maddox.rts.Property.set(var_class, "kalibr", 0.6F);
        com.maddox.rts.Property.set(var_class, "massa", 158.4F);
    }
}
