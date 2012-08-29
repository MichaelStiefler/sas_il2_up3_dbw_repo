// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombAmpoule.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombAmpoule extends com.maddox.il2.objects.weapons.Bomb
{

    public BombAmpoule()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombAmpoule.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Ampoule/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 0.5F);
        com.maddox.rts.Property.set(class1, "power", 0.5F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.171F);
        com.maddox.rts.Property.set(class1, "massa", 1.2F);
        com.maddox.rts.Property.set(class1, "randomOrient", 1);
    }
}
