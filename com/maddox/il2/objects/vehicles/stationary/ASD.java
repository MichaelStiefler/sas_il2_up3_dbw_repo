// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   ASD.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            ASDGeneric

public abstract class ASD
{
    public static class ASDUnit extends com.maddox.il2.objects.vehicles.stationary.ASDGeneric
    {

        public ASDUnit()
        {
        }
    }


    public ASD()
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
        new ASDGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.ASD$ASDUnit.class);
    }
}
