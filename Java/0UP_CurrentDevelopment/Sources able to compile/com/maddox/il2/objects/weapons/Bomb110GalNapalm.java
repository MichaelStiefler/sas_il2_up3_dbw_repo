package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb110GalNapalm extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb110GalNapalm()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb110GalNapalm.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Tank110galNapalm/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 113F);
        com.maddox.rts.Property.set(class1, "power", 110F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6F);
        com.maddox.rts.Property.set(class1, "massa", 500F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
