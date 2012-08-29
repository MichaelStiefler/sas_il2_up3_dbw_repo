// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190A6.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar01, NetAircraft

public class FW_190A6 extends com.maddox.il2.objects.air.FW_190
{

    public FW_190A6()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        {
            hierMesh().chunkVisible("7mmC_D0", false);
            hierMesh().chunkVisible("7mmCowl_D0", true);
        }
        if(getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmL_D0", false);
        if(getGunByHookName("_CANNON04") instanceof com.maddox.il2.objects.weapons.GunEmpty)
            hierMesh().chunkVisible("20mmR_D0", false);
        if(!(getGunByHookName("_ExternalDev05") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("Flap01_D0", false);
            hierMesh().chunkVisible("Flap01Holed_D0", true);
        }
        if(!(getGunByHookName("_ExternalDev06") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            hierMesh().chunkVisible("Flap04_D0", false);
            hierMesh().chunkVisible("Flap04Holed_D0", true);
        }
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
        com.maddox.il2.objects.air.FW_190A6.moveGear(hierMesh(), f);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190A6.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190A-6(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1943.1F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190A-5.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitFW_190A5.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.FW_190A6.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 9, 9, 
            9, 9, 2, 2, 9, 9, 3, 3, 3, 3, 
            9, 9, 3
        });
        com.maddox.il2.objects.air.FW_190A6.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalRock01", "_ExternalRock02", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u22tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u21sc2502tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, "BombGunSC250"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u3", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31sc250", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, 
            "PylonETC501FW190", null, "BombGunSC250"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31ab250", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, 
            "PylonETC501FW190", null, "BombGunAB250"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31sc500", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, 
            "PylonETC501FW190", null, "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31ab500", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", null, null, null, null, 
            "PylonETC501FW190", null, "BombGunAB500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31sc2504sc50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "PylonETC501FW190", null, "BombGunSC250"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u31ab2504sc50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "PylonETC501FW190", null, "BombGunAB250"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u82tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u81sc5002tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u81ab5002tank", new java.lang.String[] {
            null, null, "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, "PylonETC250", "PylonETC250", "FuelTankGun_Type_D", "FuelTankGun_Type_D", 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, "BombGunAB500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "u171sc5004sc50", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", null, null, null, null, null, null, 
            null, null, null, null, "PylonETC71", "PylonETC71", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", 
            "PylonETC501FW190", null, "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "r11tank", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", "FuelTankGun_Type_D", null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "r11sc500", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, "BombGunSC500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "r11ab500", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "PylonETC501FW190", null, "BombGunAB500"
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "r6wfrgr21", new java.lang.String[] {
            "MGunMG17si 1000", "MGunMG17si 1000", "MGunMG15120MGs  250", "MGunMG15120MGs  250", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, 
            "PylonRO_WfrGr21", "PylonRO_WfrGr21", "RocketGunWfrGr21", "RocketGunWfrGr21", null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.FW_190A6.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
