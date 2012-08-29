// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BEAU.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
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
//            Scheme2, Aircraft, Cockpit, PaintScheme

public abstract class BEAU extends com.maddox.il2.objects.air.Scheme2
{

    public BEAU()
    {
    }

    public void moveCockpitDoor(float f)
    {
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -120F), 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
        case 34: // '"'
            hitProp(0, j, actor);
            cut("Engine1");
            break;

        case 36: // '$'
        case 37: // '%'
            hitProp(1, j, actor);
            cut("Engine2");
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -50F)
            {
                f = -50F;
                flag = false;
            }
            if(f > 50F)
            {
                f = 50F;
                flag = false;
            }
            if(f1 > com.maddox.il2.objects.air.Aircraft.cvt(java.lang.Math.abs(f), 0.0F, 50F, 40F, 25F))
                f1 = com.maddox.il2.objects.air.Aircraft.cvt(java.lang.Math.abs(f), 0.0F, 50F, 40F, 25F);
            if(f1 < com.maddox.il2.objects.air.Aircraft.cvt(java.lang.Math.abs(f), 0.0F, 50F, -10F, -3.5F))
                f1 = com.maddox.il2.objects.air.Aircraft.cvt(java.lang.Math.abs(f), 0.0F, 50F, -10F, -3.5F);
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -60F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.89F, 0.0F, -103F), 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.29F, 0.0F, -63F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.29F, 0.0F, -58F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.11F, 0.99F, 0.0F, -103F), 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.11F, 0.39F, 0.0F, -63F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.11F, 0.39F, 0.0F, -58F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.BEAU.moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.2F, 0.0F, 0.165F);
        hierMesh().chunkSetLocate("GearL25_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.2F, 0.0F, 0.165F);
        hierMesh().chunkSetLocate("GearR25_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("e1"))
                    getEnergyPastArmor(12.100000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("e2"))
                    getEnergyPastArmor(12.100000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("p1"))
                    getEnergyPastArmor(12.7F, shot);
                if(s.endsWith("p2"))
                    getEnergyPastArmor(12.7F, shot);
            } else
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                case 1: // '\001'
                    if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                            debuggunnery("*** Engine1 Throttle Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                            debuggunnery("*** Engine1 Prop Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 0, 7);
                            debuggunnery("*** Engine1 Mix Controls Out..");
                        }
                    }
                    break;

                case 2: // '\002'
                    if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 1, 1);
                            debuggunnery("*** Engine2 Throttle Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 1, 6);
                            debuggunnery("*** Engine2 Prop Controls Out..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.15F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, 1, 7);
                            debuggunnery("*** Engine2 Mix Controls Out..");
                        }
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(6F, shot) > 0.0F)
                    {
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
                    }
                    break;

                case 5: // '\005'
                case 6: // '\006'
                    if(getEnergyPastArmor(1.5F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 0);
                        debuggunnery("Ailerons Controls Out..");
                    }
                    break;
                }
            } else
            if(s.startsWith("xxengine"))
            {
                int j = 0;
                if(s.startsWith("xxengine2"))
                    j = 1;
                debuggunnery("Engine Module[" + j + "]: Hit..");
                if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.2F, 0.55F), shot) > 0.0F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 280000F)
                    {
                        debuggunnery("Engine Module: Engine Crank Case Hit - Engine Stucks..");
                        FM.AS.setEngineStuck(shot.initiator, j);
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 100000F)
                    {
                        debuggunnery("Engine Module: Engine Crank Case Hit - Engine Damaged..");
                        FM.AS.hitEngine(shot.initiator, j, 2);
                    }
                }
                if(getEnergyPastArmor(0.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[j].getCylindersRatio() * 0.66F)
                {
                    FM.EI.engines[j].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 32200F)));
                    debuggunnery("Engine Module: Cylinders Hit, " + FM.EI.engines[j].getCylindersOperable() + "/" + FM.EI.engines[j].getCylinders() + " Left..");
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 1000000F)
                    {
                        FM.AS.hitEngine(shot.initiator, j, 2);
                        debuggunnery("Engine Module: Cylinders Hit - Engine Fires..");
                    }
                }
                getEnergyPastArmor(25F, shot);
            } else
            if(s.startsWith("xxmgun"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
                {
                    FM.AS.setJamBullets(0, k);
                    getEnergyPastArmor(11.98F, shot);
                }
            } else
            if(s.startsWith("xxoil"))
            {
                int l = 0;
                if(s.endsWith("2"))
                    l = 1;
                if(getEnergyPastArmor(0.21F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2435F)
                    FM.AS.hitOil(shot.initiator, l);
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine (" + l + ") Module: Oil Tank Pierced..");
            } else
            {
                if(s.startsWith("xxprop1") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine1 Module: Prop Governor Hit, Disabled..");
                    } else
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine1 Module: Prop Governor Hit, Damaged..");
                    }
                if(s.startsWith("xxprop2") && getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 3);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine2 Module: Prop Governor Hit, Disabled..");
                    } else
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, 1, 4);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine2 Module: Prop Governor Hit, Damaged..");
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
                    if(s.startsWith("xxspark") && chunkDamageVisible("Keel1") > 1 && getEnergyPastArmor(9.6F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                    {
                        debuggunnery("*** Keel1 Spars Damaged..");
                        nextDMGLevels(1, 2, "Keel1_D2", shot.initiator);
                    }
                } else
                if(s.startsWith("xxstruts"))
                {
                    if(s.startsWith("xxstruts1") && chunkDamageVisible("Engine1") > 1 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 8F), shot) > 0.0F)
                    {
                        debuggunnery("*** Engine1 Spars Damaged..");
                        nextDMGLevels(1, 2, "Engine1_D2", shot.initiator);
                    }
                    if(s.startsWith("xxstruts2") && chunkDamageVisible("Engine2") > 1 && getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 8F), shot) > 0.0F)
                    {
                        debuggunnery("*** Engine2 Spars Damaged..");
                        nextDMGLevels(1, 2, "Engine2_D2", shot.initiator);
                    }
                } else
                if(s.startsWith("xxtank"))
                {
                    int i1 = s.charAt(6) - 49;
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 10.25F)
                    {
                        if(FM.AS.astateTankStates[i1] == 0)
                        {
                            debuggunnery("Fuel Tank (" + i1 + "): Pierced..");
                            FM.AS.hitTank(shot.initiator, i1, 1);
                            FM.AS.doSetTankState(shot.initiator, i1, 1);
                        }
                        if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                        {
                            FM.AS.hitTank(shot.initiator, i1, 2);
                            debuggunnery("Fuel Tank (" + i1 + "): Hit..");
                        }
                    }
                }
            }
        } else
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
            if(point3d.x > 0.5D)
            {
                if(point3d.z > 0.91300000000000003D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                if(point3d.z > 0.34100000000000003D)
                {
                    if(point3d.x < 1.4019999999999999D)
                    {
                        if(point3d.y > 0.0D)
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
                        else
                            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
                    } else
                    {
                        FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                    }
                } else
                if(point3d.y > 0.0D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 8);
                else
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
                if(point3d.x > 1.6910000000000001D && point3d.x < 1.98D)
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
            }
        } else
        if(s.startsWith("xtail"))
            hitChunk("Tail1", shot);
        else
        if(s.startsWith("xkeel1"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder1"))
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
        else
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
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
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
            int j1;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                j1 = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                j1 = s.charAt(6) - 49;
            } else
            {
                j1 = s.charAt(5) - 49;
            }
            hitFlesh(j1, shot, ((int) (byte0)));
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 2; i++)
        {
            float f1 = FM.EI.engines[i].getControlRadiator();
            if(java.lang.Math.abs(flapps[i] - f1) <= 0.01F)
                continue;
            flapps[i] = f1;
            for(int j = 1; j < 23; j++)
                hierMesh().chunkSetAngles("Water" + (j + 22 * i) + "_D0", 0.0F, -20F * f1, 0.0F);

        }

    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
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

    private float flapps[] = {
        0.0F, 0.0F
    };

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.BEAU.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryBritain);
    }
}
