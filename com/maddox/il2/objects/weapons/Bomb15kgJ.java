// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Bomb15kgJ.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb15kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb15kgJ()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb15kgJ.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/15kgFragJ/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 15F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 7.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.32F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 15F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_mid");
    }
}
