package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;

public abstract class HE_219 extends Scheme2
    implements TypeFighter
{

    public HE_219()
    {
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 3: // '\003'
            super.FM.setGCenter(-1.5F);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, -115.5F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, -137F), 0.0F);
        hiermesh.chunkSetAngles("GearC8_D0", 0.0F, Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, -148.5F), 0.0F);
        hiermesh.chunkSetAngles("GearC9_D0", 0.0F, Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, 1.0F), 0.0F);
        Aircraft.xyz[0] = Aircraft.xyz[1] = Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F;
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.21F, 0.63F, 0.0F, 0.09F);
        hiermesh.chunkSetLocate("GearC10_D0", Aircraft.xyz, Aircraft.ypr);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f, 0.12F, 0.75F, 0.0F, -86F), 0.0F);
        Aircraft.xyz[0] = Aircraft.xyz[1] = Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F;
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.12F, 0.75F, 0.0F, 0.23F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f, 0.03F, 0.95F, 0.0F, -86F), 0.0F);
        Aircraft.xyz[0] = Aircraft.xyz[1] = Aircraft.xyz[2] = Aircraft.ypr[0] = Aircraft.ypr[1] = Aircraft.ypr[2] = 0.0F;
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.03F, 0.95F, 0.0F, 0.23F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -0.777778F * f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        HE_219.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, 0.52255F);
        hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, -65F), 0.0F);
        hierMesh().chunkSetAngles("GearC6_D0", 0.0F, Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[2], 0.0F, 0.4719F, 0.0F, -130F), 0.0F);
        Aircraft.xyz[1] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.27625F, 0.0F, 0.27625F);
        hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
        Aircraft.xyz[1] = Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.27625F, 0.0F, 0.27625F);
        hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(super.FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap1_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap2_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap3_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap4_D0", 0.0F, f1, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p3"))
                {
                    getEnergyPastArmor(35.889999389648438D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p4"))
                    getEnergyPastArmor(12.71F, shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                case 4: // '\004'
                    if(getEnergyPastArmor(1.2F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                        Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
                    }
                    break;

                case 2: // '\002'
                case 3: // '\003'
                    if(getEnergyPastArmor(4D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Aileron Wiring Hit, Aileron Controls Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(getEnergyPastArmor(1.0F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Rudder Controls Out..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(5) - 49;
                Aircraft.debugprintln(this, "*** Engine Module (" + j + "): Hit..");
                if(s.endsWith("prop") || s.endsWith("prop3"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, j, 3);
                            Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, j, 4);
                            Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("prop1") || s.endsWith("prop2"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 5);
                        Aircraft.debugprintln(this, "*** Engine Module: Drive Shaft Damaged..");
                    }
                } else
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].setEngineStuck(shot.initiator);
                            Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, j, 3);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, j, 4);
                            Aircraft.debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, j, 0);
                        Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
                    }
                } else
                if(s.endsWith("feed"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getPowerOutput() > 0.7F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, j, 100);
                        Aircraft.debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                } else
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, j);
                            Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, j, 2);
                            Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getReadyness() + "..");
                        }
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getReadyness() + "..");
                    }
                    getEnergyPastArmor(22.5F, shot);
                } else
                if(s.startsWith("xxeng1cyl") || s.startsWith("xxeng2cyl"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getCylindersRatio() * 1.75F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, j, 3);
                            Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, j);
                            Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
                        }
                        getEnergyPastArmor(22.5F, shot);
                    }
                } else
                if(s.startsWith("xxeng1mag") || s.startsWith("xxeng2mag"))
                {
                    int l = s.charAt(9) - 49;
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[j].setMagnetoKnockOut(shot.initiator, l);
                    Aircraft.debugprintln(this, "*** Engine Module: Magneto " + l + " Destroyed..");
                } else
                if(s.endsWith("sync"))
                {
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        Aircraft.debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                    }
                } else
                if(s.endsWith("oil1"))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, j);
                    Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
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
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRIn Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(6.9600000381469727D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spar Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (Aircraft.v1)).z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[k] == 0)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, 1);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, k, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, 1);
                        if(shot.powerType == 3 && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[k] > 2 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.4F)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, 10);
                    } else
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 56000F)));
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
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.0575F)
                if(point3d.y > 0.0D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
            if(point3d.x > 1.726D)
            {
                if(point3d.z > 0.44400000000000001D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(point3d.z > -0.28100000000000003D && point3d.z < 0.44400000000000001D)
                    if(point3d.y > 0.0D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(point3d.x > 2.774D && point3d.x < 3.718D && point3d.z > 0.42499999999999999D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xcockpit"))
        {
            if(((com.maddox.JGP.Tuple3d) (point3d)).z > 0.40000000000000002D)
            {
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x20);
            } else
            if(((com.maddox.JGP.Tuple3d) (point3d)).y > 0.0D)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
            else
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
            if(((com.maddox.JGP.Tuple3d) (point3d)).x > 0.20000000000000001D)
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
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
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xrudder2"))
        {
            if(chunkDamageVisible("Rudder2") < 1)
                hitChunk("Rudder2", shot);
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
        if(s.startsWith("xgear"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            {
                debuggunnery("*** Gear Hydro Failed..");
                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.setHydroOperable(false);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
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

    public void doEjectCatapult()
    {
        new com.maddox.rts.MsgAction(false, this) {

            public void doAction(java.lang.Object obj)
            {
                Aircraft aircraft = (Aircraft)obj;
                if(!com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    return;
                } else
                {
                    com.maddox.il2.engine.Loc loc = new Loc();
                    com.maddox.il2.engine.Loc loc1 = new Loc();
                    com.maddox.JGP.Vector3d vector3d = new Vector3d(0.0D, 0.0D, 10D);
                    com.maddox.il2.engine.HookNamed hooknamed = new HookNamed(aircraft, "_ExternalSeat01");
                    ((com.maddox.il2.engine.Actor) (aircraft)).pos.getAbs(loc1);
                    hooknamed.computePos(aircraft, loc1, loc);
                    loc.transform(vector3d);
                    vector3d.x += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Vwld)).x;
                    vector3d.y += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Vwld)).y;
                    vector3d.z += ((com.maddox.JGP.Tuple3d) (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft) (aircraft)).FM)).Vwld)).z;
                    new EjectionSeat(2, loc, vector3d, aircraft);
                    return;
                }
            }

        }
;
        hierMesh().chunkVisible("Seat_D0", false);
    }

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryGermany);
    }
}
