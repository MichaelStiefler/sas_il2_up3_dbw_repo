// Source File Name: BombTorpMk34.java
// Author:           initially unknown
// Last Modified by: Storebror 2012-02-05
//
// This file is part of the SAS IL-2 1946 4.11 AI flyable aircraft package

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

public class BombTorpMk34 extends Torpedo {

	static {
		Class class1 = com.maddox.il2.objects.weapons.BombTorpMk34.class;
		Property.set(class1, "mesh", "3DO/Arms/Mk34_Torpedo/mono.sim");
		Property.set(class1, "radius", 90.8F);
		Property.set(class1, "power", 160F);
		Property.set(class1, "powerType", 0);
		Property.set(class1, "kalibr", 0.569F);
		Property.set(class1, "massa", 874.1F);
		Property.set(class1, "sound", "weapon.torpedo");
		Property.set(class1, "velocity", 17F);
		Property.set(class1, "traveltime", 150F);
		Property.set(class1, "startingspeed", 0.0F);
	}

	public BombTorpMk34() {
	}
}
