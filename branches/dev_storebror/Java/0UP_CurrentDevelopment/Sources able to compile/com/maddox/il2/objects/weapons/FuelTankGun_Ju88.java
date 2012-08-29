package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Ju88 extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_Ju88()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun_Ju88.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_Ju88.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
