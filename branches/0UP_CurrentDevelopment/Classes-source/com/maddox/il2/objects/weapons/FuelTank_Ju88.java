// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FuelTank_Ju88.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_Ju88 extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Ju88()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Ju88.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/FuelTank_Ju88/mono.sim");
        com.maddox.rts.Property.set(class1, "kalibr", 0.87F);
        com.maddox.rts.Property.set(class1, "massa", 700F);
    }
}