// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AImkXV.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            AImkXVGeneric

public abstract class AImkXV
{
    public static class AImkXVUnit extends com.maddox.il2.objects.vehicles.stationary.AImkXVGeneric
    {

        public AImkXVUnit()
        {
        }
    }


    public AImkXV()
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
        new AImkXVGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.AImkXV$AImkXVUnit.class);
    }
}
