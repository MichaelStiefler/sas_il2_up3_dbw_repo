package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombPuW50 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPuW50()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPuW50.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/PuW-50/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 25F);
        com.maddox.rts.Property.set(class1, "power", 24F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.162F);
        com.maddox.rts.Property.set(class1, "massa", 50F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
