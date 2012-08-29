package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class N1K3A extends N1K3
{

    public N1K3A()
    {
        arrestor = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(f, 0.35F, 0.4F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -48F), 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, Aircraft.cvt(f, 0.35F, 0.95F, 0.0F, -58F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -82F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.1F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -48F), 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.65F, 0.0F, -58F), 0.0F);
        Aircraft.xyz[0] = Aircraft.xyz[1] = Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F;
        Aircraft.xyz[0] = Aircraft.cvt(f, 0.0F, 1.0F, -0.075F, 0.0F);
        Aircraft.ypr[1] = Aircraft.cvt(f, 0.0F, 1.0F, 40F, 0.0F);
        hiermesh.chunkSetLocate("GearC2_D0", Aircraft.xyz, Aircraft.ypr);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -37F * f, 0.0F);
        arrestor = f;
    }

    protected void moveGear(float f)
    {
        N1K3A.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        if(FM.CT.getGear() == 1.0F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.1F, 0.0F, 20F), 0.0F);
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, 0.23F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -42F), 0.0F);
        hierMesh().chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.23F, 0.0F, -45F), 0.0F);
        Aircraft.xyz[1] = Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, 0.27625F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -33F), 0.0F);
        hierMesh().chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, -66F), 0.0F);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = FM.EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            for(int i = 1; i < 9; i++)
                hierMesh().chunkSetAngles("Cowflap" + i + "_D0", 0.0F, -20F * f1, 0.0F);

        }
    }

    public void onAircraftLoaded()
    {
        com.maddox.il2.ai.BulletEmitter abulletemitter[] = new com.maddox.il2.ai.BulletEmitter[4];
        super.onAircraftLoaded();
        abulletemitter[0] = getBulletEmitterByHookName("_ExternalRock01");
        abulletemitter[1] = getBulletEmitterByHookName("_ExternalRock02");
        abulletemitter[2] = getBulletEmitterByHookName("_ExternalRock03");
        abulletemitter[3] = getBulletEmitterByHookName("_ExternalRock04");
        if(abulletemitter[0] == com.maddox.il2.objects.weapons.GunEmpty.get() && abulletemitter[2] == com.maddox.il2.objects.weapons.GunEmpty.get())
            hierMesh().chunkVisible("RailL", false);
        if(abulletemitter[1] == com.maddox.il2.objects.weapons.GunEmpty.get() && abulletemitter[3] == com.maddox.il2.objects.weapons.GunEmpty.get())
            hierMesh().chunkVisible("RailR", false);
    }

    protected float arrestor;

    static 
    {
        java.lang.Class class1 = N1K3A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "N1K");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/N1K3-A(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/N1K3-A(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/N1K3-A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitN1K3.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.1716F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 
            9, 9, 3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", null, null, null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 200", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4xtype3", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", 
            null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4xtype3+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", "RocketGunType3Mk27 1", 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+2x60", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+2x60+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", "BombGun60kgJ 1", "BombGun60kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4x60", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1"
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4x60+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun60kgJ 1"
        });
        Aircraft.weaponsRegister(class1, "20mmx900+2x100", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, "BombGun100kgJ 1", "BombGun100kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+2x100+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", "BombGun100kgJ 1", "BombGun100kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4x100", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1"
        });
        Aircraft.weaponsRegister(class1, "20mmx900+4x100+1x400dt", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            "PylonN1K1PLN1 1", "FuelTankGun_TankN1K1 1", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1", "BombGun100kgJ 1"
        });
        Aircraft.weaponsRegister(class1, "20mmx900+2x250", new java.lang.String[] {
            "MGunMG131s 300", "MGunMG131s 300", "MGunType99No2s 250", "MGunType99No2s 200", "MGunType99No2s 200", "MGunType99No2s 250", null, null, null, null, 
            null, null, "BombGun250kgJ 1", "BombGun250kgJ 1", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null
        });
    }
}
