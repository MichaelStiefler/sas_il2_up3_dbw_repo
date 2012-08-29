// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   OBS.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            OBSGeneric

public abstract class OBS
{
    public static class OBSUnit extends com.maddox.il2.objects.vehicles.stationary.OBSGeneric
    {

        public OBSUnit()
        {
        }
    }


    public OBS()
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
        new OBSGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.OBS$OBSUnit.class);
    }
}
