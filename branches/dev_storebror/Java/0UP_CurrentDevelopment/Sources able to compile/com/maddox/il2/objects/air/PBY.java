package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class PBY extends PBYX
    implements TypeBomber
{

    public PBY()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            super.FM.turret[0].bIsOperable = false;
            break;

        case 5: // '\005'
            super.FM.turret[1].bIsOperable = false;
            break;

        case 6: // '\006'
            super.FM.turret[2].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            break;

        case 5: // '\005'
            hierMesh().chunkVisible("Pilot6_D0", false);
            hierMesh().chunkVisible("HMask6_D0", false);
            hierMesh().chunkVisible("Pilot6_D1", true);
            break;

        case 6: // '\006'
            hierMesh().chunkVisible("Pilot7_D0", false);
            hierMesh().chunkVisible("HMask7_D0", false);
            hierMesh().chunkVisible("Pilot7_D1", true);
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
        if(fSightCurAltitude > 10000F)
            fSightCurAltitude = 10000F;
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
        if(fSightCurSpeed > 520F)
            fSightCurSpeed = 520F;
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

    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = PBY.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "PBY");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/PBY/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2048F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/PBN-1.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitPBY.class, CockpitPBY_Bombardier.class, CockpitPBY_TGunner.class, CockpitPBY_LGunner.class, CockpitPBY_RGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 9, 9, 3, 
            2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb05", 
            "_ExternalBomb06"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", null, null, null, null, null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "4X250", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "4X500", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "4X1000", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2xMk13a", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", null, null, null, null, "PylonBEAUPLN4 1", "PylonBEAUPLN4 1", "BombGunTorpMk13a 1", 
            "BombGunTorpMk13a 1"
        });
        Aircraft.weaponsRegister(class1, "2xMk34", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", null, null, null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "BombGunTorpMk34 1", 
            "BombGunTorpMk34 1"
        });
        Aircraft.weaponsRegister(class1, "4xMk53", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGunMk53Charge 1", "BombGunMk53Charge 1", "BombGunMk53Charge 1", "BombGunMk53Charge 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2xMk24and2xMk53", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGunMk24Flare 1", "BombGunMk24Flare 1", "BombGunMk53Charge 1", "BombGunMk53Charge 1", null, null, null, 
            null
        });
        Aircraft.weaponsRegister(class1, "2xMk24and2xMk13a", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGunMk24Flare 1", "BombGunMk24Flare 1", null, null, "PylonBEAUPLN4 1", "PylonBEAUPLN4 1", "BombGunTorpMk13a 1", 
            "BombGunTorpMk13a 1"
        });
        Aircraft.weaponsRegister(class1, "2xMk24and2xMk34", new java.lang.String[] {
            "MGunBrowning50t 1000", "MGunBrowning50t 1000", "MGunBrowning50t 1000", "BombGunMk24Flare 1", "BombGunMk24Flare 1", null, null, "PylonF4FPLN2 1", "PylonF4FPLN2 1", "BombGunTorpMk34 1", 
            "BombGunTorpMk34 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
