package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class SPITFIRE11 extends SPITFIRE9
{

    public SPITFIRE11()
    {
        flapps = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.0F, 0.6F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.2F, 1.0F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
    }

    protected void moveGear(float f)
    {
        SPITFIRE11.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        Aircraft.xyz[2] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        Aircraft.xyz[2] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    }

    public void update(float f)
    {
        super.update(f);
        float f1 = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator();
        if(java.lang.Math.abs(flapps - f1) > 0.01F)
        {
            flapps = f1;
            hierMesh().chunkSetAngles("Oil1_D0", 0.0F, -20F * f1, 0.0F);
            hierMesh().chunkSetAngles("Oil2_D0", 0.0F, -20F * f1, 0.0F);
        }
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = SPITFIRE11.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkXI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1942.11F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944.12F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/SpitfirePRXI.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSpitPR.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_ExternalDev08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        Aircraft.weaponsRegister(class1, "1x30dt", new java.lang.String[] {
            "FuelTankGun_TankSpit30 1"
        });
        Aircraft.weaponsRegister(class1, "1x45dt", new java.lang.String[] {
            "FuelTankGun_TankSpit45 1"
        });
        Aircraft.weaponsRegister(class1, "1x90dt", new java.lang.String[] {
            "FuelTankGun_TankSpit90 1"
        });
        Aircraft.weaponsRegister(class1, "1x170dt", new java.lang.String[] {
            "FuelTankGun_TankSpit170 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
