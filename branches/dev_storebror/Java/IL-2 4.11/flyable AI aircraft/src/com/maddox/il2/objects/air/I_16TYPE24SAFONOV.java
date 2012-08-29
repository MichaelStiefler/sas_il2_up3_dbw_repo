// Source File Name: I_16TYPE24SAFONOV.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class I_16TYPE24SAFONOV extends I_16 implements TypeAcePlane {

	static {
		Class class1 = I_16TYPE24SAFONOV.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "I-16");
		Property.set(class1, "meshName",
				"3DO/Plane/I-16type24(ofSafonov)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "FlightModel",
				"FlightModels/I-16type24(ofSafonov).fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitI_16TYPE24.class });
		Property.set(class1, "LOSElevation", 0.82595F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_CANNON01",
				"_CANNON02", "_MGUN01", "_MGUN02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunShKASsi 240", "MGunShKASsi 240", "MGunShVAKk 120",
				"MGunShVAKk 120" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null });
	}

	public I_16TYPE24SAFONOV() {
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}
}
