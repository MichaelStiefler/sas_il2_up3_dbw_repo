package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb50kgIncG extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb50kgIncG()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb50kgIncG.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/50kgIncG/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 20F);
        com.maddox.rts.Property.set(class1, "power", 25F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 50F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
