// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FuelTank_Dag.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            FuelTank

public class FuelTank_Dag extends com.maddox.il2.objects.weapons.FuelTank
{

    public FuelTank_Dag()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.FuelTank_Dag.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3do/arms/ETC-900Dag/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 230F);
    }
}
