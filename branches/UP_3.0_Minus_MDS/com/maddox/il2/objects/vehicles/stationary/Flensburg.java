// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Flensburg.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            FlensburgGeneric

public abstract class Flensburg
{
    public static class FlensburgUnit extends com.maddox.il2.objects.vehicles.stationary.FlensburgGeneric
    {

        public FlensburgUnit()
        {
        }
    }


    public Flensburg()
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
        new FlensburgGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Flensburg$FlensburgUnit.class);
    }
}
