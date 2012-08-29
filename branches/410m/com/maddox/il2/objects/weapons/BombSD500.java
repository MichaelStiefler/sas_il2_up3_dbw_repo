// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSD500.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSD500 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSD500()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSD500.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/SD-500/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 75F);
        com.maddox.rts.Property.set(class1, "power", 200F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.4445F);
        com.maddox.rts.Property.set(class1, "massa", 535F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
