// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bomb600kgJ.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class Bomb600kgJ extends com.maddox.il2.objects.weapons.Bomb
{

    public Bomb600kgJ()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.Bomb600kgJ.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/600kgBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 100F);
        com.maddox.rts.Property.set(class1, "power", 300F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 600F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
