package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFlareLight extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunFlareLight()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunFlareLight.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombMk24FlareLight.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.3F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
