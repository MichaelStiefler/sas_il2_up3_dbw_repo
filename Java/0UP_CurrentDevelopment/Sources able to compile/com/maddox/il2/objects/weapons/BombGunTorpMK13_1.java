package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombGunTorpMK13_1 extends com.maddox.il2.objects.weapons.TorpedoGun
{

    public BombGunTorpMK13_1()
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
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombGunTorpMK13_1.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.BombTorpMk13_1.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 0.1F);
        com.maddox.rts.Property.set(var_class, "external", 1);
        com.maddox.rts.Property.set(var_class, "sound", "weapon.bombgun_torpedo");
    }
}
