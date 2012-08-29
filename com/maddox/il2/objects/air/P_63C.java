// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_63C.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_39, PaintSchemeFMPar05, NetAircraft

public class P_63C extends com.maddox.il2.objects.air.P_39
{

    public P_63C()
    {
        fSteer = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC6_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(f, 0.01F, 0.12F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearC8_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(f, 0.01F, 0.12F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, -90F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.P_63C.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2405F);
        ypr[1] = fSteer;
        hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -72F), 0.0F);
        hierMesh().chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, -40F), 0.0F);
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, 0.11675F);
        hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, -15F), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.115F, 0.0F, -27F), 0.0F);
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, 0.11675F);
        hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, -15F), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.115F, 0.0F, -27F), 0.0F);
    }

    public void moveSteering(float f)
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -31F * f, 0.0F);
        fSteer = 20F * f;
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.P_63C.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.24F, 0.0F, 0.2405F);
        ypr[1] = fSteer;
        hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float fSteer;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_63C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P63");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-63C(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_us", "3DO/Plane/P-63C(USA)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-63C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitP_63C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.70305F);
        com.maddox.il2.objects.air.P_63C.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 0, 0, 9, 9, 9, 3, 3, 
            3, 3, 9, 9, 9, 9, 9
        });
        com.maddox.il2.objects.air.P_63C.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb01", 
            "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev04", "_ExternalDev05"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", null, null, 
            null, null, "FuelTankGun_Tank75gal2", null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "3x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, 
            null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, null, null, null, 
            null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_1x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", null, null, 
            null, null, "FuelTankGun_Tank75gal2", null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_2x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_3x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, 
            null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_1xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", "BombGunFAB100 1", null, 
            null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_1xFAB100_2x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", null, 
            null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_2xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_3xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_1xFAB250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", null, null, "PylonP63CPLN2", "BombGunFAB250 1", null, 
            null, null, null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_1xFAB250_2x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB250 1", null, 
            null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_2xFAB250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xM2_2xFAB250_1x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", "MGunBrowning50kh 300", "MGunBrowning50kh 300", "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, 
            "BombGunFAB250 1", "BombGunFAB250 1", "FuelTankGun_Tank75gal2", null, null, "PylonP63CGUNPOD", "PylonP63CGUNPOD"
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGunFAB100 1", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "3xFAB100", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1xFAB250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGunFAB250 1", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xFAB250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2xFAB250_1x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, 
            "BombGunFAB250 1", "BombGunFAB250 1", "FuelTankGun_Tank75gal2", null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "3xFAB250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGunFAB250 1", "BombGunNull 1", 
            "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1x250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGun250lbs 1", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1x500", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, null, null, "PylonP63CPLN2", "BombGun500lbs 1", null, 
            null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "1x500_2x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGun500lbs 1", null, 
            null, null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2", null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", null, null, null, 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "2x500_1x75", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", null, null, 
            "BombGun500lbs 1", "BombGun500lbs 1", "FuelTankGun_Tank75gal2", null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "3x500", new java.lang.String[] {
            "MGunBrowning50s 200", "MGunBrowning50s 200", "MGunM9k 58", null, null, "PylonP63CPLN2", "PylonP63CPLN2", "PylonP63CPLN2", "BombGun500lbs 1", "BombGunNull 1", 
            "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_63C.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null
        });
    }
}
