// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 21/07/2011 10:34:32 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   P_80.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.BombJATO;
import com.maddox.rts.*;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeBNZFighter, TypeFighterAceMaker, 
//            PaintScheme, Aircraft, AircraftLH, NetAircraft, 
//            EjectionSeat

public class P_80 extends Scheme1
    implements TypeFighter, TypeBNZFighter, TypeFighterAceMaker
{

    public P_80()
    {
        booster = new Bomb[2];
        oldthrl = -1F;
        curthrl = -1F;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        engineSurgeDamage = 0.0F;
        bHasBoosters = true;
        boosterFireOutTime = -1L;
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
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
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(NetMsgGuaranted netmsgguaranted)
        throws IOException
    {
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(NetMsgInput netmsginput)
        throws IOException
    {
        k14Mode = netmsginput.readByte();
        k14WingspanType = netmsginput.readByte();
        k14Distance = netmsginput.readFloat();
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if ((FM.Gears.nearGround() || FM.Gears.onGround())
                && FM.CT.getCockpitDoor() == 1.0F) {
          this.hierMesh().chunkVisible("HMask1_D0", false);
        } else {
          this.hierMesh().chunkVisible("HMask1_D0",
                  this.hierMesh().isChunkVisible("Pilot1_D0"));
        }
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
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.73F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(Config.isUSE_RENDER())
        {
            if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }
    
    public static void moveGear(HierMesh hiermesh, float f)
    {
        float f1 = Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        float f = Aircraft.cvt(((FlightModelMain) (super.FM)).Gears.gWheelSinking[2], 0.0F, 0.19075F, 0.0F, 1.0F);
        resetYPRmodifier();
        Aircraft.xyz[0] = -0.19075F * f;
        hierMesh().chunkSetLocate("GearC6_D0", Aircraft.xyz, Aircraft.ypr);
        hierMesh().chunkSetAngles("GearC8_D0", 0.0F, -35F * f, 0.0F);
        hierMesh().chunkSetAngles("GearC9_D0", 0.0F, -70F * f, 0.0F);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
        if(((FlightModelMain) (super.FM)).CT.GearControl > 0.5F)
        	if(FM.CT.DiffBrakesType == 0) 
            hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -60F * f, 0.0F);
    }

    public void moveSteering(float f)
    {
  	  if(FM.CT.DiffBrakesType > 0)
  		  this.hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -f, 0.0F);
    }  
    
    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    protected void moveFan(float f1)
    {
    }

    protected void hitBone(String s, Shot shot, Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                debuggunnery("Armor: Hit..");
                if(s.endsWith("p1"))
                {
                    getEnergyPastArmor(13.350000381469727D / (Math.abs(((Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(8.77F, shot);
                else
                if(s.endsWith("g1"))
                {
                    getEnergyPastArmor((double)World.Rnd().nextFloat(40F, 60F) / (Math.abs(((Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                    ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                }
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
                    if(World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(1.1F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 1);
                    }
                    if(getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        ((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;
                }
                return;
            }
            if(s.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("bloc"))
                    getEnergyPastArmor((double)World.Rnd().nextFloat(0.0F, 60F) / (Math.abs(((Tuple3d) (Aircraft.v1)).x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("cams") && getEnergyPastArmor(0.45F, shot) > 0.0F && World.Rnd().nextFloat() < ((FlightModelMain) (super.FM)).EI.engines[0].getCylindersRatio() * 20F)
                {
                    ((FlightModelMain) (super.FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Engine Cams Hit, " + ((FlightModelMain) (super.FM)).EI.engines[0].getCylindersOperable() + "/" + ((FlightModelMain) (super.FM)).EI.engines[0].getCylinders() + " Left..");
                    if(World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        ((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 2);
                        debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(shot.powerType == 3 && World.Rnd().nextFloat() < 0.75F)
                    {
                        ((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 1);
                        debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(s.endsWith("eqpt") && World.Rnd().nextFloat() < shot.power / 24000F)
                {
                    ((FlightModelMain) (super.FM)).AS.hitEngine(shot.initiator, 0, 3);
                    debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                if(s.endsWith("exht"));
                return;
            }
            if(s.startsWith("xxmgun0"))
            {
                int j = s.charAt(7) - 49;
                if(getEnergyPastArmor(1.5F, shot) > 0.0F)
                {
                    debuggunnery("Armament: Machine Gun (" + j + ") Disabled..");
                    ((FlightModelMain) (super.FM)).AS.setJamBullets(0, j);
                    getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 23.325F), shot);
                }
                return;
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                {
                    if(((FlightModelMain) (super.FM)).AS.astateTankStates[k] == 0)
                    {
                        debuggunnery("Fuel Tank (" + k + "): Pierced..");
                        ((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, 1);
                        ((FlightModelMain) (super.FM)).AS.doSetTankState(shot.initiator, k, 1);
                    }
                    if(shot.powerType == 3 && World.Rnd().nextFloat() < 0.075F)
                    {
                        ((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, k, 2);
                        debuggunnery("Fuel Tank (" + k + "): Hit..");
                    }
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                debuggunnery("Spar Construction: Hit..");
                if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingRMid Spars Damaged..");
                    nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingLOut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
                {
                    debuggunnery("Spar Construction: WingROut Spars Damaged..");
                    nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxhyd"))
            {
                ((FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 3);
                return;
            }
            if(s.startsWith("xxpnm"))
            {
                ((FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 1);
                return;
            } else
            {
                return;
            }
        }
        if(s.startsWith("xcockpit"))
        {
            ((FlightModelMain) (super.FM)).AS.setCockpitState(shot.initiator, ((FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
            getEnergyPastArmor(0.05F, shot);
        }
        if(s.startsWith("xcf"))
            hitChunk("CF", shot);
        else
        if(s.startsWith("xnose"))
            hitChunk("Nose", shot);
        else
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
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstab"))
        {
            if(s.startsWith("xstabl") && chunkDamageVisible("StabL") < 2)
                hitChunk("StabL", shot);
            if(s.startsWith("xstabr") && chunkDamageVisible("StabR") < 1)
                hitChunk("StabR", shot);
        } else
        if(s.startsWith("xvator"))
        {
            if(s.startsWith("xvatorl"))
                hitChunk("VatorL", shot);
            if(s.startsWith("xvatorr"))
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
        if(s.startsWith("xgear"))
        {
            if(s.endsWith("1") && World.Rnd().nextFloat() < 0.05F)
            {
                debuggunnery("Hydro System: Disabled..");
                ((FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 0);
            }
            if(s.endsWith("2") && World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
            {
                debuggunnery("Undercarriage: Stuck..");
                ((FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 3);
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
            hitFlesh(l, shot, byte0);
        }
    }

    public void destroy()
    {
        doCutBoosters();
        super.destroy();
    }

    public void doFireBoosters()
    {
        Eff3DActor.New(this, findHook("_Booster1"), null, 1.0F, "3DO/Effects/Rocket/RocketSmokeWhite.eff", 30F);
        Eff3DActor.New(this, findHook("_Booster2"), null, 1.0F, "3DO/Effects/Rocket/RocketSmokeWhite.eff", 30F);
    }

    public void doCutBoosters()
    {
        for(int i = 0; i < 2; i++)
            if(booster[i] != null)
            {
                booster[i].start();
                booster[i] = null;
            }

    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.DiffBrakesType = 3;
        System.out.println("*** Diff Brakes Set to Type: " + FM.CT.DiffBrakesType);
        if(super.thisWeaponsName.endsWith("RATO"))
        {
            for(int i = 0; i < 2; i++)
                try
                {
                    booster[i] = new BombJATO();
                    ((Actor) (booster[i])).pos.setBase(this, findHook("_BoosterH" + (i + 1)), false);
                    ((Actor) (booster[i])).pos.resetAsBase();
                    booster[i].drawing(true);
                }
                catch(Exception exception)
                {
                    debugprintln("Structure corrupt - can't hang Starthilferakete..");
                }

        }
    }

    protected boolean cutFM(int i, int j, Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            ((FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(actor);
            // fall through

        case 33: // '!'
        case 34: // '"'
        case 35: // '#'
        case 36: // '$'
        case 37: // '%'
        case 38: // '&'
            doCutBoosters();
            ((FlightModelMain) (super.FM)).AS.setGliderBoostOff();
            bHasBoosters = false;
            // fall through

        default:
            return super.cutFM(i, j, actor);
        }
    }

    public void engineSurge(float f)
    {
        if(((FlightModelMain) (super.FM)).AS.isMaster())
            if(curthrl == -1F)
            {
                curthrl = oldthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
            } else
            {
                curthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
                if(curthrl < 1.0F)
                {
                    if((curthrl - oldthrl) / f > 8F && ((FlightModelMain) (super.FM)).EI.engines[0].getRPM() < 3200F && ((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && World.Rnd().nextFloat() < 0.4F)
                    {
                        if(((Interpolate) (super.FM)).actor == World.getPlayerAircraft())
                            HUD.log(AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                        super.playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += 0.01D * (double)(((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
                        ((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(World.Rnd().nextFloat() < 0.1F && (super.FM instanceof RealFlightModel) && ((RealFlightModel)super.FM).isRealMode())
                            ((FlightModelMain) (super.FM)).AS.hitEngine(this, 0, 100);
                        if(World.Rnd().nextFloat() < 0.1F && (super.FM instanceof RealFlightModel) && ((RealFlightModel)super.FM).isRealMode())
                            ((FlightModelMain) (super.FM)).EI.engines[0].setEngineDies(this);
                    }
                    if((curthrl - oldthrl) / f < -8F && (curthrl - oldthrl) / f > -100F && ((FlightModelMain) (super.FM)).EI.engines[0].getRPM() < 3200F && ((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
                    {
                        super.playSound("weapon.MGunMk108s", true);
                        engineSurgeDamage += 0.001D * (double)(((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
                        ((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness() - engineSurgeDamage);
                        if(World.Rnd().nextFloat() < 0.4F && (super.FM instanceof RealFlightModel) && ((RealFlightModel)super.FM).isRealMode())
                        {
                            if(((Interpolate) (super.FM)).actor == World.getPlayerAircraft())
                                HUD.log(AircraftHotKeys.hudLogWeaponId, "Engine Flameout!");
                            ((FlightModelMain) (super.FM)).EI.engines[0].setEngineStops(this);
                        } else
                        if(((Interpolate) (super.FM)).actor == World.getPlayerAircraft())
                            HUD.log(AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
                    }
                }
                oldthrl = curthrl;
            }
    }

    public void update(float paramf)
    {
        if(((FlightModelMain) (super.FM)).AS.isMaster() && Config.isUSE_RENDER())
            if(((FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.5F && ((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6)
            {
                if(((FlightModelMain) (super.FM)).EI.engines[0].getPowerOutput() > 0.75F)
                    ((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 5);
                else
                    ((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 4);
            } else
            {
                ((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
            }
        engineSurge(paramf);
        if((super.FM instanceof Pilot) && bHasBoosters && super.thisWeaponsName.endsWith("RATO"))
        {
            if(super.FM.getAltitude() > 300F && boosterFireOutTime == -1L && ((Tuple3d) (((FlightModelMain) (super.FM)).Loc)).z != 0.0D && World.Rnd().nextFloat() < 0.05F)
            {
                doCutBoosters();
                ((FlightModelMain) (super.FM)).AS.setGliderBoostOff();
                bHasBoosters = false;
            }
            if(bHasBoosters && boosterFireOutTime == -1L && ((FlightModelMain) (super.FM)).Gears.onGround() && ((FlightModelMain) (super.FM)).EI.getPowerOutput() > 0.8F && super.FM.getSpeedKMH() > 20F)
            {
                boosterFireOutTime = Time.current() + 30000L;
                doFireBoosters();
                ((FlightModelMain) (super.FM)).AS.setGliderBoostOn();
            }
            if(bHasBoosters && boosterFireOutTime > 0L)
            {
                if(Time.current() < boosterFireOutTime)
                    ((FlightModelMain) (super.FM)).producedAF.x += 20000D;
                if(Time.current() > boosterFireOutTime + 10000L)
                {
                    doCutBoosters();
                    ((FlightModelMain) (super.FM)).AS.setGliderBoostOff();
                    bHasBoosters = false;
                }
            }
        }
        super.update(paramf);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -78.5F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -78.5F * f, 0.0F);
    }

    private float oldthrl;
    private float curthrl;
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    private float engineSurgeDamage;
    private Bomb booster[];
    protected boolean bHasBoosters;
    protected long boosterFireOutTime;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.P_80.class;
        Property.set(class1, "originCountry", PaintScheme.countryUSA);
    }
}