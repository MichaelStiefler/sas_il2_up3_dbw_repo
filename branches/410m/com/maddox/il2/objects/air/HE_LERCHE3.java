// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_LERCHE3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintSchemeFMPar06, TypeFighter, TypeX4Carrier, 
//            NetAircraft, PaintScheme

public class HE_LERCHE3 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeX4Carrier
{

    public HE_LERCHE3()
    {
        suka = new Loc();
        pictVBrake = 0.0F;
        pictAileron = 0.0F;
        pictVator = 0.0F;
        pictRudder = 0.0F;
        pictBlister = 0.0F;
        bToFire = false;
        tX4Prev = 0L;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 3: // '\003'
        case 4: // '\004'
            return false;

        case 13: // '\r'
            killPilot(this, 0);
            break;

        case 19: // '\023'
            hitProp(1, j, actor);
            FM.Gears.cgear = false;
            FM.Gears.lgear = false;
            FM.Gears.rgear = false;
            break;

        case 11: // '\013'
        case 12: // '\f'
            FM.Gears.cgear = false;
            break;

        case 17: // '\021'
            FM.Gears.lgear = false;
            break;

        case 18: // '\022'
            FM.Gears.rgear = false;
            break;

        case 7: // '\007'
        case 9: // '\t'
        case 10: // '\n'
            return false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveAileron(float f)
    {
    }

    protected void moveElevator(float f)
    {
    }

    protected void moveFlap(float f)
    {
    }

    protected void moveRudder(float f)
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.HE_LERCHE3.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[2] = com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.63F, 0.0F, 0.63F);
        hierMesh().chunkSetLocate("GearL2_D0", xyz, ypr);
        xyz[2] = com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.63F, 0.0F, 0.63F);
        hierMesh().chunkSetLocate("GearR2_D0", xyz, ypr);
        xyz[2] = com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.63F, 0.0F, 0.63F);
        hierMesh().chunkSetLocate("GearC2_D0", xyz, ypr);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                {
                    getEnergyPastArmor(65.989997863769531D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(16.21F, shot);
                else
                if(s.endsWith("g1"))
                {
                    getEnergyPastArmor(34.209999084472656D / (java.lang.Math.abs(v1.x) + 9.9999997473787516E-005D), shot);
                    FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 2);
                }
                return;
            }
            if(s.startsWith("xxcannon"))
            {
                if(s.endsWith("1"))
                {
                    debuggunnery("Armament System: Left Cannon: Disabled..");
                    FM.AS.setJamBullets(0, 0);
                }
                if(s.endsWith("2"))
                {
                    debuggunnery("Armament System: Right Cannon: Disabled..");
                    FM.AS.setJamBullets(0, 1);
                }
                getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                if(getEnergyPastArmor(1.0F, shot) > 0.0F)
                {
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 1);
                        debuggunnery("Evelator Controls Out..");
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    {
                        FM.AS.setControlsDamage(shot.initiator, 2);
                        debuggunnery("Rudder Controls Out..");
                    }
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                int i = s.charAt(5) - 49;
                com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module (" + i + "): Hit..");
                if(s.endsWith("prop"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, i, 3);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Prop Governor Hit, Disabled..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, i, 4);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("pipe"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.8F)
                    {
                        FM.AS.setInternalDamage(shot.initiator, 5);
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Drive Shaft Damaged..");
                    }
                } else
                if(s.endsWith("gear"))
                {
                    if(getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        {
                            FM.EI.engines[i].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Bullet Jams Reductor Gear..");
                        } else
                        {
                            FM.AS.setEngineSpecificDamage(shot.initiator, i, 3);
                            FM.AS.setEngineSpecificDamage(shot.initiator, i, 4);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        FM.AS.setEngineSpecificDamage(shot.initiator, i, 0);
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Supercharger Disabled..");
                    }
                } else
                if(s.endsWith("feed"))
                {
                    if(getEnergyPastArmor(3.2F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && FM.EI.engines[i].getPowerOutput() > 0.7F)
                    {
                        FM.AS.hitEngine(shot.initiator, i, 100);
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                } else
                if(s.endsWith("case"))
                {
                    if(getEnergyPastArmor(2.1F, shot) > 0.0F)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 175000F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, i);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 50000F)
                        {
                            FM.AS.hitEngine(shot.initiator, i, 2);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[i].getReadyness() + "..");
                        }
                        FM.EI.engines[i].setReadyness(shot.initiator, FM.EI.engines[i].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + FM.EI.engines[i].getReadyness() + "..");
                    }
                    getEnergyPastArmor(22.5F, shot);
                } else
                if(s.startsWith("xxeng1cyl") || s.startsWith("xxeng2cyl"))
                {
                    if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[i].getCylindersRatio() * 1.75F)
                    {
                        FM.EI.engines[i].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Cylinders Hit, " + FM.EI.engines[i].getCylindersOperable() + "/" + FM.EI.engines[i].getCylinders() + " Left..");
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 24000F)
                        {
                            FM.AS.hitEngine(shot.initiator, i, 3);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
                        {
                            FM.AS.setEngineStuck(shot.initiator, i);
                            com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
                        }
                        getEnergyPastArmor(22.5F, shot);
                    }
                } else
                if(s.startsWith("xxeng1mag") || s.startsWith("xxeng2mag"))
                {
                    int l = s.charAt(9) - 49;
                    FM.EI.engines[i].setMagnetoKnockOut(shot.initiator, l);
                    com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module: Magneto " + l + " Destroyed..");
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.2F, shot) > 0.0F)
                    if(shot.power < 14100F)
                    {
                        if(FM.AS.astateTankStates[j] == 0)
                        {
                            FM.AS.hitTank(shot.initiator, j, 1);
                            FM.AS.doSetTankState(shot.initiator, j, 1);
                        }
                        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.02F)
                            FM.AS.hitTank(shot.initiator, j, 1);
                        if(shot.powerType == 3 && FM.AS.astateTankStates[j] > 2 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.4F)
                            FM.AS.hitTank(shot.initiator, j, 10);
                    } else
                    {
                        FM.AS.hitTank(shot.initiator, j, com.maddox.il2.ai.World.Rnd().nextInt(0, (int)(shot.power / 56000F)));
                    }
                return;
            }
            if(s.startsWith("xxrad"))
            {
                if(getEnergyPastArmor(2.2F, shot) > 0.0F)
                {
                    int k = s.charAt(5) - 49;
                    if(k < 3)
                    {
                        FM.AS.hitOil(shot.initiator, 0);
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module A: Oil Radiator Hit..");
                    } else
                    {
                        FM.AS.hitOil(shot.initiator, 1);
                        com.maddox.il2.objects.air.HE_LERCHE3.debugprintln(this, "*** Engine Module B: Oil Radiator Hit..");
                    }
                }
                return;
            }
            if(s.startsWith("xxinst1"))
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x40);
                return;
            }
            if(s.startsWith("xxinst2"))
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x20);
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
        } else
        if(s.startsWith("xnose"))
        {
            if(chunkDamageVisible("Nose") < 2)
                hitChunk("CF", shot);
            FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
            if(point3d.y > 0.0D)
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 4);
            else
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 0x10);
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
        if(s.startsWith("xrudder1"))
        {
            if(chunkDamageVisible("Rudder1") < 1)
                hitChunk("Rudder1", shot);
        } else
        if(s.startsWith("xstabl"))
        {
            if(chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
        } else
        if(s.startsWith("xstabr"))
        {
            if(chunkDamageVisible("StabR") < 2)
                hitChunk("StabR", shot);
        } else
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

    public void update(float f)
    {
        boolean flag = false;
        super.update(f);
        FM.setGCenter(com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.getSpeedKMH(), 0.0F, 240F, -10.5F, 0.0F));
        float f1 = FM.CT.getAirBrake();
        if(java.lang.Math.abs(pictVBrake - f1) > 0.001F)
        {
            pictVBrake = f1;
            resetYPRmodifier();
            xyz[2] = com.maddox.il2.objects.air.HE_LERCHE3.cvt(pictVBrake, 0.0F, 1.0F, 0.0F, 0.525F);
            for(int i = 1; i < 10; i++)
                hierMesh().chunkSetLocate("Flap0" + i + "A_D0", xyz, ypr);

            flag = true;
        }
        f1 = FM.CT.getAileron();
        if(java.lang.Math.abs(pictAileron - f1) > 0.01F)
        {
            pictAileron = f1;
            flag = true;
        }
        f1 = FM.CT.getRudder();
        if(java.lang.Math.abs(pictRudder - f1) > 0.01F)
        {
            pictRudder = f1;
            flag = true;
        }
        f1 = FM.CT.getElevator();
        if(java.lang.Math.abs(pictVator - f1) > 0.01F)
        {
            pictVator = f1;
            flag = true;
        }
        if(flag)
        {
            for(int j = 0; j < 9; j++)
            {
                float f2 = -60F * pictVBrake * (fcA[j] * pictAileron + fcE[j] * pictVator + fcR[j] * pictRudder);
                hierMesh().chunkSetAngles("Flap0" + (j + 1) + "B_D0", f2, 0.0F, 0.0F);
            }

            hierMesh().chunkSetAngles("AroneC_D0", 0.0F, 45F * pictAileron, 0.0F);
            hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 45F * pictAileron, 0.0F);
            hierMesh().chunkSetAngles("AroneR_D0", 0.0F, 45F * pictAileron, 0.0F);
            hierMesh().chunkSetAngles("Rudder1_D0", 34F * pictRudder, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("VatorL_D0", -34F * pictVator, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("VatorR_D0", 34F * pictVator, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("RFlap01_D0", 60F - 60F * pictVBrake, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("RFlap02_D0", -60F + 60F * pictVBrake, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("RFlap03_D0", -60F + 60F * pictVBrake, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("RFlap04_D0", 60F - 60F * pictVBrake, 0.0F, 0.0F);
        }
        if(FM.AS.astateBailoutStep > 1)
        {
            if(pictBlister < 1.0F)
                pictBlister += 3F * f;
            hierMesh().chunkSetAngles("Blister2_D0", -110F * pictBlister, 0.0F, 0.0F);
        }
        float f4 = FM.EI.getPowerOutput() * com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.getSpeedKMH(), 0.0F, 600F, 2.0F, 0.0F);
        if(FM.CT.getAirBrake() > 0.5F)
        {
            if(FM.Or.getTangage() > 5F)
            {
                FM.getW().scale(com.maddox.il2.objects.air.HE_LERCHE3.cvt(FM.Or.getTangage(), 45F, 90F, 1.0F, 0.1F));
                float f3 = FM.Or.getTangage();
                if(java.lang.Math.abs(FM.Or.getKren()) > 90F)
                    f3 = 90F + (90F - f3);
                float f5 = f3 - 90F;
                FM.CT.trimElevator = com.maddox.il2.objects.air.HE_LERCHE3.cvt(f5, -20F, 20F, 0.5F, -0.5F);
                f5 = FM.Or.getKren();
                if(java.lang.Math.abs(f5) > 90F)
                    if(f5 > 0.0F)
                        f5 = 180F - f5;
                    else
                        f5 = -180F - f5;
                FM.CT.trimAileron = com.maddox.il2.objects.air.HE_LERCHE3.cvt(f5, -20F, 20F, 0.5F, -0.5F);
                FM.CT.trimRudder = com.maddox.il2.objects.air.HE_LERCHE3.cvt(f5, -15F, 15F, 0.04F, -0.04F);
            }
        } else
        {
            FM.CT.trimAileron = 0.0F;
            FM.CT.trimElevator = 0.0F;
            FM.CT.trimRudder = 0.0F;
        }
        FM.Or.increment(f4 * (FM.CT.getRudder() + FM.CT.getTrimRudderControl()), f4 * (FM.CT.getElevator() + FM.CT.getTrimElevatorControl()), f4 * (FM.CT.getAileron() + FM.CT.getTrimAileronControl()));
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode() || !flag || !(FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)FM;
        if(pilot.get_maneuver() == 63 && pilot.target != null)
        {
            com.maddox.JGP.Point3d point3d = new Point3d(pilot.target.Loc);
            point3d.sub(FM.Loc);
            FM.Or.transformInv(point3d);
            if((point3d.x > 4000D && point3d.x < 5500D || point3d.x > 100D && point3d.x < 5000D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F) && com.maddox.rts.Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = com.maddox.rts.Time.current();
            }
        }
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public com.maddox.il2.engine.Loc suka;
    private float pictVBrake;
    private float pictAileron;
    private float pictVator;
    private float pictRudder;
    private float pictBlister;
    public boolean bToFire;
    private long tX4Prev;
    private static final float fcA[] = {
        0.0F, 0.04F, 0.1F, 0.04F, 0.02F, -0.02F, -0.04F, -0.1F, -0.04F
    };
    private static final float fcE[] = {
        0.98F, 0.48F, 0.1F, -0.48F, -0.7F, -0.7F, -0.48F, 0.1F, 0.48F
    };
    private static final float fcR[] = {
        0.02F, 0.48F, 0.8F, 0.48F, 0.28F, -0.28F, -0.48F, -0.8F, -0.48F
    };
    private float deltaAzimuth;
    private float deltaTangage;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Lerche");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/He-LercheIIIb(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
        com.maddox.rts.Property.set(class1, "yearService", 1945F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-LercheIIIB2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHE_LIIIB.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.00705F);
        com.maddox.il2.objects.air.HE_LERCHE3.weaponTriggersRegister(class1, new int[] {
            1, 1, 2, 2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.HE_LERCHE3.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalRock03", "_ExternalRock03"
        });
        com.maddox.il2.objects.air.HE_LERCHE3.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108ki 80", "MGunMK108ki 80", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.HE_LERCHE3.weaponsRegister(class1, "3x4", new java.lang.String[] {
            "MGunMK108ki 20", "MGunMK108ki 20", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", "BombGunNull 1"
        });
        com.maddox.il2.objects.air.HE_LERCHE3.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
