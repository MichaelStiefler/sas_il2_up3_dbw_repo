package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class F4F_FM2 extends F4F
{

    public F4F_FM2()
    {
    }

    static 
    {
        java.lang.Class class1 = F4F_FM2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FM2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/FM-2(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar03());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/FM-2(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/FM-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitF4F4.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.28265F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 9, 9, 9, 9, 
            3, 3, 9, 9, 2, 2, 9, 9, 2, 2, 
            2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2x58dt", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1 1", "PylonF4FPLN1 1", "FuelTankGun_TankF4F 1", "FuelTankGun_TankF4F 1", null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "BombGun100Lbs 1", "BombGun100Lbs 1", null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2 x M-31 250lb Bombs", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2x1002x58dt", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1 1", "PylonF4FPLN1 1", "FuelTankGun_TankF4F 1", "FuelTankGun_TankF4F 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2 x 250lb Bombs + DT's", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1 1", "PylonF4FPLN1 1", "FuelTankGun_TankF4F 1", "FuelTankGun_TankF4F 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "6 x HVAR5 Rockets", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            null
        });
        Aircraft.weaponsRegister(class1, "6 x HVAR5 AP + DT's", new java.lang.String[] {
            "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "MGunBrowning50kWF 430", "PylonF4FPLN1 1", "PylonF4FPLN1 1", "FuelTankGun_TankF4F 1", "FuelTankGun_TankF4F 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5AP 1", "RocketGunHVAR5AP 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5AP 1", "RocketGunHVAR5AP 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5AP 1", "RocketGunHVAR5AP 1", 
            null
        });
        Aircraft.weaponsRegister(class1, "APIT Ammo + 6 x HVAR + DT's", new java.lang.String[] {
            "MGunBrowning50APIT 430", "MGunBrowning50APIT 430", "MGunBrowning50APIT 430", "MGunBrowning50APIT 430", "PylonF4FPLN1 1", "PylonF4FPLN1 1", "FuelTankGun_TankF4F 1", "FuelTankGun_TankF4F 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", 
            "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "RocketGunHVAR5 1", "RocketGunHVAR5 1", 
            null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
