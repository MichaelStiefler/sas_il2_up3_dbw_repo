// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BI_6.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme6, PaintSchemeFMPar02, TypeFighter, NetAircraft, 
//            PaintScheme

public class BI_6 extends com.maddox.il2.objects.air.Scheme6
    implements com.maddox.il2.objects.air.TypeFighter
{

    public BI_6()
    {
        bHasEngine = true;
        flame = null;
        dust = null;
        trail = null;
        sprite = null;
    }

    public void destroy()
    {
        if(com.maddox.il2.engine.Actor.isValid(flame))
            flame.destroy();
        if(com.maddox.il2.engine.Actor.isValid(dust))
            dust.destroy();
        if(com.maddox.il2.engine.Actor.isValid(trail))
            trail.destroy();
        if(com.maddox.il2.engine.Actor.isValid(sprite))
            sprite.destroy();
        super.destroy();
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            flame = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1F);
            dust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100D.eff", -1F);
            trail = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100T.eff", -1F);
            sprite = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboJRD1100S.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            if(com.maddox.il2.ai.World.cur().camouflage == 1)
            {
                hierMesh().chunkVisible("GearL1_D0", false);
                hierMesh().chunkVisible("GearR1_D0", false);
                hierMesh().chunkVisible("GearL4_D0", false);
                hierMesh().chunkVisible("GearR4_D0", false);
                hierMesh().chunkVisible("GearL5_D0", false);
                hierMesh().chunkVisible("GearR5_D0", false);
                hierMesh().chunkVisible("GearL6_D0", true);
                hierMesh().chunkVisible("GearR6_D0", true);
                hierMesh().chunkVisible("GearC3_D0", true);
                moveGear(0.0F);
                FM.CT.bHasBrakeControl = false;
            }
        }
        FM.Gears.bTailwheelLocked = true;
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
        if(i != 0)
            return;
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
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
        if(com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 5F + 75F * f, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -5F - 75F * f, 0.0F);
        } else
        {
            hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
            hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        }
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 75F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -75F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -75F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 75F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.BI_6.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f1, 0.0F);
    }

    protected void moveFan(float f)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(isNetMirror())
        {
            if(FM.EI.engines[0].getStage() == 6)
            {
                com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 1.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
            } else
            {
                com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
                com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            }
        } else
        if(bHasEngine && FM.CT.getPower() > 0.0F && FM.EI.engines[0].getStage() == 6)
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
        } else
        {
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
        }
        if(FM.Gears.onGround() && FM.CT.getGear() > 0.9F && FM.getSpeed() > 5F)
        {
            if(FM.Gears.lgear)
                hierMesh().chunkSetAngles("GearL6_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F), -75F + com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F), com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F));
            if(FM.Gears.rgear)
                hierMesh().chunkSetAngles("GearR6_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F), 75F + com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F), com.maddox.il2.ai.World.Rnd().nextFloat(-3F, 3F));
        }
        if(FM.EI.engines[1].getThrustOutput() > 0.4F && FM.EI.engines[1].getStage() == 6)
        {
            if(FM.EI.engines[1].getThrustOutput() > 0.65F)
                FM.AS.setSootState(this, 1, 5);
            else
                FM.AS.setSootState(this, 1, 4);
        } else
        {
            FM.AS.setSootState(this, 1, 0);
        }
        if(FM.EI.engines[2].getThrustOutput() > 0.4F && FM.EI.engines[2].getStage() == 6)
        {
            if(FM.EI.engines[2].getThrustOutput() > 0.65F)
                FM.AS.setSootState(this, 2, 5);
            else
                FM.AS.setSootState(this, 2, 4);
        } else
        {
            FM.AS.setSootState(this, 2, 0);
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    getEnergyPastArmor(12.71F, shot);
                if(s.endsWith("p2"))
                    getEnergyPastArmor(12.699999809265137D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                return;
            }
            if(s.startsWith("xxCANNON01"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(0, 0);
                    getEnergyPastArmor(11.98F, shot);
                }
                return;
            }
            if(s.startsWith("xxCANNON02"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(0, 1);
                    getEnergyPastArmor(11.98F, shot);
                }
                return;
            }
            if(s.startsWith("xxeng2cas"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.hitEngine(shot.initiator, 1, 5);
                return;
            }
            if(s.startsWith("xxeng3cas"))
            {
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.hitEngine(shot.initiator, 2, 5);
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
                    if(getEnergyPastArmor(4.5F, shot) > 0.0F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Ailerons Controls Out..");
                    }
                    break;

                case 2: // '\002'
                case 3: // '\003'
                    if(getEnergyPastArmor(1.5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Ailerons Controls Out..");
                    }
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(1.5F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        debuggunnery("*** Engine1 Throttle Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                        debuggunnery("*** Engine2 Throttle Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 2, 1);
                        debuggunnery("*** Engine3 Throttle Controls Out..");
                    }
                    break;

                case 6: // '\006'
                    if(getEnergyPastArmor(4F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Rudder Controls Out..");
                    }
                    break;

                case 7: // '\007'
                    if(getEnergyPastArmor(1.0F, shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Rudder Controls Out..");
                    }
                    break;
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
            if(s.startsWith("xxpnm"))
            {
                if(getEnergyPastArmor(5.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                    FM.AS.setInternalDamage(shot.initiator, 1);
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2 && getEnergyPastArmor(9.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    debuggunnery("*** Tail1 Spars Damaged..");
                    nextDMGLevels(1, 2, "Tail1_D2", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                {
                    if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        FM.AS.hitTank(shot.initiator, 0, 2);
                        debuggunnery("Fuel Tank: Hit..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat(0.009F, 0.1357F) < shot.mass)
                        FM.AS.hitEngine(shot.initiator, 0, 5);
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
            if(point3d.x > 0.40000000000000002D && point3d.x < 1.6719999999999999D && point3d.z <= 0.39900000000000002D)
                if(point3d.x > 1.387D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                else
                if(point3d.y > 0.0D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
        } else
        if(s.startsWith("xcockpit"))
            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
        else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 2)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel1"))
            hitChunk("Keel1", shot);
        else
        if(s.startsWith("xrudder1"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xVatorL"))
        {
            if(chunkDamageVisible("VatorL") < 1)
                hitChunk("VatorL", shot);
        } else
        if(s.startsWith("xVatorR"))
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
        if(s.startsWith("xAroneL"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xAroneR"))
            hitChunk("AroneR", shot);
        else
        if(!s.startsWith("xengine1"))
            if(s.startsWith("xengine2"))
            {
                if(chunkDamageVisible("Engine2") < 2)
                    hitChunk("Engine2", shot);
            } else
            if(s.startsWith("xengine3"))
            {
                if(chunkDamageVisible("Engine3") < 2)
                    hitChunk("Engine3", shot);
            } else
            if(s.startsWith("xgearl"))
                hitChunk("GearL2", shot);
            else
            if(s.startsWith("xgearr"))
                hitChunk("GearR2", shot);
            else
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

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 3: // '\003'
        case 19: // '\023'
            bHasEngine = false;
            FM.AS.setEngineDies(this, 0);
            return cut(com.maddox.il2.objects.air.BI_6.partNames()[i]);

        case 4: // '\004'
        case 33: // '!'
        case 34: // '"'
        case 35: // '#'
            FM.AS.setEngineDies(this, 1);
            return cut(com.maddox.il2.objects.air.BI_6.partNames()[i]);

        case 5: // '\005'
        case 36: // '$'
        case 37: // '%'
        case 38: // '&'
            FM.AS.setEngineDies(this, 2);
            return cut(com.maddox.il2.objects.air.BI_6.partNames()[i]);

        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
        case 20: // '\024'
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
        default:
            return super.cutFM(i, j, actor);
        }
    }

    public void update(float f)
    {
        super.update(f);
        if(isNetMirror())
            return;
        FM.setCapableOfBMP(true, this);
        FM.setCapableOfACM(true);
        bPowR = this == com.maddox.il2.ai.World.getPlayerAircraft();
        if((double)FM.getAltitude() - com.maddox.il2.engine.Engine.land().HQ(FM.Loc.x, FM.Loc.y) > 5D && FM.M.fuel > 0.0F)
        {
            if(FM.EI.engines[0].getControlThrottle() > (bPowR ? 0.4120879F : 0.77F) && FM.EI.engines[0].getStage() == 0)
            {
                FM.EI.engines[0].setStage(this, 6);
                if(bPowR)
                    com.maddox.il2.game.HUD.log("EngineI" + (FM.EI.engines[0].getStage() != 6 ? 48 : '1'));
            }
            if(FM.CT.PowerControl < (bPowR ? 0.4120879F : 0.77F) && FM.EI.engines[0].getStage() > 0)
            {
                FM.EI.engines[0].setEngineStops(this);
                if(bPowR)
                    com.maddox.il2.game.HUD.log("EngineI" + (FM.EI.engines[0].getStage() != 6 ? 48 : '1'));
            }
        }
        if(FM.EI.engines[1].getControlThrottle() > 0.8F && FM.EI.engines[1].getStage() == 0 && FM.M.nitro > 0.0F)
            FM.EI.engines[1].setStage(this, 6);
        if(FM.EI.engines[1].getControlThrottle() < 0.8F && FM.EI.engines[1].getStage() == 6)
            FM.EI.engines[1].setStage(this, 0);
        if(FM.EI.engines[2].getControlThrottle() > 0.8F && FM.EI.engines[2].getStage() == 0 && FM.M.nitro > 0.0F)
            FM.EI.engines[2].setStage(this, 6);
        if(FM.EI.engines[2].getControlThrottle() < 0.8F && FM.EI.engines[2].getStage() == 6)
            FM.EI.engines[2].setStage(this, 0);
        if(FM.isPlayers() && com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeedKMH()) > 750F)
        {
            v.x = v.z = 0.0D;
            v.y = com.maddox.il2.objects.air.BI_6.cvt(com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeedKMH()), 750F, 950F, 0.0F, 400000F);
            ((com.maddox.il2.fm.RealFlightModel)FM).gunMomentum(v, false);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final float powR = 0.4120879F;
    private static final float powA = 0.77F;
    private boolean bHasEngine;
    private com.maddox.il2.engine.Eff3DActor flame;
    private com.maddox.il2.engine.Eff3DActor dust;
    private com.maddox.il2.engine.Eff3DActor trail;
    private com.maddox.il2.engine.Eff3DActor sprite;
    private boolean bPowR;
    private static com.maddox.JGP.Vector3d v = new Vector3d();

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "BI-6");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/BI-6/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/BI-6.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitBI_6.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.87325F);
        com.maddox.il2.objects.air.BI_6.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.BI_6.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.BI_6.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKki 90", "MGunShVAKki 90"
        });
        com.maddox.il2.objects.air.BI_6.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
