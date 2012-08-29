package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunWPL5 extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunWPL5()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunWPL5.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketWPL5.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
