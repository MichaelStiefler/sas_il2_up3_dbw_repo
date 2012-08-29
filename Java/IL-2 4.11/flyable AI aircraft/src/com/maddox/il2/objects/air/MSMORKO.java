// Source File Name: MSMORKO.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.rts.Property;

public class MSMORKO extends MS400X {

	static {
		Class class1 = MSMORKO.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Morane");
		Property.set(class1, "meshNameDemo", "3DO/Plane/MSMorko(fi)/hier.him");
		Property.set(class1, "meshName_fi", "3DO/Plane/MSMorko(fi)/hier.him");
		Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
		Property.set(class1, "meshName", "3DO/Plane/MSMorko(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1951.8F);
		Property.set(class1, "FlightModel", "FlightModels/MSMorko.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitMorane.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_CANNON01" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG15k 300", "MGunMG15k 300", "MGunHispanoMkIki 60" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null });
	}

	public MSMORKO() {
	}

	public void update(float f) {
		this.hierMesh().chunkSetAngles("OilRad_D0", 0.0F,
				-20F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
		super.update(f);
	}
}
