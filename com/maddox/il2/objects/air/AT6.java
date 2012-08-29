// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AT6.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SBD, PaintSchemeFMPar03, PaintSchemeFMPar06, TypeStormovik, 
//            NetAircraft, Aircraft

public class AT6 extends com.maddox.il2.objects.air.SBD
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public AT6()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0] != null)
            hierMesh().chunkVisible("Pilon_D0", true);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.AT6.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "AT6");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/AT6(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar03())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/AT6(USA)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1935F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1975.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/T-6Texan.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitAT6.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.1058F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 
            9, 9, 3, 3, 2, 2, 9, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", 
            "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Rockets", new java.lang.String[] {
            null, null, null, null, "Pylon51Late 1", "Pylon51Late 1", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, null, "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", 
            "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Rockets + Droptank", new java.lang.String[] {
            null, null, null, null, "Pylon51Late 1", "Pylon51Late 1", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            null, null, null, null, null, null, "FuelTankGun_TankT6 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", 
            "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gun Pods + Rockets + Bombs", new java.lang.String[] {
            "MGunBrowning303kWF 400", "MGunBrowning303kWF 400", null, null, "Pylon51Late 1", "Pylon51Late 1", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "Pylon30calGUNPOD 1", "Pylon30calGUNPOD 1", "BombGun100Lbs 1", "BombGun100Lbs 1", null, null, null, "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", 
            "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Gun Pods + Rockets + Bombs + Droptank", new java.lang.String[] {
            "MGunBrowning303kWF 400", "MGunBrowning303kWF 400", null, null, "Pylon51Late 1", "Pylon51Late 1", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            "Pylon30calGUNPOD 1", "Pylon30calGUNPOD 1", "BombGun100Lbs 1", "BombGun100Lbs 1", null, null, "FuelTankGun_TankT6 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", 
            "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "Rockets + Flares", new java.lang.String[] {
            null, null, null, null, "Pylon51Late 1", "Pylon51Late 1", null, null, "Pylon51Late 1", "Pylon51Late 1", 
            null, null, "BombGunMk24Flare 1", "BombGunMk24Flare 1", null, null, null, "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", 
            "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1", "RocketGunWPT6 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
