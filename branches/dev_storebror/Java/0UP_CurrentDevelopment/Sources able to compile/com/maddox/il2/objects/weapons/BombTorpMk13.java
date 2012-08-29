package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk13 extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpMk13()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpMk13.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk13_new/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90.8F);
        com.maddox.rts.Property.set(class1, "power", 182.9F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 884.1F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 15.45F);
        com.maddox.rts.Property.set(class1, "traveltime", 337.31F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 14F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 24F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 74F);
        com.maddox.rts.Property.set(class1, "armingTime", 3F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 30F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 205F);
    }
}
