// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ME_163B1A.java

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
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFMPar05, TypeFighter, TypeBNZFighter, 
//            NetAircraft, PaintScheme

public class ME_163B1A extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter
{

    public ME_163B1A()
    {
        bCockpitNVentilated = false;
        bCartAttached = true;
        flame = null;
        dust = null;
        trail = null;
        sprite = null;
        turboexhaust = null;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
        pk = 0;
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
        if(com.maddox.il2.engine.Actor.isValid(turboexhaust))
            turboexhaust.destroy();
        super.destroy();
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            flame = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", -1F);
            dust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109D.eff", -1F);
            trail = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", -1F);
            sprite = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", -1F);
            turboexhaust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(turboexhaust, 1.0F);
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("1"))
                {
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    if(getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(30F, 90F) / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot) < 0.0F)
                        doRicochet(shot);
                } else
                if(s.endsWith("2"))
                    getEnergyPastArmor(13.130000114440918D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
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
                    if(getEnergyPastArmor(2.2F / (float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    break;

                case 2: // '\002'
                    if(getEnergyPastArmor(2.2F / (float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z), shot) <= 0.0F)
                        break;
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(2.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if(s.startsWith("xxsparli") && chunkDamageVisible("WingLIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparri") && chunkDamageVisible("WingRIn") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && (double)com.maddox.il2.ai.World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * java.lang.Math.abs(v1.x) && getEnergyPastArmor(17.799999237060547D / (1.0001000165939331D - java.lang.Math.abs(v1.y)), shot) > 0.0F)
                {
                    debuggunnery("*** WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                if(s.startsWith("xxspark") && chunkDamageVisible("Keel1") > 2 && getEnergyPastArmor(3.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    com.maddox.il2.objects.air.ME_163B1A.debugprintln(this, "*** Keel Spars Damaged..");
                    nextDMGLevels(1, 2, "Keel1_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxlock"))
            {
                if(s.startsWith("xxlockal") && getEnergyPastArmor(4.35F, shot) > 0.0F)
                {
                    debuggunnery("*** AroneL Lock Damaged..");
                    nextDMGLevels(1, 2, "AroneL_D0", shot.initiator);
                }
                if(s.startsWith("xxlockar") && getEnergyPastArmor(4.35F, shot) > 0.0F)
                {
                    debuggunnery("*** AroneR Lock Damaged..");
                    nextDMGLevels(1, 2, "AroneR_D0", shot.initiator);
                }
                if(s.startsWith("xxlockfl") && getEnergyPastArmor(4.35F, shot) > 0.0F)
                {
                    debuggunnery("*** VatorL Lock Damaged..");
                    nextDMGLevels(1, 2, "VatorL_D0", shot.initiator);
                }
                if(s.startsWith("xxlockfr") && getEnergyPastArmor(4.35F, shot) > 0.0F)
                {
                    debuggunnery("*** VatorR Lock Damaged..");
                    nextDMGLevels(1, 2, "VatorR_D0", shot.initiator);
                }
                if(s.startsWith("xxlockr") && getEnergyPastArmor(4.32F, shot) > 0.0F)
                {
                    debuggunnery("*** Rudder1 Lock Damaged..");
                    nextDMGLevels(1, 2, "Rudder1_D0", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int j = s.charAt(8) - 48;
                switch(j)
                {
                default:
                    break;

                case 1: // '\001'
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        FM.AS.hitEngine(shot.initiator, 0, 100);
                    if(Pd.x < -2.7000000476837158D)
                        FM.EI.engines[0].setReadyness(shot.initiator, FM.EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.01F, 0.35F));
                    break;

                case 2: // '\002'
                    if(getEnergyPastArmor(4.96F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                        FM.AS.hitEngine(shot.initiator, 0, 100);
                    break;

                case 3: // '\003'
                    getEnergyPastArmor(5.808F, shot);
                    break;
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 48;
                switch(k)
                {
                default:
                    break;

                case 1: // '\001'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 7.9F), shot) > 0.0F)
                        FM.AS.hitTank(shot.initiator, 3, 1);
                    break;

                case 2: // '\002'
                case 3: // '\003'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 7.9F), shot) > 0.0F)
                    {
                        FM.AS.hitTank(shot.initiator, 2, 1);
                        bCockpitNVentilated = true;
                    }
                    // fall through

                case 4: // '\004'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 7.9F), shot) > 0.0F)
                        FM.AS.hitTank(shot.initiator, 0, com.maddox.il2.ai.World.Rnd().nextInt(1, 4));
                    break;

                case 5: // '\005'
                    if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 7.9F), shot) > 0.0F)
                        FM.AS.hitTank(shot.initiator, 1, com.maddox.il2.ai.World.Rnd().nextInt(1, 4));
                    break;
                }
                return;
            }
            if(s.startsWith("xxammo"))
            {
                int l = s.charAt(6) - 48;
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    if(l == 0)
                        FM.AS.setJamBullets(1, 1);
                    else
                        FM.AS.setJamBullets(1, 0);
                return;
            }
            if(s.startsWith("xxgunl") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(2.0F, 35.6F), shot) > 0.0F)
                FM.AS.setJamBullets(1, 0);
            if(s.startsWith("xxgunr") && getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(2.0F, 35.6F), shot) > 0.0F)
                FM.AS.setJamBullets(1, 1);
            if(!s.startsWith("xxeqpt"));
            return;
        }
        if(s.startsWith("xcf"))
        {
            if(Pd.x > 2.0099999999999998D && getEnergyPastArmor(11.11F / ((float)java.lang.Math.sqrt(v1.y * v1.y + v1.z * v1.z) + 0.0001F), shot) <= 0.0F)
            {
                doRicochet(shot);
                return;
            }
            if(Pd.x > 0.80000000000000004D && Pd.x < 2D)
                if(Pd.z > 0.42499999999999999D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                } else
                if(Pd.y > 0.0D)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                    else
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                } else
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
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
        {
            if(chunkDamageVisible("AroneL") < 2)
                hitChunk("AroneL", shot);
        } else
        if(s.startsWith("xaroner"))
        {
            if(chunkDamageVisible("AroneR") < 2)
                hitChunk("AroneR", shot);
        } else
        if(s.startsWith("xflapl"))
        {
            if(chunkDamageVisible("VatorL") < 1)
                hitChunk("VatorL", shot);
        } else
        if(s.startsWith("xflapr"))
        {
            if(chunkDamageVisible("VatorR") < 1)
                hitChunk("VatorR", shot);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i1;
            if(s.endsWith("a") || s.endsWith("a2"))
            {
                byte0 = 1;
                i1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b") || s.endsWith("b2"))
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

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && bCockpitNVentilated)
            FM.AS.hitPilot(this, 0, 1);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(oldVwld < 20F && FM.getSpeed() > 20F)
            {
                com.maddox.il2.engine.Eff3DActor.finish(turboexhaust);
                turboexhaust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallTSPD.eff", -1F);
            }
            if(oldVwld > 20F && FM.getSpeed() < 20F)
            {
                com.maddox.il2.engine.Eff3DActor.finish(turboexhaust);
                turboexhaust = com.maddox.il2.engine.Eff3DActor.New(this, findHook("_Engine1ES_02"), null, 1.0F, "3DO/Effects/Aircraft/WhiteOxySmallGND.eff", -1F);
            }
            oldVwld = FM.getSpeed();
        }
    }

    public void doMurderPilot(int i)
    {
        if(i != 0)
        {
            return;
        } else
        {
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("HMask1_D0", false);
            return;
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -15F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        if(bCartAttached)
        {
            if(f < 1.0F)
            {
                hierMesh().chunkVisible("GearL1_D0", false);
                hierMesh().chunkVisible("GearR1_D0", false);
                if(hierMesh().isChunkVisible("Cart_D0"))
                {
                    hierMesh().chunkVisible("CartDrop_D0", true);
                    cut("CartDrop");
                }
                hierMesh().chunkVisible("Cart_D0", false);
                bCartAttached = false;
                FM.setCapableOfTaxiing(false);
                FM.CT.bHasBrakeControl = false;
            }
        } else
        {
            resetYPRmodifier();
            xyz[1] = -0.3F + 0.1125F * f;
            ypr[1] = 88F;
            hiermesh.chunkSetLocate("Cart_D0", xyz, ypr);
        }
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -15F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    }

    public void moveWheelSink()
    {
        if(!bCartAttached && FM.CT.getGear() > 0.99F)
        {
            float f = com.maddox.il2.objects.air.ME_163B1A.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.066F, -45F, 0.0F);
            hierMesh().chunkSetAngles("GearL2_D0", 0.0F, f, 0.0F);
            hierMesh().chunkSetAngles("GearL3_D0", 0.0F, f, 0.0F);
            hierMesh().chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
            hierMesh().chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
            hierMesh().chunkSetAngles("GearL6_D0", 0.0F, f, 0.0F);
        }
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        reflectControls();
    }

    protected void moveAileron(float f)
    {
        reflectControls();
    }

    private void reflectControls()
    {
        com.maddox.il2.engine.HierMesh hiermesh = hierMesh();
        float f = -20F * FM.CT.getAileron();
        float f1 = 20F * FM.CT.getElevator();
        hiermesh.chunkSetAngles("AroneL_D0", 0.0F, f + f1, 0.0F);
        hiermesh.chunkSetAngles("AroneR_D0", 0.0F, f - f1, 0.0F);
        hiermesh.chunkSetAngles("VatorL_D0", 0.0F, 0.5F * f1, 0.0F);
        hiermesh.chunkSetAngles("VatorR_D0", 0.0F, 0.5F * f1, 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, f1, 0.0F);
    }

    protected void moveFan(float f)
    {
        pk = java.lang.Math.abs((int)(FM.Vwld.length() / 14D));
        if(pk >= 1)
            pk = 1;
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            hierMesh().chunkVisible("Prop1_D0", !bDynamoRotary);
            hierMesh().chunkVisible("PropRot1_D0", bDynamoRotary);
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - FM.Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("Prop1_D0", 0.0F, dynamoOrient, 0.0F);
    }

    public void update(float f)
    {
        super.update(f);
        if(bCartAttached)
        {
            moveGear(FM.CT.getGear());
            if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                FM.CT.bHasBrakeControl = false;
            else
                FM.CT.bHasBrakeControl = true;
        }
        if(FM.AS.isMaster())
        {
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
                if(FM.EI.engines[0].getw() > 0.0F && FM.EI.engines[0].getStage() == 6)
                    FM.AS.setSootState(this, 0, 1);
                else
                    FM.AS.setSootState(this, 0, 0);
            if(oldThtl < 0.35F)
                FM.EI.setThrottle(0.0F);
            else
            if(oldThtl < 0.65F)
                FM.EI.setThrottle(0.35F);
            else
            if(oldThtl < 1.0F)
                FM.EI.setThrottle(0.65F);
            else
                FM.EI.setThrottle(1.0F);
            if(oldThtl != FM.CT.PowerControl)
            {
                oldThtl = FM.CT.PowerControl;
                if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
                    com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogPowerId, "Power", new java.lang.Object[] {
                        new Integer(java.lang.Math.round(oldThtl * 100F))
                    });
            }
            if(oldThtl == 0.0F)
            {
                if(!FM.Gears.onGround())
                {
                    if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode() && FM.EI.engines[0].getStage() == 6)
                        com.maddox.il2.game.HUD.log("EngineI0");
                    FM.EI.engines[0].setEngineStops(this);
                }
            } else
            {
                if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode() && FM.EI.engines[0].getStage() == 0 && FM.M.fuel > 0.0F)
                    com.maddox.il2.game.HUD.log("EngineI1");
                FM.EI.engines[0].setStage(this, 6);
            }
        }
    }

    public void doSetSootState(int i, int j)
    {
        switch(j)
        {
        case 0: // '\0'
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 0.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 0.0F);
            break;

        case 1: // '\001'
            com.maddox.il2.engine.Eff3DActor.setIntesity(flame, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(dust, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(trail, 1.0F);
            com.maddox.il2.engine.Eff3DActor.setIntesity(sprite, 1.0F);
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

    private boolean bCockpitNVentilated;
    public boolean bCartAttached;
    private com.maddox.il2.engine.Eff3DActor flame;
    private com.maddox.il2.engine.Eff3DActor dust;
    private com.maddox.il2.engine.Eff3DActor trail;
    private com.maddox.il2.engine.Eff3DActor sprite;
    private com.maddox.il2.engine.Eff3DActor turboexhaust;
    private float oldThtl;
    private float oldVwld;
    private float dynamoOrient;
    private boolean bDynamoRotary;
    private int pk;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.ME_163B1A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Me-163");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Me-163B-1a/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Me-163B-1a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitME_163.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.87325F);
        com.maddox.il2.objects.air.ME_163B1A.weaponTriggersRegister(class1, new int[] {
            1, 1
        });
        com.maddox.il2.objects.air.ME_163B1A.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.ME_163B1A.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108kpzl 60", "MGunMK108kpzl 60"
        });
        com.maddox.il2.objects.air.ME_163B1A.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
