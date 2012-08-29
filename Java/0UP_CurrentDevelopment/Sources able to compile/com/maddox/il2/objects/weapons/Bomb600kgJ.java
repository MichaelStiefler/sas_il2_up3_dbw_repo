package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb600kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb600kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb600kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/600kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 100F);
        com.maddox.rts.Property.set(class1, "power", 300F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 600F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
