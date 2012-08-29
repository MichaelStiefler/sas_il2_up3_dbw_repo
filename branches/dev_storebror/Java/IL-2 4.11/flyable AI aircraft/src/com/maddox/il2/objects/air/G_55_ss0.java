// Source File Name: G_55_ss0.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class G_55_ss0 extends G_55xyz {

	static {
		Class class1 = G_55_ss0.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "G.55");
		Property.set(class1, "meshName_it", "3DO/Plane/G-55_ss0(it)/hier.him");
		Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
		Property.set(class1, "meshName", "3DO/Plane/G-55_ss0(multi)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1948.5F);
		Property.set(class1, "FlightModel", "FlightModels/G-55_ss0.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitG_55.class });
		Property.set(class1, "LOSElevation", 0.9119F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunBredaSAFAT127g55 300", "MGunBredaSAFAT127g55 300",
				"MGunBredaSAFAT127g55k 300", "MGunBredaSAFAT127g55k 300",
				"MGunMG15120t 200" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null });
	}

	public G_55_ss0() {
	}
}
