package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpLTF5Practice extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpLTF5Practice()
    {
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombTorpLTF5Practice.class;
        com.maddox.rts.Property.set(var_class, "mesh", "3do/arms/Practice/mono.sim");
        com.maddox.rts.Property.set(var_class, "radius", 0.2F);
        com.maddox.rts.Property.set(var_class, "power", 1.0F);
        com.maddox.rts.Property.set(var_class, "powerType", 0);
        com.maddox.rts.Property.set(var_class, "kalibr", 0.45F);
        com.maddox.rts.Property.set(var_class, "massa", 725);
        com.maddox.rts.Property.set(var_class, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(var_class, "velocity", 20.58F);
        com.maddox.rts.Property.set(var_class, "traveltime", 98.17F);
        com.maddox.rts.Property.set(var_class, "startingspeed", 0.0F);
    }
}
