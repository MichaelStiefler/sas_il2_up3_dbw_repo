// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb30kgJ.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb30kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb30kgJ()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb30kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/30kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 30F);
        com.maddox.rts.Property.set(class1, "power", 15F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 30F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
