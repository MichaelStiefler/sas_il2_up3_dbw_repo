// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CR_32quater.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFMPar00, TypeFighter, TypeTNBFighter, 
//            Aircraft, NetAircraft

public class CR_32quater extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.computePlaneLandPose(super.FM);
        if(super.thisWeaponsName.startsWith("2x"))
        {
            hierMesh().chunkVisible("RackL_D0", true);
            hierMesh().chunkVisible("RackR_D0", true);
            return;
        }
        if(super.thisWeaponsName.startsWith("1x"))
        {
            hierMesh().chunkVisible("RackL_D0", true);
            hierMesh().chunkVisible("RackR_D0", true);
            return;
        }
        if(super.thisWeaponsName.startsWith("2_"))
        {
            hierMesh().chunkVisible("Cassette_D0", true);
            return;
        } else
        {
            return;
        }
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(super.FM.isPlayers())
            bChangedPit = true;
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor: Hit..");
                if(s.endsWith("p1"))
                    getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-006D), shot);
            } else
            {
                if(s.startsWith("xxcontrols"))
                {
                    int i = s.charAt(10) - 48;
                    switch(i)
                    {
                    default:
                        break;

                    case 1: // '\001'
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.1F, 2.3F), shot) > 0.0F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Controls: Disabled..");
                            }
                        }
                        // fall through

                    case 2: // '\002'
                    case 3: // '\003'
                        if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
                        }
                        break;
                    }
                }
                if(s.startsWith("xxspar"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                    if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
                        nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                    }
                    if(s.startsWith("xxsparli") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                    }
                    if(s.startsWith("xxsparri") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                    if(s.startsWith("xxsparlm") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), shot.initiator);
                    }
                    if(s.startsWith("xxsparrm") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
                    }
                    if(s.startsWith("xxsparlo") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), shot.initiator);
                    }
                    if(s.startsWith("xxsparro") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), shot.initiator);
                    }
                    if(s.startsWith("xxstabl") && getEnergyPastArmor(16.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
                        nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), shot.initiator);
                    }
                    if(s.startsWith("xxstabr") && getEnergyPastArmor(16.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
                        nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), shot.initiator);
                    }
                }
                if(s.startsWith("xxlock"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
                    if(s.startsWith("xxlockr") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvl") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvr") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                    }
                    if(s.startsWith("xxlockal") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");
                        nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockar") && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneR Lock Shot Off..");
                        nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                    }
                }
                if(s.startsWith("xxeng"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Hit..");
                    if(s.endsWith("prop"))
                    {
                        if(getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                        }
                    } else
                    if(s.endsWith("case"))
                    {
                        if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getReadyness() + "..");
                            }
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getReadyness() + "..");
                        }
                        getEnergyPastArmor(12.7F, shot);
                    } else
                    if(s.startsWith("xxeng1cyls"))
                    {
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.2F, 4.4F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 1.12F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
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
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto 0 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto 1 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Mix Controls Cut..");
                            }
                        }
                    } else
                    if(s.endsWith("oil1"))
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
                    }
                }
                if(s.startsWith("xxoil"))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
                }
                if(s.startsWith("xxtank1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.99F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[0] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                }
                if(s.startsWith("xxmgun"))
                {
                    if(s.endsWith("01"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                    }
                    if(s.endsWith("02"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                    }
                    if(s.endsWith("03"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                    }
                    if(s.endsWith("04"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
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
                if(((com.maddox.JGP.Tuple3d) (point3d)).x < -1.907D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.24F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 8);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.24F)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x20);
                } else
                if(((com.maddox.JGP.Tuple3d) (point3d)).z < 0.59299999999999997D)
                {
                    if(((com.maddox.JGP.Tuple3d) (point3d)).y > 0.0D)
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
                    else
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
                } else
                if(((com.maddox.JGP.Tuple3d) (point3d)).x > -1.2010000000000001D)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
                else
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
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
            hitFlesh(j, shot, byte0);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(super.FM.getAltitude() < 3000F)
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
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(explosion.initiator, 1);
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.042F) < explosion.power)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(explosion.initiator, 2);
        }
        super.msgExplosion(explosion);
    }

    public CR_32quater()
    {
        bChangedPit = true;
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
        java.lang.Class class1 = com.maddox.il2.objects.air.CR_32quater.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "CR.32");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/CR32/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1941F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/CR32.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitCR32quater.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.742F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 3, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_BOMBCASSETTE01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x50Kg_Bombs", new java.lang.String[] {
            "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", "BombGun50kg 1", "BombGun50kg 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1x100Kg_Bomb", new java.lang.String[] {
            "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", "BombGun100kg 1", null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2_Cassette", new java.lang.String[] {
            "MGunBredaSAFAT127si 350", "MGunBredaSAFAT127si 350", null, null, "BombGunSpezzoniera 12"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
