package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

public class PE_2SERIES32 extends PE_2
    implements TypeBomber, TypeDiveBomber, TypeTransport
{

    public PE_2SERIES32()
    {
        tme = 0L;
        tpos = 0.0F;
        tlim = 0.0F;
    }

    public void update(float f)
    {
        if(com.maddox.rts.Time.current() > tme)
        {
            tme = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(5000L, 20000L);
            if(FM.turret[1].target == null)
                setRadist(0);
            else
                setRadist(1);
        }
        hierMesh().chunkSetAngles("Turtle_D0", 0.0F, 0.0F, -2F);
        super.update(f);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -1F)
            {
                f1 = -1F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    private void setRadist(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot3_D0", true);
            hierMesh().chunkVisible("Pilot3a_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot3a_D0", true);
            hierMesh().chunkVisible("Pilot3_D0", false);
            break;
        }
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            break;

        case 2: // '\002'
            FM.turret[1].bIsOperable = false;
            break;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 300F)
            fSightCurAltitude = 300F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 50F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 650F)
            fSightCurSpeed = 650F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 50F)
            fSightCurSpeed = 50F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        d -= (double)(fSightCurAltitude * fSightCurAltitude) * 1.419E-005D;
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
    }

    private long tme;
    private float tpos;
    private float tlim;

    static 
    {
        java.lang.Class class1 = PE_2SERIES32.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Pe-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Pe-2series1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941.8F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Pe-2series32.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitPE2_1.class, CockpitPE2_Bombardier.class, CockpitPE2_1_TGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.76315F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 1, 10, 11, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON02", "_CANNON01", "_MGUN01", "_MGUN02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb04", "_ExternalBomb01", "_ExternalBomb01", 
            "_ExternalBomb02", "_ExternalBomb02", "_BombSpawn05", "_BombSpawn05", "_BombSpawn06", "_BombSpawn06", "_BombSpawn01", "_BombSpawn01", "_BombSpawn02", "_BombSpawn02", 
            "_BombSpawn03", "_BombSpawn03", "_BombSpawn04", "_BombSpawn04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", "_ExternalDev15", "_ExternalDev16", 
            "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", 
            "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock15", "_ExternalRock16"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6fab50", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, null, null, 
            null, null, "BombGunFAB50 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB50 1", 
            "BombGunFAB50 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB50 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4fab100_10rs82", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1", null
        });
        Aircraft.weaponsRegister(class1, "4fab100_6rs82_2rs132", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS132 1", "RocketGunRS132 1", 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "6fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, null, null, 
            null, null, "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", 
            "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab250_6rs132", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, "BombGunFAB250 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB250 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS132 1", "RocketGunRS132 1", 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "2fab250_2fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, "BombGunFAB250 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB250 1", null, null, null, null, "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "4fab250", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", "BombGunFAB250 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB250 1", "BombGunFAB250 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB250 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "2fab500", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, "BombGunFAB500 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB500 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "10fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", 
            "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "48AO10", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunAO10 24", "BombGunAO10 24", null, null, 
            "PylonKMB 1", "PylonKMB 1", null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, "RocketGunRS82 1", "BombGunNull 1", "BombGunNull 1", "RocketGunRS82 1"
        });
        Aircraft.weaponsRegister(class1, "2sab15_4fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", null, null, null, null, null, null, 
            null, null, "BombGunSAB15 1", "BombGunNull 1", "BombGunNull 1", "BombGunSAB15 1", "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", 
            "BombGunFAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunFAB100 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2sab100_2fab100", new java.lang.String[] {
            "MGunShKASki 450", "MGunUBki 150", "MGunShKASt 750", "MGunUBt 200", "BombGunSAB100 1", "BombGunNull 1", "BombGunNull 1", "BombGunSAB100 1", "BombGunFAB100 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", 
            "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", "PylonRO_82_1 1", null, null, null, null, "PylonRO_82_1 1", "PylonRO_82_1 1", 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
