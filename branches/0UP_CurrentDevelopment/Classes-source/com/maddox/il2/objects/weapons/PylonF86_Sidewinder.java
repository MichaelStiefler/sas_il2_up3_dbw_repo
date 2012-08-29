// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PylonF86_Sidewinder.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            PylonRO_82_1

public class PylonF86_Sidewinder extends com.maddox.il2.objects.weapons.PylonRO_82_1
{

    public PylonF86_Sidewinder()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class localClass = com.maddox.il2.objects.weapons.PylonF86_Sidewinder.class;
        com.maddox.rts.Property.set(localClass, "mesh", "3DO/Arms/F86_Sidewinder/mono.sim");
    }
}
