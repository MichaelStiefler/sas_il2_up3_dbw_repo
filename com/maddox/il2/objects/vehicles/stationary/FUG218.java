// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FUG218.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            FUG218Generic

public abstract class FUG218
{
    public static class FUG218Unit extends com.maddox.il2.objects.vehicles.stationary.FUG218Generic
    {

        public FUG218Unit()
        {
        }
    }


    public FUG218()
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
        new FUG218Generic.SPAWN(com.maddox.il2.objects.vehicles.stationary.FUG218$FUG218Unit.class);
    }
}
