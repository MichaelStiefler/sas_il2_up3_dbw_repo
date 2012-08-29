package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class PZL37B extends PZL37
    implements TypeBomber
{

    public PZL37B()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 25: // '\031'
            FM.turret[0].bIsOperable = false;
            break;

        case 26: // '\032'
            FM.turret[1].bIsOperable = false;
            break;

        case 27: // '\033'
            FM.turret[2].bIsOperable = false;
            break;

        case 28: // '\034'
            FM.turret[3].bIsOperable = false;
            break;

        case 29: // '\035'
            FM.turret[4].bIsOperable = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[1].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[3].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[4].bIsOperable = false;
            break;

        case 7: // '\007'
            FM.turret[2].bIsOperable = false;
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
        java.lang.Class class1 = PZL37B.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "PZL37B");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/PZL37B(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_pl", "3DO/Plane/PZL37B/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_pl", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/PZL37B(Romanian)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/PZL-37.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitPZL37.class, CockpitPZL37_Bombardier.class, CockpitPZL37_NGunner.class, CockpitPZL37_TGunner.class, CockpitPZL37RC_VGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", 
            "_BombSpawn08", "_BombSpawn09", "_BombSpawn10"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 900", "MGunBrowning303t 900", "MGunBrowning303t 900", null, null, null, null, null, null, null, 
            null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x300", new java.lang.String[] {
            "MGunBrowning303t 900", "MGunBrowning303t 900", "MGunBrowning303t 900", "BombGunPuW300 1", "BombGunPuW300 1", null, null, null, null, null, 
            null, null, null
        });
        Aircraft.weaponsRegister(class1, "16x110", new java.lang.String[] {
            "MGunBrowning303t 900", "MGunBrowning303t 900", "MGunBrowning303t 900", null, null, "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", 
            "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2"
        });
        Aircraft.weaponsRegister(class1, "2x300+16x110", new java.lang.String[] {
            "MGunBrowning303t 900", "MGunBrowning303t 900", "MGunBrowning303t 900", "BombGunPuW300 1", "BombGunPuW300 1", "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2", 
            "BombGunPuW100 2", "BombGunPuW100 2", "BombGunPuW100 2"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
