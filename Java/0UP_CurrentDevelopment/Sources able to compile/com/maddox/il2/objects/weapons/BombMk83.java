package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk83 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk83()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk83.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk83/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 100F);
        com.maddox.rts.Property.set(class1, "power", 250F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 454F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
