package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunFAB1000int extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunFAB1000int()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunFAB1000int.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombFAB1000.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "external", 0);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
    }
}
