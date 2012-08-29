package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb15kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb15kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb15kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/15kgFragJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 15F);
        com.maddox.rts.Property.set(class1, "power", 7.5F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 15F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
