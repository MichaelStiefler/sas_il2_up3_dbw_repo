package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_TankC120gal extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_TankC120gal()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun_TankC120gal.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_TankC120gal.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}