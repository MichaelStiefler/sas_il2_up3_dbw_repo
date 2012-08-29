package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomblet2Kg extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomblet2Kg()
    {
    }

    protected boolean haveSound()
    {
        return index % 16 == 0;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomblet2Kg.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/2KgBomblet/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 4F);
        com.maddox.rts.Property.set(class1, "power", 0.12F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.1F);
        com.maddox.rts.Property.set(class1, "massa", 2.0F);
        com.maddox.rts.Property.set(class1, "randomOrient", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_cassette");
    }
}
