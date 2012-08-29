// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FW_190D15.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar05, NetAircraft, Aircraft

public class FW_190D15 extends com.maddox.il2.objects.air.FW_190
{

    public FW_190D15()
    {
        kangle = 0.0F;
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
        com.maddox.il2.objects.air.FW_190D15.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        for(int i = 1; i < 15; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10F * kangle, 0.0F);

        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
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

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.FW_190D15.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "FW190");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Fw-190D-15(Beta)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944.6F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Fw-190D-15.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitFW_190D15.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.764106F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 9, 9, 1, 1, 9, 9, 1, 
            1, 1, 1, 9, 9, 1, 1, 9, 9, 1, 
            1, 9, 9, 2, 2, 9, 9, 1, 1, 9, 
            9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalDev01", "_ExternalDev02", "_CANNON03", "_CANNON04", "_ExternalDev03", "_ExternalDev04", "_CANNON05", 
            "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev05", "_ExternalDev06", "_CANNON09", "_CANNON10", "_ExternalDev07", "_ExternalDev08", "_CANNON11", 
            "_CANNON12", "_ExternalDev09", "_ExternalDev10", "_ExternalRock01", "_ExternalRock02", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", "_CANNON13", 
            "_CANNON14"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-2xMK108+DT", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonETC504FW190 1", "FuelTankGun_Type_D 1", "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-2xMG151-20", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal 1", "PylonMG15120Internal 1", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-2xMG151-20+DT", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, "PylonMG15120Internal 1", "PylonMG15120Internal 1", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonETC504FW190 1", "FuelTankGun_Type_D 1", "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-4xMG151-20", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, "PylonMG15120x2 1", "PylonMG15120x2 1", "MGunMG15120MGkh 125", 
            "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-4xMG151-20+DT", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, "PylonMG15120x2 1", "PylonMG15120x2 1", "MGunMG15120MGkh 125", 
            "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonETC504FW190 1", "FuelTankGun_Type_D 1", "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3_2xMK103", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMk103 1", "PylonMk103 1", "MGunMK103kh 35", 
            "MGunMK103kh 35", null, null, null, null, null, null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3_2xMK103+DT", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMk103 1", "PylonMk103 1", "MGunMK103kh 35", 
            "MGunMK103kh 35", null, null, null, null, "PylonETC504FW190 1", "FuelTankGun_Type_D 1", "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6_2xWfrGr21", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonRO_WfrGr21 1", "PylonRO_WfrGr21 1", "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", null, null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R6_2xWfrGr21+DT", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonRO_WfrGr21 1", "PylonRO_WfrGr21 1", "RocketGunWfrGr21 1", "RocketGunWfrGr21 1", "PylonETC504FW190 1", "FuelTankGun_Type_D 1", "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1-2xMG151-20+1xSC500", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", "PylonMG15120Internal 1", "PylonMG15120Internal 1", "MGunMG15120MGkh 125", "MGunMG15120MGkh 125", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonETC504FW190 1", null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2-2xMK108+1xSC500", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "PylonETC504FW190 1", null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3-2xMK103+1xSC500", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "BombGunSC500 1", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, "PylonMk103 1", "PylonMk103 1", "MGunMK103kh 35", 
            "MGunMK103kh 35", null, null, null, null, "PylonETC504FW190 1", null, "PylonMK108Internal 1", "PylonMK108Internal 1", "MGunMK108kh 55", 
            "MGunMK108kh 55"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
