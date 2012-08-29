package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class Fokker_Eindecker extends Scheme1
implements TypeScout, TypeFighter
{
	public boolean syncMechDmg;

	public Fokker_Eindecker(){
		syncMechDmg = false;
	}

	public void msgShot(Shot shot)
	{
		setShot(shot);
		if(shot.chunkName.startsWith("Pilot1"))
			killPilot(shot.initiator, 0);
		if(shot.chunkName.startsWith("Engine") && World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
			FM.AS.hitEngine(shot.initiator, 0, 1);
		super.msgShot(shot);
	}

	protected void moveRudder(float f)
	{
		hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -29F * f, 0.0F);
	}

	protected void moveElevator(float f)
	{
		hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -35F * f, 0.0F);
		hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -35F * f, 0.0F);
	}

	protected void moveFan(float f)
	{  
		int i = 0;
		int j = 0;
		int l = 1;

		if(oldProp[j] < 2)
		{
			i = Math.abs((int)(FM.EI.engines[0].getw() * 0.06F));
			if(i >= 1)
				i = 1;
			if(i != oldProp[j] && hierMesh().isChunkVisible(Props[j][oldProp[j]]))
			{
				hierMesh().chunkVisible(Props[0][oldProp[j]], false);
				oldProp[j] = i;
				hierMesh().chunkVisible(Props[j][i], true);
			}
		}
		if(i == 0)
		{
			propPos[j] = (propPos[j] + 57.3F * FM.EI.engines[0].getw() * f) % 360F;
		} else
		{
			float f1 = 57.3F * FM.EI.engines[0].getw();
			f1 %= 2880F;
			f1 /= 2880F;
			if(f1 <= 0.5F)
				f1 *= 2.0F;
			else
				f1 = f1 * 2.0F - 2.0F;
			f1 *= 1200F;
			propPos[j] = (propPos[j] + f1 * f) % 360F;
		}
		hierMesh().chunkSetAngles(Props[j][0], 0.0F, 0.0F, propPos[j]);

		if(oldProp[l] < 2)
		{
			i = Math.abs((int)(FM.EI.engines[0].getw() * 0.06F));
			if(i >= 1)
				i = 1;
			if(i != oldProp[l] && hierMesh().isChunkVisible(Props[l][oldProp[l]]))
			{
				hierMesh().chunkVisible(Props[l][oldProp[l]], false);
				oldProp[l] = i;
				hierMesh().chunkVisible(Props[l][i], true);
			}
		}
		if(i == 0)
		{
			propPos[l] = (propPos[l] + 57.3F * FM.EI.engines[0].getw() * f) % 360F;
		} else
		{
			float f1 = 57.3F * FM.EI.engines[0].getw();
			f1 %= 2880F;
			f1 /= 2880F;
			if(f1 <= 0.5F)
				f1 *= 2.0F;
			else
				f1 = f1 * 2.0F - 2.0F;
			f1 *= 1200F;
			propPos[l] = (propPos[l] + f1 * f) % 360F;
		}
		hierMesh().chunkSetAngles(Props[l][0], 0.0F, propPos[l], 0.0F);
	}

	protected void moveAileron(float f)
	{
	}

	protected void moveFlap(float f)
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

	protected void hitBone(String s, Shot shot, Point3d point3d)
	{
		if(s.startsWith("xx"))
		{
			if(s.startsWith("xxcontrols"))
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
				if(World.Rnd().nextFloat() < 0.2F)
				{
					((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator, 2);
					Aircraft.debugprintln(this, "*** Rudder Controls Out.. (#8)");
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
						if(s.endsWith("cylinders"))
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
						}
			} else
				if(s.endsWith("sync"))
				{
					debugprintln(this, "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
					syncMechDmg = true;

				} else
					if(s.startsWith("xxoil"))
					{
						FM.AS.hitOil(shot.initiator, 0);
						getEnergyPastArmor(0.22F, shot);
						debugprintln(this, "*** Engine Module: Oil Tank Pierced..");
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
										((FlightModelMain) (super.FM)).AS.setJamBullets(0, 2);
									}
									getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), shot);
								} else
									if(s.startsWith("xxammo"))
									{
										if(World.Rnd().nextFloat(3800F, 30000F) < shot.power)
										{
											if(s.endsWith("01"))
											{
												debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
												FM.AS.setJamBullets(0, 0);
											}
											if(s.endsWith("02"))
											{
												debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
												FM.AS.setJamBullets(0, 1);
											}
											if(s.endsWith("03"))
											{
												debugprintln(this, "*** Cowling Gun: Ammo Feed Chain Broken..");
												FM.AS.setJamBullets(0, 2);
											}
										}
										getEnergyPastArmor(World.Rnd().nextFloat(0.0F, 28.33F), shot);
									}
			if(s.startsWith("xxspar"))
			{
				Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
				if(s.startsWith("xxspart") && chunkDamageVisible("Tail1") > 2)
				{
					Aircraft.debugprintln(this, "*** Tail1 Spars Broken in Half..");
					nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
				} else
					if(s.startsWith("xxsparli") && World.Rnd().nextFloat() < 0.25F)
					{
						Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
						nextDMGLevels(1, 2, "WingLIn_D" + chunkDamageVisible("WingLIn"), shot.initiator);
					} else
						if(s.startsWith("xxsparri") && World.Rnd().nextFloat() < 0.25F)
						{
							Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
							nextDMGLevels(1, 2, "WingRIn_D" + chunkDamageVisible("WingRIn"), shot.initiator);
						} else
							if(s.startsWith("xxsparlm") && World.Rnd().nextFloat() < 0.25F)
							{
								Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
								nextDMGLevels(1, 2, "WingLMid_D" + chunkDamageVisible("WingLMid"), shot.initiator);
							} else
								if(s.startsWith("xxsparrm") && World.Rnd().nextFloat() < 0.25F)
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
					if(s.startsWith("xeng"))
					{
						if(chunkDamageVisible("Engine1") < 2)
							hitChunk("Engine1", shot);
					} else
						if(s.startsWith("xtail"))
						{
							if(chunkDamageVisible("Tail1") < 3)
								hitChunk("Tail1", shot);
						} else
							if(s.startsWith("xrudder"))
							{
								if(chunkDamageVisible("Rudder1") < 1)
									hitChunk("Rudder1", shot);
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
										if(s.startsWith("xwinglout") && chunkDamageVisible("WingLOut") < 3)
											hitChunk("WingLOut", shot);
										if(s.startsWith("xwingrout") && chunkDamageVisible("WingROut") < 3)
											hitChunk("WingROut", shot);
									}
	}

	public void onAircraftLoaded(){
		if(super.FM.isPlayers() && this.FM.isStationedOnGround())
		{
			((FlightModelMain) (super.FM)).CT.setMagnetoControl(0);
			//this.FM.brakeShoe = true;
		}
		super.onAircraftLoaded();

	}

	private void gunSync(){
		if (syncMechDmg && FM.CT.WeaponControl[0] && FM.EI.engines[0].getStage() == 6 && FM.CT.Weapons[0][0].countBullets() != 0)
			if (World.Rnd().nextFloat() < 0.005F){
				Aircraft.debugprintln(this, "*** Prop hit");
				hierMesh().chunkVisible("PropRot1_D0", false);
				hierMesh().chunkVisible("Prop1_D1", true);
				FM.EI.engines[0].setEngineDies(this);
				FM.AS.setJamBullets(0, 0);
				FM.AS.setJamBullets(0, 1);
				FM.AS.setJamBullets(0, 2);
			}
	}

	public void update(float f)
	{	
		if(((FlightModelMain) (super.FM)).Gears.onGround())
			((FlightModelMain) (super.FM)).AS.bIsEnableToBailout = true;
		else
			((FlightModelMain) (super.FM)).AS.bIsEnableToBailout = false;
		float f1 = Atmosphere.temperature((float)((Tuple3d) (((FlightModelMain) (super.FM)).Loc)).z) - 273.15F;
		float f2 = Pitot.Indicator((float)((Tuple3d) (((FlightModelMain) (super.FM)).Loc)).z, super.FM.getSpeedKMH());
		if(f2 < 0.0F)
			f2 = 0.0F;
		float f3 = (((((FlightModelMain) (super.FM)).EI.engines[0].getControlRadiator() * f * f2) / (f2 + 50F)) * (((FlightModelMain) (super.FM)).EI.engines[0].tWaterOut - f1)) / 256F;
		((FlightModelMain) (super.FM)).EI.engines[0].tWaterOut -= f3;
		if(((FlightModelMain) (super.FM)).AS.isMaster() && Config.isUSE_RENDER())
			if(((FlightModelMain) (super.FM)).EI.engines[0].getStage() == 6 && ((FlightModelMain) (super.FM)).EI.engines[0].getRPM() > 650F)
				((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 3);
			else
				((FlightModelMain) (super.FM)).AS.setSootState(this, 0, 0);
		if(FM.EI.engines[0].getStage() == 6 && (FM.CT.getMagnetoControl() == 2F || FM.CT.getMagnetoControl() == 1F))
			FM.EI.engines[0].setControlThrottle(0.33F);
		else
		if(FM.EI.engines[0].getStage() == 6 && FM.CT.getMagnetoControl() == 3F)
			FM.EI.engines[0].setControlThrottle(1F);	
		gunSync();
		super.update(f);
	}

	public void doSetSootState(int i, int j)
	{
		for(int k = 0; k < 2; k++)
		{
			if(((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] != null)
				Eff3DActor.finish(((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k]);
			((FlightModelMain) (super.FM)).AS.astateSootEffects[i][k] = null;
		}

		switch(j)
		{
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			((FlightModelMain) (super.FM)).AS.astateSootEffects[i][0] = Eff3DActor.New(this, findHook("_Engine1Smoke"), null, 1.0F, "3DO/Effects/Aircraft/EngineBlackSmall.eff", -1F);
			break;

		case 4: // '\004'
		case 5: // '\005'
		default:
			return;
		}
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

	protected float kangle;

	static 
	{
		Class class1 = com.maddox.il2.objects.air.Fokker_Eindecker.class;
		Property.set(class1, "originCountry", PaintScheme.countryGermany);
	}

}
