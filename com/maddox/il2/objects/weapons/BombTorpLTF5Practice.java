// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTorpLTF5Practice.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedo

public class BombTorpLTF5Practice extends com.maddox.il2.objects.weapons.Torpedo
{

    public BombTorpLTF5Practice()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String string)
    {
        java.lang.Class var_class;
        try
        {
            var_class = java.lang.Class.forName(string);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return var_class;
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombTorpLTF5Practice.class;
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "mesh", "3do/arms/Practice/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "radius", 0.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "power", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "kalibr", 0.45F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "massa", 725);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "velocity", 20.58F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "traveltime", 98.17F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "startingspeed", 0.0F);
    }
}
