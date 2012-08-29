// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FuelTankGun_Tank44gal.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTankGun

public class FuelTankGun_Tank44gal extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_Tank44gal()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String string)
    {
        java.lang.Class var_class;
        try
        {
            var_class = java.lang.Class.forName(string);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return var_class;
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTankGun_Tank44gal.class;
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.FuelTank_Tank44gal.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "shotFreq", 0.25F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "external", 1);
    }
}
