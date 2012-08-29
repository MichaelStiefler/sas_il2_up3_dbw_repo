// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Siren.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SirenGeneric

public abstract class Siren
{
    public static class SirenCity extends com.maddox.il2.objects.vehicles.stationary.SirenGeneric
    {

        public SirenCity()
        {
        }
    }


    public Siren()
    {
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
        new SirenGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Siren$SirenCity.class);
    }
}
