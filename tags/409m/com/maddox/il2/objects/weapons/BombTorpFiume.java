// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpFiume.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            TorpedoLtfFiume

public class BombTorpFiume extends com.maddox.il2.objects.weapons.TorpedoLtfFiume
{

    public BombTorpFiume()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpFiume.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/fiume/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 110F);
        com.maddox.rts.Property.set(class1, "power", 214F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 905F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 21.6F);
        com.maddox.rts.Property.set(class1, "traveltime", 138.8888F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
    }
}
