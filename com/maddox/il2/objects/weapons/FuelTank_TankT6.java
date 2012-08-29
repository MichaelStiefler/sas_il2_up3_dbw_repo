// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FuelTank_TankT6.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_TankT6 extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_TankT6()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_TankT6.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/F4F_Droptank/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 64.5F);
    }
}
