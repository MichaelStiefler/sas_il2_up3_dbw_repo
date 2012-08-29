// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank_Tank44gal.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_Tank44gal extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Tank44gal()
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
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return var_class;
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.FuelTank_Tank44gal.class;
        com.maddox.rts.Property.set(var_class, "mesh", "3DO/Arms/Tank44gal/mono.sim");
        com.maddox.rts.Property.set(var_class, "kalibr", 0.6F);
        com.maddox.rts.Property.set(var_class, "massa", 158.4F);
    }
}
