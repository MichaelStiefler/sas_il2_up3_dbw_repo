// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketGunS5.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunS5 extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunS5()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 + 2.81F);
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunS5.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (com.maddox.il2.objects.weapons.RocketS5.class)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocketgun_132");
    }
}
