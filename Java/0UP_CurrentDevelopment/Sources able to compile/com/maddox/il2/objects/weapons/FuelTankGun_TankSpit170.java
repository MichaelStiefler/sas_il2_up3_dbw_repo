package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankSpit170 extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_TankSpit170()
    {
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTankGun_TankSpit170.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_TankSpit170.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(var_class, "external", 1);
    }
}
