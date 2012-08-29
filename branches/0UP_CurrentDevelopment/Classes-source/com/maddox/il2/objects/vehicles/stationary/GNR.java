// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GNR.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            GNRGeneric

public abstract class GNR
{
    public static class GNRUnit extends com.maddox.il2.objects.vehicles.stationary.GNRGeneric
    {

        public GNRUnit()
        {
        }
    }


    public GNR()
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
        new GNRGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.GNR$GNRUnit.class);
    }
}
