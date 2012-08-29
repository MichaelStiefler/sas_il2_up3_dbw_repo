package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class JU_52_3MG3E extends JU_52
    implements TypeBomber
{

    public JU_52_3MG3E()
    {
        llpos = 0.0F;
        bPitUnfocused = true;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
        fSightCurReadyness = 0.0F;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(af[0] < -50F)
            {
                af[0] = -50F;
                flag = false;
            } else
            if(af[0] > 50F)
            {
                af[0] = 50F;
                flag = false;
            }
            float f = java.lang.Math.abs(af[0]);
            if(f < 20F)
            {
                if(af[1] < -1F)
                {
                    af[1] = -1F;
                    flag = false;
                }
            } else
            if(af[1] < -5F)
            {
                af[1] = -5F;
                flag = false;
            }
            if(af[1] > 45F)
            {
                af[1] = 45F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(af[0] < -120F)
            {
                af[0] = -120F;
                flag = false;
            } else
            if(af[0] > 120F)
            {
                af[0] = 120F;
                flag = false;
            }
            if(af[1] < -85F)
            {
                af[1] = -85F;
                flag = false;
            }
            if(af[1] > 5F)
            {
                af[1] = 5F;
                flag = false;
            }
            break;
        }
        return flag;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        if(FM.AS.astateEngineStates[0] > 2 && FM.AS.astateEngineStates[1] > 2)
            FM.setCapableOfBMP(false, shot.initiator);
        super.msgShot(shot);
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
        if(fSightCurForwardAngle < 0.0F)
            fSightCurForwardAngle = 0.0F;
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
        if(fSightCurSpeed > 600F)
            fSightCurSpeed = 600F;
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

    public void update(float f)
    {
        if(FM.AS.bLandingLightOn)
        {
            if(llpos < 1.0F)
            {
                llpos += 0.5F * f;
                hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90F * llpos, 0.0F);
            }
        } else
        if(llpos > 0.0F)
        {
            llpos -= 0.5F * f;
            hierMesh().chunkSetAngles("LLight_D0", 0.0F, -90F * llpos, 0.0F);
        }
        super.update(f);
    }

    private float llpos;
    public boolean bPitUnfocused;
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
        java.lang.Class class1 = JU_52_3MG3E.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-52_3mg4e.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-52_3mg3e/hierJU523E.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-52");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1934F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitJU525E.class, CockpitJU523E_Bombardier.class, CockpitJU525E_GunnerOpen.class, CockpitJU523E_BGunner.class
        });
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 3, 3, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_BombSpawn01", "_BombSpawn02", "_BombSpawn02", "_BombSpawn03", "_BombSpawn03", "_BombSpawn04", "_BombSpawn04", "_BombSpawn05", 
            "_BombSpawn05", "_BombSpawn06", "_BombSpawn06", "_BombSpawn07", "_BombSpawn07"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "18xPara", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", "BombGunPara 18", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "10xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, null, null, null, null, null, 
            null, "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "20xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, "BombGunSC50 5", "BombGunNull 1", null, null, "BombGunSC50 5", 
            "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "30xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", 
            "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "2xSC250", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, null, null, null, null, null, 
            null, "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "2xSC250_10xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, "BombGunSC250 1", "BombGunNull 1", null, null, "BombGunSC250 1", 
            "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "2xSC250_20xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", 
            "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "4xSC250", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, null, null, "BombGunSC250 1", "BombGunNull 1", null, null, "BombGunSC250 1", 
            "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "4xSC250_10xSC50", new java.lang.String[] {
            "MGunMG15t 1050", "MGunMG15t 750", null, "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", "BombGunNull 1", "BombGunSC250 1", 
            "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1", "BombGunSC50 5", "BombGunNull 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
