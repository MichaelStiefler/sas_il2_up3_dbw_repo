// Source File Name: MIG_3POKRYSHKIN.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class MIG_3POKRYSHKIN extends MIG_3 implements TypeAcePlane {

	private float kangle;

	static {
		Class class1 = MIG_3POKRYSHKIN.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "MiG");
		Property.set(class1, "meshName",
				"3do/plane/MIG-3(ofPokryshkin)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "FlightModel",
				"FlightModels/MiG-3(ofPokryshkin).fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitMIG_3.class });
		Property.set(class1, "LOSElevation", 0.906F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1, 1, 3,
				3, 3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01",
				"_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04",
				"_ExternalDev01", "_ExternalDev02", "_ExternalDev03",
				"_ExternalDev04", "_ExternalRock01", "_ExternalRock02",
				"_ExternalRock03", "_ExternalRock04", "_ExternalRock05",
				"_ExternalRock06" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2xBK", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600",
				"MGunUBk 145", "MGunUBk 145", null, null, null, null,
				"PylonMiG_3_BK", "PylonMiG_3_BK", null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "6xRS-82", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null,
				null, null, null, null, null, null, null, "PylonRO_82_3",
				"PylonRO_82_3", "RocketGunRS82 1", "RocketGunRS82 1",
				"RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1",
				"RocketGunRS82 1" });
		Aircraft.weaponsRegister(class1, "4xFAB-50", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null,
				null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1",
				"BombGunFAB50 1", null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "2xFAB-100", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null,
				null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null,
				null, null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4xAO-10", new String[] {
				"MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null,
				null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1",
				"BombGunAO10 1", null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null });
	}

	public MIG_3POKRYSHKIN() {
		this.kangle = 0.0F;
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}

	public void update(float f) {
		if (this.FM.getSpeed() > 5F) {
			this.hierMesh().chunkSetAngles("SlatL_D0",
					Aircraft.cvt(this.FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F),
					0.0F, 0.0F);
			this.hierMesh().chunkSetAngles("SlatR_D0",
					Aircraft.cvt(this.FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F),
					0.0F, 0.0F);
		}
		this.hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30F * this.kangle,
				0.0F);
		this.hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20F * this.kangle,
				0.0F);
		this.hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20F * this.kangle,
				0.0F);
		this.kangle = (0.95F * this.kangle)
				+ (0.05F * this.FM.EI.engines[0].getControlRadiator());
		super.update(f);
	}
}
