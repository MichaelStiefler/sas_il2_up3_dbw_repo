// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   DZ.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            DZGeneric

public abstract class DZ
{
    public static class DZUnit extends com.maddox.il2.objects.vehicles.stationary.DZGeneric
    {

        public DZUnit()
        {
        }
    }


    public DZ()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        new DZGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.DZ$DZUnit.class);
    }
}
