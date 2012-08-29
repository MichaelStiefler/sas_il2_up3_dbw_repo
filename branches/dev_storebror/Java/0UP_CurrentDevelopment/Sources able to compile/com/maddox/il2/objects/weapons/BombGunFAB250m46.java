package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFAB250m46 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunFAB250m46()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunFAB250m46.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombFAB250m46.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 1.0F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
    }
}
