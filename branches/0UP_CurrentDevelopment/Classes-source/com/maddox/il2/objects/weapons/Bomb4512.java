// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb4512.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class Bomb4512 extends com.maddox.il2.objects.weapons.Torpedo
{

    public Bomb4512()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb4512.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/45-12/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 20F);
        com.maddox.rts.Property.set(class1, "power", 99.8F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 802F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 22.2F);
        com.maddox.rts.Property.set(class1, "traveltime", 182.2F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 14F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 24F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 74F);
        com.maddox.rts.Property.set(class1, "armingTime", 3F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 30F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 205F);
    }
}
