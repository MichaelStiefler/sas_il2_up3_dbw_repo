// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FAC.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            FACGeneric

public abstract class FAC
{
    public static class FACUnit extends com.maddox.il2.objects.vehicles.stationary.FACGeneric
    {

        public FACUnit()
        {
        }
    }


    public FAC()
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
        new FACGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.FAC$FACUnit.class);
    }
}
