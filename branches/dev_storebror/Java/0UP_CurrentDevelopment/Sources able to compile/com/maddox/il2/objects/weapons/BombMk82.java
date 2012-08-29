package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk82 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk82()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk82.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk82/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 50F);
        com.maddox.rts.Property.set(class1, "power", 125F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 226F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
