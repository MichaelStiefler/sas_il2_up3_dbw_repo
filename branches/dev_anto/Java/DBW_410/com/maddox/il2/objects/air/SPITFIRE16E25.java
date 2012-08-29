package com.maddox.il2.objects.air;

public class SPITFIRE16E25 extends SPITFIRE16E
{

    public SPITFIRE16E25()
    {
    }
 
    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE16E25.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkXVIe(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/SpitfireMkXVIe(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeSPIT16());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SpitfireXVI25.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSpit16E.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
                0, 0, 1, 1, 1, 1, 9, 9, 9, 3, 
                3, 9, 3, 2, 2
            });
            com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
                "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", 
                "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01", "_ExternalRock01", "_ExternalRock02"
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x500", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, null, null, 
                null, "PylonSpitC", "BombGun500lbsE", null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250_1x500", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1", null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt30", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt45", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250dt90", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x30dt", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit30", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x45dt", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit45", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x90dt", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, "FuelTankGun_TankSpit90", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xRP3", new java.lang.String[] {
                "MGunBrowning50kAPIT 250", "MGunBrowning50kAPIT 250", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, "PylonSpitROCK", "PylonSpitROCK", null, 
                null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xHispano", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x500", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null, null, 
                null, "PylonSpitC", "BombGun500lbsE", null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250_1x500", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1", null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt30", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt45", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2x250dt90", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", 
                "BombGun250lbsE 1", null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x30dt", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x45dt", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H1x90dt", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90", null, null, null, 
                null, null, null, null, null
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "H2xRP3", new java.lang.String[] {
                null, null, "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitROCK", "PylonSpitROCK", null, 
                null, null, null, "RocketGunHVAR5BEAU 1", "RocketGunHVAR5BEAU 1"
            });
            com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
                null, null, null, null, null, null, null, null, null, null, 
                null, null, null, null, null
            });
        }
    }