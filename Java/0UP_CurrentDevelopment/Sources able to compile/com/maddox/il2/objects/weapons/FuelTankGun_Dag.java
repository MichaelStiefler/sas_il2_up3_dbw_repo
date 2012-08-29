package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTankGun_Dag extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_Dag()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun_Dag.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_Dag.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
