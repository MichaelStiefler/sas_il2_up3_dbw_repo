// Source File Name: G_55_Late.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class G_55_Late extends G_55xyz {

	static {
		Class class1 = G_55_Late.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "G.55");
		Property.set(class1, "meshName_it", "3DO/Plane/G-55(it)/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
		Property.set(class1, "meshName", "3DO/Plane/G-55(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/G-55-late.fmd");
		Property.set(class1, "LOSElevation", 0.9119F);
		Property.set(class1, "cockpitClass", new Class[] { CockpitG_55.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55 300",
				"MGunMG15120t 200", "MGunMG15120kh 250", "MGunMG15120kh 250" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null });
	}

	public G_55_Late() {
	}
}
