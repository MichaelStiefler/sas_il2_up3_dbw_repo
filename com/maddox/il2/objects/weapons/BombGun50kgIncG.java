// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombGun50kgIncG.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun

public class BombGun50kgIncG extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGun50kgIncG()
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
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGun50kgIncG.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.Bomb50kgIncG.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "external", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bombgun");
    }
}
