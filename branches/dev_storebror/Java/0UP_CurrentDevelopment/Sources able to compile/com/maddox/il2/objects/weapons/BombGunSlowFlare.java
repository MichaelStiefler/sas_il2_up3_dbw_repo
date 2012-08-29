package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSlowFlare extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunSlowFlare()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunSlowFlare.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombMk24Flare.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.03F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
