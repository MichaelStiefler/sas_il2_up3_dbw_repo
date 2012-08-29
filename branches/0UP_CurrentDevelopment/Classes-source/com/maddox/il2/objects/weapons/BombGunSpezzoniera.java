// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombGunSpezzoniera.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun

public class BombGunSpezzoniera extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGunSpezzoniera()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGunSpezzoniera.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.Bomblet2Kg.class);
        com.maddox.rts.Property.set(class1, "bullets", 22);
        com.maddox.rts.Property.set(class1, "shotFreq", 20F);
        com.maddox.rts.Property.set(class1, "external", 0);
        com.maddox.rts.Property.set(class1, "cassette", 1);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_AO10");
    }
}
