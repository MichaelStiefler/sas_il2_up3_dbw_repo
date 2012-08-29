// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSD2A.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSD2A extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSD2A()
    {
    }

    protected boolean haveSound()
    {
        return index % 10 == 0;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSD2A.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/SD-2A/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 25F);
        com.maddox.rts.Property.set(class1, "power", 1.01F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.0775F);
        com.maddox.rts.Property.set(class1, "massa", 2.0284F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_cassette");
    }
}
