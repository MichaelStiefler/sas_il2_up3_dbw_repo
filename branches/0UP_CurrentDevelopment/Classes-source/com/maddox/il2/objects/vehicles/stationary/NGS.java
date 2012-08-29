// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NGS.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            NGSGeneric

public abstract class NGS
{
    public static class NGSUnit extends com.maddox.il2.objects.vehicles.stationary.NGSGeneric
    {

        public NGSUnit()
        {
        }
    }


    public NGS()
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
        new NGSGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.NGS$NGSUnit.class);
    }
}
