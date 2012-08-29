// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombIT_250_T.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombIT_250_T extends com.maddox.il2.objects.weapons.Bomb
{

    public BombIT_250_T()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombIT_250_T.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/250kgBombT/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 90F);
        com.maddox.rts.Property.set(class1, "power", 106.5F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.38F);
        com.maddox.rts.Property.set(class1, "massa", 259F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
