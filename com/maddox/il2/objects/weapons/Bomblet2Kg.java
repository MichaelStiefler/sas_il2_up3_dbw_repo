// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Bomblet2Kg.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomblet2Kg extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomblet2Kg()
    {
    }

    protected boolean haveSound()
    {
        return index % 16 == 0;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomblet2Kg.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3do/arms/2KgBomblet/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.12F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 2.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "randomOrient", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_cassette");
    }
}
