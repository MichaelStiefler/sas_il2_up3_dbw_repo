// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
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

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, HE_111H2, TypeBomber, TypeTransport, 
//            Aircraft, PaintScheme

public abstract class HE_111 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeTransport
{

    public HE_111()
    {
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

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1100F, -80F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 60F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -13.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 36.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, -f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -13.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 36.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, -f1 * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.HE_111.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", f, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0] - 0.055F, 0.0F, 0.1625F, 0.0F, 0.1625F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1] - 0.055F, 0.0F, 0.1625F, 0.0F, 0.1625F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 1, 1);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 2, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                    FM.AS.hitTank(this, 3, 1);
            }
            if(FM.AS.astateTankStates[0] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        for(int i = 1; i < 6; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
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
            if(this instanceof com.maddox.il2.objects.air.HE_111H2)
            {
                if(f < -35F)
                {
                    f = -35F;
                    flag = false;
                }
                if(f > 15F)
                {
                    f = 15F;
                    flag = false;
                }
                if(f1 < -27F)
                {
                    f1 = -27F;
                    flag = false;
                }
                if(f1 > 13F)
                {
                    f1 = 13F;
                    flag = false;
                }
                break;
            }
            if(f < -25F)
            {
                f = -25F;
                flag = false;
            }
            if(f > 15F)
            {
                f = 15F;
                flag = false;
            }
            if(f1 < -40F)
            {
                f1 = -40F;
                flag = false;
            }
            if(f1 > 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -42F)
            {
                f = -42F;
                flag = false;
            }
            if(f > 42F)
            {
                f = 42F;
                flag = false;
            }
            if(f1 < -3F)
            {
                f1 = -3F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 40F)
            {
                f = 40F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 46F)
            {
                f1 = 46F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f < -55F)
            {
                f = -55F;
                flag = false;
            }
            if(f > 23F)
            {
                f = 23F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 40F)
            {
                f1 = 40F;
                flag = false;
            }
            if(f1 > 55F + 0.5F * f)
            {
                f1 = 55F + 0.5F * f;
                flag = false;
            }
            break;

        case 4: // '\004'
            if(f < -23F)
            {
                f = -23F;
                flag = false;
            }
            if(f > 55F)
            {
                f = 55F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 40F)
            {
                f1 = 40F;
                flag = false;
            }
            if(f1 > 55F - 0.5F * f)
            {
                f1 = 55F - 0.5F * f;
                flag = false;
            }
            break;

        case 5: // '\005'
            if(f < -3F)
            {
                f = -3F;
                flag = false;
            }
            if(f > 3F)
            {
                f = 3F;
                flag = false;
            }
            if(f1 < -3F)
            {
                f1 = -3F;
                flag = false;
            }
            if(f1 > 3F)
            {
                f1 = 3F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
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
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 19: // '\023'
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
                if(s.endsWith("p1"))
                {
                    if(com.maddox.il2.objects.air.Aircraft.v1.z > 0.5D)
                        getEnergyPastArmor(5D / com.maddox.il2.objects.air.Aircraft.v1.z, shot);
                    else
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.93969261646270752D)
                        getEnergyPastArmor((10D / com.maddox.il2.objects.air.Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(5D / java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z), shot);
                else
                if(s.endsWith("p3a") || s.endsWith("p3b"))
                    getEnergyPastArmor((8D / java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x)) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                else
                if(s.endsWith("p4"))
                {
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.70710676908493042D)
                        getEnergyPastArmor((8D / com.maddox.il2.objects.air.Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                    else
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > -0.70710676908493042D)
                        getEnergyPastArmor(6F, shot);
                } else
                if(s.endsWith("o1") || s.endsWith("o2"))
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.70710676908493042D)
                        getEnergyPastArmor((8D / com.maddox.il2.objects.air.Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                    else
                        getEnergyPastArmor(5F, shot);
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 1);
                            debuggunnery("Evelator Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 2);
                            debuggunnery("Rudder Controls Out..");
                        }
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Ailerons Controls Out..");
                    }
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(0.1F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        debuggunnery("*** Engine1 Throttle Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        debuggunnery("*** Engine1 Prop Controls Out..");
                    }
                    break;

                case 6: // '\006'
                    if(getEnergyPastArmor(0.1F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                        debuggunnery("*** Engine2 Throttle Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                        debuggunnery("*** Engine2 Prop Controls Out..");
                    }
                    break;
                }
            }
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("cf1") || s.endsWith("cf2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("CF") > 2 && getEnergyPastArmor(19.9F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    debuggunnery("*** CF Spars Broken in Half..");
                    msgCollision(this, "Tail1_D0", "Tail1_D0");
                    msgCollision(this, "WingLIn_D0", "WingLIn_D0");
                    msgCollision(this, "WingRIn_D0", "WingRIn_D0");
                }
                if((s.endsWith("ta1") || s.endsWith("ta2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(19.9F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    debuggunnery("*** Tail1 Spars Broken in Half..");
                    msgCollision(this, "Tail1_D0", "Tail1_D0");
                }
                if((s.endsWith("li1") || s.endsWith("li2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(17.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(17.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(13.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(13.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(10.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(10.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.endsWith("e1") && (point3d.y > 2.79D || point3d.y < 2.3199999999999998D) && getEnergyPastArmor(18F, shot) > 0.0F)
                {
                    debuggunnery("*** Engine1 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
                }
                if(s.endsWith("e2") && (point3d.y < -2.79D || point3d.y > -2.3199999999999998D) && getEnergyPastArmor(18F, shot) > 0.0F)
                {
                    debuggunnery("*** Engine2 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
                }
            }
            if(s.startsWith("xxbomb") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
            {
                debuggunnery("*** Bomb Payload Detonates..");
                FM.AS.hitTank(shot.initiator, 0, 100);
                FM.AS.hitTank(shot.initiator, 1, 100);
                FM.AS.hitTank(shot.initiator, 2, 100);
                FM.AS.hitTank(shot.initiator, 3, 100);
                msgCollision(this, "CF_D0", "CF_D0");
            }
            if(s.startsWith("xxprop"))
            {
                int j = 0;
                if(s.endsWith("2"))
                    j = 1;
                if(getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.35F)
                {
                    FM.AS.setEngineSpecificDamage(shot.initiator, j, 3);
                    debuggunnery("*** Engine" + (j + 1) + " Governor Failed..");
                }
            }
            if(s.startsWith("xxengine"))
            {
                int k = 0;
                if(s.startsWith("xxengine2"))
                    k = 1;
                if(s.endsWith("base"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, k);
                            debuggunnery("*** Engine" + (k + 1) + " Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 2);
                            debuggunnery("*** Engine" + (k + 1) + " Crank Case Hit - Engine Damaged..");
                        }
                    }
                } else
                if(s.endsWith("cyl"))
                {
                    if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[k].getCylindersRatio() * 0.5F)
                    {
                        FM.EI.engines[k].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        debuggunnery("*** Engine" + (k + 1) + " Cylinders Hit, " + FM.EI.engines[k].getCylindersOperable() + "/" + FM.EI.engines[k].getCylinders() + " Left..");
                        if(FM.AS.astateEngineStates[k] < 1)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 1);
                            FM.AS.doSetEngineState(shot.initiator, k, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 960000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 3);
                            debuggunnery("*** Engine" + (k + 1) + " Cylinders Hit - Engine Fires..");
                        }
                        getEnergyPastArmor(25F, shot);
                    }
                } else
                if(s.endsWith("sup") && getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                {
                    FM.AS.setEngineSpecificDamage(shot.initiator, k, 0);
                    debuggunnery("*** Engine" + (k + 1) + " Supercharger Out..");
                }
            }
            if(s.startsWith("xxoil"))
            {
                int l = 0;
                if(s.endsWith("2"))
                    l = 1;
                if(getEnergyPastArmor(0.21F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, l);
                    getEnergyPastArmor(0.42F, shot);
                }
            }
            if(s.startsWith("xxtank"))
            {
                int i1 = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(FM.AS.astateTankStates[i1] == 0)
                        {
                            FM.AS.hitTank(shot.initiator, i1, 1);
                            FM.AS.doSetTankState(shot.initiator, i1, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                            FM.AS.hitTank(shot.initiator, i1, 1);
                        if(shot.powerType == 3 && FM.AS.astateTankStates[i1] > 2 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.4F)
                            FM.AS.hitTank(shot.initiator, i1, 10);
                    } else
                    {
                        FM.AS.hitTank(shot.initiator, i1, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 56000F)));
                    }
            }
            return;
        }
        if(s.startsWith("xoil"))
        {
            if(s.equals("xoil1"))
            {
                FM.AS.hitOil(shot.initiator, 0);
                s = "xengine1";
            }
            if(s.equals("xoil2"))
            {
                FM.AS.hitOil(shot.initiator, 1);
                s = "xengine2";
            }
        }
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose1") < 2)
                hitChunk("Nose1", shot);
            if(shot.power > 200000F)
            {
                FM.AS.hitPilot(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
                FM.AS.hitPilot(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(3, 192));
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
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder"))
            hitChunk("Rudder1", shot);
        else
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
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
        } else
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
        } else
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
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
            {
                debuggunnery("*** Gear Hydro Failed..");
                FM.Gears.setHydroOperable(false);
            }
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
            int j1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j1 = s.charAt(6) - 49;
            } else
            {
                j1 = s.charAt(5) - 49;
            }
            hitFlesh(j1, shot, byte0);
        }
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            if(bPitUnfocused && !FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_FAK", false);
                hierMesh().chunkVisible("Pilot1_FAL", true);
                hierMesh().chunkVisible("Head1_FAK", false);
            }
            if(FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot1_FAK", false);
                hierMesh().chunkVisible("Pilot1_FAL", false);
                hierMesh().chunkVisible("Head1_FAK", false);
            }
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(bPitUnfocused && !FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot2_FAK", false);
                hierMesh().chunkVisible("Pilot2_FAL", true);
                hierMesh().chunkVisible("HMask2_D0", false);
            }
            if(FM.AS.bIsAboutToBailout)
            {
                hierMesh().chunkVisible("Pilot2_FAK", false);
                hierMesh().chunkVisible("Pilot2_FAL", false);
                hierMesh().chunkVisible("HMask2_D0", false);
            }
            FM.turret[0].bIsOperable = false;
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("HMask3_D0", false);
            FM.turret[1].bIsOperable = false;
            if(FM.turret.length == 6)
                FM.turret[5].bIsOperable = false;
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            hierMesh().chunkVisible("HMask4_D0", false);
            FM.turret[2].bIsOperable = false;
            break;

        case 4: // '\004'
            hierMesh().chunkVisible("Pilot5_D0", false);
            hierMesh().chunkVisible("Pilot5_D1", true);
            hierMesh().chunkVisible("HMask5_D0", false);
            FM.turret[3].bIsOperable = false;
            FM.turret[4].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(hierMesh().isChunkVisible("Nose_D0") || hierMesh().isChunkVisible("Nose_D1") || hierMesh().isChunkVisible("Nose_D2") || hierMesh().isChunkVisible("Nose_D3"))
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            if(hierMesh().isChunkVisible("Nose_D0") || hierMesh().isChunkVisible("Nose_D1") || hierMesh().isChunkVisible("Nose_D2") || hierMesh().isChunkVisible("Nose_D3"))
                hierMesh().chunkVisible("Gore2_D0", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Gore3_D0", true);
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

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
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
