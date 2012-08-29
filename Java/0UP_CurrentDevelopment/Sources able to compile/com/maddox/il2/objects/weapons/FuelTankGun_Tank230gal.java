package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Tank230gal extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_Tank230gal()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun_Tank230gal.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_Tank230gal.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
