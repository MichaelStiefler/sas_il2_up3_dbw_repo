// Source File Name: SEAGLADIATOR.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeFighter, TypeTNBFighter, Aircraft, 
//            Cockpit, PaintScheme

public abstract class SEAGLADIATOR extends Scheme1 implements TypeFighter,
		TypeTNBFighter {

	public boolean bChangedPit;

	static {
		Class class1 = SEAGLADIATOR.class;
		Property.set(class1, "originCountry", PaintScheme.countryBritain);
	}

	public SEAGLADIATOR() {
		this.bChangedPit = true;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 11: // '\013'
			this.hierMesh().chunkVisible("PodkosLw_D0", false);
			this.hierMesh().chunkVisible("PodkosRw_D0", false);
			break;

		case 17: // '\021'
			this.hierMesh().chunkVisible("PodkosLw_D0", false);
			break;

		case 18: // '\022'
			this.hierMesh().chunkVisible("PodkosLw_D0", false);
			break;

		case 19: // '\023'
			this.hierMesh().chunkVisible("WireC_D0", false);
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void doMurderPilot(int i) {
		switch (i) {
		case 0: // '\0'
			this.hierMesh().chunkVisible("Pilot1_D0", false);
			this.hierMesh().chunkVisible("Head1_D0", false);
			this.hierMesh().chunkVisible("HMask1_D0", false);
			this.hierMesh().chunkVisible("Pilot1_D1", true);
			break;
		}
	}

	protected void hitBone(String s, Shot shot, Point3d point3d) {
		if (s.startsWith("xx")) {
			if (s.startsWith("xxarmor")) {
				Aircraft.debugprintln(this, "*** Armor: Hit..");
				if (s.endsWith("p1")) {
					this.getEnergyPastArmor(
							8.1000003814697266D / (Math.abs(Aircraft.v1.x) + 9.9999997473787516E-006D),
							shot);
				}
			} else {
				if (s.startsWith("xxcontrols")) {
					int i = s.charAt(10) - 48;
					switch (i) {
					default:
						break;

					case 1: // '\001'
						if (this.getEnergyPastArmor(
								World.Rnd().nextFloat(0.1F, 2.3F), shot) > 0.0F) {
							if (World.Rnd().nextFloat() < 0.25F) {
								this.FM.AS.setControlsDamage(shot.initiator, 2);
								Aircraft.debugprintln(this,
										"*** Rudder Controls: Disabled..");
							}
							if (World.Rnd().nextFloat() < 0.25F) {
								this.FM.AS.setControlsDamage(shot.initiator, 1);
								Aircraft.debugprintln(this,
										"*** Elevator Controls: Disabled..");
							}
						}
						// fall through

					case 2: // '\002'
					case 3: // '\003'
						if (this.getEnergyPastArmor(1.5F, shot) > 0.0F) {
							this.FM.AS.setControlsDamage(shot.initiator, 0);
							Aircraft.debugprintln(this,
									"*** Aileron Controls: Control Crank Destroyed..");
						}
						break;
					}
				}
				if (s.startsWith("xxspar")) {
					Aircraft.debugprintln(this, "*** Spar Construction: Hit..");
					if (s.startsWith("xxspart")
							&& (this.chunkDamageVisible("Tail1") > 2)
							&& (this.getEnergyPastArmor(9.5F / (float) Math
									.sqrt((Aircraft.v1.y * Aircraft.v1.y)
											+ (Aircraft.v1.z * Aircraft.v1.z)),
									shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** Tail1 Spars Broken in Half..");
						this.nextDMGLevels(1, 2, "Tail1_D3", shot.initiator);
					}
					if (s.startsWith("xxsparli")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingLIn Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingLIn_D"
										+ this.chunkDamageVisible("WingLIn"),
								shot.initiator);
					}
					if (s.startsWith("xxsparri")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingRIn Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingRIn_D"
										+ this.chunkDamageVisible("WingRIn"),
								shot.initiator);
					}
					if (s.startsWith("xxsparlm")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingLMid Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingLMid_D"
										+ this.chunkDamageVisible("WingLMid"),
								shot.initiator);
					}
					if (s.startsWith("xxsparrm")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingRMid Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingRMid_D"
										+ this.chunkDamageVisible("WingRMid"),
								shot.initiator);
					}
					if (s.startsWith("xxsparlo")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingLOut Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingLOut_D"
										+ this.chunkDamageVisible("WingLOut"),
								shot.initiator);
					}
					if (s.startsWith("xxsparro")
							&& (World.Rnd().nextFloat() < 0.25F)
							&& (this.getEnergyPastArmor(9.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** WingROut Spars Damaged..");
						this.nextDMGLevels(
								1,
								2,
								"WingROut_D"
										+ this.chunkDamageVisible("WingROut"),
								shot.initiator);
					}
					if (s.startsWith("xxstabl")
							&& (this.getEnergyPastArmor(16.2F, shot) > 0.0F)) {
						Aircraft.debugprintln(this, "*** StabL Spars Damaged..");
						this.nextDMGLevels(1, 2,
								"StabL_D" + this.chunkDamageVisible("StabL"),
								shot.initiator);
					}
					if (s.startsWith("xxstabr")
							&& (this.getEnergyPastArmor(16.2F, shot) > 0.0F)) {
						Aircraft.debugprintln(this, "*** StabR Spars Damaged..");
						this.nextDMGLevels(1, 2,
								"StabR_D" + this.chunkDamageVisible("StabR"),
								shot.initiator);
					}
				}
				if (s.startsWith("xxlock")) {
					Aircraft.debugprintln(this, "*** Lock Construction: Hit..");
					if (s.startsWith("xxlockr")
							&& (this.getEnergyPastArmor(1.5F * World.Rnd()
									.nextFloat(1.0F, 3F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** Rudder Lock Shot Off..");
						this.nextDMGLevels(
								3,
								2,
								"Rudder1_D"
										+ this.chunkDamageVisible("Rudder1"),
								shot.initiator);
					}
					if (s.startsWith("xxlockvl")
							&& (this.getEnergyPastArmor(1.5F * World.Rnd()
									.nextFloat(1.0F, 1.2F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** VatorL Lock Shot Off..");
						this.nextDMGLevels(3, 2,
								"VatorL_D" + this.chunkDamageVisible("VatorL"),
								shot.initiator);
					}
					if (s.startsWith("xxlockvr")
							&& (this.getEnergyPastArmor(1.5F * World.Rnd()
									.nextFloat(1.0F, 1.2F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** VatorR Lock Shot Off..");
						this.nextDMGLevels(3, 2,
								"VatorR_D" + this.chunkDamageVisible("VatorR"),
								shot.initiator);
					}
					if (s.startsWith("xxlockal")
							&& (this.getEnergyPastArmor(1.5F * World.Rnd()
									.nextFloat(1.0F, 1.2F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** AroneL Lock Shot Off..");
						this.nextDMGLevels(3, 2,
								"AroneL_D" + this.chunkDamageVisible("AroneL"),
								shot.initiator);
					}
					if (s.startsWith("xxlockar")
							&& (this.getEnergyPastArmor(1.5F * World.Rnd()
									.nextFloat(1.0F, 1.2F), shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** AroneR Lock Shot Off..");
						this.nextDMGLevels(3, 2,
								"AroneR_D" + this.chunkDamageVisible("AroneR"),
								shot.initiator);
					}
				}
				if (s.startsWith("xxeng")) {
					Aircraft.debugprintln(this, "*** Engine Module: Hit..");
					if (s.endsWith("prop")) {
						if ((this.getEnergyPastArmor(0.45F, shot) > 0.0F)
								&& (World.Rnd().nextFloat() < 0.5F)) {
							this.FM.AS.setEngineSpecificDamage(shot.initiator,
									0, 3);
							Aircraft.debugprintln(this,
									"*** Engine Module: Prop Governor Hit, Disabled..");
						}
					} else if (s.endsWith("case")) {
						if (this.getEnergyPastArmor(2.1F, shot) > 0.0F) {
							if (World.Rnd().nextFloat() < (shot.power / 175000F)) {
								this.FM.AS.setEngineStuck(shot.initiator, 0);
								Aircraft.debugprintln(this,
										"*** Engine Module: Bullet Jams Crank Ball Bearing..");
							}
							if (World.Rnd().nextFloat() < (shot.power / 50000F)) {
								this.FM.AS.hitEngine(shot.initiator, 0, 2);
								Aircraft.debugprintln(
										this,
										"*** Engine Module: Crank Case Hit, Readyness Reduced to "
												+ this.FM.EI.engines[0]
														.getReadyness() + "..");
							}
							this.FM.EI.engines[0].setReadyness(
									shot.initiator,
									this.FM.EI.engines[0].getReadyness()
											- World.Rnd().nextFloat(0.0F,
													shot.power / 48000F));
							Aircraft.debugprintln(
									this,
									"*** Engine Module: Crank Case Hit, Readyness Reduced to "
											+ this.FM.EI.engines[0]
													.getReadyness() + "..");
						}
						this.getEnergyPastArmor(12.7F, shot);
					} else if (s.startsWith("xxeng1cyls")) {
						if ((this.getEnergyPastArmor(
								World.Rnd().nextFloat(0.2F, 4.4F), shot) > 0.0F)
								&& (World.Rnd().nextFloat() < (this.FM.EI.engines[0]
										.getCylindersRatio() * 1.12F))) {
							this.FM.EI.engines[0].setCyliderKnockOut(
									shot.initiator,
									World.Rnd().nextInt(1,
											(int) (shot.power / 4800F)));
							Aircraft.debugprintln(
									this,
									"*** Engine Module: Cylinders Hit, "
											+ this.FM.EI.engines[0]
													.getCylindersOperable()
											+ "/"
											+ this.FM.EI.engines[0]
													.getCylinders() + " Left..");
							if (World.Rnd().nextFloat() < (shot.power / 48000F)) {
								this.FM.AS.hitEngine(shot.initiator, 0, 3);
								Aircraft.debugprintln(this,
										"*** Engine Module: Cylinders Hit, Engine Fires..");
							}
							if (World.Rnd().nextFloat() < 0.005F) {
								this.FM.AS.setEngineStuck(shot.initiator, 0);
								Aircraft.debugprintln(this,
										"*** Engine Module: Bullet Jams Piston Head..");
							}
							this.getEnergyPastArmor(22.5F, shot);
						}
					} else if (s.endsWith("eqpt")) {
						if ((this.getEnergyPastArmor(0.2721F, shot) > 0.0F)
								&& (World.Rnd().nextFloat() < 0.5F)) {
							if (World.Rnd().nextFloat() < 0.1F) {
								this.FM.EI.engines[0].setMagnetoKnockOut(
										shot.initiator, 0);
								Aircraft.debugprintln(this,
										"*** Engine Module: Magneto 0 Destroyed..");
							}
							if (World.Rnd().nextFloat() < 0.1F) {
								this.FM.EI.engines[0].setMagnetoKnockOut(
										shot.initiator, 1);
								Aircraft.debugprintln(this,
										"*** Engine Module: Magneto 1 Destroyed..");
							}
							if (World.Rnd().nextFloat() < 0.1F) {
								this.FM.AS.setEngineSpecificDamage(
										shot.initiator, 0, 6);
								Aircraft.debugprintln(this,
										"*** Engine Module: Prop Controls Cut..");
							}
							if (World.Rnd().nextFloat() < 0.1F) {
								this.FM.AS.setEngineSpecificDamage(
										shot.initiator, 0, 1);
								Aircraft.debugprintln(this,
										"*** Engine Module: Throttle Controls Cut..");
							}
							if (World.Rnd().nextFloat() < 0.1F) {
								this.FM.AS.setEngineSpecificDamage(
										shot.initiator, 0, 7);
								Aircraft.debugprintln(this,
										"*** Engine Module: Mix Controls Cut..");
							}
						}
					} else if (s.endsWith("oil1")) {
						this.FM.AS.hitOil(shot.initiator, 0);
						Aircraft.debugprintln(this,
								"*** Engine Module: Oil Radiator Hit..");
					}
				}
				if (s.startsWith("xxoil")) {
					this.FM.AS.hitOil(shot.initiator, 0);
					this.getEnergyPastArmor(0.22F, shot);
					Aircraft.debugprintln(this,
							"*** Engine Module: Oil Tank Pierced..");
				}
				if (s.startsWith("xxtank1")
						&& (this.getEnergyPastArmor(0.1F, shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.99F)) {
					if (this.FM.AS.astateTankStates[0] == 0) {
						Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");
						this.FM.AS.hitTank(shot.initiator, 0, 1);
					}
					if ((shot.powerType == 3)
							&& (World.Rnd().nextFloat() < 0.5F)) {
						this.FM.AS.hitTank(shot.initiator, 0, 2);
						Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
					}
				}
				if (s.startsWith("xxmgun")) {
					if (s.endsWith("01")) {
						Aircraft.debugprintln(this,
								"*** Cowling Gun: Disabled..");
						this.FM.AS.setJamBullets(0, 0);
					}
					if (s.endsWith("02")) {
						Aircraft.debugprintln(this,
								"*** Cowling Gun: Disabled..");
						this.FM.AS.setJamBullets(0, 1);
					}
					if (s.endsWith("03")) {
						Aircraft.debugprintln(this,
								"*** Cowling Gun: Disabled..");
						this.FM.AS.setJamBullets(1, 0);
					}
					if (s.endsWith("04")) {
						Aircraft.debugprintln(this,
								"*** Cowling Gun: Disabled..");
						this.FM.AS.setJamBullets(1, 1);
					}
					this.getEnergyPastArmor(
							World.Rnd().nextFloat(0.0F, 28.33F), shot);
				}
			}
		} else if (s.startsWith("xcf") || s.startsWith("xcockpit")) {
			if (this.chunkDamageVisible("CF") < 3) {
				this.hitChunk("CF", shot);
			}
		} else if (s.startsWith("xeng")) {
			if (this.chunkDamageVisible("Engine1") < 2) {
				this.hitChunk("Engine1", shot);
			}
		} else if (s.startsWith("xbay")) {
			if (this.chunkDamageVisible("DinghyBay1") < 2) {
				this.hitChunk("DinghyBay1", shot);
			}
		} else if (s.startsWith("xtail")) {
			if (this.chunkDamageVisible("Tail1") < 3) {
				this.hitChunk("Tail1", shot);
			}
		} else if (s.startsWith("xkeel")) {
			if (this.chunkDamageVisible("Keel1") < 2) {
				this.hitChunk("Keel1", shot);
			}
		} else if (s.startsWith("xrudder")) {
			if (this.chunkDamageVisible("Rudder1") < 1) {
				this.hitChunk("Rudder1", shot);
			}
		} else if (s.startsWith("xstab")) {
			if (s.startsWith("xstabl")
					&& (this.chunkDamageVisible("StabL") < 2)) {
				this.hitChunk("StabL", shot);
			}
			if (s.startsWith("xstabr")
					&& (this.chunkDamageVisible("StabR") < 1)) {
				this.hitChunk("StabR", shot);
			}
		} else if (s.startsWith("xvator")) {
			if (s.startsWith("xvatorl")
					&& (this.chunkDamageVisible("VatorL") < 1)) {
				this.hitChunk("VatorL", shot);
			}
			if (s.startsWith("xvatorr")
					&& (this.chunkDamageVisible("VatorR") < 1)) {
				this.hitChunk("VatorR", shot);
			}
		} else if (s.startsWith("xwing")) {
			if (s.startsWith("xwinglin")
					&& (this.chunkDamageVisible("WingLIn") < 3)) {
				this.hitChunk("WingLIn", shot);
			}
			if (s.startsWith("xwingrin")
					&& (this.chunkDamageVisible("WingRIn") < 3)) {
				this.hitChunk("WingRIn", shot);
			}
			if (s.startsWith("xwinglmid")
					&& (this.chunkDamageVisible("WingLMid") < 3)) {
				this.hitChunk("WingLMid", shot);
			}
			if (s.startsWith("xwingrmid")
					&& (this.chunkDamageVisible("WingRMid") < 3)) {
				this.hitChunk("WingRMid", shot);
			}
			if (s.startsWith("xwinglout")
					&& (this.chunkDamageVisible("WingLOut") < 3)) {
				this.hitChunk("WingLOut", shot);
			}
			if (s.startsWith("xwingrout")
					&& (this.chunkDamageVisible("WingROut") < 3)) {
				this.hitChunk("WingROut", shot);
			}
		} else if (s.startsWith("xarone")) {
			if (s.startsWith("xaronel")
					&& (this.chunkDamageVisible("AroneL") < 1)) {
				this.hitChunk("AroneL", shot);
			}
			if (s.startsWith("xaroner")
					&& (this.chunkDamageVisible("AroneR") < 1)) {
				this.hitChunk("AroneR", shot);
			}
		} else if (s.startsWith("xpilot") || s.startsWith("xhead")) {
			byte byte0 = 0;
			int j;
			if (s.endsWith("a")) {
				byte0 = 1;
				j = s.charAt(6) - 49;
			} else if (s.endsWith("b")) {
				byte0 = 2;
				j = s.charAt(6) - 49;
			} else {
				j = s.charAt(5) - 49;
			}
			this.hitFlesh(j, shot, byte0);
		}
	}

	protected void moveAileron(float f) {
		this.hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneLrod1_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneLrod2_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneLn_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneRrod1_D0", 0.0F, 30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneRrod2_D0", 0.0F, 30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("AroneRn_D0", 0.0F, -30F * f, 0.0F);
	}

	public void moveCockpitDoor(float f) {
		this.resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.6F);
		Aircraft.xyz[2] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.0F);
		this.hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz,
				Aircraft.ypr);
		if (Config.isUSE_RENDER()) {
			if ((Main3D.cur3D().cockpits != null)
					&& (Main3D.cur3D().cockpits[0] != null)) {
				Main3D.cur3D().cockpits[0].onDoorMoved(f);
			}
			this.setDoorSnd(f);
		}
	}

	public void moveSteering(float f) {
		this.hierMesh().chunkSetAngles("GearC2_D0", f, 0.0F, 0.0F);
	}

	public void msgExplosion(Explosion explosion) {
		this.setExplosion(explosion);
		if ((explosion.chunkName != null) && (explosion.power > 0.0F)
				&& explosion.chunkName.startsWith("Tail1")) {
			if (World.Rnd().nextFloat(0.0F, 0.038F) < explosion.power) {
				this.FM.AS.setControlsDamage(explosion.initiator, 1);
			}
			if (World.Rnd().nextFloat(0.0F, 0.042F) < explosion.power) {
				this.FM.AS.setControlsDamage(explosion.initiator, 2);
			}
		}
		super.msgExplosion(explosion);
	}

	protected void nextCUTLevel(String s, int i, Actor actor) {
		super.nextCUTLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			this.bChangedPit = true;
		}
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			this.bChangedPit = true;
		}
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (this.FM.getAltitude() < 3000F) {
			this.hierMesh().chunkVisible("HMask1_D0", false);
		} else {
			this.hierMesh().chunkVisible("HMask1_D0",
					this.hierMesh().isChunkVisible("Head1_D0"));
		}
	}
}
