package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk81 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk81()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk81.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk81/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 50F);
        com.maddox.rts.Property.set(class1, "power", 64F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 113F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
