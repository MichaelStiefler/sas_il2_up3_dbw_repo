package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunPB1 extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunPB1()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunPB1.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketPB1.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
