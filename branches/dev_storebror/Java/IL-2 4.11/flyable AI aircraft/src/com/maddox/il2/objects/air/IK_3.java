// Source File Name: IK_3.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-04-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class IK_3 extends IK_3xyz implements TypeFighter {

	public IK_3() {
	}

	static {
		Class class1 = com.maddox.il2.objects.air.IK_3.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "IK-3");
		Property.set(class1, "meshName", "3DO/Plane/IK-3(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "yearService", 1939F);
		Property.set(class1, "yearExpired", 1942F);
		Property.set(class1, "FlightModel", "FlightModels/IK-3.fmd");
		Property.set(class1, "LOSElevation", 0.5926F);
		Property.set(class1, "cockpitClass",
				new Class[] { com.maddox.il2.objects.air.CockpitHURRI.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_CANNON01" });
		weaponsRegister(class1, "default", new String[] { "MGunMG17si 500",
				"MGunMG17si 500", "MGunHispanoMkIkpzl 60" });
		weaponsRegister(class1, "none", new String[] { null, null, null });
	}
}
