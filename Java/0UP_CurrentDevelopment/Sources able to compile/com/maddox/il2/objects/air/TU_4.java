package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class TU_4 extends B_29X
    implements TypeBomber
{

    public TU_4()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            killPilot(this, 4);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 7; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
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
            if(f < -23F)
            {
                f = -23F;
                flag = false;
            }
            if(f > 23F)
            {
                f = 23F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 15F)
            {
                f1 = 15F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f1 > 73F)
            {
                f1 = 73F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -38F)
            {
                f = -38F;
                flag = false;
            }
            if(f > 38F)
            {
                f = 38F;
                flag = false;
            }
            if(f1 < -41F)
            {
                f1 = -41F;
                flag = false;
            }
            if(f1 > 43F)
            {
                f1 = 43F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -85F)
            {
                f = -85F;
                flag = false;
            }
            if(f > 22F)
            {
                f = 22F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f1 > 32F)
            {
                f1 = 32F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -34F)
            {
                f = -34F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 32F)
            {
                f1 = 32F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 3: // '\003'
            FM.turret[1].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[2].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[3].bIsOperable = false;
            FM.turret[4].bIsOperable = false;
            break;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        bSightAutomation = !bSightAutomation;
        bSightBombDump = false;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (bSightAutomation ? "ON" : "OFF"));
        return bSightAutomation;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle++;
        if(fSightCurForwardAngle > 85F)
            fSightCurForwardAngle = 85F;
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle--;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)fSightCurForwardAngle)
        });
        if(bSightAutomation)
            typeBomberToggleAutomation();
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip += 0.1F;
        if(fSightCurSideslip > 3F)
            fSightCurSideslip = 3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip -= 0.1F;
        if(fSightCurSideslip < -3F)
            fSightCurSideslip = -3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 10F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 850F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 10000F)
            fSightCurAltitude = 10000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 850F)
            fSightCurAltitude = 850F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 150F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 10F;
        if(fSightCurSpeed > 800F)
            fSightCurSpeed = 800F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 10F;
        if(fSightCurSpeed < 150F)
            fSightCurSpeed = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
        if((double)java.lang.Math.abs(FM.Or.getKren()) > 4.5D)
        {
            fSightCurReadyness -= 0.0666666F * f;
            if(fSightCurReadyness < 0.0F)
                fSightCurReadyness = 0.0F;
        }
        if(fSightCurReadyness < 1.0F)
            fSightCurReadyness += 0.0333333F * f;
        else
        if(bSightAutomation)
        {
            fSightCurDistance -= (fSightCurSpeed / 3.6F) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / fSightCurAltitude));
            if((double)fSightCurDistance < (double)(fSightCurSpeed / 3.6F) * java.lang.Math.sqrt(fSightCurAltitude * 0.2038736F))
                bSightBombDump = true;
            if(bSightBombDump)
                if(FM.isTick(3, 0))
                {
                    if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets())
                    {
                        FM.CT.WeaponControl[3] = true;
                        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
                    }
                } else
                {
                    FM.CT.WeaponControl[3] = false;
                }
        }
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte((bSightAutomation ? 1 : 0) | (bSightBombDump ? 2 : 0));
        netmsgguaranted.writeFloat(fSightCurDistance);
        netmsgguaranted.writeByte((int)fSightCurForwardAngle);
        netmsgguaranted.writeByte((int)((fSightCurSideslip + 3F) * 33.33333F));
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeByte((int)(fSightCurSpeed / 2.5F));
        netmsgguaranted.writeByte((int)(fSightCurReadyness * 200F));
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        bSightAutomation = (i & 1) != 0;
        bSightBombDump = (i & 2) != 0;
        fSightCurDistance = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readUnsignedByte();
        fSightCurSideslip = -3F + (float)netmsginput.readUnsignedByte() / 33.33333F;
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = (float)netmsginput.readUnsignedByte() * 2.5F;
        fSightCurReadyness = (float)netmsginput.readUnsignedByte() / 200F;
    }

    public static boolean bChangedPit = false;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;

    static 
    {
        java.lang.Class class1 = TU_4.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "TU-4");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Tu-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1947F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1960F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitTU4.class, CockpitTU4_Bombardier.class, CockpitTU4_TGunner.class, CockpitTU4_T2Gunner.class, CockpitTU4_FGunner.class, CockpitTU4_AGunner.class, CockpitTU4_RGunner.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/B-29.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 
            3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", 
            "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            null, null, null
        });
        Aircraft.weaponsRegister(class1, "48XFAB-100", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB100 12", "BombGunFAB100 12", "BombGunFAB100 12", "BombGunFAB100 12"
        });
        Aircraft.weaponsRegister(class1, "48XFAB-50", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB50 12", "BombGunFAB50 12", "BombGunFAB50 12", "BombGunFAB50 12"
        });
        Aircraft.weaponsRegister(class1, "24XFAB-250M-46", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB250m46 6", "BombGunFAB250m46 6", "BombGunFAB250m46 6", "BombGunFAB250m46 6"
        });
        Aircraft.weaponsRegister(class1, "24XFAB-250", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB250int 6", "BombGunFAB250int 6", "BombGunFAB250int 6", "BombGunFAB250int 6"
        });
        Aircraft.weaponsRegister(class1, "14XFAB-500", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB500int 4", "BombGunFAB500int 4", "BombGunFAB500int 3", "BombGunFAB500int 3"
        });
        Aircraft.weaponsRegister(class1, "8XFAB-1000", new java.lang.String[] {
            "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
            "BombGunFAB1000int 2", "BombGunFAB1000int 2", "BombGunFAB1000int 2", "BombGunFAB1000int 2"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
