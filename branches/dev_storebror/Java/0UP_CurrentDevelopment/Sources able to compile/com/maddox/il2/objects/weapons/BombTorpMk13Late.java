package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk13Late extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpMk13Late()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpMk13Late.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk13_LATE_new/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90.8F);
        com.maddox.rts.Property.set(class1, "power", 274.9F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 1000.005F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 17.25F);
        com.maddox.rts.Property.set(class1, "traveltime", 211.9001F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 20F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 42F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 170F);
        com.maddox.rts.Property.set(class1, "armingTime", 4F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 200F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 400F);
    }
}
