package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb500lbsDC extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb500lbsDC()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb500lbsDC.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/500LbsBomb/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 50F);
        com.maddox.rts.Property.set(class1, "power", 125F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 250F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}