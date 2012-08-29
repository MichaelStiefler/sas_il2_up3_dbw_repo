// Source File Name: A5M4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-04
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

public class A5M4 extends A5M {

	public static boolean bChangedPit = false;

	static {
		Class class1 = A5M4.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "A5M");
		Property.set(class1, "meshName", "3DO/Plane/A5M4(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
		Property.set(class1, "meshName_ja", "3DO/Plane/A5M4(ja)/hier.him");
		Property.set(class1, "PaintScheme_ja", new PaintSchemeFCSPar05());
		Property.set(class1, "yearService", 1938F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/A5M4.fmd");
		Property.set(class1, "cockpitClass", new Class[] { CockpitA5M4.class });
		Property.set(class1, "LOSElevation", 0.7498F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 9, 9 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_ExternalDev01", "_ExternalDev02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunVikkersKs 500", "MGunVikkersKs 500", null, null });
		Aircraft.weaponsRegister(class1, "1xdt", new String[] {
				"MGunVikkersKs 500", "MGunVikkersKs 500", "PylonA5MPLN1",
				"FuelTankGun_TankA5M" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null });
	}

	public A5M4() {
		this.flapps = 0.0F;
	}

	protected void nextCUTLevel(String s, int i, Actor actor) {
		super.nextCUTLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			bChangedPit = true;
		}
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			bChangedPit = true;
		}
	}
}
