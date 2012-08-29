// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpLTF5PracticeL.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorpLTF5PracticeL extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpLTF5PracticeL()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpLTF5PracticeL.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/Practice/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 0.2F);
        com.maddox.rts.Property.set(class1, "power", 1.0F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 725);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 20.58F);
        com.maddox.rts.Property.set(class1, "traveltime", 98.17F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 18F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 27F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 88F);
        com.maddox.rts.Property.set(class1, "armingTime", 2.0F);
        com.maddox.rts.Property.set(class1, "dropAltitude", 40F);
        com.maddox.rts.Property.set(class1, "dropSpeed", 250F);
        com.maddox.rts.Property.set(class1, "spreadDirection", -1);
    }
}
