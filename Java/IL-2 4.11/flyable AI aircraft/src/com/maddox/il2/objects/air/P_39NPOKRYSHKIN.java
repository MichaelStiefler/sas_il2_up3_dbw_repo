// Source File Name: P_39NPOKRYSHKIN.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class P_39NPOKRYSHKIN extends P_39 implements TypeAcePlane {

	static {
		Class class1 = P_39NPOKRYSHKIN.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "P39");
		Property.set(class1, "meshName",
				"3do/plane/P-39N(ofPokryshkin)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "FlightModel",
				"FlightModels/P-39N(ofPokryshkin).fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitP_39N1.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 1, 1, 1, 1, 1, 1,
				1, 3 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06",
				"_CANNON01", "_ExternalBomb01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning50si 320", "MGunBrowning50si 320",
				"MGunBrowning303k 1300", "MGunBrowning303k 1300",
				"MGunBrowning303k 1300", "MGunBrowning303k 1300",
				"MGunM4ki 60", null });
		Aircraft.weaponsRegister(class1, "1xFAB250", new String[] {
				"MGunBrowning50si 320", "MGunBrowning50si 320",
				"MGunBrowning303k 1300", "MGunBrowning303k 1300",
				"MGunBrowning303k 1300", "MGunBrowning303k 1300",
				"MGunM4ki 60", "BombGunFAB250 1" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null, null });
	}

	public P_39NPOKRYSHKIN() {
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}
}
