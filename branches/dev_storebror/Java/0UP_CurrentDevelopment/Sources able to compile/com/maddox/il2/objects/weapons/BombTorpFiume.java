package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpFiume extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpFiume()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpFiume.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/fiume/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 110F);
        com.maddox.rts.Property.set(class1, "power", 214F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 905F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 20.59F);
        com.maddox.rts.Property.set(class1, "traveltime", 145.5988F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 24F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 31.5F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 107F);
        com.maddox.rts.Property.set(class1, "armingTime", 3.5F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 100F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 300F);
    }
}
