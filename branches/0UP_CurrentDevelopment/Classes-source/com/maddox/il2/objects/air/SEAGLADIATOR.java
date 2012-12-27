// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SEAGLADIATOR.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeTNBFighter, Aircraft, 
//            Cockpit, PaintScheme

public abstract class SEAGLADIATOR extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public SEAGLADIATOR()
    {
        bChangedPit = true;
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

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.6F);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.0F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor: Hit..");
                if(s.endsWith("p1"))
                    getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
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
                                FM.AS.setControlsDamage(shot.initiator, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                            {
                                FM.AS.setControlsDamage(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Controls: Disabled..");
                            }
                        }
                        // fall through

                    case 2: // '\002'
                    case 3: // '\003'
                        if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                        {
                            FM.AS.setControlsDamage(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
                        }
                        break;
                    }
                }
                if(s.startsWith("xxspar"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                    if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(9.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
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
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                        }
                    } else
                    if(s.endsWith("case"))
                    {
                        if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                        {
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                            {
                                FM.AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                            {
                                FM.AS.hitEngine(shot.initiator, 0, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                            }
                            FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                        }
                        getEnergyPastArmor(12.7F, shot);
                    } else
                    if(s.startsWith("xxeng1cyls"))
                    {
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.2F, 4.4F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.12F)
                        {
                            FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                            {
                                FM.AS.hitEngine(shot.initiator, 0, 3);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.005F)
                            {
                                FM.AS.setEngineStuck(shot.initiator, 0);
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
                                FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto 0 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto 1 Destroyed..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Throttle Controls Cut..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                            {
                                FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Mix Controls Cut..");
                            }
                        }
                    } else
                    if(s.endsWith("oil1"))
                    {
                        FM.AS.hitOil(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
                    }
                }
                if(s.startsWith("xxoil"))
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
                }
                if(s.startsWith("xxtank1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.99F)
                {
                    if(FM.AS.astateTankStates[0] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 0, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, 0, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                }
                if(s.startsWith("xxmgun"))
                {
                    if(s.endsWith("01"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(0, 0);
                    }
                    if(s.endsWith("02"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(0, 1);
                    }
                    if(s.endsWith("03"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        FM.AS.setJamBullets(1, 0);
                    }
                    if(s.endsWith("04"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
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
        } else
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xbay"))
        {
            if(chunkDamageVisible("DinghyBay1") < 2)
                hitChunk("DinghyBay1", shot);
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

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneLrod1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneLrod2_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneLn_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneRrod1_D0", 0.0F, 30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneRrod2_D0", 0.0F, 30F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneRn_D0", 0.0F, -30F * f, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 11: // '\013'
            hierMesh().chunkVisible("PodkosLw_D0", false);
            hierMesh().chunkVisible("PodkosRw_D0", false);
            break;

        case 17: // '\021'
            hierMesh().chunkVisible("PodkosLw_D0", false);
            break;

        case 18: // '\022'
            hierMesh().chunkVisible("PodkosLw_D0", false);
            break;

        case 19: // '\023'
            hierMesh().chunkVisible("WireC_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
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

    public boolean bChangedPit;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SEAGLADIATOR.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
    }
}