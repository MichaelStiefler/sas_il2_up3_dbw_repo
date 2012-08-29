// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   HS123.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFMPar00, TypeFighter, TypeTNBFighter, 
//            Aircraft, NetAircraft

public class HS123 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public HS123()
    {
        bChangedPit = true;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null)
        {
            hierMesh().chunkVisible("RackL_D0", true);
            hierMesh().chunkVisible("RackR_D0", true);
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

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Armor: Hit..");
                if(s.endsWith("p1"))
                    getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
            } else
            {
                if(s.startsWith("xxcontrols"))
                {
                    int i = s.charAt(10) - 48;
                    switch(i)
                    {
                    case 1: // '\001'
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 2.3F), shot) > 0.0F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            {
                                FM.AS.setControlsDamage(shot.initiator, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Controls: Disabled..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            {
                                FM.AS.setControlsDamage(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Elevator Controls: Disabled..");
                            }
                        }
                        // fall through

                    case 2: // '\002'
                    case 3: // '\003'
                        if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Aileron Controls: Control Crank Destroyed..");
                        }
                        break;
                    }
                }
                if(s.startsWith("xxspar"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Spar Construction: Hit..");
                    if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Tail1 Spars Broken in Half..");
                        nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                    }
                    if(s.startsWith("xxsparli") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                    }
                    if(s.startsWith("xxsparri") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                    if(s.startsWith("xxsparlm") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), shot.initiator);
                    }
                    if(s.startsWith("xxsparrm") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
                    }
                    if(s.startsWith("xxsparlo") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), shot.initiator);
                    }
                    if(s.startsWith("xxsparro") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), shot.initiator);
                    }
                    if(s.startsWith("xxstabl") && getEnergyPastArmor(16.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** StabL Spars Damaged..");
                        nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), shot.initiator);
                    }
                    if(s.startsWith("xxstabr") && getEnergyPastArmor(16.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** StabR Spars Damaged..");
                        nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), shot.initiator);
                    }
                }
                if(s.startsWith("xxlock"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Lock Construction: Hit..");
                    if(s.startsWith("xxlockr") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Rudder Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvl") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** VatorL Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvr") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** VatorR Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                    }
                    if(s.startsWith("xxlockal") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** AroneL Lock Shot Off..");
                        nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockar") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** AroneR Lock Shot Off..");
                        nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                    }
                }
                if(s.startsWith("xxeng"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Hit..");
                    if(s.endsWith("prop"))
                    {
                        if(getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Governor Hit, Disabled..");
                        }
                    } else
                    if(s.endsWith("case"))
                    {
                        if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                            {
                                FM.AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                            {
                                FM.AS.hitEngine(shot.initiator, 0, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                            }
                            FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                        }
                        getEnergyPastArmor(12.7F, shot);
                    } else
                    if(s.startsWith("xxeng1cyls"))
                    {
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.2F, 4.4F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.12F)
                        {
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                            {
                                FM.AS.hitEngine(shot.initiator, 0, 3);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, Engine Fires..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                            {
                                FM.AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Piston Head..");
                            }
                            getEnergyPastArmor(22.5F, shot);
                        }
                    } else
                    if(s.endsWith("eqpt"))
                    {
                        if(getEnergyPastArmor(0.2721F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto 0 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto 1 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Throttle Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Mix Controls Cut..");
                            }
                        }
                    } else
                    if(s.endsWith("oil1"))
                    {
                        FM.AS.hitOil(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Oil Radiator Hit..");
                    }
                }
                if(s.startsWith("xxoil"))
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Oil Tank Pierced..");
                }
                if(s.startsWith("xxtank1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.99F)
                {
                    if(FM.AS.astateTankStates[0] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 0, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Fuel Tank: Hit..");
                    }
                }
                if(s.startsWith("xxmgun"))
                {
                    if(s.endsWith("01"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(0, 0);
                    }
                    if(s.endsWith("02"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(0, 1);
                    }
                    if(s.endsWith("03"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(1, 0);
                    }
                    if(s.endsWith("04"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(1, 1);
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 28.33F), shot);
                }
            }
        } else
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.x < -1.907D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.24F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.24F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                } else
                if(point3d.z < 0.59299999999999997D)
                {
                    if(point3d.y > 0.0D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                } else
                if(point3d.x > -1.2010000000000001D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
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
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
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
            if(s.startsWith("xaronel") && chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
            if(s.startsWith("xaroner") && chunkDamageVisible("AroneR") < 1)
                hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int j;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j = s.charAt(6) - 49;
            } else
            {
                j = s.charAt(5) - 49;
            }
            hitFlesh(j, shot, ((int) (byte0)));
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
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
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null && explosion.power > 0.0F && explosion.chunkName.startsWith("Tail1"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.038F) < explosion.power)
                FM.AS.setControlsDamage(explosion.initiator, 1);
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.042F) < explosion.power)
                FM.AS.setControlsDamage(explosion.initiator, 2);
        }
        super.msgExplosion(explosion);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    public boolean bChangedPit;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HS123.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "HS123");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Hs123/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar00())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1936F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1940F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHS123.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/HS123.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.742F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3, 9, 3, 3, 3, 
            3, 9, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", 
            "_ExternalBomb06", "_ExternalDev02", "_ExternalBomb07"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xDroptanks+4xSC-50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, "PylonBombs 1", "PylonBombs 1", "FTGun_71 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            "BombGunSC50 1", "PylonB 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xMG-FF", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMGFFt 90", "MGunMGFFt 90", "PylonMG 1", "PylonMG 1", null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xDroptanks+4xSC-70", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, "PylonBombs 1", "PylonBombs 1", "FTGun_71 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", 
            "BombGunSC70 1", "PylonB 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC-250+4xSC-50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, "PylonBombs 1", "PylonBombs 1", null, "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", 
            "BombGunSC50 1", "PylonB 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC-250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", null, null, null, null, null, null, null, null, 
            null, "PylonB 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
