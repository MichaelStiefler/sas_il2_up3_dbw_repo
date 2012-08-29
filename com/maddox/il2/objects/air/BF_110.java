// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_110.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.MGunMK108ki;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2a, BF_110G2, TypeFighter, TypeBNZFighter, 
//            TypeStormovik, Aircraft, PaintScheme

public abstract class BF_110 extends com.maddox.il2.objects.air.Scheme2a
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeStormovik
{

    public BF_110()
    {
        kangle0 = 0.0F;
        kangle1 = 0.0F;
        slpos = 0.0F;
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 30F * f, 0.0F);
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            slpos = 0.7F * slpos + 0.13F * (FM.getAOA() <= 6.6F ? 0.0F : 0.07F);
            resetYPRmodifier();
            com.maddox.il2.objects.air.Aircraft.xyz[0] = slpos;
            hierMesh().chunkSetLocate("SlatL_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            hierMesh().chunkSetLocate("SlatR_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        }
        hierMesh().chunkSetAngles("WaterL_D0", 0.0F, 15F - 30F * kangle0, 0.0F);
        kangle0 = 0.95F * kangle0 + 0.05F * FM.EI.engines[0].getControlRadiator();
        hierMesh().chunkSetAngles("WaterR_D0", 0.0F, 15F - 30F * kangle1, 0.0F);
        kangle1 = 0.95F * kangle1 + 0.05F * FM.EI.engines[1].getControlRadiator();
        super.update(f);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("p1"))
                {
                    if(java.lang.Math.abs(point3d.y) > 0.23100000000000001D)
                        getEnergyPastArmor(8.5850000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.y) + 9.9999997473787516E-005D), shot);
                    else
                        getEnergyPastArmor(1.0F, shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.96F, 3.4839F), shot);
                else
                if(s.endsWith("p3"))
                {
                    if(point3d.z < 0.080000000000000002D)
                    {
                        getEnergyPastArmor(8.5850000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    } else
                    {
                        if(point3d.z < 0.089999999999999997D)
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                        if(point3d.y > 0.17499999999999999D && point3d.y < 0.28699999999999998D && point3d.z < 0.17699999999999999D)
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                        if(point3d.y > -0.33400000000000002D && point3d.y < -0.17699999999999999D && point3d.z < 0.20399999999999999D)
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                        if(point3d.z > 0.28799999999999998D && java.lang.Math.abs(point3d.y) < 0.076999999999999999D)
                            getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(8.5F, 12.46F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                        else
                            getEnergyPastArmor(10.510000228881836D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    }
                } else
                if(s.endsWith("p4"))
                {
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(20F, 30F), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    debuggunnery("Armor: Armor Glass: Hit..");
                    if(shot.power <= 0.0F)
                    {
                        debuggunnery("Armor: Armor Glass: Bullet Stopped..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.96F)
                            doRicochetBack(shot);
                    }
                } else
                if(s.endsWith("p5"))
                    getEnergyPastArmor(5.5100002288818359D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p6"))
                {
                    if(point3d.z > 0.44800000000000001D)
                    {
                        if(point3d.z > 0.60899999999999999D && java.lang.Math.abs(point3d.y) > 0.251D)
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                        getEnergyPastArmor(10.604999542236328D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    } else
                    if(java.lang.Math.abs(point3d.y) > 0.26400000000000001D)
                    {
                        if(point3d.z > 0.021000000000000001D)
                        {
                            getEnergyPastArmor(8.5100002288818359D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                        } else
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                    } else
                    {
                        if(point3d.z < -0.35199999999999998D && java.lang.Math.abs(point3d.y) < 0.040000000000000001D)
                        {
                            debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                            return;
                        }
                        getEnergyPastArmor(8.0600004196166992D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    }
                } else
                if(s.endsWith("p7"))
                    getEnergyPastArmor(6.059999942779541D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p8"))
                {
                    if(point3d.y > 0.112D && point3d.z < -0.31900000000000001D || point3d.y < -0.065000000000000002D && point3d.z > 0.037999999999999999D && point3d.z < 0.20399999999999999D)
                    {
                        debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                        return;
                    }
                    getEnergyPastArmor(8.0600004196166992D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                } else
                if(s.endsWith("p9"))
                {
                    if(point3d.z > 0.61099999999999999D && point3d.z < 0.67400000000000004D && java.lang.Math.abs(point3d.y) < 0.041500000000000002D)
                    {
                        debuggunnery("Armor: Bullet Went Through a Pilofacturing Hole..");
                        return;
                    }
                    getEnergyPastArmor(8.0600004196166992D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                }
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.99F)
                    return;
                int i = s.charAt(10) - 48;
                if(s.endsWith("10"))
                    i = 10;
                if(s.endsWith("11"))
                    i = 11;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.02F, 2.351F), shot) > 0.0F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                        debuggunnery("Controls: Throttle Quadrant: Hit, Engine Controls Disabled..");
                    }
                    break;

                case 3: // '\003'
                    if(getEnergyPastArmor(3.5F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Controls: Aileron Controls: Fuselage Line Destroyed..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Controls: Elevator Controls: Fuselage Line Destroyed..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Controls: Rudder Controls: Fuselage Line Destroyed..");
                    }
                    break;

                case 4: // '\004'
                case 5: // '\005'
                    if(getEnergyPastArmor(0.002F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 6: // '\006'
                case 7: // '\007'
                case 10: // '\n'
                case 11: // '\013'
                    if(getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Controls: Aileron Controls: Disabled..");
                    }
                    break;

                case 8: // '\b'
                case 9: // '\t'
                    if(getEnergyPastArmor(6.8F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Controls: Aileron Controls: Crank Destroyed..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    debuggunnery("Spar Construction: Tail1 Spars Broken in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(17.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(17.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(13.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(13.2F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxwj"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(12.5F, 55.96F), shot) > 0.0F)
                    if(s.endsWith("l"))
                    {
                        debuggunnery("Spar Construction: WingL Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                    } else
                    {
                        debuggunnery("Spar Construction: WingR Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr"))
                {
                    int j = s.charAt(6) - 48;
                    if(getEnergyPastArmor(6.56F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                        if(j < 3)
                        {
                            debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                            nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                        } else
                        {
                            debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
                            nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                        }
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int k = s.charAt(5) - 49;
                debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Hit..");
                if(s.endsWith("prop"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, k, 3);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Prop Governor Hit, Disabled..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, k, 4);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.EI.engines[k].setEngineStuck(shot.initiator);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Bullet Jams Reductor Gear..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, k, 3);
                            FM.AS.setEngineSpecificDamage(shot.initiator, k, 4);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, k, 0);
                        debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Supercharger Disabled..");
                    }
                } else
                if(s.endsWith("feed"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && FM.EI.engines[k].getPowerOutput() > 0.7F)
                    {
                        FM.AS.hitEngine(shot.initiator, k, 100);
                        debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                } else
                if(s.endsWith("fuel"))
                {
                    if(getEnergyPastArmor(1.1F, shot) > 0.0F)
                    {
                        FM.EI.engines[k].setEngineStops(shot.initiator);
                        debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Fuel Line Stalled, Engine Stalled..");
                    }
                } else
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, k);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Bullet Jams Crank Ball Bearing..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 2);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Crank Case Hit, Readyness Reduced to " + FM.EI.engines[k].getReadyness() + "..");
                        }
                        FM.EI.engines[k].setReadyness(shot.initiator, FM.EI.engines[k].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Crank Case Hit, Readyness Reduced to " + FM.EI.engines[k].getReadyness() + "..");
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(22.5F, 33.6F), shot);
                } else
                if(s.endsWith("cyl1") || s.endsWith("cyl2"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[k].getCylindersRatio() * 1.75F)
                    {
                        FM.EI.engines[k].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Cylinders Hit, " + FM.EI.engines[k].getCylindersOperable() + "/" + FM.EI.engines[k].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            FM.AS.hitEngine(shot.initiator, k, 3);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, k);
                            debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Bullet Jams Piston Head..");
                        }
                        getEnergyPastArmor(22.5F, shot);
                    }
                } else
                if(s.endsWith("mag1") || s.endsWith("mag2"))
                {
                    int j2 = s.charAt(9) - 49;
                    FM.EI.engines[k].setMagnetoKnockOut(shot.initiator, j2);
                    debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Magneto " + j2 + " Destroyed..");
                } else
                if(s.endsWith("oil1"))
                {
                    FM.AS.hitOil(shot.initiator, k);
                    debuggunnery("Engine Module (" + (k != 0 ? "Right" : "Left") + "): Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 2.345F), shot) > 0.0F)
                {
                    int l = s.charAt(5) - 49;
                    FM.AS.hitOil(shot.initiator, l);
                    getEnergyPastArmor(0.22F, shot);
                    debuggunnery("Engine Module (" + (l != 0 ? "Right" : "Left") + "): Oil Tank Pierced..");
                }
                return;
            }
            if(s.startsWith("xxw"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 0.75F), shot) > 0.0F)
                {
                    int i1 = s.charAt(3) - 49;
                    if(FM.AS.astateEngineStates[i1] == 0)
                    {
                        debuggunnery("Engine Module (" + (i1 != 0 ? "Right" : "Left") + "): Water Radiator Pierced..");
                        FM.AS.hitEngine(shot.initiator, i1, 2);
                        FM.AS.doSetEngineState(shot.initiator, i1, 2);
                    }
                    getEnergyPastArmor(2.22F, shot);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j1 = s.charAt(6) - 49;
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 2.23F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    if(FM.AS.astateTankStates[j1] == 0)
                    {
                        debuggunnery("Fuel Tank " + (j1 + 1) + ": Pierced..");
                        FM.AS.hitTank(shot.initiator, j1, 1);
                        FM.AS.doSetTankState(shot.initiator, j1, 1);
                    } else
                    if(FM.AS.astateTankStates[j1] == 1)
                    {
                        debuggunnery("Fuel Tank " + (j1 + 1) + ": Pierced..");
                        FM.AS.hitTank(shot.initiator, j1, 1);
                        FM.AS.doSetTankState(shot.initiator, j1, 2);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, 2, 2);
                        debuggunnery("Fuel Tank " + (j1 + 1) + ": Hit..");
                    }
                }
                return;
            }
            if(s.startsWith("xxhyd"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 12.39F), shot) > 0.0F)
                {
                    debuggunnery("Hydro System: Disabled..");
                    FM.AS.setInternalDamage(shot.initiator, 0);
                }
                return;
            }
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Cowling Gun: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Cowling Gun: Disabled..");
                    FM.AS.setJamBullets(0, 1);
                }
                if(s.endsWith("03"))
                {
                    debuggunnery("Cowling Gun: Disabled..");
                    FM.AS.setJamBullets(0, 2);
                }
                if(s.endsWith("04"))
                {
                    debuggunnery("Cowling Gun: Disabled..");
                    FM.AS.setJamBullets(0, 3);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 12.96F), shot);
            }
            if(s.startsWith("xxcannon"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Cowling Cannon: Disabled..");
                    FM.AS.setJamBullets(1, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Cowling Cannon: Disabled..");
                    FM.AS.setJamBullets(1, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
            }
            if(s.startsWith("xxmgff"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Cowling Cannon (MGFF): Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Cowling Cannon (MGFF): Disabled..");
                    FM.AS.setJamBullets(0, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
            }
            if(s.startsWith("xxmk"))
            {
                if(s.endsWith("01"))
                {
                    debuggunnery("Cowling Cannon (Mk 108): Disabled..");
                    FM.AS.setJamBullets(1, 0);
                }
                if(s.endsWith("02"))
                {
                    debuggunnery("Cowling Cannon (Mk 108): Disabled..");
                    FM.AS.setJamBullets(1, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(24.5F, 96.87F), shot);
            }
            return;
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit") || s.startsWith("xnose"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.x > 1.857D && point3d.z > 0.41599999999999998D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
            }
        } else
        if(s.startsWith("xengine"))
        {
            int k1 = s.charAt(7) - 48;
            if(chunkDamageVisible("Engine" + k1) < 2)
                hitChunk("Engine" + k1, shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            int l1 = s.charAt(5) - 48;
            if(chunkDamageVisible("Keel" + l1) < 3)
                hitChunk("Keel" + l1, shot);
        } else
        if(s.startsWith("xrudder"))
        {
            int i2 = s.charAt(7) - 48;
            if(chunkDamageVisible("Rudder" + i2) < 1)
                hitChunk("Rudder" + i2, shot);
        } else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && chunkDamageVisible("StabL") < 3)
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 3)
                hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
            if(s.startsWith("xvatorl") && chunkDamageVisible("VatorL") < 1)
                hitChunk("VatorL", shot);
            if(s.startsWith("xvatorr") && chunkDamageVisible("VatorR") < 1)
                hitChunk("VatorR", shot);
        } else
        if(s.startsWith("xwing"))
        {
            if(s.startsWith("xwinglin") && chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            if(s.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
            if(s.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            if(s.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xarone"))
        {
            if(s.startsWith("xaronel"))
                hitChunk("AroneL", shot);
            if(s.startsWith("xaroner"))
                hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xgear"))
        {
            if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(s.endsWith("2"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.8F, 29.35F), shot) > 0.0F)
                {
                    debuggunnery("Undercarriage: Stuck..");
                    FM.AS.setInternalDamage(shot.initiator, 3);
                }
                java.lang.String s1 = "" + s.charAt(5);
                hitChunk("Gear" + s1.toUpperCase() + "2", shot);
            }
        } else
        if(s.startsWith("xturret"))
        {
            if(getEnergyPastArmor(0.25F, shot) > 0.0F)
            {
                debuggunnery("Armament System: Turret Machine Gun(s): Disabled..");
                FM.AS.setJamBullets(10, 0);
                FM.AS.setJamBullets(10, 1);
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 26.35F), shot);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int k2;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                k2 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                k2 = s.charAt(6) - 49;
            } else
            {
                k2 = s.charAt(5) - 49;
            }
            hitFlesh(k2, shot, byte0);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.1F, 0.0F, -50.5F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.BF_110.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
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
            hierMesh().chunkVisible("HMask1_D0", false);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("HMask2_D0", false);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore2_D0", true);
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

        case 12: // '\f'
        case 18: // '\022'
        case 19: // '\023'
            hierMesh().chunkVisible("Wire_D0", false);
            break;

        case 34: // '"'
            FM.AS.hitEngine(this, 0, 2);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.66F)
                FM.AS.hitEngine(this, 0, 2);
            break;

        case 37: // '%'
            FM.AS.hitEngine(this, 1, 2);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.66F)
                FM.AS.hitEngine(this, 1, 2);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateEngineStates[0] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.06F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateEngineStates[1] > 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.06F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[0] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[2] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 5 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                FM.AS.hitTank(this, 1, 1);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                FM.AS.hitTank(this, 0, 1);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                FM.AS.hitTank(this, 3, 1);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F)
                FM.AS.hitTank(this, 2, 1);
        }
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -37F)
            {
                f = -37F;
                flag = false;
            }
            if(f > 37F)
            {
                f = 37F;
                flag = false;
            }
            if(f1 < -19F)
            {
                f1 = -19F;
                flag = false;
            }
            if(f1 > 27F)
            {
                f1 = 27F;
                flag = false;
            }
            if(java.lang.Math.abs(f) > 17.8F && java.lang.Math.abs(f) < 25F && f1 < -12F)
                flag = false;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(this instanceof com.maddox.il2.objects.air.BF_110G2)
        {
            java.lang.Object aobj[] = pos.getBaseAttached();
            if(aobj == null)
                return;
            for(int i = 0; i < aobj.length; i++)
            {
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.Bomb)
                    hierMesh().chunkVisible("Rack_D0", true);
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.MGunMK108ki)
                {
                    hierMesh().chunkVisible("Nose_D0", false);
                    hierMesh().chunkVisible("Nose_D1", true);
                }
            }

        }
    }

    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f1, 0.0F);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float kangle0;
    private float kangle1;
    private float slpos;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_110.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
