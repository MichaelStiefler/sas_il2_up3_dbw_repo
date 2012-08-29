package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunPB2 extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunPB2()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunPB2.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketPB2.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
