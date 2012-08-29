// Source File Name: LA_7KOJEDUB.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class LA_7KOJEDUB extends LA_X implements TypeAcePlane {

	static {
		Class class1 = LA_7KOJEDUB.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "La");
		Property.set(class1, "meshName", "3DO/Plane/La-7(ofKojedub)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/La-7.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitLA_7.class });
		Property.set(class1, "LOSElevation", 0.730618F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 1, 1, 3, 3, 9, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_CANNON01",
				"_CANNON02", "_ExternalBomb01", "_ExternalBomb02",
				"_ExternalBomb01", "_ExternalBomb02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null,
				null, null, null });
		Aircraft.weaponsRegister(class1, "2xFAB50", new String[] {
				"MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340",
				"BombGunFAB50 1", "BombGunFAB50 1", null, null });
		Aircraft.weaponsRegister(class1, "2xFAB100", new String[] {
				"MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340",
				"BombGunFAB100 1", "BombGunFAB100 1", null, null });
		Aircraft.weaponsRegister(class1, "2xDROPTANK", new String[] {
				"MGunShVAKANTIMATTERs 340", "MGunShVAKANTIMATTERs 340", null,
				null, "FuelTankGun_Tank80", "FuelTankGun_Tank80" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null });
	}

	public LA_7KOJEDUB() {
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}
}
