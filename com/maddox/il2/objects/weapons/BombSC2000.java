// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSC2000.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSC2000 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSC2000()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSC2000.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SC-2000/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 275.8F);
        com.maddox.rts.Property.set(class1, "power", 975F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6604F);
        com.maddox.rts.Property.set(class1, "massa", 1950F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
