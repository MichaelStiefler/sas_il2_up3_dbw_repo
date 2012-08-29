// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb500lbsMC.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb500lbsMC extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb500lbsMC()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb500lbsMC.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/500lbsMC/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 170F);
        com.maddox.rts.Property.set(class1, "power", 180F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.3F);
        com.maddox.rts.Property.set(class1, "massa", 312F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
