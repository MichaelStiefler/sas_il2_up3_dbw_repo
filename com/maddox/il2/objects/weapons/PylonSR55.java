// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   PylonSR55.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Pylon

public class PylonSR55 extends com.maddox.il2.objects.weapons.Pylon
{

    public PylonSR55()
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
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return var_class;
    }

    static 
    {
        com.maddox.rts.Property.set(((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.PylonSR55.class)))), "mesh", "3DO/Arms/SR55Pylon/mono.sim");
    }
}
