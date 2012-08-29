package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class FTGun_71 extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FTGun_71()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FTGun_71.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FT_71.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
