// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpMk13Brit.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorpMk34 extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpMk34()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpMk34.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Mk34_Torpedo/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 60.8F);
        com.maddox.rts.Property.set(class1, "power", 90.89999F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.569F);
        com.maddox.rts.Property.set(class1, "massa", 522.1F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 8.61F);
        com.maddox.rts.Property.set(class1, "traveltime", 383.2751F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 12F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 46F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 140F);
        com.maddox.rts.Property.set(class1, "armingTime", 3F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 400F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 400F);
    }
}
