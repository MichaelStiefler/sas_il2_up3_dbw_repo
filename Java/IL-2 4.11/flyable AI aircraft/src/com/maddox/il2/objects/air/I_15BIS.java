// Source File Name: I_15BIS.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_15BIS extends I_15xyz {

	static {
		Class class1 = I_15BIS.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "I-15bis");
		Property.set(class1, "meshName", "3DO/Plane/I-15bis/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFCSPar08());
		Property.set(class1, "originCountry", PaintScheme.countryRussia);
		Property.set(class1, "yearService", 1937F);
		Property.set(class1, "yearExpired", 1942F);
		Property.set(class1, "FlightModel", "FlightModels/I-15bis.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitI_15bis.class });
		Property.set(class1, "LOSElevation", 0.84305F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1, 2, 2,
				2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01",
				"_ExternalRock02", "_ExternalRock03", "_ExternalRock04",
				"_ExternalRock05", "_ExternalRock06", "_ExternalRock07",
				"_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01",
				"_ExternalDev02", "_ExternalDev03", "_ExternalDev04",
				"_ExternalDev05", "_ExternalDev06", "_ExternalDev07",
				"_ExternalDev08" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425",
				"MGunPV1sipzl 425", null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "4xAO10",
				new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775",
						"MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null,
						null, null, null, null, null, null, "BombGunAO10 1",
						"BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1",
						null, null, null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "2xAO10_2xFAB50", new String[] {
				"MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425",
				"MGunPV1sipzl 425", null, null, null, null, null, null, null,
				null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50 1",
				"BombGunFAB50 1", null, null, null, null, null, null, null,
				null });
		Aircraft.weaponsRegister(class1, "2xFAB50", new String[] {
				"MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425",
				"MGunPV1sipzl 425", null, null, null, null, null, null, null,
				null, null, null, "BombGunFAB50", "BombGunFAB50", null, null,
				null, null, null, null, null, null });
		Aircraft.weaponsRegister(class1, "4xRS82", new String[] {
				"MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425",
				"MGunPV1sipzl 425", null, null, "RocketGunRS82",
				"RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null,
				null, null, null, null, null, null, "PylonRO_82_1",
				"PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null,
				null, null });
	}

	public I_15BIS() {
	}
}
