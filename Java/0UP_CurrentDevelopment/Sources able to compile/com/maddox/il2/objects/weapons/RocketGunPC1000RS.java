package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunPC1000RS extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunPC1000RS()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunPC1000RS.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketPC1000RS.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 4F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
