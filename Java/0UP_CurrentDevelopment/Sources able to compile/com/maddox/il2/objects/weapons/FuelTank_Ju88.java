package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Ju88 extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Ju88()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Ju88.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/FuelTank_Ju88/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.87F);
        com.maddox.rts.Property.set(class1, "massa", 700F);
    }
}
