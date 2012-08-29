// Source File Name: BF_109G6MOLNAR.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class BF_109G6MOLNAR extends BF_109 implements TypeBNZFighter,
		TypeAcePlane {

	public static void moveGear(HierMesh hiermesh, float f) {
		float f1 = 0.8F;
		float f2 = (-0.5F * (float) Math.cos((f / f1) * 3.1415926535897931D)) + 0.5F;
		if ((f <= f1) || (f == 1.0F)) {
			hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
			hiermesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
		}
		f2 = (-0.5F * (float) Math
				.cos(((f - (1.0F - f1)) / f1) * 3.1415926535897931D)) + 0.5F;
		if (f >= (1.0F - f1)) {
			hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
			hiermesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
		}
		if (f > 0.99F) {
			hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
			hiermesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
			hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
			hiermesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
		}
		if (f < 0.01F) {
			hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
			hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
			hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
			hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
		}
	}

	private float kangle;

	static {
		Class class1 = BF_109G6MOLNAR.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Bf109");
		Property.set(class1, "meshName",
				"3do/plane/Bf-109G-6Early(ofMolnar)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "FlightModel", "FlightModels/Bf-109G-6Early.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitBF_109G6.class });
		Property.set(class1, "LOSElevation", 0.7498F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 1, 1, 1,
				1, 1, 9, 9, 9, 9, 3, 3, 3, 3, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03",
				"_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01",
				"_ExternalDev02", "_ExternalDev03", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_ExternalBomb05" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null,
				"MGunMG15120MGki 200", null, null, null, null, null, null,
				null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "U3-MK108", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65",
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "R1-SC250", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null,
				"MGunMG15120MGki 200", null, null, null, null, "PylonETC900",
				null, null, null, "BombGunSC250 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "R1-SC500", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null,
				"MGunMG15120MGki 200", null, null, null, null, "PylonETC900",
				null, null, null, "BombGunSC500 1", null, null, null, null });
		Aircraft.weaponsRegister(class1, "R2-SC50", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null,
				"MGunMG15120MGki 200", null, null, null, null, "PylonETC50",
				null, null, null, null, "BombGunSC50 1", "BombGunSC50 1",
				"BombGunSC50 1", "BombGunSC50 1" });
		Aircraft.weaponsRegister(class1, "R3-DROPTANK",
				new String[] { "MGunMG131si 300", "MGunMG131si 300", null,
						"MGunMG15120MGki 200", null, null, null, null,
						"PylonETC900", "FuelTankGun_Type_D", null, null, null,
						null, null, null, null });
		Aircraft.weaponsRegister(class1, "R5-MK108", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", null,
				"MGunMG15120MGki 200", null, null, "MGunMK108k 35",
				"MGunMK108k 35", null, null, "PylonMk108", "PylonMk108", null,
				null, null, null, null });
		Aircraft.weaponsRegister(class1, "R6-MG151-20", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", "MGunMG15120MGki 200",
				null, "MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null,
				null, null, "PylonMG15120", "PylonMG15120", null, null, null,
				null, null });
		Aircraft.weaponsRegister(class1, "U3R6-MG151-20", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", null,
				"MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null, null,
				null, "PylonMG15120", "PylonMG15120", null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "R3R6-MG151-20", new String[] {
				"MGunMG131si 300", "MGunMG131si 300", "MGunMG15120MGki 200",
				null, "MGunMG15120MGk 135", "MGunMG15120MGk 135", null, null,
				"PylonETC900", "FuelTankGun_Type_D", "PylonMG15120",
				"PylonMG15120", null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null });
	}

	public BF_109G6MOLNAR() {
		this.kangle = 0.0F;
	}

	protected void moveGear(float f) {
		float f1 = 0.9F - (((Wing) this.getOwner()).aircIndex(this) * 0.1F);
		float f2 = (-0.5F * (float) Math.cos((f / f1) * 3.1415926535897931D)) + 0.5F;
		if ((f <= f1) || (f == 1.0F)) {
			this.hierMesh()
					.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
			this.hierMesh()
					.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
		}
		f2 = (-0.5F * (float) Math
				.cos(((f - (1.0F - f1)) / f1) * 3.1415926535897931D)) + 0.5F;
		if (f >= (1.0F - f1)) {
			this.hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
			this.hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
		}
		if (f > 0.99F) {
			this.hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
			this.hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
			this.hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
			this.hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
		}
	}

	public void moveSteering(float f) {
		if (this.FM.CT.getGear() >= 0.98F) {
			this.hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
		}
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}

	public void update(float f) {
		if (this.FM.getSpeed() > 5F) {
			this.hierMesh()
					.chunkSetAngles(
							"SlatL_D0",
							0.0F,
							Aircraft.cvt(this.FM.getAOA(), 6.8F, 11F, 0.0F,
									1.5F), 0.0F);
			this.hierMesh()
					.chunkSetAngles(
							"SlatR_D0",
							0.0F,
							Aircraft.cvt(this.FM.getAOA(), 6.8F, 11F, 0.0F,
									1.5F), 0.0F);
		}
		this.hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20F * this.kangle,
				0.0F);
		this.hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20F * this.kangle,
				0.0F);
		this.hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20F * this.kangle,
				0.0F);
		this.hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20F * this.kangle,
				0.0F);
		this.kangle = (0.95F * this.kangle)
				+ (0.05F * this.FM.EI.engines[0].getControlRadiator());
		if (this.kangle > 1.0F) {
			this.kangle = 1.0F;
		}
		super.update(f);
	}
}
