// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IL_4.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintScheme

public abstract class IL_4 extends com.maddox.il2.objects.air.Scheme2
{

    public IL_4()
    {
        tme = 0L;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1100F, -75F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 100F * f);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, -125F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 100F * f);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, -125F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.IL_4.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    FM.AS.hitTank(this, 0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    FM.AS.hitTank(this, 1, 1);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    FM.AS.hitTank(this, 2, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                    FM.AS.hitTank(this, 3, 1);
            }
            if(FM.AS.astateTankStates[0] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                FM.AS.hitTank(this, 2, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        if(FM.getAltitude() < 3000F)
        {
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("HMask2a_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("HMask3a_D0", false);
        } else
        {
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
            hierMesh().chunkVisible("HMask2a_D0", hierMesh().isChunkVisible("Pilot2a_D0"));
            hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
            hierMesh().chunkVisible("HMask3a_D0", hierMesh().isChunkVisible("Pilot3a_D0"));
        }
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
            if(f < -52F)
            {
                f = -52F;
                flag = false;
            }
            if(f > 52F)
            {
                f = 52F;
                flag = false;
            }
            if(f1 < -60F)
            {
                f1 = -60F;
                flag = false;
            }
            if(f1 > 90F)
            {
                f1 = 90F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < 2.0F && f > -2F && f1 < 25F)
                flag = false;
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 99F)
            {
                f1 = 99F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -25F)
            {
                f = -25F;
                flag = false;
            }
            if(f > 25F)
            {
                f = 25F;
                flag = false;
            }
            if(f1 < -60F)
            {
                f1 = -60F;
                flag = false;
            }
            if(f1 > 4F)
            {
                f1 = 4F;
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
        case 35: // '#'
        default:
            break;

        case 33: // '!'
            hitProp(0, j, actor);
            break;

        case 36: // '$'
            hitProp(1, j, actor);
            break;

        case 34: // '"'
            FM.AS.hitEngine(this, 0, 2);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 0, 2);
            break;

        case 37: // '%'
            FM.AS.hitEngine(this, 1, 2);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 1, 2);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(12.5F, 16F), shot);
                else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(1.0099999904632568D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p3"))
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(12.5F, 16F), shot);
                else
                if(s.endsWith("p4"))
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(12.5F, 16F), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                if(s.endsWith("10"))
                    i = 10;
                else
                if(s.endsWith("11"))
                    i = 11;
                else
                if(s.endsWith("12"))
                    i = 12;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        debuggunnery("*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                    break;

                case 3: // '\003'
                    if(getEnergyPastArmor(2.2F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    break;

                case 4: // '\004'
                    if(getEnergyPastArmor(2.2F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(2.2F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 7);
                    break;

                case 6: // '\006'
                case 7: // '\007'
                    if(getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("*** Aileron Controls: Disabled..");
                    }
                    break;

                case 8: // '\b'
                    if(getEnergyPastArmor(0.12F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                    break;

                case 9: // '\t'
                    if(getEnergyPastArmor(0.12F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 7);
                    break;

                case 10: // '\n'
                case 11: // '\013'
                    if(getEnergyPastArmor(0.002F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("*** Elevator Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 12: // '\f'
                    if(getEnergyPastArmor(2.3F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("*** Rudder Controls: Disabled..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(26.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if((s.endsWith("e1") || s.endsWith("e2")) && getEnergyPastArmor(32F, shot) > 0.0F)
                {
                    debuggunnery("*** Engine1 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
                }
                if((s.endsWith("e3") || s.endsWith("e4")) && getEnergyPastArmor(32F, shot) > 0.0F)
                {
                    debuggunnery("*** Engine2 Suspension Broken in Half..");
                    nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                if(s.startsWith("xxlockal"))
                {
                    if(getEnergyPastArmor(4.35F, shot) > 0.0F)
                    {
                        debuggunnery("*** AroneL Lock Damaged..");
                        nextDMGLevels(1, 2, "AroneL_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlockar"))
                {
                    if(getEnergyPastArmor(4.35F, shot) > 0.0F)
                    {
                        debuggunnery("*** AroneR Lock Damaged..");
                        nextDMGLevels(1, 2, "AroneR_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlockvl"))
                {
                    if(getEnergyPastArmor(4.32F, shot) > 0.0F)
                    {
                        debuggunnery("*** VatorL Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorL_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlockvr"))
                {
                    if(getEnergyPastArmor(4.32F, shot) > 0.0F)
                    {
                        debuggunnery("*** VatorR Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorR_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlockr") && getEnergyPastArmor(4.32F, shot) > 0.0F)
                {
                    debuggunnery("*** Rudder1 Lock Damaged..");
                    nextDMGLevels(1, 2, "Rudder1_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxbomb"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F && FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0].haveBullets())
                {
                    debuggunnery("*** Bomb Payload Detonates.. CF_D" + chunkDamageVisible("CF"));
                    FM.AS.hitTank(shot.initiator, 0, 100);
                    FM.AS.hitTank(shot.initiator, 1, 100);
                    FM.AS.hitTank(shot.initiator, 2, 100);
                    FM.AS.hitTank(shot.initiator, 3, 100);
                    nextDMGLevels(3, 2, "CF_D" + chunkDamageVisible("CF"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(5) - 49;
                if(s.endsWith("prop") && getEnergyPastArmor(1.2F, shot) > 0.0F)
                    FM.EI.engines[j].setKillPropAngleDevice(shot.initiator);
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(1.7F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, j);
                            debuggunnery("*** Engine" + j + " Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 2);
                            debuggunnery("*** Engine" + j + " Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.04F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[j].setReadyness(shot.initiator, FM.EI.engines[j].getReadyness() - 0.02F);
                        debuggunnery("*** Engine" + j + " Crank Case Hit - Readyness Reduced to " + FM.EI.engines[j].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(0.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[j].getCylindersRatio() * 0.9878F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        debuggunnery("*** Engine" + j + " Cylinders Hit, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            debuggunnery("*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.05F, shot) > 0.0F)
                        FM.EI.engines[j].setKillCompressor(shot.initiator);
                    getEnergyPastArmor(2.0F, shot);
                }
                if(s.endsWith("eqpt"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        if(Pd.y > 0.0D && Pd.z < 0.18899999558925629D && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 16000F) < shot.power)
                            FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 0);
                        if(Pd.y < 0.0D && Pd.z < 0.18899999558925629D && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 16000F) < shot.power)
                            FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 1);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 4);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 0);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 6);
                        if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 26700F) < shot.power)
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 1);
                    }
                    getEnergyPastArmor(2.0F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 0);
                    debuggunnery("*** Engine" + j + " Magneto 1 Destroyed..");
                }
                if(s.endsWith("mag2"))
                {
                    FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, 1);
                    debuggunnery("*** Engine" + j + " Magneto 2 Destroyed..");
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                int k = s.charAt(5) - 49;
                if(getEnergyPastArmor(4.21F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, k);
                    getEnergyPastArmor(0.42F, shot);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int l = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.6F, shot) > 0.0F)
                {
                    if(FM.AS.astateTankStates[l] == 0)
                    {
                        FM.AS.hitTank(shot.initiator, l, 1);
                        FM.AS.doSetTankState(shot.initiator, l, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.6F)
                        FM.AS.hitTank(shot.initiator, l, 2);
                }
                return;
            }
            if(s.startsWith("xxammo"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.setJamBullets(10, 0);
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 12.39F), shot) > 0.0F)
                {
                    debuggunnery("Pneumo System: Disabled..");
                    FM.AS.setInternalDamage(shot.initiator, 1);
                }
                return;
            } else
            {
                return;
            }
        }
        if(!s.startsWith("xcockpit"));
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
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
        {
            if(chunkDamageVisible("Rudder1") < 2)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
        {
            if(chunkDamageVisible("VatorL") < 2)
                hitChunk("VatorL", shot);
        } else
        if(s.startsWith("xvatorr"))
        {
            if(chunkDamageVisible("VatorR") < 2)
                hitChunk("VatorR", shot);
        } else
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
        {
            if(chunkDamageVisible("AroneL") < 2)
                hitChunk("AroneL", shot);
        } else
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 2)
                hitChunk("AroneR", shot);
        } else
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
            if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(12.88F, 16.96F), shot) > 0.0F)
            {
                debuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
            {
                debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
                FM.AS.setJamBullets(10, 0);
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 66.35F), shot);
            }
            if(s.startsWith("xturret2"))
            {
                debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
                FM.AS.setJamBullets(11, 0);
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 66.35F), shot);
            }
            if(s.startsWith("xturret3"))
            {
                debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
                FM.AS.setJamBullets(12, 0);
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 66.35F), shot);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i1;
            if(s.endsWith("a") || s.endsWith("a2"))
            {
                byte0 = 1;
                i1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b") || s.endsWith("b2"))
            {
                byte0 = 2;
                i1 = s.charAt(6) - 49;
            } else
            {
                i1 = s.charAt(5) - 49;
            }
            hitFlesh(i1, shot, byte0);
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null && explosion.power > 0.0F)
        {
            if(explosion.chunkName.equals("Tail1_D3"))
                return;
            if(explosion.chunkName.equals("WingLIn_D3"))
                return;
            if(explosion.chunkName.equals("WingRIn_D3"))
                return;
            if(explosion.chunkName.equals("WingLMid_D3"))
                return;
            if(explosion.chunkName.equals("WingRMid_D3"))
                return;
            if(explosion.chunkName.equals("WingLOut_D3"))
                return;
            if(explosion.chunkName.equals("WingROut_D3"))
                return;
            if(explosion.chunkName.equals("Engine1_D2"))
                return;
            if(explosion.chunkName.equals("Engine2_D2"))
                return;
        }
        super.msgExplosion(explosion);
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
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            if(hierMesh().isChunkVisible("Pilot2_D0"))
            {
                hierMesh().chunkVisible("Pilot2_D0", false);
                hierMesh().chunkVisible("Pilot2_D1", true);
            } else
            {
                hierMesh().chunkVisible("Pilot2a_D0", false);
                hierMesh().chunkVisible("Pilot2a_D0", true);
            }
            break;

        case 2: // '\002'
            if(hierMesh().isChunkVisible("Pilot3_D0"))
            {
                hierMesh().chunkVisible("Pilot3_D0", false);
                hierMesh().chunkVisible("Pilot3_D1", true);
            } else
            {
                hierMesh().chunkVisible("Pilot3a_D0", false);
                hierMesh().chunkVisible("Pilot3a_D0", true);
            }
            break;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
    }

    public void typeBomberAdjSpeedMinus()
    {
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay1_D0", 0.0F, 90F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay2_D0", 0.0F, -90F * f, 0.0F);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        setRadist(0, 0);
        setRadist(1, 0);
        setRadist(2, 0);
        hierMesh().chunkVisible("Turret3a_D0", false);
    }

    public void update(float f)
    {
        if(com.maddox.rts.Time.current() > tme)
        {
            tme = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(1000L, 5000L);
            if(FM.turret.length != 0)
            {
                Object obj = null;
                if(FM.turret[0].bIsOperable != (radist[1] == 0))
                {
                    com.maddox.il2.engine.Actor actor = FM.turret[0].target;
                    if(actor != null)
                    {
                        setRadist(1, 1);
                    } else
                    {
                        com.maddox.il2.engine.Actor actor1 = FM.turret[1].target;
                        if(actor1 == null)
                            actor1 = FM.turret[2].target;
                        if(actor1 != null)
                        {
                            setRadist(1, 1);
                            FM.turret[0].target = actor1;
                        } else
                        {
                            setRadist(1, 0);
                        }
                    }
                }
                if(FM.turret[1].bIsOperable)
                {
                    com.maddox.il2.engine.Actor actor2 = FM.turret[1].target;
                    if(actor2 != null && com.maddox.il2.engine.Actor.isValid(actor2))
                    {
                        pos.getAbs(tmpLoc2);
                        actor2.pos.getAbs(tmpLoc3);
                        tmpLoc2.transformInv(tmpLoc3.getPoint());
                        if(tmpLoc3.getPoint().z < 0.0D)
                            setRadist(2, 1);
                    }
                } else
                if(FM.turret[2].bIsOperable)
                {
                    com.maddox.il2.engine.Actor actor3 = FM.turret[2].target;
                    if(actor3 != null && com.maddox.il2.engine.Actor.isValid(actor3))
                    {
                        pos.getAbs(tmpLoc2);
                        actor3.pos.getAbs(tmpLoc3);
                        tmpLoc2.transformInv(tmpLoc3.getPoint());
                        if(tmpLoc3.getPoint().z > 0.0D)
                            setRadist(2, 0);
                    }
                }
            }
        }
        super.update(f);
    }

    private void setRadist(int i, int j)
    {
        radist[i] = j;
        if(FM.AS.astatePilotStates[i] > 90)
            return;
        switch(i)
        {
        default:
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2a_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("HMask2a_D0", false);
            FM.turret[0].bIsOperable = false;
            switch(j)
            {
            case 0: // '\0'
                hierMesh().chunkVisible("Pilot2_D0", true);
                hierMesh().chunkVisible("HMask2_D0", FM.Loc.z > 3000D);
                break;

            case 1: // '\001'
                hierMesh().chunkVisible("Pilot2a_D0", true);
                hierMesh().chunkVisible("HMask2a_D0", FM.Loc.z > 3000D);
                FM.turret[0].bIsOperable = true;
                break;
            }
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3a_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("HMask3a_D0", false);
            FM.turret[1].bIsOperable = false;
            FM.turret[2].bIsOperable = false;
            switch(j)
            {
            case 0: // '\0'
                hierMesh().chunkVisible("Pilot3_D0", true);
                hierMesh().chunkVisible("HMask3_D0", FM.Loc.z > 3000D);
                FM.turret[1].bIsOperable = true;
                break;

            case 1: // '\001'
                hierMesh().chunkVisible("Pilot3a_D0", true);
                hierMesh().chunkVisible("HMask3a_D0", FM.Loc.z > 3000D);
                FM.turret[2].bIsOperable = true;
                break;
            }
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private long tme;
    private int radist[] = {
        0, 0, 0
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.IL_4.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }
}
