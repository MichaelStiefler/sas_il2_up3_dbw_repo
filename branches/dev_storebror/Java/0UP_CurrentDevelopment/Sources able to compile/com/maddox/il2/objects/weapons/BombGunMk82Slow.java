package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk82Slow extends com.maddox.il2.objects.weapons.BombGunSC50
{

    public BombGunMk82Slow()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunMk82Slow.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombMk82.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.033F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
    }
}
