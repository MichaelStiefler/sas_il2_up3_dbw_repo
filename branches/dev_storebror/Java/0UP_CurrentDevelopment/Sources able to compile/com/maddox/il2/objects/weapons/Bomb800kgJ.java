package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb800kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb800kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb800kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/800kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 120F);
        com.maddox.rts.Property.set(class1, "power", 400F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 800F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
