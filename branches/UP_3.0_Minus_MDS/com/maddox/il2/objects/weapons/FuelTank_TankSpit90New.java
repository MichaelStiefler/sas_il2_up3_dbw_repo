// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FuelTank_TankSpit90New.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_TankSpit90New extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankSpit90New()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankSpit90New.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Spit_DropTank_90gal/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 295.14F);
    }
}
