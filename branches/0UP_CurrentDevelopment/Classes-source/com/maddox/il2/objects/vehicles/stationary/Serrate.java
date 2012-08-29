// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Serrate.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SerrateGeneric

public abstract class Serrate
{
    public static class SerrateUnit extends com.maddox.il2.objects.vehicles.stationary.SerrateGeneric
    {

        public SerrateUnit()
        {
        }
    }


    public Serrate()
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
        new SerrateGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Serrate$SerrateUnit.class);
    }
}
