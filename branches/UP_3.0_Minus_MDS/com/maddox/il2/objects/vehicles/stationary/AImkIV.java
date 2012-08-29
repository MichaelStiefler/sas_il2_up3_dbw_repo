// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AImkIV.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            AImkIVGeneric

public abstract class AImkIV
{
    public static class AImkIVUnit extends com.maddox.il2.objects.vehicles.stationary.AImkIVGeneric
    {

        public AImkIVUnit()
        {
        }
    }


    public AImkIV()
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
        new AImkIVGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.AImkIV$AImkIVUnit.class);
    }
}
