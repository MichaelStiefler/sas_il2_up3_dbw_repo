// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTorpF5Bheavy.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            TorpedoLtfFiume

public class BombTorpF5Bheavy extends com.maddox.il2.objects.weapons.TorpedoLtfFiume
{

    public BombTorpF5Bheavy()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpF5Bheavy.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/LTF5B/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 120F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 267.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.45F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 812F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "velocity", 20.58F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "traveltime", 97.1817F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "startingspeed", 0.0F);
    }
}
