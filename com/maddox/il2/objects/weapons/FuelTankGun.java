// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTankGun.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun, FuelTank

public class FuelTankGun extends com.maddox.il2.objects.weapons.BombGun
{

    public FuelTankGun()
    {
    }

    public void shots(int i)
    {
        if(i != 0)
            super.shots(i);
    }

    public com.maddox.il2.objects.weapons.FuelTank getFuelTank()
    {
        if(!(bomb instanceof com.maddox.il2.objects.weapons.FuelTank))
            return null;
        else
            return (com.maddox.il2.objects.weapons.FuelTank)bomb;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTankGun.class;
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_fueltank");
    }
}
