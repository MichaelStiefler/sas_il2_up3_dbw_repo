// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombPC1600.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombPC1600 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPC1600()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPC1600.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/PC-1600/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 32F);
        com.maddox.rts.Property.set(class1, "power", 230F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.5333F);
        com.maddox.rts.Property.set(class1, "massa", 1600F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
