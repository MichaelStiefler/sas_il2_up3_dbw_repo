package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk13Brit extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpMk13Brit()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpMk13Brit.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Beau_torpedo/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90.8F);
        com.maddox.rts.Property.set(class1, "power", 247F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 817F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 20.59F);
        com.maddox.rts.Property.set(class1, "traveltime", 111.0001F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 14F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 24F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 135F);
        com.maddox.rts.Property.set(class1, "armingTime", 3F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 250F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 350F);
    }
}
