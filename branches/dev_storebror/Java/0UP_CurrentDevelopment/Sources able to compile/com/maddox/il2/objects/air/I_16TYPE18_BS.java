package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class I_16TYPE18_BS extends I_16
    implements TypeFighter, TypeTNBFighter
{

    public I_16TYPE18_BS()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -55F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -55F * f, 0.0F);
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = I_16TYPE18_BS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type18(Multi1)/hierBS.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ru", "3DO/Plane/I-16type18/hierBS.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ru", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitI_16TYPE18.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type27.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            3, 3, 9, 9, 9, 9, 9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2tank100", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, "FuelTankGun_Tank100i16", "FuelTankGun_Tank100i16"
        });
        Aircraft.weaponsRegister(class1, "6rs82", new java.lang.String[] {
            "MGunShKASsi 650", "MGunShKASsi 650", "MGunShVAKk 120", "MGunShVAKk 120", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
