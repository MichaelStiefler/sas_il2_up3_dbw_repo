package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FTL extends com.maddox.il2.objects.weapons.FuelTank
{

    public FTL()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FTL.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FTL/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 150.8F);
    }
}
