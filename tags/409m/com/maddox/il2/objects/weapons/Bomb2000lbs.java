// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb2000lbs.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb2000lbs extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb2000lbs()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb2000lbs.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/2000LbsBomb/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 250F);
        com.maddox.rts.Property.set(class1, "power", 500F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 1000F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}