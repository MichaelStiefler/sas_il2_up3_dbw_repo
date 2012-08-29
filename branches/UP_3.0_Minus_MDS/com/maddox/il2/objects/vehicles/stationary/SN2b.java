// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SN2b.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SN2bGeneric

public abstract class SN2b
{
    public static class SN2bUnit extends com.maddox.il2.objects.vehicles.stationary.SN2bGeneric
    {

        public SN2bUnit()
        {
        }
    }


    public SN2b()
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
        new SN2bGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.SN2b$SN2bUnit.class);
    }
}
