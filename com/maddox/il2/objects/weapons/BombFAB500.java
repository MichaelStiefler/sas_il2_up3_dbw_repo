// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombFAB500.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombFAB500 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFAB500()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFAB500.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3do/arms/fab-500/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 77F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 275F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.678F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_std");
    }
}
