// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombMk53Charge.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombMk53Charge extends com.maddox.il2.objects.weapons.Bomb
{

    public BombMk53Charge()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombMk53Charge.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Mk53_Charge/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 90F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 90F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.32F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 148F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_mid");
    }
}
