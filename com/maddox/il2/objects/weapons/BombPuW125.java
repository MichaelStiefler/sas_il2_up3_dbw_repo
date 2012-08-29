// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombPuW125.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombPuW125 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPuW125()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPuW125.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/PuW-12_5/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 25F);
        com.maddox.rts.Property.set(class1, "power", 1.01F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.14F);
        com.maddox.rts.Property.set(class1, "massa", 12.5F);
        com.maddox.rts.Property.set(class1, "randomOrient", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_cassette");
    }
}
