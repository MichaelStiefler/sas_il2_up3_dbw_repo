// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   NGS1.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            NGS1Generic

public abstract class NGS1
{
    public static class NGS1Unit extends com.maddox.il2.objects.vehicles.stationary.NGS1Generic
    {

        public NGS1Unit()
        {
        }
    }


    public NGS1()
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
        new NGS1Generic.SPAWN(com.maddox.il2.objects.vehicles.stationary.NGS1$NGS1Unit.class);
    }
}
