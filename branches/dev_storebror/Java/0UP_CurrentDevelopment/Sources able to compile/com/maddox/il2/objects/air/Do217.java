package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;
import java.io.PrintStream;

public abstract class Do217 extends Scheme2
    implements TypeBomber, TypeTransport
{

    public Do217()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurReadyness = 0.0F;
        calibDistance = 0.0F;
        bPitUnfocused = true;
        maneuver = null;
        wheel1 = 0.0F;
        wheel2 = 0.0F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.Gears.computePlaneLandPose(FM);
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
            calibDistance = (fSightCurSpeed / 3.6F) * floatindex(Aircraft.cvt(fSightCurAltitude, 0.0F, 7000F, 0.0F, 7F), calibrationScale);
            if((double)fSightCurDistance < (double)calibDistance + (double)(fSightCurSpeed / 3.6F) * java.lang.Math.sqrt(fSightCurAltitude * 0.2038736F))
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

    protected float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
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

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateEngineStates[2] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.39F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        for(int i = 1; i <= 4; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("hmask" + i + "_d0", false);
            else
                hierMesh().chunkVisible("hmask" + i + "_d0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            FM.turret[3].setHealth(f);
            FM.turret[4].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
            break;

        case 3: // '\003'
            FM.turret[2].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("hmask1_d0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("hmask2_d0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("hmask3_d0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("hmask4_d0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            break;
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 33: // '!'
            hitProp(0, j, actor);
            break;

        case 36: // '$'
            hitProp(1, j, actor);
            break;

        case 35: // '#'
            FM.AS.hitEngine(this, 0, 3);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 0, 1);
            break;

        case 38: // '&'
            FM.AS.hitEngine(this, 1, 3);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 1, 1);
            break;

        case 11: // '\013'
            hierMesh().chunkVisible("Wire1_D0", false);
            break;

        case 19: // '\023'
            hierMesh().chunkVisible("Wire1_D0", false);
            FM.Gears.hitCentreGear();
            break;

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            killPilot(this, 2);
            killPilot(this, 3);
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        super.update(f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, Aircraft.cvt(f, -65F, 65F, 65F, -65F), 0.0F);
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
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f > 50F)
            {
                f = 50F;
                flag = false;
            }
            if(f < -25F)
            {
                f = -25F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f1 > 80F)
            {
                f1 = 80F;
                flag = false;
            }
            if(f1 < -3F)
            {
                f1 = -3F;
                flag = false;
            }
            if(f > 155F || f < -155F)
            {
                if(f1 < 40F)
                {
                    f1 = 40F;
                    flag = false;
                }
                break;
            }
            if(f > 135F && f1 < ((f - 135F) * 40F) / 20F)
            {
                f1 = ((f - 135F) * 40F) / 20F;
                flag = false;
            }
            if(f < -135F && f1 < ((-f - 135F) * 40F) / 20F)
            {
                f1 = ((-f - 135F) * 40F) / 20F;
                flag = false;
            }
            if((f > 110F || f < -110F) && f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f > 50F)
            {
                f = 50F;
                flag = false;
            }
            if(f < -50F)
            {
                f = -50F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f1 > 35F)
            {
                f1 = 35F;
                flag = false;
            }
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f > 65F)
            {
                f = 65F;
                flag = false;
            }
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f1 > 35F)
            {
                f1 = 35F;
                flag = false;
            }
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f < -65F)
            {
                f = -65F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void moveWheelSink()
    {
        wheel1 = 0.8F * wheel1 + 0.2F * FM.Gears.gWheelSinking[0];
        wheel2 = 0.8F * wheel2 + 0.2F * FM.Gears.gWheelSinking[1];
        resetYPRmodifier();
        Aircraft.xyz[1] = -Aircraft.cvt(wheel1, 0.0F, 0.3F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        Aircraft.xyz[1] = -Aircraft.cvt(wheel2, 0.0F, 0.3F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    }

    protected void mydebuggunnery(java.lang.String s)
    {
        java.lang.System.out.println(s);
    }

    protected void setControlDamage(com.maddox.il2.ai.Shot shot, int i)
    {
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.002F && getEnergyPastArmor(4F, shot) > 0.0F)
            FM.AS.setControlsDamage(shot.initiator, i);
    }

    protected void hitChunk(java.lang.String s, com.maddox.il2.ai.Shot shot)
    {
        super.hitChunk(s, shot);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
                if(s.endsWith("p1"))
                {
                    if(Aircraft.v1.z > 0.5D)
                        getEnergyPastArmor(4D / Aircraft.v1.z, shot);
                    else
                    if(Aircraft.v1.x > 0.93969261646270752D)
                        getEnergyPastArmor((8D / Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                    else
                        getEnergyPastArmor(3F, shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(4D / java.lang.Math.abs(Aircraft.v1.z), shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor((7D / java.lang.Math.abs(Aircraft.v1.x)) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                else
                    getEnergyPastArmor(3F, shot);
            if(s.endsWith("p4"))
            {
                if(Aircraft.v1.x > 0.70710676908493042D)
                    getEnergyPastArmor((7D / Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                else
                if(Aircraft.v1.x > -0.70710676908493042D)
                    getEnergyPastArmor(5F, shot);
                else
                    getEnergyPastArmor(3F, shot);
            } else
            if(s.endsWith("a1") || s.endsWith("a2"))
                getEnergyPastArmor(3D, shot);
            if(s.startsWith("xxarmturr"))
                getEnergyPastArmor(3F, shot);
            if(s.startsWith("xxspar"))
            {
                getEnergyPastArmor(3F, shot);
                if((s.endsWith("cf1") || s.endsWith("cf2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("CF") > 1 && getEnergyPastArmor(15.9F / (float)java.lang.Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), shot) > 0.0F)
                {
                    msgCollision(this, "Tail1_D0", "Tail1_D0");
                    msgCollision(this, "WingLIn_D0", "WingLIn_D0");
                    msgCollision(this, "WingRIn_D0", "WingRIn_D0");
                }
                if((s.endsWith("t1") || s.endsWith("t2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("Tail1") > 1 && getEnergyPastArmor(15.9F / (float)java.lang.Math.sqrt(Aircraft.v1.y * Aircraft.v1.y + Aircraft.v1.z * Aircraft.v1.z), shot) > 0.0F)
                    msgCollision(this, "Tail1_D0", "Tail1_D0");
                if((s.endsWith("li1") || s.endsWith("li2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingLIn") > 1 && getEnergyPastArmor(13.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingLIn_D2", shot.initiator);
                if((s.endsWith("ri1") || s.endsWith("ri2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingRIn") > 1 && getEnergyPastArmor(10.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingRIn_D2", shot.initiator);
                if((s.endsWith("lm1") || s.endsWith("lm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingLMid") > 1 && getEnergyPastArmor(10.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingLMid_D2", shot.initiator);
                if((s.endsWith("rm1") || s.endsWith("rm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingRMid") > 1 && getEnergyPastArmor(13.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingRMid_D2", shot.initiator);
                if((s.endsWith("lo1") || s.endsWith("lo2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingLOut") > 1 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingLOut_D2", shot.initiator);
                if((s.endsWith("ro1") || s.endsWith("ro2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingROut") > 1 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "WingROut_D2", shot.initiator);
                if(s.endsWith("e1") && (point3d.y > 2.79D || point3d.y < 2.3199999999999998D) && getEnergyPastArmor(17F, shot) > 0.0F)
                    nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
                if(s.endsWith("e2") && (point3d.y < -2.79D || point3d.y > -2.3199999999999998D) && getEnergyPastArmor(17F, shot) > 0.0F)
                    nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
                if(s.endsWith("e3") && (point3d.y < -2.79D || point3d.y > -2.3199999999999998D) && getEnergyPastArmor(17F, shot) > 0.0F)
                    nextDMGLevels(3, 2, "Engine3_D0", shot.initiator);
                if((s.endsWith("k1") || s.endsWith("k2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingROut") > 1 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "Keel1_D0", shot.initiator);
                if((s.endsWith("sr1") || s.endsWith("sr2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingROut") > 1 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "StabR_D0", shot.initiator);
                if((s.endsWith("sl1") || s.endsWith("sl2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(Aircraft.v1.x) && chunkDamageVisible("WingROut") > 1 && getEnergyPastArmor(8.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    nextDMGLevels(1, 2, "StabL_D0", shot.initiator);
            }
            if(s.startsWith("xxbomb") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
            {
                FM.AS.hitTank(shot.initiator, 0, 100);
                FM.AS.hitTank(shot.initiator, 1, 100);
                FM.AS.hitTank(shot.initiator, 2, 100);
                FM.AS.hitTank(shot.initiator, 3, 100);
                msgCollision(this, "CF_D0", "CF_D0");
            }
            if(s.startsWith("xxeng"))
            {
                int i = 0;
                if(s.startsWith("xxeng2"))
                    i = 1;
                if(s.endsWith("prop"))
                {
                    int k = i;
                    if(getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.35F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, k, 3);
                }
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 190000F)
                            FM.AS.setEngineStuck(shot.initiator, i);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                            FM.AS.hitEngine(shot.initiator, i, 2);
                    }
                } else
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(1.6F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[i].getCylindersRatio() * 1.4F)
                    {
                        FM.EI.engines[i].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4000F)));
                        if(FM.AS.astateEngineStates[i] < 1)
                        {
                            FM.AS.hitEngine(shot.initiator, i, 1);
                            FM.AS.doSetEngineState(shot.initiator, i, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 900000F)
                            FM.AS.hitEngine(shot.initiator, i, 3);
                        getEnergyPastArmor(25F, shot);
                    }
                } else
                if(s.endsWith("supc") && getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                    FM.AS.setEngineSpecificDamage(shot.initiator, i, 0);
                if(getEnergyPastArmor(0.42F, shot) > 0.0F && (s.endsWith("oil1") || s.endsWith("oil2") || s.endsWith("oil3")))
                    FM.AS.hitOil(shot.initiator, i);
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(j < 4 && getEnergyPastArmor(1.8F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(FM.AS.astateTankStates[j] < 1)
                            FM.AS.hitTank(shot.initiator, j, 1);
                        if(FM.AS.astateTankStates[j] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                            FM.AS.hitTank(shot.initiator, j, 1);
                        if(shot.powerType == 3 && FM.AS.astateTankStates[j] > 1 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            FM.AS.hitTank(shot.initiator, j, 10);
                    } else
                    {
                        FM.AS.hitTank(shot.initiator, j, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 35000F)));
                    }
            }
            if(s.startsWith("xxlock"))
            {
                if(s.startsWith("xxlockr") && (s.startsWith("xxlockr1") || s.startsWith("xxlockr2")) && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                if(s.startsWith("xxlockal") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                if(s.startsWith("xxlockar") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
            }
        }
        if(s.startsWith("xxmgun"))
            if(s.endsWith("1"))
            {
                if(getEnergyPastArmor(5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(10, 0);
                    getEnergyPastArmor(11.98F, shot);
                }
            } else
            if(s.endsWith("2"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(11, 2);
                    getEnergyPastArmor(11.98F, shot);
                }
            } else
            if(s.endsWith("3"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(12, 1);
                    getEnergyPastArmor(11.98F, shot);
                }
            } else
            if(s.endsWith("4"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(13, 4);
                    getEnergyPastArmor(11.98F, shot);
                }
            } else
            if(s.endsWith("5") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
            {
                FM.AS.setJamBullets(14, 3);
                getEnergyPastArmor(11.98F, shot);
            }
        if(s.startsWith("xcf"))
        {
            setControlDamage(shot, 0);
            setControlDamage(shot, 1);
            setControlDamage(shot, 2);
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            getEnergyPastArmor(4F, shot);
        } else
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose") < 3)
                hitChunk("Nose", shot);
            if(shot.power > 200000F)
            {
                FM.AS.hitPilot(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
                FM.AS.hitPilot(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
                FM.AS.hitPilot(shot.initiator, 2, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
                FM.AS.hitPilot(shot.initiator, 3, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            if(point3d.x > 4.505000114440918D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
            else
            if(point3d.y > 0.0D)
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
            } else
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
            }
        } else
        if(s.startsWith("xtail"))
        {
            setControlDamage(shot, 1);
            setControlDamage(shot, 2);
            if(chunkDamageVisible("Tail1") < 2)
                hitChunk("Tail1", shot);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
        } else
        if(s.startsWith("xkeel1"))
        {
            setControlDamage(shot, 2);
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xkeel2"))
        {
            setControlDamage(shot, 2);
            if(chunkDamageVisible("Keel2") < 2)
                hitChunk("Keel2", shot);
        } else
        if(s.startsWith("xrudder1"))
        {
            setControlDamage(shot, 2);
            hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xrudder2"))
        {
            setControlDamage(shot, 2);
            hitChunk("Rudder2", shot);
        } else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
        else
        if(s.startsWith("xwinglin"))
        {
            setControlDamage(shot, 0);
            if(chunkDamageVisible("WingLIn") < 2)
                hitChunk("WingLIn", shot);
        } else
        if(s.startsWith("xwingrin"))
        {
            setControlDamage(shot, 0);
            if(chunkDamageVisible("WingRIn") < 2)
                hitChunk("WingRIn", shot);
        } else
        if(s.startsWith("xwinglmid"))
        {
            setControlDamage(shot, 0);
            if(chunkDamageVisible("WingLMid") < 2)
                hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            setControlDamage(shot, 0);
            if(chunkDamageVisible("WingRMid") < 2)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 2)
                hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 2)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
        else
        if(s.startsWith("xflap01"))
            hitChunk("Flap01", shot);
        else
        if(s.startsWith("xflap02"))
            hitChunk("Flap02", shot);
        else
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
        } else
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.Gears.setHydroOperable(false);
        } else
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
                FM.AS.setJamBullets(10, 0);
            if(s.startsWith("xturret2"))
                FM.AS.setJamBullets(11, 0);
            if(s.startsWith("xturret3"))
                FM.AS.setJamBullets(12, 0);
            if(s.startsWith("xturret4"))
                FM.AS.setJamBullets(13, 0);
            if(s.startsWith("xturret5"))
                FM.AS.setJamBullets(14, 0);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l = s.charAt(6) - 49;
            } else
            {
                l = s.charAt(5) - 49;
            }
            hitFlesh(l, shot, byte0);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -107F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -107F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -19F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, -19F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, 60F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, Aircraft.cvt(f, 0.1F, 1.0F, 0.0F, 60F), 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.2F, 0.0F, -65.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.2F, 0.0F, 65.5F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.2F, 0.0F, 65.5F), 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.3F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, 75F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, 75F), 0.0F);
    }

    protected void moveGear(float f)
    {
        Do217.moveGear(hierMesh(), f);
    }

    public boolean bSightAutomation;
    private boolean bSightBombDump;
    public float fSightCurDistance;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurReadyness;
    private float calibDistance;
    public boolean bPitUnfocused;
    private com.maddox.il2.ai.air.Maneuver maneuver;
    private float wheel1;
    private float wheel2;
    static final float calibrationScale[] = {
        0.0F, 0.2F, 0.4F, 0.66F, 0.86F, 1.05F, 1.2F, 1.6F
    };

    static 
    {
        java.lang.Class class1 = Do217.class;
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryGermany);
    }
}
