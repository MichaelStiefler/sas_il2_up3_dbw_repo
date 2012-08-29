// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.World;
import com.maddox.rts.Property;

public class RocketGunType3Mk27_2 extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunType3Mk27_2()
    {
    }

    public void setRocketTimeLife(float f)
    {
        f = java.lang.Math.max(com.maddox.il2.ai.World.cur().userBombDelay, 0.0F) + 1.0F;
        if(f > 10F)
            f = 10F;
        timeLife = f;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunType3Mk27_2.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketType3Mk27_2.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
