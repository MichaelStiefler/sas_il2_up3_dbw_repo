package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHYDRA extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunHYDRA()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunHYDRA.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketHYDRA.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 5F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_82");
    }
}
