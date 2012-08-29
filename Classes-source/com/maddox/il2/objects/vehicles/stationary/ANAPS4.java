// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ANAPS4.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            ANAPS4Generic

public abstract class ANAPS4
{
    public static class ANAPS4Unit extends com.maddox.il2.objects.vehicles.stationary.ANAPS4Generic
    {

        public ANAPS4Unit()
        {
        }
    }


    public ANAPS4()
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
        new ANAPS4Generic.SPAWN(com.maddox.il2.objects.vehicles.stationary.ANAPS4$ANAPS4Unit.class);
    }
}
