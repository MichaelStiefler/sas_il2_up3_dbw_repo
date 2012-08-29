// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTorpMk34.java

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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpMk34.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Mk34_Torpedo/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 90.8F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 160F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.569F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 874.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "velocity", 17F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "traveltime", 150F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "startingspeed", 0.0F);
    }
}
