// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 20/03/2011 10:17:55 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SOPWITH.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeScout, TypeFighter, TypeTNBFighter, 
//            Aircraft, PaintScheme, CockpitCAMEL

public abstract class SOPWITH extends Scheme1
    implements TypeScout, TypeFighter, TypeTNBFighter
{

    public SOPWITH()
    {
        bChangedPit = true;
    }

    protected void moveFlap(float f)
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 30F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("GearC2_D0", 30F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("GearCWireR", -30F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("GearCWireL", -30F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RudderWireR", -30F * f, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RudderWireL", -30F * f, 0.0F, 0.0F);
    }

    public void registerPit(CockpitCAMEL cockpitcamel)
    {
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    protected boolean cutFM(int i, int j, Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 19: // '\023'
            ((FlightModelMain) (super.FM)).Gears.hitCentreGear();
            break;

        case 9: // '\t'
            if(hierMesh().chunkFindCheck("GearL2_D0") != -1)
            {
                hierMesh().hideSubTrees("GearL2_D0");
                Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("GearL2_D0"));
                wreckage.collide(true);
                ((FlightModelMain) (super.FM)).Gears.hitLeftGear();
            }
            break;

        case 10: // '\n'
            if(hierMesh().chunkFindCheck("GearR2_D0") != -1)
            {
                hierMesh().hideSubTrees("GearR2_D0");
                Wreckage wreckage1 = new Wreckage(this, hierMesh().chunkFind("GearR2_D0"));
                wreckage1.collide(true);
                ((FlightModelMain) (super.FM)).Gears.hitRightGear();
            }
            break;

        case 3: // '\003'
            if(World.Rnd().nextInt(0, 99) < 1)
            {
                ((FlightModelMain) (super.FM)).AS.hitEngine(this, 0, 4);
                hitProp(0, j, actor);
                ((FlightModelMain) (super.FM)).EI.engines[0].setEngineStuck(actor);
                return cut("engine1");
            } else
            {
                ((FlightModelMain) (super.FM)).AS.setEngineDies(this, 0);
                return false;
            }
        }
        return super.cutFM(i, j, actor);
    }

    public boolean cut(String s)
    {
        boolean flag = super.cut(s);
        return flag;
    }

    protected void moveElevator(float f)
    {
        hierMesh().chunkSetAngles("Elevator", 0.0F, -35F * f, 0.0F);
        hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35F * f, 0.0F);
        hierMesh().chunkSetAngles("ElevatorWireRU", 0.0F, 35F * f, 0.0F);
        hierMesh().chunkSetAngles("ElevatorWireRL", 0.0F, 35F * f, 0.0F);
        hierMesh().chunkSetAngles("ElevatorWireLU", 0.0F, 35F * f, 0.0F);
        hierMesh().chunkSetAngles("ElevatorWireLL", 0.0F, 35F * f, 0.0F);
    }

    protected void moveAileron(float f)
    {
        hierMesh().chunkSetAngles("AroneL_D0", 0.0F, 20F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneL2_D0", 0.0F, 20F * f, 0.0F);
        hierMesh().chunkSetAngles("AroneR2_D0", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronConectionL", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronConectionR", 0.0F, 20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronLWire", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronLWire1", 0.0F, -20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronRWire", 0.0F, 20F * f, 0.0F);
        hierMesh().chunkSetAngles("AileronRWire1", 0.0F, 20F * f, 0.0F);
    }

    public void update(float f)
    {
        if(((FlightModelMain) (super.FM)).Gears.onGround())
            ((FlightModelMain) (super.FM)).AS.bIsEnableToBailout = true;
        else
            ((FlightModelMain) (super.FM)).AS.bIsEnableToBailout = false;
        super.update(f);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && ((FlightModelMain) (super.FM)).AS.astateEngineStates[0] > 3 && World.Rnd().nextFloat() < 0.4F)
            ((FlightModelMain) (super.FM)).EI.engines[0].setExtinguisherFire();
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D1", true);
            break;
        }
    }

    public void doRemoveBodyFromPlane(int i)
    {
        super.doRemoveBodyFromPlane(i);
    }

    protected void hitBone(String s, Shot shot, Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxcontrols"))
            {
                if(s.endsWith("8"))
                {
                    if(World.Rnd().nextFloat() < 0.2F)
                    {
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                        Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#8)");
                    }
                } else
                if(s.endsWith("9"))
                {
                    if(World.Rnd().nextFloat() < 0.2F)
                    {
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                        Aircraft.debugprintln(this, "*** Evelator Controls Out.. (#9)");
                    }
                    if(World.Rnd().nextFloat() < 0.2F)
                    {
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                        Aircraft.debugprintln(this, "*** Arone Controls Out.. (#9)");
                    }
                } else
                if((s.endsWith("2") || s.endsWith("4")) && World.Rnd().nextFloat() < 0.5F)
                {
                    ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                    Aircraft.debugprintln(this, "*** Arone Controls Out.. (#2/#4)");
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                Aircraft.debugprintln(this, "*** Engine Module: Hit..");
                if(s.endsWith("prop"))
                    Aircraft.debugprintln(this, "*** Prop hit");
                else
                if(s.endsWith("case"))
                {
                    if(World.Rnd().nextFloat() < shot.power / 175000F)
                    {
                        ((FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                        Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                    }
                    if(World.Rnd().nextFloat() < shot.power / 50000F)
                    {
                        ((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 2);
                        Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((FlightModelMain) (super.FM)).EI.engines[0].getReadyness() + "..");
                    }
                    ((FlightModelMain) (super.FM)).EI.engines[0].setReadyness(shot.initiator, ((FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                    Aircraft.debugprintln(this, "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((FlightModelMain) (super.FM)).EI.engines[0].getReadyness() + "..");
                } else
                if(s.endsWith("cyl1") || s.endsWith("cyl2"))
                {
                    if(World.Rnd().nextFloat() < ((FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 1.12F)
                    {
                        ((FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, " + ((FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                        if(World.Rnd().nextFloat() < shot.power / 48000F)
                        {
                            ((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                            Aircraft.debugprintln(this, "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(World.Rnd().nextFloat() < 0.005F)
                        {
                            ((FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                            Aircraft.debugprintln(this, "*** Engine Module: Bullet Jams Piston Head..");
                        }
                    }
                } else
                if(s.endsWith("oil1"))
                {
                    ((FlightModelMain) (super.FM)).AS.hitOil(shot.initiator, 0);
                    Aircraft.debugprintln(this, "*** Engine Module: Oil Radiator Hit..");
                }
            } else
            if(s.endsWith("gear"))
            {
                Aircraft.debugprintln(this, "*** Engine Module: Gear Hit..");
                if(getEnergyPastArmor(2.1F, shot) > 0.0F && World.Rnd().nextFloat() < 0.05F)
                {
                    ((FlightModelMain) (super.FM)).AS.setEngineStuck(shot.initiator, 0);
                    Aircraft.debugprintln(this, "*** Engine Module: gear hit, engine stuck..");
                }
            } else
            if(s.startsWith("xxtank"))
            {
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && World.Rnd().nextFloat() < 0.99F)
                {
                    if(((FlightModelMain) (super.FM)).AS.astateTankStates[0] == 0)
                    {
                        Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
                        ((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 2);
                    }
                    if(shot.powerType == 3 && World.Rnd().nextFloat() < 1.25F)
                    {
                        ((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0, 2);
                        Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
                    }
                }
            } else
            if(s.startsWith("xxlock"))
            {
                Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
                if(s.startsWith("xxlockr") && getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 3F), shot) > 0.0F)
                {
                    Aircraft.debugprintln(this, "*** Rudder Lock Shot Off..");
                    nextDMGLevels(3, 2, "Rudder1_D" + chunkDamageVisible("Rudder1"), shot.initiator);
                } else
                if(s.startsWith("xxlockvl") && getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    Aircraft.debugprintln(this, "*** VatorL Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorL_D" + chunkDamageVisible("VatorL"), shot.initiator);
                } else
                if(s.startsWith("xxlockvr") && getEnergyPastArmor(1.5F * World.Rnd().nextFloat(1.0F, 1.2F), shot) > 0.0F)
                {
                    Aircraft.debugprintln(this, "*** VatorR Lock Shot Off..");
                    nextDMGLevels(3, 2, "VatorR_D" + chunkDamageVisible("VatorR"), shot.initiator);
                }
            } else
            if(s.startsWith("xxmgun"))
            {
                if(s.endsWith("01"))
                {
                    Aircraft.debugprintln(this, "*** Fixed Gun #1: Disabled..");
                    ((FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
                }
                if(s.endsWith("02"))
                {
                    Aircraft.debugprintln(this, "*** Fixed Gun #2: Disabled..");
                    ((FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
                }
                if(s.endsWith("03"))
                {
                    Aircraft.debugprintln(this, "*** Fixed Gun #3: Disabled..");
                    ((FlightModelMain) (super.FM)).AS.setJamBullets(1, 0);
                }
                if(s.endsWith("04"))
                {
                    Aircraft.debugprintln(this, "*** Fixed Gun #4: Disabled..");
                    ((FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
                }
                getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), shot);
            } else
            if(s.startsWith("xxspar"))
            {
                Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
                if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2)
                {
                    Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
                    nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
                } else
                if(s.startsWith("xxsparli") && World.Rnd().nextFloat() < 0.8F)
                {
                    Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
                } else
                if(s.startsWith("xxsparri") && World.Rnd().nextFloat() < 0.8F)
                {
                    Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
                } else
                if(s.startsWith("xxsparlm") && World.Rnd().nextFloat() < 0.8F)
                {
                    Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), shot.initiator);
                } else
                if(s.startsWith("xxsparrm") && World.Rnd().nextFloat() < 0.8F)
                {
                    Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D" + chunkDamageVisible("WingRMid"), shot.initiator);
                }
            }
            return;
        }
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            Aircraft.debugprintln(this, "*** hitFlesh..");
            hitFlesh(i, shot, byte0);
        } else
        if(s.startsWith("xcockpit"))
        {
            if(chunkDamageVisible("FuselageSidesL") < 3)
                hitChunk("FuselageSidesL", shot);
            else
            if(chunkDamageVisible("FuselageSidesR") < 3)
                hitChunk("FuselageSidesR", shot);
            else
            if(World.Rnd().nextFloat() < 0.2F)
                ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
            else
            if(World.Rnd().nextFloat() < 0.4F)
                ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
            else
            if(World.Rnd().nextFloat() < 0.6F)
                ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
            else
            if(World.Rnd().nextFloat() < 0.8F)
                ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
            else
                ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
        } else
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xxeng"))
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
            if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 2)
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
            Aircraft.debugprintln(this, "*** xWing: " + s);
            if(s.startsWith("xwinglin") && chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(s.startsWith("xwingrin") && chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(s.startsWith("xwinglmid") && chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
            if(s.startsWith("xwingrmid") && chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xarone"))
        {
            if(s.startsWith("xaronel") && chunkDamageVisible("AroneL") < 1)
                hitChunk("AroneL", shot);
            if(s.startsWith("xaroner") && chunkDamageVisible("AroneR") < 1)
                hitChunk("AroneR", shot);
        }
    }

    protected float kangle;
    public boolean bChangedPit;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.SOPWITH.class;
        Property.set(class1, "originCountry", PaintScheme.countryBritain);
    }
}