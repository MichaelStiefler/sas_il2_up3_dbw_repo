// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AImkVIII.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            AImkVIIIGeneric

public abstract class AImkVIII
{
    public static class AImkVIIIUnit extends com.maddox.il2.objects.vehicles.stationary.AImkVIIIGeneric
    {

        public AImkVIIIUnit()
        {
        }
    }


    public AImkVIII()
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
        new AImkVIIIGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.AImkVIII$AImkVIIIUnit.class);
    }
}
