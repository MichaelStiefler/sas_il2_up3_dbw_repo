// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   KI_43.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, Aircraft, Cockpit, 
//            PaintScheme

public abstract class KI_43 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public KI_43()
    {
    }

    protected void moveFan(float f)
    {
        super.moveFan(f);
        hierMesh().chunkSetAngles(com.maddox.il2.objects.air.Aircraft.Props[0][0], 0.0F, propPos[0], 0.0F);
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

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.59F);
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
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -85F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -85F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.KI_43.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.245F, 0.0F, 0.245F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = f;
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.245F, 0.0F, 0.245F);
        com.maddox.il2.objects.air.Aircraft.xyz[1] = f;
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.26F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = -6F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = 16F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 1.0F * f;
        hierMesh().chunkSetLocate("Flap01_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[0] = 0.26F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[0] = 6F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[1] = -16F * f;
        com.maddox.il2.objects.air.Aircraft.ypr[2] = 1.0F * f;
        hierMesh().chunkSetLocate("Flap02_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxammo"))
            {
                if(s.startsWith("xxammol") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setJamBullets(0, 0);
                if(s.startsWith("xxammor") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.setJamBullets(0, 1);
                getEnergyPastArmor(16F, shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                debuggunnery("Controls: Hit..");
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                case 2: // '\002'
                    if(getEnergyPastArmor(0.99F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.175F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                    if(getEnergyPastArmor(0.99F, shot) <= 0.0F || com.maddox.il2.ai.World.Rnd().nextFloat() >= 0.675F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                    break;

                case 4: // '\004'
                    if(getEnergyPastArmor(0.22F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(4.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxctank"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    hitBone("xxtank1", shot, point3d);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    hitBone("xxtank2", shot, point3d);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    hitBone("xxtank3", shot, point3d);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    hitBone("xxtank4", shot, point3d);
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 140000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Stucks..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 85000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Engine Damaged..");
                        }
                    } else
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, 1);
                    } else
                    {
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - 0.002F);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Crank Case Hit - Readyness Reduced to " + FM.EI.engines[0].getReadyness() + "..");
                    }
                    getEnergyPastArmor(12F, shot);
                }
                if(s.endsWith("cyls"))
                {
                    if(getEnergyPastArmor(5.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.75F)
                    {
                        FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19000F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            FM.AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Cylinders Hit - Engine Fires..");
                        }
                    }
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag1"))
                {
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto #0 Destroyed..");
                    getEnergyPastArmor(25F, shot);
                }
                if(s.endsWith("mag2"))
                {
                    FM.EI.engines[0].setMagnetoKnockOut(shot.initiator, 1);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Engine Module: Magneto #1 Destroyed..");
                    getEnergyPastArmor(25F, shot);
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
                if(s.startsWith("xxlockal") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneL Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneL_D" + chunkDamageVisible("AroneL"), shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Lock Construction: AroneR Lock Shot Off..");
                    nextDMGLevels(3, 2, "AroneR_D" + chunkDamageVisible("AroneR"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxmgun01"))
            {
                if(getEnergyPastArmor(0.75F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun 01 Disabled..");
                    FM.AS.setJamBullets(0, 0);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxmgun02"))
            {
                if(getEnergyPastArmor(0.75F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun 02 Disabled..");
                    FM.AS.setJamBullets(0, 1);
                    getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxoil"))
            {
                if(getEnergyPastArmor(0.25F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                {
                    FM.AS.hitOil(shot.initiator, 0);
                    getEnergyPastArmor(0.22F, shot);
                    debuggunnery("Engine Module: Oil Tank Pierced..");
                }
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 12.39F), shot) > 0.0F)
                {
                    debuggunnery("Pneumo System: Disabled..");
                    FM.AS.setInternalDamage(shot.initiator, 1);
                }
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.25F, 12.39F), shot);
                return;
            }
            if(s.startsWith("xxspar"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                if((s.endsWith("li1") || s.endsWith("li2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(2.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(2.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(2.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(2.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(1.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparsl") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabL Spar Damaged..");
                    nextDMGLevels(1, 2, "StabL_D" + chunkDamageVisible("StabL"), shot.initiator);
                }
                if(s.startsWith("xxsparsr") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 0.115F) < shot.mass && getEnergyPastArmor(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** StabR Spar Damaged..");
                    nextDMGLevels(1, 2, "StabR_D" + chunkDamageVisible("StabR"), shot.initiator);
                }
                if(s.startsWith("xxspark") && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor((double)(6.8F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F)) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.Aircraft.debugprintln(this, "*** Keel Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D" + chunkDamageVisible("Keel1"), shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                {
                    if(FM.AS.astateTankStates[j] == 0)
                    {
                        debuggunnery("Fuel Tank (" + j + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, j, 1);
                        FM.AS.doSetTankState(shot.initiator, j, 1);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.08F || shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.hitTank(shot.initiator, j, 4);
                        debuggunnery("Fuel Tank (" + j + "): Hit..");
                    }
                }
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcf") || s.startsWith("xblister"))
        {
            hitChunk("CF", shot);
            if(s.startsWith("xblister"))
            {
                if(point3d.x < -0.72099999999999997D)
                {
                    if(point3d.y > 0.19800000000000001D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                    if(point3d.y < -0.19800000000000001D)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                } else
                {
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                }
            } else
            {
                if(point3d.x > -0.55900000000000005D && point3d.x < 0.39600000000000002D && point3d.z > -0.13500000000000001D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                else
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.125F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
            }
            return;
        }
        if(s.startsWith("xeng"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xtail"))
            hitChunk("Tail1", shot);
        else
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
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                FM.AS.setInternalDamage(shot.initiator, 0);
            }
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                debuggunnery("Undercarriage: Stuck..");
                FM.AS.setInternalDamage(shot.initiator, 3);
            }
        } else
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.KI_43.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
    }
}
