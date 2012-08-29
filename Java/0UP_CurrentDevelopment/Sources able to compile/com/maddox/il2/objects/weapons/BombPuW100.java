package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombPuW100 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPuW100()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPuW100.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/PuW-100/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 50F);
        com.maddox.rts.Property.set(class1, "power", 62F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 115F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
