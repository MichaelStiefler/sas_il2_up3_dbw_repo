// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SN2.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SN2Generic

public abstract class SN2
{
    public static class SN2Unit extends com.maddox.il2.objects.vehicles.stationary.SN2Generic
    {

        public SN2Unit()
        {
        }
    }


    public SN2()
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
        new SN2Generic.SPAWN(com.maddox.il2.objects.vehicles.stationary.SN2$SN2Unit.class);
    }
}