package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class SPITFIRE12 extends SPITFIRE
{

    public SPITFIRE12()
    {
        flapps = 0.0F;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
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
        SPITFIRE12.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        Aircraft.xyz[2] = Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        Aircraft.xyz[2] = Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = SPITFIRE12.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SpitfireMkXII(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Spitfire-F-XII-G3-CW.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSpit12.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5926F);
        SPITFIRE12.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 3
        });
        SPITFIRE12.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev01", "_ExternalBomb01"
        });
        SPITFIRE12.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null
        });
        SPITFIRE12.weaponsRegister(class1, "30gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30New 1", null, null
        });
        SPITFIRE12.weaponsRegister(class1, "45gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45New 1", null, null
        });
        SPITFIRE12.weaponsRegister(class1, "90gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90New 1", null, null
        });
        SPITFIRE12.weaponsRegister(class1, "250lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitC", "BombGun250lbsE 1"
        });
        SPITFIRE12.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitC", "BombGun500lbsE 1"
        });
        SPITFIRE12.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
