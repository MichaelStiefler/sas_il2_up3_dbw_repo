package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb60kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb60kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb60kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/60kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 30F);
        com.maddox.rts.Property.set(class1, "power", 30F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 60F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
