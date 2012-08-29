// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombMk82.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombMk82 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk82()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk82.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Mk82/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 125F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.32F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 226F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_mid");
    }
}
