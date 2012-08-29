// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Aeronautics.java

package com.maddox.il2.objects.vehicles.aeronautics;


// Referenced classes of package com.maddox.il2.objects.vehicles.aeronautics:
//            AeroanchoredGeneric

public abstract class Aeronautics
{
    public static class ObservBalloon_90m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public ObservBalloon_90m()
        {
        }
    }

    public static class ObservBalloon_60m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public ObservBalloon_60m()
        {
        }
    }

    public static class ObservBalloon_30m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public ObservBalloon_30m()
        {
        }
    }

    public static class BarrageBalloon_2400m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public BarrageBalloon_2400m()
        {
        }
    }

    public static class BarrageBalloon_1800m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public BarrageBalloon_1800m()
        {
        }
    }

    public static class BarrageBalloon_1200m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public BarrageBalloon_1200m()
        {
        }
    }

    public static class BarrageBalloon_600m extends com.maddox.il2.objects.vehicles.aeronautics.AeroanchoredGeneric
    {

        public BarrageBalloon_600m()
        {
        }
    }


    public Aeronautics()
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
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$BarrageBalloon_600m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$BarrageBalloon_1200m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$BarrageBalloon_1800m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$BarrageBalloon_2400m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$ObservBalloon_30m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$ObservBalloon_60m.class);
        new AeroanchoredGeneric.SPAWN(com.maddox.il2.objects.vehicles.aeronautics.Aeronautics$ObservBalloon_90m.class);
    }
}
