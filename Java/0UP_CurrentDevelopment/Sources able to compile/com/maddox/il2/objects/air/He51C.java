package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class He51C extends CR_42
{

    public He51C()
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
    }

    static 
    {
        java.lang.Class class1 = He51C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He51C");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/He51C/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/He51C/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He51C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitHe51C.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.742F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3, 3, 3, 3, 9, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev01", "_ExternalBomb07"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, null, null, null, null, "FuelTankGun_Tank50l 1", null
        });
        Aircraft.weaponsRegister(class1, "2MG17s + 4x50Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "FuelTankGun_Tank50l 1", null
        });
        Aircraft.weaponsRegister(class1, "2MG17s + 6x10Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "FuelTankGun_Tank50l 1", null
        });
        Aircraft.weaponsRegister(class1, "2MG17s + Fuel Tank + 6x10Kg HE Bombs", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "BombGun10kgCZ 1", "FuelTankGun_Tank50l 1", null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
