// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class Bomb60kgJ2 extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb60kgJ2()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb60kgJ2.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/60kgBombJ2/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 60F);
        com.maddox.rts.Property.set(class1, "power", 30F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 60F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
