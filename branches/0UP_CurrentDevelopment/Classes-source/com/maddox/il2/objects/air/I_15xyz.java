// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_15xyz.java

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
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeTNBFighter, Cockpit, 
//            Aircraft

public class I_15xyz extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeTNBFighter
{

    public I_15xyz()
    {
        suspension = 0.0F;
        suspR = 0.0F;
        suspL = 0.0F;
        blisterRemoved = false;
        sideDoorOpened = false;
    }

    public void moveCockpitDoor(float f)
    {
        if(f > 0.01F)
            sideDoorOpened = true;
        else
            sideDoorOpened = false;
        hierMesh().chunkSetAngles("blister4_D0", 0.0F, -f * 177.7F, 0.0F);
        hierMesh().chunkSetAngles("blister3_D0", 0.0F, -f * 15.6F, 0.0F);
        if(FM.getSpeedKMH() > 314F && f < 0.6F && !blisterRemoved)
            doRemoveBlisters();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void hitDaSilk()
    {
        super.hitDaSilk();
        if(!sideDoorOpened && FM.AS.bIsAboutToBailout && !FM.AS.isPilotDead(0))
        {
            sideDoorOpened = true;
            FM.CT.bHasCockpitDoorControl = true;
            FM.CT.forceCockpitDoor(0.0F);
            FM.AS.setCockpitDoor(this, 1);
        }
    }

    private final void doRemoveBlisters()
    {
        blisterRemoved = true;
        FM.CT.bHasCockpitDoorControl = false;
        bChangedPit = true;
        if(hierMesh().chunkFindCheck("blister4_D0") != -1)
        {
            hierMesh().hideSubTrees("blister4_D0");
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("blister4_D0"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
        }
        if(hierMesh().chunkFindCheck("blister3_D0") != -1)
        {
            hierMesh().hideSubTrees("blister3_D0");
            com.maddox.il2.objects.Wreckage wreckage1 = new Wreckage(this, hierMesh().chunkFind("blister3_D0"));
            wreckage1.collide(true);
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            vector3d1.set(FM.Vwld);
            wreckage1.setSpeed(vector3d1);
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

    public boolean cut(java.lang.String s)
    {
        boolean flag = super.cut(s);
        if(s.equalsIgnoreCase("WingLIn"))
            hierMesh().chunkVisible("WingLMid_CAP", true);
        else
        if(s.equalsIgnoreCase("WingRIn"))
            hierMesh().chunkVisible("WingRMid_CAP", true);
        return flag;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(i == 19)
            FM.Gears.hitCentreGear();
        return super.cutFM(i, j, actor);
    }

    public void update(float f)
    {
        FM.CT.FlapsControl = 0.0F;
        float f1 = com.maddox.il2.objects.air.Aircraft.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.0F, 10F);
        hierMesh().chunkSetAngles("Water_D0", 0.0F, f1, 0.0F);
        super.update(f);
    }

    protected void moveFlap(float f)
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        if(hiermesh.chunkFindCheck("SkiR1_D0") != -1)
        {
            float f1 = 15F;
            float f2 = (float)(java.lang.Math.random() * 2D) - 1.0F;
            float f3 = (float)(java.lang.Math.random() * 2D) - 1.0F;
            float f4 = (float)(java.lang.Math.random() * 2D) - 1.0F;
            float f5 = (float)(java.lang.Math.random() * 2D) - 1.0F;
            float f6 = f1 / 20F;
            hiermesh.chunkSetAngles("SkiR1_D0", 0.0F, -f1, 0.0F);
            hiermesh.chunkSetAngles("SkiL1_D0", 0.0F, -f1, 0.0F);
            hiermesh.chunkSetAngles("SkiC_D0", 0.0F, f1, 0.0F);
            hiermesh.chunkSetAngles("LSkiFrontDownWire1_d0", 0.0F, -f6 * 4F, f6 * 12.4F);
            hiermesh.chunkSetAngles("LSkiFrontDownWire2_d0", 0.0F, -f6 * 4F, f6 * 12.4F);
            com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.16F * f6;
            com.maddox.il2.objects.air.Aircraft.xyz[1] = 0.0F;
            com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
            hiermesh.chunkSetLocate("LSkiFrontUpWire_d0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            hiermesh.chunkSetAngles("LWire1_d0", 0.0F, 6.5F * f6 + f6 * -20F * f4, f6 * 60F);
            hiermesh.chunkSetAngles("LWire12_d0", 0.0F, 6.5F * f6 + f6 * 20F * f5, f6 * 70F);
            float f7 = f6 * -5F;
            float f8 = f6 * -10F;
            float f9 = f6 * -15F;
            float f10 = f6 * 5F * f4;
            float f11 = f6 * 10F * f4;
            float f12 = f6 * -5F * f5;
            float f13 = f6 * 5F * f2;
            float f14 = f6 * 10F * f2;
            float f15 = f6 * -5F * f3;
            hiermesh.chunkSetAngles("LWire2_d0", 0.0F, f11, f7);
            hiermesh.chunkSetAngles("LWire3_d0", 0.0F, f10, f8);
            hiermesh.chunkSetAngles("LWire4_d0", 0.0F, f11, f8);
            hiermesh.chunkSetAngles("LWire5_d0", 0.0F, f10, f8);
            hiermesh.chunkSetAngles("LWire6_d0", 0.0F, f11, f9);
            hiermesh.chunkSetAngles("LWire7_d0", 0.0F, f10, f8);
            hiermesh.chunkSetAngles("LWire8_d0", 0.0F, f11, f9);
            hiermesh.chunkSetAngles("LWire9_d0", 0.0F, f10, f7);
            hiermesh.chunkSetAngles("LWire10_d0", 0.0F, f11, f7);
            hiermesh.chunkSetAngles("LWire11_d0", 0.0F, f10, f7);
            hiermesh.chunkSetAngles("LWire13_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire14_d0", 0.0F, f12, f9);
            hiermesh.chunkSetAngles("LWire15_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire16_d0", 0.0F, f12, f9);
            hiermesh.chunkSetAngles("LWire17_d0", 0.0F, 0.0F, f8);
            hiermesh.chunkSetAngles("LWire18_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire19_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire20_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire21_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("LWire22_d0", 0.0F, f12, f8);
            hiermesh.chunkSetAngles("RSkiFrontDownWire1_d0", 0.0F, f6 * 4F, f6 * 12.4F);
            hiermesh.chunkSetAngles("RSkiFrontDownWire2_d0", 0.0F, f6 * 4F, f6 * 12.4F);
            hiermesh.chunkSetLocate("RSkiFrontUpWire_d0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
            hiermesh.chunkSetAngles("RWire1_d0", 0.0F, -6.5F * f6 + f6 * -20F * f2, f6 * 60F);
            hiermesh.chunkSetAngles("RWire12_d0", 0.0F, -6.5F * f6 + f6 * 20F * f3, f6 * 70F);
            hiermesh.chunkSetAngles("RWire2_d0", 0.0F, f14, f7);
            hiermesh.chunkSetAngles("RWire3_d0", 0.0F, f13, f8);
            hiermesh.chunkSetAngles("RWire4_d0", 0.0F, f14, f8);
            hiermesh.chunkSetAngles("RWire5_d0", 0.0F, f13, f9);
            hiermesh.chunkSetAngles("RWire6_d0", 0.0F, f14, f8);
            hiermesh.chunkSetAngles("RWire7_d0", 0.0F, f13, f8);
            hiermesh.chunkSetAngles("RWire8_d0", 0.0F, f14, f8);
            hiermesh.chunkSetAngles("RWire9_d0", 0.0F, f13, f7);
            hiermesh.chunkSetAngles("RWire10_d0", 0.0F, f14, f7);
            hiermesh.chunkSetAngles("RWire11_d0", 0.0F, f13, f7);
            hiermesh.chunkSetAngles("RWire13_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire14_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire15_d0", 0.0F, f15, f9);
            hiermesh.chunkSetAngles("RWire16_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire17_d0", 0.0F, 0.0F, f9);
            hiermesh.chunkSetAngles("RWire18_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire19_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire20_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire21_d0", 0.0F, f15, f8);
            hiermesh.chunkSetAngles("RWire22_d0", 0.0F, f15, f8);
        }
    }

    protected void moveGear(float f)
    {
    }

    public void moveWheelSink()
    {
        if(FM.Gears.onGround())
            suspension = suspension + 0.008F;
        else
            suspension = suspension - 0.008F;
        if(suspension < 0.0F)
        {
            suspension = 0.0F;
            if(!FM.isPlayers())
                FM.Gears.bTailwheelLocked = true;
        }
        if(suspension > 0.1F)
            suspension = 0.1F;
        com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 0.0F, 25F, 0.0F, 1.0F);
        suspL = FM.Gears.gWheelSinking[0] * f + suspension;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = -com.maddox.il2.objects.air.Aircraft.cvt(suspL, 0.0F, 0.24F, 0.0F, 0.24F);
        hierMesh().chunkSetLocate("GearL2_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        suspR = FM.Gears.gWheelSinking[1] * f + suspension;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = -com.maddox.il2.objects.air.Aircraft.cvt(suspR, 0.0F, 0.24F, 0.0F, 0.24F);
        hierMesh().chunkSetLocate("GearR2_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
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
                        if(getEnergyPastArmor(2.3F, shot) > 0.0F)
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
                    if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.5F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F)
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
                    if(s.startsWith("xxlockr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
                        nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvl") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockvr") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
                        nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                    }
                    if(s.startsWith("xxlockal") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** AroneL Lock Shot Off..");
                        nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                    }
                    if(s.startsWith("xxlockar") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
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
                        if(getEnergyPastArmor(0.44999998807907104D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                        }
                    } else
                    if(s.endsWith("case"))
                    {
                        if(getEnergyPastArmor(5.1F, shot) > 0.0F)
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
                        getEnergyPastArmor(22.5F, shot);
                    } else
                    if(s.startsWith("xxeng1cyls"))
                    {
                        if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.5F, 23.9F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 1.12F)
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
                        if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
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
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x80);
                }
                if(s.startsWith("xxtank1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                {
                    if(FM.AS.astateTankStates[0] == 0)
                    {
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        FM.AS.hitTank(shot.initiator, 0, 2);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.999F)
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
                } else
                if(s.startsWith("xxeqpt"))
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(s.startsWith("xcf1"))
            {
                if(point3d.x > -1.147D && point3d.x < -0.86899999999999999D && point3d.z > 0.65300000000000002D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.012F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(point3d.x > -1.1950000000000001D && point3d.x < -0.90400000000000003D && point3d.z > 0.20300000000000001D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
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

    public static boolean bChangedPit = false;
    private float suspension;
    public float suspR;
    public float suspL;
    public boolean blisterRemoved;
    private boolean sideDoorOpened;

}
