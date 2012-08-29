package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.BombGunTorpMk13;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class A_20C extends A_20
    implements TypeBomber, TypeStormovik
{

    public A_20C()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 3000F;
        fSightCurSpeed = 200F;
        fSightCurReadyness = 0.0F;
        llpos = 0.0F;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            hierMesh().chunkVisible("Blister2_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
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
            if(f < -75F)
            {
                f = -75F;
                flag = false;
            }
            if(f > 75F)
            {
                f = 75F;
                flag = false;
            }
            if(f1 < -5F)
            {
                f1 = -5F;
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
            if(f1 > 15F)
            {
                f1 = 15F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
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
            // fall through

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
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
        }
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[2] = A_20C.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.288F, 0.0F, 0.288F);
        hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
        resetYPRmodifier();
        xyz[2] = A_20C.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.288F, 0.0F, 0.288F);
        hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
        resetYPRmodifier();
        xyz[2] = A_20C.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.122F, 0.0F, 0.122F);
        hierMesh().chunkSetLocate("GearC3_D0", xyz, ypr);
    }

    public void moveCockpitDoor(float f)
    {
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, A_20C.cvt(f, 0.01F, 0.99F, 0.0F, -120F), 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateEngineStates[2] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateEngineStates[3] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0023F)
                FM.AS.hitTank(this, 3, 1);
        }
        for(int i = 1; i < 5; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null && (FM.CT.Weapons[3][0] instanceof com.maddox.il2.objects.weapons.BombGunTorpMk13))
        {
            hierMesh().chunkVisible("Bay1_D0", false);
            hierMesh().chunkVisible("Bay2_D0", false);
        }
        FM.AS.wantBeaconsNet(true);
    }

    private static final float toMeters(float f)
    {
        return 0.3048F * f;
    }

    private static final float toMetersPerSecond(float f)
    {
        return 0.4470401F * f;
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
        fSightCurDistance = A_20C.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurDistance = A_20C.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
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
        fSightCurSideslip += 0.05F;
        if(fSightCurSideslip > 3F)
            fSightCurSideslip = 3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Float(fSightCurSideslip * 10F)
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip -= 0.05F;
        if(fSightCurSideslip < -3F)
            fSightCurSideslip = -3F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Float(fSightCurSideslip * 10F)
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 3000F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 50000F)
            fSightCurAltitude = 50000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = A_20C.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 1000F)
            fSightCurAltitude = 1000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitudeft", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = A_20C.toMeters(fSightCurAltitude) * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 200F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 10F;
        if(fSightCurSpeed > 450F)
            fSightCurSpeed = 450F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 10F;
        if(fSightCurSpeed < 100F)
            fSightCurSpeed = 100F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeedMPH", new java.lang.Object[] {
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
            fSightCurDistance -= A_20C.toMetersPerSecond(fSightCurSpeed) * f;
            if(fSightCurDistance < 0.0F)
            {
                fSightCurDistance = 0.0F;
                typeBomberToggleAutomation();
            }
            fSightCurForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(fSightCurDistance / A_20C.toMeters(fSightCurAltitude)));
            if((double)fSightCurDistance < (double)A_20C.toMetersPerSecond(fSightCurSpeed) * java.lang.Math.sqrt(A_20C.toMeters(fSightCurAltitude) * 0.2038736F))
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

    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;
    private float llpos;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "A-20");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/A-20C(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1965.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/A-20C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitA_20C.class, CockpitA_20C_Bombardier.class, CockpitA_20C_TGunner.class, CockpitA_20C_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.92575F);
        A_20C.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 10, 10, 11, 3, 3, 3, 
            3, 3, 3, 3
        });
        A_20C.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN07", "_MGUN08", "_MGUN09", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", 
            "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05"
        });
        A_20C.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, null
        });
        A_20C.weaponsRegister(class1, "2x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun100Lbs", 
            "BombGun100Lbs", null, null, null
        });
        A_20C.weaponsRegister(class1, "2x1008x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun100Lbs", 
            "BombGun100Lbs", "BombGun100Lbs 4", "BombGun100Lbs 4", null
        });
        A_20C.weaponsRegister(class1, "2x1008x1002x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun100Lbs", "BombGun100Lbs", "BombGun100Lbs", 
            "BombGun100Lbs", "BombGun100Lbs 4", "BombGun100Lbs 4", null
        });
        A_20C.weaponsRegister(class1, "2x3004x300", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun300lbs", 
            "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        A_20C.weaponsRegister(class1, "2x3004x3002x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun100Lbs", "BombGun100Lbs", "BombGun300lbs", 
            "BombGun300lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        A_20C.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", 
            "BombGun500lbs", null, null, null
        });
        A_20C.weaponsRegister(class1, "2x5008x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun100Lbs 4", "BombGun100Lbs 4", null
        });
        A_20C.weaponsRegister(class1, "2x5004x300", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs", 
            "BombGun500lbs", "BombGun300lbs 2", "BombGun300lbs 2", null
        });
        A_20C.weaponsRegister(class1, "2x5002x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs", 
            "BombGun500lbs", null, null, null
        });
        A_20C.weaponsRegister(class1, "1x1000", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, "BombGun1000lbs"
        });
        A_20C.weaponsRegister(class1, "1x10008x100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, "BombGun100Lbs 4", "BombGun100Lbs 4", "BombGun1000lbs"
        });
        A_20C.weaponsRegister(class1, "1x10004x300", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, "BombGun300lbs 2", "BombGun300lbs 2", "BombGun1000lbs"
        });
        A_20C.weaponsRegister(class1, "1x10002x500", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", null, 
            null, null, null, "BombGun1000lbs"
        });
        A_20C.weaponsRegister(class1, "1xmk13", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, "BombGunTorpMk13"
        });
        A_20C.weaponsRegister(class1, "1xmk13_late", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, "BombGunTorpMk13late"
        });
        A_20C.weaponsRegister(class1, "1x53mmCirc", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, "BombGunTorp45_36AV_A"
        });
        A_20C.weaponsRegister(class1, "2xfab2502xfab250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", 
            "BombGunFAB250", null, null, null
        });
        A_20C.weaponsRegister(class1, "1xfab5002xfab100", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB500"
        });
        A_20C.weaponsRegister(class1, "1xfab5002xfab250", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGunFAB250", "BombGunFAB250", null, 
            null, null, null, "BombGunFAB500"
        });
        A_20C.weaponsRegister(class1, "1xfab1000", new java.lang.String[] {
            "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303kipzl 500", "MGunBrowning303t 500", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null, 
            null, null, null, "BombGunFAB1000"
        });
        A_20C.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null
        });
    }
}
