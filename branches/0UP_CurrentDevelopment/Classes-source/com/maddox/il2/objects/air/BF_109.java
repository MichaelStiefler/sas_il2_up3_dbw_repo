// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_109.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, PaintScheme, Aircraft, 
//            AircraftLH

public abstract class BF_109 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public BF_109()
    {
        trimElevator = 0.0F;
        bHasElevatorControl = true;
        X = 1.0F;
        GlassState = 0;
        s17 = s18 = 0.15F;
        s31 = s32 = 0.35F;
    }

    protected void moveFlap(float f)
    {
        float f_0_ = -45F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f_0_, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f_0_, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[0], 0.0F, 0.3F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("GearL99_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(((com.maddox.il2.fm.FlightModelMain) (super.FM)).Gears.gWheelSinking[1], 0.0F, 0.3F, 0.0F, 0.3F);
        hierMesh().chunkSetLocate("GearR99_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void update(float f)
    {
        super.update(f);
        if(!getOp(31) || !getOp(32))
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimAileron = ((((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.ElevatorControl * (s32 - s31) + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator * (s18 - s17)) * ((com.maddox.il2.fm.FlightModelMain) (super.FM)).SensPitch) / 3F;
        if(!bHasElevatorControl)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.ElevatorControl = 0.0F;
        if(trimElevator != ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator)
        {
            trimElevator = ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.trimElevator;
            hierMesh().chunkSetAngles("StabL_D0", 0.0F, 0.0F, -16F * trimElevator);
            hierMesh().chunkSetAngles("StabR_D0", 0.0F, 0.0F, -16F * trimElevator);
        }
    }

    protected void moveElevator(float f)
    {
        f -= trimElevator;
        hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
    }

    public void doKillPilot(int j)
    {
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("HMask1_D0", false);
            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
            {
                if(hierMesh().isChunkVisible("Blister1_D0"))
                    hierMesh().chunkVisible("Gore1_D0", true);
                hierMesh().chunkVisible("Gore2_D0", true);
            }
            break;
        }
    }

    public boolean cut(java.lang.String string)
    {
        if(string.startsWith("Tail1"))
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(this, 2, 100);
        return super.cut(string);
    }

    public void rareAction(float f, boolean bool)
    {
        super.rareAction(f, bool);
        if(bool && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[0] > 5)
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.repairTank(0);
        if(super.FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    private void reflectGlassState(int i)
    {
        GlassState |= i;
        switch(GlassState & 3)
        {
        case 1: // '\001'
            hierMesh().materialReplace("Glass2", "ZBulletsHoles");
            break;

        case 2: // '\002'
            hierMesh().materialReplace("Glass2", "GlassOil");
            break;

        case 3: // '\003'
            hierMesh().materialReplace("Glass2", "GlassOilHoles");
            break;
        }
        switch(GlassState & 0xc)
        {
        case 4: // '\004'
            hierMesh().materialReplace("GlassW", "ZBulletsHoles");
            break;

        case 8: // '\b'
            hierMesh().materialReplace("GlassW", "Wounded");
            hierMesh().chunkVisible("Gore2_D0", true);
            break;

        case 12: // '\f'
            hierMesh().materialReplace("GlassW", "WoundedHoles");
            hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }

    protected void hitBone(java.lang.String string, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(string.startsWith("xx"))
        {
            if(string.startsWith("xxarmor"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor: Hit..");
                if(string.endsWith("p1"))
                {
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(20F, 30F), shot);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
                    reflectGlassState(5);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
                    if(shot.power <= 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            doRicochetBack(shot);
                    }
                } else
                if(string.endsWith("p2"))
                    getEnergyPastArmor(0.5F, shot);
                else
                if(string.endsWith("p3"))
                {
                    if(((com.maddox.JGP.Tuple3d) (point3d)).z < -0.27000000000000002D)
                        getEnergyPastArmor(4.0999999046325684D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z) + 9.9999997473787516E-006D), shot);
                    else
                        getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-006D), shot);
                } else
                if(string.endsWith("p4"))
                    getEnergyPastArmor(9D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z) + 9.9999997473787516E-006D), shot);
                else
                if(string.endsWith("p5"))
                    getEnergyPastArmor(9D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-006D), shot);
                else
                if(string.endsWith("p6"))
                    getEnergyPastArmor(9D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).x) + 9.9999997473787516E-006D), shot);
                else
                if(string.endsWith("a1"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        shot.powerType = 0;
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot);
                }
            } else
            {
                if(string.startsWith("xxcontrols"))
                {
                    int i = string.charAt(10) - 48;
                    switch(i)
                    {
                    default:
                        break;

                    case 1: // '\001'
                    case 4: // '\004'
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
                        }
                        break;

                    case 2: // '\002'
                    case 3: // '\003'
                        if(getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");
                        }
                        break;

                    case 5: // '\005'
                    case 6: // '\006'
                        if(getEnergyPastArmor(0.002F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            bHasElevatorControl = false;
                            if(!((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasElevatorTrim)
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken..");
                        }
                        if(getEnergyPastArmor(0.002F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.1F)
                            break;
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.bHasElevatorTrim = false;
                        if(!bHasElevatorControl)
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Trimmer Controls: Disabled / Strings Broken..");
                        break;

                    case 7: // '\007'
                        if(getEnergyPastArmor(2.3F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
                        }
                        break;

                    case 8: // '\b'
                        if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                        }
                        break;

                    case 9: // '\t'
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 8);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                        }
                        break;
                    }
                }
                if(string.startsWith("xxspar"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                    if(string.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
                        nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                        nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                    }
                    if(string.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                        nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                    }
                }
                if(string.startsWith("xxwj") && getEnergyPastArmor(12.5F, shot) > 0.0F)
                    if(string.endsWith("l"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingL Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                    } else
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingR Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                if(string.startsWith("xxlock"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
                    if(string.startsWith("xxlockr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                    }
                    if(string.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                    }
                    if(string.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                    }
                }
                if(string.startsWith("xxeng"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Hit..");
                    if(string.endsWith("pipe"))
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).CT.Weapons[1].length != 2)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Nose Nozzle Pipe Bent..");
                        }
                        getEnergyPastArmor(0.3F, shot);
                    } else
                    if(string.endsWith("prop"))
                    {
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                            } else
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
                            }
                    } else
                    if(string.endsWith("gear"))
                    {
                        if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineStuck(shot.initiator);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
                            } else
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
                            }
                    } else
                    if(string.endsWith("supc"))
                    {
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
                        }
                    } else
                    if(string.endsWith("feed"))
                    {
                        if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.7F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 100);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
                        }
                    } else
                    if(string.endsWith("fuel"))
                    {
                        if(getEnergyPastArmor(1.1F, shot) > 0.0F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setEngineStops(shot.initiator);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
                        }
                    } else
                    if(string.endsWith("case"))
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
                        getEnergyPastArmor(22.5F, shot);
                    } else
                    if(string.startsWith("xxeng1cyl"))
                    {
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 1.75F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                            }
                            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
                            }
                            getEnergyPastArmor(22.5F, shot);
                        }
                    } else
                    if(string.startsWith("xxeng1mag"))
                    {
                        int i = string.charAt(9) - 49;
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].setMagnetoKnockOut(shot.initiator, i);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto " + i + " Destroyed..");
                    } else
                    if(string.endsWith("sync"))
                    {
                        if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                        }
                    } else
                    if(string.endsWith("oil1"))
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x80);
                        reflectGlassState(2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
                    }
                }
                if(string.startsWith("xxoil"))
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x80);
                    reflectGlassState(2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
                }
                if(string.startsWith("xxtank"))
                {
                    int i = string.charAt(6) - 48;
                    switch(i)
                    {
                    default:
                        break;

                    case 1: // '\001'
                    case 2: // '\002'
                        if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        {
                            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateTankStates[2] == 0)
                            {
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 1);
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, 2, 1);
                            }
                            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            {
                                ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 2);
                                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                            }
                        }
                        break;

                    case 3: // '\003'
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** MW50 Tank: Pierced..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 2);
                        }
                        break;

                    case 4: // '\004'
                        if(getEnergyPastArmor(1.7F, shot) > 0.0F && (shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F))
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Nitrogen Oxyde Tank: Pierced, Nitros Flamed..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 100);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1, 100);
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2, 100);
                        }
                        break;
                    }
                }
                if(string.startsWith("xxmgun"))
                {
                    if(string.endsWith("01"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                    }
                    if(string.endsWith("02"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                    }
                    if(string.endsWith("l"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Wing Gun (L): Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                    }
                    if(string.endsWith("r"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Wing Gun (L): Disabled..");
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 12.96F), shot);
                }
                if(string.startsWith("xxcannon"))
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Nose Cannon: Disabled..");
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
                }
                if(string.startsWith("xxammo"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(3800F, 30000F) < shot.power)
                    {
                        if(string.endsWith("01"))
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                        }
                        if(string.endsWith("02"))
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                        }
                        if(string.endsWith("l"))
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Wing Gun (L): Ammo Feed Drum Damaged..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                        }
                        if(string.endsWith("r"))
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Wing Gun (R): Ammo Feed Drum Damaged..");
                            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
                        }
                    }
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 28.33F), shot);
                }
            }
        } else
        if(string.startsWith("xcf") || string.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(string.startsWith("xcockpit"))
            {
                if(((com.maddox.JGP.Tuple3d) (point3d)).z > 0.40000000000000002D)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
                    reflectGlassState(5);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x20);
                        reflectGlassState(5);
                    }
                } else
                if(((com.maddox.JGP.Tuple3d) (point3d)).y > 0.0D)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
                } else
                {
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
                    reflectGlassState(5);
                }
                if(((com.maddox.JGP.Tuple3d) (point3d)).x > 0.20000000000000001D)
                    ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
            }
        } else
        if(string.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(string.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(string.startsWith("xkeel"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(string.startsWith("xrudder"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(string.startsWith("xstab"))
        {
            if(string.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
            if(string.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
                hitChunk("StabR", shot);
        } else
        if(string.startsWith("xvator"))
        {
            if(string.startsWith("xvatorl") && chunkDamageVisible("VatorL") < 1)
                hitChunk("VatorL", shot);
            if(string.startsWith("xvatorr") && chunkDamageVisible("VatorR") < 1)
                hitChunk("VatorR", shot);
        } else
        if(string.startsWith("xwing"))
        {
            if(string.startsWith("xwinglin") && chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(string.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(string.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            if(string.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
            if(string.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
            if(string.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(string.startsWith("xarone"))
        {
            if(string.startsWith("xaronel"))
                hitChunk("AroneL", shot);
            if(string.startsWith("xaroner"))
                hitChunk("AroneR", shot);
        } else
        if(string.startsWith("xpilot") || string.startsWith("xhead"))
        {
            int i = 0;
            int i_1_;
            if(string.endsWith("a"))
            {
                i = 1;
                i_1_ = string.charAt(6) - 49;
            } else
            if(string.endsWith("b"))
            {
                i = 2;
                i_1_ = string.charAt(6) - 49;
            } else
            {
                i_1_ = string.charAt(5) - 49;
            }
            hitFlesh(i_1_, shot, i);
            if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.getPilotHealth(0) < 1.0F)
                reflectGlassState(8);
        }
    }

    private void cutOp(int i)
    {
        super.FM.Operate &= ~(1L << i);
    }

    protected boolean getOp(int i)
    {
        return (((com.maddox.il2.fm.FlightModelMain) (super.FM)).Operate & 1L << i) != 0L;
    }

    private float Op(int i)
    {
        return getOp(i) ? 1.0F : 0.0F;
    }

    protected boolean cutFM(int i, int i_2_, com.maddox.il2.engine.Actor actor)
    {
        if(!getOp(i))
            return false;
        switch(i)
        {
        case 17: // '\021'
            cut("StabL");
            cutOp(17);
            super.FM.setCapableOfACM(false);
            if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Skill)
                super.FM.setReadyToReturn(true);
            if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 16) < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Skill)
                super.FM.setReadyToDie(true);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftStab *= 0.5F * Op(18) + 0.1F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftWingLIn *= 1.1F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftWingRIn *= 0.9F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.dragProducedCx -= 0.06F;
            if(Op(18) == 0.0F)
            {
                super.FM.SensPitch = 0.0F;
                super.FM.setGCenter(0.2F);
            } else
            {
                super.FM.setGCenter(0.1F);
                s17 = 0.0F;
                super.FM.SensPitch *= s17 + s18 + s31 + s32;
                X = 1.0F / (s17 + s18 + s31 + s32);
                s18 *= X;
                s31 *= X;
                s32 *= X;
            }
            // fall through

        case 31: // '\037'
            if(Op(31) == 0.0F)
                return false;
            cut("VatorL");
            cutOp(31);
            if(Op(32) == 0.0F)
            {
                bHasElevatorControl = false;
                super.FM.setCapableOfACM(false);
                if(Op(18) == 0.0F)
                    super.FM.setReadyToDie(true);
            }
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.squareElevators *= 0.5F * Op(32);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.dragProducedCx += 0.06F;
            s31 = 0.0F;
            super.FM.SensPitch *= s17 + s18 + s31 + s32;
            X = 1.0F / (s17 + s18 + s31 + s32);
            s17 *= X;
            s18 *= X;
            s32 *= X;
            return false;

        case 18: // '\022'
            cut("StabR");
            cutOp(18);
            super.FM.setCapableOfACM(false);
            if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 8) < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Skill)
                super.FM.setReadyToReturn(true);
            if(com.maddox.il2.ai.World.Rnd().nextInt(-1, 16) < ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Skill)
                super.FM.setReadyToDie(true);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftStab *= 0.5F * Op(17) + 0.1F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftWingLIn *= 0.9F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.liftWingRIn *= 1.1F;
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.dragProducedCx -= 0.06F;
            if(Op(17) == 0.0F)
            {
                super.FM.SensPitch = 0.0F;
                super.FM.setGCenter(0.2F);
            } else
            {
                super.FM.setGCenter(0.1F);
                s18 = 0.0F;
                super.FM.SensPitch *= s17 + s18 + s31 + s32;
                X = 1.0F / (s17 + s18 + s31 + s32);
                s17 *= X;
                s31 *= X;
                s32 *= X;
            }
            // fall through

        case 32: // ' '
            if(Op(32) == 0.0F)
                return false;
            cut("VatorR");
            cutOp(32);
            if(Op(31) == 0.0F)
            {
                bHasElevatorControl = false;
                super.FM.setCapableOfACM(false);
                if(Op(17) == 0.0F)
                    super.FM.setReadyToDie(true);
            }
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.squareElevators *= 0.5F * Op(31);
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).Sq.dragProducedCx += 0.06F;
            s32 = 0.0F;
            super.FM.SensPitch *= s17 + s18 + s31 + s32;
            X = 1.0F / (s17 + s18 + s31 + s32);
            s17 *= X;
            s18 *= X;
            s31 *= X;
            return false;

        default:
            return super.cutFM(i, i_2_, actor);
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

    static java.lang.Class _mthclass$(java.lang.String string)
    {
        java.lang.Class var_class;
        try
        {
            var_class = java.lang.Class.forName(string);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return var_class;
    }

    private float trimElevator;
    private boolean bHasElevatorControl;
    private float X;
    private float s17;
    private float s18;
    private float s31;
    private float s32;
    private int GlassState;

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.air.BF_109.class;
        com.maddox.rts.Property.set(var_class, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
