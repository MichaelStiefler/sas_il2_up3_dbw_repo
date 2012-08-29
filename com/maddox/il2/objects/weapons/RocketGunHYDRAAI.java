// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketGunHYDRAAI.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunHYDRAAI extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunHYDRAAI()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunHYDRAAI.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.RocketHYDRA.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocketgun_82");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cassette", 1);
    }
}
