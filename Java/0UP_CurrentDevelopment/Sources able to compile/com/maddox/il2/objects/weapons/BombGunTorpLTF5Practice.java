package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpLTF5Practice extends com.maddox.il2.objects.weapons.TorpedoGun
{

    public BombGunTorpLTF5Practice()
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
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombGunTorpLTF5Practice.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.BombTorpLTF5Practice.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 0.1F);
        com.maddox.rts.Property.set(var_class, "external", 1);
        com.maddox.rts.Property.set(var_class, "sound", "weapon.bombgun_torpedo");
    }
}
