package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb75Napalm extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb75Napalm()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb75Napalm.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Tank75gal_Napalm/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 77F);
        com.maddox.rts.Property.set(class1, "power", 75F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 340F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
