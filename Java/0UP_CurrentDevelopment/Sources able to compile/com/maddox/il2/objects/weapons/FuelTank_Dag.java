package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FuelTank_Dag extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Dag()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Dag.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/ETC-900Dag/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 230F);
    }
}
