// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111Z.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme5, PaintSchemeBMPar02, TypeTransport, Aircraft, 
//            NetAircraft, PaintScheme

public class HE_111Z extends com.maddox.il2.objects.air.Scheme5
    implements com.maddox.il2.objects.air.TypeTransport
{

    public HE_111Z()
    {
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1") || s.endsWith("p6"))
                {
                    if(com.maddox.il2.objects.air.Aircraft.v1.z > 0.5D)
                        getEnergyPastArmor(5D / com.maddox.il2.objects.air.Aircraft.v1.z, shot);
                    else
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.93969261646270752D)
                        getEnergyPastArmor((10D / com.maddox.il2.objects.air.Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot);
                } else
                if(s.endsWith("p2") || s.endsWith("p7"))
                    getEnergyPastArmor(5D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p3a") || s.endsWith("p3b") || s.endsWith("p8a") || s.endsWith("p8b"))
                    getEnergyPastArmor(8D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p4") || s.endsWith("p9"))
                {
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.70710676908493042D)
                        getEnergyPastArmor(8D / (com.maddox.il2.objects.air.Aircraft.v1.x * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F) + 0.0010000000474974513D), shot);
                    else
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > -0.70710676908493042D)
                        getEnergyPastArmor(6F, shot);
                } else
                if(s.endsWith("o1") || s.endsWith("o2") || s.endsWith("o3") || s.endsWith("o4") || s.endsWith("o5"))
                    if(com.maddox.il2.objects.air.Aircraft.v1.x > 0.70710676908493042D)
                        getEnergyPastArmor(8D / (com.maddox.il2.objects.air.Aircraft.v1.x * (double)com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F) + 9.9999997473787516E-005D), shot);
                    else
                        getEnergyPastArmor(5F, shot);
            } else
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                case 8: // '\b'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            debuggunnery("Controls: Evelator Controls Out..");
                            FM.AS.setControlsDamage(shot.initiator, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                        {
                            debuggunnery("Controls:  Rudder Controls Out..");
                            FM.AS.setControlsDamage(shot.initiator, 2);
                        }
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                case 7: // '\007'
                    if(getEnergyPastArmor(1.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Ailerons Controls Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;
                }
            } else
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("ta1") || s.endsWith("ta2")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(12.9F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    debuggunnery("Tail1 Spars Broken in Half..");
                    nextDMGLevels(3, 2, "Tail1_D3", shot.initiator);
                }
                if((s.endsWith("ta3") || s.endsWith("ta4")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && chunkDamageVisible("Tail2") > 2 && getEnergyPastArmor(12.9F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    debuggunnery("Tail2 Spars Broken in Half..");
                    nextDMGLevels(3, 2, "Tail2_D3", shot.initiator);
                }
                if((s.endsWith("li1") || s.endsWith("li2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if((s.endsWith("k1") || s.endsWith("k2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("Keel1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                }
                if((s.endsWith("k3") || s.endsWith("k4")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(12.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("Keel2 Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel2_D2", shot.initiator);
                }
            } else
            if(s.startsWith("xxengine"))
            {
                int j = s.charAt(8) - 49;
                if(s.endsWith("prop"))
                {
                    if(getEnergyPastArmor(2.0F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Engine" + (j + 1) + " Governor Failed..");
                        FM.AS.setEngineSpecificDamage(shot.initiator, j, 3);
                    }
                } else
                if(s.endsWith("base"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 200000F)
                        {
                            debuggunnery("Engine" + (j + 1) + " Crank Case Hit - Engine Stucks..");
                            FM.AS.setEngineStuck(shot.initiator, j);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            debuggunnery("Engine" + (j + 1) + " Crank Case Hit - Engine Damaged..");
                            FM.AS.hitEngine(shot.initiator, j, 2);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 28000F)
                        {
                            FM.EI.engines[j].setCyliderKnockOut(shot.initiator, 1);
                            debuggunnery("Engine" + (j + 1) + " Crank Case Hit - Cylinder Feed Out, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                        }
                        FM.EI.engines[j].setReadyness(shot.initiator, FM.EI.engines[j].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        debuggunnery("Engine" + (j + 1) + " Crank Case Hit - Readyness Reduced to " + FM.EI.engines[j].getReadyness() + "..");
                    }
                } else
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[j].getCylindersRatio() * 1.75F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        debuggunnery("Engine" + (j + 1) + " Cylinders Hit, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                        if(FM.AS.astateEngineStates[j] < 1)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 1);
                            FM.AS.doSetEngineState(shot.initiator, j, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            debuggunnery("Engine" + (j + 1) + " Cylinders Hit - Engine Fires..");
                            FM.AS.hitEngine(shot.initiator, j, 3);
                        }
                        getEnergyPastArmor(25F, shot);
                    }
                } else
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.05F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.89F)
                    {
                        debuggunnery("Engine" + (j + 1) + " Supercharger Out..");
                        FM.AS.setEngineSpecificDamage(shot.initiator, j, 0);
                    }
                } else
                if(s.endsWith("oil1") && getEnergyPastArmor(0.21F, shot) > 0.0F)
                {
                    FM.AS.hitOil(shot.initiator, j);
                    getEnergyPastArmor(0.42F, shot);
                }
            } else
            if(!s.startsWith("xxoil") && s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(FM.AS.astateTankStates[k] == 0)
                        {
                            FM.AS.hitTank(shot.initiator, k, 1);
                            FM.AS.doSetTankState(shot.initiator, k, 1);
                        }
                        if(FM.AS.astateTankStates[k] < 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                            FM.AS.hitTank(shot.initiator, k, 1);
                        if(shot.powerType == 3 && FM.AS.astateTankStates[k] > 2 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.03F)
                            FM.AS.hitTank(shot.initiator, k, 10);
                    } else
                    {
                        FM.AS.hitTank(shot.initiator, k, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 20000F)));
                    }
            }
        } else
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose") < 3)
                hitChunk("Nose", shot);
        } else
        if(s.startsWith("xtail1"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xtail2"))
        {
            if(chunkDamageVisible("Tail2") < 3)
                hitChunk("Tail2", shot);
        } else
        if(s.startsWith("xkeel1"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xkeel2"))
        {
            if(chunkDamageVisible("Keel2") < 2)
                hitChunk("Keel2", shot);
        } else
        if(s.startsWith("xrudder1"))
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xrudder2"))
            hitChunk("Rudder2", shot);
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
        if(s.startsWith("xengine"))
        {
            int l = s.charAt(7) - 49;
            if(chunkDamageVisible("Engine" + (l + 1)) < 2)
                hitChunk("Engine" + (l + 1), shot);
            FM.EI.engines[l].setReadyness(shot.initiator, FM.EI.engines[l].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 128000.9F));
            debuggunnery("Engine" + (l + 1) + " Hit - Readyness Reduced to " + FM.EI.engines[l].getReadyness() + "..");
        } else
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            {
                debuggunnery("Gear Hydro Failed..");
                FM.Gears.setHydroOperable(false);
            }
        } else
        if(s.startsWith("xturret"))
        {
            int i1 = s.charAt(7) - 49;
            FM.AS.setJamBullets(10 + i1, 0);
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

    public void hitProp(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        super.hitProp(i, j, actor);
        if(i == 1 || i == 2)
            super.hitProp(4, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.hitTank(this, 0, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.hitTank(this, 1, 1);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.hitTank(this, 2, 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.hitTank(this, 3, 1);
            }
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.07F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        for(int i = 1; i < 5; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

        for(int j = 7; j < 10; j++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + j + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + j + "_D0", hierMesh().isChunkVisible("Pilot" + j + "_D0"));

    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1100F, -80F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 60F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -13.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 36.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, -f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearL9_D0", 0.0F, -f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearL10_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -13.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 36.5F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, -f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR9_D0", 0.0F, f1 * f, 0.0F);
        hiermesh.chunkSetAngles("GearR10_D0", 0.0F, -f1 * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.HE_111Z.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -40F * f;
        hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap3_D0", 0.0F, f1, 0.0F);
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
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
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
            if(f1 > 36F)
            {
                f1 = 36F;
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
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 4: // '\004'
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

        case 5: // '\005'
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
            if(f1 < 0.0F)
            {
                f1 = 0.0F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 6: // '\006'
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
            if(f1 > 36F)
            {
                f1 = 36F;
                flag = false;
            }
            break;

        case 7: // '\007'
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
            if(f1 > 45F)
            {
                f1 = 45F;
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
        case 37: // '%'
            hitProp(3, j, actor);
            super.cutFM(10, j, actor);
            break;

        case 11: // '\013'
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 19: // '\023'
            hierMesh().chunkVisible("Wire_D0", false);
            // fall through

        case 20: // '\024'
            cut("GearC2");
            break;

        case 33: // '!'
            hitProp(0, j, actor);
            hitProp(1, j, actor);
            // fall through

        case 36: // '$'
            hitProp(2, j, actor);
            hitProp(4, j, actor);
            // fall through

        case 13: // '\r'
            killPilot(actor, 5);
            killPilot(actor, 6);
            killPilot(actor, 7);
            killPilot(actor, 8);
            super.cutFM(36, j, actor);
            super.cutFM(13, j, actor);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            break;

        case 2: // '\002'
            FM.turret[1].bIsOperable = false;
            break;

        case 3: // '\003'
            FM.turret[2].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[3].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[4].bIsOperable = false;
            break;

        case 7: // '\007'
            FM.turret[5].bIsOperable = false;
            break;

        case 8: // '\b'
            FM.turret[6].bIsOperable = false;
            break;

        case 9: // '\t'
            FM.turret[7].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 4: // '\004'
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            if(isChunkAnyDamageVisible("CF"))
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(isChunkAnyDamageVisible("CF"))
                hierMesh().chunkVisible("Gore2_D0", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            if(isChunkAnyDamageVisible("CF"))
                hierMesh().chunkVisible("Gore3_D0", true);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            break;

        case 5: // '\005'
            if(isChunkAnyDamageVisible("Nose"))
                hierMesh().chunkVisible("Gore4_D0", true);
            break;

        case 6: // '\006'
            hierMesh().chunkVisible("Pilot7_D0", false);
            hierMesh().chunkVisible("Pilot7_D1", true);
            if(isChunkAnyDamageVisible("Nose"))
                hierMesh().chunkVisible("Gore5_D0", true);
            break;

        case 7: // '\007'
            hierMesh().chunkVisible("Pilot8_D0", false);
            hierMesh().chunkVisible("Pilot8_D1", true);
            if(isChunkAnyDamageVisible("Nose"))
                hierMesh().chunkVisible("Gore6_D0", true);
            break;

        case 8: // '\b'
            hierMesh().chunkVisible("Pilot9_D0", false);
            hierMesh().chunkVisible("Pilot9_D1", true);
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111Z.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Zwilling");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/He-111Z/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(class1, "yearService", 1941.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111Z.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111Z.class, com.maddox.il2.objects.air.CockpitHE_111Z_RNGunner.class, com.maddox.il2.objects.air.CockpitHE_111Z_LNGunner.class, com.maddox.il2.objects.air.CockpitHE_111Z_RTGunner.class, com.maddox.il2.objects.air.CockpitHE_111Z_LTGunner.class, com.maddox.il2.objects.air.CockpitHE_111Z_RBGunner.class, com.maddox.il2.objects.air.CockpitHE_111Z_LBGunner.class
        });
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15, 16, 17
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15t 150", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMGFFt 450", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
