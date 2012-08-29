// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorp650.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorp650 extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorp650()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorp650.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Torpedo650Kg/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 80F);
        com.maddox.rts.Property.set(class1, "power", 140.5F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.35F);
        com.maddox.rts.Property.set(class1, "massa", 650F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 21D);
        com.maddox.rts.Property.set(class1, "traveltime", 97.1817F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 22F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 33F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 140F);
        com.maddox.rts.Property.set(class1, "armingTime", 3.5F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 100F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 320F);
    }
}
