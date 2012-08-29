// Source File Name: KI_46_RECCE.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

public class KI_46_RECCE extends KI_46 implements TypeFighter {

	public boolean bChangedPit;

	static {
		Class class1 = KI_46_RECCE.class;
		new NetAircraft.SPAWN(class1);
		Property.set(class1, "iconFar_shortClassName", "Ki-46");
		Property.set(class1, "meshName",
				"3DO/Plane/Ki-46(Recce)(Multi1)/hier.him");
		Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
		Property.set(class1, "yearService", 1943F);
		Property.set(class1, "yearExpired", 1948F);
		Property.set(class1, "FlightModel", "FlightModels/Ki-46-IIIRecce.fmd");
		Property.set(class1, "cockpitClass",
				new Class[] { CockpitKI_46_RECCE.class });
		Aircraft.weaponTriggersRegister(class1, new int[] { 0 });
		Aircraft.weaponHooksRegister(class1, new String[] { "_Clip04" });
		Aircraft.weaponsRegister(class1, "default", new String[] { null });
		Aircraft.weaponsRegister(class1, "none", new String[] { null });
	}

	public KI_46_RECCE() {
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
