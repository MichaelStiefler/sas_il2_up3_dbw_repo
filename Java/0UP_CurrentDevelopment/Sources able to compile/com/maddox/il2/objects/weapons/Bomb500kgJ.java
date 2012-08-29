package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb500kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb500kgJ()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb500kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/500kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 80F);
        com.maddox.rts.Property.set(class1, "power", 250F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 500F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
