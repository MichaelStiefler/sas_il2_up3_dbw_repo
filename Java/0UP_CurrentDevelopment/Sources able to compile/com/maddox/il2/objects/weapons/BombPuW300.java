package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombPuW300 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPuW300()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPuW300.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/PuW-300/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 50F);
        com.maddox.rts.Property.set(class1, "power", 145F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 320F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
