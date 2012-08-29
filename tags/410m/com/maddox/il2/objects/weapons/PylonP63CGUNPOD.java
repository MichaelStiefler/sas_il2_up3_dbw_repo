// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PylonP63CGUNPOD.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Pylon

public class PylonP63CGUNPOD extends com.maddox.il2.objects.weapons.Pylon
{

    public PylonP63CGUNPOD()
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
        com.maddox.rts.Property.set(com.maddox.il2.objects.weapons.PylonP63CGUNPOD.class, "mesh", "3DO/Arms/P-63C_gunpod/mono.sim");
    }
}
