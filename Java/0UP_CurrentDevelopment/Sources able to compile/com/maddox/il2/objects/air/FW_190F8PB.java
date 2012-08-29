package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.rts.Property;

public class FW_190F8PB extends FW_190F
    implements TypeStormovik
{

    public FW_190F8PB()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.M.massEmpty -= 24F;
        hierMesh().chunkVisible("Flap01_D0", true);
        hierMesh().chunkVisible("Flap01Holed_D0", false);
        hierMesh().chunkVisible("Flap04_D0", true);
        hierMesh().chunkVisible("Flap04Holed_D0", false);
    }

    static 
    {
        java.lang.Class class1 = FW_190F8PB.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Fw-190A-8(Beta)/hier_G8.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-8.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitFW_190F8.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb01", "_ExternalBomb04", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb05", 
            "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalDev11", "_ExternalDev12", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", 
            "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", 
            "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_ExternalRock25", "_ExternalRock26", 
            "_ExternalRock27", "_ExternalRock28", "_ExternalRock29", "_ExternalRock30", "_ExternalRock31", "_ExternalRock32", "_ExternalRock33", "_ExternalRock34", "_ExternalDev19", "_ExternalDev20"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "PB1", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", 
            "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB1_4sc50", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", 
            "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB1_1sc250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC250 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", 
            "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB1_1sc500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", 
            "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", "RocketGunPB1 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB2", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", 
            "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB2_4sc50", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunNull 1", "BombGunSC50 1", "BombGunNull 1", "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", 
            "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB2_1sc250", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC250 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", 
            "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", null, null
        });
        Aircraft.weaponsRegister(class1, "PB2_1sc500", new java.lang.String[] {
            "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", "BombGunNull 1", null, null, null, null, 
            null, null, null, null, "PylonPB2L 1", "PylonPB2R 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", 
            "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", "RocketGunPB2 1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
