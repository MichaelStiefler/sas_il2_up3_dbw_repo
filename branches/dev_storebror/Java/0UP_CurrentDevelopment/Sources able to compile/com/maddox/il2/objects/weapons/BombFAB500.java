package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombFAB500 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFAB500()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFAB500.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/fab-500/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 77F);
        com.maddox.rts.Property.set(class1, "power", 275F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.678F);
        com.maddox.rts.Property.set(class1, "massa", 500F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
