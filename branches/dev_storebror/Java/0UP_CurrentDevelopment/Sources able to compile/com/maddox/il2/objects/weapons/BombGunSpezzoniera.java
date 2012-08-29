package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunSpezzoniera extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunSpezzoniera()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunSpezzoniera.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.Bomblet2Kg.class);
        com.maddox.rts.Property.set(class1, "bullets", 22);
        com.maddox.rts.Property.set(class1, "shotFreq", 20F);
        com.maddox.rts.Property.set(class1, "external", 0);
        com.maddox.rts.Property.set(class1, "cassette", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
