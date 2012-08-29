package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunPuW100M extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunPuW100M()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunPuW100M.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombPuW100M.class);
        com.maddox.rts.Property.set(class1, "bullets", 2);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "external", 0);
        com.maddox.rts.Property.set(class1, "cassette", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
    }
}
