// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb100kgCZ.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb100kgCZ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb100kgCZ()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb100kgCZ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/S328bomb-100kgCZ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 125F);
        com.maddox.rts.Property.set(class1, "power", 50F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 100F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
