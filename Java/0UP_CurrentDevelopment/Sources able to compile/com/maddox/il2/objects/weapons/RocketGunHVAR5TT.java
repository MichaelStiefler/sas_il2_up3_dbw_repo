package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class RocketGunHVAR5TT extends com.maddox.il2.objects.weapons.RocketGunTinyTim
{

    public RocketGunHVAR5TT()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunHVAR5TT.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketHVAR5TT.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
