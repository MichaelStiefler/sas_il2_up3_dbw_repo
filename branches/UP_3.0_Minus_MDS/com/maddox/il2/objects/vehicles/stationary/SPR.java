// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SPR.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            SPRGeneric

public abstract class SPR
{
    public static class SPRUnit extends com.maddox.il2.objects.vehicles.stationary.SPRGeneric
    {

        public SPRUnit()
        {
        }
    }


    public SPR()
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
        new SPRGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.SPR$SPRUnit.class);
    }
}
