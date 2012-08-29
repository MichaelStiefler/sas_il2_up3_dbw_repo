package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB250m46 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFAB250m46()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFAB250m46.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/fab-250m-46/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 80F);
        com.maddox.rts.Property.set(class1, "power", 130F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.408F);
        com.maddox.rts.Property.set(class1, "massa", 250F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
