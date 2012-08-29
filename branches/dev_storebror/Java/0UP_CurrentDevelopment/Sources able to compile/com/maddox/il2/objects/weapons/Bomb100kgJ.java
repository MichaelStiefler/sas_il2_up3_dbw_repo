package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb100kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb100kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb100kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/100kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 55F);
        com.maddox.rts.Property.set(class1, "power", 50F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 100F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
