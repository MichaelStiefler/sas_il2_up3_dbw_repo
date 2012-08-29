package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTi extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunTi()
    {
    }

    public void setBombDelay(float f)
    {
        bombDelay = 0.0F;
        if(bomb != null)
            bomb.delayExplosion = bombDelay;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunTi.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombTi.class);
        com.maddox.rts.Property.set(class1, "bullets", 32);
        com.maddox.rts.Property.set(class1, "shotFreq", 8F);
        com.maddox.rts.Property.set(class1, "cassette", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_phball");
    }
}
