// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Beacon.java

package com.maddox.il2.objects.vehicles.radios;


// Referenced classes of package com.maddox.il2.objects.vehicles.radios:
//            BeaconGeneric, TypeHasBeacon, TypeHasRadioStation, RotatingYGBeacon, 
//            TypeHasYGBeacon, TypeHasAAFIAS, LorenzBlindLandingBeacon, TypeHasLorenzBlindLanding, 
//            TypeHasMeacon

public abstract class Beacon
{
    public static class Grossdeutscher_Rundfunk extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Grossdeutscher Rundfunk";
        }

        public Grossdeutscher_Rundfunk()
        {
        }
    }

    public static class Radio_Roma extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Radio Roma";
        }

        public Radio_Roma()
        {
        }
    }

    public static class Magyar_Radio extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Magyar Radio";
        }

        public Magyar_Radio()
        {
        }
    }

    public static class Radio_Moscow extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Radio Moscow";
        }

        public Radio_Moscow()
        {
        }
    }

    public static class Radio_BBC extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "BBC";
        }

        public Radio_BBC()
        {
        }
    }

    public static class Radio_Suomen_Yleisradio extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Suomen Yleisradio";
        }

        public Radio_Suomen_Yleisradio()
        {
        }
    }

    public static class Radio_Honolulu extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation
    {

        public java.lang.String getStationID()
        {
            return "Radio Honolulu";
        }

        public Radio_Honolulu()
        {
        }
    }

    public static class YGBeacon extends com.maddox.il2.objects.vehicles.radios.RotatingYGBeacon
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasYGBeacon
    {

        public YGBeacon()
        {
        }
    }

    public static class LorenzBLBeacon_AAIAS extends com.maddox.il2.objects.vehicles.radios.LorenzBLBeacon
        implements com.maddox.il2.objects.vehicles.radios.TypeHasAAFIAS
    {

        public LorenzBLBeacon_AAIAS()
        {
        }
    }

    public static class LorenzBLBeacon_LongRunway extends com.maddox.il2.objects.vehicles.radios.LorenzBLBeacon
    {

        public LorenzBLBeacon_LongRunway()
        {
        }
    }

    public static class LorenzBLBeacon extends com.maddox.il2.objects.vehicles.radios.LorenzBlindLandingBeacon
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon, com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding
    {

        public LorenzBLBeacon()
        {
        }
    }

    public static class Meacon extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasMeacon
    {

        public Meacon()
        {
        }
    }

    public static class RadioBeaconLowVis extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon
    {

        public RadioBeaconLowVis()
        {
        }
    }

    public static class RadioBeacon extends com.maddox.il2.objects.vehicles.radios.BeaconGeneric
        implements com.maddox.il2.objects.vehicles.radios.TypeHasBeacon
    {

        public RadioBeacon()
        {
        }
    }


    public Beacon()
    {
    }

    public static java.lang.String getBeaconID(int i)
    {
        if(i == -1)
            return "";
        if(i < beaconIDStrings.length)
            return beaconIDStrings[i];
        else
            return "XY";
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final java.lang.String beaconIDStrings[] = {
        "AM", "AR", "BD", "BO", "CA", "CM", "CT", "FC", "GR", "GT", 
        "HK", "JU", "LA", "LH", "MA", "MG", "MK", "MW", "OM", "PS", 
        "RG", "RU", "ST", "SQ", "TA", "VI", "WI", "WP", "YO", "ZB", 
        "ZO", "ZU"
    };

    static 
    {
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$RadioBeacon.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$RadioBeaconLowVis.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Meacon.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_LongRunway.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$LorenzBLBeacon_AAIAS.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$YGBeacon.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Radio_Honolulu.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Radio_Suomen_Yleisradio.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Radio_BBC.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Radio_Moscow.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Magyar_Radio.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Radio_Roma.class);
        new BeaconGeneric.SPAWN(com.maddox.il2.objects.vehicles.radios.Beacon$Grossdeutscher_Rundfunk.class);
    }
}
