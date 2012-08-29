package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpF5B extends com.maddox.il2.objects.weapons.TorpedoGun
{

    public BombGunTorpF5B()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunTorpF5B.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.BombTorpF5B.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.1F);
        com.maddox.rts.Property.set(class1, "external", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_torpedo");
    }
}
