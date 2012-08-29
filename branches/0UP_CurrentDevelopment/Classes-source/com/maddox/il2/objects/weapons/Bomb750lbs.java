// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb750lbs.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb750lbs extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb750lbs()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb750lbs.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/750lbsBomb/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 200F);
        com.maddox.rts.Property.set(class1, "power", 225F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 340F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
