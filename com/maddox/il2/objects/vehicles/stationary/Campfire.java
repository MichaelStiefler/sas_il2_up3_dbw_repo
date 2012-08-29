// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Campfire.java

package com.maddox.il2.objects.vehicles.stationary;


// Referenced classes of package com.maddox.il2.objects.vehicles.stationary:
//            CampfireGeneric

public abstract class Campfire
{
    public static class CampfireAirfield extends com.maddox.il2.objects.vehicles.stationary.CampfireGeneric
    {

        public CampfireAirfield()
        {
        }
    }


    public Campfire()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        new CampfireGeneric.SPAWN(com.maddox.il2.objects.vehicles.stationary.Campfire$CampfireAirfield.class);
    }
}
