// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombGunPuW100M.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun

public class BombGunPuW100M extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunPuW100M()
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
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return class1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunPuW100M.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.BombPuW100M.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 2.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "external", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cassette", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bombgun");
    }
}
