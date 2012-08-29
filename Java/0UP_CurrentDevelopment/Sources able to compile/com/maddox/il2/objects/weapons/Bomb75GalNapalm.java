package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb75GalNapalm extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb75GalNapalm()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb75GalNapalm.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Tank75galNapalm/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 77F);
        com.maddox.rts.Property.set(class1, "power", 75F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 340F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
