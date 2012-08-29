package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunR4Ms extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunR4Ms()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 + 2.81F);
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunR4Ms.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketR4M.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 4F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
