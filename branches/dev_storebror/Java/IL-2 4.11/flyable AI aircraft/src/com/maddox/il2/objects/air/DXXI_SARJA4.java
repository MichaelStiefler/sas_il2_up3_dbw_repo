// Source File Name: DXXI_SARJA4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class DXXI_SARJA4 extends DXXI {

	private boolean skisLocked;

	static {
		Class class1 = DXXI_SARJA4.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "D.XXI");
		Property.set(class1, "meshName", "3DO/Plane/DXXI_SARJA4/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
		Property.set(class1, "yearService", 1941F);
		Property.set(class1, "yearExpired", 1945F);
		Property.set(class1, "FlightModel", "FlightModels/FokkerS4.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitDXXI_SARJA4.class });
		Property.set(class1, "LOSElevation", 0.8472F);
		Property.set(class1, "originCountry", PaintScheme.countryFinland);
		Aircraft.weaponTriggersRegister(class1, new int[] { 10 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01" });
		Aircraft.weaponsRegister(class1, "default",
				new String[] { "MGunMG15t 750" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null });
	}

	public DXXI_SARJA4() {
		this.skisLocked = false;
	}

	public void missionStarting() {
		super.missionStarting();
		if ((World.cur().camouflage == 1) && !this.FM.isPlayers()
				&& this.FM.isWasAirborne()) {
			this.FM.Gears.bTailwheelLocked = true;
		}
	}

	protected void moveFan(float f) {
		if (Config.isUSE_RENDER()) {
			super.moveFan(-f);
			float f1 = this.FM.CT.getAileron();
			float f2 = this.FM.CT.getElevator();
			this.hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9F * f1,
					cvt(f2, -1F, 1.0F, -8F, 9.5F));
			this.hierMesh()
					.chunkSetAngles(
							"pilotarm2_d0",
							cvt(f1, -1F, 1.0F, 14F, -16F),
							0.0F,
							cvt(f1, -1F, 1.0F, 6F, -8F)
									- cvt(f2, -1F, 1.0F, -37F, 35F));
			this.hierMesh().chunkSetAngles(
					"pilotarm1_d0",
					0.0F,
					0.0F,
					cvt(f1, -1F, 1.0F, -16F, 14F)
							+ cvt(f2, -1F, 0.0F, -61F, 0.0F)
							+ cvt(f2, 0.0F, 1.0F, 0.0F, 43F));
			float f3 = this.FM.CT.getRadiator() * 30F;
			this.hierMesh().chunkSetAngles("cowlf1_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf2_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf3_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf4_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf5_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf6_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf7_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf8_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf9_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf10_d0", 0.0F, f3, 0.0F);
			this.hierMesh().chunkSetAngles("cowlf0_d0", 0.0F, f3, 0.0F);
			if (World.cur().camouflage == 1) {
				float f4 = Aircraft.cvt(this.FM.Or.getTangage(), -20F, 20F,
						-20F, 20F);
				if (this.FM.Gears.onGround() && (this.FM.CT.getGear() > 0.9F)
						&& (this.FM.getSpeed() > 5F)) {
					if (this.FM.Gears.gWheelSinking[0] > 0.0F) {
						this.hierMesh().chunkSetAngles("GearL21_D0",
								World.Rnd().nextFloat(-0.5F, 0.5F),
								World.Rnd().nextFloat(-1F, 1.0F) + f4,
								World.Rnd().nextFloat(-0.5F, 0.5F));
					} else {
						this.hierMesh().chunkSetAngles("GearL21_D0", 0.0F, f4,
								0.0F);
					}
					if (this.FM.Gears.gWheelSinking[1] > 0.0F) {
						this.hierMesh().chunkSetAngles("GearR21_D0",
								World.Rnd().nextFloat(-1F, 1.0F),
								World.Rnd().nextFloat(-1F, 1.0F) + f4,
								World.Rnd().nextFloat(-1F, 1.0F));
					} else {
						this.hierMesh().chunkSetAngles("GearR21_D0", 0.0F, f4,
								0.0F);
					}
					this.hierMesh().chunkSetAngles(
							"GearC11_D0",
							0.0F,
							Aircraft.cvt(this.FM.Or.getTangage(), -20F, 20F,
									-20F, 20F), 0.0F);
				} else if (this.FM.Gears.bTailwheelLocked) {
					if (this.skisLocked) {
						this.hierMesh().chunkSetAngles("GearL21_D0", 0.0F,
								0.0F, 0.0F);
						this.hierMesh().chunkSetAngles("GearR21_D0", 0.0F,
								0.0F, 0.0F);
						this.hierMesh().chunkSetAngles("GearC11_D0", 0.0F,
								0.0F, 0.0F);
					} else {
						this.hierMesh().chunkSetAngles("GearL21_D0", 0.0F, f4,
								0.0F);
						this.hierMesh().chunkSetAngles("GearR21_D0", 0.0F, f4,
								0.0F);
						this.hierMesh().chunkSetAngles("GearC11_D0", 0.0F, f4,
								0.0F);
						if ((f4 < 0.1F) && (f4 > -0.1F)) {
							this.skisLocked = true;
						}
					}
				} else {
					this.skisLocked = false;
					this.hierMesh()
							.chunkSetAngles("GearL21_D0", 0.0F, f4, 0.0F);
					this.hierMesh()
							.chunkSetAngles("GearR21_D0", 0.0F, f4, 0.0F);
					this.hierMesh()
							.chunkSetAngles("GearC11_D0", 0.0F, f4, 0.0F);
				}
			} else {
				this.hierMesh().chunkSetAngles("GearL21_D0", 0.0F, -100F, 0.0F);
				this.hierMesh().chunkSetAngles("GearR21_D0", 0.0F, -100F, 0.0F);
			}
		}
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			this.bChangedPit = true;
		}
		if (World.cur().camouflage != 1) {
			if (this.hierMesh().isChunkVisible("GearR22_D2")
					&& !this.hierMesh().isChunkVisible("gearr31_d0")) {
				this.hierMesh().chunkVisible("gearr31_d0", true);
				this.hierMesh().chunkVisible("gearr32_d0", true);
				Wreckage wreckage = new Wreckage(this, this.hierMesh()
						.chunkFind("GearR22_D1"));
				wreckage.collide(true);
				Vector3d vector3d = new Vector3d();
				vector3d.set(this.FM.Vwld);
				wreckage.setSpeed(vector3d);
			}
			if (this.hierMesh().isChunkVisible("GearL22_D2")
					&& !this.hierMesh().isChunkVisible("gearl31_d0")) {
				this.hierMesh().chunkVisible("gearl31_d0", true);
				this.hierMesh().chunkVisible("gearl32_d0", true);
				Wreckage wreckage1 = new Wreckage(this, this.hierMesh()
						.chunkFind("GearL22_D1"));
				wreckage1.collide(true);
				Vector3d vector3d1 = new Vector3d();
				vector3d1.set(this.FM.Vwld);
				wreckage1.setSpeed(vector3d1);
			}
		} else {
			if ((this.hierMesh().isChunkVisible("GearR11_D1") || this
					.hierMesh().isChunkVisible("GearR21_D2"))
					&& !this.hierMesh().isChunkVisible("gearr31_d0")) {
				this.hierMesh().chunkVisible("GearR11_D1", true);
				this.hierMesh().chunkVisible("GearR11_D0", false);
				this.hierMesh().chunkVisible("gearr31_d0", true);
				this.hierMesh().chunkVisible("gearr32_d0", true);
				Wreckage wreckage2 = new Wreckage(this, this.hierMesh()
						.chunkFind("GearR11_D0"));
				wreckage2.collide(true);
				Vector3d vector3d2 = new Vector3d();
				vector3d2.set(this.FM.Vwld);
				wreckage2.setSpeed(vector3d2);
			}
			if ((this.hierMesh().isChunkVisible("GearL11_D1") || this
					.hierMesh().isChunkVisible("GearL21_D2"))
					&& !this.hierMesh().isChunkVisible("gearl31_d0")) {
				this.hierMesh().chunkVisible("GearL11_D1", true);
				this.hierMesh().chunkVisible("GearL11_D0", false);
				this.hierMesh().chunkVisible("gearl31_d0", true);
				this.hierMesh().chunkVisible("gearl32_d0", true);
				Wreckage wreckage3 = new Wreckage(this, this.hierMesh()
						.chunkFind("GearL11_D0"));
				wreckage3.collide(true);
				Vector3d vector3d3 = new Vector3d();
				vector3d3.set(this.FM.Vwld);
				wreckage3.setSpeed(vector3d3);
			}
		}
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.hasSelfSealingTank = true;
		this.canopyMaxAngle = 0.45F;
		if (Config.isUSE_RENDER() && (World.cur().camouflage == 1)) {
			this.hierMesh().chunkVisible("GearL1_D0", false);
			this.hierMesh().chunkVisible("GearL22_D0", false);
			this.hierMesh().chunkVisible("GearR1_D0", false);
			this.hierMesh().chunkVisible("GearR22_D0", false);
			this.hierMesh().chunkVisible("GearC1_D0", false);
			this.hierMesh().chunkVisible("GearL31_D0", false);
			this.hierMesh().chunkVisible("GearL32_D0", false);
			this.hierMesh().chunkVisible("GearR31_D0", false);
			this.hierMesh().chunkVisible("GearR32_D0", false);
			this.hierMesh().chunkVisible("GearC11_D0", true);
			this.hierMesh().chunkVisible("GearL11_D0", true);
			this.hierMesh().chunkVisible("GearL21_D0", true);
			this.hierMesh().chunkVisible("GearR11_D0", true);
			this.hierMesh().chunkVisible("GearR21_D0", true);
			this.FM.CT.bHasBrakeControl = false;
		} else if (Math.random() < 0.0099999997764825821D) {
			this.removeWheelSpats();
		}
	}

	private void removeWheelSpats() {
		this.hierMesh().chunkVisible("GearR22_D0", false);
		this.hierMesh().chunkVisible("GearL22_D0", false);
		this.hierMesh().chunkVisible("GearR22_D2", true);
		this.hierMesh().chunkVisible("GearL22_D2", true);
		this.hierMesh().chunkVisible("gearl31_d0", true);
		this.hierMesh().chunkVisible("gearl32_d0", true);
		this.hierMesh().chunkVisible("gearr31_d0", true);
		this.hierMesh().chunkVisible("gearr32_d0", true);
	}
}
