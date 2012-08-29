// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb5327.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class Bomb5327 extends com.maddox.il2.objects.weapons.Torpedo
{

    public Bomb5327()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb5327.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/45-12/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 20F);
        com.maddox.rts.Property.set(class1, "power", 265F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.533F);
        com.maddox.rts.Property.set(class1, "massa", 1600F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 23.25F);
        com.maddox.rts.Property.set(class1, "traveltime", 159.13F);
        com.maddox.rts.Property.set(class1, "startingspeed", 40F);
    }
}
