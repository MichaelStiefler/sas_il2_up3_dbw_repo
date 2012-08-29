package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHVAR5BEAU25AS extends RocketGun
{

    public RocketGunHVAR5BEAU25AS()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.weapons.RocketGunHVAR5BEAU25AS.class;
        Property.set(class1, "bulletClass", (Object) com.maddox.il2.objects.weapons.RocketHVAR5BEAU25AS.class);
        Property.set(class1, "bullets", 1);
        Property.set(class1, "shotFreq", 1.0F);
        Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}