// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 

package com.maddox.il2.objects.weapons;

import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class FuelTankGun_TankKi44Underwing extends com.maddox.il2.objects.weapons.FuelTankGun
{

    public FuelTankGun_TankKi44Underwing()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.FuelTank_TankKi44Underwing.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "external", 1);
    }
}
