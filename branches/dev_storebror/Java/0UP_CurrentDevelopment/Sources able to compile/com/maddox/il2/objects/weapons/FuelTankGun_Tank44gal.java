package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Tank44gal extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_Tank44gal()
    {
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTankGun_Tank44gal.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_Tank44gal.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(var_class, "external", 1);
    }
}
