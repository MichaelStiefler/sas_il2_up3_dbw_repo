// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSC2500.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSC2500 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSC2500()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSC2500.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SC-2500/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 325F);
        com.maddox.rts.Property.set(class1, "power", 1200F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.8128F);
        com.maddox.rts.Property.set(class1, "massa", 2400F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
