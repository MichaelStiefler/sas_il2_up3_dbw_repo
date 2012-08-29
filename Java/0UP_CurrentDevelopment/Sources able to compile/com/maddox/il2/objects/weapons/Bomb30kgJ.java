package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb30kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb30kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb30kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/30kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 20F);
        com.maddox.rts.Property.set(class1, "power", 15F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 30F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
