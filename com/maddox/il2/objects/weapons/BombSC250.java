// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSC250.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSC250 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSC250()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSC250.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/SC-250/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 77F);
        com.maddox.rts.Property.set(class1, "power", 130F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.3683F);
        com.maddox.rts.Property.set(class1, "massa", 248.2F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
