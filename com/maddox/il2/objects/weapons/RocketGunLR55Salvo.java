// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketGunLR55Salvo.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunLR55Salvo extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunLR55Salvo()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 + 2.81F);
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunLR55Salvo.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (com.maddox.il2.objects.weapons.RocketLR55.class)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocketgun_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cassette", 1);
    }
}
