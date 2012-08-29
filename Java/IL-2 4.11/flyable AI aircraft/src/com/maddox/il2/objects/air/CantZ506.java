// Source File Name: CantZ506.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public abstract class CantZ506 extends Scheme6 {

	public CantZ506() {
		fSightCurAltitude = 300F;
		fSightCurSpeed = 50F;
		fSightCurForwardAngle = 0.0F;
		fSightSetForwardAngle = 0.0F;
		fSightCurSideslip = 0.0F;
		bPitUnfocused = true;
		bPilot1Killed = false;
		bPilot2Killed = false;
		bPilot3Killed = false;
		bPilot4Killed = false;
		bPilot5Killed = false;
		bPilot5KilledInBombPos = false;
		bayDoorAngle = 0.0F;
		bDynamoOperational = true;
		dynamoOrient = 0.0F;
		bDynamoRotary = false;
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		if (super.thisWeaponsName.endsWith("wing")) {
			hierMesh().chunkVisible("250kgWingRackL_D0", true);
			hierMesh().chunkVisible("250kgWingRackR_D0", true);
		}
	}

	public void rareAction(float f, boolean flag) {
		if (hierMesh().isChunkVisible("Prop2_D1")
				&& hierMesh().isChunkVisible("Prop3_D1")
				&& (hierMesh().isChunkVisible("Prop1_D0") || hierMesh()
						.isChunkVisible("PropRot1_D0"))) {
			mydebuggunnery("!!!!!!!!!!!!!!!!!!! HIT PROP 1 !!!!!!!!!!!!!!!!!!!!!");
			hitProp(0, 0, Engine.actorLand());
		}
		if (hierMesh().isChunkVisible("Prop2_D1")
				&& hierMesh().isChunkVisible("Prop1_D1")
				&& (hierMesh().isChunkVisible("Prop3_D0") || hierMesh()
						.isChunkVisible("PropRot3_D0"))) {
			mydebuggunnery("!!!!!!!!!!!!!!!!!!! HIT PROP 3 !!!!!!!!!!!!!!!!!!!!!");
			hitProp(2, 0, Engine.actorLand());
		}
		super.rareAction(f, flag);
		if (flag) {
			if (((FlightModelMain) (super.FM)).AS.astateEngineStates[0] > 3
					&& World.Rnd().nextFloat() < 0.39F)
				((FlightModelMain) (super.FM)).AS.hitTank(this, 0, 1);
			if (((FlightModelMain) (super.FM)).AS.astateEngineStates[1] > 3
					&& World.Rnd().nextFloat() < 0.39F)
				((FlightModelMain) (super.FM)).AS.hitTank(this, 1, 1);
			if (((FlightModelMain) (super.FM)).AS.astateEngineStates[2] > 3
					&& World.Rnd().nextFloat() < 0.39F)
				((FlightModelMain) (super.FM)).AS.hitTank(this, 2, 1);
			if (((FlightModelMain) (super.FM)).AS.astateTankStates[0] > 4
					&& World.Rnd().nextFloat() < 0.1F)
				nextDMGLevel(
						((FlightModelMain) (super.FM)).AS.astateEffectChunks[0]
								+ "0", 0, this);
			if (((FlightModelMain) (super.FM)).AS.astateTankStates[1] > 4
					&& World.Rnd().nextFloat() < 0.1F)
				nextDMGLevel(
						((FlightModelMain) (super.FM)).AS.astateEffectChunks[1]
								+ "0", 0, this);
			if (((FlightModelMain) (super.FM)).AS.astateTankStates[2] > 4
					&& World.Rnd().nextFloat() < 0.1F)
				nextDMGLevel(
						((FlightModelMain) (super.FM)).AS.astateEffectChunks[2]
								+ "0", 0, this);
			if (((FlightModelMain) (super.FM)).AS.astateTankStates[3] > 4
					&& World.Rnd().nextFloat() < 0.1F)
				nextDMGLevel(
						((FlightModelMain) (super.FM)).AS.astateEffectChunks[3]
								+ "0", 0, this);
		}
		War war = War.cur();
		if (war != null)
			;
		Actor actor = War.GetNearestEnemy(this, 16, 6000F);
		if (war != null)
			;
		Aircraft aircraft = War.getNearestEnemy(this, 5000F);
		if ((actor != null && !(actor instanceof BridgeSegment) || aircraft != null)
				&& ((FlightModelMain) (super.FM)).CT.getCockpitDoor() < 0.01F)
			((FlightModelMain) (super.FM)).AS.setCockpitDoor(this, 1);
		for (int i = 1; i <= 5; i++)
			if (super.FM.getAltitude() < 3000F)
				hierMesh().chunkVisible("hmask" + i + "_d0", false);
			else
				hierMesh().chunkVisible("hmask" + i + "_d0",
						hierMesh().isChunkVisible("Pilot" + i + "_D0"));

	}

	public void doWoundPilot(int i, float f) {
		switch (i) {
		case 2: // '\002'
			super.FM.turret[0].setHealth(f);
			break;

		case 3: // '\003'
			super.FM.turret[1].setHealth(f);
			break;

		case 4: // '\004'
			super.FM.turret[2].setHealth(f);
			super.FM.turret[3].setHealth(f);
			break;
		}
	}

	public void doMurderPilot(int i) {
		switch (i) {
		default:
			break;

		case 0: // '\0'
			hierMesh().chunkVisible("Pilot1_D0", false);
			hierMesh().chunkVisible("hmask1_d0", false);
			hierMesh().chunkVisible("Head1_D0", false);
			hierMesh().chunkVisible("Pilot1_D1", true);
			bPilot1Killed = true;
			break;

		case 1: // '\001'
			hierMesh().chunkVisible("Pilot2_D0", false);
			hierMesh().chunkVisible("hmask2_d0", false);
			hierMesh().chunkVisible("Pilot2_D1", true);
			bPilot2Killed = true;
			break;

		case 2: // '\002'
			hierMesh().chunkVisible("Pilot3_D0", false);
			hierMesh().chunkVisible("hmask3_d0", false);
			hierMesh().chunkVisible("Pilot3_D1", true);
			bPilot3Killed = true;
			break;

		case 3: // '\003'
			hierMesh().chunkVisible("Pilot4_D0", false);
			hierMesh().chunkVisible("hmask4_d0", false);
			hierMesh().chunkVisible("Pilot4_D1", true);
			bPilot4Killed = true;
			break;

		case 4: // '\004'
			if (hierMesh().isChunkVisible("Pilot5_D0")) {
				bPilot5KilledInBombPos = false;
				hierMesh().chunkVisible("hmask5_d0", false);
				hierMesh().chunkVisible("Pilot5_D0", false);
				hierMesh().chunkVisible("Pilot5_D1", true);
			}
			if (hierMesh().isChunkVisible("Pilot5bomb_D0")) {
				bPilot5KilledInBombPos = true;
				hierMesh().chunkVisible("Pilot5bomb_D0", false);
				hierMesh().chunkVisible("Pilot5bomb_D1", true);
			}
			bPilot5Killed = true;
			break;
		}
	}

	public void update(float f) {
		hierMesh().chunkSetAngles("Turret1C_D0", 0.0F,
				super.FM.turret[0].tu[1], 0.0F);
		if ((double) bayDoorAngle > 0.5D) {
			if (!bPilot5Killed
					&& !((FlightModelMain) (super.FM)).AS.isPilotParatrooper(4)) {
				hierMesh().chunkVisible("Pilot5_D0", false);
				hierMesh().chunkVisible("Pilot5bomb_D0", true);
				super.FM.turret[2].bIsOperable = false;
				super.FM.turret[3].bIsOperable = false;
			}
		} else if (!bPilot5Killed
				&& !((FlightModelMain) (super.FM)).AS.isPilotParatrooper(4)) {
			hierMesh().chunkVisible("Pilot5_D0", true);
			hierMesh().chunkVisible("Pilot5bomb_D0", false);
			super.FM.turret[2].bIsOperable = true;
			super.FM.turret[3].bIsOperable = true;
		}
		super.update(f);
	}

	protected void moveElevator(float f) {
		if (f < 0.0F) {
			hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 23.5F * f, 0.0F);
			hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 23.5F * f, 0.0F);
		} else {
			hierMesh().chunkSetAngles("VatorL_D0", 0.0F, 15.5F * f, 0.0F);
			hierMesh().chunkSetAngles("VatorR_D0", 0.0F, 15.5F * f, 0.0F);
		}
	}

	protected void moveRudder(float f) {
		hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -23F * f, 0.0F);
		hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -23F * f, 0.0F);
	}

	protected void moveAileron(float f) {
		hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -20F * f, 0.0F);
		hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -20F * f, 0.0F);
	}

	protected void moveFlap(float f) {
		float f1 = -55F * f;
		hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
		hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
	}

	public void moveCockpitDoor(float f) {
		hierMesh().chunkSetAngles("TurDoorL_D0", 0.0F, 70F * f, 0.0F);
		hierMesh().chunkSetAngles("TurDoorR_D0", 0.0F, 70F * f, 0.0F);
		if (Config.isUSE_RENDER()) {
			if (Main3D.cur3D().cockpits != null
					&& Main3D.cur3D().cockpits[0] != null)
				Main3D.cur3D().cockpits[0].onDoorMoved(f);
			setDoorSnd(f);
		}
	}

	public void hitDaSilk() {
		if (((FlightModelMain) (super.FM)).CT.getCockpitDoor() < 0.01F)
			((FlightModelMain) (super.FM)).AS.setCockpitDoor(this, 1);
		super.hitDaSilk();
	}

	protected void mydebuggunnery(String s) {
		System.out.println(s);
	}

	protected void setControlDamage(Shot shot, int i) {
		if (World.Rnd().nextFloat() < 0.002F
				&& getEnergyPastArmor(4.2F, shot) > 0.0F) {
			((FlightModelMain) (super.FM)).AS.setControlsDamage(shot.initiator,
					i);
			mydebuggunnery(i
					+ " Controls Out... //0 = AILERON, 1 = ELEVATOR, 2 = RUDDER");
		}
	}

	protected void hitBone(String s, Shot shot, Point3d point3d) {
		mydebuggunnery("HitBone called! " + s);
		mydebuggunnery("IN: " + shot.power);
		if (s.startsWith("xx")) {
			if (s.startsWith("xxarmor"))
				if (s.endsWith("p1")) {
					if (((Tuple3d) (Aircraft.v1)).z > 0.5D)
						getEnergyPastArmor(4D / ((Tuple3d) (Aircraft.v1)).z,
								shot);
					else
						getEnergyPastArmor((8D / ((Tuple3d) (Aircraft.v1)).x)
								* (double) World.Rnd().nextFloat(1.0F, 1.2F),
								shot);
				} else if (s.endsWith("p2"))
					getEnergyPastArmor(
							4D / Math.abs(((Tuple3d) (Aircraft.v1)).z), shot);
				else if (s.endsWith("p3"))
					getEnergyPastArmor(
							(7D / Math.abs(((Tuple3d) (Aircraft.v1)).x))
									* (double) World.Rnd()
											.nextFloat(1.0F, 1.2F), shot);
				else if (s.endsWith("p4")) {
					if (((Tuple3d) (Aircraft.v1)).x > 0.70710676908493042D)
						getEnergyPastArmor((7D / ((Tuple3d) (Aircraft.v1)).x)
								* (double) World.Rnd().nextFloat(1.0F, 1.2F),
								shot);
					else
						getEnergyPastArmor(5F, shot);
				} else if (s.endsWith("a1") || s.endsWith("a3")
						|| s.endsWith("a4"))
					getEnergyPastArmor(0.6F, shot);
			if (s.startsWith("xxspar")) {
				getEnergyPastArmor(4F, shot);
				if ((s.endsWith("cf1") || s.endsWith("cf2"))
						&& World.Rnd().nextFloat() < 0.1F
						&& chunkDamageVisible("CF") > 1
						&& getEnergyPastArmor(
								15.9F / (float) Math.sqrt(((Tuple3d) (Aircraft.v1)).y
										* ((Tuple3d) (Aircraft.v1)).y
										+ ((Tuple3d) (Aircraft.v1)).z
										* ((Tuple3d) (Aircraft.v1)).z), shot) > 0.0F) {
					mydebuggunnery("*** CF Spars Broken in Half..");
					msgCollision(this, "Tail1_D0", "Tail1_D0");
					msgCollision(this, "WingLIn_D0", "WingLIn_D0");
					msgCollision(this, "WingRIn_D0", "WingRIn_D0");
				}
				if ((s.endsWith("t1") || s.endsWith("t2"))
						&& World.Rnd().nextFloat() < 0.1F
						&& chunkDamageVisible("Tail1") > 1
						&& getEnergyPastArmor(
								15.9F / (float) Math.sqrt(((Tuple3d) (Aircraft.v1)).y
										* ((Tuple3d) (Aircraft.v1)).y
										+ ((Tuple3d) (Aircraft.v1)).z
										* ((Tuple3d) (Aircraft.v1)).z), shot) > 0.0F) {
					mydebuggunnery("*** Tail1 Spars Broken in Half..");
					msgCollision(this, "Tail1_D0", "Tail1_D0");
				}
				if ((s.endsWith("li1") || s.endsWith("li2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingLIn") > 1
						&& getEnergyPastArmor(
								13.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingLIn Spars Damaged..");
					nextDMGLevels(1, 2, "WingLIn_D2", shot.initiator);
				}
				if ((s.endsWith("ri1") || s.endsWith("ri2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.92000001668930054D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingRIn") > 1
						&& getEnergyPastArmor(
								10.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingRIn Spars Damaged..");
					nextDMGLevels(1, 2, "WingRIn_D2", shot.initiator);
				}
				if ((s.endsWith("lm1") || s.endsWith("lm2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingLMid") > 1
						&& getEnergyPastArmor(
								10.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingLMid Spars Damaged..");
					nextDMGLevels(1, 2, "WingLMid_D2", shot.initiator);
				}
				if ((s.endsWith("rm1") || s.endsWith("rm2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.86000001430511475D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingRMid") > 1
						&& getEnergyPastArmor(
								13.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingRMid Spars Damaged..");
					nextDMGLevels(1, 2, "WingRMid_D2", shot.initiator);
				}
				if ((s.endsWith("lo1") || s.endsWith("lo2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingLOut") > 1
						&& getEnergyPastArmor(
								8.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingLOut Spars Damaged..");
					nextDMGLevels(1, 2, "WingLOut_D2", shot.initiator);
				}
				if ((s.endsWith("ro1") || s.endsWith("ro2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingROut") > 1
						&& getEnergyPastArmor(
								8.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** WingROut Spars Damaged..");
					nextDMGLevels(1, 2, "WingROut_D2", shot.initiator);
				}
				if (s.endsWith("e1")
						&& (((Tuple3d) (point3d)).y > 2.79D || ((Tuple3d) (point3d)).y < 2.3199999999999998D)
						&& getEnergyPastArmor(17F, shot) > 0.0F) {
					mydebuggunnery("*** Engine1 Suspension Broken in Half..");
					nextDMGLevels(3, 2, "Engine1_D0", shot.initiator);
				}
				if (s.endsWith("e2")
						&& (((Tuple3d) (point3d)).y < -2.79D || ((Tuple3d) (point3d)).y > -2.3199999999999998D)
						&& getEnergyPastArmor(17F, shot) > 0.0F) {
					mydebuggunnery("*** Engine2 Suspension Broken in Half..");
					nextDMGLevels(3, 2, "Engine2_D0", shot.initiator);
				}
				if (s.endsWith("e3")
						&& (((Tuple3d) (point3d)).y < -2.79D || ((Tuple3d) (point3d)).y > -2.3199999999999998D)
						&& getEnergyPastArmor(17F, shot) > 0.0F) {
					mydebuggunnery("*** Engine3 Suspension Broken in Half..");
					nextDMGLevels(3, 2, "Engine3_D0", shot.initiator);
				}
				if ((s.endsWith("k1") || s.endsWith("k2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& getEnergyPastArmor(
								8.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** Keel spars damaged..");
					nextDMGLevels(1, 2, "Keel1_D0", shot.initiator);
				}
				if ((s.endsWith("sr1") || s.endsWith("sr2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& getEnergyPastArmor(
								8.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** Right Stab spars damaged..");
					nextDMGLevels(1, 2, "StabR_D0", shot.initiator);
				}
				if ((s.endsWith("sl1") || s.endsWith("sl2"))
						&& (double) World.Rnd().nextFloat() < 1.0D - 0.79000002145767212D * Math
								.abs(((Tuple3d) (Aircraft.v1)).x)
						&& chunkDamageVisible("WingROut") > 1
						&& getEnergyPastArmor(
								8.5F * World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F) {
					mydebuggunnery("*** Left Stab spars damaged..");
					nextDMGLevels(1, 2, "StabL_D0", shot.initiator);
				}
			}
			if (s.startsWith("xxbomb")
					&& World.Rnd().nextFloat() < 0.01F
					&& ((FlightModelMain) (super.FM)).CT.Weapons[3] != null
					&& ((FlightModelMain) (super.FM)).CT.Weapons[3][0]
							.haveBullets()) {
				mydebuggunnery("*** Bomb Payload Detonates..");
				((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 0,
						100);
				((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 1,
						100);
				((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 2,
						100);
				((FlightModelMain) (super.FM)).AS.hitTank(shot.initiator, 3,
						100);
				msgCollision(this, "CF_D0", "CF_D0");
			}
			if (s.startsWith("xxprop")) {
				byte byte0 = 0;
				if (s.endsWith("2"))
					byte0 = 1;
				if (s.endsWith("3"))
					byte0 = 2;
				if (getEnergyPastArmor(2.0F, shot) > 0.0F
						&& World.Rnd().nextFloat() < 0.35F) {
					((FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(
							shot.initiator, byte0, 3);
					mydebuggunnery("*** Engine" + (byte0 + 1)
							+ " Governor Failed..");
				}
			}
			if (s.startsWith("xxeng")) {
				byte byte1 = 0;
				if (s.startsWith("xxeng2"))
					byte1 = 1;
				if (s.startsWith("xxeng3"))
					byte1 = 2;
				if (s.endsWith("case")) {
					if (getEnergyPastArmor(0.1F, shot) > 0.0F) {
						if (World.Rnd().nextFloat() < shot.power / 200000F) {
							((FlightModelMain) (super.FM)).AS.setEngineStuck(
									shot.initiator, byte1);
							mydebuggunnery("*** Engine" + (byte1 + 1)
									+ " Crank Case Hit - Engine Stucks..");
						}
						if (World.Rnd().nextFloat() < shot.power / 50000F) {
							((FlightModelMain) (super.FM)).AS.hitEngine(
									shot.initiator, byte1, 2);
							mydebuggunnery("*** Engine" + (byte1 + 1)
									+ " Crank Case Hit - Engine Damaged..");
						}
					}
				} else if (s.endsWith("cyls1") || s.endsWith("cyls2")) {
					if (getEnergyPastArmor(1.3F, shot) > 0.0F
							&& World.Rnd().nextFloat() < ((FlightModelMain) (super.FM)).EI.engines[byte1]
									.getCylindersRatio() * 0.6F) {
						((FlightModelMain) (super.FM)).EI.engines[byte1]
								.setCyliderKnockOut(shot.initiator, World.Rnd()
										.nextInt(1, (int) (shot.power / 4800F)));
						mydebuggunnery("*** Engine"
								+ (byte1 + 1)
								+ " Cylinders Hit, "
								+ ((FlightModelMain) (super.FM)).EI.engines[byte1]
										.getCylindersOperable()
								+ "/"
								+ ((FlightModelMain) (super.FM)).EI.engines[byte1]
										.getCylinders() + " Left..");
						if (((FlightModelMain) (super.FM)).AS.astateEngineStates[byte1] < 1) {
							((FlightModelMain) (super.FM)).AS.hitEngine(
									shot.initiator, byte1, 1);
							((FlightModelMain) (super.FM)).AS.doSetEngineState(
									shot.initiator, byte1, 1);
						}
						if (World.Rnd().nextFloat() < shot.power / 960000F) {
							((FlightModelMain) (super.FM)).AS.hitEngine(
									shot.initiator, byte1, 3);
							mydebuggunnery("*** Engine" + (byte1 + 1)
									+ " Cylinders Hit - Engine Fires..");
						}
						mydebuggunnery("*** Engine"
								+ (byte1 + 1)
								+ " state "
								+ ((FlightModelMain) (super.FM)).AS.astateEngineStates[byte1]);
						getEnergyPastArmor(25F, shot);
					}
				} else if (s.endsWith("supc")
						&& getEnergyPastArmor(0.1F, shot) > 0.0F
						&& World.Rnd().nextFloat() < 0.89F) {
					((FlightModelMain) (super.FM)).AS.setEngineSpecificDamage(
							shot.initiator, byte1, 0);
					mydebuggunnery("*** Engine" + (byte1 + 1)
							+ " Supercharger Out..");
				}
				if (s.endsWith("oil1") || s.endsWith("oil2")
						|| s.endsWith("oil3")) {
					if (getEnergyPastArmor(0.6F, shot) > 0.0F)
						((FlightModelMain) (super.FM)).AS.hitOil(
								shot.initiator, byte1);
					getEnergyPastArmor(0.42F, shot);
				}
				mydebuggunnery("*** Engine"
						+ (byte1 + 1)
						+ " state = "
						+ ((FlightModelMain) (super.FM)).AS.astateEngineStates[byte1]);
			}
			if (s.startsWith("xxtank")) {
				int i = s.charAt(6) - 49;
				if (getEnergyPastArmor(0.9F, shot) > 0.0F)
					if (shot.power < 14100F) {
						if (((FlightModelMain) (super.FM)).AS.astateTankStates[i] < 1)
							((FlightModelMain) (super.FM)).AS.hitTank(
									shot.initiator, i, 1);
						if (((FlightModelMain) (super.FM)).AS.astateTankStates[i] < 4
								&& World.Rnd().nextFloat() < 0.1F)
							((FlightModelMain) (super.FM)).AS.hitTank(
									shot.initiator, i, 1);
						if (shot.powerType == 3
								&& ((FlightModelMain) (super.FM)).AS.astateTankStates[i] > 1
								&& World.Rnd().nextFloat() < 0.07F)
							((FlightModelMain) (super.FM)).AS.hitTank(
									shot.initiator, i, 10);
					} else {
						((FlightModelMain) (super.FM)).AS.hitTank(
								shot.initiator,
								i,
								World.Rnd().nextInt(0,
										(int) (shot.power / 40000F)));
					}
				mydebuggunnery("*** Tank " + (i + 1) + " state = "
						+ ((FlightModelMain) (super.FM)).AS.astateTankStates[i]);
			}
			if (s.startsWith("xxlock")) {
				mydebuggunnery("Lock Construction: Hit..");
				if (s.startsWith("xxlockr")
						&& (s.startsWith("xxlockr1") || s
								.startsWith("xxlockr2"))
						&& getEnergyPastArmor(
								5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F) {
					mydebuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
					nextDMGLevels(3, 2, "Rudder1_D"
							+ chunkDamageVisible("Rudder1"), shot.initiator);
				}
				if (s.startsWith("xxlockvl")
						&& getEnergyPastArmor(
								5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F) {
					mydebuggunnery("Lock Construction: VatorL Lock Shot Off..");
					nextDMGLevels(3, 2, "VatorL_D"
							+ chunkDamageVisible("VatorL"), shot.initiator);
				}
				if (s.startsWith("xxlockvr")
						&& getEnergyPastArmor(
								5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F) {
					mydebuggunnery("Lock Construction: VatorR Lock Shot Off..");
					nextDMGLevels(3, 2, "VatorR_D"
							+ chunkDamageVisible("VatorR"), shot.initiator);
				}
				if (s.startsWith("xxlockal")
						&& getEnergyPastArmor(
								5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F) {
					mydebuggunnery("Lock Construction: AroneL Lock Shot Off..");
					nextDMGLevels(3, 2, "AroneL_D"
							+ chunkDamageVisible("AroneL"), shot.initiator);
				}
				if (s.startsWith("xxlockar")
						&& getEnergyPastArmor(
								5.5F * World.Rnd().nextFloat(1.0F, 1.5F), shot) > 0.0F) {
					debuggunnery("Lock Construction: AroneR Lock Shot Off..");
					nextDMGLevels(3, 2, "AroneR_D"
							+ chunkDamageVisible("AroneR"), shot.initiator);
				}
			}
		}
		if (s.startsWith("xmgun"))
			if (s.endsWith("01")) {
				if (getEnergyPastArmor(5F, shot) > 0.0F
						&& World.Rnd().nextFloat() < 0.75F) {
					((FlightModelMain) (super.FM)).AS.setJamBullets(0, 0);
					getEnergyPastArmor(11.98F, shot);
				}
			} else if (s.endsWith("02")) {
				if (getEnergyPastArmor(4.85F, shot) > 0.0F
						&& World.Rnd().nextFloat() < 0.75F) {
					((FlightModelMain) (super.FM)).AS.setJamBullets(0, 1);
					getEnergyPastArmor(11.98F, shot);
				}
			} else if (s.endsWith("03")) {
				if (getEnergyPastArmor(4.85F, shot) > 0.0F
						&& World.Rnd().nextFloat() < 0.75F) {
					((FlightModelMain) (super.FM)).AS.setJamBullets(0, 2);
					getEnergyPastArmor(11.98F, shot);
				}
			} else if (s.endsWith("04")
					&& getEnergyPastArmor(4.85F, shot) > 0.0F
					&& World.Rnd().nextFloat() < 0.75F) {
				((FlightModelMain) (super.FM)).AS.setJamBullets(0, 3);
				getEnergyPastArmor(11.98F, shot);
			}
		if (s.startsWith("xcf")) {
			setControlDamage(shot, 0);
			setControlDamage(shot, 1);
			setControlDamage(shot, 2);
			if (chunkDamageVisible("CF") < 2)
				hitChunk("CF", shot);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 0x40);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 2);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 1);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 4);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 8);
		} else if (s.startsWith("xtail")) {
			setControlDamage(shot, 1);
			setControlDamage(shot, 2);
			if (chunkDamageVisible("Tail1") < 2)
				hitChunk("Tail1", shot);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 0x10);
			if (World.Rnd().nextFloat() < 0.1F)
				((FlightModelMain) (super.FM)).AS
						.setCockpitState(
								shot.initiator,
								((FlightModelMain) (super.FM)).AS.astateCockpitState | 0x20);
		} else if (s.startsWith("xkeel1")) {
			setControlDamage(shot, 2);
			if (chunkDamageVisible("Keel1") < 2)
				hitChunk("Keel1", shot);
		} else if (s.startsWith("xkeel2")) {
			setControlDamage(shot, 2);
			if (chunkDamageVisible("Keel2") < 2)
				hitChunk("Keel2", shot);
		} else if (s.startsWith("xrudder1")) {
			setControlDamage(shot, 2);
			hitChunk("Rudder1", shot);
		} else if (s.startsWith("xrudder2")) {
			setControlDamage(shot, 2);
			hitChunk("Rudder2", shot);
		} else if (s.startsWith("xstabl"))
			hitChunk("StabL", shot);
		else if (s.startsWith("xstabr"))
			hitChunk("StabR", shot);
		else if (s.startsWith("xvatorl"))
			hitChunk("VatorL", shot);
		else if (s.startsWith("xvatorr"))
			hitChunk("VatorR", shot);
		else if (s.startsWith("xwinglin")) {
			setControlDamage(shot, 0);
			if (chunkDamageVisible("WingLIn") < 2)
				hitChunk("WingLIn", shot);
		} else if (s.startsWith("xwingrin")) {
			setControlDamage(shot, 0);
			if (chunkDamageVisible("WingRIn") < 2)
				hitChunk("WingRIn", shot);
		} else if (s.startsWith("xwinglmid")) {
			setControlDamage(shot, 0);
			if (chunkDamageVisible("WingLMid") < 2)
				hitChunk("WingLMid", shot);
		} else if (s.startsWith("xwingrmid")) {
			setControlDamage(shot, 0);
			if (chunkDamageVisible("WingRMid") < 2)
				hitChunk("WingRMid", shot);
		} else if (s.startsWith("xwinglout")) {
			if (chunkDamageVisible("WingLOut") < 2)
				hitChunk("WingLOut", shot);
		} else if (s.startsWith("xwingrout")) {
			if (chunkDamageVisible("WingROut") < 2)
				hitChunk("WingROut", shot);
		} else if (s.startsWith("xaronel"))
			hitChunk("AroneL", shot);
		else if (s.startsWith("xaroner"))
			hitChunk("AroneR", shot);
		else if (s.startsWith("xengine1")) {
			if (chunkDamageVisible("Engine1") < 2)
				hitChunk("Engine1", shot);
		} else if (s.startsWith("xengine2")) {
			if (chunkDamageVisible("Engine2") < 2)
				hitChunk("Engine2", shot);
		} else if (s.startsWith("xengine3")) {
			if (chunkDamageVisible("Engine3") < 2)
				hitChunk("Engine3", shot);
		} else if (s.startsWith("xgear")) {
			if (World.Rnd().nextFloat() < 0.1F) {
				mydebuggunnery("*** Gear Hydro Failed..");
				((FlightModelMain) (super.FM)).Gears.setHydroOperable(false);
			}
		} else if (s.startsWith("xturret")) {
			if (s.startsWith("xturret1"))
				((FlightModelMain) (super.FM)).AS.setJamBullets(10, 0);
			if (s.startsWith("xturret2"))
				((FlightModelMain) (super.FM)).AS.setJamBullets(11, 0);
			if (s.startsWith("xturret3"))
				((FlightModelMain) (super.FM)).AS.setJamBullets(12, 0);
			if (s.startsWith("xturret4"))
				((FlightModelMain) (super.FM)).AS.setJamBullets(13, 0);
			if (s.startsWith("xturret5"))
				((FlightModelMain) (super.FM)).AS.setJamBullets(14, 0);
		} else if (s.startsWith("xpilot") || s.startsWith("xhead")) {
			byte byte2 = 0;
			int j;
			if (s.endsWith("a") || s.endsWith("abomb")) {
				byte2 = 1;
				j = s.charAt(6) - 49;
			} else if (s.endsWith("b") || s.endsWith("bbomb")) {
				byte2 = 2;
				j = s.charAt(6) - 49;
			} else {
				j = s.charAt(5) - 49;
			}
			mydebuggunnery("call HitFlesh:  " + j + " " + byte2 + " "
					+ shot.power);
			hitFlesh(j, shot, byte2);
		}
		mydebuggunnery("out:  " + shot.power);
	}

	protected void moveFan(float f) {
		if (bDynamoOperational) {
			pk = Math
					.abs((int) (((FlightModelMain) (super.FM)).Vwld.length() / 14D));
			if (pk >= 1)
				pk = 1;
		}
		if (bDynamoRotary != (pk == 1)) {
			bDynamoRotary = pk == 1;
			hierMesh().chunkVisible("Dina_D0", !bDynamoRotary);
			hierMesh().chunkVisible("PropDina_D0", bDynamoRotary);
		}
		dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F
				: (float) ((double) dynamoOrient - ((FlightModelMain) (super.FM)).Vwld
						.length() * 1.5444015264511108D) % 360F;
		hierMesh().chunkSetAngles("Dina_D0", 0.0F, dynamoOrient, 0.0F);
		super.moveFan(f);
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		if (i == 19)
			((FlightModelMain) (super.FM)).Gears.hitCentreGear();
		return super.cutFM(i, j, actor);
	}

	protected void moveBayDoor(float f) {
		hierMesh().chunkSetAngles("DoorL_D0", 0.0F, 70F * f, 0.0F);
		hierMesh().chunkSetAngles("DoorR_D0", 0.0F, 70F * f, 0.0F);
		resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -0.26F);
		hierMesh().chunkSetLocate("LegFairing_D0", Aircraft.xyz, Aircraft.ypr);
		bayDoorAngle = f;
	}

	public float fSightCurAltitude;
	public float fSightCurSpeed;
	public float fSightCurForwardAngle;
	public float fSightSetForwardAngle;
	public float fSightCurSideslip;
	public boolean bPitUnfocused;
	public boolean bPilot1Killed;
	public boolean bPilot2Killed;
	public boolean bPilot3Killed;
	public boolean bPilot4Killed;
	public boolean bPilot5Killed;
	public boolean bPilot5KilledInBombPos;
	public float bayDoorAngle;
	private boolean bDynamoOperational;
	private float dynamoOrient;
	private boolean bDynamoRotary;
	private int pk;

	static {
		Property.set(CLASS.THIS(), "originCountry", PaintScheme.countryItaly);
	}
}
