// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombGunTi.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun, Bomb

public class BombGunTi extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunTi()
    {
    }

    public void setBombDelay(float f)
    {
        bombDelay = 0.0F;
        if(bomb != null)
            bomb.delayExplosion = bombDelay;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunTi.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.BombTi.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 32);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 8F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cassette", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bombgun_phball");
    }
}
