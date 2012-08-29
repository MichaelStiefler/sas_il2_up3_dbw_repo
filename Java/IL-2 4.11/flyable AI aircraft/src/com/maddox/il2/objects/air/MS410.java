// Source File Name: MS410.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.Property;

public class MS410 extends MS400X {

	static {
		Class class1 = MS410.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Morane");
		Property.set(class1, "meshNameDemo", "3DO/Plane/MS410(fi)/hier.him");
		Property.set(class1, "meshName_fi", "3DO/Plane/MS410(fi)/hier.him");
		Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
		Property.set(class1, "meshName", "3DO/Plane/MS410(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "yearService", 1938F);
		Property.set(class1, "yearExpired", 1951.8F);
		Property.set(class1, "FlightModel", "FlightModels/MS410.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitMorane40.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG15k 300", "MGunMG15k 300", "MGunMG15k 300",
				"MGunMG15k 300", "MGunHispanoMkIki 60" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null });
	}

	public MS410() {
	}

	public void update(float f) {
		this.hierMesh().chunkSetAngles(
				"OilRad_D0",
				0.0F,
				-20F
						* ((FlightModelMain) (super.FM)).EI.engines[0]
								.getControlRadiator(), 0.0F);
		super.update(f);
	}
}
