// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CW.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            CWGeneric

public abstract class CW
{
    public static class CWUnit extends com.maddox.il2.objects.vehicles.stationary.CWGeneric
    {

        public CWUnit()
        {
        }
    }


    public CW()
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
        new CWGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.CW$CWUnit.class);
    }
}
