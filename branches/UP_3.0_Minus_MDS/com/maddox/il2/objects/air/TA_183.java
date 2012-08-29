// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   TA_183.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFMPar06, TypeFighter, TypeBNZFighter, 
//            TypeX4Carrier, TypeFighterAceMaker, Aircraft, NetAircraft, 
//            PaintScheme

public class TA_183 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, com.maddox.il2.objects.air.TypeX4Carrier, com.maddox.il2.objects.air.TypeFighterAceMaker
{

    public TA_183()
    {
        bToFire = false;
        tX4Prev = 0L;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        kangle = 0.0F;
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

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.9F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, -90F), 0.0F);
        hiermesh.chunkSetAngles("GearC6_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, -65F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, 0.65F);
        hiermesh.chunkSetLocate("GearL5_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, -60F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, -65F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.ypr[0] = com.maddox.il2.objects.air.Aircraft.ypr[1] = com.maddox.il2.objects.air.Aircraft.ypr[2] = com.maddox.il2.objects.air.Aircraft.xyz[0] = com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.xyz[2] = 0.0F;
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 0.6F, 0.0F, 0.65F);
        hiermesh.chunkSetLocate("GearR5_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.1F, 0.2F, 0.0F, -60F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.TA_183.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() > 0.8F)
            hierMesh().chunkSetAngles("GearC6_D0", 0.0F, f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.28F, 0.0F, 0.295F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.28F, 0.0F, -48F), 0.0F);
        hierMesh().chunkSetAngles("GearL8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.28F, 0.0F, -96F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.28F, 0.0F, 0.295F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        hierMesh().chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.28F, 0.0F, -48F), 0.0F);
        hierMesh().chunkSetAngles("GearR8_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.28F, 0.0F, -96F), 0.0F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.1795F, 0.0F, 0.1795F);
        hierMesh().chunkSetLocate("GearC3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("p1"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12F, 19F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p2"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(12.7F, 12.7F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("g1"))
                {
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(20F, 60F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                }
            } else
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(1.22F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    if(getEnergyPastArmor(1.22F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    break;
                }
            } else
            if(s.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("bloc"))
                    getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 60F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("cams") && getEnergyPastArmor(0.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 20F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Engine Cams Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 2);
                        debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 1);
                        debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(s.endsWith("eqpt") && com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                {
                    FM.AS.hitEngine(shot.initiator, 0, 3);
                    debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                if(s.endsWith("exht"));
            } else
            if(s.startsWith("xxhyd"))
                FM.AS.setInternalDamage(shot.initiator, 3);
            else
            if(s.startsWith("xxmgun0"))
            {
                int j = s.charAt(7) - 49;
                if(getEnergyPastArmor(0.5F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun (" + j + ") Disabled..");
                    FM.AS.setJamBullets(0, j);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
            } else
            if(s.startsWith("xxpnm"))
                FM.AS.setInternalDamage(shot.initiator, 1);
            else
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Spar Construction: Hit..");
                if(s.startsWith("xxsparli") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLIn Spar Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                }
                if(s.startsWith("xxsparri") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRIn Spar Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                }
                if(s.startsWith("xxsparlm") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLMid Spar Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), shot.initiator);
                }
                if(s.startsWith("xxsparrm") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingRMid Spar Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
                }
                if(s.startsWith("xxsparlo") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingLOut Spar Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D" + chunkDamageVisible("WingLOut"), shot.initiator);
                }
                if(s.startsWith("xxsparro") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** WingROut Spar Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D" + chunkDamageVisible("WingROut"), shot.initiator);
                }
                if(s.startsWith("xxspark") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor((double)(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F)) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Keel Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(3.86F / (float)java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    debuggunnery("Spar Construction: Tail1 Ribs Hit, Breaking in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                }
            } else
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                {
                    if(FM.AS.astateTankStates[k] == 0)
                    {
                        debuggunnery("Fuel Tank (" + k + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, k, 1);
                        FM.AS.doSetTankState(shot.initiator, k, 1);
                    }
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        FM.AS.hitTank(shot.initiator, k, 2);
                        debuggunnery("Fuel Tank (" + k + "): Hit..");
                    }
                }
            }
        } else
        if(s.startsWith("xcf") || s.startsWith("xcockpit"))
        {
            hitChunk("CF", shot);
            if(s.startsWith("xcockpit"))
            {
                if(point3d.x > 2D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                } else
                {
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                }
            } else
            if(point3d.x > 2.5D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            } else
            if(point3d.y > 0.0D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
            else
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose") < 2)
                hitChunk("Nose", shot);
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
            if(s.startsWith("xstabl"))
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr"))
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
        if(s.startsWith("xgear"))
        {
            if(s.endsWith("1") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(s.endsWith("2") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                debuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l = s.charAt(6) - 49;
            } else
            {
                l = s.charAt(5) - 49;
            }
            hitFlesh(l, shot, ((int) (byte0)));
        }
    }

    public void update(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && FM.AS.isMaster())
            if(FM.EI.engines[0].getPowerOutput() > 0.8F && FM.EI.engines[0].getStage() == 6)
            {
                if(FM.EI.engines[0].getPowerOutput() > 0.95F)
                    FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 3);
                else
                    FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 2);
            } else
            {
                FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 0);
            }
        super.update(f);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.EI.engines[0].setEngineDies(actor);
            return super.cutFM(i, j, actor);

        case 11: // '\013'
            cut("StabL");
            cut("StabR");
            FM.cut(17, j, actor);
            FM.cut(18, j, actor);
            break;

        case 13: // '\r'
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
        if((!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode()) && flag && (FM instanceof com.maddox.il2.ai.air.Pilot))
        {
            com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)FM;
            if(pilot.get_maneuver() == 63 && pilot.target != null)
            {
                com.maddox.JGP.Point3d point3d = new Point3d(pilot.target.Loc);
                point3d.sub(((com.maddox.JGP.Tuple3d) (FM.Loc)));
                FM.Or.transformInv(((com.maddox.JGP.Tuple3d) (point3d)));
                if((point3d.x > 4000D && point3d.x < 5500D || point3d.x > 100D && point3d.x < 5000D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F) && com.maddox.rts.Time.current() > tX4Prev + 10000L)
                {
                    bToFire = true;
                    tX4Prev = com.maddox.rts.Time.current();
                }
            }
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 1.0F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -1F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 1.0F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -1F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
        return true;
    }

    public void typeFighterAceMakerAdjDistanceReset()
    {
    }

    public void typeFighterAceMakerAdjDistancePlus()
    {
        k14Distance += 10F;
        if(k14Distance > 800F)
            k14Distance = 800F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "AskaniaWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "AskaniaWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        k14Mode = ((int) (netmsginput.readByte()));
        k14WingspanType = ((int) (netmsginput.readByte()));
        k14Distance = netmsginput.readFloat();
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

    public boolean bToFire;
    private long tX4Prev;
    private float deltaAzimuth;
    private float deltaTangage;
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    private float kangle;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Ta-183");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/Ta-183(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1946F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Ta-183.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.air.CockpitTA_183.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.2158F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
            2, 2, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", 
            "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", 
            "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", 
            "_ExternalRock25", "_ExternalRock25", "_ExternalRock26", "_ExternalRock26", "_ExternalRock27", "_ExternalRock27", "_ExternalRock28", "_ExternalRock28"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "24r4m", new java.lang.String[] {
            "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", "PylonMe262_R4M_Left", "PylonMe262_R4M_Right", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", "RocketGunR4M 1", 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4x4", new java.lang.String[] {
            "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", "MGunMK108k 120", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
