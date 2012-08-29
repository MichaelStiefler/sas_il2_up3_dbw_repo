// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RadioOperator.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            RadioOperatorGeneric

public abstract class RadioOperator
{
    public static class RadioOperatorUnit extends com.maddox.il2.objects.vehicles.stationary.RadioOperatorGeneric
    {

        public RadioOperatorUnit()
        {
        }
    }


    public RadioOperator()
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
        new RadioOperatorGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.RadioOperator$RadioOperatorUnit.class);
    }
}
