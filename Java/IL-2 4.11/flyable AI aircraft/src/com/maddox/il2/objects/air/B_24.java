// Source File Name: B_24.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-04
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public abstract class B_24 extends Scheme4 implements TypeTransport {

	public static void moveGear(HierMesh hiermesh, float f) {
		hiermesh.chunkSetAngles("GearC2_D0", 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -55F), 0.0F);
		hiermesh.chunkSetAngles("GearC10_D0", 0.0F,
				Aircraft.cvt(f, 0.05F, 0.75F, 0.0F, -60F), 0.0F);
		hiermesh.chunkSetAngles("GearC8_D0", 0.0F,
				Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, -140F), 0.0F);
		hiermesh.chunkSetAngles("GearC9_D0", 0.0F,
				Aircraft.cvt(f, 0.01F, 0.1F, 0.0F, -140F), 0.0F);
		hiermesh.chunkSetAngles("GearL2_D0", 0.0F,
				Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -95F), 0.0F);
		hiermesh.chunkSetAngles("GearL4_D0", 0.0F,
				Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -30F), 0.0F);
		hiermesh.chunkSetAngles("GearL5_D0", 0.0F,
				Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, 180F), 0.0F);
		hiermesh.chunkSetAngles("GearL6_D0", 0.0F,
				Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -98F), 0.0F);
		hiermesh.chunkSetAngles("GearL7_D0", 0.0F,
				Aircraft.cvt(f, 0.12F, 0.99F, 0.0F, -20F), 0.0F);
		hiermesh.chunkSetAngles("GearR2_D0", 0.0F,
				Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -95F), 0.0F);
		hiermesh.chunkSetAngles("GearR4_D0", 0.0F,
				Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -30F), 0.0F);
		hiermesh.chunkSetAngles("GearR5_D0", 0.0F,
				Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, 180F), 0.0F);
		hiermesh.chunkSetAngles("GearR6_D0", 0.0F,
				Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -98F), 0.0F);
		hiermesh.chunkSetAngles("GearR7_D0", 0.0F,
				Aircraft.cvt(f, 0.02F, 0.82F, 0.0F, -20F), 0.0F);
	}

	private float fCSink;

	private float fCSteer;

	private float flapps[] = { 0.0F, 0.0F, 0.0F, 0.0F };

	private float fGunPos;

	private int iGunPos;

	private long btme;

	static {
		Class class1 = B_24.class;
		Property.set(class1, "originCountry", PaintScheme.countryUSA);
	}

	public B_24() {
		this.fCSink = 0.0F;
		this.fCSteer = 0.0F;
		this.fGunPos = 1.0F;
		this.iGunPos = 1;
		this.btme = -1L;
	}

	protected boolean cutFM(int i, int j, Actor actor) {
		switch (i) {
		case 33: // '!'
			this.hitProp(1, j, actor);
			this.FM.EI.engines[1].setEngineStuck(actor);
			this.FM.AS.hitTank(actor, 1, World.Rnd().nextInt(0, 9));
			// fall through

		case 34: // '"'
			this.hitProp(0, j, actor);
			this.FM.EI.engines[0].setEngineStuck(actor);
			this.FM.AS.hitTank(actor, 0, World.Rnd().nextInt(2, 8));
			this.FM.AS.hitTank(actor, 1, World.Rnd().nextInt(0, 5));
			// fall through

		case 35: // '#'
			this.FM.AS.hitTank(actor, 0, World.Rnd().nextInt(0, 4));
			break;

		case 36: // '$'
			this.hitProp(2, j, actor);
			this.FM.EI.engines[2].setEngineStuck(actor);
			this.FM.AS.hitTank(actor, 2, World.Rnd().nextInt(0, 9));
			// fall through

		case 37: // '%'
			this.hitProp(3, j, actor);
			this.FM.EI.engines[3].setEngineStuck(actor);
			this.FM.AS.hitTank(actor, 2, World.Rnd().nextInt(0, 5));
			this.FM.AS.hitTank(actor, 3, World.Rnd().nextInt(2, 8));
			// fall through

		case 38: // '&'
			this.FM.AS.hitTank(actor, 3, World.Rnd().nextInt(0, 4));
			break;

		case 25: // '\031'
			this.FM.turret[0].bIsOperable = false;
			return false;

		case 26: // '\032'
			this.FM.turret[1].bIsOperable = false;
			return false;

		case 27: // '\033'
			this.FM.turret[2].bIsOperable = false;
			return false;

		case 28: // '\034'
			this.FM.turret[3].bIsOperable = false;
			return false;

		case 29: // '\035'
			this.FM.turret[4].bIsOperable = false;
			return false;

		case 30: // '\036'
			this.FM.turret[5].bIsOperable = false;
			return false;

		case 19: // '\023'
			this.killPilot(this, 5);
			this.killPilot(this, 6);
			this.killPilot(this, 7);
			this.killPilot(this, 8);
			this.cut("StabL");
			this.cut("StabR");
			break;

		case 13: // '\r'
			this.killPilot(this, 0);
			this.killPilot(this, 1);
			this.killPilot(this, 2);
			this.killPilot(this, 3);
			break;

		case 17: // '\021'
			this.cut("Keel1");
			this.hierMesh().chunkVisible("Keel1_CAP", false);
			break;

		case 18: // '\022'
			this.cut("Keel2");
			this.hierMesh().chunkVisible("Keel2_CAP", false);
			break;
		}
		return super.cutFM(i, j, actor);
	}

	public void doMurderPilot(int i) {
		this.hierMesh().chunkVisible("Pilot" + (i + 1) + "_D0", false);
		this.hierMesh().chunkVisible("HMask" + (i + 1) + "_D0", false);
		this.hierMesh().chunkVisible("Pilot" + (i + 1) + "_D1", true);
		this.hierMesh().chunkVisible("Head" + (i + 1) + "_D0", false);
	}

	public void doWoundPilot(int i, float f) {
		switch (i) {
		case 2: // '\002'
			this.FM.turret[0].setHealth(f);
			break;

		case 4: // '\004'
			this.FM.turret[1].setHealth(f);
			break;

		case 5: // '\005'
			this.FM.turret[2].setHealth(f);
			break;

		case 6: // '\006'
			this.FM.turret[3].setHealth(f);
			break;

		case 7: // '\007'
			this.FM.turret[4].setHealth(f);
			break;

		case 8: // '\b'
			this.FM.turret[5].setHealth(f);
			break;
		}
	}

	protected void hitBone(String s, Shot shot, Point3d point3d) {
		int i = 0;
		if (s.startsWith("xx")) {
			if (s.startsWith("xxammo")) {
				int j = s.charAt(6) - 48;
				if (s.length() > 7) {
					j = 10;
				}
				if ((this.getEnergyPastArmor(6.87F, shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.05F)) {
					switch (j) {
					case 1: // '\001'
						j = 10;
						i = 0;
						break;

					case 2: // '\002'
						j = 10;
						i = 1;
						break;

					case 3: // '\003'
						j = 11;
						i = 0;
						break;

					case 4: // '\004'
						j = 11;
						i = 1;
						break;

					case 5: // '\005'
						j = 12;
						i = 0;
						break;

					case 6: // '\006'
						j = 12;
						i = 1;
						break;

					case 7: // '\007'
						j = 13;
						i = 0;
						break;

					case 8: // '\b'
						j = 14;
						i = 0;
						break;

					case 9: // '\t'
						j = 15;
						i = 0;
						break;

					case 10: // '\n'
						j = 15;
						i = 1;
						break;
					}
					this.FM.AS.setJamBullets(j, i);
					return;
				}
			}
			if (s.startsWith("xxcontrols")) {
				int k = s.charAt(10) - 48;
				switch (k) {
				default:
					break;

				case 1: // '\001'
				case 2: // '\002'
					if ((this.getEnergyPastArmor(1.0F, shot) > 0.0F)
							&& (World.Rnd().nextFloat() < 0.5F)) {
						this.FM.AS.setControlsDamage(shot.initiator, 0);
						Aircraft.debugprintln(this,
								"*** Aileron Controls Out..");
					}
					break;

				case 3: // '\003'
					if ((World.Rnd().nextFloat() < 0.125F)
							&& (this.getEnergyPastArmor(5.2F, shot) > 0.0F)) {
						Aircraft.debugprintln(this,
								"*** Control Column: Hit, Controls Destroyed..");
						this.FM.AS.setControlsDamage(shot.initiator, 2);
						this.FM.AS.setControlsDamage(shot.initiator, 1);
						this.FM.AS.setControlsDamage(shot.initiator, 0);
					}
					this.getEnergyPastArmor(2.0F, shot);
					break;

				case 4: // '\004'
				case 5: // '\005'
				case 6: // '\006'
					if ((World.Rnd().nextFloat() < 0.252F)
							&& (this.getEnergyPastArmor(5.2F, shot) > 0.0F)) {
						if (World.Rnd().nextFloat() < 0.125F) {
							this.FM.AS.setControlsDamage(shot.initiator, 2);
						}
						if (World.Rnd().nextFloat() < 0.125F) {
							this.FM.AS.setControlsDamage(shot.initiator, 1);
						}
					}
					this.getEnergyPastArmor(2.0F, shot);
					break;
				}
				return;
			}
			if (s.startsWith("xxeng")) {
				int l = s.charAt(5) - 49;
				if (s.endsWith("case")) {
					if (this.getEnergyPastArmor(0.2F, shot) > 0.0F) {
						if (World.Rnd().nextFloat() < (shot.power / 140000F)) {
							this.FM.AS.setEngineStuck(shot.initiator, l);
							Aircraft.debugprintln(this, "*** Engine (" + l
									+ ") Crank Case Hit - Engine Stucks..");
						}
						if (World.Rnd().nextFloat() < (shot.power / 85000F)) {
							this.FM.AS.hitEngine(shot.initiator, l, 2);
							Aircraft.debugprintln(this, "*** Engine (" + l
									+ ") Crank Case Hit - Engine Damaged..");
						}
					} else if (World.Rnd().nextFloat() < 0.005F) {
						this.FM.EI.engines[l].setCyliderKnockOut(
								shot.initiator, 1);
					} else {
						this.FM.EI.engines[l]
								.setReadyness(
										shot.initiator,
										this.FM.EI.engines[l].getReadyness() - 0.00082F);
						Aircraft.debugprintln(this, "*** Engine (" + l
								+ ") Crank Case Hit - Readyness Reduced to "
								+ this.FM.EI.engines[l].getReadyness() + "..");
					}
					this.getEnergyPastArmor(12F, shot);
				}
				if (s.endsWith("cyls")) {
					if ((this.getEnergyPastArmor(5.85F, shot) > 0.0F)
							&& (World.Rnd().nextFloat() < (this.FM.EI.engines[l]
									.getCylindersRatio() * 0.75F))) {
						this.FM.EI.engines[l].setCyliderKnockOut(
								shot.initiator,
								World.Rnd().nextInt(1,
										(int) (shot.power / 19000F)));
						Aircraft.debugprintln(
								this,
								"*** Engine ("
										+ l
										+ ") Cylinders Hit, "
										+ this.FM.EI.engines[l]
												.getCylindersOperable() + "/"
										+ this.FM.EI.engines[l].getCylinders()
										+ " Left..");
						if (World.Rnd().nextFloat() < (shot.power / 18000F)) {
							this.FM.AS.hitEngine(shot.initiator, l, 2);
							Aircraft.debugprintln(this, "*** Engine (" + l
									+ ") Cylinders Hit - Engine Fires..");
						}
					}
					this.getEnergyPastArmor(25F, shot);
				}
				if (s.endsWith("mag1")) {
					this.FM.EI.engines[l].setMagnetoKnockOut(shot.initiator, 0);
					Aircraft.debugprintln(this, "*** Engine (" + l
							+ ") Module: Magneto #0 Destroyed..");
					this.getEnergyPastArmor(25F, shot);
				}
				if (s.endsWith("mag2")) {
					this.FM.EI.engines[l].setMagnetoKnockOut(shot.initiator, 1);
					Aircraft.debugprintln(this, "*** Engine (" + l
							+ ") Module: Magneto #1 Destroyed..");
					this.getEnergyPastArmor(25F, shot);
				}
				if (s.endsWith("oil1")
						&& (this.getEnergyPastArmor(0.2F, shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.5F)) {
					this.FM.AS.setOilState(shot.initiator, l, 1);
					Aircraft.debugprintln(this, "*** Engine (" + l
							+ ") Module: Oil Filter Pierced..");
				}
				return;
			}
			if (s.startsWith("xxlock")) {
				this.debuggunnery("Lock Construction: Hit..");
				if (s.startsWith("xxlockr1")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: Rudder1 Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"Rudder1_D" + this.chunkDamageVisible("Rudder1"),
							shot.initiator);
				}
				if (s.startsWith("xxlockr2")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: Rudder2 Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"Rudder2_D" + this.chunkDamageVisible("Rudder2"),
							shot.initiator);
				}
				if (s.startsWith("xxlockvl")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: VatorL Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"VatorL_D" + this.chunkDamageVisible("VatorL"),
							shot.initiator);
				}
				if (s.startsWith("xxlockvr")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: VatorR Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"VatorR_D" + this.chunkDamageVisible("VatorR"),
							shot.initiator);
				}
				if (s.startsWith("xxlockal")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: AroneL Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"AroneL_D" + this.chunkDamageVisible("AroneL"),
							shot.initiator);
				}
				if (s.startsWith("xxlockar")
						&& (this.getEnergyPastArmor(6.5F * World.Rnd()
								.nextFloat(1.0F, 1.5F), shot) > 0.0F)) {
					this.debuggunnery("Lock Construction: AroneR Lock Shot Off..");
					this.nextDMGLevels(3, 2,
							"AroneR_D" + this.chunkDamageVisible("AroneR"),
							shot.initiator);
				}
				return;
			}
			if (s.startsWith("xxoil")) {
				int i1 = s.charAt(5) - 49;
				if ((this.getEnergyPastArmor(0.21F, shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.2435F)) {
					this.FM.AS.hitOil(shot.initiator, i1);
				}
				Aircraft.debugprintln(this, "*** Engine (" + i1
						+ ") Module: Oil Tank Pierced..");
				return;
			}
			if (s.startsWith("xxpnm")) {
				if (this.getEnergyPastArmor(
						World.Rnd().nextFloat(0.25F, 1.22F), shot) > 0.0F) {
					this.debuggunnery("Pneumo System: Disabled..");
					this.FM.AS.setInternalDamage(shot.initiator, 1);
				}
				return;
			}
			if (s.startsWith("xxradio")) {
				this.getEnergyPastArmor(World.Rnd().nextFloat(5F, 25F), shot);
				return;
			}
			if (s.startsWith("xxspar")) {
				if (s.startsWith("xxsparli")
						&& (this.chunkDamageVisible("WingLIn") > 2)
						&& (this.getEnergyPastArmor(19.6F * World.Rnd()
								.nextFloat(1.0F, 3F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingLIn Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
				}
				if (s.startsWith("xxsparri")
						&& (this.chunkDamageVisible("WingRIn") > 2)
						&& (this.getEnergyPastArmor(19.6F * World.Rnd()
								.nextFloat(1.0F, 3F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingRIn Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
				}
				if (s.startsWith("xxsparlm")
						&& (this.chunkDamageVisible("WingLMid") > 2)
						&& (this.getEnergyPastArmor(16.8F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingLMid Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
				}
				if (s.startsWith("xxsparrm")
						&& (this.chunkDamageVisible("WingRMid") > 2)
						&& (this.getEnergyPastArmor(16.8F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingRMid Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
				}
				if (s.startsWith("xxsparlo")
						&& (this.chunkDamageVisible("WingLOut") > 2)
						&& (this.getEnergyPastArmor(16.6F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingLOut Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
				}
				if (s.startsWith("xxsparro")
						&& (this.chunkDamageVisible("WingROut") > 2)
						&& (this.getEnergyPastArmor(16.6F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)
						&& (World.Rnd().nextFloat() < 0.125F)) {
					Aircraft.debugprintln(this, "*** WingROut Spars Damaged..");
					this.nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
				}
				if (s.startsWith("xxspark1")
						&& (this.chunkDamageVisible("Keel1") > 1)
						&& (this.getEnergyPastArmor(16.6F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)) {
					Aircraft.debugprintln(this, "*** Keel1 Spars Damaged..");
					this.nextDMGLevels(1, 2,
							"Keel1_D" + this.chunkDamageVisible("Keel1"),
							shot.initiator);
				}
				if (s.startsWith("xxspark2")
						&& (this.chunkDamageVisible("Keel2") > 1)
						&& (this.getEnergyPastArmor(16.6F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)) {
					Aircraft.debugprintln(this, "*** Keel2 Spars Damaged..");
					this.nextDMGLevels(1, 2,
							"Keel2_D" + this.chunkDamageVisible("Keel2"),
							shot.initiator);
				}
				if (s.startsWith("xxsparsl")
						&& (this.chunkDamageVisible("StabL") > 1)
						&& (this.getEnergyPastArmor(11.2F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)) {
					this.debuggunnery("*** StabL Spars Damaged..");
					this.nextDMGLevels(1, 2, "StabL_D2", shot.initiator);
				}
				if (s.startsWith("xxsparsr")
						&& (this.chunkDamageVisible("StabR") > 1)
						&& (this.getEnergyPastArmor(11.2F * World.Rnd()
								.nextFloat(1.0F, 2.0F), shot) > 0.0F)) {
					this.debuggunnery("*** StabR Spars Damaged..");
					this.nextDMGLevels(1, 2, "StabR_D2", shot.initiator);
				}
				return;
			}
			if (s.startsWith("xxtank")) {
				int j1 = s.charAt(6) - 49;
				if (this.getEnergyPastArmor(0.06F, shot) > 0.0F) {
					if (this.FM.AS.astateTankStates[j1] == 0) {
						this.FM.AS.hitTank(shot.initiator, j1, 1);
						this.FM.AS.doSetTankState(shot.initiator, j1, 1);
					}
					if (shot.powerType == 3) {
						if (shot.power < 16100F) {
							if ((this.FM.AS.astateTankStates[j1] < 4)
									&& (World.Rnd().nextFloat() < 0.21F)) {
								this.FM.AS.hitTank(shot.initiator, j1, 1);
							}
						} else {
							this.FM.AS.hitTank(
									shot.initiator,
									j1,
									World.Rnd().nextInt(1,
											1 + (int) (shot.power / 16100F)));
						}
					} else if (shot.power > 16100F) {
						this.FM.AS.hitTank(shot.initiator, j1, World.Rnd()
								.nextInt(1, 1 + (int) (shot.power / 16100F)));
					}
				}
				return;
			} else {
				return;
			}
		}
		if (s.startsWith("xcf")) {
			if (this.chunkDamageVisible("CF") < 3) {
				this.hitChunk("CF", shot);
			}
			if (World.Rnd().nextFloat() < 0.0575F) {
				if (point3d.y > 0.0D) {
					this.FM.AS.setCockpitState(shot.initiator,
							this.FM.AS.astateCockpitState | 8);
				} else {
					this.FM.AS.setCockpitState(shot.initiator,
							this.FM.AS.astateCockpitState | 0x20);
				}
			}
			if (point3d.x > 1.726D) {
				if (point3d.z > 0.44400000000000001D) {
					this.FM.AS.setCockpitState(shot.initiator,
							this.FM.AS.astateCockpitState | 1);
				}
				if ((point3d.z > -0.28100000000000003D)
						&& (point3d.z < 0.44400000000000001D)) {
					if (point3d.y > 0.0D) {
						this.FM.AS.setCockpitState(shot.initiator,
								this.FM.AS.astateCockpitState | 4);
					} else {
						this.FM.AS.setCockpitState(shot.initiator,
								this.FM.AS.astateCockpitState | 0x10);
					}
				}
				if ((point3d.x > 2.774D) && (point3d.x < 3.718D)
						&& (point3d.z > 0.42499999999999999D)) {
					this.FM.AS.setCockpitState(shot.initiator,
							this.FM.AS.astateCockpitState | 2);
				}
				if (World.Rnd().nextFloat() < 0.12F) {
					this.FM.AS.setCockpitState(shot.initiator,
							this.FM.AS.astateCockpitState | 0x40);
				}
			}
		} else if (s.startsWith("xtail")) {
			if (this.chunkDamageVisible("Tail1") < 3) {
				this.hitChunk("Tail1", shot);
			}
			return;
		}
		if (s.startsWith("xkeel1")) {
			if (this.chunkDamageVisible("Keel1") < 2) {
				this.hitChunk("Keel1", shot);
			}
			return;
		}
		if (s.startsWith("xkeel2")) {
			if (this.chunkDamageVisible("Keel2") < 2) {
				this.hitChunk("Keel2", shot);
			}
			return;
		}
		if (s.startsWith("xrudder1")) {
			if (this.chunkDamageVisible("Rudder1") < 1) {
				this.hitChunk("Rudder1", shot);
			}
			return;
		}
		if (s.startsWith("xrudder2")) {
			if (this.chunkDamageVisible("Rudder2") < 1) {
				this.hitChunk("Rudder2", shot);
			}
			return;
		}
		if (s.startsWith("xstabl")) {
			if (this.chunkDamageVisible("StabL") < 2) {
				this.hitChunk("StabL", shot);
			}
			return;
		}
		if (s.startsWith("xstabr")) {
			if (this.chunkDamageVisible("StabR") < 2) {
				this.hitChunk("StabR", shot);
			}
			return;
		}
		if (s.startsWith("xwinglin")) {
			if (this.chunkDamageVisible("WingLIn") < 3) {
				this.hitChunk("WingLIn", shot);
			}
			return;
		}
		if (s.startsWith("xwingrin")) {
			if (this.chunkDamageVisible("WingRIn") < 3) {
				this.hitChunk("WingRIn", shot);
			}
			return;
		}
		if (s.startsWith("xwinglmid")) {
			if (this.chunkDamageVisible("WingLMid") < 3) {
				this.hitChunk("WingLMid", shot);
			}
			return;
		}
		if (s.startsWith("xwingrmid")) {
			if (this.chunkDamageVisible("WingRMid") < 3) {
				this.hitChunk("WingRMid", shot);
			}
			return;
		}
		if (s.startsWith("xwinglout")) {
			if (this.chunkDamageVisible("WingLOut") < 3) {
				this.hitChunk("WingLOut", shot);
			}
			return;
		}
		if (s.startsWith("xwingrout")) {
			if (this.chunkDamageVisible("WingROut") < 3) {
				this.hitChunk("WingROut", shot);
			}
			return;
		}
		if (s.startsWith("xaronel")) {
			if (this.chunkDamageVisible("AroneL") < 1) {
				this.hitChunk("AroneL", shot);
			}
			return;
		}
		if (s.startsWith("xaroner")) {
			if (this.chunkDamageVisible("AroneR") < 1) {
				this.hitChunk("AroneR", shot);
			}
			return;
		}
		if (s.startsWith("xengine1")) {
			if (this.chunkDamageVisible("Engine1") < 2) {
				this.hitChunk("Engine1", shot);
			}
			return;
		}
		if (s.startsWith("xengine2")) {
			if (this.chunkDamageVisible("Engine2") < 2) {
				this.hitChunk("Engine2", shot);
			}
			return;
		}
		if (s.startsWith("xengine3")) {
			if (this.chunkDamageVisible("Engine3") < 2) {
				this.hitChunk("Engine3", shot);
			}
			return;
		}
		if (s.startsWith("xengine4")) {
			if (this.chunkDamageVisible("Engine4") < 2) {
				this.hitChunk("Engine4", shot);
			}
			return;
		}
		if (s.startsWith("xgear")) {
			if (World.Rnd().nextFloat() < 0.05F) {
				Aircraft.debugprintln(this, "*** Gear Hydro Failed..");
				this.FM.Gears.setHydroOperable(false);
			}
			return;
		}
		if (s.startsWith("xturret")) {
			return;
		}
		if (s.startsWith("xmgun")) {
			int k1 = (10 * (s.charAt(5) - 48)) + (s.charAt(6) - 48);
			if ((this.getEnergyPastArmor(6.45F, shot) > 0.0F)
					&& (World.Rnd().nextFloat() < 0.35F)) {
				switch (k1) {
				case 1: // '\001'
					k1 = 10;
					i = 0;
					break;

				case 2: // '\002'
					k1 = 10;
					i = 1;
					break;

				case 3: // '\003'
					k1 = 11;
					i = 0;
					break;

				case 4: // '\004'
					k1 = 11;
					i = 1;
					break;

				case 5: // '\005'
					k1 = 12;
					i = 0;
					break;

				case 6: // '\006'
					k1 = 12;
					i = 1;
					break;

				case 7: // '\007'
					k1 = 13;
					i = 0;
					break;

				case 8: // '\b'
					k1 = 14;
					i = 0;
					break;

				case 9: // '\t'
					k1 = 15;
					i = 0;
					break;

				case 10: // '\n'
					k1 = 15;
					i = 1;
					break;
				}
				this.FM.AS.setJamBullets(k1, i);
			}
			return;
		}
		if (s.startsWith("xpilot") || s.startsWith("xhead")) {
			byte byte0 = 0;
			int l1;
			if (s.endsWith("a")) {
				byte0 = 1;
				l1 = s.charAt(6) - 49;
			} else if (s.endsWith("b")) {
				byte0 = 2;
				l1 = s.charAt(6) - 49;
			} else {
				l1 = s.charAt(5) - 49;
			}
			this.hitFlesh(l1, shot, byte0);
			return;
		} else {
			return;
		}
	}

	protected void moveBayDoor(float f) {
		for (int i = 1; i < 10; i++) {
			this.hierMesh().chunkSetAngles("Bay0" + i + "_D0", 0.0F, -30F * f,
					0.0F);
		}

		for (int j = 10; j < 13; j++) {
			this.hierMesh().chunkSetAngles("Bay" + j + "_D0", 0.0F, -30F * f,
					0.0F);
		}

	}

	protected void moveFlap(float f) {
		float f1 = -35.5F * f;
		this.hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
		this.hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
	}

	protected void moveGear(float f) {
		moveGear(this.hierMesh(), f);
	}

	protected void moveRudder(float f) {
		this.hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
		this.hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30F * f, 0.0F);
		this.fCSteer = 22.2F * f;
	}

	public void moveWheelSink() {
		this.fCSink = Aircraft.cvt(this.FM.Gears.gWheelSinking[2], 0.0F, 0.5F,
				0.0F, 0.5F);
		this.resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F,
				0.456F, 0.0F, 0.2821F);
		this.hierMesh().chunkSetLocate("GearL3_D0", Aircraft.xyz, Aircraft.ypr);
		this.resetYPRmodifier();
		Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F,
				0.456F, 0.0F, 0.2821F);
		this.hierMesh().chunkSetLocate("GearR3_D0", Aircraft.xyz, Aircraft.ypr);
	}

	public void msgExplosion(Explosion explosion) {
		this.setExplosion(explosion);
		if ((explosion.chunkName != null) && (explosion.power > 0.0F)) {
			if (explosion.chunkName.equals("Tail1_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingLIn_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingRIn_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingLMid_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingRMid_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingLOut_D3")) {
				return;
			}
			if (explosion.chunkName.equals("WingROut_D3")) {
				return;
			}
		}
		super.msgExplosion(explosion);
	}

	public void rareAction(float f, boolean flag) {
		super.rareAction(f, flag);
		if (flag) {
			if ((this.FM.AS.astateTankStates[0] > 4)
					&& (World.Rnd().nextFloat() < 0.04F)) {
				this.nextDMGLevel(this.FM.AS.astateEffectChunks[0] + "0", 0,
						this);
			}
			if ((this.FM.AS.astateTankStates[1] > 4)
					&& (World.Rnd().nextFloat() < 0.04F)) {
				this.nextDMGLevel(this.FM.AS.astateEffectChunks[1] + "0", 0,
						this);
			}
			if ((this.FM.AS.astateTankStates[2] > 4)
					&& (World.Rnd().nextFloat() < 0.04F)) {
				this.nextDMGLevel(this.FM.AS.astateEffectChunks[2] + "0", 0,
						this);
			}
			if ((this.FM.AS.astateTankStates[3] > 4)
					&& (World.Rnd().nextFloat() < 0.04F)) {
				this.nextDMGLevel(this.FM.AS.astateEffectChunks[3] + "0", 0,
						this);
			}
			if (!(this.FM instanceof RealFlightModel)
					|| !((RealFlightModel) this.FM).isRealMode()) {
				for (int i = 0; i < this.FM.EI.getNum(); i++) {
					if ((this.FM.AS.astateEngineStates[i] > 3)
							&& (World.Rnd().nextFloat() < 0.2F)) {
						this.FM.EI.engines[i].setExtinguisherFire();
					}
				}

			}
		}
		for (int j = 1; j < 10; j++) {
			if (this.FM.getAltitude() < 3000F) {
				this.hierMesh().chunkVisible("HMask" + j + "_D0", false);
			} else {
				this.hierMesh().chunkVisible("HMask" + j + "_D0",
						this.hierMesh().isChunkVisible("Pilot" + j + "_D0"));
			}
		}

	}

	public void update(float f) {
		super.update(f);
		for (int i = 0; i < 4; i++) {
			float f1 = this.FM.EI.engines[i].getControlRadiator();
			if (Math.abs(this.flapps[i] - f1) > 0.01F) {
				this.flapps[i] = f1;
				for (int j = 1; j < 9; j++) {
					this.hierMesh().chunkSetAngles(
							"Water" + ((i * 8) + j) + "_D0", 0.0F, -20F * f1,
							0.0F);
				}

			}
		}

		if (this.FM.CT.getGear() > 0.9F) {
			this.resetYPRmodifier();
			Aircraft.xyz[1] = this.fCSink;
			Aircraft.ypr[1] = this.fCSteer;
			this.hierMesh().chunkSetLocate("GearC3_D0", Aircraft.xyz,
					Aircraft.ypr);
		}
		if (this.iGunPos == 0) {
			if (this.fGunPos > 0.0F) {
				this.fGunPos -= 0.2F * f;
				this.resetYPRmodifier();
				Aircraft.xyz[1] = -0.635F + (0.635F * this.fGunPos);
				this.hierMesh().chunkSetLocate("Turret3C_D0", Aircraft.xyz,
						Aircraft.ypr);
			}
		} else if ((this.iGunPos == 1) && (this.fGunPos < 1.0F)) {
			this.fGunPos += 0.2F * f;
			this.resetYPRmodifier();
			Aircraft.xyz[1] = -0.635F + (0.635F * this.fGunPos);
			this.hierMesh().chunkSetLocate("Turret3C_D0", Aircraft.xyz,
					Aircraft.ypr);
			if (this.fGunPos > 0.8F) {
				if (this.fGunPos < 0.9F) {
					;
				}
			}
		}
		if (this.FM.AS.isMaster()) {
			if ((this.FM.turret[2].target != null)
					&& (this.FM.AS.astatePilotStates[5] < 90)) {
				this.iGunPos = 1;
			}
			if (Time.current() > this.btme) {
				this.btme = Time.current()
						+ World.Rnd().nextLong(5000L, 12000L);
				if (this.FM.turret[2].target == null) {
					this.iGunPos = 0;
				}
			}
		}
	}
}
