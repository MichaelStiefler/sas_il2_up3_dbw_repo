// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   F4U1D.java

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            F4U, PaintSchemeFMPar05, PaintSchemeFMPar06, TypeX4Carrier, 
//            NetAircraft

public class F4U1D extends com.maddox.il2.objects.air.F4U
    implements com.maddox.il2.objects.air.TypeX4Carrier
{

    public F4U1D()
    {
        bToFire = false;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 0.002F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -0.002F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 0.002F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -0.002F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
    }

    protected void mydebug(java.lang.String s)
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public boolean bToFire;
    private float deltaAzimuth;
    private float deltaTangage;
    public static boolean bChangedPit = false;
    public float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.F4U1D.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "F4U");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/F4U-1D(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/F4U-1D(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_rz", "3DO/Plane/F4U-1D(RZ)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_rz", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/F4U-1D.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitF4U1D.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0585F);
        com.maddox.il2.objects.air.F4U1D.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 3, 9, 9, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.F4U1D.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", 
            "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "8xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, null, null, 
            null, null, null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "8xhvargp2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "8xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, null, null, 
            null, null, null, null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "8xhvarap2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x154dt8xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x154dt8xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt8xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", null, null, 
            null, null, null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x154", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x1548xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x178dt2x1548xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun154Napalm 1", "BombGun154Napalm 1", null, null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2xtt1x178dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, "FuelTankGun_Tank178gal", "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, null, null, "RocketGunTinyTim", "RocketGunTinyTim", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2xtt8xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, null, null, "RocketGunTinyTim", "RocketGunTinyTim", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2xtt8xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, null, null, null, "RocketGunTinyTim", "RocketGunTinyTim", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x5002x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x5008xhvargp2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x5008xhvarap2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun500lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x5008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x5008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x500", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x5008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x5008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002xtt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun1000lbs 1", null, null, "RocketGunTinyTim", "RocketGunTinyTim", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10008xhvargp2x154dt", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002x154dt8xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            null, null, "BombGun1000lbs 1", "FuelTankGun_Tank154gal", "FuelTankGun_Tank154gal", null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002x5008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x10002x5008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun500lbs 1", "BombGun500lbs 1", "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x10008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "2x10008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", null, null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x1000", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x10008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "3x10008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x2000", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "BombGun2000lbs 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x20008xhvargp", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "BombGun2000lbs 1", null, null, null, null, "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", 
            "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5", "RocketGunHVAR5"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x20008xhvarap", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "BombGun2000lbs 1", null, null, null, null, "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", 
            "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP", "RocketGunHVAR5AP"
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1x20002x1000", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, "PylonF4UPLN3", "PylonF4UPLN3", 
            "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun2000lbs 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "1xBat", new java.lang.String[] {
            "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 400", "MGunBrowning50kWF 375", "MGunBrowning50kWF 375", "PylonF4UPLN2", null, null, null, 
            null, null, "RocketGunBat 1", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.F4U1D.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
