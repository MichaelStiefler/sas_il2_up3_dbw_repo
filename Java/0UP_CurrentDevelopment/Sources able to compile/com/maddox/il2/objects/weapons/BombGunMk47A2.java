package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk47A2 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunMk47A2()
    {
    }

    public void setBombDelay(float f)
    {
        bombDelay = 5F;
        if(bomb != null)
            bomb.delayExplosion = bombDelay;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunMk47A2.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombMk47A2.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_phball");
    }
}
