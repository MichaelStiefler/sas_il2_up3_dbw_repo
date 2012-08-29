// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTorpMk13_1.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Torpedomk13_1

public class BombTorpMk13_1 extends com.maddox.il2.objects.weapons.Torpedomk13_1
{

    public BombTorpMk13_1()
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
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombTorpMk13_1.class;
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "mesh", "3DO/Arms/Mk13_1/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "radius", 90.8F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "power", 181.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "kalibr", 0.569F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "massa", 874.1F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "velocity", 17.25F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "traveltime", 333.9536F);
        com.maddox.rts.Property.set(((java.lang.Object) (var_class)), "startingspeed", 0.0F);
    }
}
