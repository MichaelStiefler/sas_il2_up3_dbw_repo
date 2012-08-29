// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketGunR4Ms.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunR4Ms extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunR4Ms()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 + 2.81F);
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunR4Ms.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketR4M.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 4F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
