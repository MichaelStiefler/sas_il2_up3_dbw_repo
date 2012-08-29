package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombMk53Charge extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk53Charge()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk53Charge.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk53_Charge/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90F);
        com.maddox.rts.Property.set(class1, "power", 90F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 148F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
