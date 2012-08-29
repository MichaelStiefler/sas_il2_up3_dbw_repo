// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombSpezzone.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombSpezzone extends com.maddox.il2.objects.weapons.Bomb
{

    public BombSpezzone()
    {
    }

    protected boolean haveSound()
    {
        return index % 16 == 0;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombSpezzone.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/ao-10/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 5F);
        com.maddox.rts.Property.set(class1, "power", 0.18F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.1F);
        com.maddox.rts.Property.set(class1, "massa", 2.0F);
        com.maddox.rts.Property.set(class1, "randomOrient", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_cassette");
    }
}
