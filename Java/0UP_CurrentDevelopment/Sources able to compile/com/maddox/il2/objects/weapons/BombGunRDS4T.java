package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunRDS4T extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunRDS4T()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunRDS4T.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombRDS4T.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.05F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
        com.maddox.rts.Property.set(class1, "newEffect", 1);
    }
}
