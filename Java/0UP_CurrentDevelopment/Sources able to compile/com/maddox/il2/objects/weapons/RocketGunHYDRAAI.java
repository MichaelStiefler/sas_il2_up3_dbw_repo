package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHYDRAAI extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunHYDRAAI()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunHYDRAAI.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketHYDRA.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 5F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_82");
        com.maddox.rts.Property.set(class1, "cassette", 1);
    }
}
