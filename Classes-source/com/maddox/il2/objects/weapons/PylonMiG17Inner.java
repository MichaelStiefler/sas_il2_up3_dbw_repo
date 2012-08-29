// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PylonMiG17Inner.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Pylon

public class PylonMiG17Inner extends com.maddox.il2.objects.weapons.Pylon
{

    public PylonMiG17Inner()
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
        java.lang.Class localClass = com.maddox.il2.objects.weapons.PylonMiG17Inner.class;
        com.maddox.rts.Property.set(localClass, "mesh", "3DO/Arms/MiG17_InnerPylon/mono.sim");
    }
}
