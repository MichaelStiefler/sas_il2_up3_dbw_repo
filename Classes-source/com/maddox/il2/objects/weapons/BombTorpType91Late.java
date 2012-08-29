// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpType91Late.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorpType91Late extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpType91Late()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpType91Late.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk13_Torpedo/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90.8F);
        com.maddox.rts.Property.set(class1, "power", 308F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 921F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 21.09F);
        com.maddox.rts.Property.set(class1, "traveltime", 71.10061F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngle", 20F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 13.5F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 26.5F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 117F);
        com.maddox.rts.Property.set(class1, "armingTime", 4F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 60F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 330F);
    }
}
