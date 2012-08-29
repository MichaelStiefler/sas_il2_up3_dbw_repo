package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FTR extends com.maddox.il2.objects.weapons.FuelTank
{

    public FTR()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FTR.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FTR/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 150.8F);
    }
}
