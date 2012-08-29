package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.weapons.BombJATO;
import com.maddox.rts.MsgAction;

public class F84G1 extends com.maddox.il2.objects.air.Scheme2
implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeBNZFighter, TypeZBReceiver,
com.maddox.il2.objects.air.TypeFighterAceMaker, com.maddox.il2.objects.air.TypeDockable, TypeSupersonic, TypeFuelDump
{

	protected boolean bHasBoosters;
	protected long boosterFireOutTime;
	public float AirBrakeControl;
	public int k14Mode;
	public int k14WingspanType;
	public float k14Distance;
	private float dynamoOrient;
	private boolean bDynamoRotary;
	private int pk;
	private com.maddox.il2.engine.Actor queen_last;
	private long queen_time;
	private boolean bNeedSetup;
	private long dtime;
	private com.maddox.il2.engine.Actor target_;
	private com.maddox.il2.engine.Actor queen_;
	private int dockport_;
	private boolean overrideBailout;
	private boolean ejectComplete;
	private float engineSurgeDamage;
	private float oldthrl;
	private float curthrl;
	private float SonicBoom = 0.0F;
	private Eff3DActor shockwave;
	private boolean isSonic;
    public static float FlowRate = 8.5F; //Flow rate in litres per second
    public static float FuelReserve = 1628F;//Minimum amount of total fuel that can't be drained - "Reserve Tank"
    private static float kl = 1.0F;
    private static float kr = 1.0F;
    private static float kc = 1.0F;
	public static boolean bChangedPit = false;
	private com.maddox.il2.objects.weapons.Bomb booster[] = {
			null, null
	};
	
    
	public F84G1()
	{
		bHasBoosters = true;
		boosterFireOutTime = -1L;
		AirBrakeControl = 0.0F;
		k14Mode = 0;
		k14WingspanType = 0;
		k14Distance = 200F;
		dynamoOrient = 0.0F;
		bDynamoRotary = false;
		pk = 0;
		queen_last = null;
		queen_time = 0L;
		bNeedSetup = true;
		dtime = -1L;
		target_ = null;
		queen_ = null;
		engineSurgeDamage = 0.0F;
		oldthrl = -1.0F;
		curthrl = -1.0F;
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
		if(FM.getAltitude() < 3000F)
			hierMesh().chunkVisible("HMask1_D0", false);
		else
			hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
		if(FM.AP.way.isLanding() && (double)FM.getSpeed() > (double)FM.VmaxFLAPS * 2D)
			FM.CT.AirBrakeControl = 1.0F;
		else
			if(FM.AP.way.isLanding() && (double)FM.getSpeed() < (double)FM.VmaxFLAPS * 1.5D)
				FM.CT.AirBrakeControl = 0.0F;
		if(flag && FM.AP.way.curr().Action == 3 && typeDockableIsDocked() && java.lang.Math.abs(((com.maddox.il2.objects.air.Aircraft)queen_).FM.Or.getKren()) < 3F)
			if(FM.isPlayers())
			{
				if((FM instanceof com.maddox.il2.fm.RealFlightModel) && !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
				{
					typeDockableAttemptDetach();
					((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
					((com.maddox.il2.ai.air.Maneuver)FM).setCheckStrike(false);
					FM.Vwld.z -= 5D;
					dtime = com.maddox.rts.Time.current();
				}
			} else
			{
				typeDockableAttemptDetach();
				((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
				((com.maddox.il2.ai.air.Maneuver)FM).setCheckStrike(false);
				FM.Vwld.z -= 5D;
				dtime = com.maddox.rts.Time.current();
			}
	}

	public void destroy()
	{
		doCutBoosters();
		super.destroy();
	}

	public void doFireBoosters()
	{
		Eff3DActor.New(this, findHook("_Booster1"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", 33F);
		Eff3DActor.New(this, findHook("_Booster1"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", 30F);
		Eff3DActor.New(this, findHook("_Booster1"), null, 2.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", 30F);
		Eff3DActor.New(this, findHook("_Booster1"), null, 0.25F, "3DO/Effects/Aircraft/TurboHWK109D.eff", 30F);
		Eff3DActor.New(this, findHook("_Booster2"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109T.eff", 33F);
		Eff3DActor.New(this, findHook("_Booster2"), null, 1.0F, "3DO/Effects/Aircraft/TurboHWK109S.eff", 30F);
		Eff3DActor.New(this, findHook("_Booster2"), null, 2.0F, "3DO/Effects/Aircraft/TurboHWK109F.eff", 30F);
		Eff3DActor.New(this, findHook("_Booster2"), null, 0.25F, "3DO/Effects/Aircraft/TurboHWK109D.eff", 30F);
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
		if(super.thisWeaponsName.startsWith("JATO_"))
		{
			for(int i = 0; i < 2; i++)
				try
			{
					booster[i] = ((com.maddox.il2.objects.weapons.Bomb) (new BombJATO()));
					booster[i].pos.setBase(((com.maddox.il2.engine.Actor) (this)), findHook(((java.lang.Object) ("_BoosterH" + (i + 1)))), false);
					booster[i].pos.resetAsBase();
					booster[i].drawing(true);
			}
			catch(java.lang.Exception exception)
			{
				debugprintln("Structure corrupt - can't hang Starthilferakete..");
			}
		}
	}

	protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
	{
		switch(i)
		{
		case 33: // '!'
		case 34: // '"'
		case 35: // '#'
		case 36: // '$'
		case 37: // '%'
		case 38: // '&'
			doCutBoosters();
			FM.AS.setGliderBoostOff();
			bHasBoosters = false;
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public float getAirPressure(float theAltitude) {
		float fBase = 1F - (L * theAltitude / T0);
		float fExponent = (G_CONST * M) / (R * L);
		return p0 * (float) Math.pow(fBase, fExponent);
	}

	public float getAirPressureFactor(float theAltitude) {
		return getAirPressure(theAltitude) / p0;
	}

	public float getAirDensity(float theAltitude) {
		return (getAirPressure(theAltitude) * M)
		/ (R * (T0 - (L * theAltitude)));
	}

	public float getAirDensityFactor(float theAltitude) {
		return getAirDensity(theAltitude) / Rho0;
	}

	public float getMachForAlt(float theAltValue) {
		theAltValue /= 1000F; // get altitude in km
		int i = 0;
		for (i = 0; i < fMachAltX.length; i++) {
			if (fMachAltX[i] > theAltValue) {
				break;
			}
		}
		if (i == 0) {
			return fMachAltY[0];
		}
		float baseMach = fMachAltY[i - 1];
		float spanMach = fMachAltY[i] - baseMach;
		float baseAlt = fMachAltX[i - 1];
		float spanAlt = fMachAltX[i] - baseAlt;
		float spanMult = (theAltValue - baseAlt) / spanAlt;
		return baseMach + (spanMach * spanMult);
	}

	public float calculateMach() {
		return (FM.getSpeedKMH() / getMachForAlt(FM.getAltitude()));

	}

	public void soundbarier() {

		float f = getMachForAlt(FM.getAltitude()) - FM.getSpeedKMH();
		if (f < 0.5F) {
			f = 0.5F;
		}
		float f_0_ = FM.getSpeedKMH() - getMachForAlt(FM.getAltitude());
		if (f_0_ < 0.5F) {
			f_0_ = 0.5F;
		}
		if (calculateMach() <= 1.0) {
			FM.VmaxAllowed = FM.getSpeedKMH() + f;
			SonicBoom = 0.0F;
			isSonic = false;
		}
		if (calculateMach() >= 1.0) {
			FM.VmaxAllowed = FM.getSpeedKMH() + f_0_;
			isSonic = true;
		}
		if (FM.VmaxAllowed > 1500.0F) {
			FM.VmaxAllowed = 1500.0F;
		}

		if (isSonic && SonicBoom < 1) {
			super.playSound("aircraft.SonicBoom", true);
			super.playSound("aircraft.SonicBoomInternal", true);
			if (FM.actor == World.getPlayerAircraft()) {
				HUD.log(AircraftHotKeys.hudLogPowerId, "Mach 1 Exceeded!");
			}
			if (Config.isUSE_RENDER()
					&& World.Rnd().nextFloat() < getAirDensityFactor(FM.getAltitude())) {
				shockwave = Eff3DActor.New(this, findHook("_Shockwave"), null,
						1.0F, "3DO/Effects/Aircraft/Condensation.eff", -1F);
			}
			SonicBoom = 1;
		}
		if (calculateMach() > 1.01 || calculateMach() < 1.0) {
			Eff3DActor.finish(shockwave);
		}
	}

	public void engineSurge(float f) {
		if (((FlightModelMain) (super.FM)).AS.isMaster()) {
			if (curthrl == -1F) {
				curthrl = oldthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
			} else {
				curthrl = ((FlightModelMain) (super.FM)).EI.engines[0].getControlThrottle();
				if (curthrl < 1.05F) {
					if ((curthrl - oldthrl) / f > 20.0F
							&& FM.EI.engines[0].getRPM() < 3200.0F
							&& FM.EI.engines[0].getStage() == 6
							&& World.Rnd().nextFloat() < 0.40F) {
						if (FM.actor == World.getPlayerAircraft()) {
							HUD.log(AircraftHotKeys.hudLogWeaponId,
									"Compressor Stall!");
						}
						super.playSound("weapon.MGunMk108s", true);
						engineSurgeDamage += 1.0000000000000001E-002D * (double) (((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
						((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness()
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
						engineSurgeDamage += 1.0000000000000001E-003D * (double) (((FlightModelMain) (super.FM)).EI.engines[0].getRPM() / 1000F);
						((FlightModelMain) (super.FM)).EI.engines[0].doSetReadyness(((FlightModelMain) (super.FM)).EI.engines[0].getReadyness()
								- engineSurgeDamage);
						if (World.Rnd().nextFloat() < 0.40F
								&& FM instanceof RealFlightModel
								&& ((RealFlightModel) FM).isRealMode()) {
							if (FM.actor == World.getPlayerAircraft()) {
								HUD.log(AircraftHotKeys.hudLogWeaponId,
										"Engine Flameout!");
							}
							FM.EI.engines[0].setEngineStops(this);
						} else {
							if (FM.actor == World.getPlayerAircraft()) {
								HUD.log(AircraftHotKeys.hudLogWeaponId,
										"Compressor Stall!");
							}
						}
					}
				}
				oldthrl = curthrl;
			}
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
		if(FM.AS.isMaster() && com.maddox.il2.engine.Config.isUSE_RENDER())
			if(FM.EI.engines[0].getPowerOutput() > 0.5F && FM.EI.engines[0].getStage() == 6)
			{
				if(FM.EI.engines[0].getPowerOutput() > 2.0F)
					FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 5);
				else
					FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 4);
			} else
			{
				FM.AS.setSootState(((com.maddox.il2.engine.Actor) (this)), 0, 0);
			}
		if((FM instanceof com.maddox.il2.ai.air.Pilot) && bHasBoosters && super.thisWeaponsName.startsWith("JATO_"))
		{
			if(FM.getAltitude() > 300F && boosterFireOutTime == -1L && FM.Loc.z != 0.0D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.05F)
			{
				doCutBoosters();
				FM.AS.setGliderBoostOff();
				bHasBoosters = false;
			}
			if(bHasBoosters && boosterFireOutTime == -1L && FM.Gears.onGround() && FM.EI.getPowerOutput() > 0.8F && FM.getSpeedKMH() > 20F)
			{
				boosterFireOutTime = com.maddox.rts.Time.current() + 30000L;
				doFireBoosters();
				FM.AS.setGliderBoostOn();
			}
			if(bHasBoosters && boosterFireOutTime > 0L)
			{
				if(com.maddox.rts.Time.current() < boosterFireOutTime)
					FM.producedAF.x += 20000D;
				if(com.maddox.rts.Time.current() > boosterFireOutTime + 10000L)
				{
					doCutBoosters();
					FM.AS.setGliderBoostOff();
					bHasBoosters = false;
				}
			}
		}
		if(bNeedSetup)
			checkAsDrone();
		int i = aircIndex();
		if(FM instanceof com.maddox.il2.ai.air.Maneuver)
			if(typeDockableIsDocked())
			{
				if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
				{
					((com.maddox.il2.ai.air.Maneuver)FM).unblock();
					((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(48);
					for(int j = 0; j < i; j++)
						((com.maddox.il2.ai.air.Maneuver)FM).push(48);

					if(FM.AP.way.curr().Action != 3)
						((com.maddox.il2.ai.air.Maneuver)FM).AP.way.setCur(((com.maddox.il2.objects.air.Aircraft)queen_).FM.AP.way.Cur());
					((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
				}
				if(FM.M.fuel < FM.M.maxFuel)
					FM.M.fuel += 20F * f;
			} else
				if(!(FM instanceof com.maddox.il2.fm.RealFlightModel) || !((com.maddox.il2.fm.RealFlightModel)FM).isRealMode())
				{
					if(FM.EI.engines[0].getStage() == 0)
						FM.EI.setEngineRunning();
					if(dtime > 0L && ((com.maddox.il2.ai.air.Maneuver)FM).Group != null)
					{
						((com.maddox.il2.ai.air.Maneuver)FM).Group.leaderGroup = null;
						((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(22);
						((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(3000L);
						if(com.maddox.rts.Time.current() > dtime + 3000L)
						{
							dtime = -1L;
							((com.maddox.il2.ai.air.Maneuver)FM).clear_stack();
							((com.maddox.il2.ai.air.Maneuver)FM).set_maneuver(0);
							((com.maddox.il2.ai.air.Pilot)FM).setDumbTime(0L);
						}
					} else
						if(FM.AP.way.curr().Action == 0)
						{
							com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)FM;
							if(maneuver.Group != null && maneuver.Group.airc[0] == this && maneuver.Group.clientGroup != null)
								maneuver.Group.setGroupTask(2);
						}
				}
		engineSurge(f);
		soundbarier();
		super.update(f);
	}

	public void doEjectCatapult() {
		new MsgAction(false, this) {

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
				Wreckage localWreckage = new Wreckage(this, this.hierMesh().chunkFind("Blister" + i + "_D0"));
				localWreckage.collide(false);
				Vector3d localVector3d = new Vector3d();
				localVector3d.set(FM.Vwld);
				localWreckage.setSpeed(localVector3d);
			}
		}
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
		com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
	}

	public void typeFighterAceMakerAdjSideslipMinus()
	{
		k14WingspanType++;
		if(k14WingspanType > 9)
			k14WingspanType = 9;
		com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
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

	protected void moveWingFold(com.maddox.il2.engine.HierMesh hiermesh, float f)
	{
		hiermesh.chunkSetAngles("WingLMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 70F), 0.0F);
		hiermesh.chunkSetAngles("WingRMid_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -70F), 0.0F);
	}

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, Aircraft.cvt(f * kc, 0.01F, 0.7F, 0.0F, -100F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, Aircraft.cvt(f * kc, 0.01F, 0.1F, 0.0F, -80F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, Aircraft.cvt(f * kc, 0.01F, 0.1F, 0.0F, 80F), 0.0F);
        hiermesh.chunkSetAngles("GearC7_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, Aircraft.cvt(f * kl, 0.01F, 0.7F, 0.0F, 81F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, Aircraft.cvt(f * kr, 0.01F, 0.6F, 0.0F, -81F), 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, Aircraft.cvt(f * kl, 0.01F, 0.7F, 0.0F, 88F), 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, Aircraft.cvt(f * kr, 0.01F, 0.6F, 0.0F, -88F), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, Aircraft.cvt(f * kl, 0.01F, 0.025F, 0.0F, -110F), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, Aircraft.cvt(f * kr, 0.01F, 0.025F, 0.0F, 110F), 0.0F);
    }

    protected void moveGear(float f)
    {
        moveGear(hierMesh(), f);
        if(((FlightModelMain) (super.FM)).CT.getGear() >= 0.9985F)
        {
            kl = 1.0F;
            kr = 1.0F;
            kc = 1.0F;
        }
    }

	public void moveWheelSink()
	{
		float f = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.19075F, 0.0F, 1.0F);
		resetYPRmodifier();
		com.maddox.il2.objects.air.Aircraft.xyz[0] = -0.19075F * f;
		hierMesh().chunkSetLocate("GearC6_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
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
	}

	public void moveCockpitDoor(float f)
	{
		resetYPRmodifier();
		com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.2F, 0.0F, 0.9F);
		hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
		if(com.maddox.il2.engine.Config.isUSE_RENDER())
		{
			if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
				com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
			setDoorSnd(f);
		}
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

	protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
	{
		if(s.startsWith("xx"))
		{
			if(s.startsWith("xxarmor"))
			{
				debuggunnery("Armor: Hit..");
				if(s.endsWith("p1"))
				{
					getEnergyPastArmor(13.350000381469727D / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
					if(shot.power <= 0.0F)
						doRicochetBack(shot);
				} else
					if(s.endsWith("p2"))
						getEnergyPastArmor(8.77F, shot);
					else
						if(s.endsWith("g1"))
						{
							getEnergyPastArmor((double)com.maddox.il2.ai.World.Rnd().nextFloat(40F, 60F) / (java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.v1.x) + 9.9999997473787516E-005D), shot);
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
						if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F && getEnergyPastArmor(1.1F, shot) > 0.0F)
						{
							debuggunnery("Controls: Ailerones Controls: Out..");
							FM.AS.setControlsDamage(shot.initiator, 0);
						}
						break;

					case 3: // '\003'
					case 4: // '\004'
						if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
						{
							debuggunnery("Controls: Elevator Controls: Disabled / Strings Broken..");
							FM.AS.setControlsDamage(shot.initiator, 1);
						}
						if(getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 2.93F), shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
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
						if(s.startsWith("xxtank"))
						{
							int j = s.charAt(6) - 49;
							if(getEnergyPastArmor(0.1F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
							{
								if(FM.AS.astateTankStates[j] == 0)
								{
									debuggunnery("Fuel Tank (" + j + "): Pierced..");
									FM.AS.hitTank(shot.initiator, j, 1);
									FM.AS.doSetTankState(shot.initiator, j, 1);
								}
								if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.075F)
								{
									FM.AS.hitTank(shot.initiator, j, 2);
									debuggunnery("Fuel Tank (" + j + "): Hit..");
								}
							}
						} else
							if(s.startsWith("xxspar"))
							{
								debuggunnery("Spar Construction: Hit..");
								if(s.startsWith("xxsparlm") && chunkDamageVisible("WingLMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
								{
									debuggunnery("Spar Construction: WingLMid Spars Damaged..");
									nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
								}
								if(s.startsWith("xxsparrm") && chunkDamageVisible("WingRMid") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
								{
									debuggunnery("Spar Construction: WingRMid Spars Damaged..");
									nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
								}
								if(s.startsWith("xxsparlo") && chunkDamageVisible("WingLOut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
								{
									debuggunnery("Spar Construction: WingLOut Spars Damaged..");
									nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
								}
								if(s.startsWith("xxsparro") && chunkDamageVisible("WingROut") > 2 && getEnergyPastArmor(16.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F)
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
			if(s.startsWith("xxmgun1") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 0);
			if(s.startsWith("xxmgun2") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 1);
			if(s.startsWith("xxmgun3") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 2);
			if(s.startsWith("xxmgun4") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 3);
			if(s.startsWith("xxmgun5") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 4);
			if(s.startsWith("xxmgun6") && getEnergyPastArmor(4.85F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.75F)
				FM.AS.setJamBullets(1, 5);
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
									                if(World.Rnd().nextFloat() < 0.05F && ((FlightModelMain) (super.FM)).Gears.isHydroOperable())
									                {
									                    debuggunnery("Hydro System: Disabled..");
									                    ((FlightModelMain) (super.FM)).AS.setInternalDamage(shot.initiator, 0);
									                    ((FlightModelMain) (super.FM)).Gears.setHydroOperable(false);
									                    gearDamageFX(s);
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
														hitFlesh(k, shot, ((int) (byte0)));
													}
		}
	}

	protected void moveAirBrake(float f)
	{
		resetYPRmodifier();
		com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.25F);
		hierMesh().chunkSetLocate("Brake01_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
		hierMesh().chunkSetAngles("Brake02_D0", 0.0F, -70F * f, 0.0F);
	}

	public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
	{
		super.msgCollisionRequest(actor, aflag);
		if(queen_last != null && queen_last == actor && (queen_time == 0L || com.maddox.rts.Time.current() < queen_time + 5000L))
			aflag[0] = false;
		else
			aflag[0] = true;
	}

	public void missionStarting()
	{
		checkAsDrone();
	}

	private void checkAsDrone()
	{
		if(target_ == null)
		{
			if(FM.AP.way.curr().getTarget() == null)
				FM.AP.way.next();
			target_ = FM.AP.way.curr().getTarget();
			if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.ai.Wing))
			{
				com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)target_;
				int i = aircIndex();
				if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor) (wing.airc[i / 2]))))
					target_ = ((com.maddox.il2.engine.Actor) (wing.airc[i / 2]));
				else
					target_ = null;
			}
		}
		if(com.maddox.il2.engine.Actor.isValid(target_) && (target_ instanceof com.maddox.il2.objects.air.TypeTankerBoom))
		{
			queen_last = target_;
			queen_time = com.maddox.rts.Time.current();
			if(isNetMaster())
				((com.maddox.il2.objects.air.TypeDockable)target_).typeDockableRequestAttach(((com.maddox.il2.engine.Actor) (this)), aircIndex() % 2, true);
		}
		bNeedSetup = false;
		target_ = null;
	}

	public int typeDockableGetDockport()
	{
		if(typeDockableIsDocked())
			return dockport_;
		else
			return -1;
	}

	public com.maddox.il2.engine.Actor typeDockableGetQueen()
	{
		return queen_;
	}

	public boolean typeDockableIsDocked()
	{
		return com.maddox.il2.engine.Actor.isValid(queen_);
	}

	public void typeDockableAttemptAttach()
	{
		if(FM.AS.isMaster() && !typeDockableIsDocked())
		{
			com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.War.getNearestFriend(((com.maddox.il2.objects.air.Aircraft) (this)));
			if(aircraft instanceof com.maddox.il2.objects.air.TypeTankerBoom)
				((com.maddox.il2.objects.air.TypeDockable)aircraft).typeDockableRequestAttach(((com.maddox.il2.engine.Actor) (this)));
		}
	}

	public void typeDockableAttemptDetach()
	{
		if(FM.AS.isMaster() && typeDockableIsDocked() && com.maddox.il2.engine.Actor.isValid(queen_))
			((com.maddox.il2.objects.air.TypeDockable)queen_).typeDockableRequestDetach(((com.maddox.il2.engine.Actor) (this)));
	}

	public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor)
	{
	}

	public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor)
	{
	}

	public void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
	{
	}

	public void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor, int i, boolean flag)
	{
	}

	public void typeDockableDoAttachToDrone(com.maddox.il2.engine.Actor actor, int i)
	{
	}

	public void typeDockableDoDetachFromDrone(int i)
	{
	}

	public void typeDockableDoAttachToQueen(com.maddox.il2.engine.Actor actor, int i)
	{
		queen_ = actor;
		dockport_ = i;
		queen_last = queen_;
		queen_time = 0L;
		FM.EI.setEngineRunning();
		FM.CT.setGearAirborne();
		moveGear(0.0F);
		com.maddox.il2.fm.FlightModel flightmodel = ((com.maddox.il2.objects.air.Aircraft)queen_).FM;
		if(aircIndex() == 0 && (FM instanceof com.maddox.il2.ai.air.Maneuver) && (flightmodel instanceof com.maddox.il2.ai.air.Maneuver))
		{
			com.maddox.il2.ai.air.Maneuver maneuver = (com.maddox.il2.ai.air.Maneuver)flightmodel;
			com.maddox.il2.ai.air.Maneuver maneuver1 = (com.maddox.il2.ai.air.Maneuver)FM;
			if(maneuver.Group != null && maneuver1.Group != null && maneuver1.Group.numInGroup(((com.maddox.il2.objects.air.Aircraft) (this))) == maneuver1.Group.nOfAirc - 1)
			{
				com.maddox.il2.ai.air.AirGroup airgroup = new AirGroup(maneuver1.Group);
				maneuver1.Group.delAircraft(((com.maddox.il2.objects.air.Aircraft) (this)));
				airgroup.addAircraft(((com.maddox.il2.objects.air.Aircraft) (this)));
				airgroup.attachGroup(maneuver.Group);
				airgroup.rejoinGroup = null;
				airgroup.leaderGroup = null;
				airgroup.clientGroup = maneuver.Group;
			}
		}
	}

	public void typeDockableDoDetachFromQueen(int i)
	{
		if(dockport_ == i)
		{
			queen_last = queen_;
			queen_time = com.maddox.rts.Time.current();
			queen_ = null;
			dockport_ = 0;
		}
	}

	public void typeDockableReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
	throws java.io.IOException
	{
		if(typeDockableIsDocked())
		{
			netmsgguaranted.writeByte(1);
			com.maddox.il2.engine.ActorNet actornet = null;
			if(com.maddox.il2.engine.Actor.isValid(queen_))
			{
				actornet = queen_.net;
				if(actornet.countNoMirrors() > 0)
					actornet = null;
			}
			netmsgguaranted.writeByte(dockport_);
			netmsgguaranted.writeNetObj(((com.maddox.rts.NetObj) (actornet)));
		} else
		{
			netmsgguaranted.writeByte(0);
		}
	}

	public void typeDockableReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
	throws java.io.IOException
	{
		if(netmsginput.readByte() == 1)
		{
			dockport_ = ((int) (netmsginput.readByte()));
			com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
			if(netobj != null)
			{
				com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
				((com.maddox.il2.objects.air.TypeDockable)actor).typeDockableDoAttachToDrone(((com.maddox.il2.engine.Actor) (this)), dockport_);
			}
		}
	}
	
    private void gearDamageFX(String s)
    {
        if(s.startsWith("xgearl") || s.startsWith("GearL"))
        {
            if(super.FM.isPlayers())
                HUD.log("Left Gear:  Hydraulic system Failed");
            kl = World.Rnd().nextFloat();
            kr = World.Rnd().nextFloat() * kl;
            kc = 0.1F;
            cutGearCovers("L");
        } else
        if(s.startsWith("xgearr") || s.startsWith("GearR"))
        {
            if(super.FM.isPlayers())
                HUD.log("Right Gear:  Hydraulic system Failed");
            kr = World.Rnd().nextFloat();
            kl = World.Rnd().nextFloat() * kr;
            kc = 0.1F;
            cutGearCovers("R");
        } else
        {
            if(super.FM.isPlayers())
                HUD.log("Center Gear:  Hydraulic system Failed");
            kc = World.Rnd().nextFloat();
            kl = World.Rnd().nextFloat() * kc;
            kr = World.Rnd().nextFloat() * kc;
            cutGearCovers("C");
        }
        ((FlightModelMain) (super.FM)).CT.GearControl = 1.0F;
        ((FlightModelMain) (super.FM)).Gears.setHydroOperable(false);
    }

    private void cutGearCovers(String s)
    {
        Vector3d vector3d = new Vector3d();
        if(World.Rnd().nextFloat() < 0.3F)
        {
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Gear" + s + 5 + "_D0"));
            wreckage.collide(true);
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage.setSpeed(vector3d);
            hierMesh().chunkVisible("Gear" + s + 5 + "_D0", false);
            Wreckage wreckage1 = new Wreckage(this, hierMesh().chunkFind("Gear" + s + 6 + "_D0"));
            wreckage1.collide(true);
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage1.setSpeed(vector3d);
            hierMesh().chunkVisible("Gear" + s + 6 + "_D0", false);
        } else
        if(World.Rnd().nextFloat() < 0.3F)
        {
            int i = World.Rnd().nextInt(2) + 5;
            Wreckage wreckage2 = new Wreckage(this, hierMesh().chunkFind("Gear" + s + i + "_D0"));
            wreckage2.collide(true);
            vector3d.set(((FlightModelMain) (super.FM)).Vwld);
            wreckage2.setSpeed(vector3d);
            hierMesh().chunkVisible("Gear" + s + i + "_D0", false);
        }
    }

	static 
	{
		java.lang.Class class1 = com.maddox.il2.objects.air.F84G1.class;
		new NetAircraft.SPAWN(class1);
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "F84G");
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/F84G/hier.him");
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar06())));
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_us", "3DO/Plane/F84G(US)/hier.him");
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_us", ((java.lang.Object) (new PaintSchemeFMPar06())));
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1946.9F);
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1955.3F);
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/F84G.fmd");
		com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
				com.maddox.il2.objects.air.CockpitF84G1.class
		})));
		com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
				0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
				9, 9, 9, 9, 9, 9, 3, 3, 3, 3, 
				3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 
				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
				2, 2, 2, 2
		});
		com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
				"_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
				"_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
				"_ExternalBomb05", "_ExternalBomb03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", 
				"_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", 
				"_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22"
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x500lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x500lbs+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x1000lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x1000lbs+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x500lbs+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x75gal_napalm+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x75gal_napalm+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun75Napalm 1", "BombGun75Napalm 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "20xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1"
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xTinyTim", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, null, null, null, null, 
				null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02xTinyTim+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, null, null, null, null, 
				null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "02x230gal_tank", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", "FuelTankGun_Tank230gal 1", "FuelTankGun_Tank230gal 1", null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_default", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x500lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x500lbs+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x1000lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x1000lbs+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x500lbs+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x75gal_napalm+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun75Napalm 1", "BombGun75Napalm 1", null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x75gal_napalm+02x250lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun75Napalm 1", "BombGun75Napalm 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_20xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1"
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02xTinyTim", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, null, null, null, null, 
				null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02xTinyTim+08xHVAR", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, null, null, null, null, 
				null, null, "RocketGunTinyTim 1", "RocketGunTinyTim 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", "RocketGunHVARF84 1", 
				"RocketGunHVARF84 1", "RocketGunHVARF84 1", null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x230gal_tank", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", "FuelTankGun_Tank230gal 1", "FuelTankGun_Tank230gal 1", null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_01xMk7", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				null, "PylonF84Bomb 1", null, null, null, null, null, "BombGunMk7 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_01xMk7+01x230gal_tank", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", "FuelTankGun_Tank230gal 1", null, null, null, null, "BombGunMk7 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_02x1600lbs", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", null, null, null, null, "BombGun1600lbs 1", "BombGun1600lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "JATO_01x2000lbs+01x230gal", new java.lang.String[] {
				"MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", "MGunBrowning50ki 300", null, null, null, null, 
				"PylonF84Bomb 1", "PylonF84Bomb 1", "FuelTankGun_Tank230gal 1", null, null, null, null, "BombGun2000lbs 1", null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
		com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null, null, null, null, null, null, null, 
				null, null, null, null
		});
	}
}