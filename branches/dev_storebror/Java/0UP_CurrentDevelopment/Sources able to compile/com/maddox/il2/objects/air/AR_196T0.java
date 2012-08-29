package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class AR_196T0 extends AR_196T
    implements TypeBomber
{

    public AR_196T0()
    {
        arrestor = 0.0F;
        flapAngle = 0.0F;
        aileronAngle = 0.0F;
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 37F * f, 0.0F);
        arrestor = f;
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -(f <= 0.0F ? 14F : 28F) * f - flapAngle, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -(f <= 0.0F ? 28F : 14F) * f + flapAngle, 0.0F);
        aileronAngle = f;
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
        flapAngle = f * 45F >= 1.0F ? (f * 45F - 1.0F) / 2.0F : 0.0F;
        moveAileron(aileronAngle);
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 157F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 157F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        AR_196T0.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLFold_D0", 0.0F, 120F * f, 0.0F);
        hiermesh.chunkSetAngles("WingRFold_D0", -0F, -120F * f, 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
        } else
        {
            setGunPodsOn(false);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.WeaponControl[1] = false;
        }
        moveWingFold(hierMesh(), f);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.53F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void setOnGround(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, com.maddox.JGP.Vector3d vector3d)
    {
        super.setOnGround(point3d, orient, vector3d);
        if(super.FM.isPlayers())
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.cockpitDoorControl = 1.0F;
        hierMesh().chunkSetAngles("WingLFold_D0", 0.0F, 120F, 0.0F);
        hierMesh().chunkSetAngles("WingRFold_D0", -0F, -120F, 0.0F);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.wingControl = 1.0F;
    }

    public void update(float f)
    {
        super.update(f);
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() > 0.2F)
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle != 0.0F)
            {
                float f1 = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVAngle, -26F, 11F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = (-42F * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorVSink) / 37F;
                if(f2 < 0.0F && super.FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.getArrestor() < 0.95F)
                    f2 = 0.0F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > 1.0F)
                    arrestor = 1.0F;
                moveArrestorHook(arrestor);
            }
    }

    private float flapAngle;
    private float aileronAngle;
    protected float arrestor;
    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = AR_196T0.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ar-196T.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ar-196T/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ar-196T");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1938.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitAR_196T0.class, CockpitAR_196T_Gunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 3, 3, 9, 3, 9, 3, 9, 
            9, 9, 9, 1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalBomb03", "_ExternalDev02", "_ExternalBomb04", "_ExternalDev03", 
            "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_CANNON03", "_CANNON04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, null, null, "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2sc50", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", "BombGunSC50 1", "BombGunSC50 1", null, null, null, null, "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1sc250", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, "BombGunSC250", "PylonETC501FW190", null, "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1sc500", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, "BombGunSC500", "PylonETC501FW190", null, "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1sc1000", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, "PylonETC501FW190", "BombGunSC1000", "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "torpedo", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, "PylonETC501FW190", "BombGun4512", "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "torpfiume", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, "PylonETC501FW190", "BombGunTorpFiume", "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "torpf5b", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, "PylonETC501FW190", "BombGunTorpF5Bheavy", "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "torpltf5", new java.lang.String[] {
            "MGunMG15120MGs 250", "MGunMG15120MGs 250", "MGunMG15t 525", null, null, null, null, "PylonETC501FW190", "BombGunTorpLTF5Practice", "PylonMG15120Internal", 
            "PylonMG15120Internal", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
