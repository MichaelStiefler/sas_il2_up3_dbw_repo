// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketGunBat.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketBombGun

public class RocketGunBat extends com.maddox.il2.objects.weapons.RocketBombGun
{

    public RocketGunBat()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = 30F;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunBat.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.RocketBat.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 2.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun");
        com.maddox.rts.Property.set(class1, "dateOfUse", 0x128ca21);
        com.maddox.rts.Property.set(class1, "dateOfUse_F4U1D", 0x128f005);
        com.maddox.rts.Property.set(class1, "dateOfUse_B_24J100", 0x128f005);
    }
}
