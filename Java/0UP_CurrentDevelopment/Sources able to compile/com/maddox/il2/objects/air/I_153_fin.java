package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_153_fin extends I_153_M62
    implements TypeFighter, TypeTNBFighter
{

    public I_153_fin()
    {
    }

    static 
    {
        java.lang.Class class1 = I_153_fin.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-153");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-153/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-153-M62.fmd");
        I_153_fin.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 
            2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 
            9, 9, 9, 9
        });
        I_153_fin.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08"
        });
        I_153_fin.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303si 600", "MGunBrowning303si 600", "MGunBrowning303si 500", "MGunBrowning303si 520", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
        I_153_fin.weaponsRegister(class1, "4x25kg", new java.lang.String[] {
            "MGunBrowning303si 600", "MGunBrowning303si 600", "MGunBrowning303si 500", "MGunBrowning303si 520", null, null, null, null, null, null, 
            null, null, "BombGun25kg 1", "BombGun25kg 1", "BombGun25kg 1", "BombGun25kg 1", null, null, null, null, 
            null, null, null, null
        });
        I_153_fin.weaponsRegister(class1, "2x25kg_2xsc50", new java.lang.String[] {
            "MGunBrowning303si 600", "MGunBrowning303si 600", "MGunBrowning303si 500", "MGunBrowning303si 520", null, null, null, null, null, null, 
            null, null, "BombGun25kg 1", "BombGun25kg 1", "BombGunSC50 1", "BombGunSC50 1", null, null, null, null, 
            null, null, null, null
        });
        I_153_fin.weaponsRegister(class1, "2xsc50", new java.lang.String[] {
            "MGunBrowning303si 600", "MGunBrowning303si 600", "MGunBrowning303si 500", "MGunBrowning303si 520", null, null, null, null, null, null, 
            null, null, null, null, "BombGunSC50 1", "BombGunSC50 1", null, null, null, null, 
            null, null, null, null
        });
        I_153_fin.weaponsRegister(class1, "4xsc50", new java.lang.String[] {
            "MGunBrowning303si 600", "MGunBrowning303si 600", "MGunBrowning303si 500", "MGunBrowning303si 520", null, null, null, null, null, null, 
            null, null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, null, null, 
            null, null, null, null
        });
        I_153_fin.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
