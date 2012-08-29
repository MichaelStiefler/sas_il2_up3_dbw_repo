package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class FW_190A7 extends FW_190NEW
    implements TypeFighter, TypeBNZFighter
{

    public FW_190A7()
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

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmL_D0", false);
        if(getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmR_D0", false);
        if(getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmL_D0", false);
        if(getGunByHookName("_CANNON08") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("30mmR_D0", false);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        FW_190A7.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = FW_190A7.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190A-7/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-7sturm.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitFW_190A8.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 2, 2, 
            9, 1, 1, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalBomb01", "_CANNON07", "_CANNON08", "_ExternalDev01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120kh 140", "MGunMG15120kh 140", null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "bidon", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120kh 140", "MGunMG15120kh 140", null, null, null, null, 
            "FuelTankGun_Type_D", null, null, "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "allege", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, null, null, "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "allege+bidon", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            "FuelTankGun_Type_D", null, null, "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "R2", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            null, "MGunMK108kh 55", "MGunMK108kh 55", "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "R2+bidon", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, 
            "FuelTankGun_Type_D", "MGunMK108kh 55", "MGunMK108kh 55", "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "R6", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120kh 140", "MGunMG15120kh 140", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            null, null, null, "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "R6+bidon", new java.lang.String[] {
            "MGunMG131si 475", "MGunMG131si 475", "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15120kh 140", "MGunMG15120kh 140", "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", 
            "FuelTankGun_Type_D", null, null, "PylonETC501FW190"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
