// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.World;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunType3Mk27_2.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.RocketType3Mk27_2.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 2.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocketgun_132");
    }
}
