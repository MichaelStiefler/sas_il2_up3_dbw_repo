// Source File Name: BF_110C4.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

public class BF_110C4 extends BF_110 {

	public static boolean bChangedPit = false;

	static {
		Class class1 = BF_110C4.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Bf-110");
		Property.set(class1, "meshName", "3DO/Plane/Bf-110C-4/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeBMPar01());
		Property.set(class1, "yearService", 1940F);
		Property.set(class1, "yearExpired", 1945.5F);
		Property.set(class1, "FlightModel", "FlightModels/Bf-110C-4.fmd");
		Property.set(class1, "cockpitClass", new Class[] {
				CockpitBF_110C4.class, CockpitBF_110C4_Gunner.class });
		Property.set(class1, "LOSElevation", 0.66895F);
		Aircraft.weaponTriggersRegister(class1, new int[] { 0, 0, 0, 0, 1, 1,
				10 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02",
				"_MGUN05" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunMG17ki 1000", "MGunMG17ki 990", "MGunMG17ki 1029",
				"MGunMG17ki 1008", "MGunMGFFk 180", "MGunMGFFk 180",
				"MGunMG15t 750" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null,
				null, null, null, null, null });
	}

	public BF_110C4() {
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
