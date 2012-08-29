// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Bomb1000lbs_M114.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb1000lbs_M114 extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb1000lbs_M114()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb1000lbs_M114.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/1000lbs_M114/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 250F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 275F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 505F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bomb_big");
    }
}
