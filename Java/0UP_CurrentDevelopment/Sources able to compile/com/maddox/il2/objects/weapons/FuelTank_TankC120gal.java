package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_TankC120gal extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankC120gal()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankC120gal.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/F86_120galCombatTank/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 366.5F);
    }
}
