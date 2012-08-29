// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SAR.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SARGeneric

public abstract class SAR
{
    public static class SARUnit extends com.maddox.il2.objects.vehicles.stationary.SARGeneric
    {

        public SARUnit()
        {
        }
    }


    public SAR()
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
        new SARGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.SAR$SARUnit.class);
    }
}
