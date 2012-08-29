// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombBlu2Napalm.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombBlu2Napalm extends com.maddox.il2.objects.weapons.Bomb
{

    public BombBlu2Napalm()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombBlu2Napalm.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Blu2/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 77F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 75F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 316F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_std");
    }
}
