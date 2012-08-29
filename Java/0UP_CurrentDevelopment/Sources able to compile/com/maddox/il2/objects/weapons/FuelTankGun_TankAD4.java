package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankAD4 extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_TankAD4()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun_TankAD4.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_TankAD4.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
