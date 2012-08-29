package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpType91 extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpType91()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpType91.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk13_Torpedo/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90.8F);
        com.maddox.rts.Property.set(class1, "power", 204.9F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 935F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 21.09F);
        com.maddox.rts.Property.set(class1, "traveltime", 94.00061F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 15F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 25F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 86F);
        com.maddox.rts.Property.set(class1, "armingTime", 3F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 30F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 240F);
    }
}
