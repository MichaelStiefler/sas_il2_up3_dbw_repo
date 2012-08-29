package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombBlu2Napalm extends com.maddox.il2.objects.weapons.Bomb
{

    public BombBlu2Napalm()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombBlu2Napalm.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Blu2/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 77F);
        com.maddox.rts.Property.set(class1, "power", 75F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 316F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
