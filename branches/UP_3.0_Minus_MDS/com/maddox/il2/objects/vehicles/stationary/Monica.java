// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Monica.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            MonicaGeneric

public abstract class Monica
{
    public static class MonicaUnit extends com.maddox.il2.objects.vehicles.stationary.MonicaGeneric
    {

        public MonicaUnit()
        {
        }
    }


    public Monica()
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
        new MonicaGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Monica$MonicaUnit.class);
    }
}
