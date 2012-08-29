// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   SBD.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, Aircraft, Cockpit, PaintScheme

public abstract class SBD extends com.maddox.il2.objects.air.Scheme1
{

    public SBD()
    {
        arrestor = 0.0F;
        flapps = 0.0F;
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -50F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -50F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getFlap()), 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getFlap()), 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getFlap()), 0.0F);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getAirBrake()), 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getAirBrake()), 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -50F * java.lang.Math.max(f, FM.CT.getAirBrake()), 0.0F);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -102F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -117F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, -102F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.21F, 0.99F, 0.0F, -117F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.SBD.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.252F, 0.0F, -0.231F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = f;
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.252F, 0.0F, -0.231F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = f;
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -73.5F * arrestor, 0.0F);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -0.7F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
        {
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
        } else
        {
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
        }
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
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("t1"))
                {
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(21F, 42F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("t2"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12.2F, 12.9F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("t3"))
                    getEnergyPastArmor(8.9F, shot);
                else
                if(s.endsWith("t4"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12.2F, 12.9F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("t5"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12.2F, 12.9F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("t6"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12.2F, 12.9F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                if((s.endsWith("s1") || s.endsWith("s2")) && getEnergyPastArmor(4.8F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Cables: Hit, Controls Destroyed..");
                }
                if(s.endsWith("s3") && getEnergyPastArmor(3.2F, shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                    FM.AS.setControlsDamage(shot.initiator, 2);
                    FM.AS.setControlsDamage(shot.initiator, 1);
                    FM.AS.setControlsDamage(shot.initiator, 0);
                }
                if(s.endsWith("s4") && getEnergyPastArmor(0.2F, shot) > 0.0F)
                {
                    FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                }
                if((s.endsWith("s5") || s.endsWith("s6") || s.endsWith("s7") || s.endsWith("s8")) && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.35F, shot) > 0.0F)
                {
                    FM.AS.setControlsDamage(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls Out..");
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.2F, 0.55F), shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 280000F)
                        {
                            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
                            FM.AS.setEngineStuck(shot.initiator, 0);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 100000F)
                        {
                            debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                        }
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 24F), shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(0.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.66F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 32200F)));
                        debuggunnery("Engine Module: Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 1000000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.startsWith("xxeng1mag"))
                {
                    int i = s.charAt(9) - 49;
                    debuggunnery("Engine Module: Magneto " + i + " Destroyed..");
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
                }
                if(s.endsWith("oil1"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(0.25F, shot) > 0.0F)
                        debuggunnery("Engine Module: Oil Radiator Hit..");
                    FM.AS.hitOil(shot.initiator, 0);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                debuggunnery("Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxmgun1"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(0, 0);
                    getEnergyPastArmor(11.98F, shot);
                }
                return;
            }
            if(s.startsWith("xxmgun2"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(0, 1);
                    getEnergyPastArmor(11.98F, shot);
                }
                return;
            }
            if(s.startsWith("xxmgun3"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(10, com.maddox.il2.ai.World.Rnd().nextInt(0, 1));
                    getEnergyPastArmor(11.98F, shot);
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                if(getEnergyPastArmor(2.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(6.75F, shot);
                    debuggunnery("Engine Module: Oil Tank Pierced..");
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.7F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** Tail1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 10.25F)
                {
                    if(FM.AS.astateTankStates[j] == 0)
                    {
                        debuggunnery("Fuel Tank (" + j + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, j, 1);
                        FM.AS.doSetTankState(shot.initiator, j, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F || shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, j, 2);
                        debuggunnery("Fuel Tank (" + j + "): Hit..");
                    }
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xCF"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(!s.startsWith("xBlister"))
            if(s.startsWith("xEng"))
                hitChunk("Engine1", shot);
            else
            if(s.startsWith("xtail"))
            {
                if(chunkDamageVisible("Tail1") < 3)
                    hitChunk("Tail1", shot);
            } else
            if(s.startsWith("xKeel"))
                hitChunk("Keel1", shot);
            else
            if(s.startsWith("xRudder"))
            {
                if(chunkDamageVisible("Rudder1") < 2)
                    hitChunk("Rudder1", shot);
            } else
            if(s.startsWith("xStab"))
            {
                if(s.startsWith("xStabL"))
                    hitChunk("StabL", shot);
                if(s.startsWith("xStabR"))
                    hitChunk("StabR", shot);
            } else
            if(s.startsWith("xvator"))
            {
                if(s.startsWith("xvatorl") && chunkDamageVisible("VatorL") < 2)
                    hitChunk("VatorL", shot);
                if(s.startsWith("xvatorr") && chunkDamageVisible("VatorR") < 2)
                    hitChunk("VatorR", shot);
            } else
            if(s.startsWith("xWing"))
            {
                if(s.startsWith("xWingLIn") && chunkDamageVisible("WingLIn") < 3)
                    hitChunk("WingLIn", shot);
                if(s.startsWith("xWingRIn") && chunkDamageVisible("WingRIn") < 3)
                    hitChunk("WingRIn", shot);
                if(s.startsWith("xWingLMid"))
                {
                    if(chunkDamageVisible("WingLMid") < 3)
                        hitChunk("WingLMid", shot);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.mass + 0.02F)
                        FM.AS.hitOil(shot.initiator, 0);
                }
                if(s.startsWith("xWingRMid") && chunkDamageVisible("WingRMid") < 3)
                    hitChunk("WingRMid", shot);
                if(s.startsWith("xWingLOut") && chunkDamageVisible("WingLOut") < 3)
                    hitChunk("WingLOut", shot);
                if(s.startsWith("xWingROut") && chunkDamageVisible("WingROut") < 3)
                    hitChunk("WingROut", shot);
            } else
            if(s.startsWith("xArone"))
            {
                if(s.startsWith("xAroneL"))
                    hitChunk("AroneL", shot);
                if(s.startsWith("xAroneR"))
                    hitChunk("AroneR", shot);
            } else
            if(s.startsWith("xGearL"))
                hitChunk("GearL2", shot);
            else
            if(s.startsWith("xGearR"))
                hitChunk("GearR2", shot);
            else
            if(s.startsWith("xpilot") || s.startsWith("xhead"))
            {
                byte byte0 = 0;
                int k;
                if(s.endsWith("a"))
                {
                    byte0 = 1;
                    k = s.charAt(6) - 49;
                } else
                if(s.endsWith("b"))
                {
                    byte0 = 2;
                    k = s.charAt(6) - 49;
                } else
                {
                    k = s.charAt(5) - 49;
                }
                hitFlesh(k, shot, byte0);
            }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.CT.bHasArrestorControl = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        super.update(f);
        if(FM.CT.getArrestor() > 0.001F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.arrestorVAngle, -73.5F, 3.5F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = -1.224F * FM.Gears.arrestorVSink;
                if(f2 < 0.0F && FM.getSpeedKMH() > 60F)
                    com.maddox.il2.engine.Eff3DActor.New(this, FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > FM.CT.getArrestor())
                    arrestor = FM.CT.getArrestor();
                moveArrestorHook(arrestor);
            }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private float arrestor;
    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SBD.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
