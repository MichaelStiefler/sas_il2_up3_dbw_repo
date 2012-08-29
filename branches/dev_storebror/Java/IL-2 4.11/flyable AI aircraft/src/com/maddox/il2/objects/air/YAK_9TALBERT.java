// Source File Name: YAK_9TALBERT.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class YAK_9TALBERT extends YAK_9TX implements TypeAcePlane {

	static {
		Class class1 = YAK_9TALBERT.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Yak");
		Property.set(class1, "meshName", "3DO/Plane/Yak-9T(ofAlbert)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
		Property.set(class1, "FlightModel", "FlightModels/Yak-9T.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitYAK_9T.class });
		Property.set(class1, "LOSElevation", 0.661F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_CANNON01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunUBsi 200", "MGunNS37ki 30" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null });
	}

	public YAK_9TALBERT() {
	}

	public void onAircraftLoaded() {
		super.onAircraftLoaded();
		this.FM.Skill = 3;
	}
}
