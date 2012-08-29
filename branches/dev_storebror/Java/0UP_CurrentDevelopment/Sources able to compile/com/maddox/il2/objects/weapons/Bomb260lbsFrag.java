package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb260lbsFrag extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb260lbsFrag()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb260lbsFrag.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/260LbsBombFrag/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 75F);
        com.maddox.rts.Property.set(class1, "power", 50F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 118F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
