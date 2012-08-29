// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombIT_250Kg.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombIT_250Kg extends com.maddox.il2.objects.weapons.Bomb
{

    public BombIT_250Kg()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombIT_250Kg.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/BombIT250Kg/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 150F);
        com.maddox.rts.Property.set(class1, "power", 106.5F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.38F);
        com.maddox.rts.Property.set(class1, "massa", 259F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
