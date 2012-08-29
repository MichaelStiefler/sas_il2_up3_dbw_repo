// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_88A4.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_88NEW, PaintSchemeBMPar02, TypeBomber, TypeDiveBomber, 
//            TypeScout, Aircraft, NetAircraft

public class JU_88A4 extends com.maddox.il2.objects.air.JU_88NEW
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeDiveBomber, com.maddox.il2.objects.air.TypeScout
{

    public JU_88A4()
    {
        diveMechStage = 0;
        bNDives = false;
        bDropsBombs = false;
        dropStopTime = -1L;
        needsToOpenBombays = false;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
        fSightCurReadyness = 0.0F;
        fDiveRecoveryAlt = 850F;
        fDiveVelocity = 150F;
        fDiveAngle = 70F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(thisWeaponsName.startsWith("10xSC50"))
        {
            FM.M.fuel += 900F;
            FM.M.maxFuel += 900F;
            FM.M.referenceWeight += 900F;
            needsToOpenBombays = true;
        } else
        if(thisWeaponsName.startsWith("28xSC50") || thisWeaponsName.startsWith("18xSC50") || thisWeaponsName.startsWith("6xSC250"))
            needsToOpenBombays = true;
    }

    protected void moveBayDoor(float f)
    {
        if(!needsToOpenBombays)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 85F * f, 0.0F);
            hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -85F * f, 0.0F);
            hierMesh().chunkSetAngles("Bay3_D0", 0.0F, 85F * f, 0.0F);
            hierMesh().chunkSetAngles("Bay4_D0", 0.0F, -85F * f, 0.0F);
            return;
        }
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

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void update(float f)
    {
        updateJU87D5(f);
        updateJU87(f);
        super.update(f);
        if(com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeed()) > 70F && (double)FM.CT.getFlap() > 0.01D && FM.CT.FlapsControl != 0.0F)
        {
            FM.CT.FlapsControl = 0.0F;
            com.maddox.il2.ai.World.cur();
            if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log("FlapsRaised");
        }
    }

    public void updateJU87(float f)
    {
        if(this == com.maddox.il2.ai.World.getPlayerAircraft() && (FM instanceof com.maddox.il2.fm.RealFlightModel))
            if(((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
            {
                switch(diveMechStage)
                {
                case 0: // '\0'
                    if(bNDives && FM.CT.AirBrakeControl == 1.0F && FM.Loc.z > (double)fDiveRecoveryAlt)
                    {
                        diveMechStage++;
                        bNDives = false;
                    } else
                    {
                        bNDives = FM.CT.AirBrakeControl != 1.0F;
                    }
                    break;

                case 1: // '\001'
                    FM.CT.setTrimElevatorControl(-0.25F);
                    FM.CT.trimElevator = -0.25F;
                    if(FM.CT.AirBrakeControl == 0.0F || FM.CT.saveWeaponControl[3] || FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].countBullets() == 0)
                    {
                        if(FM.CT.AirBrakeControl == 0.0F)
                            diveMechStage++;
                        if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].countBullets() == 0)
                            diveMechStage++;
                    }
                    break;

                case 2: // '\002'
                    FM.CT.setTrimElevatorControl(0.45F);
                    FM.CT.trimElevator = 0.45F;
                    if(FM.CT.AirBrakeControl == 0.0F || FM.Or.getTangage() > 0.0F)
                        diveMechStage++;
                    break;

                case 3: // '\003'
                    FM.CT.setTrimElevatorControl(0.0F);
                    FM.CT.trimElevator = 0.0F;
                    diveMechStage = 0;
                    break;
                }
            } else
            {
                FM.CT.setTrimElevatorControl(0.0F);
                FM.CT.trimElevator = 0.0F;
            }
        if(bDropsBombs && FM.isTick(3, 0) && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets())
            FM.CT.WeaponControl[3] = true;
    }

    public void updateJU87D5(float f)
    {
        fDiveAngle = -FM.Or.getTangage();
        if(fDiveAngle > 89F)
            fDiveAngle = 89F;
        if(fDiveAngle < 10F)
            fDiveAngle = 10F;
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -90F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -90F * f, 0.0F);
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
            FM.turret[2].setHealth(f);
            break;

        case 3: // '\003'
            FM.turret[3].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("HMask2_D0", false);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("HMask3_D0", false);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            hierMesh().chunkVisible("HMask4_D0", false);
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
        fSightCurAltitude = 850F;
        typeDiveBomberAdjAltitudeReset();
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
        typeDiveBomberAdjAltitudePlus();
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
        if(fSightCurAltitude < 500F)
            fSightCurAltitude = 500F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
        fSightCurDistance = fSightCurAltitude * (float)java.lang.Math.tan(java.lang.Math.toRadians(fSightCurForwardAngle));
        typeDiveBomberAdjAltitudeMinus();
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 150F;
        typeDiveBomberAdjVelocityReset();
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 10F;
        if(fSightCurSpeed > 700F)
            fSightCurSpeed = 700F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
        typeDiveBomberAdjVelocityPlus();
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 10F;
        if(fSightCurSpeed < 150F)
            fSightCurSpeed = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
        typeDiveBomberAdjVelocityMinus();
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
        fSightCurAltitude = fDiveRecoveryAlt = netmsginput.readFloat();
        fSightCurSpeed = fDiveVelocity = (float)netmsginput.readUnsignedByte() * 2.5F;
        fSightCurReadyness = (float)netmsginput.readUnsignedByte() / 200F;
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
        fDiveRecoveryAlt += 10F;
        if(fDiveRecoveryAlt > 10000F)
            fDiveRecoveryAlt = 10000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fDiveRecoveryAlt)
        });
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
        fDiveRecoveryAlt -= 10F;
        if(fDiveRecoveryAlt < 500F)
            fDiveRecoveryAlt = 500F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fDiveRecoveryAlt)
        });
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
        fDiveVelocity += 10F;
        if(fDiveVelocity > 700F)
            fDiveVelocity = 700F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fDiveVelocity)
        });
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
        fDiveVelocity -= 10F;
        if(fDiveVelocity < 150F)
            fDiveVelocity = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fDiveVelocity)
        });
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, -65F, 65F, 65F, -65F), 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bChangedPit = false;
    public int diveMechStage;
    public boolean bNDives;
    private boolean bDropsBombs;
    private long dropStopTime;
    private boolean needsToOpenBombays;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;
    public float fDiveRecoveryAlt;
    public float fDiveVelocity;
    public float fDiveAngle;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-88");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ju-88A-4/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-88A-4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitJU_88A4.class, com.maddox.il2.objects.air.CockpitJU_88A4_Bombardier.class, com.maddox.il2.objects.air.CockpitJU_88A4_NGunner.class, com.maddox.il2.objects.air.CockpitJU_88A4_RGunner.class, com.maddox.il2.objects.air.CockpitJU_88A4_BGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0976F);
        com.maddox.il2.objects.air.JU_88A4.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 13, 3, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.JU_88A4.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01", 
            "_BombSpawn02"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "28xSC50", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC50 14", 
            "BombGunSC50 14"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "28xSC50_2xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC50 14", 
            "BombGunSC50 14"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "28xSC50_4xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 14", 
            "BombGunSC50 14"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "18xSC50_2xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", null, null, "BombGunSC50 14", 
            "BombGunSC50 14"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "18xSC50_4xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC50 14", 
            "BombGunSC50 14"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "10xSC50", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC50 5", 
            "BombGunSC50 5"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "10xSC50_2xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", null, null, "BombGunSC50 5", 
            "BombGunSC50 5"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "10xSC50_4xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC50 5", 
            "BombGunSC50 5"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "4xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "6xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "4xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", "BombGunSC500 1", null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xAB500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB500 1", "BombGunAB500 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "4xAB500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB500 1", "BombGunAB500 1", "BombGunAB500 1", "BombGunAB500 1", null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "1xSC1000", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC1000 1", null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "1xSC1000_1xSC250", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC250 1", "BombGunSC1000 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "1xSC1000_1xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC500 1", "BombGunSC1000 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xSC1000", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC1000 1", "BombGunSC1000 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xSC1000_2xSC500", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC1000 1", "BombGunSC1000 1", "BombGunSC500 1", "BombGunSC500 1", null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xAB1000", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB1000 1", "BombGunAB1000 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xSC1800", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, "BombGunSC1800 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xSC2000", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", null, "BombGunSC2000 1", null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "2xLT350", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "MGunMG81t 750", "MGunMG81t 750", "BombGunTorpFFF 1", "BombGunTorpFFF1 1", "BombGunNull 1", "BombGunNull 1", null, 
            null
        });
        com.maddox.il2.objects.air.JU_88A4.weaponsRegister(class1, "none", new java.lang.String[] {
            "MGunMG81t 000", "MGunMG81t 000", "MGunMG81t 000", null, null, null, null, null, null, null, 
            null
        });
    }
}
