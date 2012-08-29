// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_46.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintScheme

public abstract class KI_46 extends com.maddox.il2.objects.air.Scheme2
{

    public KI_46()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        xyz[0] = xyz[1] = xyz[2] = ypr[0] = ypr[1] = ypr[2] = 0.0F;
        xyz[1] = 0.415F * f;
        hiermesh.chunkSetLocate("GearC2_D0", xyz, ypr);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.01F, 0.05F, 0.0F, -50F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.01F, 0.05F, 0.0F, -50F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.05F, 0.88F, 0.0F, -120F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.05F, 0.11F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.05F, 0.11F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.18F, 0.98F, 0.0F, -120F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.18F, 0.24F, 0.0F, -70F), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(f, 0.18F, 0.24F, 0.0F, -70F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.KI_46.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        resetYPRmodifier();
        xyz[1] = 0.415F * FM.CT.getGear();
        ypr[1] = f;
        hierMesh().chunkSetLocate("GearC2_D0", xyz, ypr);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.16F, 0.0F, 0.16F);
        hierMesh().chunkSetLocate("GearL3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.16F, 0.0F, -32F), 0.0F);
        hierMesh().chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.16F, 0.0F, -64F), 0.0F);
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.16F, 0.0F, 0.16F);
        hierMesh().chunkSetLocate("GearR3_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.16F, 0.0F, -32F), 0.0F);
        hierMesh().chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.KI_46.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.16F, 0.0F, -64F), 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
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
            FM.AS.hitEngine(this, 0, 3);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 0, 1);
            break;

        case 37: // '%'
            FM.AS.hitEngine(this, 1, 3);
            if(com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 66)
                FM.AS.hitEngine(this, 1, 1);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxcontrol"))
            {
                int i = s.charAt(9) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
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

                case 2: // '\002'
                case 3: // '\003'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Ailerons Controls Out..");
                    }
                    break;
                }
            }
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(5) - 49;
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
                if(s.endsWith("gear"))
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
                if(s.endsWith("oil1") && getEnergyPastArmor(4.21F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, j);
                    getEnergyPastArmor(0.42F, shot);
                }
                return;
            }
            if(s.startsWith("xxfuel"))
            {
                int i1 = s.charAt(6) - 48;
                int k;
                switch(i1)
                {
                case 1: // '\001'
                default:
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F)
                        hitBone("xxfuel2", shot, point3d);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F)
                        hitBone("xxfuel3", shot, point3d);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F)
                        hitBone("xxfuel5", shot, point3d);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F)
                        hitBone("xxfuel6", shot, point3d);
                    return;

                case 2: // '\002'
                    k = 1;
                    break;

                case 3: // '\003'
                    k = com.maddox.il2.ai.World.Rnd().nextInt(0, 1);
                    break;

                case 4: // '\004'
                    k = 0;
                    break;

                case 5: // '\005'
                    k = 2;
                    break;

                case 6: // '\006'
                    k = com.maddox.il2.ai.World.Rnd().nextInt(2, 3);
                    break;

                case 7: // '\007'
                    k = 3;
                    break;
                }
                if(getEnergyPastArmor(0.6F, shot) > 0.0F)
                {
                    if(FM.AS.astateTankStates[k] == 0)
                    {
                        FM.AS.hitTank(shot.initiator, k, 2);
                        FM.AS.doSetTankState(shot.initiator, k, 2);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F || shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        FM.AS.hitTank(shot.initiator, k, 2);
                }
                return;
            }
            if(s.startsWith("xxgun"))
            {
                int l = s.charAt(5) - 49;
                if(getEnergyPastArmor(6.8F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.setJamBullets(1, l);
                getEnergyPastArmor(22.7F, shot);
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
                if(s.startsWith("xxlocksl"))
                {
                    if(getEnergyPastArmor(4.32F, shot) > 0.0F)
                    {
                        debuggunnery("*** VatorL Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorL_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlocksr"))
                {
                    if(getEnergyPastArmor(4.32F, shot) > 0.0F)
                    {
                        debuggunnery("*** VatorR Lock Damaged..");
                        nextDMGLevels(1, 2, "VatorR_D0", shot.initiator);
                    }
                } else
                if(s.startsWith("xxlockk") && getEnergyPastArmor(4.32F, shot) > 0.0F)
                {
                    debuggunnery("*** Rudder1 Lock Damaged..");
                    nextDMGLevels(1, 2, "Rudder1_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxpar"))
            {
                if(s.startsWith("xxpark") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(12.699999809265137D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                }
                if(s.startsWith("xxparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxparrs") && chunkDamageVisible("StabL") > 1 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** StabL Spars Damaged..");
                    nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
                }
                if(s.startsWith("xxparls") && chunkDamageVisible("StabR") > 1 && getEnergyPastArmor(12.800000190734863D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** StabR Spars Damaged..");
                    nextDMGLevels(1, 2, "StabR_D2", shot.initiator);
                }
                return;
            } else
            {
                return;
            }
        }
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
            if(chunkDamageVisible("Rudder1") < 1)
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
            if(chunkDamageVisible("VatorL") < 1)
                hitChunk("VatorL", shot);
        } else
        if(s.startsWith("xvatorr"))
        {
            if(chunkDamageVisible("VatorR") < 1)
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
            if(chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
        } else
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 1)
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

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_46.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
    }
}
