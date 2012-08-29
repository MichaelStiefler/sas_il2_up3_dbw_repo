// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CockpitHS_129B2.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitHS_129

public class CockpitHS_129B2 extends com.maddox.il2.objects.air.CockpitHS_129
{

    public CockpitHS_129B2()
    {
        super("3DO/Cockpit/Hs-129/hier.him");
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
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHS_129B2.class, "normZN", 0.6F);
        com.maddox.rts.Property.set(com.maddox.il2.objects.air.CockpitHS_129B2.class, "gsZN", 0.6F);
    }
}
