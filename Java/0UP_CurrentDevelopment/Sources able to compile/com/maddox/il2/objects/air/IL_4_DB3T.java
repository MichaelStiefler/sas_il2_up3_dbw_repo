package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class IL_4_DB3T extends IL_4
    implements TypeBomber
{

    public IL_4_DB3T()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
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
        java.lang.Class class1 = IL_4_DB3T.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "DB-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/DB-3T/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/DB-3T.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitDB3T.class, CockpitDB3T_FGunner.class, CockpitDB3T_TGunner.class, CockpitDB3T_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        IL_4_DB3T.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3
        });
        IL_4_DB3T.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01"
        });
        IL_4_DB3T.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", null
        });
        IL_4_DB3T.weaponsRegister(class1, "torp1", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGun4512"
        });
        IL_4_DB3T.weaponsRegister(class1, "1x53mmCirc", new java.lang.String[] {
            "MGunShKASt 1200", "MGunShKASt 1200", "MGunShKASt 1200", "BombGunTorp45_36AV_A"
        });
        IL_4_DB3T.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
