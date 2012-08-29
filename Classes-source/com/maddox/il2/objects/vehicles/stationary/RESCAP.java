// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RESCAP.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            RESCAPGeneric

public abstract class RESCAP
{
    public static class RESCAPUnit extends com.maddox.il2.objects.vehicles.stationary.RESCAPGeneric
    {

        public RESCAPUnit()
        {
        }
    }


    public RESCAP()
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
        new RESCAPGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.RESCAP$RESCAPUnit.class);
    }
}
