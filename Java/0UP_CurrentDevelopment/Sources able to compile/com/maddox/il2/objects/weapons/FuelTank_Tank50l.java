package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Tank50l extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank50l()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Tank50l.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Tank50l/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 39F);
    }
}
