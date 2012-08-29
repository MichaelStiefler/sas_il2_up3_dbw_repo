package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb100Lbs extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb100Lbs()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb100Lbs.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/100LbsBomb/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 25F);
        com.maddox.rts.Property.set(class1, "power", 24F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.2505F);
        com.maddox.rts.Property.set(class1, "massa", 50F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
