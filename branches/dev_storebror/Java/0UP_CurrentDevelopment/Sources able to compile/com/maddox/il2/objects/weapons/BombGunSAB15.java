package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSAB15 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunSAB15()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunSAB15.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombSAB15.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 3F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
