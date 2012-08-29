package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpF5Bheavy extends com.maddox.il2.objects.weapons.TorpedoLtfFiume
{

    public BombTorpF5Bheavy()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpF5Bheavy.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/LTF5B/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 120F);
        com.maddox.rts.Property.set(class1, "power", 267.5F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 812F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 20.58F);
        com.maddox.rts.Property.set(class1, "traveltime", 97.1817F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
    }
}
