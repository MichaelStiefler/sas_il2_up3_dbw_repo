// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Pylon410MG213.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Pylon

public class Pylon410MG213 extends com.maddox.il2.objects.weapons.Pylon
{

    public Pylon410MG213()
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
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.Pylon410MG213.class)))), "mesh", "3do/arms/Hs-129-MK101/410D.sim");
    }
}
