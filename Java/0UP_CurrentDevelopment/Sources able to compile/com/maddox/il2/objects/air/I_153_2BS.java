package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class I_153_2BS extends I_153_M62
    implements TypeFighter, TypeTNBFighter
{

    public I_153_2BS()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            I_153_M62.bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            I_153_M62.bChangedPit = true;
    }

    static 
    {
        java.lang.Class class1 = I_153_2BS.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-153");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-153/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitI_153.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-153-M62.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xAO10", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xAO10_2xFAB50", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4xFAB50", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, null, null, "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB50_2xFAB100", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, null, null, null, null, null, null, 
            null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB100", "BombGunFAB100", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "8xRS82", new java.lang.String[] {
            "MGunUBsi 165", "MGunUBsi 165", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", 
            "RocketGunRS82", "RocketGunRS82", null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", 
            "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
