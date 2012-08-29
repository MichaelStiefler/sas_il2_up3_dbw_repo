package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunCargo70 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunCargo70()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunCargo70.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombCargo70.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.75F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
