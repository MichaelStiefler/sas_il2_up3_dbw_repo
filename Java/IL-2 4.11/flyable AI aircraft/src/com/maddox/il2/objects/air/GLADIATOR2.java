// Source File Name: GLADIATOR2.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class GLADIATOR2 extends GLADIATOR {

	static {
		Class class1 = GLADIATOR2.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Gladiator");
		Property.set(class1, "meshName",
				"3DO/Plane/GladiatorMkII(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1943F);
		Property.set(class1, "FlightModel", "FlightModels/GladiatorMkII.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitGLADIATOR2.class });
		Property.set(class1, "LOSElevation", 0.8472F);
		Property.set(class1, "originCountry", PaintScheme.countryFinland);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600",
				"MGunBrowning303k 400", "MGunBrowning303k 400" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null });
	}

	public GLADIATOR2() {
	}
}
