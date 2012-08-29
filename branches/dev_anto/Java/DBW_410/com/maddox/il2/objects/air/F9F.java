// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 4/07/2010 5:53:04 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   F9F.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.*;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.*;

import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeBNZFighter, TypeFighterAceMaker, 
//            Aircraft, PaintScheme

public class F9F extends Scheme1
    implements TypeFighter, TypeBNZFighter, TypeFighterAceMaker, TypeZBReceiver, TypeFuelDump
{
	private float oldthrl;
	private float curthrl;
	private float engineSurgeDamage;
	private boolean overrideBailout;
	private boolean ejectComplete;
    public static float FlowRate = 8.5F; //Flow rate in litres per second
    public static float FuelReserve = 1628F;//Minimum amount of total fuel that can't be drained - "Reserve Tank"

    public F9F()
    {
    	oldthrl = -1F;
    	curthrl = -1F;
        arrestor2 = 0.0F;
        AirBrakeControl = 0.0F;
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        overrideBailout = false;
        ejectComplete = false;
    }

    public float getFlowRate(){
    	return FlowRate;
    }
    public float getFuelReserve(){
    	return FuelReserve;
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
        if ((!FM.isPlayers() || !(FM instanceof RealFlightModel) || !((RealFlightModel) FM).isRealMode()) && (FM instanceof Maneuver)){
        if(FM.AP.way.isLanding() && (double)FM.getSpeed() > (double)FM.VmaxFLAPS * 2D)
            FM.CT.AirBrakeControl = 1.0F;
        else
        if(FM.AP.way.isLanding() && (double)FM.getSpeed() < (double)FM.VmaxFLAPS * 1.5D)
            FM.CT.AirBrakeControl = 0.0F;
        }
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

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, 12.2F * f, 0.0F);
        hierMesh().chunkSetAngles("Hook2_D0", 0.0F, 0.0F, 0.0F);
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

    protected void moveWingFold(HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 70F), 0.0F);
        hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -70F), 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            FM.CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    public static void moveGear(HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -40F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, 80F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, Aircraft.cvt(f, 0.01F, 0.04F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -70F * f, 0.0F);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f, 0.02F, 0.5F, 0.0F, -90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f, 0.72F, 0.98F, -90F, 0.0F), 0.0F);
        if(f < 0.5F)
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f, 0.02F, 0.4F, 0.0F, 90F), 0.0F);
        else
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f, 0.72F, 0.98F, 90F, 0.0F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, 70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -70F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        moveGear(hierMesh(), f);
    }

    public void moveWheelSink()
    {
        float f = Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.19075F, 0.0F, 1.0F);
        resetYPRmodifier();
        Aircraft.xyz[0] = -0.19075F * f;
        hierMesh().chunkSetLocate("GearC6_D0", Aircraft.xyz, Aircraft.ypr);
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, 30F * f, 0.0F);
        if(FM.CT.GearControl > 0.5F)
            hierMesh().chunkSetAngles("GearC7_D0", 0.0F, -60F * f, 0.0F);
    }

    protected void moveFlap(float f)
    {
        float f1 = 55F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, 0.0F, f1);
        hierMesh().chunkSetAngles("Flap04_D0", 0.0F, 0.0F, f1);
    }

    protected void moveFan(float f)
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
                    getEnergyPastArmor(13.350000381469727D / (Math.abs(Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                    if(shot.power <= 0.0F)
                        doRicochetBack(shot);
                } else
                if(s.endsWith("p2"))
                    getEnergyPastArmor(8.77F, shot);
                else
                if(s.endsWith("g1"))
                {
                    getEnergyPastArmor((double)World.Rnd().nextFloat(40F, 60F) / (Math.abs(Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
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
                    if(World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(1.1F, shot) > 0.0F)
                    {
                        debuggunnery("Controls: Ailerones Controls: Out..");
                        FM.AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 3: // '\003'
                case 4: // '\004'
                    if(getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 1);
                    }
                    if(getEnergyPastArmor(World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                    {
                        debuggunnery("Controls: Rudder Controls: Disabled / Strings Broken..");
                        FM.AS.setControlsDamage(shot.initiator, 2);
                    }
                    break;
                }
            } else
            if(s.startsWith("xxeng1"))
            {
                debuggunnery("Engine Module: Hit..");
                if(s.endsWith("bloc"))
                    getEnergyPastArmor((double)World.Rnd().nextFloat(0.0F, 60F) / (Math.abs(Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
                if(s.endsWith("cams") && getEnergyPastArmor(0.45F, shot) > 0.0F && World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 20F)
                {
                    FM.EI.engines[0].setCyliderKnockOut(shot.initiator, World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                    debuggunnery("Engine Module: Engine Cams Hit, " + FM.EI.engines[0].getCylindersOperable() + "/" + FM.EI.engines[0].getCylinders() + " Left..");
                    if(World.Rnd().nextFloat() < shot.power / 24000F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 2);
                        debuggunnery("Engine Module: Engine Cams Hit - Engine Fires..");
                    }
                    if(shot.powerType == 3 && World.Rnd().nextFloat() < 0.75F)
                    {
                        FM.AS.hitEngine(shot.initiator, 0, 1);
                        debuggunnery("Engine Module: Engine Cams Hit (2) - Engine Fires..");
                    }
                }
                if(s.endsWith("eqpt") && World.Rnd().nextFloat() < shot.power / 24000F)
                {
                    FM.AS.hitEngine(shot.initiator, 0, 3);
                    debuggunnery("Engine Module: Hit - Engine Fires..");
                }
                if(s.endsWith("exht"));
            } else
            if(s.startsWith("xxtank"))
            {
                int j = s.charAt(6) - 49;
                if(getEnergyPastArmor(0.1F, shot) > 0.0F && World.Rnd().nextFloat() < 0.25F)
                {
                    if(FM.AS.astateTankStates[j] == 0)
                    {
                        debuggunnery("Fuel Tank (" + j + "): Pierced..");
                        FM.AS.hitTank(shot.initiator, j, 1);
                        FM.AS.doSetTankState(shot.initiator, j, 1);
                    }
                    if(shot.powerType == 3 && World.Rnd().nextFloat() < 0.075F)
                    {
                        FM.AS.hitTank(shot.initiator, j, 2);
                        debuggunnery("Fuel Tank (" + j + "): Hit..");
                    }
                }
            } else
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
            } else
            if(s.startsWith("xxhyd"))
                FM.AS.setInternalDamage(shot.initiator, 3);
            else
            if(s.startsWith("xxpnm"))
                FM.AS.setInternalDamage(shot.initiator, 1);
        } else
        {
            if(s.startsWith("xcockpit"))
            {
                FM.AS.setCockpitState(shot.initiator, FM.AS.astateCockpitState | 1);
                getEnergyPastArmor(0.05F, shot);
            }
            if(s.startsWith("xxhispa1") && getEnergyPastArmor(4.85F, shot) > 0.0F && World.Rnd().nextFloat() < 0.75F)
                FM.AS.setJamBullets(1, 0);
            if(s.startsWith("xxhispa2") && getEnergyPastArmor(4.85F, shot) > 0.0F && World.Rnd().nextFloat() < 0.75F)
                FM.AS.setJamBullets(1, 1);
            if(s.startsWith("xxhispa3") && getEnergyPastArmor(4.85F, shot) > 0.0F && World.Rnd().nextFloat() < 0.75F)
                FM.AS.setJamBullets(1, 2);
            if(s.startsWith("xxhispa4") && getEnergyPastArmor(4.85F, shot) > 0.0F && World.Rnd().nextFloat() < 0.75F)
                FM.AS.setJamBullets(1, 3);
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
                    FM.AS.setInternalDamage(shot.initiator, 0);
                }
                if(s.endsWith("2") && World.Rnd().nextFloat() < 0.1F && getEnergyPastArmor(World.Rnd().nextFloat(1.2F, 3.435F), shot) > 0.0F)
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
    }

    protected boolean cutFM(int i, int j, Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            FM.EI.engines[0].setEngineDies(actor);
            return super.cutFM(i, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

	public void engineSurge(float f) {
		if (((FlightModelMain) (super.FM)).AS.isMaster())

			if (curthrl == -1F) {
				curthrl = oldthrl = ((FlightModelMain) (super.FM)).EI.engines[0]
						.getControlThrottle();
			} else {
				curthrl = ((FlightModelMain) (super.FM)).EI.engines[0]
						.getControlThrottle();
				if (curthrl < 1.05F) {
					if ((curthrl - oldthrl) / f > 20.0F
							&& FM.EI.engines[0].getRPM() < 3200.0F
							&& FM.EI.engines[0].getStage() == 6
							&& World.Rnd().nextFloat() < 0.40F) {
						if (FM.actor == World.getPlayerAircraft())
							HUD.log(AircraftHotKeys.hudLogWeaponId,"Compressor Stall!");
						super.playSound("weapon.MGunMk108s", true);
						engineSurgeDamage += 1.0000000000000001E-002D * (double) (((FlightModelMain) (super.FM)).EI.engines[0]
								.getRPM() / 1000F);
						((FlightModelMain) (super.FM)).EI.engines[0]
								.doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0]
										.getReadyness()
										- engineSurgeDamage);
						if (World.Rnd().nextFloat() < 0.05F
								&& FM instanceof RealFlightModel
								&& ((RealFlightModel) FM).isRealMode()) {
							FM.AS.hitEngine(this, 0, 100);
						}
						if (World.Rnd().nextFloat() < 0.05F
								&& FM instanceof RealFlightModel
								&& ((RealFlightModel) FM).isRealMode()) {
							FM.EI.engines[0].setEngineDies(this);
						}
					}
					if ((curthrl - oldthrl) / f < -20.0F
							&& (curthrl - oldthrl) / f > -100.0F
							&& FM.EI.engines[0].getRPM() < 3200.0F
							&& FM.EI.engines[0].getStage() == 6) {
						super.playSound("weapon.MGunMk108s", true);
						engineSurgeDamage += 1.0000000000000001E-003D * (double) (((FlightModelMain) (super.FM)).EI.engines[0]
								.getRPM() / 1000F);
						((FlightModelMain) (super.FM)).EI.engines[0]
								.doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0]
										.getReadyness()
										- engineSurgeDamage);
						if (World.Rnd().nextFloat() < 0.40F
								&& FM instanceof RealFlightModel
								&& ((RealFlightModel) FM).isRealMode()) {
							if (FM.actor == World.getPlayerAircraft())
								HUD.log(AircraftHotKeys.hudLogWeaponId, "Engine Flameout!");
							FM.EI.engines[0].setEngineStops(this);
						} else {
							if (FM.actor == World.getPlayerAircraft())
								HUD.log(AircraftHotKeys.hudLogWeaponId, "Compressor Stall!");
						}
					}
				}
				oldthrl = curthrl;
			}
	}
	
    public void update(float f)
    {
		if ((FM.AS.bIsAboutToBailout || overrideBailout) && !ejectComplete
				&& FM.getSpeedKMH() > 15.0F) {
			overrideBailout = true;
			FM.AS.bIsAboutToBailout = false;
			bailout();
		}
        if(FM.AS.isMaster() && Config.isUSE_RENDER())
            if (FM.EI.engines[0].getPowerOutput() > 0.5F
                    && FM.EI.engines[0].getStage() == 6) {
              if (FM.EI.engines[0].getPowerOutput() > 0.75F) {
                FM.AS.setSootState(this, 0, 3);
              } else {
                FM.AS.setSootState(this, 0, 2);
              }
            } else {
              FM.AS.setSootState(this, 0, 0);
            }
        if(FM.CT.getArrestor() > 0.9F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                arrestor2 = Aircraft.cvt(FM.Gears.arrestorVAngle, -65F, 3F, 45F, -23F);
                hierMesh().chunkSetAngles("Hook2_D0", 0.0F, arrestor2, 0.0F);
                if(FM.Gears.arrestorVAngle >= -35F);
            } else
            {
                float f1 = -41F * FM.Gears.arrestorVSink;
                if(f1 < 0.0F && FM.getSpeedKMH() > 60F)
                    Eff3DActor.New(this, FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
                if(f1 > 0.0F && FM.CT.getArrestor() < 0.9F)
                    f1 = 0.0F;
                if(f1 > 6.2F)
                    f1 = 6.2F;
                arrestor2 += f1;
                if(arrestor2 < -23F)
                    arrestor2 = -23F;
                else
                if(arrestor2 > 45F)
                    arrestor2 = 45F;
                hierMesh().chunkSetAngles("Hook2_D0", 0.0F, arrestor2, 0.0F);
            }
        engineSurge(f);
        super.update(f);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, -60F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -60F * f, 0.0F);
    }
    
    public void moveCockpitDoor(float paramFloat)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.95F, 0.0F, 0.9F);
        Aircraft.xyz[2] = Aircraft.cvt(paramFloat, 0.01F, 0.95F, 0.0F, 0.07F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f = (float)Math.sin(Aircraft.cvt(paramFloat, 0.4F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f);
        hierMesh().chunkSetAngles("Head1_D0", 14F * f, 0.0F, 0.0F);
        if(Config.isUSE_RENDER())
        {
            if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
            setDoorSnd(paramFloat);
        }
    }
	
	  private void bailout() {
		    if (overrideBailout) {
		      if (FM.AS.astateBailoutStep >= 0 && FM.AS.astateBailoutStep < 2) {
		        if (FM.CT.cockpitDoorControl > 0.5F
		                && FM.CT.getCockpitDoor() > 0.5F) {
		          FM.AS.astateBailoutStep = (byte) 11;
		          doRemoveBlisters();
		        } else {
		          FM.AS.astateBailoutStep = (byte) 2;
		        }
		      } else if (FM.AS.astateBailoutStep >= 2
		              && FM.AS.astateBailoutStep <= 3) {
		        switch (FM.AS.astateBailoutStep) {
		          case 2:
		            if (FM.CT.cockpitDoorControl < 0.5F) {
		              doRemoveBlister1();
		            }
		            break;
		          case 3:
		            doRemoveBlisters();
		            break;
		        }
		        if (FM.AS.isMaster()) {
		          FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
		        }
		        AircraftState tmp178_177 = FM.AS;
		        tmp178_177.astateBailoutStep = (byte) (tmp178_177.astateBailoutStep + 1);
		        if (FM.AS.astateBailoutStep == 4) {
		          FM.AS.astateBailoutStep = (byte) 11;
		        }
		      } else if (FM.AS.astateBailoutStep >= 11
		              && FM.AS.astateBailoutStep <= 19) {
		        int i = FM.AS.astateBailoutStep;
		        if (FM.AS.isMaster()) {
		          FM.AS.netToMirrors(20, FM.AS.astateBailoutStep, 1, null);
		        }
		        AircraftState tmp383_382 = FM.AS;
		        tmp383_382.astateBailoutStep = (byte) (tmp383_382.astateBailoutStep + 1);
		        if (i == 11) {
		          FM.setTakenMortalDamage(true, null);
		          if (FM instanceof Maneuver
		                  && ((Maneuver) FM).get_maneuver() != 44) {
		            World.cur();
		            if (FM.AS.actor != World.getPlayerAircraft()) {
		              ((Maneuver) FM).set_maneuver(44);
		            }
		          }
		        }
		        if (FM.AS.astatePilotStates[i - 11] < 99) {
		          this.doRemoveBodyFromPlane(i - 10);
		          if (i == 11) {
		            doEjectCatapult();
		            FM.setTakenMortalDamage(true, null);
		            FM.CT.WeaponControl[0] = false;
		            FM.CT.WeaponControl[1] = false;
		            FM.AS.astateBailoutStep = (byte) -1;
		            overrideBailout = false;
		            FM.AS.bIsAboutToBailout = true;
		            ejectComplete = true;
		            if (i > 10 && i <= 19) {
		              EventLog.onBailedOut(this, i - 11);
		            }
		          }
		        }
		      }
		    }
		  }
	  
	  public void doEjectCatapult() {
		    new MsgAction(false, this)   {

		      public void doAction(Object paramObject) {
		        Aircraft localAircraft = (Aircraft) paramObject;
		        if (Actor.isValid(localAircraft)) {
		          Loc localLoc1 = new Loc();
		          Loc localLoc2 = new Loc();
		          Vector3d localVector3d = new Vector3d(0.0, 0.0, 10.0);
		          HookNamed localHookNamed = new HookNamed(localAircraft,
		                  "_ExternalSeat01");
		          localAircraft.pos.getAbs(localLoc2);
		          localHookNamed.computePos(localAircraft, localLoc2,
		                  localLoc1);
		          localLoc1.transform(localVector3d);
		          localVector3d.x += localAircraft.FM.Vwld.x;
		          localVector3d.y += localAircraft.FM.Vwld.y;
		          localVector3d.z += localAircraft.FM.Vwld.z;
		          new EjectionSeat(1, localLoc1, localVector3d, localAircraft);
		        }
		      }
		    };
		    this.hierMesh().chunkVisible("Seat_D0", false);
		    FM.setTakenMortalDamage(true, null);
		    FM.CT.WeaponControl[0] = false;
		    FM.CT.WeaponControl[1] = false;
		    FM.CT.bHasAileronControl = false;
		    FM.CT.bHasRudderControl = false;
		    FM.CT.bHasElevatorControl = false;
		  }

	  private final void doRemoveBlister1() {
		    if (this.hierMesh().chunkFindCheck("Blister1_D0") != -1
		            && FM.AS.getPilotHealth(0) > 0.0F) {
		      this.hierMesh().hideSubTrees("Blister1_D0");
		      Wreckage localWreckage = new Wreckage(this, this.hierMesh().chunkFind("Blister1_D0"));
		      localWreckage.collide(false);
		      Vector3d localVector3d = new Vector3d();
		      localVector3d.set(FM.Vwld);
		      localWreckage.setSpeed(localVector3d);
		    }
		  }

		  private final void doRemoveBlisters() {
		    for (int i = 2; i < 10; i++) {
		      if (this.hierMesh().chunkFindCheck("Blister" + i + "_D0") != -1
		              && FM.AS.getPilotHealth(i - 1) > 0.0F) {
		        this.hierMesh().hideSubTrees("Blister" + i + "_D0");
		        Wreckage localWreckage = new Wreckage(this,
		                this.hierMesh().chunkFind("Blister" + i + "_D0"));
		        localWreckage.collide(false);
		        Vector3d localVector3d = new Vector3d();
		        localVector3d.set(FM.Vwld);
		        localWreckage.setSpeed(localVector3d);
		      }
		    }
		  }
		  
    public static boolean bChangedPit = false;
    private float arrestor2;
    public float AirBrakeControl;
    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.F9F.class;
        Property.set(class1, "originCountry", PaintScheme.countryUSA);
    }
}