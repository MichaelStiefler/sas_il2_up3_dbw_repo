package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunMk25Marker extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunMk25Marker()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunMk25Marker.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombMk25Marker.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_phball");
    }
}
