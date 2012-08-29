// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   FUG.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            FUGGeneric

public abstract class FUG
{
    public static class FUGUnit extends com.maddox.il2.objects.vehicles.stationary.FUGGeneric
    {

        public FUGUnit()
        {
        }
    }


    public FUG()
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
        new FUGGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.FUG$FUGUnit.class);
    }
}
