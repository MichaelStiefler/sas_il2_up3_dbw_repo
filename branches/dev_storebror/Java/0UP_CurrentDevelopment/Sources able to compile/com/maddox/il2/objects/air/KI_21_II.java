package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class KI_21_II extends KI_21
    implements TypeBomber
{

    public KI_21_II()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
        fSightCurReadyness = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.06F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearL10_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -38F), 0.0F);
        hiermesh.chunkSetAngles("GearL11_D0", 0.0F, 0.0F, Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
        hiermesh.chunkSetAngles("GearL13_D0", 0.0F, Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -157F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f, 0.3F, 0.35F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(f, 0.3F, 0.35F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR10_D0", 0.0F, Aircraft.cvt(f, 0.34F, 0.99F, 0.0F, -38F), 0.0F);
        hiermesh.chunkSetAngles("GearR11_D0", 0.0F, 0.0F, Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -45F));
        hiermesh.chunkSetAngles("GearR13_D0", 0.0F, Aircraft.cvt(f, 0.34F, 0.99F, 0.0F, -157F), 0.0F);
    }

    protected void moveGear(float f)
    {
        KI_21_II.moveGear(hierMesh(), f);
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
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 35F)
            {
                f = 35F;
                flag = false;
            }
            if(f1 < -25F)
            {
                f1 = -25F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;

        case 1: // '\001'
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
            if(f1 < -5F)
            {
                f1 = -5F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -20F)
            {
                f = -20F;
                flag = false;
            }
            if(f > 20F)
            {
                f = 20F;
                flag = false;
            }
            if(f1 < -89F)
            {
                f1 = -89F;
                flag = false;
            }
            if(f1 > -12F)
            {
                f1 = -12F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -10F)
            {
                f = -10F;
                flag = false;
            }
            if(f > 10F)
            {
                f = 10F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 10F)
            {
                f1 = 10F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        hierMesh().chunkSetAngles("Blister4_D0", 0.0F, -45F, 0.0F);
        hierMesh().chunkSetAngles("Blister5_D0", 0.0F, -45F, 0.0F);
        hierMesh().chunkSetAngles("Blister6_D0", 0.0F, -45F, 0.0F);
        hierMesh().chunkSetAngles("Turret3C_D0", -45F, 0.0F, 0.0F);
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
        java.lang.Class class1 = KI_21_II.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ki-21");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ki-21-II(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/Ki-21-II(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ki-21-II.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitKI_21_II.class, CockpitKI_21_II_Bombardier.class, CockpitKI_21_II_NGunner.class, CockpitKI_21_II_TGunner.class, CockpitKI_21_II_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7394F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 3, 3, 3, 
            3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", 
            "_BombSpawn08", "_BombSpawn09"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "20x15", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 2", "BombGun15kgJ 3", 
            "BombGun15kgJ 3", "BombGun15kgJ 2"
        });
        Aircraft.weaponsRegister(class1, "16x50", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 1", "BombGun50kgJ 2", "BombGun50kgJ 2", "BombGun50kgJ 2", 
            "BombGun50kgJ 3", "BombGun50kgJ 3"
        });
        Aircraft.weaponsRegister(class1, "9x100", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", "BombGun100kgJ", 
            "BombGun100kgJ", "BombGun100kgJ"
        });
        Aircraft.weaponsRegister(class1, "4x250", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", "BombGun250kgJ", "BombGun250kgJ", null, null, "BombGun250kgJ", "BombGun250kgJ", null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303t 750", "MGunBrowning303t 750", "MGunBrowning303t 750", null, "BombGun500kgJ", null, null, "BombGun500kgJ", null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
