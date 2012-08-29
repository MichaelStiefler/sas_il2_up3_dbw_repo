package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunCBU24 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunCBU24()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunCBU24.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombCBU24.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
    }
}
