// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BF_109Z.java

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
//            Scheme2, PaintSchemeFMPar04, TypeFighter, Aircraft, 
//            NetAircraft, PaintScheme

public class BF_109Z extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter
{

    public BF_109Z()
    {
        kangle = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.05F, 0.49F, 0.0F, 1.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f1, 0.0F, 0.0F);
        f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.12F, 0.95F, 0.0F, 1.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, 77.5F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 33.5F * f1, 0.0F, 0.0F);
        f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.3F, 0.82F, 0.0F, 1.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f1, 0.0F, 0.0F);
        f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.34F, 0.78F, 0.0F, 1.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -77.5F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", -33.5F * f1, 0.0F, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.BF_109Z.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("GearL6_D0", 0.0F, -FM.Gears.gWheelAngles[0], 0.0F);
        hierMesh().chunkSetAngles("GearR6_D0", 0.0F, -FM.Gears.gWheelAngles[1], 0.0F);
        hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -FM.Gears.gWheelAngles[2], 0.0F);
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 1.5F), 0.0F);
        }
        hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01L2_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap01U2_D0", 0.0F, 20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02L2_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Flap02U2_D0", 0.0F, 20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        if(kangle > 1.0F)
            kangle = 1.0F;
        super.update(f);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            if(FM.AS.astateTankStates[2] > 5)
                FM.AS.repairTank(2);
            if(FM.AS.astateTankStates[3] > 5)
                FM.AS.repairTank(3);
        }
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f1, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 11: // '\013'
        case 12: // '\f'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 21: // '\025'
        case 22: // '\026'
        case 23: // '\027'
        case 24: // '\030'
        case 25: // '\031'
        case 26: // '\032'
        case 27: // '\033'
        case 28: // '\034'
        case 29: // '\035'
        case 30: // '\036'
        case 31: // '\037'
        case 32: // ' '
        case 33: // '!'
        case 34: // '"'
        case 35: // '#'
        default:
            break;

        case 3: // '\003'
        case 4: // '\004'
            return false;

        case 18: // '\022'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.5F)
                break;
            cut("Keel2");
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                cut("Tail2");
            break;

        case 13: // '\r'
            cut("WingRIn");
            cut("Engine2");
            cut("Tail2");
            FM.cut(36, j, actor);
            FM.cut(4, j, actor);
            FM.cut(20, j, actor);
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
            {
                cut("StabR");
                FM.cut(18, j, actor);
                FM.cut(17, j, actor);
            }
            break;

        case 19: // '\023'
            cut("StabR");
            FM.cut(18, j, actor);
            FM.cut(17, j, actor);
            break;

        case 20: // '\024'
            cut("StabR");
            FM.cut(18, j, actor);
            FM.cut(17, j, actor);
            break;

        case 36: // '$'
            cut("Engine2");
            cut("Nose");
            cut("Tail2");
            FM.cut(4, j, actor);
            FM.cut(13, j, actor);
            FM.cut(20, j, actor);
            break;

        case 9: // '\t'
            cut("GearL4");
            break;

        case 10: // '\n'
            cut("GearR4");
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
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor: Hit..");
                if(s.endsWith("p1"))
                {
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(20F, 30F), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Hit..");
                    if(shot.power <= 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Armor Glass: Bullet Stopped..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                            doRicochetBack(shot);
                    }
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(0.5F, shot);
                else
                if(s.endsWith("p3"))
                {
                    if(point3d.z < -0.27000000000000002D)
                        getEnergyPastArmor(4.0999999046325684D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-006D), shot);
                    else
                        getEnergyPastArmor(8.1000003814697266D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
                } else
                if(s.endsWith("p4"))
                    getEnergyPastArmor(10.100000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.z) + 9.9999997473787516E-006D), shot);
                else
                if(s.endsWith("p5"))
                    getEnergyPastArmor(10.100000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
                else
                if(s.endsWith("p6"))
                    getEnergyPastArmor(12.100000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-006D), shot);
                else
                if(s.endsWith("a1"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        shot.powerType = 0;
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(5F, 7F), shot);
                }
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                if(s.length() > 11)
                    i = 10 + (s.charAt(11) - 48);
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                case 4: // '\004'
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Control Crank Destroyed..");
                    }
                    break;

                case 2: // '\002'
                case 3: // '\003'
                case 8: // '\b'
                    if(getEnergyPastArmor(0.12F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Aileron Controls: Disabled..");
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                case 10: // '\n'
                case 11: // '\013'
                    if(getEnergyPastArmor(0.002F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Elevator Controls: Disabled / Strings Broken..");
                    }
                    break;

                case 7: // '\007'
                case 9: // '\t'
                    if(getEnergyPastArmor(2.3F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Controls: Disabled..");
                    }
                    break;

                case 12: // '\f'
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Control Column: Hit, Controls Destroyed..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 13: // '\r'
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Throttle Quadrant: Hit, Engine Controls Disabled..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                if(s.startsWith("xxspart1") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
                if(s.startsWith("xxspart2") && chunkDamageVisible("Tail2") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Tail2 Spars Broken in Half..");
                    nextDMGLevels(1, 2, "Tail2_D3", shot.initiator);
                }
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(9.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxwj"))
            {
                if(getEnergyPastArmor(12.5F, shot) > 0.0F)
                {
                    if(s.endsWith("l1"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingL Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                    }
                    if(s.endsWith("l2") || s.endsWith("r2"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingR Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                    }
                    if(s.endsWith("r1"))
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingR Outer Console Lock Destroyed..");
                        nextDMGLevels(4, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
                    }
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
                if(s.startsWith("xxlockr1") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder1 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                }
                if(s.startsWith("xxlockr2") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder2 Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder2_D" + chunkDamageVisible("Rudder2"), shot.initiator);
                }
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(5) - 49;
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Hit..");
                if(s.endsWith("pipe"))
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && FM.CT.Weapons[0] != null)
                    {
                        FM.AS.setJamBullets(0, j);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Nose Nozzle Pipe Bent..");
                    }
                    getEnergyPastArmor(0.3F, shot);
                } else
                if(s.endsWith("prop"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Prop Governor Hit, Disabled..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.EI.engines[j].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Bullet Jams Reductor Gear..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 3);
                            FM.AS.setEngineSpecificDamage(shot.initiator, j, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, j, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Supercharger Disabled..");
                    }
                } else
                if(s.endsWith("feed"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && FM.EI.engines[j].getPowerOutput() > 0.7F)
                    {
                        FM.AS.hitEngine(shot.initiator, j, 100);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                } else
                if(s.endsWith("fuel"))
                {
                    if(getEnergyPastArmor(1.1F, shot) > 0.0F)
                    {
                        FM.EI.engines[j].setEngineStops(shot.initiator);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Fuel Line Stalled, Engine Stalled..");
                    }
                } else
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, j);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Bullet Jams Crank Ball Bearing..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Crank Case Hit, Readyness Reduced to " + FM.EI.engines[j].getReadyness() + "..");
                        }
                        FM.EI.engines[j].setReadyness(shot.initiator, FM.EI.engines[j].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Crank Case Hit, Readyness Reduced to " + FM.EI.engines[j].getReadyness() + "..");
                    }
                    getEnergyPastArmor(22.5F, shot);
                } else
                if(s.endsWith("cyl1") || s.endsWith("cyl2"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[j].getCylindersRatio() * 1.75F)
                    {
                        FM.EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Cylinders Hit, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            FM.AS.hitEngine(shot.initiator, j, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, j);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Bullet Jams Piston Head..");
                        }
                        getEnergyPastArmor(22.5F, shot);
                    }
                } else
                if(s.endsWith("mag1") || s.endsWith("mag2"))
                {
                    int j1 = s.charAt(9) - 49;
                    FM.EI.engines[j].setMagnetoKnockOut(shot.initiator, j1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Magneto " + j1 + " Destroyed..");
                } else
                if(s.endsWith("sync"))
                {
                    if(getEnergyPastArmor(2.1F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.5F);
                } else
                if(s.endsWith("oil1"))
                {
                    FM.AS.hitOil(shot.initiator, j);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + j + ": Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                {
                    int k = s.charAt(5) - 49;
                    if(k == 7)
                        k = 0;
                    if(k == 8)
                        k = 1;
                    FM.AS.hitOil(shot.initiator, k);
                    getEnergyPastArmor(0.22F, shot);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine" + k + ": Oil Tank Pierced..");
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int l = s.charAt(6) - 48;
                switch(l)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        if(FM.AS.astateTankStates[2] == 0)
                        {
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                            FM.AS.hitTank(shot.initiator, 2, 1);
                            FM.AS.doSetTankState(shot.initiator, 2, 1);
                        }
                        if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.hitTank(shot.initiator, 2, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                        }
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(0.1F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                        break;
                    if(FM.AS.astateTankStates[3] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 3, 1);
                        FM.AS.doSetTankState(shot.initiator, 3, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, 3, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(0.1F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.25F)
                        break;
                    if(FM.AS.astateTankStates[1] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 1, 1);
                        FM.AS.doSetTankState(shot.initiator, 1, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, 1, 2);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                {
                    int i1 = s.charAt(9) - 49;
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Cannon(" + i1 + "): Disabled..");
                    FM.AS.setJamBullets(0, i1);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
                }
                return;
            }
            if(s.startsWith("xxammo"))
                return;
            else
                return;
        }
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.z > 0.40000000000000002D)
                {
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                } else
                if(point3d.y > 1.7649999999999999D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                if(point3d.x > 0.20000000000000001D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
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
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xrudder2"))
        {
            if(chunkDamageVisible("Rudder2") < 1)
                hitChunk("Rudder2", shot);
        } else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
                hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
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
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int k1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                k1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                k1 = s.charAt(6) - 49;
            } else
            {
                k1 = s.charAt(5) - 49;
            }
            hitFlesh(k1, shot, byte0);
        }
    }

    public void doKillPilot(int i)
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
            if(!FM.AS.bIsAboutToBailout)
            {
                if(hierMesh().isChunkVisible("Blister1_D0"))
                    hierMesh().chunkVisible("Gore1_D0", true);
                hierMesh().chunkVisible("Gore2_D0", true);
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

    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BF_109Z.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Bf109");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Bf-109Z/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1944.5F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Bf-109Z.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitBF_109Z.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7498F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 1, 9, 9, 9, 3, 3, 
            3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON05", "_CANNON04", "_ExternalDev01", "_ExternalDev03", "_ExternalDev02", "_ExternalBomb03", "_ExternalBomb03", 
            "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "mk103", new java.lang.String[] {
            "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", "MGunMK103ki 35", "MGunMK108ki 35", null, "PylonMk103", null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "sc500", new java.lang.String[] {
            "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, "PylonETC900", null, "BombGunSC500", null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "sc500sc250", new java.lang.String[] {
            "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", "PylonETC900", "PylonETC900", "PylonETC900", "BombGunSC500", "BombGunNull", 
            "BombGunSC250", "BombGunSC250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
