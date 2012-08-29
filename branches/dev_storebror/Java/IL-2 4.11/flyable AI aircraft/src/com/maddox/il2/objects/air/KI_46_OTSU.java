// Source File Name: KI_46_OTSU.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

public class KI_46_OTSU extends KI_46 implements TypeFighter {

	public boolean bChangedPit;

	static {
		Class class1 = KI_46_OTSU.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Ki-46");
		Property.set(class1, "meshName",
				"3DO/Plane/Ki-46(Otsu)(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "yearService", 1944F);
		Property.set(class1, "yearExpired", 1948F);
		Property.set(class1, "FlightModel", "FlightModels/Ki-46-IIIKai.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitKI_46_OTSU.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 1, 1 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_MGUN01",
				"_MGUN02" });
		Aircraft.weaponsRegister(class1, "default", new String[] {
				"MGunHo5ki 200", "MGunHo5ki 200" });
		Aircraft.weaponsRegister(class1, "none", new String[] { null, null });
	}

	public KI_46_OTSU() {
		this.bChangedPit = true;
	}

	protected void nextCUTLevel(String s, int i, Actor actor) {
		super.nextCUTLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			this.bChangedPit = true;
		}
	}

	protected void nextDMGLevel(String s, int i, Actor actor) {
		super.nextDMGLevel(s, i, actor);
		if (this.FM.isPlayers()) {
			this.bChangedPit = true;
		}
	}
}
