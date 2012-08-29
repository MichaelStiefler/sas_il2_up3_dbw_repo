package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class TU_2S extends TU_2
    implements TypeBomber
{

    public TU_2S()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, -85F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 85F * f, 0.0F);
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
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 50000F)
            fSightCurAltitude = 50000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 1000F)
            fSightCurAltitude = 1000F;
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
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
    }

    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = TU_2S.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Tu-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Tu-2S/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1942.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1956.6F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Tu-2S.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitTU_2S.class, CockpitTU_2S_Bombardier.class, CockpitTU_2S_TGunner.class, CockpitTU_2S_RTGunner.class, CockpitTU_2S_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 11, 12, 3, 3, 3, 3, 3, 
            3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_BombSpawn01", "_BombSpawn02", 
            "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        Aircraft.weaponsRegister(class1, "8fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB50", "BombGunFAB50", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        Aircraft.weaponsRegister(class1, "6fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        Aircraft.weaponsRegister(class1, "8fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB100", "BombGunFAB100", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        Aircraft.weaponsRegister(class1, "1fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB250", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab2506fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        Aircraft.weaponsRegister(class1, "3fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        Aircraft.weaponsRegister(class1, "6fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        Aircraft.weaponsRegister(class1, "1fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB500", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab5006fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        Aircraft.weaponsRegister(class1, "2fab5006fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        Aircraft.weaponsRegister(class1, "2fab5004fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250"
        });
        Aircraft.weaponsRegister(class1, "3fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "4fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, "BombGunFAB500", "BombGunFAB500", 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "6fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500"
        });
        Aircraft.weaponsRegister(class1, "1fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1fab10002fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB250", "BombGunFAB250", "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab10006fab50", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB50", "BombGunFAB50", 
            "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50"
        });
        Aircraft.weaponsRegister(class1, "2fab10006fab100", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100"
        });
        Aircraft.weaponsRegister(class1, "2fab10002fab250", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB250", "BombGunFAB250", 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab10001fab500", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB500", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "3fab1000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "1fab2000", new java.lang.String[] {
            "MGunShVAKk 250", "MGunShVAKk 250", "MGunUBt 550", "MGunUBt 450", "MGunUBt 350", null, null, "BombGunFAB2000", null, null, 
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
