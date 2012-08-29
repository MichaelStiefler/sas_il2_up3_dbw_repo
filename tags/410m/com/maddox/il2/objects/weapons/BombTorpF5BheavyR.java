// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpF5BheavyR.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorpF5BheavyR extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpF5BheavyR()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpF5BheavyR.class;
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
        com.maddox.rts.Property.set(class1, "impactAngleMin", 18F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 27F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 88F);
        com.maddox.rts.Property.set(class1, "armingTime", 2.0F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 40F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 250F);
        com.maddox.rts.Property.set(class1, "spreadDirection", 1);
    }
}
